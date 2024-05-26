package com.example.Cinema_backend.service;

import com.example.Cinema_backend.constants.ConstantsTicket;
import com.example.Cinema_backend.dto.SalesDTO;
import com.example.Cinema_backend.dto.TicketDTO;
import com.example.Cinema_backend.entity.Sales;
import com.example.Cinema_backend.entity.Ticket;
import com.example.Cinema_backend.mapper.SalesMapper;
import com.example.Cinema_backend.mapper.TicketMapper;
import com.example.Cinema_backend.repository.SalesRepository;
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
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {this.ticketRepository = ticketRepository;}

    public List<TicketDTO> findTickets() {
        List<Ticket> ticketList = ticketRepository.findAll();
        return ticketList.stream()
                .map(TicketMapper::fromTicket)
                .collect(Collectors.toList());
    }

    public List<SalesDTO> findSales() {
        List<Sales> salesList = salesRepository.findAll();
        return salesList.stream()
                .map(SalesMapper::fromSales)
                .collect(Collectors.toList());
    }


    public List<TicketDTO> findTicketsReduced() {
        List<Ticket> ticketList = ticketRepository.findAll();
        List<Ticket> newTicketList = new ArrayList<>();


        for (Ticket ticket : ticketList)
        {
            Optional<Sales> salesOptional = salesRepository.findById(ticket.getIdTick());
            if (salesOptional.isPresent()) {
                Ticket newTicket = ticket;
                int percent = salesOptional.get().getPercentReduced();
                newTicket.setPret(ticket.getPret() - (ticket.getPret() * percent / 100));
                newTicketList.add(newTicket);
            }
            else
            {
                newTicketList.add(ticket);
            }
        }

        return newTicketList.stream()
                .map(TicketMapper::fromTicket)
                .collect(Collectors.toList());
    }


    /**
     * Functie care returneaza un bilet cu un id dat
     * @param id id-ul biletului returnat
     * @return biletul cu id-ul dat
     * @throws Exception
     */
    public TicketDTO findTicketById(Long id) throws Exception {
        Optional<Ticket> ticketOptional = ticketRepository.findTicketByIdTick(id);
        if (!ticketOptional.isPresent()) {
            //log.error("Ticket with id {} was not found in db", id);
            throw new Exception(Ticket.class.getSimpleName() + " with id: " + id + "was not found.");
        }
        return TicketMapper.fromTicket(ticketOptional.get());
    }

    /**
     * Insereaza un nou bilet
     * @param ticketDTO biletul ce va fi inserat
     * @return id-ul noului bilet inserat
     */
    public UUID insert(TicketDTO ticketDTO) throws Exception{
        Ticket ticket = TicketMapper.toTicket(ticketDTO);
        if (TicketValidations.nonNegative(ticket.getNrTickets())) {
            if(TicketValidations.nonNegative(ticket.getPret())) {
                if (TicketValidations.validareData(ticket.getData())) {
                    if(TicketValidations.validareOra(ticket.getOra())) {
                        if(TicketValidations.validareRating(ticket.getRating())) {

                            ticket = ticketRepository.saveAndFlush(ticket);
                            return ticket.getUuid();
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

    public Long insertSale(SalesDTO salesDTO) throws Exception{
        Sales sales = SalesMapper.toSales(salesDTO);
        Optional<Ticket> ticketOptional = ticketRepository.findTicketByIdTick(sales.getTicketId());
        if (ticketOptional.isPresent()) {
            if (TicketValidations.validareProcent(sales.getPercentReduced())) {
                sales = salesRepository.save(sales);
                return sales.getTicketId();
            }
            else
            {
                throw new Exception(ConstantsTicket.wrongPercentage());
            }
        }
        else
        {
            throw new Exception(ConstantsTicket.nonexistentTicket(sales.getTicketId()));
        }
    }

    /**
     * Actualizeaza un bilet cu un id dat cu noi valori
     * @param ticketDTO noile valori puse in bilet
     * @param id id-ul biletului ce va fi actualizat
     * @return id-ul biletului actualizat
     * @throws Exception
     */
    public Long update(Long id, TicketDTO ticketDTO) throws Exception {
        //Ticket ticket = TicketMapper.toTicket(ticketDTO);
        Optional<Ticket> ticketOptional = ticketRepository.findTicketByIdTick(id);
        if (ticketOptional.isPresent()) {
            Ticket ticket = TicketMapper.toTicket(ticketDTO);//ticketOptional.get();

            if (TicketValidations.nonNegative(ticket.getNrTickets())) {
                if(TicketValidations.nonNegative(ticket.getPret())) {
                    if (TicketValidations.validareData(ticket.getData())) {
                        if(TicketValidations.validareOra(ticket.getOra())) {
                            if(TicketValidations.validareRating(ticket.getRating())) {
                                ticket.setUuid(ticketOptional.get().getUuid());
                                ticketRepository.saveAndFlush(ticket);
                                return id;
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
        else {
            throw new Exception(ConstantsTicket.nonexistentTicket(id));
        }
    }

    /**
     * Sterge un bilet cu id dat
     * @param id id-ul biletului ce va fi sters
     * @return id-ul biletului sters
     * @throws Exception
     */
    public Long delete(Long id) throws Exception {
        //Ticket ticket = TicketMapper.toTicket(ticketDTO);
        Optional<Ticket> ticketOptional = ticketRepository.findTicketByIdTick(id);
        if (ticketOptional.isPresent()) {
            ticketRepository.deleteById(ticketOptional.get().getUuid());
            return id;
        }
        else {
            throw new Exception(ConstantsTicket.nonexistentTicket(id));
        }
    }

    public Long deleteSale(Long id) throws Exception {
        //Ticket ticket = TicketMapper.toTicket(ticketDTO);
        Optional<Sales> salesOptional = salesRepository.findById(id);
        if (salesOptional.isPresent()) {
            salesRepository.deleteById(id);
            return id;
        }
        else {
            throw new Exception(ConstantsTicket.nonexistentSale(id));
        }
    }


    public Long incrementNr(Long id) throws Exception
    {
        Optional<Ticket> ticketOptional = ticketRepository.findTicketByIdTick(id);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();//ticketOptional.get();
            ticket.setNrTickets(ticket.getNrTickets() + 1);
            ticketRepository.saveAndFlush(ticket);
            return id;
        }
        else {
            throw new Exception(ConstantsTicket.nonexistentTicket(id));
        }
    }

    public Long decrementNr(Long id) throws Exception
    {
        Optional<Ticket> ticketOptional = ticketRepository.findTicketByIdTick(id);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();//ticketOptional.get();
            if(ticket.getNrTickets() > 0)
                ticket.setNrTickets(ticket.getNrTickets() - 1);
            else
                throw new Exception(ConstantsTicket.noMoreTickets());
            ticketRepository.saveAndFlush(ticket);
            return id;
        }
        else {
            throw new Exception(ConstantsTicket.nonexistentTicket(id));
        }
    }


}
