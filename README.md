# webSocket-demo
Web socket demo for collaborative data entry

**Transport Management System**<br>
This web application serves as a Transport Management System, allowing collaborative data entry and inline editing of tables. It enables users to easily add, edit, and delete records in the table.

Features:
* Collaborative data entry
* Inline editing of tables
* Organizing the table rows using drag and drop.
* Automatic reconnection to the server if the connection is lost
* Automatic page refresh upon successful reconnection
* Keyboard navigation for DataTables
* Google Maps API address autocomplete

Technologies Used:
*	Java 17
*	Spring Boot Framework 3.2.5
*	Hibernate
*	H2 database
*	Thymeleaf template
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
3.	Use the arrow keys to navigate vertically or horizontally through table cells.
4.	When a cell with a select dropdown or datepicker is selected, press the space key to open the dropdown and use arrow keys to navigate options. Press the 'Enter' key to select an option.
5.	In a cell with an address autocomplete selected, begin typing the address. As you type, a dropdown list of suggested addresses will appear. Use the arrow keys to navigate through the options. Press the 'Enter' key to select an address option. If needed, confirm the selection before proceeding.
6.	Navigate away from the cell or click outside it to save changes. Alternatively, press the 'Esc' key to discard changes.
7.	To delete a record, click on the "Delete" button in the corresponding row.
8.	You can easily rearrange rows in the table by clicking and dragging them. Simply click on the row you want to move, hold down your mouse button, and then drag it to the new position. Once you release the mouse button, the row will be dropped into place.

Contributors:
1.	Teguh Adi Sumantri
