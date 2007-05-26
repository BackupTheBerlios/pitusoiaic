

-- falla porque no existe el DNI y no se cumple la reestriccion de integridad
UPDATE Vigila
SET Num_colegiado='05'
WHERE DNI='01';'

--falla porque no existe el tipo de diabetes y no se cumple la reestriccion de integridad
UPDATE Padece
SET Tipo='Tipo ERROE'
WHERE DNI='01';

--falla por el formato de fecha 
UPDATE Diagnostico
SET Fecha='30/2/07'
WHERE DNI='01' AND Fecha='10/01/07';

--falla porque no coinciden los tipos 
UPDATE Inyecta
SET unidades='01,01'
WHERE DNI='03';