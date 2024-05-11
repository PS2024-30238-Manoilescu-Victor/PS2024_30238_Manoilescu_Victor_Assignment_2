package com.example.Cinema_backend.controller;

import com.example.Cinema_backend.constants.ConstantsEmailSender;
import com.example.Cinema_backend.dto.AccountCreationDTO;
import com.example.Cinema_backend.dto.PersonDTO;
import com.example.Cinema_backend.dto.PersonDTO2;
import com.example.Cinema_backend.mapper.PersonMapper;
import com.example.Cinema_backend.config.RabbitMQSender;
import com.example.Cinema_backend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
//import

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.slf4j.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@RestController
@CrossOrigin
@RequestMapping(value = "/person")
//@RequestMapping()
public class PersonController {

    public static final Logger log = LoggerFactory.getLogger(PersonController.class.getName());

    //@Autowired
    PersonService personService;


    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Autowired
    RabbitMQSender rabbitMQSender;

    /**
     * Functie care selecteaza toate persoanele
     * @return O lista de comenzi
     */
    @GetMapping("/SelectAllUsers")
    public ModelAndView getPersons() {
        List<PersonDTO> dtos = personService.findPersons();
        ModelAndView modelAndView = new ModelAndView("SelectAllUsers");
        modelAndView.addObject("users",dtos);
        int nr = dtos.size();
        if(nr == 1)
            log.info(nr + " Person was found.");
        else
            log.info(nr + " People were found.");
        return modelAndView;
    }

    /**
     * cauta o persoana in functie de id-ul acesteia
     * @param id id-ul persoanei cautate
     * @return persoana cu id-ul selectat
     */
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        try {
        PersonDTO dtos = personService.findPersonById(id);
            log.info("User with id \"" + id + "\" was found!");
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }
        catch (Exception e) {
            log.info("User with id \"" + id + "\" was not found! "+ e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("LoggedinClient/{email}/{parola}")
    public ModelAndView getPersonByEmailAndPassword(@PathVariable String email, @PathVariable String parola) {
        try {
            PersonDTO dtos = personService.findPersonByEmailAndParola(email, parola);
            //log.info("User with email \"" + email + "\" succesfully logged in!");
            if (dtos.getIsAdmin()) {
                log.info("Wrong role");
                return new ModelAndView("redirect:/LoginClient");
            }
            else {
                log.info("User with email \"" + email + "\" succesfully logged in!");
                ModelAndView modelAndView = new ModelAndView("/Client");
                modelAndView.addObject("client", dtos);
                return modelAndView;
            }

        }
        catch (Exception e) {
            log.info("User with email \"" + email + "\" did not log in! "+ e.getMessage());
            return new ModelAndView("redirect:/LoginClient");
        }
    }

    @GetMapping("LoggedinAdmin/{email}/{parola}")
    public ModelAndView getAdminByEmailAndPassword(@PathVariable String email, @PathVariable String parola) {
        try {
            PersonDTO dtos = personService.findPersonByEmailAndParola(email, parola);
            //log.info("User with email \"" + email + "\" succesfully logged in!");
            if (!dtos.getIsAdmin()) {
                log.info("Wrong role");
                return new ModelAndView("redirect:/LoginAdmin");
            }
            else {
                log.info("Admin with email \"" + email + "\" succesfully logged in!");
                ModelAndView modelAndView = new ModelAndView("redirect:/Admin");
                modelAndView.addObject("admin", dtos);
                return modelAndView;
            }

        }
        catch (Exception e) {
            log.info("Admin with email \"" + email + "\" did not log in! "+ e.getMessage());
            return new ModelAndView("redirect:/LoginAdmin");
        }
    }

    /**
     * Insereaza o noua persoana
     * @param personDTO persoana ce va fi inserata
     * @return id-ul noii persoane inserate
     */
    @PostMapping(path = "/createUser", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = { MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView insertPerson(@Validated PersonDTO personDTO) {
        try {
            PersonDTO personAux = personService.insert(personDTO);
            log.info("Person with uuid \"" + personAux.getUuid() + "\" was inserted!");
        return new ModelAndView("redirect:/UserOper");
        }
        catch (Exception e) {
            log.info("Person was not inserted! " + e.getMessage());
            return new ModelAndView("redirect:/UserOper");
        }
    }

    @PostMapping(path = "/RegisterClient", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = { MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView registerUser(@Validated PersonDTO personDTO) {
        try {
            personDTO.setIsAdmin(false);
            PersonDTO personAux = personService.insert(personDTO);
            log.info("User with uuid \"" + personAux.getUuid() + "\" has registered!");

            AccountCreationDTO accountCreationDTO = new AccountCreationDTO(personAux.getUuid(),personAux.getNume(),personAux.getPrenume(),personAux.getEmail());

            try{
                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                headers.setBearerAuth(accountCreationDTO.getUuid().toString() + "_" + ConstantsEmailSender.TOKEN);

                HttpEntity<AccountCreationDTO> entity = new HttpEntity<>(accountCreationDTO,headers);

                RestTemplate restTemplate = new RestTemplate();
                String message = restTemplate.exchange(ConstantsEmailSender.AccountConfirmUrl,HttpMethod.POST,entity,String.class).getBody();

                log.info(message);

            }
            catch (RestClientException exception){
                if(!exception.getMessage().contains("400"))
                    rabbitMQSender.sendAccountCreation(accountCreationDTO);
                log.error(exception.getMessage());
            }

            return new ModelAndView("redirect:/LoginClient");
        }
        catch (Exception e) {
            log.info("User did not register succesfully! " + e.getMessage());
            return new ModelAndView("redirect:/RegisterClient");
        }
    }


    /**
     * Actualizeaza o persoana cu un id dat cu noi valori
     * @param personDTO2 noile valori puse pentru persoana
     * @param id id-ul persoanei ce va fi actualizata
     * @return id-ul persoanei actualizate
     */
    @PostMapping("/updateUser")
    public ModelAndView updatePerson(@Validated Long id, @Validated PersonDTO2 personDTO2)
    {
        try {
            PersonDTO personDTO = PersonMapper.toPersonDTO(personDTO2);
            Long personID = personService.update(id, personDTO);
            log.info("User with id \"" + personID + "\" was updated!");
            return new ModelAndView("redirect:/UserOper");
        }
        catch (Exception e) {
            log.info("User with id \"" + id + "\" was not updated! " + e.getMessage());
            return new ModelAndView("redirect:/UserOper");
        }
    }

    @PostMapping(path = "/ClientUpdate/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = { MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView ClientUpdate(@PathVariable Long id, @Validated PersonDTO personDTO)
    {
        try {
            personDTO.setIsAdmin(false);
            Long personID = personService.update(id, personDTO);
            log.info("User with id \"" + personID + "\" was updated!");
            return new ModelAndView("redirect:/LoginClient");
        }
        catch (Exception e) {
            log.info("User with id \"" + id + "\" was not updated! " + e.getMessage());
            return new ModelAndView("redirect:/LoginClient");
        }
    }

    /**
     * Plaseaza o noua comanda ce contine un tichet
     * @param idPerson persoana care v-a detine comanda
     * @param idTicket id-ul tichetului continut de comanda
     * @return id-ul persoanei ce a plasat noua comanda
     */
    //@PutMapping("createOrder/{idPerson}/{idTicket}/{nr}")
    @PostMapping(path ="createOrder/{idPerson}" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = { MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    //public ResponseEntity<Long> createOrder(@PathVariable Long idPerson, @PathVariable Long idTicket, @PathVariable int nr)
    public ModelAndView createOrder(@PathVariable Long idPerson, @Validated Long idTicket, @Validated int nr)
    {
        try {
            UUID orderID = personService.createOrder(idPerson, idTicket, nr);
            log.info("User with id \"" + idPerson + "\" created the order with uuid " + orderID);
            return new ModelAndView("redirect:/LoginClient");
        }
        catch (Exception e) {
            log.info("User with id \"" + idPerson + "\" didn't create an order " + e.getMessage());
            return new ModelAndView("redirect:/LoginClient");
        }
    }


    @PostMapping(path ="/createOrderAdmin" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = { MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView createOrderAdmin(@Validated Long idPerson, @Validated Long idTicket, @Validated int nr)
    {
        try {
            UUID orderID = personService.createOrder(idPerson, idTicket, nr);
            log.info("Admin with id \"" + idPerson + "\" created the order with uuid " + orderID);
            return new ModelAndView("redirect:/OrderOper");
        }
        catch (Exception e) {
            log.info("Admin with id \"" + idPerson + "\" didn't create an order " + e.getMessage());
            return new ModelAndView("redirect:/OrderOper");
        }
    }


    /**
     * Sterge o persoana cu id dat
     * @param id id-ul persoanei ce va fi sterse
     * @return id-ul persoanei sterse
     */
    //@DeleteMapping("/DeleteClient/{id}")
    @PostMapping("/DeleteClient/{id}")
    public ModelAndView deleteClient(@PathVariable Long id)
    {
        try {
            Long personID = personService.delete(id);
            log.info("User with id \"" + personID + "\" was deleted!");
            return new ModelAndView("redirect:/LoginClient");
        }
        catch (Exception e) {
            log.info("User with id \"" + id + "\" was not deleted! " + e.getMessage());
            return new ModelAndView("redirect:/LoginClient");
        }
    }

    @PostMapping("/DeleteUser/{id}")
    public ModelAndView deleteUser(@PathVariable Long id)
    {
        try {
            Long personID = personService.delete(id);
            log.info("User with id \"" + personID + "\" was deleted!");
            return new ModelAndView("redirect:/UserOper");
        }
        catch (Exception e) {
            log.info("User with id \"" + id + "\" was not deleted! " + e.getMessage());
            return new ModelAndView("redirect:/UserOper");
        }
    }




}

