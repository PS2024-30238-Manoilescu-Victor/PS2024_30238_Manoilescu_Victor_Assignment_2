package com.example.Cinema_backend.service;


import com.example.Cinema_backend.constants.ConstantsTicket;
import com.example.Cinema_backend.dto.OrdersDTO;
import com.example.Cinema_backend.dto.TicketDTO;
import com.example.Cinema_backend.entity.Orders;
import com.example.Cinema_backend.entity.Ticket;
import com.example.Cinema_backend.mapper.OrdersMapper;
import com.example.Cinema_backend.mapper.TicketMapper;
import com.example.Cinema_backend.repository.OrdersRepositry;
import com.example.Cinema_backend.repository.TicketRepository;
import com.example.Cinema_backend.validations.TicketValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    @Autowired
    OrdersRepositry ordersRepositry;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketService ticketService;

    @Autowired
    public OrdersService(OrdersRepositry ordersRepositry) {
        this.ordersRepositry = ordersRepositry;
    }

    /**
     * Functie care pune toate comenzile intr-o lista
     * @return Lista de comenzi
     */
    public List<OrdersDTO> findOrders(){
        List<Orders> ordersList = ordersRepositry.findAll();
        return ordersList.stream()
                .map(OrdersMapper::fromOrder)
                .collect(Collectors.toList());
    }

    /**
     * Functie care returneaza o comanda cu un id dat
     * @param id id-ul comenzii returnate
     * @return comanda cu id-ul dat
     * @throws Exception
     */
    public OrdersDTO findOrderById(Long id) throws Exception
    {
        Optional<Orders> orderOptional = ordersRepositry.findOrdersByIdOrd(id);
        if (!orderOptional.isPresent()) {
            //log.error("Order with id {} was not found in db", id);
            throw new Exception(Orders.class.getSimpleName() + " with id: " + id + "was not found.");
        }
        return OrdersMapper.fromOrder(orderOptional.get());
    }

    /**
     * Insereaza o noua comanda
     * @param ordersDTO comanda ce va fi inserata
     * @return id-ul noii comenzi inserate
     */
    public Long insert(OrdersDTO ordersDTO)
    {
        Orders orders = OrdersMapper.toOrder(ordersDTO);
        orders = ordersRepositry.saveAndFlush(orders);
        return orders.getIdOrd();
    }

    /**
     * Insereaza o comanda la care adauga un tichet cu id-ul dat
     * @param ordersDTO comanda de baza ce va fi inserata
     * @param idTicket id-ul tichetului ce va fi adaugat
     * @return id-ul noii comenzi inserate
     * @throws Exception
     */
    public UUID insert2(OrdersDTO ordersDTO, Long idTicket) throws Exception
    {
        Orders orders = OrdersMapper.toOrder(ordersDTO);
        Optional<Ticket> ticketOptional = ticketRepository.findTicketByIdTick(idTicket);
        if (ticketOptional.isPresent()) {
                Ticket ticket = ticketOptional.get();
                List<Ticket> aux = orders.getTickets(); //List
                if (aux == null) {
                    aux = new ArrayList<Ticket>();
                }
                aux.add(ticket);
                orders.setTickets(aux);
                orders = ordersRepositry.saveAndFlush(orders);
                return orders.getUuid();
        }
        else
        {
            throw new Exception("The ticket with id \"" + idTicket + "\" doesn't exist!");
        }
    }

    /**
     * Actualizeaza o comanda cu un id dat cu noi valori
     * @param ordersDTO noile valori puse in comanda
     * @param id id-ul comenzii ce va fi actualizata
     * @return id-ul comenzii actualizate
     * @throws Exception
     */
    public Long update(Long id, OrdersDTO ordersDTO) throws Exception
    {
        Optional<Orders> ordersOptional = ordersRepositry.findOrdersByIdOrd(id);
        if (ordersOptional.isPresent()) {
            Orders orders = OrdersMapper.toOrder(ordersDTO);//personOptional.get();
            orders.setUuid(ordersOptional.get().getUuid());
            ordersRepositry.saveAndFlush(orders);
            return id;
        }
        else {
            throw new Exception("The order with id \"" + id + "\" doesn't exist!");
        }
    }

    /**
     * Sterge o comanda cu id dat
     * @param id id-ul comenzii ce va fi sterse
     * @return id-ul comenzii sterse
     * @throws Exception
     */
    public Long delete(Long id) throws Exception {
        Optional<Orders> orderOptional = ordersRepositry.findOrdersByIdOrd(id);
        if (orderOptional.isPresent()) {

            Orders orders = orderOptional.get();
            for(Ticket t : orders.getTickets())
            {
                ticketService.incrementNr(t.getIdTick());
            }
            ordersRepositry.deleteById(orderOptional.get().getUuid());
            return id;
        }
        else {
            throw new Exception("The order with id \"" + id + "\" doesn't exist!");
        }
    }

    /**
     * Adauga un bilet la comanda cu id-ul dat
     * @param idOrder id-ul comenzii in care se va adauga tichetul
     * @param idTicket id-ul biletului ce va fi adaugat la comanda
     * @return id-ul comenzii
     * @throws Exception
     */
    public Long addTicket(Long idOrder, Long idTicket, int nr ) throws Exception
    {
        Optional<Orders> ordersOptional = ordersRepositry.findOrdersByIdOrd(idOrder);
        if (ordersOptional.isPresent()) {
            Orders orders = ordersOptional.get();
            Optional<Ticket> ticketOptional = ticketRepository.findTicketByIdTick(idTicket);
            if (ticketOptional.isPresent()) {
                Ticket ticket = ticketOptional.get();
                /*List<Ticket> aux = orders.getTickets();
                aux.add(ticket);
                orders.setTickets(aux);*/
                for (int i = 0; i < nr; i++) {
                    orders.getTickets().add(ticket);
                    ticketService.decrementNr(idTicket);
                }
                ordersRepositry.saveAndFlush(orders);
                return idOrder;
            }
            else
            {
                throw new Exception("The ticket with id \"" + idTicket + "\" doesn't exist!");
            }

        }
        else {
            throw new Exception("The order with id \"" + idOrder + "\" doesn't exist!");

        }
        //return idOrder;
    }

    public UUID addTicket(UUID idOrder, Long idTicket, int nr ) throws Exception
    {
        Optional<Orders> ordersOptional = ordersRepositry.findById(idOrder);
        if (ordersOptional.isPresent()) {
            Orders orders = ordersOptional.get();
            Optional<Ticket> ticketOptional = ticketRepository.findTicketByIdTick(idTicket);
            if (ticketOptional.isPresent()) {
                Ticket ticket = ticketOptional.get();
                /*List<Ticket> aux = orders.getTickets();
                aux.add(ticket);
                orders.setTickets(aux);*/
                for (int i = 0; i < nr; i++) {
                    orders.getTickets().add(ticket);
                    ticketService.decrementNr(idTicket);
                }
                ordersRepositry.saveAndFlush(orders);
                return idOrder;
            }
            else
            {
                throw new Exception("The ticket with id \"" + idTicket + "\" doesn't exist!");
            }

        }
        else {
            throw new Exception("The order with id \"" + idOrder + "\" doesn't exist!");

        }
        //return idOrder;
    }

    public Long removeTicket(Long idOrder, Long idTicket, int nr) throws Exception
    {
        Optional<Orders> ordersOptional = ordersRepositry.findOrdersByIdOrd(idOrder);
        if (ordersOptional.isPresent()) {
            Orders orders = ordersOptional.get();
            Optional<Ticket> ticketOptional = ticketRepository.findTicketByIdTick(idTicket);
            if (ticketOptional.isPresent()) {
                Ticket ticket = ticketOptional.get();
                List<Ticket> aux = orders.getTickets();

                /*int contor = 0;
                int ok = nr;
                for(Ticket t : aux)
                {
                    if(t.getId() == idTicket)
                    {
                        aux.remove(contor);
                        ticketService.incrementNr(idTicket);
                        contor--;
                        ok--;
                        if(ok == 0)
                            break;
                    }
                    contor++;
                }*/

                for (int i=0; i< nr; i++) {
                    aux.remove(ticket);
                    ticketService.incrementNr(idTicket);
                }
                orders.setTickets(aux);
                ordersRepositry.saveAndFlush(orders);
                return idOrder;
            }
            else
            {
                throw new Exception("The ticket with id \"" + idTicket + "\" doesn't exist!");
            }

        }
        else {
            throw new Exception("The order with id \"" + idOrder + "\" doesn't exist!");

        }
    }


    public Long insert(TicketDTO ticketDTO) throws Exception{
        Ticket ticket = TicketMapper.toTicket(ticketDTO);
        if (TicketValidations.nonNegative(ticket.getNrTickets())) {
            if(TicketValidations.nonNegative(ticket.getPret())) {
                if (TicketValidations.validareData(ticket.getData())) {
                    if(TicketValidations.validareOra(ticket.getOra())) {
                        if(TicketValidations.validareRating(ticket.getRating())) {

                            ticket = ticketRepository.saveAndFlush(ticket);
                            return ticket.getIdTick();
                        }
                        else
                        {
                            throw new Exception(ConstantsTicket.wrongRating());
                        }
                    }
                    else
                    {
                        throw new Exception(ConstantsTicket.wrongOra());
                    }
                }
                else
                {
                    throw new Exception(ConstantsTicket.wrongData());
                }
            }
            else
            {
                throw new Exception(ConstantsTicket.negPrice());
            }

        }
        else
        {
            throw new Exception(ConstantsTicket.negNrTickets());
        }
    }

}

