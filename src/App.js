import React, { useState, useEffect } from 'react';
import Navbar from './components/Navbar';
import Launcher from './components/Launcher';
import './App.css';

function App() {
  const [systemInfo, setSystemInfo] = useState(null);
  const [updateStatus, setUpdateStatus] = useState('Checking for updates...');
  const [updateDownloaded, setUpdateDownloaded] = useState(false);
  const [activeNav, setActiveNav] = useState('home');

  useEffect(() => {
    if (window.api) {
      window.api.getSystemInfo().then((info) => {
        setSystemInfo(info);
      });

      window.api.onUpdateStatus((status) => {
        setUpdateStatus(status);
        setUpdateDownloaded(
          status.toLowerCase().includes('downloaded') ||
          status.toLowerCase().includes('install')
        );
      });

      window.api.checkForUpdates();
    }
  }, []);

  const handleRestart = () => {
    if (window.api) {
      window.api.restartAndInstall();
    }
  };

  return (
    <div className="app">
      <Navbar
        systemInfo={systemInfo}
        updateStatus={updateStatus}
        updateDownloaded={updateDownloaded}
        activeNav={activeNav}
        onNavigate={setActiveNav}
        onRestart={handleRestart}
      />
      <Launcher activeNav={activeNav} onNavigate={setActiveNav} updateStatus={updateStatus} />
    </div>
  );
}

export default App;
