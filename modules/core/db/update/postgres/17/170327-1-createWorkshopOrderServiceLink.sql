create table WORKSHOP_ORDER_SERVICE_LINK (
    ORDER_ID uuid,
    SERVICE_ID uuid,
    primary key (ORDER_ID, SERVICE_ID)
);
