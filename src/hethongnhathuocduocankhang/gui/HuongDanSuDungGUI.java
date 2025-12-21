/*
 * Help Guide Panel
 */
package hethongnhathuocduocankhang.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class HuongDanSuDungGUI extends javax.swing.JPanel {

    private JTextField txtSearch;
    private DefaultListModel<String> topicModel;
    private JList<String> lstTopics;
    private JEditorPane edContent;
    private Map<String, String> contentMap;
    private List<String> allTopics;

    public HuongDanSuDungGUI() {
        initComponents();
        buildContent();
        buildUI();
        loadContent("Giới thiệu");
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setLayout(new BorderLayout());
    }

    private void buildContent() {
        contentMap = new LinkedHashMap<>();

        // Trang chủ
        String htmlGioiThieu = ""
            + "<html><body style='font-family: Segoe UI, sans-serif; font-size:13px; line-height:1.6; color:#222;'>"
            + "<h1 style='color:#1565c0; margin-bottom:5px;'>HƯỚNG DẪN SỬ DỤNG</h1>"
            + "<h2 style='color:#1565c0; margin-top:0;'>HỆ THỐNG NHÀ THUỐC DƯỢC AN KHANG</h2>"
            + "<hr style='border:none; border-top:2px solid #1565c0; margin:15px 0;'>"
            + "<p><b>Tài liệu này cung cấp hướng dẫn chi tiết về các chức năng chính của hệ thống</b>, giúp nhân viên nhà thuốc thực hiện công việc hàng ngày một cách hiệu quả và chính xác.</p>"
            + "<h3>📋 Nội dung chính:</h3>"
            + "<ul style='line-height:1.8;'>"
            + "<li><b>Quản lý sản phẩm & kho hàng</b> – Thêm sản phẩm, quản lý lô, kiểm soát hạn dùng</li>"
            + "<li><b>Nghiệp vụ bán hàng & trả hàng</b> – Bán hàng, trả hàng, xử lý sự cố</li>"
            + "<li><b>Tra cứu & báo cáo</b> – Tìm thông tin, xuất dữ liệu</li>"
            + "</ul>"
            + "<p style='color:#d32f2f; font-weight:bold;'>💡 Mẹo: Dùng phím tắt<br> "
                + "-	Trợ giúp (F1)<br>" +
                "-	Sửa (F2)<br>" +
                "-	Tìm (F3)<br>" +
                "-	Xác nhận (F4)<br>" +
                "-	Làm mới (F5)<br>" +
                "-	Thêm, tạo (F6)<br>" +
                "-	Chọn tất cả (F7)<br>" +
                "-	Bỏ chọn tất cả (F8)<br>" +
                "-	Xóa (F9 hoặc chọn + Delete)<br>" +
"-	Xóa trắng (F10)</p>"
            + "</body></html>";

        String htmlDaoTaoDauTien = ""
            + "<html><body style='font-family: Segoe UI; font-size:13px; line-height:1.6; color:#222;'>"
            + "<h2>Bắt đầu nhanh</h2>"
            + "<p>Hướng dẫn cơ bản để làm quen với hệ thống:</p>"
            + "<ol style='line-height:1.8;'>"
            + "<li><b>Đăng nhập</b> bằng tài khoản nhân viên được cấp</li>"
            + "<li><b>Chọn module</b> từ menu bên trái (Bán hàng, Quản lý, Trả hàng, ...)</li>"
            + "<li><b>Tìm kiếm</b> thông tin bằng các ô tìm kiếm hoặc phím tắt F3</li>"
            + "<li><b>Thực hiện thao tác</b> theo từng bước hướng dẫn dưới đây</li>"
            + "</ol>"
            + "<p style='background:#e3f2fd; padding:10px; border-left:4px solid #1976d2; margin-top:15px;'>"
            + "📌 <b>Lưu ý:</b> Luôn lưu dữ liệu trước khi thoát khỏi chức năng để tránh mất thông tin."
            + "</p>"
            + "</body></html>";

        String htmlThemSanPham = ""
            + "<html><body style='font-family: Segoe UI; font-size:13px; line-height:1.6; color:#222;'>"
            + "<h2>1.1. Thêm mới một sản phẩm (Thuốc / TPCN)</h2>"
            + "<p><b>Mục đích:</b> Đăng ký sản phẩm mới vào hệ thống để có thể bán hàng và quản lý kho.</p>"
            + "<h3>📍 Cách mở:</h3>"
            + "<ul>"
            + "<li>Menu bên trái → <b>Quản lý</b> → <b>Quản lý sản phẩm</b></li>"
            + "<li>Nút <b>Thêm [F6]</b> để tạo sản phẩm mới</li>"
            + "</ul>"
            + "<h3><b style='color:#d32f2f;'>⚠️ Bước 1 – Nhập thông tin chung</b></h3>"
            + "<ol style='line-height:1.8;'>"
            + "<li><b>Tên sản phẩm</b> – Nhập đầy đủ tên bằng tiếng Việt (VD: Aspirin 325mg)</li>"
            + "<li><b>Mã sản phẩm</b> – Để trống (hệ thống tự sinh)</li>"
            + "<li><b>Loại sản phẩm</b> – Chọn: Thuốc kê đơn / Thuốc OTC / TPCN / Dụng cụ</li>"
            + "<li><b>Mô tả công dụng</b> – Ghi ngắn gọn công dụng chính</li>"
            + "<li><b>Tồn tối thiểu</b> – VD: 10 (cảnh báo khi dưới 10)</li>"
            + "<li><b>Tồn tối đa</b> – VD: 100 (không mua thêm nếu đủ)</li>"
            + "</ol>"
            + "<h3><b style='color:#d32f2f;'>⚠️ Bước 2 – Nhập mã vạch (Barcode)</b></h3>"
            + "<ol style='line-height:1.8;'>"
            + "<li>Chuyển sang tab <b>Mã vạch</b></li>"
            + "<li>Bấm <b>+ Thêm</b> để thêm mã vạch mới</li>"
            + "<li>Nhập hoặc quét mã vạch từ bao bì sản phẩm</li>"
            + "<li>Nếu nhập sai, chọn dòng và bấm <b>Xóa</b></li>"
            + "</ol>"
            + "<h3><b style='color:#d32f2f;'>⚠️ Bước 3 – Cài đặt đơn vị tính (RẤT QUAN TRỌNG)</b></h3>"
            + "<p style='color:#d32f2f; font-weight:bold;'>Đây là bước tối quan trọng! Nếu không chọn đúng, giá bán sẽ sai!</p>"
            + "<ol style='line-height:1.8;'>"
            + "<li>Chuyển sang tab <b>Đơn vị tính</b></li>"
            + "<li><b>Đơn vị cơ bản</b> – Chọn đơn vị nhỏ nhất (VD: Viên, Hộp, Lọ, ...)</li>"
            + "<li><b>Hệ số quy đổi</b> – Nếu dùng nhiều đơn vị:<br/>"
            + "   VD: 1 Hộp = 10 Viên → hệ số = 10</li>"
            + "<li><b>Giá bán</b> – Nhập giá bán cho khách hàng</li>"
            + "<li>Bấm <b>Lưu</b> để lưu từng đơn vị</li>"
            + "</ol>"
            + "<p style='background:#fff3e0; padding:10px; border-left:4px solid #f57c00;'>"
            + "📌 <b>Lưu ý:</b> Nếu quên chọn đơn vị cơ bản, hệ thống sẽ hiển thị cảnh báo. Bắt buộc phải chọn."
            + "</p>"
            + "<h3>Bước 4 – Nhà cung cấp & Giá nhập</h3>"
            + "<ol style='line-height:1.8;'>"
            + "<li>Chuyển sang tab <b>Nhà cung cấp</b></li>"
            + "<li>Bấm <b>+ Thêm</b> để thêm NCC mới</li>"
            + "<li>Chọn <b>Tên nhà cung cấp</b> từ danh sách</li>"
            + "<li>Nhập <b>Giá nhập</b> (giá bán buôn từ NCC)</li>"
            + "<li>Bấm <b>Lưu</b></li>"
            + "</ol>"
            + "<h3>Bước 5 – Khuyến mãi (nếu có chương trình)</h3>"
            + "<ol style='line-height:1.8;'>"
            + "<li>Chuyển sang tab <b>Khuyến mãi</b></li>"
            + "<li>Chọn chương trình khuyến mãi phù hợp (nếu áp dụng)</li>"
            + "<li>Nhập điều kiện (VD: Mua 3 tặng 1)</li>"
            + "</ol>"
            + "<h3 style='color:#2e7d32;'>✓ Hoàn tất</h3>"
            + "<ol style='line-height:1.8;'>"
            + "<li>Bấm nút <b>Lưu sản phẩm [F4]</b> để lưu tất cả thông tin</li>"
            + "<li>Thông báo <b>\"Thêm sản phẩm thành công!\"</b> sẽ xuất hiện</li>"
            + "<li>Sản phẩm giờ đã có thể bán hàng</li>"
            + "</ol>"
            + "</body></html>";

        String htmlQuanLyLo = ""
            + "<html><body style='font-family: Segoe UI; font-size:13px; line-height:1.6; color:#222;'>"
            + "<h2>1.2. Quản lý lô hàng (Nhập kho & kiểm soát hạn dùng)</h2>"
            + "<p><b>Mục đích:</b> Theo dõi các lô sản phẩm, phát hiện sớm lô hết hạn, và quản lý tồn kho hiệu quả.</p>"
            + "<h3>📊 Các trạng thái lô:</h3>"
            + "<ul style='line-height:1.8;'>"
            + "<li><b style='color:#2e7d32;'>🟢 Còn hạn:</b> Lô còn hạn, có thể bán bình thường</li>"
            + "<li><b style='color:#f57c00;'>🟠 Sắp hết hạn:</b> Hạn sử dụng &lt;dưới 180 ngày, cảnh báo ưu tiên bán</li>"
            + "<li><b style='color:#d32f2f;'>🔴 Hết hạn:</b> Đã quá ngày hết hạn, ngưng bán ngay</li>"
            + "<li><b style='color:#757575;'>⚫ Đã hủy:</b> Lô đã hủy, không thể bán</li>"
            + "</ul>"
            + "<h3>📥 Nhập lô từ Excel</h3>"
            + "<ol style='line-height:1.8;'>"
            + "<li>Menu → <b>Quản lý</b> → <b>Quản lý lô</b> → Tab <b>Quản lý lô</b></li>"
            + "<li>Bấm nút <b>Excel [F6]</b></li>"
            + "<li>Chọn file .xlsx từ máy tính (file phải có cột: Mã SP, Mã lô, Ngày sản xuất, Ngày hết hạn, Số lượng, Giá nhập)</li>"
            + "<li>Hệ thống sẽ nhập toàn bộ dữ liệu từ Excel</li>"
            + "<li>Kiểm tra và bấm <b>Thêm lô [F4]</b> để xác nhận</li>"
            + "</ol>"
            + "<p style='background:#e8f5e9; padding:10px; border-left:4px solid #4caf50;'>"
            + "💡 <b>Mẹo:</b> Nhập từ Excel tiết kiệm thời gian khi nhập kho hàng loạt từ NCC."
            + "</p>"
            + "<h3>🗑️ Hủy lô</h3>"
            + "<ol style='line-height:1.8;'>"
            + "<li>Chọn lô cần hủy từ danh sách</li>"
            + "<li>Bấm nút <b>Hủy lô [F5]</b></li>"
            + "<li><b style='color:#d32f2f;'>BẮNG BUỘC</b> nhập <b>Lý do hủy:</b><br/>"
            + "   VD: \"Hết hạn 15/03/2025\", \"Lỗi bao bì\", \"Không bán được\"</li>"
            + "<li>Bấm <b>Xác nhận</b></li>"
            + "<li>Lô được đánh dấu <b>Đã hủy</b> và lưu vào lịch sử (không xóa vĩnh viễn)</li>"
            + "</ol>"
            + "<p style='background:#fce4ec; padding:10px; border-left:4px solid #c2185b;'>"
            + "⚠️ <b>Quan trọng:</b> Luôn nhập lý do hủy để quản trị viên có thể kiểm toán sau này!"
            + "</p>"
            + "</body></html>";

        String htmlHuongDanBanHangThongKe = ""
            + "<html><body style='font-family: Segoe UI; font-size:13px; line-height:1.6; color:#222;'>"
            + "<h2 style='color:#1565c0;'>Hướng dẫn: Bán hàng & Thống kê doanh thu</h2>"
            + "<p>Tài liệu nhanh dành cho <b>nhân viên quầy</b> và <b>quản lý</b> để vận hành mượt mà 2 nghiệp vụ quan trọng nhất:</p>"
            + "<ul style='line-height:1.8;'>"
            + "<li><b>Bán hàng đa nhiệm</b>: mở nhiều hóa đơn song song, chuyển đổi nhanh giữa khách.</li>"
            + "<li><b>Báo cáo & Thống kê doanh thu</b>: xem doanh thu theo ngày/tháng/quý/năm, xuất Excel.</li>"
            + "</ul>"
            + "<p style='background:#e8f5e9; padding:10px; border-left:4px solid #4caf50;'>💡 Mẹo: Đọc từng mục theo thứ tự — thực hành ngay trong hệ thống để nhớ thao tác.</p>"
            + "</body></html>";

        String htmlBanHang = ""
            + "<html><body style='font-family: Segoe UI; font-size:13px; line-height:1.6; color:#222;'>"
            + "<h2>2.1. Bán hàng đa nhiệm (BanHangGUI)</h2>"
            + "<p><b>Mục tiêu:</b> Làm việc với nhiều hóa đơn song song, chuyển đổi nhanh giữa các khách hàng mà không mất dữ liệu.</p>"
            + "<h3>🧭 Điều hướng cơ bản</h3>"
            + "<ol style='line-height:1.8;'>"
            + "<li>Vào Menu → <b>Bán hàng</b>. Hệ thống tự tạo sẵn <b>1 hóa đơn mặc định</b> ở tab đầu tiên.</li>"
            + "<li><b>Thêm hóa đơn mới:</b> Bấm nút <b>+</b> trên thanh tab để mở một hóa đơn (tab) mới.</li>"
            + "<li><b>Chuyển đổi giữa các hóa đơn:</b> Bấm chọn tab tương ứng để tiếp tục xử lý hóa đơn đó.</li>"
            + "<li><b>Đóng hóa đơn:</b> Bấm biểu tượng <b>x</b> trên tab. Nếu hóa đơn có dữ liệu chưa lưu, hệ thống sẽ yêu cầu <b>Lưu</b> hoặc <b>Hủy</b>.</li>"
            + "</ol>"
            + "<h3>🛒 Thao tác trong một hóa đơn</h3>"
            + "<ul style='line-height:1.8;'>"
            + "<li>Thêm sản phẩm bằng ô tìm kiếm (có thể hỗ trợ quét mã vạch).</li>"
            + "<li>Điều chỉnh số lượng, áp dụng khuyến mãi (nếu có), kiểm tra tạm tính.</li>"
            + "<li><b>Thanh toán/Lưu hóa đơn</b> theo quy trình tại quầy.</li>"
            + "</ul>"
            + "<h3>💡 Lưu ý & Mẹo</h3>"
            + "<ul style='line-height:1.8;'>"
            + "<li>Mỗi tab là <b>một hóa đơn độc lập</b> — chuyển tab sẽ không làm mất dữ liệu của tab khác.</li>"
            + "<li>Nên <b>lưu hóa đơn</b> trước khi đóng tab để tránh gián đoạn khi quầy đông khách.</li>"
            + "<li>Phím tắt hữu ích: <b>F3</b> để vào ô tìm sản phẩm, <b>F10</b> để làm mới/xóa trắng (tùy cấu hình).</li>"
            + "</ul>"
            + "</body></html>";

        String htmlTraHang = ""
            + "<html><body style='font-family: Segoe UI; font-size:13px; line-height:1.6; color:#222;'>"
            + "<h2>2.2. Quy trình trả hàng</h2>"
            + "<p><b>Mục đích:</b> Xử lý các trường hợp khách hàng trả lại hàng (lỗi, dị ứng, không cần, ...).</p>"
            + "<p style='background:#fff3e0; padding:10px; border-left:4px solid #f57c00;'>"
            + "⏰ <b>Quy tắc quan trọng:</b> Chỉ được trả hàng trong vòng <b>30 ngày</b> kể từ lập hóa đơn."
            + "</p>"
            + "<h3>🔍 Bước 1 – Tìm hóa đơn gốc</h3>"
            + "<ol style='line-height:1.8;'>"
            + "<li>Menu → <b>Trả hàng</b></li>"
            + "<li>Nhập <b>Mã hóa đơn trong ô tìm kiếm</li>"
            + "<li>Bấm <b>Tìm [F3]</b> hoặc phím <b>Enter</b></li>"
            + "<li>Nếu tìm thấy, danh sách chi tiết sản phẩm sẽ hiển thị</li>"
            + "<li>Nếu không tìm thấy hoặc quá 30 ngày, hệ thống sẽ thông báo lỗi</li>"
            + "</ol>"
            + "<h3>✓ Bước 2 – Chọn sản phẩm trả</h3>"
            + "<ol style='line-height:1.8;'>"
            + "<li>Xem danh sách <b>Sản phẩm từ hóa đơn</b></li>"
            + "<li>Tích dấu <b>☑</b> vào sản phẩm cần trả</li>"
            + "<li>Bấm <b>Chọn tất cả [F7]</b> nếu trả toàn bộ</li>"
            + "<li>Bấm <b>Xác nhận [F4]</b> để qua bước tiếp theo</li>"
            + "</ol>"
            + "<h3>💰 Bước 3 – Chọn lý do trả & tiền hoàn</h3>"
            + "<p><b>Tiền hoàn trả = Giá bán × Hệ số hoàn trả</b></p>"
            + "<table border='1' cellpadding='8' style='border-collapse:collapse; margin:10px 0;'>"
            + "<tr style='background:#f5f5f5;'>"
            + "<td><b>Lý do trả hàng</b></td>"
            + "<td><b>Hệ số hoàn trả</b></td>"
            + "<td><b>Ví dụ</b></td>"
            + "</tr>"
            + "<tr>"
            + "<td>Lỗi nhà sản xuất</td>"
            + "<td>100%</td>"
            + "<td>Bao bì bẹp, hạn dùng sai, ...</td>"
            + "</tr>"
            + "<tr>"
            + "<td>Dị ứng (sau khi dùng)</td>"
            + "<td>70%</td>"
            + "<td>Khách hàng dị ứng, không dùng tiếp</td>"
            + "</tr>"
            + "<tr>"
            + "<td>Khách hàng thay đổi nhu cầu</td>"
            + "<td>0% hoặc thương lượng</td>"
            + "<td>Không cần nữa, xin trả lại</td>"
            + "</tr>"
            + "</table>"
            + "<p><b>Cách nhập:</b></p>"
            + "<ol style='line-height:1.8;'>"
            + "<li>Chọn <b>Lý do trả hàng</b>, <b>Tình trạng nguyên vẹn sản phẩm</b>, <b>Số lượng</b> từ danh sách</li>"
            + "<li>Hệ số hoàn trả sẽ được tự động điền</li>"
            + "</ol>"
            + "<h3>📄 Bước 4 – Hoàn tất</h3>"
            + "<ol style='line-height:1.8;'>"
            + "<li>Kiểm tra lại <b>Tổng tiền hoàn</b> trước khi lưu</li>"
            + "<li>Bấm <b>Tạo phiếu trả [F6]</b></li>"
            + "<li>Phiếu trả hàng sẽ được tạo và lưu vào hệ thống. Sản phẩm nguyên vẹn và không có lỗi do nhà sản xuất sẽ được thêm lại vào lô. Điểm tích lũy bị trừ theo số tiền hoàn trả</li>"
            + "<li>Giao phiếu cho khách</li>"
            + "</ol>"
            + "<p style='background:#e8f5e9; padding:10px; border-left:4px solid #4caf50;'>"
            + "✓ <b>Lưu ý:</b> Với sản phẩm miễn hoàn trả hóa đơn vẫn tạo cùng với phiếu trả, và sản phẩm đó sẽ không được nhận trả ở lần tiếp theo nữa với bất kỳ lý do gì"
            + "</p>"
            + "</body></html>";

        String htmlTraCuu = ""
            + "<html><body style='font-family: Segoe UI; font-size:13px; line-height:1.6; color:#222;'>"
            + "<h2>3.1. Tra cứu thông tin nhanh</h2>"
            + "<p><b>Tính năng:</b> Tìm kiếm nhanh thông tin sản phẩm, hóa đơn, khách hàng, nhân viên.</p>"
            + "<h3>📌 Cách sử dụng:</h3>"
            + "<ol style='line-height:1.8;'>"
            + "<li>Menu → <b>Tra cứu</b></li>"
            + "<li>Chọn loại thông tin cần tìm (Sản phẩm, Hóa đơn, Khách hàng, ...)</li>"
            + "<li>Nhập từ khóa tìm kiếm (Tên, Mã, Số hóa đơn, ...)</li>"
            + "<li>Bấm <b>Tìm kiếm [F4]</b> hoặc <b>Enter</b></li>"
            + "<li>Kết quả sẽ hiển thị dưới dạng danh sách</li>"
            + "</ol>"
            + "<h3>⌨️ Phím tắt chung trong hệ thống:</h3>"
            + "<ul style='line-height:1.8;'>"
            + "<li><b>F3</b> – Focus vào ô tìm kiếm</li>"
            + "<li><b>F4</b> – Tìm kiếm / Xác nhận</li>"
            + "<li><b>F6</b> – Thêm mới / Nhập từ Excel</li>"
            + "<li><b>F5</b> – Hủy / Xóa</li>"
            + "<li><b>F7</b> – Chọn tất cả</li>"
            + "<li><b>F10</b> – Xóa trắng / Làm mới</li>"
            + "</ul>"
            + "</body></html>";

        String htmlBaoCao = ""
            + "<html><body style='font-family: Segoe UI; font-size:13px; line-height:1.6; color:#222;'>"
            + "<h2>4. BÁO CÁO & THỐNG KÊ DOANH THU (ThongKeHoaDonGUI)</h2>"
            + "<p><b>Mục đích:</b> Theo dõi doanh thu theo thời gian, phân tích xu hướng bán hàng, hỗ trợ ra quyết định.</p>"
            + "<h3>⏱️ Bộ lọc thời gian</h3>"
            + "<ul style='line-height:1.8;'>"
            + "<li>Chọn nhanh: <b>Ngày</b> / <b>Tháng</b> / <b>Quý</b> / <b>Năm</b>.</li>"
            + "<li>Tùy chọn: Chọn <b>khoảng ngày</b> cụ thể để xem dữ liệu chi tiết.</li>"
            + "</ul>"
            + "<h3>🧰 Thao tác chính</h3>"
            + "<ul style='line-height:1.8;'>"
            + "<li><b>Làm mới</b> để tải lại dữ liệu theo bộ lọc hiện tại.</li>"
            + "<li><b>Xuất Excel</b> để lưu và chia sẻ báo cáo.</li>"
            + "</ul>"
            + "<h3>📈 Biểu đồ & chỉ số</h3>"
            + "<ul style='line-height:1.8;'>"
            + "<li><b>Biểu đồ doanh thu</b>: Quan sát tăng/giảm theo mốc thời gian đã chọn.</li>"
            + "<li><b>Ô tổng quan</b>: Tổng doanh thu, số hóa đơn, trung bình/hóa đơn, giảm giá (nếu có).</li>"
            + "</ul>"
            + "<h3>📋 Bảng dữ liệu</h3>"
            + "<ul style='line-height:1.8;'>"
            + "<li>Sắp xếp theo cột, lọc/tìm nhanh theo mã HĐ, khách hàng, nhân viên.</li>"
            + "<li>Đối chiếu nhanh giữa bảng và biểu đồ để phát hiện bất thường.</li>"
            + "</ul>"
            + "<p style='background:#e3f2fd; padding:10px; border-left:4px solid #1976d2;'>"
            + "💡 <b>Mẹo:</b> Xem theo <b>tuần/tháng</b> để đánh giá hiệu suất ca làm và lập kế hoạch nhập hàng.</p>"
            + "<p style='background:#fff3e0; padding:10px; border-left:4px solid #f57c00;'>"
            + "⚠️ <b>Lưu ý:</b> Số liệu có thể thay đổi khi phát sinh <b>trả hàng/hủy hóa đơn</b>. Hãy đảm bảo bộ lọc thời gian chính xác.</p>"
            + "</body></html>";

        String htmlThongBao = ""
            + "<html><body style='font-family: Segoe UI; font-size:13px; line-height:1.6; color:#222;'>"
            + "<h2>Thông báo lô hết hạn / sắp hết hạn</h2>"
            + "<h3>🎯 Mục đích</h3>"
            + "<ul style='line-height:1.8;'>"
            + "<li>Phát hiện sớm lô đã hết hạn hoặc sắp hết hạn</li>"
            + "<li>Tránh bán nhầm thuốc hết hạn, bảo đảm an toàn cho khách hàng</li>"
            + "<li>Tuân thủ quy định pháp luật về quản lý thuốc</li>"
            + "<li>Giúp ưu tiên xử lý, tiết kiệm thời gian kiểm kê</li>"
            + "</ul>"
            + "<h3>📍 Vị trí hiển thị</h3>"
            + "<p>Khu vực <b>Cảnh báo / Thông báo</b> trên dashboard chính, hoặc Menu → <b>Quản lý lô</b> → Tab <b>Theo dõi & Cảnh báo</b></p>"
            + "<h3>⚡ Ý nghĩa các loại thông báo</h3>"
            + "<table border='1' cellpadding='8' style='border-collapse:collapse; margin:10px 0;'>"
            + "<tr style='background:#f5f5f5;'>"
            + "<td><b>Biểu tượng / Loại</b></td>"
            + "<td><b>Ý nghĩa</b></td>"
            + "<td><b>Hành động cần làm</b></td>"
            + "</tr>"
            + "<tr>"
            + "<td><span style='color:#d32f2f; font-weight:bold;'>🔴 Hết hạn</span></td>"
            + "<td>Lô đã quá ngày hết hạn</td>"
            + "<td>Ngưng bán ngay, cách ly, hủy theo quy định</td>"
            + "</tr>"
            + "<tr>"
            + "<td><span style='color:#f57c00; font-weight:bold;'>🟠 Sắp hết hạn</span></td>"
            + "<td>Lô còn hạn nhưng gần hết (&lt;= 30 ngày)</td>"
            + "<td>Ưu tiên bán, áp dụng FEFO, cân nhắc khuyến mãi</td>"
            + "</tr>"
            + "</table>"
            + "<h3>📋 Cách xem chi tiết</h3>"
            + "<ol style='line-height:1.8;'>"
            + "<li>Mở Menu → <b>Quản lý lô</b> → Tab <b>Theo dõi & Cảnh báo</b></li>"
            + "<li>Danh sách hiển thị: Mã SP, Tên SP, Mã lô, Số lượng, Ngày hết hạn, Trạng thái</li>"
            + "<li>Bấm vào một dòng để xem chi tiết lô đó</li>"
            + "</ol>"
            + "<h3>💼 Quy trình xử lý</h3>"
            + "<p><b>Lô đã hết hạn:</b></p>"
            + "<ul style='line-height:1.8;'>"
            + "<li>✗ Ngưng bán ngay lập tức</li>"
            + "<li>✗ Cách ly khỏi khu vực bán hàng</li>"
            + "<li>✓ Cập nhật trạng thái thành \"Đã hủy\" trong hệ thống</li>"
            + "<li>✓ Liên hệ quản trị để xử lý tiêu hủy theo quy định</li>"
            + "</ul>"
            + "<p><b>Lô sắp hết hạn:</b></p>"
            + "<ul style='line-height:1.8;'>"
            + "<li>✓ Áp dụng quy tắc FEFO (First Expiry First Out – Hết hạn trước bán trước)</li>"
            + "<li>✓ Đẩy bán ưu tiên (có thể hạ giá hoặc khuyến mãi hợp lệ)</li>"
            + "<li>✓ Kiểm kê định kỳ (VD: hàng tuần)</li>"
            + "<li>✓ Nếu không bán được trước hạn, chuẩn bị hủy</li>"
            + "</ul>"
            + "<p style='background:#fff3e0; padding:10px; border-left:4px solid #f57c00; margin-top:15px;'>"
            + "📌 <b>Lưu ý quan trọng:</b> Kiểm tra thông báo hàng ngày vào đầu ca làm việc để tránh sơ sót. Tuân thủ quy định không bán hàng hết hạn là bổn phận của mỗi nhân viên."
            + "</p>"
            + "</body></html>";

        // Cập nhật tiêu đề theo cấu trúc yêu cầu
        contentMap.put("Giới thiệu", htmlGioiThieu);
        contentMap.put("Bắt đầu nhanh", htmlDaoTaoDauTien);
        contentMap.put("1.1. Cách thêm một sản phẩm mới", htmlThemSanPham);
        contentMap.put("1.2. Quản lý Lô hàng", htmlQuanLyLo);
        contentMap.put("Hướng dẫn: Bán hàng & Thống kê doanh thu", htmlHuongDanBanHangThongKe);
        contentMap.put("2.1. Bán hàng đa nhiệm", htmlBanHang);
        contentMap.put("2.2. Quy trình Nhận Trả Hàng", htmlTraHang);
        contentMap.put("3. Tra cứu thông tin", htmlTraCuu);
        
        // Điều chỉnh nội dung phần báo cáo theo mục 4
        htmlBaoCao = htmlBaoCao.replace("<h2>3.2. Xuất báo cáo doanh thu</h2>", "<h2>4. BÁO CÁO & THỐNG KÊ DOANH THU</h2>");
        contentMap.put("4. Báo cáo & Thống kê doanh thu", htmlBaoCao);
        
        // Thêm kết luận
//        String htmlKetLuan = "" 
//            + "<html><body style='font-family: Segoe UI; font-size:13px; line-height:1.6; color:#222;'>" 
//            + "<h2>Kết luận</h2>" 
//            + "<ul>" 
//            + "<li>Tài liệu dùng cho vận hành & đào tạo</li>" 
//            + "<li>Viết rõ ràng, thực tế, dễ áp dụng</li>" 
//            + "</ul>" 
//            + "</body></html>";
//        contentMap.put("Kết luận", htmlKetLuan);

        // Giữ nguyên thêm chủ đề phụ nếu cần
        contentMap.put("Thông báo lô hết hạn", htmlThongBao);

        allTopics = new ArrayList<>(contentMap.keySet());
        // Không sắp xếp để giữ nguyên thứ tự phần mục
    }

    private void buildUI() {
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel lbl = new JLabel("Hướng dẫn sử dụng");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(lbl, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Left: Search + Topics list
        JPanel left = new JPanel(new BorderLayout());
        left.setPreferredSize(new Dimension(260, 0));
        left.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(230,230,230)));

        txtSearch = new JTextField();
        txtSearch.setToolTipText("Tìm kiếm chủ đề...");
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSearch.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterTopics();
            }
        });
        left.add(txtSearch, BorderLayout.NORTH);

        topicModel = new DefaultListModel<>();
        for (String t : allTopics) topicModel.addElement(t);
        lstTopics = new JList<>(topicModel);
        lstTopics.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstTopics.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lstTopics.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String sel = lstTopics.getSelectedValue();
                if (sel != null) loadContent(sel);
            }
        });
        JScrollPane spList = new JScrollPane(lstTopics);
        spList.setBorder(BorderFactory.createEmptyBorder());
        left.add(spList, BorderLayout.CENTER);

        // Right: Content area
        edContent = new JEditorPane();
        edContent.setEditable(false);
        edContent.setContentType("text/html;charset=UTF-8");
        JScrollPane spContent = new JScrollPane(edContent);
        spContent.setBorder(BorderFactory.createEmptyBorder());

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, spContent);
        split.setDividerLocation(260);
        split.setResizeWeight(0);
        add(split, BorderLayout.CENTER);
    }

    private void filterTopics() {
        String q = txtSearch.getText().trim().toLowerCase();
        topicModel.clear();
        for (String t : allTopics) {
            if (q.isEmpty() || t.toLowerCase().contains(q)) {
                topicModel.addElement(t);
            }
        }
        if (!topicModel.isEmpty()) {
            lstTopics.setSelectedIndex(0);
        }
    }

    private void loadContent(String topic) {
        String html = contentMap.getOrDefault(topic, "<html><body style='font-family: Segoe UI; font-size:13px;'>Nội dung đang cập nhật...</body></html>");
        edContent.setText(html);
        edContent.setCaretPosition(0);
        lstTopics.setSelectedValue(topic, true);
    }
}
