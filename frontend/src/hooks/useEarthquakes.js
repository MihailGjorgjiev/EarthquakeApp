import { useState, useEffect, useCallback} from "react";
import { fetchAllEarthquakes, fetchByMagnitude, fetchByTimestamp, deleteEarthquake } from "../api/earthquakeApi";

export function useEarthquakes({ pollingInterval = 10000 } = {}) {
  const [earthquakes, setEarthquakes] = useState([]);
  const [error, setError] = useState(null);
  const [filters, setFilters] = useState({ minMagnitude: null, since: null });

  const load = useCallback(async () => {
    try {
      let data;
      if (filters.minMagnitude != null) {
        data = await fetchByMagnitude(filters.minMagnitude);
      } else if (filters.since != null) {
        data = await fetchByTimestamp(filters.since);
      } else {
        data = await fetchAllEarthquakes();
      }
      setEarthquakes(data);
      setError(null);
    } catch (err) {
      setError(err.message);
    }
  }, [filters]);


  useEffect(() => {
    setTimeout(load,0);    
    const id = setInterval(load, pollingInterval);
    return () => clearInterval(id);
  }, [load, pollingInterval]);

  const remove = useCallback(async (id) => {
    await deleteEarthquake(id);
    setEarthquakes((prev) => prev.filter((e) => e.id !== id));
  }, []);

  return { earthquakes, error,  filters, setFilters, remove, refresh: load };
}