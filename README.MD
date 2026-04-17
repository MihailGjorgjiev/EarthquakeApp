# Earthquake Monitoring App

A real-time earthquake dashboard with a PostgreSQL database, Spring Boot backend, and React + Vite frontend.

---

## Project Structure

```
/
├── database/       # Docker Compose PostgreSQL setup
├── backend/        # Spring Boot API
└── frontend/       # React + Vite dashboard
```

---

## 1. Database

### Prerequisites

- [Docker](https://www.docker.com/) and Docker Compose

### Configuration

Create a `.env` file in `/database`:

```
POSTGRES_USER=username
POSTGRES_PASSWORD=password
POSTGRES_DB=db_name
POSTGRES_PORT=5432
```

### Start the database

```bash
cd database
docker compose up -d
```

---

## 2. Backend

### Prerequisites

- Java 17+
- Maven (or use the included `./mvnw` wrapper — no installation needed)

### Configuration

Create a `.env` file in `/backend/src/main/resources`:

```
POSTGRES_USER=username
POSTGRES_PASSWORD=password
DB_URL=jdbc:postgresql://localhost:{postgres_port}/{postgres_db_name}
```

Use the same values set in the database `.env`. For example:

```
DB_URL=jdbc:postgresql://localhost:5432/db_name
```

### Build and run

Build the project first, then run it:

```bash
cd backend
./mvnw clean package
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

---

## 3. Frontend

### Prerequisites

- Node.js v18+
- npm v9+

### Configuration

Create a `.env` file in `/frontend`:

```
VITE_EARTHQUAKE_API_BASE_URL=http://localhost:8080/api/earthquakes
```

### Install and run

```bash
cd frontend
npm install
npm run dev
```

Open [http://localhost:5173](http://localhost:5173) in your browser.
