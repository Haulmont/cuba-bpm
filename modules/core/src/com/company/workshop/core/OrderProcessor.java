package com.company.workshop.core;

import java.util.UUID;

public interface OrderProcessor {
    void updateState(UUID entityId, String state);
}