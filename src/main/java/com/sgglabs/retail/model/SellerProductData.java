package com.sgglabs.retail.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/*
 * CREATE TABLE SellerProductData (
 *   Id BIGINT(20) NOT NULL AUTO_INCREMENT,
 *   ProdSearchResultId BIGINT(20) NOT NULL,
 *   SellerName VARCHAR(30),
 *   RatingIndex VARCHAR(30),
 *   TotalRatings VARCHAR(20),
 *   Details VARCHAR(50),
 *   BasePrice VARCHAR(50),
 *   TotalPrice VARCHAR(20),
 *   StatusId INT(2) NOT NULL DEFAULT 2,
 *   CreatedDate DATE,
 *   ModifiedDate DATE,
 *   PRIMARY KEY (Id),
 *   FOREIGN KEY (ProdSearchResultId) REFERENCES ProductSearchResult(Id)
 *   FOREIGN KEY (StatusId) REFERENCES Status(Id)
 *  );
 */
@Entity
@Table(name = "SellerProductData")
public class SellerProductData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "SellerName")
    private String sellerName;

    @Column(name = "RatingIndex")
    private String ratingIndex;

    @Column(name = "TotalRatings")
    private String numberOfRatings;

    @Column(name = "Details")
    private String details;

    @Column(name = "BasePrice")
    private String basePrice;

    @Column(name = "TotalPrice")
    private String totalPrice;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProdSearchResultId", nullable = false)
    */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ProdSearchResultId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductSearchResult productSearchResult;

    @Column(name = "StatusId")
    private int statusId;

    @Column(name = "CreatedDate")
    private LocalDate createdDate;

    @Column(name = "ModifiedDate")
    private LocalDate modifiedDate;

    public SellerProductData() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getRatingIndex() {
        return ratingIndex;
    }

    public void setRatingIndex(String ratingIndex) {
        this.ratingIndex = ratingIndex;
    }

    public String getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(String numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ProductSearchResult getProductSearchResult() {
        return productSearchResult;
    }

    public void setProductSearchResult(ProductSearchResult productSearchResult) {
        this.productSearchResult = productSearchResult;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellerProductData that = (SellerProductData) o;
        return id == that.id &&
                statusId == that.statusId &&
                Objects.equals(sellerName, that.sellerName) &&
                Objects.equals(ratingIndex, that.ratingIndex) &&
                Objects.equals(numberOfRatings, that.numberOfRatings) &&
                Objects.equals(details, that.details) &&
                Objects.equals(basePrice, that.basePrice) &&
                Objects.equals(totalPrice, that.totalPrice) &&
                Objects.equals(productSearchResult, that.productSearchResult) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(modifiedDate, that.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sellerName, ratingIndex, numberOfRatings, details,
                basePrice, totalPrice, productSearchResult, statusId, createdDate, modifiedDate);
    }

    @Override
    public String toString() {
        return "SellerProductData{" +
                "id=" + id +
                ", sellerName='" + sellerName + '\'' +
                ", ratingIndex='" + ratingIndex + '\'' +
                ", numberOfRatings='" + numberOfRatings + '\'' +
                ", details='" + details + '\'' +
                ", basePrice='" + basePrice + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", productSearchResult=" + productSearchResult +
                ", statusId=" + statusId +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}