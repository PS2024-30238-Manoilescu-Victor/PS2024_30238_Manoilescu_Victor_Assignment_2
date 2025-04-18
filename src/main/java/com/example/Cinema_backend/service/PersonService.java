package com.example.Cinema_backend.service;

import com.example.Cinema_backend.constants.ConstantsPerson;
import com.example.Cinema_backend.constants.ConstantsTicket;
import com.example.Cinema_backend.dto.FinalOrdersDTO;
import com.example.Cinema_backend.dto.OrdersDTO;
import com.example.Cinema_backend.dto.PersonDTO;
import com.example.Cinema_backend.entity.FinalOrders;
import com.example.Cinema_backend.entity.Orders;
import com.example.Cinema_backend.entity.Person;
import com.example.Cinema_backend.entity.Ticket;
import com.example.Cinema_backend.mapper.FinalOrdersMapper;
import com.example.Cinema_backend.mapper.OrdersMapper;
import com.example.Cinema_backend.mapper.PersonMapper;
import com.example.Cinema_backend.repository.FinalOrdersRepository;
import com.example.Cinema_backend.repository.OrdersRepositry;
import com.example.Cinema_backend.repository.PersonRepository;
import com.example.Cinema_backend.repository.TicketRepository;
import com.example.Cinema_backend.validations.PersonValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Date;

@Service
public class PersonService {


    //public static final Logger log = Logger.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository personRepository;

    @Autowired
    OrdersRepositry ordersRepositry;

    @Autowired
    FinalOrdersRepository finalOrdersRepository;

    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    OrdersService ordersService;
    @Autowired
    TicketService ticketService;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Functie care pune toate comenzile intr-o lista
     * @return Lista de comenzi
     */
    public List<PersonDTO> findPersons() {
        List<Person> personList = personRepository.findAll();
        return personList.stream()
                .map(PersonMapper::fromPerson)
                .collect(Collectors.toList());
    }

    public PersonDTO findPersonByEmailAndParola(String email, String parola) throws Exception
    {
        Optional<Person> personOptional = personRepository.findByEmailAndParola(email, parola);
        if (!personOptional.isPresent()) {
            //log.error("Person with id {} was not found in db", id);
            throw new Exception("Email password mismatch");
        }
        return PersonMapper.fromPerson(personOptional.get());
    }

    /**
     * Functie care returneaza o persoana cu un id dat
     * @param id id-ul persoanei returnate
     * @return persoana cu id-ul dat
     * @throws Exception
     */
    public PersonDTO findPersonById(Long id) throws Exception {
        Optional<Person> personOptional = personRepository.findPersonByIdPer(id);
        if (!personOptional.isPresent()) {
            //log.error("Person with id {} was not found in db", id);
            throw new Exception(Person.class.getSimpleName() + " with id: " + id);
        }
        return PersonMapper.fromPerson(personOptional.get());
    }

    /**
     * Insereaza o noua persoana
     * @param personDTO persoana ce va fi inserata
     * @return id-ul noii persoane inserate
     */
    public PersonDTO insert(PersonDTO personDTO) throws Exception{
        Person person = PersonMapper.toPerson(personDTO);
        //person = personRepository.saveAndFlush()(person);

        if(PersonValidations.validareEmail(person.getEmail())) {
            if (PersonValidations.validareParola(person.getParola()) == 0) {
                if (PersonValidations.validareNume(person.getNume())) {
                    if(PersonValidations.validareNume(person.getPrenume())) {
                        if (PersonValidations.validareNrTelefon((person.getNrTelefon()))) {
                            person = personRepository.saveAndFlush(person);
                            return PersonMapper.fromPerson(person);
                        }
                        else
                        {
                            throw new Exception(ConstantsPerson.wrongNrTelefon());
                        }
                    }
                    else
                    {
                        throw new Exception(ConstantsPerson.wrongSurname());
                    }
                }
                else
                {
                    throw new Exception(ConstantsPerson.wrongName());
                }
            }
            else
            {
                if (PersonValidations.validareParola(person.getParola()) == 1)
                    throw new Exception((ConstantsPerson.wrongPassLen()));
                else
                    throw new Exception((ConstantsPerson.wrongPassChar()));
            }
        }
        else
        {
            throw new Exception(ConstantsPerson.wrongEmail());
        }
    }

    /**
     * Actualizeaza o persoana cu un id dat cu noi valori
     * @param personDTO noile valori puse in persoana
     * @param id id-ul persoanei ce va fi actualizata
     * @return id-ul persoanei actualizate
     * @throws Exception
     */
    public Long update(Long id, PersonDTO personDTO) throws Exception {
        //Person person = PersonMapper.toPerson(personDTO);
        Optional<Person> personOptional = personRepository.findPersonByIdPer(id);
        if (personOptional.isPresent()) {
            Person person = PersonMapper.toPerson(personDTO);//personOptional.get();

            if(PersonValidations.validareEmail(person.getEmail())) {
                if (PersonValidations.validareParola(person.getParola()) == 0) {
                    if (PersonValidations.validareNume(person.getNume())) {
                        if(PersonValidations.validareNume(person.getPrenume())) {
                            if (PersonValidations.validareNrTelefon((person.getNrTelefon()))) {
                                person.setUuid(personOptional.get().getUuid());
                                personRepository.saveAndFlush(person);
                                return id;
                            }
                            else
                            {
                                throw new Exception(ConstantsPerson.wrongNrTelefon());
                            }
                        }
                        else
                        {
                            throw new Exception(ConstantsPerson.wrongSurname());
                        }
                    }
                    else
                    {
                        throw new Exception(ConstantsPerson.wrongName());
                    }
                }
                else
                {
                    if (PersonValidations.validareParola(person.getParola()) == 1)
                        throw new Exception((ConstantsPerson.wrongPassLen()));
                    else
                        throw new Exception((ConstantsPerson.wrongPassChar()));
                }
            }
            else
            {
                throw new Exception(ConstantsPerson.wrongEmail());
            }

        }
        else {
            throw new Exception(ConstantsPerson.nonexistentPerson(id));
        }
    }

    /**
     * Sterge o persoana cu id dat
     * @param id id-ul persoanei ce va fi sterse
     * @return id-ul persoanei sterse
     * @throws Exception
     */
    public PersonDTO delete(Long id) throws Exception {
        //Person person = PersonMapper.toPerson(personDTO);
        Optional<Person> personOptional = personRepository.findPersonByIdPer(id);
        if (personOptional.isPresent()) {
            personRepository.deleteById(personOptional.get().getUuid());
            return PersonMapper.fromPerson(personOptional.get());
        }
        else {
            throw new Exception(ConstantsPerson.nonexistentPerson(id));
        }
    }

    /**
     * Plaseaza o noua comanda ce contine un tichet
     * @param idPerson persoana care v-a detine comanda
     * @param idTicket id-ul tichetului continut de comanda
     * @return id-ul persoanei ce a plasat noua comanda
     * @throws Exception
     */
    public UUID createOrder(Long idPerson,Long idTicket, int nr) throws Exception {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(currentDate);
        Optional<Person> personOptional = personRepository.findPersonByIdPer(idPerson);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            Optional<Ticket> ticketOptional = ticketRepository.findTicketByIdTick(idTicket);
            if (ticketOptional.isPresent()) {
                //Ticket ticket = ticketOptional.get();
                OrdersDTO aux2 = new OrdersDTO();
                aux2.setDataComanda(currentDateTime);
                aux2.setPerson(person);
                UUID orderUUId = ordersService.insert2(aux2,idTicket);
                ticketService.decrementNr(idTicket);
                ordersService.addTicket(orderUUId,idTicket,nr-1);

                return orderUUId;
            }
            else {
                throw new Exception(ConstantsTicket.nonexistentTicket(idTicket));
            }

        } else {
            throw new Exception(ConstantsPerson.nonexistentPerson(idPerson));
        }

    }

    public FinalOrdersDTO finaliseOrder(Long idPerson, Long idOrder) throws Exception
    {
        Optional<Person> personOptional = personRepository.findPersonByIdPer(idPerson);
        if(!personOptional.isPresent()) {
            throw new Exception(ConstantsPerson.nonexistentPerson(idPerson));
        }
        Person person = personOptional.get();

        if(person.getOrders().size() == 0)
        {
            throw new Exception(ConstantsPerson.nonexistentCart());
        }

        Optional<Orders> ordersOptional = ordersRepositry.findOrdersByIdOrd(idOrder);
        if(!ordersOptional.isPresent()) {
            throw new Exception(ConstantsPerson.nonexistentOrder(idOrder));
        }

        Orders orders = ordersOptional.get();


        FinalOrdersDTO finalOrdersDTO = FinalOrdersMapper.toFinalOrderDTO(orders);
        FinalOrders finalOrders = FinalOrdersMapper.toFinalOrder(finalOrdersDTO);

        for (Ticket t : finalOrders.getTickets())
        {
            System.out.print(t.getNume());
        }

        finalOrders = finalOrdersRepository.saveAndFlush(finalOrders);
        return FinalOrdersMapper.fromFinalOrder(finalOrders);
    }


}
