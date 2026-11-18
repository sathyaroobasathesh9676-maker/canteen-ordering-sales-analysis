
package edu.velalar.canteen;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class CanteenService {

    public void listFoodItems() throws Exception {
        String sql = "SELECT item_id, item_name, price, stock_qty " +
                     "FROM food_items WHERE is_available = 1 ORDER BY item_id";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {

            System.out.printf("%-5s %-20s %-10s %-8s%n",
                    "ID", "Food", "Price", "Stock");

            while (r.next()) {
                System.out.printf("%-5d %-20s %-10s %-8d%n",
                        r.getInt("item_id"),
                        r.getString("item_name"),
                        r.getBigDecimal("price"),
                        r.getInt("stock_qty"));
            }
        }
    }

    public void addFoodItem(String name, BigDecimal price, int stock)
            throws Exception {

        if (name.isBlank() || price.signum() < 0 || stock < 0) {
            throw new IllegalArgumentException("Invalid item details.");
        }

        String sql = "INSERT INTO food_items " +
                "(item_name, price, stock_qty, is_available) VALUES (?, ?, ?, 1)";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setString(1, name.trim());
            p.setBigDecimal(2, price);
            p.setInt(3, stock);
            p.executeUpdate();
        }
    }

    public BigDecimal placeOrder(Map<Integer, Integer> requested)
            throws Exception {

        if (requested.isEmpty()) {
            throw new IllegalArgumentException(
                    "Order must contain at least one item.");
        }

        try (Connection c = DatabaseConnection.getConnection()) {
            c.setAutoCommit(false);

            try {
                BigDecimal total = BigDecimal.ZERO;
                Map<Integer, BigDecimal> prices = new LinkedHashMap<>();

                for (var e : requested.entrySet()) {
                    int id = e.getKey();
                    int qty = e.getValue();

                    if (qty <= 0) {
                        throw new IllegalArgumentException(
                                "Quantity must be greater than zero.");
                    }

                    String sql = "SELECT price, stock_qty, is_available " +
                            "FROM food_items WHERE item_id = ? FOR UPDATE";

                    try (PreparedStatement p = c.prepareStatement(sql)) {
                        p.setInt(1, id);

                        try (ResultSet r = p.executeQuery()) {
                            if (!r.next() || !r.getBoolean("is_available")) {
                                throw new IllegalArgumentException(
                                        "Unknown or unavailable food ID: " + id);
                            }

                            int stock = r.getInt("stock_qty");

                            if (qty > stock) {
                                throw new IllegalArgumentException(
                                        "Insufficient stock for ID " + id +
                                        ". Available: " + stock);
                            }

                            BigDecimal price = r.getBigDecimal("price");
                            prices.put(id, price);

                            total = total.add(
                                    price.multiply(BigDecimal.valueOf(qty)));
                        }
                    }
                }

                long orderId;

                String orderSql = "INSERT INTO orders " +
                        "(total_amount, order_status) VALUES (?, 'COMPLETED')";

                try (PreparedStatement p = c.prepareStatement(
                        orderSql, Statement.RETURN_GENERATED_KEYS)) {

                    p.setBigDecimal(1, total);
                    p.executeUpdate();

                    try (ResultSet keys = p.getGeneratedKeys()) {
                        if (!keys.next()) {
                            throw new SQLException("Order ID not returned.");
                        }
                        orderId = keys.getLong(1);
                    }
                }

                for (var e : requested.entrySet()) {
                    int id = e.getKey();
                    int qty = e.getValue();
                    BigDecimal price = prices.get(id);

                    String itemSql = "INSERT INTO order_items " +
                            "(order_id, item_id, quantity, unit_price, line_total) " +
                            "VALUES (?, ?, ?, ?, ?)";

                    try (PreparedStatement p = c.prepareStatement(itemSql)) {
                        p.setLong(1, orderId);
                        p.setInt(2, id);
                        p.setInt(3, qty);
                        p.setBigDecimal(4, price);
                        p.setBigDecimal(5,
                                price.multiply(BigDecimal.valueOf(qty)));
                        p.executeUpdate();
                    }

                    String updateSql = "UPDATE food_items " +
                            "SET stock_qty = stock_qty - ? " +
                            "WHERE item_id = ? AND stock_qty >= ?";

                    try (PreparedStatement p = c.prepareStatement(updateSql)) {
                        p.setInt(1, qty);
                        p.setInt(2, id);
                        p.setInt(3, qty);

                        if (p.executeUpdate() != 1) {
                            throw new SQLException("Stock update failed.");
                        }
                    }
                }

                c.commit();

                System.out.println("Order #" + orderId +
                        " completed. Total bill: Rs. " + total);

                return total;

            } catch (Exception ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    public void showSalesSummary() throws Exception {

        String sql = "SELECT COUNT(*), COALESCE(SUM(total_amount), 0) " +
                "FROM orders WHERE order_status = 'COMPLETED'";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {

            if (r.next()) {
                System.out.println("Completed orders: " + r.getLong(1));
                System.out.println("Total revenue: Rs. " + r.getBigDecimal(2));
            }
        }
    }
}
