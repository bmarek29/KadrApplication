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
public class Stanowisko {
    private int id_stanowisko;
    private String nazwa;

    public Stanowisko() {
    }

    
    public Stanowisko(int id_stanowisko, String nazwa) {
        this.id_stanowisko = id_stanowisko;
        this.nazwa = nazwa;
    }

    public int getId_stanowisko() {
        return id_stanowisko;
    }

    public void setId_stanowisko(int id_stanowisko) {
        this.id_stanowisko = id_stanowisko;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
    
}
