/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package stock_management_gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.MySql;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import order_management_gui.kitchen_gui.KitchenDashboard;

/**
 *
 * @author sanja
 */
public class Invoice extends javax.swing.JFrame {

    private String name;

    /**
     * Creates new form Invoice
     */
    public Invoice(String name) {

        initComponents();

        this.setExtendedState(StockManagementDashboard.MAXIMIZED_BOTH);

        loadOrdersTable();

        this.name = name;

        InvoiceUserDeatils();

        paidTextField.setEditable(false);
    }

    private void InvoiceUserDeatils() {

        if (name != null) {

            NameLabel.setText(name);

        }

    }

    private int getOrderItemOid(String orderId, int rowIndex) throws Exception {

        String foodName = OrdersViewTable.getValueAt(rowIndex, 1).toString();

        String query = String.format(
                "SELECT oi.oid FROM order_items oi "
                + "JOIN foods f ON oi.foods_food_id = f.food_id "
                + "WHERE oi.order_details_order_id = '%s' AND f.food_name = '%s' LIMIT 1",
                orderId, foodName
        );

        ResultSet rs = MySql.executeSearch(query);

        if (rs.next()) {

            return rs.getInt("oid");

        } else {

            throw new Exception("Order item not found for: " + foodName);

        }

    }

    private String getEmployeeIdByName(String empName) throws Exception {

        String query = "SELECT employee_employee_id FROM employee_user WHERE user_name = '" + empName + "'";
        ResultSet rs = MySql.executeSearch(query);

        if (rs.next()) {

            return rs.getString("employee_employee_id");

        } else {

            throw new Exception("Employee ID not found for name: " + empName);

        }

    }

    private String generateInvoiceId() throws Exception {

        ResultSet rs = MySql.executeSearch("SELECT invoice_id FROM invoice ORDER BY invoice_id DESC LIMIT 1");

        if (rs.next()) {

            String lastId = rs.getString("invoice_id").replace("INV", "");

            int newId = Integer.parseInt(lastId) + 1;

            return "INV" + String.format("%05d", newId);

        } else {

            return "INV00001";

        }

    }

    private void loadOrdersTable() {

        try {

            DefaultTableModel model = (DefaultTableModel) OrdersViewTable.getModel();

            model.setRowCount(0);

            String query = "SELECT * FROM order_details";

            ResultSet rs = MySql.executeSearch(query);

            while (rs.next()) {

                String Order_ID = rs.getString("order_id");

                model.addRow(new Object[]{Order_ID});

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void loadOrderItems() {

        String orderId = orderIdField.getText().trim();

        if (orderId.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please enter an Order ID.");

            return;

        }

        DefaultTableModel model = (DefaultTableModel) OrdersViewTable.getModel();

        model.setRowCount(0);

        double total = 0;

        try {

            ResultSet rs = MySql.executeSearch("SELECT * FROM order_details "
                    + "INNER JOIN order_items ON order_details.order_id = order_items.order_details_order_id "
                    + "INNER JOIN portion_types ON portion_types.portion_types_id = order_items.portion_types_portion_types_id "
                    + "INNER JOIN foods ON order_items.foods_food_id = foods.food_id WHERE order_details.order_id = '" + orderId + "'");

            while (rs.next()) {

                double price = rs.getDouble("price");

                model.addRow(new Object[]{rs.getString("order_id"), rs.getString("foods.food_name"), rs.getString("portion_types.portion_types_name"), rs.getInt("qty"), rs.getDouble("price")});

                total += price;
            }

            TotalTextField.setText(String.valueOf(total));

        } catch (Exception ex) {

            ex.printStackTrace();

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

        HeaderPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        NameLabel = new javax.swing.JLabel();
        BodyPanel = new javax.swing.JPanel();
        OrderViewPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        OrdersViewTable = new javax.swing.JTable();
        TotalLable = new javax.swing.JLabel();
        TotalTextField = new javax.swing.JTextField();
        BackToDashboardButton = new javax.swing.JButton();
        kButton3 = new com.k33ptoo.components.KButton();
        EmpID = new javax.swing.JLabel();
        kButton1 = new com.k33ptoo.components.KButton();
        orderIdField = new javax.swing.JTextField();
        refreshButton = new javax.swing.JButton();
        ReturnManage = new com.k33ptoo.components.KButton();
        paidTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        balanceValueLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        HeaderPanel.setBackground(new java.awt.Color(153, 153, 153));
        HeaderPanel.setPreferredSize(new java.awt.Dimension(540, 45));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Neol Resturant POS System");

        NameLabel.setFont(new java.awt.Font("Bodoni MT", 3, 24)); // NOI18N
        NameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NameLabel.setText("Employe!");

        javax.swing.GroupLayout HeaderPanelLayout = new javax.swing.GroupLayout(HeaderPanel);
        HeaderPanel.setLayout(HeaderPanelLayout);
        HeaderPanelLayout.setHorizontalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(NameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(217, Short.MAX_VALUE))
        );
        HeaderPanelLayout.setVerticalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(NameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );

        getContentPane().add(HeaderPanel, java.awt.BorderLayout.PAGE_START);

        BodyPanel.setLayout(new java.awt.BorderLayout());

        OrdersViewTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        OrdersViewTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order ID", "Items Name", "Portion", "QTY", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        OrdersViewTable.getTableHeader().setReorderingAllowed(false);
        OrdersViewTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OrdersViewTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(OrdersViewTable);

        TotalLable.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        TotalLable.setText("Total :");

        TotalTextField.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        TotalTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TotalTextFieldActionPerformed(evt);
            }
        });

        BackToDashboardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/back-arrow.png"))); // NOI18N
        BackToDashboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackToDashboardButtonActionPerformed(evt);
            }
        });

        kButton3.setText("Pay");
        kButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kButton3.setkEndColor(new java.awt.Color(0, 204, 204));
        kButton3.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        kButton3.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton3.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        kButton3.setkPressedColor(new java.awt.Color(0, 102, 153));
        kButton3.setkSelectedColor(new java.awt.Color(0, 102, 153));
        kButton3.setkStartColor(new java.awt.Color(0, 102, 153));
        kButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton3ActionPerformed(evt);
            }
        });

        EmpID.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        EmpID.setText("Enter Order ID / Mobile Number : ");

        kButton1.setText("Check Kitchen");
        kButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kButton1.setkEndColor(new java.awt.Color(0, 204, 204));
        kButton1.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        kButton1.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton1.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        kButton1.setkPressedColor(new java.awt.Color(0, 102, 153));
        kButton1.setkSelectedColor(new java.awt.Color(0, 102, 153));
        kButton1.setkStartColor(new java.awt.Color(0, 102, 153));
        kButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton1MouseClicked(evt);
            }
        });
        kButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton1ActionPerformed(evt);
            }
        });

        orderIdField.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        orderIdField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderIdFieldActionPerformed(evt);
            }
        });
        orderIdField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                orderIdFieldKeyReleased(evt);
            }
        });

        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/refresh.png"))); // NOI18N
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        ReturnManage.setText("Manag Returns");
        ReturnManage.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ReturnManage.setkEndColor(new java.awt.Color(0, 204, 204));
        ReturnManage.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        ReturnManage.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        ReturnManage.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        ReturnManage.setkPressedColor(new java.awt.Color(0, 102, 153));
        ReturnManage.setkSelectedColor(new java.awt.Color(0, 102, 153));
        ReturnManage.setkStartColor(new java.awt.Color(0, 102, 153));
        ReturnManage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReturnManageMouseClicked(evt);
            }
        });
        ReturnManage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReturnManageActionPerformed(evt);
            }
        });

        paidTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paidTextFieldActionPerformed(evt);
            }
        });
        paidTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                paidTextFieldKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel5.setText("Paid Amount :");

        balanceValueLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        balanceValueLabel.setText("00.00");

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Balance :");

        javax.swing.GroupLayout OrderViewPanelLayout = new javax.swing.GroupLayout(OrderViewPanel);
        OrderViewPanel.setLayout(OrderViewPanelLayout);
        OrderViewPanelLayout.setHorizontalGroup(
            OrderViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OrderViewPanelLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(OrderViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 809, Short.MAX_VALUE)
                    .addGroup(OrderViewPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(OrderViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(OrderViewPanelLayout.createSequentialGroup()
                                .addComponent(EmpID)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(orderIdField, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(refreshButton)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(OrderViewPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(OrderViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TotalLable, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(OrderViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(OrderViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(balanceValueLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(paidTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))))
                            .addGroup(OrderViewPanelLayout.createSequentialGroup()
                                .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ReturnManage, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(OrderViewPanelLayout.createSequentialGroup()
                                .addComponent(BackToDashboardButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(kButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(34, 34, 34))
        );
        OrderViewPanelLayout.setVerticalGroup(
            OrderViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OrderViewPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(OrderViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(refreshButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OrderViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(orderIdField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(EmpID, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(OrderViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ReturnManage, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addGap(20, 20, 20)
                .addGroup(OrderViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OrderViewPanelLayout.createSequentialGroup()
                        .addGroup(OrderViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TotalLable, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(OrderViewPanelLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(paidTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(OrderViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(balanceValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(OrderViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BackToDashboardButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(20, 20, 20))
        );

        BodyPanel.add(OrderViewPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(BodyPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void orderIdFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderIdFieldActionPerformed

        loadOrderItems();

    }//GEN-LAST:event_orderIdFieldActionPerformed

    private void TotalTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TotalTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalTextFieldActionPerformed

    private void BackToDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToDashboardButtonActionPerformed

        dispose();

    }//GEN-LAST:event_BackToDashboardButtonActionPerformed

    private void calculateBalance() {

        try {

            double total = Double.parseDouble(TotalTextField.getText());
            double paid = Double.parseDouble(paidTextField.getText());

            if (paid < 0) {

                JOptionPane.showMessageDialog(this, "Paid amount cannot be negative!", "Error", JOptionPane.ERROR_MESSAGE);
                paidTextField.setText("0");
                paid = 0;

            }

            double balance = paid - total;

            balanceValueLabel.setText(String.format("%.2f", balance));

        } catch (NumberFormatException e) {

            balanceValueLabel.setText("0.00");

        }

    }

    private void kButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton3ActionPerformed

        try {

            String orderId = orderIdField.getText().trim();

            String empName = NameLabel.getText().trim();

            if (empName.isEmpty() || empName.equals("Employe!")) {

                JOptionPane.showMessageDialog(this, "Please login first to continue.");
                return;

            }

            String empId = getEmployeeIdByName(empName);

            String checkQuery = String.format("SELECT invoice_id FROM invoice WHERE order_details_order_id = '%s'", orderId);

            ResultSet existing = MySql.executeSearch(checkQuery);

            if (existing.next()) {

                JOptionPane.showMessageDialog(this, "Invoice already exists for Order ID: " + orderId);
                return;

            }

            String invoiceId = generateInvoiceId();

            double total = Double.parseDouble(TotalTextField.getText());

            double paid = Double.parseDouble(paidTextField.getText());

            double balance = paid - total;

            balanceValueLabel.setText(String.format("%.2f", balance));

            String insertInvoice = String.format(
                    "INSERT INTO invoice (invoice_id, order_details_order_id, total_amount, paid_price, balance, employee_employee_id) "
                    + "VALUES ('%s', '%s', %.2f, %.2f, %.2f, '%s')",
                    invoiceId, orderId, total, paid, balance, empId
            );

            MySql.executeUpdate(insertInvoice);

            String orderItemsQuery = String.format(
                    "SELECT oi.oid, oi.qty, oi.price FROM order_items oi WHERE oi.order_details_order_id = '%s'", orderId
            );

            ResultSet rs = MySql.executeSearch(orderItemsQuery);

            while (rs.next()) {

                int orderItemId = rs.getInt("oid");

                int qty = rs.getInt("qty");

                double price = rs.getDouble("price");

                String insertItem = String.format(
                        "INSERT INTO invoice_items (invoice_invoice_id, order_details_order_id, order_items_oid, qty, price) "
                        + "VALUES ('%s', '%s', %d, %d, %.2f)",
                        invoiceId, orderId, orderItemId, qty, price
                );

                MySql.executeUpdate(insertItem);

            }

            HashMap<String, Object> params = new HashMap<>();
            params.put("invoice_id", invoiceId);
            params.put("total", total);
            params.put("paid", paid);
            params.put("balance", balance);

            JRTableModelDataSource dataSource = new JRTableModelDataSource(OrdersViewTable.getModel());

            String reportPath = "src/report/Neoul_Resturent_Full.jasper";
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, params, dataSource);
            JasperViewer.viewReport(jasperPrint, false);

            reset();

            JOptionPane.showMessageDialog(this, "Payment successful!\nInvoice ID: " + invoiceId + "\nBalance: " + balance);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }//GEN-LAST:event_kButton3ActionPerformed

    private void kButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton1MouseClicked


    }//GEN-LAST:event_kButton1MouseClicked

    private void kButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton1ActionPerformed

        KitchenDashboard kitchenDashboard = new KitchenDashboard();

        kitchenDashboard.setVisible(true);

    }//GEN-LAST:event_kButton1ActionPerformed

    DefaultTableModel model;

    public void search(String orderid) {

        model = (DefaultTableModel) OrdersViewTable.getModel();

        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);

        OrdersViewTable.setRowSorter(tr);

        tr.setRowFilter(RowFilter.regexFilter(orderid, 0));

    }

    private void orderIdFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_orderIdFieldKeyReleased

        String Od_ID = orderIdField.getText();

        search(Od_ID);


    }//GEN-LAST:event_orderIdFieldKeyReleased

    private void OrdersViewTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OrdersViewTableMouseClicked

        if (evt.getClickCount() == 2) {

            int row = OrdersViewTable.getSelectedRow();

            if (row != -1) {

                String Orderid = String.valueOf(OrdersViewTable.getValueAt(row, 0));

                orderIdField.setText(Orderid);

                loadOrderItems();

                paidTextField.setEditable(true);

            }

        }

    }//GEN-LAST:event_OrdersViewTableMouseClicked

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed

        reset();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void ReturnManageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReturnManageMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ReturnManageMouseClicked

    private void ReturnManageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReturnManageActionPerformed

        ReturnManage returnManage = new ReturnManage();
        returnManage.setVisible(true);

    }//GEN-LAST:event_ReturnManageActionPerformed

    private void paidTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paidTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paidTextFieldActionPerformed

    private void paidTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_paidTextFieldKeyReleased

        calculateBalance();

    }//GEN-LAST:event_paidTextFieldKeyReleased

    private void reset() {

        orderIdField.setText("");

        model = (DefaultTableModel) OrdersViewTable.getModel();

        model.setRowCount(0);

        loadOrdersTable();

        TotalTextField.setText("");

        paidTextField.setText("");

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Invoice(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackToDashboardButton;
    private javax.swing.JPanel BodyPanel;
    private javax.swing.JLabel EmpID;
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JPanel OrderViewPanel;
    private javax.swing.JTable OrdersViewTable;
    private com.k33ptoo.components.KButton ReturnManage;
    private javax.swing.JLabel TotalLable;
    private javax.swing.JTextField TotalTextField;
    private javax.swing.JLabel balanceValueLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private com.k33ptoo.components.KButton kButton1;
    private com.k33ptoo.components.KButton kButton3;
    private javax.swing.JTextField orderIdField;
    private javax.swing.JTextField paidTextField;
    private javax.swing.JButton refreshButton;
    // End of variables declaration//GEN-END:variables
}
