# Spring Boot Data Migration PoC (PostgreSQL to MongoDB)

This project is a Proof of Concept (PoC) demonstrating a robust data migration strategy from a PostgreSQL relational database to a MongoDB NoSQL database using Spring Boot. It leverages JobRunr for reliable, background job processing and includes automatic test data generation with DataFaker.

## Features

-   **Batch Processing**: Efficiently migrates data in batches to manage memory and performance.
-   **Transactional Data Streaming**: Streams data from PostgreSQL to minimize the application's memory footprint.
-   **Background Job Processing**: Utilizes JobRunr for scheduling, executing, and monitoring the migration job.
-   **Automatic Data Generation**: Populates the source PostgreSQL database with 1,300,000 realistic records on startup for testing purposes.
-   **Data Cleanup**: Automatically clears the MongoDB collection before each run to ensure a clean state.
-   **Dockerized Environment**: Uses Docker Compose to seamlessly set up and run PostgreSQL and MongoDB services.

## Technologies Used

-   **Java**: 24
-   **Spring Boot**: 3.5.4
-   **Databases**:
    -   PostgreSQL (Source)
    -   MongoDB (Target)
-   **Frameworks & Libraries**:
    -   Spring Data JPA
    -   Spring Data MongoDB
    -   JobRunr (for background jobs)
    -   Lombok
    -   DataFaker (for test data)
-   **Build & Environment**:
    -   Maven
    -   Docker & Docker Compose

## Prerequisites

Before you begin, ensure you have the following installed:
-   JDK 24 or newer
-   Maven 3.8+
-   Docker and Docker Compose

## How to Run the Application

The project is configured to use Spring Boot's Docker Compose support, which handles the setup of all required services.

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/danielbartl/dmpoc.git
    cd dmpoc
    ```

2.  **Run the application:**
    You can run the application directly from your IDE or by using the Maven wrapper in your terminal:
    ```bash
    ./mvnw spring-boot:run
    ```
    Spring Boot will automatically detect the `compose.yaml` file and start the PostgreSQL and MongoDB containers before launching the application.

## How It Works

### 1. Application Startup
-   When the application starts, the `DataInitializer` component calls the `SetupSource` service.
-   The `SetupSource` service first connects to MongoDB and clears any data from previous runs.
-   It then checks if the PostgreSQL database contains any customer data. If not, it generates and persists **1,300,000** fake customer records in batches.

### 2. Triggering the Data Migration
-   The migration is not automatic. It must be triggered manually by sending an HTTP request.
-   To start the migration, send a `POST` request to the following endpoint:
    ```
    POST /api/migrations/customers
    ```
-   You can use a tool like `curl`:
    ```bash
    curl -X POST http://localhost:8080/api/migrate/customers
    ```
-   This request enqueues the `migrateCustomers` job in JobRunr, which will execute it in the background.

### 3. Monitoring with the JobRunr Dashboard
-   Once the migration job is enqueued, you can monitor its progress, view logs, and see its final status on the JobRunr dashboard.
-   Access the dashboard here: **[http://localhost:8000](http://localhost:8000)**
