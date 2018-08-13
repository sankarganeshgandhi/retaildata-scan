DROP TABLE SellerProductData;
DROP TABLE ProductSearchResult;
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
    SiteHostName VARCHAR(30) NOT NULL,
    SearchURI VARCHAR(500) NOT NULL,
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
INSERT INTO Site (SiteName, SiteHostName, SearchURI, StatusId) VALUES ("Google UK", "https://www.google.co.uk", "/search?q={0}&hl=en-GB&source=lnms&tbm=shop&sa=X", 2);
INSERT INTO SearchText (SearchText, StatusId) VALUES ("menu+shampoo", 2);

CREATE TABLE ProductSearchResult(
    Id BIGINT(20) NOT NULL AUTO_INCREMENT,
    SiteName VARCHAR(30) NOT NULL,
    SearchText VARCHAR(100) NOT NULL,
    ShortDesc VARCHAR(150),
    LongDesc VARCHAR(300),
    Price VARCHAR(50),
    Unit VARCHAR(50),
    Reviews VARCHAR(250),
    Ratings VARCHAR(50),
    OtherOptions VARCHAR(100),
    ProdCategories VARCHAR(200),
    StatusId INT(2) NOT NULL DEFAULT 2,
    CreatedDate DATE,
    ModifiedDate DATE,
    PRIMARY KEY (Id),
    FOREIGN KEY (StatusId) REFERENCES Status(Id)
) ENGINE = INNODB;

CREATE TABLE SellerProductData (
    Id BIGINT(20) NOT NULL AUTO_INCREMENT,
    ProdSearchResultId BIGINT(20) NOT NULL,
    SellerName VARCHAR(150),
    RatingIndex VARCHAR(100),
    TotalRatings VARCHAR(100),
    Details VARCHAR(250),
    BasePrice VARCHAR(150),
    TotalPrice VARCHAR(150),
    StatusId INT(2) NOT NULL DEFAULT 2,
    CreatedDate DATE,
    ModifiedDate DATE,
    PRIMARY KEY (Id),
    FOREIGN KEY (ProdSearchResultId) REFERENCES ProductSearchResult(Id),
    FOREIGN KEY (StatusId) REFERENCES Status(Id)
) ENGINE = INNODB;