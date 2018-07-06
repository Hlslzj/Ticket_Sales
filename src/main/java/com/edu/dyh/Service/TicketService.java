package com.edu.dyh.Service;

import com.edu.dyh.Bean.Ticket;

import java.util.Date;
import java.util.List;

public interface TicketService {

    /**
     * 根据始发站、到达站和出发日期查询可以预定的票
     * @param checkin
     * @param checkout
     * @param startTime
     * @param theNextDay
     * @return
     */
    List<Ticket> search(String checkin, String checkout, Date startTime, Date theNextDay);

    String sell();


}
