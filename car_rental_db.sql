CREATE DATABASE car_rental_db;
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
