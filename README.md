# Oye Rating System
#### Assumptions
  - Driver and Passanger accounts are already there in a table.
  - These API will be called from secure server or some internal service;
  - If there is no record found the default rating result will be -1 with appropriate message.  

#### Approach 

#### DB schema 
![Database Schema](data:image/png;base64,https://github.com/AlvisVadaliya/oye_rating_system/blob/master/.jpg?raw=true)

### Installation
- Application is live [here](https://oye-rating-system.herokuapp.com/swagger-ui.html#/rating-controller) on heroku free server, but in case heroku is down below are the steps to install project locally

-- create database ```oye_rating_system``` in your local mysql
--  ```cd rating-System```
--  open: ```src/main/resources/application.properties```
--  edit below properties :
    - ```spring.datasource.username``` 
    - ```spring.datasource.username``` 
    - ```spring.datasource.url``` 
--run ```mvn spring-boot:run``` command in your terminal 