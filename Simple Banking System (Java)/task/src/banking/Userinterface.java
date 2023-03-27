package banking;

import java.util.Scanner;

public class Userinterface {

    public void start(Bank bank) {
        Scanner scanner = new Scanner(System.in);

        ui:
        while (true) {
            System.out.println("\n1. Create an account\n2. Log into account\n0. Exit");
            String userInput = scanner.nextLine();

            if (userInput.equals("1")) {
                System.out.println("\nYour card has been created");
                CreditCard newCard = bank.createCreditCard();
                System.out.println(newCard.toString());
            }

            if (userInput.equals("2")) {
                System.out.println("\nEnter your card number:");
                String accountNumber = scanner.nextLine();

                System.out.println("Enter your PIN:");
                String pin = scanner.nextLine();

                CreditCard creditCard = bank.login(accountNumber, pin);

                if (creditCard != null) {
                    System.out.println("\nYou have successfully logged in!");
                    login_ui: while (true) {
                        System.out.println("\n1. Balance\n2.Add income\n3. Do transfer\n4. Close account\n5. Log out\n0. Exit");
                        String loginInput = scanner.nextLine();

                        if (loginInput.equals("1")) {
                            System.out.println(bank.getBalance(accountNumber, pin));
                        }

                        if (loginInput.equals("2")) {
                            System.out.println("Enter income:");
                            String inputMoney = scanner.nextLine();
                            if (bank.changeBalance(accountNumber, inputMoney, "deposit")) {
                                System.out.println("Income was added!");
                            }
                        }

                        if (loginInput.equals("3")) {
                            System.out.println("Transfer\nEnter card number:");
                            String inputCardNumber = scanner.nextLine();
                            if (!bank.checkCard(inputCardNumber)) {
                                continue;
                            }

                            System.out.println("Enter how much money you want to transfer:");
                            String inputAmount = scanner.nextLine();
                            if(!bank.transfer(accountNumber, pin, inputCardNumber, inputAmount)) {
                                continue;
                            }
                        }

                        if (loginInput.equals("4")) {
                            if (bank.closeAccount(accountNumber, pin)) {
                                System.out.println("The account has been closed!");
                            }
                        }

                        if (loginInput.equals("5")) {
                            break;
                        }

                        if (loginInput.equals("0")) {
                            System.out.println("Bye!");
                            break ui;
                        }
                    }
                } else {
                    System.out.println("Wrong card number or PIN!");
                }
            }

            if (userInput.equals("0")) {
                System.out.print("Bye!");
                break;
            }
        }
    }
}
