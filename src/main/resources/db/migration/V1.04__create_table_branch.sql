CREATE TABLE branch
(
    branch_id       bigint      not null auto_increment,
    name            varchar(50) not null,
    street_number   int         not null,
    city_id      bigint      not null,
    created_at      datetime(6)     not null,
    is_active       bit             not null,
    deleted_at      datetime(6)     null,
    updated_at      datetime(6)     null,
    primary key(branch_id),
    constraint city_id_pk foreign key(city_id) references city(city_id)
)