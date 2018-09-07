package com.sgglabs.retail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sgglabs.retail.model.dto.ProductSearchResultDTO;
import com.sgglabs.retail.model.dto.SellerProductDataDTO;
import com.sgglabs.retail.model.entity.SearchText;
import com.sgglabs.retail.model.entity.Site;
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
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class RetailDataScanner {
    private static final Logger LOG = LoggerFactory.getLogger(RetailDataScanner.class);

    private class SearchInfo {
        private String hostName;
        private String siteName;
        private String searchText;
        private String searchURI;
        private String fullURL;

        String getSiteName() {
            return siteName;
        }

        void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        String getHostName() {
            return hostName;
        }

        void setHostName(String hostName) {
            this.hostName = hostName;
        }

        String getSearchText() {
            return searchText;
        }

        void setSearchText(String searchText) {
            this.searchText = searchText;
        }

        String getSearchURI() {
            return searchURI;
        }

        void setSearchURI(String searchURI) {
            this.searchURI = searchURI;
        }

        String getFullURL() {
            return fullURL;
        }

        void setFullURL(String fullURL) {
            this.fullURL = fullURL;
        }

        @Override
        public String toString() {
            return "SearchInfo{" +
                    "hostName='" + hostName + '\'' +
                    ", searchText='" + searchText + '\'' +
                    ", searchURI='" + searchURI + '\'' +
                    ", fullURL='" + fullURL + '\'' +
                    '}';
        }
    }

    private final static String HTML_ANCHOR_TAG = "a";
    private final static String HTML_HREF_ATTR = "href";
    private final static String HTML_TAG1 = "div.sh-sr__shop-result-group";
    private final static String HTML_TAG2 = "div.sh-pr__product-results";
    private final static String HTML_TAG3 = "div.sh-dlr__list-result";
    private final static String HTML_TAG4 = "div.sh-dlr__content";
    private final static String HTML_TAG5 = "div.ZGFjDb";
    private final static String HTML_TAG6 = "div.eIuuYe";
    private final static String HTML_TAG7 = "div.na4ICd";
    private final static String HTML_TAG8 ="span.o0Xcvc";
    private final static String HTML_TAG9 = "div.vq3ore";
    private final static String HTML_ATTR1 = "aria-label";
    private final static String HTML_TAG10 = "main-content-with-search";
    private final static String HTML_TAG11 = "pp-main";
    private final static String HTML_TAG12 = "online";
    private final static String HTML_TAG13 = "os-content";
    private final static String HTML_TAG14 = "os-sellers-content";
    private final static String HTML_TAG15 = "table.os-main-table";
    private final static String HTML_TAG16 = "tbody";
    private final static String HTML_TAG17 = "tr";

    @Autowired
    private SearchTextRepository searchTextRepo;

    @Autowired
    private SiteRepository siteRepo;

    @Autowired
    private MessagePostService messageService;

    public RetailDataScanner() {
    }

    void startScanning() {
        List<SearchInfo> searchList = getSearchList();
        fetchRetailData(searchList);
    }

    private void fetchRetailData(List<SearchInfo> searchList) {
        try {
            SearchInfo searchInfo = searchList.get(0);
            Document resultPageDoc = Jsoup.connect(searchInfo.getFullURL()).get();

            /* div.query:<searchtext>
             * div.sh-sr__shop-result-group
             *      div.sh-pr__product-results
             *          div.sh-dlr__list-result
             *              div.sh-dlr__content
             *                  div.ZGFjDb
             */
            // ("div.sh-sr__shop-result-group").("div.sh-pr__product-results")
            Elements prodSearchResultsTag = resultPageDoc.select(HTML_TAG1).select(HTML_TAG2);

            /**
             * Loop through each of the "div.sh-dlr__list-result" tag to scan through the search results
             */
            // ("div.sh-dlr__list-result")
            Elements prodSearchResultTags = prodSearchResultsTag.select(HTML_TAG3);
            for (int i = 1; i < prodSearchResultTags.size(); i++) {
                ProductSearchResultDTO productSearchResult = new ProductSearchResultDTO();
                productSearchResult.setSiteName(searchInfo.getSiteName());
                productSearchResult.setSearchText(searchInfo.getSearchText());

                Element productSearchResultRow = prodSearchResultTags.get(i);

                // ("div.sh-dlr__content")
                Elements prodSearchResultContentTag = productSearchResultRow.select(HTML_TAG4);

                // ("div.ZGFjDb")
                Elements divProductResultTags = prodSearchResultContentTag.select(HTML_TAG5);

                // For Product short description
                //"div.eIuuYe"
                Elements productNameTags = divProductResultTags.select(HTML_TAG6);

                //"a"; "href"
                String aHrefValue = productNameTags.select(HTML_ANCHOR_TAG).attr(HTML_HREF_ATTR);

                Element productNameTag = productNameTags.first();
                productSearchResult.setShortDescription(productNameTag.text());

                //For Product Price
                //"div.na4ICd"
                Elements na4IcdDivTags = prodSearchResultsTag.select(HTML_TAG7);
                Element productPriceTag = na4IcdDivTags.first();
                productSearchResult.setPrice(productPriceTag.text());

                if (!productSearchResult.getPrice().contains("shops")) continue;

                //For Product Reviews and Ratings
                Elements divTags = na4IcdDivTags.next();
                Element productReviewTag = divTags.first();
                productSearchResult.setNumberOfReviews(productReviewTag.text());

                //"span.o0Xcvc";
                Elements spanRatingTags = productReviewTag.select(HTML_TAG8);

                //"div.vq3ore"
                Elements divRatingTags = spanRatingTags.select(HTML_TAG9);
                Element divRatingTag = divRatingTags.first();
                Attributes attributes = divRatingTag.attributes();
                //"aria-label";
                productSearchResult.setTotalRatings(attributes.get(HTML_ATTR1));

                //For Product long description
                divTags = na4IcdDivTags.next().next();
                Element prodLongDescTag = divTags.first();
                productSearchResult.setLongDescription(prodLongDescTag.text());

                // For Product Categories
                divTags = na4IcdDivTags.next().next().next();
                Element prodCategoriesTag = divTags.first();
                productSearchResult.setCategories(prodCategoriesTag.text());

                // For Product Other Options
                divTags = na4IcdDivTags.next().next().next().next();
                Element prodOtherOptionsTag = divTags.first();
                productSearchResult.setOtherOptions(prodOtherOptionsTag.text());

                //productResult.getSellerList().addAll(sellerDataList);
                productSearchResult.setStatus(StatusEnum.Active.getString());

                // After clicking on the result URI
                List<SellerProductDataDTO> sellerDataList = getProductSellerData(
                        searchInfo.getHostName() + aHrefValue);

                productSearchResult.setSellerDataList(sellerDataList);
                messageService.sendSearchResultMessage(productSearchResult);
            }
        } catch (JsonProcessingException me) {
            LOG.error("unable to post the data as message to store", me);
        } catch (IOException ioe) {
            LOG.error("unable to fetch data", ioe);
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
    private List<SearchInfo> getSearchList() {
        List<SearchInfo> searchList = new ArrayList<>();
        for (Site site : siteRepo.findAll()) {
            MessageFormat urlFormat = new MessageFormat(site.getSearchURI());
            for (SearchText searchText : searchTextRepo.findAll()) {
                Object[] searchTextObjectArray = new Object[]{searchText.getSearchText()};
                SearchInfo searchInfo = new SearchInfo();
                searchInfo.setSiteName(site.getSiteName());
                searchInfo.setHostName(site.getSiteHostName());
                searchInfo.setSearchText(searchText.getSearchText());
                String formattedURI = urlFormat.format(searchTextObjectArray);
                searchInfo.setSearchURI(formattedURI);
                searchInfo.setFullURL(site.getSiteHostName() + formattedURI);
                searchList.add(searchInfo);
            }
        }
        return searchList;
    }

    private void writeToFile(final Document doc, final String fileName) throws IOException {
        final File htmlFile = new File(fileName);
        FileWriter writer = new FileWriter(htmlFile);
        writer.write(doc.outerHtml());
    }

    private Document getDocumentFromFile(final String fileName) throws IOException {
        File input = new File(fileName);
        Document doc = Jsoup.parse(input, "UTF-8");
        return doc;
    }

    private Document getDocumentFromURL(final String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        return doc;
    }

    private List<SellerProductDataDTO> getProductSellerData(final String productDetailsPageURL) throws IOException {
        // After clicking on the result URI
        Document prodDetailPageDoc = Jsoup.connect(productDetailsPageURL).get();

        //"main-content-with-search";"pp-main";"online";"os-content";"os-sellers-content"; "table.os-main-table";
        // "tbody";"tr"
        Elements productRetailStoreTableRowTags = prodDetailPageDoc.getElementById(HTML_TAG10)
                .getElementById(HTML_TAG11)
                .getElementById(HTML_TAG12).getElementById(HTML_TAG13)
                .getElementById(HTML_TAG14)
                .select(HTML_TAG15)
                .select(HTML_TAG16)
                .select(HTML_TAG17);

        List<SellerProductDataDTO> sellerProductDataList = new ArrayList<>();
        for (int i = 1; i < productRetailStoreTableRowTags.size(); i++) { //first row is the col names so skip it.
            Element row = productRetailStoreTableRowTags.get(i);
            Elements cols = row.children();
            if (cols.size() >= 6) {
                SellerProductDataDTO sellerData = new SellerProductDataDTO();
                sellerData.setSellerName(cols.get(0).text());
                sellerData.setRatingIndex(cols.get(1).text());
                sellerData.setDetails(cols.get(2).text());
                sellerData.setBasePrice(cols.get(3).text());
                sellerData.setTotalPrice(cols.get(4).text());
                sellerData.setStatus(StatusEnum.Active.getString());
                sellerProductDataList.add(sellerData);
            } else {
                LOG.warn("skipped row: " + row.text());
            }
        }
        return sellerProductDataList;
    }
}