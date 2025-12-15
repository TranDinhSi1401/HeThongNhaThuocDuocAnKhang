/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.LichSuCaLamDAO;
import hethongnhathuocduocankhang.entity.LichSuCaLam;
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

public class QuanLiLichSuCaLamGUI extends JPanel {

    private JTextField txtTimKiem;
    private JTable table;
    private JComboBox<String> cmbTieuChiTimKiem;
    private DefaultTableModel model;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private JLabel lblTongSoDong;
    private JLabel lblSoDongChon;

    public QuanLiLichSuCaLamGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // PANEL NORTH
        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        // anel Chức năng
        JPanel pnlNorthLeft = new JPanel();
        pnlNorthLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlNorthLeft.setBorder(new EmptyBorder(0, 0, 10, 0));
        pnlNorth.add(pnlNorthLeft, BorderLayout.WEST);

        // Panel Tìm kiếm
        JPanel pnlNorthRight = new JPanel();
        pnlNorthRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        cmbTieuChiTimKiem = new JComboBox<>(new String[]{
            "Mã NV",
            "Tên Nhân Viên",
            "Mã Ca",
            "Ngày Làm (yyyy-MM-dd)"
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

        String[] columnNames = {"STT", "Mã NV", "Tên Nhân Viên", "Ngày Làm", "Mã Ca", "Giờ Vào", "Giờ Ra", "Ghi Chú"};
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

        // 0. STT
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(0).setMaxWidth(40);
        columnModel.getColumn(0).setCellRenderer(centerRenderer);

        // 1. Mã NV
        columnModel.getColumn(1).setPreferredWidth(80);
        columnModel.getColumn(1).setMaxWidth(100);
        columnModel.getColumn(1).setCellRenderer(centerRenderer);

        // 2. Tên Nhân Viên 
        columnModel.getColumn(2).setPreferredWidth(200);

        // 3. Ngày Làm
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(3).setCellRenderer(centerRenderer);

        // 4. Mã Ca
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(4).setCellRenderer(centerRenderer);

        // 5 & 6. Giờ Vào - Giờ Ra
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(5).setCellRenderer(centerRenderer);
        columnModel.getColumn(6).setPreferredWidth(100);
        columnModel.getColumn(6).setCellRenderer(centerRenderer);
        
        // 7. Ghi chú
        columnModel.getColumn(7).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(centerPanel, BorderLayout.CENTER);
        
        // PANEL SOUTH (FOOTER)
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        pnlSouth.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        Font fontFooter = new Font("Arial", Font.BOLD, 13);
        
        lblTongSoDong = new JLabel("Tổng số lịch sử ca làm: 0");
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

    private void updateTable(ArrayList<LichSuCaLam> dsLS) {
        model.setRowCount(0);
        if (dsLS == null) {
            lblTongSoDong.setText("Tổng số lịch sử ca làm: 0");
            return;
        }
        int stt = 1;
        for (LichSuCaLam ls : dsLS) {
            String tenNV = (ls.getNhanVien() != null && ls.getNhanVien().getTen() != null)
                    ? ls.getNhanVien().getHoTenDem() + " " + ls.getNhanVien().getTen()
                    : ls.getNhanVien().getMaNV();

            String maCa = (ls.getCaLam() != null) ? ls.getCaLam().getMaCa() : "N/A";

            String gioRa = (ls.getThoiGianRaCa() != null)
                    ? ls.getThoiGianRaCa().format(timeFormatter)
                    : "Chưa ra ca";

            Object[] row = {
                stt++,
                ls.getNhanVien().getMaNV(),
                tenNV,
                ls.getNgayLamViec().format(dateFormatter),
                maCa,
                ls.getThoiGianVaoCa().format(timeFormatter),
                gioRa,
                ls.getGhiChu()
            };
            model.addRow(row);
        }
        lblTongSoDong.setText("Tổng số lịch sử ca làm: " + dsLS.size());
    }

    private void updateTable() {
        ArrayList<LichSuCaLam> dsLS = LichSuCaLamDAO.getAllLichSuCaLam();
        updateTable(dsLS);
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
                hienThiChiTietLichSuCaLam(e);
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
        ArrayList<LichSuCaLam> dsKetQua = new ArrayList<>();

        if (tuKhoa.isEmpty()) {
            dsKetQua = LichSuCaLamDAO.getAllLichSuCaLam();
        } else {
            try {
                switch (tieuChi) {
                    case "Mã NV":
                        dsKetQua = LichSuCaLamDAO.timTheoMaNV(tuKhoa);
                        break;
                    case "Tên Nhân Viên":
                        dsKetQua = LichSuCaLamDAO.timTheoTenNV(tuKhoa);
                        break;
                    case "Mã Ca":
                        dsKetQua = LichSuCaLamDAO.timTheoMaCa(tuKhoa);
                        break;
                    case "Ngày Làm (yyyy-MM-dd)":
                        LocalDate date = LocalDate.parse(tuKhoa);
                        dsKetQua = LichSuCaLamDAO.timTheoNgayLam(date);
                        break;
                }
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Ngày nhập phải đúng định dạng YYYY-MM-DD.", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            }
        }
        updateTable(dsKetQua);
    }

    private void hienThiChiTietLichSuCaLam(MouseEvent e) {
        int selectRow = table.getSelectedRow();
        if (selectRow != -1 && e.getClickCount() == 2) {

            ChiTietLichSuCaLamGUI pnlChiTiet = new ChiTietLichSuCaLamGUI();
            JDialog dialog = new JDialog();
            dialog.setTitle("Thông tin chi tiết Ca Làm");
            dialog.setModal(true);
            dialog.setResizable(false);
            dialog.setContentPane(pnlChiTiet);
            dialog.pack();
            dialog.setLocationRelativeTo(null);

            String maNV = model.getValueAt(selectRow, 1).toString();
            String tenNV = model.getValueAt(selectRow, 2).toString();
            String ngayLam = model.getValueAt(selectRow, 3).toString();
            String maCa = model.getValueAt(selectRow, 4).toString();
            String gioVao = model.getValueAt(selectRow, 5).toString();
            String gioRa = model.getValueAt(selectRow, 6).toString();
            Object ghiChuObj = model.getValueAt(selectRow, 7);
            String ghiChu = (ghiChuObj != null) ? ghiChuObj.toString() : "";

            pnlChiTiet.setTxtMaNhanVien(maNV);
            pnlChiTiet.setTxtTenNhanVien(tenNV);
            pnlChiTiet.setTxtNgayLam(ngayLam);
            pnlChiTiet.setTxtMaCa(maCa);
            pnlChiTiet.setTxtGioVao(gioVao);
            pnlChiTiet.setTxtGioRa(gioRa);
            pnlChiTiet.setTxtGhiChu(ghiChu);

            pnlChiTiet.getBtnHuy().setVisible(false);
            pnlChiTiet.getBtnXacNhan().setVisible(true);
            pnlChiTiet.getBtnXacNhan().setText("Đóng");

            for (ActionListener al : pnlChiTiet.getBtnXacNhan().getActionListeners()) {
                pnlChiTiet.getBtnXacNhan().removeActionListener(al);
            }
            pnlChiTiet.getBtnXacNhan().addActionListener(l -> dialog.dispose());

            dialog.setVisible(true);
        }
    }
}