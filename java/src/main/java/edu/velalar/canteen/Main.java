package edu.velalar.canteen;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
public class Main {
 public static void main(String[] args) {
  CanteenService s=new CanteenService();
  try(Scanner in=new Scanner(System.in)) {
   while(true) {
    System.out.println("\n=== CANTEEN ORDERING SYSTEM ===");
    System.out.println("1. View food items\n2. Add food item\n3. Place order\n4. Sales summary\n0. Exit");
    System.out.print("Choose: "); String choice=in.nextLine().trim();
    try {
     switch(choice) {
      case "1" -> s.listFoodItems();
      case "2" -> { System.out.print("Food name: "); String n=in.nextLine(); System.out.print("Price: "); BigDecimal p=new BigDecimal(in.nextLine()); System.out.print("Stock: "); int q=Integer.parseInt(in.nextLine()); s.addFoodItem(n,p,q); System.out.println("Item added."); }
      case "3" -> { s.listFoodItems(); Map<Integer,Integer> items=new LinkedHashMap<>(); while(true) { System.out.print("Food ID (0 to finish): "); int id=Integer.parseInt(in.nextLine()); if(id==0) break; System.out.print("Quantity: "); int q=Integer.parseInt(in.nextLine()); items.merge(id,q,Integer::sum); } s.placeOrder(items); }
      case "4" -> s.showSalesSummary();
      case "0" -> { System.out.println("Goodbye."); return; }
      default -> System.out.println("Invalid option.");
     }
    } catch(NumberFormatException e) { System.out.println("Enter a valid number."); }
      catch(Exception e) { System.out.println("Error: "+e.getMessage()); }
   }
  }
 }
}
