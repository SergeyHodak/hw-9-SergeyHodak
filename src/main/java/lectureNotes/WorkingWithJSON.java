package lectureNotes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WorkingWithJSON {
}

/*
    Работа с JSON#
    JSON - JavaScript Object Notation - это текстовый формат хранения и передачи данных. Изначально он пришел от языка
    JavaScript, но оказался настолько удобным, что сейчас это стандарт де-факто для передачи данных в интернете.
    JSON - это обычный текст. Вот пример JSON-документа:
    {
        "language": "Java",
        "version": 11,
        "features": ["oop", "multiplatform"],
        "metadata": {"fileExtension": ".java"}
    }
    Детальней про JSON можно почитать в Википедии - https://ru.wikipedia.org/wiki/JSON

    Работа с JSON в Java#
    JSON можно рассматривать как способ сохранения и восстановления Java-объектов. Мы можем взять Java-объект, и
    сохранить его в текстовом JSON-формате. А потом также можем взять текст, и сделать из этого текста Java-объект.
    Это похоже на сериализацию, но более гибко и не привязано исключительно к Java.

    Библиотека Gson#
    В мире Java есть много различных библиотек для работы с JSON. Мы рассмотрим одну из наиболее популярных библиотек
    с открытым исходным кодом от компании Google - https://github.com/google/gson
    Чтобы использовать эту библиотеку, вначале нужно добавить зависимость. Если используете Maven, то это выглядит так:

    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.8.6</version>
    </dependency>

    TODO мое дополнение, так как не удавалось импортировать библиотеку
    было: файл pom.xml
    ------------------------------------------------------------------------------------------------------------------
    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>

        <groupId>org.example</groupId>
        <artifactId>hw-9-SergeyHodak</artifactId>
        <version>1.0-SNAPSHOT</version>

        <properties>
            <maven.compiler.source>11</maven.compiler.source>
            <maven.compiler.target>11</maven.compiler.target>
        </properties>
    </project>
    -------------------------------------------------------------------------------------------------------------------

    стало: файл pom.xml
    -------------------------------------------------------------------------------------------------------------------
    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>

        <groupId>org.example</groupId>
        <artifactId>hw-9-SergeyHodak</artifactId>
        <version>1.0-SNAPSHOT</version>

        <properties>
            <maven.compiler.source>11</maven.compiler.source>
            <maven.compiler.target>11</maven.compiler.target>
        </properties>

        <dependencies>
            <dependency>
                <groupId>com.google.code.gson</groupId>
                <artifactId>gson</artifactId>
                <version>2.8.6</version>
            </dependency>
        </dependencies>
    </project>
    ------------------------------------------------------------------------------------------------------------------
    После изменения содержимого в файле pom.xml, нажать на нем ПКМ/Maven/Reload project
    TODO помог ролик, начало ролика https://youtu.be/Ig_GAqXoRro

    После этого можно начинать работу с библиотекой.

    Перевести объект в JSON-формат#
    Вот пример сохранения объекта в JSON-текст:
*/


class GsonTest {
    public static void main(String[] args) {
        Person4 person = new Person4("Bill", 30, true, 1.78);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(person);

        System.out.println(json);
    }
}

class Person4 {
    private String name;
    private int age;
    private boolean married;
    private double height;

    public Person4(String name, int age, boolean married, double height) {
        this.name = name;
        this.age = age;
        this.married = married;
        this.height = height;
    }

    public String getName() {return name;}
    public int getAge() {return age;}
    public boolean isMarried() {return married;}
    public double getHeight() {return height;}
}
/*
    Этот код после исполнения выведет такой текст:
    {
        "name": "Bill",
        "age": 30,
        "married": true,
        "height": 1.78
    }

    Получить объект из JSON-формата#
    Если мы хотим сделать обратную операцию, прочитать объект из JSON-строки, то это будет выглядеть следующим образом:
*/


class GsonTest1 {
    public static void main(String[] args) {
        String json = "{\n" +
                "  \"name\": \"Bill\",\n" +
                "  \"age\": 30,\n" +
                "  \"married\": true,\n" +
                "  \"height\": 1.78\n" +
                "}";

        Person5 person = new Gson().fromJson(json, Person5.class);
        System.out.println(person.getName());
        System.out.println(person.getAge());
        System.out.println(person.isMarried());
        System.out.println(person.getHeight());
    }
}

class Person5 {
    private String name;
    private int age;
    private boolean married;
    private double height;

    public Person5(String name, int age, boolean married, double height) {
        this.name = name;
        this.age = age;
        this.married = married;
        this.height = height;
    }

    public String getName() {return name;}
    public int getAge() {return age;}
    public boolean isMarried() {return married;}
    public double getHeight() {return height;}
}
/*
    Обратите внимание, что при десериализации из JSON нам нужно явно указывать класс объекта, который мы десериализуем.
    В нашем случае это Person5.class.

    Где на практике используется JSON#
    Краткий ответ - везде. Веб-сервера чаще всего используют JSON для запросов-ответов при реализации своих API.
    Настройки приложений часто хранятся в JSON. Поэтому разумно выделить некоторое время, чтобы вникнуть в этот формат
    хотя бы на уровне "записать объект в JSON" и "Прочитать объект из JSON-строки".
 */