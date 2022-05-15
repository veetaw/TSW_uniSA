DROP SCHEMA IF EXISTS bdCatalogo;
DROP DATABASE IF EXISTS bdCatalogo;
 
create schema bdCatalogo;
use bdCatalogo;
create table utente(
  username varchar(16) primary key,
    password varchar(32) not null,
    nome varchar(64) not null,
    cognome varchar(64) not null,
    mail varchar (64) not null,
    tipo enum("registrato", "amministratore") not null,
    via varchar(32) ,
    n_civico int,
    citta varchar(32) ,
    nazione varchar(32) 
    
);
create table ordine(
  id_ordine varchar(32) primary key,
    stato enum("in elaborazione", "elaborato","spedito","consegnato") not null,
    data date not null,
    username_utente varchar(32),
    foreign key (username_utente) references utente(username) 
);
create table corriere(
  targa varchar(32) primary key,
    compagnia varchar(32) not null,
    id_ordine_consegna varchar(32),
    foreign key (id_ordine_consegna) references ordine(id_ordine) 
);    
create table vino(
  id_prodotto varchar(32) primary key,
  nome varchar(256) not null,
    descrizione varchar(6000) not null,
    gradazione float not null,
    prezzo float not null,
    regione varchar(256) not null,
    url varchar(256),
    eliminato boolean default false not null, -- 0 per non eliminato
    tipo enum ('rosso','bianco'),
    sapore enum ('secco','dolce')
);
create table composizione_ordine(
    id_ordine_cliente varchar(32),
        id_prodotto_ordinato varchar(32),
        foreign key (id_ordine_cliente) references ordine(id_ordine),
        foreign key (id_prodotto_ordinato) references vino(id_prodotto)
);
create table pagamento(
    pagamento varchar(32) primary key,
        data_pagamento date not null,
        aliquote_iva int not null,
        tipo enum("paypal","contrassegno","carta di credito") not null,
        id_ordine_cl varchar(32),
        foreign key (id_ordine_cl) references ordine(id_ordine)
);