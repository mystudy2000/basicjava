package webapp;
import webapp.exceptions.StorageException;
import webapp.model.Resume;
import webapp.storage.ArrayStorage;
import webapp.storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

/*** Test for ArrayStorage **/
class MainArray {

    private final static Storage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) throws IOException, StorageException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.println("Проверка методов Resume - сохранение нового резюме по fullname, а удаление, выборка по uuid и также модификация fullname по uuid ");
            System.out.print("Введите команды - (save fullname | delete uuid | get uuid | update uuid fullname | clear | size | list | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 3) {
                System.out.println("Неверная команда.");
                continue;
            }

            switch (params[0]) {
                case "list":
                    // вывод всех записей
                    printAll();
                    break;
                case "size":
                    // общее количество сохраненных записей
                    System.out.println("Размер: "+ARRAY_STORAGE.size());
                    break;
                case "save":
                    // сохранение новой записи fullname. uuid создается программно.
                    r = new Resume(params[1]);
                    ARRAY_STORAGE.save(r);
                    printAll();
                    break;
                case "update":
                    // модификация записи с ключом uuid (param 1) новым значением fullname (param 2)
                    String fullName=params[2];
                    r = new Resume(fullName);
                    ARRAY_STORAGE.update(UUID.fromString(params[1]),r);
                    printAll();
                    break;
                case "delete":
                    // удаление записи по значению uuid
                    ARRAY_STORAGE.delete(UUID.fromString(params[1]));
                    System.out.println("Запись удалена " + params[1]);
                    printAll();
                    break;
                case "get":
                    // выбор записи по значению uuid
                    System.out.println(ARRAY_STORAGE.get(UUID.fromString(params[1])));

                    break;
                case "clear":
                    // удаление всех записей - очистка
                    ARRAY_STORAGE.clear();
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    private static void printAll() {
        Resume[] all = ARRAY_STORAGE.getAll();
        System.out.println("----------------------------");
        if (all.length == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.print("UUID="+r.uuid);System.out.println(" Full name:"+r.fullName);
            }
        }
        System.out.println("----------------------------");
    }
}

