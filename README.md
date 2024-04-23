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
## Delete Checklist
| Header | Description |
| :--- | :--- |
| `Header` | `Bearer {token} (JWT token obtained from login)` |

```http
DELETE /api/checklist/{checklistId}
```
_____________________________________________________________________
## Get All Checklist Items for a Checklist
| Header | Description |
| :--- | :--- |
| `Header` | `Bearer {token} (JWT token obtained from login)` |

```http
GET /api/checklist/{checklistId}/item
```
Response Body
```javascript
[
  {
    "id": number,
    "itemName": "string",
    "completed": boolean
  },
  ...
]
```
_____________________________________________________________________
## Create Checklist Item
| Header | Description |
| :--- | :--- |
| `Header` | `Bearer {token} (JWT token obtained from login)` |

```http
POST /api/checklist/{checklistId}/item
```
Request Body
```javascript
{
  "itemName": "string"
}
```
Response Body
```javascript
{
  "id": number,
  "itemName": "string",
  "completed": boolean
}
```
_____________________________________________________________________
## Get Checklist Item
| Header | Description |
| :--- | :--- |
| `Header` | `Bearer {token} (JWT token obtained from login)` |

```http
GET /api/checklist/{checklistId}/item/{checklistItemId}
```
Response Body
```javascript
{
  "id": number,
  "itemName": "string",
  "completed": boolean
}
```
_____________________________________________________________________
## Update Checklist Item Status
| Header | Description |
| :--- | :--- |
| `Header` | `Bearer {token} (JWT token obtained from login)` |

```http
PUT /api/checklist/{checklistId}/item/{checklistItemId}
```
Response Body
```javascript
{
  "id": number,
  "itemName": "string",
  "completed": boolean
}
```
_____________________________________________________________________
## Delete Checklist Item
| Header | Description |
| :--- | :--- |
| `Header` | `Bearer {token} (JWT token obtained from login)` |

```http
DELETE /api/checklist/{checklistId}/item/{checklistItemId}
```
_____________________________________________________________________
## Rename Checklist Item
| Header | Description |
| :--- | :--- |
| `Header` | `Bearer {token} (JWT token obtained from login)` |

```http
PUT /api/checklist/{checklistId}/item/rename/{checklistItemId}
```
Request Body
```javascript
{
  "itemName": "string"
}
```
Response Body
```javascript
{
  "id": number,
  "itemName": "string",
  "completed": boolean
}
```
