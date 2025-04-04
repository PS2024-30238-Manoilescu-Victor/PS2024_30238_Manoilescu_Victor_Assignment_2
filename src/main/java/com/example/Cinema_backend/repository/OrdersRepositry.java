package com.example.Cinema_backend.repository;

import com.example.Cinema_backend.entity.Orders;
import com.example.Cinema_backend.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrdersRepositry extends JpaRepository<Orders, UUID> {

    Optional<Orders> findOrdersByIdOrd(Long id);

}
