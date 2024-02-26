--
-- Database: 'personalapidb'
--

--
-- Dumping data for table 'cliente'
--
--INSERT INTO cliente (dtype, id, activo, alta, direccion, email, moroso, nombre, cif, unidades_negocio, dni) VALUES
--('Personal', 1, TRUE, '2023-11-07', 'Calle JJ 1', 'jj@j.com', FALSE, 'Juan Juanez', NULL, NULL, '12345678J'),
--('Personal', 2, TRUE, '2023-11-07', 'Calle LP 2', 'lp@l.com', FALSE, 'Luisa Perez', NULL, NULL, '12345678L'),
--('Empresa', 3, TRUE, '2023-11-07', 'Calle SI 3', 'si@s.com', FALSE, 'Servicios Informatico SL', 'J12345678', NULL, NULL);
INSERT INTO cliente (dtype, activo, alta, direccion, email, moroso, nombre, cif, unidades_negocio, dni) VALUES
('Personal', TRUE, '2023-11-07', 'Calle JJ 1', 'jj@j.com', FALSE, 'Juan Juanez', NULL, NULL, '12345678J'),
('Personal', TRUE, '2023-11-07', 'Calle LP 2', 'lp@l.com', FALSE, 'Luisa Perez', NULL, NULL, '12345678L'),
('Empresa', TRUE, '2023-11-07', 'Calle SI 3', 'si@s.com', FALSE, 'Servicios Informatico SL', 'J12345678', NULL, NULL);
--INSERT INTO cliente (dtype, activo, alta, direccion, email, moroso, nombre) VALUES
--('Personal', TRUE, '2023-11-07', 'Calle JJ 1', 'jj@j.com', FALSE, 'Juan Juanez'),
--('Personal', TRUE, '2023-11-07', 'Calle LP 2', 'lp@l.com', FALSE, 'Luisa Perez'),
--('Empresa', TRUE, '2023-11-07', 'Calle SI 3', 'si@s.com', FALSE, 'Servicios Informatico SL');


--
-- Dumping data for table 'cuenta'
--
--INSERT INTO cuenta (dtype, id, comision, fecha_creacion, interes, saldo, cliente_id) VALUES
--('Ahorro', 1, 0.2, '2023-11-07', 1.1, 100, 1),
--('Corriente', 2, 0.2, '2023-11-07', 0.5, 200, 3),
--('Ahorro', 3, 0.2, '2023-11-07', 1.1, 300, 2),
--('Ahorro', 4, 0.2, '2023-11-07', 1.1, 300, 1);
--INSERT INTO cuenta (id, comision, fecha_creacion, interes, saldo, cliente_id) VALUES
--(1, 0.2, '2023-11-07', 1.1, 100, 1),
--(2, 0.2, '2023-11-07', 0.5, 200, 3),
--(3, 0.2, '2023-11-07', 1.1, 300, 2),
--(4, 0.2, '2023-11-07', 1.1, 300, 1);
--INSERT INTO cuenta (id, comision, fecha_creacion, interes, saldo) VALUES
--(1, 0.2, '2023-11-07', 1.1, 100),
--(2, 0.2, '2023-11-07', 0.5, 200),
--(3, 0.2, '2023-11-07', 1.1, 300),
--(4, 0.2, '2023-11-07', 1.1, 300);
--INSERT INTO cuenta (comision, fecha_creacion, interes, saldo) VALUES
--(0.2, '2023-11-07', 1.1, 100),
--(0.2, '2023-11-07', 0.5, 200),
--(0.2, '2023-11-07', 1.1, 300),
--(0.2, '2023-11-07', 1.1, 300);

--
-- Dumping data for table 'prestamo'
--
INSERT INTO prestamo (id, anios, fecha_concesion, interes, interes_mora, liquidado, mensualidad, monto, moroso, saldo, cliente_id) VALUES
(1, 2, '2023-11-07', 4, 2, TRUE, NULL, 1000, FALSE, 1000, 1),
(2, 24, '2024-02-21', 3, 1, FALSE, NULL, 1000, FALSE, 1000, 1),
(3, 3, '2023-11-07', 4, 2, FALSE, NULL, 2000, FALSE, 2000, 2),
(4, 3, '2024-02-21', 3, 1, FALSE, NULL, 3000, FALSE, 2000, 2),
(5, 50, '2024-02-26', 2, 1, FALSE, NULL, 50000, FALSE, 50000, 2),
(6, 20, '2023-11-07', 4, 2, FALSE, NULL, 300000, FALSE, 300000, 3),
(7, 40, '2024-02-21', 4, 2, FALSE, NULL, 400000, TRUE, 400000, 3);


-- NOTAS:
-- + Si no le indicamos el id, lo genera automáticamente la BB.DD. H2 en función del @Id de la entidad.
-- + Hibernate transforma los nombres de entidades y propiedades a "snake_case" SIEMPRE.
