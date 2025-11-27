# **🏡 Cosy \- Backend**

The core orchestration engine for **Cosy** (Cost Optimised Server Yard).  
This Spring Boot application acts as the "Town Hall" of the Cosy ecosystem. It manages game server containers, handles
file I/O operations directly on the host, and coordinates with specialized data stores for metrics, logs, and backups.

## **🛠️ Tech Stack**

* **Language:** Java 21
* **Framework:** Spring Boot 3.x
* **Build Tool:** Maven
* **Database:** PostgreSQL (Data), InfluxDB (Metrics), Grafana Loki (Logs)
* **Runtime Support:** Docker (Native) & Kubernetes

## **📋 Prerequisites**

* **Java 21 SDK** (Required)
* **Docker Desktop / Engine** (Required for the runtime & dev infrastructure)
* **Maven** (Optional, using the wrapper is recommended)

## **🚀 Getting Started**

### **1\. Start Infrastructure**

Before running the backend, you need the "Data Silos" (Postgres, Influx, etc.) running. We provide a docker-compose file
for development.

```shell
cd infrastructure  
docker compose up \-d
```

### **2\. Configuration**

Copy the example environment file and adjust if necessary (usually defaults work for dev).

```shell
cp .env.example .env
```

Ensure your application.properties or application.yml is configured to connect to the services started in step 1\.

### **3\. Build & Run**

You can start the application using the Maven wrapper:

```shell
./mvnw spring-boot:run
```

The API will be available at http://localhost:8080/api.

## **🧹 Code Style & Linting**

We maintain a strict code style using **Spotless** and **Google Java Format (AOSP style)**.

### **Check Code Style**

To verify if your code meets the standards without modifying it:

```shell
./mvnw spotless:check
```

### **Fix Code Style**

To automatically format your code:

```shell
./mvnw spotless:apply
```

**Pro Tip:** It is highly recommended to run `spotless:apply` before pushing any code to avoid CI failures.

## **🏗️ Architecture Overview**

The backend uses a **Strategy Pattern** to handle different environments without changing application logic.

* **RuntimeService Interface:** The main contract for server management.
    * **DockerRuntimeStrategy:** Uses the local Docker Socket (/var/run/docker.sock). Used for single-node setups.
    * **KubernetesRuntimeStrategy:** Uses the K8s API. Used for cluster deployments.

### **File I/O**

Unlike traditional cloud apps, Cosy uses **Direct I/O** for file management.

* **Docker Mode:** Uses Java NIO to read Bind Mounts directly on the host.
* **K8s Mode:** Uses ephemeral helper pods to access PVCs if the server is stopped.

## **🧪 Testing**

Run unit and integration tests:

```
./mvnw test
```

## **📄 API Documentation**

Once the application is running, you can explore the REST API via Swagger UI:

* http://localhost:8080/api/swagger-ui/index.html