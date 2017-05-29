package com.company.workshop.service;

import com.company.workshop.entity.SparePart;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service(SparePartService.NAME)
public class SparePartServiceBean implements SparePartService {
    @Inject
    private Persistence persistence;

    @Transactional
    @Override
    public List<SparePart> getUsedSpareParts() {
        EntityManager em = persistence.getEntityManager();

        TypedQuery<SparePart> query = em.createQuery(
                "select distinct p from workshop$Order o join o.parts p " +
                "where @between(p.createTs, now-7, now+1, day)",
                SparePart.class);
        query.setViewName(View.LOCAL);

        return query.getResultList();
    }
}