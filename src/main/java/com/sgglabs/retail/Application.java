package com.sgglabs.retail;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileWriter;

/**
 * @Author: Sankarganesh Gandhi (sgandhi@sgglabs.com)
 */
@SpringBootApplication
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        try {
            /*Document doc = Jsoup.connect("https://www.google" +
                    ".com/search?q=men+shampoo&hl=en-GB&source=lnms&tbm=shop&sa=X").get();

            final File htmlFile = new File("/home/sankarg/temp/filename.html");
            FileWriter writer  = new FileWriter(htmlFile);
            writer.write(doc.outerHtml());*/

            /*
             * <div class="ZGFjDb">
             * <div>
             * <div class="eIuuYe"..../> contains the product name
             * This is the root of the google search list
             *
             * Each of the parentDivTags has separate div tag structure for
             *      1. Product Price
             *      2. Product Review ratings and number of reviews
             *      3. Product Description
             *      4. Product category
             *      5. Other size options
             * 1. Product Price:
             * <div class="na4ICd TxCHDf"...>
             *     <div class="mQ35Be"....>
             *         <div>
             *             <span class="O8U6h">{price}</span>
             *             some description
             *         </div>
             *     </div>
             * </div>
             * 2. Review ratings and number of reviews:
             * <div class="na4ICd TxCHDf"...>
             *     <div class="eWxN4b"....>
             *         <div>
             *             <a href="...>
             *                 <span class="o0Xcvc">
             *                     <div class="vq3ore" aria-label="has ratings"></div>
             *                 </span>
             *                 <span>number of ratings</span>
             *             </a>
             *         </div>
             *     </div>
             * </div>
             * 3. Product Description:
             * <div class="na4ICd">
             *     product description
             * </div>
             * 4. Product category
             * <div class="na4ICd">
             *     <span>category1</span>
             *     <span>category2</span>
             *     ....
             *     ...
             *     <span>categoryN</span>
             * </div>
             * 5. Other size options
             * <div class="na4ICd UTJALc">
             *     <a href="">
             *         (Size 1)
             *         <span class="dGWXMb">(Price 1)</span>
             *     </a>
             *     <a href="">
             *         (Size 2)
             *         <span class="dGWXMb">(Price 2)</span>
             *     </a>
             * </div>
             * </div>
             * </div>
             */

            File input = new File("/home/sankarg/temp/filename.html");
            Document doc = Jsoup.parse(input, "UTF-8");

            /*Elements rootDivTag = doc.select("div.sh-sr__shop-result-group");
            Elements divProductResultTags = rootDivTag.select("div.sh-pr__product-results")
                    .select("div.sh-dlr__list-result").select("div.sh-dlr__content")
                    .select("div.ZGFjDb");*/
            Elements divProductResultTags = doc.select("div.sh-pr__product-results")
                .select("div.sh-dlr__list-result").select("div.sh-dlr__content")
                .select("div.ZGFjDb");
            //for (Element prodResultTag : divProductResultTags) {
            Element prodResultTag = divProductResultTags.first();
                // For Product short description
                Elements childTags = prodResultTag.children();
                Elements productNameTags = childTags.select("div.eIuuYe");
                for (Element divTag : productNameTags) {
                    String linkText = divTag.text();
                    LOG.debug("Product Short Description Text:- " + linkText);
                }

                //For Product Price
                Elements na4IcdDivTags = childTags.select("div.na4ICd");
                Element productPriceTag = na4IcdDivTags.first();
                String linkText = productPriceTag.text();
                LOG.debug("Prod Price Text:- " + linkText);

                //For Product Reviews and Ratings
                Elements divTags = na4IcdDivTags.next();
                Element productReviewTag = divTags.first();
                linkText = productReviewTag.text();
                LOG.debug("Prod Review Text:- " + linkText);

                Elements spanRatingTags = productReviewTag.select("span.o0Xcvc");
                Elements divRatingTags = spanRatingTags.select("div.vq3ore");
                Element divRatingTag = divRatingTags.first();
                Attributes attributes= divRatingTag.attributes();
                LOG.debug("Product Rating Text:- " + attributes.get("aria-label"));

                //For Product long description
                divTags = na4IcdDivTags.next().next();
                Element prodLongDescTag = divTags.first();
                linkText = prodLongDescTag.text();
                LOG.debug("Long Description Text:- " + linkText);

                // For Product Categories
                divTags = na4IcdDivTags.next().next().next();
                Element prodCategoriesTag = divTags.first();
                linkText = prodCategoriesTag.text();
                LOG.debug("Product Categories Text:- " + linkText);

                // For Product Other Options
                divTags = na4IcdDivTags.next().next().next().next();
                Element prodOtherOptionsTag = divTags.first();
                linkText = prodOtherOptionsTag.text();
                LOG.debug("Product Categories Text:- " + linkText);

                // For Other Product Price and Retail Store
                /*Elements tableRowTags = childTags.select("div._-c2").select("table._-c4").select("tr");
                for (int i = 0; i < tableRowTags.size(); i++) { //first row is the col names so skip it.
                    Element row = tableRowTags.get(i);
                    Elements cols = row.select("td");
                    LOG.debug("Price: "+ cols.get(0).text() + " Retail Store: " + cols.get(0).text());
                }*/
            //}
        } catch (Exception ex) {
            LOG.error("unable to fetch data", ex);
        }
    }
}
/*Elements productPriceTags = childTags.select("div.mQ35Be");
                for (Element divTag : productPriceTags) {
                    String linkText = divTag.text();
                    LOG.debug("Price Text:- " + linkText);
                }*/

// For Product Reviews and Ratings
                /*Elements productReviewTags = childTags.select("div.eWxN4b");
                for (Element divReviewTextTag : productReviewTags) {
                    String linkText = divReviewTextTag.text();
                    LOG.debug("Reviews Text:- " + linkText);
                    Elements spanRatingTags = divReviewTextTag.select("span.o0Xcvc");
                    Elements divRatingTags = spanRatingTags.select("div.vq3ore");
                    Element divRatingTag = divRatingTags.first();
                    Attributes attributes= divRatingTag.attributes();
                    LOG.debug("Rating Text:-" + attributes.get("aria-label"));
                }*/