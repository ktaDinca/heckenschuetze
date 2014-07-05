package com.airvals.service.impl;

import com.airvals.dao.TicketDao;
import com.airvals.model.FlightResult;
import com.airvals.model.Person;
import com.airvals.model.Ticket;
import com.airvals.service.TicketService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 23/Jun/2014
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDao ticketDao;

    @Override
    public void saveOrUpdate(Ticket t) {
        if (t == null) {
            return;
        }
        ticketDao.saveOrUpdate(t);
    }

    /**
     * This method is used to generate the actual pdf ticket/reservation
     * @param fr
     * @param p
     * @param code
     * @param action
     * @return path to the document!
     */
    @Override
    public String buildDocument(FlightResult fr, Person p, String code, String action) {
        Document doc = new Document();
        PdfWriter pdfWriter = null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd_mm_yy");
        String filename = p.getFamilyName() + "_" + fr.getId() + "_" + sdf.format(new Date());
        String path = "c:/temp/" + filename + ".pdf";

        try {
            pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(path));
            doc.addAuthor("Airvals - Dinca Alexandru");
            doc.addCreationDate();
            doc.addProducer();

            if ("buy".equals(action)) {
                doc.addTitle("Your Airvals Ticket");
            }
            else {
                doc.addTitle("Your Airvals Reservation");
            }

            doc.setPageSize(PageSize.LETTER);

            doc.open();
            PdfContentByte cb = pdfWriter.getDirectContent();

            Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                    Font.BOLD);

            Image image1 = Image.getInstance("c:/temp/airvals.png");
            image1.scalePercent(75);
            image1.setAlignment(Element.ALIGN_RIGHT);
            doc.add(image1);

            Paragraph paragraph1 = new Paragraph();
            paragraph1.add(new Paragraph(" "));
            Paragraph title = null;
            if ("buy".equals(action)) {
                title = new Paragraph("Ticket Confirmation");
                title.setAlignment(Element.ALIGN_CENTER);
            } else {
                title = new Paragraph("Reservation Confirmation");
                title.setAlignment(Element.ALIGN_CENTER);
            }
            paragraph1.add(title);
            paragraph1.add(new Paragraph(" "));

            paragraph1.add(new Paragraph("Thank you for choosing to book your flight at Airvals. You are provided " +
                    "with all the information regarding your reservation: "));
            paragraph1.add(new Paragraph(" "));

            if ("buy".equals(action)) {
                paragraph1.add(new Paragraph("Ticket code: " + code));
//                paragraph1.add(new Paragraph("Ticket status: active within 24 hours of issuing time"));
            } else {
                paragraph1.add(new Paragraph("Reservation code: " + code));
                paragraph1.add(new Paragraph("Reservation status: active within 24 hours of issuing time"));
            }

            paragraph1.add(new Paragraph(" "));
            paragraph1.add(new Paragraph("Name: " + p.getFamilyName() + " " + p.getSurname()));
            paragraph1.add(new Paragraph("Telephone number: " + p.getPhoneNumber()));
            paragraph1.add(new Paragraph("Email Address: " + p.getEmail()));
            paragraph1.add(new Paragraph(" "));

            if (fr.getOutBoundStep1() != null) {
                paragraph1.add(new Paragraph(fr.getOutBoundStep1().getTemplate().getSource().getCode() + " - " + fr.getOutBoundStep1().getTemplate().getDestination().getCode()));
                paragraph1.add(new Paragraph("Departure time: " + fr.getOutBoundStep1().getDeparture()));
                paragraph1.add(new Paragraph("Estimated Arrival time: " + fr.getOutBoundStep1().getDueArrival()));
                paragraph1.add(new Paragraph("Airplane: " + fr.getOutBoundStep1().getTemplate().getPlane().getName()));
                paragraph1.add(new Paragraph(" "));
            }
            if (fr.getOutBoundStep2() != null) {
                paragraph1.add(new Paragraph(fr.getOutBoundStep2().getTemplate().getSource().getCode() + " - " + fr.getOutBoundStep2().getTemplate().getDestination().getCode()));
                paragraph1.add(new Paragraph("Departure time: " + fr.getOutBoundStep2().getDeparture()));
                paragraph1.add(new Paragraph("Estimated Arrival time: " + fr.getOutBoundStep2().getDueArrival()));
                paragraph1.add(new Paragraph("Airplane: " + fr.getOutBoundStep2().getTemplate().getPlane().getName()));
                paragraph1.add(new Paragraph(" "));
            }
            if (fr.getInBoundStep1() != null) {
                paragraph1.add(new Paragraph(fr.getInBoundStep1().getTemplate().getSource().getCode() + " - " + fr.getInBoundStep1().getTemplate().getDestination().getCode()));
                paragraph1.add(new Paragraph("Departure time: " + fr.getInBoundStep1().getDeparture()));
                paragraph1.add(new Paragraph("Estimated Arrival time: " + fr.getInBoundStep1().getDueArrival()));
                paragraph1.add(new Paragraph("Airplane: " + fr.getInBoundStep1().getTemplate().getPlane().getName()));
                paragraph1.add(new Paragraph(" "));
            }
            if (fr.getInBoundStep2() != null) {
                paragraph1.add(new Paragraph(fr.getInBoundStep2().getTemplate().getSource().getCode() + " - " + fr.getInBoundStep2().getTemplate().getDestination().getCode()));
                paragraph1.add(new Paragraph("Departure time: " + fr.getInBoundStep2().getDeparture()));
                paragraph1.add(new Paragraph("Estimated Arrival time: " + fr.getInBoundStep2().getDueArrival()));
                paragraph1.add(new Paragraph("Airplane: " + fr.getInBoundStep2().getTemplate().getPlane().getName()));
                paragraph1.add(new Paragraph(" "));
            }

            paragraph1.add(new Paragraph("Please check in, at the airport 45 minutes before departure!"));
            paragraph1.add(new Paragraph("All taxes are included in the ticket, please make sure you pay before checking in!"));

            paragraph1.add(new Paragraph(" "));
            paragraph1.add(new Paragraph("Should you have any questions, please do not hesitate to contact Airvals personnel."));
            paragraph1.add(new Paragraph(" "));
            paragraph1.add(new Paragraph(" "));
            paragraph1.add(new Paragraph("Best regards, "));
            paragraph1.add(new Paragraph("Airvals Office"));
            paragraph1.add(new Paragraph(" "));
            paragraph1.add(new Paragraph(" "));

            doc.add(paragraph1);

            Barcode128 code128 = new Barcode128();
            code128.setCode(code);
            code128.setCodeType(Barcode128.CODE128);
            Image code128Image = code128.createImageWithBarcode(cb, null, null);
//            code128Image.setAbsolutePosition(10,700);
            code128Image.scalePercent(100);
            code128Image.setAlignment(Element.ALIGN_RIGHT);
            doc.add(code128Image);

            BarcodeQRCode qrcode = new BarcodeQRCode(code, 120, 120, null);
            Image qrcodeImage = qrcode.getImage();
//            qrcodeImage.setAbsolutePosition(10,500);
            qrcodeImage.setAlignment(Element.ALIGN_CENTER);
            doc.add(qrcodeImage);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (doc != null) {
                doc.close();
            }
            if (pdfWriter != null) {
                pdfWriter.close();
            }
        }

        return path;
    }
}
