## API Documentation

### Swagger UI
This project includes Swagger UI for API documentation, allowing you to easily explore and test the available endpoints.

- **Billing Microservice Swagger Documentation**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **Customer Microservice Swagger Documentation**: [http://localhost:9090/swagger-ui/index.html](http://localhost:9090/swagger-ui/index.html)

### Billing Microservice Endpoints
#### 1. Add a New Billing
- **Endpoint:** `POST /api/billings/add`
- **Request Body:**
    ```json
    {
        "amount": 150.00,
        "status": "Due"
    }
    ```
- **Responses:**
  - `201 Created`: Billing created successfully
  - `400 Bad Request`: Invalid input

#### 2. Get Billings by Status
- **Endpoint:** `GET /api/billings/status/{status}`
- **Path Parameter:** `status` (e.g., "Paid", "Due")
- **Responses:**
  - `200 OK`: Successfully retrieved billings
  - `404 Not Found`: No billings found for the given status

#### 3. Get Billing by ID
- **Endpoint:** `GET /api/billings/{billingId}`
- **Path Parameter:** `billingId` (ID of the billing)
- **Responses:**
  - `200 OK`: Successfully retrieved billing
  - `404 Not Found`: Billing not found for the given ID


### Customer Microservice Endpoints
#### 1. Add a New Customer
- **Endpoint:** `POST /api/customers/add`
- **Request Body:**
    ```json
    {
        "customerName": "John Doe",
        "mobileNumber": "1234567890"
    }
    ```
- **Responses:**
  - `201 Created`: Customer created successfully
  - `400 Bad Request`: Invalid customer data provided

#### 2. Get Customers with Due Bills
- **Endpoint:** `GET /api/customers/due-bills`
- **Query Parameter:** `status` (default: "Due")
- **Responses:**
  - `200 OK`: Successfully retrieved list of customers
  - `204 No Content`: No customers with due bills found
