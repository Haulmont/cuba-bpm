package com.company.workshop.web.screens;

import com.company.workshop.entity.SparePart;
import com.company.workshop.service.SparePartService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UsedParts extends AbstractWindow {
    @Inject
    private CollectionDatasource<SparePart, UUID> partsDs;

    @Inject
    private DataManager dataManager;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        LoadContext<SparePart> lc = new LoadContext<>(SparePart.class)
                .setQuery(new LoadContext.Query(
                        "select distinct p from workshop$Order o join o.parts p " +
                        "where @between(p.createTs, now - 7, now+1, day)"))
                .setView(View.LOCAL);
        List<SparePart> parts = dataManager.loadList(lc);

        for (SparePart part : parts) {
            partsDs.includeItem(part);
        }
    }
}