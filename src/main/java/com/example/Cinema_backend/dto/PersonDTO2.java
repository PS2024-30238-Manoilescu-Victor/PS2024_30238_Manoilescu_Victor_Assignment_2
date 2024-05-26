package com.example.Cinema_backend.dto;

import com.example.Cinema_backend.entity.Orders;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter //
@Setter //
@AllArgsConstructor //
@NoArgsConstructor //
@Builder
public class PersonDTO2 {

    private UUID uuid;
    private Long id;
    private String nume1;
    private String prenume1;
    private String parola1;
    private String email1;
    private String nrTelefon1;
    private Boolean isAdmin1;
}
