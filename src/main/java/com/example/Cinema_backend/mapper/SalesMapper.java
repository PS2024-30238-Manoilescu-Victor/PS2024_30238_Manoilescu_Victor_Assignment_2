package com.example.Cinema_backend.mapper;

import com.example.Cinema_backend.dto.SalesDTO;
import com.example.Cinema_backend.entity.Sales;

public class SalesMapper {

    public static Sales toSales(SalesDTO salesDTO)
    {
        return Sales.builder()
                .ticketId(salesDTO.getTicketId())
                .percentReduced(salesDTO.getPercentReduced())
                .build();

        /*Sales aux = new Sales();
        aux.setTicketId(salesDTO.getTicketId());
        aux.setPercentReduced(salesDTO.getPercentReduced());
        return aux;*/
    }

    public static SalesDTO fromSales(Sales sales)
    {
        return SalesDTO.builder()
                .ticketId(sales.getTicketId())
                .percentReduced(sales.getPercentReduced())
                .build();

       /* SalesDTO aux = new SalesDTO();
        aux.setTicketId(sales.getTicketId());
        aux.setPercentReduced(sales.getPercentReduced());
        return aux;*/
    }

}
