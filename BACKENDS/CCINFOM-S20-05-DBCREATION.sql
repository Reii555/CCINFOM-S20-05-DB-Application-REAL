DROP DATABASE IF EXISTS delivery_pickup_db;
CREATE DATABASE delivery_pickup_db;
USE delivery_pickup_db;

-- Tables
CREATE TABLE Drivers (
    DRIVER_ID DECIMAL(10, 0) PRIMARY KEY,
    DRIVER_LASTNAME VARCHAR(20) NOT NULL,
    DRIVER_FIRSTNAME VARCHAR(20) NOT NULL,
    DRIVER_CONTACT DECIMAL(10, 0),
    DRIVER_LICENCE CHAR(13) NOT NULL,
    STATUS CHAR(10) DEFAULT 'Available',
    SHIFT TIME
);

CREATE TABLE customers (
    customer_id DECIMAL(10,0) PRIMARY KEY,
    customer_lastname VARCHAR(20) NOT NULL,
    customer_firstname VARCHAR(20) NOT NULL,
    customer_address VARCHAR(50) NOT NULL
);

CREATE TABLE Deliveries (
	DELIVERY_ID DECIMAL (10,0) PRIMARY KEY,
    ORDER_ID DECIMAL (10,0) NOT NULL,
    DELIVERY_TYPE CHAR (15) NOT NULL,
    DELIVERY_STATUS CHAR (15) DEFAULT 'Pending',
    DRIVER_ID DECIMAL (10,0), -- nullable to allow pending deliveries
    PAYMENT CHAR (15) NOT NULL,
    EST_DELIVERY_TIME TIME,
    ACT_DELIVERY_TIME TIME,
    DELIVERY_FEE INT (5) NOT NULL,
    DELIVERY_DATE DATE
    );

CREATE TABLE Pickups (
ORDER_ID DECIMAL (10, 0) PRIMARY KEY,
ORDER_TYPE CHAR (15),
STATUS CHAR (10),
PICKUP_LOCATION VARCHAR (50),
PICKUP_DATE DATE,
PICKUP_SERVICE VARCHAR (20),
PAYMENT_METHOD CHAR (15),
CUSTOMER_ID DECIMAL(10,0),
FOREIGN KEY (CUSTOMER_ID) REFERENCES customers(customer_id)
);

-- Sample dats

-- 1. CUSTOMERS
INSERT INTO customers(customer_id, customer_lastname, customer_firstname, customer_address) VALUES
(1000000001, 'Dela Cruz', 'Juan', '123 Rizal Street, Sta. Ana, Manila'),
(1000000002, 'Santos', 'Maria', '456 Mabini Avenue, Malate, Manila'),
(1000000003, 'Reyes', 'Pedro', '789 Roxas Boulevard, Pasay City'),
(1000000004, 'Garcia', 'Ana', '321 Sumulong Highway, Antipolo, Rizal'),
(1000000005, 'Bautista', 'Jose', '654 Governor\'s Drive, Gen. Trias, Cavite');

-- 2. PICKUPS
INSERT INTO Pickups(ORDER_ID, ORDER_TYPE, STATUS, PICKUP_LOCATION, PICKUP_DATE, PICKUP_SERVICE, PAYMENT_METHOD, CUSTOMER_ID) VALUES
(1000000001, 'Meal A', 'Pending', 'Sta. Ana branch', '2024-01-20', 'Express', 'Cash', 1000000001),
(1000000002, 'Combo meal C', 'Ready', 'Malate branch', '2024-01-21', 'Standard', 'Credit', 1000000002),
(1000000003, 'Combo meal D', 'Completed', 'Pasay branch', '2024-01-19', 'Express', 'Cash', 1000000003),
(1000000004, 'Meal B', 'Pending', 'Antipolo branch', '2024-01-22', 'Standard', 'Debit', 1000000004),
(1000000005, 'Meal A', 'Ready', 'Gen. Trias branch', '2024-01-21', 'Express', 'Credit', 1000000005);

-- 3. DRIVERS 
INSERT INTO Drivers (DRIVER_ID, DRIVER_LASTNAME, DRIVER_FIRSTNAME, DRIVER_CONTACT, DRIVER_LICENCE, STATUS, SHIFT) VALUES 
(1001, 'Bee', 'Jolli', '09171234567', 'DL123456789SU', 'Available', '08:00:00'),
(1002, 'Barbeque', 'Reyes', '09179876543', 'DL987654321SU', 'Available', '12:00:00'),
(1003, 'Inasal', 'Mang', '09171122334', 'DL555666777SU', 'Busy', '08:00:00'),
(1004, 'Fung', 'Din Tai', '09173334455', 'DL444333222SU', 'Available', '16:00:00'),
(1005, 'Robertson', 'Robert', '09176667788', 'DL888999000SU', 'Available', '12:00:00');

-- 4. DELIVERIES
INSERT INTO Deliveries (DELIVERY_ID, ORDER_ID, DELIVERY_TYPE, DELIVERY_STATUS, DRIVER_ID, PAYMENT, EST_DELIVERY_TIME, ACT_DELIVERY_TIME, DELIVERY_FEE, DELIVERY_DATE) VALUES
-- Completed delivery (matches pickup status)
(2025000001, 1000000003, 'Regular', 'Delivered', 1002, 'Cash', '09:30:00', '09:31:00', 150.00, '2024-01-19'),

-- Assigned but not completed  
(2025000002, 1000000002, 'Regular', 'Assigned', 1004, 'Credit', '14:03:00', NULL, 150.00, '2024-01-21'),
(2025000003, 1000000005, 'Rush', 'Assigned', 1001, 'Credit', '18:44:00', NULL, 250.00, '2024-01-21'),

-- Pending (not assigned yet)
(2025000004, 1000000001, 'Regular', 'Pending', NULL, 'Cash', '07:23:00', NULL, 150.00, '2024-01-20'),
(2025000005, 1000000004, 'Rush', 'Pending', NULL, 'Debit', '10:45:00', NULL, 250.00, '2024-01-22');