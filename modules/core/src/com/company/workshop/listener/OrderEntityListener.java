package com.company.workshop.listener;

import com.haulmont.cuba.core.app.UniqueNumbersAPI;
import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.company.workshop.entity.Order;

import javax.inject.Inject;

@Component("workshop_OrderEntityListener")
public class OrderEntityListener implements BeforeInsertEntityListener<Order> {

    @Inject
    private UniqueNumbersAPI uniqueNumbersAPI;

    @Override
    public void onBeforeInsert(Order entity, EntityManager entityManager) {
        entity.setNumber(uniqueNumbersAPI.getNextNumber("ORDER_NUMBER"));
    }
}