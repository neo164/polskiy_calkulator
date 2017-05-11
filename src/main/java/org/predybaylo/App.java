package org.predybaylo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) {
        BufferedReader d = new BufferedReader(new InputStreamReader(System.in));
        String sIn = " ";

        System.out.println("Enter an expression for the count. The allowed characters are + - * / and curly braces:");
        try {
            sIn = d.readLine();

            Calculator task = new CalculatorImpl();

            System.out.println(task.showResult(sIn));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
