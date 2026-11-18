\# Canteen Ordering and Sales Analysis System



\*\*Scenario 3 – Roll No. 93\*\*



\*\*Velalar College of Engineering and Technology\*\*  

Department of Information Technology  

Academic Year 2026–2027



\## 1. Project Overview



The Canteen Ordering and Sales Analysis System is a web-based application developed using Java, Spring Boot, MySQL and Python.



It helps manage canteen food items, process customer orders, calculate bills, update stock quantities and analyze sales.



The system prevents orders that exceed available stock and generates visual reports to understand food-item sales, daily revenue and popular food items.



\## 2. Technologies Used



\- Programming Language: Java

\- Web Framework: Spring Boot

\- Frontend: HTML, CSS, Thymeleaf

\- Database: MySQL

\- Database Connectivity: JDBC

\- Build Tool: Apache Maven

\- Data Analysis: Python 3.13

\- Python Libraries: Pandas, Matplotlib, Seaborn

\- Development Environment: Visual Studio Code



\## 3. Main Features



1\. View available food items.

2\. Add new food items with prices and stock quantities.

3\. Place orders containing multiple food items.

4\. Automatically calculate the total bill.

5\. Validate stock availability before accepting orders.

6\. Automatically update stock after successful orders.

7\. Store order and transaction details in MySQL.

8\. Display completed-order count and total revenue.

9\. Analyze sales data using Python.

10\. Generate item-wise sales, daily revenue and popular food-item charts.

11\. Display sales analysis through a web dashboard.



\## 4. Project Structure



```text

Canteen-Ordering-Sales-Analysis/

|

|-- database/

|   `-- canteen.sql

|

|-- documentation/

|   |-- SRS.md

|   |-- diagrams.md

|   |-- database-design.md

|   `-- test-cases.md

|

|-- java/

|   |-- src/main/java/

|   |-- src/main/resources/

|   |   |-- templates/

|   |   |-- static/charts/

|   |   |-- application.properties

|   |   `-- db.properties.example

|   `-- pom.xml

|

|-- python/

|   |-- db\_config.py.example

|   |-- requirements.txt

|   `-- sales\_analysis.py

|

|-- sample\_outputs/

|   |-- daily\_revenue.png

|   |-- item\_wise\_sales.png

|   `-- popular\_food\_items.png

|

|-- .gitignore

`-- README.md

```



\## 5. Database Setup



1\. Open MySQL Workbench.

2\. Connect to your MySQL server.

3\. Open `database/canteen.sql`.

4\. Execute the SQL script to create the database and tables.

5\. Configure the database credentials in the Java and Python configuration files.



\## 6. Java Web Application Setup



1\. Install JDK 17 or later.

2\. Install Apache Maven.

3\. Copy `java/src/main/resources/db.properties.example` to `java/src/main/resources/db.properties`.

4\. Update the database username and password in `db.properties`.

5\. Open a terminal inside the `java` folder.

6\. Compile the application:



```bash

mvn clean compile

```



7\. Start the Spring Boot application:



```bash

mvn spring-boot:run

```



8\. Open the website in your browser:



```text

http://localhost:8080

```



The website provides access to the food menu, order placement and sales analysis dashboard.



\## 7. Python Sales Analysis Setup



1\. Install Python 3.13.

2\. Open a terminal inside the project root folder.

3\. Install the required libraries:



```bash

py -3.13 -m pip install -r python/requirements.txt

```



4\. Copy `python/db\_config.py.example` to `python/db\_config.py`.

5\. Update the MySQL username and password in `db\_config.py`.

6\. Run the sales analysis:



```bash

py -3.13 python/sales\_analysis.py

```



The generated charts are saved in:



\- `sample\_outputs/`

\- `java/src/main/resources/static/charts/`



The website dashboard can display the updated charts after refreshing the browser.



\## 8. Sample Outputs



The Python analysis generates three visualizations:



\- `item\_wise\_sales.png` – Displays quantities sold for each food item.

\- `daily\_revenue.png` – Displays revenue generated each day.

\- `popular\_food\_items.png` – Displays the most popular food items.



The charts are displayed on the Sales Analysis dashboard.



\## 9. Testing



The application has been tested for:



\- Viewing food items.

\- Adding new food items.

\- Placing single-item and multiple-item orders.

\- Automatic bill calculation.

\- Stock reduction after successful orders.

\- Rejection of orders exceeding available stock.

\- Rejection of zero and negative quantities.

\- Rejection of invalid food-item IDs.

\- Sales summary generation.

\- Python sales analysis and chart generation.

\- Database connection failure handling.

\- Website display of sales charts.



Refer to `documentation/test-cases.md` for detailed test cases and results.



\## 10. Security Note



\- Never upload actual database passwords or credentials to GitHub.

\- Local files `db.properties` and `db\_config.py` are excluded using `.gitignore`.

\- Use the `.example` configuration files as templates.

\- Enter your own database credentials locally before running the application.



\## 11. Expected Outcome



The project provides a working canteen ordering and sales analysis solution integrating Java, Spring Boot, MySQL and Python.



It simplifies order processing, improves stock management and provides useful sales insights through an interactive web dashboard and data visualizations.



\*\*Project:\*\* Canteen Ordering and Sales Analysis System  

\*\*Roll Number:\*\* 93

