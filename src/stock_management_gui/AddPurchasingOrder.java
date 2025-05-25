/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package stock_management_gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import model.MySql;

// private global ManageGrns Frame
//    private ManageGrns manageGrns;
/**
 *
 * @author GOLDEN FIELD
 */
public class AddPurchasingOrder extends javax.swing.JFrame {

    private static HashMap<String, String> BatchMap = new HashMap<>();
    private static HashMap<String, String> SupplierMap = new HashMap<>();
    private static HashMap<String, String> StatusMap = new HashMap<>();
    private static HashMap<String, String> PaymentStatusMap = new HashMap<>();
    private static HashMap<String, String> ProductMap = new HashMap<>();

    // private global variable for GRN Id
    private String grnId;

    // private global ManageGrns Frame
    private ManageGrns manageGrns;

    /**
     * Creates new form AddPurchasingOrder
     *
     * @param manageGrns
     * @param grnId
     */
    public AddPurchasingOrder(ManageGrns manageGrns, String grnId) {

        initComponents();      // Initialize components

        addPlaceholder();      // call textfields placeholder method

        loadBatch();           // call BatchSelectComboBox load method
        loadSupplier();        // call SupplierSelectComboBox load method
        loadStatus();          // call GrnStatusSelectComboBox load method
        loadPaymentStatus();   // call PaymentStatusComboBox load method
        addGrnTableListener(); // call add column data from table method
        loadProduct();

        this.grnId = grnId; // Assign GRN id to the private variable

        this.manageGrns = manageGrns; // Assign ManageGrns Object to the private variable

        if (manageGrns != null && grnId != null && !grnId.isEmpty()) {
            
            manageGRNIdSettingsLoad();
            
        }
        
        //manageGRNIdSettingsLoad();  // call to manage old GRN data

        configureKeyBindings(); // For Default Frame Key Controls

    }

    private void addPlaceholder() {

        grnIdTextField.setText("Click to GRN ID");
        grnIdTextField.setForeground(Color.GRAY);

        NewBatchAddingTextField.setText("New Batch");
        NewBatchAddingTextField.setForeground(Color.GRAY);

        AddProductTextField.setText("Type Product Name");
        AddProductTextField.setForeground(Color.GRAY);

    }

    // global variables of Table & ComboBox Models
    private DefaultTableModel tablemodel;
    private DefaultComboBoxModel model;

    //Batch combobox
    private void loadBatch() {

        try {

            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `batch` ");

            Vector<String> vector = new Vector<>();
            vector.add("Select Batch");

            while (resultSet.next()) {

                vector.add(resultSet.getString("batch_name"));
                BatchMap.put(resultSet.getString("batch_name"), resultSet.getString("batch_id"));

            }

            model = new DefaultComboBoxModel(vector);
            BatchSelectComboBox.setModel(model);

        } catch (Exception e) {
        }

    }

    //Product combobox
    private void loadProduct() {

        try {
            ResultSet resultSet = MySql.executeSearch("SELECT * FROM  `grn` ");

            Vector<String> vector = new Vector<>();
            vector.add("Select Product");

            while (resultSet.next()) {

                String product = resultSet.getString("product_name") + " " + "Price:-" + resultSet.getString("buying_price");

                vector.add(product);

                ProductMap.put(resultSet.getString("product_name"), resultSet.getString("grn_id"));

            }
            model = new DefaultComboBoxModel(vector);
            ProductAndPriceComboBox.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    //Supplier combobox

    private void loadSupplier() {
        try {
            ResultSet resultSet = MySql.executeSearch(
                    "SELECT supplier.supplier_id, supplier.first_name, supplier.last_name, company.company_name "
                    + "FROM supplier "
                    + "INNER JOIN company ON supplier.company_company_id = company.company_id"
            );

            Vector<String> vector = new Vector<>();
            
            vector.add("Select Supplier");

            while (resultSet.next()) {
                
                String supplierId = resultSet.getString("supplier_id");
                
                String fullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                
                String companyName = resultSet.getString("company_name");

                String displayText = fullName + " - " + companyName;

                vector.add(displayText);
                
                SupplierMap.put(fullName, resultSet.getString("supplier_id"));
                
            }

            model = new DefaultComboBoxModel<>(vector);
            
            SupplierSelectComboBox.setModel(model);

        } catch (Exception e) {
            
            e.printStackTrace();
            
        }
        
    }

    //Status combobox
    private void loadStatus() {

        try {

            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `suppliers_order_status` ");

            Vector<String> vector = new Vector<>();
            vector.add("Select Status");

            while (resultSet.next()) {

                vector.add(resultSet.getString("suppliers_order_status_name"));
                StatusMap.put(resultSet.getString("suppliers_order_status_name"), resultSet.getString("suppliers_order_status_id"));

            }

            model = new DefaultComboBoxModel(vector);
            GrnStatusSelectComboBox.setModel(model);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    //Payment combobox
    private void loadPaymentStatus() {

        try {

            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `payment_status` ");

            Vector<String> vector = new Vector<>();
            vector.add("Select Payment Status");

            while (resultSet.next()) {

                vector.add(resultSet.getString("payment_status_name"));
                PaymentStatusMap.put(resultSet.getString("payment_status_name"), resultSet.getString("payment_status_id"));

            }

            model = new DefaultComboBoxModel(vector);
            PaymentStatusComboBox.setModel(model);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    //table listner add to calculate total in jtable
    private void addGrnTableListener() {

        GRNaddingtable.getModel().addTableModelListener(e -> {

            DefaultTableModel tableModel = (DefaultTableModel) GRNaddingtable.getModel();

            int row = e.getFirstRow();
            int col = e.getColumn();

            if (col == 1 || col == 6) {

                try {

                    double qty = Double.parseDouble(tableModel.getValueAt(row, 1).toString());
                    double buyingPrice = Double.parseDouble(tableModel.getValueAt(row, 6).toString());

                    double total = qty * buyingPrice;

                    tableModel.setValueAt(total, row, 10);

                } catch (NumberFormatException | NullPointerException ex) {

                    tableModel.setValueAt(0.0, row, 10);

                }

            }

            // Update the Subtotal text field with the combined total of all rows
            updateSubtotal(tableModel);

        });

    }

    //Load value into sub total textfield
    private void updateSubtotal(DefaultTableModel tableModel) {

        double combinedTotal = 0.0;

        int rowCount = tableModel.getRowCount();

        for (int row = 0; row < rowCount; row++) {

            try {

                Object value = tableModel.getValueAt(row, 10);

                if (value != null && !value.toString().trim().isEmpty()) {

                    //double rowTotal = Double.parseDouble(tableModel.getValueAt(row, 10).toString());
                    double rowTotal = Double.parseDouble(value.toString());
                    combinedTotal += rowTotal;

                }

            } catch (NumberFormatException | NullPointerException ex) {

                ex.printStackTrace();

            }

        }

        SubTotalTextField.setText(String.format("%.2f", combinedTotal));

    }

    // Method to load Manage GRN Updating GRN Id and some settings
    private void manageGRNIdSettingsLoad() {

        // checks if String GRN Data is not null
        if (grnId != null) {

            // sets the GRN Id 
            grnIdTextField.setText(grnId);

            // hides generateId button
            grnIDGenerateButton.setVisible(false);

            // Sets Hand Icon to select Employee ID
            grnIdTextField.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Sets back to GRN Id Color origin
            grnIdTextField.setForeground(Color.BLACK);

            // Enable the text field (if it was disabled)
            grnIdTextField.setEnabled(true);

            // Request focus to the text field
            grnIdTextField.setFocusable(true);

            // get fNameTextField focus
            BatchSelectComboBox.grabFocus();

            GRNaddingtable.removeAll();

            manageGRNOtherDataLoad();

        }

    }

    // Method to load Manage GRN Updating data
    private void manageGRNOtherDataLoad() {

        try {

            // search query
            ResultSet resultSet = MySql.executeSearch("SELECT `grn_id`, `batch`.`batch_name`, `supplier`.`first_name`, `supplier`.`last_name`, "
                    + "`product_name`, `suppliers_order_status`.`suppliers_order_status_name`, `qty`, `mfd`, `exp`, `buying_price`, `selling_price`, "
                    + "`discount`, `payment_status`.`payment_status_name`, `total`, `sub_total`, `payable_amount`, `payment_due` "
                    + "FROM `grn` "
                    + "INNER JOIN `batch` ON `grn`.`batch_batch_id` = `batch`.`batch_id` "
                    + "INNER JOIN `suppliers_order_status` ON `grn`.`suppliers_order_status_suppliers_order_status_id` = `suppliers_order_status`.`suppliers_order_status_id` "
                    + "INNER JOIN `payment_status` ON `grn`.`payment_status_payment_status_id` = `payment_status`.`payment_status_id` "
                    + "INNER JOIN `supplier` ON `supplier`.`supplier_id` = `grn`.`supplier_supplier_id` "
                    + "INNER JOIN `company` ON `supplier`.`company_company_id` = `company`.`company_id` "
                    + "WHERE `grn`.`grn_id` = '" + grnId + "'");

            // to check available result
            boolean hasData = false;

            // query row checker and updater loop
            while (resultSet.next()) {

                // if there's a data boolean value will be true
                hasData = true;

                // TextFields Selection Load
                // Sets back to GRN Data TextFields Color origin
                AddProductTextField.setForeground(Color.BLACK);
                SubTotalTextField.setForeground(Color.BLACK);

                // Sets Selected GRN Data to TextFields
                AddProductTextField.setText(resultSet.getString("product_name"));
                SubTotalTextField.setText(resultSet.getString("sub_total"));
                PayableAmountTextField.setText(resultSet.getString("payable_amount"));
                PaymentDueTextField.setText(resultSet.getString("payment_due"));

                // Comboboxes Selection Load
                BatchSelectComboBox.setSelectedItem(resultSet.getString("batch_name"));
                SupplierSelectComboBox.setSelectedItem(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                GrnStatusSelectComboBox.setSelectedItem(resultSet.getString("suppliers_order_status_name"));
                PaymentStatusComboBox.setSelectedItem(resultSet.getString("payment_status_name"));

                manageGRNTableDataLoad();

            }

            // checks if data loaded from query if not shows a message
            if (!hasData) {

                JOptionPane.showMessageDialog(this, "No data found for GRN.", "Info", JOptionPane.INFORMATION_MESSAGE);

            }

        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    // Method to load Manage GRN Table Updating data
    private void manageGRNTableDataLoad() {

        try {

            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `grn` "
                    + "INNER JOIN `batch` ON `grn` .`batch_batch_id`= `batch`.`batch_id`"
                    + "INNER JOIN `suppliers_order_status` ON `grn` .`suppliers_order_status_suppliers_order_status_id`= `suppliers_order_status`.`suppliers_order_status_id`"
                    + "INNER JOIN `payment_status` ON `grn` .`payment_status_payment_status_id`= `payment_status`.`payment_status_id`"
                    + "WHERE `grn`.`grn_id` = '" + grnId + "' ");

            tablemodel = (DefaultTableModel) GRNaddingtable.getModel();

            tablemodel.setRowCount(0);

            while (resultSet.next()) {

                // add row to table
                tablemodel.addRow(new Object[]{
                    resultSet.getString("product_name"),
                    resultSet.getString("qty"),
                    resultSet.getString("batch_id"),
                    resultSet.getString("suppliers_order_status_name"),
                    resultSet.getString("mfd"),
                    resultSet.getString("exp"),
                    resultSet.getString("buying_price"),
                    resultSet.getString("selling_price"),
                    resultSet.getString("discount"),
                    resultSet.getString("payment_status_name"),
                    resultSet.getString("total")

                });

            }

        } catch (Exception e) {

            e.printStackTrace();

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
        grnbatchAndStockaddingPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        grnIdTextField = new javax.swing.JTextField();
        grnIDGenerateButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        BatchSelectComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        NewBatchAddingTextField = new javax.swing.JTextField();
        kButton1 = new com.k33ptoo.components.KButton();
        SupplierAndSelectProductAddingPanel = new javax.swing.JPanel();
        addProductToGRNS = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        SupplierSelectComboBox = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        AddProductTextField = new javax.swing.JTextField();
        GrnStatusSelectComboBox = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        InsertGrnsButton = new com.k33ptoo.components.KButton();
        UpdateGrnsButton = new com.k33ptoo.components.KButton();
        jButton1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        ProductAndPriceComboBox = new javax.swing.JComboBox<>();
        GRNSaddingTablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        GRNaddingtable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        SubTotalTextField = new javax.swing.JTextField();
        PayableAmountTextField = new javax.swing.JFormattedTextField();
        PaymentDueTextField = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        PaymentStatusComboBox = new javax.swing.JComboBox<>();
        kButton2 = new com.k33ptoo.components.KButton();
        BackToDashboardPanel = new javax.swing.JPanel();
        BackToDashboardButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        headerPanel.setBackground(new java.awt.Color(153, 153, 153));
        headerPanel.setPreferredSize(new java.awt.Dimension(805, 60));

        jLabel1.setBackground(new java.awt.Color(153, 153, 153));
        jLabel1.setFont(new java.awt.Font("Audiowide", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add GRNS");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1362, Short.MAX_VALUE)
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        getContentPane().add(headerPanel, java.awt.BorderLayout.PAGE_START);

        bodyPanel.setLayout(new java.awt.BorderLayout());

        grnbatchAndStockaddingPanel.setPreferredSize(new java.awt.Dimension(783, 70));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel2.setText("GRN ID");

        grnIdTextField.setEditable(false);
        grnIdTextField.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        grnIdTextField.setEnabled(false);
        grnIdTextField.setFocusable(false);
        grnIdTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                grnIdTextFieldKeyPressed(evt);
            }
        });

        grnIDGenerateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/generate.png"))); // NOI18N
        grnIDGenerateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grnIDGenerateButtonActionPerformed(evt);
            }
        });
        grnIDGenerateButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                grnIDGenerateButtonKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel3.setText("Select Batch");

        BatchSelectComboBox.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        BatchSelectComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        BatchSelectComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BatchSelectComboBoxActionPerformed(evt);
            }
        });
        BatchSelectComboBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BatchSelectComboBoxKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel4.setText("Add New Batch");

        NewBatchAddingTextField.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        NewBatchAddingTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                NewBatchAddingTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                NewBatchAddingTextFieldFocusLost(evt);
            }
        });

        kButton1.setText("Add New Batch");
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

        javax.swing.GroupLayout grnbatchAndStockaddingPanelLayout = new javax.swing.GroupLayout(grnbatchAndStockaddingPanel);
        grnbatchAndStockaddingPanel.setLayout(grnbatchAndStockaddingPanelLayout);
        grnbatchAndStockaddingPanelLayout.setHorizontalGroup(
            grnbatchAndStockaddingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grnbatchAndStockaddingPanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addGap(58, 58, 58)
                .addComponent(grnIdTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(grnIDGenerateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel3)
                .addGap(12, 12, 12)
                .addComponent(BatchSelectComboBox, 0, 290, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(12, 12, 12)
                .addComponent(NewBatchAddingTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );
        grnbatchAndStockaddingPanelLayout.setVerticalGroup(
            grnbatchAndStockaddingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grnbatchAndStockaddingPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(grnbatchAndStockaddingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(grnbatchAndStockaddingPanelLayout.createSequentialGroup()
                        .addGroup(grnbatchAndStockaddingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(grnbatchAndStockaddingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(grnIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(grnIDGenerateButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))
                    .addGroup(grnbatchAndStockaddingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(NewBatchAddingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(grnbatchAndStockaddingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BatchSelectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        bodyPanel.add(grnbatchAndStockaddingPanel, java.awt.BorderLayout.PAGE_START);

        SupplierAndSelectProductAddingPanel.setLayout(new java.awt.BorderLayout());

        addProductToGRNS.setPreferredSize(new java.awt.Dimension(815, 128));

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel5.setText("Select Supplier");

        SupplierSelectComboBox.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        SupplierSelectComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        SupplierSelectComboBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SupplierSelectComboBoxKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel6.setText("Add Product");

        AddProductTextField.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        AddProductTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AddProductTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                AddProductTextFieldFocusLost(evt);
            }
        });
        AddProductTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddProductTextFieldKeyPressed(evt);
            }
        });

        GrnStatusSelectComboBox.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        GrnStatusSelectComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        GrnStatusSelectComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrnStatusSelectComboBoxActionPerformed(evt);
            }
        });
        GrnStatusSelectComboBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GrnStatusSelectComboBoxKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel7.setText("Select Status");

        InsertGrnsButton.setText("Insert GRNS");
        InsertGrnsButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        InsertGrnsButton.setkEndColor(new java.awt.Color(0, 204, 204));
        InsertGrnsButton.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        InsertGrnsButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        InsertGrnsButton.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        InsertGrnsButton.setkPressedColor(new java.awt.Color(0, 102, 153));
        InsertGrnsButton.setkSelectedColor(new java.awt.Color(0, 102, 153));
        InsertGrnsButton.setkStartColor(new java.awt.Color(0, 102, 153));
        InsertGrnsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertGrnsButtonActionPerformed(evt);
            }
        });

        UpdateGrnsButton.setText("Update GRNS");
        UpdateGrnsButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        UpdateGrnsButton.setkEndColor(new java.awt.Color(0, 204, 204));
        UpdateGrnsButton.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        UpdateGrnsButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        UpdateGrnsButton.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        UpdateGrnsButton.setkPressedColor(new java.awt.Color(0, 102, 153));
        UpdateGrnsButton.setkSelectedColor(new java.awt.Color(0, 102, 153));
        UpdateGrnsButton.setkStartColor(new java.awt.Color(0, 102, 153));
        UpdateGrnsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateGrnsButtonActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/refresh.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel12.setText("Select Product");

        ProductAndPriceComboBox.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        ProductAndPriceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout addProductToGRNSLayout = new javax.swing.GroupLayout(addProductToGRNS);
        addProductToGRNS.setLayout(addProductToGRNSLayout);
        addProductToGRNSLayout.setHorizontalGroup(
            addProductToGRNSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addProductToGRNSLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(addProductToGRNSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addGap(12, 12, 12)
                .addGroup(addProductToGRNSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GrnStatusSelectComboBox, 0, 276, Short.MAX_VALUE)
                    .addComponent(SupplierSelectComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(addProductToGRNSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addProductToGRNSLayout.createSequentialGroup()
                        .addComponent(InsertGrnsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(UpdateGrnsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(addProductToGRNSLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ProductAndPriceComboBox, 0, 314, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(12, 12, 12)
                        .addComponent(AddProductTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)))
                .addGap(25, 25, 25))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        addProductToGRNSLayout.setVerticalGroup(
            addProductToGRNSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addProductToGRNSLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(addProductToGRNSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(SupplierSelectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddProductTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(ProductAndPriceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addProductToGRNSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(addProductToGRNSLayout.createSequentialGroup()
                        .addGroup(addProductToGRNSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addProductToGRNSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(InsertGrnsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(UpdateGrnsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(GrnStatusSelectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)))
                .addGap(16, 16, 16)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        SupplierAndSelectProductAddingPanel.add(addProductToGRNS, java.awt.BorderLayout.PAGE_START);

        GRNaddingtable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        GRNaddingtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "QTY", "Batch NO", "Receiving Status", "MFD", "EXP", "Buying Price", "Selling Price", "Discount", "Payment Status", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, true, true, true, true, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        GRNaddingtable.getTableHeader().setReorderingAllowed(false);
        GRNaddingtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GRNaddingtableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(GRNaddingtable);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel8.setText("Sub Total");

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel9.setText("Payable Amount");

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel10.setText("Payment Due");

        PayableAmountTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        PayableAmountTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        PayableAmountTextField.setText("0.00");
        PayableAmountTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PayableAmountTextFieldKeyReleased(evt);
            }
        });

        PaymentDueTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        PaymentDueTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        PaymentDueTextField.setText("0.00");

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel11.setText("Payment Status");

        PaymentStatusComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PaymentStatusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        kButton2.setText("Add GRNS");
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PaymentStatusComboBox, 0, 241, Short.MAX_VALUE)
                    .addComponent(PayableAmountTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PaymentDueTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(SubTotalTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(kButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SubTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PayableAmountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(PaymentDueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(PaymentStatusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout GRNSaddingTablePanelLayout = new javax.swing.GroupLayout(GRNSaddingTablePanel);
        GRNSaddingTablePanel.setLayout(GRNSaddingTablePanelLayout);
        GRNSaddingTablePanelLayout.setHorizontalGroup(
            GRNSaddingTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GRNSaddingTablePanelLayout.createSequentialGroup()
                .addGroup(GRNSaddingTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(GRNSaddingTablePanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1312, Short.MAX_VALUE))
                    .addGroup(GRNSaddingTablePanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        GRNSaddingTablePanelLayout.setVerticalGroup(
            GRNSaddingTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GRNSaddingTablePanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        SupplierAndSelectProductAddingPanel.add(GRNSaddingTablePanel, java.awt.BorderLayout.CENTER);

        BackToDashboardPanel.setBackground(new java.awt.Color(153, 153, 153));
        BackToDashboardPanel.setPreferredSize(new java.awt.Dimension(833, 50));

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
                .addContainerGap(1314, Short.MAX_VALUE))
        );
        BackToDashboardPanelLayout.setVerticalGroup(
            BackToDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackToDashboardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BackToDashboardButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SupplierAndSelectProductAddingPanel.add(BackToDashboardPanel, java.awt.BorderLayout.PAGE_END);

        bodyPanel.add(SupplierAndSelectProductAddingPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(bodyPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //ID generate
    private void grnIDGenerateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grnIDGenerateButtonActionPerformed

        grnIdTextField.setCursor(new Cursor(Cursor.HAND_CURSOR));

        grnIdTextField.setForeground(Color.BLACK);

        long id = System.currentTimeMillis();
        grnIdTextField.setText("GRN" + String.valueOf(id));

        grnIdTextField.setEnabled(true);

        grnIdTextField.setFocusable(true);

    }//GEN-LAST:event_grnIDGenerateButtonActionPerformed

    //SignOutButtonCode

    private void BackToDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToDashboardButtonActionPerformed

        if (manageGrns != null) {

            // Refresh the main GUI
            manageGrns.refreshData();

            // Show the main GUI
            manageGrns.setVisible(true);

        }

        // To Close AddPurchasingOrder(this JFrame) and go back to HR Dashboard(main JFrame)  
        this.dispose();

    }//GEN-LAST:event_BackToDashboardButtonActionPerformed

    private void AddProductTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AddProductTextFieldFocusGained

        if (AddProductTextField.getText().equals("Type Product Name")) {

            AddProductTextField.setText("");
            AddProductTextField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_AddProductTextFieldFocusGained

    private void AddProductTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AddProductTextFieldFocusLost

        if (AddProductTextField.getText().isEmpty()) {

            AddProductTextField.setText("Type Product Name");
            AddProductTextField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_AddProductTextFieldFocusLost

    private void NewBatchAddingTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NewBatchAddingTextFieldFocusGained
        if (NewBatchAddingTextField.getText().equals("New Batch")) {
            NewBatchAddingTextField.setText("");
            NewBatchAddingTextField.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_NewBatchAddingTextFieldFocusGained

    private void NewBatchAddingTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NewBatchAddingTextFieldFocusLost
        if (NewBatchAddingTextField.getText().isEmpty()) {
            NewBatchAddingTextField.setText("New Batch");
            NewBatchAddingTextField.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_NewBatchAddingTextFieldFocusLost

    //Add new Batch button
    private void kButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton1ActionPerformed
        try {

            String batchname = NewBatchAddingTextField.getText();

            if (batchname.isEmpty() || batchname.equals("New Batch")) {

                JOptionPane.showMessageDialog(this, "Please enter Batch Name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {

                ResultSet resultSet = MySql.executeSearch("SELECT * FROM `batch` WHERE `batch_name`= '" + batchname + "'");

                if (resultSet.next()) {

                    JOptionPane.showMessageDialog(this, "Batch Name Already Used", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {

                    MySql.executeUpdate("INSERT INTO `batch` (`batch_name` ) VALUES ('" + batchname + "')");

                    NewBatchAddingTextField.setText("");

                    loadBatch();

                    JOptionPane.showMessageDialog(this, "Batch Added Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);

                }
            }
        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while adding the Batch", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_kButton1ActionPerformed

    //Insert Function
    private void InsertGrnsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertGrnsButtonActionPerformed

        try {

            String grnId = grnIdTextField.getText();
            String batchname = String.valueOf(BatchSelectComboBox.getSelectedItem());
            String supplier = String.valueOf(SupplierSelectComboBox.getSelectedItem());
            String ProductAndPrice = String.valueOf(ProductAndPriceComboBox.getSelectedItem());
            String productname = AddProductTextField.getText();
            String status = String.valueOf(GrnStatusSelectComboBox.getSelectedItem());

            if (grnId.isEmpty() || grnId.equals("GRN ID")) {
                JOptionPane.showMessageDialog(this, "Please generate ID", "Warning", JOptionPane.WARNING_MESSAGE);

            } else if (batchname.equals("Select Batch")) {
                JOptionPane.showMessageDialog(this, "Please select Batch", "Warning", JOptionPane.WARNING_MESSAGE);

            } else if (supplier.equals("Select Supplier")) {
                JOptionPane.showMessageDialog(this, "Please select Supplier", "Warning", JOptionPane.WARNING_MESSAGE);

            } else if (ProductAndPrice.equals("Select Product")) {
                JOptionPane.showMessageDialog(this, "Please select Product", "Warning", JOptionPane.WARNING_MESSAGE);

            } else if (productname.isEmpty() || productname.equals("Product Name")) {
                JOptionPane.showMessageDialog(this, "Please enter Product", "Warning", JOptionPane.WARNING_MESSAGE);

            } else if (status.equals("Select Status")) {
                JOptionPane.showMessageDialog(this, "Please select Status", "Warning", JOptionPane.WARNING_MESSAGE);

            } else {

                String batchNo = BatchMap.get(batchname);

                DefaultTableModel model = (DefaultTableModel) GRNaddingtable.getModel();
                model.setRowCount(0);
                Object[] rowData = {productname, "", batchNo, status, "", "", "", "", "", "", ""};
                model.addRow(rowData);

                JOptionPane.showMessageDialog(this, "Data Inserted Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);

            }

        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while inserting the Data", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_InsertGrnsButtonActionPerformed

    //Update Function
    private void UpdateGrnsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateGrnsButtonActionPerformed

        int row = GRNaddingtable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(this, "Please select the row you want to update", "Warning", JOptionPane.WARNING_MESSAGE);

        } else {

            String grnId = grnIdTextField.getText().trim();
            String productname = AddProductTextField.getText().trim();
            String status = String.valueOf(GrnStatusSelectComboBox.getSelectedItem()).trim();
            String sellingPrice = String.valueOf(GRNaddingtable.getValueAt(row, 7)).trim();
            String discount = String.valueOf(GRNaddingtable.getValueAt(row, 8)).trim();
            String paymentStatus = String.valueOf(PaymentStatusComboBox.getSelectedItem()).trim();
            String payableAmount = PayableAmountTextField.getText().trim();
            String paymentDue = PaymentDueTextField.getText().trim();

            String selectedProduct = String.valueOf(GRNaddingtable.getValueAt(row, 0)).trim();

            if (productname.isEmpty() || productname.equals("Product Name")) {
                JOptionPane.showMessageDialog(this, "Please enter product", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (status.equals("Select Status")) {
                JOptionPane.showMessageDialog(this, "Please update status", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (paymentStatus.equals("Select Payment Status")) {
                JOptionPane.showMessageDialog(this, "Please update payment status", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    String mfdd = String.valueOf(GRNaddingtable.getValueAt(row, 4)).trim();
                    String expd = String.valueOf(GRNaddingtable.getValueAt(row, 5)).trim();

                    String mfd = mfdd.isEmpty() ? "NULL" : "'" + mfdd + "'";
                    String exp = expd.isEmpty() ? "NULL" : "'" + expd + "'";

                    ResultSet rs = MySql.executeSearch("SELECT * FROM `grn` WHERE `product_name` = '" + selectedProduct + "' AND `grn_id` = '" + grnId + "'");

                    if (rs.next()) {

                        double discountPrice = 0.0;
                        discountPrice = Double.parseDouble(discount);

                        String updateQuery = "UPDATE `grn` SET `suppliers_order_status_suppliers_order_status_id` = '" + StatusMap.get(status) + "', "
                                + "`mfd` = " + mfd + ", `exp` = " + exp + ",`selling_price` = '" + sellingPrice + "',`discount` = '" + discountPrice + "', "
                                + "`payment_status_payment_status_id` = '" + PaymentStatusMap.get(paymentStatus) + "',`payable_amount` = '" + payableAmount + "', "
                                + "`payment_due` = '" + paymentDue + "' WHERE `grn_id` = '" + grnId + "'";

                        int updatedRows = MySql.executeUpdate(updateQuery);

                        if (updatedRows > 0) {
                            JOptionPane.showMessageDialog(this, "Data Updated Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "Error Updating", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        reset();

                    } else {
                        JOptionPane.showMessageDialog(this, "Product not found in database", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception e) {

                    e.printStackTrace();

                    JOptionPane.showMessageDialog(this, "Error occurred while updating the Data", "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        }

    }//GEN-LAST:event_UpdateGrnsButtonActionPerformed
    //Delete Function
    private void GRNaddingtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GRNaddingtableMouseClicked

        int tableRow = GRNaddingtable.getSelectedRow();

        String product = String.valueOf(GRNaddingtable.getValueAt(tableRow, 0));
        AddProductTextField.setText(product);

        String batch = String.valueOf(GRNaddingtable.getValueAt(tableRow, 2));
        BatchSelectComboBox.setSelectedItem(batch);

        String status = String.valueOf(GRNaddingtable.getValueAt(tableRow, 3));
        GrnStatusSelectComboBox.setSelectedItem(status);

        if (evt.getClickCount() == 2) {

            String selectedproduct = String.valueOf(GRNaddingtable.getValueAt(tableRow, 0));

            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this Row?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {

                try {

                    MySql.executeUpdate("DELETE FROM `grn` WHERE `product_name`='" + selectedproduct + "'");

                    MySql.executeUpdate("SET @row_number = 0");
                    MySql.executeUpdate("UPDATE `grn` "
                            + "SET `grn_id` = (@row_number := @row_number + 1) "
                            + "ORDER BY `grn_id`");

                    MySql.executeUpdate("ALTER TABLE `grn` AUTO_INCREMENT = 1");

                    reset();

                    JOptionPane.showMessageDialog(this, "Row Deleted Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {

                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error occurred while deleting the Row", "Error", JOptionPane.ERROR_MESSAGE);

                }

            }

        }

    }//GEN-LAST:event_GRNaddingtableMouseClicked

    //Balance load into textfield
    private void PayableAmountTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PayableAmountTextFieldKeyReleased
        try {

            double payment = Double.parseDouble(PayableAmountTextField.getText());

            double subtotal = Double.parseDouble(SubTotalTextField.getText());

            double balance = payment - subtotal;

            PaymentDueTextField.setText(String.format("%.2f", balance));
        } catch (NumberFormatException ex) {

            PaymentDueTextField.setText("0.00");
        }
    }//GEN-LAST:event_PayableAmountTextFieldKeyReleased

    private void grnIdTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grnIdTextFieldKeyPressed

        if (evt.getKeyCode() == 10) {

            BatchSelectComboBox.grabFocus();

        }

    }//GEN-LAST:event_grnIdTextFieldKeyPressed

    private void BatchSelectComboBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BatchSelectComboBoxKeyPressed

        if (evt.getKeyCode() == 10) {

            SupplierSelectComboBox.grabFocus();

        }

    }//GEN-LAST:event_BatchSelectComboBoxKeyPressed

    private void SupplierSelectComboBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SupplierSelectComboBoxKeyPressed

        if (evt.getKeyCode() == 10) {

            AddProductTextField.grabFocus();

        }

    }//GEN-LAST:event_SupplierSelectComboBoxKeyPressed

    private void AddProductTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AddProductTextFieldKeyPressed

        if (evt.getKeyCode() == 10) {

            GrnStatusSelectComboBox.grabFocus();

        }

    }//GEN-LAST:event_AddProductTextFieldKeyPressed

    //Add Function
    private void kButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton2ActionPerformed

        try {

            String grnId = grnIdTextField.getText();
            String batchname = String.valueOf(BatchSelectComboBox.getSelectedItem());
            String supplierFull = String.valueOf(SupplierSelectComboBox.getSelectedItem());
            String[] parts = supplierFull.split("-");
            String supplierName = parts[0].trim();        
            String productname = AddProductTextField.getText();
            String status = String.valueOf(GrnStatusSelectComboBox.getSelectedItem());

            // New fields to be added
            String paymentStatus = String.valueOf(PaymentStatusComboBox.getSelectedItem());
            String subtotal = SubTotalTextField.getText();
            String payment = PayableAmountTextField.getText();
            String balance = PaymentDueTextField.getText();

            // Validate the non-table fields 
            if (grnId.isEmpty() || grnId.equals("GRN ID")) {

                JOptionPane.showMessageDialog(this, "Please generate ID", "Warning", JOptionPane.WARNING_MESSAGE);
                return;

            } else if (batchname.equals("Select Batch")) {

                JOptionPane.showMessageDialog(this, "Please select Batch", "Warning", JOptionPane.WARNING_MESSAGE);
                return;

            } else if (supplierName.equals("Select Supplier")) {

                JOptionPane.showMessageDialog(this, "Please select Supplier", "Warning", JOptionPane.WARNING_MESSAGE);
                return;

            } else if (productname.isEmpty() || productname.equals("Product Name")) {

                JOptionPane.showMessageDialog(this, "Please enter Product", "Warning", JOptionPane.WARNING_MESSAGE);
                return;

            } else if (status.equals("Select Status")) {

                JOptionPane.showMessageDialog(this, "Please select Status", "Warning", JOptionPane.WARNING_MESSAGE);
                return;

            } else if (subtotal.isEmpty() || payment.isEmpty() || balance.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please enter values for Subtotal, Payable Amount and Payment Due", "Warning", JOptionPane.WARNING_MESSAGE);
                return;

            } else if (paymentStatus.equals("Select Payment Status")) {

                JOptionPane.showMessageDialog(this, "Please select Payment Status", "Warning", JOptionPane.WARNING_MESSAGE);
                return;

            }

            // Get the DefaultTableModel for the data in the table
            DefaultTableModel tableModel = (DefaultTableModel) GRNaddingtable.getModel();
            int rowCount = tableModel.getRowCount();

            LocalDate currentDate = LocalDate.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter);

            // Validate that all required columns have data
            for (int i = 0; i < rowCount; i++) {

                String qty = tableModel.getValueAt(i, 1) == null ? "" : tableModel.getValueAt(i, 1).toString();       // Column 1: QTY
                //String mfd = tableModel.getValueAt(i, 4) == null ? "" : tableModel.getValueAt(i, 4).toString();       // Column 4: MFD
                // String exp = tableModel.getValueAt(i, 5) == null ? "" : tableModel.getValueAt(i, 5).toString();       // Column 5: EXP
                String buyingPrice = tableModel.getValueAt(i, 6) == null ? "" : tableModel.getValueAt(i, 6).toString(); // Column 6: Buying Price
                //String sellingPrice = tableModel.getValueAt(i, 7) == null ? "" : tableModel.getValueAt(i, 7).toString(); // Column 7: Selling Price
                //String discount = tableModel.getValueAt(i, 8) == null ? "" : tableModel.getValueAt(i, 8).toString(); // Column 8: Discount
                String total = tableModel.getValueAt(i, 10) == null ? "" : tableModel.getValueAt(i, 10).toString();    // Column 10: Total

                // Check if any of the required fields are empty
                if (qty.isEmpty() || buyingPrice.isEmpty() || total.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all required fields in the row " + (i + 1), "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            //Insert the rows from the table (editable columns) into the database
            for (int i = 0; i < rowCount; i++) {

                String qty = tableModel.getValueAt(i, 1).toString();
                String mfd = tableModel.getValueAt(i, 4).toString();
                String exp = tableModel.getValueAt(i, 5).toString();
                String buyingPrice = tableModel.getValueAt(i, 6).toString();
                String sellingPrice = tableModel.getValueAt(i, 7).toString();
                String discount = tableModel.getValueAt(i, 8).toString();
                String total = tableModel.getValueAt(i, 10).toString();
                
                // Insert the data into the `grn_details` table (use appropriate table and column names)
                MySql.executeUpdate("INSERT INTO `grn`"
                        + "(`grn_id`, `product_name`, `qty`, `batch_batch_id`, `suppliers_order_status_suppliers_order_status_id`,"
                        + "`buying_price`,`payment_status_payment_status_id`,`total`, "
                        + "`added_date`, `sub_total`, `payable_amount`, `payment_due`, `supplier_supplier_id`) "
                        + "VALUES ('" + grnId + "','" + productname + "','" + qty + "','" + BatchMap.get(batchname) + "', '" + StatusMap.get(status) + "',"
                        + "'" + buyingPrice + "','" + PaymentStatusMap.get(paymentStatus) + "', '" + total + "', '" + formattedDate + "', '" + subtotal + "', '" + payment + "', '"
                        + balance + "', '" + SupplierMap.get(supplierName) + "')");
            }

            reset();

            JOptionPane.showMessageDialog(this, "GRN Added Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while adding the GRN", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_kButton2ActionPerformed

    private void GrnStatusSelectComboBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GrnStatusSelectComboBoxKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            InsertGrnsButton.doClick();

        }

    }//GEN-LAST:event_GrnStatusSelectComboBoxKeyPressed

    private void grnIDGenerateButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grnIDGenerateButtonKeyPressed
        // code to add key controls for grnIDGenerateButton

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT -> {

                grnIDGenerateButton.doClick();
                BatchSelectComboBox.grabFocus();

            }

            case KeyEvent.VK_RIGHT ->
                BatchSelectComboBox.grabFocus();

            case KeyEvent.VK_DOWN ->
                SupplierSelectComboBox.grabFocus();

            case KeyEvent.VK_DELETE -> {

                grnIdTextField.setText("Click to Generate ID");
                grnIdTextField.setForeground(Color.GRAY);

            }

        }

    }//GEN-LAST:event_grnIDGenerateButtonKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        reset();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void GrnStatusSelectComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrnStatusSelectComboBoxActionPerformed

// TODO add your handling code here:
    }//GEN-LAST:event_GrnStatusSelectComboBoxActionPerformed

    private void BatchSelectComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BatchSelectComboBoxActionPerformed
        String batchname = String.valueOf(BatchSelectComboBox.getSelectedItem());
        String batchId = BatchMap.get(batchname);

        ProductAndPriceComboBox.removeAllItems();
        if (batchId != null) {

            try {

                ResultSet resultSet = MySql.executeSearch("SELECT * FROM `grn` WHERE `batch_batch_id`  =' " + batchId + " '  ");

                while (resultSet.next()) {

                    String productName = resultSet.getString("product_name") + " " + "Price:-" + resultSet.getString("buying_price");
                    ProductAndPriceComboBox.addItem(productName);

                }

            } catch (Exception e) {
                e.printStackTrace();

            }

        }

    }//GEN-LAST:event_BatchSelectComboBoxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {

            new AddPurchasingOrder(null, null).setVisible(true);

        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AddProductTextField;
    private javax.swing.JButton BackToDashboardButton;
    private javax.swing.JPanel BackToDashboardPanel;
    private javax.swing.JComboBox<String> BatchSelectComboBox;
    private javax.swing.JPanel GRNSaddingTablePanel;
    private javax.swing.JTable GRNaddingtable;
    private javax.swing.JComboBox<String> GrnStatusSelectComboBox;
    private com.k33ptoo.components.KButton InsertGrnsButton;
    private javax.swing.JTextField NewBatchAddingTextField;
    private javax.swing.JFormattedTextField PayableAmountTextField;
    private javax.swing.JFormattedTextField PaymentDueTextField;
    private javax.swing.JComboBox<String> PaymentStatusComboBox;
    private javax.swing.JComboBox<String> ProductAndPriceComboBox;
    private javax.swing.JTextField SubTotalTextField;
    private javax.swing.JPanel SupplierAndSelectProductAddingPanel;
    private javax.swing.JComboBox<String> SupplierSelectComboBox;
    private com.k33ptoo.components.KButton UpdateGrnsButton;
    private javax.swing.JPanel addProductToGRNS;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JButton grnIDGenerateButton;
    private javax.swing.JTextField grnIdTextField;
    private javax.swing.JPanel grnbatchAndStockaddingPanel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private com.k33ptoo.components.KButton kButton1;
    private com.k33ptoo.components.KButton kButton2;
    // End of variables declaration//GEN-END:variables

    private void reset() {

        // checks if constructor pass ManageEmployee 
        if (manageGrns == null) {

            grnIdTextField.setText("");

        }

        NewBatchAddingTextField.setText("");
        AddProductTextField.setText("");
        SubTotalTextField.setText("0.00");
        PayableAmountTextField.setText("0.00");
        PaymentDueTextField.setText("0.00");
        kButton2.setEnabled(true);
        BatchSelectComboBox.setSelectedIndex(0);
        SupplierSelectComboBox.setSelectedIndex(0);
        GrnStatusSelectComboBox.setSelectedIndex(0);
        PaymentStatusComboBox.setSelectedIndex(0);

        DefaultTableModel model = (DefaultTableModel) GRNaddingtable.getModel();
        model.setRowCount(0);

        GRNaddingtable.clearSelection();
        GRNaddingtable.revalidate();
        GRNaddingtable.repaint();

        // Re-add the placeholders to refreshed TextFields
        addPlaceholder();

        // checks if constructor pass ManageEmployee 
        if (manageGrns == null) {

            // Grabs grnIDGenerateButton focus 
            grnIDGenerateButton.grabFocus();

        }

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
