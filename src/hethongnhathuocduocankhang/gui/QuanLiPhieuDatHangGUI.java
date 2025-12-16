/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import com.toedter.calendar.JDateChooser; // Import thư viện lịch
import hethongnhathuocduocankhang.dao.PhieuDatHangDAO;
import hethongnhathuocduocankhang.entity.PhieuDatHang;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent; // Import sự kiện cho lịch
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.ZoneId; // Import chuyển đổi Date -> LocalDate
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

public class QuanLiPhieuDatHangGUI extends JPanel {

    private JTextField txtTimKiem;
    private JDateChooser dcsNgayTimKiem; // Khai báo JDateChooser
    private JPanel pnlNhapLieu; // Panel chứa CardLayout
    private JTable table;
    private JComboBox<String> cmbTieuChiTimKiem;
    private DefaultTableModel model;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public QuanLiPhieuDatHangGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        JPanel pnlNorthLeft = new JPanel();
        pnlNorthLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlNorthLeft.setBorder(new EmptyBorder(0, 0, 10, 0));
        pnlNorth.add(pnlNorthLeft, BorderLayout.WEST);

        JPanel pnlNorthRight = new JPanel();
        pnlNorthRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        cmbTieuChiTimKiem = new JComboBox<>(new String[]{
            "Mã Phiếu Đặt",
            "Mã Nhà Cung Cấp",
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

        // --- BẮT ĐẦU PHẦN THÊM MỚI ---
        // 1. Khởi tạo DateChooser
        dcsNgayTimKiem = new JDateChooser();
        dcsNgayTimKiem.setDateFormatString("yyyy-MM-dd");
        dcsNgayTimKiem.setPreferredSize(new Dimension(200, 30));
        dcsNgayTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));

        // 2. Tạo Panel CardLayout để chứa text và date
        pnlNhapLieu = new JPanel(new CardLayout());
        pnlNhapLieu.add(txtTimKiem, "text");
        pnlNhapLieu.add(dcsNgayTimKiem, "date");
        // --- KẾT THÚC PHẦN THÊM MỚI ---

        JPanel pnlTimKiem = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        pnlTimKiem.add(new JLabel("Tìm kiếm"));
        // Thay vì add txtTimKiem, ta add pnlNhapLieu
        pnlTimKiem.add(pnlNhapLieu);

        pnlNorthRight.add(new JLabel("Tìm theo"));
        pnlNorthRight.add(cmbTieuChiTimKiem);
        pnlNorthRight.add(pnlTimKiem);

        pnlNorth.add(pnlNorthRight, BorderLayout.EAST);
        this.add(pnlNorth, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));

        String[] columnNames = {"Mã Phiếu Đặt", "Nhà Cung Cấp", "Nhân Viên Lập", "Ngày Lập", "Tổng Tiền"};
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

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(pnlNorth, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);

        updateTable();
        addEvents();
    }

    private void updateTable(ArrayList<PhieuDatHang> dsPDH) {
        model.setRowCount(0);
        if (dsPDH == null) {
            return;
        }
        for (PhieuDatHang pdh : dsPDH) {
            String tenNCC = (pdh.getNhaCungCap() != null && pdh.getNhaCungCap().getTenNCC() != null)
                    ? pdh.getNhaCungCap().getTenNCC()
                    : pdh.getNhaCungCap().getMaNCC();

            String tenNV = (pdh.getNhanVien() != null && pdh.getNhanVien().getTen() != null)
                    ? pdh.getNhanVien().getHoTenDem() + " " + pdh.getNhanVien().getTen()
                    : pdh.getNhanVien().getMaNV();

            Object[] row = {
                pdh.getMaPhieuDat(),
                tenNCC,
                tenNV,
                pdh.getNgayLap().format(formatter),
                String.format("%,.0f VND", pdh.getTongTien())
            };
            model.addRow(row);
        }
    }

    private void updateTable() {
        ArrayList<PhieuDatHang> dsPDH = PhieuDatHangDAO.getAllPhieuDatHang();
        updateTable(dsPDH);
    }

    private void addEvents() {
        txtTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyTimKiem();
            }
        });

        // Bắt sự kiện chọn ngày trên lịch để tự động tìm kiếm
        dcsNgayTimKiem.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                xuLyTimKiem();
            }
        });

        cmbTieuChiTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý chuyển đổi giao diện nhập liệu
                CardLayout cl = (CardLayout) pnlNhapLieu.getLayout();
                String tieuChi = cmbTieuChiTimKiem.getSelectedItem().toString();
                
                if (tieuChi.equals("Ngày Lập (yyyy-MM-dd)")) {
                    cl.show(pnlNhapLieu, "date"); // Hiện lịch
                    dcsNgayTimKiem.requestFocusInWindow();
                } else {
                    cl.show(pnlNhapLieu, "text"); // Hiện text
                    txtTimKiem.setText("");
                    txtTimKiem.requestFocus();
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hienThiChiTietPhieuDatHang(e);
            }
        });
    }

    private void xuLyTimKiem() {
        String tieuChi = cmbTieuChiTimKiem.getSelectedItem().toString();
        String tuKhoa = "";

        // Logic lấy từ khóa tùy thuộc vào loại đang hiển thị
        if (tieuChi.equals("Ngày Lập (yyyy-MM-dd)")) {
            Date date = dcsNgayTimKiem.getDate();
            if (date != null) {
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                tuKhoa = localDate.toString();
            }
        } else {
            tuKhoa = txtTimKiem.getText().trim();
        }

        ArrayList<PhieuDatHang> dsKetQua = new ArrayList<>();

        if (tuKhoa.isEmpty()) {
            dsKetQua = PhieuDatHangDAO.getAllPhieuDatHang();
        } else {
            try {
                switch (tieuChi) {
                    case "Mã Phiếu Đặt":
                        PhieuDatHang pdh = PhieuDatHangDAO.timPDHTheoMa(tuKhoa);
                        if (pdh != null) {
                            dsKetQua.add(pdh);
                        }
                        break;
                    case "Mã Nhà Cung Cấp":
                        dsKetQua = PhieuDatHangDAO.timPDHTheoMaNCC(tuKhoa);
                        break;
                    case "Mã Nhân Viên":
                        dsKetQua = PhieuDatHangDAO.timPDHTheoMaNV(tuKhoa);
                        break;
                    case "Ngày Lập (yyyy-MM-dd)":
                        // Vì tuKhoa đã được chuẩn hóa từ DateChooser nên không sợ sai format
                        LocalDate date = LocalDate.parse(tuKhoa);
                        dsKetQua = PhieuDatHangDAO.timPDHTheoNgayLap(date);
                        break;
                }
            } catch (DateTimeParseException e) {
                // Trường hợp này hiếm khi xảy ra nếu dùng DateChooser, nhưng vẫn giữ để an toàn
                JOptionPane.showMessageDialog(this, "Ngày nhập phải đúng định dạng YYYY-MM-DD.", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            }
        }
        updateTable(dsKetQua);
    }

    private void hienThiChiTietPhieuDatHang(MouseEvent e) {
        int selectRow = table.getSelectedRow();
        if (selectRow != -1) {
            String maPDH = model.getValueAt(selectRow, 0).toString();
            PhieuDatHang pdhDaChon = PhieuDatHangDAO.timPDHTheoMa(maPDH);

            if (pdhDaChon != null && e.getClickCount() == 2) {

                ChiTietPhieuDatHangGUI pnlChiTiet = new ChiTietPhieuDatHangGUI();

                pnlChiTiet.loadData(pdhDaChon);

                JDialog dialog = new JDialog();
                dialog.setTitle("Danh sách chi tiết Phiếu Đặt: " + pdhDaChon.getMaPhieuDat());
                dialog.setModal(true);
                dialog.setResizable(true); 
                dialog.setContentPane(pnlChiTiet);
                dialog.setPreferredSize(new Dimension(800, 400)); 
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        }
    }
}