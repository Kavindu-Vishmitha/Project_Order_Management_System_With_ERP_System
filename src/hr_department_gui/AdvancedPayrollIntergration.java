/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package hr_department_gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.FlatLaf;
import java.awt.Color;
import java.awt.Cursor;
import java.sql.ResultSet;
import java.util.Vector;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.MySql;

/**
 *
 * @author 2003k
 */
public class AdvancedPayrollIntergration extends javax.swing.JDialog {

    /**
     * Creates new form AdvancedPayrollIntergration
     */
    public AdvancedPayrollIntergration(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        addPlaceholder(); //placeholder to textfield
        loadAdvancedPay();
    }

    private String payrllId;

    public void setPayrollId(String payId) {

        this.payrllId = payId;

        if (PayrollIDTextfield != null) {

            PayrollIDTextfield.setText(payId);

        }

    }

    //addplaceholder method
    private void addPlaceholder() {

        //Payroll ID textfield placeholder and color
        PayrollIDTextfield.setText("Enter Payroll ID");
        PayrollIDTextfield.setForeground(Color.GRAY);

        //Employee ID textfield placeholder and color
        EmployeeIDTextfield.setText("Enter Employee ID");
        EmployeeIDTextfield.setForeground(Color.GRAY);

        //Advanced Amount textfield placeholder and color
        AdvanceAmountTextfield.setText("Amount");
        AdvanceAmountTextfield.setForeground(Color.GRAY);
    }

    //load Advanced Payments to table
    private void loadAdvancedPay() {
        try {

            // Execute an SQL query to fetch all records from the "advanced_payroll" table
            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `advanced_payroll` ");

            DefaultTableModel model = (DefaultTableModel) PayrollTable.getModel();
            model.setRowCount(0);  // Clear any existing rows in the table 

            // Loop through the result set to extract advanced payroll data
            while (resultSet.next()) {

                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("ad_pay_id"));
                vector.add(resultSet.getString("date"));
                vector.add(resultSet.getString("employee_name"));
                vector.add(resultSet.getString("advanced_price"));

                model.addRow(vector); // Add the vector as a new row to the table model

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

        headerPanal = new javax.swing.JPanel();
        headingText = new javax.swing.JLabel();
        bodyPanal = new javax.swing.JPanel();
        advancedPayrollAddPanel = new javax.swing.JPanel();
        PayrollIDTextfield = new javax.swing.JTextField();
        EmployeeIDTextfield = new javax.swing.JTextField();
        EmployeeNameTextfield = new javax.swing.JTextField();
        AdvanceAmountTextfield = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        kButton1 = new com.k33ptoo.components.KButton();
        refreshButton = new javax.swing.JButton();
        tablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        PayrollTable = new javax.swing.JTable();
        BackToDashboardPanel = new javax.swing.JPanel();
        BackToDashboardButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        headerPanal.setBackground(new java.awt.Color(153, 153, 153));
        headerPanal.setPreferredSize(new java.awt.Dimension(874, 45));

        headingText.setFont(new java.awt.Font("Audiowide", 0, 24)); // NOI18N
        headingText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headingText.setText("Add Advanced Payroll");

        javax.swing.GroupLayout headerPanalLayout = new javax.swing.GroupLayout(headerPanal);
        headerPanal.setLayout(headerPanalLayout);
        headerPanalLayout.setHorizontalGroup(
            headerPanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1076, Short.MAX_VALUE)
            .addGroup(headerPanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(headingText, javax.swing.GroupLayout.DEFAULT_SIZE, 1076, Short.MAX_VALUE))
        );
        headerPanalLayout.setVerticalGroup(
            headerPanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
            .addGroup(headerPanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(headingText, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
        );

        getContentPane().add(headerPanal, java.awt.BorderLayout.PAGE_START);

        bodyPanal.setLayout(new java.awt.BorderLayout());

        advancedPayrollAddPanel.setPreferredSize(new java.awt.Dimension(894, 136));
        advancedPayrollAddPanel.setRequestFocusEnabled(false);

        PayrollIDTextfield.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        PayrollIDTextfield.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        PayrollIDTextfield.setPreferredSize(new java.awt.Dimension(87, 32));
        PayrollIDTextfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                PayrollIDTextfieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                PayrollIDTextfieldFocusLost(evt);
            }
        });
        PayrollIDTextfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PayrollIDTextfieldKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                PayrollIDTextfieldKeyTyped(evt);
            }
        });

        EmployeeIDTextfield.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        EmployeeIDTextfield.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        EmployeeIDTextfield.setPreferredSize(new java.awt.Dimension(152, 32));
        EmployeeIDTextfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                EmployeeIDTextfieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                EmployeeIDTextfieldFocusLost(evt);
            }
        });
        EmployeeIDTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeeIDTextfieldActionPerformed(evt);
            }
        });
        EmployeeIDTextfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                EmployeeIDTextfieldKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                EmployeeIDTextfieldKeyTyped(evt);
            }
        });

        EmployeeNameTextfield.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        EmployeeNameTextfield.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        EmployeeNameTextfield.setPreferredSize(new java.awt.Dimension(133, 32));
        EmployeeNameTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeeNameTextfieldActionPerformed(evt);
            }
        });

        AdvanceAmountTextfield.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        AdvanceAmountTextfield.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        AdvanceAmountTextfield.setPreferredSize(new java.awt.Dimension(137, 32));
        AdvanceAmountTextfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AdvanceAmountTextfieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                AdvanceAmountTextfieldFocusLost(evt);
            }
        });
        AdvanceAmountTextfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AdvanceAmountTextfieldKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel1.setText("Payroll ID");

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel2.setText("Employee ID");

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel3.setText("Employee Name");

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel4.setText("Advance Amount");

        kButton1.setText("Add");
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

        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/refresh.png"))); // NOI18N
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout advancedPayrollAddPanelLayout = new javax.swing.GroupLayout(advancedPayrollAddPanel);
        advancedPayrollAddPanel.setLayout(advancedPayrollAddPanelLayout);
        advancedPayrollAddPanelLayout.setHorizontalGroup(
            advancedPayrollAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(advancedPayrollAddPanelLayout.createSequentialGroup()
                .addContainerGap(98, Short.MAX_VALUE)
                .addGroup(advancedPayrollAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(advancedPayrollAddPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(12, 12, 12)
                        .addComponent(PayrollIDTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(EmployeeIDTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(advancedPayrollAddPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(AdvanceAmountTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(12, 12, 12)
                .addComponent(EmployeeNameTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refreshButton)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        advancedPayrollAddPanelLayout.setVerticalGroup(
            advancedPayrollAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(advancedPayrollAddPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(advancedPayrollAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(advancedPayrollAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(EmployeeNameTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(EmployeeIDTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(advancedPayrollAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(PayrollIDTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(advancedPayrollAddPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AdvanceAmountTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bodyPanal.add(advancedPayrollAddPanel, java.awt.BorderLayout.PAGE_START);

        PayrollTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PayrollTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Payroll Advanced ID", "Date", "Employee Name", "Advanced Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        PayrollTable.getTableHeader().setReorderingAllowed(false);
        PayrollTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PayrollTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(PayrollTable);

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 994, Short.MAX_VALUE)
                .addGap(41, 41, 41))
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        bodyPanal.add(tablePanel, java.awt.BorderLayout.CENTER);

        BackToDashboardPanel.setBackground(new java.awt.Color(153, 153, 153));
        BackToDashboardPanel.setPreferredSize(new java.awt.Dimension(894, 50));

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
                .addComponent(BackToDashboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1032, Short.MAX_VALUE))
        );
        BackToDashboardPanelLayout.setVerticalGroup(
            BackToDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BackToDashboardPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BackToDashboardButton)
                .addContainerGap())
        );

        bodyPanal.add(BackToDashboardPanel, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(bodyPanal, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BackToDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToDashboardButtonActionPerformed

        this.dispose();

    }//GEN-LAST:event_BackToDashboardButtonActionPerformed

    private void EmployeeIDTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeIDTextfieldActionPerformed
        EmployeeNameTextfield.grabFocus();
    }//GEN-LAST:event_EmployeeIDTextfieldActionPerformed

    private void PayrollIDTextfieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PayrollIDTextfieldFocusGained
        //check and set clear the current textfield to enter data
        if (PayrollIDTextfield.getText().equals("Enter Payroll ID")) {
            PayrollIDTextfield.setText("");
            PayrollIDTextfield.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_PayrollIDTextfieldFocusGained

    private void PayrollIDTextfieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PayrollIDTextfieldFocusLost
        //set back the placeholder
        if (PayrollIDTextfield.getText().isEmpty()) {
            PayrollIDTextfield.setText("Enter Payroll ID");
            PayrollIDTextfield.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_PayrollIDTextfieldFocusLost

    private void EmployeeIDTextfieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_EmployeeIDTextfieldFocusGained
        //check and set clear the current textfield to enter data
        if (EmployeeIDTextfield.getText().equals("Enter Employee ID")) {
            EmployeeIDTextfield.setText("");
            EmployeeIDTextfield.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_EmployeeIDTextfieldFocusGained

    private void EmployeeIDTextfieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_EmployeeIDTextfieldFocusLost
        //set back the placeholder
        if (EmployeeIDTextfield.getText().isEmpty()) {
            EmployeeIDTextfield.setText("Enter Employee ID");
            EmployeeIDTextfield.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_EmployeeIDTextfieldFocusLost

    private void AdvanceAmountTextfieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AdvanceAmountTextfieldFocusGained
        //check and set clear the current textfield to enter data
        if (AdvanceAmountTextfield.getText().equals("Amount")) {
            AdvanceAmountTextfield.setText("");
            AdvanceAmountTextfield.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_AdvanceAmountTextfieldFocusGained

    private void AdvanceAmountTextfieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AdvanceAmountTextfieldFocusLost
        //set back the placeholder
        if (AdvanceAmountTextfield.getText().isEmpty()) {
            AdvanceAmountTextfield.setText("Amount");
            AdvanceAmountTextfield.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_AdvanceAmountTextfieldFocusLost

    private void EmployeeIDTextfieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EmployeeIDTextfieldKeyReleased
        // store a employee id & Trim to remove space
        String empId = EmployeeIDTextfield.getText();

        try {

            // check id field is empty & clear textfield    
            if (empId.isEmpty() || empId.equals("Enter Employee ID")) {

                EmployeeNameTextfield.setText("");
                return;

            }
            // execute database query
            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `employee` WHERE `employee_id` = ' " + empId + " ' ");

            if (resultSet.next()) {

                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String fullName = firstName + " " + lastName;

                EmployeeNameTextfield.setText(fullName);

            } else {

                // clear employee name text field
                EmployeeNameTextfield.setText("");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }//GEN-LAST:event_EmployeeIDTextfieldKeyReleased

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        reset(); //refresh the frame
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void PayrollIDTextfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PayrollIDTextfieldKeyPressed
        if (evt.getKeyCode() == 10) {
            EmployeeIDTextfield.grabFocus();
        }
    }//GEN-LAST:event_PayrollIDTextfieldKeyPressed

    private void EmployeeNameTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeNameTextfieldActionPerformed
        AdvanceAmountTextfield.grabFocus();
    }//GEN-LAST:event_EmployeeNameTextfieldActionPerformed

    private void PayrollTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PayrollTableMouseClicked
        // Get the index of the selected row in the table
        int row = PayrollTable.getSelectedRow();

        // Display the data of the selected row in the text fields
        EmployeeNameTextfield.setText(String.valueOf(PayrollTable.getValueAt(row, 2)));
        AdvanceAmountTextfield.setText(String.valueOf(PayrollTable.getValueAt(row, 3)));

        // Disable the Add button while deleting
        kButton1.setEnabled(false);

        // Check if the user double-clicked on a row
        if (evt.getClickCount() == 2) {

            String selectedAdvancedID = String.valueOf(PayrollTable.getValueAt(row, 0));

            // Asking to confirm before the deletion
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this Employee Advanced payment?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            //If user confirms the deletion
            if (confirm == JOptionPane.YES_OPTION) {

                try {

                    //Delete from database
                    MySql.executeUpdate("DELETE FROM `advanced_payroll` WHERE `ad_pay_id`='" + selectedAdvancedID + "' ");

                    // Renumber remaining rows
                    MySql.executeUpdate("SET @row_number = 0");
                    MySql.executeUpdate("UPDATE `advanced_payroll` "
                            + "SET `ad_pay_id` = (@row_number := @row_number + 1) "
                            + "ORDER BY `ad_pay_id`");

                    // Reset AUTO_INCREMENT value
                    MySql.executeUpdate("ALTER TABLE `advanced_payroll` AUTO_INCREMENT = 1");

                    // Reload the jtable table 
                    loadAdvancedPay();
                    reset(); // Clear the text field for the next entry

                    //Success message
                    JOptionPane.showMessageDialog(this, "Employee Advanced Payment Deleted Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {

                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error occurred while deleting the Advanced Payment", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        }


    }//GEN-LAST:event_PayrollTableMouseClicked

    //Add Button Function
    private void kButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton1ActionPerformed

        try {

            String payrollid = PayrollIDTextfield.getText();
            String employeeid = EmployeeIDTextfield.getText();
            String employeename = EmployeeNameTextfield.getText();
            String amount = AdvanceAmountTextfield.getText();

            // Validate required fields
            if (payrollid.isEmpty() || payrollid.equals("Enter Payroll ID")) {
                
                JOptionPane.showMessageDialog(this, "Please enter Payroll ID", "Warning", JOptionPane.WARNING_MESSAGE);
                
            } else if (employeeid.isEmpty() || employeeid.equals("Enter Employee ID")) {
                
                JOptionPane.showMessageDialog(this, "Please enter Employee ID", "Warning", JOptionPane.WARNING_MESSAGE);
                
            } else if (amount.isEmpty() || amount.equals("Amount")) {
                
                JOptionPane.showMessageDialog(this, "Please enter Advanced Amount", "Warning", JOptionPane.WARNING_MESSAGE);
                
            } else {
                
                try {
                    
                    double parsedAmount = Double.parseDouble(amount);
                    
                    if (parsedAmount <= 0) {
                        
                        JOptionPane.showMessageDialog(this, "Amount must be a positive value", "Warning", JOptionPane.WARNING_MESSAGE);
                        
                        return;
                        
                    }

                    // Reset AUTO_INCREMENT value (optional)
                    MySql.executeUpdate("ALTER TABLE `advanced_payroll` AUTO_INCREMENT = 1");

                    // Get the current date
                    LocalDate currentDate = LocalDate.now()
                            ;
                    String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    
                    ResultSet paymentSearch = MySql.executeSearch("SELECT * FROM `employee_payrolls` WHERE `employee_employee_id` = '" + employeeid + "'");

                    if (paymentSearch.next()) {
                        
                        String payID = paymentSearch.getString("payroll_id");
                        
                        String payableSalary = paymentSearch.getString("payable_salary");
                        
                        ResultSet advancedResult = MySql.executeSearch("SELECT * FROM `advanced_payroll` WHERE `employee_employee_id` = '" + employeeid + "' AND `advance_status_or_note` = 'Not Paid' ");

                        if (advancedResult.next()) {
                            
                            // Advance already exists
                            String advancedPrice = advancedResult.getString("advanced_price");
                            
                            JOptionPane.showMessageDialog(this, employeename + " already received an advance of " + advancedPrice, "Warning", JOptionPane.WARNING_MESSAGE);
                            
                        } else {

                            int confirm = JOptionPane.showConfirmDialog(this,
                                    
                                    employeename + "'s Salary is " + payableSalary + ". Do you want to proceed with the advance?",
                                    "Confirm Advance",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);

                            if (confirm == JOptionPane.YES_OPTION) {

                                String insertQuery = "INSERT INTO `advanced_payroll` (`employee_employee_id`, `employee_name`, `date`, `advanced_price`, `employee_payrolls_payroll_id`, `advance_status_or_note`) "
                                        + "VALUES ('" + employeeid + "', '" + employeename + "', '" + formattedDate + "', '" + amount + "', '" + payID + "', 'Not Paid')";
                                MySql.executeUpdate(insertQuery);


                                loadAdvancedPay();
                                
                                reset();
                                
                                PayrollIDTextfield.grabFocus();

                                JOptionPane.showMessageDialog(this, "Advanced Payment Added Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }

                    } else {
                        
                        JOptionPane.showMessageDialog(this, "No payroll record found for Employee ID: " + employeeid, "Error", JOptionPane.ERROR_MESSAGE);
                        
                    }

                } catch (NumberFormatException ex) {
                    
                    JOptionPane.showMessageDialog(this, "Amount must be a valid numeric value", "Warning", JOptionPane.WARNING_MESSAGE);
                    
                }
            }
        } catch (Exception e) {
            
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(this, "Error occurred while adding the Advanced Payment", "Error", JOptionPane.ERROR_MESSAGE);
                        
        }


    }//GEN-LAST:event_kButton1ActionPerformed

    private void EmployeeIDTextfieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EmployeeIDTextfieldKeyTyped

        String empID = EmployeeIDTextfield.getText();

        if (empID.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please enter employee ID", "Empty text field", JOptionPane.WARNING_MESSAGE);

        } else {

            try {

                ResultSet rs = MySql.executeSearch("SELECT * FROM `employee` WHERE `employee_id` = '" + empID + "' ");

                if (rs.next()) {

                    String fullname = rs.getString("first_name") + " " + rs.getString("last_name");

                    EmployeeNameTextfield.setText(fullname);

                } else {

                    JOptionPane.showMessageDialog(this, "No date found", "Data Missing", JOptionPane.WARNING_MESSAGE);

                }

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

    }//GEN-LAST:event_EmployeeIDTextfieldKeyTyped

    private void PayrollIDTextfieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PayrollIDTextfieldKeyTyped

        String payID = PayrollIDTextfield.getText();

        if (payID.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please enter payroll id", "Empty text field", JOptionPane.WARNING_MESSAGE);

        } else {

            try {

                ResultSet rs = MySql.executeSearch("SELECT * FROM `payroll_int_hr_details` WHERE `payroll_id` = '" + payID + "' ");

                if (rs.next()) {

                    EmployeeIDTextfield.setText(rs.getString("employee_employee_id"));

                } else {

                    JOptionPane.showMessageDialog(this, "No date found", "Data Missing", JOptionPane.WARNING_MESSAGE);

                }

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

    }//GEN-LAST:event_PayrollIDTextfieldKeyTyped

    private void AdvanceAmountTextfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AdvanceAmountTextfieldKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            kButton1.doClick();

        }

    }//GEN-LAST:event_AdvanceAmountTextfieldKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatMacLightLaf.setup();

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AdvancedPayrollIntergration dialog = new AdvancedPayrollIntergration(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AdvanceAmountTextfield;
    private javax.swing.JButton BackToDashboardButton;
    private javax.swing.JPanel BackToDashboardPanel;
    private javax.swing.JTextField EmployeeIDTextfield;
    private javax.swing.JTextField EmployeeNameTextfield;
    private javax.swing.JTextField PayrollIDTextfield;
    private javax.swing.JTable PayrollTable;
    private javax.swing.JPanel advancedPayrollAddPanel;
    private javax.swing.JPanel bodyPanal;
    private javax.swing.JPanel headerPanal;
    private javax.swing.JLabel headingText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private com.k33ptoo.components.KButton kButton1;
    private javax.swing.JButton refreshButton;
    private javax.swing.JPanel tablePanel;
    // End of variables declaration//GEN-END:variables

    // Function to reset the input fields and table selection
    private void reset() {

        PayrollIDTextfield.setText("");
        EmployeeIDTextfield.setText("");
        EmployeeNameTextfield.setText("");
        AdvanceAmountTextfield.setText("");
        kButton1.setEnabled(true);
        PayrollTable.clearSelection();

        //Re-add the placeholder to refreshed textfield
        addPlaceholder();
    }
}
