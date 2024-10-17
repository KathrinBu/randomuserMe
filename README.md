# Инструкция по запуску и настройке приложения

### Требования к среде:
**Java**: версия 17+
**Maven**: для управления зависимостями и сборки проекта
**PostgreSQL**: локальная база данных для хранения пользователей

### Запуск приложения:
Локальная база данных: Приложение использует PostgreSQL, подключение к базе данных настроено через файл .env. 
Убедитесь, что PostgreSQL настроен локально и база данных запущена.
Доступ к приложению: После запуска, веб-интерфейс доступен по адресу http://localhost:8082. Прописано в файле .env

### Как пользоваться:
#### В Веб-интерфейсе:
- выбор подходящей команды;
- "Загрузить пользователей" - форма для самостоятельного ввода;
- "Экспортировать пользователей" форма для экспорта пользователей в CSV.
- "Загрузить случайных пользователей" форма для загрузки случайных пользователей в базу данных 
через внешний API https://api.randomuser.me/;
- кнопка "Выполнить", которая реализует все указанные запросы

### Краткое описание реализации:
#### Основные классы
- UserActionController
Этот контроллер обрабатывает запросы с веб-формы, позволяет загружать пользователей из внешнего API 
и экспортировать их в CSV.
Основной метод:
handleUserAction(): перенаправляет действия пользователя в соответствующие сервисы.
- UserController
Этот контроллер отвечает за получение случайного пользователя с внешнего API https://api.randomuser.me/. 
Он использует сервис RandomUserService для выполнения запроса и возвращает данные в формате JSON.
Методы:
getRandomUser():
Этот метод обрабатывает GET-запрос на URL /random-user. Он вызывает метод getRandomUser() из сервиса RandomUserService, 
который обращается к API случайных пользователей. Полученный JSON-объект возвращается в виде строки, отформатированной.
Назначение:
Контроллер позволяет пользователям получить случайные данные о пользователях.
- UserExportController
Этот контроллер обрабатывает экспорт пользователей в CSV-файл. Он использует сервис UserExportService 
для выполнения процесса экспорта.
Методы:
exportToCSV(@RequestParam String filePath):
Обрабатывает GET-запрос на URL /users/export/csv с параметром filePath, который указывает путь для сохранения CSV-файла. 
Вызывает метод exportToCSV() из UserExportService для экспорта пользователей в указанный файл. По завершении возвращает 
успешное сообщение с указанием пути к файлу.
Назначение:
Предоставляет пользователям возможность выгружать данные пользователей в файл CSV.
- WebController
Контроллер для обработки веб-запросов на стороне клиента. 
Основной метод:
showUserForm(): отображает форму для ввода данных пользователями.
- UserActionService
Логика загрузки пользователей из API, выполнения команд и обработки действий вынесена сюда.
Основные методы:
loadUsers(): загружает пользователей с внешнего API.
exportUsers(): экспортирует пользователей в CSV.
- UserExportService
Этот класс отвечает за логику экспорта пользователей в CSV-файл.
Основной метод:
exportUsersToCsv(): формирует CSV файл с данными пользователей.
- UserService
Класс для взаимодействия с базой данных PostgreSQL. Сохраняет пользователей и управляет их данными.
Основные методы:
saveUsers(): сохраняет пользователей в базу данных.
- RandomUserService
Этот сервис отвечает за взаимодействие с внешним API для получения случайных пользователей. Он использует RestTemplate 
для выполнения HTTP-запросов.
Методы:
getRandomUser():
Этот метод делает GET-запрос к API https://randomuser.me/api/ с помощью RestTemplate и получает ответ в виде строки. 
И преобразует ответ в объект JSONObject для дальнейшей обработки.
Назначение:
Сервис реализует процесс получения случайных пользователей, предоставляет логику работы с внешним API и предоставляяя 
метод для получения данных о пользователе.
- UserActionRandomService
Этот сервис управляет бизнес-логикой, связанной с загрузкой случайных пользователей и их экспортом. Он использует 
RandomUserService для получения данных о случайных пользователях и другие сервисы для сохранения пользователей 
и экспорта.
Методы:
loadRandomUsers(int count):
Этот метод загружает указанное количество случайных пользователей. Он вызывает метод getRandomUser() из 
RandomUserService в цикле и обрабатывает полученные данные. Из полученного JSON выбираются имя, фамилия и адрес 
электронной почты, после чего данные сохраняются с помощью userService.saveUser().
loadUsers(String firstName, String lastName, String email):
Позволяет загружать конкретного пользователя с заданными именем, фамилией и адресом электронной почты, используя 
метод saveUser() из userService.
exportUsers(String filePath):
Этот метод вызывает exportToCSV(filePath) из userExportService, чтобы экспортировать пользователей в указанный CSV-файл.
Назначение:
Сервис объединяет загрузку случайных пользователей и экспорт данных, позволяет управлять операциями с 
пользователями. Он предоставляет методы для обработки как случайных, так и конкретных пользователей.

### Что нуждается в доработке
- Оптимизация взаимодействия с внешним API:
Загрузка большого количества пользователей может занять много времени. 
Добавление асинхронного запроса помогло бы повысить производительность.

- Docker для базы данных:
В текущей реализации база данных работает локально. Для удобства развертывания на других машинах стоит 
настроить PostgreSQL в Docker.

- Более глубокая проработка документации.

### Узкие места
- Экспорт всех пользователей в CSV может привести к проблемам с памятью, если их много. 
Можно добавить стриминговую обработку данных при записи в файл.
- проблемы могут возникнуть, если множество пользователей одновременно будут использовать приложение для экспорта или 
загрузки данных. Можно рассмотреть возможность кэширования.

### Что не удалось сделать
- веб-форму можно доработать для удобства использования
- интеграция в Docker, это улучшило бы гибкость развертывания.

### Как реализовать экспорт в Excel
- Создание нового сервисного класса, который будет отвечать за выгрузку пользователей в формате Excel. 
- Добавление метода в существующий сервис UserExportService, который будет принимать путь к файлу и использовать новый 
класс для экспорта в Excel.
- В контроллер UserExportController добавить новый эндпоинт для экспорта пользователей в Excel.