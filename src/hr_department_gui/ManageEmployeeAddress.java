/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hr_department_gui;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.MySql;

/**
 *
 * @author DELL
 */
public class ManageEmployeeAddress extends javax.swing.JFrame {

    private String sid;

    private Map<String, Integer> provinceMap = new HashMap<>();
    private Map<String, Integer> districtMap = new HashMap<>();
//    private static HashMap<String, String> departmentMap = new HashMap<>();

    /**
     * Creates new form ManageEmployeeAddress
     */
    public ManageEmployeeAddress() {

        initComponents();
        loadAddress();
        loadProvinces();
        loadProvinces2();
        provinceComboBox.addActionListener(e -> loadDistricts());
        provinceComboBox2.addActionListener(e -> loadDistricts2());
        DistricComboBox.addActionListener(e -> loadCities());
        districtComboBox2.addActionListener(e -> loadCities2());
//        loadDistricts();
//        loadDearpement();

    }

    DefaultTableModel model;

    private void loadAddress() {

        try {

            ResultSet resultSet = MySql.executeSearch(" SELECT * FROM `employee_address` "
                    + "INNER JOIN `employee` ON `employee`.`employee_address_em_address_id` = `employee_address`.`em_address_id` "
                    + "INNER JOIN `city` ON `city`.`city_id` = `employee_address`.`city_city_id` "
                    + "INNER JOIN `province` ON `province`.`province_id` = `employee_address`.`province_province_id` "
                    + "INNER JOIN `district` ON `district`.`district_id` = `employee_address`.`district_district_id` ");

            model = (DefaultTableModel) ManageEmployeeAddressTable.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {

                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("employee.employee_id"));
                vector.add(resultSet.getString("employee.first_name"));
                vector.add(resultSet.getString("employee.last_name"));
                vector.add(resultSet.getString("employee_address.address_line01"));
                vector.add(resultSet.getString("employee_address.address_line02"));
                vector.add(resultSet.getString("province.province_name"));
                vector.add(resultSet.getString("district.district_name"));
                vector.add(resultSet.getString("city.city_name"));
                vector.add(resultSet.getString("employee.email"));

                model.addRow(vector);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

// Load provinces from the database and populate the provinceComboBox
    private void loadProvinces() {

        try {

            // Fetch all provinces from the database
            ResultSet rs = MySql.executeSearch("SELECT * FROM province");

            // Clear existing items from the combo box and map
            provinceComboBox.removeAllItems();

            provinceMap.clear();

            // Add "Select" as the first item in the combo box
            provinceComboBox.addItem("Select");

            // Add each province to the combo box and map
            while (rs.next()) {

                String provinceName = rs.getString("province_name");

                int provinceId = rs.getInt("province_id");

                provinceComboBox.addItem(provinceName);

                provinceMap.put(provinceName, provinceId); // Store province ID for lookup

            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

// Load provinces from the database and populate the provinceComboBox
    private void loadProvinces2() {

        try {

            // Fetch all provinces from the database
            ResultSet rs = MySql.executeSearch("SELECT * FROM province");

            // Clear existing items from the combo box and map
            provinceComboBox2.removeAllItems();

            provinceMap.clear();

            // Add "Select" as the first item in the combo box
            provinceComboBox2.addItem("Select");

            // Add each province to the combo box and map
            while (rs.next()) {

                String provinceName = rs.getString("province_name");

                int provinceId = rs.getInt("province_id");

                provinceComboBox2.addItem(provinceName);

                provinceMap.put(provinceName, provinceId); // Store province ID for lookup

            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

// Load districts based on the selected province
    private void loadDistricts() {

        // Get the selected province name
        String selectedProvince = (String) provinceComboBox.getSelectedItem();

        // Check if a province is selected
        if (selectedProvince == null || "Select".equals(selectedProvince)) {

            System.out.println("No province selected.");
            DistricComboBox.removeAllItems();
            CityComboBox.removeAllItems();
            DistricComboBox.addItem("Select");
            CityComboBox.addItem("Select");
            return;

        }

        // Retrieve the province ID from the map
        Integer provinceId = provinceMap.get(selectedProvince);

        if (provinceId == null) {

            System.out.println("Province not found in the map: " + selectedProvince);
            return;

        }

        try {

            // Query to fetch districts related to the selected province
            String query = "SELECT * FROM district WHERE province_province_id = " + provinceId;

            ResultSet rs = MySql.executeSearch(query);

            // Clear existing items from the district combobox and map
            DistricComboBox.removeAllItems();

            districtMap.clear();

            // Add each district to the combo box and map
            while (rs.next()) {

                String districtName = rs.getString("district_name");

                int districtId = rs.getInt("district_id");

                DistricComboBox.addItem(districtName);

                districtMap.put(districtName, districtId); // Store district ID for lookup

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

// Load districts2 based on the selected province
    private void loadDistricts2() {

        // Get the selected province name
        String selectedProvince = (String) provinceComboBox2.getSelectedItem();

        // Check if a province is selected
        if (selectedProvince == null || "Select".equals(selectedProvince)) {

            System.out.println("No province selected.");
            districtComboBox2.removeAllItems();
            cityComboBox2.removeAllItems();
            districtComboBox2.addItem("Select");
            cityComboBox2.addItem("Select");
            return;

        }

        // Retrieve the province ID from the map
        Integer provinceId = provinceMap.get(selectedProvince);

        if (provinceId == null) {

            System.out.println("Province not found in the map: " + selectedProvince);
            return;

        }

        try {

            // Query to fetch districts related to the selected province
            String query = "SELECT * FROM district WHERE province_province_id = " + provinceId;

            ResultSet rs = MySql.executeSearch(query);

            // Clear existing items from the district combobox and map
            districtComboBox2.removeAllItems();

            districtMap.clear();

            // Add each district to the combo box and map
            while (rs.next()) {

                String districtName = rs.getString("district_name");

                int districtId = rs.getInt("district_id");

                districtComboBox2.addItem(districtName);

                districtMap.put(districtName, districtId); // Store district ID for lookup

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

// Load cities based on the selected district
    private void loadCities() {

        // Get the selected district name
        String selectedDistrict = (String) DistricComboBox.getSelectedItem();

        // Check if a district is selected
        if (selectedDistrict == null) {

            System.out.println("No district selected.");

            return;

        }

        // Retrieve the district ID from the map
        Integer districtId = districtMap.get(selectedDistrict);

        if (districtId == null) {

            System.out.println("District not found in the map: " + selectedDistrict);

            return;

        }

        try {

            // Query to fetch cities related to the selected district
            String query = "SELECT * FROM city WHERE district_district_id = " + districtId;

            ResultSet rs = MySql.executeSearch(query);

            // Clear existing items from the city combo box
            CityComboBox.removeAllItems();

            // Add each city to the combo box
            while (rs.next()) {

                String cityName = rs.getString("city_name");

                CityComboBox.addItem(cityName);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

// Load cities2 based on the selected district
    private void loadCities2() {

        // Get the selected district name
        String selectedDistrict = (String) districtComboBox2.getSelectedItem();

        // Check if a district is selected
        if (selectedDistrict == null) {

            System.out.println("No district selected.");

            return;

        }

        // Retrieve the district ID from the map
        Integer districtId = districtMap.get(selectedDistrict);

        if (districtId == null) {

            System.out.println("District not found in the map: " + selectedDistrict);

            return;

        }

        try {

            // Query to fetch cities related to the selected district
            String query = "SELECT * FROM city WHERE district_district_id = " + districtId;

            ResultSet rs = MySql.executeSearch(query);

            // Clear existing items from the city combo box
            cityComboBox2.removeAllItems();

            // Add each city to the combo box
            while (rs.next()) {

                String cityName = rs.getString("city_name");

                cityComboBox2.addItem(cityName);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void search(String searchID) {

        model = (DefaultTableModel) ManageEmployeeAddressTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
        ManageEmployeeAddressTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(searchID, 0));

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
        SearchPanel = new javax.swing.JPanel();
        EmpId = new javax.swing.JTextField();
        NameLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        searchButton = new com.k33ptoo.components.KButton();
        jButton1 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        AddressLine_01 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        AddressLine_02 = new javax.swing.JTextField();
        provinceComboBox = new javax.swing.JComboBox<>();
        DistricComboBox = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        CityComboBox = new javax.swing.JComboBox<>();
        TableUpdatePanel = new javax.swing.JPanel();
        BackToDashboardPanel = new javax.swing.JPanel();
        BackToDashboardButton = new javax.swing.JButton();
        TableViewPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ManageEmployeeAddressTable = new javax.swing.JTable();
        updateButton = new com.k33ptoo.components.KButton();
        deleteButton = new com.k33ptoo.components.KButton();
        districtComboBox2 = new javax.swing.JComboBox<>();
        DistrictLabel = new javax.swing.JLabel();
        provinceComboBox2 = new javax.swing.JComboBox<>();
        ProvinceLabel = new javax.swing.JLabel();
        CityLabel = new javax.swing.JLabel();
        cityComboBox2 = new javax.swing.JComboBox<>();
        AddNewAddressButton = new com.k33ptoo.components.KButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        HeaderPanel.setBackground(new java.awt.Color(153, 153, 153));
        HeaderPanel.setPreferredSize(new java.awt.Dimension(776, 60));

        jLabel1.setFont(new java.awt.Font("Audiowide", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Manage Employee Address");

        javax.swing.GroupLayout HeaderPanelLayout = new javax.swing.GroupLayout(HeaderPanel);
        HeaderPanel.setLayout(HeaderPanelLayout);
        HeaderPanelLayout.setHorizontalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addContainerGap(355, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                .addContainerGap(355, Short.MAX_VALUE))
        );
        HeaderPanelLayout.setVerticalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        getContentPane().add(HeaderPanel, java.awt.BorderLayout.PAGE_START);

        BodyPanel.setLayout(new java.awt.BorderLayout());

        SearchPanel.setPreferredSize(new java.awt.Dimension(780, 200));

        EmpId.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        EmpId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                EmpIdKeyReleased(evt);
            }
        });

        NameLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        NameLabel.setText("Search by ID");

        searchButton.setText("Search");
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

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/refresh.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel2.setText("Province");

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel3.setText("District");

        AddressLine_01.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel4.setText("Address Line 01");

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel5.setText("Address Line 02");

        AddressLine_02.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        provinceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));

        DistricComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel6.setText("City");

        CityComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));

        javax.swing.GroupLayout SearchPanelLayout = new javax.swing.GroupLayout(SearchPanel);
        SearchPanel.setLayout(SearchPanelLayout);
        SearchPanelLayout.setHorizontalGroup(
            SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addGroup(SearchPanelLayout.createSequentialGroup()
                .addContainerGap(325, Short.MAX_VALUE)
                .addComponent(NameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(EmpId, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(325, Short.MAX_VALUE))
            .addGroup(SearchPanelLayout.createSequentialGroup()
                .addContainerGap(311, Short.MAX_VALUE)
                .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SearchPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(20, 20, 20)
                        .addComponent(provinceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(20, 20, 20)
                        .addComponent(DistricComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(20, 20, 20)
                        .addComponent(CityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SearchPanelLayout.createSequentialGroup()
                        .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(30, 30, 30)
                        .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AddressLine_01)
                            .addComponent(AddressLine_02))))
                .addContainerGap(311, Short.MAX_VALUE))
        );
        SearchPanelLayout.setVerticalGroup(
            SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SearchPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(NameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(EmpId, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(provinceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DistricComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(AddressLine_01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(AddressLine_02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        BodyPanel.add(SearchPanel, java.awt.BorderLayout.PAGE_START);

        TableUpdatePanel.setLayout(new java.awt.BorderLayout());

        BackToDashboardPanel.setBackground(new java.awt.Color(153, 153, 153));
        BackToDashboardPanel.setPreferredSize(new java.awt.Dimension(780, 50));

        BackToDashboardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/back-arrow.png"))); // NOI18N
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
                .addGap(18, 18, 18)
                .addComponent(BackToDashboardButton)
                .addContainerGap(1226, Short.MAX_VALUE))
        );
        BackToDashboardPanelLayout.setVerticalGroup(
            BackToDashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BackToDashboardPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BackToDashboardButton)
                .addContainerGap())
        );

        TableUpdatePanel.add(BackToDashboardPanel, java.awt.BorderLayout.PAGE_END);

        ManageEmployeeAddressTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ManageEmployeeAddressTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "First Name", "Last Name", "Address Line 01", "Address Line 02", "Province", "District", "City", "Employee Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ManageEmployeeAddressTable.getTableHeader().setReorderingAllowed(false);
        ManageEmployeeAddressTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ManageEmployeeAddressTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(ManageEmployeeAddressTable);

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

        districtComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        districtComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                districtComboBox2ItemStateChanged(evt);
            }
        });

        DistrictLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        DistrictLabel.setText("Sort by Distrcit");

        provinceComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        provinceComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                provinceComboBox2ItemStateChanged(evt);
            }
        });
        provinceComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                provinceComboBox2ActionPerformed(evt);
            }
        });

        ProvinceLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        ProvinceLabel.setText("Sort by Province");

        CityLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        CityLabel.setText("Sort by City");

        cityComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));

        AddNewAddressButton.setText("Add New Address");
        AddNewAddressButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AddNewAddressButton.setkEndColor(new java.awt.Color(0, 204, 204));
        AddNewAddressButton.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        AddNewAddressButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        AddNewAddressButton.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        AddNewAddressButton.setkPressedColor(new java.awt.Color(0, 102, 153));
        AddNewAddressButton.setkSelectedColor(new java.awt.Color(0, 102, 153));
        AddNewAddressButton.setkStartColor(new java.awt.Color(0, 102, 153));
        AddNewAddressButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddNewAddressButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TableViewPanelLayout = new javax.swing.GroupLayout(TableViewPanel);
        TableViewPanel.setLayout(TableViewPanelLayout);
        TableViewPanelLayout.setHorizontalGroup(
            TableViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TableViewPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AddNewAddressButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(TableViewPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(TableViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TableViewPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(ProvinceLabel)
                        .addGap(20, 20, 20)
                        .addComponent(provinceComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CityLabel)
                        .addGap(20, 20, 20)
                        .addComponent(cityComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(DistrictLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(districtComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(45, 45, 45))
        );
        TableViewPanelLayout.setVerticalGroup(
            TableViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TableViewPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(TableViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddNewAddressButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(TableViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ProvinceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(TableViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(DistrictLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(districtComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(provinceComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cityComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        TableUpdatePanel.add(TableViewPanel, java.awt.BorderLayout.CENTER);

        BodyPanel.add(TableUpdatePanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(BodyPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BackToDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToDashboardButtonActionPerformed

        this.dispose();

    }//GEN-LAST:event_BackToDashboardButtonActionPerformed

    private void EmpIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EmpIdKeyReleased

        String searchID = EmpId.getText();

        search(searchID);

    }//GEN-LAST:event_EmpIdKeyReleased

    private void ManageEmployeeAddressTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ManageEmployeeAddressTableMouseClicked

        if (evt.getClickCount() == 2) {

            int row = ManageEmployeeAddressTable.getSelectedRow(); // Get the selected row from the table

            // Retrieve the Province name and set it in the provinceComboBox
            String Province = String.valueOf(ManageEmployeeAddressTable.getValueAt(row, 5));
            provinceComboBox.setSelectedItem(Province);

            // Retrieve the District name and set it in the DistricComboBox
            String District = String.valueOf(ManageEmployeeAddressTable.getValueAt(row, 6));
            DistricComboBox.setSelectedItem(District);

            // Retrieve the City name and set it in the CityComboBox
            String City = String.valueOf(ManageEmployeeAddressTable.getValueAt(row, 7));
            CityComboBox.setSelectedItem(City);

            // Retrieve the AddressLine_01 from the selected row and set it in the AddressLine_01 text field
            String Address01 = String.valueOf(ManageEmployeeAddressTable.getValueAt(row, 3));
            AddressLine_01.setText(Address01);

            // Retrieve the AddressLine_02 from the selected row and set it in the AddressLine_02 text field
            String Address02 = String.valueOf(ManageEmployeeAddressTable.getValueAt(row, 4));
            AddressLine_02.setText(Address02);

            // Retrieve the AddressLine_02 from the selected row and set it in the AddressLine_02 text field
            String employeeId = String.valueOf(ManageEmployeeAddressTable.getValueAt(row, 0));
            EmpId.setText(employeeId);

        }

    }//GEN-LAST:event_ManageEmployeeAddressTableMouseClicked

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed

        try {
            // Get the values entered by the user in the form fields
            String province = String.valueOf(provinceComboBox.getSelectedItem());
            String district = String.valueOf(DistricComboBox.getSelectedItem());
            String city = String.valueOf(CityComboBox.getSelectedItem());
            String addressLine1 = AddressLine_01.getText();
            String addressLine2 = AddressLine_02.getText();
            String employeeId = EmpId.getText();

            // Validate the form fields to ensure that no required fields are empty
            if (province.isEmpty() || district.isEmpty() || city.isEmpty() || addressLine1.isEmpty() || addressLine2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Query to fetch the original data of the employee
            String query = "SELECT ea.em_address_id, p.province_name, d.district_name, c.city_name, ea.address_line01, ea.address_line02 "
                    + "FROM employee_address ea "
                    + "INNER JOIN employee e ON e.employee_address_em_address_id = ea.em_address_id "
                    + "INNER JOIN city c ON c.city_id = ea.city_city_id "
                    + "INNER JOIN province p ON p.province_id = ea.province_province_id "
                    + "INNER JOIN district d ON d.district_id = ea.district_district_id "
                    + "WHERE e.employee_id = '" + employeeId + "' ";

            ResultSet rs = MySql.executeSearch(query);

            if (rs.next()) {
                int addressId = rs.getInt("em_address_id");
                String originalProvince = rs.getString("province_name");
                String originalDistrict = rs.getString("district_name");
                String originalCity = rs.getString("city_name");
                String originalAddressLine1 = rs.getString("address_line01");
                String originalAddressLine2 = rs.getString("address_line02");

                // Build the dynamic update query
                StringBuilder updateQuery = new StringBuilder("UPDATE employee_address SET ");
                boolean hasChanges = false;

                if (!province.equals(originalProvince)) {
                    updateQuery.append("province_province_id = (SELECT province_id FROM province WHERE province_name = '")
                            .append(province).append("'), ");
                    hasChanges = true;
                }
                if (!district.equals(originalDistrict)) {
                    updateQuery.append("district_district_id = (SELECT district_id FROM district WHERE district_name = '")
                            .append(district).append("'), ");
                    hasChanges = true;
                }
                if (!city.equals(originalCity)) {
                    updateQuery.append("city_city_id = (SELECT city_id FROM city WHERE city_name = '")
                            .append(city).append("'), ");
                    hasChanges = true;
                }
                if (!addressLine1.equals(originalAddressLine1)) {
                    updateQuery.append("address_line01 = '").append(addressLine1).append("', ");
                    hasChanges = true;
                }
                if (!addressLine2.equals(originalAddressLine2)) {
                    updateQuery.append("address_line02 = '").append(addressLine2).append("', ");
                    hasChanges = true;
                }

                // Execute the update query if there are changes
                if (hasChanges) {
                    // Remove the trailing comma and space
                    updateQuery.setLength(updateQuery.length() - 2);
                    updateQuery.append(" WHERE em_address_id = ").append(addressId);

                    MySql.executeUpdate(updateQuery.toString());
                    JOptionPane.showMessageDialog(this, "Employee address updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadAddress();

                } else {
                    JOptionPane.showMessageDialog(this, "No changes detected.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Employee address not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_updateButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        reset();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void provinceComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_provinceComboBox2ItemStateChanged

        //        ComboBox Select Change
        String Province = String.valueOf(provinceComboBox2.getSelectedItem());

        try {

//                    Retrieve departement ID From the Map
            Integer ProvinceID = provinceMap.get(Province);

//                    Query
            String query = "SELECT * FROM `employee_address` "
                    + "INNER JOIN `employee` ON `employee`.`employee_address_em_address_id` = `employee_address`.`em_address_id` "
                    + "INNER JOIN `city` ON `city`.`city_id` = `employee_address`.`city_city_id` "
                    + "INNER JOIN `province` ON `province`.`province_id` = `employee_address`.`province_province_id` "
                    + "INNER JOIN `district` ON `district`.`district_id` = `employee_address`.`district_district_id`";

            if (Province.equals("Select")) {

                query += "";

            } else if (Province.equals(Province)) {

                String departmentid = String.valueOf(provinceComboBox2.getSelectedIndex());
                query += " WHERE `province`.`province_id` = '" + ProvinceID + "' ";

            }

            ResultSet resultSet = MySql.executeSearch(query);

//                    Clear and Update table
            model = (DefaultTableModel) ManageEmployeeAddressTable.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {

                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("employee.employee_id"));
                vector.add(resultSet.getString("employee.first_name"));
                vector.add(resultSet.getString("employee.last_name"));
                vector.add(resultSet.getString("employee_address.address_line01"));
                vector.add(resultSet.getString("employee_address.address_line02"));
                vector.add(resultSet.getString("province.province_name"));
                vector.add(resultSet.getString("district.district_name"));
                vector.add(resultSet.getString("city.city_name"));
                vector.add(resultSet.getString("employee.email"));

                model.addRow(vector);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }//GEN-LAST:event_provinceComboBox2ItemStateChanged

    private void districtComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_districtComboBox2ItemStateChanged


    }//GEN-LAST:event_districtComboBox2ItemStateChanged

    private void provinceComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_provinceComboBox2ActionPerformed

//        loadProvinces2();

    }//GEN-LAST:event_provinceComboBox2ActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed

        try {
            
            String searchID = EmpId.getText().trim();

            if (searchID.isEmpty()) {
                
                JOptionPane.showMessageDialog(this, "Please enter Employee ID", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
                
            }

            int response = JOptionPane.showConfirmDialog(
                    
                    this,
                    "Are you sure you want to delete this record?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                    
            );

            if (response == JOptionPane.YES_OPTION) {
                
                // Check if employee exists
                String checkQuery = "SELECT COUNT(*) AS count FROM employee WHERE employee_id = '" + searchID + "'";
                
                ResultSet rs = MySql.executeSearch(checkQuery);

                if (rs.next() && rs.getInt("count") > 0) {
                    
                    // Start a transaction
                    MySql.executeUpdate("START TRANSACTION");

                    try {
                        
                        // 1️⃣ Delete dependent records in 'leave' table
                        String deleteLeaveQuery = "DELETE FROM `order_management_system_with_erp_db`.`leave` WHERE `employee_employee_id` = '" + searchID + "' ";
                        MySql.executeUpdate(deleteLeaveQuery);

                        // 2⃣ delete the employee record
                        String deleteEmployeeQuery = "DELETE FROM employee WHERE employee_id = '" + searchID + "'";
                        MySql.executeUpdate(deleteEmployeeQuery);

                        // 23⃣ Delete dependent records in 'employee_address' table
                        String deleteAddressQuery = "DELETE FROM employee_address WHERE em_address_id = (SELECT employee_address_em_address_id FROM employee WHERE employee_id = '" + searchID + "')";
                        MySql.executeUpdate(deleteAddressQuery);

                        // Commit transaction
                        MySql.executeUpdate("COMMIT");

                        JOptionPane.showMessageDialog(this, "Employee deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadAddress();
                        
                    } catch (Exception e) {
                        
                        // Rollback in case of an error
                        MySql.executeUpdate("ROLLBACK");
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "An error occurred while deleting the records. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    
                    } finally {
                        
                        rs.close();
                        
                    }
                    
                } else {
                    
                    JOptionPane.showMessageDialog(this, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    
                }
                
            }
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
        }

    }//GEN-LAST:event_deleteButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchButtonActionPerformed

    private void AddNewAddressButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddNewAddressButtonActionPerformed
        
        EmployeeAddress ea = new EmployeeAddress(this, rootPaneCheckingEnabled, sid);
        ea.setVisible(true);
        
    }//GEN-LAST:event_AddNewAddressButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageEmployeeAddress().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.k33ptoo.components.KButton AddNewAddressButton;
    private javax.swing.JTextField AddressLine_01;
    private javax.swing.JTextField AddressLine_02;
    private javax.swing.JButton BackToDashboardButton;
    private javax.swing.JPanel BackToDashboardPanel;
    private javax.swing.JPanel BodyPanel;
    private javax.swing.JComboBox<String> CityComboBox;
    private javax.swing.JLabel CityLabel;
    private javax.swing.JComboBox<String> DistricComboBox;
    private javax.swing.JLabel DistrictLabel;
    private javax.swing.JTextField EmpId;
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JTable ManageEmployeeAddressTable;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JLabel ProvinceLabel;
    private javax.swing.JPanel SearchPanel;
    private javax.swing.JPanel TableUpdatePanel;
    private javax.swing.JPanel TableViewPanel;
    private javax.swing.JComboBox<String> cityComboBox2;
    private com.k33ptoo.components.KButton deleteButton;
    private javax.swing.JComboBox<String> districtComboBox2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JComboBox<String> provinceComboBox;
    private javax.swing.JComboBox<String> provinceComboBox2;
    private com.k33ptoo.components.KButton searchButton;
    private com.k33ptoo.components.KButton updateButton;
    // End of variables declaration//GEN-END:variables

    private void reset() {

        EmpId.setText("");
        provinceComboBox.setSelectedIndex(0);
        DistricComboBox.setSelectedIndex(0);
        CityComboBox.setSelectedIndex(0);
        provinceComboBox2.setSelectedIndex(0);
        cityComboBox2.setSelectedIndex(0);
        districtComboBox2.setSelectedIndex(0);
        AddressLine_01.setText("");
        AddressLine_02.setText("");
        ManageEmployeeAddressTable.clearSelection();

    }
}
