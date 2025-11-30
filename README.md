📌 Lottery Results Scraper – Spring Boot | JSoup | Hibernate | MySQL

This project is a backend application built using Spring Boot, Hibernate/JPA, and JSoup to automatically scrape Govisetha lottery results from the official NLB Sri Lanka website and store them in a database.
The stored results are then retrieved and printed in the console for verification or for further processing.

🚀 Features

Scrapes latest Govisetha lottery results from NLB official website.
Parses HTML content using JSoup.
Stores results into MySQL database using:

1.Spring Data JPA
2.Hibernate ORM
3.Entity mapping

Retrieves & prints stored results in Spring Boot console log.
Runs automatically at application startup using CommandLineRunner.

🛠️ Tech Stack
Technology	Purpose
Spring Boot	Backend framework
JSoup	Web scraping HTML parser
Hibernate / JPA	ORM for database operations
MySQL	Database
Lombok	Reduces boilerplate code
Maven	Dependency management
📂 Project Structure
src/main/java/com/example/lottery_results
│
├── entity/
│   └── LotteryResult.java
│
├── repository/
│   └── LotteryResultRepository.java
│
├── service/
│   ├── LotteryScraperService.java
│   └── LotteryService.java
│
├── LotteryResultsApplication.java

🔧 How It Works

Application starts
JSoup connects to NLB Govisetha results page
HTML content is scraped
Parsed results are converted into LotteryResult entity
Data saved in MySQL database
Data read back and printed in console log

📥 Installation & Setup
1. Clone Repository
git clone https://github.com/sheharagamage/lottery-results-scraper.git
cd lottery-results-scraper

2. Update application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/lottery_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
logging.level.org.hibernate.SQL=debug

📦 Dependencies

Main dependencies in pom.xml:

<dependency>
    <groupId>org.jsoup</groupId>
    <artifactId>jsoup</artifactId>
    <version>1.16.1</version>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
</dependency>

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>

▶️ Run the Application

Run using Maven or IDE:

mvn spring-boot:run

📘 Example Console Output
Fetched and stored Govisetha results successfully!
Draw No: 5234
Date: 2025-11-28
Winning Numbers: 03, 11, 21, 32, 37
