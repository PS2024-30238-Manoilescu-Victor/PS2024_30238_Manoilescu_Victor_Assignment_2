package com.example.Cinema_backend.mapper;

import com.example.Cinema_backend.dto.PersonDTO;
import com.example.Cinema_backend.dto.PersonDTO2;
import com.example.Cinema_backend.entity.Person;

public class PersonMapper {

    public static Person toPerson(PersonDTO personDTO)
    {
        Person aux = new Person();
        aux.setId(personDTO.getId());
        aux.setEmail(personDTO.getEmail());
        aux.setNume(personDTO.getNume());
        aux.setPrenume(personDTO.getPrenume());
        aux.setParola(personDTO.getParola());
        aux.setNrTelefon(personDTO.getNrTelefon());
        aux.setIsAdmin(personDTO.getIsAdmin());
        aux.setOrders(personDTO.getOrders());
        return aux;
    }

    public static PersonDTO toPersonDTO(PersonDTO2 personDTO2)
    {
        PersonDTO aux = new PersonDTO();
        aux.setId(personDTO2.getId());
        aux.setEmail(personDTO2.getEmail1());
        aux.setNume(personDTO2.getNume1());
        aux.setPrenume(personDTO2.getPrenume1());
        aux.setParola(personDTO2.getParola1());
        aux.setNrTelefon(personDTO2.getNrTelefon1());
        aux.setIsAdmin(personDTO2.getIsAdmin1());
        return aux;
    }

    public static PersonDTO fromPerson(Person person)
    {
        PersonDTO aux = new PersonDTO();
        aux.setId(person.getId());
        aux.setEmail(person.getEmail());
        aux.setNume(person.getNume());
        aux.setPrenume(person.getPrenume());
        aux.setParola(person.getParola());
        aux.setNrTelefon(person.getNrTelefon());
        aux.setIsAdmin(person.getIsAdmin());
        aux.setOrders(person.getOrders());
        return aux;
    }

}

