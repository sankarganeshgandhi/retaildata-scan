package com.sgglabs.retail.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/*
 * CREATE TABLE Site(
 *   Id INT(2) NOT NULL AUTO_INCREMENT,
 *   SiteName VARCHAR(30) NOT NULL,
 *   SiteURL VARCHAR(100) NOT NULL,
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
    private static final Logger LOG = LoggerFactory.getLogger(Site.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "SiteName")
    private String siteName;

    @Column(name = "SiteURL")
    private String siteURL;

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

    public String getSiteURL() {
        return siteURL;
    }

    public void setSiteURL(String siteURL) {
        this.siteURL = siteURL;
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
                Objects.equals(siteURL, site.siteURL) &&
                Objects.equals(siteDescription, site.siteDescription) &&
                Objects.equals(createdDate, site.createdDate) &&
                Objects.equals(modifiedDate, site.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, siteName, siteURL, siteDescription, statusId, createdDate, modifiedDate);
    }

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", siteName='" + siteName + '\'' +
                ", siteURL='" + siteURL + '\'' +
                ", siteDescription='" + siteDescription + '\'' +
                ", statusId=" + statusId +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}