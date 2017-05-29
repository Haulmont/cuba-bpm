package com.company.workshop.web.mechanic;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.company.workshop.entity.Mechanic;
import com.haulmont.cuba.gui.components.ValidationErrors;

public class MechanicEdit extends AbstractEditor<Mechanic> {
    @Override
    protected void postValidate(ValidationErrors errors) {
        super.postValidate(errors);
    }
}