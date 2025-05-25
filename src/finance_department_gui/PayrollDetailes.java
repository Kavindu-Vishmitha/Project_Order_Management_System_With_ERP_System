/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package finance_department_gui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.MySql;
import java.sql.ResultSet;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import stock_management_gui.StockManagementDashboard;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author sanja
 */
public class PayrollDetailes extends javax.swing.JDialog {

    public PayrollDetailes(java.awt.Frame parent, boolean modal) {

        super(parent, modal);

        initComponents();

//        JDialog dialog = new JDialog();
//
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        
////        this.setExtendedState(PayrollDetailes.MAXIMIZED_BOTH);
//        dialog.setBounds(0, 0, screenSize.width, screenSize.height);
        addPlaceholder();

        dateLoad();

        calculateSalaries();

        loadPayrollDataToTable();

    }

    public String loadEmpname(String Empname, String Empposition, String Empdepartment, String Empid, String Empbasicsalary) {

        try {

            EmployeeNameLabel.setText(Empname);
            EmployeePositionLabel.setText(Empposition);
            EmployeeDepartmentLabel.setText(Empdepartment);
            BasicSalaryTextField.setText(Empbasicsalary);
            EmployeeIDLabel.setText(Empid);

            double netSalary = Double.parseDouble(Empbasicsalary) * 0.92;

            NetSalaryTextField.setText(String.format("%.2f", netSalary));

            PayableSalaryTextField.setText(NetSalaryTextField.getText());

            // Advance payment data ganna
            String query = "SELECT * FROM `advanced_payroll`  WHERE `employee_employee_id` = '" + Empid + "' ";

            ResultSet rs = MySql.executeSearch(query);

            if (rs.next()) {

                double advancedPrice = rs.getDouble("advanced_price");

//                double paybleslary = rs.getDouble("payable_salary");
                String adPayID = rs.getString("ad_pay_id");

                AdvancedPaymentTextField.setText(String.valueOf(advancedPrice));

                calculateSalaries();

//                PayableSalaryTextField.setText(String.valueOf(paybleslary));
                return adPayID;

            }

            rs.close();

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        return null;

    }

    public void dateLoad() {

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy - MM - dd");
        String dateformat = dateFormat.format(date);
        DatejLabel.setText(dateformat);

    }

    void loadPayrollDataToTable() {

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear existing rows

        String employeeId = EmployeeIDLabel.getText().trim();

        try {

            String query = "SELECT * FROM `employee_payrolls` "
                    + "INNER JOIN basic_salary ON basic_salary.basic_id = employee_payrolls.basic_salary_basic_id "
                    + "INNER JOIN advanced_payroll ON advanced_payroll.ad_pay_id = employee_payrolls.advanced_payroll_ad_pay_id WHERE employee_payrolls.employee_employee_id = '" + employeeId + "'  ";
            ResultSet rs = MySql.executeSearch(query);

            while (rs.next()) {

                int payrollId = rs.getInt("payroll_id");
                String empId = rs.getString("employee_employee_id");
                String empName = rs.getString("employee_name");
                String month = rs.getString("month");
                String dateIssued = rs.getString("date_issued");
                String authorizedBy = rs.getString("authorized_by");
                String paybleSalary = rs.getString("payable_salary");
                String basic = rs.getString("basic_salary.basic");
                String noPay = rs.getString("no_pay_deduction");
                String otherDeduction = rs.getString("other_deduction");
                String otherAllowance = rs.getString("other_allowances");
                String netBonus = rs.getString("net_bonus");
                String netSalary = rs.getString("net_salary");
                String ownerBonus = rs.getString("owner_bonus");
                String signOnBonus = rs.getString("sign_on_bonus");
                String performanceBonus = rs.getString("performance_bonus");
                String holidayBonus = rs.getString("holiday_bonus");
                String teamBonus = rs.getString("team_bonus");
                String advancedPayment = rs.getString("advanced_payroll.advanced_price");

                // JTable columns: PayID, Month, Date Issued, Status, Authorize By
                model.addRow(new Object[]{payrollId, empId, empName, month, dateIssued, "Finance Department", paybleSalary, basic, noPay, otherDeduction, otherAllowance, ownerBonus, netBonus, netSalary, advancedPayment, signOnBonus, performanceBonus, holidayBonus, teamBonus});

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void calculateSalaries() {

        try {

            double basic = parseDoubleSafe(BasicSalaryTextField.getText());
            double noPay = parseDoubleSafe(NoPayDeductionsTextField.getText());
            double otherDeduction = parseDoubleSafe(OtherDeductionsTextField.getText());
            double otherAllowance = parseDoubleSafe(OtherAllowancesTextField.getText());
            double netBonus = parseDoubleSafe(NetBonasTextField.getText());
            double ownerBonus = parseDoubleSafe(OwnerBonasTextField.getText());
            double signOnBonus = parseDoubleSafe(SignOnBonusTextField.getText());
            double performanceBonus = parseDoubleSafe(PerformanceBonasTextField.getText());
            double holidayBonus = parseDoubleSafe(HolidayBonasjTextField.getText());
            double teamBonus = parseDoubleSafe(TeamBonasTextField.getText());
            double advancedPayment = parseDoubleSafe(AdvancedPaymentTextField.getText());

            double epfEmployee = basic * 0.08;
            double epfEmployer = basic * 0.12;
            double etf = basic * 0.03;

            double totalBonus = netBonus + ownerBonus + signOnBonus + performanceBonus + holidayBonus + teamBonus;
            double totalDeduction = noPay + otherDeduction + epfEmployee;

            double netSalary = basic + otherAllowance + totalBonus - totalDeduction;
            double payableSalary = netSalary - advancedPayment;

            NetSalaryTextField.setText(String.format("%.2f", netSalary));
            PayableSalaryTextField.setText(String.format("%.2f", payableSalary));

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    private double parseDoubleSafe(String text) {

        if (text == null || text.trim().isEmpty()) {

            return 0.0;

        }

        try {

            return Double.parseDouble(text);

        } catch (NumberFormatException e) {

            return 0.0;

        }

    }

    private void addFieldListeners() {

        DocumentListener salaryListener = new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                calculateSalaries();
            }

            public void removeUpdate(DocumentEvent e) {
                calculateSalaries();
            }

            public void changedUpdate(DocumentEvent e) {
                calculateSalaries();
            }

        };

        NoPayDeductionsTextField.getDocument().addDocumentListener(salaryListener);
        OtherDeductionsTextField.getDocument().addDocumentListener(salaryListener);
        OtherAllowancesTextField.getDocument().addDocumentListener(salaryListener);
        NetBonasTextField.getDocument().addDocumentListener(salaryListener);
        OwnerBonasTextField.getDocument().addDocumentListener(salaryListener);
        SignOnBonusTextField.getDocument().addDocumentListener(salaryListener);
        PerformanceBonasTextField.getDocument().addDocumentListener(salaryListener);
        HolidayBonasjTextField.getDocument().addDocumentListener(salaryListener);
        TeamBonasTextField.getDocument().addDocumentListener(salaryListener);
        AdvancedPaymentTextField.getDocument().addDocumentListener(salaryListener);

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
        EmployeeDepartmentLabel = new javax.swing.JLabel();
        EmployeeNameLabel = new javax.swing.JLabel();
        EmployeeIDLabel = new javax.swing.JLabel();
        EmployeePositionLabel = new javax.swing.JLabel();
        BodyPanel = new javax.swing.JPanel();
        SalaryDetailsPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        BasicSalaryTextField = new javax.swing.JTextField();
        NoPayDeductionsTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        OtherDeductionsTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        OtherAllowancesTextField = new javax.swing.JTextField();
        NetBonasTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        OwnerBonasTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        AdvancedPaymentTextField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        NetSalaryTextField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        SignOnBonusTextField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        PerformanceBonasTextField = new javax.swing.JTextField();
        HolidayBonasjTextField = new javax.swing.JTextField();
        HolidayBonasTextField = new javax.swing.JLabel();
        TeamBonusextField = new javax.swing.JLabel();
        PayableSalaryTextField = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        TeamBonasTextField = new javax.swing.JTextField();
        kButton1 = new com.k33ptoo.components.KButton();
        kButton2 = new com.k33ptoo.components.KButton();
        jLabel4 = new javax.swing.JLabel();
        SearchPayIDTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        DatejLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        HeaderPanel.setPreferredSize(new java.awt.Dimension(662, 80));

        EmployeeDepartmentLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 24)); // NOI18N
        EmployeeDepartmentLabel.setText("Employee Department");

        EmployeeNameLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 24)); // NOI18N
        EmployeeNameLabel.setText("Employee Name");

        EmployeeIDLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        EmployeeIDLabel.setText("Employee ID");

        EmployeePositionLabel.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        EmployeePositionLabel.setText("Employee Position");

        javax.swing.GroupLayout HeaderPanelLayout = new javax.swing.GroupLayout(HeaderPanel);
        HeaderPanel.setLayout(HeaderPanelLayout);
        HeaderPanelLayout.setHorizontalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EmployeeNameLabel)
                    .addComponent(EmployeeIDLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 745, Short.MAX_VALUE)
                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EmployeeDepartmentLabel)
                    .addComponent(EmployeePositionLabel))
                .addGap(25, 25, 25))
        );
        HeaderPanelLayout.setVerticalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EmployeeNameLabel)
                    .addComponent(EmployeeDepartmentLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EmployeeIDLabel)
                    .addComponent(EmployeePositionLabel))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        getContentPane().add(HeaderPanel, java.awt.BorderLayout.PAGE_START);

        SalaryDetailsPanel.setPreferredSize(new java.awt.Dimension(1300, 277));

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel5.setText("Basic Salary :");

        BasicSalaryTextField.setEditable(false);
        BasicSalaryTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        NoPayDeductionsTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        NoPayDeductionsTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                NoPayDeductionsTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                NoPayDeductionsTextFieldFocusLost(evt);
            }
        });
        NoPayDeductionsTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                NoPayDeductionsTextFieldKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel6.setText("No Pay Deductions :");

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel7.setText("Other Deductions :");

        OtherDeductionsTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        OtherDeductionsTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                OtherDeductionsTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                OtherDeductionsTextFieldFocusLost(evt);
            }
        });
        OtherDeductionsTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                OtherDeductionsTextFieldKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel8.setText("Other Allowances :");

        OtherAllowancesTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        OtherAllowancesTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                OtherAllowancesTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                OtherAllowancesTextFieldFocusLost(evt);
            }
        });
        OtherAllowancesTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                OtherAllowancesTextFieldKeyReleased(evt);
            }
        });

        NetBonasTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        NetBonasTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                NetBonasTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                NetBonasTextFieldFocusLost(evt);
            }
        });
        NetBonasTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                NetBonasTextFieldKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel9.setText("Net(N/A) Bonas :");

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel10.setText("Owner Bonas :");

        OwnerBonasTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        OwnerBonasTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                OwnerBonasTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                OwnerBonasTextFieldFocusLost(evt);
            }
        });
        OwnerBonasTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                OwnerBonasTextFieldKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel11.setText("Advaced Payment :");

        AdvancedPaymentTextField.setEditable(false);
        AdvancedPaymentTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel12.setText("Net Salary :");

        NetSalaryTextField.setEditable(false);
        NetSalaryTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel13.setText("Sign-On Bonas :");

        SignOnBonusTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        SignOnBonusTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                SignOnBonusTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                SignOnBonusTextFieldFocusLost(evt);
            }
        });
        SignOnBonusTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SignOnBonusTextFieldKeyReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel14.setText("Performance Bonas :");

        PerformanceBonasTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        PerformanceBonasTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                PerformanceBonasTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                PerformanceBonasTextFieldFocusLost(evt);
            }
        });
        PerformanceBonasTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PerformanceBonasTextFieldKeyReleased(evt);
            }
        });

        HolidayBonasjTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        HolidayBonasjTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                HolidayBonasjTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                HolidayBonasjTextFieldFocusLost(evt);
            }
        });
        HolidayBonasjTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                HolidayBonasjTextFieldKeyReleased(evt);
            }
        });

        HolidayBonasTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        HolidayBonasTextField.setText("Holiday Bonas :");

        TeamBonusextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        TeamBonusextField.setText("Team Bonas :");

        PayableSalaryTextField.setEditable(false);
        PayableSalaryTextField.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel17.setText("Payable Salary :");

        TeamBonasTextField.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TeamBonasTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TeamBonasTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                TeamBonasTextFieldFocusLost(evt);
            }
        });
        TeamBonasTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TeamBonasTextFieldKeyReleased(evt);
            }
        });

        kButton1.setText("Genarate");
        kButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kButton1.setkEndColor(new java.awt.Color(0, 204, 204));
        kButton1.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        kButton1.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton1.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        kButton1.setkPressedColor(new java.awt.Color(0, 102, 153));
        kButton1.setkSelectedColor(new java.awt.Color(0, 102, 153));
        kButton1.setkStartColor(new java.awt.Color(0, 102, 153));
        kButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton1ActionPerformed(evt);
            }
        });

        kButton2.setText("Add");
        kButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        kButton2.setkEndColor(new java.awt.Color(0, 204, 204));
        kButton2.setkHoverEndColor(new java.awt.Color(0, 102, 153));
        kButton2.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton2.setkHoverStartColor(new java.awt.Color(0, 204, 204));
        kButton2.setkPressedColor(new java.awt.Color(0, 102, 153));
        kButton2.setkSelectedColor(new java.awt.Color(0, 102, 153));
        kButton2.setkStartColor(new java.awt.Color(0, 102, 153));
        kButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SalaryDetailsPanelLayout = new javax.swing.GroupLayout(SalaryDetailsPanel);
        SalaryDetailsPanel.setLayout(SalaryDetailsPanelLayout);
        SalaryDetailsPanelLayout.setHorizontalGroup(
            SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SalaryDetailsPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SalaryDetailsPanelLayout.createSequentialGroup()
                        .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BasicSalaryTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                            .addComponent(NoPayDeductionsTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)))
                    .addGroup(SalaryDetailsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(21, 21, 21)
                        .addComponent(OtherDeductionsTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)))
                .addGap(45, 45, 45)
                .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SalaryDetailsPanelLayout.createSequentialGroup()
                        .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel12)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(OwnerBonasTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                            .addComponent(NetBonasTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                            .addComponent(NetSalaryTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                            .addComponent(OtherAllowancesTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)))
                    .addGroup(SalaryDetailsPanelLayout.createSequentialGroup()
                        .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel17))
                        .addGap(13, 13, 13)
                        .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PayableSalaryTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(AdvancedPaymentTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE))))
                .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SalaryDetailsPanelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13)
                            .addComponent(HolidayBonasTextField)
                            .addComponent(TeamBonusextField))
                        .addGap(18, 18, 18)
                        .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PerformanceBonasTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TeamBonasTextField)
                            .addComponent(HolidayBonasjTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(SignOnBonusTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(SalaryDetailsPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(45, 45, 45))
        );
        SalaryDetailsPanelLayout.setVerticalGroup(
            SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SalaryDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SalaryDetailsPanelLayout.createSequentialGroup()
                        .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SalaryDetailsPanelLayout.createSequentialGroup()
                                .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(OtherAllowancesTextField)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(NetBonasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(SalaryDetailsPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(7, 7, 7)))
                                .addGap(12, 12, 12)
                                .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(OwnerBonasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(NetSalaryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(SalaryDetailsPanelLayout.createSequentialGroup()
                                .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(SalaryDetailsPanelLayout.createSequentialGroup()
                                        .addGap(88, 88, 88)
                                        .addComponent(HolidayBonasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(SalaryDetailsPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(11, 11, 11)
                                .addComponent(TeamBonusextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(SalaryDetailsPanelLayout.createSequentialGroup()
                                .addComponent(SignOnBonusTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(PerformanceBonasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(HolidayBonasjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(TeamBonasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)
                        .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(AdvancedPaymentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel17)
                                .addComponent(PayableSalaryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(SalaryDetailsPanelLayout.createSequentialGroup()
                        .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BasicSalaryTextField)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(NoPayDeductionsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(SalaryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(OtherDeductionsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Select Date");

        SearchPayIDTextField.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        SearchPayIDTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                SearchPayIDTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                SearchPayIDTextFieldFocusLost(evt);
            }
        });
        SearchPayIDTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SearchPayIDTextFieldKeyReleased(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PayID", "Employee ID", "Employee Name", "Month", "Date Issued", "Authorized By", "Payable Salary", "Basic Salary", "No Pay Deductions", "Other Deduction", "Other Allowances", "Owner Bonus", "Net Bonus", "Net Salary", "Advance Payment", "Sign On Bonus", "Performance Bonus", "Holiday Bonus", "Team Bonus"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        DatejLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout BodyPanelLayout = new javax.swing.GroupLayout(BodyPanel);
        BodyPanel.setLayout(BodyPanelLayout);
        BodyPanelLayout.setHorizontalGroup(
            BodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SalaryDetailsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addGroup(BodyPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(BodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BodyPanelLayout.createSequentialGroup()
                        .addComponent(SearchPayIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(BodyPanelLayout.createSequentialGroup()
                        .addGroup(BodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(BodyPanelLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(DatejLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(16, 16, 16))))
        );
        BodyPanelLayout.setVerticalGroup(
            BodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BodyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(DatejLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(SalaryDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SearchPayIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        getContentPane().add(BodyPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void kButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton2ActionPerformed

        try {

            int payrollID = generateRandomUniquePayrollID();
            String EmpName = EmployeeNameLabel.getText();
            String employeeId = EmployeeIDLabel.getText();

            // Parse inputs safely
            double basic = parseDoubleSafe(BasicSalaryTextField.getText());
            double noPay = parseDoubleSafe(NoPayDeductionsTextField.getText());
            double otherDeduction = parseDoubleSafe(OtherDeductionsTextField.getText());
            double otherAllowance = parseDoubleSafe(OtherAllowancesTextField.getText());
            double netBonus = parseDoubleSafe(NetBonasTextField.getText());
            double ownerBonus = parseDoubleSafe(OwnerBonasTextField.getText());
            double signOnBonus = parseDoubleSafe(SignOnBonusTextField.getText());
            double performanceBonus = parseDoubleSafe(PerformanceBonasTextField.getText());
            double holidayBonus = parseDoubleSafe(HolidayBonasjTextField.getText());
            double teamBonus = parseDoubleSafe(TeamBonasTextField.getText());
            double advancedPayment = parseDoubleSafe(AdvancedPaymentTextField.getText());

            // Calculations
            double epfEmployee = basic * 0.08;
            double totalBonus = netBonus + ownerBonus + signOnBonus + performanceBonus + holidayBonus + teamBonus;
            double totalDeduction = noPay + otherDeduction + epfEmployee;
            double netSalary = basic + otherAllowance + totalBonus - totalDeduction;
            double payableSalary = netSalary - advancedPayment;

            // Format issued date
            String rawDate = DatejLabel.getText().trim(); // e.g., "2025 - 05 - 17"
            String sqlIssuedDate = rawDate.replaceAll("\\s*-\\s*", "-");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDate = LocalDate.parse(sqlIssuedDate, formatter);
            String fullMonthName = parsedDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

            // Check if payroll already exists for this employee in the month
            String checkQuery = "SELECT COUNT(*) FROM employee_payrolls WHERE employee_employee_id = '" + employeeId + "' AND month = '" + fullMonthName + "'";

            ResultSet rs = MySql.executeSearch(checkQuery);

            if (rs.next() && rs.getInt(1) > 0) {

                JOptionPane.showMessageDialog(null, "Payroll already exists for this employee in the selected month.");
                return;

            }

            // Get basic salary ID
            int basicId = -1;

            ResultSet brs = MySql.executeSearch("SELECT basic_id FROM basic_salary WHERE employee_employee_id = '" + employeeId + "'");

            if (brs.next()) {

                basicId = brs.getInt("basic_id");

            }

            // Get latest advance ID (if any)
            Integer adPayId = null;

            ResultSet ars = MySql.executeSearch("SELECT ad_pay_id FROM advanced_payroll WHERE employee_employee_id = '" + employeeId + "' ORDER BY date DESC LIMIT 1");

            if (ars.next()) {

                adPayId = ars.getInt("ad_pay_id");

            }

            // Final INSERT
            String insertQuery = String.format(
                    "INSERT INTO employee_payrolls "
                    + "(payroll_id, employee_name, basic_salary_basic_id, no_pay_deduction, other_deduction, other_allowances, net_bonus, owner_bonus, "
                    + "sign_on_bonus, performance_bonus, holiday_bonus, team_bonus, net_salary, payable_salary, date_issued, employee_employee_id, month, advanced_payroll_ad_pay_id) "
                    + "VALUES (%d, '%s', %d, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, '%s', '%s', '%s', %s)",
                    payrollID, EmpName, basicId, noPay, otherDeduction, otherAllowance,
                    netBonus, ownerBonus, signOnBonus, performanceBonus, holidayBonus, teamBonus,
                    netSalary, payableSalary, sqlIssuedDate, employeeId, fullMonthName,
                    (adPayId != null ? adPayId.toString() : "NULL")
            );

            int result = MySql.executeUpdate(insertQuery);

            if (result > 0) {

                loadPayrollDataToTable();
                JOptionPane.showMessageDialog(null, "Payroll data saved successfully!");
                reset();

            } else {

                JOptionPane.showMessageDialog(null, "Failed to save payroll data.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }//GEN-LAST:event_kButton2ActionPerformed

    private void NoPayDeductionsTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPayDeductionsTextFieldKeyReleased
        calculateSalaries();
    }//GEN-LAST:event_NoPayDeductionsTextFieldKeyReleased

    private void OtherDeductionsTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OtherDeductionsTextFieldKeyReleased
        calculateSalaries();
    }//GEN-LAST:event_OtherDeductionsTextFieldKeyReleased

    private void OtherAllowancesTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OtherAllowancesTextFieldKeyReleased
        calculateSalaries();
    }//GEN-LAST:event_OtherAllowancesTextFieldKeyReleased

    private void NetBonasTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NetBonasTextFieldKeyReleased
        calculateSalaries();
    }//GEN-LAST:event_NetBonasTextFieldKeyReleased

    private void OwnerBonasTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OwnerBonasTextFieldKeyReleased
        calculateSalaries();
    }//GEN-LAST:event_OwnerBonasTextFieldKeyReleased

    private void SignOnBonusTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SignOnBonusTextFieldKeyReleased
        calculateSalaries();
    }//GEN-LAST:event_SignOnBonusTextFieldKeyReleased

    private void PerformanceBonasTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerformanceBonasTextFieldKeyReleased
        calculateSalaries();
    }//GEN-LAST:event_PerformanceBonasTextFieldKeyReleased

    private void HolidayBonasjTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HolidayBonasjTextFieldKeyReleased
        calculateSalaries();
    }//GEN-LAST:event_HolidayBonasjTextFieldKeyReleased

    private void TeamBonasTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TeamBonasTextFieldKeyReleased
        calculateSalaries();
    }//GEN-LAST:event_TeamBonasTextFieldKeyReleased

    private void NoPayDeductionsTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NoPayDeductionsTextFieldFocusGained

        if (NoPayDeductionsTextField.getText().equals("0.00")) {

            NoPayDeductionsTextField.setText("");

        }

    }//GEN-LAST:event_NoPayDeductionsTextFieldFocusGained

    private void NoPayDeductionsTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NoPayDeductionsTextFieldFocusLost

        if (NoPayDeductionsTextField.getText().equals("0.00")) {

            NoPayDeductionsTextField.setText("0.00");

        }

    }//GEN-LAST:event_NoPayDeductionsTextFieldFocusLost

    private void OtherDeductionsTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_OtherDeductionsTextFieldFocusGained

        if (OtherDeductionsTextField.getText().equals("0.00")) {

            OtherDeductionsTextField.setText("");

        }

    }//GEN-LAST:event_OtherDeductionsTextFieldFocusGained

    private void OtherDeductionsTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_OtherDeductionsTextFieldFocusLost

        if (OtherDeductionsTextField.getText().equals("0.00")) {

            OtherDeductionsTextField.setText("0.00");

        }

    }//GEN-LAST:event_OtherDeductionsTextFieldFocusLost

    private void OtherAllowancesTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_OtherAllowancesTextFieldFocusGained

        if (OtherAllowancesTextField.getText().equals("0.00")) {

            OtherAllowancesTextField.setText("");

        }

    }//GEN-LAST:event_OtherAllowancesTextFieldFocusGained

    private void OtherAllowancesTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_OtherAllowancesTextFieldFocusLost

        if (OtherAllowancesTextField.getText().equals("0.00")) {

            OtherAllowancesTextField.setText("0.00");

        }

    }//GEN-LAST:event_OtherAllowancesTextFieldFocusLost

    private void NetBonasTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NetBonasTextFieldFocusGained

        if (NetBonasTextField.getText().equals("0.00")) {

            NetBonasTextField.setText("");

        }

    }//GEN-LAST:event_NetBonasTextFieldFocusGained

    private void NetBonasTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NetBonasTextFieldFocusLost

        if (NetBonasTextField.getText().equals("0.00")) {

            NetBonasTextField.setText("0.00");

        }

    }//GEN-LAST:event_NetBonasTextFieldFocusLost

    private void OwnerBonasTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_OwnerBonasTextFieldFocusGained

        if (OwnerBonasTextField.getText().equals("0.00")) {

            OwnerBonasTextField.setText("");

        }

    }//GEN-LAST:event_OwnerBonasTextFieldFocusGained

    private void OwnerBonasTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_OwnerBonasTextFieldFocusLost

        if (OwnerBonasTextField.getText().equals("0.00")) {

            OwnerBonasTextField.setText("0.00");

        }

    }//GEN-LAST:event_OwnerBonasTextFieldFocusLost

    private void SignOnBonusTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SignOnBonusTextFieldFocusGained

        if (SignOnBonusTextField.getText().equals("0.00")) {

            SignOnBonusTextField.setText("");

        }

    }//GEN-LAST:event_SignOnBonusTextFieldFocusGained

    private void SignOnBonusTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SignOnBonusTextFieldFocusLost

        if (SignOnBonusTextField.getText().equals("0.00")) {

            SignOnBonusTextField.setText("0.00");

        }

    }//GEN-LAST:event_SignOnBonusTextFieldFocusLost

    private void PerformanceBonasTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PerformanceBonasTextFieldFocusGained

        if (PerformanceBonasTextField.getText().equals("0.00")) {

            PerformanceBonasTextField.setText("");

        }

    }//GEN-LAST:event_PerformanceBonasTextFieldFocusGained

    private void PerformanceBonasTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PerformanceBonasTextFieldFocusLost

        if (PerformanceBonasTextField.getText().equals("0.00")) {

            PerformanceBonasTextField.setText("0.00");

        }

    }//GEN-LAST:event_PerformanceBonasTextFieldFocusLost

    private void HolidayBonasjTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_HolidayBonasjTextFieldFocusGained

        if (HolidayBonasjTextField.getText().equals("0.00")) {

            HolidayBonasjTextField.setText("");

        }

    }//GEN-LAST:event_HolidayBonasjTextFieldFocusGained

    private void HolidayBonasjTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_HolidayBonasjTextFieldFocusLost

        if (HolidayBonasjTextField.getText().equals("0.00")) {

            HolidayBonasjTextField.setText("0.00");

        }

    }//GEN-LAST:event_HolidayBonasjTextFieldFocusLost

    private void TeamBonasTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TeamBonasTextFieldFocusGained

        if (TeamBonasTextField.getText().equals("0.00")) {

            TeamBonasTextField.setText("");

        }

    }//GEN-LAST:event_TeamBonasTextFieldFocusGained

    private void TeamBonasTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TeamBonasTextFieldFocusLost

        if (TeamBonasTextField.getText().equals("0.00")) {

            TeamBonasTextField.setText("0.00");

        }

    }//GEN-LAST:event_TeamBonasTextFieldFocusLost

    DefaultTableModel model;

    public void search(String orderid) {

        model = (DefaultTableModel) jTable1.getModel();

        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);

        jTable1.setRowSorter(tr);

        tr.setRowFilter(RowFilter.regexFilter(orderid, 0));

    }

    private void SearchPayIDTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchPayIDTextFieldKeyReleased

        String pay_ID = SearchPayIDTextField.getText();

        search(pay_ID);

    }//GEN-LAST:event_SearchPayIDTextFieldKeyReleased

    private void SearchPayIDTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchPayIDTextFieldFocusGained

        if (SearchPayIDTextField.getText().equals("Search PayID")) {

            SearchPayIDTextField.setText("");

        }

    }//GEN-LAST:event_SearchPayIDTextFieldFocusGained

    private void SearchPayIDTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_SearchPayIDTextFieldFocusLost

        if (SearchPayIDTextField.getText().equals("Search PayID")) {

            SearchPayIDTextField.setText("Search PayID");

        }

    }//GEN-LAST:event_SearchPayIDTextFieldFocusLost

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow >= 0) {
            String payID = jTable1.getValueAt(selectedRow, 0).toString(); // Assume PayID is in column 0
            loadPayrollDetails(payID);
        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void kButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton1ActionPerformed
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row from the table.");
            return;
        }

        try {
            // Path to the compiled Jasper report
            String reportPath = "src/report/Payroll_details.jasper";

            // Create parameters map (if needed)
            HashMap<String, Object> parameters = new HashMap<>();
            
            

            // Create data source from JTable model
            JRTableModelDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, dataSource);

            // Show the report in viewer
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating report: " + e.getMessage(), "Report Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_kButton1ActionPerformed

    private void loadPayrollDetails(String payID) {
        try {
            String query = "SELECT * FROM employee_payrolls "
                    + "INNER JOIN basic_salary ON basic_salary.basic_id = employee_payrolls.basic_salary_basic_id "
                    + "INNER JOIN advanced_payroll ON advanced_payroll.ad_pay_id = employee_payrolls.advanced_payroll_ad_pay_id WHERE payroll_id = '" + payID + "'";
            ResultSet rs = MySql.executeSearch(query);

            if (rs.next()) {
                BasicSalaryTextField.setText(rs.getString("basic_salary.basic"));
                NoPayDeductionsTextField.setText(rs.getString("no_pay_deduction"));
                OtherDeductionsTextField.setText(rs.getString("other_deduction"));
                OtherAllowancesTextField.setText(rs.getString("other_allowances"));
                NetBonasTextField.setText(rs.getString("net_bonus"));
                OwnerBonasTextField.setText(rs.getString("owner_bonus"));
                NetSalaryTextField.setText(rs.getString("net_salary"));
                AdvancedPaymentTextField.setText(rs.getString("advanced_payroll.advanced_price"));
                PayableSalaryTextField.setText(rs.getString("payable_salary"));
                SignOnBonusTextField.setText(rs.getString("sign_on_bonus"));
                PerformanceBonasTextField.setText(rs.getString("performance_bonus"));
                HolidayBonasjTextField.setText(rs.getString("holiday_bonus"));
                TeamBonasTextField.setText(rs.getString("team_bonus"));
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatMacLightLaf.setup();
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PayrollDetailes dialog = new PayrollDetailes(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField AdvancedPaymentTextField;
    private javax.swing.JTextField BasicSalaryTextField;
    private javax.swing.JPanel BodyPanel;
    private javax.swing.JLabel DatejLabel;
    private javax.swing.JLabel EmployeeDepartmentLabel;
    private javax.swing.JLabel EmployeeIDLabel;
    private javax.swing.JLabel EmployeeNameLabel;
    private javax.swing.JLabel EmployeePositionLabel;
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JLabel HolidayBonasTextField;
    private javax.swing.JTextField HolidayBonasjTextField;
    private javax.swing.JTextField NetBonasTextField;
    private javax.swing.JTextField NetSalaryTextField;
    private javax.swing.JTextField NoPayDeductionsTextField;
    private javax.swing.JTextField OtherAllowancesTextField;
    private javax.swing.JTextField OtherDeductionsTextField;
    private javax.swing.JTextField OwnerBonasTextField;
    private javax.swing.JTextField PayableSalaryTextField;
    private javax.swing.JTextField PerformanceBonasTextField;
    private javax.swing.JPanel SalaryDetailsPanel;
    private javax.swing.JTextField SearchPayIDTextField;
    private javax.swing.JTextField SignOnBonusTextField;
    private javax.swing.JTextField TeamBonasTextField;
    private javax.swing.JLabel TeamBonusextField;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private com.k33ptoo.components.KButton kButton1;
    private com.k33ptoo.components.KButton kButton2;
    // End of variables declaration//GEN-END:variables

    public void reset() {

        AdvancedPaymentTextField.setText("0.00");
        NoPayDeductionsTextField.setText("0.00");
        OtherDeductionsTextField.setText("0.00");
        OtherAllowancesTextField.setText("0.00");
        NetBonasTextField.setText("0.00");
        OwnerBonasTextField.setText("0.00");
        SignOnBonusTextField.setText("0.00");
        PerformanceBonasTextField.setText("0.00");
        HolidayBonasjTextField.setText("0.00");
        TeamBonasTextField.setText("0.00");
        PayableSalaryTextField.setText(NetSalaryTextField.getText());

    }

    // Placeholders add method
    private void addPlaceholder() {

        NoPayDeductionsTextField.setText("0.00");
        NoPayDeductionsTextField.setForeground(Color.GRAY);

        OtherDeductionsTextField.setText("0.00");
        OtherDeductionsTextField.setForeground(Color.GRAY);

        OtherAllowancesTextField.setText("0.00");
        OtherAllowancesTextField.setForeground(Color.GRAY);

        NetBonasTextField.setText("0.00");
        NetBonasTextField.setForeground(Color.GRAY);

        OwnerBonasTextField.setText("0.00");
        OwnerBonasTextField.setForeground(Color.GRAY);

        SignOnBonusTextField.setText("0.00");
        SignOnBonusTextField.setForeground(Color.GRAY);

        PerformanceBonasTextField.setText("0.00");
        PerformanceBonasTextField.setForeground(Color.GRAY);

        HolidayBonasjTextField.setText("0.00");
        HolidayBonasjTextField.setForeground(Color.GRAY);

        TeamBonasTextField.setText("0.00");
        TeamBonasTextField.setForeground(Color.GRAY);

        SearchPayIDTextField.setText("Search PayID");
        SearchPayIDTextField.setForeground(Color.GRAY);

    }

    public static int generateRandomUniquePayrollID() {

        Random random = new Random();
        int randomID;

        while (true) {

            // generate a 6-digit random number (100000–999999)
            randomID = 100000 + random.nextInt(900000);

            try {

                String query = "SELECT payroll_id FROM payroll_int_hr_details WHERE payroll_id = '" + randomID + "'";
                ResultSet rs = MySql.executeSearch(query);

                if (!rs.next()) {

                    break;

                }

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

        return randomID;

    }

}
