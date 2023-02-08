CREATE TABLE IF NOT EXISTS public.tb_order
(
    id SERIAL,
    address character varying(255) COLLATE pg_catalog."default",
    latitude double precision,
    longitude double precision,
    moment timestamp(6) with time zone,
    status smallint,
    CONSTRAINT tb_order_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.tb_product
(
    id SERIAL,
    description character varying(255) COLLATE pg_catalog."default",
    image_uri character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    price double precision,
    CONSTRAINT tb_product_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.tb_order_product
(
    order_id bigint NOT NULL,
    product_id bigint NOT NULL,
    CONSTRAINT tb_order_product_pkey PRIMARY KEY (order_id, product_id),
    CONSTRAINT fk40anaevs16kmc2tbh7wc511fq FOREIGN KEY (order_id)
        REFERENCES public.tb_order (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fksu03ywlcvyqg5y78qey2q25lc FOREIGN KEY (product_id)
        REFERENCES public.tb_product (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);