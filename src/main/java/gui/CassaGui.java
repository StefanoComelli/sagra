package gui;

import Manager.RigheCommesseManager;
import beans.Cassa;
import beans.Ordine;
import beans.StatoCassa;
import database.DbConnection;
import javax.swing.table.DefaultTableModel;
import model.CategorieProdotti;
import model.ListinoReale;
import guiUtils.GuiUtils;
import utils.IdDescr;
import guiUtils.TableMouseListener;
import guiUtils.BetterTableCellRenderer;
import jasper.JrRigaOrdineFactory;
import jasper.JrTestataOrdine;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import model.Commesse;
import model.RigheCommesse;
import model.Sconti;
import model.StatiOrdine;
import model.Varianti;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.lang.math.NumberUtils;
import org.jboss.logging.Logger;
import utils.IdOrdine;
import utils.MathUtils;
import utils.Sconto;
import utils.Valuta;

/**
 *
 * @author Stefano
 */
public class CassaGui extends javax.swing.JFrame {

    /**
     *
     */
    private class StatoFinestra {

        private boolean bloccata = false;
        private final Component component;

        /**
         *
         * @param component
         */
        public StatoFinestra(Component component) {
            this.component = component;
        }

        /**
         *
         * @return
         */
        public boolean Blocca() {
            if (bloccata) {
                return false;
            } else {
                bloccata = true;
                component.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                return true;
            }
        }

        /**
         *
         * @return
         */
        public boolean Sblocca() {
            if (!bloccata) {
                return false;
            } else {
                bloccata = false;
                component.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                return true;
            }
        }

        /**
         * @return the finestraBloccata
         */
        public boolean finestraBloccata() {
            return bloccata;
        }
    }

    private final StatoCassa statoCassa;
    private final Cassa cassa;

    private boolean flgOrdineDaSalvare;
    private Ordine ordine;

    private final int TBL_LISTINO_CATEGORIA = 0;
    private final int TBL_LISTINO_PRODOTTO = 1;
    private final int TBL_LISTINO_PREZZO = 2;
    private final int TBL_LISTINO_DESCRIZIONE = 3;
    private final int TBL_LISTINO_ID_CATEGORIA = 4;
    private final int TBL_LISTINO_ID_PRODOTTO = 5;

    private final int TBL_RIGHE_PRODOTTO = 0;
    private final int TBL_RIGHE_PREZZO = 1;
    private final int TBL_RIGHE_QUANTITA = 2;
    private final int TBL_RIGHE_NOTE = 3;
    private final int TBL_RIGHE_ID_PRODOTTO = 4;
    private final int TBL_RIGHE_ID = 5;

    private int cListino = -1;
    private int rListino = -1;
    private int cOrdine;
    private int rOrdine;

    private float percScontoDaApplicare;
    private float scontoApplicato;
    private float totale;
    private float netto;

    private final DbConnection dbConnection;

    private final StatoFinestra statoFinestra;
    private final RigheCommesseManager mgrRiga;
    private static final Logger LOGGER = Logger.getLogger(CassaGui.class);

    private boolean flgRistampa;
    private String cassaRistampa;
    private String cassiereRistampa;

    private IdOrdine ultimoOrdine;

    private Collection<javax.swing.JButton> lstBtnAdd;
    private Collection<javax.swing.JButton> lstBtnInsAdd;

    /**
     *
     * @param giorno
     * @param cassa
     * @param operatore
     * @param dbConnection
     */
    public CassaGui(String giorno, String cassa, String operatore, DbConnection dbConnection) {

        this.dbConnection = dbConnection;
        mgrRiga = new RigheCommesseManager(dbConnection);
        statoFinestra = new StatoFinestra(this);
        this.cassa = new Cassa(dbConnection, giorno, cassa, operatore);
        statoCassa = new StatoCassa(dbConnection, this.cassa.getCassa(), this.cassa.getGiorno());
        ultimoOrdine = new IdOrdine();
        initComponents();
        SetupGui();
        SetupCategorie();
        SetupSconti();
        RefreshListino();
        PopolaVarianti();
        PopolaStati();

        StatoBottoni();
        jTxtOrdine.requestFocusInWindow();

    }

    /**
     *
     */
    private void SetupSconti() {
        jCmbSconti.removeAllItems();
        jCmbSconti.addItem("");
        for (Sconti sconto : cassa.getSconti()) {
            jCmbSconti.addItem(sconto.toString());
        }
    }

    /**
     *
     * @return
     */
    private boolean ChiediConferma(String titolo) {
        Object[] options = {"Sì", "No"};
        int n = JOptionPane.showOptionDialog(this, "Confermi ?", titolo,
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, options, options[1]);
        return n == JOptionPane.YES_OPTION;
    }

    /**
     *
     */
    private void Blocca(String titolo) {
        Object[] options = {"Ok"};
        JOptionPane.showOptionDialog(this, titolo, "Errore",
                JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE,
                null, options, options[0]);
    }

    /**
     *
     */
    private void PulisciOrdine() {
        ordine = null;
        SvuotaSconto();

        flgOrdineDaSalvare = false;
        jTxtCliente.setText("");
        jSpinCoperti.setValue(0);
        jTxtTavolo.setText("");
        jChkAsporto.setSelected(false);
        jTxtOrario.setText("");
    }

    /**
     *
     */
    private void Svuota() {
        ordine = null;
        SvuotaSconto();
        RefreshOrdine(false);
        jTxtOrdine.setText("");
        jTxtOrario.setText("");
    }

    /**
     *
     */
    private void Annulla() {
        if (ordine != null) {
            if (ChiediConferma("Cancella Ordine")) {
                ordine.CancellaOrdine();
                ordine = null;
                SvuotaSconto();
                RefreshOrdine(false);
                jTxtOrdine.setText("");
            }
        }
    }

    /**
     *
     */
    private void SvuotaSconto() {
        jCmbSconti.setSelectedIndex(-1);
    }

    /**
     *
     */
    private boolean AggiornaOrdine() {

        boolean risultatoControllo = ControlloOrdine();

        if (risultatoControllo) {
            Commesse commessa = ordine.getCommessa();
            commessa.setNomeCliente(jTxtCliente.getText());
            commessa.setCoperti((int) jSpinCoperti.getValue());
            IdDescr sconto = new IdDescr((String) jCmbSconti.getSelectedItem());
            commessa.setDescSconto(sconto.getDescr());
            commessa.setAsporto(jChkAsporto.isSelected());
            if (jTxtTavolo.getText().isEmpty()) {
                commessa.setTavoloCliente("_");
            } else {
                commessa.setTavoloCliente(jTxtTavolo.getText());
            }

            commessa.setTotale(totale);
            commessa.setTotalePagato(netto);
            commessa.setScontoApplicato(scontoApplicato);
            commessa.setAsporto(jChkAsporto.isSelected());
            if (jCmbSconti.getSelectedItem() != null) {
                commessa.setDescSconto(jCmbSconti.getSelectedItem().toString());
            }
            if (commessa.getStatoOrdine() == 1) {
                commessa.setStatoOrdine(2);
            }

            ordine.getRigheMgr().setPrezziReali(ordine.getCommessa().getId(), totale, netto);
            ordine.getCommessaMgr().update(ordine.getCommessa().getId(), ordine.getCommessa());

        }
        return risultatoControllo;
    }

    /**
     *
     */
    private void Conferma() {
        if (ControlloOrdine()) {
            AggiornaOrdine();
            SvuotaSconto();
            RefreshOrdine(false);
        }
    }

    /**
     *
     */
    private void SetupGui() {

        // titolo
        setTitle(cassa.getGiorno().getTitolo());

        // sconto
        Sconto scontoGiorno = new Sconto(cassa.getGiorno().getScontoGiorno());
        jTxtScontoGiorno.setText(scontoGiorno.toString());

        // Listino
        jTblListino.setComponentPopupMenu(jPopListino);
        jTblListino.addMouseListener(new TableMouseListener(jTblListino));
        jTblListino.setDefaultRenderer(Object.class, new BetterTableCellRenderer());

        // Ordine
        jTblOrdine.setComponentPopupMenu(jPopOrdine);
        jTblOrdine.addMouseListener(new TableMouseListener(jTblOrdine));
        jTblOrdine.setDefaultRenderer(Object.class, new BetterTableCellRenderer());

        // focus
        jTxtOrdine.requestFocusInWindow();

        SetupBottoniAdd();
        SetupBottoniInsAdd();

        SetupListener();
    }

    /**
     *
     */
    private void SetupBottoniAdd() {
        lstBtnAdd = new ArrayList<>();
        lstBtnAdd.add(jBtnQta1);
        lstBtnAdd.add(jBtnQta2);
        lstBtnAdd.add(jBtnQta3);
        lstBtnAdd.add(jBtnQta4);
        lstBtnAdd.add(jBtnQta5);
        lstBtnAdd.add(jBtnQta6);
        lstBtnAdd.add(jBtnQta7);
        lstBtnAdd.add(jBtnQta8);
        lstBtnAdd.add(jBtnQta9);
        lstBtnAdd.add(jBtnQta10);
    }

    /**
     *
     */
    private void SetupBottoniInsAdd() {
        lstBtnInsAdd = new ArrayList<>();
        lstBtnInsAdd.add(jBtnAggiungi1);
        lstBtnInsAdd.add(jBtnAggiungi2);
        lstBtnInsAdd.add(jBtnAggiungi3);
        lstBtnInsAdd.add(jBtnAggiungi4);
        lstBtnInsAdd.add(jBtnAggiungi5);
        lstBtnInsAdd.add(jBtnAggiungi6);
        lstBtnInsAdd.add(jBtnAggiungi7);
        lstBtnInsAdd.add(jBtnAggiungi8);
        lstBtnInsAdd.add(jBtnAggiungi9);
        lstBtnInsAdd.add(jBtnAggiungi10);

    }

    /**
     *
     */
    private void SetupListener() {

        // Lettura Ordine
        jTxtOrdine.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                LeggeOrdine();
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });

    }

    /**
     *
     */
    private void SetupCategorie() {
        jCmbCategoria.removeAllItems();
        jCmbCategoria.addItem("");
        for (CategorieProdotti categoria : cassa.getCategorie()) {
            jCmbCategoria.addItem(categoria.toString());
        }
    }

    /**
     *
     * @return
     */
    private int getIdRigaOrdine() {
        DefaultTableModel model = (DefaultTableModel) jTblOrdine.getModel();
        return (int) model.getValueAt(rOrdine, TBL_RIGHE_ID);
    }

    /**
     *
     */
    private void EliminaRiga() {
        if (ChiediConferma("Elimina riga")) {
            int id = getIdRigaOrdine();

            mgrRiga.delete(id);
            RefreshOrdine(false);
        }
    }

    /**
     *
     * @param variante
     */
    private void ModificaVariante(String variante) {
        int id = getIdRigaOrdine();

        mgrRiga.CambiaVariante(id, variante);
        RefreshOrdine(true);
    }

    /**
     *
     */
    private void incQuantita() {
        int id = getIdRigaOrdine();

        mgrRiga.incQuantita(id);
        RefreshOrdine(true);
    }

    /**
     *
     */
    private void SettaQuantita(int qta) {
        int id = getIdRigaOrdine();

        mgrRiga.SettaQuantita(id, qta);
        RefreshOrdine(true);
    }

    /**
     *
     */
    private void decQuantita() {
        int id = getIdRigaOrdine();

        mgrRiga.decQuantita(id);
        RefreshOrdine(true);
    }

    /**
     *
     * @param qta
     */
    private void decQuantita(int qta) {
        int id = getIdRigaOrdine();

        mgrRiga.decQuantita(id, qta);
        RefreshOrdine(true);
    }

    /**
     *
     */
    private void AggiungiDaListino(int qta) {

        if (ordine != null) {
            RigheCommesse riga = new RigheCommesse();

            Valuta prezzo;
            DefaultTableModel model = (DefaultTableModel) jTblListino.getModel();

            rListino = jTblListino.getSelectedRow();

            prezzo = new Valuta(model.getValueAt(rListino, TBL_LISTINO_PREZZO));
            riga.setIdProdotto((Integer) model.getValueAt(rListino, TBL_LISTINO_ID_PRODOTTO));
            riga.setPrezzoListino(prezzo.getValore());
            riga.setQuantita(qta);
            riga.setIdCommessa(ordine.getCommessa().getId());

            mgrRiga.insert(riga);

            RefreshOrdine(false);

            jTblListino.changeSelection(rListino, cListino, true, false);
        }

    }

    /**
     *
     */
    private void RefreshOrdine(boolean riPosiziona) {
        if (ordine != null) {
            DefaultTableModel model = (DefaultTableModel) jTblOrdine.getModel();
            GuiUtils.EmptyJtable(jTblOrdine);
            int idCommessa = ordine.getCommessa().getId();
            List<RigheCommesse> righe = ordine.getRigheMgr().getByCommessa(idCommessa);
            if (righe != null) {
                for (RigheCommesse riga : righe) {
                    model.addRow(riga.getRow(dbConnection));
                }
                jTblOrdine.requestFocus();
                //if (riposiziona)
                if (jTblOrdine.getRowCount() > 0) {
                    if (riPosiziona) {
                        if (rOrdine >= jTblOrdine.getRowCount()) {
                            rOrdine = jTblOrdine.getRowCount() - 1;
                        }
                    } else {
                        rOrdine = jTblOrdine.getRowCount() - 1;
                    }

                    jTblOrdine.changeSelection(rOrdine, cOrdine, false, false);
                } else {
                    rOrdine = -1;
                }
            } else {
                rOrdine = -1;
            }

            RefreshConto();
        } else {
            GuiUtils.EmptyJtable(jTblOrdine);
            AnnullaConto();
        }
    }

    /**
     *
     */
    private void RefreshConto() {
        try {
            int idCommessa = ordine.getCommessa().getId();

            Valuta vTotale;
            totale = ordine.getRigheMgr().getTotale(idCommessa);
            vTotale = new Valuta(totale);
            jTxtTotale.setText(vTotale.toString());

            percScontoDaApplicare = getScontoDaApplicare();
            scontoApplicato = vTotale.getValore() / 100 * percScontoDaApplicare;
            Valuta vScontoOrdine = new Valuta(scontoApplicato);
            netto = MathUtils.Arrotonda(totale - scontoApplicato);
            Valuta vNnetto = new Valuta(netto);

            jTxtScontoOrdine.setText(vScontoOrdine.toString());
            jTxtNetto.setText(vNnetto.toString());
            RefreshContanti();
        } catch (Exception e) {
            jTxtTotale.setText("");
            jTxtNetto.setText("");
            jTxtScontoOrdine.setText("");
            jTxtContanti.setText("");
            jTxtResto.setText("");
        }
    }

    /**
     *
     */
    private void RefreshContanti() {
        try {
            String sContante = jTxtContanti.getText();
            Valuta vContante;
            if (!sContante.contains("€")) {
                vContante = new Valuta(Float.parseFloat(sContante));
            } else {
                vContante = new Valuta((Object) sContante);
            }
            Valuta vNetto = new Valuta((Object) jTxtNetto.getText());
            float resto;
            resto = vContante.getValore() - vNetto.getValore();
            Valuta vResto = new Valuta(resto);
            jTxtResto.setText(vResto.toString());
            jTxtContanti.setText(vContante.toString());
            if (resto >= 0) {
                jTxtResto.setForeground(Color.black);
            } else {
                jTxtResto.setForeground(Color.red);
            }
        } catch (Exception e) {
            jTxtContanti.setText("");
            jTxtResto.setText("");
        }
    }

    /**
     *
     */
    private void AnnullaConto() {
        jTxtTotale.setText("");
        jTxtScontoOrdine.setText("");
        jTxtNetto.setText("");
        jTxtContanti.setText("");
        jTxtResto.setText("");
    }

    /**
     *
     */
    private void RefreshListino() {

        boolean flgFiltro = false;
        int idCategoriaFiltro;
        DefaultTableModel model = (DefaultTableModel) jTblListino.getModel();

        IdDescr idCategoria = new IdDescr((String) jCmbCategoria.getSelectedItem());
        idCategoriaFiltro = idCategoria.getId();
        if (idCategoriaFiltro != 0) {
            flgFiltro = true;
        }

        GuiUtils.EmptyJtable(jTblListino);

        for (ListinoReale prodotto : cassa.getListino()) {
            boolean flgAdd = true;
            if (flgFiltro) {
                if (prodotto.getCategoriaProdotto().getId() != idCategoriaFiltro) {
                    flgAdd = false;
                }
            }

            if (flgAdd) {
                model.addRow(prodotto.getRow());
            }
        }
        jTblListino.requestFocus();
        if (rListino == -1 || cListino == -1) {
            jTblListino.changeSelection(rListino, cListino, false, false);
        }
    }

    /**
     *
     * @param idOrdine
     */
    private void LeggeOrdine() {
        int barcode;

        try {
            String sOrdine = jTxtOrdine.getText();

            if (!sOrdine.isEmpty() && NumberUtils.isNumber(sOrdine)) {
                barcode = Integer.parseInt(jTxtOrdine.getText());
                PulisciOrdine();
                IdOrdine idOrdine = new IdOrdine();
                idOrdine.setBarcode(barcode);
                if (idOrdine.isOk() && ordine == null) {
                    ordine = new Ordine(dbConnection, idOrdine.getId());
                    if (ordine != null) {
                        flgRistampa = true;
                        RefreshOrdine(false);
                        Commesse commessa = ordine.getCommessa();
                        jTxtCliente.setText(commessa.getNomeCliente());
                        jTxtTavolo.setText(commessa.getTavoloCliente());
                        jSpinCoperti.setValue(commessa.getCoperti());
                        cassaRistampa = commessa.getCassa().getDescrizione();
                        cassiereRistampa = commessa.getOperatore().getOperatore();
                        jChkAsporto.setSelected(commessa.isAsporto());
                        jTxtOrdine.setText("");
                        jTxtOrario.setText(commessa.getStatus());
                        StatoBottoni();
                    }
                }
            } else {
                Svuota();
            }
        } catch (Exception e) {
            LOGGER.error("Legge ordine", e);
        }
    }

    /**
     *
     * @return
     */
    private float getScontoDaApplicare() {
        Sconto scontoDaApplicare;
        if (jCmbSconti.getSelectedItem() != null && !jCmbSconti.getSelectedItem().equals("")) {
            IdDescr scontoGiorno = new IdDescr((String) jCmbSconti.getSelectedItem());
            scontoDaApplicare = new Sconto((float) scontoGiorno.getId());
        } else if (jTxtScontoGiorno.getText() != null && !jTxtScontoGiorno.getText().isEmpty()) {
            scontoDaApplicare = new Sconto(jTxtScontoGiorno.getText());
        } else {
            scontoDaApplicare = new Sconto(0);
        }
        return scontoDaApplicare.getValore();
    }

    /**
     *
     */
    private void PopolaVarianti() {
        jCmbVarianti.addItem("");
        for (Varianti variante : cassa.getVarianti()) {
            jCmbVarianti.addItem(variante.getVariante());
        }
    }

    /**
     *
     */
    private void PopolaStati() {
        for (StatiOrdine statoOrdine : cassa.getStatiOrdine()) {
            // jCmbStati.addItem(statoOrdine.toString());
        }
    }

    /**
     *
     */
    private boolean StampaOrdine() {
        boolean esito;
        esito = AggiornaOrdine();
        if (esito) {
            try {

                IdOrdine idOrdine;
                JasperReport report = (JasperReport) JRLoader.loadObjectFromFile("c://stampaCommessaCliente.jasper");

                JrTestataOrdine jrTestata = new JrTestataOrdine();
                if (!flgRistampa) {
                    jrTestata.setCassa(ordine.getCassa().getDescrizione());
                    jrTestata.setCassiere(ordine.getOperatore().getOperatore());
                } else {
                    jrTestata.setCassa(cassaRistampa);
                    jrTestata.setCassiere(cassiereRistampa);
                }
                jrTestata.setCliente(jTxtCliente.getText());
                jrTestata.setCoperti((int) jSpinCoperti.getValue());
                jrTestata.setNetto(this.netto);
                jrTestata.setSconto(this.scontoApplicato);
                jrTestata.setScontoDaApplicare(percScontoDaApplicare);
                jrTestata.setTotale(totale);
                jrTestata.setAsporto(jChkAsporto.isSelected());
                IdDescr tipoSconto = new IdDescr((String) jCmbSconti.getSelectedItem());
                jrTestata.setTipoSconto(tipoSconto.getDescr());
                jrTestata.setTavolo(jTxtTavolo.getText());
                idOrdine = new IdOrdine();
                idOrdine.setId(ordine.getCommessa().getId());
                ultimoOrdine = idOrdine;
                jrTestata.setId(idOrdine.getBarcode());

                JrRigaOrdineFactory jrFactoryBar = new JrRigaOrdineFactory(dbConnection, ordine, true);
                JrRigaOrdineFactory jrFactoryCucina = new JrRigaOrdineFactory(dbConnection, ordine, false);

                if (!jrFactoryBar.isEmpty()) {
                    Map paramBar = jrTestata.getHashMap(true);

                    JasperPrint jasperPrintBevande = JasperFillManager.fillReport(report, paramBar,
                            new JRBeanCollectionDataSource(jrFactoryBar.getBeanCollection()));
                    jasperPrintBevande.setName("Bar");

                    JasperPrintManager.printReport(jasperPrintBevande, false);
                }

                if (!jrFactoryCucina.isEmpty()) {
                    Map paramAltro = jrTestata.getHashMap(false);
                    JasperPrint jasperPrintCucina = JasperFillManager.fillReport(report, paramAltro,
                            new JRBeanCollectionDataSource(jrFactoryCucina.getBeanCollection()));
                    jasperPrintCucina.setName("Cucina");

                    JasperPrintManager.printReport(jasperPrintCucina, false);
                }

            } catch (Exception e) {
                LOGGER.error("Stampa:", e);
            }
        }
        return esito;
    }

    /**
     *
     * @return
     */
    private boolean ControlloOrdine() {
        boolean esito = true;

        if ("".equals(jTxtTavolo.getText()) && !jChkAsporto.isSelected()) {
            Blocca("Inserire il tavolo");
            esito = false;
        }
//        if (esito && (int) jSpinCoperti.getValue() == 0 && !jChkAsporto.isSelected()) {
//            esito = ChiediConferma("Numero coperti corretto?");
//        }
        return esito;
    }

    /**
     *
     */
    private void StatoBottoni() {

        boolean flgOrdineOk;
        boolean flgOrdineSel;
        boolean flgListinoSel;
        boolean flgOrdineRigheOk;

        int quantita;

        flgOrdineOk = ordine != null;
        flgListinoSel = jTblListino.getSelectedRowCount() != 0;
        flgOrdineSel = jTblOrdine.getSelectedRowCount() != 0;
        flgOrdineRigheOk = flgOrdineOk && jTblOrdine.getRowCount() > 0;

        if (flgOrdineSel) {
            DefaultTableModel model = (DefaultTableModel) jTblOrdine.getModel();
            quantita = (int) model.getValueAt(rOrdine, TBL_RIGHE_QUANTITA);
        } else {
            quantita = 0;
        }

        jBtnElimina.setEnabled(flgOrdineSel && !flgRistampa);
        // jBtnAggiungi1.setEnabled(flgListinoSel && flgOrdineOk);
        StatoBottoniInsAdd(flgListinoSel && flgOrdineOk && !flgRistampa);
        jMenuAggiungi.setEnabled(flgListinoSel && flgOrdineOk && !flgRistampa);

        jBtnConfermaVariante.setEnabled(flgOrdineSel && !flgRistampa);
        jBtnEliminaVariante.setEnabled(flgOrdineSel && !flgRistampa);
        jCmbVarianti.setEnabled(flgOrdineSel && !flgRistampa);
        StatoBottoniAdd(flgOrdineSel && !flgRistampa);
        jBtnDec.setEnabled(flgOrdineSel && !flgRistampa);

        jBtnQta.setEnabled(flgOrdineSel && !flgRistampa);

        jBtnAnnullaFiltro.setEnabled(jCmbCategoria.getSelectedItem() != null);
        if (!flgOrdineSel && !flgRistampa) {
            jCmbVarianti.setSelectedItem(null);
        } else {
            jBtnDec.setEnabled(quantita > 1);
            jMenuDec.setEnabled(quantita > 1);
        }

        if (!flgOrdineOk) {
            jTxtCliente.setText("");
            jTxtTavolo.setText("");
            jSpinCoperti.setValue(0);
            jTxtOrario.setText("");
        }
        jChkAsporto.setEnabled(flgOrdineOk && !flgRistampa);
        jBtnNuovoOrdine.setEnabled(!flgOrdineOk);
        jBtnCancellaOrdine.setEnabled(flgOrdineOk && !flgRistampa);
        jBtnPulisciOrdine.setEnabled(flgOrdineRigheOk);
        jBtnStampa.setEnabled(flgOrdineRigheOk);
        if (flgRistampa) {
            jBtnStampa.setText("Ristampa");
        } else {
            jBtnStampa.setText("Conferma e Stampa");
        }

        jTxtCliente.setEnabled(flgOrdineOk && !flgRistampa);
        jTxtTavolo.setEnabled(flgOrdineOk && !flgRistampa);
        jSpinCoperti.setEnabled(flgOrdineOk && !flgRistampa);
        jTblListino.setEnabled(flgOrdineOk && !flgRistampa);

        statoCassa.Refresh();

        setTitle(statoCassa.toString() + ultimoOrdine.toString());

    }

    /**
     *
     * @param status
     */
    private void StatoBottoniInsAdd(boolean status) {

        for (javax.swing.JButton btn : lstBtnInsAdd) {
            btn.setEnabled(status);
        }

    }

    /**
     *
     * @param status
     */
    private void StatoBottoniAdd(boolean status) {
        for (javax.swing.JButton btn : lstBtnAdd) {
            btn.setEnabled(status);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopListino = new javax.swing.JPopupMenu();
        jMenuAggiungi = new javax.swing.JMenuItem();
        jPopOrdine = new javax.swing.JPopupMenu();
        jMenuElimina = new javax.swing.JMenuItem();
        jMenuInc = new javax.swing.JMenuItem();
        jMenuDec = new javax.swing.JMenuItem();
        jBtnEsce = new javax.swing.JButton();
        jLblCliente = new javax.swing.JLabel();
        jTxtCliente = new javax.swing.JTextField();
        jLblTavolo = new javax.swing.JLabel();
        jTxtTavolo = new javax.swing.JTextField();
        jScrollOrdine = new javax.swing.JScrollPane();
        jTblOrdine = new javax.swing.JTable();
        jScrollListino = new javax.swing.JScrollPane();
        jTblListino = new javax.swing.JTable();
        jCmbCategoria = new javax.swing.JComboBox();
        jLblFiltro = new javax.swing.JLabel();
        jBtnAnnullaFiltro = new javax.swing.JButton();
        jBtnAggiungi1 = new javax.swing.JButton();
        jBtnElimina = new javax.swing.JButton();
        jPanelVarianti = new javax.swing.JPanel();
        jCmbVarianti = new javax.swing.JComboBox();
        jBtnConfermaVariante = new javax.swing.JButton();
        jBtnEliminaVariante = new javax.swing.JButton();
        jLblListino = new javax.swing.JLabel();
        jLblOrdine = new javax.swing.JLabel();
        jPanelConto = new javax.swing.JPanel();
        jLblTotale = new javax.swing.JLabel();
        jTxtTotale = new javax.swing.JTextField();
        jLblScontoOrdine = new javax.swing.JLabel();
        jTxtScontoOrdine = new javax.swing.JTextField();
        jTxtNetto = new javax.swing.JTextField();
        jLblContanti = new javax.swing.JLabel();
        jTxtContanti = new javax.swing.JTextField();
        jLblResto = new javax.swing.JLabel();
        jTxtResto = new javax.swing.JTextField();
        jBtnCalcola = new javax.swing.JButton();
        jPanelOrdine = new javax.swing.JPanel();
        jBtnNuovoOrdine = new javax.swing.JButton();
        jBtnPulisciOrdine = new javax.swing.JButton();
        jBtnCancellaOrdine = new javax.swing.JButton();
        jBtnStampa = new javax.swing.JButton();
        jTxtOrdine = new javax.swing.JTextField();
        jPanelSconto = new javax.swing.JPanel();
        jLblScontoGiorno = new javax.swing.JLabel();
        jTxtScontoGiorno = new javax.swing.JTextField();
        jLblSconti = new javax.swing.JLabel();
        jCmbSconti = new javax.swing.JComboBox();
        jLblCoperti = new javax.swing.JLabel();
        jSpinCoperti = new javax.swing.JSpinner();
        jBtnDec = new javax.swing.JButton();
        jBtnQta1 = new javax.swing.JButton();
        jSpinQta = new javax.swing.JSpinner();
        jBtnQta = new javax.swing.JButton();
        jChkAsporto = new javax.swing.JCheckBox();
        jBtnAggiungi2 = new javax.swing.JButton();
        jBtnInc1 = new javax.swing.JButton();
        jBtnQta2 = new javax.swing.JButton();
        jBtnAggiungi3 = new javax.swing.JButton();
        jBtnAggiungi4 = new javax.swing.JButton();
        jBtnAggiungi5 = new javax.swing.JButton();
        jBtnAggiungi6 = new javax.swing.JButton();
        jBtnAggiungi7 = new javax.swing.JButton();
        jBtnAggiungi8 = new javax.swing.JButton();
        jBtnAggiungi9 = new javax.swing.JButton();
        jBtnAggiungi10 = new javax.swing.JButton();
        jBtnQta3 = new javax.swing.JButton();
        jBtnQta4 = new javax.swing.JButton();
        jBtnQta5 = new javax.swing.JButton();
        jBtnQta6 = new javax.swing.JButton();
        jBtnQta7 = new javax.swing.JButton();
        jBtnQta8 = new javax.swing.JButton();
        jBtnQta9 = new javax.swing.JButton();
        jBtnQta10 = new javax.swing.JButton();
        jTxtOrario = new javax.swing.JTextField();
        jMenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuExit = new javax.swing.JMenuItem();

        jMenuAggiungi.setText("Aggiungi");
        jMenuAggiungi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuAggiungiMouseClicked(evt);
            }
        });
        jMenuAggiungi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAggiungiActionPerformed(evt);
            }
        });
        jPopListino.add(jMenuAggiungi);

        jMenuElimina.setText("Elimina");
        jMenuElimina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuEliminaActionPerformed(evt);
            }
        });
        jPopOrdine.add(jMenuElimina);

        jMenuInc.setText("+1");
        jMenuInc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuIncActionPerformed(evt);
            }
        });
        jPopOrdine.add(jMenuInc);

        jMenuDec.setText("-1");
        jMenuDec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuDecActionPerformed(evt);
            }
        });
        jPopOrdine.add(jMenuDec);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sagra Sant'Andrea: Cassa");

        jBtnEsce.setText("Esce");
        jBtnEsce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEsceActionPerformed(evt);
            }
        });

        jLblCliente.setText("Cliente:");

        jLblTavolo.setText("Tavolo:");

        jTblOrdine.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Prodotto", "Prezzo", "Quantità", "Variante", "IdProdotto", "Id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTblOrdine.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTblOrdine.getTableHeader().setReorderingAllowed(false);
        jTblOrdine.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblOrdineMouseClicked(evt);
            }
        });
        jTblOrdine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTblOrdineKeyTyped(evt);
            }
        });
        jScrollOrdine.setViewportView(jTblOrdine);
        if (jTblOrdine.getColumnModel().getColumnCount() > 0) {
            jTblOrdine.getColumnModel().getColumn(4).setMinWidth(0);
            jTblOrdine.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTblOrdine.getColumnModel().getColumn(4).setMaxWidth(0);
            jTblOrdine.getColumnModel().getColumn(5).setMinWidth(0);
            jTblOrdine.getColumnModel().getColumn(5).setPreferredWidth(0);
            jTblOrdine.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        jTblListino.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Categoria", "Prodotto", "Prezzo", "Descrizione", "IdCategoria", "IdProdotto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTblListino.setToolTipText("Listino");
        jTblListino.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        jTblListino.setEnabled(false);
        jTblListino.getTableHeader().setReorderingAllowed(false);
        jTblListino.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblListinoMouseClicked(evt);
            }
        });
        jScrollListino.setViewportView(jTblListino);
        if (jTblListino.getColumnModel().getColumnCount() > 0) {
            jTblListino.getColumnModel().getColumn(4).setMinWidth(0);
            jTblListino.getColumnModel().getColumn(4).setMaxWidth(0);
            jTblListino.getColumnModel().getColumn(5).setMinWidth(0);
            jTblListino.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        jCmbCategoria.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbCategoriaItemStateChanged(evt);
            }
        });
        jCmbCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbCategoriaActionPerformed(evt);
            }
        });

        jLblFiltro.setText("Filtro:");

        jBtnAnnullaFiltro.setText("Annulla Filtro");
        jBtnAnnullaFiltro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBtnAnnullaFiltroMouseClicked(evt);
            }
        });
        jBtnAnnullaFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAnnullaFiltroActionPerformed(evt);
            }
        });

        jBtnAggiungi1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBtnAggiungi1.setText("<== 1");
        jBtnAggiungi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAggiungi1ActionPerformed(evt);
            }
        });

        jBtnElimina.setText("Elimina");
        jBtnElimina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminaActionPerformed(evt);
            }
        });

        jPanelVarianti.setBorder(javax.swing.BorderFactory.createTitledBorder("Varianti"));
        jPanelVarianti.setName("jPanelVarianti"); // NOI18N

        jCmbVarianti.setEditable(true);

        jBtnConfermaVariante.setText("Conferma");
        jBtnConfermaVariante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnConfermaVarianteActionPerformed(evt);
            }
        });

        jBtnEliminaVariante.setText("Elimina");
        jBtnEliminaVariante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminaVarianteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelVariantiLayout = new javax.swing.GroupLayout(jPanelVarianti);
        jPanelVarianti.setLayout(jPanelVariantiLayout);
        jPanelVariantiLayout.setHorizontalGroup(
            jPanelVariantiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVariantiLayout.createSequentialGroup()
                .addGroup(jPanelVariantiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelVariantiLayout.createSequentialGroup()
                        .addComponent(jBtnConfermaVariante)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnEliminaVariante, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCmbVarianti, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelVariantiLayout.setVerticalGroup(
            jPanelVariantiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelVariantiLayout.createSequentialGroup()
                .addComponent(jCmbVarianti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelVariantiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnConfermaVariante)
                    .addComponent(jBtnEliminaVariante)))
        );

        jLblListino.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblListino.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblListino.setLabelFor(jTblOrdine);
        jLblListino.setText("Listino");
        jLblListino.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLblOrdine.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLblOrdine.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblOrdine.setLabelFor(jTblOrdine);
        jLblOrdine.setText("Ordine");
        jLblOrdine.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanelConto.setBorder(javax.swing.BorderFactory.createTitledBorder("Conto"));

        jLblTotale.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblTotale.setText("Totale:");

        jTxtTotale.setEditable(false);

        jLblScontoOrdine.setText("Sconto:");

        jTxtScontoOrdine.setEditable(false);

        jTxtNetto.setEditable(false);
        jTxtNetto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLblContanti.setText("Contanti:");

        jLblResto.setText("Resto:");

        jTxtResto.setEditable(false);

        jBtnCalcola.setText("Calcola");
        jBtnCalcola.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCalcolaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelContoLayout = new javax.swing.GroupLayout(jPanelConto);
        jPanelConto.setLayout(jPanelContoLayout);
        jPanelContoLayout.setHorizontalGroup(
            jPanelContoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContoLayout.createSequentialGroup()
                .addGroup(jPanelContoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelContoLayout.createSequentialGroup()
                        .addComponent(jLblTotale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTxtTotale, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelContoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLblContanti)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtContanti, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40)
                .addGroup(jPanelContoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLblScontoOrdine)
                    .addComponent(jLblResto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelContoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelContoLayout.createSequentialGroup()
                        .addComponent(jTxtResto, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnCalcola)
                        .addGap(0, 151, Short.MAX_VALUE))
                    .addGroup(jPanelContoLayout.createSequentialGroup()
                        .addComponent(jTxtScontoOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtNetto)))
                .addContainerGap())
        );
        jPanelContoLayout.setVerticalGroup(
            jPanelContoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContoLayout.createSequentialGroup()
                .addGroup(jPanelContoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblTotale)
                    .addComponent(jTxtTotale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblScontoOrdine)
                    .addComponent(jTxtScontoOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtNetto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelContoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelContoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLblContanti)
                        .addComponent(jTxtContanti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelContoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTxtResto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLblResto)
                        .addComponent(jBtnCalcola)))
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jPanelOrdine.setBorder(javax.swing.BorderFactory.createTitledBorder("Ordine"));

        jBtnNuovoOrdine.setText("Nuovo");
        jBtnNuovoOrdine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNuovoOrdineActionPerformed(evt);
            }
        });

        jBtnPulisciOrdine.setText("Pulisci");
        jBtnPulisciOrdine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPulisciOrdineActionPerformed(evt);
            }
        });

        jBtnCancellaOrdine.setText("Cancella");
        jBtnCancellaOrdine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancellaOrdineActionPerformed(evt);
            }
        });

        jBtnStampa.setText("Conferma e Stampa");
        jBtnStampa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnStampaActionPerformed(evt);
            }
        });

        jTxtOrdine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtOrdineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelOrdineLayout = new javax.swing.GroupLayout(jPanelOrdine);
        jPanelOrdine.setLayout(jPanelOrdineLayout);
        jPanelOrdineLayout.setHorizontalGroup(
            jPanelOrdineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOrdineLayout.createSequentialGroup()
                .addComponent(jTxtOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jBtnNuovoOrdine)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnPulisciOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnStampa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnCancellaOrdine)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelOrdineLayout.setVerticalGroup(
            jPanelOrdineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOrdineLayout.createSequentialGroup()
                .addGroup(jPanelOrdineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnNuovoOrdine)
                    .addComponent(jBtnPulisciOrdine)
                    .addComponent(jBtnCancellaOrdine)
                    .addComponent(jBtnStampa)
                    .addComponent(jTxtOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jPanelSconto.setBorder(javax.swing.BorderFactory.createTitledBorder("Sconto"));

        jLblScontoGiorno.setText("Sconto Giorno:");

        jTxtScontoGiorno.setEditable(false);

        jLblSconti.setText("Sconti:");

        jCmbSconti.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCmbScontiMouseClicked(evt);
            }
        });
        jCmbSconti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbScontiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelScontoLayout = new javax.swing.GroupLayout(jPanelSconto);
        jPanelSconto.setLayout(jPanelScontoLayout);
        jPanelScontoLayout.setHorizontalGroup(
            jPanelScontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelScontoLayout.createSequentialGroup()
                .addComponent(jLblScontoGiorno)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTxtScontoGiorno, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLblSconti)
                .addGap(18, 18, 18)
                .addComponent(jCmbSconti, 0, 366, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelScontoLayout.setVerticalGroup(
            jPanelScontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelScontoLayout.createSequentialGroup()
                .addGroup(jPanelScontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblSconti)
                    .addComponent(jCmbSconti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtScontoGiorno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblScontoGiorno))
                .addGap(0, 4, Short.MAX_VALUE))
        );

        jLblCoperti.setText("Coperti:");

        jSpinCoperti.setModel(new javax.swing.SpinnerNumberModel(0, 0, 99, 1));

        jBtnDec.setText("-1");
        jBtnDec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnDecActionPerformed(evt);
            }
        });

        jBtnQta1.setText("1");
        jBtnQta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnQta1ActionPerformed(evt);
            }
        });

        jSpinQta.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jBtnQta.setText("Quantità");
        jBtnQta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnQtaActionPerformed(evt);
            }
        });

        jChkAsporto.setText("Asporto");

        jBtnAggiungi2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBtnAggiungi2.setText("<== 2");
        jBtnAggiungi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAggiungi2ActionPerformed(evt);
            }
        });

        jBtnInc1.setText("+1");
        jBtnInc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnInc1ActionPerformed(evt);
            }
        });

        jBtnQta2.setText("2");
        jBtnQta2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnQta2ActionPerformed(evt);
            }
        });

        jBtnAggiungi3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBtnAggiungi3.setText("<== 3");
        jBtnAggiungi3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAggiungi3ActionPerformed(evt);
            }
        });

        jBtnAggiungi4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBtnAggiungi4.setText("<== 4");
        jBtnAggiungi4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAggiungi4ActionPerformed(evt);
            }
        });

        jBtnAggiungi5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBtnAggiungi5.setText("<== 5");
        jBtnAggiungi5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAggiungi5ActionPerformed(evt);
            }
        });

        jBtnAggiungi6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBtnAggiungi6.setText("<== 6");
        jBtnAggiungi6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAggiungi6ActionPerformed(evt);
            }
        });

        jBtnAggiungi7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBtnAggiungi7.setText("<== 7");
        jBtnAggiungi7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAggiungi7ActionPerformed(evt);
            }
        });

        jBtnAggiungi8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBtnAggiungi8.setText("<== 8");
        jBtnAggiungi8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAggiungi8ActionPerformed(evt);
            }
        });

        jBtnAggiungi9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBtnAggiungi9.setText("<== 9");
        jBtnAggiungi9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAggiungi9ActionPerformed(evt);
            }
        });

        jBtnAggiungi10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBtnAggiungi10.setText("<= 10");
        jBtnAggiungi10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAggiungi10ActionPerformed(evt);
            }
        });

        jBtnQta3.setText("3");
        jBtnQta3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnQta3ActionPerformed(evt);
            }
        });

        jBtnQta4.setText("4");
        jBtnQta4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnQta4ActionPerformed(evt);
            }
        });

        jBtnQta5.setText("5");
        jBtnQta5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnQta5ActionPerformed(evt);
            }
        });

        jBtnQta6.setText("6");
        jBtnQta6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnQta6ActionPerformed(evt);
            }
        });

        jBtnQta7.setText("7");
        jBtnQta7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnQta7ActionPerformed(evt);
            }
        });

        jBtnQta8.setText("8");
        jBtnQta8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnQta8ActionPerformed(evt);
            }
        });

        jBtnQta9.setText("9");
        jBtnQta9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnQta9ActionPerformed(evt);
            }
        });

        jBtnQta10.setText("10");
        jBtnQta10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnQta10ActionPerformed(evt);
            }
        });

        jTxtOrario.setEditable(false);

        jMenu1.setText("File");

        jMenuExit.setText("Esce");
        jMenuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuExitActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuExit);

        jMenuBar.add(jMenu1);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jPanelVarianti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(71, 71, 71))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 149, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBtnQta1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnQta2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnQta3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnQta4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnQta5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnQta6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnQta7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnQta8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnQta9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnQta10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLblCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLblTavolo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtTavolo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLblCoperti)
                                .addGap(18, 18, 18)
                                .addComponent(jSpinCoperti, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jChkAsporto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtOrario))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBtnElimina, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBtnQta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSpinQta, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnDec, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnInc1))
                            .addComponent(jLblOrdine, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollOrdine, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jBtnAggiungi1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnAggiungi2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnAggiungi3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnAggiungi4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnAggiungi5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnAggiungi6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnAggiungi7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnAggiungi8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnAggiungi9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnAggiungi10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLblFiltro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCmbCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnAnnullaFiltro))
                    .addComponent(jLblListino, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollListino, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jPanelConto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelSconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEsce))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelSconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBtnAnnullaFiltro)
                            .addComponent(jCmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblFiltro))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLblListino)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollListino, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBtnAggiungi1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnAggiungi2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnAggiungi3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnAggiungi4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnAggiungi5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnAggiungi6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnAggiungi7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnAggiungi8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnAggiungi9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnAggiungi10))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLblCliente)
                            .addComponent(jTxtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblTavolo)
                            .addComponent(jTxtTavolo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblCoperti)
                            .addComponent(jSpinCoperti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jChkAsporto)
                            .addComponent(jTxtOrario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLblOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnEsce)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnQta1)
                            .addComponent(jBtnQta2)
                            .addComponent(jBtnQta3)
                            .addComponent(jBtnQta4)
                            .addComponent(jBtnQta5)
                            .addComponent(jBtnQta6)
                            .addComponent(jBtnQta7)
                            .addComponent(jBtnQta8)
                            .addComponent(jBtnQta9)
                            .addComponent(jBtnQta10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnQta)
                            .addComponent(jSpinQta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnDec)
                            .addComponent(jBtnElimina)
                            .addComponent(jBtnInc1))
                        .addGap(4, 4, 4)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelVarianti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelConto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     *
     * @param evt
     */
    private void jMenuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuExitActionPerformed
        if (statoFinestra.Blocca()) {
            if (ChiediConferma("Vuoi uscire?")) {
                if (flgOrdineDaSalvare) {
                    Annulla();
                }

                dbConnection.Dispose();
                System.exit(0);
            } else {
                statoFinestra.Sblocca();
            }
        }
    }//GEN-LAST:event_jMenuExitActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnEsceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEsceActionPerformed
        if (statoFinestra.Blocca()) {
            if (ChiediConferma("Vuoi uscire?")) {
                if (flgOrdineDaSalvare) {
                    Annulla();
                }
                dbConnection.Dispose();
                System.exit(0);
            } else {
                statoFinestra.Sblocca();
            }
        }
    }//GEN-LAST:event_jBtnEsceActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnNuovoOrdineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNuovoOrdineActionPerformed
        if (statoFinestra.Blocca()) {
            jTxtOrdine.setText("");
            jTxtOrario.setText("");
            String cliente = (String) JOptionPane.showInputDialog(this, "Cliente ?",
                    "Ordine", JOptionPane.PLAIN_MESSAGE, null, null, "");
            if (cliente != null && !cliente.isEmpty()) {
                flgRistampa = false;
                ordine = new Ordine(dbConnection, this.cassa, cliente);
                flgOrdineDaSalvare = true;
                jTxtCliente.setText(cliente);
            }
            StatoBottoni();
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jBtnNuovoOrdineActionPerformed

    /**
     *
     * @param evt
     */
    private void jTblOrdineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblOrdineMouseClicked
        if (statoFinestra.Blocca()) {
            cOrdine = jTblOrdine.getSelectedColumn();
            rOrdine = jTblOrdine.getSelectedRow();
            StatoBottoni();
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jTblOrdineMouseClicked

    /**
     *
     * @param evt
     */
    private void jTblListinoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblListinoMouseClicked
        if (statoFinestra.Blocca()) {
            cListino = jTblListino.getSelectedColumn();
            rListino = jTblListino.getSelectedRow();
            if ((cListino != -1) && (evt.getClickCount() == 2)) {
                AggiungiDaListino(1);
            }
            StatoBottoni();
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jTblListinoMouseClicked

    /**
     *
     * @param evt
     */
    private void jCmbCategoriaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbCategoriaItemStateChanged
        if (statoFinestra.Blocca()) {
            rListino = -1;
            cListino = -1;
            RefreshListino();
            StatoBottoni();
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jCmbCategoriaItemStateChanged

    /**
     *
     * @param evt
     */
    private void jMenuAggiungiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuAggiungiMouseClicked
        if (statoFinestra.Blocca()) {
            AggiungiDaListino(1);
            StatoBottoni();
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jMenuAggiungiMouseClicked

    /**
     *
     * @param evt
     */
    private void jMenuAggiungiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAggiungiActionPerformed
        if (statoFinestra.Blocca()) {
            AggiungiDaListino(1);
            StatoBottoni();
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jMenuAggiungiActionPerformed

    /**
     *
     * @param inserisci
     * @param qta
     */
    private void Quantita(boolean inserisci, int qta) {
        if (qta >= 1 && statoFinestra.Blocca()) {
            if (inserisci) {
                AggiungiDaListino(qta);
            } else {
                SettaQuantita(qta);
            }
            StatoBottoni();
            statoFinestra.Sblocca();
        }
    }

    /**
     *
     * @param qta
     */
    private void InsQta(int qta) {
        Quantita(true, qta);
    }

    /**
     *
     * @param qta
     */
    private void SetQta(int qta) {
        Quantita(false, qta);
    }

    /**
     *
     * @param evt
     */
    private void jBtnAggiungi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAggiungi1ActionPerformed
//        if (statoFinestra.Blocca()) {
//            AggiungiDaListino();
//            StatoBottoni();
//            statoFinestra.Sblocca();
//        }
        InsQta(1);
    }//GEN-LAST:event_jBtnAggiungi1ActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminaActionPerformed
        if (statoFinestra.Blocca()) {
            EliminaRiga();
            StatoBottoni();
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jBtnEliminaActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnQta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnQta1ActionPerformed
        SetQta(1);
    }//GEN-LAST:event_jBtnQta1ActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnDecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDecActionPerformed
        if (statoFinestra.Blocca()) {
            decQuantita();
            StatoBottoni();
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jBtnDecActionPerformed

    /**
     *
     * @param evt
     */
    private void jMenuEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuEliminaActionPerformed
        if (statoFinestra.Blocca()) {
            EliminaRiga();
            StatoBottoni();
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jMenuEliminaActionPerformed

    /**
     *
     * @param evt
     */
    private void jCmbScontiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbScontiActionPerformed
        if (statoFinestra.Blocca()) {
            RefreshOrdine(true);
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jCmbScontiActionPerformed

    /**
     *
     * @param evt
     */
    private void jCmbScontiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCmbScontiMouseClicked
        if (statoFinestra.Blocca()) {
            RefreshOrdine(true);
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jCmbScontiMouseClicked

    /**
     *
     * @param evt
     */
    private void jBtnCancellaOrdineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancellaOrdineActionPerformed
        if (statoFinestra.Blocca()) {
            Annulla();
            StatoBottoni();
            jTxtOrdine.requestFocusInWindow();
            flgOrdineDaSalvare = false;
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jBtnCancellaOrdineActionPerformed

    /**
     *
     * @param evt
     */
    private void jMenuIncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuIncActionPerformed
        if (statoFinestra.Blocca()) {
            incQuantita();
            StatoBottoni();
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jMenuIncActionPerformed

    /**
     *
     * @param evt
     */
    private void jMenuDecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuDecActionPerformed
        if (statoFinestra.Blocca()) {
            decQuantita();
            StatoBottoni();
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jMenuDecActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnPulisciOrdineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPulisciOrdineActionPerformed
        if (ordine != null) {
            if (statoFinestra.Blocca()) {
                if (ChiediConferma("Pulisci Ordine")) {
                    ordine = null;
                    SvuotaSconto();
                    RefreshOrdine(false);
                    jTxtOrdine.setText("");
                    flgOrdineDaSalvare = false;
                    jTxtCliente.setText("");
                    jSpinCoperti.setValue(0);
                    jTxtTavolo.setText("");
                    jChkAsporto.setSelected(false);
                    jTxtOrario.setText("");
                    StatoBottoni();
                }
                statoFinestra.Sblocca();
            }
        }
        jTxtOrdine.requestFocusInWindow();
    }//GEN-LAST:event_jBtnPulisciOrdineActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnQtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnQtaActionPerformed
        if (statoFinestra.Blocca()) {
            SettaQuantita((int) jSpinQta.getValue());
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jBtnQtaActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnEliminaVarianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminaVarianteActionPerformed
        if (statoFinestra.Blocca()) {
            ModificaVariante("");
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jBtnEliminaVarianteActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnConfermaVarianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnConfermaVarianteActionPerformed
        if (statoFinestra.Blocca()) {
            String variante = (String) jCmbVarianti.getSelectedItem();
            ModificaVariante(variante);
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jBtnConfermaVarianteActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnStampaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnStampaActionPerformed
        if (statoFinestra.Blocca()) {
            if (StampaOrdine()) {
                flgOrdineDaSalvare = false;
                statoFinestra.Sblocca();
                flgRistampa = true;
            }
            StatoBottoni();
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jBtnStampaActionPerformed

    /**
     *
     * @param evt
     */
    private void jCmbCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbCategoriaActionPerformed
        if (statoFinestra.Blocca()) {
            StatoBottoni();
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jCmbCategoriaActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnCalcolaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCalcolaActionPerformed
        if (statoFinestra.Blocca()) {
            RefreshContanti();
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jBtnCalcolaActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnAnnullaFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAnnullaFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnAnnullaFiltroActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnAnnullaFiltroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnAnnullaFiltroMouseClicked
        if (statoFinestra.Blocca()) {
            jCmbCategoria.setSelectedIndex(-1);
            rListino = -1;
            cListino = -1;
            RefreshListino();
            StatoBottoni();
            statoFinestra.Sblocca();
        }
    }//GEN-LAST:event_jBtnAnnullaFiltroMouseClicked
    /**
     *
     * @param evt
     */
    private void jTxtOrdineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtOrdineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtOrdineActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnAggiungi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAggiungi2ActionPerformed
        InsQta(2);
    }//GEN-LAST:event_jBtnAggiungi2ActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnInc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnInc1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnInc1ActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnQta2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnQta2ActionPerformed
        SetQta(2);
    }//GEN-LAST:event_jBtnQta2ActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnAggiungi3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAggiungi3ActionPerformed
        InsQta(3);
    }//GEN-LAST:event_jBtnAggiungi3ActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnAggiungi4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAggiungi4ActionPerformed
        InsQta(4);
    }//GEN-LAST:event_jBtnAggiungi4ActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnAggiungi5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAggiungi5ActionPerformed
        InsQta(5);
    }//GEN-LAST:event_jBtnAggiungi5ActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnAggiungi6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAggiungi6ActionPerformed
        InsQta(6);
    }//GEN-LAST:event_jBtnAggiungi6ActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnAggiungi7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAggiungi7ActionPerformed
        InsQta(7);
    }//GEN-LAST:event_jBtnAggiungi7ActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnAggiungi8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAggiungi8ActionPerformed
        InsQta(8);
    }//GEN-LAST:event_jBtnAggiungi8ActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnAggiungi9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAggiungi9ActionPerformed
        InsQta(9);
    }//GEN-LAST:event_jBtnAggiungi9ActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnAggiungi10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAggiungi10ActionPerformed
        InsQta(10);
    }//GEN-LAST:event_jBtnAggiungi10ActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnQta3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnQta3ActionPerformed
        SetQta(3);
    }//GEN-LAST:event_jBtnQta3ActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnQta4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnQta4ActionPerformed
        SetQta(4);
    }//GEN-LAST:event_jBtnQta4ActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnQta5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnQta5ActionPerformed
        SetQta(5);
    }//GEN-LAST:event_jBtnQta5ActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnQta6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnQta6ActionPerformed
        SetQta(6);
    }//GEN-LAST:event_jBtnQta6ActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnQta7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnQta7ActionPerformed
        SetQta(7);
    }//GEN-LAST:event_jBtnQta7ActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnQta8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnQta8ActionPerformed
        SetQta(8);
    }//GEN-LAST:event_jBtnQta8ActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnQta9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnQta9ActionPerformed
        SetQta(9);
    }//GEN-LAST:event_jBtnQta9ActionPerformed
    /**
     *
     * @param evt
     */
    private void jBtnQta10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnQta10ActionPerformed
        SetQta(10);
    }//GEN-LAST:event_jBtnQta10ActionPerformed

    /**
     *
     * @param evt
     */
    private void jTblOrdineKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTblOrdineKeyTyped

        boolean flgOrdineOk;
        boolean flgOrdineSel;

        flgOrdineOk = ordine != null;
        flgOrdineSel = jTblOrdine.getSelectedRowCount() != 0;

        if (flgOrdineOk && flgOrdineSel) {
            char tasto = evt.getKeyChar();
            if (tasto >= '1' && tasto <= '9') {
                int num = (int) tasto - '0';
                SetQta(num);
            }
        }
    }//GEN-LAST:event_jTblOrdineKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAggiungi1;
    private javax.swing.JButton jBtnAggiungi10;
    private javax.swing.JButton jBtnAggiungi2;
    private javax.swing.JButton jBtnAggiungi3;
    private javax.swing.JButton jBtnAggiungi4;
    private javax.swing.JButton jBtnAggiungi5;
    private javax.swing.JButton jBtnAggiungi6;
    private javax.swing.JButton jBtnAggiungi7;
    private javax.swing.JButton jBtnAggiungi8;
    private javax.swing.JButton jBtnAggiungi9;
    private javax.swing.JButton jBtnAnnullaFiltro;
    private javax.swing.JButton jBtnCalcola;
    private javax.swing.JButton jBtnCancellaOrdine;
    private javax.swing.JButton jBtnConfermaVariante;
    private javax.swing.JButton jBtnDec;
    private javax.swing.JButton jBtnElimina;
    private javax.swing.JButton jBtnEliminaVariante;
    private javax.swing.JButton jBtnEsce;
    private javax.swing.JButton jBtnInc1;
    private javax.swing.JButton jBtnNuovoOrdine;
    private javax.swing.JButton jBtnPulisciOrdine;
    private javax.swing.JButton jBtnQta;
    private javax.swing.JButton jBtnQta1;
    private javax.swing.JButton jBtnQta10;
    private javax.swing.JButton jBtnQta2;
    private javax.swing.JButton jBtnQta3;
    private javax.swing.JButton jBtnQta4;
    private javax.swing.JButton jBtnQta5;
    private javax.swing.JButton jBtnQta6;
    private javax.swing.JButton jBtnQta7;
    private javax.swing.JButton jBtnQta8;
    private javax.swing.JButton jBtnQta9;
    private javax.swing.JButton jBtnStampa;
    private javax.swing.JCheckBox jChkAsporto;
    private javax.swing.JComboBox jCmbCategoria;
    private javax.swing.JComboBox jCmbSconti;
    private javax.swing.JComboBox jCmbVarianti;
    private javax.swing.JLabel jLblCliente;
    private javax.swing.JLabel jLblContanti;
    private javax.swing.JLabel jLblCoperti;
    private javax.swing.JLabel jLblFiltro;
    private javax.swing.JLabel jLblListino;
    private javax.swing.JLabel jLblOrdine;
    private javax.swing.JLabel jLblResto;
    private javax.swing.JLabel jLblSconti;
    private javax.swing.JLabel jLblScontoGiorno;
    private javax.swing.JLabel jLblScontoOrdine;
    private javax.swing.JLabel jLblTavolo;
    private javax.swing.JLabel jLblTotale;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuAggiungi;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenuItem jMenuDec;
    private javax.swing.JMenuItem jMenuElimina;
    private javax.swing.JMenuItem jMenuExit;
    private javax.swing.JMenuItem jMenuInc;
    private javax.swing.JPanel jPanelConto;
    private javax.swing.JPanel jPanelOrdine;
    private javax.swing.JPanel jPanelSconto;
    private javax.swing.JPanel jPanelVarianti;
    private javax.swing.JPopupMenu jPopListino;
    private javax.swing.JPopupMenu jPopOrdine;
    private javax.swing.JScrollPane jScrollListino;
    private javax.swing.JScrollPane jScrollOrdine;
    private javax.swing.JSpinner jSpinCoperti;
    private javax.swing.JSpinner jSpinQta;
    private javax.swing.JTable jTblListino;
    private javax.swing.JTable jTblOrdine;
    private javax.swing.JTextField jTxtCliente;
    private javax.swing.JTextField jTxtContanti;
    private javax.swing.JTextField jTxtNetto;
    private javax.swing.JTextField jTxtOrario;
    private javax.swing.JTextField jTxtOrdine;
    private javax.swing.JTextField jTxtResto;
    private javax.swing.JTextField jTxtScontoGiorno;
    private javax.swing.JTextField jTxtScontoOrdine;
    private javax.swing.JTextField jTxtTavolo;
    private javax.swing.JTextField jTxtTotale;
    // End of variables declaration//GEN-END:variables
}
