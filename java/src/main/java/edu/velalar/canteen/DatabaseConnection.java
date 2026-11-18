package edu.velalar.canteen;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
public final class DatabaseConnection {
 private DatabaseConnection() {}
 public static Connection getConnection() throws Exception {
  Properties p = new Properties();
  try (InputStream in = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
   if (in == null) throw new IllegalStateException("db.properties not found");
   p.load(in);
  }
  String password = System.getenv().getOrDefault("CANTEEN_DB_PASSWORD", p.getProperty("db.password", ""));
  return DriverManager.getConnection(p.getProperty("db.url"), p.getProperty("db.username"), password);
 }
}
