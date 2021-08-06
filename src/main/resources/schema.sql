DROP ALL OBJECTS;

CREATE SCHEMA IF NOT EXISTS BOOKSTORE;

CREATE TABLE IF NOT EXISTS BOOKSTORE.CATEGORIA (
	id   		INTEGER      		NOT NULL AUTO_INCREMENT,
    nome 		VARCHAR2(100) 		NOT NULL,
    descricao 	VARCHAR2(100) 		NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS BOOKSTORE.LIVRO (
	id   			INTEGER   		NOT NULL AUTO_INCREMENT,
    titulo 			VARCHAR2(100) 	NOT NULL,
    nome_autor 		VARCHAR2(100) 	NOT NULL,
    texto 			VARCHAR2(100) 	NOT NULL,
    categoria_id 	INTEGER 		NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (categoria_id) REFERENCES BOOKSTORE.CATEGORIA(id)
);