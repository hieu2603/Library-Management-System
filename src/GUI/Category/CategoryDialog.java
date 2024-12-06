/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUI.Category;

import BUS.CategoryBUS;
import DTO.CategoryDTO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Duc3m
 */
public class CategoryDialog extends javax.swing.JDialog {

    CategoryBUS categoryBUS = new CategoryBUS();
    ArrayList<CategoryDTO> categoryList;
    public boolean choosen = false;

    public CategoryDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        customInit();
    }
    
    public void customInit() {
        setLocationRelativeTo(null);
        
        categoryList = categoryBUS.getAll();
        loadDataToTable(categoryList);
        
        btn_choose.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    jTable1.getValueAt(jTable1.getSelectedRow(), 0);
                    choosen = true;
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Chưa chọn danh mục");
                }
            }
        });
        
        btn_exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
            }
        });
        
    }
    
    public void loadDataToTable(ArrayList<CategoryDTO> categoryList) {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0);
        for (CategoryDTO i : categoryList) {
            tableModel.addRow(new Object[] {
                    i.getId(),
                    i.getName()
            });
        }
    }
    
    public void refreshTable() {
        categoryList = categoryBUS.getAll();
        loadDataToTable(categoryList);
    }
    
    public int getSelectedId() {
        try {
            return (int) jTable1.getValueAt(jTable1.getSelectedRow(), 0);
        }
        catch (Exception e){
            return -1;
        }
    }
    
    public static CategoryDTO getCategory() {
        CategoryDialog cD = new CategoryDialog(null, true);
        cD.setVisible(true);
        try {
            if (cD.choosen == false) {
                return null;
            }
            int category_id = cD.getSelectedId();
            return CategoryBUS.getInstance().getById(category_id);
        } catch (Exception ex) {

        }
        return null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu2 = new javax.swing.JPopupMenu();
        option_add = new javax.swing.JMenuItem();
        option_update = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btn_choose = new javax.swing.JButton();
        btn_exit = new javax.swing.JButton();

        option_add.setText("Thêm");
        option_add.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        option_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                option_addActionPerformed(evt);
            }
        });
        jPopupMenu2.add(option_add);

        option_update.setText("Sửa");
        option_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        option_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                option_updateActionPerformed(evt);
            }
        });
        jPopupMenu2.add(option_update);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CHỌN DANH MỤC");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(212, 209, 216)));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Tên"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setComponentPopupMenu(jPopupMenu2);
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTable1.setFocusable(false);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        btn_choose.setBackground(new java.awt.Color(21, 154, 32));
        btn_choose.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_choose.setForeground(new java.awt.Color(255, 255, 255));
        btn_choose.setText("Chọn");
        btn_choose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_choose.setFocusPainted(false);

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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(btn_choose, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126)
                .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_choose, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 374, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void option_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_option_addActionPerformed
        try {
            String name;
            name = JOptionPane.showInputDialog("Nhập tên cho danh mục mới");
            if(name.equals(""))
                return;
            CategoryDTO newCategory = new CategoryDTO(name);
            if(categoryBUS.add(newCategory)) {
                JOptionPane.showMessageDialog(null, "Thêm danh mục mới thành công");
                refreshTable();
            }
        } catch (Exception e) {
            
        }
    }//GEN-LAST:event_option_addActionPerformed

    private void option_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_option_updateActionPerformed
        if (jTable1.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn danh mục nào");
            return;
        }
        int index = jTable1.getSelectedRow();
        int id = (int) jTable1.getValueAt(index, 0);
        CategoryDTO category = categoryBUS.getById(id);
        String newName;
        newName = JOptionPane.showInputDialog("Nhập tên mới cho danh mục này");
        if(newName == null)
            return;
        if(newName.equals(""))
            return;
        category.setName(newName);
        if (categoryBUS.update(category)) {
            JOptionPane.showMessageDialog(null, "Sửa danh mục thành công");
            refreshTable();
        }
    }//GEN-LAST:event_option_updateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_choose;
    private javax.swing.JButton btn_exit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JMenuItem option_add;
    private javax.swing.JMenuItem option_update;
    // End of variables declaration//GEN-END:variables
}
