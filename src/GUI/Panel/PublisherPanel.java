/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.Panel;

import BUS.PublisherBUS;
import DTO.PublisherDTO;
import DTO.SessionManager;
import GUI.Component.ManagementTable;
import GUI.Component.MenuBar;
import GUI.Component.MenuBarButton;
import GUI.Main_Frame;
import GUI.Publisher.PublisherDialog;
import config.Constants;
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
public class PublisherPanel extends javax.swing.JPanel {
    
    Main_Frame main;
    
    int functionId = Constants.functions.get("Quản lý nhà xuất bản");
    String[] searchTypes = {"Tất cả", "Mã nhà xuất bản", "Tên", "Địa chỉ", "Số điện thoại"};

    ManagementTable tablePanel = new ManagementTable();
    MenuBar menuBar = new MenuBar(searchTypes);
    MenuBarButton addBtn = new MenuBarButton("Thêm", "add.svg", new Color(173, 169, 178), "add");
    MenuBarButton exportBtn = new MenuBarButton("Xuất excel", "export_excel.svg", new Color(52, 199, 89), "export");
    
    PublisherBUS publisherBUS = new PublisherBUS();
    ArrayList<PublisherDTO> publisherList = publisherBUS.getAll();
    
    public PublisherPanel(Main_Frame main) {
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
        String[] columnNames = {"Mã nhà xuất bản", "Tên", "Địa chỉ", "Số điện thoại"};
        tablePanel.table.setModel(new DefaultTableModel(null, columnNames));
        loadDataToTable(publisherList);
        
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
                    JOptionPane.showMessageDialog(null, "Bạn chưa chọn nhà xuất bản nào");
                    return;
                }
                viewEvent();
            }
        });
        
        tablePanel.deleteOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tablePanel.table.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Bạn chưa chọn nhà xuất bản nào");
                    return;
                }
                deleteEvent();
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
        
        if(!SessionManager.getInstance().permissionCheck(functionId, "delete")) {
            tablePanel.jPopupMenu1.remove(tablePanel.jSeparator1);
            tablePanel.jPopupMenu1.remove(tablePanel.deleteOption);
        }
    }
    
    public void loadDataToTable(ArrayList<PublisherDTO> publisherList) {
        DefaultTableModel tableModel = (DefaultTableModel) tablePanel.table.getModel();
        tableModel.setRowCount(0);
        for (PublisherDTO i : publisherList) {
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
        loadDataToTable(publisherBUS.search(searchText, type));
    }
    
    public void refreshTable() {
        publisherList = publisherBUS.getAll();
        loadDataToTable(publisherList);
    }
    
    public void viewEvent() {
        int index = tablePanel.table.getSelectedRow();
        int id = (int) tablePanel.table.getValueAt(index, 0);
        PublisherDTO publisher = publisherBUS.getById(id);
        PublisherDialog pD = new PublisherDialog(null, true, publisher, "view");
        pD.setVisible(true);
        refreshTable();
    }
    
    public void addEvent() {
        PublisherDialog pD = new PublisherDialog(null, true, null, "add");
        pD.setVisible(true);
        refreshTable();
    }
    
    public void deleteEvent() {
        int index = tablePanel.table.getSelectedRow();
        int id = (int) tablePanel.table.getValueAt(index, 0);
        if(publisherBUS.delete(id)) {
            JOptionPane.showMessageDialog(null, "Xóa nhà xuất bản thành công");
        }
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
