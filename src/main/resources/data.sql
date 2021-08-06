------ CATEGORIA --------

INSERT INTO bookstore.categoria (nome, descricao) VALUES ('Informática', 'Livros de TI');
INSERT INTO bookstore.categoria (nome, descricao) VALUES ('Psicologia', 'Livros de Psicologia');
INSERT INTO bookstore.categoria (nome, descricao) VALUES ('Veterinaria', 'Livros de Veterinaria');

------ LIVRO --------

INSERT INTO bookstore.livro (titulo, nome_autor, texto, categoria_id) VALUES ('Clean code', 'Robert C. Martin', 'Lorem ipsum', 1);
INSERT INTO bookstore.livro (titulo, nome_autor, texto, categoria_id) VALUES ('Inteligencia Emocional', 'Daniel Goleman', 'Lorem ipsum', 2);
INSERT INTO bookstore.livro (titulo, nome_autor, texto, categoria_id) VALUES ('O Poder do Hábito', 'Charles Duhigg', 'Lorem ipsum', 2);
INSERT INTO bookstore.livro (titulo, nome_autor, texto, categoria_id) VALUES ('Padrões de Projetos: Soluções Reutilizáveis', 'Erich Gamma', 'Lorem ipsum', 1);
INSERT INTO bookstore.livro (titulo, nome_autor, texto, categoria_id) VALUES ('Cirurgia de Pequenos Animais', 'Theresa Fossum', 'Lorem ipsum', 3);