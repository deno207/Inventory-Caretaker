CREATE TABLE IF NOT EXISTS Address (id integer not null, city varchar(255), country varchar(255), county varchar(255), lineOne varchar(255), lineTwo varchar(255), postCode varchar(255), primary key (id));

CREATE TABLE IF NOT EXISTS Category (id integer not null, description varchar(255), imageName varchar(255), name varchar(255), parent_id integer, primary key (id));

CREATE TABLE IF NOT EXISTS StockItem (id integer not null, currentStock float(19) not null, description varchar(255), hasSubItem boolean not null, imageName varchar(255), lowStock float(19) not null, measurementType integer, name varchar(255), subItemPlural varchar(255), subItemSingular varchar(255), category_id integer, primary key (id), unique (name));

CREATE TABLE IF NOT EXISTS StockItem_SubItem (StockItem_id integer not null, subItems_id integer not null, unique (subItems_id));

CREATE TABLE IF NOT EXISTS StockItem_Supplier (StockItem_id integer not null, suppliers_id integer not null);

CREATE TABLE IF NOT EXISTS SubItem (id integer not null, amount float(19) not null, displayId varchar(255), location varchar(255), primary key (id));

CREATE TABLE IF NOT EXISTS Supplier (id integer not null, description varchar(255), imageName varchar(255), name varchar(255), phoneNumber varchar(255), website varchar(255), address_id integer, primary key (id));