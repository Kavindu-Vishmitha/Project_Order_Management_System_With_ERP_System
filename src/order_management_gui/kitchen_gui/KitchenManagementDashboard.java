package order_management_gui.kitchen_gui;

import finance_department_gui.PayrollManagement;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.mysql.cj.x.protobuf.MysqlxCrud.Order;
import java.awt.BorderLayout;
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
import javax.swing.table.JTableHeader;
import model.MySql;
import java.sql.ResultSet;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.data.category.DefaultCategoryDataset;

public class KitchenManagementDashboard extends javax.swing.JFrame {

    private String name;
    private JFrame currentWindow = null; // Keep track of the currently open window

    public KitchenManagementDashboard(String name) {

        initComponents();

        this.setExtendedState(KitchenManagementDashboard.MAXIMIZED_BOTH);

        configureEscKeyBinding(); // Esc control

        setupAltF4Protection();  // Alt + F4 control

        showPieChart();
        showRadarChart();

        this.name = name;

        kitchenUserDetails();

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

    private void kitchenUserDetails() {

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

        Table.getTableHeader().setBackground(new Color(24, 24, 24));
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

            case "KitchenDashboard":
                currentWindow = new KitchenDashboard();
                break;

        }

        currentWindow.setVisible(true);

    }

    DefaultTableModel model;

    private void loadTableData() {

        try {

            String query = "SELECT od.order_id, od.orderd_time, cs.cooking_status_name "
                    + "FROM order_details od "
                    + "INNER JOIN cooking_status cs ON od.cooking_status_cooking_status_id = cs.cooking_status_id "
                    + "WHERE cs.cooking_status_name != 'Cooked' AND DATE(od.orderd_time) = CURDATE()"
                    + "ORDER BY od.orderd_time ASC "
                    + "LIMIT 10";

            ResultSet rs = MySql.executeSearch(query);

            model = (DefaultTableModel) Table.getModel();
            model.setRowCount(0);

            while (rs.next()) {

                String orderId = rs.getString("order_id");
                String time = rs.getString("orderd_time");
                String status = rs.getString("cooking_status_name");

                model.addRow(new Object[]{orderId, time, status});

            }

            Table.setModel(model);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void showPieChart() {

        try {

            DefaultPieDataset pieDataset = new DefaultPieDataset();

            String query = "SELECT cs.cooking_status_name, COUNT(*) AS total_orders "
                    + "FROM order_details od "
                    + "JOIN cooking_status cs ON od.cooking_status_cooking_status_id = cs.cooking_status_id "
                    + "GROUP BY cs.cooking_status_name";

            ResultSet rs = MySql.executeSearch(query);

            while (rs.next()) {

                String cSName = rs.getString("cooking_status_name");
                int totalOrders = rs.getInt("total_orders");

                pieDataset.setValue(cSName, totalOrders);

            }

            JFreeChart piechart = ChartFactory.createPieChart("Order Status Distribution", pieDataset, true, true, false);

            PiePlot piePlot = (PiePlot) piechart.getPlot();
            piePlot.setBackgroundPaint(Color.white);

            pieDataset.getKeys().forEach(key -> {

                if (key.toString().equalsIgnoreCase("Prepared")) {

                    piePlot.setSectionPaint((Comparable) key, new Color(0, 0, 0));

                } else if (key.toString().equalsIgnoreCase("Not Ready")) {

                    piePlot.setSectionPaint((Comparable) key, new Color(17, 104, 112));

                } else if (key.toString().equalsIgnoreCase("Cooked")) {

                    piePlot.setSectionPaint((Comparable) key, new Color(0, 201, 200));

                } else {

                    piePlot.setSectionPaint((Comparable) key, new Color(0, 204, 204));

                }

            });

            ChartPanel pieChartPanel = new ChartPanel(piechart);
            ChartPanel1.removeAll();
            ChartPanel1.add(pieChartPanel, BorderLayout.CENTER);
            ChartPanel1.validate();

        } catch (Exception ex) {

            Logger.getLogger(KitchenManagementDashboard.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private void showRadarChart() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {

            String query = "SELECT c.category_name, SUM(g.qty) AS total_qty "
                    + "FROM grn g "
                    + "JOIN product p ON g.grn_id = p.grn_grn_id "
                    + "JOIN category c ON p.category_category_id = c.category_id "
                    + "GROUP BY c.category_id "
                    + "ORDER BY c.category_name";

            ResultSet rs = MySql.executeSearch(query);

            while (rs.next()) {

                String category = rs.getString("category_name");
                int qty = rs.getInt("total_qty");

                dataset.addValue(qty, "Stock", category);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        SpiderWebPlot plot = new SpiderWebPlot(dataset);
        plot.setStartAngle(54); // optional, rotates the plot
        plot.setBackgroundPaint(Color.LIGHT_GRAY);
        plot.setSeriesPaint(0, new Color(0, 204, 102)); // green

        JFreeChart chart = new JFreeChart("Stock by Category", new Font("Arial", Font.BOLD, 16), plot, false);

        ChartPanel radarChartPanel = new ChartPanel(chart);

        ChartPanel2.removeAll(); // JPanel you've added to hold this chart
        ChartPanel2.setLayout(new BorderLayout());
        ChartPanel2.add(radarChartPanel, BorderLayout.CENTER);
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
        kitchenDashboardButton = new com.k33ptoo.components.KButton();
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
        barChartPanel = new javax.swing.JPanel();
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
        jLabel1.setText("Kitchen Dashboard");

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
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(TimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        HeaderPanelLayout.setVerticalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(NameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HeaderPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(TimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        SideBarButtonPanel.setLayout(new java.awt.GridLayout(13, 1, 6, 6));

        kitchenDashboardButton.setText("Kitchen Dashboard");
        kitchenDashboardButton.setAlignmentY(0.0F);
        kitchenDashboardButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kitchenDashboardButton.setkBorderRadius(0);
        kitchenDashboardButton.setkEndColor(new java.awt.Color(51, 51, 51));
        kitchenDashboardButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        kitchenDashboardButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        kitchenDashboardButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        kitchenDashboardButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        kitchenDashboardButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        kitchenDashboardButton.setkStartColor(new java.awt.Color(153, 153, 153));
        kitchenDashboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kitchenDashboardButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(kitchenDashboardButton);

        sideBarMainPanel.add(SideBarButtonPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(sideBarMainPanel, java.awt.BorderLayout.LINE_START);

        MainDashboardPanel.setLayout(new java.awt.BorderLayout());

        BodyPanel.setLayout(new java.awt.GridLayout(2, 0));

        MainTablePanel.setLayout(new java.awt.BorderLayout());

        TableTitlePanel.setBackground(new java.awt.Color(222, 220, 220));
        TableTitlePanel.setPreferredSize(new java.awt.Dimension(886, 30));

        TableTitle.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        TableTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TableTitle.setText("Pending Orders");

        javax.swing.GroupLayout TableTitlePanelLayout = new javax.swing.GroupLayout(TableTitlePanel);
        TableTitlePanel.setLayout(TableTitlePanelLayout);
        TableTitlePanelLayout.setHorizontalGroup(
            TableTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TableTitlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TableTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 853, Short.MAX_VALUE)
                .addContainerGap())
        );
        TableTitlePanelLayout.setVerticalGroup(
            TableTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TableTitlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TableTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
        );

        MainTablePanel.add(TableTitlePanel, java.awt.BorderLayout.PAGE_START);

        TableBodyPanel.setBackground(new java.awt.Color(222, 220, 220));

        Table.setBackground(new java.awt.Color(222, 220, 220));
        Table.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order ID", "Ordered Time", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table.setSelectionBackground(new java.awt.Color(148, 148, 148));
        Table.setSelectionForeground(new java.awt.Color(255, 255, 255));
        Table.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(Table);

        javax.swing.GroupLayout TableBodyPanelLayout = new javax.swing.GroupLayout(TableBodyPanel);
        TableBodyPanel.setLayout(TableBodyPanelLayout);
        TableBodyPanelLayout.setHorizontalGroup(
            TableBodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TableBodyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 853, Short.MAX_VALUE)
                .addContainerGap())
        );
        TableBodyPanelLayout.setVerticalGroup(
            TableBodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TableBodyPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
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
            .addGap(0, 432, Short.MAX_VALUE)
        );
        pieChartPanelLayout.setVerticalGroup(
            pieChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 234, Short.MAX_VALUE)
        );

        ChartPanel1.add(pieChartPanel, java.awt.BorderLayout.CENTER);

        MainChartPanel.add(ChartPanel1);

        ChartPanel2.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout barChartPanelLayout = new javax.swing.GroupLayout(barChartPanel);
        barChartPanel.setLayout(barChartPanelLayout);
        barChartPanelLayout.setHorizontalGroup(
            barChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 432, Short.MAX_VALUE)
        );
        barChartPanelLayout.setVerticalGroup(
            barChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 234, Short.MAX_VALUE)
        );

        ChartPanel2.add(barChartPanel, java.awt.BorderLayout.CENTER);

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
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
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

    private void kitchenDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kitchenDashboardButtonActionPerformed

        openWindow("KitchenDashboard");

    }//GEN-LAST:event_kitchenDashboardButtonActionPerformed

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged

        configureEscKeyBinding(); // Esc control

        setupAltF4Protection();  // Alt + F4 control

    }//GEN-LAST:event_formWindowStateChanged

    public static void main(String args[]) {

        FlatMacLightLaf.setup();

        java.awt.EventQueue.invokeLater(() -> {

            new KitchenManagementDashboard(null).setVisible(true);

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
    private javax.swing.JPanel barChartPanel;
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
    private com.k33ptoo.components.KButton kitchenDashboardButton;
    private javax.swing.JPanel pieChartPanel;
    private javax.swing.JPanel sideBarIconPanel;
    private com.k33ptoo.components.KGradientPanel sideBarMainPanel;
    // End of variables declaration//GEN-END:variables

    private void configureEscKeyBinding() {

        // ESC -> Minimize
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "minimizeWindow");

        getRootPane().getActionMap().put("minimizeWindow", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setState(KitchenManagementDashboard.ICONIFIED);

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
