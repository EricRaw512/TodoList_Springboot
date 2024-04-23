# Introduction

TodoList App is built For Making todo list with REST API

# Rest API Documentation

## Authorization
Login
```http
POST /api/login
```
Request Body
```javascript
{
  "username": "string",
  "password": "string"
}
```
Response Body
{
  "jwt": "string"
}

