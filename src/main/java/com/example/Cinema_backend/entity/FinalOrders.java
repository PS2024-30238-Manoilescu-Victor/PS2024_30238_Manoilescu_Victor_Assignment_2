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
@Table(name = "finalorders")
public class FinalOrders {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID uuid;


    @Column(insertable = false/*, updatable = false*/, columnDefinition="serial")
    private Long idOrd;

    @Column
    private String dataComanda;

    @ManyToOne
    @JoinColumn(name = "person_uuid")
    @JsonIgnore
    private Person person;

    @ManyToMany
    //@JsonIgnore
    @JoinTable(
            name = "final_Ordered_Tickets",
            joinColumns = @JoinColumn(name = "order_uuid"),
            inverseJoinColumns = @JoinColumn(name = "ticket_uuid"))
    private List<Ticket> tickets;



}
