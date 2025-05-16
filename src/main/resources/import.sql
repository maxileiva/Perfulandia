INSERT INTO Rol (id_rol, rol, descripcion_rol) VALUES ('1', 'Administrador del Sistema', 'Usuarios con privilegios completos');
INSERT INTO Rol (id_rol, rol, descripcion_rol) VALUES ('2', 'Gerente de Sucursal', 'Usuarios con privilegios de gerente');
INSERT INTO Rol (id_rol, rol, descripcion_rol) VALUES ('3', 'Empleado de Ventas', 'Usuarios con privilegios de vendedor');
INSERT INTO Rol (id_rol, rol, descripcion_rol) VALUES ('4', 'Logística', 'Usuarios con privilegios de encargados de logística');
INSERT INTO Rol (id_rol, rol, descripcion_rol) VALUES ('5', 'Clientes', 'Usuario/Comprador');


INSERT INTO Stock (descripcion, concentracion, fecha_lanzamiento, Cantidad, valor) VALUES ('Armani Stronger With You', 'EDP', '2012-03-01', 20, 98990);
INSERT INTO Stock (descripcion, concentracion, fecha_lanzamiento, Cantidad, valor) VALUES ('Jean Paul Glautier Scandal', 'EDT', '2010-03-01', 10, 124990);
INSERT INTO Stock (descripcion, concentracion, fecha_lanzamiento, Cantidad, valor) VALUES ('Hallowen Man X', 'EDP', '2018-03-01', 20, 32990);


INSERT INTO Cliente (rut, nombre, apellido, telefono, correo, direccion, rol) VALUES ('123004050', 'Juan', 'Pérez', '928374976', 'juanperez@gmail.com', 'Av. Siempre Viva 123', '1');
INSERT INTO Cliente (rut, nombre, apellido, telefono, correo, direccion, rol) VALUES ('184637791', 'Pedro', 'Gonzales', '928954933', 'pgonzales1@gmail.com', 'Los Olivos 12', '2');
INSERT INTO Cliente (rut, nombre, apellido, telefono, correo, direccion, rol) VALUES ('038946442', 'Edson', 'Gomez', '939465571', 'edegomez@gmail.com', 'Psherman 73', '3');
INSERT INTO Cliente (rut, nombre, apellido, telefono, correo, direccion, rol) VALUES ('903674443', 'Pablo', 'Eduardo', '918453332', 'pablitoeduardito@gmail.com', 'El Bosque de Santiago 419', '4');
INSERT INTO Cliente (rut, nombre, apellido, telefono, correo, direccion, rol) VALUES ('143214554', 'Yonattan', 'Frei', '965657294', 'yonnatumaldy@gmail.com', 'Tte. Juan Colipi 649', '5');

