/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boston.employeemanagement;

import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Mthunzi
 */
public class EmployeeInformation extends javax.swing.JFrame {

    /**
     * Creates new form EmployeeInformation
     */
    public EmployeeInformation() {
        initComponents();
        mLoadEmployeeNames();
        mDateOfBirth();
        tblEmployees.setEnabled(false);
        mLoadDataToTable();
        
       
    }
        
    String strFirstName, strLastName, strJobTitle, strDepartment, strDoB, strPhoneNumber, strUserName, strPASSWORD;
    boolean boolCheckIfExists = false;
    boolean boolEdit = false;
    boolean boolCreate = false;
    int intEmployeeID;
    
    private void mDateOfBirth()
    {
        java.util.Date dt = new java.util.Date();
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        txtDoB.setText(sm.format(dt));
    }
            
    public void mClearVariables()
    {
        strFirstName = "";
        strLastName = "";
        strJobTitle = "";
        strDepartment = "";
        strDoB = "";
        strPhoneNumber = "";
        strUserName = "";
        strPASSWORD = "";
        intEmployeeID = 0;
        cboEmployeeName.setSelectedIndex(0);
    }
            
    public void mGetValues()
    {
        strFirstName = txtFirstName.getText();
        strLastName = txtLastName.getText();
        strJobTitle = txtJobTitle.getText();
        strDepartment = txtDepartment.getText();
        strDoB = txtDoB.getText();
        strPhoneNumber = txtPhoneNumber.getText();
        strUserName = txtUserName.getText();
        strPASSWORD = txtPassword.getText();
    }
    
    public void mSetValuesToUppercase()
    {
        strFirstName = strFirstName.toUpperCase();
        strLastName = strLastName.toUpperCase();
        strJobTitle = strJobTitle.toUpperCase();
        strDepartment = strDepartment.toUpperCase();
        strUserName = strUserName.toUpperCase();
    }
    
    public void mSetValues()
    {
        txtFirstName.setText(strFirstName);
        txtLastName.setText(strLastName);
        txtJobTitle.setText(strJobTitle);
        txtDepartment.setText(strDepartment);
        txtDoB.setText(strDoB);
        txtPhoneNumber.setText(strPhoneNumber); 
        txtUserName.setText(strUserName);
        txtPassword.setText(strPASSWORD);
    }
    
    public void mClearFields()
    {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtJobTitle.setText("");
        txtDepartment.setText("");
        txtDoB.setText("");
        txtPhoneNumber.setText("");
        txtUserName.setText("");
        txtPassword.setText("");
        cboEmployeeName.setSelectedIndex(0);
    }
    
    
     /*This method is to first check if the account being added is unique
      if it is then the account will be added 
      if it isnt then a dialog box will alert the user that the account already exists 
    */
    private void mCheckIfUserExists()
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
            String strQuery = "Select * FROM tblEmployeeInfo WHERE FirstName = '"+strFirstName+"' AND LastName = '"+strLastName+"'";
            stStatement.execute(strQuery);
            rs = stStatement.getResultSet();
            boolCheckIfExists = rs.next();
            
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
    
    /*
    Given that the employee doesn't already exist in the database
    the following method will then attempt to add the employee to the database
    used try-catch-finally block to attempt to catch any exceptions
    */
    
    private void mCreatteEmployee()
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
            String sqlinsert = "INSERT INTO tblEmployeeInfo (FirstName,LastName,JobTitle,Deparment,DateOfBirth,PhoneNumber,UserName,password)"
                    + "VALUES('"+strFirstName+"' , '"+strLastName+"', '"+strJobTitle+"', '"+strDepartment+"', '"+strDoB+"', '"+strPhoneNumber+"', '"+strUserName+"','"+strPASSWORD+"')";
            stStatement.executeUpdate(sqlinsert);
            stStatement.close();
            JOptionPane.showMessageDialog(null, "Employee Added!");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Employee Could Not Be Addded!" + " " + e);
        }
    }
    
    
    /*
      This method will get information of a particular row
      Rows are identified by the employee name in the combo box
      When this method runs it is supposed to get employee information
      using the while loop.
    */
    public void mReadEmployee()
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
            String strQuery = "SELECT * FROM tblEmployeeInfo WHERE FirstName = '"+cboEmployeeName.getSelectedItem()+"'";
            stStatement.execute(strQuery);
            rs = stStatement.getResultSet();
            JOptionPane.showMessageDialog(null, "HERE0");
             while (rs.next())
            {
                JOptionPane.showMessageDialog(null, "HERE1");
                intEmployeeID = rs.getInt(1);
                strFirstName = rs.getString(2);
                strLastName = rs.getString(3);
                strJobTitle = rs.getString(4);
                strDepartment = rs.getString(5);
                strDoB = rs.getString(6);
                strPhoneNumber = rs.getString(7);
                strUserName = rs.getString(8);
                strPASSWORD = rs.getString(9);
                
               // Object o[] = {rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Deparment"), rs.getString("DateOfBirth"), rs.getString("PhoneNumber")};
                    
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
                JOptionPane.showMessageDialog(null, "String Connection Not Closed!" + " " + e);
            }
        }
        
    }
    
    //method to load employee names to combo box
    public void mLoadEmployeeNames()
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
            String strQuery = "SELECT FirstName FROM tblEmployeeInfo";
            stStatement.execute(strQuery);
            rs = stStatement.getResultSet();
            while(rs.next())
            {
                cboEmployeeName.addItem(rs.getString(1));
                
            }
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
    
    
    //method to update a record in the database
    private void mUpdateEmployee()
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
            String strQuery = "UPDATE tblEmployeeInfo SET FirstName ='"+strFirstName+"', LastName = '"+strLastName+"', JobTitle = '"+strJobTitle+"', Deparment = '"+strDepartment+"', DateOfBirth = '"+strDoB+"', PhoneNumber = '"+strPhoneNumber+"', UserName = '"+strUserName+"', password = '"+strPASSWORD+"' "
                    + "WHERE EmployeeID = '"+intEmployeeID+"'";
            stStatement.executeUpdate(strQuery);
            stStatement.close();
            JOptionPane.showMessageDialog(null, "Employee Details Updated!");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Employee Details Could Not Be Updated!" + " " + e);
        }
        finally 
        {
            try
            {
                stStatement.close();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "Connection String Could Not Be Closed" + " " + e);
            }
        }
    
    }
    
    
    //method to delete a single record in the database
    private void mDeleteEmployee()
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
            String strQuery = "DELETE FROM tblEmployeeInfo WHERE FirstName='"+cboEmployeeName.getSelectedItem()+"'";
            stStatement.executeUpdate(strQuery);
            
            stStatement.close();
            JOptionPane.showMessageDialog(null, "Employee Details Deleted!");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Employee Details Could Not Be Deleted!" + " " + e);
        }
        finally 
        {
            try
            {
                stStatement.close();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "Connection String Could Not Be Closed" + " " + e);
            }
        }

        
    }
    //Method to delete all records in the database
    private void mDeleteAllEmployees()
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
            String strQuery = "DELETE FROM tblEmployeeInfo";
            stStatement.executeUpdate(strQuery);
            rs = stStatement.getResultSet();
            cboEmployeeName.removeAllItems();
            stStatement.close();
            JOptionPane.showMessageDialog(null, "All Employee Have Been Deleted!");
            
            
           
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Employees Could Not Be Deleted!" + " " + e);
        }
        finally 
        {
            try
            {
                stStatement.close();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "Connection String Could Not Be Closed" + " " + e);
            }
        }

        
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
            String strQuery = "SELECT * FROM tblEmployeeInfo";
            stStatement.execute(strQuery);
            DefaultTableModel tblM = (DefaultTableModel)tblEmployees.getModel();
            rs = stStatement.getResultSet();
            
            
            while(rs.next())
            {
                Object o[] = {rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Deparment"), rs.getString("DateOfBirth"), rs.getString("PhoneNumber")};
                tblM.addRow(o);
                
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
       EmployeeInformation Employee = new EmployeeInformation();
       Employee.setVisible(true);
   }
    
    
    
    
    
    
    
   
 
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        txtJobTitle = new javax.swing.JTextField();
        txtUserName = new javax.swing.JTextField();
        txtDoB = new javax.swing.JTextField();
        txtPhoneNumber = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmployees = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnDelAll = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        txtDepartment = new javax.swing.JTextField();
        btnReturn = new javax.swing.JButton();
        cboEmployeeName = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Berlin Sans FB", 1, 24)); // NOI18N
        jLabel1.setText("Employee Management System: Admin Portal");

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel2.setText("First Name");

        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel3.setText("Last Name");

        jLabel4.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel4.setText("Job Title");

        jLabel5.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel5.setText("Date of Birth");

        jLabel6.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel6.setText("Phone Number");

        jLabel7.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel7.setText("User Name");

        jLabel8.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel8.setText("Password");

        jLabel9.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel9.setText("Department");

        txtFirstName.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtFirstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFirstNameActionPerformed(evt);
            }
        });

        txtLastName.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        txtJobTitle.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        txtUserName.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        txtDoB.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        txtPhoneNumber.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        txtPassword.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        tblEmployees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "First Name", "Last Name", "Department", "DoB", "Phone Number"
            }
        ));
        jScrollPane1.setViewportView(tblEmployees);

        jLabel10.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel10.setText("Commands");

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnDelAll.setText("Delete All");
        btnDelAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelAllActionPerformed(evt);
            }
        });

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        txtDepartment.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        btnReturn.setText("Return");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        cboEmployeeName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Employee" }));
        cboEmployeeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEmployeeNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(159, 159, 159))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addGap(86, 86, 86)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboEmployeeName, 0, 173, Short.MAX_VALUE)
                            .addComponent(txtJobTitle)
                            .addComponent(txtLastName)
                            .addComponent(txtFirstName)
                            .addComponent(txtDoB)
                            .addComponent(txtPhoneNumber)
                            .addComponent(txtUserName)
                            .addComponent(txtPassword)
                            .addComponent(txtDepartment, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
                        .addGap(77, 77, 77)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(454, 454, 454)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDelAll)
                .addGap(18, 18, 18)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(btnReturn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtJobTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtDoB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRefresh)
                    .addComponent(btnCancel)
                    .addComponent(btnDelAll)
                    .addComponent(btnDelete)
                    .addComponent(btnUpdate)
                    .addComponent(btnAdd))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFirstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFirstNameActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        mReadEmployee();
        mDeleteEmployee();
        mClearFields();
        mClearVariables();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        //EmployeeID, FirstName, LastName, JobTitle, Deparment, DateOfBirth, PhoneNumber, UserName, password
        
       /*if (boolCreate = true)
       {
            if (txtFirstName.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "First Name Cannot Be Empty!");
                txtFirstName.requestFocusInWindow();
            }
            else if (txtLastName.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Last Name Cannot Be Left Empty!");
                txtLastName.requestFocusInWindow();
            }
            else if (txtJobTitle.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"Job Title Cannot Be Left Empty!");
                txtJobTitle.requestFocusInWindow();
            }
            else if (txtDepartment.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Department Cannot Be Left Empty!");
                txtDepartment.requestFocusInWindow();
            }
            else if (txtDoB.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Date of Birth Cannot Be Left Empty!");
                txtDepartment.requestFocusInWindow();
            }
            else if(txtPhoneNumber.getText().length() != 10)
            {
                
                JOptionPane.showMessageDialog(null, "Phone Number Must Be 10 Digits!");
                txtPhoneNumber.requestFocusInWindow();
            }
            
            else if (txtUserName.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Username Cannot Be Left Empty!");
                txtUserName.requestFocusInWindow();

            }
            /*else if (txtPassword.getPassword().length == 0);
            {
                JOptionPane.showMessageDialog(null, "Password Cannot Be Left Empty!");
                txtPassword.requestFocusInWindow();
            }*/
        
  
    
       mGetValues();
       mSetValuesToUppercase();
       mCheckIfUserExists();
       mCreatteEmployee();
       mClearVariables();
       mClearFields();
       mDateOfBirth();
       
       
    }//GEN-LAST:event_btnAddActionPerformed
       

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        // TODO add your handling code here:
        this.hide();
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        mGetValues();
        mSetValuesToUppercase();
        mUpdateEmployee();
        mClearFields();
        mClearVariables();
        mLoadEmployeeNames();
        
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        mRefresh();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        mClearVariables();
        mClearFields();
        mDateOfBirth();
        
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnDelAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelAllActionPerformed
        // TODO add your handling code here:
        mDeleteAllEmployees();
                   

    }//GEN-LAST:event_btnDelAllActionPerformed

    private void cboEmployeeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEmployeeNameActionPerformed
        // TODO add your handling code here:
        mReadEmployee();
        mSetValues();
    }//GEN-LAST:event_cboEmployeeNameActionPerformed

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
            java.util.logging.Logger.getLogger(EmployeeInformation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeInformation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeInformation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeInformation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeInformation().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelAll;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboEmployeeName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblEmployees;
    private javax.swing.JTextField txtDepartment;
    private javax.swing.JTextField txtDoB;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtJobTitle;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPhoneNumber;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
