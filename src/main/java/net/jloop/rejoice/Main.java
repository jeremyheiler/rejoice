package net.jloop.rejoice;

public class Main {

    public static void main(String[] args) {
        System.out.println("Rejoice 0.0.1-alpha");
        new Rejoice().interpret(args[0]).print();
        System.exit(0);
    }
}
