CREATE TABLE `oauth_client_details` (
  `client_id` varchar(256) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `redirect_uris` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `auto_approve` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
);

insert  into `oauth_client_details`(`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`redirect_uris`,`authorities`,`access_token_validity`,`refresh_token_validity`,`additional_information`,`auto_approve`) values ('client',NULL,'{bcrypt}$2a$10$SyoMZBXIzl28HaYY.Tkv3etf0JFid0Nv64P5eKBUYAOhNDbWAxxK2','read, write, read_profile','authorization_code, implicit, password, client_credentials, refresh_token','http://localhost:8080/callback','ROLE_YOUR_CLIENT',3600,2592000,NULL,'true');