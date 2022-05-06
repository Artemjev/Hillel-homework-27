***TODO:***
-----------------------------
**Notes**  app enhancement (https://github.com/Artemjev/Hillel-homework-26) using *mvc* pattern.

Improve routing in such way that controller methods can be annotated like: <br/>
    ***@GetMapping("users/{id}")*** <br/><br/>
, where ***{id}*** is the variable part of the path, which should be transmitted into the request using *req.setAttribute* (inside a **single application servlet**) 
and passed to **controller**.

*For example:* by reference ***/users/17*** in the controller ***req.getAttribute("id")*** should return **17**.<br/><br/>
*ps: simple spring:)*
 
 
***Задание:***
-----------------------------
Доработать приложение **заметки** (https://github.com/Artemjev/Hillel-homework-26) с учетом паттерна *mvc*.


Доработать маршрутизацию так, чтобы на методы контроллера можно было вешать аннотации вида: <br/>
   ***@GetMapping("users/{id}")*** <br/><br/>
, при этом ***{id}*** - переменная часть адреса, которая в **сервлете** *(1 сервлет на все приложение)* должна укладываться в запрос при помощи *req.setAttribute* 
и передаваться в **контроллер**. 

*Например:* перейдя по адресу ***/users/17*** в контроллере ***req.getAttribute("id")*** должно вернуть **17**.


 
