DROP DATABASE IF EXISTS FindYourPlace;
CREATE DATABASE FindYourPlace;
USE FindYourPlace;

CREATE TABLE Utente
(
	id_utente 		bigint			PRIMARY KEY,
    username		varchar(30)		NOT NULL UNIQUE,
    passwordHash	binary(60)		NOT NULL,
    email	 		varchar(50)		NOT NULL UNIQUE,
    numeroTel		varchar(15),
    dataNascita		datetime		NOT NULL,
    isAdmin			boolean			NOT NULL,
    nome			varchar(50)		NOT NULL,
    cognome			varchar(50)		NOT NULL
);

CREATE TABLE Notifica
(
	id_notifica 	bigint			PRIMARY KEY,
    autore			varchar(30)		NOT NULL,
    testo			varchar(1000)	NOT NULL,
    dataInvio	 	datetime		NOT NULL,
    expireDate	 	datetime		NOT NULL
);

CREATE TABLE NotificaRicevuta
(
	id_utente 		bigint			NOT NULL,
    id_notifica		bigint			NOT NULL,
    isRead			boolean			NOT NULL,
    
    FOREIGN KEY (id_utente) REFERENCES Utente(id_utente) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_notifica) REFERENCES Notifica(id_notifica) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY(id_utente, id_notifica)
);

CREATE TABLE Preferenze
(
	id_utente 		bigint			PRIMARY KEY,
    notifiche		boolean			NOT NULL,
    isStudente		boolean,
    isGenitore		boolean,
    
    FOREIGN KEY (id_utente) REFERENCES Utente(id_utente) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Ricerca
(
	id_ricerca 		bigint			PRIMARY KEY,
    dataRicerca	 	datetime		NOT NULL,
    coordinate		point			NOT NULL,
    raggio			smallint		NOT NULL,
    
    id_utente 		bigint,
    FOREIGN KEY (id_utente) REFERENCES Utente(id_utente) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Filtri
(
	id_ricerca 			bigint			PRIMARY KEY,
    costoVita	 		varchar(10),
    dangerMax			decimal(5,2),
    numAbitantiMax		int,
    numAbitantiMin		int,
    numNegoziMin		smallint,
    numScuoleMin		smallint,
    numRistorantiMin	smallint,
    
    FOREIGN KEY (id_ricerca) REFERENCES Ricerca(id_ricerca) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Luogo
(
	id_luogo 		bigint			PRIMARY KEY,
    coordinate	 	point			NOT NULL,
    qualityIndex	decimal(5,2)	NOT NULL,
    lastFoundDate	datetime		NOT NULL
);

CREATE TABLE LuogoTrovato
(
	id_ricerca 		bigint			NOT NULL,
    id_luogo		bigint			NOT NULL,
    qualityIndex	decimal(5,2)	NOT NULL,
    costoVita	 	varchar(10)		NOT NULL,
    danger			decimal(5,2)	NOT NULL,
    numAbitanti		int				NOT NULL,
    numNegozi		smallint		NOT NULL,
    numScuole		smallint		NOT NULL,
    numRistoranti	smallint		NOT NULL,
    
    FOREIGN KEY (id_ricerca) REFERENCES Ricerca(id_ricerca) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_luogo) REFERENCES Luogo(id_luogo) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY(id_ricerca, id_luogo)
);

CREATE TABLE Preferiti
(
	id_utente 		bigint			NOT NULL,
    id_luogo		bigint			NOT NULL,
    qualityIndex	decimal(5,2)	NOT NULL,
    notifiche       boolean         NOT NULL,
    
    FOREIGN KEY (id_utente) REFERENCES Utente(id_utente) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_luogo) REFERENCES Luogo(id_luogo) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY(id_utente, id_luogo)
);