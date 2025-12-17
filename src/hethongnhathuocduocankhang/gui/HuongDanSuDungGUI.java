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
import java.util.HashMap;
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
        loadContent("Thông báo lô hết hạn/sắp hết hạn");
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setLayout(new BorderLayout());
    }

    private void buildContent() {
        contentMap = new HashMap<>();

        String htmlThongBao = ""
            + "<html><body style='font-family: Segoe UI, sans-serif; font-size:13px; line-height:1.5; color:#222;'>"
            + "<h2 style='margin-top:0;'>Hướng dẫn: Thông báo lô hết hạn / sắp hết hạn</h2>"
            + "<h3>1) Mục đích</h3>"
            + "<ul>"
            + "<li>Phát hiện sớm lô đã hết hạn hoặc sắp hết hạn.</li>"
            + "<li>Tránh bán nhầm thuốc hết hạn, bảo đảm an toàn và tuân thủ.</li>"
            + "<li>Giúp ưu tiên xử lý, tiết kiệm thời gian kiểm kê.</li>"
            + "</ul>"
            + "<h3>2) Vị trí hiển thị</h3>"
            + "<ul>"
            + "<li>Khu vực <b>Thông báo</b> trên màn hình chính (dashboard).</li>"
            + "<li>Danh sách thông báo hiển thị dạng các dòng có thể bấm.</li>"
            + "</ul>"
            + "<h3>3) Ý nghĩa thông báo</h3>"
            + "<ul>"
            + "<li><span style='color:#b71c1c; font-weight:bold;'>Khẩn cấp – ĐÃ HẾT HẠN:</span> Lô đã quá hạn, cần ngưng bán và xử lý.</li>"
            + "<li><span style='color:#b26a00; font-weight:bold;'>Cảnh báo – SẮP HẾT HẠN:</span> Lô còn hạn nhưng gần hết (ví dụ &lt;= 30 ngày).</li>"
            + "</ul>"
            + "<h3>4) Xem chi tiết</h3>"
            + "<ol>"
            + "<li>Mở màn hình chính, tìm khu vực Thông báo.</li>"
            + "<li>Mỗi dòng hiển thị: Tên SP, Mã SP, Số lượng, Ngày hết hạn, Loại cảnh báo.</li>"
            + "</ol>"
            + "<h3>5) Đi đến quản lý lô</h3>"
            + "<ul>"
            + "<li>Bấm vào một dòng thông báo để chuyển đến màn hình <b>Quản lý lô</b> với bộ lọc phù hợp.</li>"
            + "<li>Hoặc mở menu bên trái → <b>Quản lý lô</b> để xem tất cả.</li>"
            + "</ul>"
            + "<h3>6) Lưu ý xử lý</h3>"
            + "<ul>"
            + "<li><b>Lô đã hết hạn:</b> Ngưng bán, cách ly/hủy theo quy định, cập nhật trạng thái.</li>"
            + "<li><b>Lô sắp hết hạn:</b> Kiểm kê, áp dụng FEFO, cân nhắc điều chuyển/khuyến mãi hợp lệ.</li>"
            + "<li>Kiểm tra định kỳ ngay cả khi có thông báo tự động.</li>"
            + "</ul>"
            + "<p style='color:#555;'>Truy cập: Menu &rarr; Trợ giúp &rarr; Hướng dẫn sử dụng.</p>"
            + "</body></html>";

        contentMap.put("Thông báo lô hết hạn/sắp hết hạn", htmlThongBao);
        contentMap.put("Bắt đầu nhanh", "<html><body style='font-family: Segoe UI; font-size:13px; color:#222;'><h2>Bắt đầu nhanh</h2><p>Chọn mục ở thanh bên trái để xem hướng dẫn chi tiết.</p></body></html>");
        contentMap.put("Bán hàng (tổng quan)", "<html><body style='font-family: Segoe UI; font-size:13px; color:#222;'>Đang cập nhật...</body></html>");
        contentMap.put("Quản lý lô sản phẩm", "<html><body style='font-family: Segoe UI; font-size:13px; color:#222;'>Đang cập nhật...</body></html>");
        contentMap.put("Liên hệ hỗ trợ", "<html><body style='font-family: Segoe UI; font-size:13px; color:#222;'><h2>Hỗ trợ</h2><p>Vui lòng liên hệ quản trị hệ thống hoặc bộ phận IT của nhà thuốc để được hỗ trợ.</p></body></html>");

        allTopics = new ArrayList<>(contentMap.keySet());
        allTopics.sort(String::compareTo);
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
