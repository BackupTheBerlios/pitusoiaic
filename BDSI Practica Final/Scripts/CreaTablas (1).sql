CREATE TABLE Usuarios (
	DNI varchar (9) not null,
	Nom_apell varchar (60) not null,
	Domicilio varchar (60) not null,
	Email varchar (45) not null,
	Telefono_movil varchar (9),
	Password varchar (15) not null,
	primary key(DNI)
);


CREATE TABLE Medicos (
	Num_colegiado varchar (15) not null,
	Nom_apell varchar (60) not null,
	Hospital varchar (30) not null,
	primary key (Num_colegiado)
);

CREATE TABLE Medidores (
	Cod varchar (30) not null,
	Nombre varchar (30) not null,
	Fabricante varchar (30) not null,
	primary key (Cod)
);


CREATE TABLE Insulina (
	Cod varchar (30) not null,
	Tipo varchar (20) not null,
	Fabricante varchar (30) not null,
	primary key (Cod)
);

CREATE TABLE Diabetes (
	Tipo varchar (15) not null,
	primary key (Tipo)
);

CREATE TABLE Comidas (
	Nombre varchar (45) not null,
	Azucares Integer not null,
	Grasas Integer not null,
	Proteinas Integer not null,
	primary key (Nombre)
);

CREATE TABLE Vigila (
	DNI varchar (9) not null,
	Num_colegiado varchar (15) not null,
	primary key (DNI),
	foreign key(DNI) references Usuarios(DNI),
	foreign key(Num_colegiado) references Medicos(Num_colegiado)
);

CREATE TABLE Padece (
	DNI varchar (9) not null,
	Tipo varchar (15) not null,
	primary key (DNI),
	foreign key(DNI) references Usuarios(DNI),
	foreign key(Tipo) references Diabetes (Tipo)
);

CREATE TABLE Diagnostico (
	DNI varchar (9) not null,
	Fecha Date not null,
	Descripcion varchar(500) not null,
	Tratamiento varchar(500),
	primary key (DNI,Fecha),
	foreign key(DNI) references Usuarios(DNI)
);

CREATE TABLE Come (
	DNI varchar (9) not null,
	Comida varchar (45) not null,
	Momento Timestamp not null,
	Duracion Integer,
	Cantidad Integer not null,
	primary key (DNI,Comida,Momento),
	foreign key(DNI) references Usuarios(DNI),
	foreign key(Comida) references Comidas(Nombre)
);

CREATE TABLE Inyecta (
	DNI varchar (9) not null,
	Insulina varchar (30) not null,
	Momento Timestamp not null,
	Unidades Integer not null,
	primary key (DNI,Momento),
	foreign key(DNI) references Usuarios(DNI),
	foreign key(Insulina) references Insulina(Cod)
);

CREATE TABLE Tiene_Medidor (
	DNI varchar (9) not null,
	Medidor varchar (30) not null,
	primary key (DNI),
	foreign key(DNI) references Usuarios(DNI),
	foreign key(Medidor) references Medidores(Cod)
);

CREATE TABLE Mide (
	DNI varchar (9) not null,
	Momento Timestamp not null,
	Medicion Integer not null,
	primary key (DNI,Momento),
	foreign key(DNI) references Usuarios(DNI)
);