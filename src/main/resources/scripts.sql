-- Type: exchange

-- DROP TYPE IF EXISTS public.exchange;

CREATE TYPE public.exchange AS ENUM
    ('NYSE', 'NASDAQ', 'AMEX', 'CME', 'LSE', 'FX');

ALTER TYPE public.exchange
    OWNER TO postgres;


-- Type: derivativetype

-- DROP TYPE IF EXISTS public.derivativetype;

CREATE TYPE public.derivativetype AS ENUM
    ('STOCK', 'FUTURES', 'OPTION', 'CURRENCY_PAIR');

ALTER TYPE public.derivativetype
    OWNER TO postgres;


-- Table: public.derivative

-- DROP TABLE IF EXISTS public.derivative;

CREATE TABLE IF NOT EXISTS public.derivative
(
    id character varying COLLATE pg_catalog."default" NOT NULL,
    ticker character varying COLLATE pg_catalog."default",
    instrument derivativetype,
    exch exchange,
    price real,
    CONSTRAINT derivative_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.derivative
    OWNER to postgres;


-- Table: public.currencypair

-- DROP TABLE IF EXISTS public.currencypair;

CREATE TABLE IF NOT EXISTS public.currencypair
(
    id character varying COLLATE pg_catalog."default" NOT NULL,
    basecurrency character varying COLLATE pg_catalog."default",
    quotecurrency character varying COLLATE pg_catalog."default",
    date timestamp without time zone,
    CONSTRAINT currencypair_pkey PRIMARY KEY (id),
    CONSTRAINT currencypair_id_fkey FOREIGN KEY (id)
        REFERENCES public.derivative (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.currencypair
    OWNER to postgres;


-- Table: public.futures

-- DROP TABLE IF EXISTS public.futures;

CREATE TABLE IF NOT EXISTS public.futures
(
    id character varying COLLATE pg_catalog."default" NOT NULL,
    commodity character varying COLLATE pg_catalog."default",
    expirationdate timestamp without time zone,
    CONSTRAINT futures_pkey PRIMARY KEY (id),
    CONSTRAINT futures_id_fkey FOREIGN KEY (id)
        REFERENCES public.derivative (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.futures
    OWNER to postgres;


-- Table: public.stock

-- DROP TABLE IF EXISTS public.stock;

CREATE TABLE IF NOT EXISTS public.stock
(
    id character varying COLLATE pg_catalog."default" NOT NULL,
    industry character varying COLLATE pg_catalog."default",
    volume real,
    atr real,
    date timestamp without time zone,
    companyname character varying(55) COLLATE pg_catalog."default",
    CONSTRAINT stock_pkey PRIMARY KEY (id),
    CONSTRAINT stock_id_fkey FOREIGN KEY (id)
        REFERENCES public.derivative (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.stock
    OWNER to postgres;

    -- Table: public.portfolio

-- DROP TABLE IF EXISTS public.portfolio;

CREATE TABLE IF NOT EXISTS public.portfolio
(
    id character varying COLLATE pg_catalog."default" NOT NULL,
    sum real,
    created timestamp without time zone,
    CONSTRAINT pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.portfolio
    OWNER to postgres;


-- Table: public.derivative_portfolio

-- DROP TABLE IF EXISTS public.derivative_portfolio;

CREATE TABLE IF NOT EXISTS public.derivative_portfolio
(
    id_p character varying COLLATE pg_catalog."default" NOT NULL,
    id_d character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT derivative_portfolio_pkey PRIMARY KEY (id_p, id_d),
    CONSTRAINT derivative_portfolio_id_d_fkey FOREIGN KEY (id_d)
        REFERENCES public.derivative (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT derivative_portfolio_id_p_fkey FOREIGN KEY (id_p)
        REFERENCES public.portfolio (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.derivative_portfolio
    OWNER to postgres;