# Spring_Boot_App

Status of Last Deployment:<br>
<img src="https://github.com/DimaGotaro/Spring_Boot_App-Git-Action-/workflows/Build/badge.svg?branch=master"><br>

Приветствую!

Для запуска приложения необходимо настроить подключение к базе данных.
В проекте используется PostgreSQL. 
Настройте параметры вашего подключения к базе данных в файле
application.properties
расположенном по пути:
src/main/resources/application.properties
Вам необходимо изменить первые три строки под ваше подключение.
Также в этом файле нужно изменить последнюю строку(upload.path).
В upload.path находится путь к папке uploads в которой хранятся изображения.
Настройте свой путь. Последний каталог в пути, в проекте это uploads,
может не существовать, он будет создан автоматически.

Далее после запуска приложения перейдите в браузере по адресу:
http://localhost:8080/
Нажмите на 'Kacher'
Для доступа ко всем возможностям приложения необходим доступ ADMIN.
Для входа доступен пользователь с правами ADMIN,
введите в поле User Name: Admin
       и в поле Password: q.
       
Можете так же зарегистрировать свою учётную запись,
однако у неё будут права USER.
