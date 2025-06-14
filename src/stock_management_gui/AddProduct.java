/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package stock_management_gui;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MySql;

/**
 *
 * @author Avishka
 */
public class AddProduct extends javax.swing.JFrame {

    private static HashMap<String, String> BatchMap = new HashMap<>();
    private static HashMap<String, String> BrandMap = new HashMap<>();
    private static HashMap<String, String> CategoryMap = new HashMap<>();
    private static HashMap<String, String> grnMap = new HashMap<>();

    /**
     * Creates new form AddProduct
     */
    public AddProduct() {

        initComponents();

        loadBatch();
        loadBrand();
        loadCategory();
        loadProductTable();
        loadProduct();
        loadQTY();
        qtyTextField.setEditable(false);

        //Generate Id Automatically
        int randomId = (int) (Math.random() * 9000) + 1000;
        productIdLabel.setText("PID" + String.valueOf(randomId));

    }

    private DefaultTableModel tablemodel;
    private DefaultComboBoxModel model;

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
        BackToDashboardPanel = new javax.swing.JPanel();
        BackToDashboardButton = new javax.swing.JButton();
        bodyPanel = new javax.swing.JPanel();
        productTablePanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        BrandSelectComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        categoryAddComboBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        qtyTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        descriptionTextField = new javax.swing.JTextField();
        updateButton = new com.k33ptoo.components.KButton();
        AddProductButton = new com.k33ptoo.components.KButton();
        refreshButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        BatchSelectCombobox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        ProductNameCombobox = new javax.swing.JComboBox<>();
        productIdLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ProductTable = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        headerPanel.setBackground(new java.awt.Color(153, 153, 153));
        headerPanel.setPreferredSize(new java.awt.Dimension(720, 60));

        jLabel1.setFont(new java.awt.Font("Audiowide", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add Product");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1061, Short.MAX_VALUE)
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getContentPane().add(headerPanel, java.awt.BorderLayout.PAGE_START);

        BackToDashboardPanel.setBackground(new java.awt.Color(153, 153, 153));
        BackToDashboardPanel.setPreferredSize(new java.awt.Dimension(50, 50));

        BackToDashboardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/back-arrow.png"))); // NOI18N
        BackToDashboardButton.setPreferredSize(new java.awt.Dimension(75, 75));
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
                .addComponent(BackToDashboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1018, Short.MAX_VALUE))
        );
        BackToDashboardPanelLayout.setVerticalGroup(
            BackToDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackToDashboardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BackToDashboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        getContentPane().add(BackToDashboardPanel, java.awt.BorderLayout.PAGE_END);

        bodyPanel.setLayout(new java.awt.BorderLayout());

        productTablePanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(1057, 220));

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel3.setText("Brand");

        BrandSelectComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        BrandSelectComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BrandSelectComboBoxActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel4.setText("Category");

        categoryAddComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Qty");

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel7.setText("Discription");

        updateButton.setText("Update");
        updateButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        updateButton.setkEndColor(new java.awt.Color(0, 204, 204));
        updateButton.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        updateButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        updateButton.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        updateButton.setkPressedColor(new java.awt.Color(0, 102, 153));
        updateButton.setkSelectedColor(new java.awt.Color(0, 102, 153));
        updateButton.setkStartColor(new java.awt.Color(0, 102, 153));
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        AddProductButton.setText("Add");
        AddProductButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AddProductButton.setkEndColor(new java.awt.Color(0, 204, 204));
        AddProductButton.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        AddProductButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        AddProductButton.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        AddProductButton.setkPressedColor(new java.awt.Color(0, 102, 153));
        AddProductButton.setkSelectedColor(new java.awt.Color(0, 102, 153));
        AddProductButton.setkStartColor(new java.awt.Color(0, 102, 153));
        AddProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddProductButtonActionPerformed(evt);
            }
        });

        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/refresh.png"))); // NOI18N
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel9.setText("Select Batch");

        BatchSelectCombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        BatchSelectCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BatchSelectComboboxActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel2.setText("Product Name");

        productIdLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        productIdLabel.setText("Product ID");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AddProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(refreshButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(12, 12, 12)
                        .addComponent(categoryAddComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(qtyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(descriptionTextField))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(productIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BatchSelectCombobox, 0, 179, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(12, 12, 12)
                        .addComponent(ProductNameCombobox, 0, 179, Short.MAX_VALUE)
                        .addGap(19, 19, 19)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BrandSelectComboBox, 0, 179, Short.MAX_VALUE)))
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BrandSelectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ProductNameCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(productIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BatchSelectCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(categoryAddComboBox, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(qtyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(descriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(AddProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        productTablePanel.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        ProductTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Batch ID", "Product Name", "Brand Name", "Category Name", "Discription", "QTY"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ProductTable.getTableHeader().setReorderingAllowed(false);
        ProductTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProductTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(ProductTable);

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel8.setText("Search Product Name");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 991, Short.MAX_VALUE)
                        .addGap(35, 35, 35))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        productTablePanel.add(jPanel2, java.awt.BorderLayout.CENTER);

        bodyPanel.add(productTablePanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(bodyPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //    Batch ComboBox
    private void loadBatch() {

        try {

            ResultSet resultSet = MySql.executeSearch(" SELECT * FROM `batch` ");

            Vector<String> vector = new Vector<>();
            vector.add("Select Batch");

            while (resultSet.next()) {

//                String batch = resultSet.getString("batch_name") + " " + "Selling Price:-" + resultSet.getString("selling_price");
                vector.add(resultSet.getString("batch_name"));

                BatchMap.put(resultSet.getString("batch_name"), resultSet.getString("batch_id"));

            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            BatchSelectCombobox.setModel(model);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    private void loadProduct() {

        String batchName = String.valueOf(BatchSelectCombobox.getSelectedItem());
        String batchId = String.valueOf(BatchMap.get(batchName));

        try {

            if (batchName.equals("Select Batch")) {

                return;

            }

            ResultSet productResult = MySql.executeSearch("SELECT `product_name`,`grn_id` FROM `grn` WHERE `batch_batch_id` = '" + batchId + "' ");

            Vector<String> productVector = new Vector<>();
            productVector.add("Select Product");

            while (productResult.next()) {

                productVector.add(productResult.getString("product_name"));

                String ProductName = productResult.getString("product_name");
                String grnId = productResult.getString("grn_id");

                grnMap.put(ProductName, grnId);

            }

            DefaultComboBoxModel productModel = new DefaultComboBoxModel(productVector);
            ProductNameCombobox.setModel(productModel);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void loadBrand() {

        try {

            ResultSet resultSet = MySql.executeSearch(" SELECT * FROM `brands` ");

            Vector<String> vector = new Vector<>();
            vector.add("Select Brand");

            while (resultSet.next()) {

                vector.add(resultSet.getString("brand_name"));
                BrandMap.put(resultSet.getString("brand_name"), resultSet.getString("brands_id"));

            }

            model = new DefaultComboBoxModel(vector);
            BrandSelectComboBox.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadCategory() {

        try {

            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `category`");

            Vector<String> vector = new Vector<>();
            vector.add("Select Category");

            while (resultSet.next()) {

                vector.add(resultSet.getString("category_name"));
                CategoryMap.put(resultSet.getString("category_name"), resultSet.getString("category_id"));

            }

            model = new DefaultComboBoxModel(vector);

            categoryAddComboBox.setModel(model);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void loadQTY() {

        String batchName = String.valueOf(BatchSelectCombobox.getSelectedItem());
        String batchId = BatchMap.get(batchName);

        try {

            if (batchName.equals("Select Batch")) {

                return;

            }

            ResultSet qtyResult = MySql.executeSearch("SELECT `qty` FROM `grn` WHERE `batch_batch_id` = '" + batchId + "' ");

            String qty = "";

            while (qtyResult.next()) {

                qty = qtyResult.getString("qty");

            }

            qtyTextField.setText(qty);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // table data load
    private void loadProductTable() {
        try {

            tablemodel = (DefaultTableModel) ProductTable.getModel();
            tablemodel.setRowCount(0);

            ResultSet resultSet = MySql.executeSearch(
                    "SELECT * FROM `product` "
                    + "INNER JOIN `batch` ON `product`.`batch_batch_id` = `batch`.`batch_id` "
                    + "INNER JOIN `brands` ON `product`.`brands_brands_id` = `brands`.`brands_id` "
                    + "INNER JOIN `category` ON `product`.`category_category_id` = `category`.`category_id` "
                    + "INNER JOIN `grn` ON `product`.`grn_grn_id` = `grn`.`grn_id`"
            );

            while (resultSet.next()) {

                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("product.product_id"));
                vector.add(resultSet.getString("batch.batch_name"));
                vector.add(resultSet.getString("product.product_name"));
                vector.add(resultSet.getString("brands.brand_name"));
                vector.add(resultSet.getString("category.category_name"));
                vector.add(resultSet.getString("product.description"));
                vector.add(resultSet.getString("product.qty"));

                tablemodel.addRow(vector);
            }

        } catch (Exception e) {

            e.printStackTrace(); // Debugging purpose, better to use logging

        }
    }

    private void BackToDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToDashboardButtonActionPerformed

        dispose();

    }//GEN-LAST:event_BackToDashboardButtonActionPerformed

    private void BrandSelectComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BrandSelectComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BrandSelectComboBoxActionPerformed

    private void AddProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddProductButtonActionPerformed

        // Add Data        
        try {

            String pid = productIdLabel.getText();
            String batch = String.valueOf(BatchSelectCombobox.getSelectedItem());
            String product = String.valueOf(ProductNameCombobox.getSelectedItem());
            String brand = String.valueOf(BrandSelectComboBox.getSelectedItem());
            String category = String.valueOf(categoryAddComboBox.getSelectedItem());
            String qty = qtyTextField.getText();
            String desc = descriptionTextField.getText();
            String grnId = grnMap.get(product);

            if (pid.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Empty Product ID", "Please contact admin", JOptionPane.WARNING_MESSAGE);

            } else if (batch.equals("Select Batch")) {

                JOptionPane.showMessageDialog(this, "Empty Data", "Please select batch", JOptionPane.WARNING_MESSAGE);

            } else if (product.equals("Select Product")) {

                JOptionPane.showMessageDialog(this, "Empty Data", "Please select product", JOptionPane.WARNING_MESSAGE);

            } else if (brand.equals("Select Brand")) {

                JOptionPane.showMessageDialog(this, "Empty Data", "Please select brand", JOptionPane.WARNING_MESSAGE);

            } else if (category.equals("Select Category")) {

                JOptionPane.showMessageDialog(this, "Empty Data", "Please enter category", JOptionPane.WARNING_MESSAGE);

            } else if (qty.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Empty Data", "Please enter qty", JOptionPane.WARNING_MESSAGE);

            } else if (desc.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Empty Data", "Please enter description", JOptionPane.WARNING_MESSAGE);

            } else {

                try {

                    ResultSet productResultSet = MySql.executeSearch("SELECT * FROM `product` WHERE `product_id` = '" + pid + "' || `grn_grn_id` = '" + grnId + "' ");

                    if (productResultSet.next()) {

                        JOptionPane.showMessageDialog(this, "Duplicate Data", "Already added product under this ID", JOptionPane.WARNING_MESSAGE);

                    } else {

                        MySql.executeUpdate("INSERT INTO `product` (`product_id`,`batch_batch_id`,`product_name`,`brands_brands_id`,`category_category_id`,`description`,`grn_grn_id`,`qty`) "
                                + "VALUES('" + pid + "','" + BatchMap.get(batch) + "','" + product + "','" + BrandMap.get(brand) + "','" + CategoryMap.get(category) + "','" + desc + "','" + grnId + "','" + qty + "') ");

                        JOptionPane.showMessageDialog(this, "Data Inserted", "Successfully added" + " " + product, JOptionPane.INFORMATION_MESSAGE);

                        loadProductTable();

                        reset();
                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }

        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding product!", "Error", JOptionPane.ERROR_MESSAGE);

        }

    }//GEN-LAST:event_AddProductButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed

        try {

            String pid = productIdLabel.getText();
            String product = String.valueOf(ProductNameCombobox.getSelectedItem());
            String category = String.valueOf(categoryAddComboBox.getSelectedItem());
            String qty = qtyTextField.getText();
            String desc = descriptionTextField.getText();
            String grnId = grnMap.get(product);

            if (pid.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Empty Product ID", "Please contact admin", JOptionPane.WARNING_MESSAGE);

            } else if (category.equals("Select Category")) {

                JOptionPane.showMessageDialog(this, "Empty Data", "Please enter category", JOptionPane.WARNING_MESSAGE);

            } else if (product.equals("Select Product")) {

                JOptionPane.showMessageDialog(this, "Empty Data", "Please select product", JOptionPane.WARNING_MESSAGE);

            } else if (qty.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Empty Data", "Please enter qty", JOptionPane.WARNING_MESSAGE);

            } else if (desc.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Empty Data", "Please enter description", JOptionPane.WARNING_MESSAGE);

            } else {

                try {

                    MySql.executeUpdate("UPDATE `product` SET `category_category_id` = '" + CategoryMap.get(category) + "', `qty` = '" + qty + "', `description` = '" + desc + "' WHERE `product_id` = '" + pid + "' ");

                    JOptionPane.showMessageDialog(this, "Data Updated", "Successfully update" + " " + product, JOptionPane.INFORMATION_MESSAGE);

                    loadProductTable();

                    reset();

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }

        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding product!", "Error", JOptionPane.ERROR_MESSAGE);

        }

    }//GEN-LAST:event_updateButtonActionPerformed

    private void BatchSelectComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BatchSelectComboboxActionPerformed

        loadProduct();
        loadQTY();

    }//GEN-LAST:event_BatchSelectComboboxActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed

        reset();

    }//GEN-LAST:event_refreshButtonActionPerformed

    private void ProductTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductTableMouseClicked

        int row = ProductTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(this, "Empty selection message", "Please select a row", JOptionPane.WARNING_MESSAGE);

        } else if (evt.getClickCount() == 1 && evt.getButton() == MouseEvent.BUTTON1) {

            qtyTextField.setEditable(true);
            BatchSelectCombobox.setEnabled(false);
            ProductNameCombobox.setEnabled(false);
            BrandSelectComboBox.setEnabled(false);

            productIdLabel.setText(String.valueOf(ProductTable.getValueAt(row, 0)));
            BatchSelectCombobox.setSelectedItem(String.valueOf(ProductTable.getValueAt(row, 1)));
            ProductNameCombobox.setSelectedItem(String.valueOf(ProductTable.getValueAt(row, 2)));
            BrandSelectComboBox.setSelectedItem(String.valueOf(ProductTable.getValueAt(row, 3)));
            categoryAddComboBox.setSelectedItem(String.valueOf(ProductTable.getValueAt(row, 4)));
            qtyTextField.setText(String.valueOf(ProductTable.getValueAt(row, 6)));
            descriptionTextField.setText(String.valueOf(ProductTable.getValueAt(row, 5)));

        } else if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {

            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this item?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {

                String productId = String.valueOf(ProductTable.getValueAt(row, 0));

                try {

                    MySql.executeUpdate("DELETE FROM `product` WHERE product_id = '" + productId + "'");

                    JOptionPane.showMessageDialog(this, "Product deleted successfully!");

                    loadProductTable();

                    reset();

                } catch (Exception e) {

                    e.printStackTrace();

                    JOptionPane.showMessageDialog(this, "Error deleting product.");

                }
            }

        }

    }//GEN-LAST:event_ProductTableMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddProduct().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.k33ptoo.components.KButton AddProductButton;
    private javax.swing.JButton BackToDashboardButton;
    private javax.swing.JPanel BackToDashboardPanel;
    private javax.swing.JComboBox<String> BatchSelectCombobox;
    private javax.swing.JComboBox<String> BrandSelectComboBox;
    private javax.swing.JComboBox<String> ProductNameCombobox;
    private javax.swing.JTable ProductTable;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JComboBox<String> categoryAddComboBox;
    private javax.swing.JTextField descriptionTextField;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JLabel productIdLabel;
    private javax.swing.JPanel productTablePanel;
    private javax.swing.JTextField qtyTextField;
    private javax.swing.JButton refreshButton;
    private com.k33ptoo.components.KButton updateButton;
    // End of variables declaration//GEN-END:variables

    private void reset() {

        productIdLabel.setText("Product ID");
        BatchSelectCombobox.setSelectedIndex(0);
        BatchSelectCombobox.setEnabled(true);
        ProductNameCombobox.setEnabled(true);
        BrandSelectComboBox.setSelectedIndex(0);
        BrandSelectComboBox.setEnabled(true);
        categoryAddComboBox.setSelectedIndex(0);
        qtyTextField.setText("");
        qtyTextField.setEditable(false);
        descriptionTextField.setText("");
        ProductTable.clearSelection();
        loadProductTable();

    }
}
