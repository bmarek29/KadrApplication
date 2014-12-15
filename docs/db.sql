CREATE TABLE do_zatwierdzenia (
  id_do_zatwierdzenia INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  users_id_user INTEGER UNSIGNED NOT NULL,
  id_pracownika INTEGER UNSIGNED NULL,
  id_pola_do_zmiany INTEGER UNSIGNED NULL,
  wartosc_do_zmiany VARCHAR NULL,
  id_user_pracownik INTEGER UNSIGNED NULL,
  id_user_kierownik INTEGER UNSIGNED NULL,
  zatwierdzone BOOL NULL DEFAULT FALSE,
  PRIMARY KEY(id_do_zatwierdzenia),
  INDEX do_zatwierdzenia_FKIndex1(users_id_user)
);

CREATE TABLE historia_stanowiska (
  id_historia_stanowiska INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  pracownik_id_pracownik INTEGER UNSIGNED NOT NULL,
  data_rozpoczecia DATE NULL,
  data_zakonczenia DATE NULL,
  nazwa VARCHAR(30) NULL,
  id_pracownik INTEGER UNSIGNED NULL,
  PRIMARY KEY(id_historia_stanowiska),
  INDEX historia_stanowiska_FKIndex1(pracownik_id_pracownik)
);

CREATE TABLE pensja (
  id_pensja INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  kwota DECIMAL NULL,
  PRIMARY KEY(id_pensja)
);

CREATE TABLE pracownik (
  id_pracownik INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  stanowisko_id_stanowisko INTEGER UNSIGNED NOT NULL,
  imie VARCHAR(25) NULL,
  nazwisko VARCHAR(30) NULL,
  data_urodzenia DATE NULL,
  tytul VARCHAR(15) NULL,
  pesel VARCHAR(12) NULL,
  id_stanowsiko INTEGER UNSIGNED NULL,
  czy_studiuje BOOL NULL,
  id_pensja INTEGER UNSIGNED NULL,
  do_zatwierdzenia BOOL NULL DEFAULT false,
  PRIMARY KEY(id_pracownik),
  INDEX pracownik_FKIndex1(stanowisko_id_stanowisko)
);

CREATE TABLE stanowisko (
  id_stanowisko INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  pensja_id_pensja INTEGER UNSIGNED NOT NULL,
  nazwa VARCHAR(30) NULL,
  id_podlegle INTEGER UNSIGNED NULL,
  PRIMARY KEY(id_stanowisko),
  INDEX stanowisko_FKIndex2(pensja_id_pensja)
);

CREATE TABLE stanowisko_has_stanowisko_podlegle (
  stanowisko_id_stanowisko INTEGER UNSIGNED NOT NULL,
  stanowisko_podlegle_id_stanowisko_podlegle INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(stanowisko_id_stanowisko, stanowisko_podlegle_id_stanowisko_podlegle),
  INDEX stanowisko_has_stanowisko_podlegle_FKIndex1(stanowisko_id_stanowisko),
  INDEX stanowisko_has_stanowisko_podlegle_FKIndex2(stanowisko_podlegle_id_stanowisko_podlegle)
);

CREATE TABLE stanowisko_podlegle (
  id_stanowisko_podlegle INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nazwa VARCHAR(30) NULL,
  PRIMARY KEY(id_stanowisko_podlegle)
);

CREATE TABLE users (
  id_user INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  uprawnienia ENUM NULL DEFAULT administrator,pracownik_kadr,kierownik_kadr,
  login VARCHAR(20) NULL,
  haslo VARCHAR(20) NULL,
  PRIMARY KEY(id_user)
);


