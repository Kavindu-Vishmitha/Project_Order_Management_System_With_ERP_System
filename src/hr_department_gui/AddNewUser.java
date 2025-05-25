package hr_department_gui;

import java.sql.ResultSet;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MySql;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class AddNewUser extends javax.swing.JFrame {

    private JFrame currentWindow = null; // Keep track of the currently open window

//   Hashmap for Department and position 
    private static HashMap<String, String> departmentMap = new HashMap<>();
    private static HashMap<String, String> positionMap = new HashMap<>();

    public AddNewUser() {

        initComponents();
        addPlaceholder();   // call addPlaceholder method
        loadDearpement();   // call loadDearpement method
        loadPosition();     // call loadPosition method
        loadEmployee();     // call loadEmployee method
        loadDearpement2();  // call loadDearpement2 method

    }

    private void openWindow(String windowType) {
        if (currentWindow != null) {
            currentWindow.dispose(); // Close the previous window if it's already open
        }

        switch (windowType) {

            case "AddDepartment":
                currentWindow = new AddDepartment();
                break;
            case "EmployeePosition":
                currentWindow = new EmployeePosition();
                break;

        }

        currentWindow.setVisible(true);
    }

    //    DefaultTableModel Goloble Variable
    DefaultTableModel model;

    //      Placeholders add method
    private void addPlaceholder() {

        // employeeIdTextField placeholder & color
        employeeIDTextField.setText("Enter Employee ID");
        employeeIDTextField.setForeground(Color.GRAY);

        // userNameTextField placeholder & color
        userNameTextField.setText("Enter UserName");
        userNameTextField.setForeground(Color.GRAY);

        // PasswordField placeholder & color
        passwordTextField.setText("Enter Password");
        passwordTextField.setForeground(Color.GRAY);

        // employeeNameTextField placeholder & color
        employeeNameTextField.setText("Enter Employee Name");
        employeeNameTextField.setForeground(Color.GRAY);

    }

//      Loading Dearpemnt Names For JcomboBox
    private void loadDearpement() {

        try {

            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `department`");     // Execute a SQL query to retrieve all records from the 'department' table

            Vector<String> vector = new Vector<>();  // Create a Vector to store department names (used to populate the JComboBox)

            vector.add("select");  // Add a default "select" option as the first item in the combo box

            // Iterate through the ResultSet to fetch department details
            while (resultSet.next()) {

                vector.add(resultSet.getString("department_name")); // Add the department name to the Vector (for display in the combo box)

                departmentMap.put(resultSet.getString("department_name"), resultSet.getString("department_id")); // Store the department name and ID in a Map for later use
            }

            // Create a DefaultComboBoxModel using the Vector of department names
            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);

            employeeDepartmentComboBox.setModel(model);     // Set the created model to the JComboBox to display the department names

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

// Method to load department data into a JComboBox
    private void loadDearpement2() {

        try {

            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `department`"); // Execute a SQL query to get all departments from the database

            Vector<String> vector = new Vector<>(); // Create a Vector to store department names

            vector.add("select"); // Add a default "select" option at the top of the combo box

//      Loop through the ResultSet to get department details
            while (resultSet.next()) {

                vector.add(resultSet.getString("department_name"));  // Add department names to the vector

                departmentMap.put(resultSet.getString("department_name"), resultSet.getString("department_id"));  // Map the department name to its ID for future use

            }

//      Create a ComboBox model with the department names
            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);

            departmentComboBox.setModel(model);  // Set the created model to the JComboBox

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

//  Method to load employee positions into a JComboBox
    public void loadPosition() {

        try {

            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `employee_position` "); // Execute a SQL query to fetch all employee positions

            Vector<String> vector = new Vector<>();  // Create a Vector to store position names for the combo box

            vector.add("Select"); // Add a default "Select" option as the first item in the combo box

//      Loop through the ResultSet to retrieve position data
            while (resultSet.next()) {

                vector.add(resultSet.getString("position_name"));// Add position names to the Vector

                positionMap.put(resultSet.getString("position_name"), resultSet.getString("employee_position_id"));// Map position names to their IDs for future reference

            }

//      Create a DefaultComboBoxModel using the Vector of position names
            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);

            positionComboBox.setModel(model); // Set the created model to the JComboBox

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

// Method to load employee data into a JTable
    public void loadEmployee() {

        try {

//      Execute a SQL query to fetch employee details, joining related tables
            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `employee_user` "
                    + "INNER JOIN `department` ON `department`.`department_id` = `employee_user`.`department_department_id` "
                    + "INNER JOIN `employee_position` ON `employee_position`.`employee_position_id` = `employee_user`.`employee_position_employee_position_id` ");

            model = (DefaultTableModel) NewuserTable.getModel(); // Get the DefaultTableModel from the JTable

            model.setRowCount(0);

            while (resultSet.next()) {

                Vector<String> vector = new Vector<>(); // Create a Vector to store a single row of data

                vector.add(resultSet.getString("employee_employee_id"));            // Employee ID
                vector.add(resultSet.getString("employee_name"));
                vector.add(resultSet.getString("user_name"));         // Username
                vector.add(resultSet.getString("department.department_name"));      // Department Name
                vector.add(resultSet.getString("employee_position.position_name")); // Position Name
                vector.add(resultSet.getString("user_password"));     // User Password

                model.addRow(vector);// Add the row to the table model

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        headerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        bodyPanel = new javax.swing.JPanel();
        BackToDashboardPanel = new javax.swing.JPanel();
        BackToDashboardButton = new javax.swing.JButton();
        PrintButtton = new com.k33ptoo.components.KButton();
        jPanel1 = new javax.swing.JPanel();
        detailsPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        employeeIDTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        employeeNameTextField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        userNameTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        employeeDepartmentComboBox = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        positionComboBox = new javax.swing.JComboBox<>();
        kButton1 = new com.k33ptoo.components.KButton();
        kButton2 = new com.k33ptoo.components.KButton();
        passwordTextField = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        buttonPanel = new javax.swing.JPanel();
        addButton = new com.k33ptoo.components.KButton();
        updateButton = new com.k33ptoo.components.KButton();
        deleteButton = new com.k33ptoo.components.KButton();
        refreshButton = new javax.swing.JButton();
        tablePanel = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        departmentComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        NewuserTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        headerPanel.setBackground(new java.awt.Color(153, 153, 153));
        headerPanel.setPreferredSize(new java.awt.Dimension(942, 45));
        headerPanel.setLayout(new java.awt.BorderLayout());

        jLabel1.setBackground(new java.awt.Color(153, 153, 153));
        jLabel1.setFont(new java.awt.Font("Audiowide", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add New User");
        jLabel1.setPreferredSize(new java.awt.Dimension(190, 50));
        headerPanel.add(jLabel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(headerPanel, java.awt.BorderLayout.PAGE_START);

        bodyPanel.setLayout(new java.awt.BorderLayout());

        BackToDashboardPanel.setBackground(new java.awt.Color(153, 153, 153));
        BackToDashboardPanel.setPreferredSize(new java.awt.Dimension(1007, 50));

        BackToDashboardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/back-arrow.png"))); // NOI18N
        BackToDashboardButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BackToDashboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackToDashboardButtonActionPerformed(evt);
            }
        });

        PrintButtton.setText("Print Report");
        PrintButtton.setToolTipText("Click Here to Delete Current Selected Employee Data Row!");
        PrintButtton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        PrintButtton.setkEndColor(new java.awt.Color(0, 204, 204));
        PrintButtton.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        PrintButtton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        PrintButtton.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        PrintButtton.setkPressedColor(new java.awt.Color(0, 102, 153));
        PrintButtton.setkSelectedColor(new java.awt.Color(0, 102, 153));
        PrintButtton.setkStartColor(new java.awt.Color(0, 102, 153));
        PrintButtton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintButttonActionPerformed(evt);
            }
        });
        PrintButtton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PrintButttonKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout BackToDashboardPanelLayout = new javax.swing.GroupLayout(BackToDashboardPanel);
        BackToDashboardPanel.setLayout(BackToDashboardPanelLayout);
        BackToDashboardPanelLayout.setHorizontalGroup(
            BackToDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackToDashboardPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(BackToDashboardButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 826, Short.MAX_VALUE)
                .addComponent(PrintButtton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        BackToDashboardPanelLayout.setVerticalGroup(
            BackToDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BackToDashboardPanelLayout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addGroup(BackToDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PrintButtton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BackToDashboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        bodyPanel.add(BackToDashboardPanel, java.awt.BorderLayout.PAGE_END);

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel8.setText("Employee ID");

        employeeIDTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                employeeIDTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                employeeIDTextFieldFocusLost(evt);
            }
        });
        employeeIDTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                employeeIDTextFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                employeeIDTextFieldKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel9.setText("Department");

        employeeNameTextField.setEditable(false);
        employeeNameTextField.setToolTipText("");
        employeeNameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                employeeNameTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                employeeNameTextFieldFocusLost(evt);
            }
        });
        employeeNameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                employeeNameTextFieldKeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel10.setText("User Name");

        userNameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                userNameTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                userNameTextFieldFocusLost(evt);
            }
        });
        userNameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                userNameTextFieldKeyPressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel11.setText("Password");

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel12.setText("Employee Name");

        employeeDepartmentComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        employeeDepartmentComboBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                employeeDepartmentComboBoxKeyPressed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel13.setText("Position");

        positionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        kButton1.setText("ADD");
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

        kButton2.setText("ADD");
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

        passwordTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                passwordTextFieldFocusLost(evt);
            }
        });
        passwordTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordTextFieldKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout detailsPanelLayout = new javax.swing.GroupLayout(detailsPanel);
        detailsPanel.setLayout(detailsPanelLayout);
        detailsPanelLayout.setHorizontalGroup(
            detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detailsPanelLayout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(detailsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(12, 12, 12)
                        .addComponent(employeeIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detailsPanelLayout.createSequentialGroup()
                        .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userNameTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwordTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(59, 59, 59)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(employeeNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(detailsPanelLayout.createSequentialGroup()
                        .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(employeeDepartmentComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 228, Short.MAX_VALUE)
                            .addComponent(positionComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        detailsPanelLayout.setVerticalGroup(
            detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(employeeIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(employeeNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(userNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(employeeDepartmentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(positionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        addButton.setText("Add");
        addButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addButton.setkEndColor(new java.awt.Color(0, 204, 204));
        addButton.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        addButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        addButton.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        addButton.setkPressedColor(new java.awt.Color(0, 102, 153));
        addButton.setkSelectedColor(new java.awt.Color(0, 102, 153));
        addButton.setkStartColor(new java.awt.Color(0, 102, 153));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

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

        deleteButton.setText("Delete");
        deleteButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        deleteButton.setkEndColor(new java.awt.Color(0, 204, 204));
        deleteButton.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        deleteButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        deleteButton.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        deleteButton.setkPressedColor(new java.awt.Color(0, 102, 153));
        deleteButton.setkSelectedColor(new java.awt.Color(0, 102, 153));
        deleteButton.setkStartColor(new java.awt.Color(0, 102, 153));
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/refresh.png"))); // NOI18N
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(refreshButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel2.setText("Department");

        departmentComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        departmentComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                departmentComboBoxItemStateChanged(evt);
            }
        });

        NewuserTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Employee Name", "User Name", "Department", "Position", "Password"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        NewuserTable.getTableHeader().setReorderingAllowed(false);
        NewuserTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NewuserTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(NewuserTable);

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tablePanelLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(departmentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(tablePanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1049, Short.MAX_VALUE)
                        .addGap(25, 25, 25))))
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(departmentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(detailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(detailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bodyPanel.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(bodyPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BackToDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToDashboardButtonActionPerformed

        this.dispose();

    }//GEN-LAST:event_BackToDashboardButtonActionPerformed


    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed

        //    Add Button Start
        try {

            // Get values entered by the user in the text fields and combo boxes
            String employeeId = employeeIDTextField.getText();
            String employeeName = employeeNameTextField.getText();
            String userName = userNameTextField.getText();
            String password = passwordTextField.getText();
            String department = String.valueOf(employeeDepartmentComboBox.getSelectedItem());
            String position = String.valueOf(positionComboBox.getSelectedItem());

            // Check if the employee ID is empty
            if (employeeId.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please Enter Employee ID", "Warning", JOptionPane.WARNING_MESSAGE);
                employeeIDTextField.grabFocus();

            } // Check if the employee name is empty
            else if (employeeName.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please Enter Employee ID to load Employee Name", "Warning", JOptionPane.WARNING_MESSAGE);
                employeeIDTextField.grabFocus();

            } // Check if the username is empty
            else if (userName.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please Enter User Name", "Warning", JOptionPane.WARNING_MESSAGE);
                userNameTextField.grabFocus();

            } // Check if the password is empty
            else if (password.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please Enter Password", "Warning", JOptionPane.WARNING_MESSAGE);
                passwordTextField.grabFocus();

            } // Validate the password using a regular expression for security (at least 8 characters, 1 letter, 1 number, 1 special character)
            else if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$!%*?&])[A-Za-z\\d@#$!%*?&]{8,}$")) {

                JOptionPane.showMessageDialog(this, """
                                                Password must contain at least 8 characters, including:
                                                - one uppercase letter
                                                - one lowercase letter
                                                - one number
                                                - one special character (e.g., @, #, $, %, ^, &, +, =)""", "Warning", JOptionPane.WARNING_MESSAGE);
                passwordTextField.grabFocus();

            } // Check if the department is empty
            else if (department.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please Select Department", "Warning", JOptionPane.WARNING_MESSAGE);
                employeeDepartmentComboBox.grabFocus();

            } // Check if the position is empty
            else if (position.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please Select Position", "Warning", JOptionPane.WARNING_MESSAGE);
                positionComboBox.grabFocus();

            } else {

                ResultSet resultSet = MySql.executeSearch("SELECT * FROM `employee_user` WHERE `employee_employee_id` = '" + employeeId + "' AND `employee_name` = '" + employeeName + "' ");

                if (resultSet.next()) {

                    JOptionPane.showMessageDialog(this, "Already exist user account with this name and id", "Warning", JOptionPane.ERROR_MESSAGE);

                } else {

                    // If all fields are valid, execute the query to insert a new employee user into the database
                    MySql.executeUpdate(" INSERT INTO `employee_user` (`employee_employee_id`,`employee_name`,`user_name`,`user_password`,`department_department_id`,`employee_position_employee_position_id`) "
                            + "VALUES ('" + employeeId + "' , '" + employeeName + "' ,'" + userName + "','" + password + "','" + departmentMap.get(department) + "','" + positionMap.get(position) + "') ");

                    // Reload the employee list to reflect the newly added employee
                    JOptionPane.showMessageDialog(this, employeeName + "" + "'s user account create successfully", "Account create success", JOptionPane.INFORMATION_MESSAGE);

                    loadEmployee();

                    // Clear the form fields after successful insertion
                    reset();

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

        }
        //    Add Button End

    }//GEN-LAST:event_addButtonActionPerformed


    private void employeeIDTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_employeeIDTextFieldKeyReleased

        String employee_ID = employeeIDTextField.getText();

        try {

            if (employee_ID.isEmpty()) {

                employeeNameTextField.setText("");

            } else {

                ResultSet resultSet = MySql.executeSearch("SELECT * FROM `employee` LEFT JOIN `department` ON"
                        + " `department`.`department_id` = `employee`.`department_department_id` LEFT JOIN `employee_position` "
                        + "ON `employee_position`.`employee_position_id` = `employee`.`employee_position_employee_position_id` "
                        + "WHERE `employee_id` = '" + employee_ID + "' ");

                if (resultSet.next()) {

                    String fullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                    System.out.println(fullName);

                    String Department = resultSet.getString("department.department_name");
                    String Position = resultSet.getString("employee_position.position_name");

                    employeeDepartmentComboBox.setSelectedItem(Department);
                    System.out.println(Department);
                    positionComboBox.setSelectedItem(Position);

                    employeeNameTextField.setText(fullName);

                } else {

                    employeeNameTextField.setText("");

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }//GEN-LAST:event_employeeIDTextFieldKeyReleased

    private void departmentComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_departmentComboBoxItemStateChanged

//        ComboBox Select Change
        String department = String.valueOf(departmentComboBox.getSelectedItem());

        try {

//                    Retrieve departement ID From the Map
            String departmentID = departmentMap.get(department);

//                    Query
            String query = " SELECT * FROM `employee_user` "
                    + "INNER JOIN `employee` ON `employee`.`employee_id` = `employee_user`.`employee_employee_id` "
                    + "INNER JOIN `department` ON `department`.`department_id` = `employee`.`department_department_id` "
                    + "INNER JOIN `employee_position` ON `employee_position`.`employee_position_id` = `employee`.`employee_position_employee_position_id` ";

            if (department.equals("select")) {

                query += "";

            } else if (department.equals(department)) {

                String departmentid = String.valueOf(departmentComboBox.getSelectedIndex());
                query += " WHERE `employee_user`.`department_department_id` = '" + departmentid + "' ";

            }

            ResultSet resultSet = MySql.executeSearch(query);

//                    Clear and Update table
            model = (DefaultTableModel) NewuserTable.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {

                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("employee.employee_id"));
                vector.add(resultSet.getString("employee.first_name"));
                vector.add(resultSet.getString("employee.last_name"));
                vector.add(resultSet.getString("employee_user.user_name"));
                vector.add(resultSet.getString("department.department_name"));
                vector.add(resultSet.getString("employee_position.position_name"));
                vector.add(resultSet.getString("employee_user.user_password"));

                model.addRow(vector);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }//GEN-LAST:event_departmentComboBoxItemStateChanged

    private void NewuserTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NewuserTableMouseClicked

        // Check if the click is a double-click 
        if (evt.getClickCount() == 2) {

            int row = NewuserTable.getSelectedRow(); // Get the selected row from the table

            // Retrieve the Employee ID from the selected row and set it in the employee ID text field
            String empID = String.valueOf(NewuserTable.getValueAt(row, 0));
            employeeIDTextField.setText(empID);
            employeeIDTextField.setEditable(false);// Make Employee ID field non-editable

            String employeeName = String.valueOf(NewuserTable.getValueAt(row, 1));
            employeeNameTextField.setText(employeeName);

            // Retrieve the Username from the selected row and set it in the username text field
            String userName = String.valueOf(NewuserTable.getValueAt(row, 2));
            userNameTextField.setText(userName);

            // Retrieve the department name and set it in the department combo box
            String department = String.valueOf(NewuserTable.getValueAt(row, 3));
            employeeDepartmentComboBox.setSelectedItem(department);

            // Retrieve the position name and set it in the position combo box
            String position = String.valueOf(NewuserTable.getValueAt(row, 4));
            positionComboBox.setSelectedItem(position);

            // Retrieve the password from the selected row and set it in the password field
            String password = String.valueOf(NewuserTable.getValueAt(row, 5));
            passwordTextField.setText(password);

        }

    }//GEN-LAST:event_NewuserTableMouseClicked

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed

        reset();

    }//GEN-LAST:event_refreshButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed

        // Get the selected row from the table
        int row = NewuserTable.getSelectedRow();

        // Check if a row is selected
        if (row == -1) {

            // Show a warning message if no employee is selected
            JOptionPane.showMessageDialog(this, "Please Select a Employee", "Warning", JOptionPane.WARNING_MESSAGE);

        } else {

            try {

                int deleteConfirm = JOptionPane.showConfirmDialog(this, "Do you really want to delete this usea account", "Delete Warning", JOptionPane.WARNING_MESSAGE);

                if (deleteConfirm == JOptionPane.YES_NO_OPTION) {

                    // Get the Employee ID from the selected row
                    String ID = String.valueOf(NewuserTable.getValueAt(row, 0));

                    // Execute the DELETE query to remove the employee from the database
                    MySql.executeUpdate("DELETE FROM `employee_user` WHERE `employee_employee_id` = '" + ID + "' ");

                    // Reload the employee list to reflect the changes
                    loadEmployee();

                    // Reset the form fields for the next operation
                    reset();

                    // Show a success message after the deletion
                    JOptionPane.showMessageDialog(this, "Successfully Delete Employee", "Information", JOptionPane.INFORMATION_MESSAGE);

                }

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

    }//GEN-LAST:event_deleteButtonActionPerformed

    private void kButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton2ActionPerformed

        openWindow("AddDepartment");

    }//GEN-LAST:event_kButton2ActionPerformed

    private void kButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton1ActionPerformed

        openWindow("EmployeePosition");

    }//GEN-LAST:event_kButton1ActionPerformed

    private void employeeIDTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_employeeIDTextFieldFocusGained

        // Checks and set clear the current employeeIDTextField to enter data to it
        if (employeeIDTextField.getText().equals("Enter Employee ID")) {

            employeeIDTextField.setText("");
            employeeIDTextField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_employeeIDTextFieldFocusGained

    private void employeeIDTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_employeeIDTextFieldFocusLost

        // Sets back the placeholder of employeeIDTextField
        if (employeeIDTextField.getText().isEmpty()) {

            employeeIDTextField.setText("Enter Employee ID");
            employeeIDTextField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_employeeIDTextFieldFocusLost

    private void employeeIDTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_employeeIDTextFieldKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            userNameTextField.requestFocus();

        }

    }//GEN-LAST:event_employeeIDTextFieldKeyPressed

    private void userNameTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_userNameTextFieldFocusGained

        // Checks and set clear the current userNameTextField to enter data to it
        if (userNameTextField.getText().equals("Enter UserName")) {

            userNameTextField.setText("");
            userNameTextField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_userNameTextFieldFocusGained

    private void userNameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_userNameTextFieldFocusLost

        // Sets back the placeholder of userNameTextField
        if (userNameTextField.getText().isEmpty()) {

            userNameTextField.setText("Enter UserName");
            userNameTextField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_userNameTextFieldFocusLost

    private void userNameTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_userNameTextFieldKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            passwordTextField.requestFocus();

        }

    }//GEN-LAST:event_userNameTextFieldKeyPressed

    private void employeeNameTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_employeeNameTextFieldFocusGained

        // Checks and set clear the current employeeNameTextField to enter data to it
        if (employeeNameTextField.getText().equals("Employee Name")) {

            employeeNameTextField.setText("");
            employeeNameTextField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_employeeNameTextFieldFocusGained

    private void employeeNameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_employeeNameTextFieldFocusLost

        // Sets back the placeholder of employeeNameTextField
        if (employeeNameTextField.getText().isEmpty()) {

            employeeNameTextField.setText("Enter Employee Name");
            employeeNameTextField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_employeeNameTextFieldFocusLost

    private void employeeNameTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_employeeNameTextFieldKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            employeeDepartmentComboBox.requestFocus();

        }

    }//GEN-LAST:event_employeeNameTextFieldKeyPressed

    private void employeeDepartmentComboBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_employeeDepartmentComboBoxKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            positionComboBox.requestFocus();

        }

    }//GEN-LAST:event_employeeDepartmentComboBoxKeyPressed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed

        addButton.setVisible(true);

        try {

            // Get the values entered by the user in the form fields
            String employeeId = employeeIDTextField.getText();
            String employeeName = employeeNameTextField.getText();
            String userName = userNameTextField.getText();
            String password = passwordTextField.getText();
            String department = String.valueOf(employeeDepartmentComboBox.getSelectedItem());
            String position = String.valueOf(positionComboBox.getSelectedItem());

            // Validate the form fields to ensure that no required fields are empty
            if (employeeId.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please Enter Your Employee ID", "Warning", JOptionPane.WARNING_MESSAGE);
                employeeIDTextField.grabFocus();

            } else if (employeeName.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please Enter Your Employee ID to load Employee Name", "Warning", JOptionPane.WARNING_MESSAGE);
                employeeNameTextField.grabFocus();

            } else if (userName.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please Enter User Name", "Warning", JOptionPane.WARNING_MESSAGE);
                userNameTextField.grabFocus();

            } else if (password.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please Enter Password", "Warning", JOptionPane.WARNING_MESSAGE);
                passwordTextField.grabFocus();

            } else if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$!%*?&])[A-Za-z\\d@#$!%*?&]{8,}$")) {

                JOptionPane.showMessageDialog(this, """
                                                Password must contain at least 8 characters, including:
                                                - one uppercase letter
                                                - one lowercase letter
                                                - one number
                                                - one special character (e.g., @, #, $, %, ^, &, +, =)""", "Warning", JOptionPane.WARNING_MESSAGE);
                passwordTextField.grabFocus();

            } else if (department.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please Select Department", "Warning", JOptionPane.WARNING_MESSAGE);
                employeeDepartmentComboBox.grabFocus();

            } else if (position.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please Select Position", "Warning", JOptionPane.WARNING_MESSAGE);
                positionComboBox.grabFocus();

            } else {

                // Fetch current data from the database for comparison
                String query = "SELECT * FROM employee_user WHERE employee_employee_id = '" + employeeId + "'";
                ResultSet rs = MySql.executeSearch(query);

                if (rs.next()) {

                    String originalUserName = rs.getString("user_name");
                    String originalPassword = rs.getString("user_password");
                    String originalDepartment = rs.getString("department_department_id");
                    String originalPosition = rs.getString("employee_position_employee_position_id");

                    // Check if there are any changes
                    boolean hasChanges = false;
                    StringBuilder updateQuery = new StringBuilder("UPDATE `employee_user` SET ");

                    if (!userName.equals(originalUserName)) {
                        updateQuery.append("`user_name` = '").append(userName).append("', ");
                        hasChanges = true;
                    }

                    if (!password.equals(originalPassword)) {
                        updateQuery.append("`user_password` = '").append(password).append("', ");
                        hasChanges = true;
                    }

                    if (!departmentMap.get(department).equals(originalDepartment)) {
                        updateQuery.append("`department_department_id` = '").append(departmentMap.get(department)).append("', ");
                        hasChanges = true;
                    }

                    if (!positionMap.get(position).equals(originalPosition)) {
                        updateQuery.append("`employee_position_employee_position_id` = '").append(positionMap.get(position)).append("', ");
                        hasChanges = true;
                    }

                    // Execute update if there are changes
                    if (hasChanges) {
                        // Remove the trailing comma and space
                        updateQuery.setLength(updateQuery.length() - 2);
                        updateQuery.append(" WHERE `employee_employee_id` = '").append(employeeId).append("'");

                        MySql.executeUpdate(updateQuery.toString());
                        JOptionPane.showMessageDialog(this, "Employee data updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                        // Reload the employee list to reflect the updated data
                        loadEmployee();

                        // Reset the form fields for the next operation
                        reset();

                    } else {
                        JOptionPane.showMessageDialog(this, "No changes detected.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_updateButtonActionPerformed

    private void passwordTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordTextFieldFocusGained

        // Checks and set clear the current employeeNameTextField to enter data to it
        if (passwordTextField.getText().equals("Enter Password")) {

            passwordTextField.setText("");
            passwordTextField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_passwordTextFieldFocusGained

    private void passwordTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordTextFieldFocusLost

        // Sets back the placeholder of employeeNameTextField
        if (passwordTextField.getText().isEmpty()) {

            passwordTextField.setText("Enter Password");
            passwordTextField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_passwordTextFieldFocusLost

    private void passwordTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordTextFieldKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            employeeNameTextField.requestFocus();

        }

    }//GEN-LAST:event_passwordTextFieldKeyPressed

    private void PrintButttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintButttonActionPerformed
        try {
            // Path to the compiled Jasper report
            String reportPath = "src/report/User_Report.jasper";

            // Create parameters map (if needed)
            HashMap<String, Object> parameters = new HashMap<>();
            
            parameters.put("department_name",  departmentComboBox.getSelectedItem());

            // Create data source from JTable model
            JRTableModelDataSource dataSource = new JRTableModelDataSource(NewuserTable.getModel());

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, dataSource);

            // Show the report in viewer
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating report: " + e.getMessage(), "Report Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_PrintButttonActionPerformed

    private void PrintButttonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PrintButttonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PrintButttonKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddNewUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackToDashboardButton;
    private javax.swing.JPanel BackToDashboardPanel;
    private javax.swing.JTable NewuserTable;
    private com.k33ptoo.components.KButton PrintButtton;
    private com.k33ptoo.components.KButton addButton;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JPanel buttonPanel;
    private com.k33ptoo.components.KButton deleteButton;
    private javax.swing.JComboBox<String> departmentComboBox;
    private javax.swing.JPanel detailsPanel;
    private javax.swing.JComboBox<String> employeeDepartmentComboBox;
    private javax.swing.JTextField employeeIDTextField;
    private javax.swing.JTextField employeeNameTextField;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private com.k33ptoo.components.KButton kButton1;
    private com.k33ptoo.components.KButton kButton2;
    private javax.swing.JTextField passwordTextField;
    private javax.swing.JComboBox<String> positionComboBox;
    private javax.swing.JButton refreshButton;
    private javax.swing.JPanel tablePanel;
    private com.k33ptoo.components.KButton updateButton;
    private javax.swing.JTextField userNameTextField;
    // End of variables declaration//GEN-END:variables

    private void reset() {

        employeeIDTextField.setText("");
        employeeIDTextField.setEditable(true);
        userNameTextField.setText("");
        passwordTextField.setText("");
        employeeNameTextField.setText("");
        employeeDepartmentComboBox.setSelectedIndex(0);
        positionComboBox.setSelectedIndex(0);
        departmentComboBox.setSelectedIndex(0);
        NewuserTable.clearSelection();
        addPlaceholder();

    }

}
