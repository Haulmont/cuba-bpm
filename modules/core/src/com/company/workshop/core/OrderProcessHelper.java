package com.company.workshop.core;

import com.company.workshop.entity.Order;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.UUID;

@Component("workshop_OrderProcessHelper")
public class OrderProcessHelper implements OrderProcessor {
    @Inject
    private Persistence persistence;

    @Override
    public void updateState(UUID entityId, String state) {
        EntityManager em = persistence.getEntityManager();

        Order order = em.find(Order.class, entityId);
        if (order != null) {
            order.setProcessState(state);
        }
    }
}