CREATE TABLE statement
(
    statement_id        bigint      not null auto_increment,
    account_id          bigint      not null,
    branch_id			bigint		not null,
    movement_type_id    bigint      not null,
    amount              double      not null,
    operation_date_time datetime(6) not null,
    created_at          datetime(6)     not null,
    is_active           bit             not null,
    deleted_at          datetime(6)     null,
    updated_at          datetime(6)     null,
    primary key(statement_id),
    constraint account_id_fk foreign key (account_id, branch_id) references account (account_id, branch_id),
    constraint movement_type_id_fk foreign key (movement_type_id) references movement_type (movement_id)
)