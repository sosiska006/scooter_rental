# scooter_rental

Учебный проект для изучения DevOps. Тема проекта: прокат самокатов.

REST API на Java (Spring Boot) с базой данных PostgreSQL. Приложение и база запускаются одной командой через Docker Compose.

## Что внутри

| Файл | Назначение |
|------|------------|
| `src/main/java/api/` | исходный код API (сущность `Scooter`, репозиторий, контроллер) |
| `src/main/resources/application.properties` | настройки подключения к БД (берутся из переменных окружения) |
| `pom.xml` | зависимости и сборка (Maven) |
| `Dockerfile` | сборка образа API (двухэтапная: `builder` и `runtime`) |
| `docker-compose.yml` | описание сервисов `api` и `db` |
| `.dockerignore`, `.gitignore` | исключают лишние файлы |

## Архитектура

```
API (api, порт 8080) -> PostgreSQL (db, postgres:16)
```

- **api** - Java-приложение, собирается из `Dockerfile`.
- **db** - PostgreSQL 16, данные хранятся в volume `db_data` (`/var/lib/postgresql/data`).
- Сервисы находятся в общей сети `scooter-net`. API обращается к базе по имени сервиса `db`.
- Настройки подключения передаются через `environment`.
- `depends_on` с `service_healthy`: API стартует только после того, как база прошла healthcheck.

## Сущность Scooter

| Поле | Тип | Описание |
|------|-----|----------|
| `id` | число | создаётся автоматически |
| `model` | строка | модель самоката |
| `batteryLevel` | число | заряд, % |
| `status` | строка | `AVAILABLE` или `RENTED` |
| `pricePerMinute` | число | цена за минуту |

## API

| Метод | Адрес | Действие |
|-------|-------|----------|
| GET | `/health` | проверка работы API |
| GET | `/scooters` | список всех самокатов |
| GET | `/scooters/{id}` | один самокат |
| POST | `/scooters` | создать самокат |
| PUT | `/scooters/{id}` | изменить самокат |
| DELETE | `/scooters/{id}` | удалить самокат |

## Запуск

Нужен установленный Docker Desktop. Из корня проекта:

```
docker compose up --build
```

Остановка (данные сохраняются):

```
docker compose stop
```

Удаление контейнеров и сети (данные в volume сохраняются):

```
docker compose down
```

Внимание: `docker compose down -v` удаляет volume `db_data` вместе с данными.

## Проверка

Состояние сервисов:

```
docker compose ps
```

Логи:

```
docker compose logs
```

## Примеры curl-запросов

Команды для Windows PowerShell (там нужно писать `curl.exe`).

Проверка работы:

```
curl.exe http://localhost:8080/health
```

Create:

```
'{"model":"Xiaomi M365","batteryLevel":90,"status":"AVAILABLE","pricePerMinute":8.5}' | curl.exe -i -X POST http://localhost:8080/scooters -H "Content-Type: application/json" -d "@-"
```

Read (все и один):

```
curl.exe http://localhost:8080/scooters
curl.exe http://localhost:8080/scooters/1
```

Update:

```
'{"model":"Xiaomi M365","batteryLevel":60,"status":"RENTED","pricePerMinute":8.5}' | curl.exe -i -X PUT http://localhost:8080/scooters/1 -H "Content-Type: application/json" -d "@-"
```

Delete:

```
curl.exe -i -X DELETE http://localhost:8080/scooters/2
```

Для Linux/macOS: `curl -X POST http://localhost:8080/scooters -H "Content-Type: application/json" -d '{"model":"Xiaomi M365","batteryLevel":90,"status":"AVAILABLE","pricePerMinute":8.5}'`

## Проверка сохранения данных после перезапуска

1. Создать самокат (POST) и изменить его (PUT).
2. Выполнить `docker compose down` (без `-v`).
3. Выполнить `docker compose up -d`.
4. Выполнить `curl.exe http://localhost:8080/scooters` - созданные данные на месте, потому что хранятся в volume `db_data`.

## Результаты проверки

`docker compose ps`:

```
(вставить вывод docker compose ps)
```

CRUD-запросы:

```
(вставить команды и ответы: POST, GET, PUT, DELETE)
```

Данные после перезапуска (`docker compose down` и `docker compose up`):

```
(вставить ответ GET /scooters после перезапуска)
```

`docker compose logs`:

```
(вставить фрагмент логов api и db)
```