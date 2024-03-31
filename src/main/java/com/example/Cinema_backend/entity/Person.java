package com.example.Cinema_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.List;

@Entity
@Getter //
@Setter //
@Builder
@AllArgsConstructor //
@NoArgsConstructor //
@Table(name = "person")

public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String nume;
    @Column
    private String prenume;
    @Column
    private String parola;
    @Column
    private String email;
    @Column
    //@OneToOne pt relatie one to one;
    //@OneToMany are lista/set; mapam cu numele tabelei
    //@ManyToOne ;mapam cu un user
    //@ManyToMany are set; fol cascade(facem o tabela de mijloc din care luam)
    private String nrTelefon;
    @Column
    private Boolean isAdmin;
    @OneToMany(mappedBy="person", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Orders> orders;

}
