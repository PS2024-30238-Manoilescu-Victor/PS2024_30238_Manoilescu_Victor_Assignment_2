package com.example.Cinema_backend.service;

import com.example.Cinema_backend.constants.ConstantsTicket;
import com.example.Cinema_backend.dto.TicketDTO;
import com.example.Cinema_backend.entity.Ticket;
import com.example.Cinema_backend.mapper.TicketMapper;
import com.example.Cinema_backend.repository.TicketRepository;
import com.example.Cinema_backend.validations.TicketValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {this.ticketRepository = ticketRepository;}

    public List<TicketDTO> findTickets() {
        List<Ticket> ticketList = ticketRepository.findAll();
        return ticketList.stream()
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
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
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
    public Long insert(TicketDTO ticketDTO) throws Exception{
        Ticket ticket = TicketMapper.toTicket(ticketDTO);
        if (TicketValidations.nonNegative(ticket.getNrTickets())) {
            if(TicketValidations.nonNegative(ticket.getPret())) {
                if (TicketValidations.validareData(ticket.getData())) {
                    if(TicketValidations.validareOra(ticket.getOra())) {
                        if(TicketValidations.validareRating(ticket.getRating())) {

                            ticket = ticketRepository.save(ticket);
                            return ticket.getId();
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

    /**
     * Actualizeaza un bilet cu un id dat cu noi valori
     * @param ticketDTO noile valori puse in bilet
     * @param id id-ul biletului ce va fi actualizat
     * @return id-ul biletului actualizat
     * @throws Exception
     */
    public Long update(Long id, TicketDTO ticketDTO) throws Exception {
        //Ticket ticket = TicketMapper.toTicket(ticketDTO);
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isPresent()) {
            Ticket ticket = TicketMapper.toTicket(ticketDTO);//ticketOptional.get();

            if (TicketValidations.nonNegative(ticket.getNrTickets())) {
                if(TicketValidations.nonNegative(ticket.getPret())) {
                    if (TicketValidations.validareData(ticket.getData())) {
                        if(TicketValidations.validareOra(ticket.getOra())) {
                            if(TicketValidations.validareRating(ticket.getRating())) {
                                ticket.setId(id);
                                ticketRepository.save(ticket);
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
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isPresent()) {
            ticketRepository.deleteById(id);
            return id;
        }
        else {
            throw new Exception(ConstantsTicket.nonexistentTicket(id));
        }
    }


    public Long incrementNr(Long id) throws Exception
    {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();//ticketOptional.get();
            ticket.setNrTickets(ticket.getNrTickets() + 1);
            ticketRepository.save(ticket);
            return id;
        }
        else {
            throw new Exception(ConstantsTicket.nonexistentTicket(id));
        }
    }

    public Long decrementNr(Long id) throws Exception
    {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();//ticketOptional.get();
            if(ticket.getNrTickets() > 0)
                ticket.setNrTickets(ticket.getNrTickets() - 1);
            else
                throw new Exception(ConstantsTicket.noMoreTickets());
            ticketRepository.save(ticket);
            return id;
        }
        else {
            throw new Exception(ConstantsTicket.nonexistentTicket(id));
        }
    }


}
