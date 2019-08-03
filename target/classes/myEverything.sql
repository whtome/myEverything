-- 创建数据库
create database if not exists myEverything;

-- 创建数据库表
create table if not exists file_index(
name varchar(256) not null comment '文件名称',
path varchar(2048) not null comment '文件路径',
depth int not null comment '文件路径深度',
file_type varchar (32) not null comment '文件类型'
);