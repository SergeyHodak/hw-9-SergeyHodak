package lectureNotes;

import java.io.File;
import java.util.Arrays;

public class FileClass {
}

/*
    Класс File#
    Файл - это абстракция для хранения данных. Интуитивно вы привыкли к файлам - текстовым, видео и аудио. Но в наиболее
    абстрактном виде файл - это просто набор байт. И читать-писать файлы на самом низком уровне мы можем именно в виде
    байтового потока. Для работы с файлами в Java есть специальный класс - File.

    Класс File#
    У класса File есть несколько конструкторов. Самый простой из них принимает один аргумент - путь к файлу в виде строки:
*/
class Test1 {
    public static void main(String[] args) {
        File file = new File("C:\\file.txt");
    }
}

/*
    В примере выше два слеша \ подряд нужны для того, потому что с помощью одиночного обычного слеша в Java записываются
    спецсимволы. Далее рассмотрим несколько наиболее популярных операций над файлами. Здесь разобраны не все, а лишь
    наиболее часто используемые методы.

    Существует ли файл#
    Мы можем узнать, существует ли файл по указанному пути, используя метод exists():
*/
class Test2 {
    public static void main(String[] args) {
        File file = new File("C:\\non\\existing\\file.txt");
        System.out.println(file.exists());
    }
}
/*
    В примере выше вызов file.exists() скорей всего возвратит false (разве что у вас на компьютере по указанному пути
    действительно существует такой файл).

    Путь к файлу#
    Когда у нас есть файл, мы можем узнать путь к этому файлу:
*/
class Test3 {
    public static void main(String[] args) {
        File file = new File("C:\\file.txt");
        System.out.println(file.getPath());
    }
}
/*
    Код выше выведет C:\file.txt. Также есть методы getAbsolutePath() и getCanonicalPath(). Эти методы дают более
    точный путь, заменяя например подстановки вида .. (каталог на уровень выше) и . (текущий каталог) на абсолютные значения.

    Является ли файл директорией#
    Файл может быть обычным файлом (хранить в себе данные), или же хранить в себе другие файлы (тогда такой файл
    называется директорией, каталогом или же папкой (folder)). Чтобы узнать, является ли файл каталогом, есть метод isDirectory():
*/
class Test4 {
    public static void main(String[] args) {
        //false
        System.out.println(new File("C:\\file.txt").isDirectory());

        //true
        System.out.println(new File("C:\\").isDirectory());
    }
}

/*

    Список файлов в каталоге#
    Если файл является каталогом, то мы можем получить список файлов внутри этого каталога (причем некоторые файлы тоже
    возможно будут каталогами). Для этого есть методы list() и listFiles():
*/
class Test5 {
    public static void main(String[] args) {
        File file = new File("C:\\file.txt");
        File[] filesInCDrive = file.listFiles();
        System.out.println(Arrays.toString(filesInCDrive));

        File file1 = new File("C:\\");
        File[] filesInCDrive1 = file1.listFiles();
        System.out.println(Arrays.toString(filesInCDrive1));
    }
}

/*

    Родительская директория#
    Если мы хотим узнать, в каком каталоге находится файл, мы используем метод getParent():
*/
class Test6 {
    public static void main(String[] args) {
        File file = new File("C:\\file.txt");
        File folder = file.getParentFile();
        System.out.println(folder);
    }
}
/*

    Создание структуры каталогов#
    Если мы знаем, что какой-то директории нет, но мы хотим ее создать, есть метод mkdir() (сокращенно от Make Directory):
*/
class Test7 {
    public static void main(String[] args) {
        File file = new File("C:\\folder");
        if (!file.exists()) {
            System.out.println("Папка не найдена, создадим ее");
            file.mkdir();
        }
        System.out.println(file.exists()); //есть такая папка?
    }
}
/*
    Для корректной работы этого метода нужно, чтобы все каталоги, кроме последнего в пути, уже были созданы. Если мы же
    хотим создать сразу несколько каталогов, есть метод mkdirs():
*/
class Test8 {
    public static void main(String[] args) {
        File file = new File("C:\\folder1\\folder2\\folder3");
        file.mkdirs();
    }
}
/*
    В пример выше внутри диска C будет создан каталог folder1, внутри которого будет создан каталог folder2, и внутри
    каталога folder2 будет создан каталог folder3.
 */