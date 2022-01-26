package lectureNotes;

import java.io.*;
import java.util.Arrays;

public class ReadingAndWritingFiles {
}

/*
    Чтение и запись файлов#
    Для работы с файлами удобно использовать потоки ввода-вывода. С их помощью можно записывать и считывать данные из файлов.

    Запись файлов#
    Есть два основных базовых класса для записи файлов. Это класс для работы с бинарными файлами - FileOutputStream,
    и класс для работы с текстовыми файлами - FileWriter.

    Класс FileOutputStream#
    Если мы работаем с бинарными файлами (звук, видео например), то нам будет удобно использовать класс FileOutputStream.
    Этот класс унаследуется от класса OutputStream. У класса FileOutputStream есть несколько конструкторов:
        FileOutputStream(String filePath)
        FileOutputStream(File fileObj)
        FileOutputStream(String filePath, boolean append)
        FileOutputStream(File fileObj, boolean append)
    Вот пример как можно записать текст (строку) в файл:
*/
class Test9 {
    public static void main(String[] args) {
        writeFileWithOutputStream();
    }

    public static void writeFileWithOutputStream() {
        String text = "Hello from Output Stream";  // то что будет записано в файл
        try(FileOutputStream fileOutputStream = new FileOutputStream("test.txt")) { // исключение, внутри которого создается файл
            byte[] buffer = text.getBytes(); //массив с байтами, от строки text
            fileOutputStream.write(buffer, 0, buffer.length); // за стрим-мить массив в файл
        } catch (IOException e) { // исключения
            System.out.println(e.getMessage()); // посмотреть сообщение исключения
        }
    }
}
/*
    Мы хотим записать строку. Но класс FileOutputStream умеет работать только с байтами. Поэтому мы вначале переводим
    строку в байтовый массив (используя метод getBytes() у класса String), и лишь потом записываем полученные байты в
    поток fileOutputStream. Мы также можем записывать байты и по одному, используя перегруженную версию метода write:
*/
    //fileOutputStream.write(buffer[0]);
/*

    Класс FileWriter#
    Класс FileWriter унаследуется от класса Writer. Позволяет читать данные в текстовом виде из файла. У класса
    FileWriter есть следующие конструкторы:
        FileWriter(File file)
        FileWriter(File file, boolean append)
        FileWriter(FileDescriptor fd)
        FileWriter(String fileName)
        FileWriter(String fileName, boolean append)
    Параметр append указывает, должны ли данные дописывать в конец файла. Если этот параметр равен true, то данные
    дописываются в конец файла, если false - текущее содержимое файла стирается, и записывается лишь новая информация.
    Вот пример, как записать текст Hello, World! в файл test.txt, расположенный в директории, откуда запускается программа:
*/
class Test10 {
    public static void writeFile() {
        File file = new File("test.txt"); // новый файл, или перезаписи существующего
        //try-with-resources
        try (FileWriter writer = new FileWriter(file)) { // исключение, создающее поток в текстовый файл
            String text = "Hello, World!"; // переменная которая содержит инфу для записи в файл
            writer.write(text); // запись
            writer.flush(); // принудительная отправка потока
        } catch (IOException e) { // исключения
            System.out.println(e.getMessage()); // показать сообщение исключения
        }
    }

    public static void main(String[] args) {
        writeFile();
    }
}
/*
    Мы использовали параметр append со значением false. Это значит, что если раньше в файле test.txt и была какая-то
    информация, то теперь она перезапишется на новую, указанную нами.

    ПОЛЕЗНО
    В примере выше использовалась специальная форма try...catch, где в теле оператора try мы объявили новую переменную
    типа FileWriter с именем writer. Особенность такого объявления - после выхода из блока try...catch поток writer
    автоматически закроется (будет вызван метод close()). Это удобный и рекомендованный подход для работы с потоками
    ввода-вывода.
    Альтернативный, и уже устаревший подход - писать блок finally, в котором мы бы обязательно писали выражение
    writer.close() для закрытия потока.

    Чтение файлов#.
    Есть два основных базовых класса для чтения файлов. Это класс для работы с бинарными файлами - FileInputStream,
    и класс для работы с текстовыми файлами - FileReader.

    Класс FileInputStream#
    Если нам нужно считывать данные из файла в виде байтового потока, то удобно использовать класс FileInputStream.
    Он унаследуется от класса InputStream. У класса FileInputStream есть несколько конструкторов. Наиболее популярный
    вариант принимает лишь один параметр - путь к файлу в виде строки:
*/
    //FileInputStream(String fileName) throws FileNotFoundException
/*
    Если мы пытаемся открыть файл, и этого файла не существует, то выбрасывается исключение FileNotFoundException.
    Вот так можно прочитать данные из файла test.txt:
*/
class Test11 {
    public static void readFileWithInputStream() {
        try(FileInputStream fileInputStream = new FileInputStream("test.txt")) {
            System.out.println("File size is bytes: " + fileInputStream.available());
            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);
            for (int i = 0; i < buffer.length; i++) {
                System.out.print((char) buffer[i]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        readFileWithInputStream();
    }
}

/*
В примере выше мы прочитали все байты сразу в массив buffer. Если нам нужно читать байты по отдельности, мы можем
использовать метод read(), который будет возвращать по одному байту. Класс FileInputStream удобен для работы с двоичными
файлами. Для работы с текстовыми файлами в принципе можно использовать этот класс, но удобней использовать класс FileWriter.

    Класс FileReader#
    Если мы хотим работать с текстовыми файлами, то удобно использовать класс FileReader. Он унаследуется от класса Reader.
    У класса FileReader есть несколько конструкторов:
        FileReader(String fileName)
        FileReader(File file)
        FileReader(FileDescriptor fd)
    Вот пример, как можно прочитать данные с файла test.txt, и вывести их в консоль:
*/
class Test12{
    public static void readFile() {
        try (FileReader reader = new FileReader("test.txt")) {
            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        readFile();
    }
}
/*
    В примере выше мы считываем данные по одному символу char, и выводим их в консоль.
    Также мы можем считывать данные блоками:
*/
class Test13 {
    public static void readFileWithBuffer() {
        try (FileReader reader = new FileReader("test.txt")) {
            char[] buf = new char[256];
            int c;
            while ((c = reader.read(buf)) > 0) {
                if (c < 256) {
                    buf = Arrays.copyOf(buf, c);
                }
                System.out.println(Arrays.toString(buf));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        readFileWithBuffer();
    }
}

/*
    В примере выше мы читаем данные блоками до 256 символов включительно, и выводим их в консоль. Может быть случай,
    когда мы прочитали блок данных меньше (например, в файле меньше 256 символов). Тогда мы фактически обрезаем такой
    массив, используя метод Arrays.copyOf().

    ПОЛЕЗНО
    Лучше считывать данные именно блоками. Чтение файлов одиночными символами очень неэффективно.
 */