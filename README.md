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
1️⃣ Ensure JDK 8 is Installed
- Java Applets require older Java versions (JDK 8 recommended).

2️⃣ Compile All Files
- javac *.java

3️⃣ Run Using appletviewer
- appletviewer ParkingApplet.html

## 📦 Object-Oriented Concepts Used

1. **Encapsulation**:
Private fields in Slot, Vehicle, Staff classes with proper getters & setters.

2. **Abstraction**:
SlotManager hides internal working and exposes only necessary methods to UI.

3. **Inheritance**:
UI panels extend AWT classes like Panel and Applet.

4. **Polymorphism**:
Multiple ActionListener implementations override actionPerformed() differently.

5. **Modularity**:
Each UI screen and logic component is separated into different .java files.
