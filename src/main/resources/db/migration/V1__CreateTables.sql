CREATE SEQUENCE IF NOT EXISTS SPACESHIPS_SEQ;
CREATE SEQUENCE IF NOT EXISTS MOVIES_SEQ;
CREATE SEQUENCE IF NOT EXISTS SHOWS_SEQ;

CREATE TABLE SPACESHIPS(
                           ID int DEFAULT NEXTVAL('SPACESHIPS_SEQ') PRIMARY KEY,
                           NAME varchar(100) NOT NULL
);

INSERT INTO SPACESHIPS(ID, NAME) VALUES(NEXTVAL('SPACESHIPS_SEQ'), 'Millenium Falcon');
INSERT INTO SPACESHIPS(ID, NAME) VALUES(NEXTVAL('SPACESHIPS_SEQ'), 'Tardis');
INSERT INTO SPACESHIPS(ID, NAME) VALUES(NEXTVAL('SPACESHIPS_SEQ'), 'Elysium');
INSERT INTO SPACESHIPS(ID, NAME) VALUES(NEXTVAL('SPACESHIPS_SEQ'), 'USS Enterprise');
INSERT INTO SPACESHIPS(ID, NAME) VALUES(NEXTVAL('SPACESHIPS_SEQ'), 'The endurance');

CREATE TABLE IF NOT EXISTS MOVIES(
                       ID int DEFAULT NEXTVAL('MOVIES_SEQ') PRIMARY KEY,
                       NAME varchar(100) NOT NULL,
                       RELEASE_DATE DATE NOT NULL
);

INSERT INTO MOVIES(ID, NAME, RELEASE_DATE) VALUES(NEXTVAL('MOVIES_SEQ'), 'Star Wars: A New Hope', DATE '1977-05-25');
INSERT INTO MOVIES(ID, NAME, RELEASE_DATE) VALUES(NEXTVAL('MOVIES_SEQ'), 'The Matrix', DATE '1999-03-31');
INSERT INTO MOVIES(ID, NAME, RELEASE_DATE) VALUES(NEXTVAL('MOVIES_SEQ'), 'Interstellar', DATE '2014-10-26');
INSERT INTO MOVIES(ID, NAME, RELEASE_DATE) VALUES(NEXTVAL('MOVIES_SEQ'), 'Inception', DATE '2010-07-16');
INSERT INTO MOVIES(ID, NAME, RELEASE_DATE) VALUES(NEXTVAL('MOVIES_SEQ'), 'The Martian', DATE '2015-09-30');


CREATE TABLE IF NOT EXISTS SHOWS(
                      ID int  DEFAULT NEXTVAL('SHOWS_SEQ') PRIMARY KEY,
                      NAME varchar(100) NOT NULL,
                      RELEASE_DATE DATE NOT NULL,
                      AMOUNT_OF_SEASONS int NOT NULL
);

INSERT INTO SHOWS(ID, NAME, RELEASE_DATE, AMOUNT_OF_SEASONS) VALUES(NEXTVAL('SHOWS_SEQ'), 'Doctor Who', DATE '1963-11-23', 26);
INSERT INTO SHOWS(ID, NAME, RELEASE_DATE, AMOUNT_OF_SEASONS) VALUES(NEXTVAL('SHOWS_SEQ'), 'Star Trek: The Original Series', DATE '1966-09-08', 3);
INSERT INTO SHOWS(ID, NAME, RELEASE_DATE, AMOUNT_OF_SEASONS) VALUES(NEXTVAL('SHOWS_SEQ'), 'Stranger Things', DATE '2016-07-15', 4);
INSERT INTO SHOWS(ID, NAME, RELEASE_DATE, AMOUNT_OF_SEASONS) VALUES(NEXTVAL('SHOWS_SEQ'), 'Breaking Bad', DATE '2016-07-15', 5);
INSERT INTO SHOWS(ID, NAME, RELEASE_DATE, AMOUNT_OF_SEASONS) VALUES(NEXTVAL('SHOWS_SEQ'), 'The Witcher', DATE '2019-12-20', 2);
