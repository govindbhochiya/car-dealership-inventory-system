# 🚗 Car Dealership Inventory System

## 📖 About the Project

The **Car Dealership Inventory System** is a full-stack web application developed as a learning project to understand modern web development using **Spring Boot**, **React**, and **MySQL**. The main objective of this project is to simplify vehicle inventory management by providing a secure and easy-to-use platform for both administrators and users.

The application allows administrators to manage the complete vehicle inventory, including adding new vehicles, updating vehicle details, deleting vehicles, restocking inventory, and monitoring available stock. Regular users can securely register, log in, browse available vehicles, search vehicles using different filters, and purchase vehicles from the inventory.

To ensure application security, the project uses **Spring Security** with **JWT (JSON Web Token)** authentication. Protected APIs can only be accessed by authenticated users, and role-based authorization ensures that only administrators can perform inventory management operations.

The backend is developed using **Spring Boot** and follows the REST API architecture. Database operations are handled using **Spring Data JPA (Hibernate)** with **MySQL** as the relational database. The frontend is built using **React**, providing a responsive and user-friendly interface that communicates with the backend through RESTful APIs.

To improve code quality and reliability, unit testing is implemented using **JUnit 5** and **Mockito**. The service layer was developed by following the **Test-Driven Development (TDD)** approach, where tests were written before implementing the business logic to verify both successful and failure scenarios.

This project helped me gain practical experience in backend development, frontend integration, REST API design, authentication and authorization, database management, unit testing, Git version control, and full-stack application development.

---

# ✨ Features

## 🔐 Authentication & Security

- User registration with secure password encryption.
- User login using JWT Authentication.
- Role-based authorization using Spring Security.
- Stateless authentication using JWT tokens.
- Protected REST APIs accessible only to authenticated users.
- Admin-only access for inventory management operations.

---

## 🚘 Vehicle Inventory Management

- Add new vehicles to the inventory.
- Update existing vehicle information.
- Delete vehicles from the inventory.
- View all available vehicles.
- Search vehicles using multiple filters such as:
  - Make
  - Model
  - Category
  - Minimum Price
  - Maximum Price
- Restock vehicle inventory by increasing available quantity.
- Purchase vehicles with automatic stock reduction.
- Prevent purchasing vehicles when stock is unavailable.

---

## ✅ Validation

- Validate required input fields.
- Prevent invalid vehicle data from being stored.
- Reject zero or negative restock quantities.
- Validate purchase quantity before processing.
- Handle invalid vehicle IDs gracefully.
- Return meaningful error messages for invalid requests.

---

## 🧪 Testing

- Unit testing using JUnit 5.
- Mocking dependencies using Mockito.
- Positive and negative test cases.
- Service layer testing.
- Test-Driven Development (Red → Green → Refactor).
- Validation testing for different business scenarios.

---

## 💻 Frontend

- Responsive React user interface.
- Login and Registration pages.
- Dashboard displaying vehicle inventory.
- Search functionality with multiple filters.
- Add Vehicle page (Admin).
- Edit Vehicle page (Admin).
- Delete Vehicle functionality (Admin).
- Purchase Vehicle functionality.
- Restock Vehicle functionality (Admin).
- Logout functionality.
- Display success and error messages after operations.

---

## 🛠 Technologies Used

### Backend

- Java
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA (Hibernate)
- Maven

### Frontend

- React
- JavaScript
- HTML
- CSS

### Database

- MySQL

### Testing

- JUnit 5
- Mockito
- Postman

### Version Control

- Git
- GitHub
# ⚙️ Installation & Setup

## 1. Clone the Repository

```bash
git clone https://github.com/your-username/car-dealership-inventory-system.git
```

---

## 2. Open the Project

### Backend

Open the backend project in **Eclipse IDE**.

### Frontend

Open the frontend folder in **Visual Studio Code**.

---

## 3. Configure the Database

Create a MySQL database:

```sql
CREATE DATABASE car_dealership;
```

Open:

```
backend/src/main/resources/application.properties
```

Update the following properties:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/car_dealership
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
```

---

## 4. Start the Backend

### Option 1 (Recommended)

Run the Spring Boot application directly from **Eclipse** by right-clicking the main application class and selecting:

```
Run As → Spring Boot App
```

### Option 2 (Command Line)

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Backend runs at:

```
http://localhost:8080
```

---

## 5. Start the Frontend

Open a terminal inside the frontend folder.

Install dependencies:

```bash
cd frontend
npm install
```

Start the React application:

If using Vite:

```bash
npm run dev
```

If using Create React App:

```bash
npm start
```

Frontend runs at:

```
http://localhost:5173
```

or

```
http://localhost:3000
```

---

## 6. Login

Register a new account or log in with an existing account.

After login, the application stores the JWT token and automatically uses it for protected API requests.

---

## 7. Run Unit Tests (Optional)

```bash
cd backend
mvn test

#📸 Application Screenshots

The following screenshots showcase the main features and user interface of the Car Dealership Inventory System.

## Login Page

![Login Page](screenshots/login.png)

---

## Register Page

![Register Page](screenshots/RegisterPage.png)

---

## Admin Dashboard

![Admin Dashboard](screenshots/AdminDashboard.png)

---

## User Dashboard

![User Dashboard](screenshots/UserDashboard.png)

---

## Add Vehicle

![Add Vehicle](screenshots/AddVehicle.png)

---

## Edit Vehicle

![Edit Vehicle](screenshots/Editpage.png)
