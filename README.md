# webSocket-demo
Web socket demo for collaborative data entry

**Transport Management System**<br>
This web application serves as a Transport Management System, allowing collaborative data entry and inline editing of tables. It enables users to easily add, edit, and delete records in the table.

Features:
* Collaborative data entry
* Inline editing of tables
* Automatic reconnection to the server if the connection is lost
* Automatic page refresh upon successful reconnection
* Keyboard navigation for DataTables

Technologies Used:
*	Java 17
*	Spring Boot Framework 3.2.5
*	Hibernate
*	H2 database
*	HTML
*	CSS (Bootstrap)
*	JavaScript (jQuery)
*	DataTables JS
*	Web Socket JS (SockJS and Stomp.js)
*	Drag and drop JS (jQuery UI)
*	Google Maps API autocomplete widgets

Setup:
1.	Clone the repository to your local machine.
2.	Ensure you have Maven installed on your system.
3.	Navigate to the project directory using the terminal/command prompt.
4.	Run the following command to start the Spring Boot application:
```shell
mvn spring-boot:run
```
6.	Access the application http://localhost:8080/jobs in your web browser.

How to Use:
1.	Click on the "Add Record" button to add a new record.
2.	To edit an existing record, simply click on the cell you want to edit and make changes.
3.	Focus on any cell within the table, then use the arrow keys to navigate vertically or horizontally through table cells.
4.	Navigate away from the cell or click outside it to save changes. Alternatively, press the 'Esc' key to discard changes.
5.	To delete a record, click on the "Delete" button in the corresponding row.

Contributors:
1.	Teguh Adi Sumantri
