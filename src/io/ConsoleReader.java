package io;

import interfaces.Reader;

import java.util.Scanner;

public class ConsoleReader implements Reader {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        return scanner.next();
    }
}
