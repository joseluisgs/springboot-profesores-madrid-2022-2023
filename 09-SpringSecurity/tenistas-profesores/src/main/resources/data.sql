-- Datos de prueba para la aplicación

-- RAQUETAS
-- Creo una secuencia para el id de las raquetas,
-- si te das cuenta e slo que hace el script, pero como estoy metiendo datos
-- en la tabla, necesito que el id sea autoincremental,a partir de un valor
DROP SEQUENCE IF EXISTS raquetas_seq;
create SEQUENCE raquetas_seq START WITH 100 INCREMENT BY 1;

INSERT INTO raquetas (id, uuid, marca, modelo, precio, imagen)
VALUES (1, 'f503a9ae-56b4-448e-b0fe-9bb0a263075c', 'Babolat', 'Pure Aero', 199.95,
        'https://img.tenniswarehouse-europe.com/watermark/rs.php?path=BPAR22-1.jpg');

INSERT INTO raquetas (id, uuid, marca, modelo, precio, imagen)
VALUES (2, 'ff46f1da-eaf1-4ffa-a8af-f867aac3e1c2', 'Head', 'Speed', 225.50,
        'https://www.m1tennis.com/24397-large_default/raqueta-head-speed-power-pwr-lite-2022.jpg');

INSERT INTO raquetas (id, uuid, marca, modelo, precio, imagen)
VALUES (3, 'f4b0b0a9-0b0a-4b0a-8b0a-9b0a0a0a0a0a', 'Wilson', 'Blade', 250.00,
        'https://www.onlytenis.com/21761-large_default/raqueta-wilson-blade-98-v8-305-gr-16x19-2022.jpg');

-- TENISTAS
DROP SEQUENCE IF EXISTS tenistas_seq;
create SEQUENCE tenistas_seq START WITH 100 INCREMENT BY 1;

INSERT INTO tenistas (id, uuid, nombre, ranking, pais, imagen, raqueta_id)
VALUES (1, 'db4e3a1e-b0b9-4a22-8959-862264b0bc57', 'Rafael Nadal', 1, 'España',
        'https://upload.wikimedia.org/wikipedia/commons/9/9b/Rafael_Nadal_10%2C_Aegon_Championships%2C_London%2C_UK_-_Diliff_%28cropped%29.jpg',1);

INSERT INTO tenistas (id, uuid, nombre, ranking, pais, imagen, raqueta_id)
VALUES (2, 'feab014d-caa8-44d7-b0dd-41f1ea2b95f6', 'Novak Djokovic', 2, 'Serbia',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/4/48/Novak_Djokovi%C4%87_Trophy_Wimbledon_2019-croped_and_edited.jpg/245px-Novak_Djokovi%C4%87_Trophy_Wimbledon_2019-croped_and_edited.jpg',2);

INSERT INTO tenistas (id, uuid, nombre, ranking, pais, imagen, raqueta_id)
VALUES (3, '893b326c-dde2-4639-bce5-8c3364d2117e', 'Roger Federer', 3, 'Suiza',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/R_federer.jpg/245px-R_federer.jpg',3);

INSERT INTO tenistas (id, uuid, nombre, ranking, pais, imagen, raqueta_id)
VALUES (4, 'd52aa228-4513-4e97-b72c-cab977a4f4d9', 'Carlos Alcaraz', 4, 'España',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/9/97/Carlos_Alcaraz_%28ESP%29_2022.jpg/1200px-Carlos_Alcaraz_%28ESP%29_2022.jpg',1);

-- USUARIOS
DROP SEQUENCE IF EXISTS usuarios_seq;
create SEQUENCE usuarios_seq START WITH 100 INCREMENT BY 1;
-- Contraseña: Admin1
insert into usuarios (id, full_name, email, username, password, avatar, created_at, last_password_change_at)
values (1, 'Admin admin', 'admin@prueba.net', 'admin', '$2a$10$vPaqZvZkz6jhb7U7k/V/v.5vprfNdOnh4sxi/qpPRkYTzPmFlI9p2',
        'https://api.lorem.space/image/face?w=150&h=150', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into usuario_roles (usuario_id, roles)
values (1, 'USER');
insert into usuario_roles (usuario_id, roles)
values (1, 'ADMIN');

-- Contraseña: Marialopez1
insert into usuarios (id, full_name, email, username, password, avatar, created_at, last_password_change_at)
values (2, 'María López', 'maria.lopez@prueba.net', 'marialopez',
        '$2a$10$3i95KIxdl8igcpDby.URMOgwdDR2q9UaSfor2kJJrhAPfNOC/HMSi',
        'https://api.lorem.space/image/face?w=150&h=150', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into usuario_roles (usuario_id, roles)
values (2, 'USER');

-- Contraseña: Angelmartinez1
insert into usuarios (id, full_name, email, username, password, avatar, created_at, last_password_change_at)
values (3, 'Ángel Martínez', 'angel.martinez@prueba.net', 'angelmartinez',
        '$2a$10$37IEM6zzuwXqFrotYDtySOKITKfeNWR3NBRqcM7JYWnBDIaq9ByZm',
        'https://api.lorem.space/image/face?w=150&h=150', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

insert into usuario_roles (usuario_id, roles)
values (3, 'USER');

-- Contraseña: Anajimenez1
insert into usuarios (id, full_name, email, username, password, avatar, created_at, last_password_change_at)
values (4, 'Ana Jiménez', 'ana.jimenez@prueba.net', 'anajimenez',
        '$2a$10$k0om5gtNBheWX54VzD1E0etCnqC0xChHjfW3lOXaeCpN5ST1vVGYm',
        'https://api.lorem.space/image/face?w=150&h=150', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into usuario_roles (usuario_id, roles)
values (4, 'USER');


