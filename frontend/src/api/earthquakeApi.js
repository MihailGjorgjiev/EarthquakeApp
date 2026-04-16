const BASE_URL = import.meta.env.VITE_EARTHQUAKE_API_BASE_URL;

async function handleResponse(result, message) {
  if (!result.ok) {
    throw new Error(`${message}: ${result.status}`);
  }
  return result.json();
}

export async function fetchAllEarthquakes() {
  const result = await fetch(BASE_URL);
  return handleResponse(result, "Failed to fetch earthquakes: " + result.status)
}

export async function fetchByMagnitude(magnitude) {
  const url = (magnitude != null) ? `${BASE_URL}/magnitude?magnitude=${magnitude}` : BASE_URL;
  const result = await fetch(url);
  return handleResponse(result, "Failed to fetch earthquakes by Magnitude: " + result.status)
}

export async function fetchByTimestamp(timestamp) {
  const url = (timestamp != null) ? `${BASE_URL}/timestamp?timestamp=${timestamp}` : BASE_URL;
  const result = await fetch(url);
  return handleResponse(result, "Failed to fetch earthquakes by Timestamp: " + result.status)
}

export async function deleteEarthquake(id) {
  const result = await fetch(`${BASE_URL}/${id}`, { method: "DELETE" });
  if (!result.ok) throw new Error("Failed to delete earthquake: " + result.status);
}