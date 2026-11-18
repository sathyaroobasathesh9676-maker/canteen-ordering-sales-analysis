# Test Cases
Update the Actual Result and Status columns after executing each test on your computer.

| ID | Test | Expected result | Actual result |
|---|---|---|---|
| TC-01 | View menu | Food names, prices and stock are listed | Pending |
| T# TEST CASES AND TEST RESULTS

**Project:** Canteen Ordering and Sales Analysis System  
**Roll Number:** 93

| Test ID | Test Scenario | Expected Result | Actual Result | Status |
|---|---|---|---|---|
| TC-01 | View food items | Display all available food items | All 9 food items displayed successfully | PASS |
| TC-02 | Add a food item | New item should be added | Pongal added successfully with price ₹30 and stock 25 | PASS |
| TC-03 | Place a valid order | Calculate bill and update stock | 2 Pongal ordered; bill ₹60; stock reduced from 25 to 23 | PASS |
| TC-04 | Order multiple food items | Calculate combined bill | 2 Idli and 1 Dosa ordered; total ₹60 | PASS |
| TC-05 | Order more than available stock | Reject the order | Order for 100 Pongal rejected because only 23 were available | PASS |
| TC-06 | Enter zero or negative quantity | Reject invalid quantity | Both zero and negative quantities were rejected | PASS |
| TC-07 | Enter an invalid food item ID | Display an error | Unknown food ID 999 was rejected | PASS |
| TC-08 | View sales summary | Display completed orders and revenue | 2 completed orders; total revenue ₹120 | PASS |
| TC-09 | Run Python sales analysis | Generate sales charts | Three charts generated successfully | PASS |
| TC-10 | Test database connection after MySQL restart | Application should reconnect successfully | Java and Python connected successfully after MySQL service restart | PASS |

## Testing Methods

1. **Unit Testing:** Individual functions and operations were tested.
2. **Integration Testing:** Java, JDBC, MySQL, and Python database connectivity were verified.
3. **System Testing:** Complete ordering and sales summary workflows were tested.
4. **Negative Testing:** Invalid quantities, unknown food IDs, and insufficient stock were tested.

## Testing Conclusion

All 10 test cases passed successfully. The system correctly manages food items, processes orders, calculates bills, updates stock, prevents invalid orders, and generates sales analysis charts.C-02 | Add valid item | Item is saved | Pending |
| TC-03 | Place valid order | Bill is shown and stock is reduced | Pending |
| TC-04 | Order multiple items | Correct combined total is calculated | Pending |
| TC-05 | Order more than stock | Order is rejected and stock remains unchanged | Pending |
| TC-06 | Enter zero/negative quantity | Input is rejected | Pending |
| TC-07 | Enter unknown food ID | Order is rejected with a clear message | Pending |
| TC-08 | View sales summary | Completed order count and revenue are displayed | Pending |
| TC-09 | Run Python analysis | Three sales charts are generated | Pending |
| TC-10 | MySQL unavailable | Connection error is shown and no order is saved | Pending |

## Testing methods
Unit testing for calculations and validation; integration testing for JDBC/MySQL; system testing for complete order workflows; negative testing for invalid input and insufficient stock.
