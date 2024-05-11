package com.example.Cinema_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter //
@Setter //
@Builder
@AllArgsConstructor //
@NoArgsConstructor //
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID uuid;

    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false/*, updatable = false*/, columnDefinition="serial")
    private Long idTick;
    @Column
    private String nume;
    @Column
    private Integer rating;
    @Column
    private Float pret;
    @Column
    private String data;
    @Column
    private String ora;
    @Column
    private Integer nrTickets;
    @ManyToMany(mappedBy = "tickets", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Orders> orders;


}
