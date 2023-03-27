package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class Bank {
    SQLiteDataSource sqlDatabase;

    public Bank(String pathToDatabase) {
        String URL = "jdbc:sqlite:" + pathToDatabase;
        this.sqlDatabase = new SQLiteDataSource();
        sqlDatabase.setUrl(URL);

        try (Connection con = sqlDatabase.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card"
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " number TEXT NOT NULL,"
                + " pin TEXT NOT NULL,"
                + " balance INTEGER DEFAULT 0);");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public CreditCard createCreditCard() {
        CreditCard creditCard = new CreditCard();

        try (Connection con = sqlDatabase.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("INSERT INTO card (number, pin)" +
                        "VALUES ('" + creditCard.number + "', '" + creditCard.pin + "');");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return creditCard;
    }

    public CreditCard login(String accountNumber, String pin) {
        String getCreditCard = "SELECT * FROM card WHERE number = ? AND pin = ?";

        try (Connection con = sqlDatabase.getConnection()) {
            try (PreparedStatement preparedStatement = con.prepareStatement(getCreditCard)) {
                preparedStatement.setString(1, accountNumber);
                preparedStatement.setString(2, pin);
                ResultSet creditCards = preparedStatement.executeQuery();

                if (creditCards.next()) {
                        return new CreditCard(accountNumber, pin);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public boolean checkCard(String inputCardNumber) {
        StringBuilder cardToCheck = new StringBuilder(inputCardNumber.substring(0, inputCardNumber.length() - 1));
        cardToCheck.append(CreditCard.generateChecksum(cardToCheck.toString()));

        if (!inputCardNumber.equals(cardToCheck.toString())) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            return false;
        }

        String checkCreditCardStatement = "SELECT * FROM card WHERE number = ?";

        try (Connection con = sqlDatabase.getConnection()) {
            try (PreparedStatement preparedStatement = con.prepareStatement(checkCreditCardStatement)) {
                preparedStatement.setString(1, inputCardNumber);
                ResultSet creditCards = preparedStatement.executeQuery();

                if (creditCards.next()) {
                    return true;
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        System.out.println("Such a card does not exist.");
        return false;
    }

    public int getBalance(String accountNumber, String pin) {
        CreditCard creditCard = this.login(accountNumber, pin);
        return creditCard.getBalance();
    }

    public boolean changeBalance(String accountNumber, String inputMoney, String cmd) {
        String preparedUpdateBalance;

        if (cmd.equals("deposit")) {
            preparedUpdateBalance = "UPDATE card SET balance = balance + ? WHERE number = ?";
        } else if (cmd.equals("withdraw")) {
            preparedUpdateBalance = "UPDATE card SET balance = balance - ? WHERE number = ?";
        } else {
            return false;
        }

        try (Connection con = sqlDatabase.getConnection()) {
            try (PreparedStatement prepBalanceUpdateStatement = con.prepareStatement(preparedUpdateBalance)) {
                prepBalanceUpdateStatement.setInt(1, Integer.parseInt(inputMoney));
                prepBalanceUpdateStatement.setString(2, accountNumber);
                prepBalanceUpdateStatement.executeUpdate();
                return true;
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public boolean transfer(String accountFrom, String accountFromPIN, String accountTo, String amount) {
        if (this.getBalance(accountFrom, accountFromPIN) < Integer.parseInt(amount)) {
            System.out.println("Not enough money!");
            return false;
        }

        this.changeBalance(accountFrom, amount, "withdraw");
        this.changeBalance(accountTo, amount, "deposit");
        System.out.println("Success!");
        return true;
    }

    public boolean closeAccount(String accountNumber, String pin) {
        String preparedCloseAccountStatement = "DELETE FROM card WHERE number = ? AND pin = ?";

        try (Connection con = sqlDatabase.getConnection()) {
            try (PreparedStatement preparedStatement = con.prepareStatement(preparedCloseAccountStatement)) {
                preparedStatement.setString(1, accountNumber);
                preparedStatement.setString(2, pin);
                int rows = preparedStatement.executeUpdate();

                if (rows == 1) {
                    return true;
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

}
