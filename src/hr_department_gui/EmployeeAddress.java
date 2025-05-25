/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hr_department_gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import model.MySql;

/**
 *
 * @author DELL
 */
public class EmployeeAddress extends javax.swing.JDialog {

    // Hashmaps for provice, district, city
    private HashMap<String, String> provinceMap = new HashMap<>();
    private HashMap<String, String> districtMap = new HashMap<>();
    private HashMap<String, String> cityMap = new HashMap<>();

    private AddEmployee addemp; // get access to parent from Employee address

    /**
     * Creates new form EmployeeAddress
     *
     * @param parent
     * @param modal
     * @param empID
     */
    public EmployeeAddress(java.awt.Frame parent, boolean modal, String empID) {

        super(parent, modal);  // Pass parent frame and modal flag
        initComponents();      // Initialize components
        employeeIDTextField.setText(empID);  // sets generated Employee ID from parent Frame 
        addPlaceholder();    // call textfields placeholder method
        loadProvince();      // call load Province ComboBox method 
        loadDistricts("Select Province");      // call load City ComboBox method
        loadCities("Select District");      // call District and City load ComboBox method

        if (parent instanceof AddEmployee addEmployee) {
            addemp = addEmployee; // Cast parent to AddEmployee
        } else {
            addemp = null; // Or handle as needed
        }

        configureKeyBindings(); // For Default Frame Key Controls

    }

    private String lastSavedCity = ""; // Holds the current City

    // Model for populating the JComboBoxes with a list of strings
    private DefaultComboBoxModel<String> model;

    // Placeholders add method
    private void addPlaceholder() {

        // postalcodeTextField placeholder & color
        postalcodeTextField.setText("Type Postal Code");
        postalcodeTextField.setForeground(Color.GRAY);

        // cityTextField placeholder & color
        cityTextField.setText("New City");
        cityTextField.setForeground(Color.GRAY);

        // addressLine1TextField placeholder & Color
        addressLine1TextField.setText("Type your address line 1");
        addressLine1TextField.setForeground(Color.GRAY);

        // addressLine2TextField placeholder & Color
        addressLine2TextField.setText("Type your address line 2");
        addressLine2TextField.setForeground(Color.GRAY);

    }

    // Method to load provinces
    private void loadProvince() {

        try {

            // Create a vector to hold the province names for the provinceComboBox.
            Vector<String> provincevector = new Vector<>();

            // Add the default selection item to the vector.
            provincevector.add("Select Province");

            // database query for to fetch all records from `province` table
            ResultSet provinceResultSet = MySql.executeSearch("SELECT * FROM `province`");

            // DefaultComboBoxModel
            model = new DefaultComboBoxModel();

            // while loop to load resultSet one by one
            while (provinceResultSet.next()) {

                // Add the province name to the vector for the comboBox.
                provincevector.add(provinceResultSet.getString("province_name"));

                // Store the mapping of province name to province ID in a HashMap for later use
                provinceMap.put(provinceResultSet.getString("province_name"), provinceResultSet.getString("province_id"));

            }

            // ArrayList eke data DefaultComboBoxModel ekata add karanawa
            for (String province : provincevector) {
                model.addElement(province);
            }

            // Set the vector as comboBox model.
            provinceComboBox.setModel(model);

        } catch (Exception e) {

            // Show an error message when an any exception occured.
            JOptionPane.showMessageDialog(this, "Error loading Province: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

        }

    }

    // Method to load districts based on selected province
    private void loadDistricts(String province) {

        try {

            // Create a vector to hold the district names for the districtComboBox.
            Vector<String> districtVector = new Vector<>();

            districtVector.add("Select District");

            // Fetch districts based on the selected province
            String provinceId = provinceMap.get(province);

            // Execute a search query to retrieve districts based on the selected province ID
            ResultSet districtResultSet = MySql.executeSearch("SELECT * FROM `district` WHERE `province_province_id` = '" + provinceId + "'");

            // Populate district list and map
            while (districtResultSet.next()) {

                // Add the district name to the list
                districtVector.add(districtResultSet.getString("district_name"));
                // Map the district name to its corresponding district ID
                districtMap.put(districtResultSet.getString("district_name"), districtResultSet.getString("district_id"));

            }

            // Initialize the DefaultComboBoxModel to hold district names
            model = new DefaultComboBoxModel<>();

            // Add each district name from the ArrayList to the ComboBox model
            for (String district : districtVector) {

                model.addElement(district);

            }

            // Set the districtComboBox with the updated model
            districtComboBox.setModel(model);

            // Reset the cityComboBox to a default state with only "Select City"
            cityComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Select City"})); // Reset city combo box

        } catch (Exception e) {

            // Show error message
            JOptionPane.showMessageDialog(this, "Error loading Districts: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

        }

    }

    // Method to load cities based on selected district
    private void loadCities(String district) {

        try {

            // create ArrayList
            Vector<String> cityVector = new Vector<>();

            cityVector.add("Select City");  // Add default option

            // Get the district ID for the selected district
            String districtId = districtMap.get(district);

            // Fetch cities for the selected district
            ResultSet cityResultSet = MySql.executeSearch("SELECT * FROM `city` WHERE `district_district_id` = '" + districtId + "'");

            // Populate city list and map
            while (cityResultSet.next()) {

                // Add the city name to the list
                cityVector.add(cityResultSet.getString("city_name"));
                // Map the city name to its corresponding city ID
                cityMap.put(cityResultSet.getString("city_name"), cityResultSet.getString("city_id"));

            }

            // Initialize the DefaultComboBoxModel to hold city names
            model = new DefaultComboBoxModel<>();

            // Add each city name from the ArrayList to the ComboBox model
            for (String city : cityVector) {
                model.addElement(city);
            }

            // Set the cityComboBox with the updated model
            cityComboBox.setModel(model);

        } catch (Exception e) {

            // Show error message
            JOptionPane.showMessageDialog(this, "Error loading cities: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

        }

    }

    // New City adding Method
    private void addNewCityToDatabase() {

        try {

            // variables creation
            // get the text from cityTextField
            String cityinput = cityTextField.getText().trim();
            // get the text from selectect districtComboBox item
            String district = String.valueOf(districtComboBox.getSelectedItem());
            // get the index of districtComboBox
            String districtIndex = String.valueOf(districtComboBox.getSelectedItem());

            if (cityinput.isEmpty() || cityinput.equals("New City")) {
                // Check if the cityTextField is empty OR matches the placeholder text "New City"

                JOptionPane.showMessageDialog(this, "Please Enter New City to Add!", "Warning: Input Field Empty", JOptionPane.ERROR_MESSAGE);

                cityTextField.grabFocus();

            } else if (district.equals("Select District")) {
                // Validate if a valid district has been selected

                JOptionPane.showMessageDialog(this, "Please Select a District to Add New City", "Warning: Unknown District of City", JOptionPane.WARNING_MESSAGE);

                districtComboBox.grabFocus();

            } else if (cityinput.matches("[a-zA-Z\\s]+")) {
                // Validate the cityinput to ensure only letters and spaces are allowed

                if (!lastSavedCity.isEmpty()) {
                    // Update lastly added City

                    // set query variable
                    String query = "UPDATE `city` SET `city_name` = '" + cityinput + "' WHERE `city_name` = '" + lastSavedCity + "' ";

                    // Execute the query
                    int rs = MySql.executeUpdate(query);

                    // If the query affected rows, show a success message
                    if (rs > 0) {

                        JOptionPane.showMessageDialog(this, "City updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                        loadCities(district);   // Reload the cityComboBox

                    }

                } else {
                    // Add new City

                    // SQL query to insert the new city into the database variable
                    String query = "INSERT INTO `city` (`city_name` , `district_district_id`) VALUES ('" + cityinput + "' , '" + districtMap.get(districtIndex) + "')";

                    // Execute the query variable
                    int rs = MySql.executeUpdate(query);

                    // If the query affected rows, show a success message
                    if (rs > 0) {

                        JOptionPane.showMessageDialog(this, "City added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                        loadCities(district);   // Reload the CityComboBox

                    }

                }

                // Save the new data and replace the old one
                lastSavedCity = cityinput;

                // Reset cityTextField
                cityTextField.setText("New City");
                cityTextField.setForeground(Color.GRAY);
                cityComboBox.grabFocus();

            } else {
                // Handle invalid cityinput (numbers or symbols) 

                JOptionPane.showMessageDialog(this, "Only letters are allowed in New City Input.", "Warning: Invalid input!", JOptionPane.ERROR_MESSAGE);
                // Exit the method

            }

        } catch (Exception e) {

            // Show an error message when an any exception occured.
            JOptionPane.showMessageDialog(this, "Error adding data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

        }

    }

    private void submitAddress() {

        try {

            // Get input values as variables from Textfields and ComboBoxes
            String postalcode = postalcodeTextField.getText();
            String province = String.valueOf(provinceComboBox.getSelectedItem());
            String district = String.valueOf(districtComboBox.getSelectedItem());
            String city = String.valueOf(cityComboBox.getSelectedItem());
            String addressLine1 = addressLine1TextField.getText();
            String addressLine2 = addressLine2TextField.getText();
            
            // Validation checks for required fields
            if (postalcode.isEmpty() || postalcode.equals("Type Postal Code")) {
                // Check if the postalcode is empty or matches the placeholder text "Type Postal Code"

                JOptionPane.showMessageDialog(this, "Please enter your Postalcode of Your Area!", "Warning: Input Field Empty!", JOptionPane.WARNING_MESSAGE);

                postalcodeTextField.grabFocus(); // Gets focus to postalcodeTextField

            } else if (!postalcode.matches("^\\d{5}$")) {
                //  Handle invalid postalcode

                JOptionPane.showMessageDialog(this, "Please enter Valid Postalcode of Your Area!", "Warning: Input Field Empty!", JOptionPane.WARNING_MESSAGE);

                postalcodeTextField.grabFocus(); // Gets focus to postalcodeTextField

            } else if (postalcode.length() != 5) {
                // Check if the postalcode has 5 characters!

                JOptionPane.showMessageDialog(this, "Postal Code length must have 5 characters!", "Warning: Invalid Input!", JOptionPane.ERROR_MESSAGE);

                postalcodeTextField.grabFocus(); // Gets focus to postalcodeTextField

            } else if (province.equals("Select Province")) {
                // Check if the province in default value

                JOptionPane.showMessageDialog(this, "Please select a Province!", "Warning: Not Selected!", JOptionPane.WARNING_MESSAGE);

                provinceComboBox.grabFocus(); // Gets focus to provinceComboBox

            } else if (district.equals("Select District")) {
                // Check if the district in default value

                JOptionPane.showMessageDialog(this, "Please select a District!", "Warning: Not Selected!", JOptionPane.WARNING_MESSAGE);

                districtComboBox.grabFocus(); // Gets focus to districtComboBox

            } else if (city.equals("Select City")) {
                // Check if the city in default value

                JOptionPane.showMessageDialog(this, "Please select a City", "Warning: Not Selected", JOptionPane.WARNING_MESSAGE);

                cityComboBox.grabFocus(); // Gets focus to cityComboBox

            } else if (addressLine1.isEmpty() || addressLine1.equals("Type your address line 1")) {
                // Check if the addressLine1 is empty or matches the placeholder text "Type your address line 1"

                JOptionPane.showMessageDialog(this, "Please enter your Address Line 1", "Warning: Input Field Empty", JOptionPane.WARNING_MESSAGE);

                addressLine1TextField.grabFocus(); // Gets focus to addressLine1TextField             

            } else if (addressLine2.isEmpty() || addressLine2.equals("Type your address line 2")) {
                // Check if the addressLine2 is empty or matches the placeholder text "Type your address line 2"

                JOptionPane.showMessageDialog(this, "Please enter your Address Line 2", "Warning: Input Field Empty", JOptionPane.WARNING_MESSAGE);

                addressLine2TextField.grabFocus(); // Gets focus to addressLine2TextField 

            } else {

                // Address INSERT Query String Variable
                String query = "INSERT INTO `employee_address` (`address_line01`,`address_line02`,`postal_code`,`city_city_id`,`province_province_id`,`district_district_id`) "
                        + "VALUES ('" + addressLine1 + "','" + addressLine2 + "','" + postalcode + "','" + cityMap.get(city) + "','" + provinceMap.get(province) + "','" + districtMap.get(district) + "')";

                // Execute Address INSERT Query
                MySql.executeUpdate(query);

                // Fetch the last inserted ID (To get Address)
                String lastInsertQuery = "SELECT LAST_INSERT_ID() AS address_id";

                // Execute Query to get Address ID
                ResultSet rs = MySql.executeSearch(lastInsertQuery);

                // Sets a variable for default Address ID
                int addressId = -1;

                // Checks and assign current Query Address ID
                if (rs.next()) {
                    addressId = rs.getInt("address_id");
                }

                // Checks if an Address Id added 
                if (addressId > 0) {

                    // Pass addressId to AddEmployee Frame
                    AddEmployee.setAddressId(addressId);

                    // dispose this (Employee Address) dialogbox
                    this.dispose();

                    // Success 
                    JOptionPane.showMessageDialog(this, "Employee Address Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                } else {

                    // Shows an Error Message
                    JOptionPane.showMessageDialog(this, "Failed to retrieve address ID!", "Error", JOptionPane.ERROR_MESSAGE);

                }

            }

        } catch (Exception e) {

            // Show an error message when an any exception occured.
            JOptionPane.showMessageDialog(this, "Error submitting data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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

        HeaderPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        BodyPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        BackToDashboardPanel = new javax.swing.JPanel();
        BackToDashboardButton = new javax.swing.JButton();
        detailsPanel = new javax.swing.JPanel();
        provinceLabel = new javax.swing.JLabel();
        provinceComboBox = new javax.swing.JComboBox<>();
        employeeIDLabel = new javax.swing.JLabel();
        employeeIDTextField = new javax.swing.JTextField();
        districtLabel = new javax.swing.JLabel();
        districtComboBox = new javax.swing.JComboBox<>();
        cityLabel = new javax.swing.JLabel();
        cityComboBox = new javax.swing.JComboBox<>();
        cityTextField = new javax.swing.JTextField();
        cityButton = new com.k33ptoo.components.KButton();
        addressLine1Label = new javax.swing.JLabel();
        addressLine1TextField = new javax.swing.JTextField();
        addressLine2Label = new javax.swing.JLabel();
        addressLine2TextField = new javax.swing.JTextField();
        submitButton = new com.k33ptoo.components.KButton();
        clearButton = new com.k33ptoo.components.KButton();
        jLabel2 = new javax.swing.JLabel();
        postalcodeTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);

        HeaderPanel.setBackground(new java.awt.Color(153, 153, 153));
        HeaderPanel.setPreferredSize(new java.awt.Dimension(780, 60));

        jLabel1.setFont(new java.awt.Font("Audiowide", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Employee Address");

        javax.swing.GroupLayout HeaderPanelLayout = new javax.swing.GroupLayout(HeaderPanel);
        HeaderPanel.setLayout(HeaderPanelLayout);
        HeaderPanelLayout.setHorizontalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addGap(260, 260, 260)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                .addGap(260, 260, 260))
        );
        HeaderPanelLayout.setVerticalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        getContentPane().add(HeaderPanel, java.awt.BorderLayout.PAGE_START);

        BodyPanel.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        BackToDashboardPanel.setBackground(new java.awt.Color(153, 153, 153));
        BackToDashboardPanel.setPreferredSize(new java.awt.Dimension(808, 50));

        BackToDashboardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/back-arrow.png"))); // NOI18N
        BackToDashboardButton.setToolTipText("Go Back!");
        BackToDashboardButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
                .addContainerGap(965, Short.MAX_VALUE))
        );
        BackToDashboardPanelLayout.setVerticalGroup(
            BackToDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackToDashboardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BackToDashboardButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(BackToDashboardPanel, java.awt.BorderLayout.PAGE_END);

        provinceLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        provinceLabel.setText("Select Province");

        provinceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Province" }));
        provinceComboBox.setToolTipText("Please Choose Your Province!");
        provinceComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                provinceComboBoxActionPerformed(evt);
            }
        });
        provinceComboBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                provinceComboBoxKeyPressed(evt);
            }
        });

        employeeIDLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        employeeIDLabel.setText("Employee ID");

        employeeIDTextField.setToolTipText("Employee ID");
        employeeIDTextField.setEnabled(false);

        districtLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        districtLabel.setText("Select District");

        districtComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select District" }));
        districtComboBox.setToolTipText("Please Choose Your District!");
        districtComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                districtComboBoxActionPerformed(evt);
            }
        });
        districtComboBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                districtComboBoxKeyPressed(evt);
            }
        });

        cityLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        cityLabel.setText("Select City");

        cityComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select City" }));
        cityComboBox.setToolTipText("Please Choose Your City!");
        cityComboBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cityComboBoxKeyPressed(evt);
            }
        });

        cityTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        cityTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cityTextField.setText("New City");
        cityTextField.setToolTipText("Please Add Your New City, If Your City Not Available!");
        cityTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cityTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cityTextFieldFocusLost(evt);
            }
        });
        cityTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cityTextFieldKeyPressed(evt);
            }
        });

        cityButton.setText("Add");
        cityButton.setToolTipText("Click to Add Your New City!");
        cityButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cityButton.setkEndColor(new java.awt.Color(0, 204, 204));
        cityButton.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        cityButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        cityButton.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        cityButton.setkPressedColor(new java.awt.Color(0, 102, 153));
        cityButton.setkSelectedColor(new java.awt.Color(0, 102, 153));
        cityButton.setkStartColor(new java.awt.Color(0, 102, 153));
        cityButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cityButtonActionPerformed(evt);
            }
        });
        cityButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cityButtonKeyPressed(evt);
            }
        });

        addressLine1Label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        addressLine1Label.setText("Address Line 1");

        addressLine1TextField.setToolTipText("Please Enter Your Addres Line 1!");
        addressLine1TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                addressLine1TextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                addressLine1TextFieldFocusLost(evt);
            }
        });
        addressLine1TextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                addressLine1TextFieldKeyPressed(evt);
            }
        });

        addressLine2Label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        addressLine2Label.setText("Address Line 2");

        addressLine2TextField.setToolTipText("Please Enter Your Addres Line 2!");
        addressLine2TextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                addressLine2TextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                addressLine2TextFieldFocusLost(evt);
            }
        });
        addressLine2TextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                addressLine2TextFieldKeyPressed(evt);
            }
        });

        submitButton.setText("Submit");
        submitButton.setToolTipText("Click to Add Your Address!");
        submitButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        submitButton.setkEndColor(new java.awt.Color(0, 204, 204));
        submitButton.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        submitButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        submitButton.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        submitButton.setkPressedColor(new java.awt.Color(0, 102, 153));
        submitButton.setkSelectedColor(new java.awt.Color(0, 102, 153));
        submitButton.setkStartColor(new java.awt.Color(0, 102, 153));
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });
        submitButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                submitButtonKeyPressed(evt);
            }
        });

        clearButton.setText("Clear");
        clearButton.setToolTipText("Click to Clear All Data!");
        clearButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        clearButton.setkEndColor(new java.awt.Color(0, 204, 204));
        clearButton.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        clearButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        clearButton.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        clearButton.setkPressedColor(new java.awt.Color(0, 102, 153));
        clearButton.setkSelectedColor(new java.awt.Color(0, 102, 153));
        clearButton.setkStartColor(new java.awt.Color(0, 102, 153));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        clearButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                clearButtonKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel2.setText("Postal Code");

        postalcodeTextField.setToolTipText("Please Enter Your Postal Code!");
        postalcodeTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                postalcodeTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                postalcodeTextFieldFocusLost(evt);
            }
        });
        postalcodeTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                postalcodeTextFieldKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout detailsPanelLayout = new javax.swing.GroupLayout(detailsPanel);
        detailsPanel.setLayout(detailsPanelLayout);
        detailsPanelLayout.setHorizontalGroup(
            detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detailsPanelLayout.createSequentialGroup()
                        .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(districtLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(provinceLabel)
                            .addComponent(employeeIDLabel))
                        .addGap(18, 18, 18)
                        .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(districtComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(provinceComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(employeeIDTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cityComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(detailsPanelLayout.createSequentialGroup()
                                .addComponent(cityTextField)
                                .addGap(18, 18, 18)
                                .addComponent(cityButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(detailsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(postalcodeTextField)
                                .addGap(177, 177, 177)))
                        .addGap(50, 50, 50))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detailsPanelLayout.createSequentialGroup()
                        .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(detailsPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 356, Short.MAX_VALUE)
                                .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(detailsPanelLayout.createSequentialGroup()
                                .addComponent(addressLine2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(addressLine2TextField))
                            .addGroup(detailsPanelLayout.createSequentialGroup()
                                .addComponent(addressLine1Label, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(addressLine1TextField)))
                        .addGap(221, 221, 221))))
        );
        detailsPanelLayout.setVerticalGroup(
            detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsPanelLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employeeIDLabel)
                    .addComponent(employeeIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(postalcodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(provinceLabel)
                    .addComponent(provinceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(districtLabel)
                    .addComponent(districtComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cityLabel)
                    .addComponent(cityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cityButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressLine1Label)
                    .addComponent(addressLine1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressLine2Label)
                    .addComponent(addressLine2TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        jPanel4.add(detailsPanel, java.awt.BorderLayout.CENTER);

        BodyPanel.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(BodyPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BackToDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToDashboardButtonActionPerformed

        // To Close EmployeeAddress(this JFrame) and go back to AddEmployee(main JFrame)  
        this.dispose();

    }//GEN-LAST:event_BackToDashboardButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed

        // Get input values as variables from Textfields and ComboBoxes
        String postalcode = postalcodeTextField.getText();
        String province = String.valueOf(provinceComboBox.getSelectedItem());
        String district = String.valueOf(districtComboBox.getSelectedItem());
        String city = String.valueOf(cityComboBox.getSelectedItem());
        String addressLine1 = addressLine1TextField.getText();
        String addressLine2 = addressLine2TextField.getText();

        if (!postalcode.isEmpty() || !postalcode.equals("Type Postal Code")
                || !province.equals("Select Province")
                || !district.equals("Select Province")
                || !city.equals("Select Province")
                || !addressLine1.isEmpty() || !addressLine1.equals("Type your address line 1")
                || !addressLine2.isEmpty() || !addressLine2.equals("Type your address line 2")) {

            // Show confirmation dialog
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to Clear the Address?", "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            // Check user's choice
            if (result == JOptionPane.YES_OPTION) {

                JOptionPane.showMessageDialog(this, "The Address Cleared Successfully!", "Data Cleared", JOptionPane.INFORMATION_MESSAGE);

                // Calling reset method (created method)
                reset();

            } else {

                JOptionPane.showMessageDialog(this, "The Address Clear Cancelled!", "Cancelled", JOptionPane.INFORMATION_MESSAGE);

            }

        } else {

            JOptionPane.showMessageDialog(this, "No Data Found To Delete!", "Error: Empty Data!", JOptionPane.ERROR_MESSAGE);

        }

    }//GEN-LAST:event_clearButtonActionPerformed

    private void cityTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cityTextFieldFocusGained

        // Checks and set clear the current cityTextField to enter data to it
        if (cityTextField.getText().equals("New City")) {

            cityTextField.setText("");
            cityTextField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_cityTextFieldFocusGained

    private void addressLine1TextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_addressLine1TextFieldFocusGained

        // Checks and set clear the current addressLine1TextField to enter data to it
        if (addressLine1TextField.getText().equals("Type your address line 1")) {

            addressLine1TextField.setText("");
            addressLine1TextField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_addressLine1TextFieldFocusGained

    private void addressLine2TextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_addressLine2TextFieldFocusGained

        // Checks and set clear the current addressLine2TextField to enter data to it
        if (addressLine2TextField.getText().equals("Type your address line 2")) {

            addressLine2TextField.setText("");
            addressLine2TextField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_addressLine2TextFieldFocusGained

    private void cityTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cityTextFieldFocusLost

        // Sets back the placeholder of cityTextField
        if (cityTextField.getText().isEmpty()) {

            cityTextField.setText("New City");
            cityTextField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_cityTextFieldFocusLost

    private void addressLine1TextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_addressLine1TextFieldFocusLost

        // Sets back the placeholder of addressLine1TextField
        if (addressLine1TextField.getText().isEmpty()) {

            addressLine1TextField.setText("Type your address line 1");
            addressLine1TextField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_addressLine1TextFieldFocusLost

    private void addressLine2TextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_addressLine2TextFieldFocusLost

        // Sets back the placeholder of addressLine2TextField
        if (addressLine2TextField.getText().isEmpty()) {

            addressLine2TextField.setText("Type your address line 2");
            addressLine2TextField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_addressLine2TextFieldFocusLost

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed

        // calling address submitting method
        submitAddress();

    }//GEN-LAST:event_submitButtonActionPerformed

    private void cityButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cityButtonActionPerformed

        // calling method to add new city
        addNewCityToDatabase();

    }//GEN-LAST:event_cityButtonActionPerformed

    private void postalcodeTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_postalcodeTextFieldFocusGained

        // Checks and set clear the current postalcodeTextField to enter data to it
        if (postalcodeTextField.getText().equals("Type Postal Code")) {

            postalcodeTextField.setText("");
            postalcodeTextField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_postalcodeTextFieldFocusGained

    private void postalcodeTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_postalcodeTextFieldFocusLost

        // Sets back the placeholder of postalcodeTextField
        if (postalcodeTextField.getText().isEmpty()) {

            postalcodeTextField.setText("Type Postal Code");
            postalcodeTextField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_postalcodeTextFieldFocusLost

    private void districtComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_districtComboBoxActionPerformed

        // get selected district
        String selectedDistrict = (String) districtComboBox.getSelectedItem();

        // check whether districtComboBox is null
        if (!selectedDistrict.equals("Select District")) {

            loadCities(selectedDistrict); // Load cities for the selected district

        } else {

            cityComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Select City"})); // Reset city combo box

        }

    }//GEN-LAST:event_districtComboBoxActionPerformed

    private void provinceComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_provinceComboBoxActionPerformed

        // get selected province
        String selectedProvince = (String) provinceComboBox.getSelectedItem();

        // check whether districtComboBox is null
        if (!selectedProvince.equals("Select Province")) {

            loadDistricts(selectedProvince); // Load districts for the selected province

        } else {

            // Reset district and city combo boxes
            districtComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Select District"}));
            cityComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Select City"}));

        }

    }//GEN-LAST:event_provinceComboBoxActionPerformed

    private void postalcodeTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_postalcodeTextFieldKeyPressed
        // code to add key controls for postalcodeTextField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_DOWN ->
                provinceComboBox.grabFocus();

            case KeyEvent.VK_DELETE ->
                postalcodeTextField.setText("");

        }

    }//GEN-LAST:event_postalcodeTextFieldKeyPressed

    private void provinceComboBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_provinceComboBoxKeyPressed
        // code to add key controls for provinceComboBox

        int keyCode = evt.getKeyCode();
        int selectedIndex = provinceComboBox.getSelectedIndex();

        if (selectedIndex >= 0) {

            switch (keyCode) {

                case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                    districtComboBox.grabFocus();

                case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_LEFT, KeyEvent.VK_A -> {

                    if (selectedIndex > 0) { // Prevent selecting an index less than 0
                        provinceComboBox.setSelectedIndex(selectedIndex - 1);
                    }

                }

                case KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {

                    if (selectedIndex < provinceComboBox.getItemCount() - 1) { // Prevent exceeding last index
                        provinceComboBox.setSelectedIndex(selectedIndex + 1);
                    }

                }

                case KeyEvent.VK_BACK_SPACE ->
                    postalcodeTextField.grabFocus();

                case KeyEvent.VK_DELETE ->
                    provinceComboBox.setSelectedIndex(0);

            }

        }

    }//GEN-LAST:event_provinceComboBoxKeyPressed

    private void districtComboBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_districtComboBoxKeyPressed
        // code to add key controls for districtComboBox

        int keyCode = evt.getKeyCode();
        int selectedIndex = districtComboBox.getSelectedIndex();

        if (selectedIndex >= 0) {

            switch (keyCode) {

                case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                    cityComboBox.grabFocus();

                case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_LEFT, KeyEvent.VK_A -> {

                    if (selectedIndex > 0) { // Prevent selecting an index less than 0
                        districtComboBox.setSelectedIndex(selectedIndex - 1);
                    }

                }

                case KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {

                    if (selectedIndex < districtComboBox.getItemCount() - 1) { // Prevent exceeding last index
                        districtComboBox.setSelectedIndex(selectedIndex + 1);
                    }

                }

                case KeyEvent.VK_BACK_SPACE ->
                    provinceComboBox.grabFocus();
                case KeyEvent.VK_DELETE ->
                    districtComboBox.setSelectedIndex(0);

            }

        }

    }//GEN-LAST:event_districtComboBoxKeyPressed

    private void cityComboBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cityComboBoxKeyPressed
        // code to add key controls for cityComboBox

        int keyCode = evt.getKeyCode();
        int selectedIndex = cityComboBox.getSelectedIndex();

        if (selectedIndex >= 0) {

            switch (keyCode) {

                case KeyEvent.VK_ENTER ->
                    addressLine1TextField.grabFocus();

                case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_LEFT, KeyEvent.VK_A -> {

                    if (selectedIndex > 0) { // Prevent selecting an index less than 0
                        cityComboBox.setSelectedIndex(selectedIndex - 1);
                    }

                }

                case KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {

                    if (selectedIndex < cityComboBox.getItemCount() - 1) { // Prevent exceeding last index
                        cityComboBox.setSelectedIndex(selectedIndex + 1);
                    }

                }

                case KeyEvent.VK_BACK_SPACE ->
                    districtComboBox.grabFocus();

                case KeyEvent.VK_DELETE ->
                    cityComboBox.setSelectedIndex(0);

                case KeyEvent.VK_INSERT ->
                    cityTextField.grabFocus();

            }

        }

    }//GEN-LAST:event_cityComboBoxKeyPressed

    private void cityTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cityTextFieldKeyPressed
        // code to add key controls for cityTextField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT -> {

                cityButton.grabFocus();
                cityButton.doClick();

            }

            case KeyEvent.VK_DOWN ->
                addressLine1TextField.grabFocus();

            case KeyEvent.VK_UP ->
                cityComboBox.grabFocus();

            case KeyEvent.VK_DELETE ->
                cityTextField.setText("");

        }

    }//GEN-LAST:event_cityTextFieldKeyPressed

    private void cityButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cityButtonKeyPressed
        // code to add key controls for cityButton

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                cityButton.doClick();

            case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_LEFT, KeyEvent.VK_A, KeyEvent.VK_BACK_SPACE ->
                cityTextField.grabFocus();

            case KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_RIGHT, KeyEvent.VK_D ->
                addressLine1TextField.grabFocus();

            case KeyEvent.VK_DELETE ->
                cityTextField.setText("");

        }

    }//GEN-LAST:event_cityButtonKeyPressed

    private void addressLine1TextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addressLine1TextFieldKeyPressed
        // code to add key controls for addressLine1

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_DOWN ->
                addressLine2TextField.grabFocus();

            case KeyEvent.VK_UP ->
                cityComboBox.grabFocus();

            case KeyEvent.VK_DELETE ->
                addressLine1TextField.setText("");

        }

    }//GEN-LAST:event_addressLine1TextFieldKeyPressed

    private void addressLine2TextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addressLine2TextFieldKeyPressed
        // code to add key controls for addressLine2TextField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT -> {

                submitButton.grabFocus();
                submitButton.doClick();

            }

            case KeyEvent.VK_DOWN ->
                submitButton.grabFocus();

            case KeyEvent.VK_UP ->
                addressLine1TextField.grabFocus();

            case KeyEvent.VK_DELETE ->
                addressLine2TextField.setText("");

        }

    }//GEN-LAST:event_addressLine2TextFieldKeyPressed

    private void submitButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_submitButtonKeyPressed
        // code to add key controls for submitButton

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                submitButton.doClick();

            case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_LEFT, KeyEvent.VK_A, KeyEvent.VK_BACK_SPACE ->
                addressLine2TextField.grabFocus();

            case KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_RIGHT, KeyEvent.VK_D ->
                clearButton.grabFocus();

            case KeyEvent.VK_DELETE -> {

                clearButton.grabFocus();
                clearButton.doClick();

            }

        }

    }//GEN-LAST:event_submitButtonKeyPressed

    private void clearButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clearButtonKeyPressed
        // code to add key controls for clearButton

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_DELETE ->
                clearButton.doClick();

            case KeyEvent.VK_UP, KeyEvent.VK_W ->
                addressLine2TextField.grabFocus();

            case KeyEvent.VK_LEFT, KeyEvent.VK_A, KeyEvent.VK_BACK_SPACE ->
                submitButton.grabFocus();

        }

    }//GEN-LAST:event_clearButtonKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackToDashboardButton;
    private javax.swing.JPanel BackToDashboardPanel;
    private javax.swing.JPanel BodyPanel;
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JLabel addressLine1Label;
    private javax.swing.JTextField addressLine1TextField;
    private javax.swing.JLabel addressLine2Label;
    private javax.swing.JTextField addressLine2TextField;
    private com.k33ptoo.components.KButton cityButton;
    private javax.swing.JComboBox<String> cityComboBox;
    private javax.swing.JLabel cityLabel;
    private javax.swing.JTextField cityTextField;
    private com.k33ptoo.components.KButton clearButton;
    private javax.swing.JPanel detailsPanel;
    private javax.swing.JComboBox<String> districtComboBox;
    private javax.swing.JLabel districtLabel;
    private javax.swing.JLabel employeeIDLabel;
    private javax.swing.JTextField employeeIDTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField postalcodeTextField;
    private javax.swing.JComboBox<String> provinceComboBox;
    private javax.swing.JLabel provinceLabel;
    private com.k33ptoo.components.KButton submitButton;
    // End of variables declaration//GEN-END:variables

    private void reset() {

        // Reset all components to default values
        provinceComboBox.setSelectedIndex(0);
        districtComboBox.setSelectedIndex(0);
        cityComboBox.setSelectedIndex(0);
        cityTextField.setText("");
        addressLine1TextField.setText("");
        addressLine2TextField.setText("");

        // Re-add the placeholders to refreshed TextFields
        addPlaceholder();

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

                reset();

            }

        });

    }

}
