--- precond: las tablas deben existir 

ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MM-YYYY HH24:MI';

delete from telefonos;
INSERT INTO telefonos VALUES  ('Plaza de España 1','310234345');
INSERT INTO telefonos VALUES  ('Plaza de España 6','123456778');   
INSERT INTO telefonos VALUES  ('Avenida Complutense 1','555222333');
INSERT INTO telefonos VALUES  ('Plaza Mayor 8','912343431');
INSERT INTO telefonos VALUES  ('Complutense 8','122352878');
commit;


delete from usuarios;
INSERT INTO usuarios VALUES   ('01',    'Ignacio Macias jareño',   'Plaza de España 1', 'ignacio@hotmail.com', 'nacho');
INSERT INTO usuarios VALUES   ('02',    'Pedro Marcos Rosas',   'Plaza de España 6', 'pedro@hotmail.com', 'pedro');   
INSERT INTO usuarios VALUES   ('03',    'Alberto Garcia Muñoz',   'Avenida Complutense 1', 'alberto@hotmail.com', 'albert');
INSERT INTO usuarios VALUES   ('04',    'Luis Sanxhez garrido',   'Plaza Mayor 8', 'luis@gmail.com', 'luso');
INSERT INTO usuarios VALUES   ('05',    'Francisco Rosas Pantoja ',   'Complutense 8', 'luisssss@gmail.com', '05');
commit;


delete from Medicos;
INSERT INTO medicos VALUES 
('01',  'Isabel Ramos Perez', 'La Paz');
INSERT INTO medicos VALUES 
('02',  'Ana Sanchez Gonzalez','Alcala de henares');
INSERT INTO medicos VALUES 
('03',  'Alvaro Gutierrez Cañizares',' Gomez Ulla');
commit;


delete from Medidores;
INSERT INTO Medidores VALUES
  ('001', 'Modelo 001','Hospital de La Paz');
INSERT INTO Medidores VALUES
  ('004', 'Modelo 004','Phillips');
INSERT INTO Medidores VALUES
  ('002', 'Modelo 002','Hospital de La Paz');
INSERT INTO Medidores VALUES
  ('011', 'Modelo 011','Sony');
commit;     

delete from Insulina;
INSERT INTO Insulina VALUES 
 ('INS1','Insulina tipo 1','Hospital Gomez Ulla');
INSERT INTO Insulina VALUES 
 ('INS2','Insulina tipo 2','Hospital Gomez Ulla');
INSERT INTO Insulina VALUES 
 ('INS3','Insulina tipo 3','Hospital de Alcala de henares');
commit;

delete from Diabetes;
INSERT INTO Diabetes VALUES
('Tipo A');
INSERT INTO Diabetes VALUES('Tipo B');
INSERT INTO Diabetes VALUES
('Tipo AB');
commit;

delete from Comidas;
INSERT INTO Comidas VALUES
('Arroz',1,2,3);
INSERT INTO Comidas VALUES
('Fruta',6,0,1);
INSERT INTO Comidas VALUES
('Verduras',2,5,2);
INSERT INTO Comidas VALUES ('Carne',4,8,13);
INSERT INTO Comidas VALUES ('Patatas',23,21,13);
INSERT INTO Comidas VALUES ('Salmon',42,8,05);
INSERT INTO Comidas VALUES ('Pasta',0,22,13);
INSERT INTO Comidas VALUES ('Tomates',23,23,08);
INSERT INTO Comidas VALUES ('Platano',41,18,22);
commit;

delete from Vigila;
INSERT INTO Vigila VALUES
('01','02');
INSERT INTO Vigila VALUES
('02','01');
INSERT INTO Vigila VALUES
('03','02');
INSERT INTO Vigila VALUES
('04','03');
commit; 

delete from Padece;
INSERT INTO Padece VALUES
('01','Tipo A');
INSERT INTO Padece VALUES
('02','Tipo B');
INSERT INTO Padece VALUES
('03','Tipo A');
INSERT INTO Padece VALUES
('04','Tipo AB');
Commit;

delete from Diagnostico;
INSERT INTO Diagnostico Values
('01','10-01-2007','El paciente se encuentra bien evoluciona favorablemente','');
INSERT INTO Diagnostico Values
('02','15-02-2007','El paciente responde al tratamiento','');
INSERT INTO Diagnostico Values
('01','13-03-2007','El paciente tiene unos niveles de glucosa elevados','Aumentar dosis de insulina');
INSERT INTO Diagnostico Values
('03','22-04-2007','El paciente tiene unos niveles de glucosa que fluctuan demasiado','Controlar su dieta y aumentar el ejercicio fisico');
commit;

delete from Come;
INSERT INTO Come VALUES
('01','Arroz','20/01/07 20:30',10,42);              
INSERT INTO Come VALUES
('02','Arroz','25/02/07 22:00',14,43);
INSERT INTO Come VALUES
('02','Fruta','29/06/07 15:05',20,42);
INSERT INTO Come VALUES ('01','Patatas','13/02/07 14:56',12,23);
INSERT INTO Come VALUES ('03','Salmon','23/05/07 21:06',13,32);
INSERT INTO Come VALUES ('05','Arroz','23/04/07 15:06',05,44);
INSERT INTO Come VALUES ('02','Pasta','23/02/07 14:23',06,14);
INSERT INTO Come VALUES ('04','Tomates','11/04/07 22:13',09,34);
INSERT INTO Come VALUES ('02','Platano','12/03/07 21:26',11,23);
INSERT INTO Come VALUES ('03','Carne','22/04/07 16:01',13,26);
commit;

delete from Inyecta;
INSERT INTO Inyecta VALUES
('01','INS1','01/04/07 23:18:00',15);
INSERT INTO Inyecta VALUES
('01','INS1','01/03/07 13:18:00',50);
INSERT INTO Inyecta VALUES
('01','INS1','02/04/07 03:18:00',34);
INSERT INTO Inyecta VALUES
('02','INS2','04/05/07 14:33:07',4);
INSERT INTO Inyecta VALUES
('03','INS3','08/04/07 21:03:03',17);
INSERT INTO Inyecta VALUES
('02','INS2','17/02/07 23:43:00',12);
INSERT INTO Inyecta VALUES
('02','INS1','19/12/07 03:23:00',12);
commit;

delete from Tiene_Medidor;
INSERT INTO Tiene_Medidor VALUES
('01','001');
INSERT INTO Tiene_Medidor VALUES
('02','004');
INSERT INTO Tiene_Medidor VALUES
('03','011');
commit;

delete from Mide;
INSERT INTO Mide VALUES
('01','01/04/07 21:03:00',200);
INSERT INTO Mide VALUES
('01','11/03/07 11:53:00',111);
INSERT INTO Mide VALUES
('02','11/03/07 13:43:00',90);
INSERT INTO Mide VALUES
('02','12/03/07 15:43:00',130);
INSERT INTO Mide VALUES
('03','16/02/07 21:03:00',150);
INSERT INTO Mide VALUES
('02','23/02/07 21:03:00',139);
INSERT INTO Mide VALUES
('04','22/04/06 13:23:00',45);
commit;



