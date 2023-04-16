/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     14.4.2023. 21:33:33                          */
/*==============================================================*/


drop table if exists BILL;

drop table if exists BRANCH;

drop table if exists CALENDAR;

drop table if exists CARBODY_TYPE;

drop table if exists CAR_BRAND;

drop table if exists CATEGORY;

drop table if exists CLIENT;

drop table if exists DISCOUNT;

drop table if exists DOOR_TYPE;

drop table if exists EMISSION_TYPE;

drop table if exists FUEL_TYPE;

drop table if exists INSURANCE;

drop table if exists LICENCE;

drop table if exists LOCATION;

drop table if exists MODEL;

drop table if exists PENALTY;

drop table if exists PRICELIST;

drop table if exists RELAPS;

drop table if exists RENT;

drop table if exists SALARY;

drop table if exists STAFF;

drop table if exists TRANSMISSION_TYPE;

drop table if exists VEHICLE;

/*==============================================================*/
/* Table: BILL                                                  */
/*==============================================================*/
create table BILL
(
   TYPE_ID              int not null,
   CARBODY_ID           int not null,
   DOOR_ID              int not null,
   TRANSMISSION_ID      int not null,
   EMISSION_ID          int not null,
   CATEGORY_ID          int not null,
   BRAND_ID             int not null,
   MODEL_ID             int not null,
   VEH_SHOP_ID          int not null,
   CLIENT_ID            int not null,
   CALENDAR_ID          int not null,
   CHASSIS_NUMBER       varchar(17) not null,
   PRICELIST_ID         int not null,
   RENT_ID              int not null,
   BILL_ID              int not null,
   PENALTY_ID           int not null,
   DISCOUNT_ID          int not null,
   INSURANCE_ID         int not null,
   FINAL_PRICE          int not null,
   primary key (TYPE_ID, CARBODY_ID, DOOR_ID, TRANSMISSION_ID, EMISSION_ID, CATEGORY_ID, BRAND_ID, MODEL_ID, VEH_SHOP_ID, CLIENT_ID, CALENDAR_ID, CHASSIS_NUMBER, PRICELIST_ID, RENT_ID, BILL_ID)
);

/*==============================================================*/
/* Table: BRANCH                                                */
/*==============================================================*/
create table BRANCH
(
   SHOP_ID              int not null,
   LOCATION_ID          int not null,
   NAME                 varchar(32) not null,
   primary key (SHOP_ID)
);

/*==============================================================*/
/* Table: CALENDAR                                              */
/*==============================================================*/
create table CALENDAR
(
   CALENDAR_ID          int not null,
   YEAR                 numeric(4,0) not null,
   MONTH                numeric(2,0),
   primary key (CALENDAR_ID)
);

/*==============================================================*/
/* Table: CARBODY_TYPE                                          */
/*==============================================================*/
create table CARBODY_TYPE
(
   CARBODY_ID           int not null,
   NAME                 varchar(32) not null,
   primary key (CARBODY_ID)
);

/*==============================================================*/
/* Table: CAR_BRAND                                             */
/*==============================================================*/
create table CAR_BRAND
(
   BRAND_ID             int not null,
   NAME                 varchar(32) not null,
   primary key (BRAND_ID)
);

/*==============================================================*/
/* Table: CATEGORY                                              */
/*==============================================================*/
create table CATEGORY
(
   TYPE_ID              int not null,
   CARBODY_ID           int not null,
   DOOR_ID              int not null,
   TRANSMISSION_ID      int not null,
   EMISSION_ID          int not null,
   CATEGORY_ID          int not null,
   primary key (TYPE_ID, CARBODY_ID, DOOR_ID, TRANSMISSION_ID, EMISSION_ID, CATEGORY_ID)
);

/*==============================================================*/
/* Table: CLIENT                                                */
/*==============================================================*/
create table CLIENT
(
   CLIENT_ID            int not null,
   NAME                 varchar(32) not null,
   SURNAME              varchar(32) not null,
   primary key (CLIENT_ID)
);

/*==============================================================*/
/* Table: DISCOUNT                                              */
/*==============================================================*/
create table DISCOUNT
(
   DISCOUNT_ID          int not null,
   DISCOUNT_TYPE        varchar(20) not null,
   primary key (DISCOUNT_ID)
);

/*==============================================================*/
/* Table: DOOR_TYPE                                             */
/*==============================================================*/
create table DOOR_TYPE
(
   DOOR_ID              int not null,
   NUMBER               int not null,
   primary key (DOOR_ID)
);

/*==============================================================*/
/* Table: EMISSION_TYPE                                         */
/*==============================================================*/
create table EMISSION_TYPE
(
   EMISSION_ID          int not null,
   EURO_NUM             varchar(7) not null,
   primary key (EMISSION_ID)
);

/*==============================================================*/
/* Table: FUEL_TYPE                                             */
/*==============================================================*/
create table FUEL_TYPE
(
   TYPE_ID              int not null,
   NAME                 varchar(32) not null,
   primary key (TYPE_ID)
);

/*==============================================================*/
/* Table: INSURANCE                                             */
/*==============================================================*/
create table INSURANCE
(
   INSURANCE_ID         int not null,
   INSURANCE_TYPE       varchar(32) not null,
   primary key (INSURANCE_ID)
);

/*==============================================================*/
/* Table: LICENCE                                               */
/*==============================================================*/
create table LICENCE
(
   LICENCE_ID           int not null,
   CLIENT_ID            int not null,
   LICENCE_TYPE         varchar(10) not null,
   primary key (LICENCE_ID)
);

/*==============================================================*/
/* Table: LOCATION                                              */
/*==============================================================*/
create table LOCATION
(
   LOCATION_ID          int not null,
   COUNTRY              varchar(32) not null,
   CITY                 varchar(32) not null,
   primary key (LOCATION_ID)
);

/*==============================================================*/
/* Table: MODEL                                                 */
/*==============================================================*/
create table MODEL
(
   BRAND_ID             int not null,
   MODEL_ID             int not null,
   NAME                 varchar(32) not null,
   YEAR                 numeric(4,0) not null,
   primary key (BRAND_ID, MODEL_ID)
);

/*==============================================================*/
/* Table: PENALTY                                               */
/*==============================================================*/
create table PENALTY
(
   PENALTY_ID           int not null,
   PENALTY_TYPE         varchar(20) not null,
   primary key (PENALTY_ID)
);

/*==============================================================*/
/* Table: PRICELIST                                             */
/*==============================================================*/
create table PRICELIST
(
   CALENDAR_ID          int not null,
   TYPE_ID              int not null,
   CARBODY_ID           int not null,
   DOOR_ID              int not null,
   TRANSMISSION_ID      int not null,
   EMISSION_ID          int not null,
   CATEGORY_ID          int not null,
   BRAND_ID             int not null,
   MODEL_ID             int not null,
   CHASSIS_NUMBER       varchar(17) not null,
   PRICELIST_ID         int not null,
   SHOP_ID              int not null,
   FINAL_PRICE          int not null,
   primary key (TYPE_ID, CARBODY_ID, DOOR_ID, TRANSMISSION_ID, EMISSION_ID, CATEGORY_ID, BRAND_ID, MODEL_ID, CALENDAR_ID, CHASSIS_NUMBER, PRICELIST_ID)
);

/*==============================================================*/
/* Table: RELAPS                                                */
/*==============================================================*/
create table RELAPS
(
   RELAPSE_ID           int not null,
   TYPE_ID              int not null,
   CARBODY_ID           int not null,
   DOOR_ID              int not null,
   TRANSMISSION_ID      int not null,
   EMISSION_ID          int not null,
   CATEGORY_ID          int not null,
   BRAND_ID             int not null,
   MODEL_ID             int not null,
   VEH_SHOP_ID          int not null,
   CLIENT_ID            int not null,
   CALENDAR_ID          int not null,
   CHASSIS_NUMBER       varchar(17) not null,
   PRICELIST_ID         int not null,
   RENT_ID              int not null,
   SHOP_ID              int not null,
   primary key (RELAPSE_ID)
);

/*==============================================================*/
/* Table: RENT                                                  */
/*==============================================================*/
create table RENT
(
   CLIENT_ID            int not null,
   TYPE_ID              int not null,
   CARBODY_ID           int not null,
   DOOR_ID              int not null,
   TRANSMISSION_ID      int not null,
   EMISSION_ID          int not null,
   CATEGORY_ID          int not null,
   BRAND_ID             int not null,
   MODEL_ID             int not null,
   VEH_SHOP_ID          int not null,
   CALENDAR_ID          int not null,
   CHASSIS_NUMBER       varchar(17) not null,
   PRICELIST_ID         int not null,
   RENT_ID              int not null,
   SHOP_ID              int not null,
   primary key (TYPE_ID, CARBODY_ID, DOOR_ID, TRANSMISSION_ID, EMISSION_ID, CATEGORY_ID, BRAND_ID, MODEL_ID, VEH_SHOP_ID, CLIENT_ID, CALENDAR_ID, CHASSIS_NUMBER, PRICELIST_ID, RENT_ID)
);

/*==============================================================*/
/* Table: SALARY                                                */
/*==============================================================*/
create table SALARY
(
   SALARY_ID            int not null,
   CALENDAR_ID          int not null,
   STAFF_ID             int not null,
   AMOUNT               int not null,
   BONUSES              decimal(3),
   primary key (SALARY_ID)
);

/*==============================================================*/
/* Table: STAFF                                                 */
/*==============================================================*/
create table STAFF
(
   STAFF_ID             int not null,
   STA_STAFF_ID         int,
   SHOP_ID              int not null,
   NAME                 varchar(32) not null,
   SURNAME              varchar(32) not null,
   primary key (STAFF_ID)
);

/*==============================================================*/
/* Table: TRANSMISSION_TYPE                                     */
/*==============================================================*/
create table TRANSMISSION_TYPE
(
   TRANSMISSION_ID      int not null,
   NAME                 varchar(32) not null,
   GEAR_NUMBER          int not null,
   primary key (TRANSMISSION_ID)
);

/*==============================================================*/
/* Table: VEHICLE                                               */
/*==============================================================*/
create table VEHICLE
(
   SHOP_ID              int not null,
   CHASSIS_NUMBER       varchar(17) not null,
   TYPE_ID              int not null,
   CARBODY_ID           int not null,
   DOOR_ID              int not null,
   TRANSMISSION_ID      int not null,
   EMISSION_ID          int not null,
   CATEGORY_ID          int not null,
   BRAND_ID             int not null,
   MODEL_ID             int not null,
   primary key (TYPE_ID, CARBODY_ID, DOOR_ID, TRANSMISSION_ID, EMISSION_ID, CATEGORY_ID, BRAND_ID, MODEL_ID, SHOP_ID, CHASSIS_NUMBER)
);

alter table BILL add constraint FK_RELATIONSHIP_10 foreign key (TYPE_ID, CARBODY_ID, DOOR_ID, TRANSMISSION_ID, EMISSION_ID, CATEGORY_ID, BRAND_ID, MODEL_ID, VEH_SHOP_ID, CLIENT_ID, CALENDAR_ID, CHASSIS_NUMBER, PRICELIST_ID, RENT_ID)
      references RENT (TYPE_ID, CARBODY_ID, DOOR_ID, TRANSMISSION_ID, EMISSION_ID, CATEGORY_ID, BRAND_ID, MODEL_ID, VEH_SHOP_ID, CLIENT_ID, CALENDAR_ID, CHASSIS_NUMBER, PRICELIST_ID, RENT_ID) on delete restrict on update cascade;

alter table BILL add constraint FK_RELATIONSHIP_18 foreign key (DISCOUNT_ID)
      references DISCOUNT (DISCOUNT_ID) on delete restrict on update cascade;

alter table BILL add constraint FK_RELATIONSHIP_19 foreign key (PENALTY_ID)
      references PENALTY (PENALTY_ID) on delete restrict on update cascade;

alter table BILL add constraint FK_RELATIONSHIP_28 foreign key (INSURANCE_ID)
      references INSURANCE (INSURANCE_ID) on delete restrict on update cascade;

alter table BRANCH add constraint FK_RELATIONSHIP_11 foreign key (LOCATION_ID)
      references LOCATION (LOCATION_ID) on delete restrict on update cascade;

alter table CATEGORY add constraint FK_RELATIONSHIP_1 foreign key (TYPE_ID)
      references FUEL_TYPE (TYPE_ID) on delete restrict on update cascade;

alter table CATEGORY add constraint FK_RELATIONSHIP_2 foreign key (CARBODY_ID)
      references CARBODY_TYPE (CARBODY_ID) on delete restrict on update cascade;

alter table CATEGORY add constraint FK_RELATIONSHIP_3 foreign key (DOOR_ID)
      references DOOR_TYPE (DOOR_ID) on delete restrict on update cascade;

alter table CATEGORY add constraint FK_RELATIONSHIP_4 foreign key (TRANSMISSION_ID)
      references TRANSMISSION_TYPE (TRANSMISSION_ID) on delete restrict on update cascade;

alter table CATEGORY add constraint FK_RELATIONSHIP_5 foreign key (EMISSION_ID)
      references EMISSION_TYPE (EMISSION_ID) on delete restrict on update cascade;

alter table LICENCE add constraint FK_RELATIONSHIP_14 foreign key (CLIENT_ID)
      references CLIENT (CLIENT_ID) on delete restrict on update cascade;

alter table MODEL add constraint FK_RELATIONSHIP_8 foreign key (BRAND_ID)
      references CAR_BRAND (BRAND_ID) on delete restrict on update cascade;

alter table PRICELIST add constraint FK_RELATIONSHIP_22 foreign key (CALENDAR_ID)
      references CALENDAR (CALENDAR_ID) on delete restrict on update cascade;

alter table PRICELIST add constraint FK_RELATIONSHIP_24 foreign key (SHOP_ID)
      references BRANCH (SHOP_ID) on delete restrict on update cascade;

alter table PRICELIST add constraint FK_RELATIONSHIP_29 foreign key (TYPE_ID, CARBODY_ID, DOOR_ID, TRANSMISSION_ID, EMISSION_ID, CATEGORY_ID, BRAND_ID, MODEL_ID, SHOP_ID, CHASSIS_NUMBER)
      references VEHICLE (TYPE_ID, CARBODY_ID, DOOR_ID, TRANSMISSION_ID, EMISSION_ID, CATEGORY_ID, BRAND_ID, MODEL_ID, SHOP_ID, CHASSIS_NUMBER) on delete restrict on update cascade;

alter table RELAPS add constraint FK_RELATIONSHIP_15 foreign key (TYPE_ID, CARBODY_ID, DOOR_ID, TRANSMISSION_ID, EMISSION_ID, CATEGORY_ID, BRAND_ID, MODEL_ID, VEH_SHOP_ID, CLIENT_ID, CALENDAR_ID, CHASSIS_NUMBER, PRICELIST_ID, RENT_ID)
      references RENT (TYPE_ID, CARBODY_ID, DOOR_ID, TRANSMISSION_ID, EMISSION_ID, CATEGORY_ID, BRAND_ID, MODEL_ID, VEH_SHOP_ID, CLIENT_ID, CALENDAR_ID, CHASSIS_NUMBER, PRICELIST_ID, RENT_ID) on delete restrict on update cascade;

alter table RELAPS add constraint FK_RELATIONSHIP_17 foreign key (SHOP_ID)
      references BRANCH (SHOP_ID) on delete restrict on update cascade;

alter table RENT add constraint FK_RELATIONSHIP_13 foreign key (CLIENT_ID)
      references CLIENT (CLIENT_ID) on delete restrict on update cascade;

alter table RENT add constraint FK_RELATIONSHIP_16 foreign key (SHOP_ID)
      references BRANCH (SHOP_ID) on delete restrict on update cascade;

alter table RENT add constraint FK_RELATIONSHIP_25 foreign key (TYPE_ID, CARBODY_ID, DOOR_ID, TRANSMISSION_ID, EMISSION_ID, CATEGORY_ID, BRAND_ID, MODEL_ID, CALENDAR_ID, CHASSIS_NUMBER, PRICELIST_ID)
      references PRICELIST (TYPE_ID, CARBODY_ID, DOOR_ID, TRANSMISSION_ID, EMISSION_ID, CATEGORY_ID, BRAND_ID, MODEL_ID, CALENDAR_ID, CHASSIS_NUMBER, PRICELIST_ID) on delete restrict on update cascade;

alter table SALARY add constraint FK_RELATIONSHIP_20 foreign key (STAFF_ID)
      references STAFF (STAFF_ID) on delete restrict on update cascade;

alter table SALARY add constraint FK_RELATIONSHIP_27 foreign key (CALENDAR_ID)
      references CALENDAR (CALENDAR_ID) on delete restrict on update cascade;

alter table STAFF add constraint FK_MANAGER foreign key (STA_STAFF_ID)
      references STAFF (STAFF_ID) on delete restrict on update cascade;

alter table STAFF add constraint FK_RELATIONSHIP_12 foreign key (SHOP_ID)
      references BRANCH (SHOP_ID) on delete restrict on update cascade;

alter table VEHICLE add constraint FK_RELATIONSHIP_6 foreign key (TYPE_ID, CARBODY_ID, DOOR_ID, TRANSMISSION_ID, EMISSION_ID, CATEGORY_ID)
      references CATEGORY (TYPE_ID, CARBODY_ID, DOOR_ID, TRANSMISSION_ID, EMISSION_ID, CATEGORY_ID) on delete restrict on update cascade;

alter table VEHICLE add constraint FK_RELATIONSHIP_7 foreign key (BRAND_ID, MODEL_ID)
      references MODEL (BRAND_ID, MODEL_ID) on delete restrict on update cascade;

alter table VEHICLE add constraint FK_RELATIONSHIP_9 foreign key (SHOP_ID)
      references BRANCH (SHOP_ID) on delete restrict on update cascade;

