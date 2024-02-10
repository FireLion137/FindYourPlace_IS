USE FindYourPlace;

INSERT INTO Utente (id_utente,username, passwordHash, email, numeroTel, dataNascita, isAdmin, nome, cognome)
VALUES ('1', 'ADMIN_ALE', '$2a$10$2jAfxYLRBWxuyLLUeWplZObDPh2QV3JHpWa.fXMnbNLUhKS1KAaRa', 'xx43ale16xx@gmail.com', '3279868355', '2002-07-03', TRUE, 'Alessandro', 'Nacchia');
INSERT INTO Utente (id_utente ,username, passwordHash, email, numeroTel, dataNascita, isAdmin, nome, cognome)
VALUES ('2', 'MarioneSt', '$2a$10$deRYhFHUcR0rKxEIJSn3neDAZzYh4KO6djFqBMItKZa/WTVqK325y', 'sturniolata@gmail.com', '3251578487', '1998-07-05', FALSE, 'Mario', 'Sturniolo');