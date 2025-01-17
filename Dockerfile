# Шаг 1: сборка приложения
FROM gradle:7.6.0-jdk17 AS build

# Копируем исходный код в контейнер
WORKDIR /home/gradle/src
COPY . .

# Сборка проекта
RUN gradle clean build -x test

# Шаг 2: создание минимального контейнера для запуска приложения
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем JAR из сборочного контейнера
COPY --from=build /home/gradle/src/build/libs/book-store-2.0.jar app.jar

COPY --from=build /home/gradle/src/images ./images
# Открываем порт приложения
EXPOSE 8080

# Запускаем приложение
CMD ["java", "-jar", "app.jar"]
