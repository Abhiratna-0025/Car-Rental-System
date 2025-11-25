CREATE DATABASE car_rental_db
USE car_rental_db;

CREATE TABLE cars (
    car_id INT PRIMARY KEY AUTO_INCREMENT,
    model VARCHAR(100),
    type VARCHAR(50),
    rent_per_day DOUBLE,
    available BOOLEAN
);

CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20)
);

CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    car_id INT,
    start_date DATE,
    end_date DATE,
    total_amount DOUBLE,
    status VARCHAR(20),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (car_id) REFERENCES cars(car_id)
);

USE car_rental_db;

INSERT INTO cars(model, type, rent_per_day, available) VALUES
('Honda City', 'Sedan', 2500, TRUE),
('Mahindra XUV 700', 'SUV', 3500, TRUE),
('Maruti Swift', 'Hatchback', 1800, TRUE);

INSERT INTO customers(name, email, phone) VALUES
('Ahmed', 'ahmed@gmail.com', '9876543210'),
('Riya', 'riya@gmail.com', '9876543211');
