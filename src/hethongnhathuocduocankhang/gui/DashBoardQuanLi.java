/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import com.orsoncharts.util.TextAnchor;
import hethongnhathuocduocankhang.bus.VaoRaCaBUS;
import hethongnhathuocduocankhang.bus.QuanLyLoBUS;
import hethongnhathuocduocankhang.dao.CaLamDAO;
import hethongnhathuocduocankhang.dao.DonViTinhDAO;
import hethongnhathuocduocankhang.dao.HoaDonDAO;
import hethongnhathuocduocankhang.dao.LichSuCaLamDAO;
import hethongnhathuocduocankhang.dao.LoSanPhamDAO;
import hethongnhathuocduocankhang.dao.NhanVienDAO;
import hethongnhathuocduocankhang.dao.SanPhamDAO;
import hethongnhathuocduocankhang.entity.CaLam;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.LichSuCaLam;
import hethongnhathuocduocankhang.entity.LoSanPham;
import hethongnhathuocduocankhang.entity.NhanVien;
import hethongnhathuocduocankhang.entity.SanPham;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.CategoryAnnotation;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;

public class DashBoardQuanLi extends javax.swing.JPanel {

    private final JPanel pnlCenter;
    private JRadioButton radThang;
    private JRadioButton radNam;
    private JComboBox<String> cmbThang;
    private JComboBox<String> cmbNam;
    private JPanel pnlNutTaiLai;
    private DefaultTableModel dtmSPSapHetHang;
    private JTable tblSPSapHetHang;
    private DefaultTableModel dtmLoSapHetHan;
    private JTable tblLoSapHetHan;
    private JLabel lblDongHo;
    private JLabel lblTenCa;
    private JLabel lblGioVaoCa;
    private JLabel lblNhanVienTruc;
    private javax.swing.Timer timer;
    private JLabel lblNgay;

    public DashBoardQuanLi() {
        initComponents();
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        try {
            NhanVien nv = NhanVienDAO.getNhanVienTheoMaNV(GiaoDienChinhGUI.getTk().getTenDangNhap().trim());
            renderThongTinNhanVien(nv);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Không có thông tin nhân viên");
        }

        setupThongTinCaLam();
        startRealTimeClock();

        pnlBieuDo.setLayout(new BorderLayout());
        pnlBieuDo.setLayout(new BorderLayout());
        pnlCenter = new JPanel(new BorderLayout());
        pnlBieuDo.add(pnlCenter, BorderLayout.CENTER);

        initBieuDo();
        initTableSPSapHetHang();
        initTableLoSapHetHan();
        initPanelThongKeNgay();
        veBieuDo(LocalDate.now().getMonthValue(), LocalDate.now().getYear(), "tháng");
        configureBtnVaoCa();
    }

    private void initBieuDo() {
        JButton btnTaiBieuDo = new JButton("Tải Biểu Đồ");
        radThang = new JRadioButton("Tháng", true);
        radNam = new JRadioButton("Năm");
        ButtonGroup tieuChiGroup = new ButtonGroup();
        tieuChiGroup.add(radThang);
        tieuChiGroup.add(radNam);

        String[] cacThang = {"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"};

        int startYear = HoaDonDAO.getNamHoaDonCuNhatVaMoiNhat().get("namCuNhat");
        int endYear = HoaDonDAO.getNamHoaDonCuNhatVaMoiNhat().get("namMoiNhat");
        int arrayLength = endYear - startYear + 1;
        String[] cacNam = new String[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            cacNam[i] = String.valueOf(startYear + i);
        }

        cmbThang = new JComboBox<>(cacThang);
        cmbThang.setSelectedItem("Tháng " + LocalDate.now().getMonthValue());
        cmbNam = new JComboBox<>(cacNam);
        cmbNam.setSelectedItem(String.valueOf(LocalDate.now().getYear()));

        pnlNutTaiLai = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlNutTaiLai.add(new JLabel("Thống kê theo: "));
        pnlNutTaiLai.add(radThang);
        pnlNutTaiLai.add(radNam);
        pnlNutTaiLai.add(Box.createHorizontalStrut(10));
        pnlNutTaiLai.add(cmbThang);
        pnlNutTaiLai.add(cmbNam);
        pnlNutTaiLai.add(Box.createHorizontalStrut(20));
        pnlNutTaiLai.add(btnTaiBieuDo);

        pnlBieuDo.add(pnlNutTaiLai, BorderLayout.NORTH);

        btnVaoCa.setOpaque(true);
        btnVaoCa.setBackground(Color.GREEN);
        btnVaoCa.setForeground(Color.WHITE);

        btnTaiBieuDo.addActionListener(e -> {
            if (radThang.isSelected()) {
                int thang = Integer.parseInt(cmbThang.getSelectedItem().toString().substring(6));
                int nam = Integer.parseInt(cmbNam.getSelectedItem().toString());
                veBieuDo(thang, nam, "tháng");
            } else {
                int nam = Integer.parseInt(cmbNam.getSelectedItem().toString());
                veBieuDo(1, nam, "năm");
            }
        });

        radThang.addActionListener(e -> {
            cmbThang.setVisible(true);
            pnlNutTaiLai.revalidate();
            pnlNutTaiLai.repaint();
        });

        radNam.addActionListener(e -> {
            cmbThang.setVisible(false);
            pnlNutTaiLai.revalidate();
            pnlNutTaiLai.repaint();
        });

        // XỬ LÝ TRẠNG THÁI NÚT KHI KHỞI ĐỘNG
        // Kiểm tra xem nhân viên có đang trong ca không để set màu nút
        try {
            String maNVHienTai = GiaoDienChinhGUI.getTk().getTenDangNhap().trim();
            LichSuCaLamDAO dao = new LichSuCaLamDAO();
            if (dao.kiemTraNhanVienDangLamViec(maNVHienTai, LocalDate.now())) {
                btnVaoCa.setText("Ra Ca");
                btnVaoCa.setBackground(Color.RED);
            } else {
                btnVaoCa.setText("Vào Ca");
                btnVaoCa.setBackground(Color.GREEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // SỰ KIỆN CLICK NÚT
//        btnVaoCa.addActionListener(e -> {
//            try {
//                String maNV = GiaoDienChinhGUI.getTk().getTenDangNhap().trim();
//                NhanVien nv = NhanVienDAO.getNhanVienTheoMaNV(maNV);
//                LocalDate ngayHienTai = LocalDate.now();
//                LocalTime gioHienTai = LocalTime.now();
//
//                String maCa = "";
//                if (gioHienTai.getHour() >= 6 && gioHienTai.getHour() < 14) {
//                    maCa = "SANG";
//                } else {
//                    maCa = "TOI";
//                }
//
//                CaLam caLam = CaLamDAO.timCaLamTheoMa(maCa);
//                if (caLam == null) {
//                    JOptionPane.showMessageDialog(this, "Không xác định được Ca Làm hiện tại (Mã ca: " + maCa + " không tồn tại)!");
//                    return;
//                }
//
//                LichSuCaLamDAO lsDAO = new LichSuCaLamDAO();
//
//                if (btnVaoCa.getText().equals("Vào Ca")) {
//                    // LOGIC VÀO CA
//                    LichSuCaLam ls = new LichSuCaLam(nv, ngayHienTai, caLam, gioHienTai, null, "");
//
//                    if (lsDAO.themLichSuCaLam(ls)) {
//                        JOptionPane.showMessageDialog(this, "Vào ca thành công lúc " + gioHienTai.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
//                        btnVaoCa.setText("Ra Ca");
//                        btnVaoCa.setBackground(Color.RED);
//                    } else {
//                        JOptionPane.showMessageDialog(this, "Lỗi: Không thể vào ca (Có thể bạn đã chấm công rồi).");
//                    }
//
//                } else {
//                    // LOGIC RA CA
//                    // Tạo giao diện nhập ghi chú
//                    JPanel pnlGhiChu = new JPanel(new BorderLayout(5, 5));
//                    pnlGhiChu.setPreferredSize(new Dimension(400, 150));
//
//                    JLabel lblLoiNhan = new JLabel("Nhập ghi chú ra ca (nếu có):");
//                    lblLoiNhan.setFont(new Font("Segoe UI", Font.BOLD, 14));
//
//                    JTextArea txtGhiChu = new JTextArea(5, 20);
//                    txtGhiChu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//                    txtGhiChu.setLineWrap(true);
//                    txtGhiChu.setWrapStyleWord(true);
//
//                    JScrollPane scrollGhiChu = new JScrollPane(txtGhiChu);
//
//                    pnlGhiChu.add(lblLoiNhan, BorderLayout.NORTH);
//                    pnlGhiChu.add(scrollGhiChu, BorderLayout.CENTER);
//
//                    // Hiển thị hộp thoại nhập
//                    int inputResult = JOptionPane.showConfirmDialog(
//                            this, pnlGhiChu, "Ghi chú Ra Ca",
//                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
//                    );
//
//                    // Nếu bấm Cancel thì thoát
//                    if (inputResult != JOptionPane.OK_OPTION) {
//                        return;
//                    }
//
//                    String ghiChu = txtGhiChu.getText().trim();
//
//                    // Hiện hộp thoại xác nhận cuối cùng
//                    int confirm = JOptionPane.showConfirmDialog(this,
//                            "Bạn có chắc chắn muốn kết thúc ca làm việc?",
//                            "Xác nhận ra ca", JOptionPane.YES_NO_OPTION);
//
//                    if (confirm == JOptionPane.YES_OPTION) {
//                        if (lsDAO.capNhatRaCa(maNV, maCa, ngayHienTai, gioHienTai, ghiChu)) {
//                            JOptionPane.showMessageDialog(this, "Ra ca thành công!");
//                            btnVaoCa.setText("Vào Ca");
//                            btnVaoCa.setBackground(Color.GREEN);
//                        } else {
//                            JOptionPane.showMessageDialog(this, "Lỗi: Không tìm thấy phiên làm việc để ra ca.");
//                        }
//                    }
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu!");
//            }
//        });
    }

    private void configureBtnVaoCa() {
        btnVaoCa.setOpaque(true);
        // Khởi tạo BUS
        VaoRaCaBUS lsBUS = new VaoRaCaBUS();
        String maNV = GiaoDienChinhGUI.getTk().getTenDangNhap().trim();

        // 1. XỬ LÝ TRẠNG THÁI NÚT KHI KHỞI ĐỘNG
        if (lsBUS.kiemTraDangLamViec(maNV)) {
            btnVaoCa.setText("Ra Ca");
            btnVaoCa.setBackground(Color.RED);
        } else {
            btnVaoCa.setText("Vào Ca");
            btnVaoCa.setBackground(Color.GREEN);
            btnVaoCa.setForeground(Color.WHITE);
        }

        // 2. XÓA CÁC LISTENER CŨ (tránh bị lặp sự kiện khi reload)
        for (java.awt.event.ActionListener al : btnVaoCa.getActionListeners()) {
            btnVaoCa.removeActionListener(al);
        }

        // 3. SỰ KIỆN CLICK NÚT MỚI
        btnVaoCa.addActionListener(e -> {
            try {
                // Lấy thông tin Ca làm hiện tại từ BUS
                CaLam caLam = lsBUS.getCaLamHienTai();

                if (caLam == null) {
                    JOptionPane.showMessageDialog(this, "Không xác định được Ca Làm hiện tại (hoặc lỗi kết nối)!");
                    return;
                }

                if (btnVaoCa.getText().equals("Vào Ca")) {
                    // --- LOGIC VÀO CA ---

                    // Hộp thoại xác nhận (GUI giữ quyền hiển thị tương tác)
                    int confirmVaoCa = JOptionPane.showConfirmDialog(this,
                            "Bạn có chắc chắn muốn BẮT ĐẦU ca làm việc (" + caLam.getTenCa() + ") không?",
                            "Xác nhận vào ca",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (confirmVaoCa != JOptionPane.YES_OPTION) {
                        return;
                    }

                    // Gọi BUS để xử lý dữ liệu
                    if (lsBUS.xuLyVaoCa(maNV, caLam)) {
                        JOptionPane.showMessageDialog(this, "Vào ca thành công lúc " + java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                        btnVaoCa.setText("Ra Ca");
                        btnVaoCa.setBackground(Color.RED);
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi: Không thể vào ca (Có thể bạn đã chấm công rồi).");
                    }

                } else {
                    // --- LOGIC RA CA ---

                    // GUI xử lý nhập liệu ghi chú
                    JPanel pnlGhiChu = new JPanel(new BorderLayout(5, 5));
                    pnlGhiChu.setPreferredSize(new Dimension(400, 150));
                    JLabel lblLoiNhan = new JLabel("Nhập ghi chú ra ca (nếu có):");
                    lblLoiNhan.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    JTextArea txtGhiChu = new JTextArea(5, 20);
                    txtGhiChu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    txtGhiChu.setLineWrap(true);
                    txtGhiChu.setWrapStyleWord(true);
                    JScrollPane scrollGhiChu = new JScrollPane(txtGhiChu);
                    pnlGhiChu.add(lblLoiNhan, BorderLayout.NORTH);
                    pnlGhiChu.add(scrollGhiChu, BorderLayout.CENTER);

                    int inputResult = JOptionPane.showConfirmDialog(
                            this, pnlGhiChu, "Ghi chú Ra Ca",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
                    );

                    if (inputResult != JOptionPane.OK_OPTION) {
                        return;
                    }

                    String ghiChu = txtGhiChu.getText().trim();

                    int confirmRaCa = JOptionPane.showConfirmDialog(this,
                            "Bạn có chắc chắn muốn kết thúc ca làm việc?",
                            "Xác nhận ra ca", JOptionPane.YES_NO_OPTION);

                    if (confirmRaCa == JOptionPane.YES_OPTION) {
                        // Gọi BUS để xử lý dữ liệu
                        if (lsBUS.xuLyRaCa(maNV, caLam, ghiChu)) {
                            JOptionPane.showMessageDialog(this, "Ra ca thành công!");
                            btnVaoCa.setText("Vào Ca");
                            btnVaoCa.setBackground(Color.GREEN);
                        } else {
                            JOptionPane.showMessageDialog(this, "Lỗi: Không tìm thấy phiên làm việc để ra ca.");
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu!");
            }
        });
    }

    private void setupThongTinCaLam() {
        jPanel17.removeAll();
        jPanel17.setLayout(new BorderLayout());
        jPanel17.setBackground(Color.WHITE);

        // Panel Đồng hồ
        JPanel pnlClock = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlClock.setBackground(Color.WHITE);
        lblDongHo = new JLabel("00:00:00");
        lblDongHo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblDongHo.setForeground(new Color(0, 153, 51));
        pnlClock.add(lblDongHo);

        // Panel Thông tin chi tiết
        JPanel pnlInfo = new JPanel();
        pnlInfo.setLayout(new BoxLayout(pnlInfo, BoxLayout.Y_AXIS));
        pnlInfo.setBackground(Color.WHITE);
        pnlInfo.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 0));

        lblNgay = new JLabel("Đang tải ngày...");
        styleLabelCaLam(lblNgay);
        lblNgay.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNgay.setForeground(new Color(0, 102, 204));

        lblTenCa = new JLabel("Ca: ...");
        lblGioVaoCa = new JLabel("Bắt đầu: ...");
        styleLabelCaLam(lblTenCa);
        styleLabelCaLam(lblGioVaoCa);

        String tenNV = "";
        if (lblHoTen != null) {
            tenNV = lblHoTen.getText();
        }

        lblNhanVienTruc = new JLabel("NV: " + tenNV);
        styleLabelCaLam(lblNhanVienTruc);
        lblNhanVienTruc.setFont(new Font("Segoe UI", Font.BOLD, 15)); // Chữ đậm
        lblNhanVienTruc.setForeground(new Color(204, 0, 0)); // Màu đỏ nổi bật

        pnlInfo.add(lblNgay);
        pnlInfo.add(Box.createVerticalStrut(5));
        pnlInfo.add(lblTenCa);
        pnlInfo.add(Box.createVerticalStrut(5));
        pnlInfo.add(lblGioVaoCa);
        pnlInfo.add(Box.createVerticalStrut(5));
        pnlInfo.add(lblNhanVienTruc);

        jPanel17.add(pnlClock, BorderLayout.NORTH);
        jPanel17.add(pnlInfo, BorderLayout.CENTER);

        jPanel17.revalidate();
        jPanel17.repaint();
    }

    // Hàm phụ trợ để set font nhanh
    private void styleLabelCaLam(JLabel lbl) {
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lbl.setForeground(new Color(51, 51, 51));
    }

    private void startRealTimeClock() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy", new java.util.Locale("vi", "VN"));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        timer = new javax.swing.Timer(1000, e -> {
            java.time.LocalDateTime now = java.time.LocalDateTime.now();

            // Cập nhật đồng hồ
            lblDongHo.setText(now.format(timeFormatter));

            // Cập nhật Ngày
            lblNgay.setText(now.format(dateFormatter));

            // Logic xác định ca
            int hour = now.getHour();
            if (hour >= 6 && hour < 14) {
                lblTenCa.setText("Ca hiện tại: Ca Sáng");
                lblGioVaoCa.setText("Thời gian: 07:00 - 14:00");
            } else if (hour >= 14 && hour < 22) {
                lblTenCa.setText("Ca hiện tại: Ca Chiều");
                lblGioVaoCa.setText("Thời gian: 14:00 - 21:00");
            } else {
                lblTenCa.setText("Ca hiện tại: Ngoài giờ");
                lblGioVaoCa.setText("Thời gian: --:--");
            }
        });
        timer.start();
    }

    private void initTableSPSapHetHang() {
        JPanel pnlCardSPSapHetHang = new JPanel(new BorderLayout());
        pnlCardSPSapHetHang.setBackground(Color.WHITE);
        pnlCardSPSapHetHang.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JPanel pnlTieuDe = new JPanel(new BorderLayout(10, 10));
        pnlTieuDe.setBackground(new Color(245, 247, 250));
        pnlTieuDe.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTieuDe = new JLabel("Sản phẩm sắp hết hàng");
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pnlTieuDe.add(lblTieuDe, BorderLayout.WEST);

        String[] tenCot = {"STT", "Mã SP", "Tên SP", "ĐVT", "Tồn Kho", "Tồn min"};
        dtmSPSapHetHang = new DefaultTableModel(new Object[][]{}, tenCot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Map<SanPham, Integer> dsSPSapHetHang = SanPhamDAO.getSPSapHetHang();
        int stt = 0;
        for (Map.Entry<SanPham, Integer> i : dsSPSapHetHang.entrySet()) {
            stt++;
            DonViTinh dvtCB = null;
            ArrayList<DonViTinh> dsDVTSP = DonViTinhDAO.getDonViTinhTheoMaSP(i.getKey().getMaSP());
            for (DonViTinh d : dsDVTSP) {
                if (d.isDonViTinhCoBan()) {
                    dvtCB = d;
                    break;
                }
            }

            Object[] row = {
                stt + "",
                i.getKey().getMaSP(),
                i.getKey().getTen(),
                dvtCB.getTenDonVi(),
                i.getValue(),
                i.getKey().getTonToiDa()
            };
            dtmSPSapHetHang.addRow(row);
        }

        DefaultTableCellRenderer rendererTonKhoDo = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // MÀU ĐỎ
                c.setForeground(Color.RED);

                // Định dạng hiển thị (Căn phải, Định dạng số)
                if (value instanceof Number) {
                    int sl = ((Number) value).intValue();
                    setText(String.format("%,d", sl)); // Định dạng số có dấu phẩy
                } else {
                    // Nếu không phải Number, hiển thị giá trị mặc định
                    setText(value != null ? value.toString() : "");
                }

                setHorizontalAlignment(JLabel.RIGHT); // Căn phải
                setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // Đệm bên phải

                return c;
            }
        };

        tblSPSapHetHang = new JTable(dtmSPSapHetHang);
        styleTable(tblSPSapHetHang);

        tblSPSapHetHang.getColumnModel().getColumn(0).setCellRenderer(getCenterRenderer());
        tblSPSapHetHang.getColumnModel().getColumn(1).setCellRenderer(getLeftRenderer());
        tblSPSapHetHang.getColumnModel().getColumn(2).setCellRenderer(getLeftRenderer());
        tblSPSapHetHang.getColumnModel().getColumn(3).setCellRenderer(getRightRenderer());
        tblSPSapHetHang.getColumnModel().getColumn(4).setCellRenderer(rendererTonKhoDo);
        tblSPSapHetHang.getColumnModel().getColumn(5).setCellRenderer(getRightRenderer());

        tblSPSapHetHang.getColumnModel().getColumn(0).setPreferredWidth(10);   // STT
        tblSPSapHetHang.getColumnModel().getColumn(1).setPreferredWidth(50);  // Mã SP
        tblSPSapHetHang.getColumnModel().getColumn(2).setPreferredWidth(200);  // Tên SP
        tblSPSapHetHang.getColumnModel().getColumn(3).setPreferredWidth(30);   // ĐVT
        tblSPSapHetHang.getColumnModel().getColumn(4).setPreferredWidth(50);   // Tồn Kho
        tblSPSapHetHang.getColumnModel().getColumn(5).setPreferredWidth(50);  // Tồn tối đa

        JScrollPane scrollPane = new JScrollPane(tblSPSapHetHang);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        pnlCardSPSapHetHang.add(pnlTieuDe, BorderLayout.NORTH);
        pnlCardSPSapHetHang.add(scrollPane, BorderLayout.CENTER);

        jPanel19.removeAll();
        jPanel19.setLayout(new BorderLayout());
        jPanel19.add(pnlCardSPSapHetHang, BorderLayout.CENTER);
        jPanel19.revalidate();
        jPanel19.repaint();
    }

    private void initTableLoSapHetHan() {
        JPanel pnlCardLoSapHetHan = new JPanel(new BorderLayout());
        pnlCardLoSapHetHan.setBackground(Color.WHITE);
        pnlCardLoSapHetHan.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        // Header Panel
        JPanel pnlTieuDe = new JPanel(new BorderLayout(10, 10));
        pnlTieuDe.setBackground(new Color(245, 247, 250));
        pnlTieuDe.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTieuDe = new JLabel("Lô sắp hết hạn");
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pnlTieuDe.add(lblTieuDe, BorderLayout.WEST);

        // Table Setup
        String[] tenCot = {"STT", "Mã lô", "Mã SP", "Tên SP", "SL", "ĐVT", "NHH"};
        dtmLoSapHetHan = new DefaultTableModel(new Object[][]{}, tenCot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) {
                    return Integer.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };

        tblLoSapHetHan = new JTable(dtmLoSapHetHan);
        styleTable(tblLoSapHetHan);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Load Dữ liệu
        ArrayList<LoSanPham> dsLo = LoSanPhamDAO.dsLoSanPham();
        ArrayList<LoSanPham> dsLoSapHetHan = (ArrayList<LoSanPham>) new QuanLyLoBUS().thongKe(dsLo).get("dsLoSapHetHan");
        int stt = 0;
        for (LoSanPham i : dsLoSapHetHan) {
            stt++;
            DonViTinh dvtCB = null;
            ArrayList<DonViTinh> dsDVTSP = DonViTinhDAO.getDonViTinhTheoMaSP(i.getSanPham().getMaSP());
            for (DonViTinh d : dsDVTSP) {
                if (d.isDonViTinhCoBan()) {
                    dvtCB = d;
                    break;
                }
            }

            Object[] row = {
                stt + "",
                i.getMaLoSanPham(),
                i.getSanPham().getMaSP(),
                SanPhamDAO.timSPTheoMa(i.getSanPham().getMaSP()).getTen(),
                i.getSoLuong(),
                (dvtCB != null ? dvtCB.getTenDonVi() : ""),
                i.getNgayHetHan().format(formatter) + ""
            };
            dtmLoSapHetHan.addRow(row);
        }

        DefaultTableCellRenderer rendererSoLuong = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value instanceof Number) {
                    int sl = ((Number) value).intValue();
                    setText(String.format("%,d", sl));
                }
                setHorizontalAlignment(JLabel.RIGHT);
                setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
                return c;
            }
        };

        // Gán Renderer
        tblLoSapHetHan.getColumnModel().getColumn(0).setCellRenderer(getCenterRenderer());
        tblLoSapHetHan.getColumnModel().getColumn(1).setCellRenderer(getLeftRenderer());
        tblLoSapHetHan.getColumnModel().getColumn(3).setCellRenderer(getLeftRenderer());
        tblLoSapHetHan.getColumnModel().getColumn(4).setCellRenderer(rendererSoLuong); // Cột SL
        tblLoSapHetHan.getColumnModel().getColumn(6).setCellRenderer(getCenterRenderer()); // NHH

        // Độ rộng cột
        tblLoSapHetHan.getColumnModel().getColumn(0).setPreferredWidth(10);     //STT
        tblLoSapHetHan.getColumnModel().getColumn(1).setPreferredWidth(150);    //Mã lô
        tblLoSapHetHan.getColumnModel().getColumn(2).setPreferredWidth(50);    //Mã SP
        tblLoSapHetHan.getColumnModel().getColumn(3).setPreferredWidth(90);     //Tên SP
        tblLoSapHetHan.getColumnModel().getColumn(4).setPreferredWidth(20);    //SL
        tblLoSapHetHan.getColumnModel().getColumn(5).setPreferredWidth(25);    //ĐVT
        tblLoSapHetHan.getColumnModel().getColumn(6).setPreferredWidth(80);    //NHH

        JScrollPane scrollPane = new JScrollPane(tblLoSapHetHan);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        pnlCardLoSapHetHan.add(pnlTieuDe, BorderLayout.NORTH);
        pnlCardLoSapHetHan.add(scrollPane, BorderLayout.CENTER);

        jPanel20.removeAll();
        jPanel20.setPreferredSize(new Dimension(500, 0));
        jPanel20.setLayout(new BorderLayout());
        jPanel20.add(pnlCardLoSapHetHan, BorderLayout.CENTER);
        jPanel20.revalidate();
        jPanel20.repaint();
    }

    // CÁC HÀM TIỆN ÍCH STYLE CHUNG CHO TABLE
    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(35);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(153, 204, 255)); // màu sắc dòng được chọn

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setForeground(new Color(102, 102, 102));
        header.setBackground(new Color(245, 247, 250));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
        header.setOpaque(false);

        table.setAutoCreateRowSorter(true);
    }

    private DefaultTableCellRenderer getRightRenderer() {
        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(JLabel.RIGHT);
        right.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        return right;
    }

    private DefaultTableCellRenderer getCenterRenderer() {
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        return center;
    }

    private DefaultTableCellRenderer getLeftRenderer() {
        DefaultTableCellRenderer left = new DefaultTableCellRenderer();
        left.setHorizontalAlignment(JLabel.LEFT);
        left.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        return left;
    }

    private void veBieuDo(int thang, int nam, String tieuChi) {
        if (tieuChi.equals("tháng")) {
            LocalDate hienTai = LocalDate.of(nam, thang, 1);
            // B1: Lấy dữ liệu từ DAO
            Map<Integer, Double> data = HoaDonDAO.getDoanhThuTungNgayTrongThang(hienTai);

            // B2: Tạo Dataset (Đầu vào cho JFreeChart)
            DefaultCategoryDataset dataset = taoDataset(data);

            // B3: Tạo đối tượng JFreeChart đã tùy chỉnh
            JFreeChart chart = taoBieuDoCoBan(dataset, tieuChi, thang, nam);

            // B4: TẠO CHARTPANEL
            // ChartPanel là một component Swing (giống JPanel)dùng để chứa đối tượng JFreeChart
            ChartPanel chartPanel = new ChartPanel(chart);

            // B5: THÊM CHARTPANEL VÀO PNLCENTER
            pnlCenter.removeAll(); // Xóa biểu đồ cũ (nếu có)
            pnlCenter.add(chartPanel, BorderLayout.CENTER); // Thêm biểu đồ mới
            chartPanel.addChartMouseListener(new CustomChartMouseListener(chart, tieuChi));
            pnlCenter.revalidate(); // Yêu cầu vẽ lại layout
            pnlCenter.repaint();    // Yêu cầu vẽ lại đồ họa
        } else {
            LocalDate thoiGian = LocalDate.of(nam, thang, 1);
            // B1: Lấy dữ liệu từ DAO
            Map<Integer, Double> data = HoaDonDAO.getDoanhThuTungThangTrongNam(thoiGian);

            // B2: Tạo Dataset (Đầu vào cho JFreeChart)
            DefaultCategoryDataset dataset = taoDataset(data);

            // B3: Tạo đối tượng JFreeChart đã tùy chỉnh
            JFreeChart chart = taoBieuDoCoBan(dataset, tieuChi, thang, nam);

            // B4: TẠO CHARTPANEL
            // ChartPanel là một component Swing (giống JPanel) dùng để chứa đối tượng JFreeChart
            ChartPanel chartPanel = new ChartPanel(chart);

            // B5: THÊM CHARTPANEL VÀO PNLCENTER
            pnlCenter.removeAll(); // Xóa biểu đồ cũ (nếu có)
            pnlCenter.add(chartPanel, BorderLayout.CENTER); // Thêm biểu đồ mới
            chartPanel.addChartMouseListener(new CustomChartMouseListener(chart, tieuChi));
            pnlCenter.revalidate(); // Yêu cầu vẽ lại layout
            pnlCenter.repaint();    // Yêu cầu vẽ lại đồ họa
        }
    }

    private double giaTriKPI = 0.0;

    private DefaultCategoryDataset taoDataset(Map<Integer, Double> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String series = "Doanh thu";
        for (Map.Entry<Integer, Double> entry : data.entrySet()) {
            String category = "" + entry.getKey();
            dataset.addValue(entry.getValue(), series, category);
        }
        return dataset;
    }

    private JFreeChart taoBieuDoCoBan(CategoryDataset dataset, String tieuChi, int thang, int nam) {
        if (tieuChi.equals("tháng")) {

            // Tạo biểu đồ cơ bản
            JFreeChart barChart = ChartFactory.createBarChart(
                    "Doanh thu " + tieuChi + " " + thang + " " + "năm" + " " + nam,
                    "Ngày", "Doanh thu (VND)",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false, true, false // Tắt Legend, Bật Tooltips
            );

            // Lấy Plot (khu vực vẽ) để tùy chỉnh
            CategoryPlot plot = barChart.getCategoryPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

            // Thêm đường KPI (ValueMarker)
//            ValueMarker kpiMarker = new ValueMarker(giaTriKPI);
//            kpiMarker.setPaint(Color.BLUE);
//            kpiMarker.setStroke(new BasicStroke( // Cài đặt nét đứt
//                    2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
//                    1.0f, new float[]{6.0f, 6.0f}, 0.0f
//            ));
//            plot.addRangeMarker(kpiMarker); // Thêm đường KPI vào trục Y (Range)
            ToMauCot rendererThang = new ToMauCot(giaTriKPI); //Tạo renderer
            plot.setRenderer(rendererThang); // Gán renderer cho plot

            return barChart;

        } else if (tieuChi.equals("năm")) {
            // Tạo biểu đồ cơ bản
            JFreeChart barChart = ChartFactory.createBarChart(
                    "Doanh thu " + tieuChi + " " + nam,
                    "Tháng", "Doanh thu (VND)",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false, true, false // Tắt Legend, Bật Tooltips
            );

            // Lấy Plot (khu vực vẽ) để tùy chỉnh
            CategoryPlot plot = barChart.getCategoryPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

//            // Thêm đường KPI (ValueMarker)
//            ValueMarker kpiMarker = new ValueMarker(kpiNam); // Dùng KPI năm
//            kpiMarker.setPaint(Color.BLUE);
//            kpiMarker.setStroke(new BasicStroke(
//                    2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
//                    1.0f, new float[]{6.0f, 6.0f}, 0.0f
//            ));
//            plot.addRangeMarker(kpiMarker);
            // *** GÁN RENDERER (CHỈ CẦN 2 DÒNG NÀY) ***
            ToMauCot rendererNam = new ToMauCot(giaTriKPI);
            plot.setRenderer(rendererNam); // Gán renderer cho plot

            return barChart;
        }
        return null;
    }

    public void renderThongTinNhanVien(NhanVien nv) {
        lblMaNV.setText(nv.getMaNV());
        lblHoTen.setText(nv.getHoTenDem() + " " + nv.getTen());
        if (nv.isGioiTinh()) {
            lblGioiTinh.setText("Nam");
        } else {
            lblGioiTinh.setText("Nữ");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = nv.getNgaySinh().format(formatter);
        lblNgaySinh.setText(formattedDate);
        lblChucVu.setText(GiaoDienChinhGUI.getTk().isQuanLy() ? "Quản lý" : "Nhân viên");
        lblTrangThai.setText(nv.isNghiViec() ? "Nghỉ việc" : "Đang làm việc");
        lblSdt.setText(nv.getSdt());
    }

//    public void renderThongTinCaLam() {
//        LocalDate today = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        String formattedDate = today.format(formatter);
//
//        lblThongTinCaLam.setText("Ca Làm" + " Ngày " + formattedDate);
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnVaoCa = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblMaNV = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblHoTen = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblNgaySinh = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblChucVu = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblGioiTinh = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblTrangThai = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblSdt = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        pnlBieuDo = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout(10, 10));

        jPanel1.setPreferredSize(new java.awt.Dimension(808, 220));
        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(360, 220));
        jPanel2.setLayout(new java.awt.BorderLayout(10, 10));

        btnVaoCa.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnVaoCa.setText("Vào Ca");
        btnVaoCa.setPreferredSize(new java.awt.Dimension(72, 60));
        jPanel2.add(btnVaoCa, java.awt.BorderLayout.PAGE_START);

        jPanel17.setLayout(new java.awt.BorderLayout());

        jPanel22.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Thông tin ca làm");
        jPanel22.add(jLabel11);

        jPanel17.add(jPanel22, java.awt.BorderLayout.PAGE_START);

        jPanel2.add(jPanel17, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.LINE_END);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(220, 220));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/profile1.png"))); // NOI18N
        jPanel4.add(jLabel1, new java.awt.GridBagConstraints());

        jPanel3.add(jPanel4, java.awt.BorderLayout.LINE_START);

        jPanel7.setBackground(new java.awt.Color(245, 245, 245));
        jPanel7.setPreferredSize(new java.awt.Dimension(200, 220));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.Y_AXIS));

        jPanel8.setForeground(new java.awt.Color(245, 245, 245));
        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("Mã nhân viên:");
        jPanel8.add(jLabel2);

        lblMaNV.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblMaNV.setForeground(new java.awt.Color(51, 51, 51));
        lblMaNV.setText("NV-XXXXX");
        jPanel8.add(lblMaNV);

        jPanel8.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        jPanel7.add(jPanel8);

        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setText("Họ tên:");
        jPanel9.add(jLabel3);

        lblHoTen.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblHoTen.setForeground(new java.awt.Color(51, 51, 51));
        lblHoTen.setText("Nguyễn Văn A");
        jPanel9.add(lblHoTen);

        jPanel7.add(jPanel9);

        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel5.setText("Ngày sinh:");
        jPanel11.add(jLabel5);

        lblNgaySinh.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblNgaySinh.setForeground(new java.awt.Color(51, 51, 51));
        lblNgaySinh.setText("XX/XX/XXXX");
        jPanel11.add(lblNgaySinh);

        jPanel7.add(jPanel11);

        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setText("Chức vụ:");
        jPanel12.add(jLabel6);

        lblChucVu.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblChucVu.setForeground(new java.awt.Color(51, 51, 51));
        lblChucVu.setText("Nhân Viên hoặc Quản Lý");
        jPanel12.add(lblChucVu);

        jPanel7.add(jPanel12);

        jPanel3.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel15.setBackground(new java.awt.Color(245, 245, 245));
        jPanel15.setPreferredSize(new java.awt.Dimension(240, 0));
        jPanel15.setLayout(new javax.swing.BoxLayout(jPanel15, javax.swing.BoxLayout.Y_AXIS));

        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setText("Giới Tính:");
        jPanel10.add(jLabel4);

        lblGioiTinh.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblGioiTinh.setForeground(new java.awt.Color(51, 51, 51));
        lblGioiTinh.setText("Nam hoặc Nữ");
        jPanel10.add(lblGioiTinh);

        jPanel10.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        jPanel15.add(jPanel10);

        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setText("Trạng Thái:");
        jPanel13.add(jLabel7);

        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTrangThai.setForeground(new java.awt.Color(51, 51, 51));
        lblTrangThai.setText("Đang làm việc");
        jPanel13.add(lblTrangThai);

        jPanel15.add(jPanel13);

        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setText("SĐT:");
        jPanel14.add(jLabel8);

        lblSdt.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblSdt.setForeground(new java.awt.Color(51, 51, 51));
        lblSdt.setText("0XXXXXXXXX");
        jPanel14.add(lblSdt);

        jPanel15.add(jPanel14);

        jPanel16.setPreferredSize(new java.awt.Dimension(19, 32));
        jPanel16.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel9.setText("   ");
        jPanel16.add(jLabel9);

        jPanel15.add(jPanel16);

        jPanel3.add(jPanel15, java.awt.BorderLayout.LINE_END);

        jPanel18.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Thông tin nhân viên");
        jPanel18.add(jLabel10);

        jPanel3.add(jPanel18, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(808, 220));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.X_AXIS));
        jPanel5.add(jPanel19);

        jPanel5.add(Box.createHorizontalStrut(10));

        jPanel5.add(jPanel20);

        jPanel5.add(Box.createHorizontalStrut(10));

        jPanel5.add(jPanel21);

        add(jPanel5, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout pnlBieuDoLayout = new javax.swing.GroupLayout(pnlBieuDo);
        pnlBieuDo.setLayout(pnlBieuDoLayout);
        pnlBieuDoLayout.setHorizontalGroup(
            pnlBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1100, Short.MAX_VALUE)
        );
        pnlBieuDoLayout.setVerticalGroup(
            pnlBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 185, Short.MAX_VALUE)
        );

        add(pnlBieuDo, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVaoCa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lblChucVu;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblNgaySinh;
    private javax.swing.JLabel lblSdt;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JPanel pnlBieuDo;
    // End of variables declaration//GEN-END:variables

    /**
     * Lớp lắng nghe di chuột Dùng Annotation (chú thích)
     */
    class CustomChartMouseListener implements ChartMouseListener {

        private JFreeChart chart;
        private CategoryPlot plot;
        private String tieuChi;

        // Lưu lại annotation cũ để xóa
        private CategoryTextAnnotation lastAnnotation;

        private NumberFormat currencyFormat;

        public CustomChartMouseListener(JFreeChart chart, String tieuChi) {
            this.chart = chart;
            this.plot = chart.getCategoryPlot();
            this.tieuChi = tieuChi;
            this.currencyFormat = NumberFormat.getCurrencyInstance();
            this.lastAnnotation = null;
        }

        @Override
        public void chartMouseMoved(ChartMouseEvent event) {
            // Xóa "bảng nhỏ" cũ đi (nếu có)
            if (lastAnnotation != null) {
                plot.removeAnnotation(lastAnnotation);
                lastAnnotation = null;
            }

            ChartEntity entity = event.getEntity();

            // Kiểm tra xem chuột có đang nằm trên MỘT CỘT không
            if (entity instanceof CategoryItemEntity) {
                CategoryItemEntity itemEntity = (CategoryItemEntity) entity;

                // Lấy dữ liệu của cột đó
                Comparable categoryKey = itemEntity.getColumnKey(); // (VD: "1", "2")
                Number value = itemEntity.getDataset().getValue(
                        itemEntity.getRowKey(),
                        itemEntity.getColumnKey()
                );

                // TẠO VÀ ĐỊNH DẠNG BẢNG NHỎ (Annotation)
                // 1. Tạo nội dung (ví dụ: Ngày 1: 5.000.000 đ)
                String prefix = this.tieuChi.equals("tháng") ? "Ngày " : "Tháng ";
                String text = prefix + categoryKey.toString() + ": "
                        + currencyFormat.format(value.doubleValue());

                // Tạo Annotation (bảng nhỏ)
                // Nó sẽ xuất hiện tại tọa độ (categoryKey, value)
                CategoryTextAnnotation annotation = new CategoryTextAnnotation(
                        text, // Nội dung
                        categoryKey, // Category (Ngày/Tháng)
                        value.doubleValue() // Value (Doanh thu)
                );

                // Tùy chỉnh bảng nhỏ
                annotation.setFont(new Font("Segoe UI", Font.BOLD, 12));
                annotation.setPaint(Color.BLACK); // Màu chữ
                // Màu nền xám nhạt, hơi trong suốt
                annotation.setPaint(new Color(50, 50, 50, 230));

                // Định vị
                // Đặt neo của bảng ở giữa category
                annotation.setCategoryAnchor(CategoryAnchor.MIDDLE);
                // Đặt text ở vị trí BOTTOM_CENTER (để cái bảng xuất hiện BÊN TRÊN điểm dữ liệu)
                annotation.setTextAnchor(org.jfree.ui.TextAnchor.BASELINE_CENTER.BOTTOM_CENTER);

                // Thêm bảng nhỏ vào biểu đồ
                plot.addAnnotation(annotation);
                lastAnnotation = annotation; // Lưu lại để xóa lần sau
            }
        }

        @Override
        public void chartMouseClicked(ChartMouseEvent event) {

        }
    }

    private void initPanelThongKeNgay() {
        jPanel21.removeAll();
        jPanel21.setPreferredSize(new Dimension(200, 0));
        jPanel21.setBackground(Color.WHITE);
        jPanel21.setLayout(new java.awt.GridLayout(2, 1, 0, 15));
        jPanel21.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // DOANH THU
        LocalDate homNay = LocalDate.now();
        LocalDate homQua = homNay.minusDays(1);

        double doanhThuHomNay = HoaDonDAO.getDoanhThuTheoNgay(homNay);
        double doanhThuHomQua = HoaDonDAO.getDoanhThuTheoNgay(homQua);

        // Tính % tăng trưởng:
        double phanTramDoanhThu = 0;
        if (doanhThuHomQua > 0) {
            phanTramDoanhThu = ((doanhThuHomNay - doanhThuHomQua) / doanhThuHomQua) * 100;
        }

        JPanel pnlDoanhThu = taoPanelThongKeCon("Doanh thu hôm nay", doanhThuHomNay, phanTramDoanhThu, true);

        // HÓA ĐƠN
        int hoaDonHomNay = HoaDonDAO.timHDTheoNgayLap(homNay).size();
        int hoaDonHomQua = HoaDonDAO.timHDTheoNgayLap(homQua).size();

        double phanTramHoaDon = 0;
        if (hoaDonHomQua > 0) {
            phanTramHoaDon = ((double) (hoaDonHomNay - hoaDonHomQua) / hoaDonHomQua) * 100;
        }

        JPanel pnlHoaDon = taoPanelThongKeCon("Tổng hóa đơn", hoaDonHomNay, phanTramHoaDon, false);

        jPanel21.add(pnlDoanhThu);
        jPanel21.add(pnlHoaDon);

        jPanel21.revalidate();
        jPanel21.repaint();
    }

    private JPanel taoPanelThongKeCon(String tieuDe, double giaTri, double phanTramTang, boolean isTienTe) {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBackground(Color.WHITE);
        pnl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Tiêu đề
        JLabel lblTieuDe = new JLabel(tieuDe);
        lblTieuDe.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTieuDe.setForeground(Color.GRAY);

        // Giá trị chính
        JLabel lblGiaTri = new JLabel();
        lblGiaTri.setFont(new Font("Segoe UI", Font.BOLD, 20));
        if (isTienTe) {
            lblGiaTri.setForeground(new Color(51, 51, 51)); // Màu đen xám
            lblGiaTri.setText(NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN")).format(giaTri));
        } else {
            lblGiaTri.setForeground(new Color(51, 51, 51));
            lblGiaTri.setText(String.valueOf((int) giaTri));
        }

        // Dòng so sánh
        JLabel lblSoSanh = new JLabel();
        lblSoSanh.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        String muiTen = phanTramTang >= 0 ? "↑" : "↓";
        String trangThai = String.format("%.1f%% so với hôm qua", Math.abs(phanTramTang));
        lblSoSanh.setText(muiTen + " " + trangThai);

        if (phanTramTang >= 0) {
            lblSoSanh.setForeground(new Color(0, 153, 51)); // Màu xanh lá (Tăng)
        } else {
            lblSoSanh.setForeground(new Color(220, 53, 69)); // Màu đỏ (Giảm)
        }

        JPanel pnlText = new JPanel(new java.awt.GridLayout(3, 1, 0, 2));
        pnlText.setBackground(Color.WHITE);
        pnlText.add(lblTieuDe);
        pnlText.add(lblGiaTri);
        pnlText.add(lblSoSanh);

        pnl.add(pnlText, BorderLayout.CENTER);

        return pnl;
    }
}
