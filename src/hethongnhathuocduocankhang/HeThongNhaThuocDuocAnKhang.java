/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hethongnhathuocduocankhang;


import hethongnhathuocduocankhang.connectDB.ConnectDB;
import hethongnhathuocduocankhang.dao.KhachHangDAO;
import hethongnhathuocduocankhang.dao.NhanVienDAO;
import hethongnhathuocduocankhang.dao.SanPhamDAO;
import hethongnhathuocduocankhang.gui.DangNhapGUI;
import hethongnhathuocduocankhang.gui.GiaoDienChinhGUI;
import hethongnhathuocduocankhang.gui.SplashScreen;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author trand
 */
public class HeThongNhaThuocDuocAnKhang {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            SplashScreen ss = new SplashScreen(0);
            FlatRobotoFont.install();
            FlatLightLaf.setup();

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    try {
                        firePropertyChange("message", null, "Kết nối cơ sở dữ liệu...");
                        ConnectDB.getInstance().connect();
                        setProgress(20);
                        firePropertyChange("message", null, "Kết nối thành công");
                    } catch (Exception ex) {
                        firePropertyChange("message", null, "Lỗi kết nối cơ sở dữ liệu");
                        throw ex;
                    }

                    try {
                        firePropertyChange("message", null, "Tải dữ liệu sản phẩm...");
                        SanPhamDAO.getAllTableSanPham();
                        setProgress(50);
                        firePropertyChange("message", null, "Sản phẩm đã sẵn sàng");
                    } catch (Exception ex) {
                    }
                    try {
                        firePropertyChange("message", null, "Tải dữ liệu khách hàng...");
                        KhachHangDAO.getAllKhachHang();
                        setProgress(75);
                        firePropertyChange("message", null, "Khách hàng sẵn sàng");
                    } catch (Exception ex) {
                    }
                    try {
                        firePropertyChange("message", null, "Tải dữ liệu nhân viên...");
                        NhanVienDAO.getAllNhanVien();
                        setProgress(95);
                        firePropertyChange("message", null, "Nhân viên sẵn sàng");
                    } catch (Exception ex) {
                    }

                    setProgress(100);
                    return null;
                }

                @Override
                protected void done() {
                    try {
                        get();
                        ss.setVisible(false);
                        ss.dispose();
                        new DangNhapGUI().setVisible(true);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Lỗi khi khởi tạo dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        System.exit(1);
                    }
                }
            };
            ss.showDuring(worker);
        });
    }

}
