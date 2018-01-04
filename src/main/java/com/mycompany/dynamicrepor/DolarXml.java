/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dynamicrepor;

import java.math.BigDecimal;
import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.field;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.FieldBuilder;
import net.sf.dynamicreports.report.builder.chart.BarChartBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

/**
 *
 * @author lenovo
 */
public class DolarXml {
    
    public static void main(String[] args) {
        try {
            // servis url'i oluştur
            String url = "http://www.tcmb.gov.tr/kurlar/today.xml";
            String data = Jsoup.connect(url).timeout(30000).execute().body();
            Document doc = Jsoup.parse(data, "", Parser.xmlParser());
            System.out.println(doc);
            
            
            


        } catch (Exception e) {
        }
    }
    
    	private void build() {
		try {
			JRXmlDataSource dataSource = new JRXmlDataSource(DolarXml.class.getResourceAsStream("today.xml"), "/Tarih_Date/Currency");
			JRXmlDataSource chartDataSource = dataSource.dataSource("/sales/chart/item");

			FieldBuilder<Integer> idField = field("id", type.integerType())
				.setDescription("@id");
			FieldBuilder<String> itemField = field("item", type.stringType());
			FieldBuilder<Integer> quantityField = field("quantity", type.integerType());
			FieldBuilder<BigDecimal> unitPriceField = field("unitprice", type.bigDecimalType());

			BarChartBuilder barChart = cht.barChart()
				.setDataSource(chartDataSource)
				.setCategory(itemField)
				.series(
					cht.serie(quantityField).setLabel("Quantity"));

			report()
			  .setTemplate(Templates.reportTemplate)
			  .columns(
			  	col.column("Id", idField),
			  	col.column("Item", itemField),
			  	col.column("Quantity", quantityField),
			  	col.column("Unit price", unitPriceField))
			  .title(Templates.createTitleComponent("XmlDatasource"))
			  .summary(barChart)
			  .pageFooter(Templates.footerComponent)
			  .setDataSource(dataSource)
			  .show();
		} catch (DRException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
    
}
