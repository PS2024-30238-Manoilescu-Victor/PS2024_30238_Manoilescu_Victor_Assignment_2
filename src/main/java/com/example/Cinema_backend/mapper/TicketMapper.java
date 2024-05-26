package com.example.Cinema_backend.mapper;

import com.example.Cinema_backend.dto.TicketDTO;
import com.example.Cinema_backend.dto.TicketDTO2;
import com.example.Cinema_backend.entity.Ticket;

public class TicketMapper {

    public static Ticket toTicket(TicketDTO ticketDTO)
    {
        return Ticket.builder()
                .uuid(ticketDTO.getUuid())
                .idTick(ticketDTO.getId())
                .nume(ticketDTO.getNume())
                .rating(ticketDTO.getRating())
                .pret(ticketDTO.getPret())
                .data(ticketDTO.getData())
                .ora(ticketDTO.getOra())
                .nrTickets(ticketDTO.getNrTickets())
                .orders(ticketDTO.getOrders())
                .imagePath(ticketDTO.getImagePath())
                .build();

        /*Ticket aux = new Ticket();
        aux.setUuid(ticketDTO.getUuid());
        aux.setIdTick(ticketDTO.getId());
        aux.setNume(ticketDTO.getNume());
        aux.setRating(ticketDTO.getRating());
        aux.setPret(ticketDTO.getPret());
        aux.setData(ticketDTO.getData());
        aux.setOra(ticketDTO.getOra());
        aux.setNrTickets(ticketDTO.getNrTickets());
        aux.setOrders(ticketDTO.getOrders());
        aux.setImagePath(ticketDTO.getImagePath());
        return aux;*/
    }

    public static TicketDTO fromTicket(Ticket ticket)
    {
        return TicketDTO.builder()
                .uuid(ticket.getUuid())
                .id(ticket.getIdTick())
                .nume(ticket.getNume())
                .rating(ticket.getRating())
                .pret(ticket.getPret())
                .data(ticket.getData())
                .ora(ticket.getOra())
                .nrTickets(ticket.getNrTickets())
                .orders(ticket.getOrders())
                .imagePath(ticket.getImagePath())
                .build();

        /*TicketDTO aux = new TicketDTO();
        aux.setUuid(ticket.getUuid());
        aux.setId(ticket.getIdTick());
        aux.setNume(ticket.getNume());
        aux.setRating(ticket.getRating());
        aux.setPret(ticket.getPret());
        aux.setData(ticket.getData());
        aux.setOra(ticket.getOra());
        aux.setNrTickets(ticket.getNrTickets());
        aux.setOrders(ticket.getOrders());
        aux.setImagePath(ticket.getImagePath());
        return aux;*/
    }

    public static TicketDTO toTicketDTO(TicketDTO2 ticketDTO2)
    {
        return TicketDTO.builder()
                .uuid(ticketDTO2.getUuid())
                .id(ticketDTO2.getId())
                .nume(ticketDTO2.getNume1())
                .rating(ticketDTO2.getRating1())
                .pret(ticketDTO2.getPret1())
                .data(ticketDTO2.getData1())
                .ora(ticketDTO2.getOra1())
                .nrTickets(ticketDTO2.getNrTickets1())
                .imagePath(ticketDTO2.getImagePath())
                .build();

        /*TicketDTO aux = new TicketDTO();
        aux.setUuid(ticketDTO2.getUuid());
        aux.setId(ticketDTO2.getId());
        aux.setNume(ticketDTO2.getNume1());
        aux.setRating(ticketDTO2.getRating1());
        aux.setPret(ticketDTO2.getPret1());
        aux.setData(ticketDTO2.getData1());
        aux.setOra(ticketDTO2.getOra1());
        aux.setNrTickets(ticketDTO2.getNrTickets1());
        aux.setImagePath(ticketDTO2.getImagePath());
        return aux;*/
    }

}
