import dao.BookingDAO;
import dao.CarDAO;
import dao.CustomerDAO;
import model.Car;
import service.BookingService;

import java.time.LocalDate;
import java.util.List;

void main() {

    // Initialize DAO objects to interact with database
    CarDAO carDAO = new CarDAO();           // Handles Car-related DB operations
    CustomerDAO customerDAO = new CustomerDAO();   // Handles Customer-related DB operations
    BookingDAO bookingDAO = new BookingDAO();     // Handles Booking-related DB operations

    // Initialize service layer which contains booking business logic
    BookingService bookingService = new BookingService(bookingDAO, carDAO, customerDAO);

    
    // Load and display all cars in a separate thread
 
    Thread loadCarsThread = new Thread(() -> {
        // Fetch all cars from database
        List<Car> cars = carDAO.findAll();
        IO.println("Available Cars:");

        // Print each car
        for (Car c : cars) {
            IO.println(c);
        }
    });

    // Start the thread
    loadCarsThread.start();

   
    // Simulate two users trying to book the same car simultaneously
   
    
    // User 1 booking Car ID 1 from today for 3 days
    Thread user1 = new Thread(() ->
        bookingService.bookCar(1, 1, LocalDate.now(), LocalDate.now().plusDays(3))
    );

    // User 2 booking the same Car ID 1 from today for 2 days
    Thread user2 = new Thread(() ->
        bookingService.bookCar(2, 1, LocalDate.now(), LocalDate.now().plusDays(2))
    );

    // Start both booking threads
    user1.start();
    user2.start();

   
    // Notes:
    // - BookingService should handle synchronization to prevent double-booking.
    // - Using separate threads simulates concurrent users accessing the system.
    // - loadCarsThread runs independently to avoid blocking the main thread.
   
}
