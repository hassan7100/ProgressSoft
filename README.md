# ClusteredData Warehouse
## Overview
Welcome to the ClusteredData Warehouse project! This README provides an overview of the system and its functionalities.

## Objective
The primary objective of this project is to accept FX deal details, validate the input data, and persist the validated data into a database. The system should ensure that the data is accurately recorded and that duplicate requests are not imported.
## Request Logic
The system processes requests according to the following logic:
#### Request Fields:
* Deal Unique Id
* From Currency ISO Code (Ordering Currency)
* To Currency ISO Code
* Deal Timestamp
* Deal Amount in Ordering Currency
#### Validation:
* Validates the structure of each row, checking for missing fields, and correct data types.
#### Duplication Check:
* The system prevents the import of the same request twice. If a request with the same unique identifier is received, it is not be imported.
#### Persistence:
* Once validated, the system should save each imported row into the database. Rollback does not happen.
## Getting Started
To get started with the ClusteredData Warehouse project, follow these steps:
1. Clone the Repository:
   ```git clone https://github.com/hassan7100/ProgressSoft/tree/master```
2. Install Dependencies: Install docker from the Docker website: https://www.docker.com/
4. open cmd on the path of the project then cd to the ReadyEnv: ```cd ReadyEnv```.
5. type the command: ```docker compose up -d```.

__You can build the jar using the command `mvn install -Dmaven.test.skip=true`__
#### UserInterface
The user will see this interface when first entering the website:

![image](https://github.com/hassan7100/ProgressSoft/assets/83410135/15eb7753-8dae-4247-b2a3-d71ac174a603)

#### UserInteraction and functionality
The user is supposed to choose a csv file that includes the deals he wants to upload, when selecting the file, the user will see:

![image](https://github.com/hassan7100/ProgressSoft/assets/83410135/fe28b448-53d8-4ffe-9938-4df4ea458d45)

After that, he will press the upload file button, and see a brief illustration of the process on the backend, see figure below.

![image](https://github.com/hassan7100/ProgressSoft/assets/83410135/700a6d94-4667-4ab5-886a-7588c0781dce)

Deals may have problems, like duplicate Id or wrong format, see figure: 

![image](https://github.com/hassan7100/ProgressSoft/assets/83410135/44a9fc74-aff9-47a0-82a4-cfd1a4105857)

## DataStructure: SynchronizedTimeSet
We can use the database mainly to check if the database contains some deal (record), 
but imagine someone uploaded a file containing 10,000 or even larger, 10,000,000, 
it will be more than 30 secs proessing time on the backend, so I have thought about an idea to cache deals on some data structure, but at the same time, we must be careful that the main memory might get populated fast,
so time caching here is the best choice to fix the both issues, Memory and performance.

Each record will have a time to live on our set, after that, it will be deleted, this way, we can most of the time, reduce the time accessing the DB,
and only focusing on the processing of the server.

## UnitTesting
I have written a propper unit testing for most cases that could happen, using Junit and Mocks.
