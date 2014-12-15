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
public class Stanowisko_Stanowisko_podlegle {
    private int id_stanowisko_fk;
    private int id_stanowisko_podlegle_fk;

    public Stanowisko_Stanowisko_podlegle() {
    }

    public Stanowisko_Stanowisko_podlegle(int id_stanowisko_fk, int id_stanowisko_podlegle_fk) {
        this.id_stanowisko_fk = id_stanowisko_fk;
        this.id_stanowisko_podlegle_fk = id_stanowisko_podlegle_fk;
    }

    public int getId_stanowisko_fk() {
        return id_stanowisko_fk;
    }

    public void setId_stanowisko_fk(int id_stanowisko_fk) {
        this.id_stanowisko_fk = id_stanowisko_fk;
    }

    public int getId_stanowisko_podlegle_fk() {
        return id_stanowisko_podlegle_fk;
    }

    public void setId_stanowisko_podlegle_fk(int id_stanowisko_podlegle_fk) {
        this.id_stanowisko_podlegle_fk = id_stanowisko_podlegle_fk;
    }
    
}
