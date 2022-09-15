[![Build Status](https://app.travis-ci.com/QmBo/warranty.svg?branch=master)](https://app.travis-ci.com/QmBo/warranty) [![codecov](https://codecov.io/gh/QmBo/warranty/branch/master/graph/badge.svg?token=vabvqtYqzz)](https://codecov.io/gh/QmBo/warranty)

# Warranty service
# What is doing?
This service was created to keep track of the serial numbers of the sold devices.

1. Everyone can check guarantees. 
    * When checking the warranty, the service also tries to determine the model of the device and the date of its production.

2. The moderator and administrator can add warranty records and add new products to the system.
3. The administrator can create and edit users.

# Development
1. Clone the repository `git clone https://github.com/QmBo/warranty.git`
2. Change to the project root directory `cd warranty/`
3. Give rights to the maven file `sudo chmod a+x ./mvnw`
4. Build a project `./mvnw package -Dmaven.test.skip=true`
5. Build the docker image `sudo docker build -t warranty .`
6. Start the service `docker-compose up -d`

# Used:
* Spring Boot
* Spring WEB
* Spring Data
* Spring Validate
* Lombok
