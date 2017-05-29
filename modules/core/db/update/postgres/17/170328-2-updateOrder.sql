alter table WORKSHOP_ORDER add column NUMBER_ integer ^
update WORKSHOP_ORDER set NUMBER_ = 0 where NUMBER_ is null ;
alter table WORKSHOP_ORDER alter column NUMBER_ set not null ;
