import styles from "./FilterBar.module.css";

const MAG_OPTIONS = [
  { label: "All", value: null },
  { label: "1+", value: 1 },
  { label: "2+", value: 2 },
  { label: "3+", value: 3 }
];

const TIME_OPTIONS = [
  { label: "All time", value: null },
  { label: "Last 5 min", value: 60 * 5 },
  { label: "Last 15 min", value: 60 * 15 },
  { label: "Last 30 min", value: 60 * 30 },
];

export default function FilterBar({ filters, setFilters, total}) {

  const isMagFilterActive = (value) =>
    filters.minMagnitude === value && filters.since === null;

  const isTimeFilterActive = (seconds) => {
    if (seconds === null) {
      return filters.since === null && filters.minMagnitude === null;
    }
    const since = new Date().getTime() - seconds * 1000;
    return filters.since === since;
  };

  const handleMag = (value) =>
    setFilters((f) => ({ ...f, minMagnitude: value,since:null }));

  const handleTime = (seconds) => {
    const since = seconds ? new Date().getTime()  - seconds*1000 : null;
    setFilters((f) => ({ ...f, since :since,minMagnitude:null}));
  };

  const filterButton = (options, isActive, onClick) =>
    options.map(({ label, value }) => (
      <button
        key={label}
        className={`${styles.pill} ${isActive(value) ? styles.active : ""}`}
        onClick={() => onClick(value)}
      >
        {label}
      </button>
    ));

  return (
    <div className={styles.bar}>
      <div className={styles.group}>
        <span className={styles.label}>Magnitude</span>
        <div className={styles.pills}>
          {filterButton(MAG_OPTIONS,isMagFilterActive,handleMag)}
        </div>
      </div>

      <div className={styles.group}>
        <span className={styles.label}>Time</span>
        <div className={styles.pills}>
          {filterButton(TIME_OPTIONS,isTimeFilterActive,handleTime)}
        </div>
      </div>

      <div className={styles.meta}>
        <span className={styles.count}>{total} events</span>
      </div>
    </div>
  );
}