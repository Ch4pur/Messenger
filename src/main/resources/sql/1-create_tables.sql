create table users
(
    user_id  int primary key auto_increment,
    username varchar(20)        not null,
    login    varchar(20) unique not null,
    password varchar(30)        not null
);

create table rooms
(
    room_id      int primary key auto_increment,
    room_title   varchar(40) not null,
    max_number_of_people int check ( max_number_of_people > 0 ) not null
);
create table room_members
(
    member_id int primary key auto_increment,

    user_id   int not null,
    room_id   int not null,

    can_write  boolean default false,
    can_invite boolean default false,
    can_edit   boolean default false,
    can_remove boolean default false,

    foreign key (user_id) references users (user_id),
    foreign key (room_id) references rooms (room_id),
    unique (user_id, room_id)
);
create table messages
(
    message_id         int primary key auto_increment,
    member_id          int       not null,
    sent_date          timestamp not null,

    replied_message_id int     default null,
    content            text      not null,

    is_read            boolean default false,
    is_edited          boolean default false,

    foreign key (member_id) references room_members (member_id),
    foreign key (replied_message_id) references messages (message_id),
    unique (member_id, sent_date)
);


