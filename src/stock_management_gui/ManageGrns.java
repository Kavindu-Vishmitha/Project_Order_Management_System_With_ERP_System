/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package stock_management_gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.MySql;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import stock_management_gui.AddPurchasingOrder;

/**
 *
 * @author GOLDEN FIELD
 */
public class ManageGrns extends javax.swing.JFrame {

    /**
     * Creates new form ManageGrns
     */
    public ManageGrns() {

        initComponents();

        addPlaceholder();     // call textfields placeholder method

        loadGRNData();

        configureKeyBindings(); // For Default Frame Key Controls

    }

    // Placeholders add method
    private void addPlaceholder() {

        // postalcodeTextField placeholder & color
        GRNSearchTextField.setText("Enter Product Name");
        GRNSearchTextField.setForeground(Color.GRAY);

    }

    // global variable of Table
    private DefaultTableModel tablemodel;

    // Employee Details Table load method
    private void loadGRNData() {

        try {

            // search query
            ResultSet resultSet = MySql.executeSearch("SELECT `grn_id`, `product_name`, `company_name`, `supplier`.`first_name`, `supplier`.`last_name`, "
                    + "`added_date`, `payment_status_name`, `sub_total`, `payable_amount`, `payment_due` "
                    + "FROM `grn` "
                    + "INNER JOIN `payment_status` ON `grn`.`payment_status_payment_status_id` = `payment_status`.`payment_status_id` "
                    + "INNER JOIN `supplier` ON `supplier`.`supplier_id` = `grn`.`supplier_supplier_id` "
                    + "INNER JOIN `company` ON `supplier`.`company_company_id` = `company`.`company_id`");

            // get ManageEmployeeTable as DefaultTableModel
            tablemodel = (DefaultTableModel) ManageGRNtable.getModel();
            tablemodel.setRowCount(0); // Clear table before loading new data

            // to check available result
            boolean hasData = false;

            // query row checker and updater loop
            while (resultSet.next()) {

                // if there's a data boolean value will be true
                hasData = true;

                String supplierFullName = resultSet.getString("supplier.first_name") + " " + resultSet.getString("supplier.last_name");

                // add row to table
                tablemodel.addRow(new Object[]{
                    resultSet.getString("grn_id"),
                    resultSet.getString("product_name"),
                    resultSet.getString("company_name"),
                    resultSet.getString("supplier.first_name") + " " + resultSet.getString("supplier.last_name"),
                    resultSet.getString("added_date"),
                    resultSet.getString("payment_status_name"),
                    resultSet.getString("sub_total"),
                    resultSet.getString("payable_amount"),
                    resultSet.getString("payment_due"),});

            }

            // checks if data loaded from query if not shows a message
            if (!hasData) {

                JOptionPane.showMessageDialog(this, "No GRN data found", "Info", JOptionPane.INFORMATION_MESSAGE);

            }

            // sets model to table
            ManageGRNtable.setModel(tablemodel);

        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    // method to update Employee status (Update button code)
    private void deleteGRNStatus() {

        try {

            // Get Employee ID from the table
            int row = ManageGRNtable.getSelectedRow();

            // check if a row selected
            if (row != -1) {

                // Get data from selected row
                String grnId = ManageGRNtable.getValueAt(row, 0).toString();

                // Confirm deletion
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this GRN data?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                // if not yes option code
                if (confirm != JOptionPane.YES_OPTION) {

                    return; // User canceled deletion

                }

                // Delete the GRN row data from the database
                String query = "DELETE FROM `grn` WHERE `grn_id` = '" + grnId + "'";

                // take the affected query row count
                int rowsAffected = MySql.executeUpdate(query);

                // checks if there is a affected row 
                if (rowsAffected > 0) {

                    JOptionPane.showMessageDialog(this, "GRN deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Refresh the table
                    loadGRNData();

                } else {

                    JOptionPane.showMessageDialog(this, "GRN ID not found!", "Error", JOptionPane.ERROR_MESSAGE);

                }

            }

        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating GRN status: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

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

        headerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        bodyPanel = new javax.swing.JPanel();
        GRNsearchPanal = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        GRNSearchTextField = new javax.swing.JTextField();
        refreshButton = new javax.swing.JButton();
        BackToDashboardPanel = new javax.swing.JPanel();
        BackToDashboardButton = new javax.swing.JButton();
        GrnsTableBody = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ManageGRNtable = new javax.swing.JTable();
        kButton1 = new com.k33ptoo.components.KButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        headerPanel.setBackground(new java.awt.Color(153, 153, 153));
        headerPanel.setPreferredSize(new java.awt.Dimension(689, 60));

        jLabel1.setFont(new java.awt.Font("Audiowide", 0, 24)); // NOI18N
        jLabel1.setText("Manage GRNS");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addContainerGap(274, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addGap(242, 242, 242))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );

        getContentPane().add(headerPanel, java.awt.BorderLayout.PAGE_START);

        bodyPanel.setLayout(new java.awt.BorderLayout());

        GRNsearchPanal.setPreferredSize(new java.awt.Dimension(689, 60));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel2.setText("Search Product By Name");

        GRNSearchTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                GRNSearchTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                GRNSearchTextFieldFocusLost(evt);
            }
        });
        GRNSearchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GRNSearchTextFieldActionPerformed(evt);
            }
        });
        GRNSearchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GRNSearchTextFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                GRNSearchTextFieldKeyReleased(evt);
            }
        });

        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/refresh.png"))); // NOI18N
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout GRNsearchPanalLayout = new javax.swing.GroupLayout(GRNsearchPanal);
        GRNsearchPanal.setLayout(GRNsearchPanalLayout);
        GRNsearchPanalLayout.setHorizontalGroup(
            GRNsearchPanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GRNsearchPanalLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(5, 5, 5)
                .addComponent(GRNSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        GRNsearchPanalLayout.setVerticalGroup(
            GRNsearchPanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GRNsearchPanalLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(GRNsearchPanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(GRNsearchPanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(GRNSearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        bodyPanel.add(GRNsearchPanal, java.awt.BorderLayout.PAGE_START);

        BackToDashboardPanel.setBackground(new java.awt.Color(153, 153, 153));
        BackToDashboardPanel.setPreferredSize(new java.awt.Dimension(689, 50));

        BackToDashboardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/back-arrow.png"))); // NOI18N
        BackToDashboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackToDashboardButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BackToDashboardPanelLayout = new javax.swing.GroupLayout(BackToDashboardPanel);
        BackToDashboardPanel.setLayout(BackToDashboardPanelLayout);
        BackToDashboardPanelLayout.setHorizontalGroup(
            BackToDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackToDashboardPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(BackToDashboardButton)
                .addContainerGap(670, Short.MAX_VALUE))
        );
        BackToDashboardPanelLayout.setVerticalGroup(
            BackToDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BackToDashboardPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BackToDashboardButton)
                .addGap(27, 27, 27))
        );

        bodyPanel.add(BackToDashboardPanel, java.awt.BorderLayout.PAGE_END);

        ManageGRNtable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ManageGRNtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "GRN ID", "Product Name", "Company Name", "Supplier Name", "Adding Date", "Paymnt Status", "Sub Total", "Paid", "Payment Due"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ManageGRNtable.getTableHeader().setReorderingAllowed(false);
        ManageGRNtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ManageGRNtableMouseClicked(evt);
            }
        });
        ManageGRNtable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ManageGRNtableKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(ManageGRNtable);

        kButton1.setText("Print GRNS");
        kButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kButton1.setkEndColor(new java.awt.Color(0, 204, 204));
        kButton1.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        kButton1.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton1.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        kButton1.setkPressedColor(new java.awt.Color(0, 102, 153));
        kButton1.setkSelectedColor(new java.awt.Color(0, 102, 153));
        kButton1.setkStartColor(new java.awt.Color(0, 102, 153));
        kButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout GrnsTableBodyLayout = new javax.swing.GroupLayout(GrnsTableBody);
        GrnsTableBody.setLayout(GrnsTableBodyLayout);
        GrnsTableBodyLayout.setHorizontalGroup(
            GrnsTableBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GrnsTableBodyLayout.createSequentialGroup()
                .addGroup(GrnsTableBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GrnsTableBodyLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GrnsTableBodyLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        GrnsTableBodyLayout.setVerticalGroup(
            GrnsTableBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GrnsTableBodyLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                .addGap(20, 20, 20)
                .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        bodyPanel.add(GrnsTableBody, java.awt.BorderLayout.CENTER);

        getContentPane().add(bodyPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void GRNSearchTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_GRNSearchTextFieldFocusGained

        // Checks and set clear the current cityTextField to enter data to it
        if (GRNSearchTextField.getText().equals("Enter Product Name")) {

            GRNSearchTextField.setText("");
            GRNSearchTextField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_GRNSearchTextFieldFocusGained

    private void GRNSearchTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_GRNSearchTextFieldFocusLost

        // Sets back the placeholder of EmployeeSearchField
        if (GRNSearchTextField.getText().isEmpty()) {

            GRNSearchTextField.setText("Enter Product Name");
            GRNSearchTextField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_GRNSearchTextFieldFocusLost

    private void BackToDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToDashboardButtonActionPerformed

        this.dispose();

    }//GEN-LAST:event_BackToDashboardButtonActionPerformed

    private void ManageGRNtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ManageGRNtableMouseClicked

        if (evt.getClickCount() == 1) {

            int row = ManageGRNtable.getSelectedRow();

            if (row != -1) {

                // Get data from selected row
                String grnId = ManageGRNtable.getValueAt(row, 0).toString();

                // sets text color to black
                GRNSearchTextField.setForeground(Color.BLACK);

                GRNSearchTextField.setText(grnId);

            }

        }

        if (evt.getClickCount() == 2) {

            // Confirm Update GRN data
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to Update this GRN Data?", "Confirm to Proceed Update", JOptionPane.YES_NO_OPTION);

            // if not yes option code
            if (confirm != JOptionPane.YES_OPTION) {

                return; // GRN canceled updation

            }

            int row = ManageGRNtable.getSelectedRow();

            if (row != -1) {

                // Get data from selected row
                String grnId = ManageGRNtable.getValueAt(row, 0).toString();

                // call openAddPurchasingOrderGUI
                openAddPurchasingOrderGUI(grnId);

            }

        }

    }//GEN-LAST:event_ManageGRNtableMouseClicked

    private void ManageGRNtableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ManageGRNtableKeyPressed
        // code to add key controls for ManageGRNtable

        int keyCode = evt.getKeyCode();

        int row = ManageGRNtable.getSelectedRow();

        if (row != -1) {

            if (keyCode == KeyEvent.VK_DELETE) {

                // Call delete method
                deleteGRNStatus();

            } else if (keyCode == KeyEvent.VK_ENTER) {

                // Open update GUI
                String grnId = ManageGRNtable.getValueAt(row, 0).toString();
                openAddPurchasingOrderGUI(grnId);

            }

        }

    }//GEN-LAST:event_ManageGRNtableKeyPressed

    private void GRNSearchTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GRNSearchTextFieldKeyReleased
        // code for GRNSearchTextField search 

        String searchText = GRNSearchTextField.getText().trim();

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) ManageGRNtable.getModel());

        ManageGRNtable.setRowSorter(sorter);

        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 1)); // 1 කියන්නේ Product Name Column Index එක


    }//GEN-LAST:event_GRNSearchTextFieldKeyReleased

    private void GRNSearchTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GRNSearchTextFieldKeyPressed
        // code to add key controls for updateButton

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_DOWN, KeyEvent.VK_S ->
                ManageGRNtable.requestFocusInWindow();

            case KeyEvent.VK_DELETE ->
                GRNSearchTextField.setText("");

        }

    }//GEN-LAST:event_GRNSearchTextFieldKeyPressed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void GRNSearchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GRNSearchTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GRNSearchTextFieldActionPerformed

    private void kButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton1ActionPerformed
        try {
            // Path to the compiled Jasper report
            String reportPath = "src/report/Grn.jasper";
            
         
         // Create parameters map (if needed)
            HashMap<String, Object> parameters = new HashMap<>();
         


            // Create data source from JTable model
            JRTableModelDataSource dataSource = new JRTableModelDataSource(ManageGRNtable.getModel());

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, dataSource);

            // Show the report in viewer
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating report: " + e.getMessage(), "Report Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(ManageGrns.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_kButton1ActionPerformed

    private void openAddPurchasingOrderGUI(String grnId) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {

            new AddPurchasingOrder(this, grnId).setVisible(true);
            this.setVisible(false); // Hide Manage GRN (this) Frame

        });

    }

    // Method to refresh the ManageGRN GUI From AddPurchasingOrder GUI
    public void refreshData() {

        reset(); // reset gui

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ManageGrns().setVisible(true);
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackToDashboardButton;
    private javax.swing.JPanel BackToDashboardPanel;
    private javax.swing.JTextField GRNSearchTextField;
    private javax.swing.JPanel GRNsearchPanal;
    private javax.swing.JPanel GrnsTableBody;
    private javax.swing.JTable ManageGRNtable;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.k33ptoo.components.KButton kButton1;
    private javax.swing.JButton refreshButton;
    // End of variables declaration//GEN-END:variables

    private void reset() {

        // Reset all components to default values
        GRNSearchTextField.setText("");
        ManageGRNtable.clearSelection();

        // Re-add the placeholders to refreshed TextFields
        addPlaceholder();

        // Re-Load EmployeeData
        loadGRNData();

        // grabs EmployeeSearchField focus
        GRNSearchTextField.grabFocus();

    }

    private void configureKeyBindings() {

        // Bind ESC key to dispose the frame
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "disposeFrame");

        getRootPane().getActionMap().put("disposeFrame", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                BackToDashboardButton.grabFocus();
                BackToDashboardButton.doClick(); // Simulate Exit button press

            }

        });

        // Bind F5 key to refresh
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F5"), "refreshFrame");

        getRootPane().getActionMap().put("refreshFrame", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                reset(); // Reset GUI

            }

        });

    }

}
