/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.Statistic;

import DAO.BookDAO;
import DAO.BookItemDAO;
import DAO.BorrowTicketDetailDAO;
import DAO.MemberDAO;
import DAO.ReturnTicketDetailDAO;
import DAO.StaffDAO;

/**
 *
 * @author Duc3m
 */
public class OverallStatistics extends javax.swing.JPanel {

    public StaffDAO staffDAO = new StaffDAO();
    public MemberDAO memberDAO = new MemberDAO();
    public BookItemDAO bookItemDAO = new BookItemDAO();
    public BookDAO bookDAO = new BookDAO();
    public BorrowTicketDetailDAO borrowDetailDAO = new BorrowTicketDetailDAO();
    public ReturnTicketDetailDAO returnDetailDAO = new ReturnTicketDetailDAO();
    
    OverviewCard overview_staff;
    OverviewCard overview_member;
    OverviewCard overview_book;
    OverviewCard overview_bookitem;
    OverviewCard overview_borrow;
    OverviewCard overview_return;
    
    public OverallStatistics() {
        initComponents();
        customInit();
    }
    
    public void customInit() {
        overview_staff = new OverviewCard("librarian.png", "Tổng số nhân viên", staffDAO.getAllCount() + "");
        overview_member = new OverviewCard("member.png", "Tổng số thành viên", memberDAO.getAllCount() + "");
        overview_book = new OverviewCard("book.png", "Tổng số đầu sách", bookDAO.getAllCount() + "");
        overview_bookitem = new OverviewCard("bookshelf.png", "Tổng số sách", bookItemDAO.getAllCount() + "");
        overview_borrow = new OverviewCard("borrow.png", "Tổng số sách đã mượn", borrowDetailDAO.getAllCount() + "");
        overview_return = new OverviewCard("return.png", "Tổng số sách đã trả", returnDetailDAO.getAllCount() + "");
        
        overview_staff.setBounds(50, 40, 400, 200);
        overview_member.setBounds(510, 40, 400, 200);
        overview_book.setBounds(50, 280, 400, 200);
        overview_bookitem.setBounds(510, 280, 400, 200);
        overview_borrow.setBounds(50, 520, 400, 200);
        overview_return.setBounds(510, 520, 400, 200);
        
        add(overview_staff);
        add(overview_member);
        add(overview_book);
        add(overview_bookitem);
        add(overview_borrow);
        add(overview_return);
    }
    
    public void updateContent() {
        overview_staff.setContent(staffDAO.getAllCount() + "");
        overview_member.setContent(memberDAO.getAllCount() + "");
        overview_book.setContent(bookDAO.getAllCount() + "");
        overview_bookitem.setContent(bookItemDAO.getAllCount() + "");
        overview_borrow.setContent(borrowDetailDAO.getAllCount() + "");
        overview_return.setContent(returnDetailDAO.getAllCount() + "");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(980, 830));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 980, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 830, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
