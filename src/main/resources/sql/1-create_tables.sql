create table users
(
    user_id  int primary key auto_increment,
    username varchar(20)        not null,
    login    varchar(20) unique not null,
    password varchar(30)        not null
);

create table room_types
(
    room_type_id         int primary key auto_increment,
    room_type_name       varchar(25) unique                     not null,
    max_number_of_people int check ( max_number_of_people > 0 ) not null
);

create table rooms
(
    room_id      int primary key auto_increment,
    room_title   varchar(40) not null,
    room_type_id int         not null,

    foreign key (room_type_id) references room_types (room_type_id)
);
create table messages
(
    message_id         int primary key auto_increment,
    sender_id          int       not null,
    receiver_id        int       not null,
    replied_message_id int     default null,

    sent_date          timestamp not null,
    content            text      not null,
    room_id            int       not null,
    is_read            boolean default false,
    is_edited          boolean default false,

    foreign key (receiver_id) references users (user_id),
    foreign key (sender_id) references users (user_id),
    foreign key (replied_message_id) references messages (message_id),
    foreign key (room_id) references rooms (room_id)
);

create table roles
(
    role_id    int primary key auto_increment,
    role_name  varchar(20) unique not null,
    can_write  boolean default false,
    can_invite boolean default false,
    can_edit   boolean default false,
    can_remove boolean default false
);

create table room_members
(
    member_id int primary key auto_increment,

    user_id int not null ,
    room_id int not null ,
    role_id int not null,

    foreign key (user_id) references users (user_id),
    foreign key (room_id) references rooms (room_id),
    foreign key (role_id) references roles (role_id)
);
