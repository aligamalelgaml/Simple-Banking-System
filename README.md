# Simple-Banking-System

The provided code consists of three classes: Main, Userinterface, and Bank. The Main class contains the main method, which creates an instance of the Userinterface class and an instance of the Bank class, passing the second command-line argument to the Bank constructor. The start method of the Userinterface class is then called, passing the Bank instance.

The Userinterface class provides a simple command-line interface for interacting with the bank. The start method takes a Bank instance as a parameter and sets up a loop that prints a menu of options to the console, reads input from the user, and takes appropriate action based on the input. The available options are:

1.   Create an account
2.   Log into account
0.  Exit

If the user chooses option 1, a new credit card is created and added to the bank's database. If the user chooses option 2, they are prompted to enter a card number and PIN. If the combination of card number and PIN is valid, the user is logged in and presented with a new menu of options:

1. Balance
2. Add income
3.  Do transfer
4.  Close account
5.  Log out
0.  Exit

If the user chooses option 1, their account balance is printed to the console. If they choose option 2, they are prompted to enter an amount to add to their balance. If they choose option 3, they are prompted to enter a recipient card number and an amount to transfer. If the transfer is successful, the appropriate account balances are updated. If they choose option 4, their account is closed and removed from the database. If they choose option 5, they are logged out and returned to the previous menu. If they choose option 0, the program exits.

The Bank class provides methods for creating new credit cards, logging in with a card number and PIN, checking if a card number is valid, retrieving an account balance, changing an account balance, transferring money between accounts, and closing an account. It uses an SQLite database to store the credit card information. The Bank constructor takes a path to the database file as a parameter and creates the card table if it doesn't exist.
