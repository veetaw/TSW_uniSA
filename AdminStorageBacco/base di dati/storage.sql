DROP DATABASE IF EXISTS storage;
CREATE DATABASE storage;
USE storage;

DROP TABLE IF EXISTS product;

CREATE TABLE product 
(	
  code int primary key AUTO_INCREMENT,
  name char(20) not null,
  description char(100),
  price int default 0,
  quantity int default 0
);

INSERT INTO product values (1,"aglianico","vino rosso coltivato nelle regioni Basilicata Campania Puglia e Molise",28,4);
INSERT INTO product values (2,"abruzzo passito","vino bianco d'abruzzo",40,2);
INSERT INTO product values (3,"chianti","vino toscano",25,2);
INSERT INTO product values (4,"barbera","piemonte",50,6);

