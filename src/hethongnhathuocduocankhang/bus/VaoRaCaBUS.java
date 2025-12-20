package hethongnhathuocduocankhang.bus;

import hethongnhathuocduocankhang.dao.CaLamDAO;
import hethongnhathuocduocankhang.dao.LichSuCaLamDAO;
import hethongnhathuocduocankhang.dao.NhanVienDAO;
import hethongnhathuocduocankhang.entity.CaLam;
import hethongnhathuocduocankhang.entity.LichSuCaLam;
import hethongnhathuocduocankhang.entity.NhanVien;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class VaoRaCaBUS {
    
    private final LichSuCaLamDAO lsDAO = new LichSuCaLamDAO();
    // private final CaLamDAO caLamDAO = new CaLamDAO(); // Nếu bạn có instance, hoặc dùng static method như code cũ
    
    /**
     * Kiểm tra xem nhân viên đã vào ca trong ngày chưa để set trạng thái nút
     */
    public boolean kiemTraDangLamViec(String maNV) {
        try {
            return lsDAO.kiemTraNhanVienDangLamViec(maNV, LocalDate.now());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xác định ca làm dựa trên giờ hiện tại
     * @return CaLam object hoặc null nếu không tìm thấy
     */
    public CaLam getCaLamHienTai() {
        LocalTime gioHienTai = LocalTime.now();
        String maCa = "";
        
        if (gioHienTai.getHour() >= 6 && gioHienTai.getHour() < 14) {
            maCa = "SANG";
        } else if (gioHienTai.getHour() >= 14 && gioHienTai.getHour() < 22){
            maCa = "TOI";
        }
        
        try {
            return CaLamDAO.timCaLamTheoMa(maCa);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean xuLyVaoCa(String maNV, CaLam caLam) throws SQLException {
        NhanVien nv = NhanVienDAO.getNhanVienTheoMaNV(maNV);
        LocalDate ngayHienTai = LocalDate.now();
        LocalTime gioHienTai = LocalTime.now();
        
        LichSuCaLam ls = new LichSuCaLam(nv, ngayHienTai, caLam, gioHienTai, null, "");
        
        return lsDAO.themLichSuCaLam(ls);
    }

    public boolean xuLyRaCa(String maNV, CaLam caLam, String ghiChu) throws SQLException {
        LocalDate ngayHienTai = LocalDate.now();
        LocalTime gioHienTai = LocalTime.now();
        
        return lsDAO.capNhatRaCa(maNV, caLam.getMaCa(), ngayHienTai, gioHienTai, ghiChu);
    }
}