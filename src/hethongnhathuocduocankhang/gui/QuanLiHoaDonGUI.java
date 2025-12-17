/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import com.toedter.calendar.JDateChooser; // Import thư viện lịch
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
import java.beans.PropertyChangeEvent; // Import để bắt sự kiện chọn ngày
import java.beans.PropertyChangeListener; // Import Listener cho Property
import java.time.LocalDate;
import java.time.ZoneId; // Import để chuyển đổi Date sang LocalDate
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date; // Import Date
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class QuanLiHoaDonGUI extends JPanel {

    private JTextField txtTimKiem;
    private JDateChooser chonLichNgayTimKiem; 
    private JPanel pnlNhapLieuTiimKiem; 
    private JTable table;
    private JComboBox<String> cmbTieuChiTimKiem;
    private JComboBox<String> cmbBoLoc;
    private DefaultTableModel model;
    private JLabel lblTongSoDong;
    private JLabel lblSoDongChon;
    private JProgressBar progressBar; 
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");  

    public QuanLiHoaDonGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // PANEL NORTH
        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BorderLayout());

        // Panel Chức năng (LEFT)
        JPanel pnlNorthLeft = new JPanel();
        pnlNorthLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlNorthLeft.setBorder(new EmptyBorder(0, 0, 10, 0));
        pnlNorth.add(pnlNorthLeft, BorderLayout.WEST);

        // Panel Tìm kiếm và Lọc
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

        // Khởi tạo DateChooser và cấu hình định dạng ngày
        chonLichNgayTimKiem = new JDateChooser();
        chonLichNgayTimKiem.setDateFormatString("yyyy-MM-dd");
        chonLichNgayTimKiem.setPreferredSize(new Dimension(200, 30));
        chonLichNgayTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));

        // Tạo Panel CardLayout để chứa cả TextField và DateChooser
        pnlNhapLieuTiimKiem = new JPanel(new CardLayout());
        pnlNhapLieuTiimKiem.add(txtTimKiem, "text"); // Thẻ hiển thị text
        pnlNhapLieuTiimKiem.add(chonLichNgayTimKiem, "date"); // Thẻ hiển thị lịch

        JPanel pnlTimKiem = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        pnlTimKiem.add(new JLabel("Tìm kiếm"));
        pnlTimKiem.add(pnlNhapLieuTiimKiem);

        pnlNorthRight.add(new JLabel("Tìm theo"));
        pnlNorthRight.add(cmbTieuChiTimKiem);
        pnlNorthRight.add(pnlTimKiem);
        pnlNorthRight.add(new JLabel("Lọc theo"));
        pnlNorthRight.add(cmbBoLoc);

        pnlNorth.add(pnlNorthRight, BorderLayout.EAST);
        this.add(pnlNorth, BorderLayout.NORTH);

        // PANEL CENTER (TABLE)
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));

        // Cột hiển thị
        String[] columnNames = {
            "STT",
            "Mã HĐ",
            "Nhân viên",
            "Khách hàng",
            "Ngày lập",
            "Tổng tiền",
            "Hình thức TT",
        };
        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(30); 
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(230, 230, 230));
        table.getTableHeader().setReorderingAllowed(false);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(220, 240, 255)); 

        // CẤU HÌNH KÍCH THƯỚC & CĂN CHỈNH CỘT
        TableColumnModel columnModel = table.getColumnModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT); 
        rightRenderer.setBorder(new EmptyBorder(0,0,0,5));

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

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(centerPanel, BorderLayout.CENTER);
        
        // PANEL SOUTH (FOOTER)
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
        progressBar.setIndeterminate(true); // Khi bắt đầu tải thanh load sẽ chạy qua chạy lại liên tục
        // Khởi tạo Worker
        SwingWorker<ArrayList<HoaDon>, HoaDon> worker = new SwingWorker<ArrayList<HoaDon>, HoaDon>() {
            @Override
            protected ArrayList<HoaDon> doInBackground() throws Exception {             
                // 1. Lấy dữ liệu từ DAO (Thực hiện ngầm)
                ArrayList<HoaDon> list = dataSupplier.get();
                
                // 2. Publish từng phần tử hoặc từng nhóm để process hiển thị
                if (list != null) {
                    for (HoaDon hd : list) {
                        publish(hd);
                    }
                }
                return list;
            }
            @Override
            protected void process(List<HoaDon> chunks) {
                for (HoaDon hd : chunks) {
                    addHoaDonToTable(hd);
                }
            }
            @Override
            protected void done() {
                try {
                    ArrayList<HoaDon> result = get();
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
            model.getRowCount() + 1,
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
        
        // Thêm sự kiện khi chọn ngày trên lịch thì tự động tìm kiếm
        chonLichNgayTimKiem.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
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
                // Lấy CardLayout từ panel nhập liệu
                CardLayout cl = (CardLayout) pnlNhapLieuTiimKiem.getLayout();
                String tieuChi = cmbTieuChiTimKiem.getSelectedItem().toString();

                if (tieuChi.equals("Ngày lập (yyyy-MM-dd)")) {
                    cl.show(pnlNhapLieuTiimKiem, "date"); // Hiển thị lịch
                    chonLichNgayTimKiem.requestFocusInWindow();
                } else {
                    cl.show(pnlNhapLieuTiimKiem, "text"); // Hiển thị ô text
                    txtTimKiem.selectAll();
                    txtTimKiem.requestFocus();
                }
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
        // Kiểm tra xem đang dùng lịch hay dùng textfield
        String tuKhoa = "";
        String tieuChi = cmbTieuChiTimKiem.getSelectedItem().toString();

        if (tieuChi.equals("Ngày lập (yyyy-MM-dd)")) {
            // Lấy ngày từ DateChooser và convert sang String chuẩn yyyy-MM-dd
            Date date = chonLichNgayTimKiem.getDate();
            if (date != null) {
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                tuKhoa = localDate.toString();
            }
        } else {
            tuKhoa = txtTimKiem.getText().trim();
        }
        
        String tuKhoaFinal = tuKhoa;

        // Sử dụng Supplier để truyền logic lấy dữ liệu vào Worker
        reloadTableData(() -> {
            ArrayList<HoaDon> dsKetQua = new ArrayList<>();
            if (tuKhoaFinal.isEmpty()) {
                return HoaDonDAO.getAllHoaDon();
            }
            
            try {
                switch (tieuChi) {
                    case "Mã Hóa Đơn":
                        HoaDon hd = HoaDonDAO.getHoaDonTheoMaHD(tuKhoaFinal);
                        if (hd != null) dsKetQua.add(hd);
                        break;
                    case "Mã Nhân Viên":
                        dsKetQua = HoaDonDAO.timHDTheoMaNV(tuKhoaFinal);
                        break;
                    case "Mã Khách Hàng":
                        dsKetQua = HoaDonDAO.timHDTheoMaKH(tuKhoaFinal);
                        break;
                    case "SĐT Khách Hàng":
                        dsKetQua = HoaDonDAO.timHDTheoSDTKH(tuKhoaFinal);
                        break;
                    case "Ngày lập (yyyy-MM-dd)":
                        LocalDate date = LocalDate.parse(tuKhoaFinal);
                        dsKetQua = HoaDonDAO.timHDTheoNgayLap(date);
                        break;
                }
            } catch (DateTimeParseException e) {
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