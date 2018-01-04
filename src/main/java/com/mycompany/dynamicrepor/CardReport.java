/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dynamicrepor;
/**
 * DynamicReports - Free Java reporting library for creating reports dynamically
 *
 * Copyright (C) 2010 - 2016 Ricardo Mariaca
 * http://www.dynamicreports.org
 *
 * This file is part of DynamicReports.
 *
 * DynamicReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DynamicReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with DynamicReports. If not, see <http://www.gnu.org/licenses/>.
 */


import com.mycompany.dynamicrepor.Conn.DB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import com.mycompany.dynamicrepor.Templates;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.ImageBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;

/**
 * @author Ricardo Mariaca (r.mariaca@dynamicreports.org)
 */
public class CardReport {

	public CardReport() {
		build();
	}
    public ArrayList<HashMap<String, String>> sqlcek() {
        ArrayList<HashMap<String, String>> al = new ArrayList<>();

        try {

String q="select first_name,last_name from actor WHERE first_name like 'D%'";
            PreparedStatement pr = new DB().preBaglan(q);

            ResultSet rss = pr.executeQuery();
            while (rss.next()) {
                HashMap<String, String> hm = new HashMap<>();
               
                hm.put("ad", rss.getString("first_name"));
                hm.put("soyad", rss.getString("last_name"));

                al.add(hm);
            }
        } catch (Exception e) {
            System.err.println("Hata : neolaki" + e);
        }
        return al;
    }
	private void build() {
		ComponentBuilder<?, ?> cardComponent = createCardComponent(sqlcek());
		HorizontalListBuilder cards = cmp.horizontalFlowList();
		
			cards.add(cardComponent);
		

		try {
			report()
			  .setTemplate(Templates.reportTemplate)
			  .setTextStyle(stl.style())
			  .setPageFormat(PageType.A4)
			  .title(
			  	Templates.createTitleComponent("Sanatçılar"),
			  	cards)
			  .show();
		} catch (Exception e) {
			                 System.out.println("hataaa"+e);
		}
	}

	private ComponentBuilder<?, ?> createCardComponent(ArrayList<HashMap<String, String>> al) {
		HorizontalListBuilder cardComponent = cmp.horizontalList();
                
		StyleBuilder cardStyle = stl.style(stl.pen1Point())
		                            .setPadding(10);
		cardComponent.setStyle(cardStyle);

		ImageBuilder image = cmp.image(Templates.class.getResource("images/user_male.png")).setFixedDimension(60, 60);
		cardComponent.add(cmp.hListCell(image).heightFixedOnMiddle());
		cardComponent.add(cmp.horizontalGap(10));

		StyleBuilder boldStyle = stl.style().bold();
                for (HashMap<String, String> hashMap : al) {
                  VerticalListBuilder content = cmp.verticalList(
			cmp.text("Name:"+hashMap.get("ad")).setStyle(boldStyle),
			
			cmp.text("Soyad:"+hashMap.get("soyad")).setStyle(boldStyle)
			
			);  
                        cardComponent.add(content);
              }
		

		return cardComponent;
	}

}
