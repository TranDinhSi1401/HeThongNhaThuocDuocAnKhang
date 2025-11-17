/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hethongnhathuocduocankhang.gui;

import java.awt.Color;
import java.awt.Paint;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;

/**
 *
 * @author GIGABYTE
 */
public class ToMauCot extends BarRenderer {

    private double kpi;
    private Paint trenKPI = new Color(102, 204, 102); // Xanh lá
    private Paint duoiKPI = new Color(229, 88, 88); // Đỏ

    public ToMauCot(double kpi) {
        this.kpi = kpi;
    }

    @Override
    public Paint getItemPaint(int row, int column){
        // Lấy dataset từ plot
        CategoryDataset dataset = getPlot().getDataset();
        
        // Lấy giá trị của cột (item) tại vị trí (row, column)
        double giaTri = dataset.getValue(row, column).doubleValue();
        
        // So sánh với KPI và trả về màu tương ứng
        if(giaTri >= kpi){
            return trenKPI;
        } else{
            return duoiKPI;
        }
        
    }
            
}
