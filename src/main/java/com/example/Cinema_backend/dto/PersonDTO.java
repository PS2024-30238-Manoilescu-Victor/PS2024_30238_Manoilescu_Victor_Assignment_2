package com.example.Cinema_backend.dto;

import com.example.Cinema_backend.entity.FinalOrders;
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
    public class PersonDTO {

        private UUID uuid;
        private Long id;
        private String nume;
        private String prenume;
        private String parola;
        private String email;
        private String nrTelefon;
        private Boolean isAdmin;
        private List<Orders> orders;
        private List<FinalOrders> finalOrders;

}
