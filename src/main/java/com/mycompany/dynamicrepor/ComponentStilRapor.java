/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dynamicrepor;

import com.mycompany.dynamicrepor.Conn.DB;
import com.mycompany.dynamicrepor.Enum.ActorEnum;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.ImageBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;

/**
 *
 * @author lenovo
 */
public class ComponentStilRapor {
    
    
    public ArrayList<HashMap<String, String>> sqlcek() {
        ArrayList<HashMap<String, String>> al = new ArrayList<>();

        try {
            String q = "SELECT CONCAT(cu.first_name, _utf8' ', cu.last_name) AS name, a.address AS address,\n" +
"	a.phone AS phone, city.city AS city, country.country AS country\n" +
"FROM customer AS cu JOIN address AS a ON cu.address_id = a.address_id JOIN city ON a.city_id = city.city_id\n" +
"	JOIN country ON city.country_id = country.country_id ";
            PreparedStatement pr = new DB().preBaglan(q);

            ResultSet rss = pr.executeQuery();
            while (rss.next()) {
                HashMap<String, String> hm = new HashMap<>();
               
                hm.put("ad", rss.getString("name"));
                hm.put("soyad", rss.getString("address"));
                hm.put("ad", rss.getString("city"));
                hm.put("soyad", rss.getString("country"));
                hm.put("ad", rss.getString("phone"));
                al.add(hm);
            }
        } catch (Exception e) {
            System.err.println("Hata : neolaki" + e);
        }
        return al;
    }
    
    	private ComponentBuilder<?, ?> createComponent(ArrayList<HashMap<String, String>> arrayList) {
		
            
                HorizontalListBuilder cardComponent = cmp.horizontalList();
		StyleBuilder cardStyle = stl.style(stl.pen1Point())
		                            .setPadding(10);
		cardComponent.setStyle(cardStyle);

		//ImageBuilder image = cmp.image(Templates.class.getResource("images/user_male.png")).setFixedDimension(60, 60);
		//cardComponent.add(cmp.hListCell(image).heightFixedOnMiddle());
		cardComponent.add(cmp.horizontalGap(10));
                
		StyleBuilder boldStyle = stl.style().bold();
                 for (HashMap<String, String> hashMap : arrayList) {
                hashMap.get("ad");
                }
		VerticalListBuilder content = cmp.verticalList(
                        
                        
			cmp.text("Name:").setStyle(boldStyle),
			cmp.text("Peter Marsh"),
			cmp.text("Address:").setStyle(boldStyle),
			cmp.text("23 Baden Av."),
			cmp.text("City:").setStyle(boldStyle),
			cmp.text("New York"));

		cardComponent.add(content);
		return cardComponent;
	}

    
    
    
    
}
