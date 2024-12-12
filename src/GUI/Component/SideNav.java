/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.Component;

import BUS.PermissionBUS;
import BUS.PermissionDetailBUS;
import BUS.StaffBUS;
import DTO.AccountDTO;
import DTO.PermissionDTO;
import DTO.PermissionDetailDTO;
import DTO.SessionManager;
import DTO.StaffDTO;
import GUI.Account.ChangePasswordDialog;
import GUI.Login_Frame;
import GUI.Main_Frame;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Duc3m
 */
public class SideNav extends javax.swing.JPanel {

    Main_Frame main;
    String[][] menuNames = {
        {"Trang chủ", "home", "home.svg"},
        {"Quản lý sách", "book", "book.svg"},
        {"Mượn sách", "borrow", "borrow.svg"},
        {"Trả sách", "return", "return.svg"},
        {"Nhập sách", "purchase", "receipt.svg"},
        {"Nhân viên", "staff", "employee.svg"},
        {"Thành viên", "member", "customer.svg"},
        {"Tài khoản", "account", "account.svg"},
        {"Vi phạm", "penalty", "namecard.svg"},
        {"Phân quyền", "permission", "permission.svg"},
        {"Thống kê", "statistic", "statistic.svg"},
        {"Kệ sách", "bookshelf", "bookshelf.svg"},
        {"Nhà xuất bản", "publisher", "publisher.svg"},
        {"Nhà cung cấp", "supplier", "supplier.svg"},
        {"Đăng xuất", "logout", "door.svg"}
    };
    public SideNav_Button menuButtons[];
    AccountDTO account;
    StaffBUS staffBUS = new StaffBUS();
    StaffDTO staff;
    PermissionBUS pBUS = new PermissionBUS();
    PermissionDTO permission;
    PermissionDetailBUS pdBUS = new PermissionDetailBUS();
    ArrayList<PermissionDetailDTO> pdList;
    
    public SideNav(Main_Frame main) {
        initComponents();     
        this.main = main;
        this.account = SessionManager.getInstance().getLoggedInAccount();
        this.staff = staffBUS.getById(account.getStaff_id());
        this.permission = pBUS.getById(account.getPermission_id());
        customInit();
    }
    
    public void customInit() {
        lbl_staffName.setText(this.staff.getFullName());
        lbl_permissionName.setText(this.permission.getName());
        
        pdList = pdBUS.getByPermissionId(account.getPermission_id());
        
        menuButtons = new SideNav_Button[menuNames.length];
        
        //Tao cac nut cho menu
        for(int i = 0; i < menuNames.length; i++) {
            menuButtons[i] = new SideNav_Button(main, menuNames[i][0], menuNames[i][1], menuNames[i][2]);
            
            //Neu khong phai nut dang xuat
            if(i != menuNames.length - 1) {
                //Them MouseEvent
                menuButtons[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent evt) {
                        selectingMenuItem(evt);
                    }
                });
            }
            //Doi mau neu la nut dang xuat
            else {
                menuButtons[i].isLogoutButton = true;
                menuButtons[i].setBackground(menuButtons[i].logoutButtonColor);
                menuButtons[i].text.setForeground(Color.white);
                
                menuButtons[i].addMouseListener(new MouseAdapter(){
                    @Override
                    public void mousePressed(MouseEvent e) {
                        int confirm = JOptionPane.showConfirmDialog(main, "Bạn có chắc muốn đăng xuất không?", "Đăng xuất", JOptionPane.YES_NO_OPTION);
                        if (confirm == 0) {
                            main.dispose();
                            new Login_Frame().setVisible(true);
                        }
                    }
                });
            }
        }
        
        //Chieu cao cua centerpanel de khong bi du thua scrollpane
        int newHeigh = 82;
        
        //Them cac nut vao menu
        centerPanel.add(menuButtons[0]);
        for(int i = 1; i < menuNames.length - 1; i++)
            if(pBUS.functionCheck(pdList, i)) {
                centerPanel.add(menuButtons[i]);
                newHeigh += 66;
            }
        bottomPanel.add(menuButtons[menuNames.length - 1]);
        
        //Doi mau cho nut dau tien
        menuButtons[0].isSelected = true;
        menuButtons[0].setBackground(menuButtons[0].enteredColor);
        menuButtons[0].text.setForeground(menuButtons[0].enteredFontColor);
        
        //Tang toc cho cai scrollpane
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);
        //Repaint panel moi khi scroll de sua anh bi hong
        jScrollPane1.getViewport().addChangeListener(e -> {
            centerPanel.repaint();
        });
        
        centerPanel.setPreferredSize(new Dimension(300, newHeigh));
        
        user_icon.setIcon(new FlatSVGIcon("./svg/icon/user_female.svg"));
    }
    
    public void selectingMenuItem(MouseEvent evt) {
        for (int i = 0; i < menuNames.length - 1; i++) {
            if (evt.getSource() == menuButtons[i]) {
                menuButtons[i].isSelected = true;
                menuButtons[i].setBackground(menuButtons[i].enteredColor);
                menuButtons[i].text.setForeground(menuButtons[i].enteredFontColor);
            } else {
                menuButtons[i].isSelected = false;
                menuButtons[i].setBackground(menuButtons[i].exitedColor);
                menuButtons[i].text.setForeground(menuButtons[i].exitedFontColor);
            }
        }
    }
    
    public void changePasswordEvent() {
        ChangePasswordDialog cpD = new ChangePasswordDialog(null, true, account);
        cpD.setVisible(true);
        if(cpD.changed == true) {
            main.dispose();
            new Login_Frame().setVisible(true);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        topPanel = new javax.swing.JPanel();
        lbl_staffName = new javax.swing.JLabel();
        lbl_permissionName = new javax.swing.JLabel();
        user_icon = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        centerPanel = new javax.swing.JPanel();
        bottomPanel = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        setInheritsPopupMenu(true);
        setMaximumSize(new java.awt.Dimension(300, 830));
        setMinimumSize(new java.awt.Dimension(1, 0));
        setLayout(new java.awt.BorderLayout());

        topPanel.setBackground(new java.awt.Color(255, 255, 255));
        topPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(108, 107, 116)));
        topPanel.setPreferredSize(new java.awt.Dimension(300, 90));

        lbl_staffName.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lbl_staffName.setText("Tên nhân viên");
        lbl_staffName.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_staffName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                user_iconMousePressed(evt);
            }
        });

        lbl_permissionName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_permissionName.setText("Tên nhóm quyền");
        lbl_permissionName.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_permissionName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                user_iconMousePressed(evt);
            }
        });

        user_icon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        user_icon.setPreferredSize(new java.awt.Dimension(50, 50));
        user_icon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                user_iconMousePressed(evt);
            }
        });

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(user_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_staffName, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                    .addComponent(lbl_permissionName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(user_icon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(topPanelLayout.createSequentialGroup()
                        .addComponent(lbl_staffName)
                        .addGap(0, 0, 0)
                        .addComponent(lbl_permissionName)))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        add(topPanel, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        centerPanel.setBackground(new java.awt.Color(255, 255, 255));
        centerPanel.setToolTipText("");
        centerPanel.setPreferredSize(new java.awt.Dimension(300, 740));
        centerPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 16));
        jScrollPane1.setViewportView(centerPanel);

        add(jScrollPane1, java.awt.BorderLayout.LINE_END);

        bottomPanel.setBackground(new java.awt.Color(255, 255, 255));
        bottomPanel.setPreferredSize(new java.awt.Dimension(300, 60));
        add(bottomPanel, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void user_iconMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_user_iconMousePressed
        changePasswordEvent();
    }//GEN-LAST:event_user_iconMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JPanel centerPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_permissionName;
    private javax.swing.JLabel lbl_staffName;
    private javax.swing.JPanel topPanel;
    private javax.swing.JLabel user_icon;
    // End of variables declaration//GEN-END:variables
}
