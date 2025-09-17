# 🏨 Hotel Reservation System (Java + JDBC + MySQL)

A simple **Hotel Management System** built using **Java JDBC** and **MySQL**.  
This project demonstrates basic **CRUD operations** (Create, Read, Update, Delete) with a console-based menu system.

---

## ✨ Features
- ✅ Reserve a room  
- ✅ View reservations  
- ✅ Get room number by reservation ID and guest name  
- ✅ Update reservation details  
- ✅ Delete reservation  
- ✅ Exit with a nice animation  

---

## 🛠 Tech Stack
- **Java** (JDK 8+)
- **JDBC** (Java Database Connectivity)
- **MySQL** (for database)

---

## ⚙️ Setup Instructions

### 1. Clone the repository

git clone [https://github.com//HotelReservationSystem.git](https://github.com/HritikKha/HotelReservationSystem..git)
cd HotelReservationSystem


### 2. Create the MySQL Database

Login to MySQL and create the database:
CREATE DATABASE hotel_db;
USE hotel_db;

### 3. Create the reservation table
CREATE TABLE reservation (
reservation_id INT AUTO_INCREMENT PRIMARY KEY,
guest_Name VARCHAR(255) NOT NULL,
room_number INT NOT NULL,
contact_number INT NOT NULL,
reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP);

### 4. Update database credentials
In HotelReservationSystem.java, update your DB details:
private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
private static final String username = "root";
private static final String password = "your_password";

### 5.  Compile and Run

## 📖 Example Menu
HOTEL MANAGEMENT SYSTEM
1. Reserve a room
2. View Reservations
3. Get Room Number
4. Update Reservations
5. Delete Reservations
0. Exit
Choose an option:

## 🙌 Contribution

Feel free to fork this repo, improve features, and make pull requests.
