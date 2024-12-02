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
    private JPanel pnlBorrowStats;
    private JLabel lblTotalBooks;
    private JLabel lblAvailableBooks;
    private JLabel lblBorrowedBooks;
    private JLabel lblCategories;
    private JLabel lblPublishers;
    private JButton btnRefresh;
    
    public StatisticPanel(Main_Frame main) {
        this.main = main;
        initComponents();
        customInit();
        loadStatistics();
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

        // Labels for statistics
        lblTotalBooks = new JLabel("Tổng số sách: 0");
        lblAvailableBooks = new JLabel("Sách có sẵn: 0");
        lblBorrowedBooks = new JLabel("Sách đang mượn: 0");
        lblCategories = new JLabel("Tổng số danh mục: 0");
        lblPublishers = new JLabel("Tổng số NXB: 0");

        // Refresh button
        btnRefresh = new JButton("Làm mới");
        btnRefresh.addActionListener(e -> loadStatistics());

        // Layout setup
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        
        // Add components to pnlBookStats
        GroupLayout statsLayout = new GroupLayout(pnlBookStats);
        pnlBookStats.setLayout(statsLayout);
        
        statsLayout.setHorizontalGroup(
            statsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(statsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalBooks)
                    .addComponent(lblAvailableBooks)
                    .addComponent(lblBorrowedBooks)
                    .addComponent(lblCategories)
                    .addComponent(lblPublishers))
                .addContainerGap())
        );
        
        statsLayout.setVerticalGroup(
            statsLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(lblTotalBooks)
            .addGap(10)
            .addComponent(lblAvailableBooks)
            .addGap(10)
            .addComponent(lblBorrowedBooks)
            .addGap(10)
            .addComponent(lblCategories)
            .addGap(10)
            .addComponent(lblPublishers)
            .addContainerGap()
        );

        // Main panel layout
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(pnlBookStats, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh))
                .addContainerGap(680, Short.MAX_VALUE))
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBookStats, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh)
                .addContainerGap(650, Short.MAX_VALUE))
        );
    }

    private void loadStatistics() {
        BookStatisticsBUS bus = new BookStatisticsBUS();
        BookStatisticsDTO stats = bus.getBookStatistics();

        lblTotalBooks.setText("Tổng số sách: " + stats.getTotalBooks());
        lblAvailableBooks.setText("Sách có sẵn: " + stats.getTotalAvailableBooks());
        lblBorrowedBooks.setText("Sách đang mượn: " + stats.getTotalBorrowedBooks());
        lblCategories.setText("Tổng số danh mục: " + stats.getTotalCategories());
        lblPublishers.setText("Tổng số NXB: " + stats.getTotalPublishers());
    }

    @SuppressWarnings("unchecked")
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
}