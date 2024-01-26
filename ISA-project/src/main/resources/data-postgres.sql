INSERT INTO public.role(name)
    VALUES ('ROLE_REGULAR_USER');
INSERT INTO public.role(name)
    VALUES ('ROLE_COMPANY_ADMIN');
INSERT INTO public.role(name)
	VALUES ('ROLE_ADMIN');
  
INSERT INTO public.users(city, company_information, country, email, is_verified, last_password_reset_date, name, password, phone_number, profession, surname, role_id)
	VALUES ('Beograd', 'preduzece1', 'Srbija', 'miljevictamara@gmail.com', true, null, 'Petar', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi', '065728314', 'softverski inzenjer', 'Knezevic',1);
INSERT INTO public.users(city, company_information, country, email, is_verified, last_password_reset_date, name, password, phone_number, profession, surname, role_id)
	VALUES ('Cacak', 'preduzece1', 'Srbija', 'admin@gmail.com', true, null, 'Isidora', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi', '065728314', 'softverski inzenjer', 'Milosevic',2);
INSERT INTO public.users(city, company_information, country, email, is_verified, last_password_reset_date, name, password, phone_number, profession, surname, role_id)
	VALUES ('Sabac', 'preduzece3', 'Srbija', 'admin1@gmail.com', true, null, 'Natasa', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi', '065728314', 'softverski inzenjer', 'Simic',2);
INSERT INTO public.users(city, company_information, country, email, is_verified, last_password_reset_date, name, password, phone_number, profession, surname, role_id)
    VALUES ('Grad', '/', 'Drzava', 'adminn@gmail.com', true, null, 'Admin', '$2a$10$E17mSVuCMgS9jfc/WZcK8eFrjD/uYgZ1XcAd2T55rwZOKv7ki23tW', '1234567890', 'profesija', 'Adminovic', 3);
INSERT INTO public.users(city, company_information, country, email, is_verified, last_password_reset_date, name, password, phone_number, profession, surname, role_id)
	VALUES ('Subotica', 'preduzece4', 'Srbija', 'andjela1108@gmail.com', true, null, 'Andjela', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi', '065720914', 'softverski inzenjer', 'Knezevic',1);
INSERT INTO public.users(city, company_information, country, email, is_verified, last_password_reset_date, name, password, phone_number, profession, surname, role_id)
	VALUES ('Nis', 'preduzece1', 'Srbija', 'andjela1108+vhb@gmail.com', true, null, 'Vasa', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi', '065727714', 'softverski inzenjer', 'Rajcevic',1);
	
INSERT INTO public.regular_user(
	user_id, penalties, role)
	VALUES (1, 0, 'REGULAR');
INSERT INTO public.regular_user(
	user_id, penalties, role)
	VALUES (5, 0, 'REGULAR');


INSERT INTO public.administrator(
	user_id, logged_in_before)
	VALUES (4, false);


INSERT INTO company (id, name, address, description, average_rating, work_end_time, work_start_time)
    VALUES (1, 'company1', 'Vuka Karadzica', 'odlicno', 5, '17:00:00', '09:00:00');
INSERT INTO company (id, name, address, description, average_rating, work_end_time, work_start_time)
    VALUES (2, 'company2', 'Cirpanova', 'odlicnooo', 5, '19:00:00', '09:00:00');

INSERT INTO public.equipment(description, name, quantity, reserved_quantity, type, price)
VALUES ('medicinska', 'Stethoscope', 5, 0, 'Medical Device', 45);
INSERT INTO public.equipment(description, name, quantity, reserved_quantity, type, price) 
	VALUES ( 'racunarska oprema','Laptop', 10, 0, 'IT Equipment', 35);
INSERT INTO public.equipment(description, name, quantity, reserved_quantity, type, price)
	VALUES ( 'laboratorijska oprema','Microscope', 3, 0,'Laboratory Equipment', 24);

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
	user_id, company_id, logged_in_before)
	VALUES (2, 1, true);
INSERT INTO public.company_administrator(
	user_id, company_id, logged_in_before)
	VALUES (3, 1, false);
INSERT INTO public.company_administrator(
	user_id, company_id, logged_in_before)
	VALUES (6, 1, false);

INSERT INTO public.appointments(
	id, duration, is_free, start_date, administrator_id)
	VALUES (1 ,5, true, '2024-1-27T12:00:00', 2);

INSERT INTO public.appointments(
	id, duration, is_free, start_date, administrator_id)
	VALUES (2, 30, true, '2024-6-11T12:00:00', 2);

INSERT INTO public.appointments(
	id, duration, is_free, start_date, administrator_id)
	VALUES (3, 30, true, '2024-6-11T12:03:00', 6);

INSERT INTO public.appointments(
	id, duration, is_free, start_date, administrator_id)
	VALUES (4 , 5, true, '2023-7-18T12:00:00', 3);


INSERT INTO public.company_appointment(
	company_id, appointment_id)
	VALUES (1, 1);
	
INSERT INTO public.company_appointment(
	company_id, appointment_id)
	VALUES (1, 2);

INSERT INTO public.company_appointment(
	company_id, appointment_id)
	VALUES (1, 3);

INSERT INTO public.company_appointment(
	company_id, appointment_id)
	VALUES (2, 4);




	
