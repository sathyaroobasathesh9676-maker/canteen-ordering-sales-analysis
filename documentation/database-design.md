# Database Design
## Tables
- **FOOD_ITEMS**(`food_id` PK, `name`, `price`, `stock_quantity`, `active`)
- **ORDERS**(`order_id` PK, `order_date`, `total_amount`, `status`)
- **ORDER_ITEMS**(`order_item_id` PK, `order_id` FK, `food_id` FK, `quantity`, `unit_price`, `line_total`)

## Relationships
One order contains one or more order items. Each order item refers to one food item. A food item can appear in many order items.

## Normalization
- **1NF:** Each field contains one atomic value and each row has a primary key.
- **2NF:** Every non-key field depends on the complete primary key. The design uses single-column primary keys.
- **3NF:** Food details, order-header details and order-line details are stored in separate tables.
- `unit_price` is retained in ORDER_ITEMS as a sale-time snapshot so later menu price changes do not alter old bills.
