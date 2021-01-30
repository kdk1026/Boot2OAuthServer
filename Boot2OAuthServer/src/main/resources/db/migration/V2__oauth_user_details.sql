CREATE TABLE `oauth_user_details` (
  `username` varchar(256) NOT NULL,
  `password` varchar(256) NOT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `authority` varchar(256) DEFAULT NULL,
  `account_non_expired` tinyint(1) DEFAULT NULL,
  `account_non_locked` tinyint(1) DEFAULT NULL,
  `credentials_non_expired` tinyint(1) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`username`)
);

insert  into `oauth_user_details`(`username`,`password`,`enabled`,`authority`,`account_non_expired`,`account_non_locked`,`credentials_non_expired`,`name`) values ('admin','{bcrypt}$2a$10$Pilq2zdGi9GhX8az..hsx.qhnd9vkSkQGPlE9Tc.ibnlEWauTrFFK',1,'user,admin',1,1,1,'관리자');
insert  into `oauth_user_details`(`username`,`password`,`enabled`,`authority`,`account_non_expired`,`account_non_locked`,`credentials_non_expired`,`name`) values ('user','{bcrypt}$2a$10$Pilq2zdGi9GhX8az..hsx.qhnd9vkSkQGPlE9Tc.ibnlEWauTrFFK',1,'user',1,1,1,'사용자');