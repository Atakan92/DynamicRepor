/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dynamicrepor;

import com.mycompany.dynamicrepor.Conn.DB;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

/**
 *
 * @author lenovo
 */
public class UlkeListe {
    public UlkeListe(){
        build();
    }
   
    String q="SELECT COUNT(city_id) as co,country\n" +
"FROM city join country on country.country_id=city.country_id\n" +
"GROUP BY country HAVING co >20";
    ResultSet rss=null;
    	private void build() {
             PreparedStatement pr = new DB().preBaglan(q);
             
        try {
            rss = pr.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(UlkeListe.class.getName()).log(Level.SEVERE, null, ex);
        }
		FontBuilder  boldFont = stl.fontArialBold().setFontSize(12);
		TextColumnBuilder<Integer> quantityColumn = col.column("SehirSayısı", "co", type.integerType());

		TextColumnBuilder<String> itemColumn = col.column("Ülke", "country", type.stringType());


		try {
			report()
				
				.columns(itemColumn, quantityColumn)
				
				.summary(
					cht.barChart()
						.setTitle("Bar chart")
						.setTitleFont(boldFont)
						
						.setCategory(itemColumn)
						.series(
							cht.serie(quantityColumn).setSeries(itemColumn))
						.setCategoryAxisFormat(
							cht.axisFormat().setLabel("Ülkeler")))
				.pageFooter(Templates.footerComponent)
				.setDataSource(rss)
				.show();
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

}
