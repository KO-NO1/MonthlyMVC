CREATE TABLE prefecture
(
   id serial PRIMARY KEY,
   prefecture VARCHAR (100),
   max_monthly_price INTEGER,
   data_flag boolean default true
);
 
CREATE TABLE mansion
(
   id serial PRIMARY KEY,
   name VARCHAR (100),
   address VARCHAR (100),
   monthly_price integer,
   prefecture_id INTEGER,
   building_date DATE,
   image_path VARCHAR (255),
   data_flag boolean default true,
   foreign key (prefecture_id) references prefecture (id) ON DELETE CASCADE
);
 
 
 
insert into prefecture(prefecture,max_monthly_price) values('東京都',200000);
insert into prefecture(prefecture,max_monthly_price) values('神奈川',180000);
insert into prefecture(prefecture,max_monthly_price) values('埼玉',160000);
insert into prefecture(prefecture,max_monthly_price) values('茨城',140000);
insert into prefecture(prefecture,max_monthly_price) values('群馬',120000);
insert into prefecture(prefecture,max_monthly_price) values('栃木',100000);
 
 
insert into mansion (name,address,prefecture_id,monthly_price,building_date,image_path,data_flag)
values('グリーンヒルズ','渋谷区渋谷X-X-X',3,200000,'2023-06-15','/image/noImage.png',true);
insert into mansion (name,address,prefecture_id,monthly_price,building_date,image_path,data_flag)
values('サンシャインレジデンス','千代田区神田神保町x-x',1,200000,'2010-05-01','/image/noImage.png',true);
insert into mansion (name,address,prefecture_id,monthly_price,building_date,image_path,data_flag)
values('レインボーハイツ','中郡二宮町二宮xxx',2,200000,'2008-12-01','/image/noImage.png',true);
insert into mansion (name,address,prefecture_id,monthly_price,building_date,image_path,data_flag)
values('グランドパレス','水戸市三の丸x-x',4,80000,'2011-11-01','/image/noImage.png',true);
insert into mansion (name,address,prefecture_id,monthly_price,building_date,image_path,data_flag)
values('パークサイド','西区南X-X-X',2,100000,'2012-07-01','/image/noImage.png',true);