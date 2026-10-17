# scooter_rental

Учебный проект для изучения DevOps. Тема проекта: прокат самокатов.

В этой части проекта простое API на Java упаковано в Docker-контейнер.

## Что внутри

| Файл | Назначение |
|------|------------|
| `api/ApiServer.java` | исходный код API (пакет `api`, порт 8080) |
| `Dockerfile` | инструкции для сборки образа, написан вручную |
| `.gitignore` | исключает скомпилированные `*.class` из репозитория |

## API

Приложение запускает веб-сервер на порту **8080** и отвечает на запрос:

| Метод | Адрес | Ответ |
|-------|-------|-------|
| GET | `/health` | текстовый ответ, например `hello world)` |

Эндпоинт `/health` нужен для проверки, что API работает.

## Dockerfile

```dockerfile
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY api/ApiServer.java api/ApiServer.java
RUN javac api/ApiServer.java
EXPOSE 8080
CMD ["java", "api.ApiServer"]
```

| Инструкция | Зачем |
|------------|-------|
| `FROM` | базовый образ с Java 17 (JDK, чтобы был компилятор) |
| `WORKDIR` | рабочая папка `/app` внутри контейнера |
| `COPY` | копирует исходный код в контейнер |
| `RUN` | при сборке образа компилирует код (`javac`) |
| `EXPOSE` | документирует порт приложения, сам порт не открывает |
| `CMD` | команда по умолчанию, запускает сервер при старте контейнера |

## Запуск без Docker (локальная проверка)

Из корня репозитория:

```
javac api/ApiServer.java
java api.ApiServer
```

Класс лежит в пакете `api`, поэтому запускать его нужно из корня репозитория командой `java api.ApiServer`, а не из папки `api`.

## Сборка образа

```
docker build -t scooter-api .
```

## Запуск контейнера

```
docker run -d -p 8080:8080 --name scooter scooter-api
```

`-p 8080:8080` пробрасывает порт: слева порт на компьютере, справа порт внутри контейнера. Он совпадает с портом в коде (`8080`).

## Проверка

Список запущенных контейнеров:

```
docker ps
```

Запрос к API в браузере: http://localhost:8080/health

Запрос к API в терминале:

```
curl http://localhost:8080/health
```

Логи контейнера:

```
docker logs scooter
```

## Результат проверки

`docker ps`:

```
(сюда вставить вывод команды docker ps)
```

Запрос к API (http://localhost:8080/health):

```
(сюда вставить ответ API)
```

`docker logs scooter`:

```
Server started on port 8080
```

## Как обновить после изменения кода

После правки `api/ApiServer.java` образ нужно пересобрать, иначе контейнер будет работать со старым кодом:

```
docker stop scooter
docker rm scooter
docker build -t scooter-api .
docker run -d -p 8080:8080 --name scooter scooter-api
```

## Остановка и удаление

```
docker stop scooter
docker rm scooter
docker rmi scooter-api
```