# Software Requirements Specification
## Canteen Ordering and Sales Analysis System
**Scenario 3 | Register No. 93 | Academic Year 2026–2027**

### 1. Purpose
The system manages canteen food items, orders, billing, stock, transactions and sales analysis.

### 2. Scope
Maintain food item names, prices and stock; accept orders containing one or more items; calculate bills; update stock after successful orders; store transaction details; prevent orders exceeding stock; retrieve sales data using Python; and visualize item-wise sales, daily revenue and popular food items.

### 3. Users and environment
Canteen staff use the Java console application to maintain items and enter orders. Management can review sales information. Java connects to MySQL through JDBC. Python uses pandas and matplotlib to analyze database records.

### 4. Functional requirements
| ID | Requirement |
|---|---|
| FR-01 | Maintain food item names, prices and stock quantities. |
| FR-02 | Accept orders containing one or more food items. |
| FR-03 | Calculate the total bill automatically. |
| FR-04 | Update stock quantities when orders are completed. |
| FR-05 | Store transaction details and generate sales reports. |
| FR-06 | Prevent orders that exceed available stock. |
| FR-07 | Retrieve sales data from the database and preprocess it using Python. |
| FR-08 | Visualize item-wise sales, daily revenue and popular food items. |

### 5. Non-functional requirements
- Accuracy: totals and stock changes must be correct.
- Reliability: failed orders must not cause partial stock deductions.
- Data integrity: use primary keys, foreign keys and database transactions.
- Usability: provide a clear menu and understandable messages.
- Maintainability: separate model, database and service responsibilities.
- Security: keep database credentials local and out of public repositories.

### 6. Business rules
1. An order must contain at least one item.
2. Quantity must be greater than zero.
3. Requested quantity cannot exceed available stock.
4. Total = sum of unit price multiplied by quantity.
5. Stock is reduced only after the complete order is successfully saved.
6. Store the unit price at the time of sale so historical bills remain accurate.

### 7. Expected outcome
The system reduces manual billing and stock-recording work and provides transaction data for sales analysis.
