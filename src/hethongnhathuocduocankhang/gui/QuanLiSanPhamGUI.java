package hethongnhathuocduocankhang.gui;

import hethongnhathuocduocankhang.bus.SanPhamBUS;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class QuanLiSanPhamGUI extends JPanel {

    private JButton btnThem, btnXoa, btnSua;
    private JTextField txtTimKiem;
    private JTable table;
    private JComboBox<String> cmbTieuChiTimKiem;
    private JComboBox<String> cmbBoLoc;
    private DefaultTableModel model;
    private JLabel lblTongSoDong;
    private JLabel lblSoDongChon;

    private SanPhamBUS sanPhamBUS;

    public QuanLiSanPhamGUI() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Khởi tạo các thành phần giao diện
        initComponents();

        // Khởi tạo BUS và load dữ liệu
        sanPhamBUS = new SanPhamBUS();
        sanPhamBUS.loadDataToTable(this, null);

        // Gắn sự kiện
        initEvents();
    }

    private void initComponents() {
        // PANEL NORTH (Chức năng & Tìm kiếm)
        JPanel pnlNorth = new JPanel(new BorderLayout());

        // Bên Trái: Các nút Thêm, Xóa, Sửa
        JPanel pnlNorthLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlNorthLeft.setBorder(new EmptyBorder(0, 0, 10, 0));

        btnThem = new JButton("Thêm - F6");
        btnXoa = new JButton("Xóa - Del");
        btnSua = new JButton("Sửa - F2");

        setupTopButton(btnThem, new Color(25, 118, 210));
        setupTopButton(btnXoa, new Color(255, 51, 51));
        setupTopButton(btnSua, new Color(0, 203, 0));

        pnlNorthLeft.add(btnThem);
        pnlNorthLeft.add(btnXoa);
        pnlNorthLeft.add(btnSua);
        pnlNorth.add(pnlNorthLeft, BorderLayout.WEST);

        // Bên Phải: Tìm kiếm & Lọc
        JPanel pnlNorthRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        cmbTieuChiTimKiem = new JComboBox<>(new String[]{"Mã sản phẩm", "Tên sản phẩm", "Mã nhà cung cấp"});
        cmbTieuChiTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbTieuChiTimKiem.setPreferredSize(new Dimension(150, 30));

        cmbBoLoc = new JComboBox<>(new String[]{"Tất cả", "Thuốc kê đơn", "Thuốc không kê đơn", "Thực phẩm chức năng"});
        cmbBoLoc.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbBoLoc.setPreferredSize(new Dimension(180, 30));

        txtTimKiem = new JTextField(20);
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));
        txtTimKiem.setPreferredSize(new Dimension(200, 30));
        txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                new EmptyBorder(5, 5, 5, 5)
        ));

        JPanel pnlTimKiem = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        pnlTimKiem.add(new JLabel("Tìm kiếm:"));
        pnlTimKiem.add(txtTimKiem);

        pnlNorthRight.add(new JLabel("Tìm theo:"));
        pnlNorthRight.add(cmbTieuChiTimKiem);
        pnlNorthRight.add(pnlTimKiem);
        pnlNorthRight.add(new JLabel("Lọc theo:"));
        pnlNorthRight.add(cmbBoLoc);
        pnlNorth.add(pnlNorthRight, BorderLayout.EAST);
        this.add(pnlNorth, BorderLayout.NORTH);

        // PANEL CENTER (Bảng dữ liệu)
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        String[] columnNames = {
            "STT", "Mã SP", "Tên SP", "Mô tả",
            "Thành phần", "Loại sản phẩm", "Tồn tối thiểu", "Tồn tối đa", "Trạng thái"
        };

        model = new DefaultTableModel(new Object[][]{}, columnNames) {
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
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        TableColumnModel columnModel = table.getColumnModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(0).setCellRenderer(centerRenderer);
        columnModel.getColumn(1).setPreferredWidth(80);
        columnModel.getColumn(1).setCellRenderer(centerRenderer);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(150);
        columnModel.getColumn(5).setPreferredWidth(120);
        columnModel.getColumn(5).setCellRenderer(centerRenderer);
        columnModel.getColumn(6).setPreferredWidth(80);
        columnModel.getColumn(6).setCellRenderer(centerRenderer);
        columnModel.getColumn(7).setPreferredWidth(80);
        columnModel.getColumn(7).setCellRenderer(centerRenderer);

        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        this.add(centerPanel, BorderLayout.CENTER);

        // PANEL SOUTH
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        lblTongSoDong = new JLabel("Tổng số sản phẩm: 0");
        lblTongSoDong.setFont(new Font("Arial", Font.BOLD, 13));
        lblTongSoDong.setForeground(new Color(0, 102, 204));

        lblSoDongChon = new JLabel("Đang chọn: 0");
        lblSoDongChon.setFont(new Font("Arial", Font.BOLD, 13));
        lblSoDongChon.setForeground(new Color(204, 0, 0));

        pnlSouth.add(lblTongSoDong);
        pnlSouth.add(new JSeparator(JSeparator.VERTICAL));
        pnlSouth.add(lblSoDongChon);
        this.add(pnlSouth, BorderLayout.SOUTH);

        // Map Keys
        mapKeyToClickButton("F6", btnThem);
        mapKeyToClickButton("DELETE", btnXoa);
        mapKeyToClickButton("F2", btnSua);
    }

    private void initEvents() {
        // Sự kiện THÊM
        btnThem.addActionListener(e -> {
            ThemSanPhamGUI dialog = new ThemSanPhamGUI(sanPhamBUS, false, "");
            dialog.setVisible(true);
            // Sau khi dialog đóng -> load lại bảng
            sanPhamBUS.loadDataToTable(this, null);
        });

        // Sự kiện SỬA
        btnSua.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa.");
                return;
            }
            String maSP = model.getValueAt(selectedRow, 1).toString();
            ThemSanPhamGUI dialog = new ThemSanPhamGUI(sanPhamBUS, true, maSP);
            dialog.setVisible(true);
            sanPhamBUS.loadDataToTable(this, null);
        });

        // Sự kiện XÓA
        btnXoa.addActionListener(e -> sanPhamBUS.xuLyXoaSanPham(this));

        // Sự kiện TÌM KIẾM
        txtTimKiem.addActionListener(e -> sanPhamBUS.xuLyTimKiem(this));
        cmbTieuChiTimKiem.addActionListener(e -> {
            txtTimKiem.selectAll();
            txtTimKiem.requestFocus();
        });

        // Sự kiện LỌC
        cmbBoLoc.addActionListener(e -> sanPhamBUS.xuLyLoc(this));

        // Sự kiện Table
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                lblSoDongChon.setText("Đang chọn: " + table.getSelectedRowCount());
            }
        });

        // Double Click xem chi tiết
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    String maSP = model.getValueAt(selectedRow, 1).toString();
                    ThemSanPhamGUI dialog = new ThemSanPhamGUI(sanPhamBUS, true, maSP);
                    dialog.setTitle("Chi tiết sản phẩm");
                    dialog.setVisible(true);
                }
            }
        });
    }

    private void mapKeyToClickButton(String key, AbstractButton button) {
        InputMap im = button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = button.getActionMap();
        im.put(KeyStroke.getKeyStroke(key), "click_" + key);
        am.put("click_" + key, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick();
            }
        });
    }

    private void setupTopButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setMargin(new Insets(5, 10, 5, 10));
        button.setPreferredSize(new Dimension(100, 30));
    }

    // Getters for BUS
    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public JTextField getTxtTimKiem() {
        return txtTimKiem;
    }

    public JComboBox<String> getCmbTieuChiTimKiem() {
        return cmbTieuChiTimKiem;
    }

    public JComboBox<String> getCmbBoLoc() {
        return cmbBoLoc;
    }

    public JLabel getLblTongSoDong() {
        return lblTongSoDong;
    }

    public JLabel getLblSoDongChon() {
        return lblSoDongChon;
    }
}
