CREATE TABLE `t_user` (
  `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '真实姓名',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `deleted` int(2) NOT NULL DEFAULT 0 COMMENT '状态：1：删除，0：有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '用户基本信息';

INSERT INTO t_user (name,username,password) VALUES ('张三','ZhangSan','000000');
INSERT INTO t_user (name,username,password) VALUES ('李四','LiSi','111111');
INSERT INTO t_user (name,username,password) VALUES ('王五','WangWu','222222');
INSERT INTO t_user (name,username,password) VALUES ('赵六','ZhaoLiu','333333');
