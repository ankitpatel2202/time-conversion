# Multi-Format Time Converter - Spring Boot Application

A Spring Boot web application that converts digital time format to spoken form in multiple languages (British, German, and Czech). This application provides REST API for time conversion with support for different regional time expressions.

## Features

- **Multi-Format Support**: Convert times to British, German, and Czech spoken formats
- **REST API**: Complete RESTful API with multiple endpoints
- **Comprehensive Time Conversion**: Handles all 24-hour time formats
- **Special Cases**: Handles noon (12:00), midnight (00:00), and quarter/half expressions


## Supported Formats

### British Time Expressions
The application converts times using traditional British spoken patterns:

- 1:00 one o'clock
- 2:05 five past two
- 3:10 ten past three
- 4:15 quarter past four
- 5:20 twenty past five
- 6:25 twenty five past six
- 6:32 six thirty two
- 7:30 half past seven
- 7:35 twenty five to eight
- 8:40 twenty to nine
- 9:45 quarter to ten
- 10:50 ten to eleven
- 11:55 five to twelve
- 00:00 midnight
- 12:00 noon


### German Time Expressions
German time conversion follows German spoken time patterns (implementation pending).

### Czech Time Expressions
Czech time conversion follows Czech spoken time patterns (implementation pending).

## API Endpoints

### Convert Single Time
- **POST** `/api/v1/time/convert`
  ```json
  {
    "time": "14:30",
    "formatType": "BRITISH"
  }
  ```

### Supported Format Types
- `BRITISH` - British spoken time format (default)
- `GERMAN` - German spoken time format
- `CZECH` - Czech spoken time format



## How to Run

### Prerequisites
- Java 21 or higher
- Maven 3.6+

### Building the Project
```bash
cd british-spoken-time-converter
mvn clean compile
```

### Running the Application
```bash
# Using Maven
mvn spring-boot:run

# Using JAR file
mvn clean package
java -jar target/british-spoken-time-converter-1.0-SNAPSHOT.jar
```



## Usage

### REST API

#### Convert a single time (British format - default):
```bash
curl -X POST http://localhost:8080/api/v1/time/convert \
  -H "Content-Type: application/json" \
  -d '{"time": "14:30"}'
```

#### Convert a single time (German format):
```bash
curl -X POST http://localhost:8080/api/v1/time/convert \
  -H "Content-Type: application/json" \
  -d '{"time": "14:30", "formatType": "GERMAN"}'
```

#### Convert a single time (Czech format):
```bash
curl -X POST http://localhost:8080/api/v1/time/convert \
  -H "Content-Type: application/json" \
  -d '{"time": "14:30", "formatType": "CZECH"}'
```



## API Response Format

### Success Response
```json
{
  "originalTime": "14:30",
  "spokenTime": "half past two pm",
  "error": null
}
```

### Error Response
```json
{
  "originalTime": "25:00",
  "spokenTime": null,
  "error": "Invalid time format. Please use HH:MM format (e.g., 12:00, 14:30)"
}
```



## Future Enhancements

Potential improvements could include:
- **Complete German Implementation**: Full German time conversion logic
- **Complete Czech Implementation**: Full Czech time conversion logic

