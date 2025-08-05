import java.io.*;
import java.util.* ;

public class SupplyChainApp {
    public static void main(String[] args) {
        // Create sample products
        CatalogProduct product1 = new CatalogProduct("Laptop", "Electronics", 1);
        CatalogProduct product2 = new CatalogProduct("Phone", "Electronics", 2);
        CatalogProduct product3 = new CatalogProduct("Tablet", "Electronics", 3);

        // Create sample quantities
        int[] quantities = {10, 20, 15};

        // Create sample transactions
        SalesTransaction[] transactions = new SalesTransaction[10];

        // Create a retailer
        ProductRetailer retailer = new ProductRetailer("Retailer1", new CatalogProduct[]{product1, product2}, new int[]{5, 10}, 101, transactions);

        // Create a warehouse operator
        WarehouseOperator warehouseOperator = new WarehouseOperator("Warehouse1", new CatalogProduct[]{product1, product2, product3}, quantities, 201, transactions);

        // Assign the warehouse operator to the retailer
        retailer.warehouseOperator = warehouseOperator;

        // Test sending an order request
        retailer.sendOrderRequest(product1, 5);

        // Test adding a transaction
        retailer.addTransaction("Order1", "Warehouse1", product1, 5, 500.0);

        // Create a supplier
        ProductSupplier supplier = new ProductSupplier("Supplier1", new CatalogProduct[]{product1, product2, product3}, quantities, 301, transactions);

        // Assign suppliers to the warehouse operator
        warehouseOperator.setSuppliers(new ProductSupplier[]{supplier});

        // Test supplier stock reduction
        supplier.reduceStock(product1, 5);

        // Test warehouse operator filling orders
        PurchaseOrder order1 = new PurchaseOrder(new PricedProduct("Laptop", "Electronics", 1, 1000.0), 5);
        PurchaseOrder order2 = new PurchaseOrder(new PricedProduct("Phone", "Electronics", 2, 800.0), 10);
        warehouseOperator.addOrder(order1);
        warehouseOperator.addOrder(order2);

        System.out.println("Warehouse operator starts filling orders...");
        warehouseOperator.fillOrders();

        // Display profit or loss
        System.out.println("\nCalculating profit or loss...");
        warehouseOperator.displayProfitOrLoss();

        // Test admin functionality
        SystemAdministrator admin = new SystemAdministrator("Admin1", 401);
        admin.addUser("Retailer", retailer);
        admin.addUser("WarehouseOperator", warehouseOperator);
        admin.addUser("ProductSupplier", supplier);
        admin.viewAllStockLevels();
        admin.generateReport();

        // Test bar graph generation (placeholder)
        System.out.println("Generating bar graph for stock levels...");
        generateBarGraph(warehouseOperator);
    }

    // Placeholder for bar graph generation
    public static void generateBarGraph(WarehouseOperator warehouseOperator) {
        CatalogProduct[] products = warehouseOperator.getTargetProducts();
        int[] quantities = warehouseOperator.getQuantities();
        System.out.println("Stock Levels:");
        for (int i = 0; i < products.length; i++) {
            System.out.print(products[i].getName() + ": ");
            for (int j = 0; j < quantities[i]; j++) {
                System.out.print("|");
            }
            System.out.println(" (" + quantities[i] + ")");
        }
    }
}
