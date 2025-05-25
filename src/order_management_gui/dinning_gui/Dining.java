/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package order_management_gui.dinning_gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.MySql;
import order_management_gui.CustomerLogin;
import raven.toast.Notifications;

/**
 *
 * @author Hash_Boy
 */
public class Dining extends javax.swing.JFrame {

    public Dining() {
        initComponents();
        this.setExtendedState(Dining.MAXIMIZED_BOTH);

        int randomId = (int) (Math.random() * 9000) + 1000;
        orderIdLable.setText("OID" + String.valueOf(randomId));;

        dt();
        times();
        updateTotalPrice();

    }

    private String customerName;

    public void setCustomerName(String cName) {

        this.customerName = cName;
        customerNameTextField.setText(cName);

    }

    public void dt() {

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dd = sdf.format(d);
        date.setText(dd);

    }
    Timer t;
    SimpleDateFormat st;

    public void times() {

        t = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Date dt = new Date();
                st = new SimpleDateFormat("hh:mm:ss");

                String tt = st.format(dt);

                time.setText(tt);
            }

        });

        t.start();
    }

    public void updateTotalPrice() {

        System.out.println("updateTotalPrice called");

        double total = 0.0;

        DefaultTableModel model = (DefaultTableModel) OrderTable.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {

            Object value = model.getValueAt(i, 5);

            if (value != null) {

                try {

                    if (value instanceof Number) {

                        total += ((Number) value).doubleValue();

                    } else {

                        total += Double.parseDouble(value.toString());

                    }

                } catch (NumberFormatException e) {

                    e.printStackTrace();

                }

            }

        }

        totalPriceLabel.setText(String.format("Rs. %,.2f", total));

    }

    public JTable addOrderItemToTable() {

        return OrderTable;

    }

    private int getPortionTypeId(String portionName) {

        switch (portionName.toLowerCase()) {

            case "full":

                return 1;

            case "half":

                return 2;

            case "no portion":

                return 3;

            default:

                return -1;

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        orderIdLable = new javax.swing.JLabel();
        bodyPanel = new javax.swing.JPanel();
        addDataPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tableAndPayPanel = new javax.swing.JPanel();
        tablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        OrderTable = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        customerNameTextField = new javax.swing.JTextField();
        menuButton = new com.k33ptoo.components.KButton();
        paymentPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        totalPriceLabel = new javax.swing.JLabel();
        makeAnOrderButton = new com.k33ptoo.components.KButton();
        footerPanel = new javax.swing.JPanel();
        signOutButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        headerPanel.setBackground(new java.awt.Color(0, 153, 102));
        headerPanel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        headerPanel.setMinimumSize(new java.awt.Dimension(100, 50));
        headerPanel.setPreferredSize(new java.awt.Dimension(855, 50));

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel3.setText("Date :");

        date.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        date.setForeground(new java.awt.Color(255, 255, 255));
        date.setText("yyyy/mm/dd");

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel7.setText("Time :");

        time.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        time.setForeground(new java.awt.Color(255, 255, 255));
        time.setText("hh : mm : ss");

        jLabel1.setFont(new java.awt.Font("Audiowide", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Dinning");

        orderIdLable.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        orderIdLable.setText("Order Id");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(orderIdLable, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(10, 10, 10)
                .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel7)
                .addGap(10, 10, 10)
                .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(orderIdLable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(headerPanel, java.awt.BorderLayout.PAGE_START);

        bodyPanel.setLayout(new java.awt.BorderLayout());

        addDataPanel.setBackground(new java.awt.Color(0, 153, 102));
        addDataPanel.setPreferredSize(new java.awt.Dimension(855, 150));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/banner.png"))); // NOI18N
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout addDataPanelLayout = new javax.swing.GroupLayout(addDataPanel);
        addDataPanel.setLayout(addDataPanelLayout);
        addDataPanelLayout.setHorizontalGroup(
            addDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1044, Short.MAX_VALUE)
        );
        addDataPanelLayout.setVerticalGroup(
            addDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2)
        );

        bodyPanel.add(addDataPanel, java.awt.BorderLayout.PAGE_START);

        tableAndPayPanel.setLayout(new java.awt.BorderLayout());

        OrderTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        OrderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FID", "Food Name", "Portion", "QTY", "Item Price", "Total Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        OrderTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(OrderTable);

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel8.setText("Customer Name");

        customerNameTextField.setEditable(false);

        menuButton.setText("Menu");
        menuButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        menuButton.setkEndColor(new java.awt.Color(51, 255, 51));
        menuButton.setkHoverEndColor(new java.awt.Color(0, 153, 102));
        menuButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        menuButton.setkHoverStartColor(new java.awt.Color(51, 255, 51));
        menuButton.setkPressedColor(new java.awt.Color(0, 153, 102));
        menuButton.setkSelectedColor(new java.awt.Color(0, 153, 102));
        menuButton.setkStartColor(new java.awt.Color(0, 153, 102));
        menuButton.setPreferredSize(new java.awt.Dimension(185, 32));
        menuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel8)
                .addGap(12, 12, 12)
                .addComponent(customerNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 427, Short.MAX_VALUE)
                .addComponent(menuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
            .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(tablePanelLayout.createSequentialGroup()
                    .addGap(32, 32, 32)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
                    .addGap(32, 32, 32)))
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(244, Short.MAX_VALUE))
            .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tablePanelLayout.createSequentialGroup()
                    .addGap(78, 78, 78)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        tableAndPayPanel.add(tablePanel, java.awt.BorderLayout.CENTER);

        paymentPanel.setPreferredSize(new java.awt.Dimension(1044, 110));

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel5.setText("Total Payment :");

        totalPriceLabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        totalPriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        totalPriceLabel.setText("0.00");

        makeAnOrderButton.setText("Place an Order");
        makeAnOrderButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        makeAnOrderButton.setkEndColor(new java.awt.Color(51, 255, 51));
        makeAnOrderButton.setkHoverEndColor(new java.awt.Color(0, 153, 102));
        makeAnOrderButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        makeAnOrderButton.setkHoverStartColor(new java.awt.Color(51, 255, 51));
        makeAnOrderButton.setkPressedColor(new java.awt.Color(0, 153, 102));
        makeAnOrderButton.setkSelectedColor(new java.awt.Color(0, 153, 102));
        makeAnOrderButton.setkStartColor(new java.awt.Color(0, 153, 102));
        makeAnOrderButton.setPreferredSize(new java.awt.Dimension(185, 32));
        makeAnOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeAnOrderButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paymentPanelLayout = new javax.swing.GroupLayout(paymentPanel);
        paymentPanel.setLayout(paymentPanelLayout);
        paymentPanelLayout.setHorizontalGroup(
            paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paymentPanelLayout.createSequentialGroup()
                .addContainerGap(748, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paymentPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(makeAnOrderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paymentPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(totalPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );
        paymentPanelLayout.setVerticalGroup(
            paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(makeAnOrderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        tableAndPayPanel.add(paymentPanel, java.awt.BorderLayout.PAGE_END);

        bodyPanel.add(tableAndPayPanel, java.awt.BorderLayout.CENTER);

        footerPanel.setBackground(new java.awt.Color(0, 153, 102));
        footerPanel.setPreferredSize(new java.awt.Dimension(855, 45));

        signOutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/logout.png"))); // NOI18N
        signOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signOutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout footerPanelLayout = new javax.swing.GroupLayout(footerPanel);
        footerPanel.setLayout(footerPanelLayout);
        footerPanelLayout.setHorizontalGroup(
            footerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(footerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(signOutButton)
                .addContainerGap(1000, Short.MAX_VALUE))
        );
        footerPanelLayout.setVerticalGroup(
            footerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(footerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(signOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        bodyPanel.add(footerPanel, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(bodyPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void signOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signOutButtonActionPerformed

        Notifications.getInstance().setJFrame(this);

        int result = JOptionPane.showConfirmDialog(this, "You Will Go Back to the Customer Login. Proceed?", "Confirm Sign Out",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {

            this.dispose();

            CustomerLogin customerLogin = new CustomerLogin();
            customerLogin.setVisible(true);

        } else {

        }

    }//GEN-LAST:event_signOutButtonActionPerformed

    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButtonActionPerformed
        DiningMenu dm = new DiningMenu(this, true);
        dm.setVisible(true);

    }//GEN-LAST:event_menuButtonActionPerformed

    private void makeAnOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeAnOrderButtonActionPerformed

        DefaultTableModel model = (DefaultTableModel) OrderTable.getModel();

        Notifications.getInstance().setJFrame(this);

        int rowCount = model.getRowCount();

        if (rowCount == 0) {

            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Please Mak an Order Before Place Using Menu");

        } else {

            String CuctomerName = customerNameTextField.getText();

            String OrderId = orderIdLable.getText();

            try {

                ResultSet OrdreCheckResultSet = MySql.executeSearch("SELECT * FROM `order_details` WHERE `order_id` = '" + OrderId + "' ");

                if (OrdreCheckResultSet.next()) {

                    Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Please Contact Hotel manager Or Call Support");

                    return;

                } else {

                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
                    String OrderTime = now.format(formatter);

                    MySql.executeUpdate("INSERT INTO `order_details` (`order_id`,`customer_name`,`orderd_time`,`cooking_status_cooking_status_id`) "
                            + "VALUES ('" + OrderId + "','" + customerName + "','" + OrderTime + "','2') ");

                    for (int i = 0; i < rowCount; i++) {

                        String foodId = model.getValueAt(i, 0).toString();

                        String foodName = model.getValueAt(i, 1).toString();

                        String portionName = model.getValueAt(i, 2).toString();

                        int portionType = getPortionTypeId(portionName);

                        if (portionType == -1) {

                            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Invalid portion type: " + portionName);

                            return;

                        }

                        int qty = Integer.parseInt(model.getValueAt(i, 3).toString());

                        double totalPrice = Double.parseDouble(model.getValueAt(i, 5).toString());

                        MySql.executeUpdate("INSERT INTO `order_items` (`order_details_order_id`,`portion_types_portion_types_id`,`foods_food_id`,`qty`,`price`) "
                                + "VALUES ('" + OrderId + "','" + portionType + "','" + foodId + "','" + qty + "','" + totalPrice + "') ");
                    }

                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, OrderId + " " + "Gose to kitchen to prepare, We Will Call You When The Order Is Ready");

                }

            } catch (Exception e) {

                e.printStackTrace();

                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Something went wrong.Please Contact Manager");

            }

        }


    }//GEN-LAST:event_makeAnOrderButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dining().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable OrderTable;
    private javax.swing.JPanel addDataPanel;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JTextField customerNameTextField;
    private javax.swing.JLabel date;
    private javax.swing.JPanel footerPanel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private com.k33ptoo.components.KButton makeAnOrderButton;
    private com.k33ptoo.components.KButton menuButton;
    private javax.swing.JLabel orderIdLable;
    private javax.swing.JPanel paymentPanel;
    private javax.swing.JButton signOutButton;
    private javax.swing.JPanel tableAndPayPanel;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JLabel time;
    private javax.swing.JLabel totalPriceLabel;
    // End of variables declaration//GEN-END:variables
private String generateUniqueOrderId() {
        return "ORD" + System.currentTimeMillis(); // or UUID.randomUUID().toString()
    }

}
