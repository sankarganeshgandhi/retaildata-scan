package com.sgglabs.retail;

import com.sgglabs.retail.model.SearchText;
import com.sgglabs.retail.model.Site;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class RetailDataScanner {
    private static final Logger LOG = LoggerFactory.getLogger(RetailDataScanner.class);

    @Autowired
    private SearchTextRepository searchTextRepo;

    @Autowired
    private SiteRepository siteRepo;

    public RetailDataScanner() {
    }

    public void startScanning() {
        List<String> urlList = getURLList();
        LOG.debug(urlList.toString());

        //fetchRetailData(urlList);
    }

    private void fetchRetailData(List<String> urlList) {
        try {
            Document tempDoc = Jsoup.connect("https://www.google.co.uk" +
                    "/search?q=men+shampoo&hl=en-GB&source=lnms&tbm=shop&sa=X").get();
            final File htmlFile = new File("/home/sankarg/temp/product-results-page.html");
            FileWriter writer  = new FileWriter(htmlFile);
            writer.write(tempDoc.outerHtml());

            Document tempSubDoc = Jsoup.connect("https://www.google.co.uk/shopping/" +
                    "product/16913124049773335120?q=men+shampoo&biw=1009&bih=647&" +
                    "prds=paur:ClkAsKraX2834tta3SMenZcR0sLYpHR1v9QTrvDvVAAUTv-P8gU8NTja5YVOwKchMEmkBfubJC5U4gUG-" +
                    "tep-utT20YGKxSR-PNXUkH2AqiNsL08qg1lEF-b2BIZAFPVH72NBcT4b0xeEB5kCrrPRaUSELx9rA&" +
                    "sa=X&ved=0ahUKEwjtq8Gr_-fcAhUlJsAKHStYDqEQ8wIIhgQ").get();

            final File prodPageHtmlFile = new File("/home/sankarg/temp/product-page.html");
            FileWriter prodPageFileWriter  = new FileWriter(prodPageHtmlFile);
            prodPageFileWriter.write(tempSubDoc.outerHtml());

            File input = new File("/home/sankarg/temp/product-results-page.html");
            Document doc = Jsoup.parse(input, "UTF-8");

            /*
             * div.sh-sr__shop-result-group
             *      div.sh-pr__product-results
             *          div.sh-dlr__list-result
             *              div.sh-dlr__content
             *                  div.ZGFjDb
             */
            // ("div.sh-sr__shop-result-group").("div.sh-pr__product-results")
            Elements prodSearchResultsTag = doc.select("div.sh-sr__shop-result-group")
                    .select("div.sh-pr__product-results");

            // ("div.sh-dlr__list-result")
            Elements prodSearchResultTag = prodSearchResultsTag.select("div.sh-dlr__list-result");

            // ("div.sh-dlr__content")
            Elements prodSearchResultContentTag = prodSearchResultTag.select("div.sh-dlr__content");

            // ("div.ZGFjDb")
            Elements divProductResultTags = prodSearchResultContentTag.select("div.ZGFjDb");

            // For Product short description
            Elements productNameTags = divProductResultTags.select("div.eIuuYe");
            String aHrefValue = productNameTags.select("a").attr("href");
            LOG.debug("Href: " + aHrefValue);
            Element productNameTag = productNameTags.first();
            LOG.debug("Prod Short Description Text:- " + productNameTag.text());

            //For Product Price
            //Elements na4IcdDivTags = childTags.select("div.na4ICd");
            Elements na4IcdDivTags = prodSearchResultsTag.select("div.na4ICd");
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
            Attributes attributes = divRatingTag.attributes();
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

            /*
             * div.id: main-content-with-search
             *      div.id: pp-main
             *          div.id: online
             *              div.id: os-content
             *                  div.id: os-sellers-content
             *                      table.os-main-table
             *                          tbody
             *                              tr.os-row
             *                                  td
             *                                  td
             *                                  td
             *                                  td
             *                                  td
             *                                  td
             *
             */
            // After clicking on the result URI
            Document subPageDoc = Jsoup.connect("https://www.google.co.uk" + aHrefValue).get();

            /*File prodInput = new File("/home/sankarg/temp/product-page.html");
            Document subPageDoc = Jsoup.parse(prodInput, "UTF-8");*/

            Elements productRetailStoreTableRowTags = subPageDoc.getElementById("main-content-with-search")
                    .getElementById("pp-main")
                    .getElementById("online").getElementById("os-content")
                    .getElementById("os-sellers-content")
                    .select("table.os-main-table")
                    .select("tbody")
                    .select("tr");

            for (int i = 1; i < productRetailStoreTableRowTags.size(); i++) { //first row is the col names so skip it.
                Element row = productRetailStoreTableRowTags.get(i);
                Elements cols = row.children();
                LOG.debug("Seller: " + cols.get(0).text() + " Seller Rating: " + cols.get(1).text()
                        + " Details: " + cols.get(2).text() + " Base Price: " + cols.get(3).text()
                        + " Total Price: " + cols.get(4).text());
            }
        } catch (Exception ex) {
            LOG.error("unable to fetch data", ex);
        }
    }

    /**
     * Returns the list of search URLs fetching the from the tables.
     * It concatenates the site URL and search string to form the fully qualified search URL
     * <p>
     * Site: https://www.google.co.uk/search?q={0}&hl=en-GB&source=lnms&tbm=shop&sa=X
     * Search Text: men+shampoo
     * URL: https://www.google.co.uk/search?q=men+shampoo&hl=en-GB&source=lnms&tbm=shop&sa=X
     */
    private ArrayList<String> getURLList() {
        ArrayList<String> urlList = new ArrayList<>();
        for (Site site : siteRepo.findAll()) {
            MessageFormat urlFormat = new MessageFormat(site.getSiteURL());
            LOG.debug("Site URL: " + site.getSiteURL());
            for (SearchText searchText : searchTextRepo.findAll()) {
                LOG.debug("Search Text: " + searchText.getSearchText());
                Object[] searchTextObjectArray = new Object[] {searchText.getSearchText()};
                urlList.add(urlFormat.format(searchTextObjectArray));
            }
        }
        return urlList;
    }
}
