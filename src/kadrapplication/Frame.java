/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 TODO:
 [ ] edycja uzytkownikow
 [ ] edycja historii uzytkownikow
 [x] dodawanie historii uzytkownikow
 [x] usuwanie historii uzytkownikow
 [ ] edycja pracownikow
 [ ] jezyk polski w db
 */
package kadrapplication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author qqq
 */
public class Frame extends JFrame {

    private ArrayList<JPanel> tabList = new ArrayList<>();
    KadrManager km = new KadrManager();
    private String privilage = "niezalogowany";
    private String zalogowano_jako = null;

    public Frame() {
        super("Start");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            System.out.println("look&feel: " + e);
        }
        setResizable(false);
        setLocationByPlatform(true);
        initComponents();

        initTabbedPaneList();

        //km.getPracownikData();
    }

    private void tabActionZatwierdzanie() {
        hideTabs();
        showTabAtIndex(jPanelZatwierdzanie, 0);
        showTabAtIndex(jPanelHistoriaZatwierdzonychZmian, 1);
        jButtonZatwierdzanieZmianZatwierdz.setEnabled(false);
        jButtonZatwierdzanieZmianOdrzuc.setEnabled(false);
    }

    private void tabActionLogowanie() {
        hideTabs();
        showTabAtIndex(jPanelLogowanie, 0);
        jLabelWarningLogin.setVisible(false);
        jLabelWarningHaslo.setVisible(false);
        jLabelLoginError.setVisible(false);
        jLabelZalogowanoJako.setText(this.privilage);
    }

    private void tabActionNowy() {
        hideTabs();
        showTabAtIndex(jPanelNowyUzytkownik, 0);
        showTabAtIndex(jPanelNowyPracownik, 1);
        showTabAtIndex(jPanelNoweStanowisko, 2);

        jLabelNowyUzytkownikError.setVisible(false);
        jLabelNowyUzytkownikDodano.setVisible(false);

        jLabelNoweStanowiskoError.setVisible(false);
        jLabelNoweStanowiskoDodano.setVisible(false);

        jLabelNowyPracownikDodano.setVisible(false);
        jLabelNowyPracownikkError.setVisible(false);
        jCheckBoxNowyPracownikStudent.setSelected(false);
    }

    private void tabActionKartoteka() {
        hideTabs();
        showTabAtIndex(jPanelPrzegladaniePracownikow, 0);
        showTabAtIndex(jPanelPrzegladanieUzytkownikow, 1);
        showTabAtIndex(jPanelPrzegladanieStanowisk, 2);
        jLabelPrzegladanieStanowiskError.setVisible(false);
        jButtonRaporty.setVisible(false);
        jButtonPrzPracownikUsun.setEnabled(false);
        jButtonPrzPracownikEdytujZatwierdz.setEnabled(false);
    }

    private void tabActionZarzadzanie() {
        hideTabs();
        showTabAtIndex(jPanelEdycjaStanowiskPodleglych, 0);
        showTabAtIndex(jPanelEdycjahistoriiStanowiskPracownika, 1);
        showTabAtIndex(jPanelDodawanieHistoriiStanowisk, 2);
        jLabelEdStPodlErrorDodaj.setVisible(false);
        jLabelEdStPodlErrorUsun.setVisible(false);
        jLabelDodawanieHistStanDodano.setVisible(false);
        jLabelDodawanieHistStanError.setVisible(false);
    }

    private void initTabbedPaneList() {
        tabList.clear();
        fillTabList();
        tabActionLogowanie();
    }

    private void showTabAtIndex(JPanel jP, int index) {
        jTabbedPaneMain.add(jP, index);
        jTabbedPaneMain.setTitleAt(index, jP.getName());
    }

    private void fillTabList() {
        tabList.add(jPanelLogowanie);
        tabList.add(jPanelNowyUzytkownik);
        tabList.add(jPanelNowyPracownik);
        tabList.add(jPanelNowyUzytkownik);
        tabList.add(jPanelNoweStanowisko);
        tabList.add(jPanelPrzegladanieUzytkownikow);
        tabList.add(jPanelPrzegladaniePracownikow);
        tabList.add(jPanelPrzegladanieStanowisk);
        tabList.add(jPanelEdycjaStanowiskPodleglych);
        tabList.add(jPanelEdycjahistoriiStanowiskPracownika);
        tabList.add(jPanelRaporty);
        tabList.add(jPanelZatwierdzanie);
        tabList.add(jPanelHistoriaZatwierdzonychZmian);
        tabList.add(jPanelDodawanieHistoriiStanowisk);
        tabList.add(jPanel15);
    }

    private void hideTabs() {
        for (JPanel tab : tabList) {
            jTabbedPaneMain.remove(tab);
        }
    }

    public DefaultComboBoxModel cmbUprawnieniaModel() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement("administrator");
        model.addElement("pracownik_kadr");
        model.addElement(("kierownik_kadr"));
        return model;
    }

    public DefaultComboBoxModel cmbRaportListType() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        if (!this.privilage.equals("pracownik_kadr")) {
            model.addElement("Raport - dane pracownika");
            model.addElement("Raport - historia stanowisk");
            model.addElement("Raport - zaświadczenie o zarobkach");
        } else {
            model.addElement("Raport - historia stanowisk");
            model.addElement("Raport - zaświadczenie o zarobkach");
        }
        return model;
    }

    public DefaultComboBoxModel cmbPracownikPlec() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement("Mezczyzna");
        model.addElement("Kobieta");
        return model;
    }

    public DefaultComboBoxModel cmbPracownikStanowisko() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        try {
            List<Stanowisko> stanowiska = new ArrayList<>(km.getAllStanowiskoList());
            for (Stanowisko s : stanowiska) {
                model.addElement(s.getNazwa());
            }
        } catch (Exception e) {
        }
        return model;
    }

    public DefaultComboBoxModel cmbStanowiskoPodlegle() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        try {
            List<Stanowisko_podlegle> stanowiska = new ArrayList<>(km.getAllStanowiskoPodlegleList());
            for (Stanowisko_podlegle s : stanowiska) {
                model.addElement(s.getNazwa());
            }
        } catch (Exception e) {
        }
        return model;
    }

    public int getIndexOfCmbStanowisko(String nazwa) {
        List<Stanowisko> stanowiska = new ArrayList<>(km.getAllStanowiskoList());
        int j = 0;
        for (int i = 0; i < stanowiska.size(); i++) {
            if (nazwa.equalsIgnoreCase(stanowiska.get(i).getNazwa())) {
                j = i;
                break;
            } else {
                j = 0;
            }
        }
        return j;
    }

    public DefaultComboBoxModel cmbPracownikSzukaj() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement("Stanowisko");
        model.addElement("Nazwisko");
        model.addElement("Pesel");
        return model;
    }

    public long generateDateInMiliseconds() {
        Date d = new Date();
        long milliseconds = d.getTime();
        return milliseconds;
    }

    public Date changeLongToDate(long milliseconds) {
        return new Date(milliseconds);
    }

    public String dateLongToString(long mili) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(mili);
        String s = formatter.format(date);
        return s;
    }

    public long getDateInMilisecFromString(String s) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(s);
        long mili = date.getTime();
        return mili;
    }

    public int getStanowiskoId(String nazwa) {
        return km.getStanowiskoIdByNazwa(nazwa);
    }

    public void disablePracownikTextFields(boolean b) {
        jTextFieldPrzPracwonikImie.setEditable(b);
        jTextFieldPrzPracownikNazwisko.setEditable(b);
        jTextFieldPrzPracownikDataPrzyjecia.setEditable(b);
        jTextFieldPrzPracownikDataUrodzenia.setEditable(b);
        jTextFieldPrzPracownikPesel.setEditable(b);
        jTextFieldPrzPracownikTytul.setEditable(b);
        jComboBoxPrzPracownikPlec.setEnabled(b);
        jComboBoxPrzPracownikStanowisko.setEnabled(b);
        jCheckBoxPrzPracownikStudent.setEnabled(b);
        jTextFieldPrzPracownikPensja.setEditable(b);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonNowy = new javax.swing.JButton();
        jButtonZatwierdzanie = new javax.swing.JButton();
        jButtonKartoteka = new javax.swing.JButton();
        jButtonZarzadzanie = new javax.swing.JButton();
        jButtonLogowanie = new javax.swing.JButton();
        tlo = new javax.swing.JPanel();
        jTabbedPaneMain = new javax.swing.JTabbedPane();
        jPanelLogowanie = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextLogin = new javax.swing.JTextField();
        jButtonLogin = new javax.swing.JButton();
        jLabelWarningLogin = new javax.swing.JLabel();
        jLabelWarningHaslo = new javax.swing.JLabel();
        jLabelLoginError = new javax.swing.JLabel();
        jTextHaslo = new javax.swing.JPasswordField();
        jPanelNowyUzytkownik = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldNowyUzytkownikLoginAdd = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldNowyUzytkownikHasloAdd = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButtonNowyUzytkownikDodaj = new javax.swing.JButton();
        jLabelNowyUzytkownikError = new javax.swing.JLabel();
        jLabelNowyUzytkownikDodano = new javax.swing.JLabel();
        jComboBoxUprawnieniaAdd = new javax.swing.JComboBox();
        jPanelNoweStanowisko = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldNoweStanowiskoNazwa = new javax.swing.JTextField();
        jButtonNoweStanowiskoDodaj = new javax.swing.JButton();
        jLabelNoweStanowiskoDodano = new javax.swing.JLabel();
        jLabelNoweStanowiskoError = new javax.swing.JLabel();
        jPanelNowyPracownik = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldNowyPracownikImie = new javax.swing.JTextField();
        jTextFieldNowyPracownikNazwisko = new javax.swing.JTextField();
        jTextFieldNowyPracownikPensja = new javax.swing.JTextField();
        jTextFieldNowyPracownikDataUrodzenia = new javax.swing.JTextField();
        jTextFieldNowyPracownikTytul = new javax.swing.JTextField();
        jTextFieldNowyPracownikPesel = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jComboBoxNowyPracownikStanowisko = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jComboBoxNowyPracownikPlec = new javax.swing.JComboBox();
        jCheckBoxNowyPracownikStudent = new javax.swing.JCheckBox();
        jLabelNowyPracownikkError = new javax.swing.JLabel();
        jLabelNowyPracownikDodano = new javax.swing.JLabel();
        jButtonNowyPracownikDodaj = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTextFieldNowyPracownikDataKoncaUmowy = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jPanelPrzegladanieUzytkownikow = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTablePrzUzytkownikow = new javax.swing.JTable();
        jTextFieldPrzUzytkLogin = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTextFieldPrzUzytkUprawnienia = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTextFieldPrzUzytDataUtworzenia = new javax.swing.JTextField();
        jTextFieldPrzUzytHaslo = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanelPrzegladaniePracownikow = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablePrzPracownik = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldPrzPracownikSzukaj = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTextFieldPrzPracwonikImie = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTextFieldPrzPracownikNazwisko = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jTextFieldPrzPracownikDataUrodzenia = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jTextFieldPrzPracownikTytul = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jTextFieldPrzPracownikPesel = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jTextFieldPrzPracownikPensja = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jTextFieldPrzPracownikDataPrzyjecia = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListPrzPracownikPodlegleList = new javax.swing.JList();
        jLabelPrzPracownikPodlegleLabel = new javax.swing.JLabel();
        jComboBoxPrzPracownikPlec = new javax.swing.JComboBox();
        jComboBoxPrzPracownikStanowisko = new javax.swing.JComboBox();
        jCheckBoxPrzPracownikStudent = new javax.swing.JCheckBox();
        jButtonPrzPracownikSzukaj = new javax.swing.JButton();
        jComboBoxPracownikSzukaj = new javax.swing.JComboBox();
        jButtonRaporty = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        jButtonPrzPracownikEdytujZatwierdz = new javax.swing.JButton();
        jButtonPrzPracownikUsun = new javax.swing.JButton();
        jTextFieldPrzPracoKoniecUmowy = new javax.swing.JTextField();
        jPanelPrzegladanieStanowisk = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTablePrzStanowisko = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        jListPrzStanowiskStanowiskaPodlegle = new javax.swing.JList();
        jLabel32 = new javax.swing.JLabel();
        jButtonPrzegladanieStanowiskUsun = new javax.swing.JButton();
        jLabelPrzegladanieStanowiskError = new javax.swing.JLabel();
        jPanelEdycjaStanowiskPodleglych = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jListEdStPodlegStanowiskaPodlegle = new javax.swing.JList();
        jScrollPane10 = new javax.swing.JScrollPane();
        jListEdStPodleglychStanowiska = new javax.swing.JList();
        jLabel35 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jButtonEdStPodlDodaj = new javax.swing.JButton();
        jButtonEdStPodlUsun = new javax.swing.JButton();
        jLabelEdStPodlErrorDodaj = new javax.swing.JLabel();
        jLabelEdStPodlErrorUsun = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jTextFieldEdStPodlNazwa = new javax.swing.JTextField();
        jComboBoxEdStPodlNazwa = new javax.swing.JComboBox();
        jPanelEdycjahistoriiStanowiskPracownika = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTableEdycjaHistStan = new javax.swing.JTable();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jTextFieldEdycjaHistStanSzukaj = new javax.swing.JTextField();
        jButtonEdycjaHistStankSzukaj = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        jTextFieldEdycjaHistStanNazwa = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        jTextFieldEdycjaHistStanDataRozpoczecia = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jTextFieldEdycjaHistStanDataZakonczenia = new javax.swing.JTextField();
        jButtonEdycjaHistStanEdytuj = new javax.swing.JButton();
        jButtonEdycjaHistStanUsun = new javax.swing.JButton();
        jPanelZatwierdzanie = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTableZatwierdzanie = new javax.swing.JTable();
        jButtonZatwierdzanieZmianZatwierdz = new javax.swing.JButton();
        jButtonZatwierdzanieZmianOdrzuc = new javax.swing.JButton();
        jPanelHistoriaZatwierdzonychZmian = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTableHistoriaZatwierdzonychZmian = new javax.swing.JTable();
        jPanelDodawanieHistoriiStanowisk = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jTextFieldDodawanieHistStanNazwa = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jTextFieldDodawanieHistStanDataRozpoczecia = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jTextFieldDodawanieHistStanDataZakonczenia = new javax.swing.JTextField();
        jButtonDodawanieHistStanDodaj = new javax.swing.JButton();
        jLabelDodawanieHistStanDodano = new javax.swing.JLabel();
        jTextFieldDodawanieHistStanImieINazw = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        jButtonDodawanieHistStanWybierzPrac = new javax.swing.JButton();
        jLabelDodawanieHistStanError = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanelRaporty = new javax.swing.JPanel();
        jComboBoxRaportType = new javax.swing.JComboBox();
        jLabel40 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jTextFieldRaportImieINazwisko = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jTextFieldRaportDataUrodzenia = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jTextFieldRaportPesel = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jTextFieldRaportStudent = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jTextFieldRaportDataZatrudnienia = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jTextFieldRaportDataKoncaUmowy = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jTextFieldRaportPensja = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jTextFieldRaportStanowisko = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jTextFieldRaportPensjaSrednia = new javax.swing.JTextField();
        jButtonRaportDrukuj = new javax.swing.JButton();
        jLabel52 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabelRaportData = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelZalogowanoJako = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Zakład podukujący wędliny \"Podlasie\" - Aplikacja kadrowa");
        setMaximumSize(new java.awt.Dimension(800, 400));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonNowy.setText("Nowy");
        jButtonNowy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNowyActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonNowy, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 110, 25));

        jButtonZatwierdzanie.setText("Zatwierdzanie");
        jButtonZatwierdzanie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonZatwierdzanieActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonZatwierdzanie, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 110, 25));

        jButtonKartoteka.setText("Kartoteka");
        jButtonKartoteka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonKartotekaActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonKartoteka, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 110, 25));

        jButtonZarzadzanie.setText("Zarządzanie");
        jButtonZarzadzanie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonZarzadzanieActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonZarzadzanie, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 110, 25));

        jButtonLogowanie.setText("Logowanie");
        jButtonLogowanie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogowanieActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonLogowanie, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 363, 110, 25));

        tlo.setMaximumSize(new java.awt.Dimension(600, 350));
        tlo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPaneMain.setMaximumSize(new java.awt.Dimension(700, 380));
        jTabbedPaneMain.setPreferredSize(new java.awt.Dimension(600, 382));
        jTabbedPaneMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPaneMainMouseClicked(evt);
            }
        });

        jPanelLogowanie.setName("Logowanie"); // NOI18N

        jLabel1.setText("Login");

        jLabel2.setText("Hasło");

        jButtonLogin.setText("Zaloguj");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        jLabelWarningLogin.setForeground(new java.awt.Color(204, 0, 0));
        jLabelWarningLogin.setText("pole nie może być puste!");
        jLabelWarningLogin.setToolTipText("");

        jLabelWarningHaslo.setForeground(new java.awt.Color(204, 0, 0));
        jLabelWarningHaslo.setText("pole nie może być puste!");

        jLabelLoginError.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelLoginError.setForeground(new java.awt.Color(204, 0, 0));
        jLabelLoginError.setText("Nieprawidłowy login lub hasło.");

        javax.swing.GroupLayout jPanelLogowanieLayout = new javax.swing.GroupLayout(jPanelLogowanie);
        jPanelLogowanie.setLayout(jPanelLogowanieLayout);
        jPanelLogowanieLayout.setHorizontalGroup(
            jPanelLogowanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLogowanieLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanelLogowanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanelLogowanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelLoginError)
                    .addGroup(jPanelLogowanieLayout.createSequentialGroup()
                        .addComponent(jTextLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelWarningLogin))
                    .addGroup(jPanelLogowanieLayout.createSequentialGroup()
                        .addComponent(jTextHaslo, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelWarningHaslo))
                    .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(298, Short.MAX_VALUE))
        );
        jPanelLogowanieLayout.setVerticalGroup(
            jPanelLogowanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLogowanieLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanelLogowanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelWarningLogin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLogowanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextHaslo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelLogowanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabelWarningHaslo)))
                .addGap(18, 18, 18)
                .addComponent(jButtonLogin)
                .addGap(18, 18, 18)
                .addComponent(jLabelLoginError)
                .addContainerGap(148, Short.MAX_VALUE))
        );

        jTabbedPaneMain.addTab("Logowanie", jPanelLogowanie);

        jPanelNowyUzytkownik.setName("Nowy użytkownik"); // NOI18N

        jLabel5.setText("Login");

        jLabel6.setText("Hasło");

        jLabel4.setText("Uprawnienia");

        jButtonNowyUzytkownikDodaj.setText("Dodaj użytkownika");
        jButtonNowyUzytkownikDodaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNowyUzytkownikDodajActionPerformed(evt);
            }
        });

        jLabelNowyUzytkownikError.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelNowyUzytkownikError.setForeground(new java.awt.Color(204, 0, 0));
        jLabelNowyUzytkownikError.setText("Pola z czerwonym tłem muszą zostać wypełnione!");

        jLabelNowyUzytkownikDodano.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelNowyUzytkownikDodano.setForeground(new java.awt.Color(0, 153, 0));
        jLabelNowyUzytkownikDodano.setText("Dodano pomyślnie.");

        jComboBoxUprawnieniaAdd.setModel(cmbUprawnieniaModel());

        javax.swing.GroupLayout jPanelNowyUzytkownikLayout = new javax.swing.GroupLayout(jPanelNowyUzytkownik);
        jPanelNowyUzytkownik.setLayout(jPanelNowyUzytkownikLayout);
        jPanelNowyUzytkownikLayout.setHorizontalGroup(
            jPanelNowyUzytkownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNowyUzytkownikLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelNowyUzytkownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4))
                .addGap(50, 50, 50)
                .addGroup(jPanelNowyUzytkownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonNowyUzytkownikDodaj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldNowyUzytkownikLoginAdd)
                    .addComponent(jTextFieldNowyUzytkownikHasloAdd)
                    .addComponent(jComboBoxUprawnieniaAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelNowyUzytkownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelNowyUzytkownikLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabelNowyUzytkownikError))
                    .addGroup(jPanelNowyUzytkownikLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelNowyUzytkownikDodano)))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanelNowyUzytkownikLayout.setVerticalGroup(
            jPanelNowyUzytkownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNowyUzytkownikLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanelNowyUzytkownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxUprawnieniaAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelNowyUzytkownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNowyUzytkownikLoginAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNowyUzytkownikError)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelNowyUzytkownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldNowyUzytkownikHasloAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanelNowyUzytkownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNowyUzytkownikDodaj, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNowyUzytkownikDodano))
                .addGap(175, 175, 175))
        );

        jTabbedPaneMain.addTab("Nowy użytkownik", jPanelNowyUzytkownik);

        jPanelNoweStanowisko.setName("Nowe stanowisko"); // NOI18N

        jLabel16.setText("Nazwa");

        jTextFieldNoweStanowiskoNazwa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNoweStanowiskoNazwaActionPerformed(evt);
            }
        });

        jButtonNoweStanowiskoDodaj.setText("Dodaj stanowisko");
        jButtonNoweStanowiskoDodaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNoweStanowiskoDodajActionPerformed(evt);
            }
        });

        jLabelNoweStanowiskoDodano.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelNoweStanowiskoDodano.setForeground(new java.awt.Color(0, 153, 0));
        jLabelNoweStanowiskoDodano.setText("Dodano pomyślnie.");

        jLabelNoweStanowiskoError.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelNoweStanowiskoError.setForeground(new java.awt.Color(204, 0, 0));
        jLabelNoweStanowiskoError.setText("Pole musi być wypełnione!");

        javax.swing.GroupLayout jPanelNoweStanowiskoLayout = new javax.swing.GroupLayout(jPanelNoweStanowisko);
        jPanelNoweStanowisko.setLayout(jPanelNoweStanowiskoLayout);
        jPanelNoweStanowiskoLayout.setHorizontalGroup(
            jPanelNoweStanowiskoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNoweStanowiskoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(37, 37, 37)
                .addGroup(jPanelNoweStanowiskoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelNoweStanowiskoLayout.createSequentialGroup()
                        .addComponent(jButtonNoweStanowiskoDodaj, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelNoweStanowiskoDodano))
                    .addGroup(jPanelNoweStanowiskoLayout.createSequentialGroup()
                        .addComponent(jTextFieldNoweStanowiskoNazwa, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelNoweStanowiskoError)))
                .addContainerGap(287, Short.MAX_VALUE))
        );
        jPanelNoweStanowiskoLayout.setVerticalGroup(
            jPanelNoweStanowiskoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNoweStanowiskoLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanelNoweStanowiskoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextFieldNoweStanowiskoNazwa, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNoweStanowiskoError))
                .addGap(47, 47, 47)
                .addGroup(jPanelNoweStanowiskoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNoweStanowiskoDodaj, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNoweStanowiskoDodano))
                .addContainerGap(191, Short.MAX_VALUE))
        );

        jTabbedPaneMain.addTab("Nowe stanowisko", jPanelNoweStanowisko);

        jPanelNowyPracownik.setName("Nowy pracownik"); // NOI18N

        jLabel7.setText("Imię");

        jLabel8.setText("Nazwisko");

        jLabel9.setText("Pensja");

        jLabel10.setText("Data urodzenia");

        jLabel11.setText("Tytuł");

        jLabel12.setText("Pesel");

        jLabel13.setText("Student");

        jTextFieldNowyPracownikImie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNowyPracownikImieActionPerformed(evt);
            }
        });

        jLabel14.setText("Stanowisko");

        jComboBoxNowyPracownikStanowisko.setModel(cmbPracownikStanowisko());

        jLabel15.setText("Płeć");

        jComboBoxNowyPracownikPlec.setModel(cmbPracownikPlec());

        jCheckBoxNowyPracownikStudent.setText("Tak");

        jLabelNowyPracownikkError.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelNowyPracownikkError.setForeground(new java.awt.Color(204, 0, 0));
        jLabelNowyPracownikkError.setText("Pola z czerwonym tłem muszą zostać wypełnione!");

        jLabelNowyPracownikDodano.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelNowyPracownikDodano.setForeground(new java.awt.Color(0, 153, 0));
        jLabelNowyPracownikDodano.setText("Dodano pomyślnie.");

        jButtonNowyPracownikDodaj.setText("Dodaj użytkownika");
        jButtonNowyPracownikDodaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNowyPracownikDodajActionPerformed(evt);
            }
        });

        jLabel17.setForeground(new java.awt.Color(153, 153, 153));
        jLabel17.setText("format: dd/mm/rrrr");

        jLabel20.setText("Koniec umowy");

        jLabel21.setForeground(new java.awt.Color(153, 153, 153));
        jLabel21.setText("format: dd/mm/rrrr");

        javax.swing.GroupLayout jPanelNowyPracownikLayout = new javax.swing.GroupLayout(jPanelNowyPracownik);
        jPanelNowyPracownik.setLayout(jPanelNowyPracownikLayout);
        jPanelNowyPracownikLayout.setHorizontalGroup(
            jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldNowyPracownikDataKoncaUmowy, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                            .addComponent(jLabel12)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldNowyPracownikPesel, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldNowyPracownikTytul, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldNowyPracownikDataUrodzenia, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(67, 67, 67)
                            .addComponent(jTextFieldNowyPracownikImie, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelNowyPracownikLayout.createSequentialGroup()
                            .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8)
                                .addComponent(jLabel15))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextFieldNowyPracownikNazwisko, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                                .addComponent(jComboBoxNowyPracownikPlec, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelNowyPracownikLayout.createSequentialGroup()
                            .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9)
                                .addComponent(jLabel13))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jCheckBoxNowyPracownikStudent)
                                .addComponent(jTextFieldNowyPracownikPensja, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(43, 43, 43)
                                .addComponent(jComboBoxNowyPracownikStanowisko, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelNowyPracownikkError)
                            .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                                .addComponent(jButtonNowyPracownikDodaj)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelNowyPracownikDodano))))
                    .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17))
                    .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21)))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        jPanelNowyPracownikLayout.setVerticalGroup(
            jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNowyPracownikImie, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel14)
                    .addComponent(jComboBoxNowyPracownikStanowisko, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldNowyPracownikNazwisko, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jComboBoxNowyPracownikPlec, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNowyPracownikDataUrodzenia, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextFieldNowyPracownikTytul, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNowyPracownikPesel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabelNowyPracownikkError))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jCheckBoxNowyPracownikStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNowyPracownikPensja, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jButtonNowyPracownikDodaj, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNowyPracownikDodano))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNowyPracownikDataKoncaUmowy, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPaneMain.addTab("Nowy pracownik", jPanelNowyPracownik);

        jPanelPrzegladanieUzytkownikow.setName("Przeglądanie użytkowników"); // NOI18N

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jTablePrzUzytkownikow.setModel(km.getUzytkownikDataTable());
        jTablePrzUzytkownikow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePrzUzytkownikowMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTablePrzUzytkownikow);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane4.setViewportView(jPanel2);

        jTextFieldPrzUzytkLogin.setEditable(false);

        jLabel33.setText("Uprawnienia");

        jTextFieldPrzUzytkUprawnienia.setEditable(false);

        jLabel34.setText("Data utworzenia");

        jTextFieldPrzUzytDataUtworzenia.setEditable(false);

        jTextFieldPrzUzytHaslo.setEditable(false);
        jTextFieldPrzUzytHaslo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPrzUzytHasloActionPerformed(evt);
            }
        });

        jLabel36.setText("Login");

        jLabel37.setText("Hasło");

        jButton1.setText("Edytuj");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Usuń");

        javax.swing.GroupLayout jPanelPrzegladanieUzytkownikowLayout = new javax.swing.GroupLayout(jPanelPrzegladanieUzytkownikow);
        jPanelPrzegladanieUzytkownikow.setLayout(jPanelPrzegladanieUzytkownikowLayout);
        jPanelPrzegladanieUzytkownikowLayout.setHorizontalGroup(
            jPanelPrzegladanieUzytkownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrzegladanieUzytkownikowLayout.createSequentialGroup()
                .addContainerGap(473, Short.MAX_VALUE)
                .addGroup(jPanelPrzegladanieUzytkownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(jLabel33)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelPrzegladanieUzytkownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPrzUzytkUprawnienia, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPrzUzytkLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPrzUzytDataUtworzenia, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPrzUzytHaslo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
            .addGroup(jPanelPrzegladanieUzytkownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelPrzegladanieUzytkownikowLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(235, Short.MAX_VALUE)))
        );
        jPanelPrzegladanieUzytkownikowLayout.setVerticalGroup(
            jPanelPrzegladanieUzytkownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrzegladanieUzytkownikowLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPrzegladanieUzytkownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPrzUzytkLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPrzegladanieUzytkownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPrzUzytkUprawnienia, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPrzegladanieUzytkownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPrzUzytDataUtworzenia, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPrzegladanieUzytkownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPrzUzytHaslo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addGap(30, 30, 30)
                .addGroup(jPanelPrzegladanieUzytkownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(140, Short.MAX_VALUE))
            .addGroup(jPanelPrzegladanieUzytkownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelPrzegladanieUzytkownikowLayout.createSequentialGroup()
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPaneMain.addTab("Przeglądanie użytkowników", jPanelPrzegladanieUzytkownikow);

        jPanelPrzegladaniePracownikow.setName("Przeglądanie pracowników"); // NOI18N

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel1.setPreferredSize(new java.awt.Dimension(400, 366));

        jTablePrzPracownik.setModel(km.getPracownikDataTable());
        jTablePrzPracownik.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePrzPracownikMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTablePrzPracownik);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 52, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("Szukaj");

        jLabel19.setText("Tekst wyszukiwany");

        jTextFieldPrzPracownikSzukaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPrzPracownikSzukajActionPerformed(evt);
            }
        });

        jLabel22.setText("Imie");

        jTextFieldPrzPracwonikImie.setEditable(false);
        jTextFieldPrzPracwonikImie.setPreferredSize(new java.awt.Dimension(80, 20));

        jLabel23.setText("Nazwisko");

        jTextFieldPrzPracownikNazwisko.setEditable(false);
        jTextFieldPrzPracownikNazwisko.setPreferredSize(new java.awt.Dimension(80, 20));

        jLabel24.setText("Płeć");

        jLabel25.setText("Data urodzenia");

        jTextFieldPrzPracownikDataUrodzenia.setEditable(false);
        jTextFieldPrzPracownikDataUrodzenia.setPreferredSize(new java.awt.Dimension(80, 20));

        jLabel26.setText("Tytuł");

        jTextFieldPrzPracownikTytul.setEditable(false);
        jTextFieldPrzPracownikTytul.setPreferredSize(new java.awt.Dimension(80, 20));

        jLabel27.setText("Pesel");

        jTextFieldPrzPracownikPesel.setEditable(false);
        jTextFieldPrzPracownikPesel.setPreferredSize(new java.awt.Dimension(80, 20));

        jLabel28.setText("Student");

        jLabel29.setText("Pensja");

        jTextFieldPrzPracownikPensja.setEditable(false);
        jTextFieldPrzPracownikPensja.setPreferredSize(new java.awt.Dimension(80, 20));

        jLabel30.setText("Data przyjęcia");

        jTextFieldPrzPracownikDataPrzyjecia.setEditable(false);
        jTextFieldPrzPracownikDataPrzyjecia.setPreferredSize(new java.awt.Dimension(80, 20));

        jLabel31.setText("Stanowisko");

        jListPrzPracownikPodlegleList.setEnabled(false);
        jScrollPane3.setViewportView(jListPrzPracownikPodlegleList);

        jLabelPrzPracownikPodlegleLabel.setText("Podległe stanowiska");

        jComboBoxPrzPracownikPlec.setModel(cmbPracownikPlec());
        jComboBoxPrzPracownikPlec.setEnabled(false);

        jComboBoxPrzPracownikStanowisko.setModel(cmbPracownikStanowisko());
        jComboBoxPrzPracownikStanowisko.setEnabled(false);

        jCheckBoxPrzPracownikStudent.setText("Tak");
        jCheckBoxPrzPracownikStudent.setEnabled(false);
        jCheckBoxPrzPracownikStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPrzPracownikStudentActionPerformed(evt);
            }
        });

        jButtonPrzPracownikSzukaj.setText("Szukaj");
        jButtonPrzPracownikSzukaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrzPracownikSzukajActionPerformed(evt);
            }
        });

        jComboBoxPracownikSzukaj.setModel(cmbPracownikSzukaj());

        jButtonRaporty.setText("Raporty");
        jButtonRaporty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRaportyActionPerformed(evt);
            }
        });

        jLabel44.setText("Koniec umowy");

        jButtonPrzPracownikEdytujZatwierdz.setText("Edytuj pracownika");
        jButtonPrzPracownikEdytujZatwierdz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrzPracownikEdytujZatwierdzActionPerformed(evt);
            }
        });

        jButtonPrzPracownikUsun.setText("Usuń pracownika");
        jButtonPrzPracownikUsun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrzPracownikUsunActionPerformed(evt);
            }
        });

        jTextFieldPrzPracoKoniecUmowy.setEditable(false);

        javax.swing.GroupLayout jPanelPrzegladaniePracownikowLayout = new javax.swing.GroupLayout(jPanelPrzegladaniePracownikow);
        jPanelPrzegladaniePracownikow.setLayout(jPanelPrzegladaniePracownikowLayout);
        jPanelPrzegladaniePracownikowLayout.setHorizontalGroup(
            jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                .addComponent(jTextFieldPrzPracownikSzukaj, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonPrzPracownikSzukaj))
                            .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                .addComponent(jComboBoxPracownikSzukaj, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonRaporty, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)))))
                .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30)
                                    .addComponent(jLabel31))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                        .addComponent(jComboBoxPrzPracownikStanowisko, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jTextFieldPrzPracownikDataPrzyjecia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel28))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldPrzPracownikPensja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBoxPrzPracownikStudent)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                        .addComponent(jLabel22)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextFieldPrzPracwonikImie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel23)
                                    .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                        .addComponent(jLabel26)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextFieldPrzPracownikTytul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldPrzPracownikDataUrodzenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                        .addComponent(jLabel24)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextFieldPrzPracownikNazwisko, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jComboBoxPrzPracownikPlec, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                        .addComponent(jLabel27)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextFieldPrzPracownikPesel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                .addComponent(jLabel44)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldPrzPracoKoniecUmowy)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelPrzPracownikPodlegleLabel)
                        .addGap(44, 44, 44))
                    .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                .addComponent(jButtonPrzPracownikEdytujZatwierdz)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonPrzPracownikUsun)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanelPrzegladaniePracownikowLayout.setVerticalGroup(
            jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldPrzPracwonikImie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(jTextFieldPrzPracownikNazwisko, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jComboBoxPrzPracownikPlec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jTextFieldPrzPracownikDataUrodzenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(jTextFieldPrzPracownikTytul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(jTextFieldPrzPracownikPesel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(jCheckBoxPrzPracownikStudent))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(jTextFieldPrzPracownikPensja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(jTextFieldPrzPracownikDataPrzyjecia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(jComboBoxPrzPracownikStanowisko, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44)
                            .addComponent(jTextFieldPrzPracoKoniecUmowy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonPrzPracownikEdytujZatwierdz)
                            .addComponent(jButtonPrzPracownikUsun)))
                    .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addGap(20, 20, 20))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jComboBoxPracownikSzukaj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButtonRaporty, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButtonPrzPracownikSzukaj)
                                    .addComponent(jLabel19)
                                    .addComponent(jTextFieldPrzPracownikSzukaj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                .addComponent(jLabelPrzPracownikPodlegleLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPaneMain.addTab("Przeglądanie pracowników", jPanelPrzegladaniePracownikow);

        jPanelPrzegladanieStanowisk.setName("Przeglądanie stanowisk"); // NOI18N

        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jTablePrzStanowisko.setModel(km.getStanowiskoDataTable());
        jTablePrzStanowisko.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePrzStanowiskoMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jTablePrzStanowisko);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 152, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        jScrollPane6.setViewportView(jPanel3);

        jListPrzStanowiskStanowiskaPodlegle.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane8.setViewportView(jListPrzStanowiskStanowiskaPodlegle);

        jLabel32.setText("Stanowiska podległe");

        jButtonPrzegladanieStanowiskUsun.setText("Usuń stanowisko");
        jButtonPrzegladanieStanowiskUsun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrzegladanieStanowiskUsunActionPerformed(evt);
            }
        });

        jLabelPrzegladanieStanowiskError.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelPrzegladanieStanowiskError.setForeground(new java.awt.Color(204, 0, 0));
        jLabelPrzegladanieStanowiskError.setText("Nie zaznaczono elementu!");

        javax.swing.GroupLayout jPanelPrzegladanieStanowiskLayout = new javax.swing.GroupLayout(jPanelPrzegladanieStanowisk);
        jPanelPrzegladanieStanowisk.setLayout(jPanelPrzegladanieStanowiskLayout);
        jPanelPrzegladanieStanowiskLayout.setHorizontalGroup(
            jPanelPrzegladanieStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrzegladanieStanowiskLayout.createSequentialGroup()
                .addGap(345, 345, 345)
                .addGroup(jPanelPrzegladanieStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPrzegladanieStanowiskError)
                    .addComponent(jButtonPrzegladanieStanowiskUsun, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelPrzegladanieStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanelPrzegladanieStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelPrzegladanieStanowiskLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(346, Short.MAX_VALUE)))
        );
        jPanelPrzegladanieStanowiskLayout.setVerticalGroup(
            jPanelPrzegladanieStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrzegladanieStanowiskLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrzegladanieStanowiskLayout.createSequentialGroup()
                .addContainerGap(138, Short.MAX_VALUE)
                .addComponent(jButtonPrzegladanieStanowiskUsun, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPrzegladanieStanowiskError)
                .addGap(132, 132, 132))
            .addGroup(jPanelPrzegladanieStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelPrzegladanieStanowiskLayout.createSequentialGroup()
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPaneMain.addTab("Przeglądanie stanowisk", jPanelPrzegladanieStanowisk);

        jPanelEdycjaStanowiskPodleglych.setName("Edycja stanowisk podległych"); // NOI18N

        jScrollPane9.setViewportView(jListEdStPodlegStanowiskaPodlegle);

        jListEdStPodleglychStanowiska.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListEdStPodleglychStanowiskaMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(jListEdStPodleglychStanowiska);

        jLabel35.setText("Stanowiska");

        jLabel38.setText("Stanowiska podległe");

        jButtonEdStPodlDodaj.setText("Dodaj do stanowisk podległych");
        jButtonEdStPodlDodaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEdStPodlDodajActionPerformed(evt);
            }
        });

        jButtonEdStPodlUsun.setText("Usuń ze stanowisk podległych");
        jButtonEdStPodlUsun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEdStPodlUsunActionPerformed(evt);
            }
        });

        jLabelEdStPodlErrorDodaj.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabelEdStPodlErrorDodaj.setForeground(new java.awt.Color(204, 0, 0));
        jLabelEdStPodlErrorDodaj.setText("Nie zaznaczono stanowiska!");

        jLabelEdStPodlErrorUsun.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabelEdStPodlErrorUsun.setForeground(new java.awt.Color(204, 0, 0));
        jLabelEdStPodlErrorUsun.setText("Nie zaznaczono stanowiska podległego!");

        jLabel41.setText("Zaznacz element z listy stanowisk podległych");

        jLabel42.setText("Wpisz nową nazwę lub wybierz element z listy");

        jComboBoxEdStPodlNazwa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBoxEdStPodlNazwa.setModel(cmbStanowiskoPodlegle());

        javax.swing.GroupLayout jPanelEdycjaStanowiskPodleglychLayout = new javax.swing.GroupLayout(jPanelEdycjaStanowiskPodleglych);
        jPanelEdycjaStanowiskPodleglych.setLayout(jPanelEdycjaStanowiskPodleglychLayout);
        jPanelEdycjaStanowiskPodleglychLayout.setHorizontalGroup(
            jPanelEdycjaStanowiskPodleglychLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEdycjaStanowiskPodleglychLayout.createSequentialGroup()
                .addGroup(jPanelEdycjaStanowiskPodleglychLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEdycjaStanowiskPodleglychLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(83, 83, 83)
                        .addGroup(jPanelEdycjaStanowiskPodleglychLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42)
                            .addGroup(jPanelEdycjaStanowiskPodleglychLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jComboBoxEdStPodlNazwa, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldEdStPodlNazwa, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButtonEdStPodlDodaj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonEdStPodlUsun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabelEdStPodlErrorUsun)
                            .addComponent(jLabelEdStPodlErrorDodaj)))
                    .addGroup(jPanelEdycjaStanowiskPodleglychLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel35)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(jPanelEdycjaStanowiskPodleglychLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEdycjaStanowiskPodleglychLayout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addGap(24, 24, 24)))
                .addContainerGap())
        );
        jPanelEdycjaStanowiskPodleglychLayout.setVerticalGroup(
            jPanelEdycjaStanowiskPodleglychLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEdycjaStanowiskPodleglychLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelEdycjaStanowiskPodleglychLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEdycjaStanowiskPodleglychLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelEdycjaStanowiskPodleglychLayout.createSequentialGroup()
                        .addGroup(jPanelEdycjaStanowiskPodleglychLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(jScrollPane10))
                        .addContainerGap())
                    .addGroup(jPanelEdycjaStanowiskPodleglychLayout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldEdStPodlNazwa, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxEdStPodlNazwa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEdStPodlDodaj, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelEdStPodlErrorDodaj)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jLabel41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEdStPodlUsun, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelEdStPodlErrorUsun)
                        .addGap(53, 53, 53))))
        );

        jTabbedPaneMain.addTab("Edycja stanowisk podległych", jPanelEdycjaStanowiskPodleglych);

        jPanelEdycjahistoriiStanowiskPracownika.setName("Edycja historii stanowisk pracownika"); // NOI18N
        jPanelEdycjahistoriiStanowiskPracownika.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelEdycjahistoriiStanowiskPracownikaMouseClicked(evt);
            }
        });

        jScrollPane11.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jTableEdycjaHistStan.setModel(km.getPracownikHistStanoDataTable());
        jTableEdycjaHistStan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableEdycjaHistStanMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(jTableEdycjaHistStan);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 135, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 34, Short.MAX_VALUE))
        );

        jScrollPane11.setViewportView(jPanel4);

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel66.setText("Szukaj");

        jLabel67.setText("Nazwisko pracownika");

        jTextFieldEdycjaHistStanSzukaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEdycjaHistStanSzukajActionPerformed(evt);
            }
        });

        jButtonEdycjaHistStankSzukaj.setText("Szukaj");
        jButtonEdycjaHistStankSzukaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEdycjaHistStankSzukajActionPerformed(evt);
            }
        });

        jLabel39.setText("Nazwa");

        jLabel68.setText("Data rozpoczęcia");

        jLabel69.setText("Data zakończenia");

        jButtonEdycjaHistStanEdytuj.setText("Edytuj");
        jButtonEdycjaHistStanEdytuj.setEnabled(false);

        jButtonEdycjaHistStanUsun.setText("Usuń");
        jButtonEdycjaHistStanUsun.setEnabled(false);
        jButtonEdycjaHistStanUsun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEdycjaHistStanUsunActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelEdycjahistoriiStanowiskPracownikaLayout = new javax.swing.GroupLayout(jPanelEdycjahistoriiStanowiskPracownika);
        jPanelEdycjahistoriiStanowiskPracownika.setLayout(jPanelEdycjahistoriiStanowiskPracownikaLayout);
        jPanelEdycjahistoriiStanowiskPracownikaLayout.setHorizontalGroup(
            jPanelEdycjahistoriiStanowiskPracownikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createSequentialGroup()
                        .addComponent(jLabel66)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel67)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldEdycjaHistStanSzukaj, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonEdycjaHistStankSzukaj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createSequentialGroup()
                        .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldEdycjaHistStanNazwa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createSequentialGroup()
                        .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldEdycjaHistStanDataRozpoczecia, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createSequentialGroup()
                        .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonEdycjaHistStanEdytuj, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel69, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldEdycjaHistStanDataZakonczenia, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jButtonEdycjaHistStanUsun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        jPanelEdycjahistoriiStanowiskPracownikaLayout.setVerticalGroup(
            jPanelEdycjahistoriiStanowiskPracownikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(jLabel67)
                    .addComponent(jTextFieldEdycjaHistStanSzukaj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEdycjaHistStankSzukaj))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                    .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createSequentialGroup()
                        .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39)
                            .addComponent(jTextFieldEdycjaHistStanNazwa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel68)
                            .addComponent(jTextFieldEdycjaHistStanDataRozpoczecia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel69)
                            .addComponent(jTextFieldEdycjaHistStanDataZakonczenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonEdycjaHistStanEdytuj)
                            .addComponent(jButtonEdycjaHistStanUsun))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jTabbedPaneMain.addTab("Edycja historii stanowisk pracownika", jPanelEdycjahistoriiStanowiskPracownika);

        jPanelZatwierdzanie.setName("Zatwierdzanie zmian"); // NOI18N

        jScrollPane14.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jTableZatwierdzanie.setModel(km.getZatwierdzanieDataTable());
        jTableZatwierdzanie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableZatwierdzanieMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(jTableZatwierdzanie);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 31, Short.MAX_VALUE))
        );

        jScrollPane14.setViewportView(jPanel5);

        jButtonZatwierdzanieZmianZatwierdz.setText("Zatwierdź");
        jButtonZatwierdzanieZmianZatwierdz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonZatwierdzanieZmianZatwierdzActionPerformed(evt);
            }
        });

        jButtonZatwierdzanieZmianOdrzuc.setText("Odrzuć");
        jButtonZatwierdzanieZmianOdrzuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonZatwierdzanieZmianOdrzucActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelZatwierdzanieLayout = new javax.swing.GroupLayout(jPanelZatwierdzanie);
        jPanelZatwierdzanie.setLayout(jPanelZatwierdzanieLayout);
        jPanelZatwierdzanieLayout.setHorizontalGroup(
            jPanelZatwierdzanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)
            .addGroup(jPanelZatwierdzanieLayout.createSequentialGroup()
                .addGap(169, 169, 169)
                .addComponent(jButtonZatwierdzanieZmianZatwierdz, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButtonZatwierdzanieZmianOdrzuc, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelZatwierdzanieLayout.setVerticalGroup(
            jPanelZatwierdzanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelZatwierdzanieLayout.createSequentialGroup()
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelZatwierdzanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonZatwierdzanieZmianZatwierdz, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonZatwierdzanieZmianOdrzuc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPaneMain.addTab("Zatwierdzanie zmian", jPanelZatwierdzanie);

        jPanelHistoriaZatwierdzonychZmian.setName("Historia zatwierdzonych zmian"); // NOI18N

        jScrollPane16.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jTableHistoriaZatwierdzonychZmian.setModel(km.getHistoriaZmianZatwierdzaniaTable());
        jScrollPane17.setViewportView(jTableHistoriaZatwierdzonychZmian);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        jScrollPane16.setViewportView(jPanel6);

        javax.swing.GroupLayout jPanelHistoriaZatwierdzonychZmianLayout = new javax.swing.GroupLayout(jPanelHistoriaZatwierdzonychZmian);
        jPanelHistoriaZatwierdzonychZmian.setLayout(jPanelHistoriaZatwierdzonychZmianLayout);
        jPanelHistoriaZatwierdzonychZmianLayout.setHorizontalGroup(
            jPanelHistoriaZatwierdzonychZmianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane16)
        );
        jPanelHistoriaZatwierdzonychZmianLayout.setVerticalGroup(
            jPanelHistoriaZatwierdzonychZmianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
        );

        jTabbedPaneMain.addTab("Historia zatwierdzonych zmian", jPanelHistoriaZatwierdzonychZmian);

        jPanelDodawanieHistoriiStanowisk.setName("Dodawanie historii stanowisk pracownika"); // NOI18N

        jLabel60.setText("Nazwa");

        jLabel61.setText("Data rozpoczęcia");

        jLabel62.setText("Data zakończenia");

        jButtonDodawanieHistStanDodaj.setText("Dodaj");
        jButtonDodawanieHistStanDodaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDodawanieHistStanDodajActionPerformed(evt);
            }
        });

        jLabelDodawanieHistStanDodano.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelDodawanieHistStanDodano.setForeground(new java.awt.Color(0, 104, 0));
        jLabelDodawanieHistStanDodano.setText("Dodano pomyślnie");

        jTextFieldDodawanieHistStanImieINazw.setEditable(false);
        jTextFieldDodawanieHistStanImieINazw.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextFieldDodawanieHistStanImieINazw.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldDodawanieHistStanImieINazw.setText("Jan Kowalski");

        jLabel63.setText("Historia stanowisk pracownika");

        jButtonDodawanieHistStanWybierzPrac.setText("Wybierz pracownika");
        jButtonDodawanieHistStanWybierzPrac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDodawanieHistStanWybierzPracActionPerformed(evt);
            }
        });

        jLabelDodawanieHistStanError.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelDodawanieHistStanError.setForeground(new java.awt.Color(205, 0, 0));
        jLabelDodawanieHistStanError.setText("Wszystkie pola muszą być wypełnione!");

        jLabel64.setForeground(new java.awt.Color(153, 153, 153));
        jLabel64.setText("format: dd/mm/rrrr");

        jLabel65.setForeground(new java.awt.Color(153, 153, 153));
        jLabel65.setText("format: dd/mm/rrrr");

        javax.swing.GroupLayout jPanelDodawanieHistoriiStanowiskLayout = new javax.swing.GroupLayout(jPanelDodawanieHistoriiStanowisk);
        jPanelDodawanieHistoriiStanowisk.setLayout(jPanelDodawanieHistoriiStanowiskLayout);
        jPanelDodawanieHistoriiStanowiskLayout.setHorizontalGroup(
            jPanelDodawanieHistoriiStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createSequentialGroup()
                .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createSequentialGroup()
                                .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldDodawanieHistStanNazwa, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createSequentialGroup()
                                .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldDodawanieHistStanDataRozpoczecia, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel64))
                            .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createSequentialGroup()
                                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButtonDodawanieHistStanDodaj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldDodawanieHistStanDataZakonczenia, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                                .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabelDodawanieHistStanDodano))
                                    .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(jLabel65))))
                            .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createSequentialGroup()
                                .addComponent(jLabel63)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldDodawanieHistStanImieINazw, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonDodawanieHistStanWybierzPrac))))
                    .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(jLabelDodawanieHistStanError)))
                .addContainerGap(197, Short.MAX_VALUE))
        );
        jPanelDodawanieHistoriiStanowiskLayout.setVerticalGroup(
            jPanelDodawanieHistoriiStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(jTextFieldDodawanieHistStanImieINazw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDodawanieHistStanWybierzPrac))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(jTextFieldDodawanieHistStanNazwa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(jTextFieldDodawanieHistStanDataRozpoczecia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62)
                    .addComponent(jTextFieldDodawanieHistStanDataZakonczenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65))
                .addGap(33, 33, 33)
                .addGroup(jPanelDodawanieHistoriiStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonDodawanieHistStanDodaj)
                    .addComponent(jLabelDodawanieHistStanDodano))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelDodawanieHistStanError)
                .addContainerGap(137, Short.MAX_VALUE))
        );

        jTabbedPaneMain.addTab("Dodawanie historii stanowisk pracownika", jPanelDodawanieHistoriiStanowisk);

        jPanel15.setName("..."); // NOI18N

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 685, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );

        jTabbedPaneMain.addTab("...", jPanel15);

        jPanelRaporty.setBackground(new java.awt.Color(255, 255, 255));
        jPanelRaporty.setName("Raport pracownika"); // NOI18N

        jComboBoxRaportType.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jComboBoxRaportType.setModel(cmbRaportListType());
        jComboBoxRaportType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxRaportTypeActionPerformed(evt);
            }
        });

        jLabel40.setText("Typ raportu");

        jLabel43.setText("Imie i nazwisko");

        jTextFieldRaportImieINazwisko.setEditable(false);

        jLabel45.setText("Data urodzenia");

        jTextFieldRaportDataUrodzenia.setEditable(false);

        jLabel46.setText("Pesel");

        jTextFieldRaportPesel.setEditable(false);

        jLabel47.setText("Student");

        jTextFieldRaportStudent.setEditable(false);

        jLabel48.setText("Data zatrudnienia");

        jTextFieldRaportDataZatrudnienia.setEditable(false);

        jLabel49.setText("Data końca umowy");

        jTextFieldRaportDataKoncaUmowy.setEditable(false);

        jLabel50.setText("Pensja");

        jTextFieldRaportPensja.setEditable(false);

        jLabel53.setText("Stanowisko");

        jTextFieldRaportStanowisko.setEditable(false);

        jLabel54.setText("Średnie wynagrodzenie z 3 ost. mies.");

        jTextFieldRaportPensjaSrednia.setEditable(false);

        jButtonRaportDrukuj.setText("Drukuj");
        jButtonRaportDrukuj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRaportDrukujActionPerformed(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel52.setText("PODLASIE");

        jLabel55.setText("ul. PL. 1-GO MAJA 3");

        jLabel56.setText("23-100 Ostrowiec Świętokrzyski");

        jLabel57.setText("REGON 9367041332336    EKD 456/EKD");

        jLabel58.setBackground(new java.awt.Color(255, 255, 255));
        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("ZAŚWIADCZENIE O ZAROBKACH");

        jLabel59.setText("Ostrowiec Świętokrzyski,");

        jLabelRaportData.setText("02/01/2015");

        javax.swing.GroupLayout jPanelRaportyLayout = new javax.swing.GroupLayout(jPanelRaporty);
        jPanelRaporty.setLayout(jPanelRaportyLayout);
        jPanelRaportyLayout.setHorizontalGroup(
            jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRaportyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel52)
                    .addComponent(jLabel55)
                    .addComponent(jLabel56)
                    .addComponent(jLabel57)
                    .addGroup(jPanelRaportyLayout.createSequentialGroup()
                        .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelRaportyLayout.createSequentialGroup()
                                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldRaportPensjaSrednia)
                                .addGap(150, 150, 150)
                                .addComponent(jButtonRaportDrukuj))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelRaportyLayout.createSequentialGroup()
                                .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel50, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel47, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel46, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextFieldRaportPensja)
                                    .addComponent(jTextFieldRaportStudent, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldRaportPesel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldRaportImieINazwisko, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanelRaportyLayout.createSequentialGroup()
                                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldRaportDataZatrudnienia))
                                    .addGroup(jPanelRaportyLayout.createSequentialGroup()
                                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldRaportDataUrodzenia, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanelRaportyLayout.createSequentialGroup()
                                        .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldRaportStanowisko))
                                    .addGroup(jPanelRaportyLayout.createSequentialGroup()
                                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldRaportDataKoncaUmowy, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(137, 137, 137)
                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRaportyLayout.createSequentialGroup()
                .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelRaportyLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxRaportType, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel59)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelRaportData))
                    .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(247, 247, 247))
        );
        jPanelRaportyLayout.setVerticalGroup(
            jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRaportyLayout.createSequentialGroup()
                .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxRaportType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(jLabel59)
                    .addComponent(jLabelRaportData))
                .addGap(9, 9, 9)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel56)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel57)
                .addGap(18, 18, 18)
                .addComponent(jLabel58)
                .addGap(33, 33, 33)
                .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRaportyLayout.createSequentialGroup()
                        .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelRaportyLayout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel51)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanelRaportyLayout.createSequentialGroup()
                                .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldRaportImieINazwisko, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel43))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldRaportPesel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel46))
                                .addGap(11, 11, 11)
                                .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldRaportStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel47))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldRaportPensja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel50))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel54)
                                .addComponent(jTextFieldRaportPensjaSrednia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButtonRaportDrukuj))
                        .addContainerGap(23, Short.MAX_VALUE))
                    .addGroup(jPanelRaportyLayout.createSequentialGroup()
                        .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelRaportyLayout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel53)
                                    .addComponent(jTextFieldRaportStanowisko, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanelRaportyLayout.createSequentialGroup()
                                .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel45)
                                    .addComponent(jTextFieldRaportDataUrodzenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel48)
                                    .addComponent(jTextFieldRaportDataZatrudnienia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)
                                .addGroup(jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel49)
                                    .addComponent(jTextFieldRaportDataKoncaUmowy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPaneMain.addTab("Raport pracownika", jPanelRaporty);

        tlo.add(jTabbedPaneMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 690, 390));

        getContentPane().add(tlo, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 1, 700, 390));

        jLabel3.setText("Zalogowano jako:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        jLabelZalogowanoJako.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelZalogowanoJako.setText("nie zalogowano");
        getContentPane().add(jLabelZalogowanoJako, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNowyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNowyActionPerformed
        tabActionNowy();
    }//GEN-LAST:event_jButtonNowyActionPerformed

    private void jButtonZatwierdzanieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZatwierdzanieActionPerformed
        tabActionZatwierdzanie();
        jTableZatwierdzanie.setModel(km.getZatwierdzanieDataTable());

    }//GEN-LAST:event_jButtonZatwierdzanieActionPerformed

    private void jButtonLogowanieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogowanieActionPerformed
        initTabbedPaneList();
    }//GEN-LAST:event_jButtonLogowanieActionPerformed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        if (jTextLogin.getText().length() == 0) {
            jLabelWarningLogin.setVisible(true);
        }
        if (String.valueOf(jTextHaslo.getPassword()).length() == 0) {
            jLabelWarningHaslo.setVisible(true);
        }
        if (jTextLogin.getText().length() != 0 && String.valueOf(jTextHaslo.getPassword()).length() != 0) {
            if (km.checkUserInDB(jTextLogin.getText(), String.valueOf(jTextHaslo.getPassword()))) {
                this.privilage = km.getUserPrivilage(jTextLogin.getText());
                jLabelZalogowanoJako.setText(this.privilage);
                jLabelLoginError.setVisible(false);
                jLabelWarningHaslo.setVisible(false);
                jLabelWarningLogin.setVisible(false);
                jButtonLogowanie.setText("Wyloguj");
                this.zalogowano_jako = jTextLogin.getText();
            } else {
                jLabelLoginError.setVisible(true);
            }
        }

    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void jTextFieldNowyPracownikImieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNowyPracownikImieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNowyPracownikImieActionPerformed

    private void jTextFieldNoweStanowiskoNazwaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNoweStanowiskoNazwaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNoweStanowiskoNazwaActionPerformed

    private void jButtonNowyUzytkownikDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNowyUzytkownikDodajActionPerformed
        Uzytkownik u = new Uzytkownik();
        if (jTextFieldNowyUzytkownikLoginAdd.getText().length() == 0) {
            jTextFieldNowyUzytkownikLoginAdd.setBackground(Color.red);
            jLabelNowyUzytkownikError.setVisible(true);
        } else {
            jTextFieldNowyUzytkownikLoginAdd.setBackground(Color.white);
        }
        if (jTextFieldNowyUzytkownikHasloAdd.getText().length() == 0) {
            jTextFieldNowyUzytkownikHasloAdd.setBackground(Color.red);
            jLabelNowyUzytkownikError.setVisible(true);
        } else {
            jTextFieldNowyUzytkownikHasloAdd.setBackground(Color.white);
        }
        if (jTextFieldNowyUzytkownikLoginAdd.getText().length() != 0 && jTextFieldNowyUzytkownikHasloAdd.getText().length() != 0) {
            u.setData_utworzenia(generateDateInMiliseconds());
            u.setHaslo(jTextFieldNowyUzytkownikHasloAdd.getText());
            u.setLogin(jTextFieldNowyUzytkownikLoginAdd.getText());
            u.setUprawnienia(jComboBoxUprawnieniaAdd.getSelectedItem().toString());
            try {
                if (km.addUzytkownik(u) == 1) {
                    jLabelNowyUzytkownikDodano.setVisible(true);
                    jLabelNowyUzytkownikError.setVisible(false);
                    jTextFieldNowyUzytkownikHasloAdd.setBackground(Color.white);
                    jTextFieldNowyUzytkownikLoginAdd.setBackground(Color.white);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jButtonNowyUzytkownikDodajActionPerformed

    private void jButtonNoweStanowiskoDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNoweStanowiskoDodajActionPerformed
        Stanowisko s = new Stanowisko();
        if (jTextFieldNoweStanowiskoNazwa.getText().length() == 0) {
            jLabelNoweStanowiskoError.setVisible(true);
        } else {
            s.setNazwa(jTextFieldNoweStanowiskoNazwa.getText());
            try {
                if (km.addStanowisko(s) == 1) {
                    jLabelNoweStanowiskoDodano.setVisible(true);
                    jLabelNoweStanowiskoError.setVisible(false);
                    jComboBoxNowyPracownikStanowisko.setModel(cmbPracownikStanowisko());
                }
            } catch (SQLException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_jButtonNoweStanowiskoDodajActionPerformed

    private void jButtonNowyPracownikDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNowyPracownikDodajActionPerformed
        boolean error = false;
        long dataUr = 0;
        long dataKonca = 0;
        int student = 0;
        //System.out.println(jComboBoxNowyPracownikPlec.getSelectedItem());
        if (jTextFieldNowyPracownikImie.getText().length() == 0) {
            jTextFieldNowyPracownikImie.setBackground(Color.red);
            jLabelNowyPracownikkError.setVisible(true);
            error = true;
        } else {
            jTextFieldNowyPracownikImie.setBackground(Color.white);
        }
        if (jTextFieldNowyPracownikNazwisko.getText().length() == 0) {
            jTextFieldNowyPracownikNazwisko.setBackground(Color.red);
            jLabelNowyPracownikkError.setVisible(true);
            error = true;
        } else {
            jTextFieldNowyPracownikNazwisko.setBackground(Color.white);
        }
        if (jTextFieldNowyPracownikDataUrodzenia.getText().length() == 0) {
            jTextFieldNowyPracownikDataUrodzenia.setBackground(Color.red);
            jLabelNowyPracownikkError.setVisible(true);
            error = true;
        } else {
            jTextFieldNowyPracownikDataUrodzenia.setBackground(Color.white);
        }
        if (jTextFieldNowyPracownikPesel.getText().length() == 0) {
            jTextFieldNowyPracownikPesel.setBackground(Color.red);
            jLabelNowyPracownikkError.setVisible(true);
            error = true;
        } else {
            jTextFieldNowyPracownikPesel.setBackground(Color.white);
        }
        if (jTextFieldNowyPracownikPensja.getText().length() == 0) {
            jTextFieldNowyPracownikPensja.setBackground(Color.red);
            jLabelNowyPracownikkError.setVisible(true);
            error = true;
        } else {
            jTextFieldNowyPracownikPensja.setBackground(Color.white);
        }
        if (!error) {
            if (jCheckBoxNowyPracownikStudent.isSelected()) {
                student = 0;
            } else {
                student = 1;
            }
            try {
                dataUr = getDateInMilisecFromString(jTextFieldNowyPracownikDataUrodzenia.getText());
                dataKonca = getDateInMilisecFromString(jTextFieldNowyPracownikDataKoncaUmowy.getText());
            } catch (ParseException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }

            Pracownik p = new Pracownik(
                    0,
                    getStanowiskoId(jComboBoxNowyPracownikStanowisko.getSelectedItem().toString()),
                    jTextFieldNowyPracownikImie.getText(),
                    jTextFieldNowyPracownikNazwisko.getText(),
                    jComboBoxNowyPracownikPlec.getSelectedItem().toString(),
                    dataUr,
                    jTextFieldNowyPracownikTytul.getText(),
                    jTextFieldNowyPracownikPesel.getText(),
                    student,
                    Integer.valueOf(jTextFieldNowyPracownikPensja.getText()),
                    generateDateInMiliseconds(),
                    dataKonca
            );
            if (km.addPracownik(p) == 1) {
                jTextFieldNowyPracownikImie.setBackground(Color.white);
                jTextFieldNowyPracownikNazwisko.setBackground(Color.white);
                jTextFieldNowyPracownikPensja.setBackground(Color.white);
                jTextFieldNowyPracownikPesel.setBackground(Color.white);
                jTextFieldNowyPracownikDataUrodzenia.setBackground(Color.white);
                jLabelNowyPracownikDodano.setVisible(true);
                jLabelNowyPracownikkError.setVisible(false);
            }
        }

    }//GEN-LAST:event_jButtonNowyPracownikDodajActionPerformed

    private void jButtonKartotekaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonKartotekaActionPerformed
        tabActionKartoteka();
        disablePracownikTextFields(false);
        jTablePrzPracownik.setModel(km.getPracownikDataTable());
    }//GEN-LAST:event_jButtonKartotekaActionPerformed

    private void jTextFieldPrzPracownikSzukajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPrzPracownikSzukajActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPrzPracownikSzukajActionPerformed

    private void jCheckBoxPrzPracownikStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPrzPracownikStudentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxPrzPracownikStudentActionPerformed

    private void jTablePrzPracownikMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePrzPracownikMouseClicked
        //pokaz przycisk raportu
        jButtonRaporty.setVisible(true);
        jButtonPrzPracownikUsun.setEnabled(true);
        jButtonPrzPracownikEdytujZatwierdz.setEnabled(true);

        int selectedId = (int) jTablePrzPracownik.getModel().getValueAt(jTablePrzPracownik.getSelectedRow(), 0);
        Pracownik p;
        p = km.getPracownikById(selectedId);

        jTextFieldPrzPracwonikImie.setText(p.getImie());
        jTextFieldPrzPracownikNazwisko.setText(p.getNazwisko());
        if (p.getPlec().equalsIgnoreCase("Mezczyzna")) {
            jComboBoxPrzPracownikPlec.setSelectedIndex(0);
        } else {
            jComboBoxPrzPracownikPlec.setSelectedIndex(1);
        }
        jTextFieldPrzPracownikPesel.setText(p.getPesel());
        jTextFieldPrzPracownikPensja.setText(Integer.toString(p.getPensja()));
        jTextFieldPrzPracownikTytul.setText(p.getTytul());
        jTextFieldPrzPracownikDataPrzyjecia.setText(dateLongToString(p.getData_przyjecia()));
        jTextFieldPrzPracownikDataUrodzenia.setText(dateLongToString(p.getData_urodzenia()));
        jTextFieldPrzPracoKoniecUmowy.setText(dateLongToString(p.getData_konca_umowy()));
        if (p.getCzy_studiuje() == 0) {
            jCheckBoxPrzPracownikStudent.setSelected(true);
        } else {
            jCheckBoxPrzPracownikStudent.setSelected(false);
        }

        int stanowiskoPracownika = p.getStanowisko_id_stanowisko();
        String nazwaStanowiska = km.getStanowiskoNazwaById(stanowiskoPracownika);
        int index = getIndexOfCmbStanowisko(nazwaStanowiska);
        jComboBoxPrzPracownikStanowisko.setSelectedIndex(index);

        jListPrzPracownikPodlegleList.setModel(km.getStanowiskoHisStanowiskoPodlegle(stanowiskoPracownika));
    }//GEN-LAST:event_jTablePrzPracownikMouseClicked

    private void jButtonPrzPracownikSzukajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrzPracownikSzukajActionPerformed
        if (jTextFieldPrzPracownikSzukaj.getText().length() == 0) {
            jTablePrzPracownik.setModel(km.getPracownikDataTable());
        } else {
            jTablePrzPracownik.setModel(km.getPracownikDataTableWithQuery(jTextFieldPrzPracownikSzukaj.getText(), jComboBoxPracownikSzukaj.getSelectedItem().toString()));
        }
    }//GEN-LAST:event_jButtonPrzPracownikSzukajActionPerformed

    private void jTextFieldPrzUzytHasloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPrzUzytHasloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPrzUzytHasloActionPerformed

    private void jTablePrzUzytkownikowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePrzUzytkownikowMouseClicked
        int selectedId = (int) jTablePrzUzytkownikow.getModel().getValueAt(jTablePrzUzytkownikow.getSelectedRow(), 0);
        Uzytkownik u;
        u = km.getUzytkownikById(selectedId);

        jTextFieldPrzUzytkLogin.setText(u.getLogin());
        jTextFieldPrzUzytkUprawnienia.setText(u.getUprawnienia());
        jTextFieldPrzUzytDataUtworzenia.setText(dateLongToString(u.getData_utworzenia()));
        jTextFieldPrzUzytHaslo.setText(u.getHaslo());


    }//GEN-LAST:event_jTablePrzUzytkownikowMouseClicked

    private void jTablePrzStanowiskoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePrzStanowiskoMouseClicked
        //wypelnienie listy podleglych
        int id = (int) jTablePrzStanowisko.getModel().getValueAt(jTablePrzStanowisko.getSelectedRow(), 0);
        jListPrzStanowiskStanowiskaPodlegle.setModel(km.getStanowiskoHisStanowiskoPodlegle(id));
    }//GEN-LAST:event_jTablePrzStanowiskoMouseClicked

    private void jButtonZarzadzanieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZarzadzanieActionPerformed
        tabActionZarzadzanie();
        jListEdStPodleglychStanowiska.setModel(km.getAllStanowiskoListModel());
        if (jTablePrzPracownik.getSelectedRow() == -1) {
            jTextFieldDodawanieHistStanImieINazw.setText("nie wybrano");
        } else {
            int selectedId = (int) jTablePrzPracownik.getModel().getValueAt(jTablePrzPracownik.getSelectedRow(), 0);
            Pracownik p;
            p = km.getPracownikById(selectedId);
            jTextFieldDodawanieHistStanImieINazw.setText(p.getImie() + " " + p.getNazwisko());
        }
    }//GEN-LAST:event_jButtonZarzadzanieActionPerformed

    private void jListEdStPodleglychStanowiskaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListEdStPodleglychStanowiskaMouseClicked
        String selectedName = jListEdStPodleglychStanowiska.getModel().getElementAt(jListEdStPodleglychStanowiska.getSelectedIndex()).toString();
        int id_stanowisko = km.getStanowiskoIdByNazwa(selectedName);
        jListEdStPodlegStanowiskaPodlegle.setModel(km.getStanowiskoHisStanowiskoPodlegle(id_stanowisko));
    }//GEN-LAST:event_jListEdStPodleglychStanowiskaMouseClicked

    private void jButtonEdStPodlDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEdStPodlDodajActionPerformed
        String nowaNazwa = "";
        int id_stanowisko = 0;
        int id_stanowisko_podlegle = 0;
        if (jListEdStPodleglychStanowiska.getSelectedIndex() == -1) {
            jLabelEdStPodlErrorDodaj.setVisible(true);
        } else {
            id_stanowisko = km.getStanowiskoIdByNazwa(jListEdStPodleglychStanowiska.getModel().getElementAt(jListEdStPodleglychStanowiska.getSelectedIndex()).toString());
            jLabelEdStPodlErrorDodaj.setVisible(false);
        }
        if (jTextFieldEdStPodlNazwa.getText().length() == 0) {
            //wybrane z comboboxa 
            nowaNazwa = jComboBoxEdStPodlNazwa.getModel().getElementAt(jComboBoxEdStPodlNazwa.getSelectedIndex()).toString();
            id_stanowisko_podlegle = km.getStanowiskoPodlegleIdByNazwa(nowaNazwa);

        } else if (id_stanowisko > 0) {
            nowaNazwa = jTextFieldEdStPodlNazwa.getText();
            try {
                km.addStanowiskoPodlegle(nowaNazwa);
            } catch (SQLException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
            id_stanowisko_podlegle = km.getStanowiskoPodlegleIdByNazwa(nowaNazwa);
            //dodaj nowa nazwe do bazy
            //i dodaj tą nazwe dla tego stanowiska do bazy
        }
        System.out.println(id_stanowisko + " " + id_stanowisko_podlegle);
        if (id_stanowisko != 0 && id_stanowisko_podlegle != 0) {
            try {
                if (km.addStanowiskoPodlegleToStanowisko(id_stanowisko, id_stanowisko_podlegle) == 1) {
                    jListEdStPodlegStanowiskaPodlegle.setModel(km.getStanowiskoHisStanowiskoPodlegle(id_stanowisko));
                    jComboBoxEdStPodlNazwa.setModel(cmbStanowiskoPodlegle());
                }
            } catch (SQLException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_jButtonEdStPodlDodajActionPerformed

    private void jButtonEdStPodlUsunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEdStPodlUsunActionPerformed
        jLabelEdStPodlErrorDodaj.setVisible(false);
        int id_podl = 0;
        int id_st = 0;
        if (jListEdStPodlegStanowiskaPodlegle.getSelectedIndex() == -1) {
            jLabelEdStPodlErrorUsun.setVisible(true);
        } else {
            jLabelEdStPodlErrorUsun.setVisible(false);
            id_podl = km.getStanowiskoPodlegleIdByNazwa((String) jListEdStPodlegStanowiskaPodlegle.getModel().getElementAt(jListEdStPodlegStanowiskaPodlegle.getSelectedIndex()));
            id_st = km.getStanowiskoIdByNazwa((String) jListEdStPodleglychStanowiska.getModel().getElementAt(jListEdStPodleglychStanowiska.getSelectedIndex()));
        }
        if (id_podl != 0 && id_st != 0) {
            km.deleteStanowiskoPodlegleFromStanowisko(id_podl, id_st);
            jListEdStPodlegStanowiskaPodlegle.setModel(km.getStanowiskoHisStanowiskoPodlegle(id_st));
        }

    }//GEN-LAST:event_jButtonEdStPodlUsunActionPerformed

    private void jButtonPrzegladanieStanowiskUsunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrzegladanieStanowiskUsunActionPerformed
        if (jTablePrzStanowisko.getSelectedRow() == -1) {
            jLabelPrzegladanieStanowiskError.setVisible(true);
        } else {
            jLabelPrzegladanieStanowiskError.setVisible(false);
            km.deleteStanowiskoById((int) jTablePrzStanowisko.getModel().getValueAt(jTablePrzStanowisko.getSelectedRow(), 0));
            jTablePrzStanowisko.setModel(km.getStanowiskoDataTable());
        }

    }//GEN-LAST:event_jButtonPrzegladanieStanowiskUsunActionPerformed

    private void jButtonPrzPracownikEdytujZatwierdzActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrzPracownikEdytujZatwierdzActionPerformed
        disablePracownikTextFields(true);
    }//GEN-LAST:event_jButtonPrzPracownikEdytujZatwierdzActionPerformed

    private void jButtonRaportyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRaportyActionPerformed
        jComboBoxRaportType.setModel(cmbRaportListType());
        showTabAtIndex(jPanelRaporty, 3);
        jTabbedPaneMain.setSelectedIndex(3);

        int selectedId = (int) jTablePrzPracownik.getModel().getValueAt(jTablePrzPracownik.getSelectedRow(), 0);
        Pracownik p;
        p = km.getPracownikById(selectedId);

        jTextFieldRaportImieINazwisko.setText(p.getImie() + " " + p.getNazwisko());
        jTextFieldRaportPesel.setText(p.getPesel());
        jTextFieldRaportPensja.setText(Integer.toString(p.getPensja()));
        jTextFieldRaportDataZatrudnienia.setText(dateLongToString(p.getData_przyjecia()));
        jTextFieldRaportDataUrodzenia.setText(dateLongToString(p.getData_urodzenia()));
        jTextFieldRaportDataKoncaUmowy.setText(dateLongToString(p.getData_konca_umowy()));
        if (p.getCzy_studiuje() == 0) {
            jTextFieldRaportStudent.setText("Tak");
        } else {
            jTextFieldRaportStudent.setText("Nie");
        }
        int stanowiskoPracownika = p.getStanowisko_id_stanowisko();
        String nazwaStanowiska = km.getStanowiskoNazwaById(stanowiskoPracownika);
        jTextFieldRaportStanowisko.setText(nazwaStanowiska);
        //ktory dzis jest?
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String s = formatter.format(date);
        jLabelRaportData.setText(s);
        //
        int sredniaPensja = 3 * p.getPensja();
        jTextFieldRaportPensjaSrednia.setText(Integer.toString(sredniaPensja));
    }//GEN-LAST:event_jButtonRaportyActionPerformed

    private void printCard() {

        PrinterJob printjob = PrinterJob.getPrinterJob();
        printjob.setJobName(" personal card ");

        printjob.setPrintable(new Printable() {
            public int print(Graphics pg, PageFormat pf, int pageNum) {

                pf.setOrientation(PageFormat.LANDSCAPE);

                if (pageNum > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2 = (Graphics2D) pg;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                g2.translate(0f, 0f);
                jPanelRaporty.paint(g2);

                return Printable.PAGE_EXISTS;
            }
        });

        if (printjob.printDialog() == false) {
            return;
        }

        try {
            printjob.print();
        } catch (PrinterException ex) {
            System.out.println("NO PAGE FOUND." + ex);
        }
    }

    public void printComponenet() {

        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setJobName(" Print Component ");

        pj.setPrintable(new Printable() {
            public int print(Graphics pg, PageFormat pf, int pageNum) {
                if (pageNum > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2 = (Graphics2D) pg;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                jPanelRaporty.paint(g2);
                return Printable.PAGE_EXISTS;
            }
        });
        if (pj.printDialog() == false) {
            return;
        }

        try {
            pj.print();
        } catch (PrinterException ex) {
            // handle exception
        }
    }
    private void jButtonRaportDrukujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRaportDrukujActionPerformed
        printCard();
        //printComponenet();
    }//GEN-LAST:event_jButtonRaportDrukujActionPerformed

    private void jButtonPrzPracownikUsunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrzPracownikUsunActionPerformed
        int selectedId = (int) jTablePrzPracownik.getModel().getValueAt(jTablePrzPracownik.getSelectedRow(), 0);
        km.deleteFromPracownikById(selectedId);
        jTablePrzPracownik.setModel(km.getPracownikDataTable());
    }//GEN-LAST:event_jButtonPrzPracownikUsunActionPerformed

    private void jTableZatwierdzanieMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableZatwierdzanieMouseClicked
        jButtonZatwierdzanieZmianZatwierdz.setEnabled(true);
        jButtonZatwierdzanieZmianOdrzuc.setEnabled(true);
    }//GEN-LAST:event_jTableZatwierdzanieMouseClicked

    private void jButtonZatwierdzanieZmianOdrzucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZatwierdzanieZmianOdrzucActionPerformed
        km.deleteFromDoZatwierdzenia((int) jTableZatwierdzanie.getModel().getValueAt(jTableZatwierdzanie.getSelectedRow(), 0));
    }//GEN-LAST:event_jButtonZatwierdzanieZmianOdrzucActionPerformed

    private void jButtonZatwierdzanieZmianZatwierdzActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZatwierdzanieZmianZatwierdzActionPerformed
        int selectedId = (int) jTableZatwierdzanie.getModel().getValueAt(jTableZatwierdzanie.getSelectedRow(), 0);
        Do_zatwierdzenia dz;
        dz = km.getDoZatwierdzeniaById(selectedId);
        Pracownik p;
        km.updatePracownik(dz);
        int id_uzytkownik = km.getUzytkownikIdByLogin(this.zalogowano_jako);
        km.updateDoZatwierdzenia(id_uzytkownik, dz);
        jTableZatwierdzanie.setModel(km.getZatwierdzanieDataTable());
    }//GEN-LAST:event_jButtonZatwierdzanieZmianZatwierdzActionPerformed

    private void jComboBoxRaportTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxRaportTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxRaportTypeActionPerformed

    private void jButtonDodawanieHistStanWybierzPracActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDodawanieHistStanWybierzPracActionPerformed
        showTabAtIndex(jPanelPrzegladaniePracownikow, 3);
        jTabbedPaneMain.setSelectedIndex(3);
    }//GEN-LAST:event_jButtonDodawanieHistStanWybierzPracActionPerformed

    private void jTabbedPaneMainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPaneMainMouseClicked
        if (jTablePrzPracownik.getSelectedRow() == -1) {
            jTextFieldDodawanieHistStanImieINazw.setText("nie wybrano");
        } else {
            int selectedId = (int) jTablePrzPracownik.getModel().getValueAt(jTablePrzPracownik.getSelectedRow(), 0);
            Pracownik p;
            p = km.getPracownikById(selectedId);
            jTextFieldDodawanieHistStanImieINazw.setText(p.getImie() + " " + p.getNazwisko());
        }
        jTableEdycjaHistStan.setModel(km.getPracownikHistStanoDataTable());
    }//GEN-LAST:event_jTabbedPaneMainMouseClicked

    private void jButtonDodawanieHistStanDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDodawanieHistStanDodajActionPerformed
        Historia h = null;
        if (jTablePrzPracownik.getSelectedRow() == -1) {
            jTextFieldDodawanieHistStanImieINazw.setText("nie wybrano");
        } else {
            int selectedId = (int) jTablePrzPracownik.getModel().getValueAt(jTablePrzPracownik.getSelectedRow(), 0);
            Pracownik p;
            p = km.getPracownikById(selectedId);
            jTextFieldDodawanieHistStanImieINazw.setText(p.getImie() + " " + p.getNazwisko());

            if (!jTextFieldDodawanieHistStanNazwa.getText().isEmpty()
                    && !jTextFieldDodawanieHistStanDataRozpoczecia.getText().isEmpty()
                    && !jTextFieldDodawanieHistStanDataZakonczenia.getText().isEmpty()) {
                try {
                    h = new Historia(0,
                            selectedId,
                            getDateInMilisecFromString(jTextFieldDodawanieHistStanDataRozpoczecia.getText()),
                            getDateInMilisecFromString(jTextFieldDodawanieHistStanDataZakonczenia.getText()),
                            jTextFieldDodawanieHistStanNazwa.getText());
                } catch (ParseException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (km.addHistoriaStanowiska(h) == 1) {
                    jLabelDodawanieHistStanDodano.setVisible(true);
                }
            } else {
                jLabelDodawanieHistStanError.setVisible(true);
            }

        }
    }//GEN-LAST:event_jButtonDodawanieHistStanDodajActionPerformed

    private void jTextFieldEdycjaHistStanSzukajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEdycjaHistStanSzukajActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEdycjaHistStanSzukajActionPerformed

    private void jButtonEdycjaHistStankSzukajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEdycjaHistStankSzukajActionPerformed
        if(jTextFieldEdycjaHistStanSzukaj.getText().isEmpty()){
            jTableEdycjaHistStan.setModel(null);
        }else{
            jTableEdycjaHistStan.setModel(km.getPracownikHistStanoDataTableWithQuery(jTextFieldEdycjaHistStanSzukaj.getText()));
        }
        
    }//GEN-LAST:event_jButtonEdycjaHistStankSzukajActionPerformed

    private void jTableEdycjaHistStanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableEdycjaHistStanMouseClicked
        //jButtonEdycjaHistStanEdytuj.setEnabled(true);
        jButtonEdycjaHistStanUsun.setEnabled(true);
        int selectedId = (int) jTableEdycjaHistStan.getModel().getValueAt(jTableEdycjaHistStan.getSelectedRow(), 0);
        Historia h = km.getHistoriaStanowiskPracownikById(selectedId);
        jTextFieldEdycjaHistStanNazwa.setText(h.getNazwa());
        jTextFieldEdycjaHistStanDataRozpoczecia.setText(dateLongToString(h.getData_rozpoczęcia()));
        jTextFieldEdycjaHistStanDataZakonczenia.setText(dateLongToString(h.getData_zakończenia()));
    }//GEN-LAST:event_jTableEdycjaHistStanMouseClicked

    private void jButtonEdycjaHistStanUsunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEdycjaHistStanUsunActionPerformed
        int selectedId = (int) jTableEdycjaHistStan.getModel().getValueAt(jTableEdycjaHistStan.getSelectedRow(), 0);
        Historia h;
        km.deleteFromHistoriaStanowiskaById(selectedId);
        jTableEdycjaHistStan.setModel(km.getPracownikHistStanoDataTable());
    }//GEN-LAST:event_jButtonEdycjaHistStanUsunActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPanelEdycjahistoriiStanowiskPracownikaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelEdycjahistoriiStanowiskPracownikaMouseClicked
        
    }//GEN-LAST:event_jPanelEdycjahistoriiStanowiskPracownikaMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonDodawanieHistStanDodaj;
    private javax.swing.JButton jButtonDodawanieHistStanWybierzPrac;
    private javax.swing.JButton jButtonEdStPodlDodaj;
    private javax.swing.JButton jButtonEdStPodlUsun;
    private javax.swing.JButton jButtonEdycjaHistStanEdytuj;
    private javax.swing.JButton jButtonEdycjaHistStanUsun;
    private javax.swing.JButton jButtonEdycjaHistStankSzukaj;
    private javax.swing.JButton jButtonKartoteka;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonLogowanie;
    private javax.swing.JButton jButtonNoweStanowiskoDodaj;
    private javax.swing.JButton jButtonNowy;
    private javax.swing.JButton jButtonNowyPracownikDodaj;
    private javax.swing.JButton jButtonNowyUzytkownikDodaj;
    private javax.swing.JButton jButtonPrzPracownikEdytujZatwierdz;
    private javax.swing.JButton jButtonPrzPracownikSzukaj;
    private javax.swing.JButton jButtonPrzPracownikUsun;
    private javax.swing.JButton jButtonPrzegladanieStanowiskUsun;
    private javax.swing.JButton jButtonRaportDrukuj;
    private javax.swing.JButton jButtonRaporty;
    private javax.swing.JButton jButtonZarzadzanie;
    private javax.swing.JButton jButtonZatwierdzanie;
    private javax.swing.JButton jButtonZatwierdzanieZmianOdrzuc;
    private javax.swing.JButton jButtonZatwierdzanieZmianZatwierdz;
    private javax.swing.JCheckBox jCheckBoxNowyPracownikStudent;
    private javax.swing.JCheckBox jCheckBoxPrzPracownikStudent;
    private javax.swing.JComboBox jComboBoxEdStPodlNazwa;
    private javax.swing.JComboBox jComboBoxNowyPracownikPlec;
    private javax.swing.JComboBox jComboBoxNowyPracownikStanowisko;
    private javax.swing.JComboBox jComboBoxPracownikSzukaj;
    private javax.swing.JComboBox jComboBoxPrzPracownikPlec;
    private javax.swing.JComboBox jComboBoxPrzPracownikStanowisko;
    private javax.swing.JComboBox jComboBoxRaportType;
    private javax.swing.JComboBox jComboBoxUprawnieniaAdd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelDodawanieHistStanDodano;
    private javax.swing.JLabel jLabelDodawanieHistStanError;
    private javax.swing.JLabel jLabelEdStPodlErrorDodaj;
    private javax.swing.JLabel jLabelEdStPodlErrorUsun;
    private javax.swing.JLabel jLabelLoginError;
    private javax.swing.JLabel jLabelNoweStanowiskoDodano;
    private javax.swing.JLabel jLabelNoweStanowiskoError;
    private javax.swing.JLabel jLabelNowyPracownikDodano;
    private javax.swing.JLabel jLabelNowyPracownikkError;
    private javax.swing.JLabel jLabelNowyUzytkownikDodano;
    private javax.swing.JLabel jLabelNowyUzytkownikError;
    private javax.swing.JLabel jLabelPrzPracownikPodlegleLabel;
    private javax.swing.JLabel jLabelPrzegladanieStanowiskError;
    private javax.swing.JLabel jLabelRaportData;
    private javax.swing.JLabel jLabelWarningHaslo;
    private javax.swing.JLabel jLabelWarningLogin;
    private javax.swing.JLabel jLabelZalogowanoJako;
    private javax.swing.JList jListEdStPodlegStanowiskaPodlegle;
    private javax.swing.JList jListEdStPodleglychStanowiska;
    private javax.swing.JList jListPrzPracownikPodlegleList;
    private javax.swing.JList jListPrzStanowiskStanowiskaPodlegle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelDodawanieHistoriiStanowisk;
    private javax.swing.JPanel jPanelEdycjaStanowiskPodleglych;
    private javax.swing.JPanel jPanelEdycjahistoriiStanowiskPracownika;
    private javax.swing.JPanel jPanelHistoriaZatwierdzonychZmian;
    private javax.swing.JPanel jPanelLogowanie;
    private javax.swing.JPanel jPanelNoweStanowisko;
    private javax.swing.JPanel jPanelNowyPracownik;
    private javax.swing.JPanel jPanelNowyUzytkownik;
    private javax.swing.JPanel jPanelPrzegladaniePracownikow;
    private javax.swing.JPanel jPanelPrzegladanieStanowisk;
    private javax.swing.JPanel jPanelPrzegladanieUzytkownikow;
    private javax.swing.JPanel jPanelRaporty;
    private javax.swing.JPanel jPanelZatwierdzanie;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPaneMain;
    private javax.swing.JTable jTableEdycjaHistStan;
    private javax.swing.JTable jTableHistoriaZatwierdzonychZmian;
    private javax.swing.JTable jTablePrzPracownik;
    private javax.swing.JTable jTablePrzStanowisko;
    private javax.swing.JTable jTablePrzUzytkownikow;
    private javax.swing.JTable jTableZatwierdzanie;
    private javax.swing.JTextField jTextFieldDodawanieHistStanDataRozpoczecia;
    private javax.swing.JTextField jTextFieldDodawanieHistStanDataZakonczenia;
    private javax.swing.JTextField jTextFieldDodawanieHistStanImieINazw;
    private javax.swing.JTextField jTextFieldDodawanieHistStanNazwa;
    private javax.swing.JTextField jTextFieldEdStPodlNazwa;
    private javax.swing.JTextField jTextFieldEdycjaHistStanDataRozpoczecia;
    private javax.swing.JTextField jTextFieldEdycjaHistStanDataZakonczenia;
    private javax.swing.JTextField jTextFieldEdycjaHistStanNazwa;
    private javax.swing.JTextField jTextFieldEdycjaHistStanSzukaj;
    private javax.swing.JTextField jTextFieldNoweStanowiskoNazwa;
    private javax.swing.JTextField jTextFieldNowyPracownikDataKoncaUmowy;
    private javax.swing.JTextField jTextFieldNowyPracownikDataUrodzenia;
    private javax.swing.JTextField jTextFieldNowyPracownikImie;
    private javax.swing.JTextField jTextFieldNowyPracownikNazwisko;
    private javax.swing.JTextField jTextFieldNowyPracownikPensja;
    private javax.swing.JTextField jTextFieldNowyPracownikPesel;
    private javax.swing.JTextField jTextFieldNowyPracownikTytul;
    private javax.swing.JTextField jTextFieldNowyUzytkownikHasloAdd;
    private javax.swing.JTextField jTextFieldNowyUzytkownikLoginAdd;
    private javax.swing.JTextField jTextFieldPrzPracoKoniecUmowy;
    private javax.swing.JTextField jTextFieldPrzPracownikDataPrzyjecia;
    private javax.swing.JTextField jTextFieldPrzPracownikDataUrodzenia;
    private javax.swing.JTextField jTextFieldPrzPracownikNazwisko;
    private javax.swing.JTextField jTextFieldPrzPracownikPensja;
    private javax.swing.JTextField jTextFieldPrzPracownikPesel;
    private javax.swing.JTextField jTextFieldPrzPracownikSzukaj;
    private javax.swing.JTextField jTextFieldPrzPracownikTytul;
    private javax.swing.JTextField jTextFieldPrzPracwonikImie;
    private javax.swing.JTextField jTextFieldPrzUzytDataUtworzenia;
    private javax.swing.JTextField jTextFieldPrzUzytHaslo;
    private javax.swing.JTextField jTextFieldPrzUzytkLogin;
    private javax.swing.JTextField jTextFieldPrzUzytkUprawnienia;
    private javax.swing.JTextField jTextFieldRaportDataKoncaUmowy;
    private javax.swing.JTextField jTextFieldRaportDataUrodzenia;
    private javax.swing.JTextField jTextFieldRaportDataZatrudnienia;
    private javax.swing.JTextField jTextFieldRaportImieINazwisko;
    private javax.swing.JTextField jTextFieldRaportPensja;
    private javax.swing.JTextField jTextFieldRaportPensjaSrednia;
    private javax.swing.JTextField jTextFieldRaportPesel;
    private javax.swing.JTextField jTextFieldRaportStanowisko;
    private javax.swing.JTextField jTextFieldRaportStudent;
    private javax.swing.JPasswordField jTextHaslo;
    private javax.swing.JTextField jTextLogin;
    private javax.swing.JPanel tlo;
    // End of variables declaration//GEN-END:variables

}
