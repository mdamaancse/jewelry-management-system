\# Jewelry Management System



\## Project Description

A Jewelry Management System developed using Java Spring Boot, Spring Data JPA, Hibernate and MySQL. It provides REST APIs to manage jewelry products and user accounts.



\## Technologies Used

\- Java 17

\- Spring Boot

\- Spring Data JPA

\- Hibernate

\- MySQL

\- Maven

\- Postman



\## Database Setup

1\. Install and start MySQL Server.

2\. Create the database using `database/schema.sql`.

3\. Configure your MySQL username and password in `application.properties`.

4\. Do not upload your actual database password to GitHub.



\## Run the Application



Open PowerShell in the project folder and execute:



&#x20;   .\\mvnw.cmd spring-boot:run



Application URL:



&#x20;   http://localhost:8080



\## Product REST APIs



| Method | Endpoint | Description |

|---|---|---|

| GET | /api/products | Get all products |

| GET | /api/products/{id} | Get product by ID |

| GET | /api/products/category/{category} | Filter by category |

| GET | /api/products/search?keyword=value | Search products |

| POST | /api/products | Add product |

| PUT | /api/products/{id} | Update product |

| DELETE | /api/products/{id} | Delete product |



\## User REST APIs



| Method | Endpoint | Description |

|---|---|---|

| POST | /api/users/register | Register user |

| POST | /api/users/login | Login user |

| GET | /api/users/profile | Get logged-in user profile |



User login uses session-based authentication.



\## API Testing

All Product and User REST APIs have been tested using Postman.



\## SQL Script

Database schema is available in:



&#x20;   database/schema.sql



\## Project Structure

\- config

\- controller

\- entity

\- repository

\- database

\- resources



\## Author

Jewelry Management System Project

