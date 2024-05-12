package com.example.Cinema_backend.mapper;

import com.example.Cinema_backend.dto.FinalOrdersDTO;
import com.example.Cinema_backend.dto.OrdersDTO;
import com.example.Cinema_backend.entity.FinalOrders;
import com.example.Cinema_backend.entity.Orders;

public class FinalOrdersMapper {

    public static FinalOrders toFinalOrder(FinalOrdersDTO ordersDTO)
    {
        FinalOrders aux = new FinalOrders();
        aux.setUuid(ordersDTO.getUuid());
        aux.setIdOrd(ordersDTO.getId());
        aux.setDataComanda(ordersDTO.getDataComanda());
        aux.setPerson(ordersDTO.getPerson());
        aux.setTickets(ordersDTO.getTickets());
        return aux;
    }

    public static FinalOrdersDTO fromFinalOrder(FinalOrders orders)
    {
        FinalOrdersDTO aux = new FinalOrdersDTO();
        aux.setUuid(orders.getUuid());
        aux.setId(orders.getIdOrd());
        aux.setDataComanda(orders.getDataComanda());
        aux.setPerson(orders.getPerson());
        aux.setTickets(orders.getTickets());
        return aux;
    }

    public static FinalOrdersDTO toFinalOrderDTO(Orders orders)
    {
        FinalOrdersDTO aux = new FinalOrdersDTO();
        aux.setUuid(orders.getUuid());
        aux.setId(orders.getIdOrd());
        aux.setDataComanda(orders.getDataComanda());
        aux.setPerson(orders.getPerson());
        aux.setTickets(orders.getTickets());
        return aux;
    }

}
