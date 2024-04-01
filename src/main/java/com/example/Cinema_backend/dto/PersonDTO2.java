package com.example.Cinema_backend.dto;

import com.example.Cinema_backend.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter //
@Setter //
@AllArgsConstructor //
@NoArgsConstructor //
public class PersonDTO2 {

    private Long id;
    private String nume1;
    private String prenume1;
    private String parola1;
    private String email1;
    private String nrTelefon1;
    private Boolean isAdmin1;
}
