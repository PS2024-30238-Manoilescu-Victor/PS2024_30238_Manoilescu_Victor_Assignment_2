package com.example.Cinema_backend.repository;

import com.example.Cinema_backend.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long>{ //CrudRepository<Person,Long> {

    Person findPersonByNumeAndPrenume(String nume, String prenume);
    List<Person> findByNume(String nume);

    Optional<Person> findByEmailAndParola(String email, String parola);
    //Person getPersonByNumeAndPrenume(String nume, String prenume);

}
