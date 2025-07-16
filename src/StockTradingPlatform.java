import java.util.*;

class Stock {
    String symbol; //"AAPL" or "GOOG" or "TSLA"...
    String name;
    double price;

    Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }

    void updatePrice(double newPrice) {
        this.price = newPrice;
    }
}

class Transaction {
    String type; // "BUY" or "SELL"
    Stock stock;
    int quantity;
    double totalPrice;

    Transaction(String type, Stock stock, int quantity) {
        this.type = type;
        this.stock = stock;
        this.quantity = quantity;
        this.totalPrice = quantity * stock.price;
    }

    @Override
    public String toString() {
        return type + " - " + stock.symbol + " - Quantity: " + quantity + " - Total: $" + totalPrice;
    }
}

class User {
    String name;
    double balance;
    Map<String, Integer> portfolio;
    List<Transaction> transactionHistory;

    User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.portfolio = new HashMap<>();
        this.transactionHistory = new ArrayList<>();
    }

    void buyStock(Stock stock, int quantity) {
        double cost = stock.price * quantity;
        if (cost > balance) {
            System.out.println("Not enough balance to buy " + quantity + " shares of " + stock.symbol);
            return;
        }
        balance -= cost;
        portfolio.put(stock.symbol, portfolio.getOrDefault(stock.symbol, 0) + quantity);
        transactionHistory.add(new Transaction("BUY", stock, quantity));
        System.out.println("Successfully bought " + quantity + " shares of " + stock.symbol);
    }

    void sellStock(Stock stock, int quantity) {
        int owned = portfolio.getOrDefault(stock.symbol, 0);
        if (quantity > owned) {
            System.out.println("Not enough shares to sell.");
            return;
        }
        balance += stock.price * quantity;
        portfolio.put(stock.symbol, owned - quantity);
        transactionHistory.add(new Transaction("SELL", stock, quantity));
        System.out.println("Successfully sold " + quantity + " shares of " + stock.symbol);
    }

    void displayPortfolio(Map<String, Stock> market) {
        System.out.println("\n--- Portfolio of " + name + " ---");
        System.out.println("Balance: $" + balance);
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            String symbol = entry.getKey();
            int quantity = entry.getValue();
            Stock stock = market.get(symbol);
            System.out.println(symbol + " - " + quantity + " shares @ $" + stock.price + " = $" + (stock.price * quantity));
        }
    }

    void displayTransactions() {
        System.out.println("\n--- Transaction History ---");
        for (Transaction t : transactionHistory) {
            System.out.println(t);
        }
    }
}

class Market {
    Map<String, Stock> stocks;

    Market() {
        stocks = new HashMap<>();
        // Initial Stocks
        stocks.put("AAPL", new Stock("AAPL", "Apple Inc.", 180.00));
        stocks.put("GOOG", new Stock("GOOG", "Alphabet Inc.", 2900.00));
        stocks.put("TSLA", new Stock("TSLA", "Tesla Inc.", 720.00));
    }

    void displayMarket() {
        System.out.println("\n--- Stock Market ---");
        for (Stock s : stocks.values()) {
            System.out.println(s.symbol + " - " + s.name + " - $" + s.price);
        }
    }

    Stock getStock(String symbol) {
        return stocks.get(symbol.toUpperCase());
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Market market = new Market();

        System.out.print("Enter your name: ");
        String username = sc.nextLine();

        User user = new User(username, 10000.00); // initial balance

        while (true) {
            System.out.println("\n==== Stock Trading Menu ====");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    market.displayMarket();
                    break;
                case 2:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = sc.nextLine();
                    Stock stockToBuy = market.getStock(buySymbol);
                    if (stockToBuy == null) {
                        System.out.println("Stock not found.");
                        break;
                    }
                    System.out.print("Enter quantity to buy: ");
                    int buyQty = sc.nextInt();
                    sc.nextLine();
                    user.buyStock(stockToBuy, buyQty);
                    break;
                case 3:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = sc.nextLine();
                    Stock stockToSell = market.getStock(sellSymbol);
                    if (stockToSell == null) {
                        System.out.println("Stock not found.");
                        break;
                    }
                    System.out.print("Enter quantity to sell: ");
                    int sellQty = sc.nextInt();
                    sc.nextLine();
                    user.sellStock(stockToSell, sellQty);
                    break;
                case 4:
                    user.displayPortfolio(market.stocks);
                    break;
                case 5:
                    user.displayTransactions();
                    break;
                case 6:
                    System.out.println("Thank you for using the Stock Trading Platform!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
