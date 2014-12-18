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
    private int stanowisko_id_stanowisko;
    private String imie;
    private String nazwisko;
    private long data_urodzenia;
    private String plec;
    private String tytul;
    private String pesel;
    private int czy_studiuje;
    private int pensja;
    private long data_przyjecia;
    private long data_konca_umowy;

    public long getData_konca_umowy() {
        return data_konca_umowy;
    }

    public void setData_konca_umowy(long data_konca_umowy) {
        this.data_konca_umowy = data_konca_umowy;
    }
    
    

    public long getData_urodzenia() {
        return data_urodzenia;
    }

    public void setData_urodzenia(long data_urodzenia) {
        this.data_urodzenia = data_urodzenia;
    }

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }

    public int getStanowisko_id_stanowisko() {
        return stanowisko_id_stanowisko;
    }

    public void setStanowisko_id_stanowisko(int stanowisko_id_stanowisko) {
        this.stanowisko_id_stanowisko = stanowisko_id_stanowisko;
    }

    
    public Pracownik() {
    }
    
    
    public Pracownik(int id_pracownik, int stanowisko_id_stanowisko, String imie, String nazwisko, String plec, long data_urodzenia,  String tytul, String pesel, int czy_studiuje, int pensja, long data_przyjecia, long data_konca_umowy) {
        this.id_pracownik = id_pracownik;
        this.stanowisko_id_stanowisko = stanowisko_id_stanowisko;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.plec = plec;
        this.data_urodzenia = data_urodzenia;        
        this.tytul = tytul;
        this.pesel = pesel;
        this.czy_studiuje = czy_studiuje;
        this.pensja = pensja;
        this.data_przyjecia = data_przyjecia;
        this.data_konca_umowy = data_konca_umowy;
        
        
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


    public int getCzy_studiuje() {
        return czy_studiuje;
    }

    public void setCzy_studiuje(int czy_studiuje) {
        this.czy_studiuje = czy_studiuje;
    }

    public long getData_przyjecia() {
        return data_przyjecia;
    }

    public void setData_przyjecia(long data_przyjecia) {
        this.data_przyjecia = data_przyjecia;
    }

    public int getPensja() {
        return pensja;
    }

    public void setPensja(int pensja) {
        this.pensja = pensja;
    }
    
    
}
