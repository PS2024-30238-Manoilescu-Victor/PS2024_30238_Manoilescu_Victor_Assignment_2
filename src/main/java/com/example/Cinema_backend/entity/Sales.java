package com.example.Cinema_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter //
@Setter //
@Builder
@AllArgsConstructor //
@NoArgsConstructor //
@Table(name = "sales")
public class Sales {

    @Id
    @Column
    private Long ticketId;
    @Column
    private Integer percentReduced;

}
