package com.sgglabs.retail.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/*
 * CREATE TABLE SearchText(
 *   Id BIGINT(10) NOT NULL AUTO_INCREMENT,
 *   SearchText VARCHAR(100) NOT NULL,
 *   StatusId INT(2) NOT NULL,
 *   CreatedDate DATE,
 *   ModifiedDate DATE,
 *   PRIMARY KEY (Id),
 *   FOREIGN KEY (StatusId) REFERENCES Status(Id)
 * )
 */
@Entity
@Table(name = "SearchText")
public class SearchText {
    private static final String TO_STRING_FORMAT =
            "SearchText[id=%s, searchText='%s', statusId=%s, createdDate=%s, modifiedDate=%s]";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "SearchText")
    private String searchText;

    @Column(name = "StatusId")
    private int statusId;

    @Column(name = "CreatedDate")
    private LocalDate createdDate;

    @Column(name = "ModifiedDate")
    private LocalDate modifiedDate;

    public SearchText() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
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
        SearchText that = (SearchText) o;
        return id == that.id &&
                statusId == that.statusId &&
                Objects.equals(searchText, that.searchText) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(modifiedDate, that.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, searchText, statusId, createdDate, modifiedDate);
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, id, searchText, statusId, createdDate, modifiedDate);
    }
}