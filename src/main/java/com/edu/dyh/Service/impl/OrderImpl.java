package com.edu.dyh.Service.impl;

import com.edu.dyh.Bean.Customer;
import com.edu.dyh.Bean.Order;
import com.edu.dyh.Bean.Ticket;
import com.edu.dyh.Mapper.OrderMapper;
import com.edu.dyh.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    TicketImpl ticketService;

    @Override
    public boolean newOrder(List<Ticket> ticketList, int ticketNumber, Customer customer) {
        if (ticketList != null || ticketList.size() < 1 || ticketNumber < 1 || ticketNumber > ticketList.size() || customer != null){
            return false;
        }
//选择数量->订票->跳转信息填写页面（评审是否需要）->订票成功 （数据库事务）
        for (int i = 0; i < ticketNumber; i++) {
            Ticket ticket = ticketList.get(i);
            Order order = new Order();
            order.setId(UUID.randomUUID().toString());
            order.setCustomer(customer);
            order.setTicket(ticket);
            ticket.setCustomerId(customer.getId());
            ticket.setOrdered(true);
            orderMapper.insert(order.getId(), customer.getId(), customer.getUserName(), ticket.getId(), ticket.getTrainNumber(), ticket.getCheckin(),
                    ticket.getCheckout(), ticket.getStartTime(), ticket.getEndTime(), ticket.getSeatType(), ticket.getSeatNumber(), ticket.getPrice());
            ticketService.setIsOrdered(order, "Y");
        }
        return true;
    }

    public List<Order> findOrderByCustomer(Customer customer) {
        return orderMapper.findOrderByCustomerId(customer.getId());
    }

    public Order findById(String orderId) {
        return orderMapper.findById(orderId);
    }

    public boolean roll(String orderId) {
        Order order = findById(orderId);
        if (order == null){
            return false;
        }
        String ticketId = order.getTicket().getId();
        Ticket ticket = ticketService.findById(ticketId);
        ticket.setOrdered(false);
        ticket.setCustomerId(null);
        orderMapper.deleteById(orderId);
        ticketService.setIsOrdered(order, "N");
        return true;
    }
}
