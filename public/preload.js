const { contextBridge, ipcRenderer } = require('electron');

// Expose protected methods that allow the renderer process to use
contextBridge.exposeInMainWorld('api', {
  getVersions: () => ipcRenderer.invoke('get-versions'),
  getMods: (version) => ipcRenderer.invoke('get-mods', version),
  launchMinecraft: (config) => ipcRenderer.invoke('launch-minecraft', config),
  getSystemInfo: () => ipcRenderer.invoke('get-system-info'),
  getHomeDir: () => ipcRenderer.invoke('get-home-dir'),
  checkForUpdates: () => ipcRenderer.invoke('check-for-updates'),
  restartAndInstall: () => ipcRenderer.invoke('restart-and-install'),
  onMinecraftOutput: (callback) => {
    ipcRenderer.on('minecraft-output', (event, data) => callback(data));
  },
  onUpdateStatus: (callback) => {
    ipcRenderer.on('update-status', (event, data) => callback(data));
  }
});
