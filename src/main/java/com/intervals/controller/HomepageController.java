package com.intervals.controller;

        import com.intervals.model.Department;
        import com.intervals.model.JobType;
        import com.intervals.service.DepartmentService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.servlet.ModelAndView;

        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.util.List;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 20/Mar/2014
 */
@Controller
public class HomepageController {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/homepage")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("homepage-tile");
    }

    @RequestMapping("/homepage-admin")
    public ModelAndView indexAdmin(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("homepage-admin-tile");
        List<String> jobs = JobType.getAllValues();

        List<Department> departments = departmentService.loadAll();

        mv.addObject("allJobs", jobs);
        mv.addObject("allDeps", departments);

        return mv;
    }


}
