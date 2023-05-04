# Congestion Tax Calculator

## Assignment

### The job done 

In the 4 horas to do this assignment, the following tasks was prioritized and completed :

Included Spring Boot

Included Maven at the app

Writed the Unit Test check if the implemented rules was correct based the description
    
- test with Saturday & Sunday
- test with all public holidays
- test with days before public holiday
- test with July
- test with a valid day but invalid time
- test with a valid day and time
- test with a vehicule that passes several stations in 60 minutes,
and if is taxed once. Validate if the amount paid is the highest one
- test if is a valid exempt vehile an if is not taxed
- test if the vehicule is taxed the maximum of 60 per day

Created an Endpoint to receive the inputs

### Tasks to be done

The following tasks couldn't be implemented:

Write test for the endpoint
- Test the complete functionality

Implement constraints validation for the endpoint inputs

Fix the bug's code

### Improvements

Refactory the app to use LocalDateTime (Java 8+) rather than the old fashion Date classe
- This will cause a significant change at the  CongestionTaxCalculator classe, 
      since i will use the LocalDate apis to deal with year, months and days

Changes the code to accept any city and year

Save the data in a data store


