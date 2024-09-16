# Employee Management System

## Product Vision Statement

The **Employee Management System (EMS)** streamlines task management and administrative processes for employees within an organization. The system provides tailored functionality for three user roles: Employees, Team Managers, and HR personnel. This platform simplifies task assignment, tracking, and completion through a user-friendly **Java-based GUI**. Designed to improve productivity and management, EMS ensures secure and efficient handling of employee data.

## Core Team

- **Ryan Rim** - Developer  
  
- **Taiwo Oso** - Developer  

- **Ryan Park** - Developer  
  
## Core Features

- **User Authentication**: Secure login and signup portals for employees and HR personnel.
- **Employee Task Management**:
  - Employees can view, accept, reject, and complete tasks.
  - Employees can receive feedback from their managers.
- **Manager Functions**:
  - Managers can assign, monitor, and edit tasks.
  - Managers track the progress of employees under their management.
- **HR Management**:
  - HR personnel can add, remove, and edit employee and manager records.
  - HR can promote or demote employees to managerial roles and off-board users.
  - HR can create, assign, and delete tasks for employees.

## Technologies

- **Java**: Core programming language.
- **SQLite**: Database for storing user and task data. (Ensure SQLite is added to the `lib` folder and included in Referenced Libraries.)
- **GUI**: Designed with a focus on **Windows**, though compatibility with **MacOS** may vary.

## Installation

To set up the project locally:

1. Clone the repository:
   ```bash
   git clone https://github.com/TaiwoOso/Employee-Management-System

## Requirements:
Please add the SQLite jar file to the lib folder and include it in Referenced Libraries.

## User Guide:

### Tasks:
1. Each task is assigned to one employee and one manager.

### HR:
2. HR personnel have access to view both employees and managers.
3. HR personnel can off-board both employees and managers.
4. Additional details for tasks such as "Title" or "Description" can be accessed by clicking on them for a popup window.

## Extra Notes:
- The GUI was primarily developed for Windows users, so there might be slight visual differences on Mac systems and vice versa.
