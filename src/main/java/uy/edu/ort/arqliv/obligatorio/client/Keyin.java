/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.arqliv.obligatorio.client;

//**********************************************************
//**********************************************************
//Program: Keyin
//Reference: Session 20
//Topics:
// 1. Using the read() method of the ImputStream class
//    in the java.io package
// 2. Developing a class for performing basic console
//    input of character and numeric types
//**********************************************************
//**********************************************************
public class Keyin {

    //*******************************
    //   support methods
    //*******************************
    //Method to display the user's prompt string
    public static void printPrompt(String prompt) {
        System.out.print(prompt + " ");
        System.out.flush();
    }

    //Method to make sure no data is available in the
    //input stream
    public static void inputFlush() {
        int dummy;
        int bAvail;

        try {
            while ((System.in.available()) != 0) {
                dummy = System.in.read();
            }
        } catch (java.io.IOException e) {
            System.out.println("Input error");
        }
    }

    //********************************
    //  data input methods for
    //string, int, char, and double
    //********************************
    public static String inString(String prompt) {
        inputFlush();
        printPrompt(prompt);
        return inString();
    }

    public static String inString() {
        int aChar;
        String s = "";
        boolean finished = false;

        while (!finished) {
            try {
                aChar = System.in.read();
                if (aChar < 0 || (char) aChar == '\n') {
                    finished = true;
                } else if ((char) aChar != '\r') {
                    s = s + (char) aChar; // Enter into string
                }
            } catch (java.io.IOException e) {
                System.out.println("Input error");
                finished = true;
            }
        }
        return s;
    }

    public static int inInt(String prompt) {
        while (true) {
            inputFlush();
            printPrompt(prompt);
            try {
                return Integer.valueOf(inString().trim()).intValue();
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Not an integer");
            }
        }
    }
    /**
     * Agregado para los updates poder controlar si no se modifica el valor
     * @param prompt
     * @return
     */
    public static Integer inIntAllowEmpty(String prompt) {
        while (true) {
            inputFlush();
            printPrompt(prompt);
            String inStr = inString().trim();
            try {
                return Integer.valueOf(inStr);
            } catch (NumberFormatException e) {
            	if(inStr.isEmpty()){
            		return null;
            	}
                System.out.println("Invalid input. Not an integer");
            }
        }
    }
    
    public static char inChar(String prompt) {
        int aChar = 0;

        inputFlush();
        printPrompt(prompt);

        try {
            aChar = System.in.read();
        } catch (java.io.IOException e) {
            System.out.println("Input error");
        }
        inputFlush();
        return (char) aChar;
    }

    /**
     * Agregado para los updates poder controlar si no se modifica el valor
     * @param prompt
     * @return
     */
    public static Double inDoubleAllowEmpty(String prompt) {
        while (true) {
            inputFlush();
            printPrompt(prompt);
            String inStr = inString().trim();
            try {
                return Double.valueOf(inStr);
            } catch (NumberFormatException e) {
            	if(inStr.isEmpty()){
            		return null;
            	}
                System.out.println("Invalid input. Not a floating point number");
            }
        }
    }
    
    public static double inDouble(String prompt) {
        while (true) {
            inputFlush();
            printPrompt(prompt);
            try {
                return Double.valueOf(inString().trim()).doubleValue();
            } catch (NumberFormatException e) {
                System.out
                        .println("Invalid input. Not a floating point number");
            }
        }
    }
}
