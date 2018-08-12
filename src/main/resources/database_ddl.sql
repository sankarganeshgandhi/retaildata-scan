DROP TABLE SearchText;
DROP TABLE Site;
DROP TABLE Status;

CREATE TABLE Status(
    Id INT(2) NOT NULL AUTO_INCREMENT,
    Description VARCHAR(30) NOT NULL,
    CreatedDate DATE,
    ModifiedDate DATE,
    PRIMARY KEY (Id)
) ENGINE = INNODB;

CREATE TABLE Site(
    Id INT(2) NOT NULL AUTO_INCREMENT,
    SiteName VARCHAR(30) NOT NULL,
    SiteURL VARCHAR(100) NOT NULL,
    SiteDescription VARCHAR(100),
    StatusId INT(2) NOT NULL,
    CreatedDate DATE,
    ModifiedDate DATE,
    PRIMARY KEY (Id),
    FOREIGN KEY (StatusId) REFERENCES Status(Id)
) ENGINE = INNODB;

CREATE TABLE SearchText(
    Id BIGINT(10) NOT NULL AUTO_INCREMENT,
    SearchText VARCHAR(100) NOT NULL,
    StatusId INT(2) NOT NULL,
    CreatedDate DATE,
    ModifiedDate DATE,
    PRIMARY KEY (Id),
    FOREIGN KEY (StatusId) REFERENCES Status(Id)
) ENGINE = INNODB;

INSERT INTO Status (Description) VALUES ("Inactive");
INSERT INTO Status (Description) VALUES ("Active");
INSERT INTO Site (SiteName, SiteURL, StatusId) VALUES ("Google UK", "http://www.google.co.uk", 2);
INSERT INTO SearchText (SearchText, StatusId) VALUES ("menu+shampoo", 2);


#CREATE TABLE ProdSearchResult(
#    Id BIGINT(30) NOT NULL,
#    SiteId INT(2) NOT NULL,
#    SearchTextId BIGINT(10) NOT NULL,
#    ShortDesc VARCHAR(150),
#    LongDesc VARCHAR(300),
#    Price VARCHAR(30),
#    Reviews VARCHAR(50),
#    Ratings VARCHAR(50),
#    OtherOptions VARCHAR(100),
#    ProdCategories VARCHAR(200),
#    StatusId INT(2) NOT NULL,
#    CreatedDate DATE,
#    ModifiedDate DATE,
#    PRIMARY KEY (Id),
#    FOREIGN KEY (SiteId) REFERENCES Site(Id)
#    FOREIGN KEY (SearchTextId) REFERENCES SearchText(Id)
#    FOREIGN KEY (StatusId) REFERENCES Status(Id)
#) ENGINE = INNODB;
