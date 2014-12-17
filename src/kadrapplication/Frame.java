/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadrapplication;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

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
        showTabAtIndex(jPanelNoweStanowiskoPodlegle, 3);
        jLabelNowyUzytkownikError.setVisible(false);
        jLabelNowyUzytkownikDodano.setVisible(false);
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
        tabList.add(jPanelNoweStanowiskoPodlegle);
        tabList.add(jPanelNoweStanowisko);
        tabList.add(jPanel6);
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
        jTextFieldLoginAdd = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldHasloAdd = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButtonNowyUzytkownikDodaj = new javax.swing.JButton();
        jLabelNowyUzytkownikError = new javax.swing.JLabel();
        jLabelNowyUzytkownikDodano = new javax.swing.JLabel();
        jComboBoxUprawnieniaAdd = new javax.swing.JComboBox();
        jPanelNoweStanowisko = new javax.swing.JPanel();
        jPanelNoweStanowiskoPodlegle = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanelNowyPracownik = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();

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
                    .addComponent(jTextFieldLoginAdd)
                    .addComponent(jTextFieldHasloAdd)
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
                    .addComponent(jTextFieldLoginAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNowyUzytkownikError)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelNowyUzytkownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldHasloAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanelNowyUzytkownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNowyUzytkownikDodaj, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNowyUzytkownikDodano))
                .addGap(186, 186, 186))
        );

        jTabbedPaneMain.addTab("Nowy użytkownik", jPanelNowyUzytkownik);

        jPanelNoweStanowisko.setName("Nowe stanowisko"); // NOI18N

        javax.swing.GroupLayout jPanelNoweStanowiskoLayout = new javax.swing.GroupLayout(jPanelNoweStanowisko);
        jPanelNoweStanowisko.setLayout(jPanelNoweStanowiskoLayout);
        jPanelNoweStanowiskoLayout.setHorizontalGroup(
            jPanelNoweStanowiskoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 695, Short.MAX_VALUE)
        );
        jPanelNoweStanowiskoLayout.setVerticalGroup(
            jPanelNoweStanowiskoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 352, Short.MAX_VALUE)
        );

        jTabbedPaneMain.addTab("Nowe stanowisko", jPanelNoweStanowisko);

        jPanelNoweStanowiskoPodlegle.setName("Nowe stanowisko podległe"); // NOI18N

        javax.swing.GroupLayout jPanelNoweStanowiskoPodlegleLayout = new javax.swing.GroupLayout(jPanelNoweStanowiskoPodlegle);
        jPanelNoweStanowiskoPodlegle.setLayout(jPanelNoweStanowiskoPodlegleLayout);
        jPanelNoweStanowiskoPodlegleLayout.setHorizontalGroup(
            jPanelNoweStanowiskoPodlegleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 695, Short.MAX_VALUE)
        );
        jPanelNoweStanowiskoPodlegleLayout.setVerticalGroup(
            jPanelNoweStanowiskoPodlegleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 352, Short.MAX_VALUE)
        );

        jTabbedPaneMain.addTab("Nowe stanowisko podległe", jPanelNoweStanowiskoPodlegle);

        jPanel6.setName("..."); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 695, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 352, Short.MAX_VALUE)
        );

        jTabbedPaneMain.addTab("...", jPanel6);

        jPanelNowyPracownik.setName("Nowy pracownik"); // NOI18N

        jLabel7.setText("Imię");

        jLabel8.setText("Nazwisko");

        jLabel9.setText("jLabel9");

        jLabel10.setText("Data urodzenia");

        jLabel11.setText("Tytuł");

        jLabel12.setText("Pesel");

        jLabel13.setText("Student");

        jTextField1.setText("jTextField1");

        jTextField2.setText("jTextField1");

        jTextField3.setText("jTextField1");

        jTextField4.setText("jTextField1");

        jTextField5.setText("jTextField1");

        jTextField6.setText("jTextField1");

        jLabel14.setText("jLabel14");

        jTextField8.setText("jTextField1");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel15.setText("Płeć");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanelNowyPracownikLayout = new javax.swing.GroupLayout(jPanelNowyPracownik);
        jPanelNowyPracownik.setLayout(jPanelNowyPracownikLayout);
        jPanelNowyPracownikLayout.setHorizontalGroup(
            jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(67, 67, 67)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelNowyPracownikLayout.createSequentialGroup()
                        .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelNowyPracownikLayout.createSequentialGroup()
                        .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(181, 181, 181))
        );
        jPanelNowyPracownikLayout.setVerticalGroup(
            jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNowyPracownikLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelNowyPracownikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jTabbedPaneMain.addTab("Nowy pracownik", jPanelNowyPracownik);

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
    private javax.swing.JButton jButtonNowy;
    private javax.swing.JButton jButtonNowyUzytkownikDodaj;
    private javax.swing.JButton jButtonUsuwanie;
    private javax.swing.JButton jButtonZarzadzanie;
    private javax.swing.JButton jButtonZatwierdzanie;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBoxUprawnieniaAdd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelLoginError;
    private javax.swing.JLabel jLabelNowyUzytkownikDodano;
    private javax.swing.JLabel jLabelNowyUzytkownikError;
    private javax.swing.JLabel jLabelWarningHaslo;
    private javax.swing.JLabel jLabelWarningLogin;
    private javax.swing.JLabel jLabelZalogowanoJako;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelLogowanie;
    private javax.swing.JPanel jPanelNoweStanowisko;
    private javax.swing.JPanel jPanelNoweStanowiskoPodlegle;
    private javax.swing.JPanel jPanelNowyPracownik;
    private javax.swing.JPanel jPanelNowyUzytkownik;
    private javax.swing.JTabbedPane jTabbedPaneMain;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextFieldHasloAdd;
    private javax.swing.JTextField jTextFieldLoginAdd;
    private javax.swing.JPasswordField jTextHaslo;
    private javax.swing.JTextField jTextLogin;
    private javax.swing.JPanel tlo;
    // End of variables declaration//GEN-END:variables
}
