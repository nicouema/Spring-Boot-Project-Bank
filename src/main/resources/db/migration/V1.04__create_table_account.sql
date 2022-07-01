CREATE TABLE account
(
	account_id              bigint          not null,
    branch_id               bigint          not null,
    client_id               bigint          not null,
    minimum_balance_allowed double          null,
    initial_balance         double          not null,
    created_at              datetime(6)     not null,
    is_active               bit             not null,
    deleted_at              datetime(6)     null,
    updated_at              datetime(6)     null,
    primary key (account_id, branch_id),
    constraint branch_id_fk foreign key (branch_id) references branch (branch_id),
    constraint client_id_fk foreign key (client_id) references client (client_id)
)