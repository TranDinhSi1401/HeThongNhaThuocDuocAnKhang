/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.bus;

import hethongnhathuocduocankhang.dao.ChiTietHoaDonDAO;
import hethongnhathuocduocankhang.dao.ChiTietXuatLoDAO;
import hethongnhathuocduocankhang.dao.DonViTinhDAO;
import hethongnhathuocduocankhang.dao.HoaDonDAO;
import hethongnhathuocduocankhang.dao.KhachHangDAO;
import hethongnhathuocduocankhang.dao.KhuyenMaiDAO;
import hethongnhathuocduocankhang.dao.LoSanPhamDAO;
import hethongnhathuocduocankhang.dao.SanPhamDAO;
import hethongnhathuocduocankhang.entity.ChiTietHoaDon;
import hethongnhathuocduocankhang.entity.ChiTietXuatLo;
import hethongnhathuocduocankhang.entity.DonViTinh;
import hethongnhathuocduocankhang.entity.HoaDon;
import hethongnhathuocduocankhang.entity.KhachHang;
import hethongnhathuocduocankhang.entity.KhuyenMai;
import hethongnhathuocduocankhang.entity.LoSanPham;
import hethongnhathuocduocankhang.entity.LoaiSanPhamEnum;
import hethongnhathuocduocankhang.entity.NhanVien;
import hethongnhathuocduocankhang.entity.SanPham;
import hethongnhathuocduocankhang.entity.TaiKhoan;
import hethongnhathuocduocankhang.gui.GiaoDienChinhGUI;
import java.awt.Font;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author trand
 */
public class BanHangBUS {
    private static final String LINE = "=".repeat(120);
    private static final String SEPARATOR = "-".repeat(120);
    
    public String chuanHoaMaSP(String input) {
        if (input == null) return "";
        input = input.trim().toUpperCase();
        // Nếu dạng chuẩn rồi thì giữ nguyên
        if (input.matches("^SP-\\d{4}$")) {
            return input;
        }
        // Nếu nhân viên nhập sp0001 
        if (input.matches("^SP\\d{4}$")) {
            return input.substring(0, 2) + "-" + input.substring(2);
        }
        // Nếu nhân viên chỉ nhập số
        if (input.matches("^\\d{4}$")) {
            return "SP-" + input;
        }
        // Nếu nhân viên quét mã sản phẩm
        if (input.matches("^\\d{12}$")) {
            String maSP = SanPhamDAO.getMaSpTheoMaVach(input);
            return maSP;
        }
        return input;
    }
    
    public Object[] themChiTietHoaDon(String maSp, JTable tblCTHD) throws Exception{
        // Lấy các thông tin liên quan đến mã sp
        String maSP = chuanHoaMaSP(maSp);
        SanPham sp = SanPhamDAO.timSPTheoMa(maSP);
        ArrayList<DonViTinh> dsDVT = DonViTinhDAO.getDonViTinhTheoMaSP(maSP);
        ArrayList<KhuyenMai> dsKM = KhuyenMaiDAO.getKhuyenMaiTheoMaSP(maSP);
        ArrayList<LoSanPham> dsLSP = LoSanPhamDAO.getLoSanPhamTheoMaSP(maSP);
        int tongSoLuong = dsLSP.stream().mapToInt(LoSanPham :: getSoLuong).sum();
        
        // Bắt lỗi các trường hợp có thể xảy ra
        if (sp == null) {
            throw new Exception("Sản phẩm không tồn tại!");
        }        
        if (dsDVT == null || dsDVT.isEmpty()) {
            throw new Exception("Sản phẩm này chưa có đơn vị tính!");
        }
        if(tongSoLuong <= 0) {
            throw new Exception("Sản phẩm hiện tại hết hàng!");
        }
               
             
        // Lấy đơn vị tính cơ bản     
        dsDVT.sort((a, b) -> Double.compare(a.getHeSoQuyDoi(), b.getHeSoQuyDoi()));
        DonViTinh dvtMacDinh = dsDVT.get(0);
        String tenDVT = dvtMacDinh.getTenDonVi();
        
        // Nếu trùng sản phẩm
        for(int i = 0; i < tblCTHD.getRowCount(); i++) {
            if(dvtMacDinh.getMaDonViTinh().equalsIgnoreCase(tblCTHD.getValueAt(i, 7).toString())) {
                int soLuong = Integer.parseInt(tblCTHD.getValueAt(i, 4).toString());
                soLuong+=1;
                tblCTHD.setValueAt(soLuong, i, 4);
                return null;
            }
        }
        
        // Tạo thông tin chi tiết hóa đơn  
        String tenSP = sp.getTen(); 
        double donGia = dvtMacDinh.getGiaBanTheoDonVi();       
        int soLuong = 1;
        double giamGia = 0;
        dsKM.sort((b, a) -> Double.compare(a.getPhanTram(), b.getPhanTram()));
        for(KhuyenMai km : dsKM) {
            if(soLuong >= km.getSoLuongToiThieu() && soLuong <= km.getSoLuongToiDa()) {
                giamGia = km.getPhanTram();
                break;
            }
        }
        double thanhTien = soLuong * donGia * (1 - giamGia / 100);
        String maDVT = dvtMacDinh.getMaDonViTinh();
        
        Object[] newRow = {0, tenSP, tenDVT, donGia, soLuong, giamGia, thanhTien, maDVT, maSP};
        
        return newRow;
    }
    
    public Object[] thayDoiChiTietHoaDon(String maSP, int soLuong, String tenDVT) throws Exception{
        // Lấy khuyến mãi đủ điều kiện
        ArrayList<KhuyenMai> dskm = KhuyenMaiDAO.getKhuyenMaiTheoMaSP(maSP);
        dskm.sort((b, a) -> Double.compare(a.getPhanTram(), b.getPhanTram()));
        double giamGia = 0;
        for(KhuyenMai km : dskm) {
            if(soLuong >= km.getSoLuongToiThieu() && soLuong <= km.getSoLuongToiDa()) {
                giamGia = km.getPhanTram();
                break;
            } 
        }                               
        // Lấy số lượng trong kho
        ArrayList<LoSanPham> dsLSP = LoSanPhamDAO.getLoSanPhamTheoMaSP(maSP);
        int tongSoLuong = 0;
        for(LoSanPham lsp : dsLSP) {
            tongSoLuong += lsp.getSoLuong();
        }

        ArrayList<DonViTinh> dsDVT = DonViTinhDAO.getDonViTinhTheoMaSP(maSP);
        for (DonViTinh dvt : dsDVT) {
            if (dvt.getTenDonVi().equals(tenDVT)) {
                int heSoQuyDoi = dvt.getHeSoQuyDoi();
                double donGia = dvt.getGiaBanTheoDonVi();
                double thanhTien = soLuong * donGia * (1 - giamGia / 100);
                String maDVT = dvt.getMaDonViTinh();
                if(soLuong * heSoQuyDoi > tongSoLuong) {
                    throw new Exception("Không đủ số lượng");
                }
                if(soLuong < 1) {
                    throw new Exception("Số lượng phải lớn hơn bằng 1");
                }
                Object[] updatedInfo = {donGia, giamGia, thanhTien, maDVT};
                return updatedInfo;
            }
        }
        return null;
    }
    
    public KhachHang layThongTinKhachHang(String sdt) {        
        KhachHang kh = null;
        try {
            kh = KhachHangDAO.getKhachHangTheoSdt(sdt);
        } catch (SQLException ex) {
            return kh;
        }
        return kh;
    }
    
    public boolean kiemTraKeDon(String maSP) {
        SanPham sp = SanPhamDAO.timSPTheoMa(chuanHoaMaSP(maSP));
        if(sp != null) {
            return sp.getLoaiSanPham().equals(LoaiSanPhamEnum.THUOC_KE_DON);
        }
        return false;
    }
    
    public boolean thanhToan(JTable tblCTHD, String maKH, boolean chuyenKhoan, double tongTien) throws Exception{
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn thanh toán không?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        );
        // Xác nhận thanh toán
        if (confirm == JOptionPane.YES_OPTION) {
            TaiKhoan tk = GiaoDienChinhGUI.getTk();
            // Bắt lỗi 
            if(tk == null) {
                throw new Exception("Vui lòng nhấn vào ca làm trước khi thanh toán");
            }
            if(tblCTHD.getRowCount() == 0) {
                throw new Exception("Vui lòng thêm sản phẩm cần thanh toán");
            }
            for(int i = 0; i < tblCTHD.getRowCount(); i++) {
                String maSP = tblCTHD.getValueAt(i, 8).toString();
                if(kiemTraKeDon(maSP) && maKH.equalsIgnoreCase("KH-00000")) {
                    throw new Exception("Vui lòng lưu thông tin khách hàng trước khi thanh toán vì có thuốc kê đơn");
                }
            }
            
            
            // Lấy mã hóa đơn mới nhất
            HoaDon hdMoiNhat = HoaDonDAO.getHoaDonMoiNhatTrongNgay();
            LocalDateTime now = LocalDateTime.now();
            String maHDMoi;

            if (hdMoiNhat == null) {
                maHDMoi = taoMaHoaDonMoi(now.toLocalDate(), 1); 
            } else {
                String maHDTruoc = hdMoiNhat.getMaHoaDon();
                int soCuoiHD = laySoThuTu(maHDTruoc);
                maHDMoi = taoMaHoaDonMoi(now.toLocalDate(), soCuoiHD + 1);
            }

            // Tạo hóa đơn
            String maNV = GiaoDienChinhGUI.getTk().getNhanVien().getMaNV();
            LocalDateTime ngayLapHD = LocalDateTime.now();
            HoaDon hd = new HoaDon(maHDMoi, new NhanVien(maNV), ngayLapHD, new KhachHang(maKH), chuyenKhoan, tongTien);

            if(false == HoaDonDAO.insertHoaDon(hd)) {
                throw new Exception("Tạo hóa đơn thất bại");
            }      

            // Tạo danh sách chi tiết hóa đơn
            ArrayList<ChiTietHoaDon> dsCTHD = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel)tblCTHD.getModel();
            for(int i = 0; i < model.getRowCount(); i++) {
               String maDVT = String.valueOf(model.getValueAt(i, 7));
               int heSoQuyDoi = DonViTinhDAO.getDonViTinhTheoMaDVT(maDVT).getHeSoQuyDoi();
               int soLuong = Integer.parseInt(model.getValueAt(i, 4).toString());
               double donGia = Double.parseDouble(model.getValueAt(i, 3).toString());
               double giamGia = Double.parseDouble(model.getValueAt(i, 5).toString());
               double thanhTien = Double.parseDouble(model.getValueAt(i, 6).toString());

               String maSP = (String) model.getValueAt(i, 8);

               ChiTietHoaDon cthdMoiNhat = ChiTietHoaDonDAO.getChiTietHoaDonMoiNhatTrongNgay();
               String maCTHDMoi;
               if (cthdMoiNhat == null) {
                   maCTHDMoi = taoMaChiTietHoaDonMoi(now.toLocalDate(), 1);
               } else {
                   String maCTHDTruoc = cthdMoiNhat.getMaChiTietHoaDon();
                   int soCuoi = laySoThuTu(maCTHDTruoc);
                   maCTHDMoi = taoMaChiTietHoaDonMoi(now.toLocalDate(), soCuoi + 1);
               }

               ChiTietHoaDon cthd = new ChiTietHoaDon(maCTHDMoi, new HoaDon(maHDMoi), new DonViTinh(maDVT), soLuong, donGia, giamGia, thanhTien);           
               dsCTHD.add(cthd);
               if(false == ChiTietHoaDonDAO.insertChiTietHoaDon(cthd)) {
                   throw new Exception("Tạo chi tiết hóa đơn thất bại");
               } 

               // Trừ tồn kho và tạo chi tiết xuất lô
               int soLuongXuat = soLuong * heSoQuyDoi;
               ArrayList<LoSanPham> dsLSP = LoSanPhamDAO.getLoSanPhamTheoMaSP(maSP);
               for(LoSanPham lsp : dsLSP) {
                    int soLuongTon = lsp.getSoLuong();
                    if (soLuongXuat <= 0)
                        break;

                    if (soLuongTon >= soLuongXuat) {
                        LoSanPhamDAO.truSoLuong(lsp.getMaLoSanPham(), soLuongXuat);

                        ChiTietXuatLo ctxl = new ChiTietXuatLo(new LoSanPham(lsp.getMaLoSanPham()), new ChiTietHoaDon(maCTHDMoi), soLuongXuat);
                        ChiTietXuatLoDAO.insertChiTietXuatLo(ctxl);

                        soLuongXuat = 0;
                    } else {
                        LoSanPhamDAO.truSoLuong(lsp.getMaLoSanPham(), soLuongTon);
                        ChiTietXuatLo ctxl = new ChiTietXuatLo(new LoSanPham(lsp.getMaLoSanPham()), new ChiTietHoaDon(maCTHDMoi), soLuongXuat);
                        ChiTietXuatLoDAO.insertChiTietXuatLo(ctxl);
                        soLuongXuat -= soLuongTon;
                    }
               }

               if (soLuongXuat > 0) {
                    throw new Exception("Không đủ số lượng");
                }
            }

            // Cập nhật điểm tích lũy cho khách hàng mua lớn hơn bằng 1 ngàn 
            if(tongTien >= 1000 && !"KH-99999".equals(maKH)) {
                int diemTichLuy = (int) Math.floor(tongTien / 1000);
                KhachHangDAO.updateDiemTichLuy(diemTichLuy, maKH);
            }

            String noiDung = taoNoiDungHoaDon(hd, dsCTHD);
            JDialog dialog = new JDialog();
            dialog.setTitle(maHDMoi);
            dialog.setSize(1000, 600);
            dialog.setLocationRelativeTo(null);
            dialog.setModal(true);

            JTextArea area = new JTextArea(noiDung);
            area.setEditable(false);
            area.setFont(new Font("Courier New", Font.PLAIN, 13));

            dialog.add(new JScrollPane(area));
            dialog.setVisible(true);
            return true;
        }
        return false;
    }
    
    private String taoMaHoaDonMoi(LocalDate ngay, int soThuTu) {
        return String.format("HD-%s-%04d", 
            ngay.format(java.time.format.DateTimeFormatter.ofPattern("ddMMyy")),
            soThuTu);
    }

    private String taoMaChiTietHoaDonMoi(LocalDate ngay, int soThuTu) {
        return String.format("CTHD-%s-%04d", 
            ngay.format(java.time.format.DateTimeFormatter.ofPattern("ddMMyy")),
            soThuTu);
    }
    
    private int laySoThuTu(String ma) {
        try {
            String[] parts = ma.split("-");
            return Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public static String taoNoiDungHoaDon(HoaDon hd, ArrayList<ChiTietHoaDon> dsCTHD) {
        int WIDTH = 120;
        String LINE = "=".repeat(WIDTH);
        String SEPARATOR = "-".repeat(WIDTH);

        StringBuilder sb = new StringBuilder();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DecimalFormat df = new DecimalFormat("#,###");

        
        sb.append(LINE).append("\n");
        sb.append(center("HÓA ĐƠN BÁN HÀNG", WIDTH)).append("\n");
        sb.append(LINE).append("\n");

        sb.append(String.format("Mã hóa đơn : %s\n", hd.getMaHoaDon()));
        sb.append(String.format("Ngày lập   : %s\n", hd.getNgayLapHoaDon().format(fmt)));
        sb.append(String.format("Nhân viên  : %s\n", hd.getNhanVien().getMaNV()));
        sb.append(String.format("Khách hàng : %s\n", hd.getKhachHang().getMaKH()));
        sb.append(String.format("Hình thức  : %s\n", hd.isChuyenKhoan() ? "Chuyển khoản" : "Tiền mặt"));

        sb.append("\n").append(SEPARATOR).append("\n");

        // Giảm độ rộng cột cho phù hợp chiều ngang 120 ký tự
        sb.append(String.format("%-4s %-55s %-8s %-15s %-12s %-10s %-12s\n",
                "STT", "Sản phẩm", "SL", "ĐVT", "Đơn giá", "Giảm giá", "Thành tiền"));

        sb.append(SEPARATOR).append("\n");

        int stt = 1;
        double tongTien = 0;

        for (ChiTietHoaDon cthd : dsCTHD) {

            String maSP = DonViTinhDAO.getMaSanPhamTheoMaDVT(cthd.getDonViTinh().getMaDonViTinh());
            String tenSP = SanPhamDAO.timSPTheoMa(maSP).getTen();
            String tenDVT = DonViTinhDAO.getDonViTinhTheoMaDVT(cthd.getDonViTinh().getMaDonViTinh()).getTenDonVi();

            String giamStr = String.format("%.0f%%", cthd.getGiamGia());

            double thanhTien = cthd.getThanhTien();
            tongTien += thanhTien;

            if (tenSP.length() > 55)
                tenSP = tenSP.substring(0, 55); // tránh tràn dòng

            sb.append(String.format(
                    "%-4d %-55s %-8d %-15s %-12s %-10s %-12s\n",
                    stt++, tenSP, cthd.getSoLuong(), tenDVT,
                    df.format(cthd.getDonGia()), giamStr, df.format(thanhTien)
            ));
        }

        sb.append(SEPARATOR).append("\n");
        String tongCongStr = "TỔNG CỘNG: " + df.format(tongTien) + " VND";
        sb.append(alignRight(tongCongStr, WIDTH)).append("\n");
        sb.append(LINE).append("\n");

        sb.append(center("CẢM ƠN QUÝ KHÁCH, HẸN GẶP LẠI!", WIDTH)).append("\n");

        sb.append(LINE).append("\n");

        return sb.toString();
    }

    
    private static String center(String text, int width) {
        int padSize = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padSize)) + text;
    }

    private static String alignRight(String text, int width) {
        int padding = width - text.length();
        if (padding < 0) padding = 0;
        return " ".repeat(padding) + text;
    } 
}
