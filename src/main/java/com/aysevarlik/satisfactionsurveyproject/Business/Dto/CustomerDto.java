package com.aysevarlik.satisfactionsurveyproject.Business.Dto;


import com.aysevarlik.satisfactionsurveyproject.data.Entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CustomerDto extends BaseEntity{
    private Long customerId;

    @NotEmpty(message = "The name field is required!")
    private String customerName;

    @NotEmpty(message = "The surname field is required!")
    private String customerSurname;

    @NotEmpty(message = "The email field is required!")
    @Email(message = "Email must be written in the correct format")
    private String customerEmail;

    @NotEmpty(message = "The message field is required!")
    private String customerMessage;


    public CustomerDto(String customerName, String customerSurname, String customerEmail, String customerMessage){
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.customerEmail = customerEmail;
        this.customerMessage = customerMessage;
    }
}
