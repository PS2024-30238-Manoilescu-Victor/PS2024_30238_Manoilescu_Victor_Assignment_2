package com.example.Cinema_backend.repository;

import com.example.Cinema_backend.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person,UUID>{ //CrudRepository<Person,Long> {


    Optional<Person> findPersonByIdPer(Long id);


    Optional<Person> findByEmailAndParola(String email, String parola);


}
