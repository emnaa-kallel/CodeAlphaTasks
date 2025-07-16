<<<<<<< HEAD
## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
=======
🎓 Task 1 : Student Grade Tracker

A simple Java console application to collect, validate, and analyze students' grades.


🧠 Features

- Input student names and their corresponding grades.
- Validates grade input (must be between 0 and 20).
- Calculates:
  - Average grade
  - Highest and lowest grade
  - Students associated with the highest and lowest grades
- Displays a grade summary in a user-friendly format.

🚀 How It Works

1. The program prompts the user to enter the number of students.
2. For each student, the user inputs:
   - Student name
   - Grade (must be between 0 and 20)
3. After all data is entered, the program:
   - Calculates the average grade
   - Identifies the highest and lowest grades
4. Outputs a summary with all calculated statistics.


💹 Task 2 : Stock Trading Platform

A Java console application that simulates a basic stock trading platform where users can buy and sell stocks, view their portfolio, and track transaction history.

🚀 Features

- 📈 View live market with predefined stocks (`AAPL`, `GOOG`, `TSLA`)
- 💵 Buy and sell stocks with real-time price updates
- 📊 Track your portfolio: shares owned, current value, and balance
- 🧾 Maintain a full transaction history (BUY/SELL)
- 🔒 Input validation for balance and ownership


🧠 How It Works

1. User starts with a default balance of $10,000.
2. User can:
   - View available stocks and their prices
   - Buy stocks if they have enough balance
   - Sell stocks they own
   - View portfolio with real-time valuation
   - View detailed transaction history
3. Prices can be updated programmatically (currently fixed)


 🏨Task 3 :  Hotel Reservation System

A Java-based console application for managing hotel room bookings, cancellations, and customer reservations with simple payment simulation and data persistence using file serialization.


📋 Features

- 🔍 Search for available rooms by category (Standard, Deluxe, Suite)
- 📝 Book rooms with check-in/check-out dates and payment simulation
- ❌ Cancel existing reservations
- 📄 View reservation details by guest name
- 💾 Persistent storage of reservations (`reservations.ser` file)
>>>>>>> ec9a1387b88e099a01e1ab9baf2fc77a1b614fd4
