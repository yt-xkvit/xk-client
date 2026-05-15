import React from 'react';
import { FaDownload, FaCog, FaHome, FaArrowCircleDown, FaSyncAlt } from 'react-icons/fa';
import './Navbar.css';

function Navbar({ systemInfo, updateStatus, updateDownloaded, onRestart }) {
  return (
    <nav className="navbar">
      <div className="navbar-left">
        <h1>🎮 xk-client-launcher</h1>
        <span className="version">Launcher</span>
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
        <div className="update-status">
          <FaSyncAlt /> {updateStatus}
        </div>
        {updateDownloaded && (
          <button className="update-btn" onClick={onRestart} title="Restart to apply update">
            <FaArrowCircleDown /> Restart
          </button>
        )}
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
