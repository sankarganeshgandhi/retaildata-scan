package com.sgglabs.retail.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/*
 * CREATE TABLE Status(
 *      Id INT(2) NOT NULL AUTO_INCREMENT,
 *      Description VARCHAR(30) NOT NULL,
 *      CreatedDate DATE,
 *      ModifiedDate DATE,
 *      PRIMARY KEY (Id)
 * );
 */
@Entity
@Table(name = "Status")
public class Status {
    private static final String TO_STRING_FORMAT =
            "Status[id=%s, description='%s', createdDate=%s, modifiedDate=%s]";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Description")
    private String description;

    @Column(name = "CreatedDate")
    private LocalDate createdDate;

    @Column(name = "ModifiedDate")
    private LocalDate modifiedDate;

    public Status() {
    }

    public void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        Status status = (Status) o;
        return id == status.id &&
                Objects.equals(description, status.description) &&
                Objects.equals(createdDate, status.createdDate) &&
                Objects.equals(modifiedDate, status.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, createdDate, modifiedDate);
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, id, description, createdDate, modifiedDate);
    }
}