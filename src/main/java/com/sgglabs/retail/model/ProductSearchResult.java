package com.sgglabs.retail.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * CREATE TABLE ProductSearchResult(
 *   Id BIGINT(20) NOT NULL AUTO_INCREMENT,
 *   SiteName VARCHAR(30) NOT NULL,
 *   SearchText VARCHAR(100) NOT NULL,
 *   ShortDesc VARCHAR(150),
 *   LongDesc VARCHAR(300),
 *   Price VARCHAR(30),
 *   Unit VARCHAR(30),
 *   Reviews VARCHAR(250),
 *   Ratings VARCHAR(50),
 *   OtherOptions VARCHAR(100),
 *   ProdCategories VARCHAR(200),
 *   StatusId INT(2) NOT NULL DEFAULT 2,
 *   CreatedDate DATE,
 *   ModifiedDate DATE
 * );
 */

@Entity
@Table(name = "ProductSearchResult")
public class ProductSearchResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "SiteName")
    private String siteName;

    @Column(name = "SearchText")
    private String searchText;

    @Column(name = "ShortDesc")
    private String shortDescription;

    @Column(name = "LongDesc")
    private String longDescription;

    @Column(name = "Price")
    private String price;

    @Column(name = "Unit")
    private String unit;

    @Column(name = "Reviews")
    private String numberOfReviews;

    @Column(name = "Ratings")
    private String rating;

    @Column(name = "OtherOptions")
    private String otherOptions;

    @Column(name = "ProdCategories")
    private String categories;

    @Column(name = "StatusId")
    private int statusId;

    @Column(name = "CreatedDate")
    private LocalDate createdDate;

    @Column(name = "ModifiedDate")
    private LocalDate modifiedDate;

    /*
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "productSearchResult")
    private List<SellerProductData> sellerList;
    */

    public ProductSearchResult() {
        //sellerList = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(String numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOtherOptions() {
        return otherOptions;
    }

    public void setOtherOptions(String otherOptions) {
        this.otherOptions = otherOptions;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    /*
    public List<SellerProductData> getSellerList() {
        return sellerList;
    }

    public void setSellerList(List<SellerProductData> sellerList) {
        this.sellerList = sellerList;
    }
    */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSearchResult that = (ProductSearchResult) o;
        return id == that.id &&
                statusId == that.statusId &&
                Objects.equals(siteName, that.siteName) &&
                Objects.equals(searchText, that.searchText) &&
                Objects.equals(shortDescription, that.shortDescription) &&
                Objects.equals(longDescription, that.longDescription) &&
                Objects.equals(price, that.price) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(numberOfReviews, that.numberOfReviews) &&
                Objects.equals(rating, that.rating) &&
                Objects.equals(otherOptions, that.otherOptions) &&
                Objects.equals(categories, that.categories) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(modifiedDate, that.modifiedDate); /*&&
                Objects.equals(sellerList, that.sellerList);*/
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, siteName, searchText, shortDescription, longDescription,
                price, unit, numberOfReviews, rating, otherOptions, categories,
                statusId, createdDate, modifiedDate);//, sellerList);
    }

    @Override
    public String toString() {
        return "ProductSearchResult{" +
                "id=" + id +
                ", siteName='" + siteName + '\'' +
                ", searchText='" + searchText + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", price='" + price + '\'' +
                ", unit='" + unit + '\'' +
                ", numberOfReviews='" + numberOfReviews + '\'' +
                ", rating='" + rating + '\'' +
                ", otherOptions='" + otherOptions + '\'' +
                ", categories='" + categories + '\'' +
                ", statusId=" + statusId +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                //", sellerList=" + sellerList +
                '}';
    }
}