package com.hillel.artemjev.notebook.infrastructure;

import com.hillel.artemjev.notebook.infrastructure.annotations.*;
import com.hillel.artemjev.notebook.infrastructure.reflection.PackageScanner;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DispatcherServlet extends HttpServlet {
    private final String packege = "com.hillel.artemjev.notebook";
    private final ApplicationContext context = new ApplicationContext(packege);
    private final PackageScanner packageScanner = new PackageScanner();
    private final List<Class<?>> controllers;

    public DispatcherServlet() {
        this.controllers = packageScanner
                .findClassesWithAnnotation(
                        packege,
                        Controller.class
                );
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
        for (Class<?> controller : controllers) {
            for (Method method : controller.getDeclaredMethods()) {
                String address = getUriFromMethod(req, method);
                if (address == null) continue;
                String requestURI = req.getRequestURI();

                String addr = req.getContextPath() + "/" + address;
                if (addr.equalsIgnoreCase(requestURI)) {
                    invokeController(req, res, controller, method);
                    return;
                }
                //------------------------------------------------------------
                String regexpAddr = addr.replace("{id}", "(\\d+)");
                if (requestURI.matches(regexpAddr)) {
                    Pattern pattern = Pattern.compile(regexpAddr);
                    Matcher matcher = pattern.matcher(requestURI);
                    matcher.matches();
                    String strId = matcher.group(1);
                    req.setAttribute("id", strId);
                    invokeController(req, res, controller, method);
                    return;
                }
            }
        }
        res.setStatus(404);
        res.getWriter().write("NOT FOUND");
    }

    private String getUriFromMethod(HttpServletRequest req, Method method) {
        String address = null;
        if (
                req.getMethod().equalsIgnoreCase("get")
                        && method.isAnnotationPresent(GetMapping.class)
        ) {
            address = method.getAnnotation(GetMapping.class).value();
        }


        if (
                req.getMethod().equalsIgnoreCase("post")
                        && method.isAnnotationPresent(PostMapping.class)
        ) {
            address = method.getAnnotation(PostMapping.class).value();
        }
        if (
                req.getMethod().equalsIgnoreCase("put")
                        && method.isAnnotationPresent(PutMapping.class)
        ) {
            address = method.getAnnotation(PutMapping.class).value();
        }
        if (
                req.getMethod().equalsIgnoreCase("delete")
                        && method.isAnnotationPresent(DeleteMapping.class)
        ) {
            address = method.getAnnotation(DeleteMapping.class).value();
        }
        return address;
    }

    private void invokeController(HttpServletRequest req, HttpServletResponse res, Class<?> controller, Method method) {
        Object controllerInstance = context.getBeanByType(controller);
        try {
            method.invoke(controllerInstance, req, res);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
