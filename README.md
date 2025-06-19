# 📦 Tracking Number Generator API

This is a scalable Spring Boot application that generates unique tracking numbers via a RESTful API.

---

## ✅ Features

- Generates unique tracking numbers based on input parameters
- Follows format: `^[A-Z0-9]{1,16}`
- Stateless endpoint: `GET /next-tracking-number`
- Includes unit, controller, and integration tests
- Deployed live on AWS Elastic Beanstalk

---

## 🚀 Live Demo

You can test the API using the following link (opens full request in browser):

👉 [Live Tracking API](https://tracking-number-api-env.eba-xpnm9er3.eu-north-1.elasticbeanstalk.com/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.23&created_at=2024-06-19T10:30:00%2B08:00&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox%20Logistics&customer_slug=redbox-logistics)

Expected response:

```json
{
  "tracking_number": "REDBOXLOGISTICSMYID...",
  "created_at": "2024-06-19T10:30:00+08:00"
}
```

---

## 🛠️ How to Run Locally

### Prerequisites

- Java 17+
- Maven 3.8+

### Build & Run

```bash
# Clone the repo
git clone https://github.com/Ranjith004-R/tracking-number-generator.git
cd tracking-number-generator

# Package the app
mvn clean package -DskipTests

# Run the app
java -jar target/tracking-api-1.0.0.jar
```

Then open your browser:

```
http://localhost:8080/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.23&created_at=2024-06-19T10:30:00%2B08:00&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox%20Logistics&customer_slug=redbox-logistics
```

---

## ✅ API Reference

### GET `/next-tracking-number`

| Parameter              | Type    | Required | Description                      |
|------------------------|---------|----------|----------------------------------|
| origin_country_id      | string  | ✅       | Country code like `MY`           |
| destination_country_id | string  | ✅       | Country code like `ID`           |
| weight                 | double  | ✅       | Package weight                   |
| created_at             | OffsetDateTime | ✅ | ISO 8601 date                    |
| customer_id            | UUID    | ✅       | Customer UUID                    |
| customer_name          | string  | ✅       | Customer's name                  |
| customer_slug          | string  | ✅       | Customer slug                    |

---

## 🧪 Testing

This project includes:

- ✅ **Unit Tests** (`TrackingNumberServiceTest`)
- ✅ **Controller Tests** (`TrackingNumberControllerTest`)
- ✅ **Integration Tests** (`TrackingNumberIntegrationTest`)

Run all tests:

```bash
mvn test
```

---

## ☁️ Deployment

This app is deployed using AWS Elastic Beanstalk.

To redeploy:

1. Build new JAR: `mvn clean package -DskipTests`
2. Go to AWS Console > Elastic Beanstalk
3. Upload & deploy new `.jar` to your environment

---

## 📅 Last Updated

2025-06-19 13:18:29
