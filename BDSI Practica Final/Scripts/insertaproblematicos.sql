--No se puede meter un DNI que ya existe

INSERT INTO usuarios
       VALUES   ('01',    'Ignacio2 Macias jareño',   'Plaza de Cuzco', 'ignacio2@hotmail.com', 'nacho2');

--No se puede meter un Cod que ya existe

INSERT INTO Medidores VALUES
  ('001', 'Modelo 001bis','Hospital de Madrid');

--No se puede insertar en Vigila si no existe el DNI

INSERT INTO Vigila VALUES
('07','02');

--No se puede tener que un paciente se vigilado por mas de 1 dector

INSERT INTO Vigila VALUES
('01','01');


--El resto de problemas que pueden generar los inserts son similares a estos
--Que se introduzca unos valores en los cuales se repite la calve principal
--Que se introduzca unos valores en los cuales no se respetan las integridades referenciales