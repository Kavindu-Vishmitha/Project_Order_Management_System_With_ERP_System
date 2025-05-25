package stock_management_gui;

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
import javax.swing.table.JTableHeader;
import model.MySql;
import java.sql.ResultSet;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.PieSectionEntity;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
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
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class StockManagementDashboard extends javax.swing.JFrame {

    private String name;
    private JFrame currentWindow = null; // Keep track of the currently open window

    public StockManagementDashboard(String name) {

        initComponents();

        this.setExtendedState(StockManagementDashboard.MAXIMIZED_BOTH);

        configureEscKeyBinding(); // Esc control

        setupAltF4Protection();  // Alt + F4 control

        showBarChart();
        showPieChart();

        this.name = name;

        stockUserDetails();

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

    private void stockUserDetails() {

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

        Table.getTableHeader().setBackground(new Color(196, 155, 77));
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

            case "AddPurchasingOrder":
                currentWindow = new AddPurchasingOrder(null, null);
                break;

            case "ManageGrns":
                currentWindow = new ManageGrns();
                break;

            case "AddProduct":
                currentWindow = new AddProduct();
                break;

            case "BrandRegister":
                currentWindow = new BrandRegister();
                break;

            case "AddCategory":
                currentWindow = new AddCategory();
                break;

            case "AddFood":
                currentWindow = new AddFood();
                break;

            case "Invoice":
                currentWindow = new Invoice(name);
                break;

        }

        currentWindow.setVisible(true);

    }

    DefaultTableModel model;

    private void loadTableData() {
        try {
            String query = "SELECT f.food_name AS `Product Name`, "
                    + "SUM(ii.qty) AS `Units Sold`, "
                    + "SUM(ii.qty * ii.price) AS `Total Revenue`, "
                    + "MAX(od.orderd_time) AS `Last Sold Date` "
                    + "FROM invoice_items ii INNER JOIN order_items oi ON ii.order_items_oid = oi.oid "
                    + "INNER JOIN foods f ON oi.foods_food_id = f.food_id "
                    + "INNER JOIN order_details od ON ii.order_details_order_id = od.order_id "
                    + "GROUP BY  f.food_id ORDER BY `Units Sold` DESC LIMIT 10";

            ResultSet rs = MySql.executeSearch(query);

            DefaultTableModel model = (DefaultTableModel) Table.getModel(); // Your JTable name
            model.setRowCount(0);

            while (rs.next()) {

                String name = rs.getString("Product Name");
                int unitsSold = rs.getInt("Units Sold");
                double revenue = rs.getDouble("Total Revenue");
                String lastSold = rs.getString("Last Sold Date");

                model.addRow(new Object[]{name, unitsSold, revenue, lastSold});
            }

            Table.setModel(model);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading top-selling products data");
        }
    }

    private void showBarChart() {

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

                dataset.setValue(qty, "Stock", category);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        JFreeChart barChart = ChartFactory.createBarChart("Stock by Category", "Category", "Quantity", dataset, PlotOrientation.VERTICAL, false, true, false);

        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLACK);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(0, 153, 204)); // Blue bars

        ChartPanel barChartPanel = new ChartPanel(barChart);
        ChartPanel1.removeAll();
        ChartPanel1.add(barChartPanel, BorderLayout.CENTER);
        ChartPanel1.validate();

    }

    private void showPieChart() {

        DefaultPieDataset pieDataset = new DefaultPieDataset();

        try {

            String query = "SELECT sos.suppliers_order_status_name, COUNT(*) AS total "
                    + "FROM grn g "
                    + "JOIN suppliers_order_status sos "
                    + "ON g.suppliers_order_status_suppliers_order_status_id = sos.suppliers_order_status_id "
                    + "GROUP BY sos.suppliers_order_status_id";

            ResultSet rs = MySql.executeSearch(query);

            while (rs.next()) {

                String status = rs.getString("suppliers_order_status_name");
                int count = rs.getInt("total");
                pieDataset.setValue(status, count);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        JFreeChart piechart = ChartFactory.createPieChart("GRN Status Summary", pieDataset, true, true, false);

        PiePlot piePlot = (PiePlot) piechart.getPlot();
        piePlot.setBackgroundPaint(Color.white);

        // Code to set tooltip text (used mouse click Listener)
        piePlot.setSimpleLabels(true); // cleaner label rendering
        piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} GRNs"));

        // Color mapping
        pieDataset.getKeys().forEach(key -> {

            String k = key.toString().toLowerCase();

            if (k.contains("placed")) {

                piePlot.setSectionPaint((Comparable) key, new Color(255, 204, 102));

            } else if (k.equals("order arrived")) {

                piePlot.setSectionPaint((Comparable) key, new Color(102, 255, 102));

            } else if (k.contains("not")) {

                piePlot.setSectionPaint((Comparable) key, new Color(0, 204, 204));

            } else {

                piePlot.setSectionPaint((Comparable) key, new Color(255, 102, 102)); // Default

            }

        });

        ChartPanel pieChartPanel = new ChartPanel(piechart);

        pieChartPanel.addChartMouseListener(new ChartMouseListener() {

            @Override
            public void chartMouseClicked(ChartMouseEvent cme) {

                ChartEntity entity = cme.getEntity();

                if (entity instanceof PieSectionEntity pieEntity) {

                    String status = (String) pieEntity.getSectionKey();

                    JOptionPane.showMessageDialog(null, "You clicked on: " + status, "GRN Status Click", JOptionPane.INFORMATION_MESSAGE);

                }

            }

            @Override
            public void chartMouseMoved(ChartMouseEvent cme) {
            }

        });

        ChartPanel2.removeAll();
        ChartPanel2.add(pieChartPanel, BorderLayout.CENTER);
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
        addPurchasingOrderButton = new com.k33ptoo.components.KButton();
        manageGRNSButton = new com.k33ptoo.components.KButton();
        addProductButton = new com.k33ptoo.components.KButton();
        brandRegisterButton = new com.k33ptoo.components.KButton();
        addCategoryButton = new com.k33ptoo.components.KButton();
        AddFoodButton = new com.k33ptoo.components.KButton();
        invoiceButton = new com.k33ptoo.components.KButton();
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
        barChartPanel = new javax.swing.JPanel();
        ChartPanel2 = new javax.swing.JPanel();
        pieChartPanel = new javax.swing.JPanel();
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
        jLabel1.setText("Stock Dashboard");

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
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
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
        SideBarButtonPanel.setLayout(new java.awt.GridLayout(13, 1, 6, 6));

        addPurchasingOrderButton.setText("Add Purchasing Order");
        addPurchasingOrderButton.setAlignmentY(0.0F);
        addPurchasingOrderButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addPurchasingOrderButton.setkBorderRadius(0);
        addPurchasingOrderButton.setkEndColor(new java.awt.Color(51, 51, 51));
        addPurchasingOrderButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        addPurchasingOrderButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        addPurchasingOrderButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        addPurchasingOrderButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        addPurchasingOrderButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        addPurchasingOrderButton.setkStartColor(new java.awt.Color(153, 153, 153));
        addPurchasingOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPurchasingOrderButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(addPurchasingOrderButton);

        manageGRNSButton.setText("Manage GRNS");
        manageGRNSButton.setAlignmentY(0.0F);
        manageGRNSButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        manageGRNSButton.setkBorderRadius(0);
        manageGRNSButton.setkEndColor(new java.awt.Color(51, 51, 51));
        manageGRNSButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        manageGRNSButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        manageGRNSButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        manageGRNSButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        manageGRNSButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        manageGRNSButton.setkStartColor(new java.awt.Color(153, 153, 153));
        manageGRNSButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageGRNSButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(manageGRNSButton);

        addProductButton.setText("Add Product");
        addProductButton.setAlignmentY(0.0F);
        addProductButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addProductButton.setkBorderRadius(0);
        addProductButton.setkEndColor(new java.awt.Color(51, 51, 51));
        addProductButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        addProductButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        addProductButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        addProductButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        addProductButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        addProductButton.setkStartColor(new java.awt.Color(153, 153, 153));
        addProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(addProductButton);

        brandRegisterButton.setText("Brand Register");
        brandRegisterButton.setAlignmentY(0.0F);
        brandRegisterButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        brandRegisterButton.setkBorderRadius(0);
        brandRegisterButton.setkEndColor(new java.awt.Color(51, 51, 51));
        brandRegisterButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        brandRegisterButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        brandRegisterButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        brandRegisterButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        brandRegisterButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        brandRegisterButton.setkStartColor(new java.awt.Color(153, 153, 153));
        brandRegisterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brandRegisterButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(brandRegisterButton);

        addCategoryButton.setText("Add Category");
        addCategoryButton.setAlignmentY(0.0F);
        addCategoryButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addCategoryButton.setkBorderRadius(0);
        addCategoryButton.setkEndColor(new java.awt.Color(51, 51, 51));
        addCategoryButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        addCategoryButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        addCategoryButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        addCategoryButton.setkIndicatorColor(new java.awt.Color(51, 51, 51));
        addCategoryButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        addCategoryButton.setkStartColor(new java.awt.Color(153, 153, 153));
        addCategoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCategoryButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(addCategoryButton);

        AddFoodButton.setText("Add Food");
        AddFoodButton.setAlignmentY(0.0F);
        AddFoodButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AddFoodButton.setkBorderRadius(0);
        AddFoodButton.setkEndColor(new java.awt.Color(51, 51, 51));
        AddFoodButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        AddFoodButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        AddFoodButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        AddFoodButton.setkIndicatorColor(new java.awt.Color(51, 51, 51));
        AddFoodButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        AddFoodButton.setkStartColor(new java.awt.Color(153, 153, 153));
        AddFoodButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddFoodButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(AddFoodButton);

        invoiceButton.setText("Invoice");
        invoiceButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        invoiceButton.setkBorderRadius(0);
        invoiceButton.setkEndColor(new java.awt.Color(51, 51, 51));
        invoiceButton.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        invoiceButton.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        invoiceButton.setkHoverStartColor(new java.awt.Color(51, 51, 51));
        invoiceButton.setkIndicatorColor(new java.awt.Color(204, 0, 51));
        invoiceButton.setkPressedColor(new java.awt.Color(51, 51, 51));
        invoiceButton.setkStartColor(new java.awt.Color(153, 153, 153));
        invoiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                invoiceButtonActionPerformed(evt);
            }
        });
        SideBarButtonPanel.add(invoiceButton);

        sideBarMainPanel.add(SideBarButtonPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(sideBarMainPanel, java.awt.BorderLayout.LINE_START);

        MainDashboardPanel.setLayout(new java.awt.BorderLayout());

        BodyPanel.setLayout(new java.awt.GridLayout(2, 0));

        MainTablePanel.setLayout(new java.awt.BorderLayout());

        TableTitlePanel.setBackground(new java.awt.Color(255, 238, 203));
        TableTitlePanel.setPreferredSize(new java.awt.Dimension(886, 30));

        TableTitle.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        TableTitle.setForeground(new java.awt.Color(0, 91, 76));
        TableTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TableTitle.setText("Top Selling Products");

        javax.swing.GroupLayout TableTitlePanelLayout = new javax.swing.GroupLayout(TableTitlePanel);
        TableTitlePanel.setLayout(TableTitlePanelLayout);
        TableTitlePanelLayout.setHorizontalGroup(
            TableTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TableTitlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TableTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
                .addContainerGap())
        );
        TableTitlePanelLayout.setVerticalGroup(
            TableTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TableTitlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TableTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
        );

        MainTablePanel.add(TableTitlePanel, java.awt.BorderLayout.PAGE_START);

        TableBodyPanel.setBackground(new java.awt.Color(255, 238, 203));

        Table.setBackground(new java.awt.Color(255, 238, 203));
        Table.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Units Sold", "Total Revenue", "Last Sold Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table.setSelectionBackground(new java.awt.Color(255, 209, 128));
        Table.setSelectionForeground(new java.awt.Color(255, 255, 255));
        Table.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(Table);

        javax.swing.GroupLayout TableBodyPanelLayout = new javax.swing.GroupLayout(TableBodyPanel);
        TableBodyPanel.setLayout(TableBodyPanelLayout);
        TableBodyPanelLayout.setHorizontalGroup(
            TableBodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TableBodyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
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

        javax.swing.GroupLayout barChartPanelLayout = new javax.swing.GroupLayout(barChartPanel);
        barChartPanel.setLayout(barChartPanelLayout);
        barChartPanelLayout.setHorizontalGroup(
            barChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 443, Short.MAX_VALUE)
        );
        barChartPanelLayout.setVerticalGroup(
            barChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 234, Short.MAX_VALUE)
        );

        ChartPanel1.add(barChartPanel, java.awt.BorderLayout.CENTER);

        MainChartPanel.add(ChartPanel1);

        ChartPanel2.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout pieChartPanelLayout = new javax.swing.GroupLayout(pieChartPanel);
        pieChartPanel.setLayout(pieChartPanelLayout);
        pieChartPanelLayout.setHorizontalGroup(
            pieChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 443, Short.MAX_VALUE)
        );
        pieChartPanelLayout.setVerticalGroup(
            pieChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 234, Short.MAX_VALUE)
        );

        ChartPanel2.add(pieChartPanel, java.awt.BorderLayout.CENTER);

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
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
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

    private void addPurchasingOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPurchasingOrderButtonActionPerformed

        openWindow("AddPurchasingOrder");

    }//GEN-LAST:event_addPurchasingOrderButtonActionPerformed

    private void manageGRNSButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageGRNSButtonActionPerformed

        openWindow("ManageGrns");

    }//GEN-LAST:event_manageGRNSButtonActionPerformed

    private void addProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductButtonActionPerformed

        openWindow("AddProduct");

    }//GEN-LAST:event_addProductButtonActionPerformed

    private void brandRegisterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brandRegisterButtonActionPerformed

        openWindow("BrandRegister");

    }//GEN-LAST:event_brandRegisterButtonActionPerformed

    private void addCategoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCategoryButtonActionPerformed

        openWindow("AddCategory");

    }//GEN-LAST:event_addCategoryButtonActionPerformed

    private void invoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_invoiceButtonActionPerformed

        openWindow("Invoice");

    }//GEN-LAST:event_invoiceButtonActionPerformed

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged

        configureEscKeyBinding(); // Esc control

        setupAltF4Protection();  // Alt + F4 control

    }//GEN-LAST:event_formWindowStateChanged

    private void AddFoodButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddFoodButtonActionPerformed

        openWindow("AddFood");

    }//GEN-LAST:event_AddFoodButtonActionPerformed

    public static void main(String args[]) {

        FlatMacLightLaf.setup();

        java.awt.EventQueue.invokeLater(() -> {

            new StockManagementDashboard(null).setVisible(true);

        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.k33ptoo.components.KButton AddFoodButton;
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
    private com.k33ptoo.components.KButton addCategoryButton;
    private com.k33ptoo.components.KButton addProductButton;
    private com.k33ptoo.components.KButton addPurchasingOrderButton;
    private javax.swing.JPanel barChartPanel;
    private com.k33ptoo.components.KButton brandRegisterButton;
    private com.k33ptoo.components.KButton invoiceButton;
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
    private com.k33ptoo.components.KButton manageGRNSButton;
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

                setState(StockManagementDashboard.ICONIFIED);

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
