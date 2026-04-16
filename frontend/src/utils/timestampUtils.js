export function formatTimestamp(ts) {
  return new Intl.DateTimeFormat("it-IT", {
    year: "numeric", month: "short", day: "2-digit",
    hour: "2-digit", minute: "2-digit", second: "2-digit",
  }).format(new Date(ts));
}