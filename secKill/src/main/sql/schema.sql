create database secKill;
user secKill;
CREATE TABLE seckill(
`seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` VARCHAR(20) NOT NULL COMMENT '商品名',
`number` INT NOT NULL COMMENT '库存数量',
`start_time` TIMESTAMP NOT NULL COMMENT '秒杀开始时间',
`end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (seckill_id),
KEY idx_start_time(start_time),
KEY idx_end_time(end_time),
KEY idx_create_time(create_time)
)ENGINE=INNODB AUTO_INCREMENT = 1000 DEFAULT CHARSET = utf8 COMMENT '秒杀库存表';

insert into seckill(name,number,start_time,end_time)
values
("1000元秒杀iphone7",100,'2017-7-1 00:00:00','2017-7-1 00:00:00'),
("500元秒杀红米note",200,'2017-7-1 00:00:00','2017-7-1 00:00:00'),
("300元秒杀iWatch",300,'2017-7-1 00:00:00','2017-7-1 00:00:00');

create table success_killed(
`seckill_id` bigint not null comment '秒杀商品id',
`user_phone` bigint not null comment '用户手机号',
`state` smallint not null default -1 comment '状态表示（-1：失败，0：成功，1：已付款）',
`create_time` timestamp not null comment '秒杀时间',
PRIMARY KEY (seckill_id,user_phone),
key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET = utf8 comment '秒杀明细表';
