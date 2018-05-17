drop table name;
drop table address;

create table name (
	id varchar(5) primary key,
	firstname varchar(50),
	lastname varchar(50),
	addrid varchar(5)
);

create table address (
	id varchar(5) primary key,
	street varchar(50),
	city varchar(50),
	state varchar(2),
	zip varchar(5)
);

insert into name (id, firstname, lastname, addrid)
values ('1', 'Joe', 'Smith', '1');

insert into name (id, firstname, lastname, addrid)
values ('2', 'Mary', 'Jones', '2');

insert into address (id, street, city, state, zip)
values ('1', '123 Main St', 'Springfield', 'WI', '54321');

insert into address (id, street, city, state, zip)
values ('2', '456 Miller Blvd', 'Asheville', 'NC', '12345');
