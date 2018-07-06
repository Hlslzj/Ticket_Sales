package com.edu.dyh.Controller;

import com.edu.dyh.Bean.Customer;
import com.edu.dyh.Bean.Order;
import com.edu.dyh.Bean.Ticket;
import com.edu.dyh.Service.impl.CustomerImpl;
import com.edu.dyh.Service.impl.TicketImpl;
import com.edu.dyh.Service.impl.OrderImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderImpl orderImpl;
    @Autowired
    private CustomerImpl customerImpl;

    @Autowired
    private TicketImpl ticketServiceImpl;



    @RequestMapping("/roll")
    @Transactional
    public synchronized String roll(HttpServletRequest request) {
        String orderId = request.getAttribute("orderId").toString();
        if (orderImpl.roll(orderId)){
            return "orderList";
        }
        return "fail";
    }



    @RequestMapping("/myOrder")
    public String MyOrder(@RequestParam(required = true, defaultValue = "1") Integer page, HttpServletRequest request, Model model) {
        String jwt = request.getHeader("Authorization");
        Customer customer = customerImpl.getCustomerByJWT(jwt);
        List<Order> orderList = orderImpl.findOrderByCustomer(customer);
        PageHelper.startPage(page, 10);
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        request.setAttribute("orderList", orderList);
        request.setAttribute("pageInfo", pageInfo);
        model.addAttribute("orderList", orderList);
        model.addAttribute("pageInfo", pageInfo);
        return "myOrder";
    }
    @RequestMapping("/new")
    @Transactional//数据库事务
    public synchronized String newOrder(HttpServletRequest request) throws ParseException {
        Customer customer = customerImpl.getCustomerByJWT(request.getHeader("Authorization "));
        String trainNumber = request.getParameter("trainNumber").toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = sdf.parse(request.getParameter("startTime").toString());
        //获得符合条件的票集合
        List<Ticket> ticketList = ticketServiceImpl.findByTrainNumberStartTime(trainNumber, startTime);
        int ticketNumber = Integer.parseInt(request.getParameter("ticketNumber").toString());

        if (!orderImpl.newOrder(ticketList, ticketNumber, customer)) {
            return "fail";
        }
        return "success";
    }


}
