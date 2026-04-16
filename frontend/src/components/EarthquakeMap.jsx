import styles from "./EarthquakeMap.module.css";

const MAP_W = 950;
const MAP_H = 620;

function project(lat, lng) {
  const x = ((lng + 180) / 360) * MAP_W;
  const y = ((90 - lat) / 180) * MAP_H;
  return { x, y };
}

function magToRadius(mag) {
  const rad = Math.max(4, Math.min(22, Math.pow(Number(mag), 1.6) * 1.4));
  return rad ? rad : 0;
}

const MAP_URL =
  "/world_map_wikipedia.svg";

export default function EarthquakeMap({ earthquakes, onSelect, selected }) {

  return (
    <div className={styles.mapContainer}>

      <img
        src={MAP_URL}
        alt="World map"
        className={styles.mapImage}
        draggable={false}
      />
      <svg
        viewBox={`0 0 ${MAP_W} ${MAP_H}`}
        className={styles.markerOverlay}
        xmlns="http://www.w3.org/2000/svg"
      >
        <defs>
          {earthquakes.map((eq) => (
            <radialGradient key={`grad-${eq.id}`} id={`mg-${eq.id}`} cx="40%" cy="35%" r="60%">
              <stop offset="0%" stopColor="var(--earthquake-color)" stopOpacity="0.9" />
              <stop offset="100%" stopColor="var(--earthquake-color)" stopOpacity="0.25" />
            </radialGradient>
          ))}
        </defs>

        {[...earthquakes]
          .sort((a, b) => Number(a.magnitude) - Number(b.magnitude))
          .map((eq) => {
            const { x, y } = project(Number(eq.latitude), Number(eq.longitude));
            const r = magToRadius(eq.magnitude);
            const color = "var(--earthquake-color)";
            const isSelected = selected?.id === eq.id;
            return (
              <g key={eq.id} onClick={() => onSelect(eq)} style={{ cursor: "pointer" }}>

                <circle cx={x} cy={y} r={r * 1.8} fill="none"
                  stroke={color} strokeWidth={0.8} opacity={isSelected ? 0.5 : 0.2}
                  className={isSelected ? styles.pulseFast : styles.pulse}
                />

                <circle cx={x} cy={y} r={r * 1.25} fill={color} opacity={0.12} />

                <circle cx={x} cy={y} r={r}
                  fill={`url(#mg-${eq.id})`}
                  stroke={color}
                  strokeWidth={isSelected ? 1.5 : 0.8}
                  opacity={isSelected ? 1 : 0.85}
                  className={styles.marker}
                />

                {Number(eq.magnitude) >= 5 && (
                  <text x={x} y={y + r + 10} textAnchor="middle"
                    fontSize="9" fill={color} opacity={0.9}
                    fontFamily="'Space Mono', monospace" fontWeight="700">
                    {Number(eq.magnitude).toFixed(1)}
                  </text>
                )}
              </g>
            );
          })}
      </svg>
    </div>
  );
}