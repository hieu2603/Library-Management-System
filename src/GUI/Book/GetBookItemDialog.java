/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUI.Book;

import BUS.BookBUS;
import BUS.BookItemBUS;
import DTO.BookDTO;
import DTO.BookItemDTO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Duc3m
 */
public class GetBookItemDialog extends javax.swing.JDialog {

    BookBUS bookBUS = new BookBUS();
    BookItemBUS bookItemBUS = new BookItemBUS();
    
    ArrayList<BookDTO> bookList;
    ArrayList<BookItemDTO> itemList;
    
    public boolean choosen = false;
    
    public GetBookItemDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        customInit();
    }
    
    public void customInit() {
        setLocationRelativeTo(null);
        
        btn_choose.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    tbl_item.getValueAt(tbl_item.getSelectedRow(), 0);
                    choosen = true;
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Chưa chọn sách");
                }
            }
        });
        
        btn_exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
            }
        });
        
        bookList = bookBUS.getAll();
        loadBookToTable(bookList);
        
    }
    
    public void loadBookToTable(ArrayList<BookDTO> bookList) {
        DefaultTableModel tableModel = (DefaultTableModel) tbl_book.getModel();
        tableModel.setRowCount(0);
        for (BookDTO i : bookList) {
            tableModel.addRow(new Object[] {
                    i.getId(),
                    i.getTitle(),
                    i.getAuthor()
            });
        }
    }
    
    public void loadItemToTable(ArrayList<BookItemDTO> itemList) {
        DefaultTableModel tableModel = (DefaultTableModel) tbl_item.getModel();
        tableModel.setRowCount(0);
        for (BookItemDTO i : itemList) {
            if(i.getStatus().equals("Đang mượn")) continue;
            tableModel.addRow(new Object[] {
                    i.getIsbn(),
                    i.getStatus(),
            });
        }
    }

    public String getSelectedISBN() {
        try {
            return (String) tbl_item.getValueAt(tbl_item.getSelectedRow(), 0);
        }
        catch (Exception e){
            return null;
        }
    }
    
    public static BookItemDTO getBookItem() {
        GetBookItemDialog gbiD = new GetBookItemDialog(null, true);
        gbiD.setVisible(true);
        try {
            if (gbiD.choosen == false) {
                return null;
            }
            String isbn = gbiD.getSelectedISBN();
            return BookItemBUS.getInstance().getByISBN(isbn);
        } catch (Exception ex) {

        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_item = new javax.swing.JTable();
        btn_choose = new javax.swing.JButton();
        btn_exit = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_book = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CHỌN SÁCH");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(212, 209, 216)));

        tbl_item.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ISBN", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_item.setFocusable(false);
        tbl_item.getTableHeader().setResizingAllowed(false);
        tbl_item.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbl_item);
        if (tbl_item.getColumnModel().getColumnCount() > 0) {
            tbl_item.getColumnModel().getColumn(0).setResizable(false);
            tbl_item.getColumnModel().getColumn(1).setResizable(false);
        }

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

        tbl_book.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sách", "Tựa đề", "Tác giả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_book.setFocusable(false);
        tbl_book.getTableHeader().setResizingAllowed(false);
        tbl_book.getTableHeader().setReorderingAllowed(false);
        tbl_book.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_bookMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_book);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_choose, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126)
                .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(200, 200, 200))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(270, 270, 270))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_choose, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_bookMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bookMousePressed
        int row = tbl_book.getSelectedRow();
        int id = (int) tbl_book.getValueAt(row, 0);
        itemList = bookItemBUS.getByBookId(id);
        loadItemToTable(itemList);
    }//GEN-LAST:event_tbl_bookMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_choose;
    private javax.swing.JButton btn_exit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl_book;
    private javax.swing.JTable tbl_item;
    // End of variables declaration//GEN-END:variables
}
