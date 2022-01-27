package hw9v1;

/*
    Задание 3#
    Напишите метод, который будет подсчитывать частоту каждого слова в файле words.txt.
    Предполагаем, что:
        words.txt содержит только слова в нижнем регистре, разделенные пробелом
        Каждое слово содержит только символы-буквы в нижнем регистре.
        Слова разделены одним или несколькими пробелами, либо переносом строки.
    Пример:
    Для файла words.txt со следующим содержанием:
        the day is sunny the the
        the sunny is is
    Метод должен вернуть частоту:
        the 4
        is 3
        sunny 2
        day 1

    Обратите внимание! Вывод на консоль должен быть отсортирован на частоте слов (от наибольшей к наименьшей)
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Task3WordFrequency {
    public String readFromFile (String file) { // метод читающий текст с файла
        try(FileInputStream fileInputStream = new FileInputStream(file)) { // в теле, из какого файла читаем
            StringBuilder result = new StringBuilder();
            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);
            for (int i = 0; i < buffer.length; i++) {
                result.append((char) buffer[i]);
            }
            return result.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public void theFrequencyOfEachWord(String text) { //метод подсчитывающий частоту каждого слова
        String[] slova = text.split("\\s+"); // разбили строку на массив из слов по пробелам

        // найти уникальные
        StringBuilder res = new StringBuilder(); // хранит уникальные слова в виде строки
        for (int i = 0; i < slova.length; i++) { // пробежка по массиву слов
            if (!res.toString().contains(slova[i])) { // если слова из массива slova, еще нет в переменной res
                res.append(slova[i]).append(" "); // записать слово в переменную
            }
        }
        String[] resu = res.toString().split("\\s+"); // разбили строку на массив из слов по пробелам (уникальные слова)

        // сколько раз каждое уникальное слово встречается во входном тексте
        String[][] resul = new String[resu.length][2]; // массив для чисел (повторений слов)
        for (int j = 0; j < resu.length; j++) { // пробежка по уникальным словам
            int count = 0; // счетчик повторений слова
            for (int g = 0; g < slova.length; g++) { // пробежка по строке со всеми словами
                if (resu[j].equals(slova[g])) { // если уникальное слово совпало со словом из текста
                    count++; // повысить счетчик встречаемости
                }
            }
            resul[j][1] = String.valueOf(count); // сколько повторяется слово
            resul[j][0] = resu[j]; // какое слово
        }

        // отсортировать
        for(int i = resul.length-1 ; i > 0 ; i--){
            for(int j = 0 ; j < i ; j++){
                if(Integer.parseInt(resul[j][1]) < Integer.parseInt(resul[j+1][1])){
                    String[] tmp = resul[j];
                    resul[j] = resul[j+1];
                    resul[j+1] = tmp;
                }
            }
        }

        // печатать в консоль
        for (String[] a: resul) {
            System.out.println(a[0] + " " + a[1]);
        }
    }

    public static void main(String[] args) {
        Task3WordFrequency test = new Task3WordFrequency();
        String a = test.readFromFile("src\\main\\java\\hw9v1\\words.txt"); // прочитать текст из файла
        test.theFrequencyOfEachWord(a); // вывести в консоль уникальные слова + их повторяемость в файле
    }
}