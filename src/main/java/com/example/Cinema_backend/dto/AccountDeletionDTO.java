package com.example.Cinema_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDeletionDTO {

    private UUID uuid;
    private String nume;
    private String prenume;
    private String email;

}

