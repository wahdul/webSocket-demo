# webSocket-demo
Web socket demo for collaborative data entry

Transport Management System
This web application serves as a Transport Management System, allowing collaborative data entry and inline editing of tables. It enables users to easily add, edit, and delete records in the table.

Features:
* Collaborative data entry
* Inline editing of tables
* Automatic reconnection to the server if the connection is lost
* Automatic page refresh upon successful reconnection

Technologies Used:
*	Java 17
*	Spring Boot Framework
*	Hibernate
*	H2 database
*	HTML
*	CSS (Bootstrap)
*	JavaScript (jQuery)
*	DataTables JS
*	Web Socket JS (SockJS and Stomp.js)
*	Drag and drop JS (jQuery UI)

Setup:
1.	Clone the repository to your local machine.
2.	Ensure you have Maven installed on your system.
3.	Navigate to the project directory using the terminal/command prompt.
4.	Run the following command to start the Spring Boot application:
```shell
mvn spring-boot:run
```
6.	Access the application in your web browser using the provided URL (usually http://localhost:8080).

How to Use:
1.	Click on the "Add Record" button to add a new record.
2.	To edit an existing record, simply click on the cell you want to edit and make changes.
3.	Press the "Enter" key to save the changes, or click outside the cell.
4.	To delete a record, click on the "Delete" button in the corresponding row.

Contributors:
1.	Teguh Adi Sumantri
