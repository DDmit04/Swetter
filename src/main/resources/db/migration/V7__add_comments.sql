create table comment (
	id int8 not null, 
	text varchar(2048), 
	message_id int8, 
	primary key (id)
)