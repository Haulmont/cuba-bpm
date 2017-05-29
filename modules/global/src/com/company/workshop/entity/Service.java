package com.company.workshop.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.StandardEntity;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Lob;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.Set;
import javax.persistence.OneToMany;

@NamePattern("%s|title")
@Table(name = "WORKSHOP_SERVICE")
@Entity(name = "workshop$Service")
public class Service extends StandardEntity {
    private static final long serialVersionUID = -4407763206353448748L;

    @Column(name = "TITLE", nullable = false, unique = true)
    protected String title;

    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;

    @Column(name = "PRICE", nullable = false)
    protected BigDecimal price;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "service")
    protected Set<SpecialOffer> specialOffers;

    public void setSpecialOffers(Set<SpecialOffer> specialOffers) {
        this.specialOffers = specialOffers;
    }

    public Set<SpecialOffer> getSpecialOffers() {
        return specialOffers;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }


}