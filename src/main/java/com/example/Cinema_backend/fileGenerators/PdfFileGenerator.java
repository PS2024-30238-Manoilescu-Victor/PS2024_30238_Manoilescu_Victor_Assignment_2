package com.example.Cinema_backend.fileGenerators;

import com.example.Cinema_backend.dto.OrdersDTO;
import com.example.Cinema_backend.entity.Ticket;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PdfFileGenerator implements FileGenerator{
    private final OrdersDTO orderDTO;

    public PdfFileGenerator(OrdersDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    @Override
    public void generateFile() {
        String downloadPath = System.getProperty("user.home") + "\\Downloads\\OrderInfo.pdf";

        Document pdfDocument = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(pdfDocument, new FileOutputStream(downloadPath));
            pdfDocument.open();

            Paragraph title = new Paragraph("Order information for order " + orderDTO.getUuid());
            title.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(title);

            pdfDocument.add(new Paragraph(" "));
            pdfDocument.add(new Paragraph(" "));
            pdfDocument.add(new Paragraph(" "));


            Paragraph informationHeader = new Paragraph("Ticket           Price          Hour             Date");
            informationHeader.setAlignment(Element.ALIGN_CENTER);
            pdfDocument.add(informationHeader);

            for (Ticket ticket: orderDTO.getTickets()) {
                Paragraph element = new Paragraph("              " + ticket.getNume() + "          " + ticket.getPret() + "          " + ticket.getOra() + "           " + ticket.getData());
                element.setAlignment(Element.ALIGN_CENTER);
                pdfDocument.add(element);
            }

            pdfDocument.add(new Paragraph(" "));

            Paragraph purchaseDate = new Paragraph("Purchase date: " + orderDTO.getDataComanda());

            pdfDocument.add(purchaseDate);

            pdfDocument.close();
            writer.close();

        }
        catch (DocumentException | FileNotFoundException e) {
            System.out.println("Something went wrong while creating pdf file!");
        }
    }
}
