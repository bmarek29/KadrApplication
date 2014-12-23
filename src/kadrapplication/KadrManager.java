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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

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
        } catch (Exception e) {
            System.out.println("sql connection error: " + e);
        }
    }

    public long getDateInMilisecFromString(String s) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(s);
        long mili = date.getTime();
        return mili;
    }

    public Pracownik getPracownikById(int id) {
        Pracownik p = null;
        try {
            getConnection();
            rs = st.executeQuery("select * from pracownik where id_pracownik='" + id + "'");
            while (rs.next()) {
                p = new Pracownik(
                        rs.getInt("id_pracownik"),
                        rs.getInt("stanowisko_id_stanowisko"),
                        rs.getString("imie"),
                        rs.getString("nazwisko"),
                        rs.getString("plec"),
                        rs.getLong("data_urodzenia"),
                        rs.getString("tytul"),
                        rs.getString("pesel"),
                        rs.getInt("czy_studiuje"),
                        rs.getInt("pensja"),
                        rs.getLong("data_przyjecia"),
                        rs.getLong("data_konca_umowy")
                );
            }
        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return p;
    }

    public Uzytkownik getUzytkownikById(int id) {
        Uzytkownik p = null;
        try {
            getConnection();
            rs = st.executeQuery("select * from uzytkownik where id_uzytkownik='" + id + "'");
            while (rs.next()) {
                p = new Uzytkownik(
                        rs.getInt("id_uzytkownik"),
                        rs.getLong("data_utworzenia"),
                        rs.getString("uprawnienia"),
                        rs.getString("login"),
                        rs.getString("haslo")
                );
            }
        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return p;
    }

    public Historia getHistoriaStanowiskaByIdPracownika(int id_pracownik) {
        Historia h = null;
        try {
            getConnection();
            rs = st.executeQuery("select nazwa from stanowisko_podlegle where id_stanowisko_podlegle=" + id_pracownik);
            while (rs.next()) {
                h = new Historia(rs.getInt("id_historia_stanowiska"), id_pracownik, rs.getLong("data_rozpoczecia"), rs.getLong("data_zakonczenia"), rs.getString("nazwa"));
            }
        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return h;
    }

    public DefaultListModel getStanowiskoHisStanowiskoPodlegle(int stanowisko_id) {
        DefaultListModel model = new DefaultListModel();
        int podlegle_id = 0;
        ArrayList<Stanowisko_podlegle> id_list = new ArrayList<>();
        Stanowisko_podlegle sp;
        try {
            getConnection();
            rs = st.executeQuery("select * from "
                    + "stanowisko_has_stanowisko_podlegle"
                    + " where stanowisko_id_stanowisko=" + stanowisko_id);
            while (rs.next()) {
                podlegle_id = rs.getInt("stanowisko_podlegle_id_stanowisko_podlegle");
                sp = new Stanowisko_podlegle();
                sp.setId_stanowisko_podlegle(podlegle_id);
                id_list.add(sp);
            }
            for (Stanowisko_podlegle id : id_list) {
                model.addElement(getStanowiskoPodlegleById(id.getId_stanowisko_podlegle()).getNazwa());
            }

        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return model;
    }

    public Stanowisko_podlegle getStanowiskoPodlegleById(int id) {
        Stanowisko_podlegle sp = null;
        try {
            getConnection();
            rs = st.executeQuery("select nazwa from stanowisko_podlegle where id_stanowisko_podlegle=" + id);
            while (rs.next()) {
                sp = new Stanowisko_podlegle(id, rs.getString("nazwa"));
            }
        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return sp;
    }

    public int getStanowiskoPodlegleIdByNazwa(String nazwa) {
        int sp = 0;
        try {
            getConnection();
            rs = st.executeQuery("select id_stanowisko_podlegle from stanowisko_podlegle where nazwa='" + nazwa + "'");
            while (rs.next()) {
                sp = rs.getInt("id_stanowisko_podlegle");
            }
        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return sp;
    }

    public int addStanowiskoPodlegleToStanowisko(int id_st, int id_pdl) throws SQLException {
        int dodano = 0;
        try {
            getConnection();
            ps = con.prepareStatement("insert into "
                    + "stanowisko_has_stanowisko_podlegle"
                    + "(stanowisko_id_stanowisko,"
                    + "stanowisko_podlegle_id_stanowisko_podlegle) "
                    + "values (?,?)");
            ps.setInt(1, id_st);
            ps.setInt(2, id_pdl);
            dodano = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println("error " + e);
        } finally {
            ps.close();
            con.close();
        }
        return dodano;
    }

    public FillTable getPracownikHistoriaStanowiskDataTable(Pracownik p) {
        try {
            getConnection();
            rs = st.executeQuery("select id_pracownik as id,imie,nazwisko,pesel from pracownik");

            this.model = new FillTable(rs);

        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return model;
    }

    public FillTable getPracownikDataTable() {
        try {
            getConnection();
            rs = st.executeQuery("select id_pracownik as id,imie,nazwisko,pesel from pracownik");

            this.model = new FillTable(rs);

        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return model;
    }

    public FillTable getPracownikHistStanoDataTable() {
        try {
            getConnection();
            rs = st.executeQuery("select h.id_historia_stanowiska as id, "
                    + "CONCAT(p.imie, ' ',p.nazwisko) as pracownik, "
                    + "h.nazwa as nazwa from historia_stanowiska h, pracownik p "
                    + "where h.pracownik_id_pracownik = p.id_pracownik");
            this.model = new FillTable(rs);
        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return model;
    }

    public FillTable getZatwierdzanieDataTable() {
        try {
            getConnection();
            rs = st.executeQuery("select id_do_zatwierdzenia as id, "
                    + "u.login as uzytkownik, "
                    + "CONCAT(p.imie, ' ', p.nazwisko) as pracownik, "
                    + "nazwa_pola_do_zmiany as pole, "
                    + "wartosc_do_zmiany as wartosc "
                    + "from do_zatwierdzenia d, pracownik p, uzytkownik u "
                    + "where d.id_pracownika = p.id_pracownik "
                    + "and d.uzytkownik_id_uzytkownik = u.id_uzytkownik "
                    + "and d.zatwierdzone like 1");
            this.model = new FillTable(rs);

        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return model;
    }

    public Do_zatwierdzenia getDoZatwierdzeniaById(int id) {
        Do_zatwierdzenia dz = null;
        try {
            getConnection();
            rs = st.executeQuery("select * from do_zatwierdzenia where id_do_zatwierdzenia='" + id + "'");
            while (rs.next()) {
                dz = new Do_zatwierdzenia(id,
                        rs.getInt("uzytkownik_id_uzytkownik"),
                        rs.getInt("id_pracownika"),
                        rs.getString("nazwa_pola_do_zmiany"),
                        rs.getString("wartosc_do_zmiany"),
                        rs.getInt("id_uzytkownik_pracownik"),
                        rs.getInt("id_uzytkownik_kierownik"),
                        rs.getInt("zatwierdzone")
                );
            }
        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return dz;
    }

    public FillTable getPracownikDataTableWithQuery(String nazwa, String cmb) {
        String query = "";
        if (cmb.equals("Stanowisko")) {
            query = "select id_pracownik as id,imie,nazwisko,pesel from pracownik where stanowisko_id_stanowisko=" + getStanowiskoIdByNazwa(nazwa);
        }
        if (cmb.equals("Nazwisko")) {
            query = "select id_pracownik as id,imie,nazwisko,pesel from pracownik where nazwisko='" + nazwa + "'";
        }
        if (cmb.equals("Pesel")) {
            query = "select id_pracownik as id,imie,nazwisko,pesel from pracownik where pesel='" + nazwa + "'";
        }
        if (cmb.equals("")) {
            query = "select id_pracownik as id,imie,nazwisko,pesel from pracownik";
        }
        try {
            getConnection();
            rs = st.executeQuery(query);

            this.model = new FillTable(rs);

        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return model;
    }

    public List<Pracownik> getPracownikDataList() {
        List<Pracownik> pracownicy = new ArrayList<>();
        try {
            getConnection();
            rs = st.executeQuery("select * from pracownik");
            while (rs.next()) {
                Pracownik pracownik = new Pracownik(
                        rs.getInt("id_pracownik"),
                        rs.getInt("stanowisko_id_stanowisko"),
                        rs.getString("imie"),
                        rs.getString("nazwisko"),
                        rs.getString("plec"),
                        rs.getLong("data_urodzenia"),
                        rs.getString("tytul"),
                        rs.getString("pesel"),
                        rs.getInt("czy_studiuje"),
                        rs.getInt("pensja"),
                        rs.getLong("data_przyjecia"),
                        rs.getLong("data_konca_umowy")
                );

                pracownicy.add(pracownik);
            }

        } catch (Exception e) {
            System.out.println("bład: " + e);
        }

        return pracownicy;
    }

    public List<Uzytkownik> getUzytkownikData() {
        ArrayList<Uzytkownik> uzytkownicy = new ArrayList<Uzytkownik>();
        try {
            getConnection();
            rs = st.executeQuery("select * from uzytkownik");
            uzytkownicy.clear();
            while (rs.next()) {
                Uzytkownik uzytkownik = new Uzytkownik(
                        rs.getInt("id_uzytkownik"),
                        rs.getLong("data_utworzenia"),
                        rs.getString("uprawnienia"),
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

    public FillTable getUzytkownikDataTable() {
        try {
            getConnection();
            rs = st.executeQuery("select id_uzytkownik as id,uprawnienia,login from uzytkownik");

            this.model = new FillTable(rs);

        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return model;
    }

    public FillTable getStanowiskoDataTable() {
        try {
            getConnection();
            rs = st.executeQuery("select id_stanowisko as id,nazwa from stanowisko");

            this.model = new FillTable(rs);

        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return model;
    }

    public DefaultListModel getAllStanowiskoListModel() {
        DefaultListModel model = new DefaultListModel();
        try {
            getConnection();
            rs = st.executeQuery("select * from stanowisko");
            while (rs.next()) {
                model.addElement(rs.getString("nazwa"));
            }
        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return model;
    }

    public boolean checkUserInDB(String login, String haslo) {
        String pass = "";
        try {
            getConnection();
            rs = st.executeQuery("select haslo from uzytkownik where login='" + login + "'");
            while (rs.next()) {
                pass = rs.getString("haslo");
            }
            if (pass.equals(haslo)) {
                return true;
            }
        } catch (Exception e) {
            System.err.println("bladcheckUserInDB " + e);
        }
        return false;
    }

    public String getUserPrivilage(String login) {
        String privilage = "";
        try {
            getConnection();
            rs = st.executeQuery("select uprawnienia from uzytkownik where login='" + login + "'");
            while (rs.next()) {
                privilage = rs.getString("uprawnienia");
            }

        } catch (Exception e) {
            System.err.println("bladGetUserPrivilage " + e);
        }
        return privilage;
    }

    public int addUzytkownik(Uzytkownik u) throws SQLException {
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
            System.err.println("error " + e);
        } finally {
            ps.close();
            con.close();
        }
        return dodano;
    }

    public int addStanowisko(Stanowisko s) throws SQLException {
        int dodano = 0;
        try {
            getConnection();
            ps = con.prepareStatement("insert into stanowisko(nazwa) values (?)");
            ps.setString(1, s.getNazwa());
            dodano = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println("error " + e);
        } finally {
            ps.close();
            con.close();
        }
        return dodano;
    }

    public int addStanowiskoPodlegle(String nazwa) throws SQLException {
        int dodano = 0;
        try {
            getConnection();
            ps = con.prepareStatement("insert into stanowisko_podlegle(nazwa) values (?)");
            ps.setString(1, nazwa);
            dodano = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println("error " + e);
        } finally {
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

        return stanowiska;
    }

    public List<Stanowisko_podlegle> getAllStanowiskoPodlegleList() {
        List<Stanowisko_podlegle> stanowiska = new ArrayList<>();
        try {
            getConnection();
            rs = st.executeQuery("select * from stanowisko_podlegle");
            while (rs.next()) {
                Stanowisko_podlegle s = new Stanowisko_podlegle(
                        rs.getInt("id_stanowisko_podlegle"),
                        rs.getString("nazwa")
                );
                stanowiska.add(s);
            }

        } catch (Exception e) {
            System.out.println("bład: " + e);
        }

        return stanowiska;
    }

    public int getStanowiskoIdByNazwa(String nazwa) {
        int id = 0;
        try {
            getConnection();
            rs = st.executeQuery("select id_stanowisko from stanowisko where nazwa='" + nazwa + "'");
            while (rs.next()) {
                id = rs.getInt("id_stanowisko");
            }

        } catch (Exception e) {
            System.err.println("bladGetUserPrivilage " + e);
        }
        return id;
    }

    public String getStanowiskoNazwaById(int id) {
        String nazwa = "";
        try {
            getConnection();
            rs = st.executeQuery("select nazwa from stanowisko where id_stanowisko='" + id + "'");
            while (rs.next()) {
                nazwa = rs.getString("nazwa");
            }

        } catch (Exception e) {
            System.err.println("bladGetUserPrivilage " + e);
        }
        return nazwa;
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
                    + "data_przyjecia,"
                    + "data_konca_umowy"
                    + ") values(?,?,?,?,?,?,?,?,?,?,?)");
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
            ps.setLong(11, p.getData_konca_umowy());
            dodano = ps.executeUpdate();

            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("błąd: " + e);
        }
        return dodano;
    }

    public void deleteStanowiskoPodlegleFromStanowisko(int id_pod, int id_st) {
        try {
            getConnection();
            ps = con.prepareStatement("delete from stanowisko_has_stanowisko_podlegle where "
                    + "stanowisko_id_stanowisko=? and "
                    + "stanowisko_podlegle_id_stanowisko_podlegle=?");
            ps.setInt(1, id_st);
            ps.setInt(2, id_pod);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
        }
    }

    public void deleteFromStanowiskoHasPodlegle(int id) {
        try {
            getConnection();
            ps = con.prepareStatement("delete from stanowisko_has_stanowisko_podlegle where "
                    + "stanowisko_id_stanowisko=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void deleteStanowiskoById(int id) {
        deleteFromStanowiskoHasPodlegle(id);
        try {
            getConnection();
            ps = con.prepareStatement("delete from stanowisko where "
                    + "id_stanowisko=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void deleteFromHistoriaStanowiska(int id) {
        try {
            getConnection();
            ps = con.prepareStatement("delete from historia_stanowiska where "
                    + "pracownik_id_pracownik=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void deleteFromPracownikById(int id) {
        deleteFromHistoriaStanowiska(id);
        try {
            getConnection();
            ps = con.prepareStatement("delete from pracownik where "
                    + "id_pracownik=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    void deleteFromDoZatwierdzenia(int id) {
        try {
            getConnection();
            ps = con.prepareStatement("delete from do_zatwierdzenia where id_do_zatwierdzenia=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    /* int		stanowisko_id_stanowisko,czy_studiuje,pensja
     * String	imie,nazwisko,plec,tytul,pesel
     * Long		data_przyjecia,data_konca_umowy
     */

    public void updatePracownik(Do_zatwierdzenia dz) {
        try {
            String nazwa = null;
            long data;
            int pensja;
            String wartosc = dz.getWartosc_do_zmiany();
            switch (dz.getNazwa_pola_do_zmiany()) {
                case "imie":
                    nazwa = "imie";
                    break;
                case "nazwisko":
                    nazwa = "nazwisko";
                    break;
                case "plec":
                    nazwa = "plec";
                    break;
                case "data_urodzenia":
                    nazwa = "data_urodzenia";
                    data = getDateInMilisecFromString(wartosc);
                    wartosc = Long.toString(data);
                    break;
                case "tytul":
                    nazwa = "tytul";
                    break;
                case "pesel":
                    nazwa = "pesel";
                    break;
                case "czy_studiuje":
                    nazwa = "czy_studiuje";
                    if (wartosc.equals("Tak")) {
                        wartosc = "0";
                    } else {
                        wartosc = "1";
                    }
                    break;
                case "pensja":
                    nazwa = "pensja";
                    pensja = Integer.valueOf(wartosc);
                    wartosc = Integer.toString(pensja);
                    break;
                case "data_przyjecia":
                    nazwa = "data_przyjecia";
                    data = getDateInMilisecFromString(wartosc);
                    wartosc = Long.toString(data);
                    break;
                case "data_konca_umowy":
                    nazwa = "data_konca_umowy";
                    data = getDateInMilisecFromString(wartosc);
                    wartosc = Long.toString(data);
                    break;
                default:
                    nazwa = " ";
            }
            try {
                getConnection();
                ps = con.prepareStatement("update pracownik set " + nazwa + "='" + wartosc + "' where id_pracownik=" + dz.getId_pracownika());
                ps.executeUpdate();

                ps.close();
                con.close();
            } catch (Exception e) {
                System.out.println("684błąd:" + e);
            }
        } catch (ParseException ex) {
            Logger.getLogger(KadrManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getUzytkownikIdByLogin(String zalogowano_jako) {
        int id = 0;
        try {
            getConnection();
            rs = st.executeQuery("select id_uzytkownik from uzytkownik where login='" + zalogowano_jako + "'");
            while (rs.next()) {
                id = rs.getInt("id_uzytkownik");
            }
        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return id;
    }

    public void updateDoZatwierdzenia(int id_uzytkownik, Do_zatwierdzenia dz) {

        try {
            getConnection();
            ps = con.prepareStatement("update do_zatwierdzenia set id_uzytkownik_kierownik='" + id_uzytkownik + "' ,"
                    + "zatwierdzone = 0 where id_do_zatwierdzenia=" + dz.getId_do_zatwierdzenia());
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println("bład " + e);
        }

    }

    public FillTable getHistoriaZmianZatwierdzaniaTable() {
        try {
            getConnection();
            rs = st.executeQuery("select id_do_zatwierdzenia as id, "
                    + "u.login as 'kto zatwierdzil', "
                    + "CONCAT(p.imie, ' ', p.nazwisko) as 'dane pracownia',  "
                    + "nazwa_pola_do_zmiany as pole, "
                    + "wartosc_do_zmiany as wartosc  "
                    + "from do_zatwierdzenia d, pracownik p, uzytkownik u "
                    + "where d.id_pracownika = p.id_pracownik "
                    + "and d.uzytkownik_id_uzytkownik = u.id_uzytkownik  "
                    + "and d.zatwierdzone like 0");

            this.model = new FillTable(rs);

        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return model;
    }

    int addHistoriaStanowiska(Historia h) {
        int dodano = 0;
        try {
            getConnection();
            ps = con.prepareStatement("insert into historia_stanowiska("
                    + "pracownik_id_pracownik,"
                    + "data_rozpoczecia,"
                    + "data_zakonczenia,"
                    + "nazwa) values(?,?,?,?)");
            ps.setInt(1, h.getId_pracownik());
            ps.setLong(2, h.getData_rozpoczęcia());
            ps.setLong(3, h.getData_zakończenia());
            ps.setString(4, h.getNazwa());
            dodano = ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println("błądDodawanie histSrano: " + e);
        }
        return dodano;
    }

    TableModel getPracownikHistStanoDataTableWithQuery(String text) {
        try {
            getConnection();
            rs = st.executeQuery("select h.id_historia_stanowiska as id, "
                    + "CONCAT(p.imie, ' ',p.nazwisko) as pracownik, "
                    + "h.nazwa as nazwa from historia_stanowiska h, pracownik p "
                    + "where h.pracownik_id_pracownik = p.id_pracownik and p.nazwisko like '" + text + "'");
            this.model = new FillTable(rs);
        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return model;
    }

    Historia getHistoriaStanowiskPracownikById(int selectedId) {
        Historia h = null;
        try {
            getConnection();
            rs = st.executeQuery("select * from historia_stanowiska where id_historia_stanowiska='" + selectedId + "'");
            while (rs.next()) {
                h = new Historia(
                        selectedId,
                        rs.getInt("pracownik_id_pracownik"),
                        rs.getLong("data_rozpoczecia"),
                        rs.getLong("data_zakonczenia"),
                        rs.getString("nazwa")
                );
            }
        } catch (Exception e) {
            System.out.println("bład " + e);
        }
        return h;

    }

    void deleteFromHistoriaStanowiskaById(int selectedId) {
        try {
            getConnection();
            ps = con.prepareStatement("delete from historia_stanowiska where "
                    + "id_historia_stanowiska=?");
            ps.setInt(1, selectedId);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    void updateHistoriaStanowiskNazwa(String text, int id) {
        try {
            getConnection();
            ps = con.prepareStatement("update historia_stanowiska set nazwa='" + text + "' where id_historia_stanowiska=" + id);
            ps.executeUpdate();

            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println("updateHistoriaStanowiskN błąd:" + e);
        }
    }

    void updateHistoriaStanowiskData(String nazwaPola, long data, int id) {
        String nazwa = null;
        switch (nazwaPola) {
            case "data_rozpoczecia":
                nazwa = nazwaPola;
                break;
            default:
                nazwa = nazwaPola;
        }
        try {
            getConnection();
            ps = con.prepareStatement("update historia_stanowiska set " + nazwa + "='" + data + "' where id_historia_stanowiska=" + id);
            ps.executeUpdate();

            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println("updateHistoriaStanowiskD błąd:" + e);
        }
    }

    void deleteFromUzytkownik(int id) {
        try {
            getConnection();
            ps = con.prepareStatement("delete from uzytkownik where id_uzytkownik=" + id);
            ps.executeUpdate();

            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println("deleteUzytkownik błąd:" + e);
        }
    }

    void updateUzytkownik(String nazwaPola, String text, int id) {
        String nazwa = null;
        switch (nazwaPola) {
            case "login":
                nazwa = nazwaPola;
                break;
            default:
                nazwa = nazwaPola;
        }
        try {
            getConnection();
            ps = con.prepareStatement("update uzytkownik set " + nazwa + "='" + text + "' where id_uzytkownik=" + id);
            ps.executeUpdate();

            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println("updateUzytkownik błąd:" + e);
        }
    }

    void addDoZatwierdzeniaString(String nazwaPola, String text, int id, int uzytkownikId) {
        String nazwa;
        switch (nazwaPola) {
            case "imie":
                nazwa = nazwaPola;
                break;
            case "nazwisko":
                nazwa = nazwaPola;
                break;
            case "pesel":
                nazwa = nazwaPola;
                break;
            case "plec":
                nazwa = nazwaPola;
                break;
            case "data_urodzenia":
                nazwa = nazwaPola;
                break;
            case "data_przyjecia":
                nazwa = nazwaPola;
                break;
            case "czy_studiuje":
                nazwa = nazwaPola;
                break;
            case "pensja":
                nazwa = nazwaPola;
                break;
            default:
                nazwa = nazwaPola;
        }
        try {
            getConnection();
            ps = con.prepareStatement("insert into do_zatwierdzenia"
                    + "(uzytkownik_id_uzytkownik, "
                    + "id_pracownika, "
                    + "nazwa_pola_do_zmiany, "
                    + "wartosc_do_zmiany, "
                    + "zatwierdzone) "
                    + "values(?,?,?,?,?)");
            ps.setInt(1, uzytkownikId);
            ps.setInt(2, id);
            ps.setString(3, nazwa);
            ps.setString(4, text);
            ps.setInt(5, 1);
            ps.executeUpdate();

            ps.close();
            con.close();
            System.out.println("akuku");
        } catch (Exception e) {
            System.out.println("addToDoZatwierdzenia błąd:" + e);
        }
    }

}
