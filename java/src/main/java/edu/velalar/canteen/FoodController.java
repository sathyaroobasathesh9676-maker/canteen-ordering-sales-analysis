package edu.velalar.canteen;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FoodController {

    private final JdbcTemplate jdbcTemplate;

    public FoodController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/menu")
    public String showMenu(Model model) {

        String sql = """
            SELECT item_id, item_name, price, stock_qty
            FROM food_items
            WHERE is_available = 1
            ORDER BY item_id
            """;

        List<Map<String, Object>> foodItems =
                jdbcTemplate.queryForList(sql);

        model.addAttribute("foodItems", foodItems);

        return "menu";
    }
}