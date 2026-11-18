# System Design Diagrams
These Mermaid diagrams can be previewed in VS Code with a Mermaid extension and exported as images if required.

## Use Case Diagram
```mermaid
flowchart LR
 Staff([Canteen Staff]) --> Menu((View menu and stock))
 Staff --> Maintain((Maintain food items))
 Staff --> Order((Place order))
 Order --> Bill((Calculate bill))
 Order --> Check((Validate stock))
 Order --> Update((Update stock))
 Manager([Management]) --> Report((View sales report))
 Manager --> Charts((Generate sales charts))
```

## Class Diagram
```mermaid
classDiagram
 class FoodItem {
  +int id
  +String name
  +BigDecimal price
  +int stockQuantity
 }
 class Order {
  +long id
  +LocalDateTime orderDate
  +BigDecimal totalAmount
 }
 class OrderItem {
  +long id
  +int quantity
  +BigDecimal unitPrice
  +BigDecimal lineTotal()
 }
 class CanteenService {
  +listFoodItems()
  +addFoodItem()
  +placeOrder()
  +showSalesSummary()
 }
 Order "1" --> "*" OrderItem
 OrderItem "*" --> "1" FoodItem
 CanteenService ..> FoodItem
 CanteenService ..> Order
```

## System Architecture
```mermaid
flowchart TB
 User[Canteen staff / Management] --> UI[Java console]
 UI --> Service[Java service layer]
 Service --> JDBC[JDBC data access]
 JDBC --> DB[(MySQL database)]
 DB --> Python[Python pandas analysis]
 Python --> Charts[Sales charts]
```

## ER Diagram
```mermaid
erDiagram
 FOOD_ITEMS ||--o{ ORDER_ITEMS : appears_in
 ORDERS ||--|{ ORDER_ITEMS : contains
 FOOD_ITEMS {
  int food_id PK
  varchar name
  decimal price
  int stock_quantity
  boolean active
 }
 ORDERS {
  bigint order_id PK
  datetime order_date
  decimal total_amount
  varchar status
 }
 ORDER_ITEMS {
  bigint order_item_id PK
  bigint order_id FK
  int food_id FK
  int quantity
  decimal unit_price
  decimal line_total
 }
```
