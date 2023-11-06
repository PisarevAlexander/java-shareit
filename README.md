# java-shareit
Cервис для шеринга вещей. Сервис позволяет пользователям, во-первых, возможность рассказывать, какими вещами они готовы поделиться, а во-вторых, находить нужную вещь и брать её в аренду на какое-то время. Сервис не только позволяет бронировать вещь на определённые даты, но и закрывать к ней доступ на время бронирования от других желающих. На случай, если нужной вещи на сервисе нет, у пользователей остается возможность оставлять запросы.

## Stack
- Java 11,
- Spring Boot,
- Maven,
- Lombok,
- PostgresSQL,
- LiquidBase,
- Hibernate,
- Docker.

## API
**Booking**
- POST /bookings - добавить новое бронирование
- PATCH /bookings/{bookingId} - изменить бронироавние
- GET /bookings/{bookingId} - получить бронирование по id
- GET /bookings - получить список бронирований
- GET /bookings/owner - получить бронирования пользователя

**Item**
- POST /items - добавить новый предмет
- PATCH /items/{itemId} - обновить предмет
- GET /items/{itemId} - получить предмет по id
- GET /items - получить список предметов
- GET /items/search - поиск предметов
- POST /items/{itemId}/comment - опубликовать комментарий

**Request**
- POST /requests - новый запрос
- GET /requests - получить запросы
- GET /requests/all - получить все запросы
- GET /requests/{requestId} - получить запрос по id

**User**
- POST /users - добавить нового пользователя
- GET /users/{userId} - получить пользователя по id
- GET /users - получить пользователей
- PATCH /users/{userId} - обновать пользователя
- DELETE /users/{userId} - удалить пользователя

## Launch
Используйте docker-compose up для развертывания приложения.

## ERD
![ERD](https://github.com/PisarevAlexander/java-shareit/blob/main/ERD.png?raw=true)
