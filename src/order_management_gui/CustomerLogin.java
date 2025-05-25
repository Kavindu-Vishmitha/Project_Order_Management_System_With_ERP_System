package order_management_gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JWindow;

public class CustomerLogin extends javax.swing.JDialog {

    // This JWindow will serve as a semi‐transparent dark overlay behind the dialog
    private JWindow fakeBackground;
    private order_management_gui.dinning_gui.Dining dining;

    public CustomerLogin(java.awt.Frame parent, boolean modal) {

        super(parent, modal);

        initComponents();

        // Make sure the dialog itself is modal
        setModal(true);

        addPlaceholder();   // call textfields placeholder method

        configureKeyBindings();   // For Default Frame Key Controls

    }

    public CustomerLogin() {

        this(null, true);

    }

    @Override
    public void setVisible(boolean b) {

        if (b) {

            // create the semi‐transparent overlay
            fakeBackground = new JWindow();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            fakeBackground.setSize(screenSize);

            // Transparent dark overlay effect
            fakeBackground.setBackground(new Color(0, 0, 0, 220));

            // Make sure window uses no layout and paints as-is
            fakeBackground.setLayout(null);
            fakeBackground.setLocationRelativeTo(null);
            fakeBackground.setAlwaysOnTop(false);

            // Needed for Windows to apply transparency properly
            fakeBackground.setOpacity(0.6f);

            fakeBackground.setVisible(true);

            super.setVisible(true);

            if (fakeBackground != null) {
                fakeBackground.dispose();
            }

        } else {

            super.setVisible(false);

        }

    }

    // Placeholders add method
    private void addPlaceholder() {

        // nameTextField placeholder & Color
        nameTextField.setText("Ex : Sanjana Kumara");
        nameTextField.setForeground(Color.GRAY);

        // mobileTextField placeholder & Color
        mobileTextField.setText("Ex : 0761430xxx");
        mobileTextField.setForeground(Color.GRAY);

    }

    public void setDining(order_management_gui.dinning_gui.Dining dining) {

        this.dining = dining;

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        mobileTextField = new javax.swing.JTextField();
        diningButton = new com.k33ptoo.components.KButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(0, 153, 102));
        jPanel2.setAutoscrolls(true);
        jPanel2.setPreferredSize(new java.awt.Dimension(888, 50));

        jLabel1.setFont(new java.awt.Font("Audiowide", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Welcome to Our Restaurant");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setPreferredSize(new java.awt.Dimension(400, 417));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Enter Name");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 310, -1));

        nameTextField.setBackground(new java.awt.Color(219, 219, 219));
        nameTextField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        nameTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        nameTextField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 102), 3, true));
        nameTextField.setPreferredSize(new java.awt.Dimension(64, 32));
        nameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nameTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                nameTextFieldFocusLost(evt);
            }
        });
        nameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nameTextFieldKeyPressed(evt);
            }
        });
        jPanel3.add(nameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 320, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Mobile");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 150, -1));

        mobileTextField.setBackground(new java.awt.Color(219, 219, 219));
        mobileTextField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        mobileTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mobileTextField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 102), 3, true));
        mobileTextField.setPreferredSize(new java.awt.Dimension(64, 32));
        mobileTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                mobileTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                mobileTextFieldFocusLost(evt);
            }
        });
        mobileTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mobileTextFieldKeyPressed(evt);
            }
        });
        jPanel3.add(mobileTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 320, 30));

        diningButton.setText("Dining");
        diningButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        diningButton.setkEndColor(new java.awt.Color(51, 255, 51));
        diningButton.setkHoverEndColor(new java.awt.Color(0, 153, 102));
        diningButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        diningButton.setkHoverStartColor(new java.awt.Color(51, 255, 51));
        diningButton.setkPressedColor(new java.awt.Color(0, 153, 102));
        diningButton.setkSelectedColor(new java.awt.Color(0, 153, 102));
        diningButton.setkStartColor(new java.awt.Color(0, 153, 102));
        diningButton.setPreferredSize(new java.awt.Dimension(185, 32));
        diningButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diningButtonActionPerformed(evt);
            }
        });
        diningButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                diningButtonKeyPressed(evt);
            }
        });
        jPanel3.add(diningButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 280, 190, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/food.png"))); // NOI18N
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, -1));

        jPanel1.add(jPanel3, java.awt.BorderLayout.LINE_START);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 488, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 408, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void diningButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diningButtonActionPerformed

        try {

            String name = nameTextField.getText();
            String mobile = mobileTextField.getText();
            
            if (name.isEmpty()|| name.equals("Ex : Sanjana Kumara")) {

                JOptionPane.showMessageDialog(this, "Please Enter Your Name", "Empty", JOptionPane.WARNING_MESSAGE);
                nameTextField.grabFocus();

            } else if (mobile.isEmpty()|| name.equals("Ex : 0761430xxx")) {

                JOptionPane.showMessageDialog(this, "Please Enter Your Mobile", "Empty", JOptionPane.WARNING_MESSAGE);
                mobileTextField.grabFocus();

            } else if (!mobile.matches("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$")) {

                JOptionPane.showMessageDialog(this, "Please Enter Valid Mobile Number", "Empty", JOptionPane.WARNING_MESSAGE);
                mobileTextField.grabFocus();

            } else {

                order_management_gui.dinning_gui.Dining diningGUI = new order_management_gui.dinning_gui.Dining();
                diningGUI.setVisible(true);

                this.dispose();

                diningGUI.setCustomerName(name);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }//GEN-LAST:event_diningButtonActionPerformed

    private void nameTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTextFieldFocusGained

        // Checks and set clear the current nameTextField to enter data to it
        if (nameTextField.getText().equals("Ex : Sanjana Kumara")) {

            nameTextField.setText("");
            nameTextField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_nameTextFieldFocusGained

    private void mobileTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mobileTextFieldFocusGained

        // Checks and set clear the current mobileTextField to enter data to it
        if (mobileTextField.getText().equals("Ex : 0761430xxx")) {

            mobileTextField.setText("");
            mobileTextField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_mobileTextFieldFocusGained

    private void nameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTextFieldFocusLost

        // Sets back the placeholder of nameTextField
        if (nameTextField.getText().isEmpty()) {

            nameTextField.setText("Ex : Sanjana Kumara");
            nameTextField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_nameTextFieldFocusLost

    private void mobileTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mobileTextFieldFocusLost

        // Sets back the placeholder of mobileTextField
        if (mobileTextField.getText().isEmpty()) {

            mobileTextField.setText("Ex : 0761430xxx");
            mobileTextField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_mobileTextFieldFocusLost

    private void nameTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameTextFieldKeyPressed
        // code to add key controls for nameTextField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_DOWN ->
                mobileTextField.grabFocus();

            case KeyEvent.VK_DELETE ->
                nameTextField.setText("");

        }

    }//GEN-LAST:event_nameTextFieldKeyPressed

    private void mobileTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mobileTextFieldKeyPressed
        // code to add key controls for mobileTextField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_DOWN ->
                diningButton.grabFocus();

            case KeyEvent.VK_UP ->
                nameTextField.grabFocus();

            case KeyEvent.VK_DELETE ->
                mobileTextField.setText("");

        }

    }//GEN-LAST:event_mobileTextFieldKeyPressed

    private void diningButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diningButtonKeyPressed
        // code to add key controls for diningButton

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                diningButton.doClick();

            case KeyEvent.VK_LEFT, KeyEvent.VK_A, KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_BACK_SPACE ->
                mobileTextField.grabFocus();

            case KeyEvent.VK_DELETE ->
                reset();

        }

    }//GEN-LAST:event_diningButtonKeyPressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        if (fakeBackground != null) {
            fakeBackground.dispose();
        }
        dispose();

    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {

        FlatMacLightLaf.setup();

        java.awt.EventQueue.invokeLater(() -> {

            new CustomerLogin().setVisible(true);

        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.k33ptoo.components.KButton diningButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField mobileTextField;
    private javax.swing.JTextField nameTextField;
    // End of variables declaration//GEN-END:variables

    private void configureKeyBindings() {

        // Alt + F11 detection
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher((KeyEvent e) -> {

            if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F11 && e.isAltDown()) {

                // Alt + F4 pressed
                int confirm = JOptionPane.showConfirmDialog(null, "Do you really want to close the app?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }

                return true;

            }

            return false;

        });

    }

    // reset code
    private void reset() {

        nameTextField.setText("");
        mobileTextField.setText("");

    }

}
