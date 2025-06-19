# 📦 Tracking Number Generator API

A scalable and efficient RESTful API built with **Spring Boot 3.1.9** to generate **unique parcel tracking numbers**. Designed to handle **high concurrency** and suitable for deployment on cloud platforms like AWS.

---

## ✅ Features

- Unique tracking number generation (matches regex: `^[A-Z0-9]{1,16}$`)
- Fast, stateless, horizontally scalable design
- Accepts order-related metadata as input
- RFC 3339 timestamps
- Dockerized and deployed to AWS Elastic Beanstalk

---

## 🛠 Tech Stack

- Java 17
- Spring Boot 3.1.9
- Maven 3.9.10
- Docker
- AWS Elastic Beanstalk

---

## 🚀 Live API URL

```http
GET https://tracking-number-api-env.eba-xpnm9er3.eu-north-1.elasticbeanstalk.com/next-tracking-number
```

### 🔍 Sample Request:

```
GET /next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2025-06-17T15:30:00%2B08:00&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox%20Logistics&customer_slug=redbox-logistics
```

### ✅ Sample Response:

```json
{
  "tracking_number": "F3A8C1B7E90D4F2C",
  "created_at": "2025-06-17T15:30:00+08:00"
}
```

---

## 📦 How to Build Locally

> Prerequisites: Java 17, Maven 3.9+, Docker (optional for containerization)

1. Clone or unzip the project
2. Open terminal in the project root (where `pom.xml` is)
3. Run:

```bash
mvn clean package
```

4. Jar will be available in `target/`

---

## 🐳 Docker (Optional)

To build and run using Docker:

1. Build Docker image:

```bash
docker build -t tracking-number-api .
```

2. Run the container:

```bash
docker run -p 8080:8080 tracking-number-api
```

Then access at [http://localhost:8080/next-tracking-number](http://localhost:8080/next-tracking-number)

---

## 📁 Project Structure

```
.
├── src/
│   └── main/
│       ├── java/
│       │   └── com.example.tracking/
│       └── resources/
├── pom.xml
├── Dockerfile
└── README.md
```

## Test Coverage

To ensure the reliability and correctness of the tracking number generator, I’ve included both unit and integration tests in the project.

### Unit Tests

The unit tests focus on the core logic responsible for generating tracking numbers. These tests verify that:
- Each generated tracking number matches the required format (`^[A-Z0-9]{1,16}$`).
- The service does not produce duplicates even when generating many tracking numbers in sequence.
- The logic handles a variety of inputs without failure.

### Integration Test

An integration test is provided for the `/next-tracking-number` endpoint. It checks that:
- The endpoint accepts and processes all required query parameters.
- The response contains both a valid tracking number and timestamp.
- The controller behaves consistently and responds correctly under normal conditions.

### Running the Tests

To run the tests, simply use Maven:

```bash
./mvnw test


---

## 📝 Author

**Ranjith Bogavilli**  

---
