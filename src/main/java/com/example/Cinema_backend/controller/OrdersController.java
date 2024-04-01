package com.example.Cinema_backend.controller;

import java.util.List;

import com.example.Cinema_backend.dto.OrdersDTO;
import com.example.Cinema_backend.service.OrdersService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@CrossOrigin
@RequestMapping(value = "/orders")
public class OrdersController {

    public static final Logger log = LoggerFactory.getLogger(PersonController.class.getName());

    //@Autowired
    OrdersService ordersService;


    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    /**
     * Functie care selecteaza toate comenzile
     * @return O lista de comenzi
     */
    @GetMapping("/selectAllOrders")
    public ModelAndView getOrders() {
        List<OrdersDTO> dtos = ordersService.findOrders();
        int nr = dtos.size();
        log.info(nr + " Order(s) were found.");
        ModelAndView modelAndView = new ModelAndView("SelectAllOrders");
        modelAndView.addObject("orders",dtos);
        return modelAndView;
    }

    /**
     * cauta o comanda in functie de id-ul acesteia
     * @param id id-ul comenzii cautate
     * @return comanda cu id-ul selectat
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrdersDTO> getOrderById(@PathVariable Long id) {
        try {
            OrdersDTO dtos = ordersService.findOrderById(id);
            log.info("Order with id \"" + id + "\" was found!");
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }
        catch (Exception e) {
            log.info("Order with id \"" + id + "\" was not found! "+ e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Insereaza o noua comanda
     * @param ordersDTO comanda ce va fi inserata
     * @return id-ul noii comenzi inserate
     */
    @PostMapping()
    public ResponseEntity<Long> insertOrder(@Validated @RequestBody OrdersDTO ordersDTO) {
        try {
            Long orderID = ordersService.insert(ordersDTO);
            log.info("Order with id \"" + orderID + "\" was inserted!");
            return new ResponseEntity<>(orderID, HttpStatus.CREATED);
        }
        catch (Exception e) {
            log.info("Order was not inserted! " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    /**
     * Actualizeaza o comanda cu un id dat cu noi valori
     * @param ordersDTO noile valori puse in comanda
     * @param id id-ul comenzii ce va fi actualizata
     * @return id-ul comenzii actualizate
     */
    @PutMapping("/{id}")
    public ResponseEntity<Long> updateOrder(@Validated @RequestBody OrdersDTO ordersDTO, @PathVariable Long id)
    {
        try {
            Long orderID = ordersService.update(id, ordersDTO);
            log.info("Order with id \"" + orderID + "\" was updated!");
            return new ResponseEntity<>(orderID, HttpStatus.CREATED);
        }
        catch (Exception e) {
            log.info("Order with id \"" + id + "\" was not updated! " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    /**
     * Adauga un bilet la comanda cu id-ul dat
     * @param idOrder id-ul comenzii in care se va adauga tichetul
     * @param idTicket2 id-ul biletului ce va fi adaugat la comanda
     * @return id-ul comenzii
     */
    //@PutMapping("addTicket/{idOrder}/{idTicket}/{nr}")
    //@PostMapping("addTicket/{idOrder}")
    @PostMapping("addTicket")
    //public ResponseEntity<Long> addTicketToOrder(@PathVariable Long idOrder, @PathVariable Long idTicket, @PathVariable int nr)
    public ModelAndView addTicketToOrder(@Validated Long idOrder, @Validated Long idTicket2, @Validated int nr2)
    {
        try {
            Long orderID = ordersService.addTicket(idOrder, idTicket2, nr2);
            log.info("Ticket with id \"" + idTicket2 + "\" was added to order " + orderID + ".");
            return new ModelAndView("redirect:/LoginClient");
        }
        catch (Exception e) {
            log.info("Ticket with id \"" + idTicket2 + "\" was not added to order " + idOrder + ". " + e.getMessage());
            return new ModelAndView("redirect:/LoginClient");
        }
    }

    @PostMapping("addTicketAdmin")
    //public ResponseEntity<Long> addTicketToOrder(@PathVariable Long idOrder, @PathVariable Long idTicket, @PathVariable int nr)
    public ModelAndView addTicketToOrder2(@Validated Long idOrder, @Validated Long idTicket2, @Validated int nr2)
    {
        try {
            Long orderID = ordersService.addTicket(idOrder, idTicket2, nr2);
            log.info("Ticket with id \"" + idTicket2 + "\" was added to order " + orderID + ".");
            return new ModelAndView("redirect:/OrderOper");
        }
        catch (Exception e) {
            log.info("Ticket with id \"" + idTicket2 + "\" was not added to order " + idOrder + ". " + e.getMessage());
            return new ModelAndView("redirect:/OrderOper");
        }
    }


    //@PutMapping("removeTicket/{idOrder}/{idTicket}/{nr}")
    //@PostMapping("removeTicket/{idOrder2}")
    @PostMapping("removeTicket")
    //public ResponseEntity<Long> removeTicketFromOrder(@PathVariable Long idOrder, @PathVariable Long idTicket, @PathVariable int nr)
    public ModelAndView removeTicketFromOrder(@Validated Long idOrder, @Validated Long idTicket2, @Validated int nr2)
    {
        try {
            Long orderID = ordersService.removeTicket(idOrder, idTicket2, nr2);
            log.info("Ticket with id \"" + idTicket2 + "\" was removed from the order " + orderID + ".");
            return new ModelAndView("redirect:/LoginClient");
        }
        catch (Exception e) {
            log.info("Ticket with id \"" + idTicket2 + "\" was not removed from order " + idOrder + ". " + e.getMessage());
            return new ModelAndView("redirect:/LoginClient");
        }
    }

    @PostMapping("removeTicketAdmin")
    //public ResponseEntity<Long> removeTicketFromOrder(@PathVariable Long idOrder, @PathVariable Long idTicket, @PathVariable int nr)
    public ModelAndView removeTicketFromOrder2(@Validated Long idOrder, @Validated Long idTicket2, @Validated int nr2)
    {
        try {
            Long orderID = ordersService.removeTicket(idOrder, idTicket2, nr2);
            log.info("Ticket with id \"" + idTicket2 + "\" was removed from the order " + orderID + ".");
            return new ModelAndView("redirect:/OrderOper");
        }
        catch (Exception e) {
            log.info("Ticket with id \"" + idTicket2 + "\" was not removed from order " + idOrder + ". " + e.getMessage());
            return new ModelAndView("redirect:/OrderOper");
        }
    }

    /**
     * Sterge o comanda cu id dat
     * @param id id-ul comenzii ce va fi sterse
     * @return id-ul comenzii sterse
     */
    //@DeleteMapping("deleteTicket/{id}")
    @PostMapping("clientDeleteOrder/{id}")
    public ModelAndView clientDeleteOrder(@PathVariable Long id)
    {
        try {
            Long orderID = ordersService.delete(id);
            log.info("Order with id \"" + orderID + "\" was deleted!");
            return new ModelAndView("redirect:/LoginClient");
        }
        catch (Exception e) {
            log.info("Order with id \"" + id + "\" was not deleted! " + e.getMessage());
            return new ModelAndView("redirect:/LoginClient");
        }
    }

    @PostMapping("deleteOrder/{id}")
    public ModelAndView deleteOrder(@PathVariable Long id)
    {
        try {
            Long orderID = ordersService.delete(id);
            log.info("Order with id \"" + orderID + "\" was deleted!");
            return new ModelAndView("redirect:/OrderOper");
        }
        catch (Exception e) {
            log.info("Order with id \"" + id + "\" was not deleted! " + e.getMessage());
            return new ModelAndView("redirect:/OrderOper");
        }
    }



}
