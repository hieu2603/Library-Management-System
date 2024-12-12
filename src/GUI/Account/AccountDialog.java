/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUI.Account;


import BUS.AccountBUS;
import BUS.PermissionBUS;
import BUS.StaffBUS;
import DTO.AccountDTO;
import DTO.PermissionDTO;
import DTO.SessionManager;
import DTO.StaffDTO;
import GUI.Permission.GetPermissionDialog;
import GUI.Staff.GetStaffDialog;
import config.Constants;
import helper.Validator;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import javax.swing.JOptionPane;


/**
 *
 * @author Duc3m
 */
public class AccountDialog extends javax.swing.JDialog {
    
    AccountDTO account;
    String mode;
    int functionId = Constants.functions.get("Quản lý tài khoản");
    
    AccountBUS accountBUS = new AccountBUS();
    StaffBUS staffBUS = new StaffBUS();
    PermissionBUS permissionBUS = new PermissionBUS();
    
    StaffDTO staff;
    PermissionDTO permission;

    public AccountDialog(java.awt.Frame parent, boolean modal, AccountDTO account, String mode) {
        super(parent, modal);
        this.account = account;
        this.mode = mode;
        initComponents();
        customInit();
    }
    
    public void customInit() {
        setLocationRelativeTo(null);
        
        cbx_status.addItem(Constants.account_status[0]);
        if(!mode.equals("add"))
            for(int i=1; i<Constants.account_status.length; i++)
                cbx_status.addItem(Constants.account_status[i]);
        
        btn_save.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(mode.equals("view"))
                    updateEvent();
                if(mode.equals("add"))
                    addEvent();
            }
        });
        
        btn_edit.addActionListener((ActionEvent e) -> {
            enableForm();
        });
        
        btn_exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
            }
        });
        
        btn_staff.addActionListener((ActionEvent e) -> {
            staff = GetStaffDialog.getStaff();
            if(staff == null)
                return;
            if(accountBUS.checkStaffExisted(staff.getId())) {
                JOptionPane.showMessageDialog(null, "Nhân viên này đã có tài khoản");
                return;
            }
            txt_staff.setText(staff.getFullName());
        });
        
        btn_permission.addActionListener((ActionEvent e) -> {
            permission = GetPermissionDialog.getPermission();
            if(permission == null)
                return;
            txt_permission.setText(permission.getName());
        });
        
        if(mode.equals("view"))
            initViewMode();
        if(mode.equals("add"))
            initAddMode();
    }
    
    public void initViewMode() {
        txt_id.setText(account.getId() + "");
        txt_username.setText(account.getUsername());
        txt_password.setText(account.getPassword());
        txt_staff.setText(staffBUS.getById(account.getStaff_id()).getFullName());
        txt_permission.setText(permissionBUS.getById(account.getPermission_id()).getName());
        cbx_status.setSelectedItem(account.getStatus());
        
        txt_username.setFocusable(false);
        txt_password.setEnabled(false);
        btn_staff.setEnabled(false);
        btn_permission.setEnabled(false);
        cbx_status.setEnabled(false);
        
        staff = staffBUS.getById(account.getStaff_id());
        permission = permissionBUS.getById(account.getPermission_id());
        
        if(!SessionManager.getInstance().permissionCheck(functionId, "edit"))
            btn_edit.setEnabled(false);

    }

    public void initAddMode() {
        jLabel1.setText("TẠO TÀI KHOẢN");
        btn_save.setText("Thêm");
        //Giấu nút sửa
        btn_edit.setEnabled(false);
        btn_edit.setText("");
        btn_edit.setBorder(null);
        btn_edit.setBackground(new Color(240, 240, 240));
    }
    
    public void enableForm() {
        txt_username.setFocusable(true);
        txt_password.setEnabled(true);
        btn_staff.setEnabled(true);
        btn_permission.setEnabled(true);
        cbx_status.setEnabled(true);
        btn_edit.setEnabled(false);
    }
    
    public void editAccount() {
        account.setUsername(txt_username.getText());
        account.setPassword(String.valueOf(txt_password.getPassword()));
        account.setPermission_id(permission.getId());
        account.setStaff_id(staff.getId());
        account.setStatus(cbx_status.getSelectedItem().toString());
    }
    
    public void updateEvent() {
        editAccount();
        if(accountBUS.update(account)) {
            JOptionPane.showMessageDialog(null, "Lưu thông tin tài khoản thành công");
            dispose();
        }
    }
    
    public boolean validateInputs() {
        if(Validator.isEmpty(txt_username.getText())) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên đăng nhập");
            return false;
        }        
        
        if(Validator.isEmpty(txt_password.getText())) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mật khảu");
            return false;
        }
        
        if(Validator.isEmpty(txt_staff.getText())) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn nhân viên");
            return false;
        }
        
        if(Validator.isEmpty(txt_permission.getText())) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn phân quyền");
            return false;
        }
        
        if(!Validator.isWithinLength(txt_username.getText(), 20)) {
            JOptionPane.showMessageDialog(this, "Bạn không được nhập quá 20 kí tự");
            return false;
        }
        
        if(!Validator.isWithinLength(txt_password.getText(), 20)) {
            JOptionPane.showMessageDialog(this, "Bạn không được nhập quá 20 kí tự");
            return false;
        }
        
        if(accountBUS.isUsernameDuplicate(txt_username.getText())){
            JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại");
            return false;
        }
        
        if(Validator.isValidPassword(txt_password.getText())) {
            JOptionPane.showMessageDialog(this, "Bạn phải nhập mật khẩu ít nhất 8 ký tự, gồm ít nhất 1 chữ cái, 1 số, 1 ký tự đặc biệt");
            return false;
        }
        
        return true;
    }
    
    public AccountDTO getNewAccount() {
        String username = txt_username.getText();
        String password= String.valueOf(txt_password.getPassword());
        int permission_id = permission.getId();
        String status = cbx_status.getSelectedItem().toString();
        int staff_id = staff.getId();
        return new AccountDTO(username, password, permission_id, status, staff_id);
    }
    
    public void addEvent() {
        if(!validateInputs()){
            return;
        }
        account = getNewAccount();
        if(accountBUS.createAccount(account)) {
            JOptionPane.showMessageDialog(null, "Thêm tài khoản mới thành công");
            dispose();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lbl_username = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        lbl_password = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        lbl_id = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        lbl_staff = new javax.swing.JLabel();
        txt_staff = new javax.swing.JTextField();
        lbl_permission = new javax.swing.JLabel();
        txt_permission = new javax.swing.JTextField();
        lbl_status = new javax.swing.JLabel();
        cbx_status = new javax.swing.JComboBox<>();
        btn_permission = new javax.swing.JButton();
        btn_staff = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_exit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CHI TIẾT TÀI KHOẢN");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(212, 209, 216)));

        lbl_username.setText("Tên đăng nhập");

        txt_id.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_id.setFocusable(false);

        lbl_password.setText("Mật khẩu");

        lbl_id.setText("Mã tài khoản");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_password)
                    .addComponent(lbl_username)
                    .addComponent(lbl_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_password, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(txt_username)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(lbl_id)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_username)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_password)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lbl_staff.setText("Nhân viên");

        txt_staff.setFocusable(false);

        lbl_permission.setText("Nhóm quyền");

        txt_permission.setFocusable(false);
        txt_permission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_permissionActionPerformed(evt);
            }
        });

        lbl_status.setText("Trạng thái");

        btn_permission.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btn_permission.setText("...");
        btn_permission.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_permission.setFocusPainted(false);
        btn_permission.setMaximumSize(new java.awt.Dimension(40, 40));
        btn_permission.setPreferredSize(new java.awt.Dimension(40, 40));
        btn_permission.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_permission.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        btn_staff.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btn_staff.setText("...");
        btn_staff.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_staff.setFocusPainted(false);
        btn_staff.setMaximumSize(new java.awt.Dimension(40, 40));
        btn_staff.setPreferredSize(new java.awt.Dimension(70, 40));
        btn_staff.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_staff.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbx_status, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbl_staff, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_staff, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(lbl_permission, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_permission)
                            .addComponent(lbl_status, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_permission, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_staff, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(lbl_staff)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_staff, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(txt_staff))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_permission)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_permission)
                    .addComponent(btn_permission, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_status)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbx_status))
        );

        btn_save.setBackground(new java.awt.Color(21, 154, 32));
        btn_save.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_save.setForeground(new java.awt.Color(255, 255, 255));
        btn_save.setText("Lưu");
        btn_save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_save.setFocusPainted(false);

        btn_edit.setBackground(new java.awt.Color(255, 215, 64));
        btn_edit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_edit.setForeground(new java.awt.Color(255, 255, 255));
        btn_edit.setText("Sửa");
        btn_edit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_edit.setFocusPainted(false);

        btn_exit.setBackground(new java.awt.Color(244, 67, 54));
        btn_exit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_exit.setForeground(new java.awt.Color(255, 255, 255));
        btn_exit.setText("Hủy");
        btn_exit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_exit.setFocusPainted(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(185, 185, 185))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_permissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_permissionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_permissionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_exit;
    private javax.swing.JButton btn_permission;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_staff;
    private javax.swing.JComboBox<String> cbx_status;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbl_id;
    private javax.swing.JLabel lbl_password;
    private javax.swing.JLabel lbl_permission;
    private javax.swing.JLabel lbl_staff;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JTextField txt_id;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_permission;
    private javax.swing.JTextField txt_staff;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
