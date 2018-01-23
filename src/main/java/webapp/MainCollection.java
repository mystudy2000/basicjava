package webapp;

import org.apache.commons.text.RandomStringGenerator;
import webapp.model.Resume;

import java.util.*;

public class MainCollection {
    static Resume r;
    // Length of test array and strings
    static int ArrayLengthLimit = 3;
    static int StringLengthLimit = 10;
    // Arrays for tests
    static String[] fullNameArray = new String[ArrayLengthLimit];
    static UUID[] testUUID = new UUID [ArrayLengthLimit];

    public static void main (String[] args) {
        Collection<Resume> collection = new ArrayList();

// Используем  RandomStringGenerator для заполнения коллекции
        for (int i = 0; i < ArrayLengthLimit; i++) {
            fullNameArray[i] = new RandomStringGenerator.Builder().withinRange('a', 'z').build()
                    .generate(StringLengthLimit);
            r = new Resume(fullNameArray[i]);
            collection.add(r);
            testUUID[i] = r.getUuid();
        }
        for (Resume r : collection) {
            System.out.print("* ");
            System.out.println(r);
        }
// Используем итератор для поиска и сравнения внутри коллекции
        Iterator<Resume> iterator = collection.iterator();
        int j = 0;
         while (iterator.hasNext()) {
            Resume r = iterator.next();
//            if (Objects.equals(r.getUuid(), testUUID[j])) {
//                iterator.remove();
//            }
            j=j+1;
        }
        System.out.println("Выводим коллекцию в строку");
        System.out.println(collection.toString());

// Инициализируем map с использованием  RandomStringGenerator
        Map <UUID,Resume> map = new HashMap<>();
        for (int i = 0; i < ArrayLengthLimit; i++) {
            fullNameArray[i] = new RandomStringGenerator.Builder().withinRange('a', 'z').build().generate(StringLengthLimit);
            r = new Resume(fullNameArray[i]);
            testUUID[i] = r.getUuid();
            map.put( testUUID[i],r);
            }
        for (Map.Entry<UUID, Resume> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }
// Lesson06 Iterator, Comparator and Inner Classes
        List<Resume> resumes = new ArrayList(Arrays.asList(new Resume(fullNameArray[0]), new Resume(fullNameArray[1]), new Resume(fullNameArray[2])));
        resumes.remove(1);
        System.out.println(resumes);
        }
}
