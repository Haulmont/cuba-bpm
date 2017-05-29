package com.company.workshop.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "WORKSHOP_SPECIAL_OFFER")
@Entity(name = "workshop$SpecialOffer")
public class SpecialOffer extends StandardEntity {
    private static final long serialVersionUID = -8358262847422629780L;

    @Temporal(TemporalType.DATE)
    @Column(name = "FROM_DATE", nullable = false)
    protected Date fromDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "TO_DATE", nullable = false)
    protected Date toDate;

    @Column(name = "DISCOUNT", nullable = false)
    protected Integer discount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SERVICE_ID")
    protected Service service;

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }


    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }


}