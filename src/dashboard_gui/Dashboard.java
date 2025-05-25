package dashboard_gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import finance_department_gui.FinanceDepartmentDashboard;
import hr_department_gui.HRDepartmentDashboard;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import model.MySql;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import order_management_gui.kitchen_gui.KitchenManagementDashboard;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import stock_management_gui.StockManagementDashboard;

public class Dashboard extends javax.swing.JFrame {

    private String name;
    private JFrame currentWindow = null; // Keep track of the currently open window

    public Dashboard(String name) {

        initComponents();

        this.setExtendedState(Dashboard.MAXIMIZED_BOTH);

        configureEscKeyBinding(); // Esc control

        setupAltF4Protection();  // Alt + F4 control

        showBarChart();
        showPieChart();

        this.name = name;

        adminDetails();

        date();
        time();

        cardDataLoad();
        
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

    private void adminDetails() {

        if (name != null) {

            NameLabel.setText("Welcome, " + name + "!");

            addPosition();

        }

    }

    private void addPosition() {
        
        try {
            
            String query = "SELECT ep.position_name "
                    + "FROM employee_user eu "
                    + "JOIN employee_position ep "
                    + "ON eu.employee_position_employee_position_id = ep.employee_position_id "
                    + "WHERE eu.user_name = '" + name + "'";

            ResultSet rs = MySql.executeSearch(query);

            String position = null;

            if (rs.next()) {
                
                position = rs.getString("position_name");
                
                PositionLabel.setText("Position: " + position);
                
            } else {
                
                PositionLabel.setText("Position: UserPosition");
                
            }

        } catch (Exception e) {

            e.printStackTrace();

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

    ResultSet rs;

    private void showBarChart() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {

            String query = "SELECT DATE_FORMAT(od.orderd_time, '%M') AS month_name, "
                    + "SUM(i.total_amount) AS total_revenue "
                    + "FROM invoice i "
                    + "JOIN order_details od ON i.order_details_order_id = od.order_id "
                    + "GROUP BY MONTH(od.orderd_time), DATE_FORMAT(od.orderd_time, '%M') "
                    + "ORDER BY MONTH(od.orderd_time)";

            rs = MySql.executeSearch(query);

            while (rs.next()) {

                String month = rs.getString("month_name");
                double amount = rs.getDouble("total_revenue");
                dataset.setValue(amount, "Amount", month);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Monthly Revenue", // chart title
                "Month", // x-axis label
                "Amount", // y-axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                false, true, false
        );

        CategoryPlot categoryPlot = chart.getCategoryPlot();
        categoryPlot.setBackgroundPaint(Color.LIGHT_GRAY);

        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        renderer.setSeriesPaint(0, new Color(255, 209, 128));

        ChartPanel barChartPanel = new ChartPanel(chart);
        ChartPanel1.removeAll();
        ChartPanel1.add(barChartPanel, BorderLayout.CENTER);
        ChartPanel1.validate();

    }

    public void showPieChart() {

        try {

            // SQL query to get employee count per department
            String query = "SELECT d.department_name, COUNT(e.employee_id) AS employee_count "
                    + "FROM employee e "
                    + "JOIN department d ON e.department_department_id = d.department_id "
                    + "GROUP BY d.department_id, d.department_name";

            rs = MySql.executeSearch(query);

            // Create dataset
            DefaultPieDataset pieDataset = new DefaultPieDataset();

            while (rs.next()) {

                String deptName = rs.getString("department_name");
                int empCount = rs.getInt("employee_count");
                pieDataset.setValue(deptName, empCount);

            }

            // Create chart
            JFreeChart pieChart = ChartFactory.createPieChart("Employees per Department", pieDataset, false, true, false);

            // Customize appearance
            PiePlot piePlot = (PiePlot) pieChart.getPlot();
            piePlot.setBackgroundPaint(Color.WHITE);

            // Optionally set random colors for sections
            for (Object key : pieDataset.getKeys()) {

                piePlot.setSectionPaint((Comparable) key, new Color(
                        (int) (Math.random() * 255),
                        (int) (Math.random() * 255),
                        (int) (Math.random() * 255)
                ));

            }

            // Display chart in panel
            ChartPanel pieChartPanel = new ChartPanel(pieChart);
            ChartPanel2.removeAll();
            ChartPanel2.add(pieChartPanel, BorderLayout.CENTER);
            ChartPanel2.validate();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    
    private void cardDataLoad() {
        
        employeeCount();
        departmentCount();
        totalSales();
        daySales();
        monthSales();
        yearSales();
        
    }

    private void employeeCount() {

        try {

            rs = MySql.executeSearch(
                    "SELECT COUNT(*) AS emp_count "
                    + "FROM employee e "
                    + "JOIN employee_position ep ON e.employee_position_employee_position_id = ep.employee_position_id "
                    + "WHERE ep.position_name <> 'Super admin'"
            );

            if (rs.next()) {

                int count = rs.getInt("emp_count");

                employeeCount.setText("Employee: " + count);

            } else {

                departmentCount.setText("Employee: 100");  // default value

            }

            rs.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void departmentCount() {

        try {

            rs = MySql.executeSearch("SELECT COUNT(*) AS dept_count FROM department");

            if (rs.next()) {

                int count = rs.getInt("dept_count");

                departmentCount.setText("Department: " + count);

            } else {

                departmentCount.setText("Department: 5");  // default value

            }

            rs.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void totalSales() {

        try {

            String query = "SELECT SUM(total_amount) AS total_sales FROM invoice";

            rs = MySql.executeSearch(query);

            if (rs.next()) {

                double totalSalesAmount = rs.getDouble("total_sales");

                totalSales.setText("Total Sales: Rs. " + totalSalesAmount);

            } else {

                totalSales.setText("Total Sales: Rs. 0.00");  // default value

            }

            rs.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void daySales() {

        try {

            // Join and filter by today's date
            String query = "SELECT SUM(i.total_amount) AS total_sales "
                    + "FROM invoice i "
                    + "INNER JOIN order_details o ON i.order_details_order_id = o.order_id "
                    + "WHERE DATE(o.orderd_time) = CURDATE()";

            rs = MySql.executeSearch(query);

            if (rs.next()) {

                double totalSalesAmount = rs.getDouble("total_sales");

                daySales.setText("Day: Rs. " + totalSalesAmount);

            } else {

                daySales.setText("Day: Rs. 0.00");  // default value

            }

            rs.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    LocalDate today = LocalDate.now();
    int month = today.getMonthValue(); // 5
    int year = today.getYear();

    private void monthSales() {

        try {

            String query = "SELECT SUM(i.total_amount) AS total_sales "
                    + "FROM invoice i "
                    + "JOIN order_details o ON i.order_details_order_id = o.order_id "
                    + "WHERE MONTH(o.orderd_time) = " + month + " AND YEAR(o.orderd_time) = " + year;

            rs = MySql.executeSearch(query);

            if (rs.next()) {

                double totalSalesAmount = rs.getDouble("total_sales");

                monthSales.setText("Month: Rs. " + totalSalesAmount);

            } else {

                monthSales.setText("Month: Rs. 0.00");

            }

            rs.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void yearSales() {

        try {

            String query = "SELECT SUM(i.total_amount) AS total_sales "
                    + "FROM invoice i "
                    + "JOIN order_details o ON i.order_details_order_id = o.order_id "
                    + "WHERE YEAR(o.orderd_time) = " + year;

            rs = MySql.executeSearch(query);

            if (rs.next()) {

                double totalSalesAmount = rs.getDouble("total_sales");

                yearSales.setText("Year: Rs. " + totalSalesAmount);

            } else {

                yearSales.setText("Year: Rs. 0.00");

            }

            rs.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    
    
    
    private void openWindow(String windowType) {
        
        if (currentWindow != null) {
            
            currentWindow.dispose(); // Close the previous window if it's already open

        }
        
        switch (windowType) {

            case "HRDepartmentDashboard":
                currentWindow = new HRDepartmentDashboard("Admin");
                break;
            
            case "FinanceDepartmentDashboard":
                currentWindow = new FinanceDepartmentDashboard("Admin");
                break;
            
            case "StockManagementDashboard":
                currentWindow = new StockManagementDashboard("Admin");
                break;
            
            case "KitchenManagementDashboard":
                currentWindow = new KitchenManagementDashboard("Admin");
                break;
         
        }
        
        currentWindow.setVisible(true);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel11 = new javax.swing.JLabel();
        headerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        bodyPanel = new javax.swing.JPanel();
        sideBarBodyPanel = new javax.swing.JPanel();
        SideBarIconPanel = new javax.swing.JPanel();
        sideBarHideIconPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        ProfileSettingIconPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        LogOutButtonPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        SideBarButtonsPanel = new javax.swing.JPanel();
        HRButton = new com.k33ptoo.components.KButton();
        FinanceButton = new com.k33ptoo.components.KButton();
        StockButton = new com.k33ptoo.components.KButton();
        kitchenButton = new com.k33ptoo.components.KButton();
        PositionsAndEtcPanel = new javax.swing.JPanel();
        NameLabel = new javax.swing.JLabel();
        PositionLabel = new javax.swing.JLabel();
        DateLabel = new javax.swing.JLabel();
        TimeLabel = new javax.swing.JLabel();
        DetailShowingPanel = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        employeeCount = new com.k33ptoo.components.KButton();
        departmentCount = new com.k33ptoo.components.KButton();
        totalSales = new com.k33ptoo.components.KButton();
        jPanel11 = new javax.swing.JPanel();
        daySales = new com.k33ptoo.components.KButton();
        monthSales = new com.k33ptoo.components.KButton();
        yearSales = new com.k33ptoo.components.KButton();
        FooterPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        MainChartPanel = new javax.swing.JPanel();
        ChartPanel1 = new javax.swing.JPanel();
        barChartPanel = new javax.swing.JPanel();
        ChartPanel2 = new javax.swing.JPanel();
        pieChartPanel = new javax.swing.JPanel();

        jLabel11.setText("jLabel11");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(850, 800));
        setUndecorated(true);
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        headerPanel.setBackground(new java.awt.Color(153, 153, 153));
        headerPanel.setPreferredSize(new java.awt.Dimension(738, 60));

        jLabel1.setFont(new java.awt.Font("Audiowide", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Admin Dashboard");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(300, 300, 300)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
                .addGap(300, 300, 300))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(headerPanel, java.awt.BorderLayout.PAGE_START);

        bodyPanel.setPreferredSize(new java.awt.Dimension(782, 50));
        bodyPanel.setLayout(new java.awt.BorderLayout());

        sideBarBodyPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sideBarBodyPanel.setPreferredSize(new java.awt.Dimension(280, 620));
        sideBarBodyPanel.setLayout(new java.awt.BorderLayout());

        SideBarIconPanel.setBackground(new java.awt.Color(153, 153, 153));
        SideBarIconPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        SideBarIconPanel.setPreferredSize(new java.awt.Dimension(60, 620));
        SideBarIconPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sideBarHideIconPanel.setBackground(new java.awt.Color(153, 153, 153));
        sideBarHideIconPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sideBarHideIconPanel.setLayout(new java.awt.BorderLayout());

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/back-arrow.png"))); // NOI18N
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel8MousePressed(evt);
            }
        });
        sideBarHideIconPanel.add(jLabel8, java.awt.BorderLayout.CENTER);

        SideBarIconPanel.add(sideBarHideIconPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 50));

        ProfileSettingIconPanel.setBackground(new java.awt.Color(153, 153, 153));
        ProfileSettingIconPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ProfileSettingIconPanel.setPreferredSize(new java.awt.Dimension(32, 32));
        ProfileSettingIconPanel.setLayout(new java.awt.BorderLayout());

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/userprofile.png"))); // NOI18N
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ProfileSettingIconPanel.add(jLabel9, java.awt.BorderLayout.CENTER);

        SideBarIconPanel.add(ProfileSettingIconPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 60, 50));

        LogOutButtonPanel.setBackground(new java.awt.Color(153, 153, 153));
        LogOutButtonPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        LogOutButtonPanel.setLayout(new java.awt.BorderLayout());

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/logout.png"))); // NOI18N
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        LogOutButtonPanel.add(jLabel10, java.awt.BorderLayout.CENTER);

        SideBarIconPanel.add(LogOutButtonPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 60, 50));

        jPanel7.setBackground(new java.awt.Color(0, 102, 153));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        SideBarIconPanel.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 60, 10));

        jPanel8.setBackground(new java.awt.Color(0, 102, 153));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        SideBarIconPanel.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 60, 10));

        jPanel9.setBackground(new java.awt.Color(0, 102, 153));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        SideBarIconPanel.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 60, -1));

        sideBarBodyPanel.add(SideBarIconPanel, java.awt.BorderLayout.LINE_START);

        SideBarButtonsPanel.setLayout(new java.awt.GridLayout(13, 1, 6, 6));

        HRButton.setText("HR Department");
        HRButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        HRButton.setkBorderRadius(0);
        HRButton.setkEndColor(new java.awt.Color(51, 51, 51));
        HRButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        HRButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        HRButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        HRButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        HRButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        HRButton.setkStartColor(new java.awt.Color(153, 153, 153));
        HRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HRButtonActionPerformed(evt);
            }
        });
        SideBarButtonsPanel.add(HRButton);

        FinanceButton.setText("Finance Department");
        FinanceButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        FinanceButton.setkBorderRadius(0);
        FinanceButton.setkEndColor(new java.awt.Color(51, 51, 51));
        FinanceButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        FinanceButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        FinanceButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        FinanceButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        FinanceButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        FinanceButton.setkStartColor(new java.awt.Color(153, 153, 153));
        FinanceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinanceButtonActionPerformed(evt);
            }
        });
        SideBarButtonsPanel.add(FinanceButton);

        StockButton.setText("Stock Management");
        StockButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        StockButton.setkBorderRadius(0);
        StockButton.setkEndColor(new java.awt.Color(51, 51, 51));
        StockButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        StockButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        StockButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        StockButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        StockButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        StockButton.setkStartColor(new java.awt.Color(153, 153, 153));
        StockButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StockButtonActionPerformed(evt);
            }
        });
        SideBarButtonsPanel.add(StockButton);

        kitchenButton.setText("Kitchen Management");
        kitchenButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kitchenButton.setkBorderRadius(0);
        kitchenButton.setkEndColor(new java.awt.Color(51, 51, 51));
        kitchenButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        kitchenButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        kitchenButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        kitchenButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        kitchenButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        kitchenButton.setkStartColor(new java.awt.Color(153, 153, 153));
        kitchenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kitchenButtonActionPerformed(evt);
            }
        });
        SideBarButtonsPanel.add(kitchenButton);

        sideBarBodyPanel.add(SideBarButtonsPanel, java.awt.BorderLayout.CENTER);

        bodyPanel.add(sideBarBodyPanel, java.awt.BorderLayout.LINE_START);

        PositionsAndEtcPanel.setPreferredSize(new java.awt.Dimension(782, 50));

        NameLabel.setFont(new java.awt.Font("Bodoni MT", 3, 24)); // NOI18N
        NameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NameLabel.setText("Welcome, Admin!");

        PositionLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        PositionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PositionLabel.setText("Position: Administrator");

        DateLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        DateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DateLabel.setText("Date: yyyy-mm-dd");

        TimeLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        TimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TimeLabel.setText("Time: hh:mm:ss");

        javax.swing.GroupLayout PositionsAndEtcPanelLayout = new javax.swing.GroupLayout(PositionsAndEtcPanel);
        PositionsAndEtcPanel.setLayout(PositionsAndEtcPanelLayout);
        PositionsAndEtcPanelLayout.setHorizontalGroup(
            PositionsAndEtcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PositionsAndEtcPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106)
                .addComponent(PositionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(DateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(TimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        PositionsAndEtcPanelLayout.setVerticalGroup(
            PositionsAndEtcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PositionsAndEtcPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PositionsAndEtcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(PositionLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        bodyPanel.add(PositionsAndEtcPanel, java.awt.BorderLayout.PAGE_START);

        jPanel10.setLayout(new java.awt.GridLayout(1, 6, 5, 0));

        employeeCount.setText("Employee: 10");
        employeeCount.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        employeeCount.setkEndColor(new java.awt.Color(0, 204, 204));
        employeeCount.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        employeeCount.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        employeeCount.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        employeeCount.setkPressedColor(new java.awt.Color(0, 102, 153));
        employeeCount.setkSelectedColor(new java.awt.Color(0, 102, 153));
        employeeCount.setkStartColor(new java.awt.Color(0, 102, 153));
        jPanel10.add(employeeCount);

        departmentCount.setText("Department: 5");
        departmentCount.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        departmentCount.setkEndColor(new java.awt.Color(0, 204, 204));
        departmentCount.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        departmentCount.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        departmentCount.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        departmentCount.setkPressedColor(new java.awt.Color(0, 102, 153));
        departmentCount.setkSelectedColor(new java.awt.Color(0, 102, 153));
        departmentCount.setkStartColor(new java.awt.Color(0, 102, 153));
        jPanel10.add(departmentCount);

        totalSales.setText("Total Sales: Rs.1000.00");
        totalSales.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        totalSales.setkEndColor(new java.awt.Color(0, 204, 204));
        totalSales.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        totalSales.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        totalSales.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        totalSales.setkPressedColor(new java.awt.Color(0, 102, 153));
        totalSales.setkSelectedColor(new java.awt.Color(0, 102, 153));
        totalSales.setkStartColor(new java.awt.Color(0, 102, 153));
        jPanel10.add(totalSales);

        jPanel11.setLayout(new java.awt.GridLayout(1, 6, 5, 0));

        daySales.setText("Day: Rs.20000.00");
        daySales.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        daySales.setkEndColor(new java.awt.Color(0, 204, 204));
        daySales.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        daySales.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        daySales.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        daySales.setkPressedColor(new java.awt.Color(0, 102, 153));
        daySales.setkSelectedColor(new java.awt.Color(0, 102, 153));
        daySales.setkStartColor(new java.awt.Color(0, 102, 153));
        jPanel11.add(daySales);

        monthSales.setText("Month: Rs.100000.00");
        monthSales.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        monthSales.setkEndColor(new java.awt.Color(0, 204, 204));
        monthSales.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        monthSales.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        monthSales.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        monthSales.setkPressedColor(new java.awt.Color(0, 102, 153));
        monthSales.setkSelectedColor(new java.awt.Color(0, 102, 153));
        monthSales.setkStartColor(new java.awt.Color(0, 102, 153));
        jPanel11.add(monthSales);

        yearSales.setText("Year: Rs.10000000.00");
        yearSales.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        yearSales.setkEndColor(new java.awt.Color(0, 204, 204));
        yearSales.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        yearSales.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        yearSales.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        yearSales.setkPressedColor(new java.awt.Color(0, 102, 153));
        yearSales.setkSelectedColor(new java.awt.Color(0, 102, 153));
        yearSales.setkStartColor(new java.awt.Color(0, 102, 153));
        jPanel11.add(yearSales);

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
                .addGap(200, 200, 200)
                .addGroup(FooterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(200, 200, 200))
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

        MainChartPanel.setLayout(new java.awt.GridLayout(1, 2));

        ChartPanel1.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout barChartPanelLayout = new javax.swing.GroupLayout(barChartPanel);
        barChartPanel.setLayout(barChartPanelLayout);
        barChartPanelLayout.setHorizontalGroup(
            barChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 518, Short.MAX_VALUE)
        );
        barChartPanelLayout.setVerticalGroup(
            barChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        ChartPanel1.add(barChartPanel, java.awt.BorderLayout.CENTER);

        MainChartPanel.add(ChartPanel1);

        ChartPanel2.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout pieChartPanelLayout = new javax.swing.GroupLayout(pieChartPanel);
        pieChartPanel.setLayout(pieChartPanelLayout);
        pieChartPanelLayout.setHorizontalGroup(
            pieChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 518, Short.MAX_VALUE)
        );
        pieChartPanelLayout.setVerticalGroup(
            pieChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        ChartPanel2.add(pieChartPanel, java.awt.BorderLayout.CENTER);

        MainChartPanel.add(ChartPanel2);

        javax.swing.GroupLayout DetailShowingPanelLayout = new javax.swing.GroupLayout(DetailShowingPanel);
        DetailShowingPanel.setLayout(DetailShowingPanelLayout);
        DetailShowingPanelLayout.setHorizontalGroup(
            DetailShowingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(FooterPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1048, Short.MAX_VALUE)
            .addGroup(DetailShowingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MainChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(DetailShowingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 1048, Short.MAX_VALUE))
        );
        DetailShowingPanelLayout.setVerticalGroup(
            DetailShowingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DetailShowingPanelLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125)
                .addComponent(MainChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FooterPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(DetailShowingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(DetailShowingPanelLayout.createSequentialGroup()
                    .addGap(120, 120, 120)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(319, Short.MAX_VALUE)))
        );

        bodyPanel.add(DetailShowingPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(bodyPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked

        System.exit(0);

    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MousePressed

        if (hide == true) {

            hidemenu(sideBarBodyPanel, hide, jLabel8);
            SwingUtilities.updateComponentTreeUI(this);

            hide = false;

        } else {

            hidemenu(sideBarBodyPanel, hide, jLabel8);
            SwingUtilities.updateComponentTreeUI(this);

            hide = true;

        }
    }//GEN-LAST:event_jLabel8MousePressed

    private void HRButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HRButtonActionPerformed

        openWindow("HRDepartmentDashboard");

    }//GEN-LAST:event_HRButtonActionPerformed

    private void FinanceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FinanceButtonActionPerformed

        openWindow("FinanceDepartmentDashboard");

    }//GEN-LAST:event_FinanceButtonActionPerformed

    private void StockButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StockButtonActionPerformed

        openWindow("StockManagementDashboard");

    }//GEN-LAST:event_StockButtonActionPerformed

    private void kitchenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kitchenButtonActionPerformed

        openWindow("KitchenManagementDashboard");

    }//GEN-LAST:event_kitchenButtonActionPerformed

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged

        configureEscKeyBinding(); // Esc control

        setupAltF4Protection();  // Alt + F4 control

    }//GEN-LAST:event_formWindowStateChanged

    public static void main(String args[]) {

        FlatMacLightLaf.setup();

        java.awt.EventQueue.invokeLater(() -> {

            new Dashboard("Admin").setVisible(true);

        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ChartPanel1;
    private javax.swing.JPanel ChartPanel2;
    private javax.swing.JLabel DateLabel;
    private javax.swing.JPanel DetailShowingPanel;
    private com.k33ptoo.components.KButton FinanceButton;
    private javax.swing.JPanel FooterPanel;
    private com.k33ptoo.components.KButton HRButton;
    private javax.swing.JPanel LogOutButtonPanel;
    private javax.swing.JPanel MainChartPanel;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JLabel PositionLabel;
    private javax.swing.JPanel PositionsAndEtcPanel;
    private javax.swing.JPanel ProfileSettingIconPanel;
    private javax.swing.JPanel SideBarButtonsPanel;
    private javax.swing.JPanel SideBarIconPanel;
    private com.k33ptoo.components.KButton StockButton;
    private javax.swing.JLabel TimeLabel;
    private javax.swing.JPanel barChartPanel;
    private javax.swing.JPanel bodyPanel;
    private com.k33ptoo.components.KButton daySales;
    private com.k33ptoo.components.KButton departmentCount;
    private com.k33ptoo.components.KButton employeeCount;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private com.k33ptoo.components.KButton kitchenButton;
    private com.k33ptoo.components.KButton monthSales;
    private javax.swing.JPanel pieChartPanel;
    private javax.swing.JPanel sideBarBodyPanel;
    private javax.swing.JPanel sideBarHideIconPanel;
    private com.k33ptoo.components.KButton totalSales;
    private com.k33ptoo.components.KButton yearSales;
    // End of variables declaration//GEN-END:variables

    private void configureEscKeyBinding() {

        // ESC -> Minimize
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "minimizeWindow");

        getRootPane().getActionMap().put("minimizeWindow", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setState(Dashboard.ICONIFIED);

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
