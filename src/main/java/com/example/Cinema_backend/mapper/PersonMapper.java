package com.example.Cinema_backend.mapper;

import com.example.Cinema_backend.dto.PersonDTO;
import com.example.Cinema_backend.dto.PersonDTO2;
import com.example.Cinema_backend.entity.Person;

public class PersonMapper {

    public static Person toPerson(PersonDTO personDTO)
    {

        return Person.builder()
                .uuid(personDTO.getUuid())
                .idPer(personDTO.getId())
                .email(personDTO.getEmail())
                .nume(personDTO.getNume())
                .prenume(personDTO.getPrenume())
                .parola(personDTO.getParola())
                .nrTelefon(personDTO.getNrTelefon())
                .isAdmin(personDTO.getIsAdmin())
                .orders(personDTO.getOrders())
                .finalOrders(personDTO.getFinalOrders())
                .build();

        /*Person aux = new Person();
        aux.setUuid(personDTO.getUuid());
        aux.setIdPer(personDTO.getId());
        aux.setEmail(personDTO.getEmail());
        aux.setNume(personDTO.getNume());
        aux.setPrenume(personDTO.getPrenume());
        aux.setParola(personDTO.getParola());
        aux.setNrTelefon(personDTO.getNrTelefon());
        aux.setIsAdmin(personDTO.getIsAdmin());
        aux.setOrders(personDTO.getOrders());
        aux.setFinalOrders(personDTO.getFinalOrders());
        return aux;*/
    }

    public static PersonDTO toPersonDTO(PersonDTO2 personDTO2)
    {
        return PersonDTO.builder()
                .uuid(personDTO2.getUuid())
                .id(personDTO2.getId())
                .email(personDTO2.getEmail1())
                .nume(personDTO2.getNume1())
                .prenume(personDTO2.getPrenume1())
                .parola(personDTO2.getParola1())
                .nrTelefon(personDTO2.getNrTelefon1())
                .isAdmin(personDTO2.getIsAdmin1())
                .build();
    }

    public static PersonDTO fromPerson(Person person)
    {
        return PersonDTO.builder()
                .uuid(person.getUuid())
                .id(person.getIdPer())
                .email(person.getEmail())
                .nume(person.getNume())
                .prenume(person.getPrenume())
                .parola(person.getParola())
                .nrTelefon(person.getNrTelefon())
                .isAdmin(person.getIsAdmin())
                .orders(person.getOrders())
                .finalOrders(person.getFinalOrders())
                .build();

        /*PersonDTO aux = new PersonDTO();
        aux.setUuid(person.getUuid());
        aux.setId(person.getIdPer());
        aux.setEmail(person.getEmail());
        aux.setNume(person.getNume());
        aux.setPrenume(person.getPrenume());
        aux.setParola(person.getParola());
        aux.setNrTelefon(person.getNrTelefon());
        aux.setIsAdmin(person.getIsAdmin());
        aux.setOrders(person.getOrders());
        aux.setFinalOrders(person.getFinalOrders());
        return aux;*/
    }

}

