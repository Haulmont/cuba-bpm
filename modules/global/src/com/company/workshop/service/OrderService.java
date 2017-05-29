package com.company.workshop.service;

import com.company.workshop.entity.Order;

import java.math.BigDecimal;

public interface OrderService {
    String NAME = "workshop_OrderService";

    BigDecimal calculateAmount(Order order);
}