package com.carrental;

// Import DAO interfaces and their JDBC implementations
import com.carrental.dao.BookingDao;
import com.carrental.dao.CarDao;
import com.carrental.dao.CustomerDao;
import com.carrental.dao.jdbc.BookingDaoJdbc;
import com.carrental.dao.jdbc.CarDaoJdbc;
import com.carrental.dao.jdbc.CustomerDaoJdbc;

// Import model classes
import com.carrental.model.Booking;
import com.carrental.model.Car;
import com.carrental.model.Customer;

// Import service classes
import com.carrental.service.BookingProcessor;
import com.carrental.service.CarRentalService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {

        // Initialize DAO objects for database operations
        CarDao carDao = new CarDaoJdbc();           // Handles Car CRUD operations
        CustomerDao customerDao = new CustomerDaoJdbc(); // Handles Customer CRUD operations
        BookingDao bookingDao = new BookingDaoJdbc();    // Handles Booking CRUD operations

        // Initialize the main service layer that connects DAOs
        CarRentalService service = new CarRentalService(carDao, customerDao, bookingDao);

        // Create a thread-safe queue to hold booking requests
        BlockingQueue<Booking> queue = new LinkedBlockingQueue<>();

        // Start a separate thread that processes bookings asynchronously
        Thread bookingThread = new Thread(new BookingProcessor(queue, service));
        bookingThread.start();

        // Scanner for user input from console
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        // Main menu loop
        while (!exit) {
            System.out.println("\n=== CAR RENTAL SYSTEM ===");
            System.out.println("1. List available cars");
            System.out.println("2. Register customer");
            System.out.println("3. Make booking");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine(); // Consume leftover newline

            switch (choice) {
                case 1 -> {
                    // List all available cars
                    List<Car> cars = service.listAvailableCars();
                    cars.forEach(System.out::println);
                }
                case 2 -> {
                    // Register a new customer
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Phone: ");
                    String phone = sc.nextLine();

                    Customer c = service.registerCustomer(name, email, phone);
                    System.out.println("Registered: " + c);
                }
                case 3 -> {
                    // Make a booking
                    System.out.print("Customer ID: ");
                    int custId = sc.nextInt();
                    System.out.print("Car ID: ");
                    int carId = sc.nextInt();
                    System.out.print("Start date (YYYY-MM-DD): ");
                    String start = sc.next();
                    System.out.print("End date (YYYY-MM-DD): ");
                    String end = sc.next();

                    // Create Customer and Car objects with IDs only (other fields not needed)
                    Customer customer = new Customer();
                    customer.setId(custId);

                    Car car = new Car();
                    car.setId(carId);

                    // Create a new Booking object
                    Booking booking = new Booking();
                    booking.setCustomer(customer);
                    booking.setCar(car);
                    booking.setStartDate(LocalDate.parse(start));
                    booking.setEndDate(LocalDate.parse(end));
                    booking.setStatus("PENDING"); // Initially set as pending

                    // Add the booking to the queue for asynchronous processing
                    try {
                        queue.put(booking);
                        System.out.println("Booking request queued. It will be processed shortly.");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Restore interrupted status
                    }
                }
                case 0 -> {
                    // Exit the system
                    exit = true;
                    bookingThread.interrupt(); // Stop the booking processing thread
                }
                default -> System.out.println("Invalid choice.");
            }
        }

        sc.close(); // Close the Scanner
        System.out.println("System exited.");
    }
}
