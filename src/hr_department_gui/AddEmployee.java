package hr_department_gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import model.MySql;

public class AddEmployee extends javax.swing.JFrame {

    private static int addressId;
    private String empId;
    private ManageEmployee manageEmployee;

    public static void setAddressId(int id) {

        addressId = id;

    }

    private void loadAddressId() {

        addressIDTextField.setText(String.valueOf(addressId));

    }

    public AddEmployee(ManageEmployee manageEmployee, String empId) {

        initComponents();

        addPlaceholder();
        
        addressViewPlaceholders();

        dateChooserListeners(); // call this method to activate datechooser key press 

        this.empId = empId;

        this.manageEmployee = manageEmployee; // Assign ManageEmployee Object

        ComponentsHide();  // Hide necessary Components

        manageEmployeeIdSettingsLoad();  // manage old employee data

        configureKeyBindings(); // For Default Frame Key Controls

    }

    // Placeholders add method
    private void addPlaceholder() {

        // checks if constructor pass ManageEmployee 
        if (manageEmployee == null) {

            // employeeIdTextField placeholder & color
            employeeIdTextField.setText("Click to Generate ID");
            employeeIdTextField.setForeground(Color.GRAY);

            // nicTextField placeholder & color
            nicTextField.setText("NIC Number");
            nicTextField.setForeground(Color.GRAY);

        }

        // fNameTextField placeholder & color
        fNameTextField.setText("First Name");
        fNameTextField.setForeground(Color.GRAY);

        // lNameTextField placeholder & color
        lNameTextField.setText("Last Name");
        lNameTextField.setForeground(Color.GRAY);

        // contactNoTextField placeholder & Color
        contactNoTextField.setText("Mobile Number");
        contactNoTextField.setForeground(Color.GRAY);

        // emailTextField placeholder & Color
        emailTextField.setText("Email Address");
        emailTextField.setForeground(Color.GRAY);

    }

    private void addressViewPlaceholders() {

        // postalcodeViewTextField placeholder & color
        postalcodeViewTextField.setText("See Postal Code Here");
        postalcodeViewTextField.setForeground(Color.GRAY);

        // AddressLine1ViewTextField placeholder
        AddressLine1ViewTextField.setText("See Your Address Line 1 Here");
        AddressLine1ViewTextField.setForeground(Color.GRAY);

        // AddressLine2ViewTextField placeholder
        AddressLine2ViewTextField.setText("See Your Address Line 2 Here");
        AddressLine2ViewTextField.setForeground(Color.GRAY);

        // ProvinceViewTextField placeholder
        ProvinceViewTextField.setText("See Your Province Here");
        ProvinceViewTextField.setForeground(Color.GRAY);

        // DistrictViewTextField placeholder
        DistrictViewTextField.setText("See Your District Here");
        DistrictViewTextField.setForeground(Color.GRAY);

        // CityViewTextField placeholder
        CityViewTextField.setText("See Your City Here");
        CityViewTextField.setForeground(Color.GRAY);

    }

    private void ComponentsHide() {

        toLabel.setVisible(false); // to label hide by default
        dateOfFinalDayChooser.setVisible(false); // Intern Final DateChooser hide by default

        addressIDTextField.setVisible(false); // addressIDTextField hide by default

        // checks if String employeeData = null
        if (empId == null) {

            // hides update button
            updateButton.setVisible(false);

        } else {

            // hides submit button
            submitButton.setVisible(false);

            // hides addaddress button
            addressButton.setVisible(false);

        }

    }
    
    private void addressViewWithData() {
        
        // Sets back to Address TextFields Color origin
                postalcodeViewTextField.setForeground(Color.BLACK);
                AddressLine1ViewTextField.setForeground(Color.BLACK);
                AddressLine2ViewTextField.setForeground(Color.BLACK);
                ProvinceViewTextField.setForeground(Color.BLACK);
                DistrictViewTextField.setForeground(Color.BLACK);
                CityViewTextField.setForeground(Color.BLACK);

                // Enable the textfields (if it was disabled)
                postalcodeViewTextField.setEnabled(true);
                AddressLine1ViewTextField.setEnabled(true);
                AddressLine2ViewTextField.setEnabled(true);
                ProvinceViewTextField.setEnabled(true);
                DistrictViewTextField.setEnabled(true);
                CityViewTextField.setEnabled(true);

                // Request focus to the textfields
                postalcodeViewTextField.setFocusable(true);
                AddressLine1ViewTextField.setFocusable(true);
                AddressLine2ViewTextField.setFocusable(true);
                ProvinceViewTextField.setFocusable(true);
                DistrictViewTextField.setFocusable(true);
                CityViewTextField.setFocusable(true);
        
    }

    // Method to load the addressId into the text field
    private void loadAddressFromAddressId() {

        try {

            String query = "SELECT `postal_code`, `address_line01`, `address_line02`, `province_name`, `district_name` , `city_name` "
                    + "FROM `employee_address` "
                    + "INNER JOIN `province` ON `employee_address`.`province_province_id` = `province`.`province_id` "
                    + "INNER JOIN `district` ON `employee_address`.`district_district_id` = `district`.`district_id` "
                    + "INNER JOIN `city` ON `employee_address`.`city_city_id` = `city`.`city_id` "
                    + "WHERE `em_address_id` = " + addressId;

            ResultSet rs = MySql.executeSearch(query);

            if (rs.next()) {

                addressViewWithData();
                
                postalcodeViewTextField.setText(rs.getString("postal_code"));
                AddressLine1ViewTextField.setText(rs.getString("address_line01"));
                AddressLine2ViewTextField.setText(rs.getString("address_line02"));
                ProvinceViewTextField.setText(rs.getString("province_name"));
                DistrictViewTextField.setText(rs.getString("district_name"));
                CityViewTextField.setText(rs.getString("city_name"));

                addressButton.setVisible(false);

                postalcodeViewTextField.grabFocus();

            } else {

                JOptionPane.showMessageDialog(this, "No Address Saved!", "Loading Error", JOptionPane.ERROR_MESSAGE);

                // checks if dateOfFinalDayChooser visible or not
                if (dateOfFinalDayChooser.isVisible()) {

                    // if visible select it
                    dateOfFinalDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

                } else {

                    // select emailTextField
                    emailTextField.grabFocus();

                }

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, "Error loading address: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

        }

    }

    private void addAddress() {

        String employeeId = employeeIdTextField.getText();

        if (employeeId.equals("Click to Generate ID")) {

            JOptionPane.showMessageDialog(this, "Please Click Generate Icon Button to Generate Employee ID", "Warning: Missing Employee ID", JOptionPane.WARNING_MESSAGE);

            generateButton.grabFocus(); // Gets focus to generateButton 

        } else {

            // Creating EmployeeAddress from button click
            EmployeeAddress address = new EmployeeAddress(this, true, employeeId);
            address.setVisible(true);

            loadAddressId();
            loadAddressFromAddressId();

        }

    }

    // delete current address
    private void deleteAddress() {

        try {

            // Retrieve the Address ID from the text field
            String addressID = addressIDTextField.getText().trim();

            // Validate the Address ID
            if (addressID.isEmpty()) {

                JOptionPane.showMessageDialog(this, "No Valid Address ID to delete!", "No Data!", JOptionPane.WARNING_MESSAGE);

                return;

            }

            // Confirm deletion
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this address too?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.NO_OPTION) {

                return; // User canceled deletion

            }

            // Execute the DELETE query
            String query = "DELETE FROM `employee_address` WHERE `em_address_id` = '" + addressID + "'";

            int rowsAffected = MySql.executeUpdate(query);

            // Check if the deletion was successful
            if (rowsAffected > 0) {

                JOptionPane.showMessageDialog(this, "Address deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear the text field and refresh the GUI
                addressIDTextField.setText("");

                // add addressview placeholders
                addressViewPlaceholders();

                // re-show the address Button
                addressButton.setVisible(true);

            } else {

                JOptionPane.showMessageDialog(this, "Address ID not found!", "Error", JOptionPane.ERROR_MESSAGE);

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, "Error deleting address: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

        }
    }

    // Employee Data Submit Method
    private void submitEmployee() {

        try {

            // Get input values as variables from Textfields and ComboBoxes
            String employeeID = employeeIdTextField.getText();
            String nicinput = nicTextField.getText().trim();
            String fNameinput = fNameTextField.getText().trim();
            String lNameinput = lNameTextField.getText().trim();
            String contactNoinput = contactNoTextField.getText().trim();
            String emailinput = emailTextField.getText().trim();
            String addressloadId = addressIDTextField.getText().trim();

            Boolean Male = maleRadio.isSelected();
            Boolean Female = femaleRadio.isSelected();
            Boolean FullTime = fullTimeRadio.isSelected();
            Boolean PartTime = partTimeRadio.isSelected();
            Boolean Intern = internRadio.isSelected();

            Date selectedBirthDate = dateOfBirthDayChooser.getDate();
            Date selectedHireDate = dateOfHireDayChooser.getDate();
            Date selectedFinalDate = dateOfFinalDayChooser.getDate();

            // Validation checks for required fields
            if (employeeID.equals("Click to Generate ID")) {
                // Check if the employeeIdTextField matches the placeholder text "Click to Generate ID"

                JOptionPane.showMessageDialog(this, "Please Click Generate Icon Button to Generate Employee ID!", "Warning: Missing Employee ID", JOptionPane.WARNING_MESSAGE);

                generateButton.grabFocus(); // Gets focus to generateButton 

            } else if (nicinput.isEmpty() || nicinput.equals("NIC Number")) {
                // Check if the nicTextField is empty OR matches the placeholder text "NIC Number"

                JOptionPane.showMessageDialog(this, "Please Enter NIC Number!", "Warning: Input Field Empty", JOptionPane.ERROR_MESSAGE);

                nicTextField.grabFocus(); // Gets focus to nicTextField 

            } else if (!nicinput.matches("^\\d{9}[VXvx]$|^\\d{12}$")) {
                // Check if the nicTextField is empty OR matches the placeholder text "NIC Number"

                JOptionPane.showMessageDialog(this, "Invalid NIC Number!", "Warning: Invalid Input!", JOptionPane.ERROR_MESSAGE);

                nicTextField.grabFocus(); // Gets focus to nicTextField 

            } else if (nicinput.length() < 10 || nicinput.length() > 12) {
                // Check if the lNameinput is between 10 and 12 characters!

                JOptionPane.showMessageDialog(this, "NIC must be 10 or 12 characters long!", "Warning: Invalid Input!", JOptionPane.ERROR_MESSAGE);

                nicTextField.grabFocus(); // Gets focus to nicTextField 

            } else if (fNameinput.isEmpty() || fNameinput.equals("First Name")) {
                // Check if the fNameTextField is empty OR matches the placeholder text "First Name"

                JOptionPane.showMessageDialog(this, "Please Enter Your First Name!", "Warning: Input Field Empty", JOptionPane.ERROR_MESSAGE);

                fNameTextField.grabFocus(); // Gets focus to fNameTextField 

            } else if (!fNameinput.matches("[a-zA-Z\\s]+")) {
                // Handle invalid fNameinput (numbers or symbols) 

                JOptionPane.showMessageDialog(this, "Only letters are allowed in First Name Input.", "Warning: Invalid input!", JOptionPane.ERROR_MESSAGE);

                fNameTextField.grabFocus(); // Gets focus to fNameTextField 

            } else if (fNameinput.length() < 2 || fNameinput.length() > 80) {
                // Check if the fNameinput is between 2 and 80 characters!

                JOptionPane.showMessageDialog(this, "First name length must be between 2 and 80 characters!", "Warning: Invalid Input!", JOptionPane.ERROR_MESSAGE);

                fNameTextField.grabFocus(); // Gets focus to fNameTextField 

            } else if (lNameinput.isEmpty() || lNameinput.equals("Last Name")) {
                // Check if the lNameTextField is empty OR matches the placeholder text "Last Name"

                JOptionPane.showMessageDialog(this, "Please Enter Your Last Name!", "Warning: Input Field Empty", JOptionPane.ERROR_MESSAGE);

                lNameTextField.grabFocus(); // Gets focus to lNameTextField 

            } else if (!fNameinput.matches("[a-zA-Z\\s]+")) {
                // Handle invalid lNameinput (numbers or symbols) 

                JOptionPane.showMessageDialog(this, "Only letters are allowed in Last Name Input.", "Warning: Invalid input!", JOptionPane.ERROR_MESSAGE);

                lNameTextField.grabFocus(); // Gets focus to lNameTextField 

            } else if (lNameinput.length() < 2 || lNameinput.length() > 80) {
                // Check if the lNameinput is between 2 and 80 characters!

                JOptionPane.showMessageDialog(this, "Last name length must be between 2 and 80 characters!", "Warning: Invalid Input!", JOptionPane.ERROR_MESSAGE);

                lNameTextField.grabFocus(); // Gets focus to lNameTextField 

            } else if (contactNoinput.isEmpty() || contactNoinput.equals("Mobile Number")) {
                // Check if the contactNoTextField is empty OR matches the placeholder text "Mobile Number"

                JOptionPane.showMessageDialog(this, "Please Enter Your Mobile Number!", "Warning: Input Field Empty", JOptionPane.ERROR_MESSAGE);

                contactNoTextField.grabFocus(); // Gets focus to contactNoTextField 

            } else if (!contactNoinput.matches("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$")) {
                // Handle invalid contactNoTextField contact formats 

                JOptionPane.showMessageDialog(this, "Invalid Mobile Number!", "Warning: Invalid Input!", JOptionPane.WARNING_MESSAGE);

                contactNoTextField.grabFocus(); // Gets focus to contactNoTextField 

            } else if (contactNoinput.length() != 10) {
                // Check if the contactNoinput has 10 characters!

                JOptionPane.showMessageDialog(this, "Contact Number length must have 10 characters!", "Warning: Invalid Input!", JOptionPane.ERROR_MESSAGE);

                contactNoTextField.grabFocus(); // Gets focus to contactNoTextField 

            } else if (emailinput.isEmpty() || emailinput.equals("Email Address")) {
                // Check if the emailinput is empty OR matches the placeholder text "Email Address"

                JOptionPane.showMessageDialog(this, "Please enter your email!", "Warning: Input Field Empty", JOptionPane.WARNING_MESSAGE);

                emailTextField.grabFocus(); // Gets focus to emailTextField 

            } else if (emailinput.length() < 5 || emailinput.length() > 100) {
                // Check if the emailinput is between 5 and 100 characters!

                JOptionPane.showMessageDialog(this, "Email length must be between 5 and 100 characters!", "Warning: Invalid Input!", JOptionPane.ERROR_MESSAGE);

                emailTextField.grabFocus(); // Gets focus to emailTextField 

            } else if (!emailinput.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(com|lk|edu\\.lk|ac\\.lk|gov\\.lk|sch\\.lk|org\\.lk|net|co|in)$")) {
                // Handle invalid emailTextField formats 

                JOptionPane.showMessageDialog(this, "Invalid Email Address!", "Warning: Invalid Input!", JOptionPane.WARNING_MESSAGE);

                emailTextField.grabFocus(); // Gets focus to emailTextField 

            } else if (buttonGroup1.getSelection() == null) {
                // Check if Employee Gender Selected

                JOptionPane.showMessageDialog(this, "Please Select Your Gender!", "Warning: Unknown Gender", JOptionPane.WARNING_MESSAGE);

            } else if (buttonGroup2.getSelection() == null) {
                // Check if Employee Type Selected

                JOptionPane.showMessageDialog(this, "Please Select Your Employee Type!", "Warning: Unknown Employee Type", JOptionPane.WARNING_MESSAGE);

            } else if (selectedBirthDate == null) {
                // Check if Employee Birth Date Selected

                JOptionPane.showMessageDialog(this, "Please Select Your Date Of Birth!", "Warning: Unknown Date Of Birth", JOptionPane.WARNING_MESSAGE);

                dateOfBirthDayChooser.grabFocus(); // Gets focus to dateOfBirthDayChooser 

            } else if (!isValidPastDate(selectedBirthDate)) {
                // Check if Employee Selected Birth Date is in Past or not

                JOptionPane.showMessageDialog(this, "Please Select a Valid Birthday!", "Warning: Invalid Date Of Birth", JOptionPane.WARNING_MESSAGE);

                dateOfBirthDayChooser.grabFocus(); // Gets focus to dateOfBirthDayChooser 

            } else if (selectedHireDate == null) {
                // Check if Employee Hire Date Selected

                JOptionPane.showMessageDialog(this, "Please Select Your Date Of Hire!", "Warning: Unknown Date Of Hire", JOptionPane.WARNING_MESSAGE);

                dateOfHireDayChooser.grabFocus(); // Gets focus to dateOfHireDayChooser 

            } else if (!isValidPastDate(selectedHireDate)) {
                // Check if Employee Selected Hired Date is in Past or not

                JOptionPane.showMessageDialog(this, "Please Select a Valid Hired day!", "Warning: Invalid Date Of Hire", JOptionPane.WARNING_MESSAGE);

                dateOfHireDayChooser.grabFocus(); // Gets focus to dateOfHireDayChooser 

            } else if (addressloadId.isEmpty()) {
                // Check if the emailinput is empty OR matches the placeholder text "Email Address"

                JOptionPane.showMessageDialog(this, "Please Add Your Address!", "Warning: Input Field Empty", JOptionPane.WARNING_MESSAGE);

                String empID = employeeIdTextField.getText();

                // Creating EmployeeAddress from button click
                EmployeeAddress address = new EmployeeAddress(this, true, empID);
                address.setVisible(true);

                loadAddressId();
                loadAddressFromAddressId();

            } else {

                ResultSet resultSet = MySql.executeSearch("SELECT * FROM `employee` WHERE `email` = '" + emailinput + "' OR `nic` = '" + nicinput + "' OR `contact_number` = '" + contactNoinput + "'");

                if (resultSet.next()) {

                    JOptionPane.showMessageDialog(this, "This Employee Already Registered", "Warning: Invalid Employee!", JOptionPane.WARNING_MESSAGE);

                    return; // Stop further execution

                } else {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    StringBuilder query = new StringBuilder("INSERT INTO `employee` "
                            + "(`employee_id`,`nic`,`first_name`,`last_name`,`contact_number`,`email`,"
                            + "`date_of_birth`,`date_of_hire`,`date_of_final`,"
                            + "`gender_gender_id`,`employee_address_em_address_id`,`employee_type_employee_type_id`) "
                            + "VALUES('" + employeeID + "', '" + nicinput + "', '" + fNameinput + "', '" + lNameinput + "', "
                            + "'" + contactNoinput + "', '" + emailinput + "', '" + sdf.format(selectedBirthDate) + "', '" + sdf.format(selectedHireDate) + "', ");

                    if (Intern) {

                        if (selectedFinalDate == null) {
                            // Check if Employee Intern Final Date Selected

                            JOptionPane.showMessageDialog(this, "Please Select Your Date Of Final!", "Warning: Unknown Date Of Final", JOptionPane.WARNING_MESSAGE);

                            dateOfFinalDayChooser.grabFocus(); // Gets focus to dateOfFinalDayChooser 

                            return; // Stop further execution

                        } else if (!isValidFutureDate(selectedFinalDate)) {
                            // Check if Employee Selected Hired Date is in Future or not

                            JOptionPane.showMessageDialog(this, "Please Select a Valid Final day!", "Warning: Invalid Date Of Final", JOptionPane.WARNING_MESSAGE);

                            dateOfFinalDayChooser.grabFocus(); // Gets focus to dateOfFinalDayChooser 

                            return; // Stop further execution

                        } else {

                            query.append("'").append(sdf.format(selectedFinalDate)).append("', ");

                        }

                    } else {

                        query.append("NULL, ");

                    }

                    if (Male) {

                        query.append("'").append(1).append("', ");

                    } else if (Female) {

                        query.append("'").append(2).append("', ");

                    }

                    query.append("'").append(addressId).append("', ");

                    if (FullTime) {

                        query.append("'").append(1).append("')");

                    } else if (PartTime) {

                        query.append("'").append(2).append("')");

                    } else if (Intern) {

                        query.append("'").append(3).append("')");

                    }

                    MySql.executeUpdate(query.toString());

                    // Success 
                    JOptionPane.showMessageDialog(this, "Employee Registered Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Confirm Reset
                    int confirm = JOptionPane.showConfirmDialog(this,"""
                                                                     Are you sure you want to Reset to Add New Employee Data?
                                                                     * Select No to Update Current Employee
                                                                     """, "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {

                        reset();
                        addressViewPlaceholders();

                    } else {
                        
                        submitButton.setVisible(false);
                        updateButton.setVisible(true);
                        generateButton.setVisible(false);
                        return;
                        
                    }


                }

            }

        } catch (Exception e) {

            // Show an error message when an any exception occured.
            JOptionPane.showMessageDialog(this, "Error Adding Employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

        }

    }

    // Method to load Manage Employee Updating Employee Id and some settings
    private void manageEmployeeIdSettingsLoad() {

        // checks if String employeeData is not null
        if (empId != null) {

            // sets the Employee Id 
            employeeIdTextField.setText(empId);

            // hides generateId button
            generateButton.setVisible(false);

            // Sets Hand Icon to select Employee ID
            employeeIdTextField.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Sets back to Employee Id Color origin
            employeeIdTextField.setForeground(Color.BLACK);

            // Enable the text field (if it was disabled)
            employeeIdTextField.setEnabled(true);

            // Request focus to the text field
            employeeIdTextField.setFocusable(true);

            // Disable Edit text field
            nicTextField.setEditable(false);

            // get fNameTextField focus
            fNameTextField.grabFocus();

            manageEmployeeOtherDataLoad();

        }

    }

    // Method to load Manage Employee Updating data
    private void manageEmployeeOtherDataLoad() {

        try {

            // search query
            ResultSet resultSet = MySql.executeSearch("SELECT `employee_id`, `nic`, `first_name`, `last_name`, `contact_number`, `email`, `date_of_birth`, `date_of_hire`, `date_of_final`, "
                    + "`gender_name`, `em_address_id`, `postal_code`, `address_line01`, `address_line02`, `province_name`, `district_name`, `city_name`, `type_name` "
                    + "FROM `employee` LEFT JOIN `department`  ON `employee`.`department_department_id` = `department`.`department_id` "
                    + "LEFT JOIN `employee_position`  ON `employee`.`employee_position_employee_position_id` = `employee_position`.`employee_position_id` "
                    + "INNER JOIN `employee_type`  ON `employee`.`employee_type_employee_type_id` = `employee_type`.`employee_type_id` "
                    + "LEFT JOIN `employee_status`  ON `employee`.`employee_status_employee_status_id` = `employee_status`.`employee_status_id` "
                    + "INNER JOIN `gender`  ON `employee`.`gender_gender_id` = `gender`.`gender_id` "
                    + "INNER JOIN `employee_address`  ON `employee`.`employee_address_em_address_id` = `employee_address`.`em_address_id` "
                    + "INNER JOIN `province` ON `employee_address`.`province_province_id` = `province`.`province_id` "
                    + "INNER JOIN `district` ON `employee_address`.`district_district_id` = `district`.`district_id` "
                    + "INNER JOIN `city` ON `employee_address`.`city_city_id` = `city`.`city_id` "
                    + "WHERE `employee`.`employee_id` = '" + empId + "'");

            // to check available result
            boolean hasData = false;

            // query row checker and updater loop
            while (resultSet.next()) {

                // if there's a data boolean value will be true
                hasData = true;

                java.sql.Date BirthDate = resultSet.getDate("date_of_birth");
                java.sql.Date HireDate = resultSet.getDate("date_of_hire");
                java.sql.Date FinalDate = resultSet.getDate("date_of_final");

                // Sets back to Employee Data TextFields Color origin
                nicTextField.setForeground(Color.BLACK);
                fNameTextField.setForeground(Color.BLACK);
                lNameTextField.setForeground(Color.BLACK);
                contactNoTextField.setForeground(Color.BLACK);
                emailTextField.setForeground(Color.BLACK);

                // Sets Selected Employee Data to Employee Components
                nicTextField.setText(resultSet.getString("nic"));
                fNameTextField.setText(resultSet.getString("first_name"));
                lNameTextField.setText(resultSet.getString("last_name"));
                contactNoTextField.setText(resultSet.getString("contact_number"));
                emailTextField.setText(resultSet.getString("email"));
                dateOfBirthDayChooser.setDate(BirthDate);
                dateOfHireDayChooser.setDate(HireDate);
                dateOfFinalDayChooser.setDate(FinalDate);

                // Employee Gender radio buttons load
                switch (resultSet.getString("gender_name")) {
                    case "Male" -> // Select the radio button
                        maleRadio.setSelected(true);
                    case "Female" -> // Select the radio button
                        femaleRadio.setSelected(true);
                    default -> {
                        buttonGroup1.clearSelection();
                    }
                }

                // Employee Type radio buttons load
                switch (resultSet.getString("type_name")) {
                    case "Full Time" -> // Select the fullTimeRadio button
                        fullTimeRadio.setSelected(true);
                    case "Part Time" -> // Select the partTimeRadio button
                        partTimeRadio.setSelected(true);
                    case "Intern" -> // Select the internRadio button
                        internRadio.setSelected(true);
                    default -> {
                        buttonGroup1.clearSelection();
                    }
                }

                // Sets back to Address TextFields Color origin
                postalcodeViewTextField.setForeground(Color.BLACK);
                AddressLine1ViewTextField.setForeground(Color.BLACK);
                AddressLine2ViewTextField.setForeground(Color.BLACK);
                ProvinceViewTextField.setForeground(Color.BLACK);
                DistrictViewTextField.setForeground(Color.BLACK);
                CityViewTextField.setForeground(Color.BLACK);

                // Sets Address Data to Address View TextFields
                addressIDTextField.setText(resultSet.getString("em_address_id"));
                postalcodeViewTextField.setText(resultSet.getString("postal_code"));
                AddressLine1ViewTextField.setText(resultSet.getString("address_line01"));
                AddressLine2ViewTextField.setText(resultSet.getString("address_line02"));
                ProvinceViewTextField.setText(resultSet.getString("province_name"));
                DistrictViewTextField.setText(resultSet.getString("district_name"));
                CityViewTextField.setText(resultSet.getString("city_name"));

                // Enable the textfields (if it was disabled)
                postalcodeViewTextField.setEnabled(true);
                AddressLine1ViewTextField.setEnabled(true);
                AddressLine2ViewTextField.setEnabled(true);
                ProvinceViewTextField.setEnabled(true);
                DistrictViewTextField.setEnabled(true);
                CityViewTextField.setEnabled(true);

                // Request focus to the textfields
                postalcodeViewTextField.setFocusable(true);
                AddressLine1ViewTextField.setFocusable(true);
                AddressLine2ViewTextField.setFocusable(true);
                ProvinceViewTextField.setFocusable(true);
                DistrictViewTextField.setFocusable(true);
                CityViewTextField.setFocusable(true);

            }

            // checks if data loaded from query if not shows a message
            if (!hasData) {

                JOptionPane.showMessageDialog(this, "No data found for employees.", "Info", JOptionPane.INFORMATION_MESSAGE);

            }

        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    // Employee Data Update Method
    private void updateEmployee() {

        try {

            // Get input values as variables from Textfields and ComboBoxes
            String employeeID = employeeIdTextField.getText();
            String nicinput = nicTextField.getText().trim();
            String fNameinput = fNameTextField.getText().trim();
            String lNameinput = lNameTextField.getText().trim();
            String contactNoinput = contactNoTextField.getText().trim();
            String emailinput = emailTextField.getText().trim();
            String addressloadId = addressIDTextField.getText().trim();

            Boolean Male = maleRadio.isSelected();
            Boolean Female = femaleRadio.isSelected();
            Boolean FullTime = fullTimeRadio.isSelected();
            Boolean PartTime = partTimeRadio.isSelected();
            Boolean Intern = internRadio.isSelected();

            Date selectedBirthDate = dateOfBirthDayChooser.getDate();
            Date selectedHireDate = dateOfHireDayChooser.getDate();
            Date selectedFinalDate = dateOfFinalDayChooser.getDate();

            // Validation checks for required fields
            if (employeeID.equals("Click to Generate ID") || employeeID.isEmpty()) {
                // Check if the employeeIdTextField matches the placeholder text "Click to Generate ID"

                JOptionPane.showMessageDialog(this, "Error Loading Employee ID!", "Warning: Missing Employee ID", JOptionPane.WARNING_MESSAGE);

                return;

            } else if (nicinput.isEmpty() || nicinput.equals("NIC Number")) {
                // Check if the nicTextField is empty OR matches the placeholder text "NIC Number"

                JOptionPane.showMessageDialog(this, "Error Loading NIC Number!", "Warning: Input Field Empty", JOptionPane.ERROR_MESSAGE);

                nicTextField.grabFocus(); // Gets focus to nicTextField 

            } else if (!nicinput.matches("^\\d{9}[VXvx]$|^\\d{12}$")) {
                // Check if the nicTextField is empty OR matches the placeholder text "NIC Number"

                JOptionPane.showMessageDialog(this, "Invalid NIC Number!", "Warning: Invalid Input!", JOptionPane.ERROR_MESSAGE);

                nicTextField.grabFocus(); // Gets focus to nicTextField 

            } else if (nicinput.length() < 10 || nicinput.length() > 12) {
                // Check if the lNameinput is between 10 and 12 characters!

                JOptionPane.showMessageDialog(this, "NIC must be 10 or 12 characters long!", "Warning: Invalid Input!", JOptionPane.ERROR_MESSAGE);

                nicTextField.grabFocus(); // Gets focus to nicTextField 

            }

            // Fetch existing values from the database
            String fetchQuery = "SELECT `employee_id`, `nic`, `first_name`, `last_name`, `contact_number`, `email`, `date_of_birth`, `date_of_hire`, `date_of_final`, "
                    + "`gender_name`, `em_address_id`, `postal_code`, `address_line01`, `address_line02`, `province_name`, `district_name`, `city_name`, `type_name` "
                    + "FROM `employee` LEFT JOIN `department`  ON `employee`.`department_department_id` = `department`.`department_id` "
                    + "LEFT JOIN `employee_position`  ON `employee`.`employee_position_employee_position_id` = `employee_position`.`employee_position_id` "
                    + "INNER JOIN `employee_type`  ON `employee`.`employee_type_employee_type_id` = `employee_type`.`employee_type_id` "
                    + "LEFT JOIN `employee_status`  ON `employee`.`employee_status_employee_status_id` = `employee_status`.`employee_status_id` "
                    + "INNER JOIN `gender`  ON `employee`.`gender_gender_id` = `gender`.`gender_id` "
                    + "INNER JOIN `employee_address`  ON `employee`.`employee_address_em_address_id` = `employee_address`.`em_address_id` "
                    + "INNER JOIN `province` ON `employee_address`.`province_province_id` = `province`.`province_id` "
                    + "INNER JOIN `district` ON `employee_address`.`district_district_id` = `district`.`district_id` "
                    + "INNER JOIN `city` ON `employee_address`.`city_city_id` = `city`.`city_id` "
                    + "WHERE `employee`.`employee_id` = '" + employeeID + "'";

            // execute query
            ResultSet rs = MySql.executeSearch(fetchQuery);

            // check if no results
            if (!rs.next()) {

                JOptionPane.showMessageDialog(this, "Employee ID not found in the database!", "Error: No Match", JOptionPane.ERROR_MESSAGE);
                return;

            }

            // Get existing values
            String existingFirstName = rs.getString("first_name");
            String existingLastName = rs.getString("last_name");
            String existingContactNo = rs.getString("contact_number");
            String existingEmail = rs.getString("email");
            Date existingDOB = rs.getDate("date_of_birth");
            Date existingHireDate = rs.getDate("date_of_hire");
            Date existingFinalDate = rs.getDate("date_of_final");
            String existingGender = rs.getString("gender_name");
            String existingType = rs.getString("type_name");

            // Compare and construct the UPDATE query
            StringBuilder updateQuery = new StringBuilder("UPDATE `employee` "
                    + "INNER JOIN `gender`  ON `employee`.`gender_gender_id` = `gender`.`gender_id` "
                    + "INNER JOIN `employee_type`  ON `employee`.`employee_type_employee_type_id` = `employee_type`.`employee_type_id` "
                    + "SET ");

            // variable to check if there's an change to update
            boolean changesMade = false;

            // date format
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            if (!fNameinput.equals(existingFirstName)) {
                // Update query String Build first name

                updateQuery.append("`first_name` = '").append(fNameinput).append("', ");

                changesMade = true;

            }

            if (!lNameinput.equals(existingLastName)) {
                // Update query String Build last name

                updateQuery.append("`last_name` = '").append(lNameinput).append("', ");

                changesMade = true;

            }

            if (!contactNoinput.equals(existingContactNo)) {
                // Update query String Build contact no

                updateQuery.append("`contact_number` = '").append(contactNoinput).append("', ");

                changesMade = true;

            }

            if (!emailinput.equals(existingEmail)) {
                // Update query String Build email

                updateQuery.append("`email` = '").append(emailinput).append("', ");

                changesMade = true;

            }

            if (!sdf.format(selectedBirthDate).equals(sdf.format(existingDOB))) {
                // Update query String Build BirthDate

                updateQuery.append("`date_of_birth` = '").append(sdf.format(selectedBirthDate)).append("', ");

                changesMade = true;

            }

            if (!sdf.format(selectedHireDate).equals(sdf.format(existingHireDate))) {
                // Update query String Build HireDate

                updateQuery.append("`date_of_hire` = '").append(sdf.format(selectedHireDate)).append("', ");

                changesMade = true;

            }

            if (fNameinput.isEmpty() || fNameinput.equals("First Name")) {
                // Check if the fNameTextField is empty OR matches the placeholder text "First Name"

                JOptionPane.showMessageDialog(this, "Please Enter Your First Name!", "Warning: Input Field Empty", JOptionPane.ERROR_MESSAGE);

                fNameTextField.grabFocus(); // Gets focus to fNameTextField 

            } else if (!fNameinput.matches("[a-zA-Z\\s]+")) {
                // Handle invalid fNameinput (numbers or symbols) 

                JOptionPane.showMessageDialog(this, "Only letters are allowed in First Name Input.", "Warning: Invalid input!", JOptionPane.ERROR_MESSAGE);

                fNameTextField.grabFocus(); // Gets focus to fNameTextField 

            } else if (fNameinput.length() < 2 || fNameinput.length() > 80) {
                // Check if the fNameinput is between 2 and 80 characters!

                JOptionPane.showMessageDialog(this, "First name length must be between 2 and 80 characters!", "Warning: Invalid Input!", JOptionPane.ERROR_MESSAGE);

                fNameTextField.grabFocus(); // Gets focus to fNameTextField 

            } else if (lNameinput.isEmpty() || lNameinput.equals("Last Name")) {
                // Check if the lNameTextField is empty OR matches the placeholder text "Last Name"

                JOptionPane.showMessageDialog(this, "Please Enter Your Last Name!", "Warning: Input Field Empty", JOptionPane.ERROR_MESSAGE);

                lNameTextField.grabFocus(); // Gets focus to lNameTextField 

            } else if (!fNameinput.matches("[a-zA-Z\\s]+")) {
                // Handle invalid lNameinput (numbers or symbols) 

                JOptionPane.showMessageDialog(this, "Only letters are allowed in Last Name Input.", "Warning: Invalid input!", JOptionPane.ERROR_MESSAGE);

                lNameTextField.grabFocus(); // Gets focus to lNameTextField 

            } else if (lNameinput.length() < 2 || lNameinput.length() > 80) {
                // Check if the lNameinput is between 2 and 80 characters!

                JOptionPane.showMessageDialog(this, "Last name length must be between 2 and 80 characters!", "Warning: Invalid Input!", JOptionPane.ERROR_MESSAGE);

                lNameTextField.grabFocus(); // Gets focus to lNameTextField 

            } else if (contactNoinput.isEmpty() || contactNoinput.equals("Mobile Number")) {
                // Check if the contactNoTextField is empty OR matches the placeholder text "Mobile Number"

                JOptionPane.showMessageDialog(this, "Please Enter Your Mobile Number!", "Warning: Input Field Empty", JOptionPane.ERROR_MESSAGE);

                contactNoTextField.grabFocus(); // Gets focus to contactNoTextField 

            } else if (!contactNoinput.matches("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$")) {
                // Handle invalid contactNoTextField contact formats 

                JOptionPane.showMessageDialog(this, "Invalid Mobile Number!", "Warning: Invalid Input!", JOptionPane.WARNING_MESSAGE);

                contactNoTextField.grabFocus(); // Gets focus to contactNoTextField 

            } else if (contactNoinput.length() != 10) {
                // Check if the contactNoinput has 10 characters!

                JOptionPane.showMessageDialog(this, "Contact Number length must have 10 characters!", "Warning: Invalid Input!", JOptionPane.ERROR_MESSAGE);

                contactNoTextField.grabFocus(); // Gets focus to contactNoTextField 

            } else if (emailinput.isEmpty() || emailinput.equals("Email Address")) {
                // Check if the emailinput is empty OR matches the placeholder text "Email Address"

                JOptionPane.showMessageDialog(this, "Please enter your email!", "Warning: Input Field Empty", JOptionPane.WARNING_MESSAGE);

                emailTextField.grabFocus(); // Gets focus to emailTextField 

            } else if (emailinput.length() < 5 || emailinput.length() > 100) {
                // Check if the emailinput is between 5 and 100 characters!

                JOptionPane.showMessageDialog(this, "Email length must be between 5 and 100 characters!", "Warning: Invalid Input!", JOptionPane.ERROR_MESSAGE);

                emailTextField.grabFocus(); // Gets focus to emailTextField 

            } else if (!emailinput.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(com|lk|edu\\.lk|ac\\.lk|gov\\.lk|sch\\.lk|org\\.lk|net|co|in)$")) {
                // Handle invalid emailTextField formats 

                JOptionPane.showMessageDialog(this, "Invalid Email Address!", "Warning: Invalid Input!", JOptionPane.WARNING_MESSAGE);

                emailTextField.grabFocus(); // Gets focus to emailTextField 

            } else if (buttonGroup1.getSelection() == null) {
                // Check if Employee Gender Selected

                JOptionPane.showMessageDialog(this, "Please Select Your Gender!", "Warning: Unknown Gender", JOptionPane.WARNING_MESSAGE);

            } else if (buttonGroup2.getSelection() == null) {
                // Check if Employee Type Selected

                JOptionPane.showMessageDialog(this, "Please Select Your Employee Type!", "Warning: Unknown Employee Type", JOptionPane.WARNING_MESSAGE);

            } else if (selectedBirthDate == null) {
                // Check if Employee Birth Date Selected

                JOptionPane.showMessageDialog(this, "Please Select Your Date Of Birth!", "Warning: Unknown Date Of Birth", JOptionPane.WARNING_MESSAGE);

                dateOfBirthDayChooser.grabFocus(); // Gets focus to dateOfBirthDayChooser 

            } else if (!isValidPastDate(selectedBirthDate)) {
                // Check if Employee Selected Birth Date is in Past or not

                JOptionPane.showMessageDialog(this, "Please Select a Valid Birthday!", "Warning: Invalid Date Of Birth", JOptionPane.WARNING_MESSAGE);

                dateOfBirthDayChooser.grabFocus(); // Gets focus to dateOfBirthDayChooser 

            } else if (selectedHireDate == null) {
                // Check if Employee Hire Date Selected

                JOptionPane.showMessageDialog(this, "Please Select Your Date Of Hire!", "Warning: Unknown Date Of Hire", JOptionPane.WARNING_MESSAGE);

                dateOfHireDayChooser.grabFocus(); // Gets focus to dateOfHireDayChooser 

            } else if (!isValidPastDate(selectedHireDate)) {
                // Check if Employee Selected Hired Date is in Past or not

                JOptionPane.showMessageDialog(this, "Please Select a Valid Hired day!", "Warning: Invalid Date Of Hire", JOptionPane.WARNING_MESSAGE);

                dateOfHireDayChooser.grabFocus(); // Gets focus to dateOfHireDayChooser 

            } else if (Intern) {

                if (selectedFinalDate == null) {
                    // Check if Employee Intern Final Date Selected

                    JOptionPane.showMessageDialog(this, "Please Select Your Date Of Final!", "Warning: Unknown Date Of Final", JOptionPane.WARNING_MESSAGE);

                    dateOfFinalDayChooser.grabFocus(); // Gets focus to dateOfFinalDayChooser 

                    return; // Stop further execution

                } else if (!isValidFutureDate(selectedFinalDate)) {
                    // Check if Employee Selected Hired Date is in Future or not

                    JOptionPane.showMessageDialog(this, "Please Select a Valid Final day!", "Warning: Invalid Date Of Final", JOptionPane.WARNING_MESSAGE);

                    dateOfFinalDayChooser.grabFocus(); // Gets focus to dateOfFinalDayChooser 

                    return; // Stop further execution

                } else if ((selectedFinalDate != null && existingFinalDate == null)
                        || (selectedFinalDate != null && !sdf.format(selectedFinalDate).equals(sdf.format(existingFinalDate)))) {
                    // Update query String Build FinalDate

                    updateQuery.append("`date_of_final` = '").append(sdf.format(selectedFinalDate)).append("', ");

                    changesMade = true;

                }

            } else {

                // get new gender value
                String newGender = Male ? "Male" : (Female ? "Female" : null);

                if (newGender == null ? existingGender != null : !newGender.equals(existingGender)) {
                    // Update query String Build Gender

                    updateQuery.append("`gender_name` = '").append(newGender).append("', ");

                    changesMade = true;

                }

                // get new type value
                String newType = FullTime ? "Full Time" : (PartTime ? "Part Time" : (Intern ? "Intern" : null));

                if (newType == null ? existingType != null : !newType.equals(existingType)) {
                    // Update query String Build Type

                    updateQuery.append("`type_name` = '").append(newType).append("', ");

                    changesMade = true;

                }

                if (!changesMade) {
                    // if no changes happenned

                    JOptionPane.showMessageDialog(this, "No changes detected to Update!", "Info: No New Data", JOptionPane.INFORMATION_MESSAGE);

                    return;

                }

                // Remove the trailing comma and space, and add the WHERE clause
                updateQuery.setLength(updateQuery.length() - 2); // Remove last comma and space

                updateQuery.append(" WHERE `employee_id` = '").append(employeeID).append("'");

                // Execute the UPDATE query
                MySql.executeUpdate(updateQuery.toString());

                // Success
                JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);

            }

            if (addressloadId.isEmpty()) {
                // Check if the emailinput is empty OR matches the placeholder text "Email Address"

                JOptionPane.showMessageDialog(this, "Current Employee Address Not Found!", "Warning: Input Field Empty", JOptionPane.WARNING_MESSAGE);

                // Creating EmployeeAddress from button click
                EmployeeAddress address = new EmployeeAddress(this, true, empId);
                address.setVisible(true);

                loadAddressId();
                loadAddressFromAddressId();

            }

        } catch (Exception e) {

            // Show an error message when an any exception occured.
            JOptionPane.showMessageDialog(this, "Error Adding Employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

        }

    }

    // Method to validate the selected date as a Past Date
    public static boolean isValidPastDate(Date selectedDate) {

        // Convert Date to LocalDate
        LocalDate selectedLocalDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();

        // Check if the selected date is not after today
        return !selectedLocalDate.isAfter(today);

    }

    // Method to validate the selected date as a Future Date
    public static boolean isValidFutureDate(Date selectedDate) {

        // Convert Date to LocalDate
        LocalDate selectedLocalDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();

        // Check if the selected date is not before today
        return !selectedLocalDate.isBefore(today);

    }

    private void dateChooserListeners() {

        dateOfBirthDayChooser.getDateEditor().getUiComponent().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dateOfBirthDayChooserKeyPressed(evt);
            }
        });

        dateOfHireDayChooser.getDateEditor().getUiComponent().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dateOfHireDayChooserKeyPressed(evt);
            }
        });

        dateOfFinalDayChooser.getDateEditor().getUiComponent().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dateOfFinalDayChooserKeyPressed(evt);
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        HeaderPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        bodyPanel = new javax.swing.JPanel();
        employeeIdLabel = new javax.swing.JLabel();
        employeeIdTextField = new javax.swing.JTextField();
        generateButton = new javax.swing.JButton();
        fNameLabel = new javax.swing.JLabel();
        fNameTextField = new javax.swing.JTextField();
        dOBLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        emailTextField = new javax.swing.JTextField();
        lNameLabel = new javax.swing.JLabel();
        lNameTextField = new javax.swing.JTextField();
        contactNoLabel = new javax.swing.JLabel();
        contactNoTextField = new javax.swing.JTextField();
        dateOfHireLabel = new javax.swing.JLabel();
        employeeTypeLabel = new javax.swing.JLabel();
        fullTimeRadio = new javax.swing.JRadioButton();
        partTimeRadio = new javax.swing.JRadioButton();
        internRadio = new javax.swing.JRadioButton();
        refreshButton = new javax.swing.JButton();
        dateOfBirthDayChooser = new com.toedter.calendar.JDateChooser();
        dateOfHireDayChooser = new com.toedter.calendar.JDateChooser();
        dateOfFinalDayChooser = new com.toedter.calendar.JDateChooser();
        toLabel = new javax.swing.JLabel();
        emailLabel1 = new javax.swing.JLabel();
        addressButton = new com.k33ptoo.components.KButton();
        submitButton = new com.k33ptoo.components.KButton();
        updateButton = new com.k33ptoo.components.KButton();
        employeeGenderLabel = new javax.swing.JLabel();
        maleRadio = new javax.swing.JRadioButton();
        femaleRadio = new javax.swing.JRadioButton();
        AddressLine1ViewTextField = new javax.swing.JTextField();
        AddressLine2ViewTextField = new javax.swing.JTextField();
        ProvinceViewTextField = new javax.swing.JTextField();
        DistrictViewTextField = new javax.swing.JTextField();
        CityViewTextField = new javax.swing.JTextField();
        nicTextField = new javax.swing.JTextField();
        employeeTypeLabel2 = new javax.swing.JLabel();
        addressIDTextField = new javax.swing.JTextField();
        postalcodeViewTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        BackToDashboardPanel = new javax.swing.JPanel();
        BackToDashboardButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        HeaderPanel.setBackground(new java.awt.Color(153, 153, 153));
        HeaderPanel.setPreferredSize(new java.awt.Dimension(732, 60));

        jLabel1.setFont(new java.awt.Font("Audiowide", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add Employee");

        javax.swing.GroupLayout HeaderPanelLayout = new javax.swing.GroupLayout(HeaderPanel);
        HeaderPanel.setLayout(HeaderPanelLayout);
        HeaderPanelLayout.setHorizontalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addGap(280, 280, 280)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                .addGap(280, 280, 280))
        );
        HeaderPanelLayout.setVerticalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addGap(15, 15, 15))
        );

        getContentPane().add(HeaderPanel, java.awt.BorderLayout.PAGE_START);

        bodyPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        employeeIdLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        employeeIdLabel.setText("Employee ID");

        employeeIdTextField.setEditable(false);
        employeeIdTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        employeeIdTextField.setToolTipText("Please Generate Employee ID!");
        employeeIdTextField.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        employeeIdTextField.setEnabled(false);
        employeeIdTextField.setFocusable(false);

        generateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/generate.png"))); // NOI18N
        generateButton.setToolTipText("Click Here to Generate ID!");
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });
        generateButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                generateButtonKeyPressed(evt);
            }
        });

        fNameLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        fNameLabel.setText("First Name");

        fNameTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        fNameTextField.setToolTipText("Please Enter Your First Name!");
        fNameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fNameTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                fNameTextFieldFocusLost(evt);
            }
        });
        fNameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fNameTextFieldKeyPressed(evt);
            }
        });

        dOBLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        dOBLabel.setText("NIC");

        emailLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        emailLabel.setText("Email");

        emailTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        emailTextField.setToolTipText("Please Enter a Valid Email like user@example.com");
        emailTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                emailTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                emailTextFieldFocusLost(evt);
            }
        });
        emailTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                emailTextFieldKeyPressed(evt);
            }
        });

        lNameLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        lNameLabel.setText("Last Name");

        lNameTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lNameTextField.setToolTipText("Please Enter Your Last Name!");
        lNameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lNameTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lNameTextFieldFocusLost(evt);
            }
        });
        lNameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lNameTextFieldKeyPressed(evt);
            }
        });

        contactNoLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        contactNoLabel.setText("Contact Number");

        contactNoTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        contactNoTextField.setToolTipText("Please Enter Your Mobile Number!");
        contactNoTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                contactNoTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                contactNoTextFieldFocusLost(evt);
            }
        });
        contactNoTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                contactNoTextFieldKeyPressed(evt);
            }
        });

        dateOfHireLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        dateOfHireLabel.setText("Date Of Hire");

        employeeTypeLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        employeeTypeLabel.setText("Employee Type");

        buttonGroup2.add(fullTimeRadio);
        fullTimeRadio.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        fullTimeRadio.setText("Full Time");
        fullTimeRadio.setToolTipText("Please Select Your Employee Type!");
        fullTimeRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullTimeRadioActionPerformed(evt);
            }
        });
        fullTimeRadio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fullTimeRadioKeyPressed(evt);
            }
        });

        buttonGroup2.add(partTimeRadio);
        partTimeRadio.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        partTimeRadio.setText("Part Time");
        partTimeRadio.setToolTipText("Please Select Your Employee Type!");
        partTimeRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partTimeRadioActionPerformed(evt);
            }
        });
        partTimeRadio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                partTimeRadioKeyPressed(evt);
            }
        });

        buttonGroup2.add(internRadio);
        internRadio.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        internRadio.setText("Intern");
        internRadio.setToolTipText("Please Select Your Employee Type!");
        internRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                internRadioActionPerformed(evt);
            }
        });
        internRadio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                internRadioKeyPressed(evt);
            }
        });

        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/refresh.png"))); // NOI18N
        refreshButton.setToolTipText("Click Here to Refresh & Clear All Data You Filled!");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });
        refreshButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                refreshButtonKeyPressed(evt);
            }
        });

        dateOfBirthDayChooser.setToolTipText("Please Select Your Date of Birth!");
        dateOfBirthDayChooser.setDateFormatString("yyyy-MM-dd");
        dateOfBirthDayChooser.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        dateOfBirthDayChooser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dateOfBirthDayChooserKeyPressed(evt);
            }
        });

        dateOfHireDayChooser.setToolTipText("Please Select Your Date of Hire!");
        dateOfHireDayChooser.setDateFormatString("yyyy-MM-dd");
        dateOfHireDayChooser.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        dateOfHireDayChooser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dateOfHireDayChooserKeyPressed(evt);
            }
        });

        dateOfFinalDayChooser.setToolTipText("Please Select Your Date of Final!");
        dateOfFinalDayChooser.setDateFormatString("yyyy-MM-dd");
        dateOfFinalDayChooser.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        dateOfFinalDayChooser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dateOfFinalDayChooserKeyPressed(evt);
            }
        });

        toLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        toLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        toLabel.setText("to");

        emailLabel1.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        emailLabel1.setText("Address");

        addressButton.setText("Add");
        addressButton.setToolTipText("Click Here to Add Employee Address!");
        addressButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addressButton.setkEndColor(new java.awt.Color(0, 204, 204));
        addressButton.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        addressButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        addressButton.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        addressButton.setkPressedColor(new java.awt.Color(0, 102, 153));
        addressButton.setkSelectedColor(new java.awt.Color(0, 102, 153));
        addressButton.setkStartColor(new java.awt.Color(0, 102, 153));
        addressButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressButtonActionPerformed(evt);
            }
        });
        addressButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                addressButtonKeyPressed(evt);
            }
        });

        submitButton.setText("Submit");
        submitButton.setToolTipText("Click Here to Register Current Employee!");
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

        updateButton.setText("Update");
        updateButton.setToolTipText("Click Here to Update Current Employee Data!");
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
        updateButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                updateButtonKeyPressed(evt);
            }
        });

        employeeGenderLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        employeeGenderLabel.setText("Gender");

        buttonGroup1.add(maleRadio);
        maleRadio.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        maleRadio.setText("Male");
        maleRadio.setToolTipText("Please Select Your Gender!");
        maleRadio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                maleRadioKeyPressed(evt);
            }
        });

        buttonGroup1.add(femaleRadio);
        femaleRadio.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        femaleRadio.setText("Female");
        femaleRadio.setToolTipText("Please Select Your Gender!");
        femaleRadio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                femaleRadioKeyPressed(evt);
            }
        });

        AddressLine1ViewTextField.setEditable(false);
        AddressLine1ViewTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        AddressLine1ViewTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AddressLine1ViewTextField.setToolTipText("To Edit, Use Manage Employee Address!");
        AddressLine1ViewTextField.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        AddressLine1ViewTextField.setEnabled(false);
        AddressLine1ViewTextField.setFocusable(false);
        AddressLine1ViewTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddressLine1ViewTextFieldKeyPressed(evt);
            }
        });

        AddressLine2ViewTextField.setEditable(false);
        AddressLine2ViewTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        AddressLine2ViewTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AddressLine2ViewTextField.setToolTipText("To Edit, Use Manage Employee Address!");
        AddressLine2ViewTextField.setEnabled(false);
        AddressLine2ViewTextField.setFocusable(false);
        AddressLine2ViewTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddressLine2ViewTextFieldKeyPressed(evt);
            }
        });

        ProvinceViewTextField.setEditable(false);
        ProvinceViewTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ProvinceViewTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ProvinceViewTextField.setToolTipText("To Edit, Use Manage Employee Address!");
        ProvinceViewTextField.setEnabled(false);
        ProvinceViewTextField.setFocusable(false);
        ProvinceViewTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProvinceViewTextFieldKeyPressed(evt);
            }
        });

        DistrictViewTextField.setEditable(false);
        DistrictViewTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        DistrictViewTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        DistrictViewTextField.setToolTipText("To Edit, Use Manage Employee Address!");
        DistrictViewTextField.setEnabled(false);
        DistrictViewTextField.setFocusable(false);
        DistrictViewTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DistrictViewTextFieldKeyPressed(evt);
            }
        });

        CityViewTextField.setEditable(false);
        CityViewTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        CityViewTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        CityViewTextField.setToolTipText("To Edit, Use Manage Employee Address!");
        CityViewTextField.setEnabled(false);
        CityViewTextField.setFocusable(false);
        CityViewTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CityViewTextFieldKeyPressed(evt);
            }
        });

        nicTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        nicTextField.setToolTipText("Please Enter Your National Identity Card Number!");
        nicTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nicTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                nicTextFieldFocusLost(evt);
            }
        });
        nicTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nicTextFieldKeyPressed(evt);
            }
        });

        employeeTypeLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        employeeTypeLabel2.setText("Date Of Birth");

        addressIDTextField.setEditable(false);
        addressIDTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        addressIDTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        addressIDTextField.setFocusable(false);

        postalcodeViewTextField.setEditable(false);
        postalcodeViewTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        postalcodeViewTextField.setToolTipText("To Edit, Use Manage Employee Address!");
        postalcodeViewTextField.setEnabled(false);
        postalcodeViewTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                postalcodeViewTextFieldKeyPressed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/info.png"))); // NOI18N
        jLabel2.setToolTipText("Use Manage Employee Address to Update Employee Address!");

        javax.swing.GroupLayout bodyPanelLayout = new javax.swing.GroupLayout(bodyPanel);
        bodyPanel.setLayout(bodyPanelLayout);
        bodyPanelLayout.setHorizontalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bodyPanelLayout.createSequentialGroup()
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bodyPanelLayout.createSequentialGroup()
                                .addComponent(employeeIdLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                .addGap(367, 367, 367))
                            .addGroup(bodyPanelLayout.createSequentialGroup()
                                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bodyPanelLayout.createSequentialGroup()
                                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(fNameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bodyPanelLayout.createSequentialGroup()
                                                .addComponent(emailLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(34, 34, 34))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bodyPanelLayout.createSequentialGroup()
                                                .addComponent(lNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(2, 2, 2)))
                                        .addGap(361, 361, 361))
                                    .addGroup(bodyPanelLayout.createSequentialGroup()
                                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(bodyPanelLayout.createSequentialGroup()
                                                .addComponent(employeeIdTextField)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(generateButton))
                                            .addComponent(emailTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lNameTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(fNameTextField, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addGap(50, 50, 50)))
                                .addGap(19, 19, 19))
                            .addGroup(bodyPanelLayout.createSequentialGroup()
                                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(bodyPanelLayout.createSequentialGroup()
                                        .addComponent(emailLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(bodyPanelLayout.createSequentialGroup()
                                        .addComponent(addressButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(addressIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(postalcodeViewTextField)))
                                .addGap(108, 108, 108)))
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bodyPanelLayout.createSequentialGroup()
                                .addComponent(dOBLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(375, 375, 375))
                            .addGroup(bodyPanelLayout.createSequentialGroup()
                                .addComponent(contactNoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(352, 352, 352))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(refreshButton)
                                .addGap(36, 36, 36))
                            .addGroup(bodyPanelLayout.createSequentialGroup()
                                .addComponent(employeeTypeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                .addGap(366, 366, 366))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyPanelLayout.createSequentialGroup()
                                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(bodyPanelLayout.createSequentialGroup()
                                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(bodyPanelLayout.createSequentialGroup()
                                                .addComponent(dateOfHireLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(85, 85, 85))
                                            .addGroup(bodyPanelLayout.createSequentialGroup()
                                                .addComponent(dateOfHireDayChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                                                .addGap(18, 18, 18)
                                                .addComponent(toLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)))
                                        .addComponent(dateOfFinalDayChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                                    .addComponent(nicTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(contactNoTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bodyPanelLayout.createSequentialGroup()
                                        .addComponent(fullTimeRadio)
                                        .addGap(18, 18, 18)
                                        .addComponent(partTimeRadio)
                                        .addGap(18, 18, 18)
                                        .addComponent(internRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bodyPanelLayout.createSequentialGroup()
                                        .addComponent(maleRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(femaleRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dateOfBirthDayChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bodyPanelLayout.createSequentialGroup()
                                        .addComponent(employeeGenderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(29, 29, 29)
                                        .addComponent(employeeTypeLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(34, 34, 34))))
                    .addGroup(bodyPanelLayout.createSequentialGroup()
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AddressLine1ViewTextField)
                            .addComponent(AddressLine2ViewTextField))
                        .addGap(439, 439, 439))
                    .addGroup(bodyPanelLayout.createSequentialGroup()
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ProvinceViewTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                            .addComponent(DistrictViewTextField)
                            .addComponent(CityViewTextField))
                        .addContainerGap())))
        );
        bodyPanelLayout.setVerticalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employeeIdLabel)
                    .addComponent(dOBLabel))
                .addGap(12, 12, 12)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(generateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(employeeIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nicTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fNameLabel)
                    .addComponent(contactNoLabel))
                .addGap(12, 12, 12)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contactNoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bodyPanelLayout.createSequentialGroup()
                        .addComponent(lNameLabel)
                        .addGap(12, 12, 12)
                        .addComponent(lNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bodyPanelLayout.createSequentialGroup()
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(employeeGenderLabel)
                            .addComponent(employeeTypeLabel2))
                        .addGap(12, 12, 12)
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateOfBirthDayChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(maleRadio)
                                .addComponent(femaleRadio)))))
                .addGap(18, 18, 18)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(bodyPanelLayout.createSequentialGroup()
                        .addComponent(emailLabel)
                        .addGap(12, 12, 12)
                        .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(emailLabel1)
                            .addGroup(bodyPanelLayout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)))
                        .addGap(12, 12, 12)
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addressButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addressIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(postalcodeViewTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(bodyPanelLayout.createSequentialGroup()
                        .addComponent(employeeTypeLabel)
                        .addGap(12, 12, 12)
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fullTimeRadio)
                            .addComponent(partTimeRadio)
                            .addComponent(internRadio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dateOfHireLabel)
                        .addGap(12, 12, 12)
                        .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateOfHireDayChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(toLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateOfFinalDayChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(AddressLine1ViewTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddressLine2ViewTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ProvinceViewTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DistrictViewTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CityViewTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(refreshButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );

        getContentPane().add(bodyPanel, java.awt.BorderLayout.CENTER);

        BackToDashboardPanel.setBackground(new java.awt.Color(153, 153, 153));
        BackToDashboardPanel.setPreferredSize(new java.awt.Dimension(732, 50));

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
                .addGap(909, 909, 909))
        );
        BackToDashboardPanelLayout.setVerticalGroup(
            BackToDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackToDashboardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BackToDashboardButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(BackToDashboardPanel, java.awt.BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed

        // Sets Hand Icon to select Employee ID
        employeeIdTextField.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Sets back to Employee Id Color origin
        employeeIdTextField.setForeground(Color.BLACK);

        // Generate Id when button clicked
        long id = System.currentTimeMillis();
        employeeIdTextField.setText("EMP" + String.valueOf(id));

        // Enable the text field (if it was disabled)
        employeeIdTextField.setEnabled(true);

        // Request focus to the text field
        employeeIdTextField.setFocusable(true);

    }//GEN-LAST:event_generateButtonActionPerformed

    private void BackToDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToDashboardButtonActionPerformed

        if (manageEmployee != null) {

            // Refresh the main GUI
            manageEmployee.refreshData();

            // Show the main GUI
            manageEmployee.setVisible(true);

        }

        // To Close AddEmployee(this JFrame) and go back to HR Dashboard(main JFrame)  
        this.dispose();

    }//GEN-LAST:event_BackToDashboardButtonActionPerformed

    private void addressButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressButtonActionPerformed

        addAddress();

    }//GEN-LAST:event_addressButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed

        // Show a confirmation dialog
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to reset the form? This will clear all entered data!", "Reset Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        // Check the user's choice
        if (confirm == JOptionPane.YES_OPTION) {

            // Calling reset method (created method) - clear all data
            reset();

            JOptionPane.showMessageDialog(this, "The form has been reset!", "Reset Successful!", JOptionPane.INFORMATION_MESSAGE);

        } else {

            // User chose 'No' - no action needed
            JOptionPane.showMessageDialog(this, "The form was not reset!", "Action Cancelled!", JOptionPane.INFORMATION_MESSAGE);

        }


    }//GEN-LAST:event_refreshButtonActionPerformed

    private void fNameTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fNameTextFieldFocusGained

        // Checks and set clear the current fNameTextField to enter data to it
        if (fNameTextField.getText().equals("First Name")) {

            fNameTextField.setText("");
            fNameTextField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_fNameTextFieldFocusGained

    private void lNameTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lNameTextFieldFocusGained

        // Checks and set clear the current lNameTextField to enter data to it
        if (lNameTextField.getText().equals("Last Name")) {

            lNameTextField.setText("");
            lNameTextField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_lNameTextFieldFocusGained

    private void contactNoTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_contactNoTextFieldFocusGained

        // Checks and set clear the current contactNoTextField to enter data to it
        if (contactNoTextField.getText().equals("Mobile Number")) {

            contactNoTextField.setText("");
            contactNoTextField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_contactNoTextFieldFocusGained

    private void fNameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fNameTextFieldFocusLost

        // Sets back the placeholder of fNameTextField
        if (fNameTextField.getText().isEmpty()) {

            fNameTextField.setText("First Name");
            fNameTextField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_fNameTextFieldFocusLost

    private void lNameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lNameTextFieldFocusLost

        // Sets back the placeholder of lNameTextField
        if (lNameTextField.getText().isEmpty()) {

            lNameTextField.setText("Last Name");
            lNameTextField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_lNameTextFieldFocusLost

    private void contactNoTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_contactNoTextFieldFocusLost

        // Sets back the placeholder of contactNoTextField
        if (contactNoTextField.getText().isEmpty()) {

            contactNoTextField.setText("Mobile Number");
            contactNoTextField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_contactNoTextFieldFocusLost

    private void emailTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_emailTextFieldFocusLost

        // Sets back the placeholder of emailTextField
        if (emailTextField.getText().isEmpty()) {

            emailTextField.setText("Email Address");
            emailTextField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_emailTextFieldFocusLost

    private void emailTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_emailTextFieldFocusGained

        // Checks and set clear the current emailTextField to enter data to it
        if (emailTextField.getText().equals("Email Address")) {

            emailTextField.setText("");
            emailTextField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_emailTextFieldFocusGained

    private void nicTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nicTextFieldFocusGained

        // Checks and set clear the current emailTextField to enter data to it
        if (nicTextField.getText().equals("NIC Number")) {

            nicTextField.setText("");
            nicTextField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_nicTextFieldFocusGained

    private void nicTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nicTextFieldFocusLost

        // Sets back the placeholder of contactNoTextField
        if (nicTextField.getText().isEmpty()) {

            nicTextField.setText("NIC Number");
            nicTextField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_nicTextFieldFocusLost

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed

        submitEmployee();

    }//GEN-LAST:event_submitButtonActionPerformed

    private void internRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_internRadioActionPerformed

        toLabel.setVisible(internRadio.isSelected());
        dateOfFinalDayChooser.setVisible(internRadio.isSelected());

        SwingUtilities.updateComponentTreeUI(this);

    }//GEN-LAST:event_internRadioActionPerformed

    private void fullTimeRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullTimeRadioActionPerformed

        toLabel.setVisible(false);
        dateOfFinalDayChooser.setVisible(false);

        SwingUtilities.updateComponentTreeUI(this);

    }//GEN-LAST:event_fullTimeRadioActionPerformed

    private void partTimeRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partTimeRadioActionPerformed

        toLabel.setVisible(false);
        dateOfFinalDayChooser.setVisible(false);

        SwingUtilities.updateComponentTreeUI(this);

    }//GEN-LAST:event_partTimeRadioActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed

        updateEmployee();

    }//GEN-LAST:event_updateButtonActionPerformed

    private void generateButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_generateButtonKeyPressed
        // code to add key controls for generateButton

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT -> {

                generateButton.doClick();
                nicTextField.grabFocus();

            }

            case KeyEvent.VK_RIGHT ->
                nicTextField.grabFocus();

            case KeyEvent.VK_DOWN ->
                fNameTextField.grabFocus();

            case KeyEvent.VK_DELETE -> {

                employeeIdTextField.setText("Click to Generate ID");
                employeeIdTextField.setForeground(Color.GRAY);

            }

        }

    }//GEN-LAST:event_generateButtonKeyPressed

    private void nicTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nicTextFieldKeyPressed
        // code to add key controls for nicTextField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                fNameTextField.grabFocus();

            case KeyEvent.VK_UP ->
                generateButton.grabFocus();

            case KeyEvent.VK_DOWN ->
                contactNoTextField.grabFocus();

            case KeyEvent.VK_DELETE ->
                nicTextField.setText("");

        }

    }//GEN-LAST:event_nicTextFieldKeyPressed

    private void fNameTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fNameTextFieldKeyPressed
        // code to add key controls for fNameTextField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                lNameTextField.grabFocus();

            case KeyEvent.VK_UP ->
                generateButton.grabFocus();

            case KeyEvent.VK_DOWN ->
                contactNoTextField.grabFocus();

            case KeyEvent.VK_DELETE ->
                fNameTextField.setText("");

        }

    }//GEN-LAST:event_fNameTextFieldKeyPressed

    private void lNameTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lNameTextFieldKeyPressed
        // code to add key controls for lNameTextField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                contactNoTextField.grabFocus();

            case KeyEvent.VK_UP ->
                fNameTextField.grabFocus();

            case KeyEvent.VK_DOWN ->
                emailTextField.grabFocus();

            case KeyEvent.VK_DELETE ->
                lNameTextField.setText("");

        }

    }//GEN-LAST:event_lNameTextFieldKeyPressed

    private void contactNoTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contactNoTextFieldKeyPressed
        // code to add key controls for contactNoTextField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                emailTextField.grabFocus();

            case KeyEvent.VK_UP ->
                nicTextField.grabFocus();

            case KeyEvent.VK_DOWN ->
                maleRadio.grabFocus();

            case KeyEvent.VK_DELETE ->
                contactNoTextField.setText("");

        }

    }//GEN-LAST:event_contactNoTextFieldKeyPressed

    private void emailTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emailTextFieldKeyPressed
        // code to add key controls for emailTextField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                maleRadio.grabFocus();

            case KeyEvent.VK_UP ->
                lNameTextField.grabFocus();

            case KeyEvent.VK_DOWN ->
                addressButton.grabFocus();

            case KeyEvent.VK_DELETE ->
                emailTextField.setText("");

        }

    }//GEN-LAST:event_emailTextFieldKeyPressed

    private void addressButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addressButtonKeyPressed
        // code to add key controls for addressButton

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                addressButton.doClick();

            case KeyEvent.VK_RIGHT, KeyEvent.VK_D ->
                dateOfHireDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

            case KeyEvent.VK_LEFT, KeyEvent.VK_A ->
                internRadio.grabFocus();

            case KeyEvent.VK_UP, KeyEvent.VK_W ->
                emailTextField.grabFocus();

            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> {

                // checks if String[] employeeData = null
                if (empId == null) {

                    // if employeeData is null selects submitButton 
                    submitButton.grabFocus();

                } else {

                    // if employeeData is not null selects updateButton
                    updateButton.grabFocus();

                }

            }

            case KeyEvent.VK_BACK_SPACE -> {

                // checks if dateOfFinalDayChooser visible or not
                if (dateOfFinalDayChooser.isVisible()) {

                    // if visible select it
                    dateOfFinalDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

                } else {

                    // if not visible select dateOfHireDayChooser
                    dateOfHireDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

                }

            }

        }

    }//GEN-LAST:event_addressButtonKeyPressed

    private void dateOfHireDayChooserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dateOfHireDayChooserKeyPressed
        // code to add key controls for dateOfHireDayChooser

        // Access the key code
        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT -> {

                // checks if dateOfFinalDayChooser visible or not
                if (dateOfFinalDayChooser.isVisible()) {

                    // if visible select it
                    dateOfFinalDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

                } else {

                    // if not visible select address button
                    addressButton.grabFocus();
                    addressButton.doClick();

                }

            }

            case KeyEvent.VK_UP ->
                fullTimeRadio.grabFocus();

            case KeyEvent.VK_DOWN -> {

                // checks if String[] employeeData = null
                if (empId == null) {

                    // if employeeData is null selects submitButton 
                    submitButton.grabFocus();

                } else {

                    // if employeeData is not null selects updateButton
                    updateButton.grabFocus();

                }

            }

            case KeyEvent.VK_DELETE ->
                dateOfHireDayChooser.setDate(null);

        }

    }//GEN-LAST:event_dateOfHireDayChooserKeyPressed

    private void dateOfFinalDayChooserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dateOfFinalDayChooserKeyPressed
        // code to add key controls for dateOfFinalDayChooser

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT -> {

                addressButton.grabFocus();
                addressButton.doClick();

            }

            case KeyEvent.VK_UP ->
                fullTimeRadio.grabFocus();

            case KeyEvent.VK_DOWN -> {

                // checks if String[] employeeData = null
                if (empId == null) {

                    // if employeeData is null selects submitButton 
                    submitButton.grabFocus();

                } else {

                    // if employeeData is not null selects updateButton
                    updateButton.grabFocus();

                }

            }

            case KeyEvent.VK_DELETE ->
                dateOfFinalDayChooser.setDate(null);

        }

    }//GEN-LAST:event_dateOfFinalDayChooserKeyPressed

    private void dateOfBirthDayChooserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dateOfBirthDayChooserKeyPressed
        // code to add key controls for dateOfBirthDayChooser

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                dateOfHireDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

            case KeyEvent.VK_DOWN ->
                fullTimeRadio.grabFocus();

            case KeyEvent.VK_UP ->
                maleRadio.grabFocus();

            case KeyEvent.VK_DELETE ->
                dateOfBirthDayChooser.setDate(null);

        }

    }//GEN-LAST:event_dateOfBirthDayChooserKeyPressed

    private void maleRadioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_maleRadioKeyPressed
        // code to add key controls for maleRadio

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_M -> {

                // Select the radio button
                maleRadio.setSelected(true);

                // select buttonGroup 2
                fullTimeRadio.grabFocus();

            }

            case KeyEvent.VK_BACK_SPACE ->
                emailTextField.grabFocus();

            case KeyEvent.VK_DELETE ->
                buttonGroup1.clearSelection();

        }

    }//GEN-LAST:event_maleRadioKeyPressed

    private void femaleRadioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_femaleRadioKeyPressed
        // code to add key controls for femaleRadio

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_F -> {

                // Select the radio button
                femaleRadio.setSelected(true);

                // select buttonGroup 2 (by focusing fullTimeRadio)
                fullTimeRadio.grabFocus();

            }

            case KeyEvent.VK_BACK_SPACE ->
                emailTextField.grabFocus();

            case KeyEvent.VK_DELETE ->
                buttonGroup1.clearSelection();

        }

    }//GEN-LAST:event_femaleRadioKeyPressed

    private void fullTimeRadioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fullTimeRadioKeyPressed
        // code to add key controls for fullTimeRadio

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_F -> {

                // Select the radio button
                fullTimeRadio.setSelected(true);

                // select dateOfBirthDayChooser
                dateOfBirthDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

            }

            case KeyEvent.VK_BACK_SPACE ->
                maleRadio.grabFocus();

            case KeyEvent.VK_DELETE ->
                buttonGroup2.clearSelection();

        }

    }//GEN-LAST:event_fullTimeRadioKeyPressed

    private void partTimeRadioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_partTimeRadioKeyPressed
        // code to add key controls for partTimeRadio

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_P -> {

                // Select the radio button
                partTimeRadio.setSelected(true);

                // select dateOfBirthDayChooser
                dateOfBirthDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

            }

            case KeyEvent.VK_BACK_SPACE ->
                maleRadio.grabFocus();

            case KeyEvent.VK_DELETE ->
                buttonGroup2.clearSelection();

        }

    }//GEN-LAST:event_partTimeRadioKeyPressed

    private void internRadioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_internRadioKeyPressed
        // code to add key controls for internRadio

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_I -> {

                // Select the radio button
                internRadio.setSelected(true);

                // select dateOfBirthDayChooser
                dateOfBirthDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

            }

            case KeyEvent.VK_BACK_SPACE ->
                maleRadio.grabFocus();

            case KeyEvent.VK_DELETE ->
                buttonGroup2.clearSelection();

        }

    }//GEN-LAST:event_internRadioKeyPressed

    private void postalcodeViewTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_postalcodeViewTextFieldKeyPressed
        // code to add key controls for postalcodeViewTextField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_DOWN ->
                AddressLine1ViewTextField.grabFocus();

            case KeyEvent.VK_UP ->
                emailTextField.grabFocus();

            case KeyEvent.VK_SPACE, KeyEvent.VK_END -> {

                // checks if String[] employeeData = null
                if (empId == null) {

                    // if employeeData is null selects submitButton 
                    submitButton.grabFocus();
                    submitButton.doClick();

                } else {

                    // if employeeData is not null selects updateButton
                    updateButton.grabFocus();
                    updateButton.doClick();

                }

            }

            case KeyEvent.VK_DELETE ->
                deleteAddress();

        }

    }//GEN-LAST:event_postalcodeViewTextFieldKeyPressed

    private void AddressLine1ViewTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AddressLine1ViewTextFieldKeyPressed
        // code to add key controls for AddressLine1ViewTextField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_DOWN ->
                AddressLine2ViewTextField.grabFocus();

            case KeyEvent.VK_UP ->
                postalcodeViewTextField.grabFocus();

            case KeyEvent.VK_SPACE, KeyEvent.VK_END -> {

                // checks if String[] employeeData = null
                if (empId == null) {

                    // if employeeData is null selects submitButton 
                    submitButton.grabFocus();
                    submitButton.doClick();

                } else {

                    // if employeeData is not null selects updateButton
                    updateButton.grabFocus();
                    updateButton.doClick();

                }

            }

            case KeyEvent.VK_DELETE ->
                deleteAddress();

        }

    }//GEN-LAST:event_AddressLine1ViewTextFieldKeyPressed

    private void AddressLine2ViewTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AddressLine2ViewTextFieldKeyPressed
        // code to add key controls for AddressLine2ViewTextField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_DOWN ->
                ProvinceViewTextField.grabFocus();

            case KeyEvent.VK_UP ->
                AddressLine1ViewTextField.grabFocus();

            case KeyEvent.VK_SPACE, KeyEvent.VK_END -> {

                // checks if String[] employeeData = null
                if (empId == null) {

                    // if employeeData is null selects submitButton 
                    submitButton.grabFocus();
                    submitButton.doClick();

                } else {

                    // if employeeData is not null selects updateButton
                    updateButton.grabFocus();
                    updateButton.doClick();

                }

            }

            case KeyEvent.VK_DELETE ->
                deleteAddress();

        }

    }//GEN-LAST:event_AddressLine2ViewTextFieldKeyPressed

    private void ProvinceViewTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProvinceViewTextFieldKeyPressed
        // code to add key controls for ProvinceViewTextField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_DOWN ->
                DistrictViewTextField.grabFocus();

            case KeyEvent.VK_UP ->
                AddressLine2ViewTextField.grabFocus();

            case KeyEvent.VK_SPACE, KeyEvent.VK_END -> {

                // checks if String[] employeeData = null
                if (empId == null) {

                    // if employeeData is null selects submitButton 
                    submitButton.grabFocus();
                    submitButton.doClick();

                } else {

                    // if employeeData is not null selects updateButton
                    updateButton.grabFocus();
                    updateButton.doClick();

                }

            }

            case KeyEvent.VK_DELETE ->
                deleteAddress();

        }

    }//GEN-LAST:event_ProvinceViewTextFieldKeyPressed

    private void DistrictViewTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DistrictViewTextFieldKeyPressed
        // code to add key controls for DistrictViewTextField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_DOWN ->
                CityViewTextField.grabFocus();

            case KeyEvent.VK_UP ->
                ProvinceViewTextField.grabFocus();

            case KeyEvent.VK_SPACE, KeyEvent.VK_END -> {

                // checks if String[] employeeData = null
                if (empId == null) {

                    // if employeeData is null selects submitButton 
                    submitButton.grabFocus();
                    submitButton.doClick();

                } else {

                    // if employeeData is not null selects updateButton
                    updateButton.grabFocus();
                    updateButton.doClick();

                }

            }

            case KeyEvent.VK_DELETE ->
                deleteAddress();

        }

    }//GEN-LAST:event_DistrictViewTextFieldKeyPressed

    private void CityViewTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CityViewTextFieldKeyPressed
        // code to add key controls for CityViewTextField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_DOWN -> {

                // checks if String[] employeeData = null
                if (empId == null) {

                    // if employeeData is null selects submitButton 
                    submitButton.grabFocus();

                } else {

                    // if employeeData is not null selects updateButton
                    updateButton.grabFocus();

                }

            }

            case KeyEvent.VK_UP ->
                DistrictViewTextField.grabFocus();

            case KeyEvent.VK_SPACE, KeyEvent.VK_END -> {

                // checks if String[] employeeData = null
                if (empId == null) {

                    // if employeeData is null selects submitButton 
                    submitButton.grabFocus();
                    submitButton.doClick();

                } else {

                    // if employeeData is not null selects updateButton
                    updateButton.grabFocus();
                    updateButton.doClick();

                }

            }

            case KeyEvent.VK_DELETE ->
                deleteAddress();

        }

    }//GEN-LAST:event_CityViewTextFieldKeyPressed

    private void submitButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_submitButtonKeyPressed
        // code to add key controls for submitButton

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                submitButton.doClick();

            case KeyEvent.VK_UP, KeyEvent.VK_W -> {

                // checks if dateOfFinalDayChooser visible or not
                if (dateOfFinalDayChooser.isVisible()) {

                    // if visible select it
                    dateOfFinalDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

                } else {

                    // if not visible select dateOfHireDayChooser
                    dateOfHireDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

                }

            }

            case KeyEvent.VK_LEFT, KeyEvent.VK_A, KeyEvent.VK_BACK_SPACE ->
                CityViewTextField.grabFocus();

            case KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_RIGHT, KeyEvent.VK_D ->
                refreshButton.grabFocus();

            case KeyEvent.VK_DELETE -> {

                refreshButton.grabFocus();
                refreshButton.doClick();

            }

        }

    }//GEN-LAST:event_submitButtonKeyPressed

    private void updateButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_updateButtonKeyPressed
        // code to add key controls for updateButton

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                updateButton.doClick();

            case KeyEvent.VK_UP, KeyEvent.VK_W -> {

                // checks if dateOfFinalDayChooser visible or not
                if (dateOfFinalDayChooser.isVisible()) {

                    // if visible select it
                    dateOfFinalDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

                } else {

                    // if not visible select dateOfHireDayChooser
                    dateOfHireDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

                }

            }

            case KeyEvent.VK_LEFT, KeyEvent.VK_A, KeyEvent.VK_BACK_SPACE ->
                CityViewTextField.grabFocus();

            case KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_RIGHT, KeyEvent.VK_D ->
                refreshButton.grabFocus();

            case KeyEvent.VK_DELETE -> {

                refreshButton.grabFocus();
                refreshButton.doClick();

            }

        }

    }//GEN-LAST:event_updateButtonKeyPressed

    private void refreshButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_refreshButtonKeyPressed
        // code to add key controls for refreshButton

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_DELETE ->
                refreshButton.doClick();

            case KeyEvent.VK_UP, KeyEvent.VK_W -> {

                // checks if dateOfFinalDayChooser visible or not
                if (dateOfFinalDayChooser.isVisible()) {

                    // if visible select it
                    dateOfFinalDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

                } else {

                    // if not visible select dateOfHireDayChooser
                    dateOfHireDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

                }

            }

            case KeyEvent.VK_LEFT, KeyEvent.VK_A, KeyEvent.VK_BACK_SPACE -> {

                // checks if String[] employeeData = null
                if (empId == null) {

                    // if employeeData is null selects submitButton 
                    submitButton.grabFocus();

                } else {

                    // if employeeData is not null selects updateButton
                    updateButton.grabFocus();

                }

            }

        }

    }//GEN-LAST:event_refreshButtonKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */

        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AddEmployee(null, null).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AddressLine1ViewTextField;
    private javax.swing.JTextField AddressLine2ViewTextField;
    private javax.swing.JButton BackToDashboardButton;
    private javax.swing.JPanel BackToDashboardPanel;
    private javax.swing.JTextField CityViewTextField;
    private javax.swing.JTextField DistrictViewTextField;
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JTextField ProvinceViewTextField;
    private com.k33ptoo.components.KButton addressButton;
    private javax.swing.JTextField addressIDTextField;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel contactNoLabel;
    private javax.swing.JTextField contactNoTextField;
    private javax.swing.JLabel dOBLabel;
    private com.toedter.calendar.JDateChooser dateOfBirthDayChooser;
    private com.toedter.calendar.JDateChooser dateOfFinalDayChooser;
    private com.toedter.calendar.JDateChooser dateOfHireDayChooser;
    private javax.swing.JLabel dateOfHireLabel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel emailLabel1;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JLabel employeeGenderLabel;
    private javax.swing.JLabel employeeIdLabel;
    private javax.swing.JTextField employeeIdTextField;
    private javax.swing.JLabel employeeTypeLabel;
    private javax.swing.JLabel employeeTypeLabel2;
    private javax.swing.JLabel fNameLabel;
    private javax.swing.JTextField fNameTextField;
    private javax.swing.JRadioButton femaleRadio;
    private javax.swing.JRadioButton fullTimeRadio;
    private javax.swing.JButton generateButton;
    private javax.swing.JRadioButton internRadio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lNameLabel;
    private javax.swing.JTextField lNameTextField;
    private javax.swing.JRadioButton maleRadio;
    private javax.swing.JTextField nicTextField;
    private javax.swing.JRadioButton partTimeRadio;
    private javax.swing.JTextField postalcodeViewTextField;
    private javax.swing.JButton refreshButton;
    private com.k33ptoo.components.KButton submitButton;
    private javax.swing.JLabel toLabel;
    private com.k33ptoo.components.KButton updateButton;
    // End of variables declaration//GEN-END:variables

    private void reset() {

        // Reset all components to default values
        // checks if constructor pass ManageEmployee 
        if (manageEmployee == null) {

            employeeIdTextField.setText("");
            nicTextField.setText("");

        }

        dateOfBirthDayChooser.setDate(null);
        fNameTextField.setText("");
        lNameTextField.setText("");
        contactNoTextField.setText("");
        emailTextField.setText("");
        buttonGroup1.clearSelection();
        buttonGroup2.clearSelection();
        dateOfHireDayChooser.setDate(null);
        dateOfFinalDayChooser.setDate(null);

        // Re-add the placeholders to refreshed TextFields
        addPlaceholder();

        // checks if constructor pass ManageEmployee 
        if (manageEmployee == null) {

            // Grabs generateButton focus 
            generateButton.grabFocus();

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

                refreshButton.doClick(); // Simulate Refresh button press

            }

        });

    }

}
