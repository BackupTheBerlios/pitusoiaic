-- Creacion de indices por DNI en come, inyecta, mide
-- Indices en usuario domicilio, para el telefono
-- Indexar vigila.medico multiples consultas consulta al medico que vigila aun paciente.

DROP INDEX DNI_Come;
CREATE INDEX DNI_Come ON Come (DNI);
COMMIT;

DROP INDEX DNI_Inyecta;
CREATE INDEX DNI_Come ON Mide (DNI);
COMMIT;

DROP INDEX DNI_Mide;
CREATE INDEX DNI_Mide ON Mide (DNI);
COMMIT;

DROP INDEX DOMIC_Usuarios;
CREATE INDEX DOMIC_Usuarios ON Usuarios(Domicilio);
COMMIT;

DROP INDEX MEDICO_Vigila;
CREATE INDEX MEDICO_Vigila ON Vigila (Num_colegiado);
COMMIT;