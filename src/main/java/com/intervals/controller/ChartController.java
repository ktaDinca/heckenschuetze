package com.intervals.controller;

import com.intervals.model.*;
import com.intervals.service.ActivityService;
import com.intervals.service.DepartmentService;
import com.intervals.service.ProjectService;
import com.intervals.service.WeeklySheetService;
import com.intervals.util.DateUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 29/Apr/2014
 */

@Controller
public class ChartController {

    @Autowired
    private WeeklySheetService weeklySheetService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/intervals/charts/weekly/project-hours-donut", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> loadProjectWorkForCurrentDonutChart(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object>  map = new HashMap<String, Object>();

        String start = request.getParameter("start");
        Date day = null;
        if (start == null || "undefined".equals(start)) {
            map.put("message", "failed");
            return map;
        }

        day = new Date(Long.parseLong(start));
        Date thisWeeksMonday = DateUtils.findThisWeeksMonday(day);
        Employee emp = (Employee) request.getSession().getAttribute("loggedInUser");
        WeeklySheet currentWeekly = weeklySheetService.findWeeklySheetByDateInWeek(thisWeeksMonday, emp);
        if (currentWeekly == null) {
            map.put("message", "failed");
            return map;
        }

        List<Activity> currentActivities = activityService.findAllByWeekly(currentWeekly);
        Map<String, Long> projectHoursMap = new HashMap<String, Long>();
        for (Activity a : currentActivities) {
            if (a.getProject() == null) {
                continue;
            }
            if (projectHoursMap.get(a.getProject().getName()) == null) {
                projectHoursMap.put(a.getProject().getName(), DateUtils.getDateDiff(a.getStart(), a.getEnd(), TimeUnit.HOURS));
            }
            else {
                Long oldValue = projectHoursMap.get(a.getProject().getName());
                projectHoursMap.put(a.getProject().getName(), oldValue + DateUtils.getDateDiff(a.getStart(), a.getEnd(), TimeUnit.HOURS));
            }
        }

        map.put("projectHours", projectHoursMap);
        map.put("message", "success");
        return map;
    }

    @RequestMapping(value = "/intervals/charts/weekly/working-hours-line", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loadWorkingHoursForWeekly(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        String start = request.getParameter("start");
        Date day = null;
        if (start == null || "undefined".equals(start)) {
            map.put("message", "failed");
            return map;
        }

        day = new Date(Long.parseLong(start));
        Date thisWeeksMonday = DateUtils.findThisWeeksMonday(day);
        Employee emp = (Employee) request.getSession().getAttribute("loggedInUser");
        WeeklySheet currentWeekly = weeklySheetService.findWeeklySheetByDateInWeek(thisWeeksMonday, emp);
        if (currentWeekly == null) {
            map.put("message", "failed");
            return map;
        }
        List<Activity> currentActivities = activityService.findAllByWeekly(currentWeekly);

        // ziua (monday), ora de inceput (8:40).
        Map<String, Date> startingHoursMap = new HashMap<String, Date>();
        Map<String, Date> endingHoursMap = new HashMap<String, Date>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Activity activity : currentActivities) {

            if(startingHoursMap.get(DateUtils.goTodayAt1Am(activity.getStart())) == null) {
                startingHoursMap.put(sdf.format(DateUtils.goTodayAt1Am(activity.getStart())), activity.getStart());
            }
            else {
                if (activity.getStart().before(startingHoursMap.get(DateUtils.goTodayAt1Am(activity.getStart())))) {
                    startingHoursMap.put(sdf.format(DateUtils.goTodayAt1Am(activity.getStart())), activity.getStart());
                }
            }

            if(endingHoursMap.get(DateUtils.goTodayAt1Am(activity.getEnd())) == null) {
                endingHoursMap.put(sdf.format(DateUtils.goTodayAt1Am(activity.getEnd())), activity.getEnd());
            }
            else {
                if (activity.getEnd().before(endingHoursMap.get(DateUtils.goTodayAt1Am(activity.getEnd())))) {
                    endingHoursMap.put(sdf.format(DateUtils.goTodayAt1Am(activity.getEnd())), activity.getEnd());
                }
            }
        }

        map.put("startings", startingHoursMap);
        map.put("endings", endingHoursMap);
        map.put("message", "success");

        return map;
    }


    @RequestMapping(value = "/intervals/charts/general/project-hours-donut", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loadWorkingHoursForAllProjects(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        Employee emp = (Employee) request.getSession().getAttribute("loggedInUser");
        if (emp == null) {
            return map;
        }

        Department dept = null;
        if (emp.getDepartment() == null) {
            dept = departmentService.findDepartmentByManager(emp);
            if (dept == null) {
                return map;
            }
        }

        List<Project> projects = projectService.findByDepartment(dept);
        Map<String, Long> projectHoursMap = getWorkingHours(projects);

        map.put("message", "success");
        map.put("generalWorkPerProject", projectHoursMap);

        return map;
    }


    @RequestMapping(value = "/intervals/charts/general/export/xls/work-department", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> exportWorkPerDepartmentAsXLS(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();

        Department dept = null;
        Employee emp = (Employee) request.getSession().getAttribute("loggedInUser");
        if (emp.getDepartment() == null) {
            dept = departmentService.findDepartmentByManager(emp);
            if (dept == null) {
                return map;
            }
        }

        List<Project> projects = projectService.findByDepartment(dept);
        Map<String, Long> projectHoursMap = getWorkingHours(projects);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("First sheet");

        SimpleDateFormat sdf = new SimpleDateFormat("yy-mm-dd-hh-mm");
        String filename = new String(dept.getName() + sdf.format(new Date()) + ".xls");

        Set<String> keyset = projectHoursMap.keySet();
        int rownum = 1;
        Row titleRow = sheet.createRow(rownum ++);
        Cell titleCell = titleRow.createCell(2);

        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);

        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 20);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.GREY_80_PERCENT.index);
        style.setFont(font);

        titleCell.setCellValue("The amount of work put into each project");
        titleCell.setCellStyle(style);

        ++rownum;

        HSSFCellStyle headingStyle = workbook.createCellStyle();
        headingStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);

        HSSFFont font2 = workbook.createFont();
        font2.setFontName(HSSFFont.FONT_ARIAL);
        font2.setFontHeightInPoints((short) 16);
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font2.setColor(HSSFColor.BLUE_GREY.index);
        headingStyle.setFont(font2);

        Row headingRow = sheet.createRow(rownum ++);
        Cell headingCell1 = headingRow.createCell(2);
        Cell headingCell2 = headingRow.createCell(3);

        headingCell1.setCellValue("Project");
        headingCell1.setCellStyle(headingStyle);

        headingCell2.setCellValue("Hours");
        headingCell2.setCellStyle(headingStyle);

        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Long val = projectHoursMap.get(key);
            int cellnum = 2;

            Cell cell = row.createCell(cellnum ++);
            cell.setCellValue(key);

            cell = row.createCell(cellnum);
            cell.setCellValue(val);
        }

        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);

        try {
            FileOutputStream out =
                    new FileOutputStream(new File("c:/reports/intervals/" + filename));
            workbook.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        map.put("filepath", filename);
        map.put("message", "success");
        return map;
    }


    @RequestMapping(value = "/intervals/charts/general/export/pdf/work-department", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> exportWorkPerDepartmentAsPDF(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        Department dept = null;
        Employee emp = (Employee) request.getSession().getAttribute("loggedInUser");
        if (emp.getDepartment() == null) {
            dept = departmentService.findDepartmentByManager(emp);
            if (dept == null) {
                return map;
            }
        }

        List<Project> projects = projectService.findByDepartment(dept);
        Map<String, Long> projectHoursMap = getWorkingHours(projects);

        SimpleDateFormat sdf = new SimpleDateFormat("yy-mm-dd-hh-mm");
        String filename = new String(dept.getName() + sdf.format(new Date()) + ".pdf");

        PdfWriter pdfWriter = null;
        Document document = new Document();
        try {
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("c:/reports/intervals/" + filename));
            document.open();

            Image image1 = Image.getInstance("c:/reports/intervals/intervals.png");
            image1.scalePercent(75);
            image1.setAlignment(Element.ALIGN_RIGHT);
            document.add(image1);

            Paragraph paragraph1 = new Paragraph();
            paragraph1.add(new Paragraph(" "));
            paragraph1.add(new Paragraph(" "));
            paragraph1.add(new Paragraph(" "));
            paragraph1.add(new Paragraph(" "));

            document.add(paragraph1);

            PdfPTable table = new PdfPTable(3);
            PdfPCell cell;
            cell = new PdfPCell(new Phrase("The amount of work put into each project"));
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(dept.getName()));
            cell.setRowspan(projectHoursMap.size());
            table.addCell(cell);

            for (String key : projectHoursMap.keySet()) {
                table.addCell(key);
                table.addCell(projectHoursMap.get(key).toString());
            }
//
//            table.addCell("row 1; cell 1");
//            table.addCell("row 1; cell 2");
//            table.addCell("row 2; cell 1");
//            table.addCell("row 2; cell 2");


            document.add(table);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
            if (pdfWriter != null) {
                pdfWriter.close();
            }
        }

        map.put("message", "success");
        map.put("filepath", filename);

        return map;
    }

    public Map<String, Long> getWorkingHours(List<Project> projects) {
        List<Activity> activities = null;
        Map<String, Long> projectHoursMap = new HashMap<String, Long>();
        for (Project p : projects) {
            activities = activityService.findAllByProject(p);
            projectHoursMap.put(p.getName(), new Long(0));
            for (Activity a : activities) {
                Long currentHours = DateUtils.getDateDiff(a.getStart(), a.getEnd(), TimeUnit.HOURS);
                Long previousHours = projectHoursMap.get(p.getName());
                projectHoursMap.put(p.getName(), currentHours + previousHours);
            }
        }
        return projectHoursMap;
    }

    @RequestMapping(value = "/intervals/reports/xls/{filepath}", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getXLSReports(HttpServletRequest request, HttpServletResponse response, @PathVariable("filepath") String filepath) {
        return new FileSystemResource("c:/reports/intervals/" + filepath + ".xls");
    }

    @RequestMapping(value = "/intervals/reports/pdf/{filepath}", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getPDFReports(HttpServletRequest request, HttpServletResponse response, @PathVariable("filepath") String filepath) {
        return new FileSystemResource("c:/reports/intervals/" + filepath + ".pdf");
    }


}
