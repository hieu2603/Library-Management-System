/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUI.BorrowTicket;

import BUS.BorrowTicketDetailBUS;
import DAO.BookDAO;
import DAO.BorrowTicketDAO;
import DAO.BorrowTicketDetailDAO;
import DTO.BookDTO;
import DTO.BorrowTicketDTO;
import DTO.BorrowTicketDetailDTO;
import DTO.MemberDTO;
import helper.Formatter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Duc3m
 */
public class GetBorrowedBookDialog extends javax.swing.JDialog {

    MemberDTO member;
    ArrayList<BorrowTicketDTO> ticketList;
    ArrayList<BorrowTicketDetailDTO> detailList;
    
    public boolean choosen = false;
    
    public GetBorrowedBookDialog(java.awt.Frame parent, boolean modal, MemberDTO member) {
        super(parent, modal);
        this.member = member;
        initComponents();
        customInit();
    }
    
    public GetBorrowedBookDialog(java.awt.Frame parent, boolean modal, MemberDTO member, ArrayList<BorrowTicketDTO> ticketList, ArrayList<BorrowTicketDetailDTO> detailList) {
        super(parent, modal);
        this.member = member;
        this.ticketList = ticketList;
        this.detailList = detailList;
        initComponents();
        customInit();
    }
    
    public void customInit() {
        setLocationRelativeTo(null);
        
        btn_choose.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    tbl_isbn.getValueAt(tbl_isbn.getSelectedRow(), 0);
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
        
        loadTicketToTable(ticketList);
    }
    
    public void loadTicketToTable(ArrayList<BorrowTicketDTO> ticketList) {
        DefaultTableModel tableModel = (DefaultTableModel) tbl_ticket.getModel();
        tableModel.setRowCount(0);
        for (BorrowTicketDTO i : ticketList) {
            tableModel.addRow(new Object[] {
                    i.getId(),
                    Formatter.getDate(i.getBorrow_date()),
                    Formatter.getDate(i.getDue_date())
            });
        }
    }
    
    public void loadBookToTable(ArrayList<BorrowTicketDetailDTO> detailList) {
        DefaultTableModel tableModel = (DefaultTableModel) tbl_isbn.getModel();
        tableModel.setRowCount(0);
        for (BorrowTicketDetailDTO i : detailList) {
            BookDTO book = BookDAO.getInstance().getByISBN(i.getIsbn());
            tableModel.addRow(new Object[] {
                    i.getIsbn(),
                    book.getTitle(),
            });
        }
    }
    
    public int getSelectedId() {
        try {
            return (int) tbl_ticket.getValueAt(tbl_ticket.getSelectedRow(), 0);
        }
        catch (Exception e){
            return -1;
        }
    }
    
    public String getSelectedISBN() {
        try {
            return (String) tbl_isbn.getValueAt(tbl_isbn.getSelectedRow(), 0);
        }
        catch (Exception e){
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_isbn = new javax.swing.JTable();
        btn_choose = new javax.swing.JButton();
        btn_exit = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_ticket = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CHỌN SÁCH");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(212, 209, 216)));

        tbl_isbn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ISBN", "Tên sách"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_isbn.setFocusable(false);
        tbl_isbn.getTableHeader().setResizingAllowed(false);
        tbl_isbn.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbl_isbn);
        if (tbl_isbn.getColumnModel().getColumnCount() > 0) {
            tbl_isbn.getColumnModel().getColumn(0).setResizable(false);
            tbl_isbn.getColumnModel().getColumn(1).setResizable(false);
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

        tbl_ticket.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã phiếu mượn", "Ngày mượn", "Ngày trả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_ticket.setFocusable(false);
        tbl_ticket.getTableHeader().setResizingAllowed(false);
        tbl_ticket.getTableHeader().setReorderingAllowed(false);
        tbl_ticket.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_ticketMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_ticket);
        if (tbl_ticket.getColumnModel().getColumnCount() > 0) {
            tbl_ticket.getColumnModel().getColumn(0).setResizable(false);
            tbl_ticket.getColumnModel().getColumn(1).setResizable(false);
            tbl_ticket.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(btn_choose, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 213, Short.MAX_VALUE)
                .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(180, 180, 180))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(294, 294, 294))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_choose, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_ticketMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ticketMousePressed
        int row = tbl_ticket.getSelectedRow();
        int id = (int) tbl_ticket.getValueAt(row, 0);
        detailList = BorrowTicketDetailBUS.getInstance().getByTicketID(detailList, id);
        loadBookToTable(detailList);
    }//GEN-LAST:event_tbl_ticketMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_choose;
    private javax.swing.JButton btn_exit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl_isbn;
    private javax.swing.JTable tbl_ticket;
    // End of variables declaration//GEN-END:variables
}
