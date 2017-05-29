alter table WORKSHOP_ORDER_SERVICE_LINK add constraint FK_WOSL_ORDER foreign key (ORDER_ID) references WORKSHOP_ORDER(ID);
alter table WORKSHOP_ORDER_SERVICE_LINK add constraint FK_WOSL_SERVICE foreign key (SERVICE_ID) references WORKSHOP_SERVICE(ID);
