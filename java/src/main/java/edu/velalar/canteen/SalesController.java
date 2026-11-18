
package edu.velalar.canteen;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SalesController {

    private final JdbcTemplate jdbcTemplate;

    public SalesController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/sales")
    public String showSalesDashboard(Model model) {

        String summarySql = """
            SELECT
                COUNT(*) AS total_orders,
                COALESCE(SUM(total_amount), 0) AS total_revenue
            FROM orders
            WHERE order_status = 'COMPLETED'
            """;

        Map<String, Object> summary =
                jdbcTemplate.queryForMap(summarySql);

        String itemsSql = """
            SELECT
                f.item_name,
                SUM(oi.quantity) AS quantity_sold,
                SUM(oi.line_total) AS revenue
            FROM order_items oi
            JOIN orders o ON o.order_id = oi.order_id
            JOIN food_items f ON f.item_id = oi.item_id
            WHERE o.order_status = 'COMPLETED'
            GROUP BY f.item_id, f.item_name
            ORDER BY quantity_sold DESC
            """;

        List<Map<String, Object>> itemSales =
                jdbcTemplate.queryForList(itemsSql);

        model.addAttribute("totalOrders", summary.get("total_orders"));
        model.addAttribute("totalRevenue", summary.get("total_revenue"));
        model.addAttribute("itemSales", itemSales);

        return "sales";
    }
}
