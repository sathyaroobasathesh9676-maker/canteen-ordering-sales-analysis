CREATE DATABASE IF NOT EXISTS canteen_db;
USE canteen_db;

CREATE TABLE IF NOT EXISTS food_items (
 food_id INT AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR(100) NOT NULL UNIQUE,
 price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
 stock_quantity INT NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
 active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS orders (
 order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
 order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 total_amount DECIMAL(10,2) NOT NULL CHECK (total_amount >= 0),
 status VARCHAR(20) NOT NULL DEFAULT 'COMPLETED'
);

CREATE TABLE IF NOT EXISTS order_items (
 order_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
 order_id BIGINT NOT NULL,
 food_id INT NOT NULL,
 quantity INT NOT NULL CHECK (quantity > 0),
 unit_price DECIMAL(10,2) NOT NULL,
 line_total DECIMAL(10,2) NOT NULL,
 FOREIGN KEY (order_id) REFERENCES orders(order_id),
 FOREIGN KEY (food_id) REFERENCES food_items(food_id)
);

INSERT IGNORE INTO food_items(name,price,stock_quantity) VALUES
('Idli',10.00,100),('Dosa',35.00,50),('Poori',30.00,40),
('Tea',12.00,100),('Coffee',18.00,80),('Veg Meals',75.00,30);
