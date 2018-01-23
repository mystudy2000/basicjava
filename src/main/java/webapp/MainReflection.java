package webapp;

import webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException,NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("dummy");
        Class<? extends Resume> resumeClass = r.getClass();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, UUID.randomUUID());
        Method method = resumeClass.getMethod("toString");
        Object result = method.invoke(r);
        System.out.println(r);
    }
}
