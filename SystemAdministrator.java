

import java.io.*;
import java.util.*;

interface SystemMonitor {
    void generateReport();
    void alertLowStock();
}

public class SystemAdministrator implements SystemMonitor {
    String name;
    int id;
    Map<String, List<SupplyChainActor>> usersByRole = new HashMap<>();

    public SystemAdministrator(String name, int id) {
        this.name = name;
        this.id = id;
        usersByRole.put("Retailer", new ArrayList<>());
        usersByRole.put("WarehouseOperator", new ArrayList<>());
        usersByRole.put("ProductSupplier", new ArrayList<>());
    }

    public void addUser(String role, SupplyChainActor user) {
        usersByRole.get(role).add(user);
    }

    public void addUser(String role, SupplyChainActor... users) {
        usersByRole.get(role).addAll(Arrays.asList(users));
    }

    public void viewAllStockLevels() {
        for (String role : usersByRole.keySet()) {
            System.out.println("-- " + role + " --");
            for (SupplyChainActor p : usersByRole.get(role)) {
                System.out.println(p.getName() + "'s Stock:\n" + p.stockUpdate());
            }
        }
    }

    public void viewStockGraph() {
        for (String role : usersByRole.keySet()) {
            System.out.println("-- " + role + " --");
            for (SupplyChainActor p : usersByRole.get(role)) {
                System.out.println(p.getName() + "'s Stock:");
                for (int i = 0; i < p.products.length; i++) {
                    System.out.printf("%-15s | %s%n", 
                        p.products[i].getName(), 
                        "*".repeat(p.quantity[i]));
                }
            }
        }
    }

    @Override
    public void generateReport() {
        try (FileWriter fw = new FileWriter("inventory_report.txt")) {
            for (String role : usersByRole.keySet()) {
                fw.write("Role: " + role + "\n");
                for (SupplyChainActor p : usersByRole.get(role)) {
                    fw.write("User: " + p.getName() + "\n" + p.stockUpdate() + "\n");
                }
            }
            System.out.println("Inventory report generated.");
        } catch (IOException e) {
            System.err.println("Error writing report: " + e.getMessage());
        }
    }

    @Override
    public void alertLowStock() {
        for (String role : usersByRole.keySet()) {
            for (SupplyChainActor p : usersByRole.get(role)) {
                for (int i = 0; i < p.products.length; i++) {
                    if (p.quantity[i] < 5) {
                        System.out.println("Low stock alert: " + 
                            p.products[i].getName() + " for " + p.getName());
                    }
                }
            }
        }
    }

    static class AdminUtils {
        public static void log(String message) {
            try (FileWriter fw = new FileWriter("admin_log.txt", true)) {
                fw.write(new java.util.Date() + ": " + message + "\n");
            } catch (IOException e) {
                System.err.println("Logging failed: " + e.getMessage());
            }
        }
    }

    public void predictDemand() {
        System.out.println("Predicting future demand using sales history...");
    }
}
