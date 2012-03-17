# Tested in: MYSQL 5.5
# CREATE DATABASE ASTRO_PRICELIST; # TODO: Ability to create the DB from here!

#Table to hold all 
CREATE TABLE allStockNames (
	
	id		SMALLINT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
	name	VARCHAR(20) NOT NULL,
	market	VARCHAR(10) NOT NULL
);

CREATE TABLE stockPriceHistory (
	
	id		SMALLINT UNSIGNED NOT NULL,
	volume	MEDIUMINT UNSIGNED NOT NULL,
	latest	INT UNSIGNED NOT NULL,
	buy		INT UNSIGNED NOT NULL,
	sell	INT UNSIGNED NOT NULL,
	time	DATETIME,
	
	FOREIGN KEY (id) REFERENCES allStockNames(id)
);

#Test, Insert into allStockNames
INSERT INTO allStockNames VALUES ('1', 'Apa', 'MidCap');
INSERT INTO allStockNames VALUES ('2', 'Bepa', 'SmallCap');
INSERT INTO allStockNames VALUES ('3', 'Cepa', 'LargeCap');

#Test, Insert into stockPriceHistory
INSERT INTO stockPriceHistory VALUES ('1', '20', '20', '40', '30', '2012-05-01 00:00:01');
INSERT INTO stockPriceHistory VALUES ('2', '120', '120', '140', '130', '2012-05-02 00:00:01');
INSERT INTO stockPriceHistory VALUES ('3', '220', '220', '240', '230', '2012-05-03 00:00:01');
INSERT INTO stockPriceHistory VALUES ('1', '320', '320', '340', '330', '2012-05-03 00:00:01');
INSERT INTO stockPriceHistory VALUES ('2', '420', '420', '440', '430', '2012-05-03 00:00:01');
INSERT INTO stockPriceHistory VALUES ('3', '520', '520', '540', '530', '2012-05-03 00:00:01');

#Test, Select from stockPriceHistory JOIN allStockNames
#Awkward SELECT statement => Just to avoid 
SELECT stockPriceHistory.id, volume, latest, buy, sell, time, name, market FROM stockPriceHistory
JOIN allStockNames ON allStockNames.id = stockPriceHistory.id

# :-)
#