В даном проекте созданы такие тесты для API сайта [заказа бургеров](https://stellarburgers.nomoreparties.site) как:
***
1. Создание пользователя:   
   + создание уникального пользователя;
   + создание пользователя, который уже зарегистрирован;
   + создание пользователя и не заполнить одно из обязательных полей.
2. Логин пользователя:
    + логин под существующим пользователем,
    + логин с неверным логином и паролем.
3. Изменение данных пользователя:
    + с авторизацией,
    + без авторизации.
4. Создание заказа:
    + с авторизацией,
    + без авторизации,
    + с ингредиентами,
    + без ингредиентов,
    + с неверным хешем ингредиентов.
5. Получение заказов конкретного пользователя:
    + авторизованный пользователь,
    + неавторизованный пользователь.
***
В проекте используется java 11, библиотеки: junit4 для тестирования, rest assured для API, faker для генерации данных,
lombok как кодогенератор, составлен отчет о тестировании в allure.