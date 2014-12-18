/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadrapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.util.List;


/**
 *
 * @author qqq
 */
public class KadrManager {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement ps;
    private final String url = "jdbc:mysql://localhost:3306/kadrapp";

    private FillTable model;

    public void getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, "root", "");
            st = con.createStatement();

            System.out.println("połączono");
        } catch (Exception e) {
            System.out.println("sql error: " + e);
        }
    }

    public FillTable getPracownikDataTable() {
        ArrayList<Pracownik> pracowniks = new ArrayList<>();

        try {
            getConnection();
            rs = st.executeQuery("select * from pracownik");
            
            this.model = new FillTable(rs);

        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return model;
    }

    public List<Uzytkownik> getUzytkownikData(){
        ArrayList<Uzytkownik> uzytkownicy = new ArrayList<Uzytkownik>();
        try {
            getConnection();
            rs = st.executeQuery("select * from uzytkownik");
            uzytkownicy.clear();
            while (rs.next()) {
                Uzytkownik uzytkownik = new Uzytkownik(
                        rs.getInt("id_uzytkownik"),
                        rs.getString("data_utworzenia"),
                        rs.getLong("uprawnienia"), 
                        rs.getString("login"),
                        rs.getString("haslo")
                );
                uzytkownicy.add(uzytkownik);
            }

        } catch (Exception e) {
            System.out.println("bład " + e);
        }    
        return uzytkownicy;
    }
    
    public boolean checkUserInDB(String login, String haslo){
        String pass = "";
        try {
            getConnection();
            rs = st.executeQuery("select haslo from uzytkownik where login='" + login + "'");
            while(rs.next()){
               pass = rs.getString("haslo"); 
            }
            if (pass.equals(haslo))
                return true;
        } catch (Exception e) {
            System.err.println("bladcheckUserInDB "+e);
        }
        return false;
    }
    
    public String getUserPrivilage(String login){
        String privilage = "";
        try {
            getConnection();
            rs = st.executeQuery("select uprawnienia from uzytkownik where login='" + login + "'");
            while (rs.next()){
               privilage = rs.getString("uprawnienia"); 
            }
            
        } catch (Exception e) {
            System.err.println("bladGetUserPrivilage "+e);
        }
        return privilage;
    }
    
    public int addUzytkownik(Uzytkownik u) throws SQLException{
        int dodano = 0;
        try {
            getConnection();
            ps = con.prepareStatement("insert into uzytkownik(data_utworzenia,uprawnienia,login,haslo) values (?,?,?,?)");
            ps.setLong(1, u.getData_utworzenia());
            ps.setString(2, u.getUprawnienia());
            ps.setString(3, u.getLogin());
            ps.setString(4, u.getHaslo());
            dodano = ps.executeUpdate();

            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println("error "+e);
        }finally{
            ps.close();
            con.close();
        }
        return dodano;
    }
    
    public int addStanowisko(Stanowisko s) throws SQLException{
        int dodano = 0;
        try {
            getConnection();
            ps = con.prepareStatement("insert into stanowisko(nazwa) values (?)");
            ps.setString(1, s.getNazwa());
            
            dodano = ps.executeUpdate();

            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println("error "+e);
        }finally{
            ps.close();
            con.close();
        }
        return dodano;
    }
    
    public List<Stanowisko> getAllStanowiskoList() {
        List<Stanowisko> stanowiska = new ArrayList<>();
        try {
            getConnection();
            rs = st.executeQuery("select * from stanowisko");
            while (rs.next()) {
                Stanowisko s = new Stanowisko(
                        rs.getInt("id_stanowisko"),
                        rs.getString("nazwa")
                );                        
                stanowiska.add(s);
            }

        } catch (Exception e) {
            System.out.println("bład: " + e);
        }

        return  stanowiska;
    }
    public int getStanowiskoIdByNazwa(String nazwa){
        int id = 0;
        try {
            getConnection();
            rs = st.executeQuery("select id_stanowisko from stanowisko where nazwa='" + nazwa + "'");
            while (rs.next()){
               id = rs.getInt("id_stanowisko"); 
            }
            
        } catch (Exception e) {
            System.err.println("bladGetUserPrivilage "+e);
        }
        return id;
    }
    
    public int addPracownik(Pracownik p) {
        int dodano = 0;
        try {
            getConnection();
            //insert into pracownik(stanowisko_id_stanowisko,imie,nazwisko,`płeć`,
            //data_urodzenia,tytul,pesel,czy_studiuje,pensja)VALUES(0,'a','a','a',2002,'a',999,0,09);
            ps = con.prepareStatement("insert into pracownik("
                    + "stanowisko_id_stanowisko,"
                    + "imie,"
                    + "nazwisko,"
                    + "plec,"
                    + "data_urodzenia,"
                    + "tytul,"
                    + "pesel,"
                    + "czy_studiuje,"
                    + "pensja,"
                    + "data_przyjecia"
                    + ") values(?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, p.getStanowisko_id_stanowisko());
            ps.setString(2, p.getImie());
            ps.setString(3, p.getNazwisko());
            ps.setString(4, p.getPlec());
            ps.setLong(5, p.getData_urodzenia());
            ps.setString(6, p.getTytul());
            ps.setString(7, p.getPesel());
            ps.setInt(8, p.getCzy_studiuje());
            ps.setInt(9, p.getPensja());
            ps.setLong(10, p.getData_przyjecia());
            

            dodano = ps.executeUpdate();

            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("błąd: " + e);
        }
        return dodano;
    }
}
