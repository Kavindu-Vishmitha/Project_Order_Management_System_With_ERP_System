/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hr_department_gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
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

/**
 *
 * @author Avishka
 */
public class ManageEmployee extends javax.swing.JFrame {

    // Hashmaps for Employee status, department, type, position
    private static HashMap<String, String> statusTypeMap = new HashMap<>();
    private static HashMap<String, String> departmaentTypeHashMap = new HashMap<>();
    private static HashMap<String, String> EmpPositionHashMap = new HashMap<>();
    private static HashMap<String, String> empTypeHashMap = new HashMap<>();
    private static HashMap<String, String> positiontypeHashMap = new HashMap<>();

    public ManageEmployee() {

        initComponents();     // Initialize components

        addPlaceholder();     // call textfields placeholder method

        dateChooserListeners(); // call this method to activate datechooser key press 

        initializeRowSorter(); // call table row sorter method

        loadEmployeeData();   // call table data load method
        loadEmployeeStatus(); // call employee status combobox load method
        loadDepartment();     // call employee department combobox load method
        loadEmployeeType();   // call employee type combobox load method
        loadPosition();       // call employee position combobox load method

        LoadEmpPosition();

        configureKeyBindings(); // For Default Frame Key Controls

    }

    private DefaultComboBoxModel model3;

    private void LoadEmpPosition() {

        try {

            // search query
            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `employee_position`");

            // create new arraylist
            ArrayList<String> arrayList = new ArrayList<>();

            // add default value
            arrayList.add("Select Position");

            // query row checker and updater loop
            while (resultSet.next()) {

                // adds data to arraylist
                arrayList.add(resultSet.getString("position_name"));
                EmpPositionHashMap.put(resultSet.getString("position_name"), resultSet.getString("employee_position_id"));

            }

            // create new model
            model3 = new DefaultComboBoxModel();
            PositionAddComboBox.setModel(model3);  // set model to combobox

            // add arraylist to model
            model3.addAll(arrayList);

            // set default value to DepartmentComboBox
            PositionAddComboBox.setSelectedItem("Select Position");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // Placeholders add method
    private void addPlaceholder() {

        // postalcodeTextField placeholder & color
        EmployeeSearchField.setText("Enter Employee Name or ID");
        EmployeeSearchField.setForeground(Color.GRAY);

    }

    // global variables of Table & ComboBox Models
    private DefaultTableModel tablemodel;
    private DefaultComboBoxModel model;
    private DefaultComboBoxModel model2;

    // table row sorter
    private TableRowSorter<DefaultTableModel> rowSorter;

    // table row sort initializer
    private void initializeRowSorter() {

        // get ManageEmployeeTable as DefaultTableModel
        tablemodel = (DefaultTableModel) ManageEmployeeTable.getModel();

        // create new row sorter to tablemodel
        rowSorter = new TableRowSorter<>(tablemodel);

        // add row sorter to table
        ManageEmployeeTable.setRowSorter(rowSorter);

    }

    // Employee Details Table load method
    private void loadEmployeeData() {

        try {

            // search query
            ResultSet resultSet = MySql.executeSearch("SELECT employee_id, nic, first_name, last_name, contact_number, email, "
                    + "date_of_birth, date_of_hire, date_of_final, gender_name, address_line01, address_line02, "
                    + "status_name, type_name, position_name, department_name "
                    + "FROM employee "
                    + "INNER JOIN employee_address ON employee.employee_address_em_address_id = employee_address.em_address_id "
                    + "LEFT JOIN department ON employee.department_department_id = department.department_id "
                    + "LEFT JOIN employee_position ON employee.employee_position_employee_position_id = employee_position.employee_position_id "
                    + "INNER JOIN employee_type ON employee.employee_type_employee_type_id = employee_type.employee_type_id "
                    + "LEFT JOIN employee_status ON employee.employee_status_employee_status_id = employee_status.employee_status_id "
                    + "INNER JOIN gender ON employee.gender_gender_id = gender.gender_id");

            // get ManageEmployeeTable as DefaultTableModel
            tablemodel = (DefaultTableModel) ManageEmployeeTable.getModel();
            tablemodel.setRowCount(0); // Clear table before loading new data

            // to check available result
            boolean hasData = false;

            // query row checker and updater loop
            while (resultSet.next()) {

                // if there's a data boolean value will be true
                hasData = true;

                // get address as a variable
                String address = (resultSet.getString("address_line01") != null ? resultSet.getString("address_line01") : "")
                        + " " + (resultSet.getString("address_line02") != null ? resultSet.getString("address_line02") : "");

                // add row to table
                tablemodel.addRow(new Object[]{
                    resultSet.getString("employee_id"),
                    resultSet.getString("nic"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("contact_number"),
                    resultSet.getString("email"),
                    resultSet.getString("date_of_birth"),
                    resultSet.getString("date_of_hire"),
                    resultSet.getString("date_of_final") != null ? resultSet.getString("date_of_final") : "     -     ",
                    resultSet.getString("gender_name"),
                    address,
                    resultSet.getString("status_name") != null ? resultSet.getString("status_name") : "     -     ",
                    resultSet.getString("type_name"),
                    resultSet.getString("position_name") != null ? resultSet.getString("position_name") : "     -     ",
                    resultSet.getString("department_name") != null ? resultSet.getString("department_name") : "     -     "

                });

            }

            // checks if data loaded from query if not shows a message
            if (!hasData) {

                JOptionPane.showMessageDialog(this, "No data found for employees.", "Info", JOptionPane.INFORMATION_MESSAGE);

            }

            // sets model to table
            ManageEmployeeTable.setModel(tablemodel);

        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    // method for load Employ Status to Comboboxes
    private void loadEmployeeStatus() {

        try {

            // search query
            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `employee_status`");

            // create new arraylist
            ArrayList<String> arrayList = new ArrayList<>();

            // add default value
            arrayList.add("Select Status");

            // query row checker and updater loop
            while (resultSet.next()) {

                // adds data to arraylist
                arrayList.add(resultSet.getString("status_name"));

                // store data to hashmap for future use
                statusTypeMap.put(resultSet.getString("status_name"), resultSet.getString("employee_status_id"));

            }

            // create new model
            model = new DefaultComboBoxModel();
            EmployeeStatusComboBox.setModel(model); // set model to combobox

            // create another new model
            model2 = new DefaultComboBoxModel();
            statusComboBox.setModel(model2);  // set model2 to combobox

            // add arraylist to model
            model.addAll(arrayList);

            // add arraylist to model2
            model2.addAll(arrayList);

            // set default values for two status comboxes
            EmployeeStatusComboBox.setSelectedItem("Select Status");
            statusComboBox.setSelectedItem("Select Status");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // method for load Employ Department to Combobox
    private void loadDepartment() {

        try {

            // search query
            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `department`");

            // create new arraylist
            ArrayList<String> arrayList = new ArrayList<>();

            // add default value
            arrayList.add("Select Department");

            // query row checker and updater loop
            while (resultSet.next()) {

                // adds data to arraylist
                arrayList.add(resultSet.getString("department_name"));

                // store data to hashmap for future use
                departmaentTypeHashMap.put(resultSet.getString("department_name"), resultSet.getString("department_id"));
            }

            // create new model
            model = new DefaultComboBoxModel();
            DepartmentComboBox.setModel(model);  // set model to combobox

            // add arraylist to model
            model.addAll(arrayList);

            // set default value to DepartmentComboBox
            DepartmentComboBox.setSelectedItem("Select Department");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void loadEmployeeType() {

        try {

            // search query
            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `employee_type`");

            // create new arraylist
            ArrayList<String> arrayList = new ArrayList<>();

            // add default value
            arrayList.add("Select Type");

            // query row checker and updater loop
            while (resultSet.next()) {

                // adds data to arraylist
                arrayList.add(resultSet.getString("type_name"));

                // store data to hashmap for future use
                empTypeHashMap.put(resultSet.getString("type_name"), resultSet.getString("employee_type_id"));

            }

            // create new model
            model = new DefaultComboBoxModel();
            employeeTypeComboBox.setModel(model);  // set model to combobox

            // add arraylist to model
            model.addAll(arrayList);

            // set default value to employeeTypeComboBox
            employeeTypeComboBox.setSelectedItem("Select Type");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void loadPosition() {

        try {

            // search query
            ResultSet resultSet = MySql.executeSearch("SELECT * FROM `employee_position`");

            // create new arraylist
            ArrayList<String> arrayList = new ArrayList<>();

            // add default value
            arrayList.add("Select Position");

            // query row checker and updater loop
            while (resultSet.next()) {

                // adds data to arraylist
                arrayList.add(resultSet.getString("position_name"));

                // store data to hashmap for future use
                positiontypeHashMap.put(resultSet.getString("position_name"), resultSet.getString("employee_position_id"));

            }

            // create new model
            model = new DefaultComboBoxModel();
            positionComboBox.setModel(model);  // set model to combobox

            // add arraylist to model
            model.addAll(arrayList);

            // set default value to positionComboBox
            positionComboBox.setSelectedItem("Select Position");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // method to update Employee status (Update button code)
    private void updateEmployeeStatus() {

        try {

            // Get Employee ID from the table
            int row = ManageEmployeeTable.getSelectedRow();

            // check if a row selected
            if (row != -1) {

                // Get data from selected row
                String empId = ManageEmployeeTable.getValueAt(row, 0).toString();

                // Get the selected status from the ComboBox
                String newStatus = (String) EmployeeStatusComboBox.getSelectedItem();

                if (newStatus == null || newStatus.equals("Select Status")) {

                    JOptionPane.showMessageDialog(this, "Please select a valid status!", "Validation Error", JOptionPane.WARNING_MESSAGE);

                    EmployeeStatusComboBox.grabFocus();

                    return;

                }

                // Update the database with the new status
                String query = "UPDATE `employee` SET `employee_status_employee_status_id` = '" + statusTypeMap.get(newStatus) + "' WHERE `employee_id` = '" + empId + "'";

                // take the affected query row count
                int rowsAffected = MySql.executeUpdate(query);

                // checks if there is a affected row 
                if (rowsAffected > 0) {

                    JOptionPane.showMessageDialog(this, "Employee status updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Refresh the table
                    loadEmployeeData();

                } else {

                    JOptionPane.showMessageDialog(this, "Employee ID not found!", "Error", JOptionPane.ERROR_MESSAGE);

                }

            }

        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating employee status: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    // method to update Employee status (Update button code)
    private void deleteEmployeeStatus() {

        try {

            // Get Employee ID from the table
            int row = ManageEmployeeTable.getSelectedRow();

            // check if a row selected
            if (row != -1) {

                // Get data from selected row
                String empId = ManageEmployeeTable.getValueAt(row, 0).toString();

                // Confirm deletion
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this employee?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                // if not yes option code
                if (confirm != JOptionPane.YES_OPTION) {

                    return; // User canceled deletion

                }

                // Delete the employee from the database
                String query = "DELETE FROM `employee` WHERE `employee_id` = '" + empId + "'";

                // take the affected query row count
                int rowsAffected = MySql.executeUpdate(query);

                // checks if there is a affected row 
                if (rowsAffected > 0) {

                    JOptionPane.showMessageDialog(this, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Refresh the table
                    loadEmployeeData();

                } else {

                    JOptionPane.showMessageDialog(this, "Employee ID not found!", "Error", JOptionPane.ERROR_MESSAGE);

                }

            }

        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating employee status: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    // Filter Table By Department Combobox
    private void filterTableByDepartment() {

        // variable for get combobox selected item
        String selectedDepartment = (String) DepartmentComboBox.getSelectedItem();

        if (selectedDepartment == null || selectedDepartment.equals("Select Department")) {

            rowSorter.setRowFilter(null);
            return;

        }

        // reset code for other ComboBoxes and Dates
        statusComboBox.setSelectedIndex(0);
        employeeTypeComboBox.setSelectedIndex(0);
        positionComboBox.setSelectedIndex(0);
        dateOfHireFromDayChooser.setDate(null);
        dateOfHireToDayChooser.setDate(null);

        rowSorter.setRowFilter(RowFilter.regexFilter("^" + selectedDepartment + "$", 14));

    }

    // Filter Table By Position Combobox
    private void filterTableByPosition() {

        // variable for get combobox selected item
        String selectedPostion = (String) positionComboBox.getSelectedItem();

        // checks string value null or default
        if (selectedPostion == null || selectedPostion.equals("Select Position")) {

            // Show all rows if "Select Position" is chosen
            rowSorter.setRowFilter(null);
            return;

        }

        // reset code for other ComboBoxes and Dates
        DepartmentComboBox.setSelectedIndex(0);
        statusComboBox.setSelectedIndex(0);
        employeeTypeComboBox.setSelectedIndex(0);
        dateOfHireFromDayChooser.setDate(null);
        dateOfHireToDayChooser.setDate(null);

        // Apply a filter based on the department name in the relevant column
        rowSorter.setRowFilter(RowFilter.regexFilter("^" + selectedPostion + "$", 13));

    }

    // Filter Table By Type Combobox
    private void filterTableByType() {

        // variable for get combobox selected item
        String selectedType = (String) employeeTypeComboBox.getSelectedItem();

        // checks string value null or default        
        if (selectedType == null || selectedType.equals("Select Type")) {

            // Show all rows if "Select Type" is chosen
            rowSorter.setRowFilter(null);
            return;

        }

        // reset code for other ComboBoxes and Dates
        DepartmentComboBox.setSelectedIndex(0);
        statusComboBox.setSelectedIndex(0);
        positionComboBox.setSelectedIndex(0);
        dateOfHireFromDayChooser.setDate(null);
        dateOfHireToDayChooser.setDate(null);

        // Apply a filter based on the department name in the relevant column
        rowSorter.setRowFilter(RowFilter.regexFilter("^" + selectedType + "$", 12));

    }

    // Filter Table By Status Combobox
    private void filterTableByStatus() {

        // variable for get combobox selected item
        String selectedStatus = (String) statusComboBox.getSelectedItem();

        // checks string value null or default        
        if (selectedStatus == null || selectedStatus.equals("Select Status")) {

            // Show all rows if "Select Status" is chosen
            rowSorter.setRowFilter(null);
            return;

        }

        // reset code for other ComboBoxes and Dates
        DepartmentComboBox.setSelectedIndex(0);
        statusComboBox.setSelectedIndex(0);
        positionComboBox.setSelectedIndex(0);
        dateOfHireFromDayChooser.setDate(null);
        dateOfHireToDayChooser.setDate(null);

        // Apply a filter based on the department name in the relevant column
        rowSorter.setRowFilter(RowFilter.regexFilter("^" + selectedStatus + "$", 11));

    }

    private Date fromDate = null;
    private Date toDate = null;

    // Filter Table By Hired Date
    private void filterByDateRange() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // Ensure strict date validation

        try {

            // Handle cases based on selected dates
            String query = null;

            if (fromDate != null && toDate != null) {
                // Both dates are selected: Filter rows between the two dates

                String formattedFromDate = sdf.format(fromDate);
                String formattedToDate = sdf.format(toDate);

                // SQL query to fetch rows within the date range
                query = "SELECT `employee_id`, `nic`, `first_name`, `last_name`, `contact_number`, `email`, `date_of_birth`, `date_of_hire`, `date_of_final`, "
                        + "`gender_name`, `address_line01`, `address_line02`, `status_name`, `type_name`, `position_name`, `department_name` "
                        + "FROM `employee` "
                        + "INNER JOIN `employee_address`  ON `employee`.`employee_address_em_address_id` = `employee_address`.`em_address_id` "
                        + "LEFT JOIN `department`  ON `employee`.`department_department_id` = `department`.`department_id` "
                        + "LEFT JOIN `employee_position`  ON `employee`.`employee_position_employee_position_id` = `employee_position`.`employee_position_id` "
                        + "INNER JOIN `employee_type`  ON `employee`.`employee_type_employee_type_id` = `employee_type`.`employee_type_id` "
                        + "LEFT JOIN `employee_status`  ON `employee`.`employee_status_employee_status_id` = `employee_status`.`employee_status_id` "
                        + "INNER JOIN `gender`  ON `employee`.`gender_gender_id` = `gender`.`gender_id` "
                        + "WHERE `employee`.`date_of_hire` BETWEEN '" + formattedFromDate + "' AND '" + formattedToDate + "'";

                if (!isValidPastDate(fromDate)) {
                    // Check if Employee Selected Birth Date is in Past or not

                    JOptionPane.showMessageDialog(this, "Please Select a Valid Minimum Hire Date to sort table!", "Warning: Invalid Date Of Hire", JOptionPane.WARNING_MESSAGE);

                    dateOfHireFromDayChooser.grabFocus(); // Gets focus to dateOfBirthDayChooser 

                } else if (!isValidPastDate(toDate)) {
                    // Check if Employee Selected Birth Date is in Past or not

                    JOptionPane.showMessageDialog(this, "Please Select a Valid Maximum Hire Date to sort table!", "Warning: Invalid Date Of Hire", JOptionPane.WARNING_MESSAGE);

                    dateOfHireToDayChooser.grabFocus(); // Gets focus to dateOfBirthDayChooser 

                }

            } else if (fromDate != null) {
                // Only "From" date is selected: Filter rows on or after this date

                String formattedFromDate = sdf.format(fromDate);

                // SQL query to fetch rows after the from date
                query = "SELECT `employee_id`, `nic`, `first_name`, `last_name`, `contact_number`, `email`, `date_of_birth`, `date_of_hire`, `date_of_final`, "
                        + "`gender_name`, `address_line01`, `address_line02`, `status_name`, `type_name`, `position_name`, `department_name` "
                        + "FROM `employee` "
                        + "INNER JOIN `employee_address`  ON `employee`.`employee_address_em_address_id` = `employee_address`.`em_address_id` "
                        + "LEFT JOIN `department`  ON `employee`.`department_department_id` = `department`.`department_id` "
                        + "LEFT JOIN `employee_position`  ON `employee`.`employee_position_employee_position_id` = `employee_position`.`employee_position_id` "
                        + "INNER JOIN `employee_type`  ON `employee`.`employee_type_employee_type_id` = `employee_type`.`employee_type_id` "
                        + "LEFT JOIN `employee_status`  ON `employee`.`employee_status_employee_status_id` = `employee_status`.`employee_status_id` "
                        + "INNER JOIN `gender`  ON `employee`.`gender_gender_id` = `gender`.`gender_id` "
                        + "WHERE `employee`.`date_of_hire` >= '" + formattedFromDate + "'";

                if (!isValidPastDate(fromDate)) {
                    // Check if Employee Selected Birth Date is in Past or not

                    JOptionPane.showMessageDialog(this, "Please Select a Valid Minimum Hire Date to sort table!", "Warning: Invalid Date Of Hire", JOptionPane.WARNING_MESSAGE);

                    dateOfHireFromDayChooser.grabFocus(); // Gets focus to dateOfBirthDayChooser 

                }

            } else if (toDate != null) {
                // Only "To" date is selected: Filter rows on or before this date

                String formattedToDate = sdf.format(toDate);

                // SQL query to fetch rows before the to date
                query = "SELECT `employee_id`, `nic`, `first_name`, `last_name`, `contact_number`, `email`, `date_of_birth`, `date_of_hire`, `date_of_final`, "
                        + "`gender_name`, `address_line01`, `address_line02`, `status_name`, `type_name`, `position_name`, `department_name` "
                        + "FROM `employee` "
                        + "INNER JOIN `employee_address`  ON `employee`.`employee_address_em_address_id` = `employee_address`.`em_address_id` "
                        + "LEFT JOIN `department`  ON `employee`.`department_department_id` = `department`.`department_id` "
                        + "LEFT JOIN `employee_position`  ON `employee`.`employee_position_employee_position_id` = `employee_position`.`employee_position_id` "
                        + "INNER JOIN `employee_type`  ON `employee`.`employee_type_employee_type_id` = `employee_type`.`employee_type_id` "
                        + "LEFT JOIN `employee_status`  ON `employee`.`employee_status_employee_status_id` = `employee_status`.`employee_status_id` "
                        + "INNER JOIN `gender`  ON `employee`.`gender_gender_id` = `gender`.`gender_id` "
                        + "WHERE `employee`.`date_of_hire` <= '" + formattedToDate + "'";

                if (!isValidPastDate(toDate)) {
                    // Check if Employee Selected Birth Date is in Past or not

                    JOptionPane.showMessageDialog(this, "Please Select a Valid Maximum Hire Date to sort table!", "Warning: Invalid Date Of Hire", JOptionPane.WARNING_MESSAGE);

                    dateOfHireToDayChooser.grabFocus(); // Gets focus to dateOfBirthDayChooser 

                }

            }

            if (query != null) {
                // Execute the query and update the table

                ResultSet resultSet = MySql.executeSearch(query);

                tablemodel = (DefaultTableModel) ManageEmployeeTable.getModel();
                tablemodel.setRowCount(0); // Clear the table

                boolean found = false;

                while (resultSet.next()) {

                    found = true;

                    String address = (resultSet.getString("address_line01") != null ? resultSet.getString("address_line01") : "")
                            + " "
                            + (resultSet.getString("address_line02") != null ? resultSet.getString("address_line02") : "");

                    tablemodel.addRow(new Object[]{
                        resultSet.getString("employee_id"),
                        resultSet.getString("nic"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("contact_number"),
                        resultSet.getString("email"),
                        resultSet.getString("date_of_birth"),
                        resultSet.getString("date_of_hire"),
                        resultSet.getString("date_of_final") != null ? resultSet.getString("date_of_final") : "     -     ",
                        resultSet.getString("gender_name"),
                        address,
                        resultSet.getString("status_name") != null ? resultSet.getString("status_name") : " - ",
                        resultSet.getString("type_name"),
                        resultSet.getString("position_name") != null ? resultSet.getString("position_name") : "     -     ",
                        resultSet.getString("department_name") != null ? resultSet.getString("department_name") : "     -     "

                    });

                }

                if (!found) {

                    JOptionPane.showMessageDialog(this, "No data found!", "No Results!", JOptionPane.INFORMATION_MESSAGE);

                    EmployeeSearchField.grabFocus();

                }

            }

        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error filtering by date range: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    // EmployeeSearchField search Employee method
    private void searchEmployee() {

        String search = EmployeeSearchField.getText().trim();

        // Validate input
        if (search.isEmpty() || search.equals("Enter Employee Name or ID")) {

            JOptionPane.showMessageDialog(this, "Please enter an Employee ID or Name!", "Validation Error!", JOptionPane.WARNING_MESSAGE);

            EmployeeSearchField.grabFocus();

            // Show all rows if EmployeeSearchField is empty
            rowSorter.setRowFilter(null);
            return;

        }

        try {

            // query variable initialize
            String query = null;

            // Determine whether the input is numeric (ID) or alphabetic (Name)
            if (search.matches("[a-zA-Z\\s]+")) {
                // Alphabetic input (Name)

                // assign name query
                query = "SELECT `employee_id`, `nic`, `first_name`, `last_name`, `contact_number`, `email`, `date_of_birth`, "
                        + "`date_of_hire`, `date_of_final`, `gender_name`, `address_line01`, `address_line02`, "
                        + "`status_name`, `type_name`, `position_name`, `department_name` "
                        + "FROM `employee` "
                        + "INNER JOIN `employee_address` ON `employee`.`employee_address_em_address_id` = `employee_address`.`em_address_id` "
                        + "LEFT JOIN `department` ON `employee`.`department_department_id` = `department`.`department_id` "
                        + "LEFT JOIN `employee_position` ON `employee`.`employee_position_employee_position_id` = `employee_position`.`employee_position_id` "
                        + "INNER JOIN `employee_type` ON `employee`.`employee_type_employee_type_id` = `employee_type`.`employee_type_id` "
                        + "LEFT JOIN `employee_status` ON `employee`.`employee_status_employee_status_id` = `employee_status`.`employee_status_id` "
                        + "INNER JOIN `gender` ON `employee`.`gender_gender_id` = `gender`.`gender_id` "
                        + "WHERE `employee`.`first_name` LIKE '%" + search + "%' OR `employee`.`last_name` LIKE '%" + search + "%'";

            } else {
                // Numeric input (Employee ID)

                // assign id query
                query = "SELECT `employee_id`, `nic`, `first_name`, `last_name`, `contact_number`, `email`, `date_of_birth`, "
                        + "`date_of_hire`, `date_of_final`, `gender_name`, `address_line01`, `address_line02`, "
                        + "`status_name`, `type_name`, `position_name`, `department_name` "
                        + "FROM `employee` "
                        + "INNER JOIN `employee_address` ON `employee`.`employee_address_em_address_id` = `employee_address`.`em_address_id` "
                        + "LEFT JOIN `department` ON `employee`.`department_department_id` = `department`.`department_id` "
                        + "LEFT JOIN `employee_position` ON `employee`.`employee_position_employee_position_id` = `employee_position`.`employee_position_id` "
                        + "INNER JOIN `employee_type` ON `employee`.`employee_type_employee_type_id` = `employee_type`.`employee_type_id` "
                        + "LEFT JOIN `employee_status` ON `employee`.`employee_status_employee_status_id` = `employee_status`.`employee_status_id` "
                        + "INNER JOIN `gender` ON `employee`.`gender_gender_id` = `gender`.`gender_id` "
                        + "WHERE `employee`.`employee_id` LIKE '%" + search + "%'";

            }

            // checks if query not null
            if (query != null) {

                // execute search query
                ResultSet resultSet = MySql.executeSearch(query);

                // assign ManageEmployeeTable as a DefaultTableModel
                tablemodel = (DefaultTableModel) ManageEmployeeTable.getModel();
                tablemodel.setRowCount(0); // clear the table

                // boolean variable for check query results empty or not
                boolean found = false;

                // Populate the table model with fetched data
                while (resultSet.next()) {

                    // sets boolean true if data found
                    found = true;

                    // get address as a variable
                    String address = (resultSet.getString("address_line01") != null ? resultSet.getString("address_line01") : "")
                            + " "
                            + (resultSet.getString("address_line02") != null ? resultSet.getString("address_line02") : "");

                    // add row to table
                    tablemodel.addRow(new Object[]{
                        resultSet.getString("employee_id"),
                        resultSet.getString("nic"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("contact_number"),
                        resultSet.getString("email"),
                        resultSet.getString("date_of_birth"),
                        resultSet.getString("date_of_hire"),
                        resultSet.getString("date_of_final") != null ? resultSet.getString("date_of_final") : "     -     ",
                        resultSet.getString("gender_name"),
                        address,
                        resultSet.getString("status_name") != null ? resultSet.getString("status_name") : "     -     ",
                        resultSet.getString("type_name"),
                        resultSet.getString("position_name") != null ? resultSet.getString("position_name") : "     -     ",
                        resultSet.getString("department_name") != null ? resultSet.getString("department_name") : "     -     "

                    });

                }

                // if no data validation part
                if (!found) {

                    JOptionPane.showMessageDialog(this, "No matching employee found!", "No Results!", JOptionPane.INFORMATION_MESSAGE);

                }

            }

        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

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

    private void dateChooserListeners() {

        dateOfHireFromDayChooser.getDateEditor().getUiComponent().addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {

                dateOfHireFromDayChooserKeyPressed(evt);

            }

        });

        dateOfHireToDayChooser.getDateEditor().getUiComponent().addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {

                dateOfHireToDayChooserKeyPressed(evt);

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

        headerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        BackToDashboardPanel = new javax.swing.JPanel();
        BackToDashboardButton = new javax.swing.JButton();
        PrintButtton = new com.k33ptoo.components.KButton();
        bodyPanel = new javax.swing.JPanel();
        addingPanel = new javax.swing.JPanel();
        searchPanel = new javax.swing.JPanel();
        SearchPanel = new javax.swing.JPanel();
        EmployeeSearchField = new javax.swing.JTextField();
        EmployeeStatusComboBox = new javax.swing.JComboBox<>();
        EmployeeStatusLabel = new javax.swing.JLabel();
        RefreshButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        searchButton = new com.k33ptoo.components.KButton();
        PositionAddComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        Addposition = new com.k33ptoo.components.KButton();
        jPanel4 = new javax.swing.JPanel();
        DepartmentLabel2 = new javax.swing.JLabel();
        DepartmentComboBox = new javax.swing.JComboBox<>();
        DateOfHireLabel2 = new javax.swing.JLabel();
        StatusLabel2 = new javax.swing.JLabel();
        positionComboBox = new javax.swing.JComboBox<>();
        employeeTypeComboBox = new javax.swing.JComboBox<>();
        JobTitleLabel2 = new javax.swing.JLabel();
        statusComboBox = new javax.swing.JComboBox<>();
        EmployeeTypeLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        updateButton = new com.k33ptoo.components.KButton();
        deleteButton = new com.k33ptoo.components.KButton();
        dateOfHireFromDayChooser = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        dateOfHireToDayChooser = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ManageEmployeeTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        headerPanel.setBackground(new java.awt.Color(153, 153, 153));
        headerPanel.setPreferredSize(new java.awt.Dimension(896, 60));

        jLabel1.setFont(new java.awt.Font("Audiowide", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Manage Employee");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addGap(280, 280, 280)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                .addGap(280, 280, 280))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );

        getContentPane().add(headerPanel, java.awt.BorderLayout.PAGE_START);

        BackToDashboardPanel.setBackground(new java.awt.Color(153, 153, 153));
        BackToDashboardPanel.setPreferredSize(new java.awt.Dimension(896, 50));

        BackToDashboardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/back-arrow.png"))); // NOI18N
        BackToDashboardButton.setToolTipText("Go Back!");
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

        javax.swing.GroupLayout BackToDashboardPanelLayout = new javax.swing.GroupLayout(BackToDashboardPanel);
        BackToDashboardPanel.setLayout(BackToDashboardPanelLayout);
        BackToDashboardPanelLayout.setHorizontalGroup(
            BackToDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackToDashboardPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(BackToDashboardButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 788, Short.MAX_VALUE)
                .addComponent(PrintButtton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        BackToDashboardPanelLayout.setVerticalGroup(
            BackToDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BackToDashboardPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(BackToDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BackToDashboardButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PrintButtton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        getContentPane().add(BackToDashboardPanel, java.awt.BorderLayout.PAGE_END);

        bodyPanel.setLayout(new java.awt.BorderLayout());

        addingPanel.setLayout(new java.awt.BorderLayout());

        SearchPanel.setPreferredSize(new java.awt.Dimension(803, 130));

        EmployeeSearchField.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        EmployeeSearchField.setToolTipText("Please Search Using Only Employee ID or Name!");
        EmployeeSearchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                EmployeeSearchFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                EmployeeSearchFieldFocusLost(evt);
            }
        });
        EmployeeSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EmployeeSearchFieldKeyPressed(evt);
            }
        });

        EmployeeStatusComboBox.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        EmployeeStatusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        EmployeeStatusComboBox.setToolTipText("Please Choose Employee Status for Update Employee!");
        EmployeeStatusComboBox.setPreferredSize(new java.awt.Dimension(75, 32));
        EmployeeStatusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeeStatusComboBoxActionPerformed(evt);
            }
        });
        EmployeeStatusComboBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EmployeeStatusComboBoxKeyPressed(evt);
            }
        });

        EmployeeStatusLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        EmployeeStatusLabel.setText("Employee Status");

        RefreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/refresh.png"))); // NOI18N
        RefreshButton.setToolTipText("Click Here to Refresh & Clear All Data You Filled!");
        RefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshButtonActionPerformed(evt);
            }
        });
        RefreshButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RefreshButtonKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel2.setText("Employee ID");

        searchButton.setText("Search");
        searchButton.setToolTipText("Click Here to Search!");
        searchButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        searchButton.setkEndColor(new java.awt.Color(0, 204, 204));
        searchButton.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        searchButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        searchButton.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        searchButton.setkPressedColor(new java.awt.Color(0, 102, 153));
        searchButton.setkSelectedColor(new java.awt.Color(0, 102, 153));
        searchButton.setkStartColor(new java.awt.Color(0, 102, 153));
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        searchButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchButtonKeyPressed(evt);
            }
        });

        PositionAddComboBox.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel4.setText("Employee Position");

        Addposition.setText("Add Position");
        Addposition.setToolTipText("Click Here to Search!");
        Addposition.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Addposition.setkEndColor(new java.awt.Color(0, 204, 204));
        Addposition.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        Addposition.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        Addposition.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        Addposition.setkPressedColor(new java.awt.Color(0, 102, 153));
        Addposition.setkSelectedColor(new java.awt.Color(0, 102, 153));
        Addposition.setkStartColor(new java.awt.Color(0, 102, 153));
        Addposition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddpositionActionPerformed(evt);
            }
        });
        Addposition.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddpositionKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout SearchPanelLayout = new javax.swing.GroupLayout(SearchPanel);
        SearchPanel.setLayout(SearchPanelLayout);
        SearchPanelLayout.setHorizontalGroup(
            SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SearchPanelLayout.createSequentialGroup()
                .addContainerGap(130, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EmployeeSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(RefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
            .addGroup(SearchPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(EmployeeStatusLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EmployeeStatusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PositionAddComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Addposition, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SearchPanelLayout.setVerticalGroup(
            SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SearchPanelLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(RefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(EmployeeSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EmployeeStatusLabel)
                    .addComponent(EmployeeStatusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PositionAddComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(Addposition, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1061, Short.MAX_VALUE)
            .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(SearchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1061, Short.MAX_VALUE))
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
            .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchPanelLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SearchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(15, 15, 15)))
        );

        addingPanel.add(searchPanel, java.awt.BorderLayout.PAGE_START);

        jPanel4.setPreferredSize(new java.awt.Dimension(1026, 230));

        DepartmentLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        DepartmentLabel2.setText("Sort By Department");
        DepartmentLabel2.setPreferredSize(new java.awt.Dimension(122, 32));

        DepartmentComboBox.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        DepartmentComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        DepartmentComboBox.setToolTipText("Please Choose Employee Department for Sort Manage Employee Table!");
        DepartmentComboBox.setPreferredSize(new java.awt.Dimension(75, 32));
        DepartmentComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DepartmentComboBoxActionPerformed(evt);
            }
        });
        DepartmentComboBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DepartmentComboBoxKeyPressed(evt);
            }
        });

        DateOfHireLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        DateOfHireLabel2.setText("Sort By Employee Type");
        DateOfHireLabel2.setPreferredSize(new java.awt.Dimension(122, 32));

        StatusLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        StatusLabel2.setText("Sort By Position");
        StatusLabel2.setPreferredSize(new java.awt.Dimension(86, 32));

        positionComboBox.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        positionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        positionComboBox.setToolTipText("Please Choose Employee Position for Sort Manage Employee Table!");
        positionComboBox.setPreferredSize(new java.awt.Dimension(75, 32));
        positionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                positionComboBoxActionPerformed(evt);
            }
        });
        positionComboBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                positionComboBoxKeyPressed(evt);
            }
        });

        employeeTypeComboBox.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        employeeTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        employeeTypeComboBox.setToolTipText("Please Choose Employee Type for Sort Manage Employee Table!");
        employeeTypeComboBox.setPreferredSize(new java.awt.Dimension(75, 32));
        employeeTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeTypeComboBoxActionPerformed(evt);
            }
        });
        employeeTypeComboBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                employeeTypeComboBoxKeyPressed(evt);
            }
        });

        JobTitleLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        JobTitleLabel2.setText("Sort By Status");
        JobTitleLabel2.setPreferredSize(new java.awt.Dimension(98, 32));

        statusComboBox.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        statusComboBox.setToolTipText("Please Choose Employee Status for Sort Manage Employee Table!");
        statusComboBox.setPreferredSize(new java.awt.Dimension(72, 32));
        statusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusComboBoxActionPerformed(evt);
            }
        });
        statusComboBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                statusComboBoxKeyPressed(evt);
            }
        });

        EmployeeTypeLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        EmployeeTypeLabel2.setText("Sort By Date of Hire");
        EmployeeTypeLabel2.setPreferredSize(new java.awt.Dimension(140, 32));

        updateButton.setText("Update");
        updateButton.setToolTipText("Click Here to Update Current Employee Status!");
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

        deleteButton.setText("Delete");
        deleteButton.setToolTipText("Click Here to Delete Current Selected Employee Data Row!");
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
        deleteButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                deleteButtonKeyPressed(evt);
            }
        });

        dateOfHireFromDayChooser.setDateFormatString("yyyy-MM-dd");
        dateOfHireFromDayChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateOfHireFromDayChooserPropertyChange(evt);
            }
        });
        dateOfHireFromDayChooser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dateOfHireFromDayChooserKeyPressed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("to");

        dateOfHireToDayChooser.setDateFormatString("yyyy-MM-dd");
        dateOfHireToDayChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateOfHireToDayChooserPropertyChange(evt);
            }
        });
        dateOfHireToDayChooser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dateOfHireToDayChooserKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(332, Short.MAX_VALUE)
                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(333, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(StatusLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DateOfHireLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DepartmentLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(employeeTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DepartmentComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(positionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EmployeeTypeLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JobTitleLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(dateOfHireFromDayChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(dateOfHireToDayChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DepartmentComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DepartmentLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JobTitleLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(employeeTypeComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(DateOfHireLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(StatusLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(positionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(EmployeeTypeLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateOfHireToDayChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateOfHireFromDayChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        addingPanel.add(jPanel4, java.awt.BorderLayout.CENTER);

        bodyPanel.add(addingPanel, java.awt.BorderLayout.PAGE_START);

        jPanel2.setPreferredSize(new java.awt.Dimension(1026, 200));

        ManageEmployeeTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ManageEmployeeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "NIC", "First Name", "Last Name", "Contact Number", "Email", "Date of Birth", "Date of Hire", "Date of Final", "Gender", "Address", "Status", "Employee Type", "Position", "Department"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ManageEmployeeTable.setToolTipText("Single Click for Load & Delete Employ Data or Double Click for Update Employee Data!");
        ManageEmployeeTable.getTableHeader().setReorderingAllowed(false);
        ManageEmployeeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ManageEmployeeTableMouseClicked(evt);
            }
        });
        ManageEmployeeTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ManageEmployeeTableKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(ManageEmployeeTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1011, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                .addContainerGap())
        );

        bodyPanel.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(bodyPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BackToDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToDashboardButtonActionPerformed

        this.dispose();

    }//GEN-LAST:event_BackToDashboardButtonActionPerformed

    private void DepartmentComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DepartmentComboBoxActionPerformed

        filterTableByDepartment();

    }//GEN-LAST:event_DepartmentComboBoxActionPerformed

    private void positionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_positionComboBoxActionPerformed

        filterTableByPosition();

    }//GEN-LAST:event_positionComboBoxActionPerformed

    private void employeeTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeTypeComboBoxActionPerformed

        filterTableByType();

    }//GEN-LAST:event_employeeTypeComboBoxActionPerformed

    private void statusComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusComboBoxActionPerformed

        filterTableByStatus();

    }//GEN-LAST:event_statusComboBoxActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed

        searchEmployee();

    }//GEN-LAST:event_searchButtonActionPerformed

    private void RefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshButtonActionPerformed

        reset();

    }//GEN-LAST:event_RefreshButtonActionPerformed

    private void EmployeeSearchFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_EmployeeSearchFieldFocusGained

        // Checks and set clear the current cityTextField to enter data to it
        if (EmployeeSearchField.getText().equals("Enter Employee Name or ID")) {

            EmployeeSearchField.setText("");
            EmployeeSearchField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_EmployeeSearchFieldFocusGained

    private void EmployeeSearchFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_EmployeeSearchFieldFocusLost

        // Sets back the placeholder of EmployeeSearchField
        if (EmployeeSearchField.getText().isEmpty()) {

            EmployeeSearchField.setText("Enter Employee Name or ID");
            EmployeeSearchField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_EmployeeSearchFieldFocusLost

    private void ManageEmployeeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ManageEmployeeTableMouseClicked

        if (evt.getClickCount() == 1) {

            int row = ManageEmployeeTable.getSelectedRow();

            if (row != -1) {

                // Get data from selected row
                String empId = ManageEmployeeTable.getValueAt(row, 0).toString();
                String fname = ManageEmployeeTable.getValueAt(row, 2).toString();
                String lname = ManageEmployeeTable.getValueAt(row, 3).toString();

                // sets text color to black
                EmployeeSearchField.setForeground(Color.BLACK);

                EmployeeSearchField.setText(empId);

            }

        }

        if (evt.getClickCount() == 2) {

            // Confirm Update Employee data
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to Update this Employee Data?", "Confirm to Proceed Update", JOptionPane.YES_NO_OPTION);

            // if not yes option code
            if (confirm != JOptionPane.YES_OPTION) {

                return; // User canceled updation

            }

            int row = ManageEmployeeTable.getSelectedRow();

            if (row != -1) {

                // Get data from selected row
                String empId = ManageEmployeeTable.getValueAt(row, 0).toString();

                // call openAddEmployeeGUI
                openAddEmployeeGUI(empId);

            }

        }

    }//GEN-LAST:event_ManageEmployeeTableMouseClicked

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed

        updateEmployeeStatus();

    }//GEN-LAST:event_updateButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed

        deleteEmployeeStatus();

    }//GEN-LAST:event_deleteButtonActionPerformed

    private void dateOfHireFromDayChooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateOfHireFromDayChooserPropertyChange

        // Get the From Data from the dateOfHireFromDayChooser
        fromDate = dateOfHireFromDayChooser.getDate();

        filterByDateRange();

    }//GEN-LAST:event_dateOfHireFromDayChooserPropertyChange

    private void dateOfHireToDayChooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateOfHireToDayChooserPropertyChange

        // Get the To Data from the dateOfHireToDayChooser
        toDate = dateOfHireToDayChooser.getDate();

        filterByDateRange();

    }//GEN-LAST:event_dateOfHireToDayChooserPropertyChange

    private void EmployeeSearchFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EmployeeSearchFieldKeyPressed
        // code to add key controls for EmployeeSearchField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT -> {

                searchButton.grabFocus();
                searchButton.doClick();

            }

            case KeyEvent.VK_DOWN ->
                EmployeeStatusComboBox.grabFocus();

            case KeyEvent.VK_DELETE ->
                EmployeeSearchField.setText("");

        }

    }//GEN-LAST:event_EmployeeSearchFieldKeyPressed

    private void searchButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchButtonKeyPressed
        // code to add key controls for searchButton

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                searchButton.doClick();

            case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_LEFT, KeyEvent.VK_A, KeyEvent.VK_BACK_SPACE, KeyEvent.VK_HOME ->
                EmployeeSearchField.grabFocus();

            case KeyEvent.VK_DOWN, KeyEvent.VK_S ->
                EmployeeStatusComboBox.grabFocus();

            case KeyEvent.VK_RIGHT, KeyEvent.VK_D ->
                RefreshButton.grabFocus();

            case KeyEvent.VK_DELETE ->
                RefreshButton.doClick();

        }

    }//GEN-LAST:event_searchButtonKeyPressed

    private void RefreshButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RefreshButtonKeyPressed
        // code to add key controls for RefreshButton

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_DELETE ->
                RefreshButton.doClick();

            case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_LEFT, KeyEvent.VK_A, KeyEvent.VK_BACK_SPACE ->
                searchButton.grabFocus();

            case KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_RIGHT, KeyEvent.VK_D ->
                EmployeeStatusComboBox.grabFocus();

            case KeyEvent.VK_HOME ->
                EmployeeSearchField.grabFocus();

        }

    }//GEN-LAST:event_RefreshButtonKeyPressed

    private void EmployeeStatusComboBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EmployeeStatusComboBoxKeyPressed
        // code to add key controls for EmployeeStatusComboBox

        int keyCode = evt.getKeyCode();
        int selectedIndex = EmployeeStatusComboBox.getSelectedIndex();

        if (selectedIndex >= 0) {

            switch (keyCode) {

                case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT -> {

                    updateButton.grabFocus();
                    updateButton.doClick();

                }

                case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_LEFT, KeyEvent.VK_A -> {

                    if (selectedIndex > 0) { // Prevent selecting an index less than 0
                        EmployeeStatusComboBox.setSelectedIndex(selectedIndex - 1);
                    }

                }

                case KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {

                    if (selectedIndex < EmployeeStatusComboBox.getItemCount() - 1) { // Prevent exceeding last index
                        EmployeeStatusComboBox.setSelectedIndex(selectedIndex + 1);
                    }

                }

                case KeyEvent.VK_BACK_SPACE, KeyEvent.VK_HOME ->
                    EmployeeSearchField.grabFocus();

                case KeyEvent.VK_SPACE, KeyEvent.VK_PAGE_DOWN, KeyEvent.VK_TAB ->
                    DepartmentComboBox.grabFocus();

                case KeyEvent.VK_DELETE ->
                    EmployeeStatusComboBox.setSelectedIndex(0);

            }

        }

    }//GEN-LAST:event_EmployeeStatusComboBoxKeyPressed

    private void DepartmentComboBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DepartmentComboBoxKeyPressed
        // code to add key controls for DepartmentComboBox

        int keyCode = evt.getKeyCode();
        int selectedIndex = DepartmentComboBox.getSelectedIndex();

        if (selectedIndex >= 0) {

            switch (keyCode) {

                case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                    statusComboBox.grabFocus();

                case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_LEFT, KeyEvent.VK_A -> {

                    if (selectedIndex > 0) { // Prevent selecting an index less than 0
                        DepartmentComboBox.setSelectedIndex(selectedIndex - 1);
                    }

                }

                case KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {

                    if (selectedIndex < DepartmentComboBox.getItemCount() - 1) { // Prevent exceeding last index
                        DepartmentComboBox.setSelectedIndex(selectedIndex + 1);
                    }

                }

                case KeyEvent.VK_BACK_SPACE, KeyEvent.VK_PAGE_UP, KeyEvent.VK_TAB ->
                    EmployeeStatusComboBox.grabFocus();

                case KeyEvent.VK_HOME ->
                    EmployeeSearchField.grabFocus();

                case KeyEvent.VK_DELETE ->
                    DepartmentComboBox.setSelectedIndex(0);

            }

        }

    }//GEN-LAST:event_DepartmentComboBoxKeyPressed

    private void statusComboBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_statusComboBoxKeyPressed
        // code to add key controls for statusComboBox

        int keyCode = evt.getKeyCode();
        int selectedIndex = statusComboBox.getSelectedIndex();

        if (selectedIndex >= 0) {

            switch (keyCode) {

                case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                    employeeTypeComboBox.grabFocus();

                case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_LEFT, KeyEvent.VK_A -> {

                    if (selectedIndex > 0) { // Prevent selecting an index less than 0
                        statusComboBox.setSelectedIndex(selectedIndex - 1);
                    }

                }

                case KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {

                    if (selectedIndex < statusComboBox.getItemCount() - 1) { // Prevent exceeding last index
                        statusComboBox.setSelectedIndex(selectedIndex + 1);
                    }

                }

                case KeyEvent.VK_BACK_SPACE ->
                    DepartmentComboBox.grabFocus();

                case KeyEvent.VK_HOME ->
                    EmployeeSearchField.grabFocus();

                case KeyEvent.VK_DELETE ->
                    statusComboBox.setSelectedIndex(0);

            }

        }

    }//GEN-LAST:event_statusComboBoxKeyPressed

    private void employeeTypeComboBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_employeeTypeComboBoxKeyPressed
        // code to add key controls for statusComboBox

        int keyCode = evt.getKeyCode();
        int selectedIndex = employeeTypeComboBox.getSelectedIndex();

        if (selectedIndex >= 0) {

            switch (keyCode) {

                case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                    positionComboBox.grabFocus();

                case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_LEFT, KeyEvent.VK_A -> {

                    if (selectedIndex > 0) { // Prevent selecting an index less than 0
                        employeeTypeComboBox.setSelectedIndex(selectedIndex - 1);
                    }

                }

                case KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {

                    if (selectedIndex < employeeTypeComboBox.getItemCount() - 1) { // Prevent exceeding last index
                        employeeTypeComboBox.setSelectedIndex(selectedIndex + 1);
                    }

                }

                case KeyEvent.VK_BACK_SPACE ->
                    DepartmentComboBox.grabFocus();

                case KeyEvent.VK_HOME ->
                    EmployeeSearchField.grabFocus();

                case KeyEvent.VK_DELETE ->
                    employeeTypeComboBox.setSelectedIndex(0);

            }

        }

    }//GEN-LAST:event_employeeTypeComboBoxKeyPressed

    private void positionComboBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_positionComboBoxKeyPressed
        // code to add key controls for positionComboBox

        int keyCode = evt.getKeyCode();
        int selectedIndex = positionComboBox.getSelectedIndex();

        if (selectedIndex >= 0) {

            switch (keyCode) {

                case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                    // select dateOfHireFromDayChooser
                    dateOfHireFromDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

                case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_LEFT, KeyEvent.VK_A -> {

                    if (selectedIndex > 0) { // Prevent selecting an index less than 0
                        positionComboBox.setSelectedIndex(selectedIndex - 1);
                    }

                }

                case KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {

                    if (selectedIndex < positionComboBox.getItemCount() - 1) { // Prevent exceeding last index
                        positionComboBox.setSelectedIndex(selectedIndex + 1);
                    }

                }

                case KeyEvent.VK_BACK_SPACE ->
                    employeeTypeComboBox.grabFocus();

                case KeyEvent.VK_HOME ->
                    EmployeeSearchField.grabFocus();

                case KeyEvent.VK_DELETE ->
                    positionComboBox.setSelectedIndex(0);

            }

        }

    }//GEN-LAST:event_positionComboBoxKeyPressed

    private void dateOfHireFromDayChooserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dateOfHireFromDayChooserKeyPressed
        // code to add key controls for dateOfHireFromDayChooser

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                // select dateOfHireToDayChooser
                dateOfHireToDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

            case KeyEvent.VK_DOWN ->
                ManageEmployeeTable.requestFocusInWindow();

            case KeyEvent.VK_UP ->
                statusComboBox.grabFocus();

            case KeyEvent.VK_HOME ->
                EmployeeSearchField.grabFocus();

            case KeyEvent.VK_DELETE ->
                dateOfHireFromDayChooser.setDate(null);

        }

    }//GEN-LAST:event_dateOfHireFromDayChooserKeyPressed

    private void dateOfHireToDayChooserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dateOfHireToDayChooserKeyPressed
        // code to add key controls for dateOfHireToDayChooser

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_DOWN ->
                ManageEmployeeTable.requestFocusInWindow();

            case KeyEvent.VK_UP ->
                statusComboBox.grabFocus();

            case KeyEvent.VK_HOME ->
                EmployeeSearchField.grabFocus();

            case KeyEvent.VK_DELETE ->
                dateOfHireFromDayChooser.setDate(null);

        }

    }//GEN-LAST:event_dateOfHireToDayChooserKeyPressed

    private void updateButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_updateButtonKeyPressed
        // code to add key controls for updateButton

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                updateButton.doClick();

            case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_LEFT, KeyEvent.VK_A ->
                positionComboBox.grabFocus();

            case KeyEvent.VK_BACK_SPACE ->
                EmployeeStatusComboBox.grabFocus();

            case KeyEvent.VK_RIGHT, KeyEvent.VK_D ->
                deleteButton.grabFocus();

            case KeyEvent.VK_DOWN, KeyEvent.VK_S ->
                ManageEmployeeTable.requestFocusInWindow();

            case KeyEvent.VK_HOME ->
                EmployeeSearchField.grabFocus();

            case KeyEvent.VK_DELETE -> {

                RefreshButton.grabFocus();
                RefreshButton.doClick();

            }

        }

    }//GEN-LAST:event_updateButtonKeyPressed

    private void deleteButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_deleteButtonKeyPressed
        // code to add key controls for deleteButton

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                deleteButton.doClick();

            case KeyEvent.VK_UP, KeyEvent.VK_W ->
                // select dateOfHireFromDayChooser
                dateOfHireFromDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

            case KeyEvent.VK_LEFT, KeyEvent.VK_A, KeyEvent.VK_BACK_SPACE ->
                updateButton.grabFocus();

            case KeyEvent.VK_RIGHT, KeyEvent.VK_D ->
                // select dateOfHireToDayChooser
                dateOfHireToDayChooser.getDateEditor().getUiComponent().requestFocusInWindow();

            case KeyEvent.VK_DOWN, KeyEvent.VK_S ->
                ManageEmployeeTable.requestFocusInWindow();

            case KeyEvent.VK_HOME ->
                EmployeeSearchField.grabFocus();

            case KeyEvent.VK_DELETE -> {

                RefreshButton.grabFocus();
                RefreshButton.doClick();

            }

        }

    }//GEN-LAST:event_deleteButtonKeyPressed

    private void ManageEmployeeTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ManageEmployeeTableKeyPressed
        // code to add key controls for ManageEmployeeTable

        int keyCode = evt.getKeyCode();

        int row = ManageEmployeeTable.getSelectedRow();

        if (row != -1) {

            if (keyCode == KeyEvent.VK_DELETE) {

                // Call delete method
                deleteEmployeeStatus();

            } else if (keyCode == KeyEvent.VK_ENTER) {

                // Open update GUI
                String empId = ManageEmployeeTable.getValueAt(row, 0).toString();
                openAddEmployeeGUI(empId);

            }

        }

    }//GEN-LAST:event_ManageEmployeeTableKeyPressed

    private void EmployeeStatusComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeStatusComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmployeeStatusComboBoxActionPerformed

    private void AddpositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddpositionActionPerformed

        String empPosition = String.valueOf(PositionAddComboBox.getSelectedItem());

        String empID = EmployeeSearchField.getText();

        if (empPosition.equals("Select Position")) {

            JOptionPane.showMessageDialog(this, "Position Null", "Please select department", JOptionPane.ERROR_MESSAGE);

        } else {

            try {

                ResultSet empAddPosition = MySql.executeSearch("SELECT `employee_position_employee_position_id` FROM `employee` WHERE `employee_id` = '" + empID + "' ");

                if (empAddPosition.next()) {

                    String employeeId = empAddPosition.getString("employee_position_employee_position_id");

                    if (employeeId == null || employeeId.trim().isEmpty()) {

                        try {

                            MySql.executeUpdate("UPDATE `employee` SET "
                                    + "`employee_position_employee_position_id` = '" + EmpPositionHashMap.get(empPosition) + "' WHERE `employee_id` = '" + empID + "' ");

                            JOptionPane.showMessageDialog(this, "Add To Position", empID + " Add to position Successfully", JOptionPane.INFORMATION_MESSAGE);

                            loadEmployeeData();
                            reset();

                        } catch (Exception e) {

                            e.printStackTrace();

                        }

                    }

                }

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

    }//GEN-LAST:event_AddpositionActionPerformed

    private void AddpositionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AddpositionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddpositionKeyPressed

    private void PrintButttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintButttonActionPerformed
      try {
            // Path to the compiled Jasper report
            String reportPath = "src/report/Manage_Employee.jasper";

            // Create parameters map (if needed)
            HashMap<String, Object> parameters = new HashMap<>();

            // Create data source from JTable model
            JRTableModelDataSource dataSource = new JRTableModelDataSource(ManageEmployeeTable.getModel());

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, dataSource);

            // Show the report in viewer
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating report: " + e.getMessage(), "Report Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_PrintButttonActionPerformed

    private void openAddEmployeeGUI(String empId) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AddEmployee(this, empId).setVisible(true);
            this.setVisible(false); // Hide Manage Employee (this) Frame
        });

    }

    // Method to refresh the ManageEmployee GUI From AddEmployee GUI
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
            new ManageEmployee().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.k33ptoo.components.KButton Addposition;
    private javax.swing.JButton BackToDashboardButton;
    private javax.swing.JPanel BackToDashboardPanel;
    private javax.swing.JLabel DateOfHireLabel2;
    private javax.swing.JComboBox<String> DepartmentComboBox;
    private javax.swing.JLabel DepartmentLabel2;
    private javax.swing.JTextField EmployeeSearchField;
    private javax.swing.JComboBox<String> EmployeeStatusComboBox;
    private javax.swing.JLabel EmployeeStatusLabel;
    private javax.swing.JLabel EmployeeTypeLabel2;
    private javax.swing.JLabel JobTitleLabel2;
    private javax.swing.JTable ManageEmployeeTable;
    private javax.swing.JComboBox<String> PositionAddComboBox;
    private com.k33ptoo.components.KButton PrintButtton;
    private javax.swing.JButton RefreshButton;
    private javax.swing.JPanel SearchPanel;
    private javax.swing.JLabel StatusLabel2;
    private javax.swing.JPanel addingPanel;
    private javax.swing.JPanel bodyPanel;
    private com.toedter.calendar.JDateChooser dateOfHireFromDayChooser;
    private com.toedter.calendar.JDateChooser dateOfHireToDayChooser;
    private com.k33ptoo.components.KButton deleteButton;
    private javax.swing.JComboBox<String> employeeTypeComboBox;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JComboBox<String> positionComboBox;
    private com.k33ptoo.components.KButton searchButton;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JComboBox<String> statusComboBox;
    private com.k33ptoo.components.KButton updateButton;
    // End of variables declaration//GEN-END:variables

    private void reset() {

        // Reset all components to default values
        EmployeeSearchField.setText("");
        EmployeeStatusComboBox.setSelectedIndex(0);
        ManageEmployeeTable.clearSelection();
        DepartmentComboBox.setSelectedIndex(0);
        statusComboBox.setSelectedIndex(0);
        employeeTypeComboBox.setSelectedIndex(0);
        positionComboBox.setSelectedIndex(0);
        dateOfHireFromDayChooser.setDate(null);
        dateOfHireToDayChooser.setDate(null);

        // Re-add the placeholders to refreshed TextFields
        addPlaceholder();

        // Re-Load EmployeeData
        loadEmployeeData();

        // grabs EmployeeSearchField focus
        EmployeeSearchField.grabFocus();

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

                RefreshButton.doClick(); // Simulate Refresh button press

            }

        });

    }

}
