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
import model.RigheCommesse;
import model.Varianti;
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
    private final int TBL_RIGHE_SCONTO = 2;
    private final int TBL_RIGHE_QUANTITA = 3;
    private final int TBL_RIGHE_NOTE = 4;
    private final int TBL_RIGHE_ID_PRODOTTO = 5;
    private final int TBL_RIGHE_ID = 6;

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
        RefreshListino();
        StatoBottoni();
    }

    /**
     *
     */
    private void SetupGui() {

        jTblListino.setComponentPopupMenu(jPopListino);
        jTblListino.addMouseListener(new TableMouseListener(jTblListino));

        jTblOrdine.setComponentPopupMenu(jPopOrdine);
        jTblOrdine.addMouseListener(new TableMouseListener(jTblOrdine));

        PopolaVarianti();
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
        //TODO

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
        DefaultTableModel model = (DefaultTableModel) jTblOrdine.getModel();
        GuiUtils.EmptyJtable(jTblOrdine);
        int idCommessa = ordine.getCommessa().getId();
        List<RigheCommesse> righe = ordine.getRigheMgr().getByCommessa(idCommessa);
        for (RigheCommesse riga : righe) {
            model.addRow(riga.getRow());
        }
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
            int row = jTblListino.getSelectedRow();

            DefaultTableModel model = (DefaultTableModel) jTblOrdine.getModel();
            quantita = (int) model.getValueAt(row, TBL_RIGHE_QUANTITA);
        } else {
            quantita = 0;
        }

        jBtnElimina.setEnabled(flgOrdineSel);
        jBtnAggiungi.setEnabled(flgListinoSel && flgOrdineOk);

        jBtnConfermaVariante.setEnabled(flgOrdineSel);
        jBtnEliminaVariante.setEnabled(flgOrdineSel);
        jCmbVarianti.setEnabled(flgOrdineSel);
        jBtnInc.setEnabled(flgOrdineSel);
        jbtnDec.setEnabled(flgOrdineSel);

        jBtnAnnullaFiltro.setEnabled(jCmbCategoria.getSelectedItem() != null);
        if (!flgOrdineSel) {
            jCmbVarianti.setSelectedItem(null);
        } else {
            jbtnDec.setEnabled(quantita > 1);
        }

        jBtnNuovoOrdine.setEnabled(!flgOrdineOk);
        jBtnAnnullaOrdine.setEnabled(flgOrdineOk);
        jBtnConfermaOrdine.setEnabled(flgOrdineOk);

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
        jButtonEsce = new javax.swing.JButton();
        jBtnNuovoOrdine = new javax.swing.JButton();
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
        jbtnDec = new javax.swing.JButton();
        jPanelVarianti = new javax.swing.JPanel();
        jCmbVarianti = new javax.swing.JComboBox();
        jBtnConfermaVariante = new javax.swing.JButton();
        jBtnEliminaVariante = new javax.swing.JButton();
        jLabelVarianti = new javax.swing.JLabel();
        jLabelListino = new javax.swing.JLabel();
        jLabelOrdine = new javax.swing.JLabel();
        jBtnConfermaOrdine = new javax.swing.JButton();
        jBtnAnnullaOrdine = new javax.swing.JButton();
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
        jPopOrdine.add(jMenuElimina);

        jMenuVarianti.setText("Varianti");
        jPopOrdine.add(jMenuVarianti);

        jMenuInc.setText("+1");
        jPopOrdine.add(jMenuInc);

        jMenuDec.setText("-1");
        jPopOrdine.add(jMenuDec);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sagra Sant'Andrea: Cassa");

        jButtonEsce.setText("Esce");
        jButtonEsce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEsceActionPerformed(evt);
            }
        });

        jBtnNuovoOrdine.setText("Nuovo Ordine");
        jBtnNuovoOrdine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNuovoOrdineActionPerformed(evt);
            }
        });

        jLblCliente.setText("Cliente:");

        jLblTavolo.setText("Tavolo:");

        jTblOrdine.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Prodotto", "Prezzo", "Sconto", "Quantità", "Note", "IdProdotto", "Id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
            jTblOrdine.getColumnModel().getColumn(5).setMinWidth(0);
            jTblOrdine.getColumnModel().getColumn(5).setPreferredWidth(0);
            jTblOrdine.getColumnModel().getColumn(5).setMaxWidth(0);
            jTblOrdine.getColumnModel().getColumn(6).setMinWidth(0);
            jTblOrdine.getColumnModel().getColumn(6).setPreferredWidth(0);
            jTblOrdine.getColumnModel().getColumn(6).setMaxWidth(0);
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
        jTblListino.setToolTipText("");
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
        jBtnAggiungi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBtnAggiungiMouseClicked(evt);
            }
        });
        jBtnAggiungi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAggiungiActionPerformed(evt);
            }
        });

        jBtnElimina.setText("Elimina");
        jBtnElimina.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBtnEliminaMouseClicked(evt);
            }
        });
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

        jbtnDec.setText("-1");
        jbtnDec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDecActionPerformed(evt);
            }
        });

        jPanelVarianti.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        jLabelVarianti.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelVarianti.setText("Varianti");

        javax.swing.GroupLayout jPanelVariantiLayout = new javax.swing.GroupLayout(jPanelVarianti);
        jPanelVarianti.setLayout(jPanelVariantiLayout);
        jPanelVariantiLayout.setHorizontalGroup(
            jPanelVariantiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVariantiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelVariantiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelVariantiLayout.createSequentialGroup()
                        .addComponent(jLabelVarianti)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelVariantiLayout.createSequentialGroup()
                        .addComponent(jCmbVarianti, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBtnConfermaVariante)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnEliminaVariante)))
                .addContainerGap())
        );
        jPanelVariantiLayout.setVerticalGroup(
            jPanelVariantiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelVariantiLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelVarianti)
                .addGap(2, 2, 2)
                .addGroup(jPanelVariantiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCmbVarianti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnConfermaVariante)
                    .addComponent(jBtnEliminaVariante))
                .addGap(27, 27, 27))
        );

        jLabelListino.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelListino.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelListino.setLabelFor(jTblOrdine);
        jLabelListino.setText("Listino");

        jLabelOrdine.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelOrdine.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelOrdine.setLabelFor(jTblOrdine);
        jLabelOrdine.setText("Ordine");

        jBtnConfermaOrdine.setText("Conferma Ordine");

        jBtnAnnullaOrdine.setText("Annulla Ordine");

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanelVarianti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jBtnElimina, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBtnInc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbtnDec)
                                .addGap(4, 4, 4))
                            .addComponent(jScrollOrdine)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jBtnNuovoOrdine)
                                        .addGap(22, 22, 22)
                                        .addComponent(jBtnConfermaOrdine))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLblCliente)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTxtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLblTavolo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTxtTavolo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jBtnAnnullaOrdine)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(jLabelListino, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLblFiltro)
                        .addGap(18, 18, 18)
                        .addComponent(jCmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(jBtnAnnullaFiltro))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButtonEsce)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jBtnAggiungi)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollListino, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnNuovoOrdine)
                            .addComponent(jBtnConfermaOrdine)
                            .addComponent(jBtnAnnullaOrdine))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLblCliente)
                            .addComponent(jTxtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblTavolo)
                            .addComponent(jTxtTavolo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblFiltro)
                            .addComponent(jBtnAnnullaFiltro))
                        .addGap(4, 4, 4)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelOrdine)
                    .addComponent(jLabelListino))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollListino, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnAggiungi)
                    .addComponent(jScrollOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnDec)
                    .addComponent(jBtnInc)
                    .addComponent(jBtnElimina))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonEsce)
                    .addComponent(jPanelVarianti, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     *
     * @param evt
     */
    private void jMenuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuExitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuExitActionPerformed

    /**
     *
     * @param evt
     */
    private void jButtonEsceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEsceActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButtonEsceActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnNuovoOrdineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNuovoOrdineActionPerformed
        // TODO add your handling code here:
        ordine = new Ordine(this.cassa, "Adriano Celentano");
        StatoBottoni();
    }//GEN-LAST:event_jBtnNuovoOrdineActionPerformed

    /**
     *
     * @param evt
     */
    private void jTblOrdineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblOrdineMouseClicked
        // TODO add your handling code here:
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
        // TODO add your handling code here:
        int colonna = jTblOrdine.getSelectedColumn();
        int riga = jTblOrdine.getSelectedRow();

        StatoBottoni();
    }//GEN-LAST:event_jTblListinoMouseClicked

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
     * @param evt
     */
    private void jBtnAnnullaFiltroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnAnnullaFiltroMouseClicked
        // TODO add your handling code here:
        jCmbCategoria.setSelectedIndex(-1);
        RefreshListino();
        StatoBottoni();
    }//GEN-LAST:event_jBtnAnnullaFiltroMouseClicked

    /**
     *
     * @param evt
     */
    private void jCmbCategoriaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbCategoriaItemStateChanged
        // TODO add your handling code here:
        RefreshListino();
        StatoBottoni();
    }//GEN-LAST:event_jCmbCategoriaItemStateChanged

    /**
     *
     * @param evt
     */
    private void jMenuAggiungiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuAggiungiMouseClicked
        // TODO add your handling code here:
        AggiungiDaListino();
    }//GEN-LAST:event_jMenuAggiungiMouseClicked

    /**
     *
     * @param evt
     */
    private void jBtnAggiungiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnAggiungiMouseClicked
        // TODO add your handling code here:
        AggiungiDaListino();
    }//GEN-LAST:event_jBtnAggiungiMouseClicked

    /**
     *
     * @param evt
     */
    private void jMenuAggiungiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAggiungiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuAggiungiActionPerformed

    /**
     *
     * @param evt
     */
    private void jBtnAggiungiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAggiungiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnAggiungiActionPerformed

    private void jBtnEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnEliminaActionPerformed

    private void jBtnEliminaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnEliminaMouseClicked
        // TODO add your handling code here:
        EliminaRiga();
    }//GEN-LAST:event_jBtnEliminaMouseClicked

    private void jBtnIncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnIncActionPerformed
        // TODO add your handling code here:
        incQuantita();
    }//GEN-LAST:event_jBtnIncActionPerformed

    private void jbtnDecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDecActionPerformed
        // TODO add your handling code here:
        decQuantita();
    }//GEN-LAST:event_jbtnDecActionPerformed

    private void jBtnConfermaVarianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnConfermaVarianteActionPerformed
        // TODO add your handling code here:
        String variante = (String) jCmbVarianti.getSelectedItem();
        ModificaVariante(variante);

    }//GEN-LAST:event_jBtnConfermaVarianteActionPerformed

    private void jBtnEliminaVarianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminaVarianteActionPerformed
        // TODO add your handling code here:
        ModificaVariante("");

    }//GEN-LAST:event_jBtnEliminaVarianteActionPerformed

    private void jBtnAnnullaFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAnnullaFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnAnnullaFiltroActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAggiungi;
    private javax.swing.JButton jBtnAnnullaFiltro;
    private javax.swing.JButton jBtnAnnullaOrdine;
    private javax.swing.JButton jBtnConfermaOrdine;
    private javax.swing.JButton jBtnConfermaVariante;
    private javax.swing.JButton jBtnElimina;
    private javax.swing.JButton jBtnEliminaVariante;
    private javax.swing.JButton jBtnInc;
    private javax.swing.JButton jBtnNuovoOrdine;
    private javax.swing.JButton jButtonEsce;
    private javax.swing.JComboBox jCmbCategoria;
    private javax.swing.JComboBox jCmbVarianti;
    private javax.swing.JLabel jLabelListino;
    private javax.swing.JLabel jLabelOrdine;
    private javax.swing.JLabel jLabelVarianti;
    private javax.swing.JLabel jLblCliente;
    private javax.swing.JLabel jLblFiltro;
    private javax.swing.JLabel jLblTavolo;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuAggiungi;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenuItem jMenuDec;
    private javax.swing.JMenuItem jMenuElimina;
    private javax.swing.JMenuItem jMenuExit;
    private javax.swing.JMenuItem jMenuInc;
    private javax.swing.JMenuItem jMenuVarianti;
    private javax.swing.JPanel jPanelVarianti;
    private javax.swing.JPopupMenu jPopListino;
    private javax.swing.JPopupMenu jPopOrdine;
    private javax.swing.JScrollPane jScrollListino;
    private javax.swing.JScrollPane jScrollOrdine;
    private javax.swing.JTable jTblListino;
    private javax.swing.JTable jTblOrdine;
    private javax.swing.JTextField jTxtCliente;
    private javax.swing.JTextField jTxtTavolo;
    private javax.swing.JButton jbtnDec;
    // End of variables declaration//GEN-END:variables
}
