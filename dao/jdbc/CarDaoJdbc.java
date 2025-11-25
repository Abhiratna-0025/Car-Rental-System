package com.carrental.dao.jdbc;

import com.carrental.dao.CarDao;
import com.carrental.model.Car;
import com.carrental.model.CarType;
import com.carrental.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDaoJdbc implements CarDao {

    /**
     * Save a car to the database.
     * - If car ID is 0, insert a new car.
     * - Otherwise, update existing car details.
     */
    @Override
    public Car save(Car car) {
        String sqlInsert = "INSERT INTO cars (model, brand, type, daily_rate, available) VALUES (?, ?, ?, ?, ?)";
        String sqlUpdate = "UPDATE cars SET model=?, brand=?, type=?, daily_rate=?, available=? WHERE id=?";

        try (Connection conn = DbUtil.getConnection()) {
            // Insert new car
            if (car.getId() == 0) {
                try (PreparedStatement ps = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, car.getModel());
                    ps.setString(2, car.getBrand());
                    ps.setString(3, car.getType().name()); // Store enum as string
                    ps.setDouble(4, car.getDailyRate());
                    ps.setBoolean(5, car.isAvailable());
                    ps.executeUpdate();

                    // Get generated ID from DB
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            car.setId(rs.getInt(1));
                        }
                    }
                }
            } 
            // Update existing car
            else {
                try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                    ps.setString(1, car.getModel());
                    ps.setString(2, car.getBrand());
                    ps.setString(3, car.getType().name());
                    ps.setDouble(4, car.getDailyRate());
                    ps.setBoolean(5, car.isAvailable());
                    ps.setInt(6, car.getId());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log SQL errors
        }

        return car;
    }

    /**
     * Find a car by its ID
     */
    @Override
    public Optional<Car> findById(Integer id) {
        String sql = "SELECT * FROM cars WHERE id=?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToCar(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Retrieve all cars from the database
     */
    @Override
    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cars.add(mapRowToCar(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    /**
     * Delete a car by its ID
     */
    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM cars WHERE id=?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve all cars that are currently available
     */
    @Override
    public List<Car> findAvailableCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE available = TRUE";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cars.add(mapRowToCar(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    /**
     * Map a ResultSet row to a Car object
     * Converts SQL types to Java types and enum
     */
    private Car mapRowToCar(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setId(rs.getInt("id"));
        car.setModel(rs.getString("model"));
        car.setBrand(rs.getString("brand"));
        car.setType(CarType.valueOf(rs.getString("type"))); // Convert string to enum
        car.setDailyRate(rs.getDouble("daily_rate"));
        car.setAvailable(rs.getBoolean("available"));
        return car;
    }
}

