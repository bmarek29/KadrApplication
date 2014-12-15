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
public class Stanowisko_podlegle {
    private int id_stanowisko_podlegle;
    private String nazwa;

    public Stanowisko_podlegle() {
    }

    
    public Stanowisko_podlegle(int id_stanowisko_podlegle, String nazwa) {
        this.id_stanowisko_podlegle = id_stanowisko_podlegle;
        this.nazwa = nazwa;
    }

    public int getId_stanowisko_podlegle() {
        return id_stanowisko_podlegle;
    }

    public void setId_stanowisko_podlegle(int id_stanowisko_podlegle) {
        this.id_stanowisko_podlegle = id_stanowisko_podlegle;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
    
    
}
