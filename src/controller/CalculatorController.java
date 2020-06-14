package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculatorController {

    private List<String> list;
    private List<List<String>> treeList;
    private int startBracket, endBracket, startBracket2, endBracket2;
    private boolean plusSign = true;
    private boolean basicBracket = false;
    private String currentExpression;
    private StringBuilder bufferExpression = new StringBuilder();
    StringBuilder mainString;
    private int startPos = 0, finishPos = 0;

    public String startParse(String input){
            treeList = new ArrayList();
            list = new ArrayList<>();
            list.add(input);
            calculate(input);

        if (!plusSign) {
            bufferExpression.insert(0, '-');
        }
            plusSign = true;
            if (treeList.get(treeList.size() - 1).size() == 3) {
                return treeList.get(treeList.size() - 1).get(2);
            } else return treeList.get(treeList.size() - 1).get(3);
    }

    public List<List<String>> getTreeList() {
        return treeList;
    }

    public List<String> getList() {
        return list;
    }

    private int valueCalc(String input) {
        int signs = 0;
        for (int i = 0; i < input.length(); i++) {
            if ((input.charAt(i) == '+') || (input.charAt(i) == '-') || (input.charAt(i) == '*') || (input.charAt(i) == '/') ||
                (input.charAt(i) == '%') || (input.charAt(i) == ')') || (input.charAt(i) == '(')) {
                signs++;
            }
        }
        return signs;
    }

    private void calculate(String input) {
        int[] charsPos = new int[valueCalc(input) + 2];

        float a = 0, b = 0;
        Float c = null;

        bufferExpression.delete(0, bufferExpression.length());
        bufferExpression.append(input);

        checkNestingAndSpecialFunc();

        for (int i = 0; i < bufferExpression.length(); i++) {
            if (bufferExpression.charAt(i) == '*') {
                multiplyDividePercent(i, charsPos, '*');
                break;
            }
            if (bufferExpression.charAt(i) == '/') {
                multiplyDividePercent(i, charsPos, '/');
                break;
            }
            if (bufferExpression.charAt(i) == '%') {
                multiplyDividePercent(i, charsPos, '%');
                break;
            }

        }

        for (int i = 0; i < bufferExpression.length(); i++) {
            if ((bufferExpression.charAt(i) == '*') || (bufferExpression.charAt(i) == '/') || (bufferExpression.charAt(i) == '%')) {
                calculate(bufferExpression.toString());
            }
        }

        for (int i = 0; i < bufferExpression.length(); i++) {
            if ((bufferExpression.charAt(i) == '+') && (plusSign)) {
                findExpression(i, charsPos);
                a = Float.parseFloat(bufferExpression.substring(startPos, i));
                b = Float.parseFloat(bufferExpression.substring(i + 1, finishPos));
                currentExpression = bufferExpression.substring(startPos, finishPos);
                bufferExpression.delete(startPos, finishPos);
                c = a + b;
                treeList.add(newList(a, "+", b, c));
                bufferExpression.insert(startPos, c.toString());
                checkExpression(bufferExpression.toString(), currentExpression, c.toString());
                break;
            }
            if ((bufferExpression.charAt(i) == '+') && (!plusSign)) {
                findExpression(i, charsPos);
                a = Float.parseFloat(bufferExpression.substring(0, i));
                b = Float.parseFloat(bufferExpression.substring(i + 1, finishPos));
                currentExpression = bufferExpression.substring(startPos, finishPos);
                bufferExpression.delete(0, finishPos);
                if (b > a) {
                    c = b - a;
                    treeList.add(newList(a * -1, "+", b, c));
                    plusSign = true;
                } else {
                    c = a - b;
                    treeList.add(newList(a * -1, "+", b, c * -1));
                }
                bufferExpression.insert(0, c.toString());
                checkExpression(bufferExpression.toString(), currentExpression, c.toString());
                break;
            }
            if ((bufferExpression.charAt(i) == '-') && (plusSign)) {
                findExpression(i, charsPos);
                a = Float.parseFloat(bufferExpression.substring(0, i));
                b = Float.parseFloat(bufferExpression.substring(i + 1, finishPos));
                currentExpression = bufferExpression.substring(startPos, finishPos);
                bufferExpression.delete(0, finishPos);
                if (b > a) {
                    c = b - a;
                    treeList.add(newList(a, "-", b, c * -1));
                    plusSign = false;
                } else {
                    c = a - b;
                    treeList.add(newList(a, "-", b, c));
                }
                bufferExpression.insert(0, c.toString());
                checkExpression(bufferExpression.toString(), currentExpression, c.toString());
                break;
            }
            if ((bufferExpression.charAt(i) == '-') && (!plusSign)) {
                findExpression(i, charsPos);
                a = Float.parseFloat(bufferExpression.substring(0, i));
                b = Float.parseFloat(bufferExpression.substring(i + 1, finishPos));
                bufferExpression.delete(0, finishPos);
                c = a + b;
                treeList.add(newList(a * -1, "-", b, c * -1));
                bufferExpression.insert(0, c.toString());
                checkExpression(bufferExpression.toString(), currentExpression, c.toString());
                break;
            }
        }
        for (int i = 0; i < bufferExpression.length(); i++) {
            if ((bufferExpression.charAt(i) == '+') || (bufferExpression.charAt(i) == '-')) {
                calculate(bufferExpression.toString());
            }
        }
    }

    private void checkNestingAndSpecialFunc(){
        for (int i = 0; i < bufferExpression.length(); i++) {
            if (bufferExpression.charAt(i) == '(') {
                startBracket = i;
            }
            if (bufferExpression.charAt(i) == 's') {
                basicBracket = false;
                calculateSubStringForSinglePowsSqrt(i, 3, 4);
                sqrtFunction(bufferExpression);
                updateInput(mainString);
            }else if (bufferExpression.charAt(i) == '^') {
                if(bufferExpression.charAt(i+1) == '-' && bufferExpression.charAt(i+2) == '1') {    //  1/x  ~ ()^-1
                    basicBracket = false;
                    calculateSubStringForSinglePowsSqrt(i, 2, 3);
                    reverseFunction(bufferExpression);
                    updateInput(mainString);
                } else if(bufferExpression.charAt(i+1) == '2'){     // ()^2
                    basicBracket = false;
                    calculateSubStringForSinglePowsSqrt(i, 1, 2);
                    powTwoThreeFunction(bufferExpression, 2);
                    updateInput(mainString);
                } else if(bufferExpression.charAt(i+1) == '3'){     // ()^3
                    basicBracket = false;
                    calculateSubStringForSinglePowsSqrt(i, 1, 2);
                    powTwoThreeFunction(bufferExpression, 3);
                    updateInput(mainString);
                } else {    // ()^()
                    basicBracket = false;

                    String subStrDegree = "";
                    int bracketCounter = 0;
                    for (int j = i + 1; j < bufferExpression.length(); j++) {
                        if (bufferExpression.charAt(j) == '(') {
                            bracketCounter++;
                        }
                        if (bufferExpression.charAt(j) == ')') {
                            bracketCounter--;
                        }
                        if (bracketCounter == 0) {
                            String currentSubString = bufferExpression.substring(i + 2, j);

                            bufferExpression.delete(i + 1, j + 1);
                            StringBuilder mainString = new StringBuilder(bufferExpression);
                            calculate(currentSubString);
                            mainString.insert(i + 1, bufferExpression.toString());
                            bufferExpression.delete(0, bufferExpression.length());
                            bufferExpression.insert(0, mainString.toString());

                            try {
                                Float.parseFloat(currentSubString);
                            } catch (Exception e) {
                                list.add(bufferExpression.toString());
                            }
                            subStrDegree += bufferExpression.charAt(i + 1);

                            for (int k = i + 2; k < bufferExpression.length(); k++) {
                                if ((bufferExpression.charAt(k) == '+') || (bufferExpression.charAt(k) == '-') ||
                                        (bufferExpression.charAt(k) == '*') || (bufferExpression.charAt(k) == '/') ||
                                        (bufferExpression.charAt(k) == '(') || (bufferExpression.charAt(k) == ')') || (bufferExpression.charAt(k) == '%')) {
                                    break;
                                }
                                subStrDegree += bufferExpression.charAt(k);
                            }
                            break;
                        }
                    }
                    calculateSubStringPow(i, 0, 1, subStrDegree.length());

                    if (!plusSign && subStrDegree.charAt(0) != '-') {
                        subStrDegree = "-" + subStrDegree;
                    }
                    exponentiation(bufferExpression, subStrDegree);
                    updateInput(mainString);
                }
            } else if (bufferExpression.charAt(i) == ')' && (i == bufferExpression.length() - 1 || (bufferExpression.charAt(i + 1) != 's' && bufferExpression.charAt(i + 1) != '^'))) {
                basicBracket = true;
                calculateSubString(i, 0, 0, 1);
                updateInput(mainString);
            }
        }
    }

    private StringBuilder reverseFunction(StringBuilder number) {
        float a = Float.parseFloat(number.toString());
        number.delete(0, number.length());
        number.insert(0, Math.pow(a, -1));
        treeList.add(newList(a, "1/x", Float.parseFloat(number.toString())));
        return number;
    }

    private StringBuilder sqrtFunction(StringBuilder number) {
        float a = Float.parseFloat(number.toString());
        number.delete(0, number.length());
        number.insert(0, Math.sqrt(a));
        treeList.add(newList(a, "sqrt", Float.parseFloat(number.toString())));  //√
        return number;
    }

    private StringBuilder powTwoThreeFunction(StringBuilder number, int power) {
        float a = Float.parseFloat(number.toString());
        number.delete(0, number.length());
        number.insert(0, Math.pow(a, power));
        treeList.add(newList(a, "^"+ String.valueOf(power), Float.parseFloat(number.toString())));
        return number;
    }

    private StringBuilder exponentiation(StringBuilder number, String degree) {
        float num = Float.parseFloat(number.toString());
        float deg = Float.parseFloat(degree);
        number.delete(0, number.length());
        number.insert(0, Math.pow(num, deg));
        treeList.add(newList(num, "^", deg, Float.parseFloat(number.toString())));
        return number;
    }

    private List<String> newList(float a, String sign, float b, float c) {
        List<String> currentList = Arrays.asList(String.valueOf(a), sign, String.valueOf(b), String.valueOf(c));
        return currentList;
    }

    private List<String> newList(float a, String sign, float c) {
        List<String> currentList = Arrays.asList(String.valueOf(a), sign, String.valueOf(c));
        return currentList;
    }

    private void multiplyDividePercent(int i, int[] charsPos, char sign) {
        float a = 0, b = 0;
        Float c = null;
        charsPos[0] = 0;
        for (int j = 0, k = 1; j < bufferExpression.length() - 1; j++) {
            if ((bufferExpression.charAt(j) == '+') || (bufferExpression.charAt(j) == '-') ||
                    (bufferExpression.charAt(j) == '*') || (bufferExpression.charAt(j) == '/') || (bufferExpression.charAt(j) == '%') ||
                    (bufferExpression.charAt(j) == '(') || (bufferExpression.charAt(j) == ')')) {
                charsPos[k] = j + 1;
                k++;
            }
            charsPos[charsPos.length - 1] = bufferExpression.length() + 1;
        }
        for (int k = 0; k < charsPos.length; k++) {

            if (charsPos[k] == i + 1) {
                startPos = charsPos[k - 1];
                finishPos = charsPos[k + 1] - 1;
            }
        }

        a = Float.parseFloat(bufferExpression.substring(startPos, i));
        b = Float.parseFloat(bufferExpression.substring(i + 1, finishPos));
        currentExpression = bufferExpression.substring(startPos, finishPos);
        bufferExpression.delete(startPos, finishPos);
        if (sign == '*') {
            c = a * b;
            treeList.add(newList(a, "*", b, c));
        } else if (sign == '/') {
            c = a / b;
            treeList.add(newList(a, "/", b, c));
        } else if (sign == '%') {
            c = a / 100 * b;
            treeList.add(newList(a, "%", b, c));
        }
        bufferExpression.insert(startPos, c.toString());
        checkExpression(bufferExpression.toString(), currentExpression, c.toString());
        calculate(bufferExpression.toString());
    }

    private void findExpression(int i, int[] charsPos) {
        charsPos[0] = 0;
        for (int j = 0, k = 1; j < bufferExpression.length() - 1; j++) {
            if ((bufferExpression.charAt(j) == '+') || (bufferExpression.charAt(j) == '-') ||
                    (bufferExpression.charAt(j) == '*') || (bufferExpression.charAt(j) == '/') || (bufferExpression.charAt(j) == '%') ||
                    (bufferExpression.charAt(j) == '(') || (bufferExpression.charAt(j) == ')')) {
                charsPos[k] = j + 1;
                k++;
            }
            charsPos[charsPos.length - 1] = bufferExpression.length() + 1;
        }
        for (int k = 0; k < charsPos.length; k++) {

            if (charsPos[k] == i + 1) {
                startPos = charsPos[k - 1];
                finishPos = charsPos[k + 1] - 1;
            }
        }
    }

    private void checkExpression(String input, String currentExpression, String newLine) {
        if (basicBracket) {
            try {
                Float.parseFloat(input);
            } catch (Exception e) {
                list.add(list.get(list.size() - 1).replace(currentExpression, newLine));
            }
        } else list.add(list.get(list.size() - 1).replace(currentExpression, newLine));
    }

    private void calculateSubStringForSinglePowsSqrt(int i, int a, int b) {
        endBracket = i + a;
        String subString = "";
        subString = bufferExpression.substring(startBracket + 1, endBracket - b);
        bufferExpression.delete(startBracket, endBracket + 1);
        mainString = new StringBuilder(bufferExpression);
        calculate(subString);
    }

    private void calculateSubString(int i, int fNumber, int sNumber, int tNumber) {
        endBracket = i + fNumber;
        String subString = "";
        subString = bufferExpression.substring(startBracket + 1, endBracket - sNumber);
        bufferExpression.delete(startBracket, endBracket + tNumber);
        mainString = new StringBuilder(bufferExpression);
        calculate(subString);
    }

    private void calculateSubStringPow(int i, int firstNumber, int secondNumber, int thirdNumber) {
        endBracket = i + firstNumber;
        String subString = "";
        subString = bufferExpression.substring(startBracket + 1, endBracket - secondNumber);
        bufferExpression.delete(startBracket, endBracket + thirdNumber);
        mainString = new StringBuilder(bufferExpression);
        calculate(subString);
    }

    private void updateInput(StringBuilder mainString) {
        mainString.insert(startBracket, bufferExpression.toString());
        bufferExpression.delete(0, bufferExpression.length());
        bufferExpression.insert(0, mainString.toString());
        list.add(bufferExpression.toString());
        calculate(bufferExpression.toString());
    }

    public void clear() {
        treeList.clear();
        list.clear();
    }
}

