package com.example.Cinema_backend.controller;

import java.util.List;
import java.util.UUID;

import com.example.Cinema_backend.dto.SalesDTO;
import com.example.Cinema_backend.dto.TicketDTO;
import com.example.Cinema_backend.dto.TicketDTO2;
import com.example.Cinema_backend.mapper.TicketMapper;
import com.example.Cinema_backend.service.TicketService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@CrossOrigin
@RequestMapping(value = "/ticket")
public class TicketController {


    public static final Logger log = LoggerFactory.getLogger(TicketController.class.getName());


    TicketService ticketService;


    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Functie care selecteaza toate tichetele
     * @return O lista de comenzi
     */
    @GetMapping("/selectAll")
    public ResponseEntity<List<TicketDTO>> getTickets() {
        List<TicketDTO> dtos = ticketService.findTickets();
        int nr = dtos.size();
        log.info(nr + " Ticket(s) were found.");
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


    @GetMapping("/clientSelectAll")
    public ModelAndView clientGetTickets() {
        List<TicketDTO> dtos = ticketService.findTicketsReduced();
        int nr = dtos.size();
        log.info(nr + " Ticket(s) were found.");
        ModelAndView modelAndView = new ModelAndView("SelectAllTickets");
        modelAndView.addObject("tickets",dtos);
        return modelAndView;
    }

    @GetMapping("/SelectAllTickets")
    public ModelAndView adminGetTickets() {
        List<TicketDTO> dtos = ticketService.findTickets();
        int nr = dtos.size();
        log.info(nr + " Ticket(s) were found.");
        ModelAndView modelAndView = new ModelAndView("SelectAllTicketsAdmin");
        modelAndView.addObject("tickets",dtos);
        return modelAndView;
    }

    @GetMapping("/SelectAllSales")
    public ModelAndView adminGetSales() {
        List<SalesDTO> dtos = ticketService.findSales();
        int nr = dtos.size();
        log.info(nr + " Sale(s) were found.");
        ModelAndView modelAndView = new ModelAndView("SelectAllSalesAdmin");
        modelAndView.addObject("sales",dtos);
        return modelAndView;
    }

    /**
     * cauta un tichet in functie de id-ul acestuia
     * @param id id-ul tichetului cautate
     * @return tichetul cu id-ul selectat
     */
    @GetMapping("getTicket/{id}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long id) {
        try {
            TicketDTO dtos = ticketService.findTicketById(id);
            log.info("Ticket with id \"" + id + "\" was found!");
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }
        catch (Exception e) {
            log.info("Ticket with id \"" + id + "\" was not found! "+ e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Insereaza un nou tichet
     * @param ticketDTO tichet ce va fi inserat
     * @return id-ul tichetului inserat
     */
    @PostMapping(path = "/createTicket", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = { MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView insertTicket(@Validated TicketDTO ticketDTO) {
        try {
            UUID ticketUUID = ticketService.insert(ticketDTO);
            log.info("Ticket with uuid \"" + ticketUUID + "\" was inserted!");
            return new ModelAndView("redirect:/TicketOper");
        }
        catch (Exception e) {
            log.info("Ticket was not inserted! " + e.getMessage());
            return new ModelAndView("redirect:/TicketOper");
        }
    }

    @PostMapping(path = "/createSale", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = { MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView createSale(@Validated SalesDTO salesDTO) {
        try {
            Long ticketID = ticketService.insertSale(salesDTO);
            log.info("Ticket with id \"" + ticketID + "\" received a new promotion!");
            return new ModelAndView("redirect:/SaleOper");
        }
        catch (Exception e) {
            log.info("Promotion not created! " + e.getMessage());
            return new ModelAndView("redirect:/SaleOper");
        }
    }


    /**
     * Actualizeaza un tichet cu un id dat cu noi valori
     * @param ticketDTO2 noile valori puse in tichet
     * @param id id-ul tichetul ce va fi actualizat
     * @return id-ul tichetului actualizat
     */
    @PostMapping(path = "/updateTicket", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = { MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView updateTicket(@Validated Long id, @Validated TicketDTO2 ticketDTO2)
    {
        try {
            TicketDTO ticketDTO = TicketMapper.toTicketDTO(ticketDTO2);
            Long ticketID = ticketService.update(id, ticketDTO);
            log.info("Ticket with id \"" + ticketID + "\" was updated!");
            return new ModelAndView("redirect:/TicketOper");
        }
        catch (Exception e) {
            log.info("Ticket with id \"" + id + "\" was not updated! " + e.getMessage());
            return new ModelAndView("redirect:/TicketOper");
        }
    }

    /**
     * Sterge un tichet cu id dat
     * @param id id-ul tichetului ce va fi sters
     * @return id-ul tichetului sters
     */
    @PostMapping("/deleteTicket/{id}")
    public ModelAndView deleteTicket(@PathVariable Long id)
    {
        try {
            Long ticketID = ticketService.delete(id);
            log.info("Ticket with id \"" + ticketID + "\" was deleted!");
            return new ModelAndView("redirect:/TicketOper");
        }
        catch (Exception e) {
            log.info("Ticket with id \"" + id + "\" was not deleted! " + e.getMessage());
            return new ModelAndView("redirect:/TicketOper");
        }
    }

    @PostMapping("/seePoster/{id}")
    public ModelAndView seePoster(@PathVariable Long id)
    {
        try {
            TicketDTO ticket = ticketService.findTicketById(id);
            log.info("Poster for ticket with id \"" + ticket.getId() + "\" was accessed!");
            ModelAndView modelAndView = new ModelAndView("Poster");
            modelAndView.addObject("ticket",ticket);
            return modelAndView;
        }
        catch (Exception e) {
            log.info("Poster for ticket with id \"" + id + "\" was not accessed! " + e.getMessage());
            return new ModelAndView("redirect:/TicketOper");
        }
    }

    @PostMapping("/deleteSale/{id}")
    public ModelAndView deleteSale(@PathVariable Long id)
    {
        try {
            Long ticketID = ticketService.deleteSale(id);
            log.info("Ticket with id \"" + ticketID + "\" had his promotion removed!");
            return new ModelAndView("redirect:/SaleOper");
        }
        catch (Exception e) {
            log.info("Ticket with id \"" + id + "\" did not have his promotion removed! " + e.getMessage());
            return new ModelAndView("redirect:/SaleOper");
        }
    }

}
