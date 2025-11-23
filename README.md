# Car-Rental-System
The Car Rental System is a Java-based application designed to streamline and automate the process of car rental management for both customers and administrators.​

# Car Rental System (Java GUI)

The **Car Rental System** is a Java-based application with a **Graphical User Interface (GUI)** designed to automate and simplify the process of renting cars for both customers and administrators. The system supports booking management, user authentication, and an efficient rental workflow integrated with a database backend.

---

##  Features

###  Admin Functionalities
- Add, update, and delete car information
- View all registered customers
- View and manage rental bookings
- Update booking status (Booked, Ongoing, Completed, Cancelled)

### Customer Functionalities
- Register and log in
- Search available cars based on type, price, and availability
- Book cars for selected dates
- View booking history

### GUI Highlights
- Built using Java Swing/JavaFX (update as needed)
- Fully interactive windows and forms
- Input validation and error message dialogs
- Smooth navigation with menus and buttons

---

## Technologies Used

| Component | Technology |
|----------|------------|
| Programming Language | Java |
| GUI Framework |JavaFX |
| Database | MySQL|
| Database Connectivity | JDBC |
| Tools (Recommended) | IntelliJ IDEA / NetBeans / Eclipse |

---

## Concepts Implemented

### 🔹 Object-Oriented Programming
The project follows full OOP principles:
- **Inheritance** → (`User → Customer / Admin`, `Vehicle → Car`)
- **Polymorphism** → Dashboard UI and rental calculation overridden based on user type
- **Interfaces** → `CRUDOperations<T>` implemented by DAO classes
- **Exception Handling** → Custom exceptions for invalid inputs, login failure, and booking errors

### 🔹 Collections & Generics
- Data stored using `ArrayList<Car>`, `ArrayList<Booking>` etc.
- Generics used for type safety in DAO operations

### 🔹 Multithreading & Synchronization
- Background threads used for loading data from the database to keep the GUI responsive
- Synchronization implemented during booking to prevent double-booking of cars

Each class handles CRUD operations using reusable methods.

### 🔹 JDBC Connectivity
- JDBC API used to connect Java GUI with the database
- `Connection`, `PreparedStatement`, `ResultSet` used for secure SQL communication
- SQL Injection prevented using parameterized queries
- `DBConnection.java` manages database connection handling


▶️ How to Run the Project

Clone the repository

git clone https://github.com/your-username/car-rental-system.git


Open the project in any Java-supported IDE
(IntelliJ IDEA, Eclipse, NetBeans, VS Code with Java extension, etc.)

Run the main program

Main.java
---


