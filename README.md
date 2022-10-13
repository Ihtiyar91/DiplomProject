# ***Дипломный проект по курсу «Тестировщик ПО»***
## ***О проекте***

Проект представляет собой комплексное автоматизированное тестирование сервиса по покупке туров через интернет-банк. Купить тур можно с помощью двух способов:

- оплата с помощью дебетовой карты
- покупка в кредит

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:

- сервису платежей (Payment Gate)
- кредитному сервису (Credit Gate)

Приложение в собственной СУБД сохраняет информацию о том, каким способом был совершён платёж и успешно ли он был совершён.

## ***Документация***
- [План  тестирования](https://github.com/Ihtiyar91/DiplomProject/blob/f23e12621ea69a718f65f66185e18453c9cdeead/Plan.md)
- [Отчет по итогам тестирования](https://github.com/Ihtiyar91/DiplomProject/blob/aef6ad9c52ca006c8e86eff7b759f07ddb20f551/Report.md)
- [Отчет по итогам автоматизации](https://github.com/Ihtiyar91/DiplomProject/blob/4c4aa262d226aaef722c30f35b460d0d26977617/Summary.md)

## ***Перед началом работы***
***1.*** Необходимо склонировать [репозиторий](https://github.com/Yana-85/QA-Diploma) с помощью команды `git clone`.

***2.*** Установить [Docker](https://www.docker.com/), инструкция по установке по [ссылке](https://github.com/netology-code/aqa-homeworks/blob/master/docker/installation.md).

***3.*** Открыть проект в Intellij IDEA, инструкция по установке по [ссылке](https://github.com/netology-code/javaqa-homeworks/blob/master/intro/idea.md).
## ***Запуск***
***1. Запускаем docker-контейнер с СУБД MySQL и PostgreSQL, а также Node.js командой в терминале:***
```
docker-compose up -d
```
***2. Запускаем SUT***

- для СУБД ***MySQL***:
```
java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar ./artifacts/aqa-shop.jar
```
- для СУБД ***PostgreSQL***:
```
java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar ./artifacts/aqa-shop.jar
```

***3. Запускаем авто-тесты***

Для этого открываем еще одну вкладку в терминале и вводим следующую команду:

- для СУБД ***MySQL***:

```
./gradlew clean test -D db.url=jdbc:mysql://localhost:3306/app
```

- для СУБД ***PostgreSQL***:

```
./gradlew clean test -D db.url=jdbc:postgresql://localhost:5432/app
```
***4. Генерируем отчет Allure по итогам тестирования***
```
./gradlew allureServe
```
Отчет открывается после прохождения тестов автоматически в браузере по умолчанию.
* Если отчет не открывается автоматически в браузере, то выполнить команду: ```./gradlew allureReport``` и открыть отчет вручную (файл index.html) по адресу: ```.\build\reports\allure-report\allureReport```

## ***Завершение работы Sut***
Для завершения работы SUT, необходимо в терминале, где был запущен SUT, ввести команду:
```
 Ctrl+C
```
## ***Остановка и удаление контейнеров***
***1.*** Для остановки контейнеров в терминале IDEA вводим следующую команду:
```
docker-compose stop
``` 
***2.*** Для удаления контейнеров в терминале вводим команду:
```
docker-compose down
``` 
