package com.example.Cinema_backend.mapper;

import com.example.Cinema_backend.dto.FinalOrdersDTO;
import com.example.Cinema_backend.dto.OrdersDTO;
import com.example.Cinema_backend.entity.Orders;

public class OrdersMapper {

    public static Orders toOrder(OrdersDTO ordersDTO)
    {
        Orders aux = new Orders();
        aux.setUuid(ordersDTO.getUuid());
        aux.setIdOrd(ordersDTO.getId());
        aux.setDataComanda(ordersDTO.getDataComanda());
        aux.setPerson(ordersDTO.getPerson());
        aux.setTickets(ordersDTO.getTickets());
        return aux;
    }

    public static OrdersDTO fromOrder(Orders orders)
    {
        OrdersDTO aux = new OrdersDTO();
        aux.setUuid(orders.getUuid());
        aux.setId(orders.getIdOrd());
        aux.setDataComanda(orders.getDataComanda());
        aux.setPerson(orders.getPerson());
        aux.setTickets(orders.getTickets());
        return aux;
    }

    public static OrdersDTO fromFinalOrdersDTO(FinalOrdersDTO finalOrdersDTO)
    {
        {
            OrdersDTO aux = new OrdersDTO();
            aux.setUuid(finalOrdersDTO.getUuid());
            aux.setId(finalOrdersDTO.getId());
            aux.setDataComanda(finalOrdersDTO.getDataComanda());
            aux.setPerson(finalOrdersDTO.getPerson());
            aux.setTickets(finalOrdersDTO.getTickets());
            return aux;
        }
    }

}
