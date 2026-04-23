# XK Client - Minecraft Launcher

A powerful, open-source Minecraft launcher for managing versions, mods, and game instances with a modern JavaFX interface.

## Features

✅ **Multi-Version Support** - Manage multiple Minecraft versions seamlessly
✅ **Mod Management** - Support for Forge and Fabric mods with dependency resolution
✅ **Multiple Game Instances** - Create and manage separate game profiles
✅ **Account Management** - Store and manage multiple Minecraft accounts
✅ **Server Quick-Connect** - Save and quickly connect to favorite servers
✅ **Custom JVM Arguments** - Fine-tune performance with custom JVM settings
✅ **Performance Monitoring** - Real-time monitoring of game performance
✅ **Cross-Platform** - Works on Windows, macOS, and Linux
✅ **Game Launcher** - Direct launch from the launcher
✅ **Download Manager** - Automated version and mod downloads

## Requirements

- Java 11 or higher
- Maven 3.6+
- Minecraft Java Edition

## Installation

### Clone the Repository
```bash
git clone https://github.com/yt-xkvit/xk-client.git
cd xk-client
```

### Build with Maven
```bash
mvn clean install
```

### Run the Launcher
```bash
mvn javafx:run
```

## Project Structure

```
xk-client/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/xkclient/
│   │   │       ├── XKClient.java
│   │   │       ├── model/
│   │   │       │   ├── GameInstance.java
│   │   │       │   ├── Account.java
│   │   │       │   ├── Mod.java
│   │   │       │   └── Server.java
│   │   │       ├── service/
│   │   │       │   ├── ConfigService.java
│   │   │       │   ├── InstanceService.java
│   │   │       │   ├── AccountService.java
│   │   │       │   └── ServerService.java
│   │   │       ├── ui/
│   │   │       │   └── controller/
│   │   │       │       ├── MainController.java
│   │   │       │       ├── InstancesController.java
│   │   │       │       ├── AccountsController.java
│   │   │       │       └── ServersController.java
│   │   │       └── util/
│   │   │           ├── GameLauncher.java
│   │   │           ├── DownloadManager.java
│   │   │           ├── VersionManager.java
│   │   │           └── PerformanceMonitor.java
│   │   └── resources/
│   │       └── logback.xml
│   └── test/
├── pom.xml
├── LICENSE
├── .gitignore
└── README.md
```

## Configuration

XK Client stores configuration in `~/.xkclient/`:
- `instances.json` - Game instance configurations
- `accounts.json` - Saved Minecraft accounts
- `servers.json` - Favorite servers
- `settings.json` - Launcher preferences

## Features Overview

### Game Instances
- Create multiple isolated game environments
- Customize JVM memory, CPU cores, and arguments
- Save instance-specific mod configurations
- View last played time and creation date

### Accounts
- Store multiple Minecraft accounts
- Support for both online and offline modes
- UUID and email storage
- Quick account switching

### Servers
- Save favorite servers
- Quick-connect with one click
- Track join count and last played time
- Server descriptions and port customization

### Utilities
- **GameLauncher** - Launch Minecraft with custom settings
- **DownloadManager** - Download game versions and mods
- **VersionManager** - Manage installed Minecraft versions
- **PerformanceMonitor** - Monitor system performance

## Usage

### Creating an Instance
1. Go to the "Instances" tab
2. Enter an instance name
3. Select Minecraft version
4. Click "Create Instance"
5. Customize JVM settings (optional)

### Adding an Account
1. Go to the "Accounts" tab
2. Enter username, email, and UUID
3. Select online or offline mode
4. Click "Add Account"

### Managing Servers
1. Go to the "Servers" tab
2. Enter server name, address, and port
3. Mark as favorite (optional)
4. Click "Add Server"

### Launching the Game
1. Select an instance
2. Choose an account
3. Select a server (optional)
4. Click "Play"

## API Reference

### ConfigService
```java
ConfigService config = new ConfigService();
List<GameInstance> instances = config.loadInstances();
config.saveInstances(instances);
```

### InstanceService
```java
InstanceService instanceService = new InstanceService(config);
GameInstance instance = instanceService.createInstance("MyInstance", "1.20.1");
instanceService.addMod("MyInstance", "modId");
```

### GameLauncher
```java
Process game = GameLauncher.launchGame(
    instancePath, version, javaPath,
    maxMemory, minMemory, jvmArgs,
    username, uuid
);
```

### DownloadManager
```java
DownloadManager.downloadFile(url, destinationPath);
DownloadManager.downloadFileWithProgress(url, path, (downloaded, total) -> {
    System.out.println("Progress: " + (downloaded * 100 / total) + "%");
});
```

## Development

### Technologies Used
- **JavaFX 21** - Modern UI framework
- **Maven** - Build automation
- **Gson** - JSON processing
- **Apache HTTP Client** - Network operations
- **SLF4J + Logback** - Logging

### Building from Source
```bash
mvn clean compile
mvn javafx:run
```

### Running Tests
```bash
mvn test
```

### Building Executable JAR
```bash
mvn package
```

## Contributing

Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## Roadmap

- [ ] Web UI for remote management
- [ ] Mod marketplace integration
- [ ] Auto-update system
- [ ] Multiplayer game sync
- [ ] Plugin system for extensions
- [ ] Performance optimization
- [ ] Dark mode theme

## License

MIT License - See LICENSE file for details

## Support

For issues, questions, or suggestions, please open a GitHub issue.

## Disclaimer

This launcher is not affiliated with Mojang Studios or Microsoft. Minecraft is a trademark of Microsoft Corporation.
