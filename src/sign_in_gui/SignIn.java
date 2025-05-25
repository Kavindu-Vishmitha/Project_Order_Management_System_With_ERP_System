package sign_in_gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import dashboard_gui.Dashboard;
import finance_department_gui.FinanceDepartmentDashboard;
import hr_department_gui.HRDepartmentDashboard;
import java.awt.Color;
import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import model.MySql;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import order_management_gui.kitchen_gui.KitchenManagementDashboard;
import stock_management_gui.Invoice;
import stock_management_gui.StockManagementDashboard;

public class SignIn extends javax.swing.JFrame {

    private SignInBackground signInBackground;

    public SignIn() {

        initComponents();

        loadSignInBackground();    // load signInBackground

        addPlaceholder();   // call textfields placeholder method

        configureEscKeyBinding(); // Esc control

        setupAltF4Protection();  // Alt + F4 control

    }

    private void loadSignInBackground() {

        signInBackground = new SignInBackground();
        signInBackground.setEnabled(false);
        signInBackground.setAlwaysOnTop(false);
        signInBackground.toBack();
        signInBackground.setVisible(true);

    }

    @Override
    public void dispose() {

        super.dispose();

        if (signInBackground != null) {
            signInBackground.dispose(); // close background when SignIn is closed
        }

    }

    // Placeholders add method
    private void addPlaceholder() {

        // nameTextField placeholder & Color
        usernameTextField.setText("Ex : Sanjana_Kumara");
        usernameTextField.setForeground(Color.GRAY);

        // mobileTextField placeholder & Color
        passwordField.setText("Ex : ********");
        passwordField.setForeground(Color.GRAY);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButton1 = new javax.swing.JToggleButton();
        headerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        FooterPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        bodyPanel = new javax.swing.JPanel();
        signInLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        signInButton = new com.k33ptoo.components.KButton();

        jToggleButton1.setText("jToggleButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        headerPanel.setBackground(new java.awt.Color(153, 153, 153));
        headerPanel.setPreferredSize(new java.awt.Dimension(468, 60));

        jLabel1.setFont(new java.awt.Font("Audiowide", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Sign In");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(215, 215, 215)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                .addGap(207, 207, 207))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        getContentPane().add(headerPanel, java.awt.BorderLayout.PAGE_START);

        FooterPanel.setBackground(new java.awt.Color(51, 51, 51));
        FooterPanel.setPreferredSize(new java.awt.Dimension(468, 60));

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Designed & Developed By NeoLife Software Solutions");

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("All Rights Reserved | NeoLife Software Solutions");

        javax.swing.GroupLayout FooterPanelLayout = new javax.swing.GroupLayout(FooterPanel);
        FooterPanel.setLayout(FooterPanelLayout);
        FooterPanelLayout.setHorizontalGroup(
            FooterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FooterPanelLayout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addGroup(FooterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FooterPanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                        .addGap(103, 103, 103))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FooterPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(87, 87, 87))))
        );
        FooterPanelLayout.setVerticalGroup(
            FooterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FooterPanelLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(12, 12, 12))
        );

        getContentPane().add(FooterPanel, java.awt.BorderLayout.PAGE_END);

        signInLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        signInLabel.setText("User Name");

        usernameTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        usernameTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usernameTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                usernameTextFieldFocusLost(evt);
            }
        });
        usernameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                usernameTextFieldKeyPressed(evt);
            }
        });

        passwordLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        passwordLabel.setText("Password");

        passwordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        passwordField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                passwordFieldFocusLost(evt);
            }
        });
        passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordFieldKeyPressed(evt);
            }
        });

        signInButton.setText("Sign IN");
        signInButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        signInButton.setkEndColor(new java.awt.Color(0, 204, 204));
        signInButton.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        signInButton.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        signInButton.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        signInButton.setkPressedColor(new java.awt.Color(0, 102, 153));
        signInButton.setkSelectedColor(new java.awt.Color(0, 102, 153));
        signInButton.setkStartColor(new java.awt.Color(0, 102, 153));
        signInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signInButtonActionPerformed(evt);
            }
        });
        signInButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                signInButtonKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout bodyPanelLayout = new javax.swing.GroupLayout(bodyPanel);
        bodyPanel.setLayout(bodyPanelLayout);
        bodyPanelLayout.setHorizontalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(signInLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(48, 48, 48)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(passwordField))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyPanelLayout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(signInButton, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(134, 134, 134))
        );
        bodyPanelLayout.setVerticalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(signInLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(signInButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        getContentPane().add(bodyPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void usernameTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameTextFieldFocusGained

        // Checks and set clear the current nameTextField to enter data to it
        if (usernameTextField.getText().equals("Ex : Sanjana_Kumara")) {

            usernameTextField.setText("");
            usernameTextField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_usernameTextFieldFocusGained

    private void passwordFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordFieldFocusGained

        // Checks and set clear the current mobileTextField to enter data to it
        if (passwordField.getText().equals("Ex : ********")) {

            passwordField.setText("");
            passwordField.setForeground(Color.BLACK);

        }

    }//GEN-LAST:event_passwordFieldFocusGained

    private void usernameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameTextFieldFocusLost

        // Sets back the placeholder of nameTextField
        if (usernameTextField.getText().isEmpty()) {

            usernameTextField.setText("Ex : Sanjana_Kumara");
            usernameTextField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_usernameTextFieldFocusLost

    private void passwordFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordFieldFocusLost

        // Sets back the placeholder of mobileTextField
        if (passwordField.getText().isEmpty()) {

            passwordField.setText("Ex : ********");
            passwordField.setForeground(Color.GRAY);

        }

    }//GEN-LAST:event_passwordFieldFocusLost

    private void usernameTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameTextFieldKeyPressed
        // code to add key controls for nameTextField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_DOWN ->
                passwordField.grabFocus();

            case KeyEvent.VK_DELETE ->
                usernameTextField.setText("");

        }

    }//GEN-LAST:event_usernameTextFieldKeyPressed

    private void passwordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordFieldKeyPressed
        // code to add key controls for mobileTextField

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT, KeyEvent.VK_DOWN ->
                signInButton.grabFocus();

            case KeyEvent.VK_UP ->
                usernameTextField.grabFocus();

            case KeyEvent.VK_DELETE ->
                passwordField.setText("");

        }

    }//GEN-LAST:event_passwordFieldKeyPressed

    private void signInButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_signInButtonKeyPressed
        // code to add key controls for diningButton

        int keyCode = evt.getKeyCode();

        switch (keyCode) {

            case KeyEvent.VK_ENTER, KeyEvent.VK_INSERT ->
                signInButton.doClick();

            case KeyEvent.VK_LEFT, KeyEvent.VK_A, KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_BACK_SPACE ->
                passwordField.grabFocus();

            case KeyEvent.VK_DELETE ->
                reset();

        }

    }//GEN-LAST:event_signInButtonKeyPressed

    private void signInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signInButtonActionPerformed

        String username = usernameTextField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || username.equals("Ex : Sanjana_Kumara")) {

            JOptionPane.showMessageDialog(this, "Please Enter Your Name", "Empty", JOptionPane.WARNING_MESSAGE);
            usernameTextField.grabFocus();

        } else if (password.isEmpty() || password.equals("Ex : ********")) {

            JOptionPane.showMessageDialog(this, "Please Enter Your Password", "Empty", JOptionPane.WARNING_MESSAGE);
            passwordField.grabFocus();

        } else {

            String query = "SELECT d.department_name FROM employee_user eu "
                    + "INNER JOIN department d ON eu.department_department_id = d.department_id "
                    + "WHERE eu.user_name = '" + username + "' AND eu.user_password = '" + password + "'";

            ResultSet rs = null;

            try {

                rs = MySql.executeSearch(query);

            } catch (Exception ex) {

                Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);

            }

            try {

                if (rs.next()) {

                    String departmentName = null;

                    try {

                        departmentName = rs.getString("department_name");

                    } catch (SQLException ex) {

                        Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);

                    }

                    switch (departmentName) {

                        case "Management Department" -> {

                            String name = usernameTextField.getText();
                            new Dashboard(name).setVisible(true);

                        }

                        case "Hr Department" -> {

                            String name = usernameTextField.getText();
                            new HRDepartmentDashboard(name).setVisible(true);

                        }

                        case "Finance Department" -> {

                            String name = usernameTextField.getText();
                            new FinanceDepartmentDashboard(name).setVisible(true);

                        }

                        case "Stock Department" -> {

                            CashierLoging();

                        }

                        case "Kitchen Department" -> {

                            String name = usernameTextField.getText();
                            new KitchenManagementDashboard(name).setVisible(true);

                        }

                        default ->

                            JOptionPane.showMessageDialog(null, "No dashboard found for: " + departmentName);

                    }

                    this.dispose();

                } else {

                    JOptionPane.showMessageDialog(null, "Invalid username or password");

                }
            } catch (SQLException ex) {
                Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }//GEN-LAST:event_signInButtonActionPerformed

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged

        if ((evt.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED) {

            if (signInBackground != null) {
                signInBackground.dispose(); // close background when A is closed
            }

        } else if ((evt.getNewState() & Frame.NORMAL) == Frame.NORMAL) {

            loadSignInBackground();
            this.setAlwaysOnTop(true);
            this.toFront();

        }

        configureEscKeyBinding(); // Esc control

        setupAltF4Protection();  // Alt + F4 control

    }//GEN-LAST:event_formWindowStateChanged

    public static void main(String args[]) {

        FlatMacLightLaf.setup();

        java.awt.EventQueue.invokeLater(() -> {

            new SignIn().setVisible(true);

        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel FooterPanel;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private com.k33ptoo.components.KButton signInButton;
    private javax.swing.JLabel signInLabel;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables

    private void configureEscKeyBinding() {

        // ESC -> Minimize
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "minimizeWindow");

        getRootPane().getActionMap().put("minimizeWindow", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setState(SignIn.ICONIFIED);

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

    // reset code
    private void reset() {

        usernameTextField.setText("");
        passwordField.setText("");

    }

    private void CashierLoging() {

        try {

            String Username = usernameTextField.getText();
            String Password = passwordField.getText();

            String query = "SELECT * FROM employee_user "
                    + "INNER JOIN employee_position ON employee_position.employee_position_id = employee_user.employee_position_employee_position_id  "
                    + "WHERE employee_user.user_name = '" + Username + "' AND employee_user.user_password = '" + Password + "'";

            ResultSet rs = MySql.executeSearch(query);

            if (rs.next()) {

                String position = rs.getString("employee_position.position_name");
                String password = rs.getString("employee_user.user_password");

                if (Password.equals(password) && position.equals("Cashier")) {

                    Invoice invoice = new Invoice(Username);

                    invoice.setVisible(true);

                } else {

                    System.out.println("weradii");

                    String name = usernameTextField.getText();
                    new StockManagementDashboard(name).setVisible(true); 

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
