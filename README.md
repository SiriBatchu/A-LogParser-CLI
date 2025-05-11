# A-LogParser-CLI

A powerful command-line utility designed to extract, process, and summarize data from diverse log files such as APM logs, application logs, and request logs. It categorizes entries, computes essential metrics, and outputs structured JSON reports. The system is built to support easy integration of new log formats and types.

# 🔍 Core Capabilities

## 📘 Multi-Format Parsing:

Processes APM, application, and request logs efficiently.

Ignores corrupt or incomplete entries without interruption.

## 📈 Detailed Analytics:

APM Logs: Computes stats like min, max, median, and average for each performance metric.

Application Logs: Tallies counts based on severity (INFO, ERROR, etc.).

Request Logs: Delivers:

Response time analysis (min, max, and percentiles: 50th, 90th, 95th, 99th).

HTTP status code distributions (e.g., 2XX, 4XX, 5XX).

## 🛠️ Scalable Architecture:

Built for modular growth—easily accommodates new log formats.

## 📁 JSON Reports:

Outputs results to separate files like apm.json, application.json, and request.json.

# Getting Started

## Prerequisites

Java 8+

Maven

# Instructions

## Clone the codebase:

## How to Build and Run

### 1. Clone the repository
```bash
git clone https://github.com/yourusername/logparser.git
cd logparser
```

### 2. Build the project
```bash
mvn clean compile
```

### 3. Run the application
> Make sure your log file (e.g., `input.txt`) is in the project root directory.

```bash
mvn exec:java -D"exec.mainClass"="logparser.Main" -D"exec.args"="--file input.txt"
```

### 4. Run the tests
```bash
mvn test
```

### 5. Output files
After running the application, you will find:
- `apm.json` – APM metric aggregations
- `application.json` – Log level counts
- `request.json` – API route response statistics

