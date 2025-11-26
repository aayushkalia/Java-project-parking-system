# Parking Management System (Java Applet)

A modular and interactive Parking Management System built using **Java Applets**, **AWT**, and **Object-Oriented Programming (OOP)**.  
This project simulates real-world parking operations such as tracking slots, vehicle entry/exit, basic staff handling, and a dashboard view — all inside a single applet with simple navigation.

---

## 🚀 Features

- **Dashboard Screen**
  - Shows total slots, occupied slots, free slots, and total revenue.

- **Slot Management**
  - 30 slots displayed in a 6×5 grid.
  - Color-coded:
    - 🟩 Green → Free  
    - 🟥 Red → Occupied
  - Click a slot to occupy/free it with confirmation dialogs.

- **Vehicle Entry**
  - Enter vehicle number and type (2W/4W).
  - Auto‑assigns the correct free slot.
  - Marks slot as occupied and stores vehicle info.

- **Vehicle Exit**
  - Frees the slot associated with the vehicle.
  - Calculates flat charges:
    - ₹10 for 2‑wheelers  
    - ₹20 for 4‑wheelers
  - Updates total revenue.

- **Staff Management**
  - Add, remove, and view staff.
  - Data stored in-memory (ArrayList).

- **Navigation System**
  - Switch between all screens inside one applet.

---

## 📁 Project Structure

/src
├── Slot.java
├── Vehicle.java
├── SlotManager.java
├── DashboardPanel.java
├── SlotPanel.java
├── EntryPanel.java
├── ExitPanel.java
├── StaffPanel.java
├── ParkingApplet.java
└── ParkingApplet.html


---

## ▶️ How to Run

### **1. Make sure you use JDK 8**
Applets only run on older Java versions (Java 8 recommended).

### **2. Compile all files**
```bash
javac *.java

3. Run using appletviewer
appletviewer ParkingApplet.html

📦 Object-Oriented Concepts Used
Encapsulation:
Private fields for Slot, Vehicle, Staff with getters/setters.

Abstraction:
SlotManager handles internal logic — UI interacts via methods.

Inheritance:
Panels extend Java AWT Panel/Applet classes.

Polymorphism:
Different ActionListeners override actionPerformed().

Modularity:
Each UI screen is a separate Java file.
