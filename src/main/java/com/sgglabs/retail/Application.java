package com.sgglabs.retail;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
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

            Elements divProductResultTags = doc.select("div.ZGFjDb");
            //for (Element prodResultTag : divProductResultTags) {
            Element prodResultTag = divProductResultTags.first();
                Elements childTags = prodResultTag.children();
                Elements productNameTags = childTags.select("div.eIuuYe");
                for (Element divTag : productNameTags) {
                    String linkText = divTag.text();
                    LOG.debug("Product Text:- " + linkText);
                }

                //For Price
                Elements productPriceTags = childTags.select("div.mQ35Be");
                for (Element divTag : productPriceTags) {
                    String linkText = divTag.text();
                    LOG.debug("Price Text:- " + linkText);
                }
            //}
            /*
            //For Product name
            Elements tagsForProdResult = doc.getElementsByAttributeValue("class", "eIuuYe");
            for (Element divTag : tagsForProdResult) {
                String linkHref = divTag.attr("href");
                String linkText = divTag.text();
                LOG.debug("Product Text:- " + linkText);
            }

            //For Product price
            Elements tagsForPrice = doc.getElementsByAttributeValue("class", "O8U6h");
            for (Element spanTag : tagsForPrice) {
                String linkText = spanTag.text();
                LOG.debug("Price Text:- " + linkText);
            }

            //For Product Description
            Elements tagsForProdDescription = doc.getElementsByAttributeValue("class", "na4ICd");
            for (Element divTag : tagsForProdDescription) {
                String linkText = divTag.text();
                LOG.debug("Description Text:- " + linkText);
            }

            //For other options
            Elements tagsForOtherOptions = doc.getElementsByAttributeValue("class", "na4ICd UTJALc");
            for (Element divTag : tagsForOtherOptions) {
                String linkText = divTag.text();
                LOG.debug("Other Options Text:- " + linkText);
            }
            */
        } catch (Exception ex) {
            LOG.error("unable to fetch data", ex);
        }
    }
}
