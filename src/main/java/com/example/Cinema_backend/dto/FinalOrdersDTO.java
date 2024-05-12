package com.example.Cinema_backend.dto;

import com.example.Cinema_backend.entity.Person;
import com.example.Cinema_backend.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter //
@Setter //
@AllArgsConstructor //
@NoArgsConstructor //
public class FinalOrdersDTO {

    private UUID uuid;
    private Long id;
    private String dataComanda;
    private Person person;
    private List<Ticket> tickets;

}
