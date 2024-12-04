package GUI.Panel;

import BUS.BookStatisticsBUS;
import DTO.BookStatisticsDTO;
import GUI.Main_Frame;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class StatisticPanel extends javax.swing.JPanel {

    private Main_Frame main;
    private JPanel pnlBookStats;
    private JLabel lblTotalBorrowedBooks;
    private JLabel lblTotalBorrowTickets;
    private JLabel lblPenaltyRevenue;
    private JLabel lblTotalDamagedLostBooks;
    private JButton btnRefresh;

    public StatisticPanel(Main_Frame main) {
        this.main = main;
        initComponents();
        customInit();
        loadStatistics();
    }

    private void initComponents() {
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
    }

    private void customInit() {
        // Create Book Statistics Panel
        pnlBookStats = new JPanel();
        pnlBookStats.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Thống kê sách",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14)
        ));
        pnlBookStats.setBackground(Color.WHITE);

        // Initialize JLabels for statistics
        lblTotalBorrowedBooks = new JLabel("Tổng số sách đang mượn: 0");
        lblTotalBorrowedBooks.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        lblTotalBorrowTickets = new JLabel("Tổng số phiếu mượn: 0");
        lblTotalBorrowTickets.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        lblPenaltyRevenue = new JLabel("Doanh thu từ phiếu phạt: 0 VND");
        lblPenaltyRevenue.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        lblTotalDamagedLostBooks = new JLabel("Số sách hư hỏng/mất: 0");
        lblTotalDamagedLostBooks.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Initialize Refresh button
        btnRefresh = new JButton("Làm mới");
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRefresh.setBackground(new Color(0, 120, 215));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(e -> loadStatistics());

        // Set layout for pnlBookStats using GroupLayout
        GroupLayout statsLayout = new GroupLayout(pnlBookStats);
        pnlBookStats.setLayout(statsLayout);
        statsLayout.setAutoCreateGaps(true);
        statsLayout.setAutoCreateContainerGaps(true);

        statsLayout.setHorizontalGroup(
            statsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblTotalBorrowedBooks)
                .addComponent(lblTotalBorrowTickets)
                .addComponent(lblPenaltyRevenue)
                .addComponent(lblTotalDamagedLostBooks)
        );

        statsLayout.setVerticalGroup(
            statsLayout.createSequentialGroup()
                .addComponent(lblTotalBorrowedBooks)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalBorrowTickets)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPenaltyRevenue)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalDamagedLostBooks)
        );

        // Set layout for StatisticPanel using GroupLayout
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(pnlBookStats, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnRefresh, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(pnlBookStats)
                .addGap(20)
                .addComponent(btnRefresh)
                .addContainerGap(650, Short.MAX_VALUE)
        );
    }

    private void loadStatistics() {
        BookStatisticsBUS bus = new BookStatisticsBUS();
        BookStatisticsDTO stats = bus.getBookStatistics();

        if (stats != null) {
            lblTotalBorrowedBooks.setText("Tổng số sách đang mượn: " + stats.getTotalBorrowedBooks());
            lblTotalBorrowTickets.setText("Tổng số phiếu mượn: " + stats.getTotalBorrowTickets());
            lblPenaltyRevenue.setText("Doanh thu từ phiếu phạt: " + String.format("%.2f", stats.getPenaltyRevenue()) + " VND");
            lblTotalDamagedLostBooks.setText("Số sách hư hỏng/mất: " + stats.getTotalDamagedLostBooks());
        } else {
            lblTotalBorrowedBooks.setText("Tổng số sách đang mượn: N/A");
            lblTotalBorrowTickets.setText("Tổng số phiếu mượn: N/A");
            lblPenaltyRevenue.setText("Doanh thu từ phiếu phạt: N/A");
            lblTotalDamagedLostBooks.setText("Số sách hư hỏng/mất: N/A");
            JOptionPane.showMessageDialog(this, "Không thể tải dữ liệu thống kê.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}