import React, { useState } from "react";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";

// Fix marker icon issue
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png",
  iconUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png",
  shadowUrl: "https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png",
});

export default function StudentMap() {
  const [address, setAddress] = useState("");
  const [position, setPosition] = useState([18.5204, 73.8567]); // Pune default
  const [showMap, setShowMap] = useState(false);

  const findLocation = async () => {
    if (!address) return;

    const url = "https://nominatim.openstreetmap.org/search?format=json&q=" + address;

    const response = await fetch(url);
    const data = await response.json();

    if (data.length === 0) {
      alert("Address not found");
      return;
    }

    const lat = parseFloat(data[0].lat);
    const lon = parseFloat(data[0].lon);

    setPosition([lat, lon]);
    setShowMap(true);
  };

  return (
    <div style={{ textAlign: "center", padding: "20px" }}>
      <h2>Student Location</h2>

      <input
        type="text"
        placeholder="Enter Student Address"
        value={address}
        onChange={(e) => setAddress(e.target.value)}
        style={{ padding: "10px", width: "300px" }}
      />

      <br />
      <br />

      <button onClick={findLocation}>Show Location</button>

      <br />
      <br />

      {showMap && (
        <MapContainer
          center={position}
          zoom={15}
          style={{ height: "500px", width: "80%", margin: "auto" }}
        >
          <TileLayer
            attribution="© OpenStreetMap contributors"
            url="https://tile.openstreetmap.org/{z}/{x}/{y}.png"
          />

          <Marker position={position}>
            <Popup>Student House</Popup>
          </Marker>
        </MapContainer>
      )}
    </div>
  );
}
