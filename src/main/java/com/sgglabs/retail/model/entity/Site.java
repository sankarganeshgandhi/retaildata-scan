package com.sgglabs.retail.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/*
 * CREATE TABLE Site(
 *   Id INT(2) NOT NULL AUTO_INCREMENT,
 *   SiteName VARCHAR(30) NOT NULL,
 *   SiteHostName VARCHAR(30) NOT NULL,
 *   SearchURI VARCHAR(500) NOT NULL,
 *   SiteDescription VARCHAR(100),
 *   StatusId INT(2) NOT NULL,
 *   CreatedDate DATE,
 *   ModifiedDate DATE,
 *   PRIMARY KEY (Id),
 *   FOREIGN KEY (StatusId) REFERENCES Status(Id)
 * )
 */
@Entity
@Table(name = "Site")
public class Site {
    private static final String TO_STRING_FORMAT =
            "Site[id=%s, siteName='%s', siteHostName='%s', searchURL='%s', siteDescription='%s'" +
            "statusId=%s, createDate=%s, modifiedDate=%s]";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "SiteName")
    private String siteName;

    @Column(name = "SiteHostName")
    private String siteHostName;

    @Column(name = "SearchURI")
    private String searchURI;

    @Column(name = "SiteDescription")
    private String siteDescription;

    @Column(name = "StatusId")
    private int statusId;

    @Column(name = "CreatedDate")
    private LocalDate createdDate;

    @Column(name = "ModifiedDate")
    private LocalDate modifiedDate;

    public Site() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteHostName() {
        return siteHostName;
    }

    public void setSiteHostName(String siteHostName) {
        this.siteHostName = siteHostName;
    }

    public String getSearchURI() {
        return searchURI;
    }

    public void setSearchURI(String searchURI) {
        this.searchURI = searchURI;
    }

    public String getSiteDescription() {
        return siteDescription;
    }

    public void setSiteDescription(String siteDescription) {
        this.siteDescription = siteDescription;
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
        Site site = (Site) o;
        return id == site.id &&
                statusId == site.statusId &&
                Objects.equals(siteName, site.siteName) &&
                Objects.equals(siteHostName, site.siteHostName) &&
                Objects.equals(searchURI, site.searchURI) &&
                Objects.equals(siteDescription, site.siteDescription) &&
                Objects.equals(createdDate, site.createdDate) &&
                Objects.equals(modifiedDate, site.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, siteName, siteHostName, searchURI, siteDescription, statusId,
                createdDate, modifiedDate);
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, id, siteName, siteHostName, searchURI,
                siteDescription, statusId, createdDate, modifiedDate);
    }
}