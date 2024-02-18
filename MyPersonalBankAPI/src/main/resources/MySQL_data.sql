--
-- Database: `personalapidb`
--

--
-- Dumping data for table `cliente`
--
INSERT INTO `cliente` (`dtype`, `id`, `activo`, `alta`, `direccion`, `email`, `moroso`, `nombre`, `cif`, `unidades_de_negocio`, `dni`) VALUES
('Personal', 1, b'1', '2023-11-07', 'Calle JJ 1', 'jj@j.com', b'0', 'Juan Juanez', NULL, NULL, '12345678J'),
('Personal', 2, b'1', '2023-11-07', 'Calle LP 2', 'lp@l.com', b'0', 'Luisa Perez', NULL, NULL, '12345678L'),
('Empresa', 3, b'1', '2023-11-07', 'Calle SI 3', 'si@s.com', b'0', 'Servicios Informatico SL', 'J12345678', NULL, NULL);

--
-- Dumping data for table `cuenta`
--
INSERT INTO `cuenta` (`dtype`, `id`, `comision`, `fecha_creacion`, `interes`, `saldo`, `cliente_id`) VALUES
('Ahorro', 1, 0.2, '2023-11-07', 1.1, 100, 1),
('Corriente', 2, 0.2, '2023-11-07', 0.5, 200, 3),
('Ahorro', 3, 0.2, '2023-11-07', 1.1, 300, 2),
('Ahorro', 4, 0.2, '2023-11-07', 1.1, 300, 1);

--
-- Dumping data for table `prestamo`
--
INSERT INTO `prestamo` (`id`, `anios`, `fecha_concesion`, `interes`, `interes_mora`, `liquidado`, `mensualidad`, `monto`, `moroso`, `saldo`, `cliente_id`) VALUES
(1, 2, '2023-11-07', 4, 2, b'0', NULL, 1000, b'0', 1000, 1),
(2, 25, '2023-11-07', 4, 2, b'0', NULL, 100000, b'0', 100000, 3);

-- NOTAS:
-- + Si no le indicamos el id, lo genera automáticamente la BB.DD. MySQL en función del @Id de la entidad.
-- + Hibernate transforma los nombres de entidades y propiedades a "snake_case" SIEMPRE.
