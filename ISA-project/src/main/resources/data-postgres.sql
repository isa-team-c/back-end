INSERT INTO public.role(name)
VALUES ('ROLE_REGULAR_USER');
INSERT INTO public.role(name)
VALUES ('ROLE_COMPANY_ADMINISTRATOR');

INSERT INTO public.users(city, company_information, country, email, is_verified, last_password_reset_date, name, password, phone_number, profession, surname, role_id)
	VALUES ('Beograd', 'preduzece1', 'Srbija', 'projekatisa2023@gmail.com', true, null, 'Petar', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi', '065728314', 'softverski inzenjer', 'Knezevic',1);
INSERT INTO public.users(city, company_information, country, email, is_verified, last_password_reset_date, name, password, phone_number, profession, surname, role_id)
	VALUES ('Cacak', 'preduzece1', 'Srbija', 'admin@gmail.com', true, null, 'Isidora', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi', '065728314', 'softverski inzenjer', 'Milosevic',2);

INSERT INTO public.company(address, average_rating, description, name)
	VALUES ('Vuka Karadzica', 5, 'odlicno', 'company1');
INSERT INTO public.company(address, average_rating, description, name)
	VALUES ('Cirpanova', 5, 'odlicnooo', 'company2');

INSERT INTO public.equipment(description, name, quantity, type)
	VALUES ('medicinska', 'Stethoscope', 5, 'Medical Device');
INSERT INTO public.equipment (description,name,quantity,type) 
	VALUES ('raÄunarska oprema','Laptop',10,'IT Equipment');
INSERT INTO public.equipment (description,name,quantity,type) 
	VALUES ('laboratorijska oprema','Microscope',3,'Laboratory Equipment');

INSERT INTO public.company_equipment(
	company_id, equipment_id)
	VALUES (1, 1);
INSERT INTO public.company_equipment(
	company_id, equipment_id)
	VALUES (1, 2);
INSERT INTO public.company_equipment(
	company_id, equipment_id)
	VALUES (2, 3);

INSERT INTO public.company_administrator(
	user_id, company_id)
	VALUES (2, 1);

INSERT INTO public.appointments(
	id, duration, is_free, start_date, administrator_id)
	VALUES (1 ,5, false, '2023-12-31T12:00:00', 2);

INSERT INTO public.appointments(
	id, duration, is_free, start_date, administrator_id)
	VALUES (2, 30, true, '2023-12-17 22:56:45.647+01', 2);

INSERT INTO public.company_appointment(
	company_id, appointment_id)
	VALUES (1, 1);
	
INSERT INTO public.company_appointment(
	company_id, appointment_id)
	VALUES (1, 2);

INSERT INTO public.reservation(
	id, status, appointment_id)
	VALUES (1, 1, 1);

INSERT INTO public.reservation_equipment(
	reservation_id, equipment_id)
	VALUES (1, 1);

INSERT INTO public.reservation_user(
	user_id, reservation_id)
	VALUES (1, 1);