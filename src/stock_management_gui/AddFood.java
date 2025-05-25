/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package stock_management_gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.awt.Cursor;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MySql;

/**
 *
 * @author 2003k
 */
public class AddFood extends javax.swing.JFrame {
    
    private static HashMap<String, String> portionMap = new HashMap<>();

    /**
     * Creates new form AddFood
     */
    public AddFood() {
        initComponents();
        addPlaceholder();
        loadPortion();
        loadFood();
        foodIDTextField.setEditable(false);
        //changeAvailability();
    }

//    private void changeAvailability () {
//        
//        try {                  
//            
//            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
//            
//            int rowCount = model.getRowCount();
//            
//            for (int i = 0; i < rowCount; i++) {
//                
//                String foodId = model.getValueAt(i, 0).toString();
//                
//                String qtyfT = model.getValueAt(i, 4).toString();
//                
//                int qty = 0;
//                
//                try {
//                    
//                    qty = Integer.parseInt(qtyfT);
//                    
//                } catch (NumberFormatException e) {
//                    
//                    e.printStackTrace();
//                    
//                }
//                
//                if (qty == 0) {
//                    
//                    MySql.executeUpdate("UPDATE `foods` SET `food_status_food_status_id` = '2' "
//                            + "WHERE `food_id` = '"+foodId+"' ");
//                    
//                }
//                
//            }
//
//        } catch (Exception e) {
//            
//            e.printStackTrace();
//            
//        }
//        
//    }
    private void addPlaceholder() {
        
        foodIDTextField.setText("Food ID");
        foodIDTextField.setForeground(Color.GRAY);
        
        jTextField1.setText("Food Name");
        jTextField1.setForeground(Color.GRAY);
        
        jTextArea1.setText("Food Description");
        jTextArea1.setForeground(Color.GRAY);
        
        jTextField3.setText("Quantity");
        jTextField3.setForeground(Color.GRAY);
        
        jFormattedTextField1.setText("Price");
        jFormattedTextField1.setForeground(Color.GRAY);
        
    }

    // Load portion into the combo box
    private void loadPortion() {
        try {
            
            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `portion_types` ");
            Vector<String> vector = new Vector<>();
            vector.add("Select Portion");
            
            while (resultSet.next()) {
                vector.add(resultSet.getString("portion_types_name"));
                portionMap.put(resultSet.getString("portion_types_name"), resultSet.getString("portion_types_id"));
            }
            
            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    //load food to table
    private void loadFood() {
        
        try {
            
            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `foods`"
                    + "INNER JOIN `portion_types` ON `foods` .`portion_types_portion_types_id`= `portion_types`.`portion_types_id`"
                    + "INNER JOIN `food_status` ON `food_status`.`food_status_id` = `foods`.`food_status_food_status_id` ");
            
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            
            while (resultSet.next()) {
                
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("food_id"));
                vector.add(resultSet.getString("food_name"));
                vector.add(resultSet.getString("description"));
                vector.add(resultSet.getString("portion_types.portion_types_name"));
                vector.add(resultSet.getString("qty"));
                vector.add(resultSet.getString("price"));
                vector.add(resultSet.getString("food_status.food_status_name"));
                
                model.addRow(vector);
                
            }
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
        }
    }

    /**
     * Creates new form AddFood
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        BackToDashboardButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        foodIDTextField = new javax.swing.JTextField();
        generateButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        AddButton = new com.k33ptoo.components.KButton();
        kButton1 = new com.k33ptoo.components.KButton();
        jLabel9 = new javax.swing.JLabel();
        AvailableradioButton = new javax.swing.JRadioButton();
        NotAvailableRadioButton = new javax.swing.JRadioButton();
        refreshButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));
        jPanel4.setPreferredSize(new java.awt.Dimension(973, 50));

        jLabel1.setFont(new java.awt.Font("Audiowide", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add Food");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1104, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setPreferredSize(new java.awt.Dimension(973, 50));

        BackToDashboardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/back-arrow.png"))); // NOI18N
        BackToDashboardButton.setPreferredSize(new java.awt.Dimension(75, 75));
        BackToDashboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackToDashboardButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BackToDashboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1061, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addComponent(BackToDashboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanel3.setPreferredSize(new java.awt.Dimension(400, 438));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel2.setText("Food ID");

        foodIDTextField.setPreferredSize(new java.awt.Dimension(64, 32));
        foodIDTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                foodIDTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                foodIDTextFieldFocusLost(evt);
            }
        });
        foodIDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foodIDTextFieldActionPerformed(evt);
            }
        });

        generateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/generate.png"))); // NOI18N
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel3.setText("Food Name");

        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel4.setText("Description");

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel5.setText("Portion");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea1FocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(jTextArea1);

        jTextField3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField3FocusLost(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel6.setText("QTY");

        jFormattedTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFormattedTextField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFormattedTextField1FocusLost(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel7.setText("Price");

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        AddButton.setText("Add");
        AddButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AddButton.setkEndColor(new java.awt.Color(0, 204, 204));
        AddButton.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        AddButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        AddButton.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        AddButton.setkPressedColor(new java.awt.Color(0, 102, 153));
        AddButton.setkSelectedColor(new java.awt.Color(0, 102, 153));
        AddButton.setkStartColor(new java.awt.Color(0, 102, 153));
        AddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButtonActionPerformed(evt);
            }
        });

        kButton1.setText("Update");
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

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel9.setText("Availability");

        buttonGroup1.add(AvailableradioButton);
        AvailableradioButton.setText("Available");

        buttonGroup1.add(NotAvailableRadioButton);
        NotAvailableRadioButton.setText("Not Available");

        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/refresh.png"))); // NOI18N
        refreshButton.setPreferredSize(new java.awt.Dimension(75, 75));
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(AddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AvailableradioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(NotAvailableRadioButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(12, 12, 12))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(61, 61, 61)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField3)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(foodIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(generateButton))
                            .addComponent(jTextField1)
                            .addComponent(jScrollPane1)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jFormattedTextField1))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(generateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(foodIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(AvailableradioButton)
                    .addComponent(NotAvailableRadioButton))
                .addGap(41, 41, 41)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(AddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(76, 76, 76))
        );

        jPanel1.add(jPanel3, java.awt.BorderLayout.LINE_START);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Food Name", "Description", "Portion", "QTY", "Price", "Availability"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        jPanel1.add(jPanel5, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BackToDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToDashboardButtonActionPerformed
        dispose();
    }//GEN-LAST:event_BackToDashboardButtonActionPerformed

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        
        foodIDTextField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        foodIDTextField.setForeground(Color.BLACK);
        
        long id = System.currentTimeMillis();
        foodIDTextField.setText("FDI" + String.valueOf(id));
        
        foodIDTextField.setEnabled(true);
        
        foodIDTextField.setFocusable(true);

    }//GEN-LAST:event_generateButtonActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void foodIDTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foodIDTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_foodIDTextFieldActionPerformed

    private void foodIDTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_foodIDTextFieldFocusGained
        
        if (foodIDTextField.getText().equals("Food ID")) {
            foodIDTextField.setText("");
            foodIDTextField.setForeground(Color.BLACK);
        }

    }//GEN-LAST:event_foodIDTextFieldFocusGained

    private void foodIDTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_foodIDTextFieldFocusLost
        
        if (foodIDTextField.getText().isEmpty()) {
            foodIDTextField.setText("Food ID");
            foodIDTextField.setForeground(Color.GRAY);
        }

    }//GEN-LAST:event_foodIDTextFieldFocusLost

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        
        if (jTextField1.getText().equals("Food Name")) {
            jTextField1.setText("");
            jTextField1.setForeground(Color.BLACK);
        }

    }//GEN-LAST:event_jTextField1FocusGained

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        
        if (jTextField1.getText().isEmpty()) {
            jTextField1.setText("Food Name");
            jTextField1.setForeground(Color.GRAY);
        }

    }//GEN-LAST:event_jTextField1FocusLost

    private void jTextArea1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea1FocusGained
        
        if (jTextArea1.getText().equals("Food Description")) {
            jTextArea1.setText("");
            jTextArea1.setForeground(Color.BLACK);
        }

    }//GEN-LAST:event_jTextArea1FocusGained

    private void jTextArea1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea1FocusLost
        
        if (jTextArea1.getText().isEmpty()) {
            jTextArea1.setText("Food Description");
            jTextArea1.setForeground(Color.GRAY);
        }

    }//GEN-LAST:event_jTextArea1FocusLost

    private void jTextField3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField3FocusGained
        
        if (jTextField3.getText().equals("Quantity")) {
            jTextField3.setText("");
            jTextField3.setForeground(Color.BLACK);
        }

    }//GEN-LAST:event_jTextField3FocusGained

    private void jTextField3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField3FocusLost
        
        if (jTextField3.getText().isEmpty()) {
            jTextField3.setText("Quantity");
            jTextField3.setForeground(Color.GRAY);
        }

    }//GEN-LAST:event_jTextField3FocusLost

    private void jFormattedTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextField1FocusGained
        
        if (jFormattedTextField1.getText().equals("Price")) {
            jFormattedTextField1.setText("");
            jFormattedTextField1.setForeground(Color.BLACK);
        }

    }//GEN-LAST:event_jFormattedTextField1FocusGained

    private void jFormattedTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextField1FocusLost
        
        if (jFormattedTextField1.getText().isEmpty()) {
            jFormattedTextField1.setText("Price");
            jFormattedTextField1.setForeground(Color.GRAY);
        }

    }//GEN-LAST:event_jFormattedTextField1FocusLost

    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtonActionPerformed
        
        try {
            
            int AvailableID = 0;
            
            String foodid = foodIDTextField.getText();
            String foodname = jTextField1.getText();
            String description = jTextArea1.getText();
            String qty = jTextField3.getText();
            String price = jFormattedTextField1.getText();
            String portion = String.valueOf(jComboBox1.getSelectedItem());
            
            if (foodid.isEmpty() || foodid.equals("Food ID")) {
                JOptionPane.showMessageDialog(this, "Please click the button for a new ID", "Warning", JOptionPane.WARNING_MESSAGE);
                
            } else if (foodname.isEmpty() || foodname.equals("Food Name")) {
                JOptionPane.showMessageDialog(this, "Please enter Food Name", "Warning", JOptionPane.WARNING_MESSAGE);
                
            } else if (description.isEmpty() || description.equals("Food Description")) {
                JOptionPane.showMessageDialog(this, "Please enter Description", "Warning", JOptionPane.WARNING_MESSAGE);
                
            } else if (qty.isEmpty() || qty.equals("Quantity")) {
                JOptionPane.showMessageDialog(this, "Please enter Quantity", "Warning", JOptionPane.WARNING_MESSAGE);
                
            } else if (price.isEmpty() || price.equals("Price")) {
                JOptionPane.showMessageDialog(this, "Please enter Price", "Warning", JOptionPane.WARNING_MESSAGE);
                
            } else {
                
                try {
                    double parsedPrice = Double.parseDouble(price); // Check if the price is a valid number
                    if (parsedPrice <= 0) {
                        JOptionPane.showMessageDialog(this, "Price must be a positive value", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Price must be a valid numeric value", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                if (portion.equals("Select Portion")) {
                    JOptionPane.showMessageDialog(this, "Please select a Portion", "Warning", JOptionPane.WARNING_MESSAGE);
                    
                } else {
                    
                    if (AvailableradioButton.isSelected()) {
                        
                        AvailableID = 1;
                        
                    } else if (NotAvailableRadioButton.isSelected()) {
                        
                        AvailableID = 2;
                        
                    }
                    
                    if (AvailableID == 0) {
                        
                        JOptionPane.showMessageDialog(this, "Data Null", "Please select availability", JOptionPane.WARNING_MESSAGE);
                        
                    } else {
                        
                        ResultSet resultSet = MySql.executeSearch("SELECT * FROM `foods` WHERE `food_id`= '" + foodid + "'");
                        
                        if (resultSet.next()) {
                            
                            JOptionPane.showMessageDialog(this, "This Food already exists", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            
                            MySql.executeUpdate("ALTER TABLE `foods` AUTO_INCREMENT = 1");
                            
                            MySql.executeUpdate("INSERT INTO `foods` (`food_id`,`food_name`,`description`,`portion_types_portion_types_id`,`qty`,`price`,`food_status_food_status_id`) VALUES ('" + foodid + "','" + foodname + "','" + description + "','" + portionMap.get(portion) + "','" + qty + "','" + price + "','" + AvailableID + "')");
                            
                            loadFood();
                            
                            refresh();
                            
                            foodIDTextField.grabFocus();
                            
                            JOptionPane.showMessageDialog(this, "Food Added Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);
                        }
                        
                    }
                    
                }
            }
        } catch (Exception e) {
            
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(this, "Error occurred while adding the Food", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_AddButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        refresh();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        
        int row = jTable1.getSelectedRow();
        
        generateButton.setEnabled(false);
        
        AddButton.setEnabled(false);
        
        String foodid = String.valueOf(jTable1.getValueAt(row, 0));
        foodIDTextField.setText(foodid);
        
        String foodname = String.valueOf(jTable1.getValueAt(row, 1));
        jTextField1.setText(foodname);
        
        String description = String.valueOf(jTable1.getValueAt(row, 2));
        jTextArea1.setText(description);
        
        String portion = String.valueOf(jTable1.getValueAt(row, 3));
        jComboBox1.setSelectedItem(portion);
        
        String qty = String.valueOf(jTable1.getValueAt(row, 4));
        jTextField3.setText(qty);
        
        String price = String.valueOf(jTable1.getValueAt(row, 5));
        jFormattedTextField1.setText(price);
        
        if (evt.getClickCount() == 2) {
            
            String selectedFoodID = String.valueOf(jTable1.getValueAt(row, 0));
            
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this food?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                
                try {
                    
                    MySql.executeUpdate("DELETE FROM `foods` WHERE `food_id`='" + selectedFoodID + "'");
                    
                    MySql.executeUpdate("SET @row_number = 0");
                    MySql.executeUpdate("UPDATE `foods` "
                            + "SET `food_id` = (@row_number := @row_number + 1) "
                            + "ORDER BY `food_id`");
                    
                    MySql.executeUpdate("ALTER TABLE `foods` AUTO_INCREMENT = 1");
                    
                    loadFood();
                    refresh();
                    
                    JOptionPane.showMessageDialog(this, "Food Deleted Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (Exception e) {
                    
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error occurred while deleting the Food", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void kButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton1ActionPerformed
        int row = jTable1.getSelectedRow();
        
        int avalability = 0;
        
        if (row == -1) {
            
            JOptionPane.showMessageDialog(this, "Please Select a Row", "Warning", JOptionPane.WARNING_MESSAGE);
            
        } else {
            
            String foodid = foodIDTextField.getText();
            String foodname = jTextField1.getText();
            String description = jTextArea1.getText();
            String qty = jTextField3.getText();
            String price = jFormattedTextField1.getText();
            String portion = String.valueOf(jComboBox1.getSelectedItem());
            
            String selectedFoodid = String.valueOf(jTable1.getValueAt(row, 0));
            
            if (foodid.isEmpty() || foodid.equals("Food ID")) {
                JOptionPane.showMessageDialog(this, "Please click the button for a new ID", "Warning", JOptionPane.WARNING_MESSAGE);
                
            } else if (foodname.isEmpty() || foodname.equals("Food Name")) {
                JOptionPane.showMessageDialog(this, "Please enter Food Name", "Warning", JOptionPane.WARNING_MESSAGE);
                
            } else if (description.isEmpty() || description.equals("Food Description")) {
                JOptionPane.showMessageDialog(this, "Please enter Description", "Warning", JOptionPane.WARNING_MESSAGE);
                
            } else if (qty.isEmpty() || qty.equals("Quantity")) {
                JOptionPane.showMessageDialog(this, "Please enter Quantity", "Warning", JOptionPane.WARNING_MESSAGE);
                
            } else if (price.isEmpty() || price.equals("Price")) {
                JOptionPane.showMessageDialog(this, "Please enter Price", "Warning", JOptionPane.WARNING_MESSAGE);
                
            } else {
                
                try {
                    double parsedPrice = Double.parseDouble(price); // Check if the price is a valid number
                    if (parsedPrice <= 0) {
                        JOptionPane.showMessageDialog(this, "Price must be a positive value", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Price must be a valid numeric value", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                if (portion.equals("Select Portion")) {
                    JOptionPane.showMessageDialog(this, "Please select a Portion", "Warning", JOptionPane.WARNING_MESSAGE);
                    
                } else {
                    
                    if (AvailableradioButton.isSelected()) {
                        
                        avalability = 1;
                        
                    } else if (NotAvailableRadioButton.isSelected()) {
                        
                        avalability = 2;
                        
                    } else {
                        
                        JOptionPane.showMessageDialog(this, "Please select a Avaliability ", "Warning", JOptionPane.WARNING_MESSAGE);
                        
                    }
                    
                    try {
                        
                        ResultSet rs = MySql.executeSearch("SELECT * FROM `foods` WHERE `food_id`= '" + foodid + "'");
                        
                        if (rs.next()) {
                            
                            MySql.executeUpdate("UPDATE `foods` SET `food_name` = '" + foodname + "',`description` = '" + description + "',`portion_types_portion_types_id` = '" + portionMap.get(portion) + "',"
                                    + "`qty` = '" + qty + "',`price` = '" + price + "',`food_status_food_status_id` = '" + avalability + "' "
                                    + "WHERE `food_id` = '" + selectedFoodid + "'");
                            
                            loadFood();
                            refresh();
                            
                            JOptionPane.showMessageDialog(this, "Food updated Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);
                            
                        }
                        
                    } catch (Exception e) {
                        
                        e.printStackTrace();
                        
                    }
                    
                }
                
            }
            
        }

    }//GEN-LAST:event_kButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddFood().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.k33ptoo.components.KButton AddButton;
    private javax.swing.JRadioButton AvailableradioButton;
    private javax.swing.JButton BackToDashboardButton;
    private javax.swing.JRadioButton NotAvailableRadioButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField foodIDTextField;
    private javax.swing.JButton generateButton;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private com.k33ptoo.components.KButton kButton1;
    private javax.swing.JButton refreshButton;
    // End of variables declaration//GEN-END:variables

    private void refresh() {
        
        foodIDTextField.setText("");
        jTextField1.setText("");
        jTextArea1.setText("");
        jTextField3.setText("");
        jFormattedTextField1.setText("");
        jComboBox1.setSelectedIndex(0);
        buttonGroup1.clearSelection();
        AddButton.setEnabled(true);
        jTable1.clearSelection();
        
        generateButton.setEnabled(true);
        
        addPlaceholder();
        
    }
}
