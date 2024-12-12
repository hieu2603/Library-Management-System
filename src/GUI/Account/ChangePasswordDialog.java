/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUI.Account;

import BUS.AccountBUS;
import DTO.AccountDTO;
import javax.swing.JOptionPane;

/**
 *
 * @author Duc3m
 */
public class ChangePasswordDialog extends javax.swing.JDialog {

    AccountDTO account;
    AccountBUS accountBUS = new AccountBUS();
    public boolean changed = false;
    
    public ChangePasswordDialog(java.awt.Frame parent, boolean modal, AccountDTO account) {
        super(parent, modal);
        this.account = account;
        initComponents();
        setLocationRelativeTo(null);
    }
    
    public void changePasswordEvent() {
        String oldPass = new String(txt_oldPass.getPassword());
        String newPass = new String(txt_newPass.getPassword());
        String confirmNewPass = new String(txt_confirmNewPass.getPassword());
        if(!account.getPassword().equals(oldPass)) {
            JOptionPane.showMessageDialog(null, "Mật khẩu cũ không khớp");
            return;
        }
        if(!newPass.equals(confirmNewPass)) {
            JOptionPane.showMessageDialog(null, "Xác nhận mật khẩu mới không khớp");
            return;
        }
        account.setPassword(newPass);
        if(accountBUS.update(account)) {
            JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công");
            changed = true;
            dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbl_password = new javax.swing.JLabel();
        lbl_password1 = new javax.swing.JLabel();
        lbl_password2 = new javax.swing.JLabel();
        btn_save = new javax.swing.JButton();
        btn_exit = new javax.swing.JButton();
        txt_oldPass = new javax.swing.JPasswordField();
        txt_newPass = new javax.swing.JPasswordField();
        txt_confirmNewPass = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ĐỔI MẬT KHẨU");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(212, 209, 216)));

        lbl_password.setText("Mật khẩu cũ");

        lbl_password1.setText("Mật khẩu mới");

        lbl_password2.setText("Xác nhận mật khẩu mới");

        btn_save.setBackground(new java.awt.Color(21, 154, 32));
        btn_save.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_save.setForeground(new java.awt.Color(255, 255, 255));
        btn_save.setText("Đổi mật khẩu");
        btn_save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_save.setFocusPainted(false);
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_exit.setBackground(new java.awt.Color(244, 67, 54));
        btn_exit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_exit.setForeground(new java.awt.Color(255, 255, 255));
        btn_exit.setText("Hủy");
        btn_exit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_exit.setFocusPainted(false);
        btn_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_password)
                            .addComponent(lbl_password1)
                            .addComponent(lbl_password2)
                            .addComponent(txt_oldPass)
                            .addComponent(txt_newPass, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(txt_confirmNewPass, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(btn_save)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(lbl_password)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_oldPass, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_password1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_newPass, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_password2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_confirmNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exitActionPerformed
        dispose();
    }//GEN-LAST:event_btn_exitActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        changePasswordEvent();
    }//GEN-LAST:event_btn_saveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_exit;
    private javax.swing.JButton btn_save;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_password;
    private javax.swing.JLabel lbl_password1;
    private javax.swing.JLabel lbl_password2;
    private javax.swing.JPasswordField txt_confirmNewPass;
    private javax.swing.JPasswordField txt_newPass;
    private javax.swing.JPasswordField txt_oldPass;
    // End of variables declaration//GEN-END:variables
}
