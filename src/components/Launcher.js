import React, { useState, useEffect } from 'react';
import { FaHome, FaGamepad, FaUsers, FaCog, FaPlay, FaTrash, FaClock, FaCloudDownloadAlt } from 'react-icons/fa';
import './Launcher.css';

function Launcher({ activeNav, onNavigate, updateStatus }) {
  const [versions, setVersions] = useState([]);
  const [mods, setMods] = useState([]);
  const [selectedVersion, setSelectedVersion] = useState('1.20.1');
  const [username, setUsername] = useState('Player');
  const [ramMin, setRamMin] = useState(2);
  const [ramMax, setRamMax] = useState(8);
  const [enabledMods, setEnabledMods] = useState({});
  const [output, setOutput] = useState([]);
  const [isLaunching, setIsLaunching] = useState(false);
  const [showConsole, setShowConsole] = useState(false);

  useEffect(() => {
    if (window.api) {
      window.api.getVersions().then(v => {
        setVersions(v);
        if (v.length > 0) {
          setSelectedVersion(v[0]);
        }
      });

      window.api.onMinecraftOutput((data) => {
        setOutput(prev => [...prev, data]);
      });
    }
  }, []);

  useEffect(() => {
    if (window.api && selectedVersion) {
      window.api.getMods(selectedVersion).then(m => {
        setMods(m);
        const modsObj = {};
        m.forEach(mod => {
          modsObj[mod.name] = true;
        });
        setEnabledMods(modsObj);
      });
    }
  }, [selectedVersion]);

  const handleLaunch = async () => {
    if (!window.api) return;
    
    setIsLaunching(true);
    setOutput([]);
    setShowConsole(true);

    const homeDir = await window.api.getHomeDir();
    const config = {
      version: selectedVersion,
      username: username,
      ramMin: ramMin,
      ramMax: ramMax,
      mods: Object.keys(enabledMods).filter(m => enabledMods[m]),
      gameDir: `${homeDir}/.xkclient/game`
    };

    try {
      await window.api.launchMinecraft(config);
    } catch (err) {
      setOutput(prev => [...prev, `Error: ${err.message}`]);
    } finally {
      setIsLaunching(false);
    }
  };

  const toggleMod = (modName) => {
    setEnabledMods(prev => ({
      ...prev,
      [modName]: !prev[modName]
    }));
  };

  const navItems = [
    { id: 'home', label: 'Home', icon: FaHome },
    { id: 'instances', label: 'Instances', icon: FaGamepad },
    { id: 'downloads', label: 'Downloads', icon: FaCloudDownloadAlt },
    { id: 'friends', label: 'Friends', icon: FaUsers },
    { id: 'settings', label: 'Settings', icon: FaCog },
  ];

  return (
    <div className="launcher">
      <div className="launcher-wrapper">
        <div className="launcher-sidebar">
          <div className="sidebar-header">
            <div className="launcher-logo">XK</div>
          </div>

          <nav className="sidebar-nav">
            {navItems.map(item => {
              const IconComponent = item.icon;
              return (
                <button
                  key={item.id}
                  className={`nav-item ${activeNav === item.id ? 'active' : ''}`}
                  onClick={() => onNavigate(item.id)}
                  title={item.label}
                >
                  <IconComponent size={20} />
                  <span className="nav-label">{item.label}</span>
                </button>
              );
            })}
          </nav>

          {/* Profile Section */}
          <div className="sidebar-profile">
            <div className="profile-avatar">{username.charAt(0).toUpperCase()}</div>
            <div className="profile-info">
              <p className="profile-username">{username}</p>
              <p className="profile-status">Ready to play</p>
            </div>
          </div>
        </div>

        {/* Main Content */}
        <div className="launcher-content">
          {/* Home View */}
          {activeNav === 'home' && (
            <div className="content-section home-section">
              <div className="content-header space-between">
                <div>
                  <h1>Welcome Back</h1>
                  <p className="content-subtitle">Ready to play? Launch your game below.</p>
                </div>
                <button
                  className="secondary-btn"
                  onClick={() => setShowConsole((prev) => !prev)}
                >
                  {showConsole ? 'Hide Console' : 'Show Console'}
                </button>
              </div>

              {/* Featured Instance Card */}
              <div className="featured-instance">
                <div className="instance-banner">
                  <div className="banner-gradient"></div>
                  <div className="instance-title">
                    <h2>Minecraft</h2>
                    <p className="instance-version">{selectedVersion}</p>
                  </div>
                </div>

                <div className="instance-details">
                  <div className="detail-row">
                    <label>Player Name</label>
                    <input 
                      type="text" 
                      value={username}
                      onChange={(e) => setUsername(e.target.value)}
                      disabled={isLaunching}
                      className="input-field"
                    />
                  </div>

                  <div className="detail-row">
                    <label>Version</label>
                    <select 
                      value={selectedVersion} 
                      onChange={(e) => setSelectedVersion(e.target.value)}
                      disabled={isLaunching}
                      className="input-field"
                    >
                      {versions.map(v => (
                        <option key={v} value={v}>{v}</option>
                      ))}
                    </select>
                  </div>

                  <div className="ram-settings">
                    <div className="detail-row">
                      <label>Min RAM: {ramMin}GB</label>
                      <input 
                        type="range" 
                        min="1" 
                        max="8" 
                        value={ramMin}
                        onChange={(e) => setRamMin(parseInt(e.target.value))}
                        disabled={isLaunching}
                        className="range-input"
                      />
                    </div>
                    <div className="detail-row">
                      <label>Max RAM: {ramMax}GB</label>
                      <input 
                        type="range" 
                        min={ramMin} 
                        max="16" 
                        value={ramMax}
                        onChange={(e) => setRamMax(parseInt(e.target.value))}
                        disabled={isLaunching}
                        className="range-input"
                      />
                    </div>
                  </div>

                  <button 
                    className={`launch-btn-large ${isLaunching ? 'launching' : ''}`}
                    onClick={handleLaunch}
                    disabled={isLaunching}
                  >
                    <FaPlay /> {isLaunching ? 'LAUNCHING...' : 'LAUNCH GAME'}
                  </button>
                </div>
              </div>

              {/* Mods Section */}
              {mods.length > 0 && (
                <div className="mods-section">
                  <h3>Installed Mods ({Object.values(enabledMods).filter(Boolean).length}/{mods.length})</h3>
                  <div className="mods-grid">
                    {mods.map(mod => (
                      <div key={mod.name} className="mod-card">
                        <input 
                          type="checkbox"
                          checked={enabledMods[mod.name] || false}
                          onChange={() => toggleMod(mod.name)}
                          disabled={isLaunching}
                          id={`mod-${mod.name}`}
                        />
                        <label htmlFor={`mod-${mod.name}`}>{mod.name}</label>
                      </div>
                    ))}
                  </div>
                </div>
              )}

              {/* Console */}
              {showConsole && (
                <div className="console-section">
                  <div className="console-header">
                    <h3><FaClock size={16} /> Console Output</h3>
                    <button 
                      className="console-clear-btn"
                      onClick={() => setOutput([])}
                    >
                      <FaTrash /> Clear
                    </button>
                  </div>
                  <div className="console-output">
                    {output.length === 0 ? (
                      <p className="console-empty">Waiting for output...</p>
                    ) : (
                      output.map((line, idx) => (
                        <div key={idx} className="console-line">{line}</div>
                      ))
                    )}
                  </div>
                </div>
              )}
            </div>
          )}

          {/* Instances View */}
          {activeNav === 'instances' && (
            <div className="content-section instances-section">
              <div className="content-header">
                <h1>Game Instances</h1>
                <p className="content-subtitle">Manage and launch your game profiles</p>
              </div>
              <div className="instances-grid">
                <div className="instance-card">
                  <div className="card-image">Vanilla</div>
                  <h3>Vanilla 1.20.1</h3>
                  <p className="card-version">{selectedVersion}</p>
                  <button className="card-btn" onClick={handleLaunch} disabled={isLaunching}>
                    <FaPlay /> Play
                  </button>
                </div>
              </div>
            </div>
          )}

          {/* Downloads View */}
          {activeNav === 'downloads' && (
            <div className="content-section downloads-section">
              <div className="content-header">
                <h1>Downloads</h1>
                <p className="content-subtitle">Auto-update and asset downloads.</p>
              </div>
              <div className="download-card">
                <div>
                  <span className="download-pill">Auto updates</span>
                  <p>Whenever a new launcher update is available, it will download in the background and notify you automatically.</p>
                </div>
                <div className="download-status">{updateStatus}</div>
              </div>
              <div className="empty-state small">
                <p>No downloads are active right now. Updates will appear here when available.</p>
              </div>
            </div>
          )}

          {/* Friends View */}
          {activeNav === 'friends' && (
            <div className="content-section friends-section">
              <div className="content-header">
                <h1>Friends</h1>
                <p className="content-subtitle">Connect with your friends</p>
              </div>
              <div className="empty-state">
                <FaUsers size={48} />
                <p>No friends yet. Add friends to see them here!</p>
              </div>
            </div>
          )}

          {/* Settings View */}
          {activeNav === 'settings' && (
            <div className="content-section settings-section">
              <div className="content-header">
                <h1>Settings</h1>
                <p className="content-subtitle">Configure your launcher preferences</p>
              </div>
              <div className="settings-panel">
                <div className="setting-group">
                  <label>Username</label>
                  <input 
                    type="text" 
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    className="input-field"
                  />
                </div>
                <div className="setting-group">
                  <label>Theme</label>
                  <select className="input-field">
                    <option>Dark (Default)</option>
                    <option>Light</option>
                  </select>
                </div>
                <div className="setting-group">
                  <label>Console Visibility</label>
                  <label className="checkbox-label">
                    <input 
                      type="checkbox" 
                      checked={showConsole}
                      onChange={(e) => setShowConsole(e.target.checked)}
                    />
                    Show console output
                  </label>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default Launcher;
