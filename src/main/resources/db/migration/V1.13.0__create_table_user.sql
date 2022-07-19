
create table user
(
    user_id             bigint      not null    auto_increment,
    email               varchar(40) not null unique,
    password            varchar(200) not null,
    role_id             bigint      not null,
    client_id           bigint      null,
    is_active bit not null,
    created_at datetime(6) not null,
    updated_at datetime(6) null,
    primary key (user_id),
    foreign key (role_id) references role(role_id),
    foreign key (client_id) references client(client_id)

) engine = InnoDB;
