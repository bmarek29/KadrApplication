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

    public FillTable getPracownikData() {
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
}
