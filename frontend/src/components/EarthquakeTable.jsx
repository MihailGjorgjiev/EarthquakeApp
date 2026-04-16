import { useState } from "react";
import { MagBadge } from "./MagBadge";
import { formatTimestamp } from "./../utils/timestampUtils";
import styles from "./EarthquakeTable.module.css";

const COLUMNS = [
  { key: "magnitude", label: "Mag", sortable: true },
  { key: "place", label: "Location", sortable: false },
  { key: "timestamp", label: "Time", sortable: true },
  { key: "magType", label: "Type", sortable: false },
  { key: "actions", label: "", sortable: false },
];

export default function EarthquakeTable({ earthquakes, onDelete }) {
  const [sort, setSort] = useState({ key: "timestamp", dir: "desc" });

  const { key, dir } = sort;
  const order = (dir === "asc") ? 1 : -1;

  const sorted = [...earthquakes].sort((a, b) => {
    const valA = a[key]
    const valB = b[key];
    if (valA === valB) return 0;
    return valA > valB ? order : -order;
  });

  const toggleSort = (key) =>
    setSort((s) => ({
      key,
      dir: (s.key === key && s.dir === "asc") ? "desc" : "asc"
    }));


  const getSortArrow=(colKey)=>{
    if(sort.key !== colKey) return " ↕";
    return (sort.dir === "asc") ? " ↑" : " ↓";
  }

  return (
    <div className={styles.tableContainer}>
      <table className={styles.table}>
        <thead>
          <tr>
            {COLUMNS.map((col) => (
              <th
                key={col.key}
                className={`${styles.th} ${col.sortable ? styles.sortable : ""}`}
                onClick={() => col.sortable && toggleSort(col.key)}
              >
                {col.label}
                {col.sortable && (
                  <span className={styles.sortArrow}>
                    {getSortArrow(col.key)}
                  </span>
                )}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {sorted.map((earthquake) => (
            <tr
              key={earthquake.id}
              className={styles.row}
            >
              <td className={styles.td}><MagBadge magnitude={earthquake.magnitude} /></td>
              <td className={`${styles.td} ${styles.place}`}>{earthquake.place}</td>
              <td className={`${styles.td} ${styles.time}`}>{formatTimestamp(earthquake.timestamp)}</td>
              <td className={styles.td}>
                <span className={styles.magType}>{earthquake.magType}</span>
              </td>
              <td className={styles.td}>
                <button
                  className={styles.deleteBtn}
                  onClick={(e) => { e.stopPropagation(); onDelete(earthquake.id); }}
                  title="Delete"
                >
                  X
                </button>
              </td>
            </tr>
          ))}
          {sorted.length === 0 && (
            <tr>
              <td colSpan={6} className={styles.empty}>No earthquakes match your filters.</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}