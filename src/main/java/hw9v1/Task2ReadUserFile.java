package hw9v1;

/*
    Задание 2#
    Дан текстовый файл file2.txt, необходимо считать файл в список объектов User и создать новый файл user.json.
    Предполагаем, что каждая строка содержит одинаковое количество "колонок", разделенных пробелом.

    Пример:
        Для файла file2.txt со следующим содержанием:
            name age
            alice 21
            ryan 30
        Новый файл должен иметь следующий вид:
            [
                {
                    "name": "alice",
                    "age":21
                },
                {
                    "name": "ryan",
                    "age":30
                }
            ]
 */

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Task2ReadUserFile {
    public static ArrayList<String> readFileWithBuffer(String file) {
        ArrayList<String> res = new ArrayList<>();
        try (FileReader reader = new FileReader(file)) { //в теле указывается с какого файла читать
            int c; // переменная для хранения битов
            String name = ""; // переменная которая хранит имя переменной name
            String age = ""; // переменная которая хранит имя переменной age
            StringBuilder result = new StringBuilder(); // переменная которая собирает прочитанное
            String value1 = ""; // переменная которая хранить значение для переменной name
            String value2 = ""; // переменная которая хранить значение для переменной age
            while ((c = reader.read()) != -1) { // выполнять пока следующий бит не равен -1 (еще есть что читать)
                if ((char) c == ' ' & name.equals("")) { // если бит == пробел, и название переменной еще не указано
                    name = result.toString(); // присвоить название переменной
                    result = new StringBuilder(); // сбросить результат
                    continue; // следующий бит
                }
                if ((char) c == '\n' & age.equals("")) { // если это конец строки и название переменной аргумента еще пусто
                    age = result.toString(); // присвоить название переменной
                    result = new StringBuilder(); //сбросить результата
                    continue; // следующий бит
                }
                if ((char) c == ' ' & !name.equals("")) { // если пробел и переменная уже имеет свое имя
                    value1 = result.toString(); // значение для переменной name
                    result = new StringBuilder(); // сбросить результат
                    continue; // следующий бит
                }
                if ((char) c == '\n' & !age.equals("")) { // если это конец строки и название переменной аргумента еще пусто
                    value2 = result.toString(); // значение для переменной age
                    result = new StringBuilder(); //сбросить результата
                    User test1 = new User(name, value1, age, value2); // создать экземпляр класса Пользователь
                    res.add(test1.toString()); // добавить в коллекцию
                    continue; // следующий бит
                }
                result.append((char) c); // записать символ в переменную которая собирает прочитанное
            }
            User test2 = new User(name, value1, age, result.toString()); // создать экземпляр класса Пользователь
            res.add(test2.toString()); // добавить в коллекцию
            return res;
        } catch (IOException e) { // иначе, вызовется исключение
            System.out.println(e.getMessage()); // посмотреть сообщение от исключения
        }
        return res;
    }

    public static void writeToFile(ArrayList<String> args, String file) { // запись в файл
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(args);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) { // запускалка
        ArrayList<String> argsd = readFileWithBuffer("src\\main\\java\\hw9v1\\file2.txt"); // прочитать из файла
        System.out.println(argsd);
        writeToFile(argsd, "src\\main\\java\\hw9v1\\user.json"); // записать в файл
    }
    /*
    ШПАРГАЛКА ПО МОЕМУ РЕШЕНИЮ
    Для того что бы, решить вторую задачу нужно:
        1) Создать файл с именем file2.txt
           разместив его в этом репозитории, и вставив в него содержимое из задания:
                name age
                alice 21
                ryan 30
        2) Создать дополнительный класс Юзер
           поля у него name и age.
               Конструктор класса, получает 4 переменные
                   1. имя переменной name
                   2. имя переменной age
                   3. содержимое для переменной name
                   4. содержимое для переменной age
               задает значения для полей в виде - имя переменной и ее значение (все в виде строки).
           геттеры для полей
           ту стринг для полей
        3) Метод, который будет читать из файла
           Этот метод возвращает в место вызова массив строк. А на вход получает ссылку на файл file2.txt
           в теле try..catch указывается с какого файла читать
           создаю шесть переменных:
               1. переменная для хранения битов
               2. переменная которая хранит имя переменной name
               3. переменная которая хранит имя переменной age
               4. переменная которая собирает прочитанное
               5. переменная которая хранить значение для переменной name
               6. переменная которая хранить значение для переменной age
           создаю цикл вайл с условием - выполнять пока следующий бит не равен -1 (еще есть что читать)
               в нем четыре ифа:
                   1. если бит == пробел, и название переменной еще не указано
                          присвоить название переменной name
                          сбросить результат
                          следующий бит
                   2. если это конец строки и название переменной аргумента еще пусто
                          присвоить название переменной age
                          сбросить результата
                          следующий бит
                   3. если пробел и переменная уже имеет свое имя
                          значение для переменной name
                          сбросить результат
                          следующий бит
                   4. если это конец строки и название переменной аргумента еще пусто
                          значение для переменной age
                          сбросить результата
                          создать экземпляр класса Пользователь (передав ему все четыре переменные)
                          добавить в коллекцию этого пользователя
                          следующий бит
                   в конце ифов в теле цикла - записать символ в переменную которая собирает прочитанное
           после вайл создать экземпляр класса Пользователь (и отдать ему последние 4 переменные)
           добавить в коллекцию этот экземпляр
           ретурн коллекцию
           ретурн коллекцию
        4) Создать метод для записи в файл
           ничего не возвращает, принемает на в ход коллекцию и путь с именем нового файла
           через try..catch в теле новый файл в {запись информации в файл}
     */
}

class User {
    private final String name;
    private final String age;

    User(String name, String value1, String age, String value2) {
        name = name.strip(); //очистить от невидимых символов в начале о конце строки
        value1 = value1.strip(); //очистить от невидимых символов в начале о конце строки
        age = age.strip(); //очистить от невидимых символов в начале о конце строки
        value2 = value2.strip(); //очистить от невидимых символов в начале о конце строки
        this.name = "\"" + name + "\": \"" + value1 + "\"";
        this.age = "\"" + age + "\": \"" + value2 + "\"";
    }

    public String getName() {return name;}
    public String getAge() {return age;}

    @Override
    public String toString() {
        return "{\n" + getName()+ ",\n" + getAge() + "\n}";
    }
}