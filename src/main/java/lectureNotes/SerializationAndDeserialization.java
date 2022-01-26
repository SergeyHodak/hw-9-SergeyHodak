package lectureNotes;

import java.io.*;
import java.util.ArrayList;

public class SerializationAndDeserialization {
}

/*
    Сериализация и десериализация#
    Иногда бывает удобно "заморозить" объект, сохранить его (в файл, например, или передать по сети Интернет), и
    восстановить его из этого "замороженного" состояния. В Java есть такая возможность. Мы можем сохранять объекты
    целиком в байтовый поток, и восстанавливать их оттуда Это называется сериализация и десериализация.
        Сериализация - это запись состояния объекта в поток.
        Десериализация - это восстановление состояния объекта из потока.
    При этом, поскольку мы работаем на уровне потоков, мы можем сохранять и считывать состояние объектов не только из
    файлов, например, а из любого другого места - из сети Интернет, из оперативной памяти, и любых других потоков.

    Интерфейс Serializable#
    Сериализовать можно лишь те объекты, которые реализуют интерфейс Serializable. У этого интерфейса нет никаких
    методов. Если класс реализует такой интерфейс, это всего лишь сигнал потокам ObjectOutputStream и ObjectInputStream,
    что объекты такого класса можно сериализовать.

    ИНТЕРЕСНО
    Такие интерфейсы также называют маркерными, поскольку сами по себе они ничего не значат, а лишь указывают на тип объекта.

    Класс ObjectOutputStream#
    Для сериализации объектов в поток используется класс ObjectOutputStream. Он записывает данные в другой поток,
    которые передается ему как конструктор:
*/
    //ObjectOutputStream(OutputStream out)
/*
    У класса ObjectOutputStream есть ряд методов:
        void close(): закрывает поток;
        void flush(): очищает внутренний буфер и сбрасывает его содержимое в выходной поток;
        void write(byte[] buf): записывает в поток массив байтов buf;
        void write(int val): записывает в поток один младший байт из val;
        void writeBoolean(boolean val): записывает в поток значение val;
        void writeByte(int val): записывает в поток один младший байт из val;
        void writeChar(int val): записывает в поток значение типа char, представленное целочисленным значением val;
        void writeDouble(double val): записывает в поток значение типа double;
        void writeFloat(float val): записывает в поток значение типа float;
        void writeInt(int val): записывает целочисленное значение val;
        void writeLong(long val): записывает значение типа long;
        void writeShort(int val): записывает значение типа short;
        void writeUTF(String str): записывает в поток строку в кодировке UTF-8;
        void writeObject(Object obj): записывает в поток отдельный объект.
    По сути, эти методы охватывают весь спектр данных, которые мы хотим сохранять.  Вот пример, как можно сохранить
    объект класса Person в файл с именем person.dat:
*/
class Program {
    public static void main(String[] args) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.dat"))) {
            Person p = new Person("Sam", 33, 178, true);
            oos.writeObject(p);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

class Person implements Serializable{
    private String name;
    private int age;
    private transient double height;
    private transient boolean married;

    Person(String name, int age, double height, boolean married){
        this.name = name;
        this.age = age;
        this.height = height;
        this.married = married;
    }

    public String getName() {return name;}
    public int getAge() {return age;}
    public double getHeight() {return height;}
    public boolean isMarried() {return married;}
}
/*
    В примере выше в файле person.dat будет сохранено двоичное представление объекта p.

    Класс ObjectInputStream#
    Для обратного процесса, десериализации, есть класс ObjectInputStream. В конструкторе этот класс принимает ссылку
    на поток ввода (объект класса InputStream):
*/
    //ObjectInputStream(InputStream in)
/*
    Основные методы ObjectInputStream, отвечающие за считывание различных типов данных, показаны ниже:
        void close(): закрывает поток;
        int skipBytes(int len): пропускает при чтении len байт;
        int available(): возвращает количество байт, доступных для чтения;
        int read(): считывает из потока один байт и возвращает его целочисленное представление;
        boolean readBoolean(): считывает из потока одно значение boolean;
        byte readByte(): считывает из потока одно значение типа byte;
        char readChar(): считывает из потока один символ char;
        double readDouble(): считывает значение типа double;
        float readFloat(): считывает из потока значение типа float;
        int readInt(): считывает целочисленное значение int;
        long readLong(): считывает значение типа long;
        short readShort(): считывает значение типа short;
        String readUTF(): считывает строку в кодировке UTF-8;
        Object readObject(): считывает из потока объект.
    Например, вот так можно десериализовать сохраненный ранее объект типа Person из файла person.dat:
*/
class Program1 {
    public static void main(String[] args) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("person.dat"))) {
            Person p = (Person) ois.readObject();
            System.out.printf("Name: %s \t Age: %d \n", p.getName(), p.getAge());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
/*
    Вот пример, как можно записать в файл и прочитать из файла список объектов:
*/
class Program2 {
    public static void main(String[] args) {

        String filename = "people.dat";

        // создадим список объектов, которые будем записывать
        ArrayList<Person2> people = new ArrayList<Person2>();
        people.add(new Person2("Tom", 30, 175, false));
        people.add(new Person2("Sam", 33, 178, true));

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(people);
            System.out.println("File has been written");
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

        // десериализация в новый список
        ArrayList<Person2> newPeople = new ArrayList<Person2>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            newPeople = (ArrayList<Person2>) ois.readObject();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        for(Person2 p : newPeople) {
            System.out.printf("Name: %s \t Age: %d \n", p.getName(), p.getAge());
        }
    }
}

class Person2 implements Serializable{
    private String name;
    private int age;
    private transient double height;
    private transient boolean married;

    Person2(String name, int age, double height, boolean married){
        this.name = name;
        this.age = age;
        this.height = height;
        this.married = married;
    }

    public String getName() {return name;}
    public int getAge() {return age;}
    public double getHeight() {return height;}
    public boolean isMarried() {return married;}
}
/*

    Исключение данных из сериализации#
    Не все поля в классе мы хотим сериализовать. Такие поля нужно объявить с модификатором transient. Например, для
    класса Person3 мы хотим исключить из сериализации поля height и married. В таком случае код класса будет выглядеть
    следующим образом:
*/
class Person3 implements Serializable{
    private String name;
    private int age;
    private transient double height;  //!!
    private transient boolean married; //!!

    Person3(String name, int age, double height, boolean married){
        this.name = name;
        this.age = age;
        this.height = height;
        this.married = married;
    }

    public String getName() {return name;}
    public int getAge() {return age;}
    public double getHeight() {return height;}
    public boolean isMarried() {return married;}
}
/*

    Используйте сериализацию с осторожностью#
    Сериализация - это мощный инструмент, использовать который следует с осторожностью. В случае сложных иерархий
    классов сериализация может привести к проблемам. Поэтому, если вам нужно хранить и считывать файлы, лучше
    задуматься о формате JSON. У него меньше проблем с переносимостью и совместимостью.
 */