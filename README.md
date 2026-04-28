# Digital Banking Application

Full-stack banking application built with Spring Boot, Java 21, React and MySQL.

## Features

### Authentication
- User registration
- Login
- Secure password encryption

### Accounts
- Active balance management
- Savings account transfers
- Money deposits

### Transfers
- Customer to customer transfers
- Savings transfers

### Cards
- Auto-generated banking cards
- Create additional cards
- Block lost cards

### Payments
- Utility bills
- Fine payments

### Exchange
- Currency conversion:
- RON
- EUR
- USD
- RUB
- JPY

### Support
- Lost card support
- PIN reset
- Assistance tickets

### My Account
- Profile management
- Update phone
- Change password
- Deactivate account

## Tech Stack

Backend:
- Java 21
- Spring Boot
- Spring Security
- Maven
- Hibernate / JPA

Frontend:
- React
- Vite
- React Router
- Axios

Database:
- MySQL (XAMPP)

## Run Backend

```bash
mvn spring-boot:run
```

Backend runs on:

```bash
http://localhost:8080
```

## Run Frontend

```bash
cd banking-ui
npm install
npm run dev
```

Frontend:

```bash
http://localhost:5173
```