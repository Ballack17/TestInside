# TestInside

###### Тестовое приложение по работе с JWT

###### **Установка приложения**

Для удобства собранный файл находится в папке "mainapp" (это на тот случай если у вас на ПК не установлены переменные среды JAVA_HOME)
В одноу папку необходимо скопировать mainapp, файлы docker-compose.yml и Dockerfile - если вы копируете весь проект, то все файлы уже находятся в нужном месте.
открываете терминал в папке проекта и вводите
docker-compose build   / у вас сбилдится образ
docker-compose up   / в докере у вас запустится приложение вместе с базой данных необходимой для работы

при запуске используется Preliquibase и Liquibase 
создаётся БД (postgre) с данными для проверки работоспособности

###### **endpoint's**

_"/auth"_ метод POST 
    входные данные в формате Json { "name": String, "password": String}
происходит поиск в БД юзера и проверка пароля
ответ в формате Json {"token": String /сгенерированный токен/} и 200 ОК
в случае отсутствия пользователя ответ "Incorrect username or password" и 401 Unauthorized

_"api/v1/message"_ метод POST 
только для Авторизованных пользователей. Header должен содержать поле "Authorization", формат Токена "Bearer_" + токен
    входные данные в формате Json { "name": String, "message": String}
происходит поиск юзера по полю "name", и проиходит добавляется значения поля "message" в БД, в ответ 200 ОК. 
если в поле "message" значение "history_10", то сообщение не сохраняется в БД,
в ответ приходит Json со списком из 10-ти последних сообщений пользователя "name" в БД и статус 200 ОК

примеры Curl запросов в файле "curl запросы.txt"
