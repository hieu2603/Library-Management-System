/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.Panel;

import BUS.BookBUS;
import BUS.CategoryBUS;
import BUS.PublisherBUS;
import DTO.BookDTO;
import GUI.Book.BookDialog;
import GUI.Component.ManagementTable;
import GUI.Component.MenuBar;
import GUI.Component.MenuBarButton;
import GUI.Main_Frame;
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
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Duc3m
 */
public class BookPanel extends javax.swing.JPanel {
    
    Main_Frame main;
    
    String[] searchTypes = {"Tất cả", "Mã sách", "Tên sách", "Tác giả", "Nhà xuất bản", "Năm xuất bản", "Thể loại"};

    ManagementTable tablePanel = new ManagementTable();
    MenuBar menuBar = new MenuBar(searchTypes);
    MenuBarButton addBtn = new MenuBarButton("Thêm", "add.svg", new Color(173, 169, 178), "add");
    MenuBarButton exportBtn = new MenuBarButton("Xuất excel", "export_excel.svg", new Color(52, 199, 89), "export");
    
    BookBUS bookBUS = new BookBUS();
    CategoryBUS categoryBUS = new CategoryBUS();
    PublisherBUS publisherBUS = new PublisherBUS();
    ArrayList<BookDTO> bookList = new BookBUS().getAll();
    
    public BookPanel(Main_Frame main) {
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
        String[] columnNames = {"Mã sách", "Tên sách", "Tác giả", "Nhà xuất bản", "Năm xuất bản", "Thể loại", "Số lượng"};
        tablePanel.table.setModel(new DefaultTableModel(null, columnNames));
        loadDataToTable(bookList);
        
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
                    JOptionPane.showMessageDialog(null, "Bạn chưa chọn sách nào");
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
    
    public void loadDataToTable(ArrayList<BookDTO> bookList) {
        DefaultTableModel tableModel = (DefaultTableModel) tablePanel.table.getModel();
        tableModel.setRowCount(0);
        for (BookDTO i : bookList) {
            tableModel.addRow(new Object[] {
                    i.getId(),
                    i.getTitle(),
                    i.getAuthor(),
                    publisherBUS.getById(i.getPublisherId()).getName(),
                    i.getYearPublish(),
                    categoryBUS.getById(i.getCategoryId()).getName(),
                    i.getQuantity()
            });
        }
    }
    
    public void searchEvent() {
        String searchText = menuBar.txt_search.getText();
        String type = (String) menuBar.cbx_type.getSelectedItem();
        loadDataToTable(bookBUS.search(searchText, type));
    }
    
    public void refreshTable() {
        bookList = bookBUS.getAll();
        loadDataToTable(bookList);
    }
    
    public void viewEvent() {
        int index = tablePanel.table.getSelectedRow();
        int id = (int) tablePanel.table.getValueAt(index, 0);
        BookDTO book = bookBUS.getById(id);
        BookDialog bDialog = new BookDialog(null, true, book, "view");
        bDialog.setVisible(true);
        refreshTable();
    }
    
    public void addEvent() {
        BookDialog bDialog = new BookDialog(null, true, null, "add");
        bDialog.setVisible(true);
        refreshTable();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

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

        add(jLayeredPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    // End of variables declaration//GEN-END:variables
}
