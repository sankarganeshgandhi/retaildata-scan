package com.sgglabs.retail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Sankarganesh Gandhi (sgandhi@sgglabs.com)
 */
@SpringBootApplication
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        try {
            Document doc = Jsoup.connect("https://www.google" +
                    ".com/search?q=men+shampoo&hl=en-GB&source=lnms&tbm=shop&sa=X").get();

            /*
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
             *             <span class="O8U6h"></span>
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
             */
            Elements parentDivTags = doc.getElementsByAttributeValue("class", "eIuuYe");
            for (Element parentDivTag : parentDivTags) {
                String linkHref = parentDivTags.attr("href");
                String linkText = parentDivTags.text();
                LOG.debug("linkText:- " + linkText);
            }
        } catch (Exception ex) {
            LOG.error("unable to fetch data", ex);
        }
    }
}
