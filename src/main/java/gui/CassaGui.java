package gui;

import Manager.RigheCommesseManager;
import beans.Cassa;
import beans.Ordine;
import static java.awt.event.MouseEvent.*;
import javax.swing.table.DefaultTableModel;
import model.CategorieProdotti;
import model.ListinoReale;
import guiUtils.GuiUtils;
import utils.IdDescr;
import guiUtils.TableMouseListener;
import guiUtils.BetterTableCellRenderer;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.RigheCommesse;
import model.Sconti;
import model.Varianti;
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
        int row = jTblOrdine.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) jTblOrdine.getModel();
        return (int) model.getValueAt(row, TBL_RIGHE_ID);
    }

    /**
     *
     */
    private void EliminaRiga() {
        int id = getIdRigaOrdine();
        RigheCommesseManager rigaMgr = new RigheCommesseManager();

        rigaMgr.delete(id);
        RefreshOrdine();
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
    private void decQuantita() {
        int id = getIdRigaOrdine();
        RigheCommesseManager rigaMgr = new RigheCommesseManager();

        rigaMgr.decQuantita(id);
        RefreshOrdine();
    }

    /**
     *
     */
    private void AggiungiDaListino() {
        int row = jTblListino.getSelectedRow();
        RigheCommesse riga = new RigheCommesse();

        Valuta prezzo;
        DefaultTableModel model = (DefaultTableModel) jTblListino.getModel();

        prezzo = new Valuta(model.getValueAt(row, TBL_LISTINO_PREZZO));
        riga.setIdProdotto((Integer) model.getValueAt(row, TBL_LISTINO_ID_PRODOTTO));
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
            for (RigheCommesse riga : righe) {
                model.addRow(riga.getRow());
            }

            RefreshConto();
        }
    }

    /**
     *
     */
    private void RefreshConto() {
        int idCommessa = ordine.getCommessa().getId();

        float percScontoDaApplicare;
        float scontoApplicato;
        Valuta totale;

        totale = new Valuta(ordine.getRigheMgr().getTotale(idCommessa));
        jTxtTotale.setText(totale.toString());

        percScontoDaApplicare = getScontoDaApplicare();
        scontoApplicato = totale.getValore() / 100 * percScontoDaApplicare;
        Valuta scontoOrdine = new Valuta(scontoApplicato);
        Valuta netto = new Valuta(totale.getValore() - scontoApplicato);

        jTxtScontoOrdine.setText(scontoOrdine.toString());
        jTxtNetto.setText(netto.toString());
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
    private void StatoBottoni() {
        boolean flgOrdineOk;
        boolean flgOrdineSel;
        boolean flgListinoSel;

        int quantita;

        flgOrdineOk = ordine != null;
        flgListinoSel = jTblListino.getSelectedRowCount() != 0;
        flgOrdineSel = jTblOrdine.getSelectedRowCount() != 0;

        if (flgOrdineSel) {
            int row = jTblOrdine.getSelectedRow();

            DefaultTableModel model = (DefaultTableModel) jTblOrdine.getModel();
            quantita = (int) model.getValueAt(row, TBL_RIGHE_QUANTITA);
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

        jBtnAnnullaFiltro.setEnabled(jCmbCategoria.getSelectedItem() != null);
        if (!flgOrdineSel) {
            jCmbVarianti.setSelectedItem(null);
        } else {
            jBtnDec.setEnabled(quantita > 1);
        }

        if (!flgOrdineOk) {
            jTxtCliente.setText("");
            jTxtTavolo.setText("");
            JSpinCoperti.setValue(0);
        }
        jBtnNuovoOrdine.setEnabled(!flgOrdineOk);
        jBtnAnnullaOrdine.setEnabled(flgOrdineOk);
        jBtnConfermaOrdine.setEnabled(flgOrdineOk);
        jTxtCliente.setEnabled(flgOrdineOk);
        jTxtTavolo.setEnabled(flgOrdineOk);
        JSpinCoperti.setEnabled(flgOrdineOk);
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
        jMenuVarianti = new javax.swing.JMenuItem();
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
        jBtnInc = new javax.swing.JButton();
        jBtnDec = new javax.swing.JButton();
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
        jPanelSconto = new javax.swing.JPanel();
        jLblScontoGiorno = new javax.swing.JLabel();
        jTxtScontoGiorno = new javax.swing.JTextField();
        jLblSconti = new javax.swing.JLabel();
        jCmbSconti = new javax.swing.JComboBox();
        jLblCoperti = new javax.swing.JLabel();
        JSpinCoperti = new javax.swing.JSpinner();
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

        jMenuVarianti.setText("Varianti");
        jPopOrdine.add(jMenuVarianti);

        jMenuInc.setText("+1");
        jPopOrdine.add(jMenuInc);

        jMenuDec.setText("-1");
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

        jBtnInc.setText("+1");
        jBtnInc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnIncActionPerformed(evt);
            }
        });

        jBtnDec.setText("-1");
        jBtnDec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnDecActionPerformed(evt);
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
                .addContainerGap()
                .addComponent(jCmbVarianti, 0, 257, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnConfermaVariante)
                .addGap(18, 18, 18)
                .addComponent(jBtnEliminaVariante)
                .addContainerGap())
        );
        jPanelVariantiLayout.setVerticalGroup(
            jPanelVariantiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelVariantiLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelVariantiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCmbVarianti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnConfermaVariante)
                    .addComponent(jBtnEliminaVariante))
                .addGap(27, 27, 27))
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
                .addContainerGap()
                .addGroup(jPanelContoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelContoLayout.createSequentialGroup()
                        .addComponent(jLblTotale)
                        .addGap(18, 18, 18))
                    .addGroup(jPanelContoLayout.createSequentialGroup()
                        .addComponent(jLblContanti)
                        .addGap(7, 7, 7)))
                .addGroup(jPanelContoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTxtContanti, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(jTxtTotale))
                .addGap(26, 26, 26)
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
                        .addComponent(jTxtNetto))))
        );
        jPanelContoLayout.setVerticalGroup(
            jPanelContoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelContoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblTotale)
                    .addComponent(jTxtTotale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblScontoOrdine)
                    .addComponent(jTxtScontoOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtNetto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelContoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblContanti)
                    .addComponent(jTxtContanti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblResto)
                    .addComponent(jTxtResto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelOrdine.setBorder(javax.swing.BorderFactory.createTitledBorder("Ordine"));

        jBtnNuovoOrdine.setText("Nuovo");
        jBtnNuovoOrdine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNuovoOrdineActionPerformed(evt);
            }
        });

        jBtnConfermaOrdine.setText("Conferma");

        jBtnAnnullaOrdine.setText("Annulla");

        javax.swing.GroupLayout jPanelOrdineLayout = new javax.swing.GroupLayout(jPanelOrdine);
        jPanelOrdine.setLayout(jPanelOrdineLayout);
        jPanelOrdineLayout.setHorizontalGroup(
            jPanelOrdineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOrdineLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBtnNuovoOrdine)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnConfermaOrdine)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnAnnullaOrdine)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelOrdineLayout.setVerticalGroup(
            jPanelOrdineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOrdineLayout.createSequentialGroup()
                .addGroup(jPanelOrdineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnNuovoOrdine)
                    .addComponent(jBtnConfermaOrdine)
                    .addComponent(jBtnAnnullaOrdine))
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

        JSpinCoperti.setModel(new javax.swing.SpinnerNumberModel(0, 0, 99, 1));

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
                        .addGap(18, 18, 18)
                        .addComponent(jPanelSconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLblCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(jLblTavolo)
                        .addGap(310, 310, 310)
                        .addComponent(jCmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jPanelVarianti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jPanelConto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jBtnEsce))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLblOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jBtnElimina, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jBtnInc)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jBtnDec))
                                        .addComponent(jScrollOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jBtnAggiungi))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(290, 290, 290)
                                    .addComponent(jTxtTavolo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLblCoperti)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(JSpinCoperti, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLblListino, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollListino, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLblFiltro)
                                    .addGap(252, 252, 252)
                                    .addComponent(jBtnAnnullaFiltro)))
                            .addGap(10, 10, 10))))
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
                    .addComponent(JSpinCoperti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblOrdine)
                    .addComponent(jLblListino))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBtnAggiungi)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jBtnElimina)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jBtnDec)
                                        .addComponent(jBtnInc)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelConto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanelVarianti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollListino, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnEsce)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     *
     * @param evt
     */
    private void jMenuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuExitActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnEsceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEsceActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jBtnEsceActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnNuovoOrdineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNuovoOrdineActionPerformed
        // create a jframe
        JFrame frame = new JFrame("JOptionPane showMessageDialog example");

        String cliente = (String) JOptionPane.showInputDialog(frame, "Cliente ?",
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
        int colonna = jTblOrdine.getSelectedColumn();
        int riga = jTblOrdine.getSelectedRow();
        if (evt.getButton() == BUTTON3) {
            System.out.printf("Colonna %d", jTblOrdine.getSelectedColumn());
        }
        StatoBottoni();
    }//GEN-LAST:event_jTblOrdineMouseClicked

    /**
     *
     * @param evt
     */
    private void jTblListinoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblListinoMouseClicked
        int colonna = jTblOrdine.getSelectedColumn();
        int riga = jTblOrdine.getSelectedRow();

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
        // TODO add your handling code here:
        AggiungiDaListino();
        StatoBottoni();
    }//GEN-LAST:event_jMenuAggiungiActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnAggiungiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAggiungiActionPerformed
        // TODO add your handling code here:
        AggiungiDaListino();
        StatoBottoni();
    }//GEN-LAST:event_jBtnAggiungiActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminaActionPerformed
        EliminaRiga();
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
    private void jBtnConfermaVarianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnConfermaVarianteActionPerformed
        String variante = (String) jCmbVarianti.getSelectedItem();
        ModificaVariante(variante);
    }//GEN-LAST:event_jBtnConfermaVarianteActionPerformed

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
    private void jBtnAnnullaFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAnnullaFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnAnnullaFiltroActionPerformed

    private void jMenuEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuEliminaActionPerformed
        EliminaRiga();
    }//GEN-LAST:event_jMenuEliminaActionPerformed

    private void jCmbScontiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbScontiActionPerformed
        // TODO add your handling code here:
        RefreshOrdine();

    }//GEN-LAST:event_jCmbScontiActionPerformed

    private void jCmbScontiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCmbScontiMouseClicked
        // TODO add your handling code here:
        RefreshOrdine();

    }//GEN-LAST:event_jCmbScontiMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner JSpinCoperti;
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
    private javax.swing.JMenuItem jMenuVarianti;
    private javax.swing.JPanel jPanelConto;
    private javax.swing.JPanel jPanelOrdine;
    private javax.swing.JPanel jPanelSconto;
    private javax.swing.JPanel jPanelVarianti;
    private javax.swing.JPopupMenu jPopListino;
    private javax.swing.JPopupMenu jPopOrdine;
    private javax.swing.JScrollPane jScrollListino;
    private javax.swing.JScrollPane jScrollOrdine;
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
