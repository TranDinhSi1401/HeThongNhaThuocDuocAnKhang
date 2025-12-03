/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import com.orsoncharts.util.TextAnchor;
import hethongnhathuocduocankhang.dao.HoaDonDAO;
import hethongnhathuocduocankhang.dao.NhanVienDAO;
import hethongnhathuocduocankhang.entity.NhanVien;
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
import java.time.format.DateTimeFormatter;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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

/**
 *
 * @author trand
 */
public class DashBoardQuanLi extends javax.swing.JPanel {

    private final JPanel pnlCenter;
    private final JRadioButton radThang;
    private final JRadioButton radNam;
    private final JComboBox<String> cmbThang;
    private final JComboBox<String> cmbNam;
    private final JPanel pnlNutTaiLai;

    /**
     * Creates new form TongQuanGUI
     */
    public DashBoardQuanLi() {
        initComponents();
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        try {
            NhanVien nv = NhanVienDAO.getNhanVienTheoMaNV(GiaoDienChinhGUI.getTk().getTenDangNhap().trim());
            renderThongTinNhanVien(nv);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Không có thông tin nhân viên");
        }

        //pnlBieuDo.setBorder(BorderFactory.createTitledBorder("Biểu đồ Doanh thu tháng MM"));
        pnlBieuDo.setLayout(new BorderLayout());

        pnlCenter = new JPanel();
        pnlCenter.setLayout(new BorderLayout());
        pnlBieuDo.add(pnlCenter, BorderLayout.CENTER);

        // Khởi tạo nút bấm
        JButton btnTaiBieuDo = new JButton("Tải Biểu Đồ");
        radThang = new JRadioButton("Tháng", true);
        radNam = new JRadioButton("Năm");
        ButtonGroup tieuChiGroup = new ButtonGroup();
        tieuChiGroup.add(radThang);
        tieuChiGroup.add(radNam);

        String[] cacThang = {"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 19", "Tháng 10", "Tháng 11", "Tháng 12"};

        int startYear = 2025;
        int endYear = 2045;
        int arrayLength = endYear - startYear + 1;
        String[] cacNam = new String[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            int currentYear = startYear + i;
            cacNam[i] = String.valueOf(currentYear);
        }

        cmbThang = new JComboBox<>(cacThang);
        cmbThang.setSelectedItem("Tháng " + LocalDate.now().getMonthValue());
        cmbNam = new JComboBox<>(cacNam);
        cmbNam.setSelectedItem(LocalDate.now().getYear());

        pnlNutTaiLai = new JPanel();
        pnlNutTaiLai.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pnlNutTaiLai.add(new JLabel("Thống kê theo: "));
        pnlNutTaiLai.add(radThang);
        pnlNutTaiLai.add(radNam);
        pnlNutTaiLai.add(new Label(""));
        pnlNutTaiLai.add(cmbThang);
        pnlNutTaiLai.add(cmbNam);
        cmbThang.setVisible(true);
        cmbNam.setVisible(true);

        pnlNutTaiLai.add(new Label(""));
        pnlNutTaiLai.add(new Label(""));
        pnlNutTaiLai.add(new Label(""));

        pnlNutTaiLai.add(btnTaiBieuDo);

        pnlBieuDo.add(pnlNutTaiLai, BorderLayout.NORTH);

        btnVaoCa.setOpaque(true);
        btnVaoCa.setBackground(Color.GREEN);
        btnVaoCa.setForeground(Color.WHITE);

        // Thêm sự kiện cho nút
        btnTaiBieuDo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radThang.isSelected()) {
                    int thang = Integer.parseInt(cmbThang.getSelectedItem().toString().substring(6)); //với: Tháng 1
                    int nam = Integer.parseInt(cmbNam.getSelectedItem().toString());
                    veBieuDo(thang, nam, "tháng");
                } else {
                    int nam = Integer.parseInt(cmbNam.getSelectedItem().toString());
                    veBieuDo(1, nam, "năm");
                }
            }
        });

        radThang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmbThang.setVisible(true);
                cmbNam.setVisible(true);
                // Cập nhật giao diện
                pnlNutTaiLai.revalidate();
                pnlNutTaiLai.repaint();
            }
        });

        radNam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmbThang.setVisible(false);
                cmbNam.setVisible(true);
                // Cập nhật giao diện
                pnlNutTaiLai.revalidate();
                pnlNutTaiLai.repaint();
            }
        });

        btnVaoCa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnVaoCa.getText().equals("Vào Ca")) {

                    btnVaoCa.setText("Ra Ca");
                    //Cho phép nút hiển thị màu nền
                    btnVaoCa.setOpaque(true);
                    btnVaoCa.setBackground(Color.RED);
                    btnVaoCa.setForeground(Color.WHITE);
                } else {

                    btnVaoCa.setText("Vào Ca");
                    btnVaoCa.setOpaque(true);
                    btnVaoCa.setBackground(Color.GREEN);
                    btnVaoCa.setForeground(Color.WHITE);
                }
            }
        });

        JPanel pnlCardBanChay = new JPanel(new BorderLayout());
        pnlCardBanChay.setBackground(Color.WHITE);
        pnlCardBanChay.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JPanel pnlTieuDe = new JPanel(new BorderLayout(10, 10));
        pnlTieuDe.setBackground(new Color(245, 247, 250));
        pnlTieuDe.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTieuDe = new JLabel("Sản phẩm bán chạy");
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 16));

        String[] cacLuaChonLoc = {"Tuần này", "Tháng này"};
        JComboBox<String> cmbLoc = new JComboBox<>(cacLuaChonLoc);
        cmbLoc.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        pnlTieuDe.add(lblTieuDe, BorderLayout.WEST);
        pnlTieuDe.add(cmbLoc, BorderLayout.EAST);

        String[] tenCot = {"STT", "Tên SP", "SL", "Đơn giá"};
        Object[][] duLieu = {
            {1, "Sản phẩm 1", 112, "76.000"},
            {2, "Sản phẩm 1", 134, "55.000"},
            {3, "Sản phẩm 1", 342, "178.000"}
        };

// Tạo model và set không cho phép sửa
        DefaultTableModel moHinhBang = new DefaultTableModel(duLieu, tenCot) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable bang = new JTable(moHinhBang);

// --- TÙY CHỈNH GIAO DIỆN BẢNG ---
// 4. Tùy chỉnh Header của bảng
        JTableHeader tieuDeBang = bang.getTableHeader();
        tieuDeBang.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tieuDeBang.setForeground(new Color(102, 102, 102));
        tieuDeBang.setBackground(new Color(245, 247, 250));
        tieuDeBang.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
        tieuDeBang.setOpaque(false);

// Bật sắp xếp
        bang.setAutoCreateRowSorter(true);

// 5. Tùy chỉnh nội dung Bảng
        bang.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bang.setRowHeight(35);
        bang.setShowGrid(false);
        bang.setIntercellSpacing(new Dimension(0, 0));
        bang.setSelectionBackground(new Color(230, 245, 255));

// 6. Căn lề cho các cột
        DefaultTableCellRenderer canhLePhai = new DefaultTableCellRenderer();
        canhLePhai.setHorizontalAlignment(JLabel.RIGHT);
        canhLePhai.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        DefaultTableCellRenderer canhLeGiua = new DefaultTableCellRenderer();
        canhLeGiua.setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer canhLeTrai = new DefaultTableCellRenderer();
        canhLeTrai.setHorizontalAlignment(JLabel.LEFT);
        canhLeTrai.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        bang.getColumnModel().getColumn(0).setCellRenderer(canhLeGiua);   // STT
        bang.getColumnModel().getColumn(1).setCellRenderer(canhLeTrai);    // Tên SP
        bang.getColumnModel().getColumn(2).setCellRenderer(canhLePhai);  // SL
        bang.getColumnModel().getColumn(3).setCellRenderer(canhLePhai);  // Đơn giá

// 7. Đặt độ rộng cột
        bang.getColumnModel().getColumn(0).setPreferredWidth(50);
        bang.getColumnModel().getColumn(1).setPreferredWidth(200);
        bang.getColumnModel().getColumn(2).setPreferredWidth(80);
        bang.getColumnModel().getColumn(3).setPreferredWidth(100);

// 8. Đưa bảng vào JScrollPane
        JScrollPane thanhCuon = new JScrollPane(bang);
        thanhCuon.setBorder(BorderFactory.createEmptyBorder());
        thanhCuon.getViewport().setBackground(Color.WHITE);

// 9. Gắn Title Bar và Bảng vào "Card" chính
        pnlCardBanChay.add(pnlTieuDe, BorderLayout.NORTH);
        pnlCardBanChay.add(thanhCuon, BorderLayout.CENTER);

// --- THÊM VÀO jPanel19 ---
        jPanel19.setPreferredSize(new Dimension(400, 0));
        jPanel19.setLayout(new BorderLayout());
        jPanel19.setPreferredSize(new Dimension(200, 100));

        jPanel19.add(pnlCardBanChay, BorderLayout.CENTER);

        jPanel19.revalidate();
        jPanel19.repaint();

        // --- KẾT THÚC CODE ---
        JPanel pnlCard_HetHang = new JPanel(new BorderLayout());
        pnlCard_HetHang.setBackground(Color.WHITE);
        pnlCard_HetHang.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JPanel pnlTieuDe_HetHang = new JPanel(new BorderLayout(10, 10));
        pnlTieuDe_HetHang.setBackground(new Color(245, 247, 250));
        pnlTieuDe_HetHang.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTieuDe_HetHang = new JLabel("Sản phẩm sắp hết hàng");
        lblTieuDe_HetHang.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pnlTieuDe_HetHang.add(lblTieuDe_HetHang, BorderLayout.WEST);

// 3. Tạo Bảng (tên biến mới)
        String[] tenCot_HetHang = {"STT", "Tên SP", "Tồn kho", "Tồn tối thiểu"};
        Object[][] duLieu_HetHang = {
            {1, "Sản phẩm 1", 1, 10},
            {2, "Sản phẩm 1", 125, 10},
            {3, "Sản phẩm 1", 324, 10}
        };

// Model
        DefaultTableModel moHinhBang_HetHang = new DefaultTableModel(duLieu_HetHang, tenCot_HetHang) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2 || columnIndex == 3) {
                    return Integer.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };

// 
        JTable bang_HetHang = new JTable(moHinhBang_HetHang);

// --- TÙY CHỈNH GIAO DIỆN BẢNG ---
// 4. Header (tên biến mới)
        JTableHeader tieuDeBang_HetHang = bang_HetHang.getTableHeader();
        tieuDeBang_HetHang.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tieuDeBang_HetHang.setForeground(new Color(102, 102, 102));
        tieuDeBang_HetHang.setBackground(new Color(245, 247, 250));
        tieuDeBang_HetHang.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
        tieuDeBang_HetHang.setOpaque(false);

        bang_HetHang.setAutoCreateRowSorter(true);

// 5. Tùy chỉnh nội dung Bảng
        bang_HetHang.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bang_HetHang.setRowHeight(35);
        bang_HetHang.setShowGrid(false);
        bang_HetHang.setIntercellSpacing(new Dimension(0, 0));
        bang_HetHang.setSelectionBackground(new Color(230, 245, 255));

// 6. Căn lề (tên biến mới)
        DefaultTableCellRenderer canhLePhai_HetHang = new DefaultTableCellRenderer();
        canhLePhai_HetHang.setHorizontalAlignment(JLabel.RIGHT);
        canhLePhai_HetHang.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        DefaultTableCellRenderer canhLeGiua_HetHang = new DefaultTableCellRenderer();
        canhLeGiua_HetHang.setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer canhLeTrai_HetHang = new DefaultTableCellRenderer();
        canhLeTrai_HetHang.setHorizontalAlignment(JLabel.LEFT);
        canhLeTrai_HetHang.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        bang_HetHang.getColumnModel().getColumn(0).setCellRenderer(canhLeGiua_HetHang);
        bang_HetHang.getColumnModel().getColumn(1).setCellRenderer(canhLeTrai_HetHang);
        bang_HetHang.getColumnModel().getColumn(3).setCellRenderer(canhLePhai_HetHang);

// 7. RENDERER TÙY CHỈNH CHO CỘT "TỒN KHO" (tên biến mới)
        DefaultTableCellRenderer rendererTonKho_HetHang = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (value instanceof Integer) {
                    int tonKho = (Integer) value;
                    int hanMucTon = (Integer) table.getValueAt(row, 3);

                    setText(String.format("%02d", tonKho));

                    if (tonKho <= hanMucTon) {
                        c.setForeground(Color.RED);
                    } else {
                        c.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
                    }
                }

                setHorizontalAlignment(JLabel.RIGHT);
                setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
                return c;
            }
        };

// Áp dụng renderer cho bảng mới
        bang_HetHang.getColumnModel().getColumn(2).setCellRenderer(rendererTonKho_HetHang);

// 8. Đặt độ rộng cột
        bang_HetHang.getColumnModel().getColumn(0).setPreferredWidth(50);
        bang_HetHang.getColumnModel().getColumn(1).setPreferredWidth(200);
        bang_HetHang.getColumnModel().getColumn(2).setPreferredWidth(80);
        bang_HetHang.getColumnModel().getColumn(3).setPreferredWidth(100);

// 9. Đưa bảng vào JScrollPane
        JScrollPane thanhCuon_HetHang = new JScrollPane(bang_HetHang);
        thanhCuon_HetHang.setBorder(BorderFactory.createEmptyBorder());
        thanhCuon_HetHang.getViewport().setBackground(Color.WHITE);

// 10. Gắn Title Bar và Bảng vào panel chính
        pnlCard_HetHang.add(pnlTieuDe_HetHang, BorderLayout.NORTH);
        pnlCard_HetHang.add(thanhCuon_HetHang, BorderLayout.CENTER);

// --- THÊM "CARD" MỚI NÀY VÀO jPanel20 ---
// BƯỚC 1: Đặt layout cho jPanel20
        jPanel20.setPreferredSize(new Dimension(250, 0));
        jPanel20.setLayout(new BorderLayout());

// BƯỚC 2: Thêm "Card" (mới) vào jPanel20
        jPanel20.add(pnlCard_HetHang, BorderLayout.CENTER);

// BƯỚC 3: Cập nhật lại giao diện (quan trọng)
        jPanel20.revalidate();
        jPanel20.repaint();

        jPanel21.setPreferredSize(new Dimension(400, 0));

// --- KẾT THÚC CODE ---
        veBieuDo(LocalDate.now().getMonthValue(), LocalDate.now().getYear(), "tháng");
        //renderThongTinCaLam();
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

            // B4: TẠO CHARTPANEL (Đây là bước "cầu nối")
            // ChartPanel là một component Swing (giống JPanel)
            // dùng để chứa đối tượng JFreeChart
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

            // B4: TẠO CHARTPANEL (Đây là bước "cầu nối")
            // ChartPanel là một component Swing (giống JPanel)
            // dùng để chứa đối tượng JFreeChart
            ChartPanel chartPanel = new ChartPanel(chart);

            // B5: THÊM CHARTPANEL VÀO PNLCENTER
            pnlCenter.removeAll(); // Xóa biểu đồ cũ (nếu có)
            pnlCenter.add(chartPanel, BorderLayout.CENTER); // Thêm biểu đồ mới
            chartPanel.addChartMouseListener(new CustomChartMouseListener(chart, tieuChi));
            pnlCenter.revalidate(); // Yêu cầu vẽ lại layout
            pnlCenter.repaint();    // Yêu cầu vẽ lại đồ họa
        }
    }

    private double giaTriKPI = 3000000;

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
        // Dòng NumberFormat đã được xóa, vì listener sẽ tự quản lý

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
            ValueMarker kpiMarker = new ValueMarker(giaTriKPI);
            kpiMarker.setPaint(Color.BLUE);
            kpiMarker.setStroke(new BasicStroke( // Cài đặt nét đứt
                    2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    1.0f, new float[]{6.0f, 6.0f}, 0.0f
            ));
            plot.addRangeMarker(kpiMarker); // Thêm đường KPI vào trục Y (Range)

            // *** GÁN RENDERER (CHỈ CẦN 2 DÒNG NÀY) ***
            ToMauCot rendererThang = new ToMauCot(giaTriKPI); //Tạo renderer
            plot.setRenderer(rendererThang); // Gán renderer cho plot

            // Toàn bộ code về "StandardCategoryToolTipGenerator" đã được XÓA ở đây.
            return barChart;

        } else if (tieuChi.equals("năm")) {
            // Tạo biểu đồ cơ bản
            JFreeChart barChart = ChartFactory.createBarChart(
                    "Doanh thu " + tieuChi + " " + nam,
                    "Tháng", "Doanh thu (VND)", // Sửa trục X
                    dataset,
                    PlotOrientation.VERTICAL,
                    false, true, false // Tắt Legend, Bật Tooltips
            );

            // Lấy Plot (khu vực vẽ) để tùy chỉnh
            CategoryPlot plot = barChart.getCategoryPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

            // Bạn có thể muốn có một KPI khác cho năm
            double kpiNam = giaTriKPI * 25; // Ví dụ: KPI năm = KPI ngày * 25 ngày làm việc

            // Thêm đường KPI (ValueMarker)
            ValueMarker kpiMarker = new ValueMarker(kpiNam); // Dùng KPI năm
            kpiMarker.setPaint(Color.BLUE);
            kpiMarker.setStroke(new BasicStroke(
                    2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    1.0f, new float[]{6.0f, 6.0f}, 0.0f
            ));
            plot.addRangeMarker(kpiMarker);

            // *** GÁN RENDERER (CHỈ CẦN 2 DÒNG NÀY) ***
            ToMauCot rendererNam = new ToMauCot(kpiNam); // Dùng KPI năm
            plot.setRenderer(rendererNam); // Gán renderer cho plot

            // Toàn bộ code về "StandardCategoryToolTipGenerator" đã được XÓA ở đây.
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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
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

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(245, 245, 245));
        jPanel4.setPreferredSize(new java.awt.Dimension(220, 220));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/profile1.png"))); // NOI18N
        jPanel4.add(jLabel1, new java.awt.GridBagConstraints());

        jPanel3.add(jPanel4, java.awt.BorderLayout.LINE_START);

        jPanel7.setBackground(new java.awt.Color(245, 245, 245));
        jPanel7.setPreferredSize(new java.awt.Dimension(200, 220));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.Y_AXIS));

        jPanel8.setBackground(new java.awt.Color(245, 245, 245));
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

        jPanel9.setBackground(new java.awt.Color(245, 245, 245));
        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setText("Họ tên:");
        jPanel9.add(jLabel3);

        lblHoTen.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblHoTen.setForeground(new java.awt.Color(51, 51, 51));
        lblHoTen.setText("Nguyễn Văn A");
        jPanel9.add(lblHoTen);

        jPanel7.add(jPanel9);

        jPanel11.setBackground(new java.awt.Color(245, 245, 245));
        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel5.setText("Ngày sinh:");
        jPanel11.add(jLabel5);

        lblNgaySinh.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblNgaySinh.setForeground(new java.awt.Color(51, 51, 51));
        lblNgaySinh.setText("XX/XX/XXXX");
        jPanel11.add(lblNgaySinh);

        jPanel7.add(jPanel11);

        jPanel12.setBackground(new java.awt.Color(245, 245, 245));
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

        jPanel15.setPreferredSize(new java.awt.Dimension(240, 0));
        jPanel15.setLayout(new javax.swing.BoxLayout(jPanel15, javax.swing.BoxLayout.Y_AXIS));

        jPanel10.setBackground(new java.awt.Color(245, 245, 245));
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

        jPanel13.setBackground(new java.awt.Color(245, 245, 245));
        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setText("Trạng Thái:");
        jPanel13.add(jLabel7);

        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTrangThai.setForeground(new java.awt.Color(51, 51, 51));
        lblTrangThai.setText("Đang làm việc");
        jPanel13.add(lblTrangThai);

        jPanel15.add(jPanel13);

        jPanel14.setBackground(new java.awt.Color(245, 245, 245));
        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setText("SĐT:");
        jPanel14.add(jLabel8);

        lblSdt.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblSdt.setForeground(new java.awt.Color(51, 51, 51));
        lblSdt.setText("0XXXXXXXXX");
        jPanel14.add(lblSdt);

        jPanel15.add(jPanel14);

        jPanel16.setBackground(new java.awt.Color(245, 245, 245));
        jPanel16.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel9.setText("   ");
        jPanel16.add(jLabel9);

        jPanel15.add(jPanel16);

        jPanel3.add(jPanel15, java.awt.BorderLayout.LINE_END);

        jPanel18.setBackground(new java.awt.Color(245, 245, 245));
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
     * Lớp lắng nghe di chuột mới Dùng Annotation (chú thích) thay vì Marker
     */
    class CustomChartMouseListener implements ChartMouseListener {

        private JFreeChart chart;
        private CategoryPlot plot;
        private String tieuChi; // ("tháng" hoặc "năm")

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

                // *** TẠO VÀ ĐỊNH DẠNG "BẢNG NHỎ" (Annotation) ***
                // 1. Tạo nội dung (ví dụ: "Ngày 1: 5.000.000 ₫")
                String prefix = this.tieuChi.equals("tháng") ? "Ngày " : "Tháng ";
                String text = prefix + categoryKey.toString() + ": "
                        + currencyFormat.format(value.doubleValue());

                // 2. Tạo Annotation (cái "bảng nhỏ")
                // Nó sẽ xuất hiện tại tọa độ (categoryKey, value)
                CategoryTextAnnotation annotation = new CategoryTextAnnotation(
                        text, // Nội dung
                        categoryKey, // Category (Ngày/Tháng)
                        value.doubleValue() // Value (Doanh thu)
                );

                // 3. Tùy chỉnh "bảng nhỏ"
                annotation.setFont(new Font("Segoe UI", Font.BOLD, 12));
                annotation.setPaint(Color.BLACK); // Màu chữ
                // Màu nền xám nhạt, hơi trong suốt
                annotation.setPaint(new Color(50, 50, 50, 230));
                //annotation.setOutlineVisible(true); // Bật viền
                //annotation.setOutlinePaint(Color.GRAY); // Màu viền
                //annotation.setdding(5, 5, 5, 5); // Đệm 5px

                // 4. Định vị (Quan trọng!)
                // Đặt neo của "bảng" ở giữa category
                annotation.setCategoryAnchor(CategoryAnchor.MIDDLE);
                // Đặt text ở vị trí BOTTOM_CENTER (để cái "bảng" xuất hiện BÊN TRÊN điểm dữ liệu)
                annotation.setTextAnchor(org.jfree.ui.TextAnchor.BASELINE_CENTER.BOTTOM_CENTER);

                // 5. Thêm "bảng nhỏ" vào biểu đồ
                plot.addAnnotation(annotation);
                lastAnnotation = annotation; // Lưu lại để xóa lần sau
            }
        }

        @Override
        public void chartMouseClicked(ChartMouseEvent event) {
            // Không cần làm gì
        }
    }
}
