import dao.BookingDAO;
import dao.CarDAO;
import dao.CustomerDAO;
import model.Car;
import service.BookingService;

void main() {

    CarDAO carDAO = new CarDAO();
    CustomerDAO customerDAO = new CustomerDAO();
    BookingDAO bookingDAO = new BookingDAO();
    BookingService bookingService = new BookingService(bookingDAO, carDAO, customerDAO);

    // Load cars in a separate thread
    Thread loadCarsThread = new Thread(() -> {
        List<Car> cars = carDAO.findAll();
        IO.println("Available Cars:");
        for (Car c : cars) {
            IO.println(c);
        }
    });
    loadCarsThread.start();

    // Two users trying to book the same car
    Thread user1 = new Thread(() ->
            bookingService.bookCar(1, 1, LocalDate.now(), LocalDate.now().plusDays(3))
    );

    Thread user2 = new Thread(() ->
            bookingService.bookCar(2, 1, LocalDate.now(), LocalDate.now().plusDays(2))
    );

    user1.start();
    user2.start();
}
