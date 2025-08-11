# 🎬 Movie Catalogue System

## Overview

This Java Spring Boot application connects to **The Movie Database (TMDb) API** to let users:

- Browse trending movies
- Search for movies by keyword
- View detailed movie information
- Mark movies as favorites and manage them

Favorites are saved in an **H2 in-memory database** to persist user selections during the app runtime.

---

## Features

- **Trending Movies:** View popular movies right on the homepage
- **Search:** Find movies by title using a search bar
- **Movie Details:** See full movie info including poster, release date, rating, and description
- **Favorites Management:** Add or remove movies from favorites via list or detail pages
- **Favorites Listing:** View all favorite movies in one place and remove any if desired

---

## Technology Stack

- **Java 17+**
- **Spring Boot** (MVC architecture)
- **Thymeleaf** for server-rendered HTML views
- **H2 Database** (in-memory) for storing favorite movies
- **RestTemplate** to call TMDb API
- **Bootstrap 5** for responsive UI styling

---

## Setup Instructions

### 1. Obtain TMDb API Key

- Create a free account at [TMDb](https://www.themoviedb.org/)
- Navigate to API section and copy your API key

### 2. Configure the Application

- Open `src/main/resources/application.properties`
- Replace `tmdb.api.key` value with your API key: fe136b5420f8f7252f96fdc4a382fb96


Build and Run the Application
Using Maven

Run the following commands:

mvn clean install
mvn spring-boot:run

After building, run:

java -jar target/moviecatalogue-0.0.1-SNAPSHOT.jar

Open your web browser and go to:
http://localhost:8081/
http://localhost:8081/h2-console