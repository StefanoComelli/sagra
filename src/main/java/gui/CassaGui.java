package gui;

import Manager.RigheCommesseManager;
import beans.Cassa;
import beans.Ordine;
import javax.swing.table.DefaultTableModel;
import model.CategorieProdotti;
import model.ListinoReale;
import guiUtils.GuiUtils;
import utils.IdDescr;
import guiUtils.TableMouseListener;
import guiUtils.BetterTableCellRenderer;
import jasper.JrRigaOrdineFactory;
import jasper.JrTestataOrdine;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import model.RigheCommesse;
import model.Sconti;
import model.Varianti;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import utils.Sconto;
import utils.Valuta;

/**
 *
 * @author Stefano
 */
public class CassaGui extends javax.swing.JFrame {

    private final Cassa cassa;
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

    private int cListino;
    private int rListino;
    private int cOrdine;
    private int rOrdine;

    private float percScontoDaApplicare;
    private float scontoApplicato;
    private float totale;
    private float netto;

    /**
     *
     * @param giorno
     * @param cassa
     * @param operatore
     */
    public CassaGui(String giorno, String cassa, String operatore) {
        this.cassa = new Cassa(giorno, cassa, operatore);
        initComponents();
        SetupGui();
        SetupCategorie();
        SetupSconti();
        RefreshListino();
        PopolaVarianti();
        StatoBottoni();
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
    private void Annulla() {

        if (ordine != null) {
            if (ChiediConferma("Annulla Ordine")) {
                ordine.AnnullaOrdine();
                ordine = null;
                SvuotaSconto();
                RefreshOrdine();
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
    private void AggiornaOrdine() {
        if (ControlloOrdine()) {
            ordine.getCommessa().setNomeCliente(jTxtCliente.getText());
            ordine.getCommessa().setCoperti((int) jSpinCoperti.getValue());
            IdDescr sconto = new IdDescr((String) jCmbSconti.getSelectedItem());
            ordine.getCommessa().setDescSconto(sconto.getDescr());

            ordine.getCommessaMgr().update(ordine.getCommessa().getId(), ordine.getCommessa());
        }
    }

    /**
     *
     */
    private void Conferma() {
        if (ControlloOrdine()) {
            ordine.getCommessa().setNomeCliente(jTxtCliente.getText());
            ordine.getCommessa().setCoperti((int) jSpinCoperti.getValue());
            IdDescr sconto = new IdDescr((String) jCmbSconti.getSelectedItem());
            ordine.getCommessa().setDescSconto(sconto.getDescr());

            ordine.getCommessaMgr().update(ordine.getCommessa().getId(), ordine.getCommessa());
            ordine = null;
            SvuotaSconto();
            RefreshOrdine();
        }
    }

    /**
     *
     */
    private void SetupGui() {

        setTitle(cassa.getGiorno().getTitolo());
        Sconto scontoGiorno = new Sconto(cassa.getGiorno().getScontoGiorno());
        jTxtScontoGiorno.setText(scontoGiorno.toString());
        jTblListino.setComponentPopupMenu(jPopListino);
        jTblListino.addMouseListener(new TableMouseListener(jTblListino));

        jTblOrdine.setComponentPopupMenu(jPopOrdine);
        jTblOrdine.addMouseListener(new TableMouseListener(jTblOrdine));

        jTblOrdine.setDefaultRenderer(Object.class, new BetterTableCellRenderer());
        jTblListino.setDefaultRenderer(Object.class, new BetterTableCellRenderer());
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
            RigheCommesseManager rigaMgr = new RigheCommesseManager();

            rigaMgr.delete(id);
            RefreshOrdine();
        }
    }

    /**
     *
     * @param variante
     */
    private void ModificaVariante(String variante) {
        int id = getIdRigaOrdine();
        RigheCommesseManager rigaMgr = new RigheCommesseManager();

        rigaMgr.CambiaVariante(id, variante);
        RefreshOrdine();
    }

    /**
     *
     */
    private void incQuantita() {
        int id = getIdRigaOrdine();
        RigheCommesseManager rigaMgr = new RigheCommesseManager();

        rigaMgr.incQuantita(id);
        RefreshOrdine();
    }

    /**
     *
     */
    private void SettaQuantita(int qta) {
        int id = getIdRigaOrdine();
        RigheCommesseManager rigaMgr = new RigheCommesseManager();

        rigaMgr.SettaQuantita(id, qta);
        RefreshOrdine();
    }

    /**
     *
     */
    private void decQuantita() {
        int id = getIdRigaOrdine();
        RigheCommesseManager rigaMgr = new RigheCommesseManager();

        rigaMgr.decQuantita(id);
        RefreshOrdine();
    }

    /**
     *
     * @param qta
     */
    private void decQuantita(int qta) {
        int id = getIdRigaOrdine();
        RigheCommesseManager rigaMgr = new RigheCommesseManager();

        rigaMgr.decQuantita(id, qta);
        RefreshOrdine();
    }

    /**
     *
     */
    private void AggiungiDaListino() {
        RigheCommesse riga = new RigheCommesse();

        Valuta prezzo;
        DefaultTableModel model = (DefaultTableModel) jTblListino.getModel();

        prezzo = new Valuta(model.getValueAt(rListino, TBL_LISTINO_PREZZO));
        riga.setIdProdotto((Integer) model.getValueAt(rListino, TBL_LISTINO_ID_PRODOTTO));
        riga.setPrezzoListino(prezzo.getValore());
        riga.setQuantita(1);
        riga.setIdCommessa(ordine.getCommessa().getId());

        RigheCommesseManager mgrRiga = new RigheCommesseManager();
        mgrRiga.insert(riga);

        RefreshOrdine();

    }

    /**
     *
     */
    private void RefreshOrdine() {

        if (ordine != null) {
            DefaultTableModel model = (DefaultTableModel) jTblOrdine.getModel();
            GuiUtils.EmptyJtable(jTblOrdine);
            int idCommessa = ordine.getCommessa().getId();
            List<RigheCommesse> righe = ordine.getRigheMgr().getByCommessa(idCommessa);
            if (righe != null) {
                for (RigheCommesse riga : righe) {
                    model.addRow(riga.getRow());
                }
                jTblOrdine.requestFocus();
                if (jTblOrdine.getRowCount() > 0) {
                    if (rOrdine >= jTblOrdine.getRowCount()) {
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
        int idCommessa = ordine.getCommessa().getId();

        Valuta vTotale;
        totale = ordine.getRigheMgr().getTotale(idCommessa);
        vTotale = new Valuta(totale);
        jTxtTotale.setText(vTotale.toString());

        percScontoDaApplicare = getScontoDaApplicare();
        scontoApplicato = vTotale.getValore() / 100 * percScontoDaApplicare;
        Valuta vScontoOrdine = new Valuta(scontoApplicato);
        netto = totale - scontoApplicato;
        Valuta vNnetto = new Valuta(netto);

        jTxtScontoOrdine.setText(vScontoOrdine.toString());
        jTxtNetto.setText(vNnetto.toString());
    }

    /**
     *
     */
    private void AnnullaConto() {
        jTxtTotale.setText("");
        jTxtScontoOrdine.setText("");
        jTxtNetto.setText("");
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
        jTblListino.changeSelection(rListino, cListino, false, false);

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
    private void StampaOrdine() {
        AggiornaOrdine();
        try {

            JasperReport report = (JasperReport) JRLoader.loadObjectFromFile("src/main/resources/jasper/stampaCommessaCliente.jasper");

            JrTestataOrdine jrTestata = new JrTestataOrdine();
            jrTestata.setCassa(ordine.getCassa().getDescrizione());
            jrTestata.setCassiere(ordine.getOperatore().getOperatore());
            jrTestata.setCliente(jTxtCliente.getText());
            jrTestata.setCoperti((int) jSpinCoperti.getValue());
            jrTestata.setNetto(this.netto);
            jrTestata.setSconto(this.scontoApplicato);
            jrTestata.setScontoDaApplicare(percScontoDaApplicare);
            jrTestata.setTotale(totale);
            JrRigaOrdineFactory jrFactory = new JrRigaOrdineFactory(ordine);

            Map parameters = jrTestata.getHashMap();

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters,
                    new JRBeanCollectionDataSource(jrFactory.getBeanCollection()));
            jasperPrint.setName("titolo");

            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    private boolean ControlloOrdine() {
        boolean esito = true;

        if ((int) jSpinCoperti.getValue() == 0) {
            esito = ChiediConferma("Numero coperti corretto?");
        }

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

        jBtnElimina.setEnabled(flgOrdineSel);
        jBtnAggiungi.setEnabled(flgListinoSel && flgOrdineOk);
        jMenuAggiungi.setEnabled(flgListinoSel && flgOrdineOk);

        jBtnConfermaVariante.setEnabled(flgOrdineSel);
        jBtnEliminaVariante.setEnabled(flgOrdineSel);
        jCmbVarianti.setEnabled(flgOrdineSel);
        jBtnInc.setEnabled(flgOrdineSel);
        jBtnDec.setEnabled(flgOrdineSel);

        jBtnQta.setEnabled(flgOrdineSel);

        jBtnAnnullaFiltro.setEnabled(jCmbCategoria.getSelectedItem() != null);
        if (!flgOrdineSel) {
            jCmbVarianti.setSelectedItem(null);
        } else {
            jBtnDec.setEnabled(quantita > 1);
            jMenuDec.setEnabled(quantita > 1);
        }

        if (!flgOrdineOk) {
            jTxtCliente.setText("");
            jTxtTavolo.setText("");
            jSpinCoperti.setValue(0);
        }
        jBtnNuovoOrdine.setEnabled(!flgOrdineOk);
        jBtnAnnullaOrdine.setEnabled(flgOrdineOk);
        jBtnConfermaOrdine.setEnabled(flgOrdineRigheOk);
        jBtnStampa.setEnabled(flgOrdineRigheOk);
        jTxtCliente.setEnabled(flgOrdineOk);
        jTxtTavolo.setEnabled(flgOrdineOk);
        jSpinCoperti.setEnabled(flgOrdineOk);
        jTblListino.setEnabled(flgOrdineOk);
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
        jBtnAggiungi = new javax.swing.JButton();
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
        jPanelOrdine = new javax.swing.JPanel();
        jBtnNuovoOrdine = new javax.swing.JButton();
        jBtnConfermaOrdine = new javax.swing.JButton();
        jBtnAnnullaOrdine = new javax.swing.JButton();
        jBtnStampa = new javax.swing.JButton();
        jPanelSconto = new javax.swing.JPanel();
        jLblScontoGiorno = new javax.swing.JLabel();
        jTxtScontoGiorno = new javax.swing.JTextField();
        jLblSconti = new javax.swing.JLabel();
        jCmbSconti = new javax.swing.JComboBox();
        jLblCoperti = new javax.swing.JLabel();
        jSpinCoperti = new javax.swing.JSpinner();
        jBtnDec = new javax.swing.JButton();
        jBtnInc = new javax.swing.JButton();
        jSpinQta = new javax.swing.JSpinner();
        jBtnQta = new javax.swing.JButton();
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
        jTblOrdine.getTableHeader().setReorderingAllowed(false);
        jTblOrdine.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblOrdineMouseClicked(evt);
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

        jBtnAggiungi.setText("<- Aggiungi");
        jBtnAggiungi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAggiungiActionPerformed(evt);
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
                .addComponent(jBtnConfermaVariante)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 305, Short.MAX_VALUE)
                .addComponent(jBtnEliminaVariante))
            .addGroup(jPanelVariantiLayout.createSequentialGroup()
                .addComponent(jCmbVarianti, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jLblTotale.setText("Totale:");

        jTxtTotale.setEditable(false);

        jLblScontoOrdine.setText("Sconto:");

        jTxtScontoOrdine.setEditable(false);

        jTxtNetto.setEditable(false);
        jTxtNetto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLblContanti.setText("Contanti:");

        jLblResto.setText("Resto:");

        jTxtResto.setEditable(false);

        javax.swing.GroupLayout jPanelContoLayout = new javax.swing.GroupLayout(jPanelConto);
        jPanelConto.setLayout(jPanelContoLayout);
        jPanelContoLayout.setHorizontalGroup(
            jPanelContoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContoLayout.createSequentialGroup()
                .addGroup(jPanelContoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelContoLayout.createSequentialGroup()
                        .addComponent(jLblTotale)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                        .addContainerGap())
                    .addGroup(jPanelContoLayout.createSequentialGroup()
                        .addComponent(jTxtScontoOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTxtNetto, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))))
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
                        .addComponent(jLblResto)))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jPanelOrdine.setBorder(javax.swing.BorderFactory.createTitledBorder("Ordine"));

        jBtnNuovoOrdine.setText("Nuovo");
        jBtnNuovoOrdine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNuovoOrdineActionPerformed(evt);
            }
        });

        jBtnConfermaOrdine.setText("Conferma");
        jBtnConfermaOrdine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnConfermaOrdineActionPerformed(evt);
            }
        });

        jBtnAnnullaOrdine.setText("Annulla");
        jBtnAnnullaOrdine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAnnullaOrdineActionPerformed(evt);
            }
        });

        jBtnStampa.setText("Stampa");
        jBtnStampa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnStampaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelOrdineLayout = new javax.swing.GroupLayout(jPanelOrdine);
        jPanelOrdine.setLayout(jPanelOrdineLayout);
        jPanelOrdineLayout.setHorizontalGroup(
            jPanelOrdineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOrdineLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnNuovoOrdine)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnConfermaOrdine)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnStampa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnAnnullaOrdine))
        );
        jPanelOrdineLayout.setVerticalGroup(
            jPanelOrdineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOrdineLayout.createSequentialGroup()
                .addGroup(jPanelOrdineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnNuovoOrdine)
                    .addComponent(jBtnConfermaOrdine)
                    .addComponent(jBtnAnnullaOrdine)
                    .addComponent(jBtnStampa))
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
                .addContainerGap()
                .addComponent(jLblScontoGiorno)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTxtScontoGiorno, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLblSconti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCmbSconti, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelScontoLayout.setVerticalGroup(
            jPanelScontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelScontoLayout.createSequentialGroup()
                .addGroup(jPanelScontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelScontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLblSconti)
                        .addComponent(jCmbSconti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelScontoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLblScontoGiorno)
                        .addComponent(jTxtScontoGiorno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLblCoperti.setText("Coperti:");

        jSpinCoperti.setModel(new javax.swing.SpinnerNumberModel(0, 0, 99, 1));

        jBtnDec.setText("-1");
        jBtnDec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnDecActionPerformed(evt);
            }
        });

        jBtnInc.setText("+1");
        jBtnInc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnIncActionPerformed(evt);
            }
        });

        jSpinQta.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        jBtnQta.setText("Quantità");
        jBtnQta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnQtaActionPerformed(evt);
            }
        });

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
                        .addComponent(jPanelOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelSconto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jPanelVarianti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanelConto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBtnElimina, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBtnQta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSpinQta, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnDec, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jBtnInc))
                            .addComponent(jScrollOrdine))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLblFiltro)
                                    .addComponent(jBtnAggiungi))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollListino, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jCmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jBtnAnnullaFiltro))))
                            .addComponent(jBtnEsce, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLblOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(290, 290, 290)
                                        .addComponent(jTxtTavolo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLblCoperti)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSpinCoperti, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(101, 101, 101)
                                .addComponent(jLblListino, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLblCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(jLblTavolo)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelSconto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelOrdine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblCliente)
                    .addComponent(jTxtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblTavolo)
                    .addComponent(jTxtTavolo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblFiltro)
                    .addComponent(jBtnAnnullaFiltro)
                    .addComponent(jLblCoperti)
                    .addComponent(jSpinCoperti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblOrdine)
                    .addComponent(jLblListino))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnAggiungi)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnElimina)
                            .addComponent(jBtnQta)
                            .addComponent(jSpinQta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnDec)
                            .addComponent(jBtnInc)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollListino, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnEsce)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jPanelVarianti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanelConto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     *
     * @param evt
     */
    private void jMenuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuExitActionPerformed
        if (ChiediConferma("Vuoi uscire?")) {
            System.exit(0);
        }
    }//GEN-LAST:event_jMenuExitActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnEsceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEsceActionPerformed
        if (ChiediConferma("Vuoi uscire?")) {
            System.exit(0);
        }
        System.exit(0);
    }//GEN-LAST:event_jBtnEsceActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnNuovoOrdineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNuovoOrdineActionPerformed

        String cliente = (String) JOptionPane.showInputDialog(this, "Cliente ?",
                "Ordine", JOptionPane.PLAIN_MESSAGE, null, null, "");
        if (!cliente.isEmpty()) {
            ordine = new Ordine(this.cassa, cliente);
            jTxtCliente.setText(cliente);
        }
        StatoBottoni();
    }//GEN-LAST:event_jBtnNuovoOrdineActionPerformed

    /**
     *
     * @param evt
     */
    private void jTblOrdineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblOrdineMouseClicked
        cOrdine = jTblOrdine.getSelectedColumn();
        rOrdine = jTblOrdine.getSelectedRow();
        StatoBottoni();
    }//GEN-LAST:event_jTblOrdineMouseClicked

    /**
     *
     * @param evt
     */
    private void jTblListinoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblListinoMouseClicked
        cListino = jTblListino.getSelectedColumn();
        rListino = jTblListino.getSelectedRow();
        StatoBottoni();
    }//GEN-LAST:event_jTblListinoMouseClicked

    /**
     *
     * @param evt
     */
    private void jBtnAnnullaFiltroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnAnnullaFiltroMouseClicked
        jCmbCategoria.setSelectedIndex(-1);
        RefreshListino();
        StatoBottoni();
    }//GEN-LAST:event_jBtnAnnullaFiltroMouseClicked

    /**
     *
     * @param evt
     */
    private void jCmbCategoriaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbCategoriaItemStateChanged
        RefreshListino();
        StatoBottoni();
    }//GEN-LAST:event_jCmbCategoriaItemStateChanged

    /**
     *
     * @param evt
     */
    private void jMenuAggiungiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuAggiungiMouseClicked
        AggiungiDaListino();
        StatoBottoni();
    }//GEN-LAST:event_jMenuAggiungiMouseClicked

    /**
     *
     * @param evt
     */
    private void jMenuAggiungiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAggiungiActionPerformed
        AggiungiDaListino();
        StatoBottoni();
    }//GEN-LAST:event_jMenuAggiungiActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnAggiungiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAggiungiActionPerformed
        AggiungiDaListino();
        StatoBottoni();
    }//GEN-LAST:event_jBtnAggiungiActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminaActionPerformed
        EliminaRiga();
        StatoBottoni();
    }//GEN-LAST:event_jBtnEliminaActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnIncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnIncActionPerformed
        incQuantita();
        StatoBottoni();
    }//GEN-LAST:event_jBtnIncActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnDecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDecActionPerformed
        decQuantita();
        StatoBottoni();
    }//GEN-LAST:event_jBtnDecActionPerformed

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
    private void jMenuEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuEliminaActionPerformed
        EliminaRiga();
        StatoBottoni();
    }//GEN-LAST:event_jMenuEliminaActionPerformed

    /**
     *
     * @param evt
     */
    private void jCmbScontiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbScontiActionPerformed
        RefreshOrdine();
        StatoBottoni();

    }//GEN-LAST:event_jCmbScontiActionPerformed

    /**
     *
     * @param evt
     */
    private void jCmbScontiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCmbScontiMouseClicked
        RefreshOrdine();

    }//GEN-LAST:event_jCmbScontiMouseClicked

    /**
     *
     * @param evt
     */
    private void jBtnAnnullaOrdineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAnnullaOrdineActionPerformed
        Annulla();
        StatoBottoni();
    }//GEN-LAST:event_jBtnAnnullaOrdineActionPerformed

    /**
     *
     * @param evt
     */
    private void jMenuIncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuIncActionPerformed
        incQuantita();
        StatoBottoni();
    }//GEN-LAST:event_jMenuIncActionPerformed

    /**
     *
     * @param evt
     */
    private void jMenuDecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuDecActionPerformed
        decQuantita();
        StatoBottoni();
    }//GEN-LAST:event_jMenuDecActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnConfermaOrdineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnConfermaOrdineActionPerformed
        Conferma();
        StatoBottoni();
    }//GEN-LAST:event_jBtnConfermaOrdineActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnQtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnQtaActionPerformed
        SettaQuantita((int) jSpinQta.getValue());
    }//GEN-LAST:event_jBtnQtaActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnEliminaVarianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminaVarianteActionPerformed
        ModificaVariante("");
    }//GEN-LAST:event_jBtnEliminaVarianteActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnConfermaVarianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnConfermaVarianteActionPerformed
        String variante = (String) jCmbVarianti.getSelectedItem();
        ModificaVariante(variante);
    }//GEN-LAST:event_jBtnConfermaVarianteActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnStampaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnStampaActionPerformed
        StampaOrdine();
    }//GEN-LAST:event_jBtnStampaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAggiungi;
    private javax.swing.JButton jBtnAnnullaFiltro;
    private javax.swing.JButton jBtnAnnullaOrdine;
    private javax.swing.JButton jBtnConfermaOrdine;
    private javax.swing.JButton jBtnConfermaVariante;
    private javax.swing.JButton jBtnDec;
    private javax.swing.JButton jBtnElimina;
    private javax.swing.JButton jBtnEliminaVariante;
    private javax.swing.JButton jBtnEsce;
    private javax.swing.JButton jBtnInc;
    private javax.swing.JButton jBtnNuovoOrdine;
    private javax.swing.JButton jBtnQta;
    private javax.swing.JButton jBtnStampa;
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
    private javax.swing.JTextField jTxtResto;
    private javax.swing.JTextField jTxtScontoGiorno;
    private javax.swing.JTextField jTxtScontoOrdine;
    private javax.swing.JTextField jTxtTavolo;
    private javax.swing.JTextField jTxtTotale;
    // End of variables declaration//GEN-END:variables
}
