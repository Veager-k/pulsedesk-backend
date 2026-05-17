## Pulsedesk Backend
A simple Spring Boot backend that analyzes user comments using Gemini and decides whether a support ticket should be created.

## Features
- AI comment analysis (Gemini API)
- Automatic ticket classification and priority
- REST API with Swagger UI
- In‑memory H2 database
- Deployed on Railway

## Live API (Swagger)
https://pulsedesk-backend-production.up.railway.app/swagger-ui.html

## How It Works
When a comment is submitted, the backend sends it to Gemini.
Gemini returns JSON describing whether a ticket should be created, plus title, category, priority, and summary.

## Available Endpoints
- POST /comments
- GET /comments
- GET /comments/{id}
- GET /tickets
- GET /tickets/{id}

## Run Locally
Add your Gemini API key:
gemini.api.key=YOUR_KEY

Start the app:
```
./mvnw spring-boot:run
```

Open Swagger:
http://localhost:8080/swagger-ui.html

## Example Request
POST /comments
```
{
  "content": "The billing page crashes when I update my card."
}
```

## Optional: Run with Docker
docker build -t pulsedesk .
docker run -p 8080:8080 -e gemini.api.key=YOUR_KEY pulsedesk
