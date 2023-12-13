INSERT INTO public.role(name)
VALUES ('ROLE_REGULAR_USER');

INSERT INTO public.users(city, company_information, country, email, is_verified, last_password_reset_date, name, password, phone_number, profession, surname, role_id)
	VALUES ('Beograd', 'preduzece1', 'Srbija', 'petar@gmail.com', true, null, 'Petar', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi', '065728314', 'softverski inzenjer', 'Knezevic',1);

INSERT INTO public.regular_user(
	user_id, penalties)
	VALUES (1, 0);
