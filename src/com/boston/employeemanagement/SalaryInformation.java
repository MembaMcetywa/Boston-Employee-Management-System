/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boston.employeemanagement;

import java.text.SimpleDateFormat;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mthunzi
 */
public class SalaryInformation extends javax.swing.JFrame {

    /**
     * Creates new form SalaryInformation
     */
    public SalaryInformation() {
        initComponents();
        //employee id autoincrements in the database so there is no need to add employee id because its automatically there.
        txtEmployeeID.setEnabled(false);
        tblSalary.setEnabled(false);
        mLoadDataToTable();
        
    }
    String strFirstName, strLastName, strDateOfBirth, strDepartment, strTotalAmount;
    double dblBasicSalary, dblOvertime, dblMedical, dblBonus, dblOther, dblTotalOvertime, dblRatePerHour;
    Boolean boolCreate = false;
    Boolean boolCheckIfExist = false;
    int EmployeeID;
    
    private void mGetValues()
    {
        strFirstName = txtFName.getText();
        strLastName = txtLName.getText();
        strDateOfBirth = txtDateOB.getText();
        dblBasicSalary = Double.parseDouble(txtSalary.getText());
        strDepartment = txtDep.getText();
        strTotalAmount = txtTotalAmnt.getText();
        dblOvertime = Double.parseDouble(txtOvertime.getText());
        dblMedical = Double.parseDouble(txtMedical.getText());
        dblBonus = Double.parseDouble(txtBonus.getText());
        dblOther = Double.parseDouble(txtOther.getText());
        dblTotalOvertime = Double.parseDouble(txtTotalOvertime.getText());
        dblRatePerHour = Double.parseDouble(txtRate.getText());
    }
    /*
    Since i opted for use of a textbox instead of a label
    to display the final salary ,this method will 
    output the result i get from the salary calculation
    */
    private void mSetText()
    {
        txtTotalAmnt.setText(strTotalAmount);
    }
    
    private void mSetValuesToUpper()
    {
        strFirstName = strFirstName.toUpperCase();
        strLastName = strLastName.toUpperCase();
        strDepartment = strDepartment.toUpperCase();
    }
    private void mClearFields()
    {
        txtFName.setText("");
        txtLName.setText("");
        txtDateOB.setText("");
        txtSalary.setText("0");
        txtDep.setText("");
        txtOvertime.setText("0");
        txtMedical.setText("0");
        txtBonus.setText("0");
        txtOther.setText("0");
        txtTotalOvertime.setText("0");
        txtRate.setText("0");
    }
    
    private void mClearCalcFields()
    {
        txtOvertime.setText("0");
        txtMedical.setText("0");
        txtBonus.setText("0");
        txtOther.setText("0");
        txtTotalOvertime.setText("0");
        txtRate.setText("0");
    }
    private void mClearCalcVar()
    {
        dblOvertime = 0;
        dblMedical = 0;
        dblBonus = 0;
        dblOther = 0;
        dblTotalOvertime = 0;
        dblRatePerHour = 0;
    }
    
    private void mClearVariables()
    {
        strFirstName = "";
        strLastName = "";
        strDateOfBirth = "";
        dblBasicSalary = 0;
        strDepartment = "";
        dblOvertime = 0;
        dblMedical = 0;
        dblBonus = 0;
        dblOther = 0;
        dblTotalOvertime = 0;
        dblRatePerHour = 0;
        strTotalAmount = "";
    }
    private void mDateOfBirth()
    {
        java.util.Date dt = new java.util.Date();
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        txtDateOB.setText(sm.format(dt));
    }
    private void mCreateEmployee()
    {
        String strDBConnectionString = null;
        String URL = "jdbc:mysql://localhost:3306/dbsEmployee";
        String strDBUser = "root";
        String strDBPassword = "Password";
        java.sql.Connection conMySQLConnectionString;
        Statement stStatement = null;
        ResultSet rs = null;
        try 
        {
            conMySQLConnectionString = DriverManager.getConnection(URL, strDBUser, strDBPassword);
            stStatement = conMySQLConnectionString.createStatement();
            String strQuery = "INSERT INTO tblSalaryInfo (FirstName, LastName, DateOfBirth, BasicSalary, Department, TotalAmount)"
                    + "VALUES ('"+strFirstName+"', '"+strLastName+"', '"+strDateOfBirth+"', '"+dblBasicSalary+"', '"+strDepartment+"', '"+strTotalAmount+"')";
                   stStatement.executeUpdate(strQuery);
            stStatement.close();
            JOptionPane.showMessageDialog(null, "Employee Salary Information Saved!");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Employee Salary Could Not Be Saved!" + " " + e);
        }
        finally 
        {
            try
            {
                stStatement.close();
            }
            catch (Exception e)
            {
               JOptionPane.showMessageDialog(null, "String Connection Could Not Be Closed!" + " " + e);
            }
        }
    }
    
    private void mCheckIfExists()
    {
        String strDBConnectionString = "jdbc:mysql://localhost:3306/dbsEmployee";
        String strDBUser = "root";
        String strDBPassword = "Password";
        java.sql.Connection conMySQLConnectionString;
        Statement stStatement = null;
        ResultSet rs = null;
        try 
        {
            conMySQLConnectionString = DriverManager.getConnection(strDBConnectionString, strDBUser, strDBPassword);
            stStatement = conMySQLConnectionString.createStatement();
            String strQuery = "Select * FROM tblSalaryInfo WHERE FirstName = '"+strFirstName+"' AND LastName = '"+strLastName+"'";
            stStatement.execute(strQuery);
            rs = stStatement.getResultSet();
            boolCheckIfExist = rs.next();
            
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        finally
        {
            try 
            {
                stStatement.close();
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, "Connection could not be closed" + " " + e);
            }
        }
    }
    
    private String mCalculate()
    {
       /*
        In this method i chose to make the return type a string for ease of displaying in the textbox
        I took the basic salary as the focal point of the equation
        */
        strTotalAmount = Double.toString(dblBasicSalary - (dblMedical + dblOther) + (dblRatePerHour * dblTotalOvertime) + dblBonus);
        return strTotalAmount;
    }
    
    /*
    this method is meant to load information from the database to the table 
    in the gui. This method will be automatically called
    when the window is opened as it will be placed in the constructor.
    */
    private void mLoadDataToTable()
    {
        String strDBConnectionString = null;
        String URL = "jdbc:mysql://localhost:3306/dbsEmployee";
        String strDBUser = "root";
        String strDBPassword = "Password";
        java.sql.Connection conMySQLConnectionString;
        Statement stStatement = null;
        ResultSet rs = null;
        try
        {
            conMySQLConnectionString = DriverManager.getConnection(URL, strDBUser, strDBPassword);
            stStatement = conMySQLConnectionString.createStatement();
            String strQuery = "SELECT * FROM tblSalaryInfo";
            stStatement.execute(strQuery);
            DefaultTableModel tblS = (DefaultTableModel)tblSalary.getModel();
            rs = stStatement.getResultSet();
            
            
            while(rs.next())
            {
                Object o[] = {rs.getString("FirstName"), rs.getString("LastName"), rs.getString("BasicSalary"), rs.getString("TotalAmount")};
                tblS.addRow(o);
                
            }
            stStatement.close();
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        finally 
        {
            try
            {
                stStatement.close();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "String Connection Could Not Be Closed" + " " + e);
            }
        }
     
    }
    /*This method will kill the open window and recall an updated one
        useful when users want to see updated information before moving on
    */
    private void mRefresh()
   {
       this.dispose();
       SalaryInformation Salary = new SalaryInformation();
       Salary.setVisible(true);
   }
   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtEmployeeID = new javax.swing.JTextField();
        txtFName = new javax.swing.JTextField();
        txtLName = new javax.swing.JTextField();
        txtDateOB = new javax.swing.JTextField();
        txtSalary = new javax.swing.JTextField();
        txtDep = new javax.swing.JTextField();
        txtOvertime = new javax.swing.JTextField();
        txtMedical = new javax.swing.JTextField();
        txtBonus = new javax.swing.JTextField();
        txtOther = new javax.swing.JTextField();
        txtTotalOvertime = new javax.swing.JTextField();
        txtRate = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSalary = new javax.swing.JTable();
        btnSave = new javax.swing.JButton();
        btnCalculate = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txtTotalAmnt = new javax.swing.JTextField();
        btnReturn = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Berlin Sans FB", 1, 20)); // NOI18N
        jLabel1.setText("Please enter the amounts");

        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel3.setText("Last Name");

        jLabel4.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel4.setText("EmployeeID");

        jLabel5.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel5.setText("First Name");

        jLabel6.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel6.setText("Date of Birth");

        jLabel7.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel7.setText("Basic Salary");

        jLabel8.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel8.setText("Deparment");

        jLabel9.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel9.setText("Medical");

        jLabel10.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel10.setText("Bonus");

        jLabel11.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel11.setText("Other");
        jLabel11.setToolTipText("");

        jLabel12.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel12.setText("Rate Per Hour");

        jLabel13.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel13.setText("Total Overtime");

        jLabel14.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel14.setText("Overtime");

        txtEmployeeID.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        txtFName.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        txtLName.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtLName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLNameActionPerformed(evt);
            }
        });

        txtDateOB.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        txtSalary.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtSalary.setText("0");
        txtSalary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSalaryActionPerformed(evt);
            }
        });

        txtDep.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDepActionPerformed(evt);
            }
        });

        txtOvertime.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtOvertime.setText("0");

        txtMedical.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtMedical.setText("0");

        txtBonus.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtBonus.setText("0");
        txtBonus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBonusActionPerformed(evt);
            }
        });

        txtOther.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtOther.setText("0");

        txtTotalOvertime.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtTotalOvertime.setText("0");
        txtTotalOvertime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalOvertimeActionPerformed(evt);
            }
        });

        txtRate.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtRate.setText("0");

        tblSalary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "First Name", "Last Name", "Basic Salary (R)", "Total Amount (R)"
            }
        ));
        jScrollPane1.setViewportView(tblSalary);

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCalculate.setText("Calculate");
        btnCalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalculateActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel15.setText("Total Amount");

        txtTotalAmnt.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtTotalAmnt.setText("0");

        btnReturn.setText("Return");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        jLabel16.setText("R");

        jLabel17.setText("R");

        jLabel18.setText("R");

        jLabel19.setText("R");

        jLabel20.setText("R");

        jLabel21.setText("R");

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btnReturn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtDateOB, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDep, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtEmployeeID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtFName, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                        .addComponent(txtLName))
                                    .addComponent(txtSalary, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(128, 128, 128)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel19)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMedical, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtOvertime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtOther, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBonus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(130, 130, 130)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTotalOvertime, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel21)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTotalAmnt, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(btnRefresh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnCalculate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnSave, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel1)
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnReturn)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtEmployeeID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtFName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(txtOvertime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txtMedical, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtTotalOvertime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtDateOB, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtBonus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtOther, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtTotalAmnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addGap(18, 18, 18)
                        .addComponent(btnSave)
                        .addGap(12, 12, 12)
                        .addComponent(btnCalculate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnClear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRefresh)))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSalaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSalaryActionPerformed

    private void txtDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDepActionPerformed

    private void txtLNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLNameActionPerformed

    private void txtTotalOvertimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalOvertimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalOvertimeActionPerformed

    private void btnCalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalculateActionPerformed
        // TODO add your handling code here:
        mGetValues();
        mCalculate();
        mSetText();
        mClearCalcFields();
        mClearCalcVar();
        
    }//GEN-LAST:event_btnCalculateActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        mClearFields();
        mClearVariables();
    }//GEN-LAST:event_btnClearActionPerformed

    private void txtBonusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBonusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBonusActionPerformed

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        // TODO add your handling code here:
        this.hide();
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        mCreateEmployee();
       
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        mRefresh();
    }//GEN-LAST:event_btnRefreshActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SalaryInformation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalaryInformation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalaryInformation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalaryInformation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SalaryInformation().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalculate;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblSalary;
    private javax.swing.JTextField txtBonus;
    private javax.swing.JTextField txtDateOB;
    private javax.swing.JTextField txtDep;
    private javax.swing.JTextField txtEmployeeID;
    private javax.swing.JTextField txtFName;
    private javax.swing.JTextField txtLName;
    private javax.swing.JTextField txtMedical;
    private javax.swing.JTextField txtOther;
    private javax.swing.JTextField txtOvertime;
    private javax.swing.JTextField txtRate;
    private javax.swing.JTextField txtSalary;
    private javax.swing.JTextField txtTotalAmnt;
    private javax.swing.JTextField txtTotalOvertime;
    // End of variables declaration//GEN-END:variables
}
