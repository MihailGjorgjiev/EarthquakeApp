import { useState } from "react";
import { useEarthquakes } from "./hooks/useEarthquakes";
import EarthquakeTable from "./components/EarthquakeTable";
import EarthquakeMap from "./components/EarthquakeMap";
import FilterBar from "./components/FilterBar";
import "./App.css";

const VIEWS = {
  table: { label: "Table", showMap: false, showTable: true },
  map: { label: "Map", showMap: true, showTable: false },
  both: { label: "Both", showMap: true, showTable: true },
};

export default function App() {
  const { earthquakes, error, filters, setFilters, remove } = useEarthquakes();
  const [view, setView] = useState("both"); // "table" | "map" | "both"

  const config=VIEWS[view];


  return (
    <div className="app">
      <header className="header">
        <div className="headerLeft">
          
          <div className="subtitle">Real-Time Earthquake Monitoring App</div>
        </div>

        <div className="viewToggle">
          {Object.entries(VIEWS).map(([key, v]) => (
            <button
              key={key}
              className={`viewBtn ${view === key ? "viewBtnActive" : ""}`}
              onClick={() => setView(key)}
            >
              {v.label}
            </button>
          ))}
        </div>
      </header>

      <main className="main">
        {error && (
          <div className="errorBanner">
            ⚠ Could not connect to backend: {error}
          </div>
        )}

        <FilterBar
          filters={filters}
          setFilters={setFilters}
          total={earthquakes.length}
        />

        <div className={"contentArea"}>
          <div className="primaryContent">
            {config.showMap && (
              <div className="section">
                <EarthquakeMap earthquakes={earthquakes} />
              </div>
            )}

            {config.showTable && (
              <div className="section">
                
                <EarthquakeTable
                  earthquakes={earthquakes}
                  onDelete={remove}
                />
              </div>
            )}
          </div>

        </div>
      </main>
    </div>
  );
}