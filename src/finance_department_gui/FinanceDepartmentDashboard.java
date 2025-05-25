package finance_department_gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
import model.MySql;
import java.sql.ResultSet;
import javax.swing.table.JTableHeader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.Timer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;

public class FinanceDepartmentDashboard extends javax.swing.JFrame {

    private String name;
    private JFrame currentWindow = null; // Keep track of the currently open window

    public FinanceDepartmentDashboard(String name) {

        initComponents();

        this.setExtendedState(FinanceDepartmentDashboard.MAXIMIZED_BOTH);

        configureEscKeyBinding(); // Esc control

        setupAltF4Protection();  // Alt + F4 control

        showLineChart();
        showHistogram();

        this.name = name;

        financeUserDetails();

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

    private void financeUserDetails() {

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

        Table.getTableHeader().setBackground(new Color(0, 139, 105));
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

            case "PayrollManagement":
                currentWindow = new PayrollManagement();
                break;

            case "CompanyPromotion":
                currentWindow = new CompanyPromotion();
                break;

            case "AddExpences":
                currentWindow = new AddExpences();
                break;

        }

        currentWindow.setVisible(true);

    }

    DefaultTableModel model;

    private void loadTableData() {

        try {

            String query = "SELECT employee_name, "
                    + "month, "
                    + "net_salary, "
                    + "(no_pay_deduction + other_deduction) AS total_deductions, "
                    + "payable_salary "
                    + "FROM employee_payrolls "
                    + "ORDER BY month DESC "
                    + "LIMIT 10";

            ResultSet rs = MySql.executeSearch(query);

            model = (DefaultTableModel) Table.getModel();
            model.setRowCount(0);

            while (rs.next()) {

                String emp = rs.getString("employee_name");
                String month = rs.getString("month");
                double net = rs.getDouble("net_salary");
                double ded = rs.getDouble("total_deductions");
                double payable = rs.getDouble("payable_salary");

                model.addRow(new Object[]{emp, month, net, ded, payable});

            }

            Table.setModel(model);

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    private void showLineChart() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {

            String query = "SELECT DATE_FORMAT(STR_TO_DATE(month, '%M'), '%m') AS month_num, "
                    + "month AS month_name, "
                    + "SUM(payable_salary) AS total_income, "
                    + "(SELECT IFNULL(SUM(price), 0) FROM company_expences "
                    + " WHERE MONTH(date) = DATE_FORMAT(STR_TO_DATE(ep.month, '%M'), '%m')) AS total_expense "
                    + "FROM employee_payrolls ep GROUP BY month ORDER BY month_num";

            ResultSet rs = MySql.executeSearch(query);

            while (rs.next()) {

                String month = rs.getString("month_name");
                double income = rs.getDouble("total_income");
                double expense = rs.getDouble("total_expense");

                dataset.setValue(income, "Income", month);
                dataset.setValue(expense, "Expense", month);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        // Create chart
        JFreeChart linechart = ChartFactory.createLineChart("Monthly Income vs Expense", "Month", "Amount (Rs.)", dataset, PlotOrientation.VERTICAL, true, true, false);

        // Customize plot
        CategoryPlot plot = linechart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);

        // Customize lines
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(0, 139, 105));   // Income Color
        renderer.setSeriesPaint(1, new Color(204, 0, 0));     // Expense Color

        // Display chart
        ChartPanel lineChartPanel = new ChartPanel(linechart);
        ChartPanel1.removeAll();
        ChartPanel1.add(lineChartPanel, BorderLayout.CENTER);
        ChartPanel1.validate();

    }
    
    private void showHistogram() {
        
        List<Double> expenseList = new ArrayList<>();

        try {
            
            String query = "SELECT price FROM company_expences WHERE price IS NOT NULL";
            
            ResultSet rs = MySql.executeSearch(query);

            while (rs.next()) {
                
                expenseList.add(rs.getDouble("price"));
                
            }

        } catch (Exception e) {
            
            e.printStackTrace();
            return;
            
        }

        if (expenseList.isEmpty()) {
            
            JOptionPane.showMessageDialog(null, "No expense data available to display.");
            return;
            
        }

        // Convert List<Double> to primitive double[] array
        double[] values = new double[expenseList.size()];
        
        for (int i = 0; i < expenseList.size(); i++) {
            
            values[i] = expenseList.get(i);
            
        }

        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("Expenses", values, 10); // bin count

        JFreeChart chart = ChartFactory.createHistogram("Daily Expense Distribution", "Expense Amount (Rs.)", "Frequency", dataset, PlotOrientation.VERTICAL, false, true, false);

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);

        ChartPanel histrogramPanel = new ChartPanel(chart);
        ChartPanel2.removeAll();
        ChartPanel2.add(histrogramPanel, BorderLayout.CENTER);
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
        payrollManagementButton = new com.k33ptoo.components.KButton();
        companyPromotionButton = new com.k33ptoo.components.KButton();
        addExpencesButton = new com.k33ptoo.components.KButton();
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
        lineChartPanel = new javax.swing.JPanel();
        ChartPanel2 = new javax.swing.JPanel();
        histrogramPanel = new javax.swing.JPanel();
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
        jLabel1.setText("Finance Dashboard");

        NameLabel.setFont(new java.awt.Font("Bodoni MT", 3, 24)); // NOI18N
        NameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NameLabel.setText("Welcome, Home!");

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
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(TimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        HeaderPanelLayout.setVerticalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(NameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(DateLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(TimeLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        SideBarButtonPanel.setLayout(new java.awt.GridLayout(13, 1, 6, 6));

        payrollManagementButton.setText("Payroll Management");
        payrollManagementButton.setAlignmentY(0.0F);
        payrollManagementButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        payrollManagementButton.setkBorderRadius(0);
        payrollManagementButton.setkEndColor(new java.awt.Color(51, 51, 51));
        payrollManagementButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        payrollManagementButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        payrollManagementButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        payrollManagementButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        payrollManagementButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        payrollManagementButton.setkStartColor(new java.awt.Color(153, 153, 153));
        payrollManagementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payrollManagementButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(payrollManagementButton);

        companyPromotionButton.setText("Company Promotion");
        companyPromotionButton.setAlignmentY(0.0F);
        companyPromotionButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        companyPromotionButton.setkBorderRadius(0);
        companyPromotionButton.setkEndColor(new java.awt.Color(51, 51, 51));
        companyPromotionButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        companyPromotionButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        companyPromotionButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        companyPromotionButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        companyPromotionButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        companyPromotionButton.setkStartColor(new java.awt.Color(153, 153, 153));
        companyPromotionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                companyPromotionButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(companyPromotionButton);

        addExpencesButton.setText("Add Expences");
        addExpencesButton.setAlignmentY(0.0F);
        addExpencesButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addExpencesButton.setkBorderRadius(0);
        addExpencesButton.setkEndColor(new java.awt.Color(51, 51, 51));
        addExpencesButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        addExpencesButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        addExpencesButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        addExpencesButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        addExpencesButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        addExpencesButton.setkStartColor(new java.awt.Color(153, 153, 153));
        addExpencesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addExpencesButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(addExpencesButton);

        sideBarMainPanel.add(SideBarButtonPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(sideBarMainPanel, java.awt.BorderLayout.LINE_START);

        MainDashboardPanel.setLayout(new java.awt.BorderLayout());

        BodyPanel.setLayout(new java.awt.GridLayout(2, 0));

        MainTablePanel.setLayout(new java.awt.BorderLayout());

        TableTitlePanel.setBackground(new java.awt.Color(200, 252, 234));
        TableTitlePanel.setPreferredSize(new java.awt.Dimension(886, 30));

        TableTitle.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        TableTitle.setForeground(new java.awt.Color(0, 90, 52));
        TableTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TableTitle.setText("Monthly Payroll");

        javax.swing.GroupLayout TableTitlePanelLayout = new javax.swing.GroupLayout(TableTitlePanel);
        TableTitlePanel.setLayout(TableTitlePanelLayout);
        TableTitlePanelLayout.setHorizontalGroup(
            TableTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TableTitlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TableTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE)
                .addContainerGap())
        );
        TableTitlePanelLayout.setVerticalGroup(
            TableTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TableTitlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TableTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
        );

        MainTablePanel.add(TableTitlePanel, java.awt.BorderLayout.PAGE_START);

        TableBodyPanel.setBackground(new java.awt.Color(200, 252, 234));

        Table.setBackground(new java.awt.Color(200, 252, 234));
        Table.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee", "Month", "Net Salary", "Deductions", "Payable"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table.setSelectionBackground(new java.awt.Color(5, 193, 156));
        Table.setSelectionForeground(new java.awt.Color(255, 255, 255));
        Table.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(Table);

        javax.swing.GroupLayout TableBodyPanelLayout = new javax.swing.GroupLayout(TableBodyPanel);
        TableBodyPanel.setLayout(TableBodyPanelLayout);
        TableBodyPanelLayout.setHorizontalGroup(
            TableBodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TableBodyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE)
                .addContainerGap())
        );
        TableBodyPanelLayout.setVerticalGroup(
            TableBodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TableBodyPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addContainerGap())
        );

        MainTablePanel.add(TableBodyPanel, java.awt.BorderLayout.CENTER);

        BodyPanel.add(MainTablePanel);

        MainChartPanel.setLayout(new java.awt.GridLayout(1, 2));

        ChartPanel1.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout lineChartPanelLayout = new javax.swing.GroupLayout(lineChartPanel);
        lineChartPanel.setLayout(lineChartPanelLayout);
        lineChartPanelLayout.setHorizontalGroup(
            lineChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 458, Short.MAX_VALUE)
        );
        lineChartPanelLayout.setVerticalGroup(
            lineChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 218, Short.MAX_VALUE)
        );

        ChartPanel1.add(lineChartPanel, java.awt.BorderLayout.CENTER);

        MainChartPanel.add(ChartPanel1);

        ChartPanel2.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout histrogramPanelLayout = new javax.swing.GroupLayout(histrogramPanel);
        histrogramPanel.setLayout(histrogramPanelLayout);
        histrogramPanelLayout.setHorizontalGroup(
            histrogramPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 458, Short.MAX_VALUE)
        );
        histrogramPanelLayout.setVerticalGroup(
            histrogramPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 218, Short.MAX_VALUE)
        );

        ChartPanel2.add(histrogramPanel, java.awt.BorderLayout.CENTER);

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
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
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

    private void payrollManagementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payrollManagementButtonActionPerformed

        openWindow("PayrollManagement");

    }//GEN-LAST:event_payrollManagementButtonActionPerformed

    private void companyPromotionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_companyPromotionButtonActionPerformed

        openWindow("CompanyPromotion");

    }//GEN-LAST:event_companyPromotionButtonActionPerformed

    private void addExpencesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addExpencesButtonActionPerformed

        openWindow("AddExpences");

    }//GEN-LAST:event_addExpencesButtonActionPerformed

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged

        configureEscKeyBinding(); // Esc control

        setupAltF4Protection();  // Alt + F4 control

    }//GEN-LAST:event_formWindowStateChanged

    public static void main(String args[]) {

        FlatMacLightLaf.setup();

        java.awt.EventQueue.invokeLater(() -> {

            new FinanceDepartmentDashboard(null).setVisible(true);

        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BodyPanel;
    private javax.swing.JPanel ChartPanel1;
    private javax.swing.JPanel ChartPanel2;
    private javax.swing.JLabel DateLabel;
    private javax.swing.JPanel FooterPanel;
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JPanel MainChartPanel;
    private javax.swing.JPanel MainDashboardPanel;
    private javax.swing.JPanel MainTablePanel;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JPanel SideBarButtonPanel;
    private javax.swing.JTable Table;
    private javax.swing.JPanel TableBodyPanel;
    private javax.swing.JLabel TableTitle;
    private javax.swing.JPanel TableTitlePanel;
    private javax.swing.JLabel TimeLabel;
    private com.k33ptoo.components.KButton addExpencesButton;
    private com.k33ptoo.components.KButton companyPromotionButton;
    private javax.swing.JPanel histrogramPanel;
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
    private javax.swing.JPanel lineChartPanel;
    private com.k33ptoo.components.KButton payrollManagementButton;
    private javax.swing.JPanel sideBarIconPanel;
    private com.k33ptoo.components.KGradientPanel sideBarMainPanel;
    // End of variables declaration//GEN-END:variables

    private void configureEscKeyBinding() {

        // ESC -> Minimize
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "minimizeWindow");

        getRootPane().getActionMap().put("minimizeWindow", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setState(FinanceDepartmentDashboard.ICONIFIED);

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
