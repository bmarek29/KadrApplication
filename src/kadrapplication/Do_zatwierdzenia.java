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
    private int id_pracownika;
    private int id_pola_do_zmiany;
    private String wartosc_do_zmiany;
    private int id_uzytkownik_kierownik;
    private int id_uzytkownik_pracownik;
    private boolean zatwierdzone;

    public Do_zatwierdzenia() {
    }

    public Do_zatwierdzenia(int id_do_zatwierdzenia, int id_pracownika, int id_pola_do_zmiany, String wartosc_do_zmiany, int id_uzytkownik_kierownik, int id_uzytkownik_pracownik, boolean zatwierdzone) {
        this.id_do_zatwierdzenia = id_do_zatwierdzenia;
        this.id_pracownika = id_pracownika;
        this.id_pola_do_zmiany = id_pola_do_zmiany;
        this.wartosc_do_zmiany = wartosc_do_zmiany;
        this.id_uzytkownik_kierownik = id_uzytkownik_kierownik;
        this.id_uzytkownik_pracownik = id_uzytkownik_pracownik;
        this.zatwierdzone = zatwierdzone;
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

    public int getId_pola_do_zmiany() {
        return id_pola_do_zmiany;
    }

    public void setId_pola_do_zmiany(int id_pola_do_zmiany) {
        this.id_pola_do_zmiany = id_pola_do_zmiany;
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

    public boolean isZatwierdzone() {
        return zatwierdzone;
    }

    public void setZatwierdzone(boolean zatwierdzone) {
        this.zatwierdzone = zatwierdzone;
    }
    
}
