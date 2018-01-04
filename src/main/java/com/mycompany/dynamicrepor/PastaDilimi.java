/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dynamicrepor;

import com.mycompany.dynamicrepor.Conn.DB;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.style.FontBuilder;



public class PastaDilimi {
    public PastaDilimi() throws SQLException{
        buildPasta();
    }
    
    private void buildPasta() throws SQLException {
        String q="SELECT\n" +
"c.name AS category\n" +
", SUM(p.amount) AS total_sales\n" +
"FROM payment AS p\n" +
"INNER JOIN rental AS r ON p.rental_id = r.rental_id\n" +
"INNER JOIN inventory AS i ON r.inventory_id = i.inventory_id\n" +
"INNER JOIN film AS f ON i.film_id = f.film_id\n" +
"INNER JOIN film_category AS fc ON f.film_id = fc.film_id\n" +
"INNER JOIN category AS c ON fc.category_id = c.category_id\n" +
"GROUP BY c.name\n" +
"ORDER BY total_sales ASC";
        PreparedStatement pr = new DB().preBaglan(q);
            ResultSet rss = pr.executeQuery();
		FontBuilder boldFont = stl.fontArialBold().setFontSize(12);

		TextColumnBuilder<String> itemColumn = col.column("Katagori", "category", type.stringType());
		TextColumnBuilder<Integer> quantityColumn = col.column("ToplamSatış", "total_sales", type.integerType());

		try {
			report()
				.setTemplate(Templates.reportTemplate)
				.columns(itemColumn, quantityColumn)
				 .title(
                           Components.text("Filmlerin Dağılım Tablosu")
                    )
				.summary(
					cht.pie3DChart()
						
						.setTitleFont(boldFont)
						.setKey(itemColumn)
						.series(
							cht.serie(quantityColumn)))
				.pageFooter(Templates.footerComponent)
				.setDataSource(rss)
				.show();
		} catch (Exception e) {
                  System.out.println("Hata " +e);
		}
	}
    
    
    
}
