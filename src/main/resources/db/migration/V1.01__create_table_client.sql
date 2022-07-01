CREATE TABLE client
(
    client_id       bigint          not null auto_increment,
    doc_type_id     bigint          not null,
    doc_number      varchar(20)     not null,
    name            varchar(30)     not null,
    lastname        varchar(30)     not null,
    phone_number    varchar(30)     null,
    street_name     varchar(30)     null,
    street_number   int             null,
    created_at      datetime(6)     not null,
    is_active       bit             not null,
    deleted_at      datetime(6)     null,
    updated_at      datetime(6)     null,
    primary key(client_id),
    constraint doc_type_id_fk foreign key (doc_type_id) references document_type (document_type_id)
) engine = InnoDB;
