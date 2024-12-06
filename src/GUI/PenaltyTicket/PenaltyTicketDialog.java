/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUI.PenaltyTicket;

import BUS.BookBUS;
import BUS.BookItemBUS;
import BUS.MemberBUS;
import BUS.PenaltyBUS;
import BUS.PenaltyTicketBUS;
import BUS.PenaltyTicketDetailBUS;
import BUS.ReturnTicketBUS;
import BUS.ReturnTicketDetailBUS;
import BUS.StaffBUS;
import DAO.PenaltyDAO;
import DAO.PenaltyTicketDAO;
import DTO.BookDTO;
import DTO.MemberDTO;
import DTO.PenaltyTicketDTO;
import DTO.PenaltyTicketDetailDTO;
import DTO.ReturnTicketDTO;
import DTO.ReturnTicketDetailDTO;
import DTO.SessionManager;
import DTO.StaffDTO;
import GUI.Member.GetMemberDialog;
import GUI.ReturnTicket.GetReturnTicketDialog;
import GUI.ReturnTicket.ReturnTicketDialog;
import helper.Formatter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Duc3m
 */
public class PenaltyTicketDialog extends javax.swing.JDialog {

    String mode;
    
    PenaltyTicketDTO penaltyTicket;
    StaffDTO staff;
    MemberDTO member;
    Timestamp  penalty_date;
    ReturnTicketDTO returnTicket;
    int total_fine;
    
    PenaltyBUS penaltyBUS = new PenaltyBUS();
    PenaltyTicketBUS penaltyTicketBUS = new PenaltyTicketBUS();
    PenaltyTicketDetailBUS detailBUS = new PenaltyTicketDetailBUS();
    BookItemBUS bookItemBUS = new BookItemBUS();
    
    ArrayList<PenaltyTicketDetailDTO> detailList;
    
    public PenaltyTicketDialog(java.awt.Frame parent, boolean modal, PenaltyTicketDTO penaltyTicket, String mode) {
        super(parent, modal);
        this.penaltyTicket = penaltyTicket;
        this.mode = mode;
        initComponents();
        customInit();
    }
    
    public void customInit() {
        setLocationRelativeTo(null);
        
        btn_getTicket.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(mode.equals("view"))
                    viewTicketEvent();
                if(mode.equals("add"))
                    addTicketEvent();
            }
        });
        
        btn_save.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(mode.equals("add"))
                    addEvent();
            }
        });
        
        btn_exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
            }
        });
        
        btn_member.addActionListener((ActionEvent e) -> {
            if (jTable1.getRowCount() > 0) {
                JOptionPane.showMessageDialog(this, "Không thể đổi thành viên bây giờ!");
                return;
            }
            member = GetMemberDialog.getMember();
            if(member == null)
                return;
            txt_member.setText(member.getFull_name());
            
        });
        
        if(mode.equals("view"))
            initViewMode();
        if(mode.equals("add"))
            initAddMode();
    }
    
    public void loadDataToTable(ArrayList<PenaltyTicketDetailDTO> detailList) {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0);
        for (PenaltyTicketDetailDTO i : detailList) {
            BookDTO book = BookBUS.getInstance().getByISBN(i.getIsbn());
            tableModel.addRow(new Object[] {
                    i.getIsbn(),
                    book.getTitle(),
                    penaltyBUS.getNameFromId(i.getPenalty_id()),
                    Formatter.FormatVND(i.getFine()),
                    i.getDays_passed() + ""
            });
        }
    }
    
    public void initData() {
        member = MemberBUS.getInstance().getById(penaltyTicket.getMember_id());
        staff = StaffBUS.getInstance().getById(penaltyTicket.getStaff_id());
        
        detailList = detailBUS.getByPenaltyTicketId(penaltyTicket.getId());
    }

    public void initViewMode() {
        initData();
        txt_id.setText(penaltyTicket.getId() + "");
        txt_member.setText(member.getFull_name());
        txt_staff.setText(staff.getFullName());
        txt_ticketId.setText(penaltyTicket.getReturnTicket_id() + "");
        txt_penaltyDate.setText(Formatter.getDate(penaltyTicket.getPenalty_date()));
        txt_totalFine.setText(Formatter.FormatVND(penaltyTicket.getTotal_fine()));
        btn_member.setEnabled(false);
        btn_save.setVisible(false);
        loadDataToTable(detailList);
    }

    public void initAddMode() {
        detailList = new ArrayList<>();
        staff = SessionManager.getInstance().getLoggedInStaff();
        penalty_date = new Timestamp(System.currentTimeMillis());

        jLabel7.setText("TẠO PHIẾU PHẠT MỚI");
        lbl_id.setEnabled(false);
        txt_id.setEnabled(false);
        lbl_status.setEnabled(false);
        txt_status.setEnabled(false);

        txt_staff.setText(staff.getFullName());
        txt_penaltyDate.setText(Formatter.getDate(penalty_date));
    }
    
    public void viewTicketEvent() {
        int returnTicket_id = Integer.parseInt(txt_ticketId.getText());
        ReturnTicketDTO returnTicket = ReturnTicketBUS.getInstance().getByID(returnTicket_id);
        ReturnTicketDialog rtD = new ReturnTicketDialog(null, true, returnTicket, "view");
        rtD.setVisible(true);
    }
    
    public void addTicketEvent() {
        if (member == null) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn thành viên!");
            return;
        }
        returnTicket = GetReturnTicketDialog.getReturnTicket(member);
        if(returnTicket == null)
            return;
        txt_ticketId.setText(returnTicket.getId() + "");
        detailList.clear();
        ArrayList<ReturnTicketDetailDTO> returnTicketDetailList = ReturnTicketDetailBUS.getInstance().getByReturnTicketIdToPenalty(returnTicket.getId());
        
        int newPenaltyTicketID = PenaltyTicketDAO.getInstance().getLastID() + 1;
        for(ReturnTicketDetailDTO i : returnTicketDetailList) {
            int penalty_id = penaltyBUS.getIdFromStatus(i.getStatus());
            String isbn = i.getIsbn();
            int fine = calculateFine(i);
            int days_passed =  i.getDays_passed();
            
            PenaltyTicketDetailDTO detail = new PenaltyTicketDetailDTO(newPenaltyTicketID, penalty_id, isbn, fine, days_passed);
            detailList.add(detail);
            loadDataToTable(detailList);
            total_fine = calculateTotalFine(detailList);
            txt_totalFine.setText(Formatter.FormatVND(total_fine));
        }
    }
    
    public int calculateFine(ReturnTicketDetailDTO returnTicketDetail) {
        int result = 0;
        if(returnTicketDetail.getDays_passed()!= 0)
            result += 10000 * returnTicketDetail.getDays_passed();
        result += penaltyBUS.getFineFromStatus(returnTicketDetail.getStatus());
        if(returnTicketDetail.getStatus().equals("Mất")) {
            long price = bookItemBUS.getByISBN(returnTicketDetail.getIsbn()).getPrice();
            result += price;
        }
        return result;
    }
    
    public int calculateTotalFine(ArrayList<PenaltyTicketDetailDTO> detailList) {
        int result = 0;
        for(PenaltyTicketDetailDTO i : detailList)
            result += i.getFine();
        return result;
    }
    
    public PenaltyTicketDTO getNewPenaltyTicket() {
        int member_id = member.getId();
        int staff_id = staff.getId();
        int returnticket_id = returnTicket.getId();
        int total_fine = calculateTotalFine(detailList);
        
        return new PenaltyTicketDTO(member_id, staff_id, penalty_date, returnticket_id, total_fine);
    }
    
    public void addEvent() {
        penaltyTicket = getNewPenaltyTicket();
        if(penaltyTicketBUS.addWithDetail(penaltyTicket, detailList)) {
            JOptionPane.showMessageDialog(this, "Tạo phiếu phạt thành công!");
            dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbl_id = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_member = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_staff = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_penaltyDate = new javax.swing.JTextField();
        lbl_status = new javax.swing.JLabel();
        txt_status = new javax.swing.JTextField();
        btn_member = new javax.swing.JButton();
        lbl_id1 = new javax.swing.JLabel();
        txt_ticketId = new javax.swing.JTextField();
        btn_getTicket = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        lbl_status1 = new javax.swing.JLabel();
        txt_totalFine = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btn_save = new javax.swing.JButton();
        btn_exit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(212, 209, 216)));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(212, 209, 216)));
        jPanel2.setPreferredSize(new java.awt.Dimension(686, 80));

        lbl_id.setText("Mã");

        txt_id.setFocusable(false);

        jLabel2.setText("Thành viên");

        txt_member.setFocusable(false);
        txt_member.setPreferredSize(new java.awt.Dimension(150, 30));

        jLabel3.setText("Nhân viên");

        txt_staff.setFocusable(false);

        jLabel4.setText("Ngày phạt");

        txt_penaltyDate.setFocusable(false);

        lbl_status.setText("Trạng thái");

        txt_status.setFocusable(false);

        btn_member.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btn_member.setText("...");
        btn_member.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_member.setFocusPainted(false);
        btn_member.setMaximumSize(new java.awt.Dimension(40, 40));
        btn_member.setPreferredSize(new java.awt.Dimension(70, 40));
        btn_member.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_member.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        lbl_id1.setText("Mã phiếu trả");

        txt_ticketId.setFocusable(false);

        btn_getTicket.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btn_getTicket.setText("...");
        btn_getTicket.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_getTicket.setFocusPainted(false);
        btn_getTicket.setMaximumSize(new java.awt.Dimension(40, 40));
        btn_getTicket.setPreferredSize(new java.awt.Dimension(70, 40));
        btn_getTicket.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_getTicket.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_id))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txt_member, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_member, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txt_staff, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_id1)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txt_ticketId, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_getTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(47, 47, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(lbl_status)
                    .addComponent(txt_status, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_penaltyDate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_id)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_id, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_member, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_penaltyDate, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_member, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_id1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_staff, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(lbl_status)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_status, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btn_getTicket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_ticketId, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ISBN", "Tên sách", "Lý do phạt", "Phí phạt", "Số ngày trễ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setFocusable(false);
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("CHI TIẾT PHIẾU PHẠT");
        jLabel7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(212, 209, 216)));

        lbl_status1.setText("Tổng phí phạt");

        txt_totalFine.setFocusable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(207, 207, 207)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_status1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_totalFine, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_totalFine, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_status1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        btn_save.setBackground(new java.awt.Color(21, 154, 32));
        btn_save.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_save.setForeground(new java.awt.Color(255, 255, 255));
        btn_save.setText("Đồng ý");
        btn_save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_save.setFocusPainted(false);

        btn_exit.setBackground(new java.awt.Color(244, 67, 54));
        btn_exit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_exit.setForeground(new java.awt.Color(255, 255, 255));
        btn_exit.setText("Hủy");
        btn_exit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_exit.setFocusPainted(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_exit;
    private javax.swing.JButton btn_getTicket;
    private javax.swing.JButton btn_member;
    private javax.swing.JButton btn_save;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_id;
    private javax.swing.JLabel lbl_id1;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JLabel lbl_status1;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_member;
    private javax.swing.JTextField txt_penaltyDate;
    private javax.swing.JTextField txt_staff;
    private javax.swing.JTextField txt_status;
    private javax.swing.JTextField txt_ticketId;
    private javax.swing.JTextField txt_totalFine;
    // End of variables declaration//GEN-END:variables
}
