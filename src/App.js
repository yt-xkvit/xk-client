import React, { useState, useEffect } from 'react';
import Navbar from './components/Navbar';
import Launcher from './components/Launcher';
import './App.css';

function App() {
  const [systemInfo, setSystemInfo] = useState(null);

  useEffect(() => {
    if (window.api) {
      window.api.getSystemInfo().then(info => {
        setSystemInfo(info);
      });
    }
  }, []);

  return (
    <div className="app">
      <Navbar systemInfo={systemInfo} />
      <Launcher />
    </div>
  );
}

export default App;
