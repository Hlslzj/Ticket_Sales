package com.edu.dyh.Controller;


import com.edu.dyh.Bean.Ticket;
import com.edu.dyh.Service.impl.TicketImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    TicketImpl ticketServiceImpl;

    @RequestMapping("/index")
    public String index() {
        return "ticketSearch";
    }

    @RequestMapping("/search")
    public String search(@RequestParam(required = true, defaultValue = "1") Integer page, HttpServletRequest request, Model model) {
//        String checkin = ticket.getCheckin();
//        String checkout = ticket.getCheckout();
//        startTime = ticket.getStartTime();
        String checkin = request.getParameter("checkin");
        String checkout = request.getParameter("checkout");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//SimpleDateFormat线程不安全，解决方法：将对象由共享变为局部私有
        try {
            Date startTime = sdf.parse(request.getParameter("startTime"));
            //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
            PageHelper.startPage(page, 10);
            List<Ticket> ticketList = ticketServiceImpl.search(checkin, checkout, startTime, theNextDay(startTime));
            PageInfo<Ticket> pageInfo = new PageInfo<>(ticketList);
            model.addAttribute("list", ticketList);
            model.addAttribute("pageInfo", pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ticketList";
    }

    private Date theNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
//        calendar.clear();//清除缓存
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
        return calendar.getTime();
    }


}
