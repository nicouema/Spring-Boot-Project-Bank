CREATE TABLE document_type
(
    document_type_id        bigint      not null    auto_increment,
    description             varchar(50) not null,
    created_at      datetime(6)     not null,
    is_active       bit             not null,
    deleted_at      datetime(6)     null,
    updated_at      datetime(6)     null,
    primary key(document_type_id)
)