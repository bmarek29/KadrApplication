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
public class Uzytkownik {
    private int id_uzytkownik;
    private long data_utworzenia;    
    private String uprawnienia;
    private String login;
    private String haslo;

    public Uzytkownik() {
    }

    public Uzytkownik(int id_uzytkownik,long data_utworzenia  ,String uprawnienia, String login, String haslo) {
        this.id_uzytkownik = id_uzytkownik;
        
        this.data_utworzenia = data_utworzenia;
        this.uprawnienia = uprawnienia;
        this.login = login;
        this.haslo = haslo;
    }

    public int getId_uzytkownik() {
        return id_uzytkownik;
    }

    public void setId_uzytkownik(int id_uzytkownik) {
        this.id_uzytkownik = id_uzytkownik;
    }

    public String getUprawnienia() {
        return uprawnienia;
    }

    public void setUprawnienia(String uprawnienia) {
        this.uprawnienia = uprawnienia;
    }

    public long getData_utworzenia() {
        return data_utworzenia;
    }

    public void setData_utworzenia(long data_utworzenia) {
        this.data_utworzenia = data_utworzenia;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }
    
}
