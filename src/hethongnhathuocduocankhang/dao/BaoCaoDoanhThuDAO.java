/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.dao;

import hethongnhathuocduocankhang.connectDB.ConnectDB;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

public class BaoCaoDoanhThuDAO {

    public Map<String, double[]> getDoanhThuMap(java.util.Date tuNgay, java.util.Date denNgay, String loaiThongKe) {
        Map<String, double[]> dataMap = new HashMap<>();
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String timeCol = "";
        String groupBy = "";

        // --- SỬA LỖI TẠI ĐÂY ---
        if ("Theo ngày".equals(loaiThongKe)) {
            timeCol = "CONVERT(VARCHAR(10), Ngay, 103)"; // dd/MM/yyyy
            // CŨ (SAI): groupBy = "Ngay"; -> Group theo giây
            // MỚI (ĐÚNG): Group theo chuỗi hiển thị
            groupBy = "CONVERT(VARCHAR(10), Ngay, 103)"; 
            
        } else if ("Theo tháng".equals(loaiThongKe)) {
            timeCol = "RIGHT('00' + CAST(MONTH(Ngay) AS VARCHAR), 2) + '/' + CAST(YEAR(Ngay) AS VARCHAR)";
            groupBy = "YEAR(Ngay), MONTH(Ngay)";
        } else if ("Theo quý".equals(loaiThongKe)) {
            timeCol = "N'Quý ' + CAST(DATEPART(QUARTER, Ngay) AS VARCHAR) + N' năm ' + CAST(YEAR(Ngay) AS VARCHAR)";
            groupBy = "YEAR(Ngay), DATEPART(QUARTER, Ngay)";
        } else if ("Theo năm".equals(loaiThongKe)) {
            timeCol = "N'Năm ' + CAST(YEAR(Ngay) AS VARCHAR)";
            groupBy = "YEAR(Ngay)";
        }

        // Câu SQL giữ nguyên logic UNION ALL
        String sql = "SELECT " + timeCol + " AS ThoiGian, " +
                     "SUM(CASE WHEN Loai = 1 THEN ThanhTien ELSE 0 END) AS TongHoaDon, " +
                     "SUM(CASE WHEN Loai = 2 THEN ThanhTien ELSE 0 END) AS TongTraHang " +
                     "FROM ( " +
                     "   SELECT ngayLapHoaDon AS Ngay, tongTien AS ThanhTien, 1 AS Loai FROM HoaDon " +
                     "   WHERE ngayLapHoaDon BETWEEN ? AND ? " +
                     "   UNION ALL " +
                     "   SELECT ngayLapPhieuTraHang AS Ngay, tongTienHoanTra AS ThanhTien, 2 AS Loai FROM PhieuTraHang " +
                     "   WHERE ngayLapPhieuTraHang BETWEEN ? AND ? " +
                     ") AS BangTam " +
                     "GROUP BY " + groupBy; 
                     // Lưu ý: Đã bỏ + ", " + timeCol ở group by vì groupBy bây giờ đã trùng hoặc bao hàm timeCol

        try {
            Timestamp start = new Timestamp(tuNgay.getTime());
            
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(denNgay);
            cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
            cal.set(java.util.Calendar.MINUTE, 59);
            cal.set(java.util.Calendar.SECOND, 59);
            Timestamp end = new Timestamp(cal.getTimeInMillis());

            stmt = con.prepareStatement(sql);
            stmt.setTimestamp(1, start);
            stmt.setTimestamp(2, end);
            stmt.setTimestamp(3, start);
            stmt.setTimestamp(4, end);

            rs = stmt.executeQuery();
            while (rs.next()) {
                String key = rs.getString("ThoiGian");
                double tongHD = rs.getDouble("TongHoaDon");
                double tongTra = rs.getDouble("TongTraHang");
                dataMap.put(key, new double[]{tongHD, tongTra});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
             try { if(stmt!=null) stmt.close(); if(rs!=null) rs.close(); } catch(SQLException ex){}
        }
        return dataMap;
    }

}
