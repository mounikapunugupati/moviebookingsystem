# 🎬 Movie Booking System

## 📌 Overview

Movie Booking System is a full-stack web application that enables users to browse movies, view show timings, select seats, and book movie tickets online. The application provides a seamless ticket booking experience with an intuitive user interface and efficient backend management.

This project is developed using **Spring Boot**, **Java**, **MySQL**, **HTML**, **CSS**, and **JavaScript**.

---

## 🚀 Features

### User Features
- User Registration and Login
- Browse Available Movies
- View Movie Details
- View Show Timings
- Select Seats
- Book Movie Tickets
- View Booking History
- Cancel Bookings

## 🛠️ Technologies Used

### Backend
- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate

### Frontend
- HTML5
- CSS3
- JavaScript

### Database
- MySQL

### Tools & Platforms
- Maven
- Git
- GitHub
- Postman
- IntelliJ IDEA / Eclipse
- MySQL Workbench

---

## 🏗️ Architecture

```text
Frontend (HTML, CSS, JavaScript)
            │
            ▼
     Spring Boot Backend
            │
            ▼
 Spring Data JPA/Hibernate
            │
            ▼
       MySQL Database
```

---

## 📂 Project Structure

```text
MovieBookingSystem/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/moviebooking/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── repository/
│   │   │       ├── entity/
│   │   │       └── MovieBookingApplication.java
│   │
│   │   ├── resources/
│   │   │   ├── static/
│   │   │   │   ├── css/
│   │   │   │   ├── js/
│   │   │   │   └── images/
│   │   │   ├── templates/
│   │   │   └── application.properties
│
├── pom.xml
├── README.md
└── .gitignore


## 🔄 Application Workflow

### 1. User Registration
- User creates an account.
- Details are stored in the database.

### 2. User Login
- User enters credentials.
- System validates login information.

### 3. Browse Movies
- User views available movies.
- User selects a movie.

### 4. Select Show
- User chooses a show timing.
- Available seats are displayed.

### 5. Book Ticket
- User selects seats.
- Booking details are saved.

### 6. Confirmation
- Ticket booking is confirmed.
- User can view booking details.

---

## 📚 REST API Endpoints

### Authentication

```http
POST /register
POST /login
```

### Movies

```http
GET    /movies
GET    /movies/{id}
POST   /movies
PUT    /movies/{id}
DELETE /movies/{id}
```

### Shows

```http
GET    /shows
POST   /shows
PUT    /shows/{id}
DELETE /shows/{id}
```

### Bookings

```http
POST   /bookings
GET    /bookings
DELETE /bookings/{id}
```

---

## 🎯 Key Modules

### Authentication Module
- User Registration
- User Login
- Session Management

### Movie Management Module
- Add Movies
- Edit Movies
- Delete Movies
- View Movies

### Show Management Module
- Schedule Shows
- Update Show Timings
- Delete Shows

### Booking Module
- Seat Selection
- Ticket Booking
- Booking Cancellation

### User Module
- Profile Management
- Booking History

---


## 🧪 Testing

The application can be tested using:

- Postman
- Browser Testing
- JUnit
- Integration Testing

---



## 🎓 Learning Outcomes

This project helped in understanding:

- Spring Boot Development
- MVC Architecture
- REST API Development
- Database Design
- CRUD Operations
- JPA and Hibernate
- Frontend Development
- Full-Stack Application Development

---

## 👩‍💻 Author

**Mounika Punugupati**

B.Tech Final Year Student

### Technical Skills
- Java
- Spring Boot
- MySQL
- HTML
- CSS
- JavaScript
- Git & GitHub

---

## 🎥 Live Demo

Watch the live demonstration of the Movie Booking System here:

🔗 Demo Video:
https://drive.google.com/file/d/1hErt8uS-Xd8v65uo-i_Dlj4lDsqgECR-/view?usp=sharing

