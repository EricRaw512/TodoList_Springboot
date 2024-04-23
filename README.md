# Introduction

TodoList App is built For Making todo list with REST API

# Rest API Documentation
## Login
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
```javascript
{
  "jwt": "string"
}
```
_____________________________________________________________________

## Register
```http
POST /api/register
```
Request Body
```javascript
{
  "username": "string",
  "password": "string"
}
```
_____________________________________________________________________
## Get All Checklist
| Header | Description |
| :--- | :--- |
| `Header` | `Bearer {token} (JWT token obtained from login)` |

```http
GET /api/checklist
```
Response Body
```javascript
[
  {
    "id": number,
    "name": "string",
  },
  .
```
_____________________________________________________________________
## Create Checklist
| Header | Description |
| :--- | :--- |
| `Header` | `Bearer {token} (JWT token obtained from login)` |

```http
POST /api/checklist
```
Request Body
```javascript
{
  "name": "string"
}
```
Response Body
```javascript
{
  "id": number,
  "name": "string",
}
```
_____________________________________________________________________
## Create Checklist
| Header | Description |
| :--- | :--- |
| `Header` | `Bearer {token} (JWT token obtained from login)` |

```http
POST /api/checklist
```
Request Body
```javascript
{
  "name": "string"
}
```
Response Body
```javascript
{
  "id": number,
  "name": "string",
}
```
