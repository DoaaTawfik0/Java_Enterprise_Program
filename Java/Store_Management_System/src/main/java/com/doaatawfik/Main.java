final int MAX_PRODUCTS = 100;

int productCount = 0;

int[] productCodes = new int[MAX_PRODUCTS];
String[] productNames = new String[MAX_PRODUCTS];
double[] productPrices = new double[MAX_PRODUCTS];
int[] stockQuantities = new int[MAX_PRODUCTS];
int[] soldQuantities = new int[MAX_PRODUCTS];

Scanner scanner = new Scanner(System.in);

// ==========================================
// 1. MAIN METHOD AND MENU
// ==========================================

void main(String[] args) {

    int choice;

    do {
        printMenu();
        choice = enterChoice();

        switch (choice) {

            case 1:
                addNewProduct();
                break;

            case 2:
                displayAllProducts();
                break;

            case 3:
                sellProduct();
                break;

            case 4:
                restockProduct();
                break;

            case 5:
                searchProductByCode();
                break;

            case 6:
                displayLowStockProducts();
                break;

            case 7:
                displayTotalInventoryValue();
                break;

            case 8:
                displaySalesReport();
                break;

            case 9:
                displayBestSellingProduct();
                break;

            case 0:
                System.out.println("Exiting Store Management System...");
                break;

            default:
                System.out.println("Invalid choice! Please try again.");
        }

        System.out.println();

    } while (choice != 0);

    scanner.close();
}

void printMenu() {

    System.out.println("===== STORE MANAGEMENT SYSTEM =====");
    System.out.println("1. Add New Product");
    System.out.println("2. Display All Products");
    System.out.println("3. Sell Product");
    System.out.println("4. Restock Product");
    System.out.println("5. Search Product by Code");
    System.out.println("6. Show Low Stock Alert (quantity < 5)");
    System.out.println("7. Calculate Total Inventory Value");
    System.out.println("8. Show Sales Report");
    System.out.println("9. Show Best Selling Product");
    System.out.println("0. Exit");
}

int enterChoice() {
    System.out.print("Enter your choice: ");
    return Integer.parseInt(scanner.nextLine());
}

// ==========================================
// 2. ADD NEW PRODUCT
// ==========================================

void addNewProduct() {

    if (productCount >= MAX_PRODUCTS) {
        System.out.println("ERROR: Maximum stock capacity reached!");
        return;
    }

    int code = enterProductCode();

    if (!validateProductCode(code)) {
        return;
    }

    String name = enterProductName();

    if (!validateProductName(name)) {
        return;
    }

    double price = enterProductPrice();

    if (!validateProductPrice(price)) {
        return;
    }

    int quantity = enterProductQuantity();

    if (!validateProductQuantity(quantity)) {
        return;
    }

    // Store product data directly in the arrays
    productCodes[productCount] = code;
    productNames[productCount] = name;
    productPrices[productCount] = price;
    stockQuantities[productCount] = quantity;
    soldQuantities[productCount] = 0;

    productCount++;

    System.out.println("Product added successfully!");
}

// ==========================================
// 3. SELL AND RESTOCK PRODUCTS
// ==========================================

void sellProduct() {

    int code = enterProductCode();
    int index = getProductIndex(code);

    if (index == -1) {
        System.out.println("ERROR: Product not found!");
        return;
    }

    int quantity = enterProductQuantity();

    if (!validateProductQuantity(quantity) || quantity == 0) {
        System.out.println("ERROR: Quantity must be greater than zero!");
        return;
    }

    if (!hasEnoughStock(index, quantity)) {
        return;
    }

    stockQuantities[index] -= quantity;
    soldQuantities[index] += quantity;

    double totalPrice = productPrices[index] * quantity;

    System.out.println("\n========== Selling Confirmation ==========");
    System.out.println("Product: " + productNames[index]);
    System.out.println("Quantity Sold: " + quantity);
    System.out.printf("Total Price: %.2f%n", totalPrice);
    System.out.println("Remaining Stock: " + stockQuantities[index]);
    System.out.println("==========================================");
}

void restockProduct() {

    int code = enterProductCode();
    int index = getProductIndex(code);

    if (index == -1) {
        System.out.println("ERROR: Product not found!");
        return;
    }

    int quantity = enterProductQuantity();

    if (!validateProductQuantity(quantity) || quantity == 0) {
        System.out.println("ERROR: Quantity must be greater than zero!");
        return;
    }

    stockQuantities[index] += quantity;

    System.out.println("Product restocked successfully!");
    System.out.println("New Stock: " + stockQuantities[index]);
}

boolean hasEnoughStock(int index, int quantity) {

    if (stockQuantities[index] < quantity) {
        System.out.println("ERROR: Only " +
                stockQuantities[index] + " units are available!");
        return false;
    }

    return true;
}

// ==========================================
// 4. SEARCH AND DISPLAY PRODUCTS
// ==========================================

void searchProductByCode() {

    int code = enterProductCode();
    int index = getProductIndex(code);

    if (index == -1) {
        System.out.println("ERROR: Product not found!");
        return;
    }

    displayProductDetails(index);
}

int getProductIndex(int code) {

    for (int i = 0; i < productCount; i++) {

        if (productCodes[i] == code) {
            return i;
        }
    }

    return -1;
}

void displayAllProducts() {

    if (productCount == 0) {
        System.out.println("No products in the system.");
        return;
    }

    System.out.printf("%-10s %-20s %10s %10s %10s%n",
            "Code", "Name", "Price", "Stock", "Sold");

    System.out.println("================================================================");

    for (int i = 0; i < productCount; i++) {

        System.out.printf("%-10d %-20s %10.2f %10d %10d%n",
                productCodes[i],
                productNames[i],
                productPrices[i],
                stockQuantities[i],
                soldQuantities[i]);
    }

    System.out.println("================================================================");
    System.out.println("Total Products: " + productCount);
}

void displayProductDetails(int index) {

    double revenue = productPrices[index] * soldQuantities[index];

    System.out.println("\n========== Product Details ==========");
    System.out.println("Code: " + productCodes[index]);
    System.out.println("Name: " + productNames[index]);
    System.out.printf("Price: %.2f%n", productPrices[index]);
    System.out.println("Stock: " + stockQuantities[index]);
    System.out.println("Sold: " + soldQuantities[index]);
    System.out.printf("Total Revenue: %.2f%n", revenue);
    System.out.println("=====================================");
}

// ==========================================
// 5. INVENTORY REPORTS
// ==========================================

void displayLowStockProducts() {

    int count = 0;

    System.out.printf("%-10s %-20s %10s%n",
            "Code", "Name", "Stock");

    System.out.println("==========================================");

    for (int i = 0; i < productCount; i++) {

        if (stockQuantities[i] < 5) {

            System.out.printf("%-10d %-20s %10d%n",
                    productCodes[i],
                    productNames[i],
                    stockQuantities[i]);

            count++;
        }
    }

    if (count == 0) {
        System.out.println("All products are well stocked!");
    } else {
        System.out.println("==========================================");
        System.out.println("Total Low Stock Items: " + count);
    }
}

void displayTotalInventoryValue() {

    if (productCount == 0) {
        System.out.println("No inventory available.");
        return;
    }

    double inventoryValue = 0;

    for (int i = 0; i < productCount; i++) {
        inventoryValue += productPrices[i] * stockQuantities[i];
    }

    System.out.printf("Total Inventory Value: %.2f%n", inventoryValue);
}

// ==========================================
// 6. SALES REPORTS
// ==========================================

void displaySalesReport() {

    int totalUnitsSold = 0;
    double totalRevenue = 0;

    for (int i = 0; i < productCount; i++) {

        totalUnitsSold += soldQuantities[i];

        totalRevenue += productPrices[i] * soldQuantities[i];
    }

    System.out.println("\n========== Sales Report ==========");

    System.out.println("Total Units Sold: " + totalUnitsSold);

    System.out.printf("Total Revenue: %.2f%n", totalRevenue);

    if (totalUnitsSold == 0) {
        System.out.println("No sales recorded yet.");
    } else {

        double averageSaleValue = totalRevenue / totalUnitsSold;

        System.out.printf("Average Revenue Per Unit: %.2f%n",
                averageSaleValue);
    }

    System.out.println("==================================");
}

void displayBestSellingProduct() {

    int maxIndex = -1;

    for (int i = 0; i < productCount; i++) {

        if (soldQuantities[i] > 0 &&
                (maxIndex == -1 ||
                        soldQuantities[i] > soldQuantities[maxIndex])) {

            maxIndex = i;
        }
    }

    if (maxIndex == -1) {
        System.out.println("No sales recorded yet.");
        return;
    }

    System.out.println("\n========== Best Selling Product ==========");

    displayProductDetails(maxIndex);

    System.out.println("==========================================");
}

// ==========================================
// 7. INPUT METHODS
// ==========================================

int enterProductCode() {
    System.out.print("Enter Product Code: ");
    return Integer.parseInt(scanner.nextLine());
}

String enterProductName() {
    System.out.print("Enter Product Name: ");
    return scanner.nextLine();
}

double enterProductPrice() {
    System.out.print("Enter Product Price: ");
    return Double.parseDouble(scanner.nextLine());
}

int enterProductQuantity() {
    System.out.print("Enter Product Quantity: ");
    return Integer.parseInt(scanner.nextLine());
}

// ==========================================
// 8. VALIDATION METHODS
// ==========================================

boolean validateProductCode(int code) {

    for (int i = 0; i < productCount; i++) {

        if (productCodes[i] == code) {
            System.out.println("ERROR: Product code already exists!");
            return false;
        }
    }

    return true;
}

boolean validateProductName(String name) {

    if (name.isBlank()) {
        System.out.println("ERROR: Product name cannot be empty!");
        return false;
    }

    return true;
}

boolean validateProductPrice(double price) {

    if (price <= 0) {
        System.out.println("ERROR: Product price must be greater than 0!");
        return false;
    }

    return true;
}

boolean validateProductQuantity(int quantity) {

    if (quantity < 0) {
        System.out.println("ERROR: Product quantity cannot be negative!");
        return false;
    }

    return true;
}

