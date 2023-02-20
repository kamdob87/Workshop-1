package pl.coderslab;


import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    static String[][] tasks;
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static final String FILE_NAME = "tasks.csv";

    public static boolean noExit = true;

    public static void main(String[] args) {
        importFileData();
        printOption();
    }

    public static void printOption() {
        Scanner scan = new Scanner(System.in);
        while (noExit) {
            System.out.println();
            System.out.println(ConsoleColors.BLUE + "Please select an option:");
            for (String print : OPTIONS) {
                System.out.println(ConsoleColors.RESET + print);
            }
            String input = scan.nextLine();
            System.out.println(input);
            switch (input) {
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask();
                    break;
                case "list":
                    listTask();
                    break;
                case "exit":
                    noExit = false;
                    exitTask();
                    break;
                default:
                    System.out.println("Please select a correct option.");
                    System.out.println();
            }
        }
    }

    public static void importFileData() {
        File file = new File(FILE_NAME);
        try {
            Scanner scan = new Scanner(file);
            int count = 0;
            while (scan.hasNextLine()) {
                scan.nextLine();
                count++;
            }
            int i = 0;
            tasks = new String[count][3];
            Scanner scan2 = new Scanner(file);
            while (scan2.hasNextLine()) {
                String line = scan2.nextLine();
                String[] parts = line.split(",");
                for (int j = 0; j < parts.length; j++) {
                    tasks[i][j] = parts[j];
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Błąd wczytanie pliku ");
            noExit = false;
        }
    }

    public static void addTask() {
        System.out.println("Please add task description");
        Scanner scan = new Scanner(System.in);
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = scan.nextLine();
        System.out.println("Please add task due date");
        tasks[tasks.length - 1][1] = " " + scan.nextLine();
        System.out.println("Is your task is important: true/false");
        tasks[tasks.length - 1][2] = " " + scan.nextLine();
    }

    public static void removeTask() {
        System.out.println("Please select number to remove.");
        Scanner scan = new Scanner(System.in);
        int numberToDelete;
        while (true) {
            while (!scan.hasNextInt()) {
                scan.next();
                System.out.println("Incorrect argument passed. Please give number greater or equal 0");
            }
            numberToDelete = scan.nextInt();
            if (numberToDelete > 0) {
                break;
            } else {
                System.out.println("Incorrect argument passed. Please give number greater or equal 0");
            }
        }
        tasks = ArrayUtils.remove(tasks, numberToDelete);
        System.out.println("Value was successfully deleted");
    }

    public static void listTask() {
        for (int i = 0; i < tasks.length; i++) {
            System.out.println(i + " : " + tasks[i][0] + tasks[i][1] + tasks[i][2]);
        }
    }

    public static void exitTask() {
        try (PrintWriter printWriter = new PrintWriter("tasks.csv")) {
            for (String[] task : tasks) {
                printWriter.print(task[0] + ",");
                printWriter.print(task[1] + ",");
                printWriter.println(task[2]);
            }
        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println("Błąd zapisu do pliku.");
        }
        System.out.println(ConsoleColors.RED + "Bye, bye.");
    }
}
