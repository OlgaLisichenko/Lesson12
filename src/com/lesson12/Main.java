package com.lesson12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.*;

/**
 * <p>1. Есть txt файл с номерами документов.
 * Номером документа является строка, состоящая из букв и цифр(без служебных символов).
 * Файл содержит каждый номер документа с новой строки и в строке никакой другой информации, только номер документа.
 * Валидный номер документа должен иметь длину 15 символов и начинаться с последовательности docnum или contract
 * (далее любая последовательность букв/цифр).
 * Написать программу для чтения информации из входного файла. Путь к входному файлу должен задаваться через консоль.
 * <p>2. Улучшить предыдущее задание:
 * Валидные номера документов следует записать в один файл-отчет.
 * Невалидные номера документов следует записать в другой файл-отчет,
 * но после номеров документов следует добавить информацию о том, почему этот документ невалиден.
 */
public class Main {
    public static void main(String[] args) throws IOException {

        try (Scanner scanner = new Scanner(System.in)) {
            String message = "Set the path to the file.";
            String message1 = " (does not contain fifteen characters)";
            String message2 = " (does not start with docnum and with contract)";
            String message3 = " (does not contain only litters or digits)";

            System.out.println(message);
            Path pathFromConsole = Path.of(scanner.nextLine());   // resources/allNumbers.txt

            List<String> numbersOfDoc = Files.readAllLines(pathFromConsole);
            System.out.println(numbersOfDoc);

            Path validData = Path.of("resources", "validData.txt");
            Path invalidData = Path.of("resources", "invalidData.txt");

            for (String docNumber : numbersOfDoc) {
                if (isValidNumber(docNumber)) {
                    Files.write(validData, List.of(docNumber), APPEND);
                } else {
                    if (docNumber.length() != 15) {
                        Files.write(invalidData, List.of(docNumber + message1), APPEND);
                    } else if (!docNumber.startsWith("docnum") && !docNumber.startsWith("contract")) {
                        Files.write(invalidData, List.of(docNumber + message2), APPEND);
                    } else {
                        Files.write(invalidData, List.of(docNumber + message3), APPEND);
                    }
                }
            }
        }
    }

    private static boolean isValidNumber(String docNum) {
        boolean isDocNumberValid = (docNum.length() == 15 && (docNum.startsWith("docnum") || docNum.startsWith("contract")));
        char[] numberFromChars = docNum.toCharArray();
        boolean isContainOnlyLetterOrDigit = true;

        for (char ch : numberFromChars) {
            if (!Character.isLetterOrDigit(ch)) {
                isContainOnlyLetterOrDigit = false;
                break;
            }
        }
        return isContainOnlyLetterOrDigit && isDocNumberValid;
    }
}