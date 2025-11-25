package com.carrental.model;

import java.time.LocalDate;

/**
 * Represents a car booking made by a customer.
 */
public class Booking {
    // Unique identifier for the booking
    private int id;

    // Car that is being booked
    private Car car;

    // Customer who made the booking
    private Customer customer;

    // Booking start date
    private LocalDate startDate;

    // Booking end date
    private LocalDate endDate;

    // Total cost for the booking
    private double totalAmount;

    // Status of the booking (e.g., PENDING, BOOKED, CANCELLED, COMPLETED)
    private String status;

    // Default constructor
    public Booking() {}

    // Parameterized constructor
    public Booking(int id, Car car, Customer customer,
                   LocalDate startDate, LocalDate endDate,
                   double totalAmount, String status) {
        this.id = id;
        this.car = car;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Getter and setter methods
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    /**
     * Returns a string representation of the booking.
     * Useful for debugging or logging purposes.
     */
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", car=" + car +
                ", customer=" + customer +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                '}';
    }
}
