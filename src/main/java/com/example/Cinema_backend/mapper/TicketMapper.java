package com.example.Cinema_backend.mapper;

import com.example.Cinema_backend.dto.TicketDTO;
import com.example.Cinema_backend.dto.TicketDTO2;
import com.example.Cinema_backend.entity.Ticket;

public class TicketMapper {

    public static Ticket toTicket(TicketDTO ticketDTO)
    {
        Ticket aux = new Ticket();
        aux.setUuid(ticketDTO.getUuid());
        aux.setIdTick(ticketDTO.getId());
        aux.setNume(ticketDTO.getNume());
        aux.setRating(ticketDTO.getRating());
        aux.setPret(ticketDTO.getPret());
        aux.setData(ticketDTO.getData());
        aux.setOra(ticketDTO.getOra());
        aux.setNrTickets(ticketDTO.getNrTickets());
        aux.setOrders(ticketDTO.getOrders());
        return aux;
    }

    public static TicketDTO fromTicket(Ticket ticket)
    {
        TicketDTO aux = new TicketDTO();
        aux.setUuid(ticket.getUuid());
        aux.setId(ticket.getIdTick());
        aux.setNume(ticket.getNume());
        aux.setRating(ticket.getRating());
        aux.setPret(ticket.getPret());
        aux.setData(ticket.getData());
        aux.setOra(ticket.getOra());
        aux.setNrTickets(ticket.getNrTickets());
        aux.setOrders(ticket.getOrders());
        return aux;
    }

    public static TicketDTO toTicketDTO(TicketDTO2 ticketDTO2)
    {
        TicketDTO aux = new TicketDTO();
        aux.setUuid(ticketDTO2.getUuid());
        aux.setId(ticketDTO2.getId());
        aux.setNume(ticketDTO2.getNume1());
        aux.setRating(ticketDTO2.getRating1());
        aux.setPret(ticketDTO2.getPret1());
        aux.setData(ticketDTO2.getData1());
        aux.setOra(ticketDTO2.getOra1());
        aux.setNrTickets(ticketDTO2.getNrTickets1());
        return aux;
    }

}
