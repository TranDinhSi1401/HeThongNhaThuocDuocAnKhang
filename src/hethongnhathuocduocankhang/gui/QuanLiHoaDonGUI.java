/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.dao.HoaDonDAO;
import hethongnhathuocduocankhang.entity.HoaDon;
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
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class QuanLiHoaDonGUI extends JPanel {

    private JTextField txtTimKiem;
    private JTable table;
    private JComboBox<String> cmbTieuChiTimKiem;
    private JComboBox<String> cmbBoLoc;
    private DefaultTableModel model;

    // Label hiển thị số lượng (Footer)
    private JLabel lblTongSoDong;
    private JLabel lblSoDongChon;
    
    // Thanh tiến trình loading
    private JProgressBar progressBar;
    
    // Format ngày giờ dùng chung
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public QuanLiHoaDonGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // --- 1. PANEL NORTH ---
        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        // 1.1. Panel Chức năng (LEFT)
        JPanel pnlNorthLeft = new JPanel();
        pnlNorthLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlNorthLeft.setBorder(new EmptyBorder(0, 0, 10, 0));
        pnlNorth.add(pnlNorthLeft, BorderLayout.WEST);

        // 1.2. Panel Tìm kiếm và Lọc
        JPanel pnlNorthRight = new JPanel();
        pnlNorthRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        cmbTieuChiTimKiem = new JComboBox<>(new String[]{
            "Mã Hóa Đơn",
            "Mã Nhân Viên",
            "Mã Khách Hàng",
            "SĐT Khách Hàng",
            "Ngày lập (yyyy-MM-dd)"
        });
        cmbTieuChiTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbTieuChiTimKiem.setPreferredSize(new Dimension(180, 30));

        cmbBoLoc = new JComboBox<>(new String[]{
            "Tất cả",
            "Đã thanh toán",
            "Chưa thanh toán",
            "Tiền mặt",
            "Chuyển khoản"
        });
        cmbBoLoc.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbBoLoc.setPreferredSize(new Dimension(150, 30));

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
        pnlNorthRight.add(new JLabel("Lọc theo"));
        pnlNorthRight.add(cmbBoLoc);

        pnlNorth.add(pnlNorthRight, BorderLayout.EAST);
        this.add(pnlNorth, BorderLayout.NORTH);

        // --- 2. PANEL CENTER (TABLE) ---
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));

        // Cột hiển thị
        String[] columnNames = {
            "STT",
            "Mã HĐ",
            "Nhân viên",
            "Khách hàng",
            "Ngày lập",
            "Tổng tiền",
            "Hình thức TT"
        };
        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(30); // Tăng chiều cao dòng lên xíu cho thoáng
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(230, 230, 230));
        table.getTableHeader().setReorderingAllowed(false);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(220, 240, 255)); // Màu chọn dòng nhẹ nhàng

        // --- CẤU HÌNH KÍCH THƯỚC & CĂN CHỈNH CỘT ---
        TableColumnModel columnModel = table.getColumnModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT); // Dùng cho cột tiền
        rightRenderer.setBorder(new EmptyBorder(0,0,0,5)); // Padding phải

        // 0. STT
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(0).setMaxWidth(50);
        columnModel.getColumn(0).setCellRenderer(centerRenderer);

        // 1. Mã HĐ
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(1).setMaxWidth(150);
        columnModel.getColumn(1).setCellRenderer(centerRenderer);

        // 2. Nhân viên
        columnModel.getColumn(2).setPreferredWidth(150);
        
        // 3. Khách hàng
        columnModel.getColumn(3).setPreferredWidth(150);

        // 4. Ngày lập
        columnModel.getColumn(4).setPreferredWidth(130);
        columnModel.getColumn(4).setCellRenderer(centerRenderer);
        
        // 5. Tổng tiền
        columnModel.getColumn(5).setPreferredWidth(120);
        columnModel.getColumn(5).setCellRenderer(rightRenderer);
        
        // 6. Hình thức TT
        columnModel.getColumn(6).setPreferredWidth(120);
        columnModel.getColumn(6).setCellRenderer(centerRenderer);
        
        // LƯU Ý: Đã xóa cấu hình cột 7 vì model chỉ có 0-6 cột

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(centerPanel, BorderLayout.CENTER);
        
        // --- 3. PANEL SOUTH (FOOTER) ---
        JPanel pnlSouth = new JPanel(new BorderLayout());
        pnlSouth.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        JPanel pnlInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        Font fontFooter = new Font("Arial", Font.BOLD, 13);
        
        lblTongSoDong = new JLabel("Tổng số hóa đơn: 0");
        lblTongSoDong.setFont(fontFooter);
        lblTongSoDong.setForeground(new Color(0, 102, 204));
        
        lblSoDongChon = new JLabel("Đang chọn: 0");
        lblSoDongChon.setFont(fontFooter);
        lblSoDongChon.setForeground(new Color(204, 0, 0));
        
        pnlInfo.add(lblTongSoDong);
        pnlInfo.add(new JSeparator(JSeparator.VERTICAL));
        pnlInfo.add(lblSoDongChon);
        
        // Thanh progress bar
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(false);
        progressBar.setVisible(false);
        progressBar.setPreferredSize(new Dimension(200, 18));
        progressBar.setStringPainted(true);
        progressBar.setString("Đang tải dữ liệu...");
        
        JPanel pnlProgress = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlProgress.add(progressBar);
        
        pnlSouth.add(pnlInfo, BorderLayout.WEST);
        pnlSouth.add(pnlProgress, BorderLayout.EAST);
        
        this.add(pnlSouth, BorderLayout.SOUTH);

        // Load dữ liệu ban đầu
        reloadTableData(() -> HoaDonDAO.getAllHoaDon());
        addEvents();
    }

    /**
     * Hàm dùng chung để chạy SwingWorker
     * @param dataSupplier: Interface functional cung cấp list dữ liệu (từ DAO)
     */
    private void reloadTableData(Supplier<ArrayList<HoaDon>> dataSupplier) {
        // Reset bảng và UI
        model.setRowCount(0);
        lblTongSoDong.setText("Đang tải...");
        
        // Hiển thị thanh loading
        progressBar.setVisible(true);
        progressBar.setIndeterminate(true); // Chạy qua lại vô tận
        
        // Khởi tạo Worker
        SwingWorker<ArrayList<HoaDon>, HoaDon> worker = new SwingWorker<ArrayList<HoaDon>, HoaDon>() {
            @Override
            protected ArrayList<HoaDon> doInBackground() throws Exception {
                // Giả lập độ trễ nhỏ nếu cần để thấy hiệu ứng loading (Optional)
                // Thread.sleep(500); 
                
                // 1. Lấy dữ liệu từ DAO (Thực hiện ngầm)
                ArrayList<HoaDon> list = dataSupplier.get();
                
                // 2. Publish từng phần tử hoặc từng nhóm để process hiển thị
                if (list != null) {
                    for (HoaDon hd : list) {
                        publish(hd);
                        // Ngủ cực ngắn để tạo hiệu ứng "rơi" từ từ nếu muốn
                        // Thread.sleep(1); 
                    }
                }
                return list;
            }

            @Override
            protected void process(List<HoaDon> chunks) {
                // Hàm này chạy trên EDT, an toàn để update UI
                for (HoaDon hd : chunks) {
                    addHoaDonToTable(hd);
                }
            }

            @Override
            protected void done() {
                try {
                    ArrayList<HoaDon> result = get(); // Lấy kết quả cuối cùng
                    lblTongSoDong.setText("Tổng số hóa đơn: " + (result != null ? result.size() : 0));
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(QuanLiHoaDonGUI.this, "Lỗi tải dữ liệu: " + e.getMessage());
                } finally {
                    // Tắt thanh loading
                    progressBar.setIndeterminate(false);
                    progressBar.setVisible(false);
                }
            }
        };
        
        worker.execute(); // Bắt đầu chạy
    }

    private void addHoaDonToTable(HoaDon hd) {
        String tenNV = "Không có NV";
        if (hd.getNhanVien() != null) {
            tenNV = (hd.getNhanVien().getTen() == null) ? hd.getNhanVien().getMaNV() : hd.getNhanVien().getHoTenDem() + " " + hd.getNhanVien().getTen();
        }

        String tenKH = "Khách lẻ";
        if (hd.getKhachHang() != null) {
            tenKH = (hd.getKhachHang().getTen() == null) ? hd.getKhachHang().getMaKH() : hd.getKhachHang().getHoTenDem() + " " + hd.getKhachHang().getTen();
        }

        Object[] row = {
            model.getRowCount() + 1, // STT tự tăng dựa trên số dòng hiện tại
            hd.getMaHoaDon(),
            tenNV,
            tenKH,
            hd.getNgayLapHoaDon().format(formatter),
            String.format("%,.0f VND", hd.getTongTien()),
            hd.isChuyenKhoan() ? "Chuyển khoản" : "Tiền mặt"
        };
        model.addRow(row);
    }

    private void addEvents() {
        txtTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyTimKiem();
            }
        });

        cmbBoLoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyLoc();
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
                hienThiChiTietHoaDon(e);
            }
        });
        
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

        // Sử dụng Supplier để truyền logic lấy dữ liệu vào Worker
        reloadTableData(() -> {
            ArrayList<HoaDon> dsKetQua = new ArrayList<>();
            if (tuKhoa.isEmpty()) {
                return HoaDonDAO.getAllHoaDon();
            }
            
            try {
                switch (tieuChi) {
                    case "Mã Hóa Đơn":
                        HoaDon hd = HoaDonDAO.getHoaDonTheoMaHD(tuKhoa);
                        if (hd != null) dsKetQua.add(hd);
                        break;
                    case "Mã Nhân Viên":
                        dsKetQua = HoaDonDAO.timHDTheoMaNV(tuKhoa);
                        break;
                    case "Mã Khách Hàng":
                        dsKetQua = HoaDonDAO.timHDTheoMaKH(tuKhoa);
                        break;
                    case "SĐT Khách Hàng":
                        dsKetQua = HoaDonDAO.timHDTheoSDTKH(tuKhoa);
                        break;
                    case "Ngày lập (yyyy-MM-dd)":
                        LocalDate date = LocalDate.parse(tuKhoa);
                        dsKetQua = HoaDonDAO.timHDTheoNgayLap(date);
                        break;
                }
            } catch (DateTimeParseException e) {
                 // Lưu ý: Trong doInBackground không được show Dialog trực tiếp, 
                 // nhưng ở đây ta chỉ catch lỗi parse ngày đơn giản.
                 // Nếu muốn show lỗi chuẩn, cần xử lý ở done() hoặc process()
                 System.err.println("Lỗi định dạng ngày: " + e.getMessage());
            }
            return dsKetQua;
        });
    }

    private void xuLyLoc() {
        String boLoc = cmbBoLoc.getSelectedItem().toString();

        reloadTableData(() -> {
            switch (boLoc) {
                case "Tất cả":
                    return HoaDonDAO.getAllHoaDon();
                case "Đã thanh toán":
                    // Giả sử bảng DB của bạn vẫn giữ logic cũ về boolean trạng thái
                    // Nếu đã bỏ cột TrangThai, bạn cần sửa lại DAO method tương ứng
                    return HoaDonDAO.timHDTheoTrangThai(true); 
                case "Chưa thanh toán":
                    return HoaDonDAO.timHDTheoTrangThai(false);
                case "Tiền mặt":
                    return HoaDonDAO.timHDTheoHinhThuc(false);
                case "Chuyển khoản":
                    return HoaDonDAO.timHDTheoHinhThuc(true);
                default:
                    return new ArrayList<>();
            }
        });
    }

    private void hienThiChiTietHoaDon(MouseEvent e) {
        int selectRow = table.getSelectedRow();
        if (selectRow != -1) {
            String maHD = model.getValueAt(selectRow, 1).toString();
            
            // Xử lý lấy chi tiết không cần SwingWorker vì chỉ lấy 1 object, khá nhanh
            // Tuy nhiên nếu chi tiết quá nhiều thì cũng nên dùng Worker.
            HoaDon hdDaChon = HoaDonDAO.getHoaDonTheoMaHD(maHD);

            if (hdDaChon != null && e.getClickCount() == 2) {
                ChiTietHoaDonGUI pnlChiTiet = new ChiTietHoaDonGUI();
                pnlChiTiet.loadData(hdDaChon);

                JDialog dialog = new JDialog();
                dialog.setTitle("Danh sách chi tiết Hóa Đơn: " + hdDaChon.getMaHoaDon());
                dialog.setModal(true);
                dialog.setContentPane(pnlChiTiet);
                dialog.setPreferredSize(new Dimension(800, 400));
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        }
    }
}