package com.carrental.dao.jdbc;

import com.carrental.dao.CustomerDao;
import com.carrental.model.Customer;
import com.carrental.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDaoJdbc implements CustomerDao {

    /**
     * Save a customer to the database.
     * - If customer ID is 0, insert a new record.
     * - Otherwise, update existing customer details.
     */
    @Override
    public Customer save(Customer customer) {
        String insert = "INSERT INTO customers (name, email, phone) VALUES (?, ?, ?)";
        String update = "UPDATE customers SET name=?, email=?, phone=? WHERE id=?";

        try (Connection conn = DbUtil.getConnection()) {
            // Insert new customer
            if (customer.getId() == 0) {
                try (PreparedStatement ps = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, customer.getName());
                    ps.setString(2, customer.getEmail());
                    ps.setString(3, customer.getPhone());
                    ps.executeUpdate();

                    // Get generated ID from DB
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) customer.setId(rs.getInt(1));
                    }
                }
            } 
            // Update existing customer
            else {
                try (PreparedStatement ps = conn.prepareStatement(update)) {
                    ps.setString(1, customer.getName());
                    ps.setString(2, customer.getEmail());
                    ps.setString(3, customer.getPhone());
                    ps.setInt(4, customer.getId());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log SQL exceptions
        }

        return customer;
    }

    /**
     * Find a customer by ID
     */
    @Override
    public Optional<Customer> findById(Integer id) {
        String sql = "SELECT * FROM customers WHERE id=?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs)); // Map ResultSet to Customer
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Retrieve all customers
     */
    @Override
    public List<Customer> findAll() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Delete a customer by ID
     */
    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM customers WHERE id=?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Find a customer by email
     */
    @Override
    public Optional<Customer> findByEmail(String email) {
        String sql = "SELECT * FROM customers WHERE email=?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Map a ResultSet row to a Customer object
     */
    private Customer mapRow(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setId(rs.getInt("id"));
        c.setName(rs.getString("name"));
        c.setEmail(rs.getString("email"));
        c.setPhone(rs.getString("phone"));
        return c;
    }
}
