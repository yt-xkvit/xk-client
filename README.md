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

## Requirements

- Java 11 or higher
- Maven 3.6+
- Minecraft Java Edition account

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
│   │   │       ├── service/
│   │   │       ├── ui/
│   │   │       └── util/
│   │   └── resources/
│   │       ├── styles/
│   │       └── config/
│   └── test/
├── pom.xml
└── README.md
```

## Configuration

XK Client stores configuration in `~/.xkclient/`:
- `instances.json` - Game instance configurations
- `accounts.json` - Saved Minecraft accounts
- `servers.json` - Favorite servers
- `settings.json` - Launcher preferences

## Usage

1. **Add Minecraft Version**: Download from the versions tab
2. **Create Instance**: Set up a new game instance with mods
3. **Add Account**: Login with your Minecraft account
4. **Launch Game**: Select instance and click Play

## Features Overview

### Game Instances
- Create multiple isolated game environments
- Customize JVM memory, CPU cores, and arguments
- Save instance-specific mod configurations

### Mod Management
- Add/remove mods to instances
- Automatic dependency resolution
- Support for both Forge and Fabric
- Mod metadata and version tracking

### Account Management
- Store multiple Minecraft accounts
- Secure token storage
- Quick account switching

### Server Management
- Save favorite servers
- Quick-connect with one click
- Server address and port customization

## Performance Monitoring

Monitor real-time performance metrics:
- FPS counter
- Memory usage
- CPU usage
- Chunk loading time

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

## License

MIT License - See LICENSE file for details

## Support

For issues, questions, or suggestions, please open a GitHub issue.

## Disclaimer

This launcher is not affiliated with Mojang Studios or Microsoft. Minecraft is a trademark of Microsoft Corporation.
