import styles from "./MagBadge.module.css";

export function MagBadge({ magnitude }) {
  return (
    
    <span className={styles.badge}>
      <span className={styles.dot} />
      {Number(magnitude).toFixed(1)}
    </span>
  );
}
