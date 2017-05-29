package com.company.workshop.web.main;

import com.company.workshop.entity.Order;
import com.haulmont.cuba.gui.components.AbstractMainWindow;
import com.haulmont.cuba.gui.components.Embedded;
import com.haulmont.cuba.gui.components.mainwindow.FtsField;
import com.haulmont.cuba.gui.components.mainwindow.SideMenu;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class ExtAppMainWindow extends AbstractMainWindow {
    @Inject
    private FtsField ftsField;

    @Inject
    private Embedded logoImage;

    @Inject
    private SideMenu sideMenu;

    @Inject
    private CollectionDatasource<Order, UUID> newOrdersDs;
    @Inject
    private CollectionDatasource<Order, UUID> readyOrdersDs;
    @Inject
    private CollectionDatasource<Order, UUID> inProgressOrdersDs;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        sideMenu.requestFocus();

        initLayoutAnalyzerContextMenu(logoImage);
        initLogoImage(logoImage);
        initFtsField(ftsField);
    }

    public void refreshData() {
        inProgressOrdersDs.refresh();
        newOrdersDs.refresh();
        readyOrdersDs.refresh();
    }
}