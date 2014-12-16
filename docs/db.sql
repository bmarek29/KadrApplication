-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Czas generowania: 16 Gru 2014, 12:03
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
`id_do_zatwierdzenia` int(10) unsigned NOT NULL,
  `uzytkownik_id_uzytkownik` int(11) NOT NULL,
  `id_pracownika` int(11) NOT NULL,
  `id_pola_do_zmiany` int(11) NOT NULL,
  `wartosc_do_zmiany` varchar(200) NOT NULL,
  `id_uzytkownik_pracownik` int(11) NOT NULL,
  `id_uzytkownik_kierownik` int(11) NOT NULL,
  `zatwierdzone` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `historia_stanowiska`
--

CREATE TABLE IF NOT EXISTS `historia_stanowiska` (
`id_historia_stanowiska` int(11) NOT NULL,
  `pracownik_id_pracownik` int(11) NOT NULL,
  `data_rozpoczecia` date NOT NULL,
  `data_zakonczenia` date NOT NULL,
  `nazwa` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `pracownik`
--

CREATE TABLE IF NOT EXISTS `pracownik` (
`id_pracownik` int(11) NOT NULL,
  `stanowisko_id_stanowisko` int(11) NOT NULL,
  `imie` varchar(20) NOT NULL,
  `nazwisko` varchar(20) NOT NULL,
  `data_urodzenia` date NOT NULL,
  `tytul` varchar(10) NOT NULL,
  `pesel` varchar(12) NOT NULL,
  `czy_studiuje` tinyint(4) NOT NULL,
  `pensja` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `stanowisko`
--

CREATE TABLE IF NOT EXISTS `stanowisko` (
`id_stanowisko` int(11) NOT NULL,
  `nazwa` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `stanowisko_has_stanowisko_podlegle`
--

CREATE TABLE IF NOT EXISTS `stanowisko_has_stanowisko_podlegle` (
  `stanowisko_id_stanowisko` int(11) NOT NULL,
  `stanowisko_podlegle_id_stanowisko_podlegle` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `stanowisko_podlegle`
--

CREATE TABLE IF NOT EXISTS `stanowisko_podlegle` (
`id_stanowisko_podlegle` int(11) NOT NULL,
  `nazwa` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `uzytkownik`
--

CREATE TABLE IF NOT EXISTS `uzytkownik` (
`id_uzytkownik` int(10) unsigned NOT NULL,
  `uprawnienia` enum('administrator','pracownik_kadr','kierownik_kadr') DEFAULT NULL,
  `login` varchar(20) DEFAULT NULL,
  `haslo` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
MODIFY `id_do_zatwierdzenia` int(10) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `historia_stanowiska`
--
ALTER TABLE `historia_stanowiska`
MODIFY `id_historia_stanowiska` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `pracownik`
--
ALTER TABLE `pracownik`
MODIFY `id_pracownik` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `stanowisko`
--
ALTER TABLE `stanowisko`
MODIFY `id_stanowisko` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `stanowisko_podlegle`
--
ALTER TABLE `stanowisko_podlegle`
MODIFY `id_stanowisko_podlegle` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `uzytkownik`
--
ALTER TABLE `uzytkownik`
MODIFY `id_uzytkownik` int(10) unsigned NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
