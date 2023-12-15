INSERT INTO public.role(name)
VALUES ('ROLE_REGULAR_USER');
INSERT INTO public.role(name)
VALUES ('ROLE_COMPANY_ADMINISTRATOR');

INSERT INTO public.users(city, company_information, country, email, is_verified, last_password_reset_date, name, password, phone_number, profession, surname, role_id)
	VALUES ('Beograd', 'preduzece1', 'Srbija', 'petar@gmail.com', true, null, 'Petar', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi', '065728314', 'softverski inzenjer', 'Knezevic',1);
INSERT INTO public.users(city, company_information, country, email, is_verified, last_password_reset_date, name, password, phone_number, profession, surname, role_id)
	VALUES ('Beograd', 'preduzece2', 'Srbija', 'nikola@gmail.com', true, null, 'Nikola', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi', '0611111111', 'administrator', 'Nikolic',2);

INSERT INTO public.regular_user(
	user_id, penalties)
	VALUES (1, 0);
	
INSERT INTO public.company(
	id, address, average_rating, description, name)
	VALUES (1, 'a1', '2', 'd1', 'c1');
	
INSERT INTO public.company_administrator(
	user_id, company_id)
	VALUES (2, 1);

INSERT INTO public.appointments(
	id, duration, is_free, start_date, administrator_id)
	VALUES (1, 30, true, '2023-12-17 22:56:45.647+01', 2);
	
INSERT INTO public.company_appointment(
	company_id, appointment_id)
	VALUES (1, 1);
	
INSERT INTO public.equipment(
	id, description, name, quantity, type)
	VALUES (1, 'e1', 'e1', 100, 't1');
	
INSERT INTO public.company_equipment(
	company_id, equipment_id)
	VALUES (1, 1);