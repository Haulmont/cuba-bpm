package com.company.workshop.service;

import com.company.workshop.entity.SparePart;

import java.util.List;

public interface SparePartService {
    String NAME = "workshop_SparePartService";

    List<SparePart> getUsedSpareParts();
}