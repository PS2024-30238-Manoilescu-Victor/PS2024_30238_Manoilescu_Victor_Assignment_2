package com.example.Cinema_backend.fileGenerators;

import com.example.Cinema_backend.dto.OrdersDTO;
import com.example.Cinema_backend.entity.Ticket;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class TextFileGenerator implements FileGenerator{
    private final OrdersDTO orderDTO;

    public TextFileGenerator(OrdersDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    @Override
    public void generateFile() {
        String downloadPath = System.getProperty("user.home") + "\\Downloads\\OrderInfo.txt";

        String textContent = """
                Order %s:
                
                
                
                Ticket          Price          Hour          Date
                """.formatted(orderDTO.getUuid().toString());
        for (Ticket ticket : orderDTO.getTickets()) {
            textContent = textContent.concat(ticket.getNume() + "            " + ticket.getPret() + "            " + ticket.getOra() + "             " + ticket.getData() + "\n");
        }

        textContent = textContent.concat("\n");
        textContent = textContent.concat("Date Placed: " + orderDTO.getDataComanda() + "\n");

        try {
            PrintWriter writer = new PrintWriter(downloadPath);

            writer.print(textContent);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Something went wrong while creating txt file!");
        }
    }
}
