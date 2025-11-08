# my-dental-app
my-dental-app is a modular, microservices-based dental clinic management system that simplifies daily clinical workflows.
It enables clinics to manage appointments, patient records, doctor schedules, treatments, and billing in a secure and efficient way.
Each microservice (Auth, Patient, Appointment, Billing, Notification, etc.) communicates asynchronously via Kafka, with Redis caching frequently accessed data to improve response times.

Built for modern cloud environments, the application uses Spring Boot, Spring Security (JWT), Spring JPA, MongoDB, and Docker Compose to orchestrate services seamlessly.

The project is ideal for learning and demonstrating enterprise-grade backend design with:

✅ Modular Microservices Architecture

🔐 Centralized JWT Authentication

⚙️ Event-driven communication (Kafka)

⚡ Caching layer using Redis

🗄️ Hybrid Databases (SQL + NoSQL)

🚀 Containerized Deployment with Docker & WSL2

🔄 Version control via GitHub
