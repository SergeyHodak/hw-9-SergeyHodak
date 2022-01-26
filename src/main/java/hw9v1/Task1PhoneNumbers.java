package hw9v1;

/*
    Задание 1#
    Дан текстовый файл file.txt, который содержит список номеров телефонов (один на линии). Необходимо написать метод,
    который будет считывать файл и выводить в консоль все валидные номера телефонов. Предполагаем, что "валидный"
    номер телефона - это строка в одном из двух форматов: (xxx) xxx-xxxx или xxx-xxx-xxxx (х обозначает цифру).

    Пример:
        Для файла file.txt со следующим содержанием:
            987-123-4567
            123 456 7890
            (123) 456-7890
        Метод должен вывести на экран
            987-123-4567
            (123) 456-7890
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Task1PhoneNumbers {
    public static void findPhoneNumbersInAFile(String file) throws IOException { // метод, который может найти номера телефонов в файле
        try (FileReader fail = new FileReader(file)){ // в теле исключения создается экземпляр чтения с файла
            int count; // целочисленное значение бита
            StringBuilder result = new StringBuilder(); //строка в которую будем запихивать прочитанное
            while ((count = fail.read()) != -1) { //выполнять пока, есть что читать
                if (count == 13 ) { // если пошел бит с инфой о переходе на следующую строку
                    if (compareWithTemplate(result.toString())) { // сравнить прочитанную строку с шаблоном телефонных номеров
                        System.out.println(result); //если номер соответствует вывести его в консоль
                    }
                    result = new StringBuilder(); // очистить строку
                    count = fail.read(); // перейти на новую строку в читаемом файле
                } else { //иначе, это не бит перехода на новую строку
                    result.append((char) count); // добавить бит в строку для анализа
                }
            }
            if (compareWithTemplate(result.toString())) { //проверить последнюю запись с шаблоном
                System.out.println(result); // если ок, то печатаем в консоль
            }
        } catch (FileNotFoundException e) { //исключения
            e.printStackTrace(); // посмотреть содержимое исключения
        }
    }


    private static boolean compareWithTemplate(String text) { // метод сравнивает строку с шаблоном
        String test = text.replaceAll("[(][\\d]+[)][ ][\\d]+[-][\\d]+", "1"); // если формат (xxx) xxx-xxxx
        test = test.replaceAll("[\\d]+[-][\\d]+[-][\\d]+", "1"); // если формат xxx-xxx-xxxx
        if (test.equals("1")) { return true;} //если есть соответствие формату
        return false; // совпадений с шаблонами нету
    }

    public static void main(String[] args) throws IOException {
        Task1PhoneNumbers.findPhoneNumbersInAFile("src\\main\\java\\hw9v1\\file.txt"); // прочитать файл
    }
}