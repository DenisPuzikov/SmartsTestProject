insert into BANK (bank_name) values ('Sberbank');
insert into BANK (BANK_NAME) values ('Tinkoffbank');
insert into BANK (BANK_NAME) values ('Alfabank');

insert into client (LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, PASSPORT, fk_client_bank)
values ('Fedotov', 'Nikita', 'Ivanovich', 89277069755, 'fedotNik@gmail.com', 361029031, 0);
insert into client (LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, PASSPORT, fk_client_bank)
values ('Urupin', 'Andey', 'Alexseevitch', 89171762834, 'urupinAndrew123@gmail.com', 3215356982, 0);
insert into client (LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, PASSPORT, fk_client_bank)
values ('Ganin', 'Igor', 'Anatolievich', 89371000546, 'IamGanin@mail.ru', 3015243586, 0);
insert into CLIENT (LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, PASSPORT, FK_CLIENT_BANK)
values ('Ovdienko', 'Oleg', 'Vitalievich', 89277073252, 'staryDed@yandex.ru', 2996123543, 0);
insert into CLIENT (LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, PASSPORT, FK_CLIENT_BANK)
values ('Ovechkin', 'Alex', 'Igorevitch', 89379377777, 'ovisuperstar@gmail.com', 3130834529, 1);
insert into CLIENT (LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, PASSPORT, FK_CLIENT_BANK)
values ('Andersen', 'Pamela', 'Rurikovna', 89993521621, 'penthouse1993@gmail.com', 4187126743, 1);
insert into CLIENT (LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, PASSPORT, FK_CLIENT_BANK)
values ('Cruze', 'Tom', 'Azamatovich', 89003542687, 'topgunmaveric2022@mail.ru', 3001942764, 1);
insert into CLIENT (LAST_NAME, FIRST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL, PASSPORT, FK_CLIENT_BANK)
values ('Puzikov', 'Denis', 'Aleksandrovich', 89377777777, 'iWannaWorkInSMARTS@gmail.com', 3636104274, 2);

insert into CREDIT (CREDIT_LIMIT, INTEREST_RATE, fk_credit_bank) values (100000.0, 30.0, 0);
insert into CREDIT (CREDIT_LIMIT, INTEREST_RATE, fk_credit_bank) values (500000.0, 25.0, 0);
insert into CREDIT (CREDIT_LIMIT, INTEREST_RATE, fk_credit_bank) values (1000000.0, 20.0, 0);
insert into CREDIT (CREDIT_LIMIT, INTEREST_RATE, FK_CREDIT_BANK) values (2500000.0, 5.0, 1);
insert into CREDIT (CREDIT_LIMIT, INTEREST_RATE, FK_CREDIT_BANK) values (1500000.0, 10.0, 1);
insert into CREDIT (CREDIT_LIMIT, INTEREST_RATE, FK_CREDIT_BANK) values (500000.0, 12.5, 1);
insert into CREDIT (CREDIT_LIMIT, INTEREST_RATE, FK_CREDIT_BANK) values (100000.0, 15.2, 1);
insert into CREDIT (CREDIT_LIMIT, INTEREST_RATE, FK_CREDIT_BANK) values (1250000.0, 8.7, 2);
insert into CREDIT (CREDIT_LIMIT, INTEREST_RATE, FK_CREDIT_BANK) values (900000.0, 14.6, 2);


