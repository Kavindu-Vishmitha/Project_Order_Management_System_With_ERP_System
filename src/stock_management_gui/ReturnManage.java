package stock_management_gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JOptionPane;
import model.MySql;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.MySql;

public class ReturnManage extends javax.swing.JFrame {

    public ReturnManage() {
        initComponents();
        loadReturnFood();
    }

    private void loadReturnFood() {

        try {

            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `manage_returns`");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {

                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("return_id"));
                vector.add(resultSet.getString("foods_food_id"));
                vector.add(resultSet.getString("product_name"));
                vector.add(resultSet.getString("invoice_invoice_id"));
                vector.add(resultSet.getString("customer_name"));
                vector.add(resultSet.getString("price"));

                model.addRow(vector);

            }
        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        bodyPanel = new javax.swing.JPanel();
        AddAndUpdateReturnsPanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        returnIDTextField = new javax.swing.JTextField();
        generateButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        foodIDTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        invoiceIDTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        foodNameTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        customerNameTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        priceTextField = new javax.swing.JTextField();
        kButton1 = new com.k33ptoo.components.KButton();
        jButton1 = new javax.swing.JButton();
        searchTextField = new javax.swing.JTextField();
        kButton2 = new com.k33ptoo.components.KButton();
        ReturnsManageTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        BackToDashboardPanel = new javax.swing.JPanel();
        BackToDashboardButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        headerPanel.setBackground(new java.awt.Color(153, 153, 153));
        headerPanel.setPreferredSize(new java.awt.Dimension(800, 50));

        jLabel1.setFont(new java.awt.Font("Audiowide", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Manage Returns");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1122, Short.MAX_VALUE)
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        getContentPane().add(headerPanel, java.awt.BorderLayout.PAGE_START);

        bodyPanel.setLayout(new java.awt.BorderLayout());

        AddAndUpdateReturnsPanel.setPreferredSize(new java.awt.Dimension(800, 260));

        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel2.setText("Return ID");

        returnIDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnIDTextFieldActionPerformed(evt);
            }
        });

        generateButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/generate.png"))); // NOI18N
        generateButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButton2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel3.setText("Food ID");

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel4.setText("Invoice ID");

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel5.setText("Food Name");

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel6.setText("Customer Name");

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel7.setText("Price");

        kButton1.setText("Add Returns");
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

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/refresh.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        searchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTextFieldKeyReleased(evt);
            }
        });

        kButton2.setText("Search");
        kButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kButton2.setkEndColor(new java.awt.Color(0, 204, 204));
        kButton2.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        kButton2.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton2.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        kButton2.setkPressedColor(new java.awt.Color(0, 102, 153));
        kButton2.setkSelectedColor(new java.awt.Color(0, 102, 153));
        kButton2.setkStartColor(new java.awt.Color(0, 102, 153));
        kButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddAndUpdateReturnsPanelLayout = new javax.swing.GroupLayout(AddAndUpdateReturnsPanel);
        AddAndUpdateReturnsPanel.setLayout(AddAndUpdateReturnsPanelLayout);
        AddAndUpdateReturnsPanelLayout.setHorizontalGroup(
            AddAndUpdateReturnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(AddAndUpdateReturnsPanelLayout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addGroup(AddAndUpdateReturnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddAndUpdateReturnsPanelLayout.createSequentialGroup()
                        .addGroup(AddAndUpdateReturnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, AddAndUpdateReturnsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(customerNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, AddAndUpdateReturnsPanelLayout.createSequentialGroup()
                                .addGroup(AddAndUpdateReturnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(51, 51, 51)
                                .addGroup(AddAndUpdateReturnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(AddAndUpdateReturnsPanelLayout.createSequentialGroup()
                                        .addComponent(returnIDTextField)
                                        .addGap(12, 12, 12)
                                        .addComponent(generateButton2))
                                    .addComponent(invoiceIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(45, 45, 45)
                        .addGroup(AddAndUpdateReturnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3)))
                    .addGroup(AddAndUpdateReturnsPanelLayout.createSequentialGroup()
                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddAndUpdateReturnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddAndUpdateReturnsPanelLayout.createSequentialGroup()
                        .addGap(194, 194, 194)
                        .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                    .addComponent(priceTextField)
                    .addComponent(foodNameTextField)
                    .addComponent(foodIDTextField))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        AddAndUpdateReturnsPanelLayout.setVerticalGroup(
            AddAndUpdateReturnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddAndUpdateReturnsPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(AddAndUpdateReturnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddAndUpdateReturnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(returnIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(generateButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(AddAndUpdateReturnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(foodIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(AddAndUpdateReturnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(invoiceIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(foodNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(AddAndUpdateReturnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(priceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AddAndUpdateReturnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddAndUpdateReturnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        bodyPanel.add(AddAndUpdateReturnsPanel, java.awt.BorderLayout.PAGE_START);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Return ID", "Food ID", "Food Name", "Invoice ID", "Customer Name", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout ReturnsManageTableLayout = new javax.swing.GroupLayout(ReturnsManageTable);
        ReturnsManageTable.setLayout(ReturnsManageTableLayout);
        ReturnsManageTableLayout.setHorizontalGroup(
            ReturnsManageTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReturnsManageTableLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1022, Short.MAX_VALUE)
                .addGap(50, 50, 50))
        );
        ReturnsManageTableLayout.setVerticalGroup(
            ReturnsManageTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReturnsManageTableLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        bodyPanel.add(ReturnsManageTable, java.awt.BorderLayout.CENTER);

        BackToDashboardPanel.setBackground(new java.awt.Color(153, 153, 153));
        BackToDashboardPanel.setPreferredSize(new java.awt.Dimension(800, 50));

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
                .addContainerGap()
                .addComponent(BackToDashboardButton)
                .addContainerGap(1078, Short.MAX_VALUE))
        );
        BackToDashboardPanelLayout.setVerticalGroup(
            BackToDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BackToDashboardPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BackToDashboardButton)
                .addContainerGap())
        );

        bodyPanel.add(BackToDashboardPanel, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(bodyPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void generateButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButton2ActionPerformed

        returnIDTextField.setCursor(new Cursor(Cursor.HAND_CURSOR));

        returnIDTextField.setForeground(Color.BLACK);

        long id = System.currentTimeMillis();
        returnIDTextField.setText("RTI" + String.valueOf(id));

        returnIDTextField.setEnabled(true);

        returnIDTextField.setFocusable(true);

    }//GEN-LAST:event_generateButton2ActionPerformed

    private void BackToDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToDashboardButtonActionPerformed

        dispose();
        
    }//GEN-LAST:event_BackToDashboardButtonActionPerformed

    private void returnIDTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnIDTextFieldActionPerformed


    }//GEN-LAST:event_returnIDTextFieldActionPerformed

    private void kButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton1ActionPerformed

        try {
            String returnid = returnIDTextField.getText().trim();
            String foodid = foodIDTextField.getText().trim();
            String invoiceid = invoiceIDTextField.getText().trim();
            String foodname = foodNameTextField.getText().trim();
            String customername = customerNameTextField.getText().trim();
            String price = priceTextField.getText().trim();

            if (returnid.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please click the button for a new Return ID", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (foodid.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Food ID", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (invoiceid.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Invoice ID", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (foodname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Food Name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (customername.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Customer Name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (price.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Price", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (!price.matches("\\d+(\\.\\d{1,2})?")) {
                JOptionPane.showMessageDialog(this, "Invalid Price. Only numbers are allowed (e.g. 100 or 100.50)", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {

                ResultSet rs1 = MySql.executeSearch("SELECT food_id FROM foods WHERE food_id = '" + foodid + "'");
                if (!rs1.next()) {
                    JOptionPane.showMessageDialog(this, "Invalid Food ID", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                ResultSet rs2 = MySql.executeSearch("SELECT invoice_id FROM invoice WHERE invoice_id = '" + invoiceid + "'");
                if (!rs2.next()) {
                    JOptionPane.showMessageDialog(this, "Invalid Invoice ID", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                ResultSet rs3 = MySql.executeSearch("SELECT * FROM manage_returns WHERE invoice_invoice_id = '" + invoiceid + "'");
                if (rs3.next()) {
                    JOptionPane.showMessageDialog(this, "Invoice ID already exists", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                MySql.executeUpdate("INSERT INTO manage_returns (return_id, foods_food_id, product_name, invoice_invoice_id, customer_name, price) VALUES ('"
                        + returnid + "', '" + foodid + "', '" + foodname + "', '" + invoiceid + "', '" + customername + "', '" + price + "')");

                returnIDTextField.grabFocus();
                JOptionPane.showMessageDialog(this, "Return added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refresh();
                loadReturnFood();

            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while adding the return", "Error", JOptionPane.ERROR_MESSAGE);

        }

    }//GEN-LAST:event_kButton1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        refresh();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int row = jTable1.getSelectedRow();

        if (evt.getClickCount() == 2) {

            String selectedReturnID = String.valueOf(jTable1.getValueAt(row, 0));

            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this return food?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {

                try {

                    MySql.executeUpdate("DELETE FROM `manage_returns` WHERE `return_id`='" + selectedReturnID + "'");

                    JOptionPane.showMessageDialog(this, "Food Deleted Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);

                    refresh();
                    loadReturnFood();

                } catch (Exception e) {

                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error occurred while deleting the Food", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void kButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton2ActionPerformed

    private void searchTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextFieldKeyReleased
        String searchText = searchTextField.getText().trim();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        try {
            ResultSet rs = MySql.executeSearch(
                    "SELECT return_id, foods_food_id, product_name, invoice_invoice_id, customer_name, price "
                    + "FROM manage_returns WHERE invoice_invoice_id LIKE '%" + searchText + "%'"
            );

            while (rs.next()) {
                Object[] row = {
                    rs.getString("return_id"),
                    rs.getString("foods_food_id"),
                    rs.getString("product_name"),
                    rs.getString("invoice_invoice_id"),
                    rs.getString("customer_name"),
                    rs.getDouble("price")
                };
                model.addRow(row);
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_searchTextFieldKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReturnManage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddAndUpdateReturnsPanel;
    private javax.swing.JButton BackToDashboardButton;
    private javax.swing.JPanel BackToDashboardPanel;
    private javax.swing.JPanel ReturnsManageTable;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JTextField customerNameTextField;
    private javax.swing.JTextField foodIDTextField;
    private javax.swing.JTextField foodNameTextField;
    private javax.swing.JButton generateButton2;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JTextField invoiceIDTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private com.k33ptoo.components.KButton kButton1;
    private com.k33ptoo.components.KButton kButton2;
    private javax.swing.JTextField priceTextField;
    private javax.swing.JTextField returnIDTextField;
    private javax.swing.JTextField searchTextField;
    // End of variables declaration//GEN-END:variables

    private void refresh() {

        returnIDTextField.setText("");
        foodIDTextField.setText("");
        invoiceIDTextField.setText("");
        foodNameTextField.setText("");
        customerNameTextField.setText("");
        priceTextField.setText("");
        returnIDTextField.grabFocus();
    }

}
