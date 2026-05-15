const { app, BrowserWindow, ipcMain, Menu } = require('electron');
const path = require('path');
const isDev = require('electron-is-dev');
const { execFile } = require('child-process-promise');
const fs = require('fs').promises;
const os = require('os');

let mainWindow;

// Create window
function createWindow() {
  mainWindow = new BrowserWindow({
    width: 1200,
    height: 800,
    minWidth: 900,
    minHeight: 600,
    webPreferences: {
      nodeIntegration: false,
      contextIsolation: true,
      enableRemoteModule: false,
      preload: path.join(__dirname, 'preload.js')
    },
    icon: path.join(__dirname, '../build/icon.ico')
  });

  const startUrl = isDev
    ? 'http://localhost:3000'
    : `file://${path.join(__dirname, '../build/index.html')}`;

  mainWindow.loadURL(startUrl);

  if (isDev) {
    mainWindow.webContents.openDevTools();
  }

  mainWindow.on('closed', () => {
    mainWindow = null;
  });
}

app.on('ready', createWindow);

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit();
  }
});

app.on('activate', () => {
  if (mainWindow === null) {
    createWindow();
  }
});

// IPC Handlers
ipcMain.handle('get-versions', async () => {
  const versionsPath = path.join(os.homedir(), '.xkclient', 'versions');
  try {
    const versions = await fs.readdir(versionsPath);
    return versions.sort();
  } catch (err) {
    return ['1.20.1', '1.19.2', '1.16.5'];
  }
});

ipcMain.handle('get-home-dir', async () => {
  return os.homedir();
});

ipcMain.handle('get-mods', async (event, version) => {
  const modsPath = path.join(os.homedir(), '.xkclient', 'mods', version);
  try {
    const mods = await fs.readdir(modsPath);
    return mods.filter(f => f.endsWith('.jar')).map(f => ({
      name: f.replace('.jar', ''),
      file: f,
      enabled: true
    }));
  } catch (err) {
    return [];
  }
});

ipcMain.handle('launch-minecraft', async (event, config) => {
  try {
    const javaPath = process.platform === 'win32' ? 'javaw' : 'java';
    const args = [
      `-Xmx${config.ramMax}M`,
      `-Xms${config.ramMin}M`,
      '-XX:+UnlockExperimentalVMOptions',
      '-XX:+UseG1GC',
      '-XX:MaxGCPauseMillis=200',
      '-XX:+DisableExplicitGC',
      '-XX:+ParallelRefProcEnabled',
      '-XX:+AlwaysPreTouch',
      `-Duser.name=${config.username}`,
      `-Duser.home=${os.homedir()}`,
      `-Duser.timezone=UTC`,
      `--version-name=${config.version}`,
      `--game-dir=${config.gameDir}`,
      '--demo',
      'net.minecraft.client.main.Main'
    ];

    mainWindow.webContents.send('minecraft-output', 'Launching Minecraft...');

    const promise = execFile(javaPath, args);
    
    if (promise.childProcess) {
      promise.childProcess.stdout.on('data', (data) => {
        mainWindow.webContents.send('minecraft-output', data.toString());
      });
      promise.childProcess.stderr.on('data', (data) => {
        mainWindow.webContents.send('minecraft-output', data.toString());
      });
    }

    await promise;
    mainWindow.webContents.send('minecraft-output', 'Game closed');
    return { success: true };
  } catch (err) {
    mainWindow.webContents.send('minecraft-output', `Error: ${err.message}`);
    return { success: false, error: err.message };
  }
});

ipcMain.handle('get-system-info', async () => {
  return {
    platform: process.platform,
    arch: process.arch,
    cpus: os.cpus().length,
    totalMemory: Math.round(os.totalmem() / 1024 / 1024 / 1024),
    freeMemory: Math.round(os.freemem() / 1024 / 1024 / 1024)
  };
});

// Menu
const template = [
  {
    label: 'File',
    submenu: [
      {
        label: 'Exit',
        accelerator: 'CmdOrCtrl+Q',
        click: () => {
          app.quit();
        }
      }
    ]
  },
  {
    label: 'Edit',
    submenu: [
      { role: 'undo' },
      { role: 'redo' },
      { type: 'separator' },
      { role: 'cut' },
      { role: 'copy' },
      { role: 'paste' }
    ]
  },
  {
    label: 'Help',
    submenu: [
      {
        label: 'About',
        click: () => {
          // Show about dialog
        }
      }
    ]
  }
];

if (isDev) {
  template.push({
    label: 'Debug',
    submenu: [
      { role: 'reload' },
      { role: 'forceReload' },
      { role: 'toggleDevTools' }
    ]
  });
}

const menu = Menu.buildFromTemplate(template);
Menu.setApplicationMenu(menu);
