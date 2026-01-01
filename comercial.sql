-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Vcategoriasersión del servidor:         10.4.32-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para comercial
CREATE DATABASE IF NOT EXISTS `comercial` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `comercial`;

-- Volcando estructura para tabla comercial.categorias
CREATE TABLE IF NOT EXISTS `categorias` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL,
  `ubicacion` varchar(70) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10574 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla comercial.categorias: ~10 rows (aproximadamente)
REPLACE INTO `categorias` (`id`, `descripcion`, `ubicacion`) VALUES
	(10564, 'Procesadores', 'Mezzanine'),
	(10565, 'Video', 'Bodega 1'),
	(10566, 'Telefonía', 'Bodega 2'),
	(10567, 'Almacenamiento', 'Mezzanine'),
	(10568, 'Periféricos', 'Mezzanine'),
	(10569, 'Portátiles', 'Mezzanine'),
	(10570, 'Sonido', 'Bodega 2'),
	(10571, 'Comunicaciones', 'Bodega 1'),
	(10572, 'Videojuegos', 'Mostrador'),
	(10573, 'Accesorios', 'Mostrador');

-- Volcando estructura para tabla comercial.clientes
CREATE TABLE IF NOT EXISTS `clientes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `capacidad_credito` int(11) NOT NULL,
  `correo_electronico` varchar(60) NOT NULL,
  `direccion` varchar(255) NOT NULL,
  `fecha_ingreso` date NOT NULL,
  `identificacion` bigint(20) NOT NULL,
  `nombre_completo` varchar(60) NOT NULL,
  `telefono` varchar(255) NOT NULL,
  `tipo_identificacion` varchar(50) NOT NULL,
  `imagen` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla comercial.clientes: ~31 rows (aproximadamente)
REPLACE INTO `clientes` (`id`, `capacidad_credito`, `correo_electronico`, `direccion`, `fecha_ingreso`, `identificacion`, `nombre_completo`, `telefono`, `tipo_identificacion`, `imagen`) VALUES
	(1, 3000000, 'eobando@gmail.com', 'Cr 52 38 - 70', '2010-03-10', 70195347, 'Elkin León Obando Builes', '345-1245', 'Cédula', ''),
	(2, 1370000, 'admon@lasacacias.com', 'Av. 33: Calle 33 #63B -51', '2015-12-31', 811005425, 'Bar Las Acacias', '458-6547', 'NIT', ''),
	(3, 1120000, 'oliver1236@hotmail.com', 'Cl 44 69-64', '2015-11-06', 521949, 'Oliver Steve Smith', '325-8457', 'Cédula extranjería', ''),
	(4, 440000, 'mlagos@yahoo.es', 'Cl 44 69 - 38', '2017-09-26', 1258795000, 'Maritza Lagos Oliva ', '320-1000', 'Cédula', ''),
	(6, 8000000, 'luis1266@hotmail.com', 'Cr 43F 14-76 ', '2016-10-15', 1012584561, 'Luis Felipe Erazo Castro', '546-1935', 'Cédula', ''),
	(7, 1700000, 'taller1276@hotmail.es', 'Cl 44 74-39 ', '2012-07-05', 821547600, 'Taller Mofeta', '254-3254', 'NIT', ''),
	(8, 360000, 'distribuidora1286@hotmail.com', 'Cl 45 67-25 ', '2010-08-05', 890901845, 'Distribuidora La 45', '236-1235', 'NIT', ''),
	(9, 150000, 'tania1296@hotmail.com', 'Cr 84 33-62 ', '2017-05-11', 43856974, 'Tania Angelica Villar Cariaga', '365-4125', 'Cédula', ''),
	(10, 1560000, 'monjas@secre.edu.co', 'Cl 75 64 B-34 ', '2018-01-16', 811569700, 'I.E. Las Monjas', '265-1278', 'NIT', ''),
	(11, 830000, 'juan12116@hotmail.com', 'Cr43 31-148 ', '2017-02-28', 71985419, 'Juan Carrasco Vega', '322-1001', 'Cédula', ''),
	(12, 5870000, 'ferretería12126@hotmail.com', 'Cr 50 67-44 ', '2013-01-24', 811205872, 'Ferretería Las Tres Rayas', '236-4561', 'NIT', ''),
	(13, 670000, 'mbillar@yahoo.es', 'Cl 34 A 40-139 Itagüí ', '2018-11-18', 891658731, 'Billar Bola 8', '236-5469', 'NIT', ''),
	(14, 1070000, 'diana12146@yahoo.com', 'Cr 50 39-64 ', '2014-10-23', 1028745961, 'Diana Carolina Zuñiga Ceron', '214-5421', 'Cédula', ''),
	(15, 0, 'peluquería12156@hotmail.com', 'Av 33 64-174 ', '2011-05-18', 811005698, 'Peluquería Luis XV', '231-1289', 'NIT', ''),
	(16, 280000, 'talabartería12166@hotmail.com', 'Cr 46 39-03 ', '2016-05-04', 890754133, 'Talabartería El Carriel', '325-4578', 'NIT', ''),
	(17, 830000, 'flor12176@gmail.com', 'Cl 11s 50-29', '2012-11-19', 42954836, 'Flor Marina Peña Mendoza', '456-2791', 'Cédula', ''),
	(18, 280000, 'jose.luis@gmail.com', 'Cl 53 46-47 L-110', '2018-05-04', 115800654, 'José Luis Flores Terán', '321-8521', 'Cédula', ''),
	(19, 4180000, 'compañía12196@hotmail.com', 'Cr 39 55-17', '2013-11-03', 811247961, 'Compañía XYZ Ltda.', '254-7139', 'NIT', ''),
	(20, 10440000, 'notaría12206@hotmail.com', 'Cr 42B 33-38', '2017-05-10', 811005641, 'Notaría 2020', '241-2514', 'NIT', ''),
	(21, 1140000, 'mwilliam@yahoo.es', 'Cl 51 45 - 57', '2018-12-12', 679124, 'William Wilstermann', '321-5432', 'Cédula extranjería', ''),
	(22, 5200000, 'lapresentacion@educame.gov.co', 'Calle 7 No. 58 - 30', '2008-03-14', 800546124, 'I.E. La Presentación (Guayabal)', '2555481', 'NIT', ''),
	(23, 6000000, 'laguaya@correo.com', 'Calle 44 No. 55 - 23', '2012-10-13', 812456123, 'Ferretería la Guaya', '3216547', 'NIT', ''),
	(24, 5000000, 'jfelipe@gmail.com', 'Calle 56 Boston', '2014-11-14', 70192934, 'Juan Felipe Gil', '321-9845', 'Cédula', ''),
	(25, 2690000, 'pluza@hotmail.com', 'Carrera 70 Estadio', '2001-11-12', 75489, 'Peter Lu', '2569871', 'Cédula extranjería', ''),
	(27, 4000000, 'jgil@correo.com', 'Cra. 52 51 27 Torre 1', '2013-11-14', 1000324564, 'Juan Hernando Gil', '3221000', 'Cédula', ''),
	(29, 4500000, 'alobando@yahoo.es', 'Cl 3 16 23 Rionegro', '2017-07-15', 1000342321, 'Alba Luz Obando', '8765678', 'Cédula', ''),
	(30, 3000000, 'remington@uniremington.edu.co', 'Cl 51 No. 51-27 Edificio Remington', '2020-02-28', 811005, 'Corporación Universitaria Remington', '322-1000', 'NIT', ''),
	(31, 3500000, 'dlloweb@miremington.edu.co', 'Uniremington Medellín', '2023-01-29', 1234567890, 'Grupo Desarrollo Web II', '6043221000', 'NIT', ''),
	(35, 3500000, 'casanueva@hotmail.com', 'Cr. 40 39-68', '2023-05-28', 812214412, 'Inmoviliaria Casa Nueva', '3458172', 'NIT', ''),
	(36, 5000000, 'gerencia@desarrollo.com', 'Barrio Candelaria MDE', '2023-05-30', 813789456, 'Empresa de Desarrollo Ltda.', '6043214589', 'NIT', ''),
	(38, 2500000, 'juan.ospina@gmail.com', 'Calle. 51 51 27', '2025-06-03', 1010075436, 'Juan de Dios Ospina', '3104217693', 'Cédula', '');

-- Volcando estructura para tabla comercial.detalles
CREATE TABLE IF NOT EXISTS `detalles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cantidad` int(11) NOT NULL,
  `producto_id` bigint(20) DEFAULT NULL,
  `nro_factura` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKio4oyl8qt5jclekxp7bwws2iy` (`producto_id`),
  KEY `FKh6w084nyoo5xcjr8urvu28ryj` (`nro_factura`),
  CONSTRAINT `FKh6w084nyoo5xcjr8urvu28ryj` FOREIGN KEY (`nro_factura`) REFERENCES `facturas` (`nro_factura`),
  CONSTRAINT `FKio4oyl8qt5jclekxp7bwws2iy` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla comercial.detalles: ~7 rows (aproximadamente)
REPLACE INTO `detalles` (`id`, `cantidad`, `producto_id`, `nro_factura`) VALUES
	(1, 4, 2, 1),
	(2, 4, 7, 1),
	(3, 3, 6, 1),
	(4, 1, 7, 1),
	(5, 2, 3, 2),
	(6, 1, 7, 2),
	(7, 10, 4, 3);

-- Volcando estructura para tabla comercial.facturas
CREATE TABLE IF NOT EXISTS `facturas` (
  `nro_factura` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL,
  `fecha_venta` date NOT NULL,
  `observacion` varchar(255) DEFAULT NULL,
  `cliente_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`nro_factura`),
  KEY `FK1qiuk10rfkovhlfpsk7oic0v8` (`cliente_id`),
  CONSTRAINT `FK1qiuk10rfkovhlfpsk7oic0v8` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla comercial.facturas: ~3 rows (aproximadamente)
REPLACE INTO `facturas` (`nro_factura`, `descripcion`, `fecha_venta`, `observacion`, `cliente_id`) VALUES
	(1, 'Compra de tecnología para oficinas', '2025-08-20', '', 2),
	(2, 'Compra electrodomésticos', '2025-08-20', '', 1),
	(3, 'Implementación almacenamiento', '2025-08-20', 'Paga en 30 días', 2);

-- Volcando estructura para tabla comercial.productos
CREATE TABLE IF NOT EXISTS `productos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL,
  `disponible` bit(1) NOT NULL,
  `existencia` int(11) NOT NULL,
  `ultimo_ingreso` date NOT NULL,
  `precio` double NOT NULL,
  `imagen` varchar(255) NOT NULL DEFAULT '',
  `categoria_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2fwq10nwymfv7fumctxt9vpgb` (`categoria_id`),
  CONSTRAINT `FK2fwq10nwymfv7fumctxt9vpgb` FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla comercial.productos: ~23 rows (aproximadamente)
REPLACE INTO `productos` (`id`, `descripcion`, `disponible`, `existencia`, `ultimo_ingreso`, `precio`, `imagen`, `categoria_id`) VALUES
	(1, 'Procesador Intel core i7 10ma. Gen', b'0', 25, '2022-05-10', 1200000, '', 10564),
	(2, 'Monitor HP N246v', b'1', 16, '2023-02-26', 458000, '', 10565),
	(3, 'Teléfono IP GrandStream GXP1200', b'1', 5, '2023-08-30', 725000, '', 10566),
	(4, 'Disco duro Ext. 2Tb USB 3.0', b'1', 31, '2022-05-10', 683600, '', 10567),
	(5, 'Memoria USB 32Gb 3.0 Kingston', b'1', 82, '2022-05-10', 195000, '', 10567),
	(6, 'Diadema Logitech HD34F5', b'1', 9, '2022-05-10', 114000, '', 10568),
	(7, 'Pantalla LCD Panasonic 32Pulg. TC-1332', b'1', 13, '2023-02-26', 450000, '', 10565),
	(8, 'Portátil ASUS Pro Z11', b'1', 3, '2023-01-28', 7200000, '', 10569),
	(9, 'Mouse inalámbrico Logitech M170', b'1', 32, '2022-05-10', 37500, '', 10568),
	(10, 'Multipuerto USB 3.0 Targus 4P', b'1', 15, '2022-05-10', 67000, '', 10573),
	(11, 'Computador Portatil Lenovo X220', b'1', 2, '2022-10-15', 1250000, '', 10569),
	(12, 'Teléfono Smartphone Xiaomi RedMi 10 Pro', b'1', 15, '2022-08-14', 1820000, '', 10566),
	(13, 'iPhone 12 Pro Max de 128GB', b'0', 5, '2022-11-05', 6007999, '', 10566),
	(14, 'Portátil HP Elitebook 830 G5', b'1', 10, '2022-04-15', 3450000, '', 10569),
	(16, 'Portátil HP Probook', b'1', 5, '2023-02-15', 6800000, '', 10569),
	(18, 'Mouse inalámbrico Genius T30', b'1', 48, '2023-03-03', 37900, '', 10568),
	(20, 'Teléfono Smartphone Samsung S20', b'1', 7, '2022-11-19', 2056000, '', 10566),
	(22, 'Monitor Sansung S23 23"', b'1', 9, '2022-11-10', 780000, '', 10565),
	(23, 'Morral portátil Toto TG2', b'1', 12, '2023-02-25', 265000, '', 10573),
	(24, 'Hub Lenovo USB-C 7-in-1', b'0', 10, '2023-01-20', 289900, '', 10573),
	(25, 'Adaptador Convertidor Display Port macho a HDMI', b'1', 8, '2023-02-05', 19900, '', 10573),
	(26, 'Adaptador Convertidor USB Tipo C a Ethernet Rj45 Red Gigabit Lan', b'1', 14, '2023-02-05', 35900, '', 10573),
	(27, 'Audifonos Sony sencillos', b'1', 50, '2023-05-14', 780000, '', 10573);

-- Volcando estructura para tabla comercial.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK3mn3mw3d0cll7s45kfxtokq1x` (`usuario_id`,`nombre`),
  CONSTRAINT `FK2xjxpywohsns7mai6w193l8vn` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla comercial.roles: ~3 rows (aproximadamente)
REPLACE INTO `roles` (`id`, `nombre`, `usuario_id`) VALUES
	(101, 'ROLE_USER', 1001),
	(103, 'ROLE_ADMIN', 1002),
	(102, 'ROLE_USER', 1002);

-- Volcando estructura para tabla comercial.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `clave` varchar(128) DEFAULT NULL,
  `nombre` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKio49vjba68pmbgpy9vtw8vm81` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla comercial.usuarios: ~3 rows (aproximadamente)
REPLACE INTO `usuarios` (`id`, `activo`, `clave`, `nombre`) VALUES
	(1001, b'1', '$2a$10$57hVdTSn.vOPowlRbH1uB.xjOEh/jdKYgqaWQHODWN9e.SHicwKZG', 'juan'),
	(1002, b'1', '$2a$10$S6okx/Ux/sUPsovD5hVPGODkEs0fxDJtp64VdCQVHHSbYuovYbg4S', 'admon'),
	(1003, b'1', '$2a$10$ffGHZ.b/4tyeU2QgFlTRGOqqRrt66BynP3ek14XFgy0KebPdSQ9hG', 'pepe');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
