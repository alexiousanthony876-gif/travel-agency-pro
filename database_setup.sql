create database if not exists tms;

use tms;

create table if not exists account(
    username varchar(30),
    name varchar(30),
    password varchar(30),
    security varchar(50),
    answer varchar(50),
    role varchar(20)
);

insert into account(username, name, password, security, answer, role) values('admin', 'Admin', 'admin', 'Your Lucky Number?', '1234', 'Admin');

create table if not exists customer(
	username varchar(30),
	id varchar(30),
	number varchar(30),
	name varchar(30),
	gender varchar(20),
	country varchar(30),
	address varchar(50),
	email varchar(40)
);

create table if not exists bookPackage(
	username varchar(30),
	package varchar(30),
	persons varchar(20),
	id varchar(30),
	number varchar(30),
	price varchar(20)
);

create table if not exists bookHotel(
	username varchar(30),
	hotel varchar(30),
	persons varchar(20),
	days varchar(20),
	ac varchar(10),
	food varchar(10),
	id varchar(30),
	number varchar(30),
	price varchar(20)
);

create table if not exists hotel(
	name varchar(30),
	costperperson varchar(20),
	foodincluded varchar(20),
	acroom varchar(20)
);

insert into hotel values('Raddisson Blue Hotel', '2000', '1600', '1000');
insert into hotel values('River View Hotel', '1800', '1200', '1000');
insert into hotel values('The Taj Hotel', '5000', '2500', '2000');
insert into hotel values('Marriott International', '4000', '2000', '1500');
insert into hotel values('Hyatt Regency', '4500', '2200', '1800');
insert into hotel values('Hotel Royal Orchid', '2500', '1500', '1200');
