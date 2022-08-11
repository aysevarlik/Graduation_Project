package com.aysevarlik.satisfactionsurveyproject.controller;

import com.aysevarlik.satisfactionsurveyproject.Business.Dto.CustomerDto;
import com.aysevarlik.satisfactionsurveyproject.Business.Services.ServicesImpl.ServicesImpl;
import com.aysevarlik.satisfactionsurveyproject.data.Entity.CustomerEntity;
import com.aysevarlik.satisfactionsurveyproject.data.Repository.ICustomerRepo;
import com.aysevarlik.satisfactionsurveyproject.excel.ExcelGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    ICustomerRepo repo;

    @Autowired
    ServicesImpl services;


    //http://localhost:8081/customer/export/excel
    @GetMapping("/customer/export/excel")
    public String exportIntoExcel(HttpServletResponse response, Model model) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=records_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<CustomerDto> list = services.getAllCustomers();
        ExcelGenerator generator = new ExcelGenerator(list);
        generator.generate(response);
        model.addAttribute("excel",response);
        return "questionnaire";
    }

    //http://localhost:8081/login
    @GetMapping("/login")
    public String getLogin(@RequestParam(name = "error", required = false) String error, Model model){
        if (error!=null){
            model.addAttribute("user","boş bırakılamaz");
        }else{
            model.addAttribute("user","yanlış girdiniz");
        }
        return "login";
    }


    //http://localhost:8081/customer/save
    @GetMapping("customer/save")
    public String getCustomer(Model model){
        model.addAttribute("customer",new CustomerDto());
        return "questionnaire";
    }

    //http://localhost:8081/customer/save
    @PostMapping("customer/save")
    public String postMessage(@Valid @ModelAttribute("customer") @RequestBody CustomerDto customerDto, BindingResult result){
        if (result.hasErrors()){
            return "questionnaire";
        }
        CustomerEntity entity = new CustomerEntity();
        entity.setCustomerName(customerDto.getCustomerName());
        entity.setCustomerSurname(customerDto.getCustomerSurname());
        entity.setCustomerEmail(customerDto.getCustomerEmail());
        entity.setCustomerMessage(customerDto.getCustomerMessage());
        repo.save(entity);
        return "redirect:/success";
    }
    //http://localhost:8081/success
    @GetMapping("/success")
    public String getSuccess(Model model){
        return "success";
    }

    //http://localhost:8081/logout
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/customer/save";
    }

    @GetMapping("/error")
    public String error(Model model) {
        model.addAttribute("message","OOPS!");
        return "error";
    }

}



