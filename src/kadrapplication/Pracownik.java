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
public class Pracownik {
private int id_pracownik;

    private String imie;
    private String nazwisko;
    private String data_urodzenia;
    private String tytul;
    private String pesel;
    private String id_stanowisko;
    private boolean czy_studiuje;
    private String data_przyjecia;
    private int pensja;

    public Pracownik() {
    }
    
    
    public Pracownik(int id_pracownik, String imie, String nazwisko, String data_urodzenia, String tytul, String pesel, String id_stanowisko, boolean czy_studiuje, String data_przyjecia, int pensja) {
        this.id_pracownik = id_pracownik;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.data_urodzenia = data_urodzenia;
        this.tytul = tytul;
        this.pesel = pesel;
        this.id_stanowisko = id_stanowisko;
        this.czy_studiuje = czy_studiuje;
        this.data_przyjecia = data_przyjecia;
        this.pensja = pensja;
    }

    public String getId_stanowisko() {
        return id_stanowisko;
    }

    public void setId_stanowisko(String id_stanowisko) {
        this.id_stanowisko = id_stanowisko;
    }

    
    public int getId_pracownik() {
        return id_pracownik;
    }

    public void setId_pracownik(int id_pracownik) {
        this.id_pracownik = id_pracownik;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getData_urodzenia() {
        return data_urodzenia;
    }

    public void setData_urodzenia(String data_urodzenia) {
        this.data_urodzenia = data_urodzenia;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }


    public boolean isCzy_studiuje() {
        return czy_studiuje;
    }

    public void setCzy_studiuje(boolean czy_studiuje) {
        this.czy_studiuje = czy_studiuje;
    }

    public String getData_przyjecia() {
        return data_przyjecia;
    }

    public void setData_przyjecia(String data_przyjecia) {
        this.data_przyjecia = data_przyjecia;
    }

    public int getPensja() {
        return pensja;
    }

    public void setPensja(int pensja) {
        this.pensja = pensja;
    }
    
    
}
