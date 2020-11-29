package com.borjamoll.scrapping.services;

import com.borjamoll.scrapping.data.Product;
import com.borjamoll.scrapping.data.Search;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

/**
 * private final String ->atajos de CSS para .select
 * ProductList to return product object
 * objectMapper -> product object to json
 */
@Service
public class ProductService {

    private final String amazon = "https://www.amazon.es/s?k=";
    private final String normalSearch = "&dc&__mk_es_ES=ÅMÅŽÕÑ&qid=1606578057&rnid=831276031&ref=sr_nr_p_85_1";
    private final String primeSearch = "&rh=p_85%3A831314031&dc&__mk_es_ES=ÅMÅŽÕÑ&qid=1606578115&rnid=831276031&ref=sr_nr_p_85_1";
    private final String priceCSS = "span#price_inside_buybox";
    private final String priceNewCSS = "span#newBuyBoxPrice";
    private final String urlSectionCSS = "a.a-link-normal";
    private final String productTitle = "span#productTitle";
    private final String href = "abs:href";
    private final String starsCSS = "span#acrPopover";
    private final String sectionCSS = "h2.a-size-mini";

    private Document doc;
    private ArrayList<Product> products = new ArrayList<>();

    private Elements sections = null;
    private Document link = null;
    private String urlSection;
    private String stars;
    private Double price;


    public String run(Search key) throws JsonProcessingException {
        if(key.getKey().equals("") || key.getKey()==null){
            return "Bad request";
        }
        try {
            if(key.isPrime()){
                doc = Jsoup.connect(amazon + key.getKey().replace(" ", "+") + primeSearch).get();
            }else{
                doc = Jsoup.connect(amazon + key.getKey() + normalSearch).get();
            }

        } catch (Exception e) {
            return "Search not found";
        }
        sections = doc.select(sectionCSS);
        products = productList(sections);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(products);
    }

    public ArrayList<Product> productList(Elements list) {
        for (Element section : list) {
            urlSection = section.select(urlSectionCSS).attr(href);
            System.out.println(urlSection);
            try {
                link = Jsoup.connect(urlSection).get();
            } catch (Exception e) {
                System.out.println("Lost web");
            }

            try {
                stars = link.select(starsCSS).attr("title");
                if(stars.length()<1){
                    stars="No stars";
                }
                price = Double.parseDouble(link.select(priceCSS).text().split(" ")[0].replace(".","").replace(",","."));
                if (link.select(priceCSS).text().length() < link.select(priceNewCSS).text().length()) {
                    price = Double.parseDouble(link.select(priceNewCSS).text().split(" ")[0].replace(".","").replace(",","."));
                }
                if (price > 1) {
                    products.add(new Product(link.select(productTitle).text(), price, stars));
                }
            } catch (Exception e) {
                System.out.println("Lost product");
            }
        }
        return products;
    }
}
