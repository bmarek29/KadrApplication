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
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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


}
