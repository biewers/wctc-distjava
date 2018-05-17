drop table name;
drop table address;

create table name (
	id varchar(5) primary key,
	firstname varchar(50),
	lastname varchar(50)
);

create table address (
	id varchar(5) primary key,
	nameid varchar(5),
	street varchar(50),
	city varchar(50),
	state varchar(2),
	zip varchar(5)
);

insert into name (id, firstname, lastname) values ('1', 'Joe', 'Smith');
insert into name (id, firstname, lastname) values ('2', 'Mary', 'Jones');

insert into address (id, nameid, street, city, state, zip)
values ('1', '1', '123 Main St', 'Springfield', 'WI', '54321');

insert into address (id, nameid, street, city, state, zip)
values ('2', '2', '456 Miller Blvd', 'Asheville', 'NC', '12345');
