# Project2: API tests

## Description

* The Project2 includes API tests for [Stellar Burgers](https://stellarburgers.nomoreparties.site/) API;
* tests grouped by endpoint located at ```/src/test/```;
* object models located at ```/src/main/java/```;
* test data creates before and removes after running;
* was added Allure report (it's available at: ```target/allure-results/```).

## List of Checks 
### User Creation 
* create new user;
* create user with already used credentials;
* create user without one of required fields.

### User Authorization
* existing user;
* invalid credentials.

### Changing User Data
* with authorization;
* without authorization.

### Place New Order
* with authorization;
* without authorization;
* with ingredients; 
* without ingredients; 
* with non-existing ingredient.

### Reciving list of Orders of Definite User
* with authorization;
* without authorization.

## Stack
* Java 11; 
* Amazon Coretto JDK;
* Maven;
* JUnit 4;
* RestAssured;
* Allure.

## How to Get Project Locally?
1. Clone repository: 
```git clone git@github.com:ilyastepanovs/Project2.git```;
2. Open project in IDE;
3. Run the tests from the following location ```/src/test/```

## How to Open Allure Report?
1. Open project directory;
2. Enter ```mvn allure:serve``` in terminal.

