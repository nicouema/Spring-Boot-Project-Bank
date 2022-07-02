CREATE TABLE city
(
    city_id         bigint      not null auto_increment,
    name            varchar(50) not null,
    created_at      datetime(6)     not null,
    is_active       bit             not null,
    deleted_at      datetime(6)     null,
    updated_at      datetime(6)     null,
    primary key(city_id)
)