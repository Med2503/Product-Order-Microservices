# 🛒 Order Validation System - Spring Boot Microservices

A cloud-native **microservices application** built with **Spring Boot**, using **Apache Kafka** for event-driven communication, **Keycloak** for security, and **Prometheus** for monitoring.

> The system validates customer orders by checking product availability before final confirmation.

---

## 📦 Core Microservices

| Service        | Description                                      |
|----------------|--------------------------------------------------|
| **order-ms**   | Creates and validates orders                    |
| **product-ms** | Manages products and stock availability         |
| **gateway**    | Routes client requests and handles security     |
| **config-server** | Centralized configuration management         |
| **discovery-server** | Service registry with Eureka              |

---

## 🔁 Workflow (Event-Driven)

1. A client places an order via **order-ms**.
2. `order-ms` sends an event to **Kafka**.
3. **product-ms** listens and checks stock availability.
4. A result event is sent back (success/failure).
5. **order-ms** updates the order status accordingly.

---

## 🔐 Security & Access

- All traffic goes through **Spring Cloud Gateway**.
- Authentication and role-based access via **Keycloak (OAuth2)**.
- Protected microservice routes behind Gateway.

---

## 📡 Monitoring & Observability

- Each service exposes metrics via **Spring Boot Actuator**.
- Metrics collected by **Prometheus**.
- Dashboards can be built with **Grafana** (optional).

---

## 🧰 Tech Stack

- **Java 17**, **Spring Boot 3**, **Spring Cloud**
- **Apache Kafka** for messaging
- **Spring Security + Keycloak** for authentication
- **Spring Cloud Gateway**, **Eureka**, **Config Server**
- **Prometheus + Micrometer** for monitoring
- **Docker Compose** for infrastructure setup

---

## 🚀 How to Run

### 1. Prerequisites

- Docker & Docker Compose
- Java 17+, Maven 3+

### 2. Start Dependencies

```bash
docker-compose up -d
