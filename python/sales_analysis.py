
from pathlib import Path
import pandas as pd
import matplotlib.pyplot as plt
import mysql.connector

from db_config import DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASSWORD

ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / "sample_outputs"
OUTPUT.mkdir(exist_ok=True)


def main():
    connection = None

    try:
        connection = mysql.connector.connect(
            host=DB_HOST,
            port=DB_PORT,
            database=DB_NAME,
            user=DB_USER,
            password=DB_PASSWORD
        )

        print("Connected to Canteen Database!")

        sql = """
        SELECT
            o.order_id,
            o.order_date,
            f.item_name AS food_name,
            oi.quantity,
            oi.unit_price,
            oi.line_total
        FROM orders o
        JOIN order_items oi ON oi.order_id = o.order_id
        JOIN food_items f ON f.item_id = oi.item_id
        WHERE o.order_status = 'COMPLETED'
        ORDER BY o.order_date
        """

        cursor = connection.cursor(dictionary=True)
        cursor.execute(sql)
        rows = cursor.fetchall()
        cursor.close()

        df = pd.DataFrame(rows)

        if df.empty:
            print("No completed sales found.")
            return

        df["order_date"] = pd.to_datetime(df["order_date"])
        df["quantity"] = pd.to_numeric(df["quantity"])
        df["line_total"] = pd.to_numeric(df["line_total"])

        # 1. Item-wise sales
        item_sales = (
            df.groupby("food_name")["quantity"]
            .sum()
            .sort_values(ascending=False)
        )

        item_sales.plot(
            kind="bar",
            title="Item-wise Sales",
            xlabel="Food Item",
            ylabel="Quantity Sold"
        )

        plt.tight_layout()
        plt.savefig(OUTPUT / "item_wise_sales.png", dpi=150)
        plt.close()

        # 2. Daily revenue
        daily = (
            df.set_index("order_date")
            .resample("D")["line_total"]
            .sum()
        )

        daily.plot(
            kind="line",
            marker="o",
            title="Daily Revenue",
            xlabel="Date",
            ylabel="Revenue (Rs.)"
        )

        plt.tight_layout()
        plt.savefig(OUTPUT / "daily_revenue.png", dpi=150)
        plt.close()

        # 3. Popular food items
        item_sales.head(5).plot(
            kind="pie",
            autopct="%1.1f%%",
            title="Popular Food Items",
            ylabel=""
        )

        plt.tight_layout()
        plt.savefig(OUTPUT / "popular_food_items.png", dpi=150)
        plt.close()

        print("\nSALES ANALYSIS COMPLETED!")
        print("\nItem-wise Sales:")
        print(item_sales)
        print("\nDaily Revenue:")
        print(daily)
        print("\nCharts saved in:", OUTPUT)

    except Exception as error:
        print("Error:", error)

    finally:
        if connection and connection.is_connected():
            connection.close()


if __name__ == "__main__":
    main()
