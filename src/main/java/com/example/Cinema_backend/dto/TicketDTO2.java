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
public class TicketDTO2 {

    private Long id;

    private String nume1;
    private Integer rating1;
    private Float pret1;
    private String data1;
    private String ora1;
    private Integer nrTickets1;


}
