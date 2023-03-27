package banking;

import java.util.Objects;
import java.util.Random;

public class CreditCard {
    String number;

    String pin;

    private int balance;

    public CreditCard() {
        this.number = generateNumber();
        this.pin = generatePIN();
        this.balance = 0;
    }

    public CreditCard(String accountNumber, String pin) {
        this(accountNumber, pin, 0);
    }

    public CreditCard(String accountNumber, String pin, int balance) {
        this.number = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    private String generateNumber() {
        String bin = "400000";
        StringBuilder generatedNumber = new StringBuilder(bin);

        for(int i = 0; i < 9; i++) {
            Random randomizer = new Random();
            generatedNumber.append(randomizer.nextInt(10));
        }

        String checksum = generateChecksum(String.valueOf(generatedNumber));

        return generatedNumber.append(checksum).toString();
    }

    protected static String generateChecksum(String generatedNumber) {
        int sum = 0;
        for (int i = 0; i < generatedNumber.length(); i++) {
            int num = Integer.parseInt(String.valueOf(generatedNumber.charAt(i)));
            num = i % 2 == 0 ? num * 2 : num;
            sum += num > 9 ? num - 9 : num;
        }
        sum = 10 - sum % 10 == 10 ? 0 : 10 - sum % 10;
        return String.valueOf(sum);
    }

    private static String generatePIN() {
        StringBuilder generatedPIN = new StringBuilder();
        Random randomizer = new Random();

        for(int i = 0; i < 4; i++) {
            generatedPIN.append(randomizer.nextInt(10));
        }

        return generatedPIN.toString();
    }

    protected int getBalance() {
        return balance;
    }


    @Override
    public String toString() {
        return "Your card number:\n" + this.number + "\nYour card PIN:\n" + this.pin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return pin.equals(that.pin) && number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, pin);
    }

}
