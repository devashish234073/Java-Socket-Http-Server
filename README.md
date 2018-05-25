# Java-Socket-Http-Server
A socket http server implemented in Java with url mapping feature , which helps rapid development of web project.

The api package contains the files required for implementing a project. And the "Runner.java" in the default package is one such implementation.

Below is the code in the main method of "Runner.java"
![p1](https://user-images.githubusercontent.com/20777854/40559964-25868914-6076-11e8-93f3-bbeaf21b3683.png)

## Blue box
Here first we need to create url mappings. So a "GET" request with "/" url will be served by the "index.html" page from the "html" directory.

## Red box
This is another way of doing the above step where the abstract class "AbstractResponse"'s getResponse is implemented. It has dynamic behaviour as it has the handle of the "Request" object. 

## Green box
This is the loop that serves the incoming requests.
"server.accept()" gets the request
"server.sendResponse()" send the response from the mapping based on the url pattern.

Below picture shows the output at "http://localhost:8888" in the browser:
![p2](https://user-images.githubusercontent.com/20777854/40560814-c76a1e1a-6078-11e8-821c-a2344e2ff445.png)

Below picture shows the classes (and some important portion from each) inside the api package:
![p2](https://user-images.githubusercontent.com/20777854/40561564-506c79cc-607b-11e8-9930-67782f491376.png)

### AbstractResponse.java
This is an abstract class to provide dynamic responses through the Mappings.

### Mapping.java
This class has two constructors to set url mappings. Already demonstrated above in the first image in the blue and red box.

### Request.java
The parse() method in this class extracts out all the information and attribute out of request , which helps in creating dynamic web pages.

### Response.java
The constructor for this takes a plain String as input and attaches the http header to it thus making it browser readable.

### Server.java
This class is baically the first point to use any of the above classes.

