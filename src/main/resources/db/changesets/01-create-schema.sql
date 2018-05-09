create table "product" (
    "id" uuid not null,
    "name" varchar(255) not null,
    "price" numeric(10, 2) not null,
    primary key (id)
);

create table "productorder" (
    "id" bigserial not null,
    "date" timestamp not null,
    primary key (id)
);

create table "entry" (
    "order_id" bigint not null,
    "product_id" uuid not null,
    "amount" integer,
    primary key (order_id, product_id),
    foreign key (order_id)
        references "productorder" (id)
        on delete cascade
        on update cascade,
    foreign key (product_id)
        references "product" (id)
        on delete cascade
        on update cascade
);

create index "uuid"
    on "product"
    using btree (id);

create index "entry_orders"
    on "entry"
    using btree (order_id);