package org.predybaylo;

import java.util.LinkedList;

/**
 * {@inheritDoc}
 * <p>
 * <p>
 * Дана трока вида: a+b+c-d+(e*f/h)=x.
 * 1. Парсим строку, выделяя числа, знаки операций, и символы приоритета (это будут три структуры для хранения. для чисел, знаков-операций(-+* /) и приоритетов("()") )(если встретим то, что не должны были встретить то выкинуть эксепшн и завершить выполнение приложения?);
 * 2. Исходя из символов приоритета(если они есть определяем что будет первостепенным по операциям с числами - это я про скобки);
 * 3. Если нет символов приоритета, то смотрим на операции с числами что будет выполняться  (умножение вперед и т. д.);
 * 4. Считаем, исходя из очереди чисел, знаков приоритета и знаков операций.
 * 5. Вывод результата X.
 * <p/>
 * <p>
 * <p>
 * методы:
 * seenOperation
 * seenPriority
 * seenNumbes
 * showResult
 * <p/>
 */

public class CalculatorImpl implements Calculator {

    @Override
    public int showResult(String s) {
        LinkedList<Integer> stackForNum = new LinkedList<Integer>(); // list for num
        LinkedList<Character> stackForPriority = new LinkedList<Character>(); // list for operators

        for (int i = 0; i < s.length(); i++) { // Parse a string with an expression and compute

            char c = s.charAt(i);

            if (c == '(')
                stackForPriority.add('('); // If you see that, we add priority operation

            else if (c == ')') {
                while (stackForPriority.getLast() != '(')

                    processOperator(stackForNum, stackForPriority.removeLast());

                stackForPriority.removeLast(); // If you see that, we delete

            } else if (seenOperator(c)) {

                while (!stackForPriority.isEmpty() && seenPriority(stackForPriority.getLast()) >= seenPriority(c))

                    processOperator(stackForNum, stackForPriority.removeLast());
                stackForPriority.add(c);
            } else {
                String operand = "";
                while (i < s.length() && Character.isDigit(s.charAt(i))) // Num??
                    operand += s.charAt(i++);
                --i;
                stackForNum.add(Integer.parseInt(operand));
            }
        }
        while (!stackForPriority.isEmpty()) //go to end
            processOperator(stackForNum, stackForPriority.removeLast());

        return stackForNum.get(0);  //return result
    }

    private void processOperator(LinkedList<Integer> stack, char operator) {
        int r = stack.removeLast(); // if you see that, we delete
        int l = stack.removeLast(); // If you see that, we delete
        switch (operator) {
            case '+':
                stack.add(l + r);
                break;
            case '-':
                stack.add(l - r);
                break;
            case '*':
                stack.add(l * r);
                break;
            case '/':
                stack.add(l / r);
                break;
            case '%':
                stack.add(l % r);
                break;
            case '^':
                stack.add((int)Math.pow(l,r));
                break;

        }
    }

    private boolean seenOperator(char c) { // seen operators
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^';
    }

    private int seenPriority(char codeOperation) { //return code
        switch (codeOperation) { // At + or - return 1, for * /% 2 otherwise -1
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }


}
