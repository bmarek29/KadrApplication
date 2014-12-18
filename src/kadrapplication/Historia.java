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
public class Historia {
    private int id_historia;
    private int id_pracownik;
    private long data_rozpoczęcia;
    private long data_zakończenia;
    private String nazwa;

    public Historia() {
    }

    public Historia(int id_historia, int id_pracownik, long data_rozpoczęcia, long data_zakończenia, String nazwa) {
        this.id_historia = id_historia;
        this.id_pracownik = id_pracownik;
        this.data_rozpoczęcia = data_rozpoczęcia;
        this.data_zakończenia = data_zakończenia;
        this.nazwa = nazwa;
    }

    public int getId_historia() {
        return id_historia;
    }

    public void setId_historia(int id_historia) {
        this.id_historia = id_historia;
    }

    public int getId_pracownik() {
        return id_pracownik;
    }

    public void setId_pracownik(int id_pracownik) {
        this.id_pracownik = id_pracownik;
    }

    public long getData_rozpoczęcia() {
        return data_rozpoczęcia;
    }

    public void setData_rozpoczęcia(long data_rozpoczęcia) {
        this.data_rozpoczęcia = data_rozpoczęcia;
    }

    public long getData_zakończenia() {
        return data_zakończenia;
    }

    public void setData_zakończenia(long data_zakończenia) {
        this.data_zakończenia = data_zakończenia;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

}