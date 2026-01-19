# FileServerCLI - File Server Client-Server Application
![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk)
![Status](https://img.shields.io/badge/Status-Complete-brightgreen)
![Architecture](https://img.shields.io/badge/Architecture-OOP-blue)
## Overview
FileServerCLI is a client-server file management system that allows users to upload, download, and delete files on a remote server through a command-line interface. The application supports files of any format, transmitting them as byte arrays between client and server.
This project was completed as part of the [Hyperskill](https://hyperskill.org/projects/52) educational project.

## Features

- **Client-Server Architecture:** Separate client and server applications
- **File Operations:**
  - **GET** - Download files from server
  - **PUT** - Upload files to server
  - **DELETE** - Remove files from server
- **Dual File Identification:** Access files by both **name** and **ID**
- **Binary File Support:** Handle files of any format (images, documents, archives, etc.)
- **Local Storage:** Both client and server store files in their respective `data` directories
- **Console Interface:** Simple CLI for all operations

## Project Structure

```
FileServerCLI/
├── src/
│   ├── client/
│   │   ├── data/              # Client-side file storage
│   │   ├── ClientService.java # Client main service logic
│   │   ├── FileManager.java   # Client file operations
│   │   ├── Main.java          # Client entry point
│   │   └── RequestManager.java # HTTP request handling
│   ├── server/
│   │   ├── data/              # Server-side file storage
│   │   ├── FileManager.java   # Server file operations
│   │   ├── Main.java          # Server entry point
│   │   └── RequestManager.java # Server request processing
├── .gitignore
├── FileServer.iml
└── README.md
```

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher

### Compilation

1. **Navigate to the project directory:**
```bash
cd FileServerCLI
```

2. **Compile both client and server:**
```bash
# Compile server
javac -d out src/server/*.java

# Compile client
javac -d out src/client/*.java
```

### Running the Application

#### Start the Server:
```bash
# From the project root directory
java -cp out server.Main
```

#### Start the Client (in a separate terminal):
```bash
# From the project root directory
java -cp out client.Main
```

**Default Connection:**
- Server runs on `localhost:23456` (configurable in code)
- Client connects to the same address

## 🎮 Usage Instructions

### Client Menu Options:
```
Enter action (1 - get a file, 2 - create a file, 3 - delete a file, 4 - exit, 5 - stop server): > 
```

### Available Actions:

#### 1. **GET a File** - Download from Server
```
Enter action: > 1
Do you want to get the file by name or by id (1 - name, 2 - id): > 1
Enter filename: > myfile.pdf
The request was sent.
File saved on the hard drive!
```
- Files are downloaded to client's `data/` directory

#### 2. **PUT a File** - Upload to Server
```
Enter action: > 2
Enter filename: > document.txt
Enter name of the file to be saved on server: > server_copy.txt
The request was sent.
Response says that file is saved! ID = 12345
```
- Files are uploaded from client's `data/` directory
- Server responds with a unique ID for the file

#### 3. **DELETE a File** - Remove from Server
```
Enter action: > 3
Do you want to delete the file by name or by id (1 - name, 2 - id): > 1
Enter filename: > oldfile.jpg
The request was sent.
The response says that the file was successfully deleted.
```

#### 4. **Exit** - Close Client
```
Enter action: > 4
```

#### 5. **Stop Server** - Shutdown Remote Server
```
Enter action: > 5
The request was sent.
```

## Important Notes

- **Organize Files:** Keep files to upload in client's `data/` directory
- **Check Server Storage:** Monitor server's `data/` directory for uploaded files
- **Server Must Be Running** Client cannot connect if server is not started
- **Concurrent Access:** The basic version may not support multiple simultaneous clients
- **No Authentication:** All clients have full access to all files
- **File Overwrites:** Uploading with existing names may overwrite files
- **Backup Important Files:** Server deletions are permanent
