/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.PhieuTraHangDAO;
import hethongnhathuocduocankhang.entity.PhieuTraHang;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class QuanLiPhieuTraHangGUI extends JPanel { 

    private JTextField txtTimKiem;
    private JTable table;
    private JComboBox<String> cmbTieuChiTimKiem;
    private DefaultTableModel model;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private JLabel lblTongSoDong;
    private JLabel lblSoDongChon;

    public QuanLiPhieuTraHangGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // PANEL NORTH
        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        // Panel Chức năng
        JPanel pnlNorthLeft = new JPanel();
        pnlNorthLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlNorthLeft.setBorder(new EmptyBorder(0, 0, 10, 0));
        pnlNorth.add(pnlNorthLeft, BorderLayout.WEST);
        
        // Panel Tìm kiếm
        JPanel pnlNorthRight = new JPanel();
        pnlNorthRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        cmbTieuChiTimKiem = new JComboBox<>(new String[]{
            "Mã Phiếu Trả", 
            "Mã Hóa Đơn Gốc", 
            "Mã Nhân Viên", 
            "Ngày Lập (yyyy-MM-dd)"
        });
        cmbTieuChiTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbTieuChiTimKiem.setPreferredSize(new Dimension(180, 30));

        txtTimKiem = new JTextField(20);
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        txtTimKiem.setPreferredSize(new Dimension(200, 30));
        txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                new EmptyBorder(5, 5, 5, 5)
        ));

        JPanel pnlTimKiem = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        pnlTimKiem.add(new JLabel("Tìm kiếm"));
        pnlTimKiem.add(txtTimKiem);

        pnlNorthRight.add(new JLabel("Tìm theo"));
        pnlNorthRight.add(cmbTieuChiTimKiem);
        pnlNorthRight.add(pnlTimKiem);

        pnlNorth.add(pnlNorthRight, BorderLayout.EAST);
        this.add(pnlNorth, BorderLayout.NORTH);

        // PANEL CENTER (TABLE)
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));

        String[] columnNames = {"STT", "Mã Phiếu Trả", "Mã Hóa Đơn Gốc", "Nhân Viên Lập", "Ngày Lập", "Tổng Tiền Hoàn Trả"};
        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(220, 220, 220));
        table.getTableHeader().setReorderingAllowed(false);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        // CẤU HÌNH KÍCH THƯỚC & CĂN CHỈNH CỘT
        TableColumnModel columnModel = table.getColumnModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        // 0. STT
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(0).setMaxWidth(40);
        columnModel.getColumn(0).setCellRenderer(centerRenderer);

        // 1. Mã Phiếu Trả
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(1).setMaxWidth(120);
        columnModel.getColumn(1).setCellRenderer(centerRenderer);

        // 2. Mã Hóa Đơn Gốc
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(2).setMaxWidth(120);
        columnModel.getColumn(2).setCellRenderer(centerRenderer);

        // 3. Nhân Viên Lập 
        columnModel.getColumn(3).setPreferredWidth(200);

        // 4. Ngày Lập
        columnModel.getColumn(4).setPreferredWidth(120);
        columnModel.getColumn(4).setCellRenderer(centerRenderer);
        
        // 5. Tổng Tiền Hoàn Trả 
        columnModel.getColumn(5).setPreferredWidth(120);
        columnModel.getColumn(5).setCellRenderer(rightRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(centerPanel, BorderLayout.CENTER);
        
        // PANEL SOUTH (FOOTER)
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        pnlSouth.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        Font fontFooter = new Font("Arial", Font.BOLD, 13);
        
        lblTongSoDong = new JLabel("Tổng số phiếu trả: 0");
        lblTongSoDong.setFont(fontFooter);
        lblTongSoDong.setForeground(new Color(0, 102, 204));
        
        lblSoDongChon = new JLabel("Đang chọn: 0");
        lblSoDongChon.setFont(fontFooter);
        lblSoDongChon.setForeground(new Color(204, 0, 0));
        
        pnlSouth.add(lblTongSoDong);
        pnlSouth.add(new JSeparator(JSeparator.VERTICAL));
        pnlSouth.add(lblSoDongChon);
        
        this.add(pnlSouth, BorderLayout.SOUTH);

        updateTable();
        addEvents();
    }

    private void updateTable(ArrayList<PhieuTraHang> dsPTH) {
        model.setRowCount(0);
        if (dsPTH == null) {
            lblTongSoDong.setText("Tổng số phiếu trả: 0");
            return;
        }
        int stt = 1;
        for (PhieuTraHang pth : dsPTH) {
            String tenNV = (pth.getNhanVien() != null && pth.getNhanVien().getTen() != null)
                           ? pth.getNhanVien().getHoTenDem() + " " + pth.getNhanVien().getTen() 
                           : pth.getNhanVien().getMaNV(); 

            Object[] row = {
                stt++,
                pth.getMaPhieuTraHang(),
                pth.getHoaDon().getMaHoaDon(),
                tenNV,
                pth.getNgayLapPhieuTraHang().format(formatter), 
                String.format("%,.0f VND", pth.getTongTienHoanTra()) 
            };
            model.addRow(row);
        }
        lblTongSoDong.setText("Tổng số phiếu trả: " + dsPTH.size());
    }

    private void updateTable() {
        ArrayList<PhieuTraHang> dsPTH = PhieuTraHangDAO.getAllPhieuTraHang();
        updateTable(dsPTH);
    }

    private void addEvents() {
        txtTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyTimKiem();
            }
        });
        
        cmbTieuChiTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtTimKiem.setText("");
                txtTimKiem.requestFocus();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hienThiChiTietPhieuTraHang(e);
            }
        });
        
        // Sự kiện đếm dòng chọn
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    lblSoDongChon.setText("Đang chọn: " + table.getSelectedRowCount());
                }
            }
        });
    }

    private void xuLyTimKiem() {
        String tuKhoa = txtTimKiem.getText().trim();
        String tieuChi = cmbTieuChiTimKiem.getSelectedItem().toString();
        ArrayList<PhieuTraHang> dsKetQua = new ArrayList<>();

        if (tuKhoa.isEmpty()) {
            dsKetQua = PhieuTraHangDAO.getAllPhieuTraHang();
        } else {
            try {
                switch (tieuChi) {
                    case "Mã Phiếu Trả":
                        PhieuTraHang pth = PhieuTraHangDAO.timPTHTheoMa(tuKhoa);
                        if (pth != null) {
                            dsKetQua.add(pth);
                        }
                        break;
                    case "Mã Hóa Đơn Gốc":
                        dsKetQua = PhieuTraHangDAO.timPTHTheoMaHD(tuKhoa);
                        break;
                    case "Mã Nhân Viên":
                        dsKetQua = PhieuTraHangDAO.timPTHTheoMaNV(tuKhoa);
                        break;
                    case "Ngày Lập (yyyy-MM-dd)":
                        LocalDate date = LocalDate.parse(tuKhoa); 
                        dsKetQua = PhieuTraHangDAO.timPTHTheoNgayLap(date);
                        break;
                }
            } catch (DateTimeParseException e) {
                 JOptionPane.showMessageDialog(this, "Ngày nhập phải đúng định dạng YYYY-MM-DD.", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            }
        }
        updateTable(dsKetQua); 
    }

    private void hienThiChiTietPhieuTraHang(MouseEvent e) {
        int selectRow = table.getSelectedRow();
        if (selectRow != -1) {
            String maPTH = model.getValueAt(selectRow, 1).toString(); 
            PhieuTraHang pthDaChon = PhieuTraHangDAO.timPTHTheoMa(maPTH); 

            if (pthDaChon != null && e.getClickCount() == 2) { 
                
                ChiTietPhieuTraHangGUI pnlChiTiet = new ChiTietPhieuTraHangGUI(); 
                
                pnlChiTiet.loadData(pthDaChon);

                JDialog dialog = new JDialog();
                dialog.setTitle("Danh sách chi tiết Phiếu Trả Hàng: " + pthDaChon.getMaPhieuTraHang()); 
                dialog.setModal(true);
                dialog.setResizable(true); 
                dialog.setContentPane(pnlChiTiet);
                dialog.setPreferredSize(new Dimension(900, 400));
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        }
    }
}