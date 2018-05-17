# Week 13 RESTful Services

## Agenda

- RESTful Services
- Testing
- Activity
- Project

## RESTful Services

### What are RESTful Services?

- REpresentational State Transfer
	- Don't think request/response
	- Think state of entities
	- Think operations that retrieve and alter state
- Built on top of HTTP verbs (i.e., CRUD operations)
	- POST - create a new entity
	- GET - read current state of an entity or entities
	- PUT - update the current state of an entity
	- DELETE - delete an entity
- Typically the request body and response body are in JSON
	- Any MIME type is supported in actuality

### Read

- Retrieve the list of all names
	- GET http://localhost:8080/NamesApp13/names
- Retrieve a single name by id
	- GET http://localhost:8080/NamesApp13/names/1

### Create

- Create a new entity
	- POST http://localhost:8080/NamesApp13/names
- Request body (the entity state to create)

	{
		"first": "Aaron",
		"last": "Rodgers",
	}

- Response body (the entity state that was created)

	{
		"id": "12345",
		"first": "Aaron",
		"last": "Rodgers"
	}

### Update

- Update an existing entity
	- PUT http://localhost:8080/NamesApp13/names/12345
- Request body (the new entity state to update)

	{
		"id": "12345",
		"first": "Erin",
		"last": "Rodgers"
	}

### Delete

- Delete an entity
	- DELETE http://localhost:8080/NamesApp13/names/12345

## Testing REST Services

- Postman

## Activity

- Create new package: rest
- Create new NameController annotated with @RestController
- Add appropriate methods to NameController (GET, POST, PUT, DELETE)
- Add property to application.properties (hack workaround for now)
- Modify Name and Address entities' "id" fields to "uuid" generated fields
	- Required for creation of new entities

## Project Week 13

- Add REST services to your week 12 project
- You should include a REST controller with methods for the following:
	- GET /product - returns a list of products
	- GET /product/{id} - returns a single product by ID
	- POST /product - creates a new product
	- PUT /product - updates an existing product
	- DELETE /product/{id} - deletes an existing product
