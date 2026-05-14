# XK Client - Minecraft Launcher

A modern, fast Electron-based Minecraft launcher built with React and Node.js. Similar to Lunar Client with a sleek dark UI and cyan/green accents.

## 🎮 Features

✅ **Modern Electron UI** - Built with React for smooth performance  
✅ **Multi-Version Support** - Manage multiple Minecraft versions  
✅ **Mod Manager** - Enable/disable mods per version  
✅ **RAM Allocation** - Slider controls from 1-16 GB  
✅ **Real-time Console** - Live game output streaming  
✅ **System Monitoring** - Display RAM, CPU, and OS info  
✅ **Lunar Client Design** - Beautiful cyan/green dark theme  
✅ **Cross-Platform** - Works on Windows, macOS, and Linux  
✅ **Windows Installer** - Auto-builds NSIS .exe  
✅ **Fast Startup** - Instant launch compared to Java

## 📋 Requirements

- Node.js >= 14.0.0
- npm or yarn
- Java 11+ (for running Minecraft)

## 🚀 Installation & Setup

### Clone Repository
```bash
git clone https://github.com/yt-xkvit/xk-client.git
cd xk-client
```

### Install Dependencies
```bash
npm install
```

### Development Mode
```bash
npm run electron-dev
```
This starts React dev server + Electron with hot reload.

### Build for Production
```bash
# Windows EXE
npm run dist

# macOS DMG
npm run electron-build -- --mac

# Linux AppImage
npm run electron-build -- --linux
```

## 📁 Project Structure

```
xk-client/
├── public/
│   ├── electron.js          # Electron main process
│   ├── preload.js           # IPC security bridge
│   └── index.html           # HTML entry
├── src/
│   ├── components/
│   │   ├── Launcher.js      # Main launcher UI
│   │   ├── Launcher.css     # Launcher styles
│   │   ├── Navbar.js        # Top navigation
│   │   └── Navbar.css       # Navbar styles
│   ├── App.js               # Root component
│   ├── App.css              # Global styles
│   └── index.js             # React entry
├── package.json
└── README.md
```

## ⚙️ Configuration

XK Client stores config in `~/.xkclient/`:
- `versions/` - Minecraft versions
- `mods/` - Game mods (organized by version)
- `game/` - Game directory

## 🎯 Features Breakdown

### Launcher Settings
- **Version Selector** - Choose from installed Minecraft versions
- **Username** - Set player username (max 16 characters)
- **Min RAM** - Minimum heap memory (1-8 GB)
- **Max RAM** - Maximum heap memory (2-16 GB)
- **Launch Button** - Start Minecraft with current config

### Mod Manager
- **Mod List** - Shows all installed mods for selected version
- **Checkboxes** - Enable/disable individual mods
- **Mod Counter** - Display active mods vs total

### Console Output
- **Real-time Logs** - Stream game output as it runs
- **Auto-scroll** - Automatically scrolls to latest message
- **Clear Button** - Wipe console history

### System Info Bar
- **RAM Display** - Free/Total memory in GB
- **CPU Cores** - Number of processor cores
- **Platform** - Operating system (Windows/macOS/Linux)

## 🎨 UI Theme

Modern dark theme inspired by Lunar Client:
- **Primary Background** - #1a1a1a (very dark gray)
- **Accent Color** - #00ff88 (bright cyan)
- **Secondary Accent** - #00ccff (light cyan)
- **Text Primary** - #ffffff (white)

## 🔧 Development

### Technologies Used
- **Electron** - Desktop framework
- **React 18** - UI library
- **Node.js** - Backend runtime
- **Electron Builder** - Packaging & distribution

### Available Scripts

| Script | Description |
|--------|-------------|
| `npm run electron-dev` | Start development mode |
| `npm run dist` | Build Windows installer |
| `npm run dist-all` | Build all platforms |

## 📦 Building Installers

### Windows NSIS Installer
Builds an installer with start menu and desktop shortcuts.

Located in: `dist/XK Client Setup 1.0.0.exe`

## 🤝 Contributing

Contributions welcome! Please:
1. Fork repository
2. Create feature branch (`git checkout -b feature/NewFeature`)
3. Commit changes (`git commit -m 'Add NewFeature'`)
4. Push to branch (`git push origin feature/NewFeature`)
5. Open Pull Request

## 📄 License

MIT License - See LICENSE file

## 💬 Support

Issues, questions, or suggestions? Open a GitHub issue!

---

**XK Client v1.0.0** - Your modern Minecraft launcher! 🚀
