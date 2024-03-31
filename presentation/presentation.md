---
marp: true
theme: default
---

# Створення RESTful-сервісів з використанням Spring Boot

Доповідачі:
- Сутулов Нікіта ІМ-12
- Назаренко Олександр ІМ-12

---

## Чому Spring?

Переваги використання Spring для CRUD-додатків:
- Модульність та гнучкість
- Ін'єкція залежностей (DI) та інверсія управління (IoC)
- Спрощене управління транзакціями
- Надійна підтримка тестування
- Безшовна інтеграція з іншими технологіями

---

## Побудова додатку CRUD за допомогою Spring

Основні компоненти:
- Контролери
- Сервіси
- Репозиторії
- Представлення (за потреби)

![bg right width:500px](https://raw.githubusercontent.com/NikitaSutulov/spring-rest-demo/master/presentation/assets/layers.png)

---

## Spring Data JPA

Переваги використання Spring Data JPA для доступу до даних
- Спрощене рівень доступу до даних
- Автоматичні операції CRUD
- Методи запитів
- Підтримка пагінації та сортування

Інтеграція з Hibernate або іншими постачальниками JPA

---

## Налаштування середовища

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [PostgreSQL 16.2](https://www.postgresql.org/download/)
- [Spring Initializer](https://start.spring.io/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- [Postman](https://www.postman.com/downloads/)
