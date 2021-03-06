package kadrapplication;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;

public class Frame extends JFrame {

    private ArrayList<JPanel> tabList = new ArrayList<>();
    KadrManager km = new KadrManager();
    private String privilege = "niezalogowany";
    private String zalogowano_jako = null;

    public Frame() {
        super("Start");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("look&feel: " + e);
        }
        setResizable(false);
        setLocationByPlatform(true);
        initComponents();

        initTabbedPaneList();

        go();
    }
    private void go() {
        Runnable helloRunnable = new Runnable() {
            public void run() {
                jTableEdycjaHistStan.setModel(km.getPracownikHistStanoDataTable());
                jTableHistoriaZatwierdzonychZmian.setModel(km.getHistoriaZmianZatwierdzaniaTable());
                jTablePrzPracownik.setModel(km.getPracownikDataTable());
                jTablePrzStanowisko.setModel(km.getStanowiskoDataTable());
                jTablePrzUzytkownikow.setModel(km.getUzytkownikDataTable());
                jTableZatwierdzanie.setModel(km.getZatwierdzanieDataTable());                
                jListEdStPodleglychStanowiska.setModel(km.getAllStanowiskoListModel());
            }
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 0, 300, TimeUnit.SECONDS);

    }

    private void showButtonsAfterLogin(boolean b) {

        jButtonKartoteka.setVisible(b);
        jButtonZarzadzanie.setVisible(b);
        if (this.privilege.equals("pracownik_kadr")) {
            jButtonZatwierdzanie.setVisible(false);
            jButtonNowy.setVisible(false);
        } else {
            jButtonNowy.setVisible(b);
            jButtonZatwierdzanie.setVisible(b);
        }
    }

    private void tabActionZatwierdzanie() {
        hideTabs();
        showTabAtIndex(jPanelZatwierdzanie, 0);
        if (this.privilege.equals("administrator")) {
            showTabAtIndex(jPanelHistoriaZatwierdzonychZmian, 1);
        }
        jButtonZatwierdzanieZmianZatwierdz.setEnabled(false);
        jButtonZatwierdzanieZmianOdrzuc.setEnabled(false);
    }

    private void tabActionLogowanie() {
        hideTabs();
        showTabAtIndex(jPanelLogowanie, 0);
        jLabelWarningLogin.setVisible(false);
        jLabelWarningHaslo.setVisible(false);
        jLabelLoginError.setVisible(false);
        jLabelZalogowanoJako.setText(this.privilege);
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
        if (this.privilege.equals("administrator")) {
            jButtonPrzPracownikUsun.setVisible(true);
            showTabAtIndex(jPanelPrzegladanieUzytkownikow, 1);
            showTabAtIndex(jPanelPrzegladanieStanowisk, 2);
            jLabelPrzegladanieStanowiskError.setVisible(false);
        } else {
            jButtonPrzPracownikUsun.setVisible(false);
        }
        jButtonRaporty.setVisible(false);
        jButtonPrzPracownikUsun.setEnabled(false);
        jButtonPrzPracownikEdytujZatwierdz.setEnabled(false);
    }

    private void tabActionZarzadzanie() {
        hideTabs();
        showTabAtIndex(jPanelEdycjahistoriiStanowiskPracownika, 0);
        if (this.privilege.equals("administrator")) {
            jButtonEdycjaHistStanUsun.setVisible(true);
            showTabAtIndex(jPanelEdycjaStanowiskPodleglych, 1);
            showTabAtIndex(jPanelDodawanieHistoriiStanowisk, 2);
        } else {
            jButtonEdycjaHistStanUsun.setVisible(false);
        }
        jLabelEdStPodlErrorDodaj.setVisible(false);
        jLabelEdStPodlErrorUsun.setVisible(false);
        jLabelDodawanieHistStanDodano.setVisible(false);
        jLabelDodawanieHistStanError.setVisible(false);
        jLabelEdycjaHistStanError.setVisible(false);
    }

    private void initTabbedPaneList() {
        tabList.clear();
        fillTabList();
        tabActionLogowanie();
        showButtonsAfterLogin(false);
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
        tabList.add(jPanelZatwierdzanie);
        tabList.add(jPanelHistoriaZatwierdzonychZmian);
        tabList.add(jPanelDodawanieHistoriiStanowisk);
        tabList.add(jPanelRaporty);
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
        if (!this.privilege.equals("pracownik_kadr")) {
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
        switch (this.privilege) {
            case "administrator":
                jTextFieldPrzPracownikImie.setEditable(b);
                jTextFieldPrzPracownikNazwisko.setEditable(b);
                jTextFieldPrzPracownikDataPrzyjecia.setEditable(b);
                jTextFieldPrzPracownikDataUrodzenia.setEditable(b);
                jTextFieldPrzPracownikKoniecUmowy.setEditable(b);
                jTextFieldPrzPracownikPesel.setEditable(b);
                jTextFieldPrzPracownikTytul.setEditable(b);
                jComboBoxPrzPracownikPlec.setEnabled(b);
                jComboBoxPrzPracownikStanowisko.setEnabled(b);
                jCheckBoxPrzPracownikStudent.setEnabled(b);
                jTextFieldPrzPracownikPensja.setEditable(b);
                break;
            case "kierownik_kadr":
                jTextFieldPrzPracownikNazwisko.setEditable(b);
                jTextFieldPrzPracownikTytul.setEditable(b);
                jComboBoxPrzPracownikStanowisko.setEnabled(b);
                jTextFieldPrzPracownikDataPrzyjecia.setEditable(b);
                jCheckBoxPrzPracownikStudent.setEnabled(b);
                jTextFieldPrzPracownikPensja.setEditable(b);
                break;
            default:
                jTextFieldPrzPracownikNazwisko.setEditable(b);
                jTextFieldPrzPracownikTytul.setEditable(b);
                jComboBoxPrzPracownikStanowisko.setEnabled(b);
                jTextFieldPrzPracownikDataPrzyjecia.setEditable(b);
                jCheckBoxPrzPracownikStudent.setEnabled(b);
                break;
        }
    }

    private void disableAllPracownikTextFields(boolean b) {
        jTextFieldPrzPracownikImie.setEditable(b);
        jTextFieldPrzPracownikNazwisko.setEditable(b);
        jTextFieldPrzPracownikDataPrzyjecia.setEditable(b);
        jTextFieldPrzPracownikDataUrodzenia.setEditable(b);
        jTextFieldPrzPracownikKoniecUmowy.setEditable(b);
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
        jTextFieldPrzUzytkLogin = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTextFieldPrzUzytkUprawnienia = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTextFieldPrzUzytDataUtworzenia = new javax.swing.JTextField();
        jTextFieldPrzUzytHaslo = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jButtonPrzUzytkownikowEdytuj = new javax.swing.JButton();
        jButtonPrzUzytkownikowUsun = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTablePrzUzytkownikow = new javax.swing.JTable();
        jPanelPrzegladaniePracownikow = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldPrzPracownikSzukaj = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTextFieldPrzPracownikImie = new javax.swing.JTextField();
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
        jTextFieldPrzPracownikKoniecUmowy = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablePrzPracownik = new javax.swing.JTable();
        jPanelPrzegladanieStanowisk = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jListPrzStanowiskStanowiskaPodlegle = new javax.swing.JList();
        jLabel32 = new javax.swing.JLabel();
        jButtonPrzegladanieStanowiskUsun = new javax.swing.JButton();
        jLabelPrzegladanieStanowiskError = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTablePrzStanowisko = new javax.swing.JTable();
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
        jLabelEdycjaHistStanError = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTableEdycjaHistStan = new javax.swing.JTable();
        jPanelZatwierdzanie = new javax.swing.JPanel();
        jButtonZatwierdzanieZmianZatwierdz = new javax.swing.JButton();
        jButtonZatwierdzanieZmianOdrzuc = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTableZatwierdzanie = new javax.swing.JTable();
        jPanelHistoriaZatwierdzonychZmian = new javax.swing.JPanel();
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
        jPanelRaporty = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jPanel7 = new javax.swing.JPanel();
        jPanelRaportv2 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jComboBoxRaportType = new javax.swing.JComboBox();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabelRaportTytulRaportu = new javax.swing.JLabel();
        jTextFieldRaportImieINazwisko = new javax.swing.JTextField();
        jTextFieldRaportPesel = new javax.swing.JTextField();
        jTextFieldRaportStudent = new javax.swing.JTextField();
        jTextFieldRaportPensja = new javax.swing.JTextField();
        jTextFieldRaportPensjaSrednia = new javax.swing.JTextField();
        jLabelRaportSrednieWynagrodzenie = new javax.swing.JLabel();
        jLabelRaportPensja = new javax.swing.JLabel();
        jLabelRaportStudent = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabelRaportDataZatrudnienia = new javax.swing.JLabel();
        jLabelRaportDataKoncaUmowy = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jTextFieldRaportStanowisko = new javax.swing.JTextField();
        jTextFieldRaportDataKoncaUmowy = new javax.swing.JTextField();
        jTextFieldRaportDataZatrudnienia = new javax.swing.JTextField();
        jTextFieldRaportDataUrodzenia = new javax.swing.JTextField();
        jButtonRaportDrukuj = new javax.swing.JButton();
        jLabel83 = new javax.swing.JLabel();
        jLabelRaportData = new javax.swing.JLabel();
        jLabelRaportHistoriaStanowisk = new javax.swing.JLabel();
        jPanelRaportTable = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        jTableRaport = new javax.swing.JTable();
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jButtonPrzUzytkownikowEdytuj.setText("Edytuj");
        jButtonPrzUzytkownikowEdytuj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrzUzytkownikowEdytujActionPerformed(evt);
            }
        });

        jButtonPrzUzytkownikowUsun.setText("Usuń");
        jButtonPrzUzytkownikowUsun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrzUzytkownikowUsunActionPerformed(evt);
            }
        });

        jTablePrzUzytkownikow.setModel(km.getUzytkownikDataTable());
        jTablePrzUzytkownikow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePrzUzytkownikowMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTablePrzUzytkownikow);

        javax.swing.GroupLayout jPanelPrzegladanieUzytkownikowLayout = new javax.swing.GroupLayout(jPanelPrzegladanieUzytkownikow);
        jPanelPrzegladanieUzytkownikow.setLayout(jPanelPrzegladanieUzytkownikowLayout);
        jPanelPrzegladanieUzytkownikowLayout.setHorizontalGroup(
            jPanelPrzegladanieUzytkownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrzegladanieUzytkownikowLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelPrzegladanieUzytkownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(jLabel33)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37)
                    .addComponent(jButtonPrzUzytkownikowEdytuj, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanelPrzegladanieUzytkownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonPrzUzytkownikowUsun, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPrzUzytkUprawnienia, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPrzUzytkLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPrzUzytDataUtworzenia, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPrzUzytHaslo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );
        jPanelPrzegladanieUzytkownikowLayout.setVerticalGroup(
            jPanelPrzegladanieUzytkownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrzegladanieUzytkownikowLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPrzegladanieUzytkownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPrzegladanieUzytkownikowLayout.createSequentialGroup()
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
                            .addComponent(jButtonPrzUzytkownikowEdytuj)
                            .addComponent(jButtonPrzUzytkownikowUsun))
                        .addGap(0, 129, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPaneMain.addTab("Przeglądanie użytkowników", jPanelPrzegladanieUzytkownikow);

        jPanelPrzegladaniePracownikow.setName("Przeglądanie pracowników"); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("Szukaj");

        jLabel19.setText("Tekst wyszukiwany");

        jTextFieldPrzPracownikSzukaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPrzPracownikSzukajActionPerformed(evt);
            }
        });

        jLabel22.setText("Imie");

        jTextFieldPrzPracownikImie.setEditable(false);
        jTextFieldPrzPracownikImie.setPreferredSize(new java.awt.Dimension(80, 20));

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

        jButtonPrzPracownikEdytujZatwierdz.setText("Edytuj");
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

        jTextFieldPrzPracownikKoniecUmowy.setEditable(false);

        jTablePrzPracownik.setModel(km.getPracownikDataTable());
        jTablePrzPracownik.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePrzPracownikMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTablePrzPracownik);

        javax.swing.GroupLayout jPanelPrzegladaniePracownikowLayout = new javax.swing.GroupLayout(jPanelPrzegladaniePracownikow);
        jPanelPrzegladaniePracownikow.setLayout(jPanelPrzegladaniePracownikowLayout);
        jPanelPrzegladaniePracownikowLayout.setHorizontalGroup(
            jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                .addGap(73, 73, 73)
                                .addComponent(jButtonRaporty, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
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
                                        .addComponent(jTextFieldPrzPracownikImie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                .addComponent(jTextFieldPrzPracownikKoniecUmowy)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelPrzPracownikPodlegleLabel)
                        .addGap(44, 44, 44))
                    .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                .addComponent(jButtonPrzPracownikEdytujZatwierdz, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
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
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelPrzegladaniePracownikowLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldPrzPracownikImie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(jTextFieldPrzPracownikKoniecUmowy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPrzegladaniePracownikowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonPrzPracownikEdytujZatwierdz, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonPrzPracownikUsun))))
                .addContainerGap())
        );

        jTabbedPaneMain.addTab("Przeglądanie pracowników", jPanelPrzegladaniePracownikow);

        jPanelPrzegladanieStanowisk.setName("Przeglądanie stanowisk"); // NOI18N

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

        jTablePrzStanowisko.setModel(km.getStanowiskoDataTable());
        jTablePrzStanowisko.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePrzStanowiskoMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jTablePrzStanowisko);

        javax.swing.GroupLayout jPanelPrzegladanieStanowiskLayout = new javax.swing.GroupLayout(jPanelPrzegladanieStanowisk);
        jPanelPrzegladanieStanowisk.setLayout(jPanelPrzegladanieStanowiskLayout);
        jPanelPrzegladanieStanowiskLayout.setHorizontalGroup(
            jPanelPrzegladanieStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrzegladanieStanowiskLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(jPanelPrzegladanieStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPrzegladanieStanowiskError)
                    .addComponent(jButtonPrzegladanieStanowiskUsun, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelPrzegladanieStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanelPrzegladanieStanowiskLayout.setVerticalGroup(
            jPanelPrzegladanieStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrzegladanieStanowiskLayout.createSequentialGroup()
                .addContainerGap(138, Short.MAX_VALUE)
                .addComponent(jButtonPrzegladanieStanowiskUsun, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPrzegladanieStanowiskError)
                .addGap(132, 132, 132))
            .addGroup(jPanelPrzegladanieStanowiskLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanelPrzegladanieStanowiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanelPrzegladanieStanowiskLayout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane8)))
                .addContainerGap())
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                        .addGap(53, 53, 53))
                    .addGroup(jPanelEdycjaStanowiskPodleglychLayout.createSequentialGroup()
                        .addGroup(jPanelEdycjaStanowiskPodleglychLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                            .addComponent(jScrollPane9))
                        .addGap(22, 22, 22))))
        );

        jTabbedPaneMain.addTab("Edycja stanowisk podległych", jPanelEdycjaStanowiskPodleglych);

        jPanelEdycjahistoriiStanowiskPracownika.setName("Edycja historii stanowisk pracownika"); // NOI18N
        jPanelEdycjahistoriiStanowiskPracownika.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelEdycjahistoriiStanowiskPracownikaMouseClicked(evt);
            }
        });

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

        jTextFieldEdycjaHistStanNazwa.setEditable(false);

        jLabel68.setText("Data rozpoczęcia");

        jTextFieldEdycjaHistStanDataRozpoczecia.setEditable(false);

        jLabel69.setText("Data zakończenia");

        jTextFieldEdycjaHistStanDataZakonczenia.setEditable(false);

        jButtonEdycjaHistStanEdytuj.setText("Edytuj");
        jButtonEdycjaHistStanEdytuj.setEnabled(false);
        jButtonEdycjaHistStanEdytuj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEdycjaHistStanEdytujActionPerformed(evt);
            }
        });

        jButtonEdycjaHistStanUsun.setText("Usuń");
        jButtonEdycjaHistStanUsun.setEnabled(false);
        jButtonEdycjaHistStanUsun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEdycjaHistStanUsunActionPerformed(evt);
            }
        });

        jLabelEdycjaHistStanError.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelEdycjaHistStanError.setForeground(new java.awt.Color(204, 0, 0));
        jLabelEdycjaHistStanError.setText("Pola nie mogą być puste!");

        jTableEdycjaHistStan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableEdycjaHistStanMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(jTableEdycjaHistStan);

        javax.swing.GroupLayout jPanelEdycjahistoriiStanowiskPracownikaLayout = new javax.swing.GroupLayout(jPanelEdycjahistoriiStanowiskPracownika);
        jPanelEdycjahistoriiStanowiskPracownika.setLayout(jPanelEdycjahistoriiStanowiskPracownikaLayout);
        jPanelEdycjahistoriiStanowiskPracownikaLayout.setHorizontalGroup(
            jPanelEdycjahistoriiStanowiskPracownikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createSequentialGroup()
                        .addComponent(jLabel66)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel67)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldEdycjaHistStanSzukaj, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonEdycjaHistStankSzukaj, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createSequentialGroup()
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
                                    .addComponent(jButtonEdycjaHistStanUsun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabelEdycjaHistStanError)))
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
                        .addGap(44, 44, 44)
                        .addComponent(jLabelEdycjaHistStanError)
                        .addGap(0, 132, Short.MAX_VALUE))
                    .addGroup(jPanelEdycjahistoriiStanowiskPracownikaLayout.createSequentialGroup()
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jTabbedPaneMain.addTab("Edycja historii stanowisk pracownika", jPanelEdycjahistoriiStanowiskPracownika);

        jPanelZatwierdzanie.setName("Zatwierdzanie zmian"); // NOI18N

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

        jTableZatwierdzanie.setModel(km.getZatwierdzanieDataTable());
        jTableZatwierdzanie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableZatwierdzanieMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(jTableZatwierdzanie);

        javax.swing.GroupLayout jPanelZatwierdzanieLayout = new javax.swing.GroupLayout(jPanelZatwierdzanie);
        jPanelZatwierdzanie.setLayout(jPanelZatwierdzanieLayout);
        jPanelZatwierdzanieLayout.setHorizontalGroup(
            jPanelZatwierdzanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelZatwierdzanieLayout.createSequentialGroup()
                .addGap(169, 169, 169)
                .addComponent(jButtonZatwierdzanieZmianZatwierdz, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButtonZatwierdzanieZmianOdrzuc, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(180, Short.MAX_VALUE))
            .addGroup(jPanelZatwierdzanieLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane15)
                .addContainerGap())
        );
        jPanelZatwierdzanieLayout.setVerticalGroup(
            jPanelZatwierdzanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelZatwierdzanieLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelZatwierdzanieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonZatwierdzanieZmianZatwierdz, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonZatwierdzanieZmianOdrzuc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        jTabbedPaneMain.addTab("Zatwierdzanie zmian", jPanelZatwierdzanie);

        jPanelHistoriaZatwierdzonychZmian.setName("Historia zatwierdzonych zmian"); // NOI18N

        jTableHistoriaZatwierdzonychZmian.setModel(km.getHistoriaZmianZatwierdzaniaTable());
        jScrollPane17.setViewportView(jTableHistoriaZatwierdzonychZmian);

        javax.swing.GroupLayout jPanelHistoriaZatwierdzonychZmianLayout = new javax.swing.GroupLayout(jPanelHistoriaZatwierdzonychZmian);
        jPanelHistoriaZatwierdzonychZmian.setLayout(jPanelHistoriaZatwierdzonychZmianLayout);
        jPanelHistoriaZatwierdzonychZmianLayout.setHorizontalGroup(
            jPanelHistoriaZatwierdzonychZmianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHistoriaZatwierdzonychZmianLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelHistoriaZatwierdzonychZmianLayout.setVerticalGroup(
            jPanelHistoriaZatwierdzonychZmianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHistoriaZatwierdzonychZmianLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addContainerGap())
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

        jPanelRaporty.setName("Raport pracownika"); // NOI18N

        jScrollPane13.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane13.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanelRaportv2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel58.setText("Typ raportu");

        jComboBoxRaportType.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jComboBoxRaportType.setModel(cmbRaportListType());
        jComboBoxRaportType.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBoxRaportTypeMouseClicked(evt);
            }
        });
        jComboBoxRaportType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxRaportTypeActionPerformed(evt);
            }
        });

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel70.setText("PODLASIE");

        jLabel71.setText("ul. PL. 1-GO MAJA 3");

        jLabel72.setText("23-100 Ostrowiec Świętokrzyski");

        jLabel73.setText("REGON 9367041332336    EKD 456/EKD");

        jLabelRaportTytulRaportu.setBackground(new java.awt.Color(255, 255, 255));
        jLabelRaportTytulRaportu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelRaportTytulRaportu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelRaportTytulRaportu.setText("RAPORT DOTYCZĄCY PRACOWNIKA");

        jTextFieldRaportImieINazwisko.setEditable(false);
        jTextFieldRaportImieINazwisko.setBackground(new java.awt.Color(255, 255, 255));

        jTextFieldRaportPesel.setEditable(false);
        jTextFieldRaportPesel.setBackground(new java.awt.Color(255, 255, 255));

        jTextFieldRaportStudent.setEditable(false);
        jTextFieldRaportStudent.setBackground(new java.awt.Color(255, 255, 255));

        jTextFieldRaportPensja.setEditable(false);
        jTextFieldRaportPensja.setBackground(new java.awt.Color(255, 255, 255));

        jTextFieldRaportPensjaSrednia.setEditable(false);
        jTextFieldRaportPensjaSrednia.setBackground(new java.awt.Color(255, 255, 255));

        jLabelRaportSrednieWynagrodzenie.setText("Średnie wynagrodzenie z 3 ost. mies.");

        jLabelRaportPensja.setText("Pensja");

        jLabelRaportStudent.setText("Student");

        jLabel77.setText("Pesel");

        jLabel78.setText("Imie i nazwisko");

        jLabel79.setText("Data urodzenia");

        jLabelRaportDataZatrudnienia.setText("Data zatrudnienia");

        jLabelRaportDataKoncaUmowy.setText("Data końca umowy");

        jLabel82.setText("Stanowisko");

        jTextFieldRaportStanowisko.setEditable(false);
        jTextFieldRaportStanowisko.setBackground(new java.awt.Color(255, 255, 255));

        jTextFieldRaportDataKoncaUmowy.setEditable(false);
        jTextFieldRaportDataKoncaUmowy.setBackground(new java.awt.Color(255, 255, 255));

        jTextFieldRaportDataZatrudnienia.setEditable(false);
        jTextFieldRaportDataZatrudnienia.setBackground(new java.awt.Color(255, 255, 255));

        jTextFieldRaportDataUrodzenia.setEditable(false);
        jTextFieldRaportDataUrodzenia.setBackground(new java.awt.Color(255, 255, 255));

        jButtonRaportDrukuj.setText("Drukuj");
        jButtonRaportDrukuj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRaportDrukujActionPerformed(evt);
            }
        });

        jLabel83.setText("Ostrowiec Świętokrzyski,");

        jLabelRaportData.setText("02/01/2015");

        jLabelRaportHistoriaStanowisk.setText("Historia stanowisk");

        jTableRaport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableRaport.setEnabled(false);
        jTableRaport.setName("Historia stanowisk"); // NOI18N
        jScrollPane18.setViewportView(jTableRaport);

        javax.swing.GroupLayout jPanelRaportTableLayout = new javax.swing.GroupLayout(jPanelRaportTable);
        jPanelRaportTable.setLayout(jPanelRaportTableLayout);
        jPanelRaportTableLayout.setHorizontalGroup(
            jPanelRaportTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanelRaportTableLayout.setVerticalGroup(
            jPanelRaportTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelRaportv2Layout = new javax.swing.GroupLayout(jPanelRaportv2);
        jPanelRaportv2.setLayout(jPanelRaportv2Layout);
        jPanelRaportv2Layout.setHorizontalGroup(
            jPanelRaportv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRaportv2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRaportv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRaportv2Layout.createSequentialGroup()
                        .addComponent(jLabel58)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxRaportType, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel83)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelRaportData))
                    .addComponent(jLabelRaportTytulRaportu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelRaportv2Layout.createSequentialGroup()
                        .addComponent(jLabelRaportSrednieWynagrodzenie, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldRaportPensjaSrednia))
                    .addGroup(jPanelRaportv2Layout.createSequentialGroup()
                        .addComponent(jLabelRaportHistoriaStanowisk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonRaportDrukuj))
                    .addGroup(jPanelRaportv2Layout.createSequentialGroup()
                        .addGroup(jPanelRaportv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel70)
                            .addComponent(jLabel71)
                            .addComponent(jLabel72)
                            .addComponent(jLabel73)
                            .addGroup(jPanelRaportv2Layout.createSequentialGroup()
                                .addGroup(jPanelRaportv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelRaportv2Layout.createSequentialGroup()
                                        .addGroup(jPanelRaportv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabelRaportStudent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel77, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel78, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanelRaportv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jTextFieldRaportStudent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                            .addComponent(jTextFieldRaportPesel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                            .addComponent(jTextFieldRaportImieINazwisko, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)))
                                    .addGroup(jPanelRaportv2Layout.createSequentialGroup()
                                        .addComponent(jLabelRaportDataZatrudnienia, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldRaportDataZatrudnienia, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelRaportv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanelRaportv2Layout.createSequentialGroup()
                                        .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldRaportDataUrodzenia, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanelRaportv2Layout.createSequentialGroup()
                                        .addComponent(jLabelRaportDataKoncaUmowy, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldRaportDataKoncaUmowy))
                                    .addGroup(jPanelRaportv2Layout.createSequentialGroup()
                                        .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldRaportStanowisko))))
                            .addGroup(jPanelRaportv2Layout.createSequentialGroup()
                                .addComponent(jLabelRaportPensja, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldRaportPensja, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanelRaportTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelRaportv2Layout.setVerticalGroup(
            jPanelRaportv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRaportv2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRaportv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxRaportType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58)
                    .addComponent(jLabel83)
                    .addComponent(jLabelRaportData))
                .addGap(9, 9, 9)
                .addComponent(jLabel70)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel71)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel72)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel73)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelRaportTytulRaportu)
                .addGap(18, 18, 18)
                .addGroup(jPanelRaportv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRaportv2Layout.createSequentialGroup()
                        .addGroup(jPanelRaportv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldRaportImieINazwisko, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel78))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelRaportv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldRaportPesel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel77)
                            .addComponent(jLabel82)
                            .addComponent(jTextFieldRaportStanowisko, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanelRaportv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldRaportStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelRaportStudent)))
                    .addGroup(jPanelRaportv2Layout.createSequentialGroup()
                        .addGroup(jPanelRaportv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel79)
                            .addComponent(jTextFieldRaportDataUrodzenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72)
                        .addGroup(jPanelRaportv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelRaportDataKoncaUmowy)
                            .addComponent(jTextFieldRaportDataKoncaUmowy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldRaportDataZatrudnienia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelRaportDataZatrudnienia))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelRaportv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRaportPensja)
                    .addComponent(jTextFieldRaportPensja, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelRaportv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRaportSrednieWynagrodzenie)
                    .addComponent(jTextFieldRaportPensjaSrednia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelRaportv2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRaportHistoriaStanowisk)
                    .addComponent(jButtonRaportDrukuj))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelRaportTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelRaportv2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(jPanelRaportv2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane13.setViewportView(jPanel7);

        javax.swing.GroupLayout jPanelRaportyLayout = new javax.swing.GroupLayout(jPanelRaporty);
        jPanelRaporty.setLayout(jPanelRaportyLayout);
        jPanelRaportyLayout.setHorizontalGroup(
            jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRaportyLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(468, 468, 468))
        );
        jPanelRaportyLayout.setVerticalGroup(
            jPanelRaportyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
    void hideButtonsAfterLogout() {

    }
    private void jButtonLogowanieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogowanieActionPerformed
        initTabbedPaneList();
        if (jButtonLogowanie.getText().equals("Wyloguj")) {
            hideButtonsAfterLogout();
            this.privilege = "nie zalogowano";
            jButtonLogowanie.setText("Logowanie");
            initTabbedPaneList();
        }

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
                this.privilege = km.getUserPrivilage(jTextLogin.getText());
                jLabelZalogowanoJako.setText(this.privilege);
                jLabelLoginError.setVisible(false);
                jLabelWarningHaslo.setVisible(false);
                jLabelWarningLogin.setVisible(false);
                jButtonLogowanie.setText("Wyloguj");
                this.zalogowano_jako = jTextLogin.getText();
                showButtonsAfterLogin(true);
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
        jButtonPrzPracownikEdytujZatwierdz.setText("Edytuj");
        jButtonPrzPracownikEdytujZatwierdz.setEnabled(false);

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

        jTextFieldPrzPracownikImie.setText(p.getImie());
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
        jTextFieldPrzPracownikKoniecUmowy.setText(dateLongToString(p.getData_konca_umowy()));
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
        //jezeli koniec umowy za mniej niz 4 miesiace to alert
        long koniecUmowy = p.getData_konca_umowy();
        long teraz = generateDateInMiliseconds();
        long roznica = koniecUmowy - teraz;
        long czteryMiesiace = 10447506987L;
        if (roznica < czteryMiesiace) {
            jTextFieldPrzPracownikKoniecUmowy.setBackground(Color.red);
        } else {
            jTextFieldPrzPracownikKoniecUmowy.setBackground(Color.lightGray);
        }

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
        jTableEdycjaHistStan.setModel(km.getPracownikHistStanoDataTable());

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
        if (jButtonEdStPodlUsun.getText().equals("Potwierdź")) {
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
            jButtonEdStPodlUsun.setText("Usuń ze stanowisk podległych");
        } else {
            jButtonEdStPodlUsun.setText("Potwierdź");
        }


    }//GEN-LAST:event_jButtonEdStPodlUsunActionPerformed

    private void jButtonPrzegladanieStanowiskUsunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrzegladanieStanowiskUsunActionPerformed
        if (jButtonPrzegladanieStanowiskUsun.getText().equals("Potwierdź")) {
            if (jTablePrzStanowisko.getSelectedRow() == -1) {
                jLabelPrzegladanieStanowiskError.setVisible(true);
            } else {
                jLabelPrzegladanieStanowiskError.setVisible(false);
                km.deleteStanowiskoById((int) jTablePrzStanowisko.getModel().getValueAt(jTablePrzStanowisko.getSelectedRow(), 0));
                jTablePrzStanowisko.setModel(km.getStanowiskoDataTable());
            }
            jButtonPrzegladanieStanowiskUsun.setText("Usuń stanowisko");
        } else {
            jButtonPrzegladanieStanowiskUsun.setText("Potwierdź");
        }
    }//GEN-LAST:event_jButtonPrzegladanieStanowiskUsunActionPerformed

    private void jButtonPrzPracownikEdytujZatwierdzActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrzPracownikEdytujZatwierdzActionPerformed
        int selectedId = (int) jTablePrzPracownik.getModel().getValueAt(jTablePrzPracownik.getSelectedRow(), 0);
        Pracownik p = km.getPracownikById(selectedId);
        int uzytkownikId = km.getUzytkownikIdByLogin(zalogowano_jako);

        if (jButtonPrzPracownikEdytujZatwierdz.getText().equals("Edytuj")) {
            disablePracownikTextFields(true);
            jButtonPrzPracownikEdytujZatwierdz.setText("Zatwierdź");
        } else {
            if (!jTextFieldPrzPracownikImie.getText().equals(p.getImie())) {
                km.addDoZatwierdzeniaString("imie", jTextFieldPrzPracownikImie.getText(), selectedId, uzytkownikId);
            }
            if (!jTextFieldPrzPracownikNazwisko.getText().equals(p.getNazwisko())) {
                km.addDoZatwierdzeniaString("nazwisko", jTextFieldPrzPracownikNazwisko.getText(), selectedId, uzytkownikId);
            }
            if (!jTextFieldPrzPracownikPesel.getText().equals(p.getPesel())) {
                km.addDoZatwierdzeniaString("pesel", jTextFieldPrzPracownikPesel.getText(), selectedId, uzytkownikId);
            }
            if (!jTextFieldPrzPracownikTytul.getText().equals(p.getTytul())) {
                km.addDoZatwierdzeniaString("tytul", jTextFieldPrzPracownikTytul.getText(), selectedId, uzytkownikId);
            }
            if (!((String) jComboBoxPrzPracownikPlec.getModel().getElementAt(jComboBoxPrzPracownikPlec.getSelectedIndex())).equals(p.getPlec())) {
                km.addDoZatwierdzeniaString("plec", (String) jComboBoxPrzPracownikPlec.getModel().getElementAt(jComboBoxPrzPracownikPlec.getSelectedIndex()), selectedId, uzytkownikId);
            }
            if (!(jTextFieldPrzPracownikDataPrzyjecia.getText().equals(dateLongToString(p.getData_przyjecia())))) {
                km.addDoZatwierdzeniaString("data_przyjecia", jTextFieldPrzPracownikDataPrzyjecia.getText(), selectedId, uzytkownikId);
            }
            if (!(jTextFieldPrzPracownikDataUrodzenia.getText().equals(dateLongToString(p.getData_urodzenia())))) {
                km.addDoZatwierdzeniaString("data_urodzenia", jTextFieldPrzPracownikDataUrodzenia.getText(), selectedId, uzytkownikId);
            }
            if (!(jTextFieldPrzPracownikKoniecUmowy.getText().equals(dateLongToString(p.getData_konca_umowy())))) {
                km.addDoZatwierdzeniaString("data_konca_umowy", jTextFieldPrzPracownikKoniecUmowy.getText(), selectedId, uzytkownikId);
            }
            if ((jCheckBoxPrzPracownikStudent.isSelected() == true && p.getCzy_studiuje() == 1)) {
                km.addDoZatwierdzeniaString("czy_studiuje", "Tak", selectedId, uzytkownikId);
            }
            if ((jCheckBoxPrzPracownikStudent.isSelected() == false && (p.getCzy_studiuje() == 0))) {
                km.addDoZatwierdzeniaString("czy_studiuje", "Nie", selectedId, uzytkownikId);
            }
            if ((Integer.valueOf(jTextFieldPrzPracownikPensja.getText())) != p.getPensja()) {
                km.addDoZatwierdzeniaString("pensja", jTextFieldPrzPracownikPensja.getText(), selectedId, uzytkownikId);
            }
            int idStanowisko = km.getStanowiskoIdByNazwa((String) jComboBoxPrzPracownikStanowisko.getSelectedItem());
            if (idStanowisko != p.getStanowisko_id_stanowisko()) {
                km.addDoZatwierdzeniaString("stanowisko", Integer.toString(idStanowisko), selectedId, uzytkownikId);
            }

            disableAllPracownikTextFields(false);
            jButtonPrzPracownikEdytujZatwierdz.setText("Edytuj");
            jButtonPrzPracownikEdytujZatwierdz.setEnabled(false);
        }
    }//GEN-LAST:event_jButtonPrzPracownikEdytujZatwierdzActionPerformed

    private void jButtonRaportyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRaportyActionPerformed
        jComboBoxRaportType.setModel(cmbRaportListType());
        if (this.privilege.equals("administrator")) {
            showTabAtIndex(jPanelRaporty, 3);
            jTabbedPaneMain.setSelectedIndex(3);
        } else {
            showTabAtIndex(jPanelRaporty, 1);
            jTabbedPaneMain.setSelectedIndex(1);
        }
        jComboBoxRaportType.setSelectedItem("Raport - historia stanowisk");
        int selectedId = (int) jTablePrzPracownik.getModel().getValueAt(jTablePrzPracownik.getSelectedRow(), 0);
        Pracownik p;
        p = km.getPracownikById(selectedId);
        jTableRaport.setModel(km.getPracownikHistStanoRaportDataTable(selectedId));

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
                jPanelRaportv2.paint(g2);

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


    private void jButtonPrzPracownikUsunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrzPracownikUsunActionPerformed
        if (jButtonPrzPracownikUsun.getText().equals("Potwierdź")) {
            int selectedId = (int) jTablePrzPracownik.getModel().getValueAt(jTablePrzPracownik.getSelectedRow(), 0);
            km.deleteFromPracownikById(selectedId);
            jTablePrzPracownik.setModel(km.getPracownikDataTable());
            jButtonPrzPracownikUsun.setText("Usuń pracownika");
            //clearTextFields()
            jTextFieldPrzPracownikDataPrzyjecia.setText("");
            jTextFieldPrzPracownikDataUrodzenia.setText("");
            jTextFieldPrzPracownikImie.setText("");
            jTextFieldPrzPracownikKoniecUmowy.setText("");
            jTextFieldPrzPracownikKoniecUmowy.setBackground(Color.darkGray);
            jTextFieldPrzPracownikNazwisko.setText("");
            jTextFieldPrzPracownikPensja.setText("");
            jTextFieldPrzPracownikPesel.setText("");
        } else {
            jButtonPrzPracownikUsun.setText("Potwierdź");
        }
    }//GEN-LAST:event_jButtonPrzPracownikUsunActionPerformed

    private void jTableZatwierdzanieMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableZatwierdzanieMouseClicked
        jButtonZatwierdzanieZmianZatwierdz.setEnabled(true);
        jButtonZatwierdzanieZmianOdrzuc.setEnabled(true);
    }//GEN-LAST:event_jTableZatwierdzanieMouseClicked

    private void jButtonZatwierdzanieZmianOdrzucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZatwierdzanieZmianOdrzucActionPerformed
        km.deleteFromDoZatwierdzenia((int) jTableZatwierdzanie.getModel().getValueAt(jTableZatwierdzanie.getSelectedRow(), 0));
        jTableZatwierdzanie.setModel(km.getZatwierdzanieDataTable());
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
        jTableHistoriaZatwierdzonychZmian.setModel(km.getHistoriaZmianZatwierdzaniaTable());
        jButtonZatwierdzanieZmianZatwierdz.setEnabled(false);
        jButtonZatwierdzanieZmianOdrzuc.setEnabled(false);
    }//GEN-LAST:event_jButtonZatwierdzanieZmianZatwierdzActionPerformed

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

                h = new Historia(0,
                        selectedId,
                        jTextFieldDodawanieHistStanDataRozpoczecia.getText(),
                        jTextFieldDodawanieHistStanDataZakonczenia.getText(),
                        jTextFieldDodawanieHistStanNazwa.getText());

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
        if (jTextFieldEdycjaHistStanSzukaj.getText().isEmpty()) {
            jTableEdycjaHistStan.setModel(null);
        } else {
            jTableEdycjaHistStan.setModel(km.getPracownikHistStanoDataTableWithQuery(jTextFieldEdycjaHistStanSzukaj.getText()));
        }

    }//GEN-LAST:event_jButtonEdycjaHistStankSzukajActionPerformed

    private void jTableEdycjaHistStanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableEdycjaHistStanMouseClicked
        jButtonEdycjaHistStanEdytuj.setEnabled(true);
        jButtonEdycjaHistStanUsun.setEnabled(true);
        int selectedId = (int) jTableEdycjaHistStan.getModel().getValueAt(jTableEdycjaHistStan.getSelectedRow(), 0);
        Historia h = km.getHistoriaStanowiskPracownikById(selectedId);
        jTextFieldEdycjaHistStanNazwa.setText(h.getNazwa());
        jTextFieldEdycjaHistStanDataRozpoczecia.setText(h.getData_rozpoczęcia());
        jTextFieldEdycjaHistStanDataZakonczenia.setText(h.getData_zakończenia());
    }//GEN-LAST:event_jTableEdycjaHistStanMouseClicked

    private void jButtonEdycjaHistStanUsunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEdycjaHistStanUsunActionPerformed
        if (jButtonEdycjaHistStanUsun.getText().equals("Potwierdź")) {
            int selectedId = (int) jTableEdycjaHistStan.getModel().getValueAt(jTableEdycjaHistStan.getSelectedRow(), 0);
            Historia h;
            km.deleteFromHistoriaStanowiskaById(selectedId);
            jTableEdycjaHistStan.setModel(km.getPracownikHistStanoDataTable());
            jButtonEdycjaHistStanUsun.setText("Usuń");
        } else {
            jButtonEdycjaHistStanUsun.setText("Potwierdź");
        }

    }//GEN-LAST:event_jButtonEdycjaHistStanUsunActionPerformed

    private void jButtonPrzUzytkownikowEdytujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrzUzytkownikowEdytujActionPerformed
        int selectedId = (int) jTablePrzUzytkownikow.getModel().getValueAt(jTablePrzUzytkownikow.getSelectedRow(), 0);
        Uzytkownik u = km.getUzytkownikById(selectedId);
        if (jButtonPrzUzytkownikowEdytuj.getText().equals("Edytuj")) {
            jTextFieldPrzUzytHaslo.setEditable(true);
            jTextFieldPrzUzytkLogin.setEditable(true);
            jButtonPrzUzytkownikowEdytuj.setText("Zatwierdź");
        } else {
            //sprawdz czy sie zmienilo i zrob update
            if (!jTextFieldPrzUzytHaslo.getText().equals(u.getHaslo())) {
                km.updateUzytkownik("haslo", jTextFieldPrzUzytHaslo.getText(), selectedId);
            }
            if (!jTextFieldPrzUzytkLogin.getText().equals(u.getLogin())) {
                km.updateUzytkownik("login", jTextFieldPrzUzytkLogin.getText(), selectedId);
            }
            //
            jTextFieldPrzUzytHaslo.setEditable(false);
            jTextFieldPrzUzytkLogin.setEditable(false);
            jButtonPrzUzytkownikowEdytuj.setText("Edytuj");
            jTablePrzUzytkownikow.setModel(km.getUzytkownikDataTable());
        }
    }//GEN-LAST:event_jButtonPrzUzytkownikowEdytujActionPerformed

    private void jPanelEdycjahistoriiStanowiskPracownikaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelEdycjahistoriiStanowiskPracownikaMouseClicked

    }//GEN-LAST:event_jPanelEdycjahistoriiStanowiskPracownikaMouseClicked

    private void jButtonEdycjaHistStanEdytujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEdycjaHistStanEdytujActionPerformed
        int selectedId = (int) jTableEdycjaHistStan.getModel().getValueAt(jTableEdycjaHistStan.getSelectedRow(), 0);
        Historia h = km.getHistoriaStanowiskPracownikById(selectedId);
        if (jButtonEdycjaHistStanEdytuj.getText().equals("Edytuj")) {
            jTextFieldEdycjaHistStanDataRozpoczecia.setEditable(true);
            jTextFieldEdycjaHistStanNazwa.setEditable(true);
            jTextFieldEdycjaHistStanDataZakonczenia.setEditable(true);
            jButtonEdycjaHistStanEdytuj.setText("Zatwierdź");
        } else {
            if (jTextFieldEdycjaHistStanDataRozpoczecia.getText().isEmpty()
                    || jTextFieldEdycjaHistStanDataZakonczenia.getText().isEmpty()
                    || jTextFieldEdycjaHistStanNazwa.getText().isEmpty()) {
                jLabelEdycjaHistStanError.setVisible(true);
            } else {
                jLabelEdycjaHistStanError.setVisible(false);
                //sprawdz ktore pole zostalo zmienione i wyslij update
                if (!jTextFieldEdycjaHistStanNazwa.getText().equals(h.getNazwa())) {
                    km.updateHistoriaStanowiskNazwa(jTextFieldEdycjaHistStanNazwa.getText(), selectedId);
                }
                if (!jTextFieldEdycjaHistStanDataRozpoczecia.getText().equals(h.getData_rozpoczęcia())) {
                    try {
                        km.updateHistoriaStanowiskData("data_rozpoczecia", getDateInMilisecFromString(jTextFieldEdycjaHistStanDataRozpoczecia.getText()), selectedId);
                    } catch (ParseException ex) {
                        Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (!jTextFieldEdycjaHistStanDataZakonczenia.getText().equals(h.getData_zakończenia())) {
                    try {
                        km.updateHistoriaStanowiskData("data_zakonczenia", getDateInMilisecFromString(jTextFieldEdycjaHistStanDataZakonczenia.getText()), selectedId);
                        //
                    } catch (ParseException ex) {
                        Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                jTextFieldEdycjaHistStanDataRozpoczecia.setEditable(false);
                jTextFieldEdycjaHistStanNazwa.setEditable(false);
                jTextFieldEdycjaHistStanDataZakonczenia.setEditable(false);
                jButtonEdycjaHistStanEdytuj.setText("Edytuj");
            }
        }

    }//GEN-LAST:event_jButtonEdycjaHistStanEdytujActionPerformed

    private void jButtonPrzUzytkownikowUsunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrzUzytkownikowUsunActionPerformed
        if (jButtonPrzUzytkownikowUsun.getText().equals("Potwierdź")) {
            int selectedId = (int) jTablePrzUzytkownikow.getModel().getValueAt(jTablePrzUzytkownikow.getSelectedRow(), 0);
            km.deleteFromUzytkownik(selectedId);
            jTablePrzUzytkownikow.setModel(km.getUzytkownikDataTable());
            jButtonPrzUzytkownikowUsun.setText("Usuń");
        } else {
            jButtonPrzUzytkownikowUsun.setText("Potwierdź");
        }
    }//GEN-LAST:event_jButtonPrzUzytkownikowUsunActionPerformed

    private void jComboBoxRaportTypeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxRaportTypeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxRaportTypeMouseClicked
    void showFieldsInRaportTab(boolean b) {
        jLabelRaportPensja.setVisible(b);
        jTextFieldRaportPensja.setVisible(b);

        jLabelRaportDataKoncaUmowy.setVisible(b);
        jTextFieldRaportDataKoncaUmowy.setVisible(b);

        jLabelRaportDataZatrudnienia.setVisible(b);
        jTextFieldRaportDataZatrudnienia.setVisible(b);

        jLabelRaportStudent.setVisible(b);
        jTextFieldRaportStudent.setVisible(b);

        jLabelRaportHistoriaStanowisk.setVisible(b);
        jPanelRaportTable.setVisible(b);
    }
    private void jComboBoxRaportTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxRaportTypeActionPerformed
        if (jComboBoxRaportType.getSelectedItem().equals("Raport - dane pracownika")) {
            jLabelRaportTytulRaportu.setText("DANE PRACOWNIKA");
            showFieldsInRaportTab(true);
        } else if (jComboBoxRaportType.getSelectedItem().equals("Raport - historia stanowisk")) {
            jLabelRaportTytulRaportu.setText("HISTORIA STANOWISK");
            showFieldsInRaportTab(true);
            jLabelRaportPensja.setVisible(false);
            jTextFieldRaportPensja.setVisible(false);
            jLabelRaportSrednieWynagrodzenie.setVisible(false);
            jTextFieldRaportPensjaSrednia.setVisible(false);
        } else {
            jLabelRaportTytulRaportu.setText("ZAŚWIADCZENIE O ZAROBKACH");
            showFieldsInRaportTab(false);
            jPanelRaportTable.setVisible(false);
            jLabelRaportSrednieWynagrodzenie.setVisible(true);
            jTextFieldRaportPensjaSrednia.setVisible(true);

        }

    }//GEN-LAST:event_jComboBoxRaportTypeActionPerformed

    private void jButtonRaportDrukujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRaportDrukujActionPerformed
        printCard();
    }//GEN-LAST:event_jButtonRaportDrukujActionPerformed

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
    private javax.swing.JButton jButtonPrzUzytkownikowEdytuj;
    private javax.swing.JButton jButtonPrzUzytkownikowUsun;
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
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel58;
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
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelDodawanieHistStanDodano;
    private javax.swing.JLabel jLabelDodawanieHistStanError;
    private javax.swing.JLabel jLabelEdStPodlErrorDodaj;
    private javax.swing.JLabel jLabelEdStPodlErrorUsun;
    private javax.swing.JLabel jLabelEdycjaHistStanError;
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
    private javax.swing.JLabel jLabelRaportDataKoncaUmowy;
    private javax.swing.JLabel jLabelRaportDataZatrudnienia;
    private javax.swing.JLabel jLabelRaportHistoriaStanowisk;
    private javax.swing.JLabel jLabelRaportPensja;
    private javax.swing.JLabel jLabelRaportSrednieWynagrodzenie;
    private javax.swing.JLabel jLabelRaportStudent;
    private javax.swing.JLabel jLabelRaportTytulRaportu;
    private javax.swing.JLabel jLabelWarningHaslo;
    private javax.swing.JLabel jLabelWarningLogin;
    private javax.swing.JLabel jLabelZalogowanoJako;
    private javax.swing.JList jListEdStPodlegStanowiskaPodlegle;
    private javax.swing.JList jListEdStPodleglychStanowiska;
    private javax.swing.JList jListPrzPracownikPodlegleList;
    private javax.swing.JList jListPrzStanowiskStanowiskaPodlegle;
    private javax.swing.JPanel jPanel7;
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
    private javax.swing.JPanel jPanelRaportTable;
    private javax.swing.JPanel jPanelRaportv2;
    private javax.swing.JPanel jPanelRaporty;
    private javax.swing.JPanel jPanelZatwierdzanie;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPaneMain;
    private javax.swing.JTable jTableEdycjaHistStan;
    private javax.swing.JTable jTableHistoriaZatwierdzonychZmian;
    private javax.swing.JTable jTablePrzPracownik;
    private javax.swing.JTable jTablePrzStanowisko;
    private javax.swing.JTable jTablePrzUzytkownikow;
    private javax.swing.JTable jTableRaport;
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
    private javax.swing.JTextField jTextFieldPrzPracownikDataPrzyjecia;
    private javax.swing.JTextField jTextFieldPrzPracownikDataUrodzenia;
    private javax.swing.JTextField jTextFieldPrzPracownikImie;
    private javax.swing.JTextField jTextFieldPrzPracownikKoniecUmowy;
    private javax.swing.JTextField jTextFieldPrzPracownikNazwisko;
    private javax.swing.JTextField jTextFieldPrzPracownikPensja;
    private javax.swing.JTextField jTextFieldPrzPracownikPesel;
    private javax.swing.JTextField jTextFieldPrzPracownikSzukaj;
    private javax.swing.JTextField jTextFieldPrzPracownikTytul;
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
