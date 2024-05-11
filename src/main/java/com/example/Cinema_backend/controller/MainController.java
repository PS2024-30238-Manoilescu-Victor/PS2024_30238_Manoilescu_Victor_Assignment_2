package com.example.Cinema_backend.controller;

import com.example.Cinema_backend.dto.PersonDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MainController {

    @GetMapping("/")
    public ModelAndView principal()
    {
        return new ModelAndView("PaginaPrincipala");
    }

    @GetMapping("/LoginClient")
    public ModelAndView LoginClient()
    {
        return new ModelAndView("LoginClient");
    }

    @GetMapping("/LoginAdmin")
    public ModelAndView LoginAdmin()
    {
        return new ModelAndView("LoginAdmin");
    }

    @GetMapping("/RegisterClient")
    public ModelAndView RegisterClient()
    {
        return new ModelAndView("RegisterClient");
    }

    @GetMapping("/Client")
    public ModelAndView Client(@RequestParam (defaultValue = "Client") PersonDTO data)
    {
        //ModelAndView modelAndView = new ModelAndView("Client");
        //modelAndView.addObject("data", data);
        return new ModelAndView("Client");
    }


    @GetMapping("/Admin")
    public ModelAndView Admin()
    {
        return new ModelAndView("Admin");
    }
    @GetMapping("/UserOper")
    public ModelAndView UserOper()
    {
        return new ModelAndView("UserOper");
    }

    @GetMapping("/OrderOper")
    public ModelAndView OrderOper()
    {
        return new ModelAndView("OrderOper");
    }

    @GetMapping("/TicketOper")
    public ModelAndView TicketOper()
    {
        return new ModelAndView("TicketOper");
    }

    @GetMapping("/SaleOper")
    public ModelAndView SaleOper()
    {
        return new ModelAndView("SaleOper");
    }

}
