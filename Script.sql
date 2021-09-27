create table users (
id  bigserial not null,
name varchar(20) not null,
primary key (id)
);

create table exams (
id  bigserial not null,
name varchar(50) not null,
primary key (id)
);

create table questions (
id  bigserial not null,
answer1 varchar(30) not null,
answer2 varchar(30) not null,
answer3 varchar(30) not null,
correct bpchar(1) not null,
description varchar(50) not null,
exam_id int8,
foreign key (exam_id) references exams(id),
primary key (id)
);

create table user_exams (
id  bigserial not null,
result int4,
exam_id int,
user_id int,
foreign key (exam_id) references exams(id),
foreign key (user_id) references users(id),
primary key (id)
);

create table user_answers (
id  bigserial not null,
answer bpchar(1)) not null,
question_id int8,
userExam_id int8,
foreign key (question_id) references questions(id),
foreign key (userExam_id) references user_exams(id),
primary key (id)
);

insert into exams (name) values 
('JAVA pagrindai'),
('PHP pagrindai');

insert into questions (exam_id, description, answer1, answer2, answer3,  correct) values 
(1,'Kelintais metais atsirado Java programavimo kalba?','1991','1997','2001','a'),
(1,'Pirma JAVA klase','init','run','main','c'),
(2,'Kaip atsidaro php script?','!','<@','<?php','c');


