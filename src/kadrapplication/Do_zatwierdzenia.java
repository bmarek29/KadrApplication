/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadrapplication;

/**
 *
 * @author qqq
 */
public class Do_zatwierdzenia {
    private int id_do_zatwierdzenia;
    private int uzytkownik_id_uzytkownik;
    private int id_pracownika;
    private String nazwa_pola_do_zmiany;
    private String wartosc_do_zmiany;
    private int id_uzytkownik_kierownik;
    private int id_uzytkownik_pracownik;
    private int zatwierdzone;

    public Do_zatwierdzenia() {
    }

    public Do_zatwierdzenia(int id_do_zatwierdzenia, int uzytkownik_id_uzytkownik, int id_pracownika, String nazwa_pola_do_zmiany, String wartosc_do_zmiany, int id_uzytkownik_kierownik, int id_uzytkownik_pracownik, int zatwierdzone) {
        this.id_do_zatwierdzenia = id_do_zatwierdzenia;
        this.uzytkownik_id_uzytkownik = uzytkownik_id_uzytkownik;
        this.id_pracownika = id_pracownika;
        this.nazwa_pola_do_zmiany = nazwa_pola_do_zmiany;
        this.wartosc_do_zmiany = wartosc_do_zmiany;
        this.id_uzytkownik_kierownik = id_uzytkownik_kierownik;
        this.id_uzytkownik_pracownik = id_uzytkownik_pracownik;
        this.zatwierdzone = zatwierdzone;
    }

    

    public int getUzytkownik_id_uzytkownik() {
        return uzytkownik_id_uzytkownik;
    }

    public void setUzytkownik_id_uzytkownik(int uzytkownik_id_uzytkownik) {
        this.uzytkownik_id_uzytkownik = uzytkownik_id_uzytkownik;
    }

    

    public int getId_do_zatwierdzenia() {
        return id_do_zatwierdzenia;
    }

    public void setId_do_zatwierdzenia(int id_do_zatwierdzenia) {
        this.id_do_zatwierdzenia = id_do_zatwierdzenia;
    }

    public int getId_pracownika() {
        return id_pracownika;
    }

    public void setId_pracownika(int id_pracownika) {
        this.id_pracownika = id_pracownika;
    }

    public String getNazwa_pola_do_zmiany() {
        return nazwa_pola_do_zmiany;
    }

    public void setNazwa_pola_do_zmiany(String nazwa_pola_do_zmiany) {
        this.nazwa_pola_do_zmiany = nazwa_pola_do_zmiany;
    }

    public String getWartosc_do_zmiany() {
        return wartosc_do_zmiany;
    }

    public void setWartosc_do_zmiany(String wartosc_do_zmiany) {
        this.wartosc_do_zmiany = wartosc_do_zmiany;
    }

    public int getId_uzytkownik_kierownik() {
        return id_uzytkownik_kierownik;
    }

    public void setId_uzytkownik_kierownik(int id_uzytkownik_kierownik) {
        this.id_uzytkownik_kierownik = id_uzytkownik_kierownik;
    }

    public int getId_uzytkownik_pracownik() {
        return id_uzytkownik_pracownik;
    }

    public void setId_uzytkownik_pracownik(int id_uzytkownik_pracownik) {
        this.id_uzytkownik_pracownik = id_uzytkownik_pracownik;
    }

    public int getZatwierdzone() {
        return zatwierdzone;
    }

    public void setZatwierdzone(int zatwierdzone) {
        this.zatwierdzone = zatwierdzone;
    }


}
    