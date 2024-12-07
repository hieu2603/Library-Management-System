/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.Panel;

import BUS.SupplierBUS;
import DTO.SupplierDTO;
import GUI.Component.ManagementTable;
import GUI.Component.MenuBar;
import GUI.Component.MenuBarButton;
import GUI.Main_Frame;
import GUI.Supplier.SupplierDialog;
import helper.JTableExporter;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Duc3m
 */
public class SupplierPanel extends javax.swing.JPanel {
    
    Main_Frame main;
    
    String[] searchTypes = {"Tất cả", "Mã nhà cung cấp", "Tên", "Địa chỉ", "Số điện thoại"};

    ManagementTable tablePanel = new ManagementTable();
    MenuBar menuBar = new MenuBar(searchTypes);
    MenuBarButton addBtn = new MenuBarButton("Thêm", "add.svg", new Color(173, 169, 178), "add");
    MenuBarButton exportBtn = new MenuBarButton("Xuất excel", "export_excel.svg", new Color(52, 199, 89), "export");
    
    SupplierBUS supplierBUS = new SupplierBUS();
    ArrayList<SupplierDTO> supplierList = supplierBUS.getAll();
    
    public SupplierPanel(Main_Frame main) {
        this.main = main;
        initComponents();
        customInit();
    }

    public void customInit() {
        //Đặt menuBar và table lên layer 100
        menuBar.setBounds(14, 20, 940, 150);
        jLayeredPane1.add(menuBar, Integer.valueOf(100));
        tablePanel.setBounds(14, 180, 940, 600);
        jLayeredPane1.add(tablePanel, Integer.valueOf(100));
        
        //Quy định các cột
        String[] columnNames = {"Mã nhà cung cấp", "Tên", "Địa chỉ", "Số điện thoại"};
        tablePanel.table.setModel(new DefaultTableModel(null, columnNames));
        loadDataToTable(supplierList);
        
        menuBar.btn_refresh.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                refreshTable();
            }
        });
        
        menuBar.jToolBar1.add(addBtn);
        
        addBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addEvent();
            }
        });
        
        menuBar.jToolBar1.add(exportBtn);
        exportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JTableExporter.exportJTableToExcel(tablePanel.table);
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        });
        
        tablePanel.viewOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tablePanel.table.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Bạn chưa chọn nhà cung cấp nào");
                    return;
                }
                viewEvent();
            }
        });
        
        menuBar.txt_search.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e) {
                searchEvent();
            }
        });
        
        menuBar.cbx_type.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                searchEvent();
            }
        });
        
    }
    
    public void loadDataToTable(ArrayList<SupplierDTO> supplierList) {
        DefaultTableModel tableModel = (DefaultTableModel) tablePanel.table.getModel();
        tableModel.setRowCount(0);
        for (SupplierDTO i : supplierList) {
            tableModel.addRow(new Object[] {
                    i.getId(),
                    i.getName(),
                    i.getAddress(),
                    i.getPhone()
            });
        }
    }
    
    public void searchEvent() {
        String searchText = menuBar.txt_search.getText();
        String type = (String) menuBar.cbx_type.getSelectedItem();
        loadDataToTable(supplierBUS.search(searchText, type));
    }
    
    public void refreshTable() {
        supplierList = supplierBUS.getAll();
        loadDataToTable(supplierList);
    }
    
    public void viewEvent() {
        int index = tablePanel.table.getSelectedRow();
        int id = (int) tablePanel.table.getValueAt(index, 0);
        SupplierDTO supplier = supplierBUS.getById(id);
        SupplierDialog sD = new SupplierDialog(null, true, supplier, "view");
        sD.setVisible(true);
        refreshTable();
    }
    
    public void addEvent() {
        SupplierDialog sD = new SupplierDialog(null, true, null, "add");
        sD.setVisible(true);
        refreshTable();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();

        jLayeredPane1.setPreferredSize(new java.awt.Dimension(980, 830));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background/product_background1.jpg"))); // NOI18N

        jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    // End of variables declaration//GEN-END:variables
}
