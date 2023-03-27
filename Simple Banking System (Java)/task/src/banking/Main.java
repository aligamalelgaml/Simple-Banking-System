package banking;

public class Main {
    public static void main(String[] args) {

        Userinterface ui = new Userinterface();
        Bank myBank = new Bank(args[1]);
        ui.start(myBank);

    }
}