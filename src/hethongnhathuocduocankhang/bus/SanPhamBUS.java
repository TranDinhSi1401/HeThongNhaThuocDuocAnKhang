/*
 * Class BUS: Xử lý logic nghiệp vụ, gán sự kiện cho GUI và gọi DAO
 */
package hethongnhathuocduocankhang.bus;

import hethongnhathuocduocankhang.dao.*;
import hethongnhathuocduocankhang.entity.*;
import hethongnhathuocduocankhang.gui.QuanLiSanPhamGUI;
import hethongnhathuocduocankhang.gui.ThemSanPhamGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SanPhamBUS {

    private final QuanLiSanPhamGUI QLSanPhamGUI;

    // Các danh sách tạm để lưu dữ liệu khi đang thao tác trên Form Thêm/Sửa
    private List<DonViTinh> listTempDVT = new ArrayList<>();
    private List<SanPhamCungCap> listTempSPCC = new ArrayList<>();
    private List<KhuyenMai> listTempKM = new ArrayList<>();

    public SanPhamBUS(QuanLiSanPhamGUI view) {
        this.QLSanPhamGUI = view;

        // 1. Load dữ liệu ban đầu lên bảng
        loadDataToTable(SanPhamDAO.getAllTableSanPham());

        // 2. Gán sự kiện cho các nút trên màn hình chính
        addMainEvents();
    }

    // ========================================================================
    // A. XỬ LÝ SỰ KIỆN MÀN HÌNH QuanLiSanPhamGUI
    // ========================================================================
    private void addMainEvents() {
        // Nút Thêm
        QLSanPhamGUI.getBtnThem().addActionListener(e -> hienThiFormThemSanPham());

        // Nút Xóa
        QLSanPhamGUI.getBtnXoa().addActionListener(e -> xuLyXoaSanPham());

        // Nút Sửa
        QLSanPhamGUI.getBtnSua().addActionListener(e -> hienThiFormSuaSanPham());

        // Tìm kiếm, hỗ trợ người dùng khi chọn vào một tiêu chí tìm kiếm thì tự focus vào txt tìm kiếm
        QLSanPhamGUI.getTxtTimKiem().addActionListener(e -> xuLyTimKiem());
        QLSanPhamGUI.getCmbTieuChiTimKiem().addActionListener(e -> {
            QLSanPhamGUI.getTxtTimKiem().setText("");
            QLSanPhamGUI.getTxtTimKiem().requestFocus();
        });

        // Lọc
        QLSanPhamGUI.getCmbBoLoc().addActionListener(e -> xuLyLoc());

        // Sự kiện click bảng (Double click để xem chi tiết)
        QLSanPhamGUI.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    hienThiChiTietSanPham();
                }
            }
        });

        // Đếm số dòng đang chọn
        QLSanPhamGUI.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                QLSanPhamGUI.getLblSoDongChon().setText("Đang chọn: " + QLSanPhamGUI.getTable().getSelectedRowCount());
            }
        });
    }

    //Xóa bảng cũ và tải danh sách sản phẩm truyền vào lên bảng
    private void loadDataToTable(ArrayList<SanPham> dsSP) {
        DefaultTableModel model = QLSanPhamGUI.getModel();
        model.setRowCount(0);

        if (dsSP == null) {
            QLSanPhamGUI.getLblTongSoDong().setText("Tổng số sản phẩm: 0");
            return;
        }

        int stt = 1;
        for (SanPham sp : dsSP) {
            model.addRow(new Object[]{
                stt++,
                sp.getMaSP(),
                sp.getTen(),
                sp.getMoTa(),
                sp.getThanhPhan(),
                sp.getLoaiSanPham().toString(),
                sp.getTonToiThieu(),
                sp.getTonToiDa(),
                sp.isDaXoa() ? "Ngừng bán" : "Đang bán"
            });
        }
        QLSanPhamGUI.getLblTongSoDong().setText("Tổng số sản phẩm: " + dsSP.size());
    }

    private void xuLyTimKiem() {
        String tuKhoa = QLSanPhamGUI.getTxtTimKiem().getText().trim();
        String tieuChi = QLSanPhamGUI.getCmbTieuChiTimKiem().getSelectedItem().toString();
        ArrayList<SanPham> dsKetQua = new ArrayList<>();

        if (tuKhoa.isEmpty()) {
            dsKetQua = SanPhamDAO.getAllTableSanPham();
        } else {
            switch (tieuChi) {
                case "Mã sản phẩm":
                    SanPham sp = SanPhamDAO.timSPTheoMa(tuKhoa);
                    if (sp != null) {
                        dsKetQua.add(sp);
                    }
                    break;
                case "Tên sản phẩm":
                    dsKetQua = SanPhamDAO.timSPTheoTen(tuKhoa);
                    break;
                case "Mã nhà cung cấp":
                    dsKetQua = SanPhamDAO.timSPTheoMaNCC(tuKhoa);
                    break;
            }
        }
        loadDataToTable(dsKetQua);
    }

    private void xuLyLoc() {
        String boLoc = QLSanPhamGUI.getCmbBoLoc().getSelectedItem().toString();
        String locTheo = "";

        if (boLoc.equals("Thuốc kê đơn")) {
            locTheo = "THUOC_KE_DON";
        } else if (boLoc.equals("Thuốc không kê đơn")) {
            locTheo = "THUOC_KHONG_KE_DON";
        } else if (boLoc.equals("Thực phẩm chức năng")) {
            locTheo = "THUC_PHAM_CHUC_NANG";
        }

        if (boLoc.equals("Tất cả")) {
            loadDataToTable(SanPhamDAO.getAllTableSanPham());
        } else {
            loadDataToTable(SanPhamDAO.timSPTheoLoai(locTheo));
        }
    }

    private void xuLyXoaSanPham() {
        int[] selectedRows = QLSanPhamGUI.getTable().getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(QLSanPhamGUI, "Vui lòng chọn sản phẩm cần xóa.");
            return;
        }

        if (JOptionPane.showConfirmDialog(QLSanPhamGUI, "Bạn có chắc muốn xóa các sản phẩm đã chọn?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            int count = 0;
            for (int row : selectedRows) {
                String maSP = QLSanPhamGUI.getModel().getValueAt(row, 1).toString();
                if (SanPhamDAO.xoaSanPham(maSP)) {
                    count++;
                }
            }
            if (count > 0) {
                JOptionPane.showMessageDialog(QLSanPhamGUI, "Đã xóa thành công " + count + " sản phẩm.");
                loadDataToTable(SanPhamDAO.getAllTableSanPham());
                QLSanPhamGUI.getLblSoDongChon().setText("Đang chọn: 0");
            } else {
                JOptionPane.showMessageDialog(QLSanPhamGUI, "Xóa thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ========================================================================
    // B. XỬ LÝ FORM THÊM / SỬA / XEM CHI TIẾT TỪ ThemSanPhamGUI
    // ========================================================================
    // --- 1. MỞ FORM THÊM MỚI ---
    private void hienThiFormThemSanPham() {
        ThemSanPhamGUI pnlThemSP = new ThemSanPhamGUI();

        // Reset list tạm
        listTempDVT.clear();
        listTempSPCC.clear();
        listTempKM.clear();

        // Tự động sinh mã SP mới
        int maSPCuoi = SanPhamDAO.getMaSPCuoiCung();
        String maSPNew = String.format("SP-%04d", maSPCuoi + 1);
        pnlThemSP.getTxtMaSanPham().setText(maSPNew);
        pnlThemSP.getTxtMaSanPham().setEditable(false);

        // Tạo Dialog
        JDialog dialog = createDialog(pnlThemSP, "Thêm sản phẩm mới");

        // Gán sự kiện cho form
        setupSuKienTrenThemSanPhamGUI(pnlThemSP, dialog, false); // isEditMode = false, là chế độ thêm không phải chế độ sửa

        dialog.setVisible(true);
    }

    // --- 2. MỞ FORM SỬA ---
    private void hienThiFormSuaSanPham() {
        int selectedRow = QLSanPhamGUI.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(QLSanPhamGUI, "Vui lòng chọn sản phẩm cần sửa.");
            return;
        }

        String maSP = QLSanPhamGUI.getModel().getValueAt(selectedRow, 1).toString();
        SanPham sp = SanPhamDAO.timSPTheoMa(maSP);
        if (sp == null) {
            return;
        }

        ThemSanPhamGUI pnlSuaSP = new ThemSanPhamGUI();
        JDialog dialog = createDialog(pnlSuaSP, "Sửa sản phẩm: " + sp.getTen());

        // Load dữ liệu cũ từ DB vào Form và List tạm
        loadDataToForm(pnlSuaSP, sp);

        // Gán sự kiện
        setupSuKienTrenThemSanPhamGUI(pnlSuaSP, dialog, true); // isEditMode = true, là chế độ sửa không phải chế độ thêm

        dialog.setVisible(true);
    }

    // --- 3. MỞ FORM CHI TIẾT (VIEW ONLY) ---
    private void hienThiChiTietSanPham() {
        int selectedRow = QLSanPhamGUI.getTable().getSelectedRow();
        String maSP = QLSanPhamGUI.getModel().getValueAt(selectedRow, 1).toString();
        SanPham sp = SanPhamDAO.timSPTheoMa(maSP);

        ThemSanPhamGUI pnlChiTietSP = new ThemSanPhamGUI();
        JDialog dialog = createDialog(pnlChiTietSP, "Chi tiết: " + sp.getTen());

        loadDataToForm(pnlChiTietSP, sp);
        khoaGiaoDienChiTiet(pnlChiTietSP);

        pnlChiTietSP.getBtnHuy().setText("Đóng");
        pnlChiTietSP.getBtnHuy().addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    // --- 4. CÁC HÀM HỖ TRỢ FORM ---
    private JDialog createDialog(JPanel content, String title) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setContentPane(content);
        dialog.setSize(950, 850);
        dialog.setLocationRelativeTo(null);
        return dialog;
    }

    private void setupSuKienTrenThemSanPhamGUI(ThemSanPhamGUI pnlThemSP, JDialog dialog, boolean isEditMode) {
        // Nút Hủy
        pnlThemSP.getBtnHuy().addActionListener(e -> dialog.dispose());

        // Nút Lưu
        pnlThemSP.getBtnXacNhan().addActionListener(e -> {
            if (checkHopLeVaLuu(pnlThemSP, isEditMode)) {
                dialog.dispose();
                loadDataToTable(SanPhamDAO.getAllTableSanPham());
            }
        });

        // --- SỰ KIỆN BARCODE ---
        pnlThemSP.getBtnThemBarcode().addActionListener(e -> {
            String code = pnlThemSP.getTxtInputBarcode().getText().trim();
            if (code.isEmpty()) {
                return;
            }

            // Check trùng trên bảng
            for (int i = 0; i < pnlThemSP.getModelBarcode().getRowCount(); i++) {
                if (pnlThemSP.getModelBarcode().getValueAt(i, 0).equals(code)) {
                    JOptionPane.showMessageDialog(dialog, "Mã vạch này đã có trong danh sách!");
                    return;
                }
            }
            pnlThemSP.getModelBarcode().addRow(new Object[]{code});
            pnlThemSP.getTxtInputBarcode().setText("");
            pnlThemSP.getTxtInputBarcode().requestFocus();
        });

        pnlThemSP.getBtnXoaBarcode().addActionListener(e -> {
            int[] selectedRows = pnlThemSP.getTblBarcode().getSelectedRows();
            if (selectedRows.length > 0) {
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    pnlThemSP.getModelBarcode().removeRow(selectedRows[i]);
                }
            }
        });

        // --- SỰ KIỆN ĐƠN VỊ TÍNH ---
        pnlThemSP.getChkDonViCoBan().addActionListener(e -> {
            boolean isCoBan = pnlThemSP.getChkDonViCoBan().isSelected();
            if (isCoBan) {
                pnlThemSP.getTxtHeSoQuyDoi().setText("1");
                pnlThemSP.getTxtHeSoQuyDoi().setEnabled(false);
            } else {
                pnlThemSP.getTxtHeSoQuyDoi().setEnabled(true);
                pnlThemSP.getTxtHeSoQuyDoi().setText("");
            }
        });

        pnlThemSP.getBtnThemDVT().addActionListener(e -> {
            try {
                String tenDV = pnlThemSP.getTxtTenDonVi().getText().trim();
                if (tenDV.isEmpty()) {
                    throw new Exception("Chưa nhập tên ĐVT");
                }
                int heSo = Integer.parseInt(pnlThemSP.getTxtHeSoQuyDoi().getText().trim());
                String giaBanFormatted = pnlThemSP.getTxtGiaBanDonVi().getText();
                String giaBanRaw = giaBanFormatted.replaceAll("\\D", "");
                double gia = 0.0;
                if (!giaBanRaw.trim().isEmpty()) {
                    gia = Double.parseDouble(giaBanRaw);
                }
                boolean isCoBan = pnlThemSP.getChkDonViCoBan().isSelected();
                if (isCoBan) {
                    for (DonViTinh dv : listTempDVT) {
                        if (dv.isDonViTinhCoBan()) {
                            JOptionPane.showMessageDialog(dialog, "Đã tồn tại đơn vị tính cơ bản (" + dv.getTenDonVi() + ")");
                            return;
                        }
                    }
                }

                String maDVT = "DVT-" + pnlThemSP.getTxtMaSanPham().getText().split("-")[1] + "-" + tenDV;
                DonViTinh dvt = new DonViTinh(maDVT, new SanPham(pnlThemSP.getTxtMaSanPham().getText()), heSo, gia, tenDV, isCoBan);

                listTempDVT.add(dvt);
                pnlThemSP.getModelDVT().addRow(new Object[]{
                    maDVT, tenDV, heSo, String.format("%,.0f", gia), isCoBan ? "Có" : "Không"
                });

                // Clear input
                pnlThemSP.getTxtTenDonVi().setText("");
                pnlThemSP.getTxtGiaBanDonVi().setText("");
                pnlThemSP.getChkDonViCoBan().setSelected(false);
                pnlThemSP.getTxtHeSoQuyDoi().setEnabled(true);
                pnlThemSP.getTxtHeSoQuyDoi().setText("");

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(dialog, "Hệ số hoặc Giá bán phải là số hợp lệ.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage());
            }
        });

        pnlThemSP.getBtnXoaDVT().addActionListener(e -> {
            int[] cacDongDaChon = pnlThemSP.getTblDonViTinh().getSelectedRows();
            if (cacDongDaChon.length > 0) {
                for (int i = cacDongDaChon.length - 1; i >= 0; i--) {
                    int dongDangChon = cacDongDaChon[i];
                    if (dongDangChon < listTempDVT.size()) {
                        listTempDVT.remove(dongDangChon);
                    }
                    pnlThemSP.getModelDVT().removeRow(dongDangChon);
                }
            }
        });

        // --- SỰ KIỆN NHÀ CUNG CẤP ---
        pnlThemSP.getBtnTimNCC().addActionListener(e -> {
            String timNCC = pnlThemSP.getTxtTimNCC().getText().trim();
            pnlThemSP.getModelTimKiemNCC().setRowCount(0);
            ArrayList<NhaCungCap> dsNCC = timNCC.isEmpty() ? NhaCungCapDAO.getAllNhaCungCap() : NhaCungCapDAO.timNCCTheoTen(timNCC);
            for (NhaCungCap ncc : dsNCC) {
                pnlThemSP.getModelTimKiemNCC().addRow(new Object[]{ncc.getMaNCC(), ncc.getTenNCC(), ncc.getSdt(), ncc.getDiaChi()});
            }
        });

        pnlThemSP.getBtnThemNCC().addActionListener(e -> {
            int[] rows = pnlThemSP.getTableKQTimKiemNCC().getSelectedRows();
            if (rows.length == 0) {
                return;
            }
            try {
                double gia = Double.parseDouble(pnlThemSP.getTxtGiaNhap().getText().trim());
                for (int r : rows) {
                    String maNCC = pnlThemSP.getModelTimKiemNCC().getValueAt(r, 0).toString();
                    String tenNCC = pnlThemSP.getModelTimKiemNCC().getValueAt(r, 1).toString();
                    // Check trùng trong listTemp
                    boolean tonTai = listTempSPCC.stream().anyMatch(spcc -> spcc.getNhaCungCap().getMaNCC().equals(maNCC));
                    if (!tonTai) {
                        NhaCungCap ncc = NhaCungCapDAO.timNCCTheoMa(maNCC);
                        SanPhamCungCap spcc = new SanPhamCungCap(new SanPham(pnlThemSP.getTxtMaSanPham().getText()), ncc, tonTai, gia);
                        listTempSPCC.add(spcc);
                        pnlThemSP.getModelNCCChon().addRow(new Object[]{maNCC, tenNCC, String.format("%,.0f", gia)});
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Giá nhập không hợp lệ.");
            }
        });

        pnlThemSP.getBtnXoaNCC().addActionListener(e -> {
            int[] rows = pnlThemSP.getTblNCCChon().getSelectedRows();
            if (rows.length == 0) {
                return;
            }
            for (int i = rows.length - 1; i >= 0; i--) {
                int row = rows[i];
                if (row < listTempSPCC.size()) {
                    listTempSPCC.remove(row);
                }
                pnlThemSP.getModelNCCChon().removeRow(row);
            }
        });

        // --- SỰ KIỆN KHUYẾN MÃI ---
        pnlThemSP.getBtnTimKM().addActionListener(e -> {
            String timKM = pnlThemSP.getTxtTimKM().getText().trim();
            pnlThemSP.getModelKQTimKiemKM().setRowCount(0);
            ArrayList<KhuyenMai> dsKM = timKM.isEmpty() ? KhuyenMaiDAO.getAllKhuyenMai() : KhuyenMaiDAO.timKMTheoMoTa(timKM);
            for (KhuyenMai km : dsKM) {
                pnlThemSP.getModelKQTimKiemKM().addRow(new Object[]{
                    km.getMaKhuyenMai(), km.getMoTa(), km.getPhanTram(),
                    km.getNgayBatDau(), km.getNgayKetThuc()
                });
            }
        });

        pnlThemSP.getBtnThemKM().addActionListener(e -> {
            int[] rows = pnlThemSP.getTblTimKiemKM().getSelectedRows();
            for (int r : rows) {
                String maKM = pnlThemSP.getModelKQTimKiemKM().getValueAt(r, 0).toString();
                boolean tonTai = listTempKM.stream().anyMatch(km -> km.getMaKhuyenMai().equals(maKM));
                if (!tonTai) {
                    KhuyenMai km = KhuyenMaiDAO.timKMTheoMa(maKM);
                    if (km != null) {
                        listTempKM.add(km);
                        pnlThemSP.getModelKMChon().addRow(new Object[]{
                            km.getMaKhuyenMai(), km.getMoTa(), km.getPhanTram() + "%",
                            km.getSoLuongToiThieu(), km.getSoLuongToiDa(), LocalDate.now()
                        });
                    }
                }
            }
        });

        pnlThemSP.getBtnXoaKM().addActionListener(e -> {
            int[] rows = pnlThemSP.getTblKMChon().getSelectedRows();
            if (rows.length == 0) {
                return;
            }
            for (int i = rows.length - 1; i >= 0; i--) {
                int row = rows[i];
                if (row < listTempKM.size()) {
                    listTempKM.remove(row);
                }
                pnlThemSP.getModelKMChon().removeRow(row);
            }
        });
    }

    private boolean checkHopLeVaLuu(ThemSanPhamGUI form, boolean isEditMode) {
        if (form.getTxtTenSanPham().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tên sản phẩm không được để trống.");
            return false;
        }
        if (listTempDVT.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sản phẩm phải có ít nhất 1 đơn vị tính.");
            return false;
        }

        try {
            SanPham sp = new SanPham();
            sp.setMaSP(form.getTxtMaSanPham().getText());
            sp.setTen(form.getTxtTenSanPham().getText());
            sp.setMoTa(form.getTxtMoTa().getText());
            sp.setThanhPhan(form.getTxtThanhPhan().getText());

            int indexLoai = form.getCmbLoaiSanPham().getSelectedIndex();
            if (indexLoai == 0) {
                sp.setLoaiSanPham(LoaiSanPhamEnum.THUOC_KE_DON);
            } else if (indexLoai == 1) {
                sp.setLoaiSanPham(LoaiSanPhamEnum.THUOC_KHONG_KE_DON);
            } else {
                sp.setLoaiSanPham(LoaiSanPhamEnum.THUC_PHAM_CHUC_NANG);
            }

            sp.setTonToiThieu(Integer.parseInt(form.getTxtTonToiThieu().getText().trim()));
            sp.setTonToiDa(Integer.parseInt(form.getTxtTonToiDa().getText().trim()));

            boolean success = false;

            if (!isEditMode) //là chế độ thêm
            {
                if (SanPhamDAO.themSanPham(sp)) {
                    themCacTTLienQuanDenSP(sp, form);
                    success = true;
                    JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công!");
                }
            } else // là chế độ sửa
            {
                if (SanPhamDAO.suaSanPham(sp.getMaSP(), sp)) {
                    // Xóa liên kết cũ (Cách đơn giản nhất để cập nhật list con)
                    String maSP = sp.getMaSP();
                    MaVachSanPhamDAO.xoaMaVachTheoMaSP(maSP);
                    // Lưu ý: DVT thường không xóa hết rồi thêm lại vì dính khóa ngoại Invoice,
                    // ở đây dùng logic update đè hoặc thêm mới nếu chưa có.

                    // Với NCC và KM thì xóa hết liên kết cũ rồi thêm mới
                    SanPhamCungCapDAO.xoaHetNCCuaSP(maSP);
                    KhuyenMaiSanPhamDAO.xoaHetKMCuaSP(maSP);
                    themCacTTLienQuanDenSP(sp, form); // Lưu lại list mới
                    success = true;
                    JOptionPane.showMessageDialog(null, "Cập nhật sản phẩm thành công!");
                }
            }
            return success;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu: " + e.getMessage());
            return false;
        }
    }

    private void themCacTTLienQuanDenSP(SanPham sp, ThemSanPhamGUI form) {
        // ====================================================================
        // 1. XỬ LÝ ĐƠN VỊ TÍNH (Logic Thông Minh: Insert/Update/Delete an toàn)
        // ====================================================================

        // Bước A: Lấy danh sách DVT cũ đang nằm trong Database
        List<DonViTinh> listOldDVT = DonViTinhDAO.getDonViTinhTheoMaSP(sp.getMaSP());

        // Bước B: Xử lý những ĐVT bị người dùng xóa trên giao diện (Có trong DB cũ nhưng không có trong List mới)
        for (DonViTinh oldDVT : listOldDVT) {
            boolean conTonTai = false;
            for (DonViTinh newDVT : listTempDVT) {
                if (newDVT.getMaDonViTinh().equals(oldDVT.getMaDonViTinh())) {
                    conTonTai = true;
                    break;
                }
            }

            if (!conTonTai) {
                // User đã xóa dòng này trên GUI -> Tiến hành xóa trong DB
                try {
                    // Cần đảm bảo DAO có hàm xoaDonViTinh(String maDVT)
                    if (!DonViTinhDAO.xoaDonViTinh(oldDVT.getMaDonViTinh())) {
                        JOptionPane.showMessageDialog(null,
                                "Cảnh báo: Đơn vị tính '" + oldDVT.getTenDonVi()
                                + "' không thể xóa vì đã tồn tại trong lịch sử giao dịch (Hóa đơn/Kho).",
                                "Không thể xóa ĐVT", JOptionPane.WARNING_MESSAGE);

                        // (Tùy chọn) Nếu không xóa được, bạn có thể chọn giải pháp "Xóa mềm" (Soft Delete)
                        // Ví dụ: oldDVT.setTrangThai(NgungHoatDong); DonViTinhDAO.suaDonViTinh(oldDVT);
                    }
                } catch (Exception e) {
                    System.err.println("Lỗi khi xóa ĐVT: " + e.getMessage());
                }
            }
        }

        // Bước C: Xử lý Thêm mới hoặc Cập nhật (Duyệt danh sách mới từ GUI)
        for (DonViTinh dvt : listTempDVT) {
            dvt.setSanPham(sp);

            // Kiểm tra xem ĐVT này đã tồn tại trong DB chưa
            if (DonViTinhDAO.getDonViTinhTheoMaDVT(dvt.getMaDonViTinh()) != null) {
                // Đã tồn tại -> Thực hiện UPDATE (Cập nhật giá, hệ số quy đổi...)
                DonViTinhDAO.suaDonViTinh(dvt.getMaDonViTinh(), dvt);
            } else {
                // Chưa tồn tại -> Thực hiện INSERT
                DonViTinhDAO.themDonViTinh(dvt);
            }
        }
//        for (DonViTinh dvt : listTempDVT) {
//            dvt.setSanPham(sp);
//            // Kiểm tra tồn tại để Update hoặc Insert
//            if (DonViTinhDAO.getDonViTinhTheoMaDVT(dvt.getMaDonViTinh()) != null) {
//                DonViTinhDAO.suaDonViTinh(dvt.getMaDonViTinh(), dvt);
//            } else {
//                DonViTinhDAO.themDonViTinh(dvt);
//            }
//        }

        // 2. Lưu Barcode
        for (int i = 0; i < form.getModelBarcode().getRowCount(); i++) {
            String code = form.getModelBarcode().getValueAt(i, 0).toString();
            MaVachSanPham mv = new MaVachSanPham(sp, code);
            MaVachSanPhamDAO.themMaVach(mv);
        }

        // 3. Lưu NCC
        for (SanPhamCungCap spcc : listTempSPCC) {
            try {
                spcc.setSanPham(sp);
                SanPhamCungCapDAO.themSanPhamCungCap(spcc);
            } catch (Exception e) {
            }
        }

        // 4. Lưu KM
        for (KhuyenMai km : listTempKM) {
            KhuyenMaiSanPham kmsp = new KhuyenMaiSanPham();
            kmsp.setSanPham(sp);
            kmsp.setKhuyenMai(km);
            kmsp.setNgayChinhSua(LocalDate.now());
            try {
                KhuyenMaiSanPhamDAO.themKhuyenMaiSanPham(kmsp);
            } catch (Exception e) {
            }
        }
    }

    private void loadDataToForm(ThemSanPhamGUI form, SanPham sp) {
        // 1. Info cơ bản
        form.getTxtMaSanPham().setText(sp.getMaSP());
        form.getTxtMaSanPham().setEditable(false); // Khóa mã
        form.getTxtTenSanPham().setText(sp.getTen());
        form.getTxtMoTa().setText(sp.getMoTa());
        form.getTxtThanhPhan().setText(sp.getThanhPhan());
        form.getTxtTonToiThieu().setText(String.valueOf(sp.getTonToiThieu()));
        form.getTxtTonToiDa().setText(String.valueOf(sp.getTonToiDa()));

        LoaiSanPhamEnum loai = sp.getLoaiSanPham();
        if (loai == LoaiSanPhamEnum.THUOC_KE_DON) {
            form.getCmbLoaiSanPham().setSelectedIndex(0);
        } else if (loai == LoaiSanPhamEnum.THUOC_KHONG_KE_DON) {
            form.getCmbLoaiSanPham().setSelectedIndex(1);
        } else {
            form.getCmbLoaiSanPham().setSelectedIndex(2);
        }

        // 2. Load Barcode
        ArrayList<String> barcodes = MaVachSanPhamDAO.getDsMaVachTheoMaSP(sp.getMaSP());
        for (String code : barcodes) {
            form.getModelBarcode().addRow(new Object[]{code});
        }

        // 3. Load DVT vào listTemp và Table
        listTempDVT = DonViTinhDAO.getDonViTinhTheoMaSP(sp.getMaSP());
        for (DonViTinh dvt : listTempDVT) {
            form.getModelDVT().addRow(new Object[]{
                dvt.getMaDonViTinh(), dvt.getTenDonVi(), dvt.getHeSoQuyDoi(),
                String.format("%,.0f", dvt.getGiaBanTheoDonVi()),
                dvt.isDonViTinhCoBan() ? "Có" : "Không"
            });
        }

        // 4. Load NCC
        listTempSPCC = SanPhamCungCapDAO.getSanPhamCungCapTheoMaSP(sp.getMaSP());
        for (SanPhamCungCap spcc : listTempSPCC) {
            form.getModelNCCChon().addRow(new Object[]{
                spcc.getNhaCungCap().getMaNCC(),
                spcc.getNhaCungCap().getTenNCC(),
                String.format("%,.0f", spcc.getGiaNhap())
            });
        }

        // 5. Load KM
        listTempKM = KhuyenMaiDAO.getKhuyenMaiTheoMaSP(sp.getMaSP());
        for (KhuyenMai km : listTempKM) {
            form.getModelKMChon().addRow(new Object[]{
                km.getMaKhuyenMai(), km.getMoTa(), km.getPhanTram() + "%",
                km.getSoLuongToiThieu(), km.getSoLuongToiDa(), ""
            });
        }
    }

    private void khoaGiaoDienChiTiet(ThemSanPhamGUI form) {
        form.getTxtTenSanPham().setEditable(false);
        form.getTxtMoTa().setEditable(false);
        form.getTxtThanhPhan().setEditable(false);
        form.getCmbLoaiSanPham().setEnabled(false);
        form.getTxtTonToiThieu().setEditable(false);
        form.getTxtTonToiDa().setEditable(false);

        form.getTxtInputBarcode().setEditable(false);
        form.getBtnThemBarcode().setEnabled(false);
        form.getBtnXoaBarcode().setEnabled(false);

        form.getTxtTenDonVi().setEditable(false);
        form.getTxtHeSoQuyDoi().setEditable(false);
        form.getTxtGiaBanDonVi().setEditable(false);
        form.getChkDonViCoBan().setEnabled(false);
        form.getBtnThemDVT().setEnabled(false);
        form.getBtnXoaDVT().setEnabled(false);

        form.getTxtTimNCC().setEditable(false);
        form.getBtnTimNCC().setEnabled(false);
        form.getBtnThemNCC().setEnabled(false);
        form.getBtnXoaNCC().setEnabled(false);

        form.getTxtTimKM().setEditable(false);
        form.getBtnTimKM().setEnabled(false);
        form.getBtnThemKM().setEnabled(false);
        form.getBtnXoaKM().setEnabled(false);

        form.getBtnXacNhan().setVisible(false);
    }
}
