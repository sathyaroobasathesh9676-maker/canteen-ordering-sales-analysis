

package edu.velalar.canteen;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

    private final JdbcTemplate jdbcTemplate;

    public OrderController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/order")
    public String showOrderPage(Model model) {
        loadFoodItems(model);
        return "order";
    }

    @PostMapping("/place-order")
    @Transactional
    public String placeOrder(
            @RequestParam Map<String, String> formData,
            Model model) {

        try {
            Map<Integer, Integer> requested = new LinkedHashMap<>();

            for (Map.Entry<String, String> entry : formData.entrySet()) {
                if (entry.getKey().startsWith("qty_")) {
                    int itemId = Integer.parseInt(
                            entry.getKey().substring(4));

                    int quantity = Integer.parseInt(entry.getValue());

                    if (quantity < 0) {
                        throw new IllegalArgumentException(
                                "Quantity cannot be negative.");
                    }

                    if (quantity > 0) {
                        requested.put(itemId, quantity);
                    }
                }
            }

            if (requested.isEmpty()) {
                throw new IllegalArgumentException(
                        "Please select at least one food item.");
            }

            BigDecimal total = BigDecimal.ZERO;

            for (Map.Entry<Integer, Integer> entry : requested.entrySet()) {
                int itemId = entry.getKey();
                int quantity = entry.getValue();

                Map<String, Object> item = jdbcTemplate.queryForMap(
                        "SELECT price, stock_qty, is_available " +
                        "FROM food_items WHERE item_id = ? FOR UPDATE",
                        itemId
                );

                int stock = ((Number) item.get("stock_qty")).intValue();
                boolean available = Boolean.TRUE.equals(
                        item.get("is_available"));

                if (!available) {
                    throw new IllegalArgumentException(
                            "This food item is unavailable: " + itemId);
                }

                if (quantity > stock) {
                    throw new IllegalArgumentException(
                            "Insufficient stock for item ID " + itemId +
                            ". Available stock: " + stock);
                }

                BigDecimal price = (BigDecimal) item.get("price");

                total = total.add(
                        price.multiply(BigDecimal.valueOf(quantity)));
            }

            jdbcTemplate.update(
                    "INSERT INTO orders (total_amount, order_status) " +
                    "VALUES (?, 'COMPLETED')",
                    total
            );

            Long orderId = jdbcTemplate.queryForObject(
                    "SELECT LAST_INSERT_ID()",
                    Long.class
            );

            for (Map.Entry<Integer, Integer> entry : requested.entrySet()) {
                int itemId = entry.getKey();
                int quantity = entry.getValue();

                BigDecimal price = jdbcTemplate.queryForObject(
                        "SELECT price FROM food_items WHERE item_id = ?",
                        BigDecimal.class,
                        itemId
                );

                BigDecimal lineTotal =
                        price.multiply(BigDecimal.valueOf(quantity));

                jdbcTemplate.update(
                        "INSERT INTO order_items " +
                        "(order_id, item_id, quantity, unit_price, line_total) " +
                        "VALUES (?, ?, ?, ?, ?)",
                        orderId, itemId, quantity, price, lineTotal
                );

                int updated = jdbcTemplate.update(
                        "UPDATE food_items " +
                        "SET stock_qty = stock_qty - ? " +
                        "WHERE item_id = ? AND stock_qty >= ?",
                        quantity, itemId, quantity
                );

                if (updated != 1) {
                    throw new IllegalArgumentException(
                            "Stock update failed for item ID: " + itemId);
                }
            }

            model.addAttribute("total", total);
            model.addAttribute("orderId", orderId);

            return "order-success";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            loadFoodItems(model);
            return "order";
        }
    }

    private void loadFoodItems(Model model) {
        String sql = """
            SELECT item_id, item_name, price, stock_qty
            FROM food_items
            WHERE is_available = 1 AND stock_qty > 0
            ORDER BY item_id
            """;

        List<Map<String, Object>> foodItems =
                jdbcTemplate.queryForList(sql);

        model.addAttribute("foodItems", foodItems);
    }
}
