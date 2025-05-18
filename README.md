# AI-Driven Fitness Microservices Application

This project is a microservices-based fitness recommendation application built using Spring Boot. It integrates AI (Google Gemini API) for generating personalized activity recommendations, and uses RabbitMQ for messaging between services. Eureka handles service discovery for microservice communication.

## 🏗️ Architecture

The application is composed of the following microservices:

### ✅ Services

- **eureka**  
  Service discovery server using Spring Cloud Netflix Eureka.

- **gateway** *(optional in future)*  
  API gateway for routing requests.

- **activityservice**  
  Publishes activity events to RabbitMQ based on user actions.

- **aiservice**  
  Consumes messages from RabbitMQ and calls Gemini API to generate AI-powered fitness recommendations.

- **userservice**  
  Handles user-related data and management.

### ⚙️ Technologies Used

- Java 17 / Spring Boot
- Spring Cloud (Eureka, Gateway)
- RabbitMQ (message broker)
- Google Gemini API (LLM-based AI integration)
- Postman (for testing APIs)
- Docker (coming soon)
- WebClient (non-blocking HTTP client)

---

## 🔁 Flow Overview

1. User logs activity via `activityservice`.
2. Activity is published to RabbitMQ.
3. `aiservice` consumes the activity and sends a structured prompt to Gemini API.
4. AI returns structured recommendations (e.g., nutrition, recovery tips).
5. Response can be returned to users via frontend or logs.

---

## 🤖 Gemini AI Prompt (Example)

Gemini is used to generate AI-powered JSON responses with:
- Recovery suggestions
- Smart snack pairing
- Calorie conversion (healthy, cheat, recovery)
- Fun facts
- Scientific references and energy equivalence

---

## ✅ Current Status

- [x] Backend microservices created
- [x] RabbitMQ and Eureka integrated
- [x] Gemini API integration working
- [x] Postman used to test endpoints
- [x] JSON prompt working as expected
- [ ] Error handling under development
- [ ] Dockerization of services in progress

---

## 📌 TODO

- [ ] Resolve AI service connection refusal (localhost:80) issue
- [ ] Add global exception handling and fallback strategies
- [ ] Create Dockerfiles and docker-compose for all services
- [ ] Write integration tests (RabbitMQ and AI response validation)
- [ ] Improve logging and add monitoring (e.g., Actuator, Zipkin)
- [ ] Create Swagger documentation for each service
- [ ] Build frontend for user interaction

---

## 🧪 Testing

Use Postman to:
- Send activity events
- Test RabbitMQ consumption
- Validate Gemini JSON response
- Check logs for AI response validation

---

## 💡 Contributing

Jay Shah  
📍 Currently resolving Gemini API errors  
📦 Next up: Docker + Full Integration

---
