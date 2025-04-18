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
public class TicketDTO {

    private UUID uuid;
    private Long id;
    private String nume;
    private Integer rating;
    private Float pret;
    private String data;
    private String ora;
    private Integer nrTickets;
    private List<Orders> orders;
    private String imagePath;


}
