


-- numero de pacientes de cada medico 2
-- Epidosdios de hipoglucemia 2
-- Los valores max y min de las medidas 2


-- Usuarios con telefono 3
drop view Tabla_usuarios;
Create view Tabla_usuarios(DNI,Nom_apell,telefono,Tipo) 
as select usuarios.DNI,usuarios.Nom_apell,telefono,Padece.tipo from usuarios,telefonos,Padece 
where telefonos.Domicilio=usuarios.Domicilio and Padece.DNI=Usuarios.DNI;
commit;

-- Diagnoticos
drop view Diagnosticos;
Create view Diagnosticos(Num_colegiado,Nombre_apellidos,Fecha,Descripcion) as 
select Vigila.Num_colegiado,Usuarios.Nom_apell,Diagnostico.Fecha,Diagnostico.Descripcion
from Vigila,Diagnostico,Usuarios where Vigila.DNI=Diagnostico.DNI and Vigila.DNI=Usuarios.DNI; 
commit;



-- DNi con las comidas 3
drop view Tabla_comidas;
Create view Tabla_comidas(DNI,Nombre) as select Distinct(usuarios.DNI),Comidas.Nombre from Comidas,usuarios,Come
where Come.DNI=USuarios.DNI and Come.Comida=Comidas.Nombre;
commit;

-- medico que vigila al paciente 4
drop view Vigilados;
Create view Vigilados(Nom_apell_paciente,Nom_apell_medico,telefono) as 
select Usuarios.Nom_apell,Medicos.Nom_apell,telefonos.telefono from usuarios,Medicos,Vigila,Telefonos              
where Usuarios.DNI=Vigila.DNI and Vigila.Num_colegiado=medicos.num_colegiado and Usuarios.domicilio=telefonos.domicilio;
commit;


-- Numero de pacientes y medicos 2 2
drop view numero_usuarios;
create view numero_usuarios (numero_de_pacientes) as select count(DNI) from usuarios;
commit;

drop view numero_medicos;
create view numero_medicos (numero_de_medicos) as select count(Num_colegiado) from Medicos;
commit;

-- Insulinas
drop view tipo_insulina;
Create view tipo_insulina(Nom_apell,Nombre_insulina,numero_de_veces)
as select Usuarios.Nom_apell,Insulina.Tipo,COUNT(Inyecta.DNI) from usuarios,Insulina,Inyecta
where Usuarios.DNI=Inyecta.DNI and insulina.Cod=Inyecta.insulina
GROUP BY Usuarios.Nom_apell,Insulina.Tipo;

-- Come arroz
drop view come_arroz;
Create view come_arroz(Nom_apell,DNI) as
select Distinct Nom_apell,Come.DNI from Usuarios,Come
where Usuarios.DNI=Come.DNI and come.DNI in
(
select DNI from Come
where come.comida='Arroz'
);
commit;


-- pacientes solos
drop view pacientes_solos;
Create view pacientes_solos(Nom_apell) as
select U1.Nom_apell from Usuarios U1 where NOT Exists
(
select * from Usuarios U2,Vigila
where U1.DNI=U2.DNI and Vigila.DNI=U2.DNI
);


-- numero de pacientes
drop view numero_pacientes;
create view numero_pacientes(Numero_colegiado,Nombre_y_apellidos,pacientes) as
select Medicos.Num_colegiado,Medicos.Nom_apell, COUNT(Vigila.DNI) from Medicos,Vigila
where Vigila.Num_colegiado=Medicos.Num_colegiado
GROUP BY Medicos.Num_colegiado,Medicos.Nom_apell;
commit;

-- episodios hipoglucemia
drop view episodios_hipoglucemia;
Create view episodios_hipoglucemia(Nom_apell,Fecha)
as select Nom_apell,M1.Momento from Usuarios,Mide  M1 where
EXISTS
(
select * from Mide M2 where M2.DNI=Usuarios.DNI and M2.Medicion<=60 and M2.Medicion>=0
and M1.DNI=M2.DNI and M1.Momento=M2.Momento  
);

select * from episodios_hipoglucemia;


-- pacientes max y min
drop view pacientes_maxmin;
Create view pacientes_maxmin(Nom_apell,Maximo,Minimo)
as select Nom_apell, max(Medicion),min(Medicion) from Mide,Usuarios
where Usuarios.DNI=Mide.DNI
GROUP BY Nom_apell;