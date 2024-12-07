/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUI.Staff;

import BUS.StaffBUS;
import DTO.SessionManager;
import DTO.StaffDTO;
import config.Constants;
import helper.Formatter;
import helper.Validator;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.sql.Timestamp;
import javax.swing.JOptionPane;

/**
 *
 * @author Duc3m
 */
public class StaffDialog extends javax.swing.JDialog {

    StaffDTO staff;
    String mode;
    int functionId = Constants.functions.get("Quản lý nhân viên");
    
    StaffBUS staffBUS = new StaffBUS();
    
    public StaffDialog(java.awt.Frame parent, boolean modal, StaffDTO staff, String mode) {
        super(parent, modal);
        this.staff = staff;
        this.mode = mode;
        initComponents();
        customInit();
    }

    public void customInit() {
        setLocationRelativeTo(null);
        
        cbx_status.addItem(Constants.staff_status[0]);
        if(!mode.equals("add"))
            for(int i=1; i<Constants.staff_status.length; i++)
                cbx_status.addItem(Constants.staff_status[i]);
        
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
        
        if(mode.equals("view"))
            initViewMode();
        if(mode.equals("add"))
            initAddMode();
    }
    
    public void initViewMode() {
        txt_id.setText(staff.getId() + "");
        txt_name.setText(staff.getFullName());
        jDateChooser1.setDate(new Date(staff.getBirthday().getTime()));
        if(staff.getGender().equals("Nam")) {
            radio_male.setSelected(true);
            radio_female.setEnabled(false);
        }
        else {
            radio_female.setSelected(true);
            radio_male.setEnabled(false);
        }
        txt_address.setText(staff.getAddress());
        txt_date.setText(Formatter.getDate(staff.getHireDate()));
        txt_phone.setText(staff.getPhone());
        txt_email.setText(staff.getEmail());
        cbx_status.setSelectedItem(staff.getStatus());
        
        txt_name.setFocusable(false);
        jDateChooser1.setEnabled(false);
        txt_address.setFocusable(false);
        txt_phone.setFocusable(false);
        txt_email.setFocusable(false);
        cbx_status.setEnabled(false);
        
        if(!SessionManager.getInstance().permissionCheck(functionId, "edit"))
            btn_edit.setEnabled(false);
    }
    
    public void initAddMode() {
        jLabel2.setText("THÊM NHÂN VIÊN MỚI");
        btn_save.setText("Thêm");
        //Giấu nút sửa
        btn_edit.setEnabled(false);
        btn_edit.setText("");
        btn_edit.setBorder(null);
        btn_edit.setBackground(new Color(240, 240, 240));
    }
    
    public void enableForm() {
        txt_name.setFocusable(true);
        jDateChooser1.setEnabled(true);
        radio_male.setEnabled(true);
        radio_female.setEnabled(true);
        txt_address.setFocusable(true);
        txt_phone.setFocusable(true);
        txt_email.setFocusable(true);
        cbx_status.setEnabled(true);
        btn_edit.setEnabled(false);
    }
    
    public void editStaff() {
        staff.setFullName(txt_name.getText());
        staff.setBirthday(new Timestamp(jDateChooser1.getDate().getTime()));
        if(radio_male.isSelected())
            staff.setGender("Nam");
        if(radio_female.isSelected())
            staff.setGender("Nữ");
        staff.setAddress(txt_address.getText());
        staff.setPhone(txt_phone.getText());
        staff.setEmail(txt_email.getText());
        staff.setStatus(cbx_status.getSelectedItem().toString());
    }
    
    public void updateEvent() {
        if(!validateInputs())
            return;
        editStaff();
        if(staffBUS.updateStaff(staff)) {
            JOptionPane.showMessageDialog(null, "Lưu thông tin nhân viên thành công");
            dispose();
        }
    }
    
    public StaffDTO getNewStaff() {
        String fullName = txt_name.getText();
        String email = txt_email.getText();
        String phone = txt_phone.getText();
        String gender = "";
        if(radio_male.isSelected())
            gender = "Nam";
        if(radio_female.isSelected())
            gender = "Nữ";
        Timestamp birthday = new Timestamp(jDateChooser1.getDate().getTime());
        String address = txt_address.getText();
        Timestamp hireDate = new Timestamp(System.currentTimeMillis());
        return new StaffDTO(fullName, email, phone, gender, birthday, address, hireDate, email);
    }
    
    public boolean validateInputs() {
        if(Validator.isEmpty(txt_name.getText())) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập họ tên");
            return false;
        }
        if(jDateChooser1.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn ngày sinh");
            return false;
        }
        if(radio_male.isSelected()==false && radio_female.isSelected()==false) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn giới tính cho nhân viên");
            return false;
        }
        if(Validator.isEmpty(txt_address.getText())) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập địa chỉ của nhân viên");
            return false;
        }
        if(Validator.isEmpty(txt_phone.getText())) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập số điện thoại của nhân viên");
            return false;
        }
        if(Validator.isEmpty(txt_email.getText())) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập email của nhân viên");
            return false;
        }
        if(!Validator.isName(txt_name.getText())) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên hợp lệ");
            return false;
        }
        if(!Validator.isWithinLength(txt_address.getText(), 50)) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được dài quá 50 kí tự");
            return false;
        }
        if(!Validator.isPhoneNumber(txt_phone.getText())) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải là số bắt đầu bằng 0 và có độ dài 10 chữ số");
            return false;
        }
        if(!Validator.isEmail(txt_email.getText())) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng email");
            return false;
        }
        return true;
    }
    
    public void addEvent() {
        if(!validateInputs())
            return;
        staff = getNewStaff();
        if(staffBUS.createStaff(staff)) {
            JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công");
            dispose();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbl_id = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        lbl_name = new javax.swing.JLabel();
        txt_name = new javax.swing.JTextField();
        lbl_birthday = new javax.swing.JLabel();
        lbl_gender = new javax.swing.JLabel();
        radio_male = new javax.swing.JRadioButton();
        radio_female = new javax.swing.JRadioButton();
        lbl_address = new javax.swing.JLabel();
        txt_address = new javax.swing.JTextField();
        lbl_date = new javax.swing.JLabel();
        txt_date = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        lbl_phone = new javax.swing.JLabel();
        txt_phone = new javax.swing.JTextField();
        lbl_email = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        lbl_status = new javax.swing.JLabel();
        cbx_status = new javax.swing.JComboBox<>();
        btn_save = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_exit = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(212, 209, 216)));

        lbl_id.setText("Mã nhân viên");

        txt_id.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_id.setFocusable(false);

        lbl_name.setText("Họ tên");

        lbl_birthday.setText("Ngày sinh");

        lbl_gender.setText("Giới tính");

        buttonGroup1.add(radio_male);
        radio_male.setSelected(true);
        radio_male.setText("Nam");

        buttonGroup1.add(radio_female);
        radio_female.setText("Nữ");

        lbl_address.setText("Địa chỉ");

        lbl_date.setText("Ngày vào làm");

        txt_date.setFocusable(false);

        jDateChooser1.setDateFormatString("dd/MM/yyyy");
        jDateChooser1.setFocusable(false);
        jDateChooser1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbl_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_id))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_name)
                            .addComponent(txt_name)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_birthday)
                                    .addComponent(lbl_address)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbl_gender)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(radio_male)
                                                .addGap(18, 18, 18)
                                                .addComponent(radio_female))))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(34, 34, 34)
                                        .addComponent(lbl_date))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(txt_address)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(12, 12, 12))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbl_id)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbl_name)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_birthday)
                    .addComponent(lbl_gender))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radio_male, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(radio_female, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_address)
                            .addComponent(lbl_date))
                        .addGap(47, 47, 47))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_address, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );

        lbl_phone.setText("Số điện thoại");

        lbl_email.setText("Email");

        lbl_status.setText("Trạng thái");

        cbx_status.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lbl_phone)
                        .addComponent(lbl_email)
                        .addComponent(txt_email, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                        .addComponent(txt_phone))
                    .addComponent(lbl_status)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbx_status, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lbl_phone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_phone, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_email)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_status)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbx_status, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("CHI TIẾT NHÂN VIÊN");
        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(212, 209, 216)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(229, 229, 229))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_exit;
    private javax.swing.JButton btn_save;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbx_status;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbl_address;
    private javax.swing.JLabel lbl_birthday;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_gender;
    private javax.swing.JLabel lbl_id;
    private javax.swing.JLabel lbl_name;
    private javax.swing.JLabel lbl_phone;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JRadioButton radio_female;
    private javax.swing.JRadioButton radio_male;
    private javax.swing.JTextField txt_address;
    private javax.swing.JTextField txt_date;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_phone;
    // End of variables declaration//GEN-END:variables
}
