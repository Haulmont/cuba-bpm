alter table WORKSHOP_SPECIAL_OFFER drop column TO_DATE cascade ;
alter table WORKSHOP_SPECIAL_OFFER add column TO_DATE date ^
update WORKSHOP_SPECIAL_OFFER set TO_DATE = current_date where TO_DATE is null ;
alter table WORKSHOP_SPECIAL_OFFER alter column TO_DATE set not null ;
