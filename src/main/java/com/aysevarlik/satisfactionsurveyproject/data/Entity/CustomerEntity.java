package com.aysevarlik.satisfactionsurveyproject.data.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor

@Entity
@Table(name = "customers")
public class CustomerEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerId", updatable = false, nullable = false)
    private Long customerId;

    @Column(name = "customerName")
    private String customerName;

    @Column(name = "customerSurname")
    private String customerSurname;

    @Column(name = "customerEmail")
    private String customerEmail;

    @Column(name = "customerMessage")
    private String customerMessage;



}
