package hr_department_gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.MySql;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class HRDepartmentDashboard extends javax.swing.JFrame {

    private String name;
    private JFrame currentWindow = null; // Keep track of the currently open window

    public HRDepartmentDashboard(String name) {

        initComponents();

        this.setExtendedState(HRDepartmentDashboard.MAXIMIZED_BOTH);

        configureEscKeyBinding(); // Esc control

        setupAltF4Protection();  // Alt + F4 control
        
        showPieChart();
        showLineChart();

        this.name = name;

        hrUserDetails();
        
        date();
        time();

        tableFormat();  // table format

        loadTableData();    // table data load

    }

    public void changeimage(JLabel image, String resourceImage) {

        ImageIcon imageIcon = new ImageIcon(getClass().getResource(resourceImage));
        image.setIcon(imageIcon);

    }

    boolean hide = true;

    public void hidemenu(JPanel showmenu, boolean dashboard, JLabel icButton) {

        if (dashboard == true) {

            showmenu.setPreferredSize(new Dimension(60, showmenu.getHeight()));
            changeimage(icButton, "/resources/menu.png");
        } else {

            showmenu.setPreferredSize(new Dimension(300, showmenu.getHeight()));
            changeimage(icButton, "/resources/back-arrow.png");
        }

    }

    private void hrUserDetails() {

        if (name != null) {

            NameLabel.setText("Welcome, " + name + "!");

        }

    }

    Date d;
    Timer t;
    SimpleDateFormat st;

    public void date() {

        d = new Date();
        st = new SimpleDateFormat("yyyy-MM-dd");
        String dd = st.format(d);
        DateLabel.setText("Date :   " + dd);

    }

    public void time() {

        t = new Timer(0, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Date dt = new Date();
                st = new SimpleDateFormat("hh:mm:ss");

                String tt = st.format(dt);
                TimeLabel.setText("Time :   " + tt);
            }

        });

        t.start();

    }

    private void tableFormat() {

        Table.getTableHeader().setBackground(new Color(59, 144, 146));
        Table.getTableHeader().setForeground(Color.WHITE);
        Table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 18));

        Table.setRowHeight(27); // normal row height

        // Set header height
        JTableHeader header = Table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40)); // header height

    }

    private void openWindow(String windowType) {

        if (currentWindow != null) {

            currentWindow.dispose(); // Close the previous window if it's already open

        }

        switch (windowType) {

            case "AddEmployee":
                currentWindow = new AddEmployee(null, null);
                break;

            case "AddDepartment":
                currentWindow = new AddDepartment();
                break;

            case "EmployeePosition":
                currentWindow = new EmployeePosition();
                break;

            case "AddEmployeeToDepartments":
                currentWindow = new AddEmployeeToDepartments();
                break;

            case "AddNewUser":
                currentWindow = new AddNewUser();
                break;

            case "ManageEmployee":
                currentWindow = new ManageEmployee();
                break;

            case "ManageEmployeeAddress":
                currentWindow = new ManageEmployeeAddress();
                break;

            case "LeaveManage":
                currentWindow = new LeaveManage();
                break;

            case "MarkAttendance":
                currentWindow = new MarkAttendance();
                break;

            case "PayrollIntegrations":
                currentWindow = new PayrollIntegrations();
                break;

            case "ManageAdvancedPayroll":
                currentWindow = new ManageAdvancedPayroll();
                break;

            case "ManageCustomer":
                currentWindow = new ManageCustomer();
                break;

            case "AddNewCompany":
                currentWindow = new AddNewCompany();
                break;

            case "AddNewSupplier":
                currentWindow = new AddNewSupplier();
                break;

        }

        currentWindow.setVisible(true);

    }

    ResultSet rs;
    DefaultTableModel model;

    private void loadTableData() {

        try {

            String query = "SELECT ea.employee_name, ea.date, ea.time, at.attendence_type_name AS status "
                    + "FROM employee_attendence ea "
                    + "INNER JOIN attendence_type at ON ea.attendence_type_type_id = at.type_id "
                    + "ORDER BY ea.date DESC, ea.time DESC LIMIT 10";

            rs = MySql.executeSearch(query);

            model = (DefaultTableModel) Table.getModel();
            model.setRowCount(0);

            while (rs.next()) {

                String name = rs.getString("employee_name");
                String date = rs.getString("date");
                String time = rs.getString("time");
                String status = rs.getString("status");

                model.addRow(new Object[]{name, date, time, status});

            }

            Table.setModel(model);

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    private void showPieChart() {
        
        DefaultPieDataset pieDataset = new DefaultPieDataset();

        try {
            
            String query = "SELECT at.attendence_type_name AS status, COUNT(*) AS count " +
                       "FROM employee_attendence ea " +
                       "JOIN attendence_type at ON ea.attendence_type_type_id = at.type_id " +
                       "WHERE MONTH(ea.date) = MONTH(CURDATE()) " +
                       "AND YEAR(ea.date) = YEAR(CURDATE()) " +
                       "GROUP BY at.attendence_type_name";

            rs = MySql.executeSearch(query);

            while (rs.next()) {
                
                String status = rs.getString("status");
                int count = rs.getInt("count");
                pieDataset.setValue(status, count);
                
            }

        } catch (Exception e) {
            
            e.printStackTrace();
            
        }

        JFreeChart piechart = ChartFactory.createPieChart("Employee Attendance Summary", pieDataset, true, true, false);

        PiePlot piePlot = (PiePlot) piechart.getPlot();
        piePlot.setBackgroundPaint(Color.white);

        pieDataset.getKeys().forEach(key -> {
            
            if (key.toString().equalsIgnoreCase("Present")) {
                
                piePlot.setSectionPaint((Comparable) key, new Color(102, 255, 102)); 
                
            } else if (key.toString().equalsIgnoreCase("Absent")) {
                
                piePlot.setSectionPaint((Comparable) key, new Color(255, 102, 102)); 
                
            } else {
                
                piePlot.setSectionPaint((Comparable) key, new Color(0, 204, 204));
                
            }
            
        });

        ChartPanel pieChartPanel = new ChartPanel(piechart);
        ChartPanel1.removeAll();
        ChartPanel1.add(pieChartPanel, BorderLayout.CENTER);
        ChartPanel1.validate();
        
    }

    private void showLineChart() {
        
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    try {
        
        String query = "SELECT DATE(date) AS day, COUNT(*) AS present_count " +
                       "FROM employee_attendence " +
                       "WHERE attendence_type_type_id = 1 " +
                       "AND MONTH(date) = MONTH(CURDATE()) " +
                       "AND YEAR(date) = YEAR(CURDATE()) " +
                       "GROUP BY DATE(date) " +
                       "ORDER BY DATE(date)";

        rs = MySql.executeSearch(query);

        while (rs.next()) {
            
            String day = rs.getString("day"); 
            int count = rs.getInt("present_count");

            dataset.setValue(count, "Present", day);
            
        }

    } catch (Exception e) {
        
        e.printStackTrace();
        
    }

    JFreeChart linechart = ChartFactory.createLineChart("Daily Attendance Trend (Current Month)", "Date", "Present Count", dataset, PlotOrientation.VERTICAL, true, true, false);

    CategoryPlot plot = linechart.getCategoryPlot();
    plot.setBackgroundPaint(Color.white);

    LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
    renderer.setSeriesPaint(0, new Color(0, 102, 204));

    ChartPanel lineChartPanel = new ChartPanel(linechart);
    ChartPanel2.removeAll();
    ChartPanel2.add(lineChartPanel, BorderLayout.CENTER);
    ChartPanel2.validate();
    
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        HeaderPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        NameLabel = new javax.swing.JLabel();
        DateLabel = new javax.swing.JLabel();
        TimeLabel = new javax.swing.JLabel();
        sideBarMainPanel = new com.k33ptoo.components.KGradientPanel();
        sideBarIconPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        SideBarButtonPanel = new javax.swing.JPanel();
        addEmployeeButton = new com.k33ptoo.components.KButton();
        addDepartmentButton = new com.k33ptoo.components.KButton();
        productPromotionButton = new com.k33ptoo.components.KButton();
        addEmployeeToDepartmentButton = new com.k33ptoo.components.KButton();
        addNewUserButton = new com.k33ptoo.components.KButton();
        manageEmployeeButton = new com.k33ptoo.components.KButton();
        manageEmployeeAddressButton = new com.k33ptoo.components.KButton();
        leaveManageButton = new com.k33ptoo.components.KButton();
        MarkAttendenceButton = new com.k33ptoo.components.KButton();
        PayrollIntegrationButton = new com.k33ptoo.components.KButton();
        ManageAdvancedPayrollButton = new com.k33ptoo.components.KButton();
        ManageCustomerButton = new com.k33ptoo.components.KButton();
        AddNewCompanyButton = new com.k33ptoo.components.KButton();
        AddNewSupplierButton = new com.k33ptoo.components.KButton();
        MainDashboardPanel = new javax.swing.JPanel();
        BodyPanel = new javax.swing.JPanel();
        MainTablePanel = new javax.swing.JPanel();
        TableTitlePanel = new javax.swing.JPanel();
        TableTitle = new javax.swing.JLabel();
        TableBodyPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        MainChartPanel = new javax.swing.JPanel();
        ChartPanel1 = new javax.swing.JPanel();
        pieChartPanel = new javax.swing.JPanel();
        ChartPanel2 = new javax.swing.JPanel();
        lineChartPanel = new javax.swing.JPanel();
        FooterPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        HeaderPanel.setBackground(new java.awt.Color(153, 153, 153));
        HeaderPanel.setPreferredSize(new java.awt.Dimension(638, 50));

        jLabel1.setFont(new java.awt.Font("Audiowide", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("HR Dashboard");

        NameLabel.setFont(new java.awt.Font("Bodoni MT", 3, 24)); // NOI18N
        NameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NameLabel.setText("Welcome, Name!");

        DateLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        DateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DateLabel.setText("Date: yyyy-mm-dd");

        TimeLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        TimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TimeLabel.setText("Time: hh:mm:ss");

        javax.swing.GroupLayout HeaderPanelLayout = new javax.swing.GroupLayout(HeaderPanel);
        HeaderPanel.setLayout(HeaderPanelLayout);
        HeaderPanelLayout.setHorizontalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(NameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(TimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        HeaderPanelLayout.setVerticalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(NameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(HeaderPanel, java.awt.BorderLayout.PAGE_START);

        sideBarMainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sideBarMainPanel.setkEndColor(new java.awt.Color(0, 0, 0));
        sideBarMainPanel.setkStartColor(new java.awt.Color(255, 255, 255));
        sideBarMainPanel.setPreferredSize(new java.awt.Dimension(300, 551));
        sideBarMainPanel.setLayout(new java.awt.BorderLayout());

        sideBarIconPanel.setBackground(new java.awt.Color(153, 153, 153));
        sideBarIconPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sideBarIconPanel.setPreferredSize(new java.awt.Dimension(60, 549));
        sideBarIconPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/back-arrow.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel4MousePressed(evt);
            }
        });
        jPanel1.add(jLabel4, java.awt.BorderLayout.CENTER);

        sideBarIconPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 60));

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        sideBarIconPanel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 60, 10));

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/logout.png"))); // NOI18N
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel5, java.awt.BorderLayout.CENTER);

        sideBarIconPanel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 60, 60));

        jPanel4.setBackground(new java.awt.Color(0, 51, 102));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        sideBarIconPanel.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 60, -1));

        sideBarMainPanel.add(sideBarIconPanel, java.awt.BorderLayout.LINE_START);

        SideBarButtonPanel.setPreferredSize(new java.awt.Dimension(240, 549));
        SideBarButtonPanel.setLayout(new java.awt.GridLayout(17, 1, 6, 6));

        addEmployeeButton.setText("Add Employee");
        addEmployeeButton.setAlignmentY(0.0F);
        addEmployeeButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addEmployeeButton.setkBorderRadius(0);
        addEmployeeButton.setkEndColor(new java.awt.Color(51, 51, 51));
        addEmployeeButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        addEmployeeButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        addEmployeeButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        addEmployeeButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        addEmployeeButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        addEmployeeButton.setkStartColor(new java.awt.Color(153, 153, 153));
        addEmployeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmployeeButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(addEmployeeButton);

        addDepartmentButton.setText("Add Department");
        addDepartmentButton.setAlignmentY(0.0F);
        addDepartmentButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addDepartmentButton.setkBorderRadius(0);
        addDepartmentButton.setkEndColor(new java.awt.Color(51, 51, 51));
        addDepartmentButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        addDepartmentButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        addDepartmentButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        addDepartmentButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        addDepartmentButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        addDepartmentButton.setkStartColor(new java.awt.Color(153, 153, 153));
        addDepartmentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDepartmentButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(addDepartmentButton);

        productPromotionButton.setText("Employee Position");
        productPromotionButton.setAlignmentY(0.0F);
        productPromotionButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        productPromotionButton.setkBorderRadius(0);
        productPromotionButton.setkEndColor(new java.awt.Color(51, 51, 51));
        productPromotionButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        productPromotionButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        productPromotionButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        productPromotionButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        productPromotionButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        productPromotionButton.setkStartColor(new java.awt.Color(153, 153, 153));
        productPromotionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productPromotionButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(productPromotionButton);

        addEmployeeToDepartmentButton.setText("Add Employee to Department");
        addEmployeeToDepartmentButton.setAlignmentY(0.0F);
        addEmployeeToDepartmentButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addEmployeeToDepartmentButton.setkBorderRadius(0);
        addEmployeeToDepartmentButton.setkEndColor(new java.awt.Color(51, 51, 51));
        addEmployeeToDepartmentButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        addEmployeeToDepartmentButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        addEmployeeToDepartmentButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        addEmployeeToDepartmentButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        addEmployeeToDepartmentButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        addEmployeeToDepartmentButton.setkStartColor(new java.awt.Color(153, 153, 153));
        addEmployeeToDepartmentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmployeeToDepartmentButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(addEmployeeToDepartmentButton);

        addNewUserButton.setText("Add New User");
        addNewUserButton.setAlignmentY(0.0F);
        addNewUserButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addNewUserButton.setkBorderRadius(0);
        addNewUserButton.setkEndColor(new java.awt.Color(51, 51, 51));
        addNewUserButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        addNewUserButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        addNewUserButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        addNewUserButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        addNewUserButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        addNewUserButton.setkStartColor(new java.awt.Color(153, 153, 153));
        addNewUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewUserButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(addNewUserButton);

        manageEmployeeButton.setText("Manage Employee");
        manageEmployeeButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        manageEmployeeButton.setkBorderRadius(0);
        manageEmployeeButton.setkEndColor(new java.awt.Color(51, 51, 51));
        manageEmployeeButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        manageEmployeeButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        manageEmployeeButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        manageEmployeeButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        manageEmployeeButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        manageEmployeeButton.setkStartColor(new java.awt.Color(153, 153, 153));
        manageEmployeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageEmployeeButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(manageEmployeeButton);

        manageEmployeeAddressButton.setText("Manage Employee Address");
        manageEmployeeAddressButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        manageEmployeeAddressButton.setkBorderRadius(0);
        manageEmployeeAddressButton.setkEndColor(new java.awt.Color(51, 51, 51));
        manageEmployeeAddressButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        manageEmployeeAddressButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        manageEmployeeAddressButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        manageEmployeeAddressButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        manageEmployeeAddressButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        manageEmployeeAddressButton.setkStartColor(new java.awt.Color(153, 153, 153));
        manageEmployeeAddressButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageEmployeeAddressButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(manageEmployeeAddressButton);

        leaveManageButton.setText("Leave Manage");
        leaveManageButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        leaveManageButton.setkBorderRadius(0);
        leaveManageButton.setkEndColor(new java.awt.Color(51, 51, 51));
        leaveManageButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        leaveManageButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        leaveManageButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        leaveManageButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        leaveManageButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        leaveManageButton.setkStartColor(new java.awt.Color(153, 153, 153));
        leaveManageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaveManageButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(leaveManageButton);

        MarkAttendenceButton.setText("Mark Attendance");
        MarkAttendenceButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        MarkAttendenceButton.setkBorderRadius(0);
        MarkAttendenceButton.setkEndColor(new java.awt.Color(51, 51, 51));
        MarkAttendenceButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        MarkAttendenceButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        MarkAttendenceButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        MarkAttendenceButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        MarkAttendenceButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        MarkAttendenceButton.setkStartColor(new java.awt.Color(153, 153, 153));
        MarkAttendenceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MarkAttendenceButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(MarkAttendenceButton);

        PayrollIntegrationButton.setText("Payroll Integration");
        PayrollIntegrationButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        PayrollIntegrationButton.setkBorderRadius(0);
        PayrollIntegrationButton.setkEndColor(new java.awt.Color(51, 51, 51));
        PayrollIntegrationButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        PayrollIntegrationButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        PayrollIntegrationButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        PayrollIntegrationButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        PayrollIntegrationButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        PayrollIntegrationButton.setkStartColor(new java.awt.Color(153, 153, 153));
        PayrollIntegrationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PayrollIntegrationButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(PayrollIntegrationButton);

        ManageAdvancedPayrollButton.setText("Manage Advanced Payroll");
        ManageAdvancedPayrollButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ManageAdvancedPayrollButton.setkBorderRadius(0);
        ManageAdvancedPayrollButton.setkEndColor(new java.awt.Color(51, 51, 51));
        ManageAdvancedPayrollButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        ManageAdvancedPayrollButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        ManageAdvancedPayrollButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        ManageAdvancedPayrollButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        ManageAdvancedPayrollButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        ManageAdvancedPayrollButton.setkStartColor(new java.awt.Color(153, 153, 153));
        ManageAdvancedPayrollButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManageAdvancedPayrollButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(ManageAdvancedPayrollButton);

        ManageCustomerButton.setText("Manage Customer");
        ManageCustomerButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ManageCustomerButton.setkBorderRadius(0);
        ManageCustomerButton.setkEndColor(new java.awt.Color(51, 51, 51));
        ManageCustomerButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        ManageCustomerButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        ManageCustomerButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        ManageCustomerButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        ManageCustomerButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        ManageCustomerButton.setkStartColor(new java.awt.Color(153, 153, 153));
        ManageCustomerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManageCustomerButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(ManageCustomerButton);

        AddNewCompanyButton.setText("Add New Company");
        AddNewCompanyButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AddNewCompanyButton.setkBorderRadius(0);
        AddNewCompanyButton.setkEndColor(new java.awt.Color(51, 51, 51));
        AddNewCompanyButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        AddNewCompanyButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        AddNewCompanyButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        AddNewCompanyButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        AddNewCompanyButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        AddNewCompanyButton.setkStartColor(new java.awt.Color(153, 153, 153));
        AddNewCompanyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddNewCompanyButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(AddNewCompanyButton);

        AddNewSupplierButton.setText("Add New Supplier");
        AddNewSupplierButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AddNewSupplierButton.setkBorderRadius(0);
        AddNewSupplierButton.setkEndColor(new java.awt.Color(51, 51, 51));
        AddNewSupplierButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        AddNewSupplierButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        AddNewSupplierButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        AddNewSupplierButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        AddNewSupplierButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        AddNewSupplierButton.setkStartColor(new java.awt.Color(153, 153, 153));
        AddNewSupplierButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddNewSupplierButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(AddNewSupplierButton);

        sideBarMainPanel.add(SideBarButtonPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(sideBarMainPanel, java.awt.BorderLayout.LINE_START);

        MainDashboardPanel.setLayout(new java.awt.BorderLayout());

        BodyPanel.setLayout(new java.awt.GridLayout(2, 0));

        MainTablePanel.setLayout(new java.awt.BorderLayout());

        TableTitlePanel.setBackground(new java.awt.Color(227, 244, 244));
        TableTitlePanel.setPreferredSize(new java.awt.Dimension(886, 30));

        TableTitle.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        TableTitle.setForeground(new java.awt.Color(75, 109, 98));
        TableTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TableTitle.setText("Recent Attendance");

        javax.swing.GroupLayout TableTitlePanelLayout = new javax.swing.GroupLayout(TableTitlePanel);
        TableTitlePanel.setLayout(TableTitlePanelLayout);
        TableTitlePanelLayout.setHorizontalGroup(
            TableTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TableTitlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TableTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
                .addContainerGap())
        );
        TableTitlePanelLayout.setVerticalGroup(
            TableTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TableTitlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TableTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
        );

        MainTablePanel.add(TableTitlePanel, java.awt.BorderLayout.PAGE_START);

        TableBodyPanel.setBackground(new java.awt.Color(227, 244, 244));

        Table.setBackground(new java.awt.Color(227, 244, 244));
        Table.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee Name", "Date", "Time", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table.setSelectionBackground(new java.awt.Color(153, 153, 153));
        Table.setSelectionForeground(new java.awt.Color(255, 255, 255));
        Table.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(Table);

        javax.swing.GroupLayout TableBodyPanelLayout = new javax.swing.GroupLayout(TableBodyPanel);
        TableBodyPanel.setLayout(TableBodyPanelLayout);
        TableBodyPanelLayout.setHorizontalGroup(
            TableBodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TableBodyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
                .addContainerGap())
        );
        TableBodyPanelLayout.setVerticalGroup(
            TableBodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TableBodyPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addContainerGap())
        );

        MainTablePanel.add(TableBodyPanel, java.awt.BorderLayout.CENTER);

        BodyPanel.add(MainTablePanel);

        MainChartPanel.setLayout(new java.awt.GridLayout(1, 2));

        ChartPanel1.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout pieChartPanelLayout = new javax.swing.GroupLayout(pieChartPanel);
        pieChartPanel.setLayout(pieChartPanelLayout);
        pieChartPanelLayout.setHorizontalGroup(
            pieChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 354, Short.MAX_VALUE)
        );
        pieChartPanelLayout.setVerticalGroup(
            pieChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 228, Short.MAX_VALUE)
        );

        ChartPanel1.add(pieChartPanel, java.awt.BorderLayout.CENTER);

        MainChartPanel.add(ChartPanel1);

        ChartPanel2.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout lineChartPanelLayout = new javax.swing.GroupLayout(lineChartPanel);
        lineChartPanel.setLayout(lineChartPanelLayout);
        lineChartPanelLayout.setHorizontalGroup(
            lineChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 354, Short.MAX_VALUE)
        );
        lineChartPanelLayout.setVerticalGroup(
            lineChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 228, Short.MAX_VALUE)
        );

        ChartPanel2.add(lineChartPanel, java.awt.BorderLayout.CENTER);

        MainChartPanel.add(ChartPanel2);

        BodyPanel.add(MainChartPanel);

        MainDashboardPanel.add(BodyPanel, java.awt.BorderLayout.CENTER);

        FooterPanel.setBackground(new java.awt.Color(51, 51, 51));
        FooterPanel.setPreferredSize(new java.awt.Dimension(747, 60));

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("All Rights Reserved | NeoLife Software Solutions");

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Designed & Developed By NeoLife Software Solutions");

        javax.swing.GroupLayout FooterPanelLayout = new javax.swing.GroupLayout(FooterPanel);
        FooterPanel.setLayout(FooterPanelLayout);
        FooterPanelLayout.setHorizontalGroup(
            FooterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FooterPanelLayout.createSequentialGroup()
                .addGap(211, 211, 211)
                .addGroup(FooterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(211, 211, 211))
        );
        FooterPanelLayout.setVerticalGroup(
            FooterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FooterPanelLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        MainDashboardPanel.add(FooterPanel, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(MainDashboardPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked

        dispose();

    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MousePressed

        if (hide == true) {

            hidemenu(sideBarMainPanel, hide, jLabel4);
            SwingUtilities.updateComponentTreeUI(this);

            hide = false;

        } else {

            hidemenu(sideBarMainPanel, hide, jLabel4);
            SwingUtilities.updateComponentTreeUI(this);

            hide = true;

        }
    }//GEN-LAST:event_jLabel4MousePressed

    private void addEmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEmployeeButtonActionPerformed

        openWindow("AddEmployee");

    }//GEN-LAST:event_addEmployeeButtonActionPerformed

    private void addDepartmentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDepartmentButtonActionPerformed

        openWindow("AddDepartment");

    }//GEN-LAST:event_addDepartmentButtonActionPerformed

    private void productPromotionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productPromotionButtonActionPerformed

        openWindow("EmployeePosition");

    }//GEN-LAST:event_productPromotionButtonActionPerformed

    private void addEmployeeToDepartmentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEmployeeToDepartmentButtonActionPerformed

        openWindow("AddEmployeeToDepartments");

    }//GEN-LAST:event_addEmployeeToDepartmentButtonActionPerformed

    private void addNewUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewUserButtonActionPerformed

        openWindow("AddNewUser");

    }//GEN-LAST:event_addNewUserButtonActionPerformed

    private void manageEmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageEmployeeButtonActionPerformed

        openWindow("ManageEmployee");

    }//GEN-LAST:event_manageEmployeeButtonActionPerformed

    private void manageEmployeeAddressButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageEmployeeAddressButtonActionPerformed

        openWindow("ManageEmployeeAddress");

    }//GEN-LAST:event_manageEmployeeAddressButtonActionPerformed

    private void leaveManageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leaveManageButtonActionPerformed

        openWindow("LeaveManage");

    }//GEN-LAST:event_leaveManageButtonActionPerformed

    private void MarkAttendenceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MarkAttendenceButtonActionPerformed

        openWindow("MarkAttendance");

    }//GEN-LAST:event_MarkAttendenceButtonActionPerformed

    private void PayrollIntegrationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PayrollIntegrationButtonActionPerformed

        openWindow("PayrollIntegrations");

    }//GEN-LAST:event_PayrollIntegrationButtonActionPerformed

    private void ManageAdvancedPayrollButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManageAdvancedPayrollButtonActionPerformed

        openWindow("ManageAdvancedPayroll");

    }//GEN-LAST:event_ManageAdvancedPayrollButtonActionPerformed

    private void ManageCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManageCustomerButtonActionPerformed

        openWindow("ManageCustomer");

    }//GEN-LAST:event_ManageCustomerButtonActionPerformed

    private void AddNewCompanyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddNewCompanyButtonActionPerformed

        openWindow("AddNewCompany");

    }//GEN-LAST:event_AddNewCompanyButtonActionPerformed

    private void AddNewSupplierButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddNewSupplierButtonActionPerformed

        openWindow("AddNewSupplier");

    }//GEN-LAST:event_AddNewSupplierButtonActionPerformed

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged

        configureEscKeyBinding(); // Esc control

        setupAltF4Protection();  // Alt + F4 control

    }//GEN-LAST:event_formWindowStateChanged

    public static void main(String args[]) {

        FlatMacLightLaf.setup();

        java.awt.EventQueue.invokeLater(() -> {

            new HRDepartmentDashboard(null).setVisible(true);

        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.k33ptoo.components.KButton AddNewCompanyButton;
    private com.k33ptoo.components.KButton AddNewSupplierButton;
    private javax.swing.JPanel BodyPanel;
    private javax.swing.JPanel ChartPanel1;
    private javax.swing.JPanel ChartPanel2;
    private javax.swing.JLabel DateLabel;
    private javax.swing.JPanel FooterPanel;
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JPanel MainChartPanel;
    private javax.swing.JPanel MainDashboardPanel;
    private javax.swing.JPanel MainTablePanel;
    private com.k33ptoo.components.KButton ManageAdvancedPayrollButton;
    private com.k33ptoo.components.KButton ManageCustomerButton;
    private com.k33ptoo.components.KButton MarkAttendenceButton;
    private javax.swing.JLabel NameLabel;
    private com.k33ptoo.components.KButton PayrollIntegrationButton;
    private javax.swing.JPanel SideBarButtonPanel;
    private javax.swing.JTable Table;
    private javax.swing.JPanel TableBodyPanel;
    private javax.swing.JLabel TableTitle;
    private javax.swing.JPanel TableTitlePanel;
    private javax.swing.JLabel TimeLabel;
    private com.k33ptoo.components.KButton addDepartmentButton;
    private com.k33ptoo.components.KButton addEmployeeButton;
    private com.k33ptoo.components.KButton addEmployeeToDepartmentButton;
    private com.k33ptoo.components.KButton addNewUserButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private com.k33ptoo.components.KButton leaveManageButton;
    private javax.swing.JPanel lineChartPanel;
    private com.k33ptoo.components.KButton manageEmployeeAddressButton;
    private com.k33ptoo.components.KButton manageEmployeeButton;
    private javax.swing.JPanel pieChartPanel;
    private com.k33ptoo.components.KButton productPromotionButton;
    private javax.swing.JPanel sideBarIconPanel;
    private com.k33ptoo.components.KGradientPanel sideBarMainPanel;
    // End of variables declaration//GEN-END:variables

    private void configureEscKeyBinding() {

        // ESC -> Minimize
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "minimizeWindow");

        getRootPane().getActionMap().put("minimizeWindow", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setState(HRDepartmentDashboard.ICONIFIED);

            }

        });

    }

    private void setupAltF4Protection() {

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {

            if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F4 && e.isAltDown()) {

                int confirm = JOptionPane.showConfirmDialog(null, "Do you really want to close the app?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }

                return true;

            }

            return false;

        });

    }

}
