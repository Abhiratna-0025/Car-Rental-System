package dao;

import model.Car;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO implements CRUDRepository<Car> {

    @Override
    public Car findById(int id) {
        String sql = "SELECT * FROM cars WHERE car_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToCar(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Car> findAll() {
        List<Car> list = new ArrayList<>();
        String sql = "SELECT * FROM cars";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRowToCar(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean save(Car car) {
        String sql = "INSERT INTO cars(model, type, rent_per_day, available) VALUES (?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, car.getModel());
            ps.setString(2, car.getType());
            ps.setDouble(3, car.getRentPerDay());
            ps.setBoolean(4, car.isAvailable());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Car car) {
        String sql = "UPDATE cars SET model=?, type=?, rent_per_day=?, available=? WHERE car_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, car.getModel());
            ps.setString(2, car.getType());
            ps.setDouble(3, car.getRentPerDay());
            ps.setBoolean(4, car.isAvailable());
            ps.setInt(5, car.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM cars WHERE car_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Car mapRowToCar(ResultSet rs) throws SQLException {
        return new Car(
                rs.getInt("car_id"),
                rs.getString("model"),
                rs.getString("type"),
                rs.getDouble("rent_per_day"),
                rs.getBoolean("available")
        );
    }
}
