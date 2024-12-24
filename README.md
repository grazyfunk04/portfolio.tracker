Portfolio Tracker - Backend
Overview
The Portfolio Tracker backend is a Java Spring Boot application that allows users to track their portfolio investments. It integrates with a MongoDB database to store user and portfolio data. The application exposes RESTful APIs to interact with the frontend for adding, viewing, and managing portfolios.

Tech Stack
Backend: Java Spring Boot
Database: MongoDB
Build Tool: Maven
Deployment: Docker, Render (for production)
Features
User Authentication: Secure login and registration system.
Portfolio Management: Add, edit, and delete portfolio entries.
Real-Time Data: Fetch real-time data for portfolio management.
Dockerized: Easy deployment with Docker.
RESTful APIs: All operations are exposed through REST APIs.
Prerequisites
Before you run this project locally, make sure you have the following installed:

Java 8 or higher
Maven
MongoDB (or use a MongoDB cloud service like MongoDB Atlas)
Docker (Optional for containerized deployment)
Getting Started
Clone the Repository
Clone the repository to your local machine:

git clone https://github.com/grazyfunk04/portfolio.tracker.git
cd portfolio.tracker
Backend Setup
Install Dependencies: Run the following command to install the required dependencies:

mvn install
Run the Application: Once the dependencies are installed, start the Spring Boot application with:

mvn spring-boot:run
The backend will be available at http://localhost:8080.

Docker Setup (Optional)
If you want to run the application in a Docker container, follow these steps:

Build the Docker Image:

docker build -t portfolio-backend .
Run the Docker Container:

docker run -p 8080:8080 portfolio-backend
This will start the backend on http://localhost:8080 inside the Docker container.

MongoDB Configuration
The application expects a MongoDB database to be running locally or remotely (e.g., MongoDB Atlas). Make sure to update the application.properties or application.yml file with the correct MongoDB connection string.
Example:

spring.data.mongodb.uri=mongodb://localhost:27017/portfolioDB

API Documentation
POST /api/auth/register
Registers a new user.

Request Body:
{
  "username": "user1",
  "password": "password123"
}
Response:

201 Created on success
400 Bad Request on failure

POST /api/auth/login
Logs in a user.

Request Body:
{
  "username": "user1",
  "password": "password123"
}
Response:

200 OK with token on success
401 Unauthorized on failure

GET /api/portfolio
Fetches all portfolios for the logged-in user.

Response:

200 OK with portfolio data
401 Unauthorized if not authenticated

Assumptions and Limitations
The application requires MongoDB to be up and running.
A running instance of the backend must be available for the frontend to interact with.
User authentication is required to manage portfolio data.
This application is built for educational purposes and may not be production-ready.
Deployment
The application is deployed on Render using Docker. The live backend can be accessed here.

License
This project is licensed under the MIT License - see the LICENSE file for details.
