# Real-Time Leaderboard

A Spring Boot application that provides a real-time leaderboard dashboard with WebSocket support, Redis data persistence, and role-based authentication.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [License](#license)

## Installation

### Prerequisites

- Java 24 or higher
- Maven 3.6 or higher
- Docker and Docker Compose

### Setup

1. Clone the repository:

   ```bash
   git clone <repository-url>
   cd demo.realTimeLeaderboard
   ```

2. Start Redis using Docker Compose:

   ```bash
   docker-compose up -d
   ```

3. Build and run the application:

   ```bash
   ./mvnw spring-boot:run
   ```

4. Access the application at `http://localhost:8080`

## Usage

The Real-Time Leaderboard application provides a web-based dashboard for managing and viewing player scores in real-time.

### Features

- **Real-time Updates**: Uses WebSocket technology to broadcast leaderboard changes instantly to all connected users
- **Role-based Access Control**:
  - Admin users can add and update player scores
  - Regular users can only view the leaderboard in read-only mode
- **Data Persistence**: Scores are stored in Redis for persistence across application restarts
- **Interactive Dashboard**: Modern, responsive web interface with real-time statistics
- **Authentication System**: Secure login with predefined demo accounts
- **Live Statistics**: Displays total players, highest score, and average score
- **Auto-reconnection**: Automatically reconnects to the server if connection is lost

### Demo Accounts

- **Admin**: `admin` / `admin` (can update scores)
- **User**: `user` / `user` (read-only access)

### How to Use

1. Navigate to `http://localhost:8080` in your web browser
2. Log in using one of the demo accounts
3. Admin users will see a form to add new scores
4. All users can view the live leaderboard and statistics
5. Changes are broadcast to all connected users in real-time

## License

This project is licensed under the MIT License.
