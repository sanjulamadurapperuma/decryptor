# Decryptor

## Overview

This project is a simple tool designed to **decrypt** encrypted strings, using **RSA asymmetric encryption**. This tool allows the user to input an encrypted string (Base64 encoded) and decrypt it using a private key stored in a JKS keystore.

## Disclaimer

**Important**:

- This tool is **for testing purposes only**. It should not be used in a production environment or for any unauthorized decryption of data.
- The tool is intended for use by individuals who have proper authorization to handle encrypted credentials.
- **Use at your own discretion**. Decrypting strings and accessing private data may violate privacy agreements, security policies, or laws in certain jurisdictions.
- This tool should only be used to **decrypt strings for testing, debugging, and development** purposes within controlled environments.

## Features

- Accepts **Base64 encoded encrypted string**.
- Decrypts the password using **RSA/ECB/OAEP with SHA-1 and MGF1 padding**.
- Can load the private key from a **JKS keystore**.
- Outputs the decrypted string in plain text.

## Prerequisites

- **Java 8 or higher**.
- A **JKS keystore** that contains the private key for decryption.
- Maven for building the project.

## How to Use

### 1. Build the Project

You need to build the project first using Maven. If you haven’t already, install Maven on your system.

#### Steps to Build:

1. Clone the repository:

    ```bash
    git clone https://github.com/sanjulamadurapperuma/decryptor.git
    cd decryptor
    ```

2. Run Maven to build the project:

    ```bash
    mvn clean package
    ```

3. After successful build, you’ll find the JAR file in the `target/` directory (e.g., `decryptor-1.0.jar`).

### 2. Running the Tool

To run the tool, use the following command:

```bash
java -jar target/decryptor-1.0.jar
```
