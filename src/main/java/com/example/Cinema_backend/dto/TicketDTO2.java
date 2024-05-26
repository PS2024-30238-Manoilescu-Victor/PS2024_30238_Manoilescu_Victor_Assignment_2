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
public class TicketDTO2 {

    private UUID uuid;
    private Long id;
    private String nume1;
    private Integer rating1;
    private Float pret1;
    private String data1;
    private String ora1;
    private Integer nrTickets1;
    private String imagePath;

}
