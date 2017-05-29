package com.company.workshop.web.order;

import com.company.workshop.entity.Order;
import com.company.workshop.entity.OrderStatus;
import com.company.workshop.service.OrderService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.ValidationErrors;

import javax.inject.Inject;
import javax.inject.Named;

public class OrderEdit extends AbstractEditor<Order> {

    @Inject
    private OrderService orderService;

    @Named("fieldGroup.hoursSpent")
    private TextField hoursSpentField;

    @Override
    protected void initNewItem(Order item) {
        super.initNewItem(item);

        item.setStatus(OrderStatus.NEW);
    }

    @Override
    protected boolean preCommit() {
        Order order = getItem();
        order.setAmount(orderService.calculateAmount(order));

        return super.preCommit();
    }

    public void refreshAmount() {
        Order order = getItem();
        order.setAmount(orderService.calculateAmount(order));
    }

    @Override
    protected void postValidate(ValidationErrors errors) {
        if (getItem().getHoursSpent() != null && getItem().getHoursSpent() < 0) {
            errors.add(hoursSpentField, "'Hours Spent' must be greater or equal to 0");
        }
    }
}