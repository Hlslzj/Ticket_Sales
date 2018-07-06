package com.edu.dyh.Service;

import com.edu.dyh.Bean.Customer;
import com.edu.dyh.Bean.Ticket;

import java.util.List;

public interface OrderService {

    boolean newOrder(List<Ticket> ticketList, int ticketNumber, Customer customer);

    boolean roll(String orderId);
}
