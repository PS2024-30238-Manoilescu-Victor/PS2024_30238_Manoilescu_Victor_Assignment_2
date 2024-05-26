package com.example.Cinema_backend.service;

import com.example.Cinema_backend.dto.PersonDTO;
import com.example.Cinema_backend.dto.TicketDTO;
import com.example.Cinema_backend.repository.PersonRepository;
import com.example.Cinema_backend.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonServiceTest {

    @Autowired
    public PersonService personService;

    @Autowired
    public TicketService ticketService;

    @Autowired
    public OrdersService ordersService;
    @Test
    void findPersons() {

        List<PersonDTO> personDTOList = personService.findPersons();
        assertEquals(8,personDTOList.size());

    }

    @Test
    void findPersonByEmailAndParola() throws Exception {

        PersonDTO personDTOOptional = personService.findPersonByEmailAndParola("cristianfilipdud@gmail.com","123456");
        assertEquals("2985c19f-fbb3-47a5-9dfe-6e0c90877d37",personDTOOptional.getUuid().toString());

    }

    @Test
    @Transactional
    void createOrder() throws Exception {

        PersonDTO testPerson = personService.findPersonById(12L);
        TicketDTO testTicket = ticketService.findTicketById(2L);
        int nrInitialTichete = testTicket.getNrTickets();
        int nrInitialComenzi = testPerson.getOrders().size();
        UUID uuid = personService.createOrder(12L,2L,5);
        PersonDTO testPerson2 = personService.findPersonById(12L);
        TicketDTO testTicket2 = ticketService.findTicketById(2L);
        assertEquals(nrInitialTichete - 5,testTicket2.getNrTickets());

    }

}