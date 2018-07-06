package com.edu.dyh.Controller;

import com.edu.dyh.Bean.Customer;
import com.edu.dyh.Service.impl.CustomerImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

//import com.genequ.ticketmanagement.Service.MailService;

//这个注解导致返回字符串
//@RestController
//@ResponseBody
@Slf4j
@Controller
@RequestMapping("/customer")
public class CustomerController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerImpl customerService;


   // private CustomerMapper customerMapper;


//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Autowired
//    private MailService mailService;

    /**
     * 注册
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(HttpServletRequest request) {
//        CustomerImpl Service = new CustomerImpl();
        if (request.getParameter("passwordsignup").equals(request.getParameter("passwordsignup_confirm"))) {
            Customer customer = new Customer();
            customer.setId(UUID.randomUUID().toString());
            customer.setUserName(request.getParameter("usernamesignup"));
//            customer.setPassword(request.getParameter("passwordsignup"));
            //使用BCryptPasswordEncoder管理密码
            customer.setPassword(DigestUtils.md5DigestAsHex((request.getParameter("passwordsignup")).getBytes()));
            customer.setEmail(request.getParameter("emailsignup"));
            customer.setCellphone(request.getParameter("cellphonesignup"));

            try {
                if (customerService.register(customer)) {
//            confirm("注册成功")；
//            mailService.sendSimpleMail(customer.getEmail(), "恭喜您成功注册TicketManagement","恭喜您成功注册TicketManagement！您的用户名为：" + customer.getUserName());
                    return "success";
                } else {
                    return "fail";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "fail";
            }
        } else {
            //两次输入密码不一样
            return "fail";
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login() {
        return "login";
    }


    /**
     * 登录成功
     *
     * @return
     */
    @RequestMapping("/success")
    public String success() {
        return "success";
    }

    /**
     * 登录失败
     *
     * @return
     */
    @RequestMapping("/fail")
    public String fail() {
        return "fail";
    }

    /**
     * 我的信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/myProfile")
    public Customer myProfile(HttpServletRequest request, HttpServletResponse response) {
        Customer customer = customerService.getCustomerByJWT(request.getHeader("Authorization "));
        response.setHeader("username", customer.getUserName());
        response.setHeader("email", customer.getEmail());
        response.setHeader("cellphone", customer.getCellphone());
        return customer;
    }




}
