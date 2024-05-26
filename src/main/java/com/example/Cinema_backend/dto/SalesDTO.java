package com.example.Cinema_backend.dto;


import lombok.*;

@Getter //
@Setter //
@AllArgsConstructor //
@NoArgsConstructor //
@Builder
public class SalesDTO {

    private Long ticketId;
    private Integer percentReduced;

}
