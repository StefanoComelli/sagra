package gui;

import beans.Login;
import database.DbConnection;
import java.awt.Cursor;
import model.Casse;
import model.Giorni;
import model.Operatori;
import org.jboss.logging.Logger;

/**
 *
 * @author Stefano
 */
public class LoginGui extends javax.swing.JFrame {

    private static final Logger LOGGER = Logger.getLogger(LoginGui.class);

    private final DbConnection dbConnection;
    private final Login login;

    /**
     *
     */
    private void GoLogin() {
        jCmbCassa.getSelectedItem();
        String giorno = (String) jCmbGiorno.getSelectedItem();
        String cassa = (String) jCmbCassa.getSelectedItem();
        String operatore = (String) jCmbOperatore.getSelectedItem();
        // String giorno, String cassa, String operatore
        CassaGui cassaGui = new CassaGui(giorno, cassa, operatore, dbConnection);
        cassaGui.setVisible(true);
        setVisible(false);
    }

    /**
     *
     */
    private void StatoFinestra() {

        if (jCmbCassa.getSelectedItem() == null || jCmbOperatore.getSelectedItem() == null
                || jCmbGiorno.getSelectedItem() == null) {
            jBtnOk.setEnabled(false);
        } else {
            jBtnOk.setEnabled(true);
        }
    }

    /**
     *
     */
    private void Setup() {

        for (Casse cassa : login.getCasse()) {
            jCmbCassa.addItem(cassa.toString());
        }
        jCmbCassa.setSelectedItem(null);

        for (Operatori operatore : login.getOperatori()) {
            jCmbOperatore.addItem(operatore.toString());
        }
        jCmbOperatore.setSelectedItem(null);

        for (Giorni giorno : login.getGiorni()) {
            jCmbGiorno.addItem(giorno.toString());
        }
        jCmbGiorno.setSelectedItem(0);

        setLocationRelativeTo(null);

        StatoFinestra();
    }

    /**
     * Creates new form TimeSheetGui
     */
    public LoginGui() {
        dbConnection = new DbConnection();
        login = new Login(dbConnection);
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Setup();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        jCmbCassa = new javax.swing.JComboBox();
        jLblCassa = new javax.swing.JLabel();
        jLblOperatore = new javax.swing.JLabel();
        jCmbOperatore = new javax.swing.JComboBox();
        jLblGiorno = new javax.swing.JLabel();
        jCmbGiorno = new javax.swing.JComboBox();
        jBtnEsce = new javax.swing.JButton();
        jBtnOk = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sagra Sant'Andrea: Login");
        setName("Form"); // NOI18N
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jPanel.setName("jPanel"); // NOI18N

        jCmbCassa.setName("jCmbCassa"); // NOI18N
        jCmbCassa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbCassaItemStateChanged(evt);
            }
        });

        jLblCassa.setText("Cassa:");
        jLblCassa.setName("jLblCassa"); // NOI18N

        jLblOperatore.setText("Operatore:");
        jLblOperatore.setName("jLblOperatore"); // NOI18N

        jCmbOperatore.setName("jCmbOperatore"); // NOI18N
        jCmbOperatore.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbOperatoreItemStateChanged(evt);
            }
        });

        jLblGiorno.setText("Giorno:");
        jLblGiorno.setName("jLblGiorno"); // NOI18N

        jCmbGiorno.setEnabled(false);
        jCmbGiorno.setName("jCmbGiorno"); // NOI18N
        jCmbGiorno.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbGiornoItemStateChanged(evt);
            }
        });

        jBtnEsce.setText("Esce");
        jBtnEsce.setName("jBtnEsce"); // NOI18N
        jBtnEsce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEsceActionPerformed(evt);
            }
        });

        jBtnOk.setText("Ok");
        jBtnOk.setName("jBtnOk"); // NOI18N
        jBtnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLblOperatore)
                            .addComponent(jLblCassa))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCmbCassa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCmbOperatore, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addComponent(jLblGiorno)
                        .addGap(23, 23, 23)
                        .addComponent(jCmbGiorno, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                        .addComponent(jBtnOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                        .addComponent(jBtnEsce)))
                .addContainerGap())
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblGiorno)
                    .addComponent(jCmbGiorno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCmbCassa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblCassa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblOperatore)
                    .addComponent(jCmbOperatore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnOk)
                    .addComponent(jBtnEsce))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Esce");
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     *
     * @param evt
     */
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    /**
     *
     * @param evt
     */
    private void jCmbCassaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbCassaItemStateChanged
        StatoFinestra();
    }//GEN-LAST:event_jCmbCassaItemStateChanged

    /**
     *
     * @param evt
     */
    private void jCmbOperatoreItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbOperatoreItemStateChanged
        StatoFinestra();
    }//GEN-LAST:event_jCmbOperatoreItemStateChanged

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
    private void jCmbGiornoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbGiornoItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jCmbGiornoItemStateChanged

    /**
     *
     * @param evt
     */
    private void jBtnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnOkActionPerformed
        GoLogin();
    }//GEN-LAST:event_jBtnOkActionPerformed

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
            java.util.logging.Logger.getLogger(LoginGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGui().setVisible(true);
            }
        });
    }

    /**
     *
     * @param setCursorBusy
     */
    private void setCursorBusy(boolean isBusy) {

        Cursor cursor;

        if (isBusy) {
            cursor = new Cursor(Cursor.WAIT_CURSOR);
        } else {
            cursor = new Cursor(Cursor.DEFAULT_CURSOR);
        }
        setCursor(cursor);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JButton jBtnEsce;
    private javax.swing.JButton jBtnOk;
    private javax.swing.JComboBox jCmbCassa;
    private javax.swing.JComboBox jCmbGiorno;
    private javax.swing.JComboBox jCmbOperatore;
    private javax.swing.JLabel jLblCassa;
    private javax.swing.JLabel jLblGiorno;
    private javax.swing.JLabel jLblOperatore;
    private javax.swing.JPanel jPanel;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables

}
