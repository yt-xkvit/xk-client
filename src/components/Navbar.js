import React from 'react';
import { FaDownload, FaCog, FaHome } from 'react-icons/fa';
import './Navbar.css';

function Navbar({ systemInfo }) {
  return (
    <nav className="navbar">
      <div className="navbar-left">
        <h1>🎮 XK Client</h1>
        <span className="version">v1.0.0</span>
      </div>
      
      <div className="navbar-center">
        {systemInfo && (
          <div className="system-info">
            <span>💾 {systemInfo.freeMemory}/{systemInfo.totalMemory} GB RAM</span>
            <span>⚙️ {systemInfo.cpus} Cores</span>
            <span>🖥️ {systemInfo.platform}</span>
          </div>
        )}
      </div>

      <div className="navbar-right">
        <button className="nav-btn" title="Home">
          <FaHome /> Home
        </button>
        <button className="nav-btn" title="Downloads">
          <FaDownload /> Downloads
        </button>
        <button className="nav-btn" title="Settings">
          <FaCog /> Settings
        </button>
      </div>
    </nav>
  );
}

export default Navbar;
