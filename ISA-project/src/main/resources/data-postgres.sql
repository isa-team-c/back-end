INSERT INTO public.role(name)
VALUES ('ROLE_REGULAR_USER');

INSERT INTO public.role(name)
VALUES ('ROLE_COMPANY_ADMIN');

INSERT INTO public.role(name)
VALUES ('ROLE_ADMIN');

INSERT INTO public.users(city, company_information, country, email, is_verified, last_password_reset_date, name, password, phone_number, profession, surname, role_id)
	VALUES ('Beograd', 'preduzece1', 'Srbija', 'petar@gmail.com', true, null, 'Petar', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi', '065728314', 'softverski inzenjer', 'Knezevic',1);

INSERT INTO public.users(city, company_information, country, email, is_verified, last_password_reset_date, name, password, phone_number, profession, surname, role_id)
	VALUES ('Grad', '/', 'Drzava', 'admin@gmail.com', true, null, 'Admin', '$2a$10$E17mSVuCMgS9jfc/WZcK8eFrjD/uYgZ1XcAd2T55rwZOKv7ki23tW', '1234567890', 'profesija', 'Adminovic', 3);

INSERT INTO public.regular_user(
	user_id, penalties)
	VALUES (1, 0);

INSERT INTO public.administrator(
	user_id, logged_in_before)
	VALUES (2, false);
