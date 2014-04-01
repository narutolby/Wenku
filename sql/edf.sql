create database EDF;
use EDF;
create table USER(
  id varchar(32) primary key,
  username varchar(255) not null,
  password varchar(255) not null,
  mark int(11) default  200
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8;


/*资源表*/
create table RESOURCE(
  id varchar(32) primary key,
  resource_real_name varchar(255),
  upload_user_id varchar(32),   /*外键关联user表id*/
  upload_time datetime,
  resource_type varchar(10),
  resource_size bigint,
  download_times int(5),
  resource_snapshot_path varchar(255) ,
  delete_flag int /*0:代表资源存在 1：代表资源删除*/
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8;
