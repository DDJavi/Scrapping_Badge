package com.borjamoll.scrapping;

import com.borjamoll.scrapping.data.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class ScrappingApplicationTests {



	@Test
	void contextLoads() {
	}
	@Test
	void stringCut(){
		String hola="2.000'02 €";
		String[] num = hola.split(" ");
		//float sub = Float.parseFloat(hola.substring(0,5).replace("'","."));
		System.out.println(Double.parseDouble(num[0].replace(".", "").replace("'",".")));

	}
	@Test
	void principal(){
		Document doc = null;
		Double price;
		String title = null;
		String stars = null;
		String pricehtml = "span#price_inside_buybox";
		String pricehtmlnew = "span#newBuyBoxPrice";
		try{
			doc = Jsoup.connect("https://www.amazon.es/s?k=tostadora&__mk_es_ES=%C3%85M%C3%85%C5%BD%C3%95%C3%91&ref=nb_sb_noss_2").get();
		}catch(Exception e){
			System.out.println("Page not found");
		}

		/**
		 * div.a-section esta todo
		 * span.a-size-base-plus esta el titulo
		 */
		Elements producto = doc.select("h2.a-size-mini");


		ArrayList<Product> products = new ArrayList<>();

		Document link = null;
		for(Element product : producto){

			String url = product.select("a.a-link-normal").attr("abs:href");
			try {
				link = Jsoup.connect(url).get();
			}catch(Exception e){
				System.out.println("pagina perdida");
			}
			try {
				//prize = findPrize(link.select("span#price_inside_buybox"),link.select(""));

				price = Double.parseDouble(link.select(pricehtml).text().substring(0,5));
				if(link.select(pricehtml).text().length() < link.select(pricehtmlnew).text().length()){
					price = Double.parseDouble(link.select(pricehtmlnew).text().substring(0,5));
				}
				if(price>1) {
					products.add(new Product(link.select("span#productTitle").text(), price, "stars"));
				}
			}catch(Exception e){
				System.out.println("Un producto perdido");
			}

		}
		for(Product prod : products){
			System.out.println("Product: " + prod.getProduct());
			System.out.println("Prize: " + prod.getPrice() + "€");
		}

	}
}
