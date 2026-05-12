# 💰 Personal Finance Dashboard — Backend

Java Spring Boot REST API backend for the Personal Finance Dashboard.

---

## 🛠 Tech Stack

| Layer       | Technology              |
|-------------|-------------------------|
| Language    | Java 17                 |
| Framework   | Spring Boot 3.2         |
| Database    | PostgreSQL               |
| Auth        | JWT (JSON Web Tokens)   |
| ORM         | Spring Data JPA         |
| Security    | Spring Security         |
| Build       | Maven                   |

---

## 📁 Project Structure

```
src/main/java/com/finance/app/
├── FinanceDashboardApplication.java   ← Entry point
├── config/
│   ├── SecurityConfig.java            ← JWT filter + CORS
│   ├── JwtUtil.java                   ← Token generate/validate
│   └── GlobalExceptionHandler.java    ← Error handling
├── controller/
│   ├── AuthController.java            ← /api/auth/*
│   ├── TransactionController.java     ← /api/transactions/*
│   └── DashboardController.java       ← /api/dashboard/*
├── service/
│   ├── AuthService.java
│   └── TransactionService.java
├── repository/
│   ├── UserRepository.java
│   └── TransactionRepository.java
├── model/
│   ├── User.java
│   └── Transaction.java
└── dto/
    └── FinanceDTOs.java
```
## 🔗 Related
- 🖥️ Frontend: [Finance Dashboard UI](https://github.com/Wajid148/my-project/blob/main/finance-dashboard-v4.html)
---
## 👨‍💻 Author
- GitHub: [@Wajid148](https://github.com/Wajid148)

## ⚙️ Setup

### 1. Prerequisites
- Java 17+
- Maven 3.8+
- PostgreSQL 14+

### 2. Create the Database

```sql
CREATE DATABASE finance_dashboard;
CREATE USER finance_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE finance_dashboard TO finance_user;
```

### 3. Configure `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/finance_dashboard
spring.datasource.username=finance_user
spring.datasource.password=your_password

app.jwt.secret=your_super_secret_key_min_32_characters_long
app.cors.allowed-origins=http://localhost:3000,http://127.0.0.1:5500
```

### 4. Run

```bash
mvn clean install
mvn spring-boot:run
```

Server starts at: `http://localhost:8080`

---

## 🔗 API Endpoints

### Auth

| Method | Endpoint             | Description        | Auth? |
|--------|----------------------|--------------------|-------|
| POST   | `/api/auth/register` | Register new user  | ❌    |
| POST   | `/api/auth/login`    | Login, get token   | ❌    |

### Transactions

| Method | Endpoint                      | Description              | Auth? |
|--------|-------------------------------|--------------------------|-------|
| GET    | `/api/transactions`           | Get all transactions     | ✅    |
| GET    | `/api/transactions?type=INCOME` | Filter by type         | ✅    |
| GET    | `/api/transactions/range?start=&end=` | Filter by date | ✅    |
| POST   | `/api/transactions`           | Add transaction          | ✅    |
| PUT    | `/api/transactions/{id}`      | Update transaction       | ✅    |
| DELETE | `/api/transactions/{id}`      | Delete transaction       | ✅    |

### Dashboard

| Method | Endpoint                        | Description               | Auth? |
|--------|---------------------------------|---------------------------|-------|
| GET    | `/api/dashboard/summary`        | Monthly income/expenses   | ✅    |
| GET    | `/api/dashboard/monthly-stats`  | 12-month chart data       | ✅    |
| GET    | `/api/dashboard/export`         | Download CSV              | ✅    |

---

## 📦 Request / Response Examples

### Register
```json
POST /api/auth/register
{
  "username": "nick",
  "email": "nick@example.com",
  "password": "secret123",
  "fullName": "Nick Kenter"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "nick",
  "email": "nick@example.com",
  "fullName": "Nick Kenter"
}
```

### Add Transaction
```json
POST /api/transactions
Authorization: Bearer <token>
{
  "amount": 440.00,
  "type": "EXPENSE",
  "category": "Subscription",
  "description": "Netflix",
  "date": "2025-01-14"
}
```

### Dashboard Summary Response
```json
GET /api/dashboard/summary
{
  "monthlyIncome": 2645.00,
  "monthlyExpenses": 1895.00,
  "totalBalance": 750.00,
  "spendingLimit": 12645.00,
  "spentAmount": 1895.00,
  "spentPercentage": 14.98,
  "foodAndHealth": 863.00,
  "entertainment": 248.00,
  "shopping": 1835.00,
  "investment": 1835.00,
  "transport": 0,
  "utilities": 0,
  "other": 0
}
```

---

## 🔐 Authentication

All protected endpoints require:
```
Authorization: Bearer <your_jwt_token>
```

Add token to every request after login/register.

---

## 🌐 Connecting Frontend

In your `finance-dashboard.html`, replace the static data with API calls:

```javascript
const API_BASE = 'http://localhost:8080/api';
let authToken = localStorage.getItem('token');

// Load dashboard
async function loadDashboard() {
  const res = await fetch(`${API_BASE}/dashboard/summary`, {
    headers: { 'Authorization': `Bearer ${authToken}` }
  });
  const data = await res.json();
  document.querySelector('.income-amount').textContent = `$${data.monthlyIncome}`;
  document.querySelector('.expense-amount').textContent = `$${data.monthlyExpenses}`;
}

// Add transaction
async function addIncome() {
  const res = await fetch(`${API_BASE}/transactions`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${authToken}`
    },
    body: JSON.stringify({
      amount: parseFloat(document.getElementById('incomeAmount').value),
      type: 'INCOME',
      category: document.getElementById('incomeCategory').value,
      description: document.getElementById('incomeDescription').value,
      date: document.getElementById('incomeDate').value
    })
  });
  if (res.ok) { loadDashboard(); loadTransactions(); }
}
```
