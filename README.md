# E-commerce REST API

A robust e-commerce REST API built with Spring Boot, providing essential features for online shopping platforms.

## Features

### User Management
- User registration and authentication
- User profile management

### Product Management
- Product catalog with categories
- Product details and descriptions
- Category-based product organization

### Shopping Cart
- Cart creation and management
- Add/remove items from cart
- Update item quantities
- Cart total calculation

### Security
- JWT-based authentication
- Password encryption
- Secure endpoints

## Tech Stack

- **Backend Framework**: Spring Boot 3.4.5
- **Language**: Java 24
- **Database**: MySQL 8.0+
- **ORM**: Hibernate/JPA
- **Build Tool**: Maven
- **Database Migration**: Flyway
- **Testing**: JUnit
- **Security**: Spring Security, JWT

## Prerequisites

- Java 24 or higher
- MySQL 8.0 or higher
- Maven
- Postman (for API testing)

## Project Setup

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd Ecommerce-Api
   ```

2. Set up your configuration:
   - Create a database named `store`
   - Copy the template files to create your local configuration:
      • Copy src/main/resources/application.yaml.template to src/main/resources/application.yaml
      • Copy maven.properties.template to maven.properties
   - Edit these files to include your actual database credentials and any other environment-specific settings.
        username: your_username
        password: your_password

3. Build the project:
   Run the following command to build the project and download dependencies:
   ```bash
   mvn clean install
   ```
   
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
   The application will start on port 8081

## API Testing with Postman

### 1. Environment Setup

1. Create a new Postman environment
2. Add these variables:
   ```
   baseUrl: http://localhost:8081
   token: (will be set after login)
   ```

### 2. Authentication Flow

#### Register User
```http
POST {{baseUrl}}/auth/register
Content-Type: application/json

{
    "name": "Test User",
    "email": "test@example.com",
    "password": "password123"
}
```
- Save the user ID from response

#### Login
```http
POST {{baseUrl}}/auth/login
Content-Type: application/json

{
    "email": "test@example.com",
    "password": "password123"
}
```

### 3. Product Testing Flow

#### Get All Products
```http
GET {{baseUrl}}/products
Authorization: Bearer {{token}}
```

#### Get Products by Category
```http
GET {{baseUrl}}/products?categoryId=1
Authorization: Bearer {{token}}
```

#### Get Product Details
```http
GET {{baseUrl}}/products/1
Authorization: Bearer {{token}}
```

### 4. Cart Testing Flow

#### Create Cart
```http
POST {{baseUrl}}/carts
```
- Save the cart ID from response

#### Add Item to Cart
```http
POST {{baseUrl}}/carts/{{cartId}}/items
Content-Type: application/json
{
    "productId": 1,
    "quantity": 2
}
```

#### Get Cart
```http
GET {{baseUrl}}/carts/{{cartId}}
Authorization: Bearer {{token}}
```

#### Update Cart Item
```http
PUT {{baseUrl}}/carts/{{cartId}}/items/1
Authorization: Bearer {{token}}
Content-Type: application/json

{
    "quantity": 3
}
```

#### Clear Cart
```http
DELETE {{baseUrl}}/carts/{{cartId}}/items
Authorization: Bearer {{token}}
```

### 5. User Profile Testing Flow

#### Get User Profile
```http
GET {{baseUrl}}/users/{{userId}}
Authorization: Bearer {{token}}
```

#### Update User Profile
```http
PUT {{baseUrl}}/users/{{userId}}
Authorization: Bearer {{token}}
Content-Type: application/json

{
    "name": "Updated Name",
    "email": "updated@example.com"
}
```

#### Add Address
```http
POST {{baseUrl}}/users/{{userId}}/addresses
Authorization: Bearer {{token}}
Content-Type: application/json

{
    "street": "123 Main St",
    "city": "Anytown",
    "state": "CA",
    "zip": "12345"
}
```


## Error Handling

The API uses standard HTTP status codes:
- 200: Success
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 500: Internal Server Error

## Database Schema

### Main Tables
- users
- products
- categories
- carts
- cart_items
- addresses
- profiles
- wishlist

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License. 
