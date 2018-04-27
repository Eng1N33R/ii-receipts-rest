create table "products" (
    id uuid not null,
    name varchar(255) not null,
    price numeric(10, 2) not null,
    primary key (id)
);

create table "orders" (
    id bigserial not null,
    date timestamp not null,
    order_entries json not null,
    primary key (id)
);

create index uuid
    on public.products
    using btree (id);