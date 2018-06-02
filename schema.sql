DROP TABLE IF EXISTS oauth_client_details;
DROP TABLE IF EXISTS oauth_access_token;
DROP TABLE IF EXISTS oauth_approvals;
DROP TABLE IF EXISTS oauth_refresh_token;
DROP TABLE IF EXISTS e_mall_address;
DROP TABLE IF EXISTS e_mall_user;
DROP TABLE IF EXISTS client_user;
DROP TABLE IF EXISTS google_user;
DROP TABLE IF EXISTS facebook_user;

CREATE TABLE oauth_client_details(
	client_id VARCHAR(256) PRIMARY KEY,  
	resource_ids VARCHAR(256),  
	client_secret VARCHAR(256),  
	scope VARCHAR(256),  
	authorized_grant_types VARCHAR(256),  
	web_server_redirect_uri VARCHAR(256),  
	authorities VARCHAR(256),  
	access_token_validity INTEGER,  
	refresh_token_validity INTEGER,  
	additional_information VARCHAR(4096),  
	autoapprove VARCHAR(256));
	
CREATE TABLE oauth_access_token (  
	token_id VARCHAR(256),  
	token LONG VARBINARY,  
	authentication_id VARCHAR(256) PRIMARY KEY,  
	user_name VARCHAR(256),  
	client_id VARCHAR(256),  
	authentication LONG VARBINARY,  
	refresh_token VARCHAR(256));
	
CREATE TABLE oauth_approvals (    
	userId VARCHAR(256),    
	clientId VARCHAR(256),    
	scope VARCHAR(256),    
	status VARCHAR(10),    
	expiresAt TIMESTAMP,    
	lastModifiedAt TIMESTAMP);
	
CREATE TABLE oauth_refresh_token (    
	token_id VARCHAR(256),    
	token LONG VARBINARY,    
	authentication LONG VARBINARY);
	
CREATE TABLE e_mall_user (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  username varchar(50) DEFAULT NULL,
  password varchar(100) DEFAULT NULL,
  enabled bit DEFAULT 1,
  PRIMARY KEY (id)
);

CREATE TABLE e_mall_address (
  user_id bigint(20) NOT NULL,
  street varchar(50) DEFAULT NULL,
  city varchar(100) DEFAULT NULL,
  province varchar(50) DEFAULT NULL,
  postal_code varchar(6) DEFAULT NULL,
  PRIMARY KEY (user_id)
);
	
CREATE TABLE client_user (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  username varchar(50) DEFAULT NULL,
  password varchar(100) DEFAULT NULL,
  access_token varchar(100) DEFAULT NULL,
  access_token_validity datetime DEFAULT NULL,
  refresh_token varchar(100) DEFAULT NULL,
  enabled bit DEFAULT 1,
  PRIMARY KEY (id)
);

CREATE TABLE google_user (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  sub varchar(50) DEFAULT NULL,
  telephone varchar(11) DEFAULT NULL,
  address varchar(100) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE facebook_user (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  sub varchar(50) DEFAULT NULL,
  telephone varchar(11) DEFAULT NULL,
  address varchar(100) DEFAULT NULL,
  PRIMARY KEY (id)
);
	
INSERT INTO oauth_client_details(
	client_id, resource_ids, client_secret, scope, authorized_grant_types, 
	web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, 
	additional_information, autoapprove)
	VALUES ('webapp', null, '$2a$04$Ja839oYOTPlNI0EyX0KTs.N4BSjeWwSWe0vdoGYPS5BedKLLSqlBa', 'user:profile:read,user:profile:write', 'authorization_code',    
		    'http://localhost:8282/callback', null, 3600, -1, null, 'false');
			
INSERT INTO oauth_client_details(
	client_id, client_secret, scope, authorized_grant_types, 
	web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, 
	additional_information, autoapprove)
	VALUES ('clientapp', '$2a$04$Ja839oYOTPlNI0EyX0KTs.N4BSjeWwSWe0vdoGYPS5BedKLLSqlBa', 'user:profile:read,user:profile:write', 'client_credentials',    
		    'http://localhost:8282/callback', null, 3600, -1, null, 'false');
			
INSERT INTO oauth_client_details(
	client_id, client_secret, scope, authorized_grant_types, 
	web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, 
	additional_information, autoapprove)
	VALUES ('webapp2', '$2a$04$Ja839oYOTPlNI0EyX0KTs.N4BSjeWwSWe0vdoGYPS5BedKLLSqlBa', 'user:profile:read,user:profile:write', 'implicit',    
		    'http://localhost:8282/callback', null, 3600, -1, null, 'false');
			

INSERT INTO oauth_client_details(
	client_id, client_secret, scope, authorized_grant_types, 
	web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, 
	additional_information, autoapprove)
	VALUES ('webapp3', '$2a$04$Ja839oYOTPlNI0EyX0KTs.N4BSjeWwSWe0vdoGYPS5BedKLLSqlBa', 'user:profile:read,user:profile:write', 'password',    
		    null, null, 3600, -1, null, 'false');
			
			
INSERT INTO e_mall_user(username, password) VALUES('joe', '$2a$04$3lTPdSHGjGU.5tijTiLBD.RbBv.fswDSiinl2GDrNoy/Rh0BRjMKi');
INSERT INTO e_mall_user(username, password) VALUES('don', '$2a$04$5UdIjubHJOJn4YOM//aNROJqIuuwok4iTT.89S07IE/TNDRP45sfK');
INSERT INTO e_mall_user(username, password) VALUES('jim', '$2a$04$3lTPdSHGjGU.5tijTiLBD.RbBv.fswDSiinl2GDrNoy/Rh0BRjMKi');
INSERT INTO e_mall_user(username, password) VALUES('tom', '$2a$04$5UdIjubHJOJn4YOM//aNROJqIuuwok4iTT.89S07IE/TNDRP45sfK');

INSERT INTO e_mall_address (user_id, street, city, province, postal_code) VALUES (1, '175 Columnbia Sreet', 'Waterloo', 'Ontario', 'N2K3Z5');
INSERT INTO e_mall_address (user_id, street, city, province, postal_code) VALUES (2, '200 University Ave. West', 'Waterloo', 'Ontario', 'N2K6L3');

		    
INSERT INTO client_user (username, password) VALUES ('frodob', '$2a$04$3lTPdSHGjGU.5tijTiLBD.RbBv.fswDSiinl2GDrNoy/Rh0BRjMKi');
INSERT INTO client_user (username, password) VALUES ('frodoc', '$2a$04$5UdIjubHJOJn4YOM//aNROJqIuuwok4iTT.89S07IE/TNDRP45sfK');
