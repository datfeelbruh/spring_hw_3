Создать сервис бронирования номеров в отеле. На Spring data!
Есть набор номеров (имя номера - уникальное, его класс - эконом, люкс).
Есть брони (номер брони, имя номера, дата от, дата до, имя покупателя).
Все поля не null.
Хранение происходит в базе данных.
Необходимо написать веб сервис, который:
1. Может выдать все бронирования покупателя
2. Может выдать бронирование по номеру брони
3. Забронировать номер на свободную дату - ответ, если получилось: номер брони
   (в случае, если номер на эту дату забронирован - ответ: "Номер забронирован")
```
{
    "roomName": "10A",
    "beginDate": "2022-01-01",
    "endDate": "2022-02-02",
    "clientName": "dmitry"
}
```
```
OK : return booking.id
NOT_OK : return "Номер забронирован"
```
4. Удалить бронь по номеру.

*Для особо одаренных задача:
Создать базу клиентов (имя, email). Клиент создается, если он в первый раз бронирует отель.