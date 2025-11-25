package dao;

import model.*;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO implements CRUDRepository<Booking> {

    @Override
    public Booking findById(int id) {
        String sql = "SELECT * FROM bookings WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToBooking(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Booking> findAll() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRowToBooking(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean save(Booking b) {
        String sql = "INSERT INTO bookings(customer_id, car_id, start_date, end_date, total_amount, status) VALUES(?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, b.getCustomer().getId());
            ps.setInt(2, b.getCar().getId());
            ps.setDate(3, Date.valueOf(b.getStartDate()));
            ps.setDate(4, Date.valueOf(b.getEndDate()));
            ps.setDouble(5, b.getTotalAmount());
            ps.setString(6, b.getStatus().toString());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Booking b) {
        String sql = "UPDATE bookings SET status=? WHERE booking_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, b.getStatus().toString());
            ps.setInt(2, b.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM bookings WHERE booking_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Booking mapRowToBooking(ResultSet rs) throws SQLException {
        CustomerDAO cdao = new CustomerDAO();
        CarDAO cardao = new CarDAO();

        return new Booking(
                rs.getInt("booking_id"),
                cdao.findById(rs.getInt("customer_id")),
                cardao.findById(rs.getInt("car_id")),
                rs.getDate("start_date").toLocalDate(),
                rs.getDate("end_date").toLocalDate(),
                rs.getDouble("total_amount"),
                BookingStatus.valueOf(rs.getString("status"))
        );
    }
}
