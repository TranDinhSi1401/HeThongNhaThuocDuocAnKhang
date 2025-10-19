/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

/**
 *
 * @author GIGABYTE
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QuanLiSanPhamGUI extends JPanel {

        // Khai báo các biến
    private JButton btnThem, btnXoa, btnSua, btnApDungBoLoc, btnTimKiem;
    private JCheckBox cbMaSP, cbTenSP, cbLoaiSP, cbNhaCC;
    private JRadioButton rbTatCa, rbKeDon, rbKhongKeDon, rbTPCN;
    private JTextField txtTimKiem;
    private JTable productTable;

    public QuanLiSanPhamGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10)); // Viền đệm

        // TẠO PANEL NORTH
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        northPanel.setPreferredSize(new Dimension(700, 50));
        //JPanel pnlTimKiem = new JPanel(new BorderLayout(5, 0));
        //pnlTimKiem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        txtTimKiem = new JTextField(78);
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        txtTimKiem.setBorder(new EmptyBorder(5, 5, 5, 5));

        btnTimKiem = new JButton();

        int kichThuoc = 30;
        String url = "/img/icon_kinh_lup.png";

        ImageIcon iconKinhLup = new ImageIcon(getClass().getResource(url));
        Image hinhKinhLup = iconKinhLup.getImage();
        Image scaleHinhKinhLup = hinhKinhLup.getScaledInstance(kichThuoc, kichThuoc, Image.SCALE_SMOOTH);
        ImageIcon scaledHinhKinhLup = new ImageIcon(scaleHinhKinhLup);
        btnTimKiem.setIcon(scaledHinhKinhLup);
        btnTimKiem.setPreferredSize(new Dimension(kichThuoc, kichThuoc));
        btnTimKiem.setMargin(new Insets(0, 0, 0, 0));
        northPanel.add(btnTimKiem);
        northPanel.add(txtTimKiem);

        // Thêm thanh tìm kiếm vào phía BẮC của centerPanel
        //centerPanel.add(searchBarPanel, BorderLayout.NORTH);
        //TẠO PANEL WEST
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setPreferredSize(new Dimension(220, 0));

        // 1.1. Nút Thêm Sản Phẩm
        btnThem = new JButton("Thêm Sản Phẩm");
        setupButton(btnThem, new Color(34, 139, 34), Color.WHITE, 14);

        // 1.2. Panel Tiêu chí tìm kiếm
        JPanel pnlTieuChiTimKiem = new JPanel();
        pnlTieuChiTimKiem.setPreferredSize(new Dimension(0, 150));
        pnlTieuChiTimKiem.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlTieuChiTimKiem.setBorder(createTitledBorder("TIÊU CHÍ TÌM KIẾM"));

        cbMaSP = new JCheckBox("MÃ SẢN PHẨM");
        cbMaSP.setSelected(true);
        cbTenSP = new JCheckBox("TÊN SẢN PHẨM");
        cbLoaiSP = new JCheckBox("LOẠI SẢN PHẨM");
        cbNhaCC = new JCheckBox("NHÀ CUNG CẤP");

        pnlTieuChiTimKiem.add(cbMaSP);
        pnlTieuChiTimKiem.add(cbTenSP);
        pnlTieuChiTimKiem.add(cbLoaiSP);
        pnlTieuChiTimKiem.add(cbNhaCC);
        pnlTieuChiTimKiem.setMaximumSize(new Dimension(Integer.MAX_VALUE, pnlTieuChiTimKiem.getPreferredSize().height));

        // 1.3. Panel Lọc theo
        JPanel pnlLoc = new JPanel();
        pnlLoc.setPreferredSize(new Dimension(0, 180));
        pnlLoc.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlLoc.setBorder(createTitledBorder("LỌC THEO"));

        rbTatCa = new JRadioButton("Tất cả");
        rbTatCa.setSelected(true);
        rbKeDon = new JRadioButton("Thuốc kê đơn");
        rbKhongKeDon = new JRadioButton("Thuốc không kê đơn");
        rbTPCN = new JRadioButton("Thực phẩm chức năng");

        ButtonGroup filterGroup = new ButtonGroup();
        filterGroup.add(rbTatCa);
        filterGroup.add(rbKeDon);
        filterGroup.add(rbKhongKeDon);
        filterGroup.add(rbTPCN);

        btnApDungBoLoc = new JButton("ÁP DỤNG BỘ LỌC");
        btnApDungBoLoc.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlLoc.add(rbTatCa);
        pnlLoc.add(new Label("\n\n"));
        pnlLoc.add(rbKeDon);
        pnlLoc.add(rbKhongKeDon);
        pnlLoc.add(rbTPCN);
        pnlLoc.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlLoc.add(btnApDungBoLoc);
        pnlLoc.setMaximumSize(new Dimension(Integer.MAX_VALUE, pnlLoc.getPreferredSize().height));

        // 1.4. Các nút Xóa và Sửa
        btnXoa = new JButton("Xóa Sản Phẩm");
        setupButton(btnXoa, new Color(220, 20, 60), Color.WHITE, 14);

        btnSua = new JButton("Sửa Sản Phẩm");
        setupButton(btnSua, new Color(65, 105, 225), Color.WHITE, 14);

        // 1.5. Thêm tất cả vào westPanel
        westPanel.add(btnThem);
        westPanel.add(Box.createRigidArea(new Dimension(0, 15))); //Tạo một khoảng trống cố định 15px theo chiều dọc.
        westPanel.add(pnlTieuChiTimKiem);
        westPanel.add(Box.createRigidArea(new Dimension(0, 15)));//Tạo một khoảng trống cố định 15px theo chiều dọc.
        westPanel.add(pnlLoc);

        //Đây là một “khoảng trống đàn hồi” (glue).
        //Khác với RigidArea (cố định), glue tự co giãn để chiếm hết phần trống còn lại trong panel.
        //Mục đích: đẩy các thành phần bên dưới (như nút Xóa, Sửa) xuống cuối panel.
        westPanel.add(Box.createVerticalGlue());
        westPanel.add(btnXoa);
        westPanel.add(Box.createRigidArea(new Dimension(0, 10)));//Tạo một khoảng trống cố định 15px theo chiều dọc.
        westPanel.add(btnSua);

        // 2. TẠO PANEL CENTE
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));

        // 2.2. Bảng dữ liệu (phía Trung tâm của centerPanel)
        String[] columnNames = {"MÃ SẢN PHẨM", "TÊN SẢN PHẨM", "<html><body style='color:blue;'>NGÀY SẢN XUẤT</body></html>", "HẠN SỬ DỤNG", "Thành phần", "Mô tả"};
        Object[][] data = {
            {"SP001", "Paracetamol 500mg", "01/01/2024", "01/01/2026", "Paracetamol", "Giảm đau, hạ sốt"},
            {"SP002", "Vitamin C", "15/05/2024", "15/05/2025", "Ascorbic Acid", "Bổ sung vitamin"},
            {"", "", "", "", "", ""}, {"", "", "", "", "", ""}, {"", "", "", "", "", ""},
            {"", "", "", "", "", ""}, {"", "", "", "", "", ""},};

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            //ghi đè (override) lại hàm isCellEditable() để ngăn người dùng chỉnh sửa dữ liệu trực tiếp trong bảng.
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        productTable = new JTable(model);
        productTable.setRowHeight(30);
        productTable.setFont(new Font("Arial", Font.PLAIN, 12));
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        productTable.getTableHeader().setBackground(new Color(240, 240, 240));
        productTable.getTableHeader().setReorderingAllowed(false); //người dùng có thể kéo thả tiêu đề cột để thay đổi vị trí.

        JScrollPane scrollPane = new JScrollPane(productTable);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(createTitledBorder("DANH SÁCH SẢN PHẨM"));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Thêm bảng vào TRUNG TÂM của centerPanel
        centerPanel.add(tablePanel, BorderLayout.CENTER);

        // 3.THÊM CÁC PANEL VÀO PANEL CHÍNH
        this.add(northPanel, BorderLayout.NORTH);
        this.add(westPanel, BorderLayout.WEST);
        this.add(centerPanel, BorderLayout.CENTER);
    }

    // --- CÁC HÀM HỖ TRỢ
    //Mục đích: tạo khung viền có tiêu đề đẹp, đồng bộ font.
    //createEtchedBorder() → tạo khung viền chìm.
    //title → dòng chữ trên khung (ví dụ: “DANH SÁCH SẢN PHẨM”).
    //TitledBorder.LEFT, TitledBorder.TOP → vị trí tiêu đề nằm góc trên bên trái.
    //Font: Arial, Bold, size 12.
    private TitledBorder createTitledBorder(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), title, TitledBorder.LEFT,
                TitledBorder.TOP, new Font("Arial", Font.BOLD, 12));
    }

    private void setupButton(JButton button, Color bgColor, Color fgColor, int fontSize) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Arial", Font.BOLD, fontSize));
        button.setMargin(new Insets(10, 10, 10, 10));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));
    }
}
