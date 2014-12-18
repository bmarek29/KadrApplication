/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadrapplication;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    
    public Frame() {
        super("Start");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
        }
        setResizable(false);
        setLocationByPlatform(true);
        initComponents();

        initTabbedPaneList();
        
        //km.getPracownikData();
    }
    private void tabActionLogowanie(){
        hideTabs();
        showTabAtIndex(jPanelLogowanie, 0);
        jLabelWarningLogin.setVisible(false);
        jLabelWarningHaslo.setVisible(false);
        jLabelLoginError.setVisible(false);
        jLabelZalogowanoJako.setText(privilage);
    }
    
    private void tabActionNowy(){
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
    
    private void tabActionKartoteka(){
        hideTabs();
        showTabAtIndex(jPanelPrzegladaniePracownikow, 0);
        showTabAtIndex(jPanelPrzegladanieUzytkownikow, 1);  
        showTabAtIndex(jPanelPrzegladanieStanowisk, 2);    
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
        tabList.add(jPanel9);
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
    
    public DefaultComboBoxModel cmbPracownikPlec(){
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement("Mężczyzna");
        model.addElement("Kobieta");
        return model;
    }
    
    public DefaultComboBoxModel cmbPracownikStanowisko(){
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
    
    
    
    public long generateDateInMiliseconds(){
        Date d = new Date();
        long milliseconds = d.getTime();
        return milliseconds;
    }
    
    public Date changeLongToDate(long milliseconds){
        return new Date(milliseconds);
    }
    
    public long getDateInMilisecFromString(String s) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(s);
        long mili = date.getTime();        
        return mili;
    }
    
    public int getStanowiskoId(String nazwa){
        int id = km.getStanowiskoIdByNazwa(nazwa); 
        return id;
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
        jButtonUsuwanie = new javax.swing.JButton();
        jButtonKartoteka = new javax.swing.JButton();
        jButtonZarzadzanie = new javax.swing.JButton();
        jButtonDrukowanie = new javax.swing.JButton();
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
        jLabel3 = new javax.swing.JLabel();
        jLabelZalogowanoJako = new javax.swing.JLabel();
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
        jComboBox1 = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jComboBoxNowyPracownikPlec = new javax.swing.JComboBox();
        jCheckBoxNowyPracownikStudent = new javax.swing.JCheckBox();
        jLabelNowyPracownikkError = new javax.swing.JLabel();
        jLabelNowyPracownikDodano = new javax.swing.JLabel();
        jButtonNowyPracownikDodaj = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jPanelPrzegladanieUzytkownikow = new javax.swing.JPanel();
        jPanelPrzegladaniePracownikow = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanelPrzegladanieStanowisk = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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

        jButtonUsuwanie.setText("Usuwanie");
        getContentPane().add(jButtonUsuwanie, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 69, 110, 25));

        jButtonKartoteka.setText("Kartoteka");
        jButtonKartoteka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonKartotekaActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonKartoteka, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 98, 110, 25));

        jButtonZarzadzanie.setText("Zarządzanie");
        getContentPane().add(jButtonZarzadzanie, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 127, 110, 25));

        jButtonDrukowanie.setText("Drukowanie");
        getContentPane().add(jButtonDrukowanie, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 156, 110, 25));

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

        jLabel3.setText("Zalogowano jako:");

        jLabelZalogowanoJako.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelZalogowanoJako.setText("nie zalogowano");

        jLabelLoginError.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelLoginError.setForeground(new java.awt.Color(204, 0, 0));
        jLabelLoginError.setText("Nieprawidłowy login lub hasło.");

        javax.swing.GroupLayout jPanelLogowanieLayout = new javax.swing.GroupLayout(jPanelLogowanie);
        jPanelLogowanie.setLayout(jPanelLogowanieLayout);
        jPanelLogowanieLayout.setHorizontalGroup(
            jPanelLogowanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLogowanieLayout.createSequentialGroup()
                .addGroup(jPanelLogowanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLogowanieLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
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
                            .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelLogowanieLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelZalogowanoJako)))
                .addContainerGap(313, Short.MAX_VALUE))
        );
        jPanelLogowanieLayout.setVerticalGroup(
            jPanelLogowanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLogowanieLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanelLogowanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelWarningLogin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLogowanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextHaslo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelWarningHaslo))
                .addGap(18, 18, 18)
                .addComponent(jButtonLogin)
                .addGap(18, 18, 18)
                .addComponent(jLabelLoginError)
                .addGap(144, 144, 144)
                .addGroup(jPanelLogowanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabelZalogowanoJako))
                .addContainerGap())
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
                    .addComponent(jButtonNowyUzytkownikDodaj, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(jTextFieldNowyUzytkownikLoginAdd)
                    .addComponent(jTextFieldNowyUzytkownikHasloAdd)
                    .addComponent(jComboBoxUprawnieniaAdd, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanelNowyUzytkownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelNowyUzytkownikLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabelNowyUzytkownikError))
                    .addGroup(jPanelNowyUzytkownikLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelNowyUzytkownikDodano)))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        jPanelNowyUzytkownikLayout.setVerticalGroup(
            jPanelNowyUzytkownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNowyUzytkownikLayout.createSequentialGroup()
                .addContainerGap()
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
                .addGap(186, 186, 186))
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
                .addGroup(jPanelNoweStanowiskoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelNoweStanowiskoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel16)
                        .addGap(37, 37, 37)
                        .addComponent(jTextFieldNoweStanowiskoNazwa, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelNoweStanowiskoError))
                    .addGroup(jPanelNoweStanowiskoLayout.createSequentialGroup()
                        .addGap(265, 265, 265)
                        .addComponent(jLabelNoweStanowiskoDodano)))
                .addContainerGap(297, Short.MAX_VALUE))
            .addGroup(jPanelNoweStanowiskoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelNoweStanowiskoLayout.createSequentialGroup()
                    .addContainerGap(112, Short.MAX_VALUE)
                    .addComponent(jButtonNoweStanowiskoDodaj, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(446, 446, 446)))
        );
        jPanelNoweStanowiskoLayout.setVerticalGroup(
            jPanelNoweStanowiskoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNoweStanowiskoLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanelNoweStanowiskoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextFieldNoweStanowiskoNazwa, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNoweStanowiskoError))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                .addComponent(jLabelNoweStanowiskoDodano)
                .addGap(135, 135, 135))
            .addGroup(jPanelNoweStanowiskoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelNoweStanowiskoLayout.createSequentialGroup()
                    .addGap(193, 193, 193)
                    .addComponent(jButtonNoweStanowiskoDodaj, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(110, Short.MAX_VALUE)))
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

        jComboBox1.setModel(cmbPracownikStanowisko());

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

        javax.swing.GroupLayout jPanelNowyPracownikLayout = new javax.swing.GroupLayout(jPanelNowyPracownik);
        jPanelNowyPracownik.setLayout(jPanelNowyPracownikLayout);
        jPanelNowyPracownikLayout.setHorizontalGroup(
            jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                .addContainerGap()
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
                            .addComponent(jTextFieldNowyPracownikPensja, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(43, 43, 43)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelNowyPracownikkError)
                            .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                                .addComponent(jButtonNowyPracownikDodaj)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelNowyPracownikDodano))))
                    .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)))
                .addContainerGap(104, Short.MAX_VALUE))
        );
        jPanelNowyPracownikLayout.setVerticalGroup(
            jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNowyPracownikImie, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel14)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jTabbedPaneMain.addTab("Nowy pracownik", jPanelNowyPracownik);

        jPanelPrzegladanieUzytkownikow.setName("Przeglądanie użytkowników"); // NOI18N

        javax.swing.GroupLayout jPanelPrzegladanieUzytkownikowLayout = new javax.swing.GroupLayout(jPanelPrzegladanieUzytkownikow);
        jPanelPrzegladanieUzytkownikow.setLayout(jPanelPrzegladanieUzytkownikowLayout);
        jPanelPrzegladanieUzytkownikowLayout.setHorizontalGroup(
            jPanelPrzegladanieUzytkownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 695, Short.MAX_VALUE)
        );
        jPanelPrzegladanieUzytkownikowLayout.setVerticalGroup(
            jPanelPrzegladanieUzytkownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 336, Short.MAX_VALUE)
        );

        jTabbedPaneMain.addTab("Przeglądanie użytkowników", jPanelPrzegladanieUzytkownikow);

        jPanelPrzegladaniePracownikow.setName("Przeglądanie pracowników"); // NOI18N

        jTable2.setModel(km.getPracownikDataTable());
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 836, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout jPanelPrzegladaniePracownikowLayout = new javax.swing.GroupLayout(jPanelPrzegladaniePracownikow);
        jPanelPrzegladaniePracownikow.setLayout(jPanelPrzegladaniePracownikowLayout);
        jPanelPrzegladaniePracownikowLayout.setHorizontalGroup(
            jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelPrzegladaniePracownikowLayout.setVerticalGroup(
            jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPaneMain.addTab("Przeglądanie pracowników", jPanelPrzegladaniePracownikow);

        jPanelPrzegladanieStanowisk.setName("Przeglądanie stanowisk"); // NOI18N

        javax.swing.GroupLayout jPanelPrzegladanieStanowiskLayout = new javax.swing.GroupLayout(jPanelPrzegladanieStanowisk);
        jPanelPrzegladanieStanowisk.setLayout(jPanelPrzegladanieStanowiskLayout);
        jPanelPrzegladanieStanowiskLayout.setHorizontalGroup(
            jPanelPrzegladanieStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 695, Short.MAX_VALUE)
        );
        jPanelPrzegladanieStanowiskLayout.setVerticalGroup(
            jPanelPrzegladanieStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 336, Short.MAX_VALUE)
        );

        jTabbedPaneMain.addTab("Przeglądanie stanowisk", jPanelPrzegladanieStanowisk);

        jPanel9.setName("..."); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 695, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 336, Short.MAX_VALUE)
        );

        jTabbedPaneMain.addTab("...", jPanel9);

        tlo.add(jTabbedPaneMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 700, 380));

        getContentPane().add(tlo, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 11, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNowyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNowyActionPerformed
        tabActionNowy();
    }//GEN-LAST:event_jButtonNowyActionPerformed

    private void jButtonZatwierdzanieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZatwierdzanieActionPerformed

    }//GEN-LAST:event_jButtonZatwierdzanieActionPerformed

    private void jButtonLogowanieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogowanieActionPerformed
        initTabbedPaneList();
    }//GEN-LAST:event_jButtonLogowanieActionPerformed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        if (jTextLogin.getText().length() == 0)
        jLabelWarningLogin.setVisible(true);
        if (String.valueOf(jTextHaslo.getPassword()).length() == 0)
        jLabelWarningHaslo.setVisible(true);
        if (jTextLogin.getText().length() != 0 && String.valueOf(jTextHaslo.getPassword()).length() != 0){
            if (km.checkUserInDB(jTextLogin.getText(), String.valueOf(jTextHaslo.getPassword()))){
                this.privilage = km.getUserPrivilage(jTextLogin.getText());
                jLabelZalogowanoJako.setText(this.privilage);
                jLabelLoginError.setVisible(false);
                jLabelWarningHaslo.setVisible(false);
                jLabelWarningLogin.setVisible(false);
            }else{
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
        if(jTextFieldNowyUzytkownikLoginAdd.getText().length() == 0){
            jTextFieldNowyUzytkownikLoginAdd.setBackground(Color.red);
            jLabelNowyUzytkownikError.setVisible(true);
        }else{
            jTextFieldNowyUzytkownikLoginAdd.setBackground(Color.white);
        }
        if(jTextFieldNowyUzytkownikHasloAdd.getText().length() == 0){
            jTextFieldNowyUzytkownikHasloAdd.setBackground(Color.red);
            jLabelNowyUzytkownikError.setVisible(true);
        }else{
            jTextFieldNowyUzytkownikHasloAdd.setBackground(Color.white);
        }
        if(jTextFieldNowyUzytkownikLoginAdd.getText().length() != 0 && jTextFieldNowyUzytkownikHasloAdd.getText().length() != 0){
            u.setData_utworzenia(generateDateInMiliseconds());
            u.setHaslo(jTextFieldNowyUzytkownikHasloAdd.getText());
            u.setLogin(jTextFieldNowyUzytkownikLoginAdd.getText());
            u.setUprawnienia(jComboBoxUprawnieniaAdd.getSelectedItem().toString());
            try {
                if(km.addUzytkownik(u) == 1){
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
        if(jTextFieldNoweStanowiskoNazwa.getText().length() == 0){
            jLabelNoweStanowiskoError.setVisible(true);
        }else{
            s.setNazwa(jTextFieldNoweStanowiskoNazwa.getText());
            try {
                if(km.addStanowisko(s) == 1){
                    jLabelNoweStanowiskoDodano.setVisible(true);
                    jLabelNoweStanowiskoError.setVisible(false);
                    jComboBox1.setModel(cmbPracownikStanowisko());
                }
            } catch (SQLException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
            
        
    }//GEN-LAST:event_jButtonNoweStanowiskoDodajActionPerformed

    private void jButtonNowyPracownikDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNowyPracownikDodajActionPerformed
        boolean error = false;
        long dataUr = 0;
        int student = 0;
        if(jTextFieldNowyPracownikImie.getText().length() == 0){
            jTextFieldNowyPracownikImie.setBackground(Color.red);
            jLabelNowyPracownikkError.setVisible(true);
            error = true;
        }else{
            jTextFieldNowyPracownikImie.setBackground(Color.white);
        }
        if(jTextFieldNowyPracownikNazwisko.getText().length() == 0){
            jTextFieldNowyPracownikNazwisko.setBackground(Color.red);
            jLabelNowyPracownikkError.setVisible(true);
            error = true;
        }else{
            jTextFieldNowyPracownikNazwisko.setBackground(Color.white);
        }
        if(jTextFieldNowyPracownikDataUrodzenia.getText().length() == 0){
            jTextFieldNowyPracownikDataUrodzenia.setBackground(Color.red);
            jLabelNowyPracownikkError.setVisible(true);
            error = true;
        }else{
            jTextFieldNowyPracownikDataUrodzenia.setBackground(Color.white);
        }        
        if(jTextFieldNowyPracownikPesel.getText().length() == 0){
            jTextFieldNowyPracownikPesel.setBackground(Color.red);
            jLabelNowyPracownikkError.setVisible(true);
            error = true;
        }else{
            jTextFieldNowyPracownikPesel.setBackground(Color.white);
        }
        if(jTextFieldNowyPracownikPensja.getText().length() == 0){
            jTextFieldNowyPracownikPensja.setBackground(Color.red);
            jLabelNowyPracownikkError.setVisible(true);
            error = true;
        }else{
            jTextFieldNowyPracownikPensja.setBackground(Color.white);
        }
        if(!error){
            if(jCheckBoxNowyPracownikStudent.isSelected()){
                student = 0;
            }else{
                student = 1;
            }
            try {
                dataUr = getDateInMilisecFromString(jTextFieldNowyPracownikDataUrodzenia.getText());
            } catch (ParseException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
             
            Pracownik p = new Pracownik(
                    0,
                    getStanowiskoId(cmbPracownikStanowisko().getSelectedItem().toString()),
                    jTextFieldNowyPracownikImie.getText(),
                    jTextFieldNowyPracownikNazwisko.getText(),
                    dataUr,
                    cmbPracownikPlec().getSelectedItem().toString(),
                    jTextFieldNowyPracownikTytul.getText(),
                    jTextFieldNowyPracownikPesel.getText(),
                    student,
                    generateDateInMiliseconds(),
                    Integer.valueOf(jTextFieldNowyPracownikPensja.getText())
            );
            if(km.addPracownik(p) == 1){
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
    }//GEN-LAST:event_jButtonKartotekaActionPerformed

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
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JButton jButtonDrukowanie;
    private javax.swing.JButton jButtonKartoteka;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonLogowanie;
    private javax.swing.JButton jButtonNoweStanowiskoDodaj;
    private javax.swing.JButton jButtonNowy;
    private javax.swing.JButton jButtonNowyPracownikDodaj;
    private javax.swing.JButton jButtonNowyUzytkownikDodaj;
    private javax.swing.JButton jButtonUsuwanie;
    private javax.swing.JButton jButtonZarzadzanie;
    private javax.swing.JButton jButtonZatwierdzanie;
    private javax.swing.JCheckBox jCheckBoxNowyPracownikStudent;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBoxNowyPracownikPlec;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelLoginError;
    private javax.swing.JLabel jLabelNoweStanowiskoDodano;
    private javax.swing.JLabel jLabelNoweStanowiskoError;
    private javax.swing.JLabel jLabelNowyPracownikDodano;
    private javax.swing.JLabel jLabelNowyPracownikkError;
    private javax.swing.JLabel jLabelNowyUzytkownikDodano;
    private javax.swing.JLabel jLabelNowyUzytkownikError;
    private javax.swing.JLabel jLabelWarningHaslo;
    private javax.swing.JLabel jLabelWarningLogin;
    private javax.swing.JLabel jLabelZalogowanoJako;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelLogowanie;
    private javax.swing.JPanel jPanelNoweStanowisko;
    private javax.swing.JPanel jPanelNowyPracownik;
    private javax.swing.JPanel jPanelNowyUzytkownik;
    private javax.swing.JPanel jPanelPrzegladaniePracownikow;
    private javax.swing.JPanel jPanelPrzegladanieStanowisk;
    private javax.swing.JPanel jPanelPrzegladanieUzytkownikow;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPaneMain;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextFieldNoweStanowiskoNazwa;
    private javax.swing.JTextField jTextFieldNowyPracownikDataUrodzenia;
    private javax.swing.JTextField jTextFieldNowyPracownikImie;
    private javax.swing.JTextField jTextFieldNowyPracownikNazwisko;
    private javax.swing.JTextField jTextFieldNowyPracownikPensja;
    private javax.swing.JTextField jTextFieldNowyPracownikPesel;
    private javax.swing.JTextField jTextFieldNowyPracownikTytul;
    private javax.swing.JTextField jTextFieldNowyUzytkownikHasloAdd;
    private javax.swing.JTextField jTextFieldNowyUzytkownikLoginAdd;
    private javax.swing.JPasswordField jTextHaslo;
    private javax.swing.JTextField jTextLogin;
    private javax.swing.JPanel tlo;
    // End of variables declaration//GEN-END:variables
}
