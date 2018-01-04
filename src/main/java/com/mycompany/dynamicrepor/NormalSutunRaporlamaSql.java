/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dynamicrepor;

import com.mycompany.dynamicrepor.Conn.DB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.component.Components;

/**
 *
 * @author lenovo
 */
public class NormalSutunRaporlamaSql {
    public NormalSutunRaporlamaSql() throws SQLException{
        buildSql();
    }
       
     
        
        private void buildSql() throws SQLException {
       String q = "Select customer_id,first_name,last_name from customer";
            PreparedStatement pr = new DB().preBaglan(q);
            ResultSet rss = pr.executeQuery();
        try {
            report()
                    .setTemplate(Templates.reportTemplate)
                    .columns(
                            
                            col.column("SIRA", "customer_id", type.integerType()),
                            col.column("AD", "first_name", type.stringType()),
                            col.column("SOYAD", "last_name", type.stringType())
                            
                    )
                    .title(
                           Components.text("Mevcut Müşteri Listesi")
                    )
                    .setDataSource(rss)
                    .show();
        } catch (Exception e) {
            System.out.println(e + "hata nerde");
        }

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
