package com.example.Cinema_backend.mapper;

import com.example.Cinema_backend.dto.FinalOrdersDTO;
import com.example.Cinema_backend.dto.OrdersDTO;
import com.example.Cinema_backend.entity.Orders;

public class OrdersMapper {

    public static Orders toOrder(OrdersDTO ordersDTO)
    {

        return Orders.builder()
                .uuid(ordersDTO.getUuid())
                .idOrd(ordersDTO.getId())
                .dataComanda(ordersDTO.getDataComanda())
                .person(ordersDTO.getPerson())
                .tickets(ordersDTO.getTickets())
                .build();
    }

    public static OrdersDTO fromOrder(Orders orders)
    {
        return OrdersDTO.builder()
                .uuid(orders.getUuid())
                .id(orders.getIdOrd())
                .dataComanda(orders.getDataComanda())
                .person(orders.getPerson())
                .tickets(orders.getTickets())
                .build();

        /*OrdersDTO aux = new OrdersDTO();
        aux.setUuid(orders.getUuid());
        aux.setId(orders.getIdOrd());
        aux.setDataComanda(orders.getDataComanda());
        aux.setPerson(orders.getPerson());
        aux.setTickets(orders.getTickets());
        return aux;*/
    }

    public static OrdersDTO fromFinalOrdersDTO(FinalOrdersDTO finalOrdersDTO)
    {

        return OrdersDTO.builder()
                .uuid(finalOrdersDTO.getUuid())
                .id(finalOrdersDTO.getId())
                .dataComanda(finalOrdersDTO.getDataComanda())
                .person(finalOrdersDTO.getPerson())
                .tickets(finalOrdersDTO.getTickets())
                .build();

            /*OrdersDTO aux = new OrdersDTO();
            aux.setUuid(finalOrdersDTO.getUuid());
            aux.setId(finalOrdersDTO.getId());
            aux.setDataComanda(finalOrdersDTO.getDataComanda());
            aux.setPerson(finalOrdersDTO.getPerson());
            aux.setTickets(finalOrdersDTO.getTickets());
            return aux;*/

    }

}
