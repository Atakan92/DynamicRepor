/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dynamicrepor;

import com.mycompany.dynamicrepor.Conn.DB;
import com.mycompany.dynamicrepor.Enum.ActorEnum;
import java.awt.Component;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import javax.swing.table.DefaultTableModel;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

/**
 *
 * @author lenovo
 */
public class NormalSutunRaporlamaJava {
   public NormalSutunRaporlamaJava(){
       build();
   }

    public ArrayList<HashMap<String, String>> sqlcek() {
        ArrayList<HashMap<String, String>> al = new ArrayList<>();

        try {
            String q = "Select * from actor";
            PreparedStatement pr = new DB().preBaglan(q);

            ResultSet rss = pr.executeQuery();
            while (rss.next()) {
                HashMap<String, String> hm = new HashMap<>();
                hm.put("id", rss.getString("" + ActorEnum.actor_id));
                hm.put("ad", rss.getString("" + ActorEnum.first_name));
                hm.put("soyad", rss.getString("" + ActorEnum.last_name));
                al.add(hm);
            }
        } catch (Exception e) {
            System.err.println("Hata : neolaki" + e);
        }
        return al;
    }

    private void build() {
        try {
            report()
                    .setTemplate(Templates.reportTemplate)
                    .columns(
                            
                            col.column("SIRANO", "id", type.stringType()),
                            col.column("AD", "ad", type.stringType()),
                            col.column("SOYAD", "soyad", type.stringType())
                            
                    )
                    .title(
                           Components.text("Mevcut Actor Listesi")
                    )
                    .setDataSource(createDataSource(sqlcek()))
                    .show();
        } catch (Exception e) {
            System.out.println(e + "hata nerde");
        }

    }

    
    private JRDataSource createDataSource(ArrayList<HashMap<String, String>> arrayList) {
                
		DRDataSource dataSource = new DRDataSource("id", "ad", "soyad");
                for (HashMap<String, String> hashMap : arrayList) {
                    
                     dataSource.add(hashMap.get("id"),hashMap.get("ad"),hashMap.get("soyad"));
                    
            
                }
                
		return dataSource;
	}
  
    public void veriYazdir(ArrayList<HashMap<String, String>> arrayList) {
        int i = 1;
        Set<String> keys = arrayList.get(0).keySet();

        for (HashMap<String, String> hashMap : arrayList) {
            for (String key : keys) {
                System.out.print(hashMap.get(key) + " ");

            }
            System.out.println(" ");

        }

        i++;
    }

}
