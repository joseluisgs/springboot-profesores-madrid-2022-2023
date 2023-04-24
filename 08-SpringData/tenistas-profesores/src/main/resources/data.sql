-- Datos de prueba para la aplicación

-- RAQUETAS

INSERT INTO raquetas (id, uuid, marca, modelo, precio, imagen)
VALUES (NEXTVAL('raquetas_seq'), 'f503a9ae-56b4-448e-b0fe-9bb0a263075c', 'Babolat', 'Pure Aero', 199.95,
        'https://img.tenniswarehouse-europe.com/watermark/rs.php?path=BPAR22-1.jpg');

INSERT INTO raquetas (id, uuid, marca, modelo, precio, imagen)
VALUES (NEXTVAL('raquetas_seq'), 'ff46f1da-eaf1-4ffa-a8af-f867aac3e1c2', 'Head', 'Speed', 225.50,
        'https://www.m1tennis.com/24397-large_default/raqueta-head-speed-power-pwr-lite-2022.jpg');

INSERT INTO raquetas (id, uuid, marca, modelo, precio, imagen)
VALUES (NEXTVAL('raquetas_seq'), 'f4b0b0a9-0b0a-4b0a-8b0a-9b0a0a0a0a0a', 'Wilson', 'Blade', 250.00,
        'https://www.onlytenis.com/21761-large_default/raqueta-wilson-blade-98-v8-305-gr-16x19-2022.jpg');
