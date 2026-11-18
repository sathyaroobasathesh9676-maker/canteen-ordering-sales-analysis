
package edu.velalar.canteen;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

    private final JdbcTemplate jdbcTemplate;
    private final CanteenService canteenService;

    public OrderController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.canteenService = new CanteenService();
    }

    @GetMapping("/order")
    public String showOrderPage(Model model) {
        loadFoodItems(model);
        return "order";
    }

    @PostMapping("/place-order")
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

                    if (quantity > 0) {
                        requested.put(itemId, quantity);
                    }
                }
            }

            var total = canteenService.placeOrder(requested);

            model.addAttribute("total", total);
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
