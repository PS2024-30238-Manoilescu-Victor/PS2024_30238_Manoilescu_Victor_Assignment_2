package com.example.Cinema_backend.service;

import com.example.Cinema_backend.dto.TicketDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrdersServiceTest {

    @Autowired
    OrdersService ordersService;

    @Autowired
    public TicketService ticketService;

    @Test
    @Transactional
    void addTicket() throws Exception {

        TicketDTO ticketDTO = ticketService.findTicketById(2L);
        int initialNr = ticketDTO.getNrTickets();
        ordersService.addTicket(17L,2L,3);
        TicketDTO ticketDTO2 = ticketService.findTicketById(2L);
        assertEquals(initialNr - 3, ticketDTO2.getNrTickets());

    }

    @Test
    @Transactional
    void removeTicket() throws Exception {

        TicketDTO ticketDTO = ticketService.findTicketById(2L);
        int initialNr = ticketDTO.getNrTickets();
        ordersService.removeTicket(17L,2L,3);
        TicketDTO ticketDTO2 = ticketService.findTicketById(2L);
        assertEquals(initialNr + 3, ticketDTO2.getNrTickets());

    }
}