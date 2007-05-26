
-- 2 updates normales
UPDATE Usuarios
sET contrasena='nueva'
WHERE DNI='01';
commit;

UPDATE Medicos
sET Hospital='Madrid, la Moncloa'
WHERE Num_colegiado='01';
commit;

--Se cambia el medico que vigila y se cumple la reestriccion de integridad
UPDATE Vigila
SET Num_colegiado='03'
WHERE DNI='01';
commit;

--Se cambia el tipo de UPDATE Vigila
UPDATE Padece
SET Tipo='Tipo B'
WHERE DNI='01';
commit;

--Se cambia el tiempo de una medida
UPDATE Mide
SET Momento='01/01/07 01:01:01'
WHERE DNI='01' AND Momento='01/04/07 21:03:00,000000';
commit;