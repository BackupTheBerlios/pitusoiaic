-- DISPARADOR PARA COMPROBAR LAS INSERCIONES Y MODIFICACIONES SOBRE LA TABLA 
-- COMIDAS
CREATE OR REPLACE TRIGGER insertadatosalimentos
    BEFORE INSERT ON comidas
    FOR EACH ROW
BEGIN
  IF (:new.Azucares < 0) THEN
      RAISE_APPLICATION_ERROR(-20000, 'No se admiten valores de azucares 
negativos');
  ELSIF (:new.Grasas < 0) THEN
  	  RAISE_APPLICATION_ERROR(-20000, 'No se admiten valores de grasas 
negativos');
  ELSIF (:new.Proteinas < 0) THEN
  	  RAISE_APPLICATION_ERROR(-20000, 'No se admiten valores de proteinas 
negativos');
  ELSIF ((:new.Grasas + :new.Azucares + :new.Proteinas) < 0 ) THEN
  	  RAISE_APPLICATION_ERROR(-20000, 'La suma de Grasas, Azucares y 
Proteinas no puede exceder el 100 %');
  END IF;
END insertadatosalimentos;
/

commit;

CREATE OR REPLACE TRIGGER actualizadatosalimentos
    BEFORE INSERT ON comidas
    FOR EACH ROW
BEGIN
  IF (:new.Azucares < 0) THEN
      RAISE_APPLICATION_ERROR(-20000, 'No se admiten valores de azucares 
negativos');
  ELSIF (:new.Grasas < 0) THEN
  	  RAISE_APPLICATION_ERROR(-20000, 'No se admiten valores de grasas 
negativos');
  ELSIF (:new.Proteinas < 0) THEN
  	  RAISE_APPLICATION_ERROR(-20000, 'No se admiten valores de proteinas 
negativos');
  ELSIF ((:new.Grasas + :new.Azucares + :new.Proteinas) < 0 ) THEN
  	  RAISE_APPLICATION_ERROR(-20000, 'La suma de Grasas, Azucares y 
Proteinas no puede exceder el 100 %');
  END IF;
END actualizadatosalimentos;
/
commit;

-- DISPARADOR PARA COMPROBAR LAS INSERCIONES Y MODIFICACIONES SOBRE LA TABLA 
-- INYECTA
CREATE OR REPLACE TRIGGER insertadatosinyeccion
	BEFORE INSERT ON inyecta
	FOR EACH ROW
BEGIN
	IF (:new.Unidades < 0) THEN
		RAISE_APPLICATION_ERROR(-20000, 'No se admiten valores de unidades de 
inyeccion negativos');
	ELSIF (:new.Unidades > 60 ) THEN
		RAISE_APPLICATION_ERROR(-20000, 'La capacidad máxima de inyección de las 
agujas es de 60 unidades');
	ELSIF (:new.Unidades > 35) THEN
		DBMS_OUTPUT.PUT_LINE('Ha introducido una inyeccion de ' || :new.Unidades 
|| ' unidades, es una cantidad
		bastante elevada, deberia acudir a su medico');
	END IF;
END insertadatosinyeccion;
/
commit;

CREATE OR REPLACE TRIGGER actualizadatosinyeccion
	BEFORE UPDATE ON inyecta
	FOR EACH ROW
BEGIN
	IF (:new.Unidades < 0) THEN
		RAISE_APPLICATION_ERROR(-20000, 'No se admiten valores de unidades de 
inyeccion negativos');
	ELSIF (:new.Unidades > 60 ) THEN
		RAISE_APPLICATION_ERROR(-20000, 'La capacidad máxima de inyección de las 
agujas es de 60 unidades');
	ELSIF (:new.Unidades > 35) THEN
		DBMS_OUTPUT.PUT_LINE('Ha introducido una inyeccion de ' || :new.Unidades 
|| ' unidades, es una cantidad
		bastante elevada, deberia acudir a su medico');
	END IF;
END actualizadatosinyeccion;
/
commit;

-- DISPARADOR PARA COMPROBAR LAS INSERCIONES Y MODIFICACIONES SOBRE LA TABLA 
-- MIDE
CREATE OR REPLACE TRIGGER insertadatosmedicion
	BEFORE INSERT ON mide
	FOR EACH ROW
BEGIN
	IF (:new.Medicion < 0 ) THEN
		RAISE_APPLICATION_ERROR(-20000, 'No se admiten valores de glucosa 
negativos');
	ELSIF (:new.Medicion < 60) THEN
		DBMS_OUTPUT.PUT_LINE('Una medicion de ' || :new.Medicion || ' corresponde 
con una hipoglucemia muy fuerte,
		si son continuadas deberia acudir a su medico.');
	ELSIF (:new.Medicion > 330) THEN
		DBMS_OUTPUT.PUT_LINE('Una medicion de ' || :new.Medicion || ' corresponde 
con un nivel de glucosa demasiado elevado,
		si son continuadas deberia acudir a su medico.');
	END IF;
END insertadatosmedicion;
/
commit;


CREATE OR REPLACE TRIGGER actualizadatosmedicion
	BEFORE UPDATE ON mide
	FOR EACH ROW
BEGIN
	IF (:new.Medicion < 0 ) THEN
		RAISE_APPLICATION_ERROR(-20000, 'No se admiten valores de glucosa 
negativos');
	ELSIF (:new.Medicion < 60) THEN
		DBMS_OUTPUT.PUT_LINE('Una medicion de ' || :new.Medicion || ' corresponde 
con una hipoglucemia muy fuerte,
		si son continuadas deberia acudir a su medico.');
	ELSIF (:new.Medicion > 330) THEN
		DBMS_OUTPUT.PUT_LINE('Una medicion de ' || :new.Medicion || ' corresponde 
con un nivel de glucosa demasiado elevado,
		si son continuadas deberia acudir a su medico.');
	END IF;
END actualizadatosmedicion;
/
commit;






-- DISPARADOR PARA COMPROBAR LAS INSERCIONES Y MODIFICACIONES SOBRE LA TABLA 
-- COME
CREATE OR REPLACE TRIGGER insertadatoscomidas
	BEFORE INSERT ON Come
	FOR EACH ROW
BEGIN
	IF (:new.Duracion < 0 ) THEN
		RAISE_APPLICATION_ERROR(-20000, 'No se admiten valores de duracion 
negativos');
	ELSIF (:new.Duracion > 90) THEN
		DBMS_OUTPUT.PUT_LINE('Ha introducido una duración de la comida de ' || 
:new.Duracion ||
		' asegurese de no equivocarse');
	END IF;

	IF (:new.Cantidad < 0) THEN
		RAISE_APPLICATION_ERROR(-20000, 'No se admiten valores de cantidad 
negativos');
	END IF;
END insertadatoscomidas;
/
commit;



CREATE OR REPLACE TRIGGER actualizadatoscomidas
	BEFORE UPDATE ON Come
	FOR EACH ROW
BEGIN
	IF (:new.Duracion < 0 ) THEN
		RAISE_APPLICATION_ERROR(-20000, 'No se admiten valores de duracion 
negativos');
	ELSIF (:new.Duracion > 90) THEN
		DBMS_OUTPUT.PUT_LINE('Ha introducido una duración de la comida de ' || 
:new.Duracion ||
		' asegurese de no equivocarse');
	END IF;

	IF (:new.Cantidad < 0) THEN
		RAISE_APPLICATION_ERROR(-20000, 'No se admiten valores de cantidad 
negativos');
	END IF;
END actualizadatoscomidas;
/
commit;