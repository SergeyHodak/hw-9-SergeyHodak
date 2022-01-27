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
                if (c == 32 & name.equals("")) { // если бит == пробел, и название переменной еще не указано
                    name = result.toString(); // назвать переменную
                    result = new StringBuilder(); // сбросить результат
                    continue; // следующий бит
                }
                if (c == 13 & age.equals("")) { // если это конец строки и название переменной аргумента еще пусто
                    age = result.toString(); // присвоить название переменной
                    result = new StringBuilder(); //сбросить результата
                    c = reader.read(); // пропустить шаг с битом 10
                    continue; // следующий бит
                }
                if (c == 32 & !name.equals("")) { // если пробел и переменная уже имеет свое имя
                    value1 = result.toString(); // значение переменную
                    result = new StringBuilder(); // сбросить результат
                    continue; // следующий бит
                }
                if (c == 13 & !age.equals("")) { // если это конец строки и название переменной аргумента еще пусто
                    value2 = result.toString(); // присвоить название переменной
                    result = new StringBuilder(); //сбросить результата
                    c = reader.read(); // пропустить шаг с битом 10
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
        writeToFile(readFileWithBuffer("src\\main\\java\\hw9v1\\file2.txt"), "src\\main\\java\\hw9v1\\user.json"); // прочитать из файла, и записать в файл
    }
}

class User {
    private final String name;
    private final String age;

    User(String name, String value1, String age, String value2) {
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