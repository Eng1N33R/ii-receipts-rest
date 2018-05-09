# Receipts Rest API

REST API для работы с базой данных заказов.

### Запуск

Запускать при помощи Maven task `spring-boot:run`. Работает на `localhost:8080`.

По умолчанию работает с моей удалённой базой, для которой уже была проведена
изначальная настройка при помощи Liquibase. Подключение к другой базе
настраивается в `src/main/resources/application.properties`.

### Авторизация

Авторизация проводится посредством хэдеров Basic Authentication.

| Username | Password |
| --- | --- |
| `user` | `qwerty` |
| `debug` | `debug` |

### Endpoints

| Endpoint | Действие |
| --- | --- |
| `GET /api/orders` | Получить детали всех заказов |
| `POST /api/orders` | Создать новый заказ (JSON-тело) |
| `GET /api/orders/{id}` | Получить детали заказа `id` |
| `GET /api/orders/pdf/{id}` | Получить PDF-чек для заказа `id` |
| `GET /api/products/find/{id}/{limit}` | Найти `limit` продуктов, ID которых начинаются со строки `id` |