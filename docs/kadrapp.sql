-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Czas generowania: 28 Gru 2014, 02:49
-- Wersja serwera: 5.6.21
-- Wersja PHP: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Baza danych: `kadrapp`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `do_zatwierdzenia`
--

CREATE TABLE IF NOT EXISTS `do_zatwierdzenia` (
`id_do_zatwierdzenia` int(10) NOT NULL,
  `uzytkownik_id_uzytkownik` int(11) NOT NULL,
  `id_pracownika` int(11) NOT NULL,
  `nazwa_pola_do_zmiany` varchar(20) NOT NULL,
  `wartosc_do_zmiany` varchar(200) NOT NULL,
  `id_uzytkownik_pracownik` int(11) DEFAULT NULL,
  `id_uzytkownik_kierownik` int(11) DEFAULT NULL,
  `zatwierdzone` tinyint(4) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `do_zatwierdzenia`
--

INSERT INTO `do_zatwierdzenia` (`id_do_zatwierdzenia`, `uzytkownik_id_uzytkownik`, `id_pracownika`, `nazwa_pola_do_zmiany`, `wartosc_do_zmiany`, `id_uzytkownik_pracownik`, `id_uzytkownik_kierownik`, `zatwierdzone`) VALUES
(75, 11, 35, 'pensja', '2100', NULL, 11, 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `historia_stanowiska`
--

CREATE TABLE IF NOT EXISTS `historia_stanowiska` (
`id_historia_stanowiska` int(11) NOT NULL,
  `pracownik_id_pracownik` int(11) NOT NULL,
  `data_rozpoczecia` varchar(20) NOT NULL,
  `data_zakonczenia` varchar(20) NOT NULL,
  `nazwa` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `pracownik`
--

CREATE TABLE IF NOT EXISTS `pracownik` (
`id_pracownik` int(11) NOT NULL,
  `stanowisko_id_stanowisko` int(11) NOT NULL,
  `imie` varchar(20) NOT NULL,
  `nazwisko` varchar(20) NOT NULL,
  `plec` varchar(15) NOT NULL,
  `data_urodzenia` bigint(20) unsigned NOT NULL,
  `tytul` varchar(50) NOT NULL,
  `pesel` varchar(12) NOT NULL,
  `czy_studiuje` tinyint(4) NOT NULL,
  `pensja` decimal(10,0) NOT NULL,
  `data_przyjecia` bigint(20) unsigned NOT NULL,
  `data_konca_umowy` bigint(20) unsigned NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `pracownik`
--

INSERT INTO `pracownik` (`id_pracownik`, `stanowisko_id_stanowisko`, `imie`, `nazwisko`, `plec`, `data_urodzenia`, `tytul`, `pesel`, `czy_studiuje`, `pensja`, `data_przyjecia`, `data_konca_umowy`) VALUES
(35, 6, 'Patryk', 'Nowak', 'Mezczyzna', 683762400000, 'mgr', '91020901344', 1, '2100', 1419731307844, 1451602800000);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `stanowisko`
--

CREATE TABLE IF NOT EXISTS `stanowisko` (
`id_stanowisko` int(11) NOT NULL,
  `nazwa` varchar(30) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `stanowisko`
--

INSERT INTO `stanowisko` (`id_stanowisko`, `nazwa`) VALUES
(6, 'Spedycja');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `stanowisko_has_stanowisko_podlegle`
--

CREATE TABLE IF NOT EXISTS `stanowisko_has_stanowisko_podlegle` (
  `stanowisko_id_stanowisko` int(11) NOT NULL,
  `stanowisko_podlegle_id_stanowisko_podlegle` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `stanowisko_has_stanowisko_podlegle`
--

INSERT INTO `stanowisko_has_stanowisko_podlegle` (`stanowisko_id_stanowisko`, `stanowisko_podlegle_id_stanowisko_podlegle`) VALUES
(6, 15),
(6, 16);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `stanowisko_podlegle`
--

CREATE TABLE IF NOT EXISTS `stanowisko_podlegle` (
`id_stanowisko_podlegle` int(11) NOT NULL,
  `nazwa` varchar(30) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `stanowisko_podlegle`
--

INSERT INTO `stanowisko_podlegle` (`id_stanowisko_podlegle`, `nazwa`) VALUES
(15, 'pakowanie wedlin'),
(16, 'produkcja wedlin');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `uzytkownik`
--

CREATE TABLE IF NOT EXISTS `uzytkownik` (
`id_uzytkownik` int(10) NOT NULL,
  `data_utworzenia` bigint(20) unsigned NOT NULL,
  `uprawnienia` enum('administrator','pracownik_kadr','kierownik_kadr') CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `login` varchar(20) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `haslo` varchar(20) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8_polish_ci COLLATE utf8_polish_ci;

--
-- Zrzut danych tabeli `uzytkownik`
--

INSERT INTO `uzytkownik` (`id_uzytkownik`, `data_utworzenia`, `uprawnienia`, `login`, `haslo`) VALUES
(11, 1418853516545, 'administrator', 'admin', 'admin'),
(13, 1419730175151, 'kierownik_kadr', 'kierownik', 'kierownik'),
(14, 1419730188046, 'pracownik_kadr', 'pracownik', 'pracownik');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `do_zatwierdzenia`
--
ALTER TABLE `do_zatwierdzenia`
 ADD PRIMARY KEY (`id_do_zatwierdzenia`);

--
-- Indexes for table `historia_stanowiska`
--
ALTER TABLE `historia_stanowiska`
 ADD PRIMARY KEY (`id_historia_stanowiska`);

--
-- Indexes for table `pracownik`
--
ALTER TABLE `pracownik`
 ADD PRIMARY KEY (`id_pracownik`);

--
-- Indexes for table `stanowisko`
--
ALTER TABLE `stanowisko`
 ADD PRIMARY KEY (`id_stanowisko`);

--
-- Indexes for table `stanowisko_podlegle`
--
ALTER TABLE `stanowisko_podlegle`
 ADD PRIMARY KEY (`id_stanowisko_podlegle`);

--
-- Indexes for table `uzytkownik`
--
ALTER TABLE `uzytkownik`
 ADD PRIMARY KEY (`id_uzytkownik`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `do_zatwierdzenia`
--
ALTER TABLE `do_zatwierdzenia`
MODIFY `id_do_zatwierdzenia` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=76;
--
-- AUTO_INCREMENT dla tabeli `historia_stanowiska`
--
ALTER TABLE `historia_stanowiska`
MODIFY `id_historia_stanowiska` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT dla tabeli `pracownik`
--
ALTER TABLE `pracownik`
MODIFY `id_pracownik` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=36;
--
-- AUTO_INCREMENT dla tabeli `stanowisko`
--
ALTER TABLE `stanowisko`
MODIFY `id_stanowisko` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT dla tabeli `stanowisko_podlegle`
--
ALTER TABLE `stanowisko_podlegle`
MODIFY `id_stanowisko_podlegle` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT dla tabeli `uzytkownik`
--
ALTER TABLE `uzytkownik`
MODIFY `id_uzytkownik` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=15;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
