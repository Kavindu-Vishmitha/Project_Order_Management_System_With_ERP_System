/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package order_management_gui.dinning_gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import model.MySql;
import model.PortionData;

/**
 *
 * @author 2003k
 */
public class DiningSelectPortion extends javax.swing.JDialog {

    private Dining diningRef;

    public DiningSelectPortion(java.awt.Frame parent, boolean modal, Dining dining) {
        
        super(parent, true);
        
        this.diningRef = dining;
        
        initComponents();

        qtySpinner.addChangeListener(new ChangeListener() {
            
            public void stateChanged(ChangeEvent e) {
                
                if (foodId == null || foodId.isEmpty()) {
                    
                    return; // Skip if foodId not set yet
                    
                }

                int currentQty = (Integer) qtySpinner.getValue();
                
                int availableQty = getAvailableQty(foodId);

                if (currentQty > availableQty) {
                    
                    JOptionPane.showMessageDialog(null, "Only " + availableQty + " items available!");
                    
                    qtySpinner.setValue(availableQty);
                    
                }

                updateTotalPrice();
            }

        });

    }

    private String foodId;
    private String foodName;
    private String portionType;
    private Double Price;
    private Double unitPrice = 0.0;

    public void setFoodID(String foodId) {
        
        this.foodId = foodId;
        
        foodIdJLabel.setText(foodId);
        

        int availableQty = getAvailableQty(foodId);

        if (availableQty <= 0) {
                        
            qtySpinner.setEnabled(false);
            
            priceFormattedTextField.setEditable(false);
            
            makeOrderButton.setEnabled(false);
            
            JOptionPane.showMessageDialog(null, "Sorry, this item is out of stock!");                  
            
        } else {
            
            SpinnerNumberModel model = new SpinnerNumberModel(1, 1, availableQty, 1);
            
            qtySpinner.setModel(model);
            
            qtySpinner.setEnabled(true);
            
            priceFormattedTextField.setEditable(true);
            
            makeOrderButton.setEnabled(true);
            
        }
    }

    public int getAvailableQty(String foodId) {
        
        System.out.println("Food ID: " + foodId);

        int availableQty = 0;

        try {
            
            ResultSet qtyresultSet = MySql.executeSearch("SELECT `qty` FROM `foods` WHERE `food_id` = '"+foodId+"' ");
            
            if (qtyresultSet.next()) {
                
                availableQty = qtyresultSet.getInt("qty");                
                
            }
            
            System.out.println("DB connection opened");
                    
            System.out.println("Executing query with foodId = " + foodId);

            if (qtyresultSet.next()) {
                availableQty = qtyresultSet.getInt("qty");
                System.out.println("Available qty from DB: " + availableQty);
            } else {
                System.out.println("No rows returned for this foodId!");
            };

        } catch (Exception e) {
            e.printStackTrace();
        }

        return availableQty;
    }

    public void setFoodName(String fname) {

        this.foodName = fname;
        foodNamejLabel.setText(fname);

    }

    public void setPortionType(String Portion) {

        this.portionType = Portion;
        portionTypeJlabel.setText(Portion);

    }

    public void setfoodPrice(Double Price) {

        this.unitPrice = (Price == null || Price.isNaN()) ? 0.0 : Price;

        if (Price == null || Price.isNaN()) {

            priceFormattedTextField.setText("Rs. 0.00");

            ItemPricejlabel.setText("Rs. 0.00");

        } else {

            String formatted = String.format("%,.2f", Price);

            priceFormattedTextField.setText(formatted);

            ItemPricejlabel.setText("Rs. " + formatted);

        }

        updateTotalPrice();

    }

    private void updateTotalPrice() {

        int qty = (Integer) qtySpinner.getValue();
        double totalPrice = unitPrice * qty;
        String formatted = String.format("%,.2f", totalPrice);
        priceFormattedTextField.setValue(formatted);

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
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        foodNamejLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        priceFormattedTextField = new javax.swing.JFormattedTextField();
        makeOrderButton = new com.k33ptoo.components.KButton();
        qtySpinner = new javax.swing.JSpinner();
        portionTypeJlabel = new javax.swing.JLabel();
        ItemPricejlabel = new javax.swing.JLabel();
        foodIdJLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(0, 153, 102));
        jPanel2.setMinimumSize(new java.awt.Dimension(100, 50));
        jPanel2.setPreferredSize(new java.awt.Dimension(590, 50));

        jLabel1.setFont(new java.awt.Font("Audiowide", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Select Portion");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel2.setText("Food Name");

        foodNamejLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        foodNamejLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        foodNamejLabel.setText("Food Name");

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel5.setText("QTY");

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel6.setText("Price");

        makeOrderButton.setText("Make an Order");
        makeOrderButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        makeOrderButton.setkEndColor(new java.awt.Color(51, 255, 51));
        makeOrderButton.setkHoverEndColor(new java.awt.Color(0, 153, 102));
        makeOrderButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        makeOrderButton.setkHoverStartColor(new java.awt.Color(51, 255, 51));
        makeOrderButton.setkPressedColor(new java.awt.Color(0, 153, 102));
        makeOrderButton.setkSelectedColor(new java.awt.Color(0, 153, 102));
        makeOrderButton.setkStartColor(new java.awt.Color(0, 153, 102));
        makeOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeOrderButtonActionPerformed(evt);
            }
        });

        portionTypeJlabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        portionTypeJlabel.setText("Portion Type");

        ItemPricejlabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ItemPricejlabel.setText("Item Price");

        foodIdJLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        foodIdJLabel.setText("Food Id");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(qtySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(priceFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(foodNamejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(foodIdJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ItemPricejlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(portionTypeJlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(186, 186, 186)
                .addComponent(makeOrderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ItemPricejlabel)
                    .addComponent(foodIdJLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(foodNamejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(portionTypeJlabel))
                .addGap(44, 44, 44)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(priceFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qtySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addComponent(makeOrderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void makeOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeOrderButtonActionPerformed

        String foodId = foodIdJLabel.getText();
        String foodName = foodNamejLabel.getText();
        String portionType = portionTypeJlabel.getText();
        int qty = (Integer) qtySpinner.getValue();
        double price = unitPrice * qty;
        Double itemPrice = unitPrice;

        DefaultTableModel model = (DefaultTableModel) diningRef.addOrderItemToTable().getModel();

        boolean itemExists = false;

        for (int i = 0; i < model.getRowCount(); i++) {

            String existingFoodId = model.getValueAt(i, 0).toString();

            String existingPortionType = model.getValueAt(i, 2).toString();

            if (existingFoodId.equals(foodId) && existingPortionType.equals(portionType)) {

                int existingQty = Integer.parseInt(model.getValueAt(i, 3).toString());

                int newQty = existingQty + qty;

                double newTotal = newQty * unitPrice;

                model.setValueAt(newQty, i, 3);

                model.setValueAt(newTotal, i, 5);

                itemExists = true;

                break;

            }

        }

        if (!itemExists) {

            model.addRow(new Object[]{foodId, foodName, portionType, qty, unitPrice, price});

        }
        
        try {
            
            MySql.executeUpdate("UPDATE `foods` SET `qty` = qty - " + qty + " WHERE `food_id` = '" + foodId + "' ");
            System.out.println("foodId = '" + foodId + "', qty = " + qty);
            
            ResultSet qtycheckresult = MySql.executeSearch("SELECT `qty` FROM `foods` WHERE `food_id` = '" + foodId + "'");
           
            if (qtycheckresult.next()) {
                
                int reamainQty = qtycheckresult.getInt("qty");
                
                if (reamainQty <=0 ) {
                    
                    MySql.executeUpdate("UPDATE `foods` SET `food_status_food_status_id` = 2 WHERE `food_id` = '" + foodId + "' ");
                    
                }
                
            }
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
        }

        diningRef.updateTotalPrice();

        this.dispose();

    }//GEN-LAST:event_makeOrderButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatMacLightLaf.setup();

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                Dining diningInstance = new Dining();

                DiningSelectPortion dialog = new DiningSelectPortion(new javax.swing.JFrame(), true, diningInstance);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ItemPricejlabel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel foodIdJLabel;
    private javax.swing.JLabel foodNamejLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private com.k33ptoo.components.KButton makeOrderButton;
    private javax.swing.JLabel portionTypeJlabel;
    private javax.swing.JFormattedTextField priceFormattedTextField;
    private javax.swing.JSpinner qtySpinner;
    // End of variables declaration//GEN-END:variables

}
