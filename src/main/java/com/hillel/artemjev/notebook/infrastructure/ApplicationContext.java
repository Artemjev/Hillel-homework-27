package com.hillel.artemjev.notebook.infrastructure;

import com.hillel.artemjev.notebook.infrastructure.annotations.Autowired;
import com.hillel.artemjev.notebook.infrastructure.annotations.Component;
import com.hillel.artemjev.notebook.infrastructure.annotations.Controller;
import com.hillel.artemjev.notebook.infrastructure.reflection.PackageScanner;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ApplicationContext {
    private Map<Class, Object> beans = new HashMap<>();
    private final PackageScanner packageScanner = new PackageScanner();
    private final String packege;

    public ApplicationContext(String packageName) {
        this.packege = packageName;
        createBeans();
    }

    private void createBeans() {
        List<Class> componentsClasses = getComponentsClasses();
        for (Class componentsClass : componentsClasses) {
            createBean(componentsClass);
        }
        for (Class componentsClass : componentsClasses) {
            postProcessBean(beans.get(componentsClass));
        }
    }

    public Object getBeanByType(Class type) {
        return beans.keySet().stream()
                .filter(type::isAssignableFrom)
                .findFirst()
                .map(cls -> beans.get(cls))
                .orElse(null);
    }

    private List<Class> getComponentsClasses() {
        List<Class<?>> list1 = packageScanner.findClassesWithAnnotation(packege, Controller.class);
        List<Class<?>> list2 = packageScanner.findClassesWithAnnotation(packege, Component.class);

        return Stream
                .concat(list1.stream(), list2.stream())
                .collect(Collectors.toList());
    }

    private void createBean(Class componentsClass) {
        try {
            Object object = componentsClass.getConstructor().newInstance();
            beans.put(componentsClass, object);
        } catch (Exception e) {
            throw new RuntimeException("ERROR CREATE BEAN " + componentsClass.getSimpleName(), e);
        }
    }

    private void postProcessBean(Object bean) {
        List<Field> fields = Arrays.stream(bean.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Autowired.class))
                .collect(Collectors.toList());

        for (Field field : fields) {
            field.setAccessible(true);
            Class type = field.getType();

            Object value = getBeanByType(type);

            try {
                field.set(bean, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
