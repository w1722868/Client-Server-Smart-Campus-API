# Smart-Campus-API

## API Overview
The SmartCampus API is a RESTful service created for managing rooms, sensors, and sensor readings in a smart campus environment. The API is built using JAX‑RS and demonstrates:

**Base URL**

This is where API starts and links to available resources. `http://localhost:8080/SmartCampus/api/v1`

### Rooms
It represents physical rooms on campus. The API supports listing of all rooms, retrieving a single room, creating rooms, and deleting rooms. A room cannot be deleted if it contains ACTIVE sensors.

- `GET /api/v1/rooms` - Get all rooms  
- `GET /api/v1/rooms/{id}` - Get a specific room  
- `POST /api/v1/rooms` - Create a new room  
- `DELETE /api/v1/rooms/{id}` - Delete a room if it doesn’t have **ACTIVE** sensors

### Sensors
It represents environmental sensors installed in rooms. The API supports listing of all sensors, filtering the sensors by it's type, and creating new sensors. A sensor can only be created if its `roomId` refers to an existing room.

- `GET /api/v1/sensors` - Get all sensors  
- `GET /api/v1/sensors?type={type}` - Filter sensors by type  
- `POST /api/v1/sensors` - Create a new sensor  
  - `roomId` must refer to an existing room

### Sensor Readings (Sub‑resource)
It represents a collection of readings for each sensor. The API exposes this as a sub‑resource at `/sensors/{id}/readings`. Adding a new reading updates the sensor’s `currentValue` and readings cannot be added if the sensor is in MAINTENANCE.

- `GET /api/v1/sensors/{id}/readings` - Get readings for a sensor  
- `POST /api/v1/sensors/{id}/readings` - Add a new reading  
  - Updates the sensor’s `currentValue`  
  - Not allowed if sensor is in `MAINTENANCE`
 
## Step-by-step guide on how to build the project and launch the server

The following steps explain how to set up the environment, build the project, and run the API on Apache Tomcat using NetBeans.

**Prerequisites**

- NetBeans IDE (Java version)
- Apache Tomcat 9+

**Step 1 - Install NetBeans**

Download and install NetBeans from: `https://netbeans.apache.org/` 

**Step 2 — Download and Configure Apache Tomcat**

1. Download Apache Tomcat 9 from: `https://tomcat.apache.org/download-90.cgi` 
2. Extract the downloaded ZIP file to a folder of your choice  

**Step 3 — Add Tomcat Server to NetBeans**

1. Open **NetBeans**.
2. Go to the **Services** tab.
3. Right‑click **Servers** → **Add Server**.
4. Select **Apache Tomcat or TomEE** → click **Next**.
5. Click **Browse** and select the folder where you extracted Tomcat  (e.g., `apache-tomcat-9.0.72`).
6. Enter any username and password you want (Tomcat requires credentials).
7. Click **Finish**.
8. NetBeans will ask for the same credentials again — enter them and click **OK**.

If everything is correct, you will see a **green triangle icon** next to the Tomcat 

**Step 4 — Create the Project**

1. In NetBeans, click **File → New Project**.
2. Select **Java with Maven → Web Application**.
3. Enter a project name (e.g., *SmartCampus*).
4. Click **Next**.
5. Select **Apache Tomcat** as the server.
6. Select **Java EE 8 Web** as the framework.
7. Click **Finish**.

**Step 5 — Build the Project**

In NetBeans, Right‑click the project > **Clean and Build**

**Step 6 — Run the Application**

In NetBeans, Right‑click the project > Run  

**Step 7 — Verify the API Is Running**

Open a browser or use curl: `http://localhost:8080/SmartCampus/api/v1`

## Sample curl commands to demonstrate successful interactions with different parts of the API

GET API Discovery Info:  
```bash
curl -X GET http://localhost:8080/SmartCampus/api/v1
```
GET All Rooms: 
```bash
curl -X GET http://localhost:8080/SmartCampus/api/v1/rooms
```
Create a New Room: 
```bash
curl -X POST http://localhost:8080/SmartCampus/api/v1/rooms/ \
  -H "Content-Type: application/json" \
  -d '{"id":"R10","name":"Test Room","capacity":40,"sensorIds":[]}'
```

Filter Sensors by Type: 
```bash
curl -X GET http://localhost:8080/SmartCampus/api/v1/sensors?type=CO2
```
Delete a Room: 
```bash
curl -X DELETE http://localhost:8080/SmartCampus/api/v1/rooms/R2
```
## Report

Below are the answers to the quesstions from the coursework brief. 

<ins>Part 1</ins>

**Question: In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions.**

The default lifecycle of a JAX-RS resource class is per-request, which means a new instance gets created for every incoming HTTP request. The resource class does not get treated as a singleton until and unless it is intentionally configured. As each request gets its own instance, instance fields are not shared between threads, which means they don’t need any synchronisation. However, any shared in‑memory data structures can be accessed by multiple threads simultaneously. This architectural decision means that the resource instance itself does not require thread-safety, but any shared data must be properly synchronised and managed using thread-safe data structures. 

**Question: Why is the provision of ”Hypermedia” (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?**

By adding hypermedia links inside REST responses, the API becomes more advanced because the server isn’t just returning data, but it is also telling the client what possible next actions could be. This means the client doesn’t have to guess how to move through the system, and the server guides the flow, which makes the interaction more flexible. This approach also benefits the client developers compared to static documentation because, instead of figuring out which endpoint to call next, the client can simply follow the links provided in the response. This way, it reduces the chance of mistakes and allows the API to change its structure without causing issues for existing clients.

<ins>Part 2</ins>

**Question: When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client-side processing.**

When returning a list of rooms, returning only rooms IDs means the response is quicker and smaller to send over the network. The advantage is that it uses less bandwidth, but the disadvantage is that the client must make an extra request to get full details for each room. 
On the other hand, returning the full room objects sends more data in one go, meaning clients receive full details for each room. The advantage is that the client gets everything they need immediately without making additional requests. However, the disadvantage is that it uses more bandwidth and can be slower due to large amount of data.

**Question: Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times.**

Yes, the DELETE operation is idempotent in my implementation. When the client sends a DELETE request for a specific room for the first time, the server will delete that specific room and return a success response (for example, the room has been deleted). If the client mistakenly sends the exact same DELETE request again for the same room, nothing new will happen on the server, as the room has already been deleted before. The server will return a response indicating that the room no longer exists, but the system does not change after the first successful DELETE request. This means whether the client sends the DELETE request once or multiple times, the result is the same: the room gets deleted if the request is sent for the first time or remains deleted after the first request.

<ins>Part 3</ins>

**Question: We explicitly use the @Consumes (MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch?**

The technical consequence the client will face when they send data in a different format apart from JSON is that JAX-RS will not accept the request. Because we are explicitly using @Consumes (MediaType.APPLICATION_JSON) annotation on the POST method, the server is expecting the request body to be JSON only. If the client sends the request body in a different format, such as text/plain or application/xml, JAX-RS will check the Content-Type header and will reject it if it doesn’t match. When this happens, the server will return an HTTP 415 Unsupported Media Type response and the POST method is not executed.

**Question: You implemented this filtering using @QueryParam.Contrast this with an alternative design where the type is part of the URL path (e.g., /api/v1/sensors/type/CO2). Why is the query parameter approach generally considered superior for filtering and searching collections?**

The query parameter approach is generally considered superior for filtering and searching collections because when we use @QueryParam - extract query parameters from the Query component of the request URL, the base URL stays the same, and the client can simply add filters (for example: /api/v1/sensors?type=CO2). This makes the API flexible and easier to extend. 
On the other hand, if the type is placed in the URL path (for example: /api/v1/sensors/type/CO2), it will look like a separate resource instead of just a filter, which makes the API harder to maintain. At a later stage, if we do want to add more filters, the URL path looks messy.  

<ins>Part 4</ins>

**Question: Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class?**

When everything is placed in one massive controller class (for example: sensors/{id}/readings/{rid}), the class becomes hard to read and maintain. Alternatively, the architectural benefits of using the Sub-Resource Locator pattern, which lets a parent resource class return another resource class, are that it helps to keep the API organised and easier to manage. Instead of placing all nested paths inside one class, we can delegate the logic to separate classes. This makes the API easier to extend, test and helps lift the pressure of being overloaded with responsibilities.

<ins>Part 5</ins>

**Question: Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?**

Standard 404 means that the URL is incorrect or there is no resource at that path. On the other hand, HTTP 422 (Unprocessable Entity) means that the request is valid, but the data inside is wrong. HTTP 422 is often considered more semantically accurate than a standard 404 because it means the JSON payload is valid, but the issue is that it contains a reference to something that doesn’t exist. So, HTTP 422 gives a clearer message.

**Question: From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?**

From a cybersecurity standpoint, exposing internal Java stack traces to external API consumers is risky because it reveals internal details. From a stack trace, information such as method name, class name, package structure and which frameworks are being used is revealed to the attacker, and they can use it to understand code, identify any weak points, look for vulnerabilities and plan targeted attacks. It is crucial to protect internal details from getting exposed.

**Question: Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method?**

It is advantageous to use JAX-RS filters for cross-cutting concerns like logging because we only need to define logging logic once, and thereafter, it applies logging automatically for all methods. On the other hand, manually inserting Logger.info() is a long and messy process, as inside every single resource method, we manually have to add logging code. This is a disadvantage against JAX-RS filters because it creates duplicated code and makes it hard to maintain the API, as every time we add a new endpoint, we must remember to add logging.
