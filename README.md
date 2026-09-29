# AIM - Anesthesia Information Management System

An **Anesthesia Information Management System** (AIMS) for documentation and decision support in perioperative medicine.

This project is intellectual property of the **University of Applied Sciences Technikum Wien**. All rights reserved by the authors.

---

## 📋 Overview

AIM is a Java-based desktop application that provides comprehensive documentation and decision support for anesthesia management in clinical settings. The system integrates with healthcare standards such as FHIR (Fast Healthcare Interoperability Resources) for medical data exchange and uses PostgreSQL for robust data persistence.

---

## ✨ Key Features

- **Patient Documentation**: Comprehensive anesthesia records and patient data management
- **FHIR Integration**: Support for HL7 FHIR R4 standard medical data interchange
- **Database Support**: PostgreSQL backend for secure data storage
- **Modern UI**: JavaFX-based graphical interface for intuitive user interaction
- **Decision Support**: Tools for informed clinical decision-making during anesthesia procedures

---

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|---|---|---|
| **Java** | 18 | Core application language |
| **JavaFX** | 18 | Desktop GUI framework |
| **Maven** | Latest | Build automation and dependency management |
| **PostgreSQL** | 42.4.0 | Database system |
| **FHIR (HAPI)** | 6.0.1 | Healthcare interoperability standard |
| **JUnit 5** | 5.8.1 | Unit testing framework |
| **Logback** | 1.2.11 | Logging framework |

---

## 📦 Project Structure

```
AIM/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/
│       │       └── (Application source code)
│       ├── resources/
│       │   └── (FXML layouts, CSS, etc.)
│       └── IPS-example-Bundle-with-renal-disease-et-al.json
├── pom.xml
├── README.md
├── .gitignore
└── Software.iml
```

---

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK)** 18 or higher
- **Maven** 3.6+
- **PostgreSQL** 12+ (for database backend)
- **Git**

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/ayah05/AIM.git
   cd AIM
   ```

2. **Install dependencies:**
   ```bash
   mvn clean install
   ```

3. **Configure PostgreSQL:**
   - Ensure PostgreSQL is running
   - Update database connection details in your configuration file

4. **Build the project:**
   ```bash
   mvn compile
   ```

5. **Run the application:**
   ```bash
   mvn clean javafx:run
   ```

---

## 📝 Development

### Build with Maven

```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package the application
mvn package
```

### Project Configuration

The project uses Maven for dependency management. Key configurations:

- **Compiler Target**: Java 18
- **Source Encoding**: UTF-8
- **JavaFX Maven Plugin**: For running GUI applications

---

## 📚 Dependencies

### Core Dependencies
- **JavaFX**: GUI framework (controls, FXML, base)
- **PostgreSQL Driver**: Database connectivity
- **HAPI FHIR**: Healthcare data standards integration
- **JUnit 5**: Testing framework
- **Logback**: Logging infrastructure
- **Woodstox**: XML processing

---

## 📄 Data Format

The project includes sample FHIR data in JSON format:
- `IPS-example-Bundle-with-renal-disease-et-al.json` - Example medical data bundle with patient conditions

---

## ⚖️ License & Rights

**Intellectual Property Notice:** This project is intellectual property of the **University of Applied Sciences Technikum Wien**. All rights are reserved by the authors.

For usage, modification, or distribution inquiries, please contact the copyright holders.

---

## 👥 Authors

- **University of Applied Sciences Technikum Wien**
- **GitHub**: [@ayah05](https://github.com/ayah05)

---

## 📞 Support & Contribution

- **Issues**: [GitHub Issues](https://github.com/ayah05/AIM/issues)
- **Pull Requests**: [GitHub Pull Requests](https://github.com/ayah05/AIM/pulls)
- **Discussions**: [GitHub Discussions](https://github.com/ayah05/AIM/discussions)

---

## 🔗 References

- [FHIR Standard](https://www.hl7.org/fhir/)
- [JavaFX Documentation](https://openjfx.io/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Maven Documentation](https://maven.apache.org/)

---

**Last Updated**: January 2023
