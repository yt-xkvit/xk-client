import React, { useState, useEffect } from 'react';
import { FaPlay, FaCog, FaTrash } from 'react-icons/fa';
import './Launcher.css';

function Launcher() {
  const [versions, setVersions] = useState([]);
  const [mods, setMods] = useState([]);
  const [selectedVersion, setSelectedVersion] = useState('1.20.1');
  const [username, setUsername] = useState('Player');
  const [ramMin, setRamMin] = useState(2);
  const [ramMax, setRamMax] = useState(4);
  const [enabledMods, setEnabledMods] = useState({});
  const [output, setOutput] = useState([]);
  const [isLaunching, setIsLaunching] = useState(false);

  useEffect(() => {
    if (window.api) {
      // Get versions
      window.api.getVersions().then(v => {
        setVersions(v);
        if (v.length > 0) {
          setSelectedVersion(v[0]);
        }
      });

      // Listen for minecraft output
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

    const config = {
      version: selectedVersion,
      username: username,
      ramMin: ramMin,
      ramMax: ramMax,
      mods: Object.keys(enabledMods).filter(m => enabledMods[m]),
      gameDir: `${require('os').homedir()}/.xkclient/game`
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

  return (
    <div className="launcher">
      <div className="launcher-container">
        <div className="launcher-config">
          <div className="config-section">
            <h2>🎮 Launcher Settings</h2>
            
            <div className="config-group">
              <label>Minecraft Version</label>
              <select 
                value={selectedVersion} 
                onChange={(e) => setSelectedVersion(e.target.value)}
                disabled={isLaunching}
              >
                {versions.map(v => (
                  <option key={v} value={v}>{v}</option>
                ))}
              </select>
            </div>

            <div className="config-group">
              <label>Username</label>
              <input 
                type="text" 
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                disabled={isLaunching}
                placeholder="Your Minecraft username"
              />
            </div>

            <div className="ram-config">
              <div className="config-group">
                <label>Min RAM: {ramMin} GB</label>
                <input 
                  type="range" 
                  min="1" 
                  max="8" 
                  value={ramMin}
                  onChange={(e) => setRamMin(parseInt(e.target.value))}
                  disabled={isLaunching}
                />
              </div>
              <div className="config-group">
                <label>Max RAM: {ramMax} GB</label>
                <input 
                  type="range" 
                  min={ramMin} 
                  max="16" 
                  value={ramMax}
                  onChange={(e) => setRamMax(parseInt(e.target.value))}
                  disabled={isLaunching}
                />
              </div>
            </div>

            <button 
              className="launch-btn"
              onClick={handleLaunch}
              disabled={isLaunching}
            >
              <FaPlay /> {isLaunching ? 'LAUNCHING...' : 'LAUNCH GAME'}
            </button>
          </div>

          <div className="config-section">
            <h2>🧩 Mods ({Object.values(enabledMods).filter(Boolean).length}/{mods.length})</h2>
            <div className="mods-list">
              {mods.length === 0 ? (
                <p className="empty-mods">No mods installed</p>
              ) : (
                mods.map(mod => (
                  <div key={mod.name} className="mod-item">
                    <input 
                      type="checkbox"
                      checked={enabledMods[mod.name] || false}
                      onChange={() => toggleMod(mod.name)}
                      disabled={isLaunching}
                      id={`mod-${mod.name}`}
                    />
                    <label htmlFor={`mod-${mod.name}`}>{mod.name}</label>
                  </div>
                ))
              )}
            </div>
          </div>
        </div>

        <div className="launcher-console">
          <div className="console-header">
            <h3>📋 Console Output</h3>
            <button 
              className="clear-btn"
              onClick={() => setOutput([])}
              title="Clear console"
            >
              <FaTrash /> Clear
            </button>
          </div>
          <div className="console-output">
            {output.length === 0 ? (
              <p className="console-empty">Console output will appear here...</p>
            ) : (
              output.map((line, idx) => (
                <div key={idx} className="console-line">
                  {line}
                </div>
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Launcher;
