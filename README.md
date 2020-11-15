# Taxi Rating System
#### Assumptions
  - Driver, Passanger accounts and Rides are already there in a table. 
  - These API will be called from secure server or some internal service;
  - If there is no record found the default rating result will be -1 with appropriate message.  
  
#### DB schema 
![Dbschema Img](<src/main/resources/databaseSchema.png>)

### Installation
- Application is live [here](https://oye-rating-system.herokuapp.com/swagger-ui.html#/rating-controller) on heroku free server, but in case heroku is down below are the steps to install project locally

- create database ```oye_rating_system``` in your local mysql
- clone this project 
-   ```cd rating-System```
-   open: ```src/main/resources/application.properties``` in your Text-editor
-   edit below properties :
    - ```spring.datasource.username``` 
    - ```spring.datasource.password``` 
    - ```spring.datasource.url``` 

-   run ```mvn spring-boot:run``` command in your terminal
