import { useEffect, useState } from "react";
import api from "../api/api";
import { useNavigate } from "react-router-dom";
import "./Dashboard.css";

function Dashboard() {
    const [vehicles, setVehicles] = useState([]);
    const [search, setSearch] = useState({ make: "", model: "", category: "", minPrice: "", maxPrice: "" });
    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    useEffect(() => { loadVehicles(); }, []);

    const loadVehicles = async () => {
        try {
            const response = await api.get("/vehicles", { headers: { Authorization: `Bearer ${localStorage.getItem("token")}` } });
            setVehicles(response.data);
            setMessage("");
        } catch (error) {
            setMessage(error.response?.data?.message || "Unable to connect to server.");
        }
    };

    const handleSearchChange = (event) => setSearch({ ...search, [event.target.name]: event.target.value });

    const handleSearch = async (event) => {
        event.preventDefault();
        try {
            const params = {};
            Object.entries(search).forEach(([key, value]) => {
                if (String(value).trim()) params[key] = String(value).trim();
            });
            const response = await api.get("/vehicles/search", { params, headers: { Authorization: `Bearer ${localStorage.getItem("token")}` } });
            setVehicles(response.data);
            setMessage("");
        } catch (error) {
            setMessage(error.response?.data?.message || "Unable to connect to server.");
        }
    };

    const purchaseVehicle = async (id) => {
        try {
            await api.post(`/vehicles/${id}/purchase`, {}, { headers: { Authorization: `Bearer ${localStorage.getItem("token")}` } });
            setMessage("Vehicle purchased successfully.");
            loadVehicles();
        } catch (error) {
            setMessage(error.response?.data?.message || "Unable to connect to server.");
        }
    };

    const handleLogout = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("role");
        navigate("/login");
    };

    return (
        <main className="dashboard-page">
            <header className="dashboard-header">
                <div>
                    <p className="page-label">Vehicle Inventory</p>
                    <h1>Available Vehicles</h1>
                    <p className="header-description">Browse available vehicles and make a purchase.</p>
                </div>
                <button className="logout-button" onClick={handleLogout}>Logout</button>
            </header>

            <section className="search-section">
                <div className="section-title">
                    <h2>Search Vehicles</h2>
                    <button className="clear-button" type="button" onClick={() => { setSearch({ make: "", model: "", category: "", minPrice: "", maxPrice: "" }); loadVehicles(); }}>Clear filters</button>
                </div>
                <form className="search-form" onSubmit={handleSearch}>
                    <input type="text" name="make" placeholder="Make" value={search.make} onChange={handleSearchChange} />
                    <input type="text" name="model" placeholder="Model" value={search.model} onChange={handleSearchChange} />
                    <input type="text" name="category" placeholder="Category" value={search.category} onChange={handleSearchChange} />
                    <input type="number" name="minPrice" placeholder="Min price" value={search.minPrice} onChange={handleSearchChange} min="0" />
                    <input type="number" name="maxPrice" placeholder="Max price" value={search.maxPrice} onChange={handleSearchChange} min="0" />
                    <button type="submit">Search</button>
                </form>
            </section>

            {message && <p className="dashboard-message">{message}</p>}

            <section className="inventory-section">
                <div className="inventory-heading"><h2>Inventory</h2><span>{vehicles.length} vehicle{vehicles.length !== 1 ? "s" : ""} found</span></div>

                {vehicles.length === 0 ? (
                    <div className="empty-state"><h3>No vehicles found</h3><p>Try changing your search filters.</p></div>
                ) : (
                    <div className="vehicle-grid">
                        {vehicles.map((vehicle) => (
                            <article className="vehicle-card" key={vehicle.id}>
                                <div className="vehicle-image"><span>{vehicle.make?.charAt(0) || "V"}</span></div>
                                <div className="vehicle-content">
                                    <p className="vehicle-category">{vehicle.category || "Vehicle"}</p>
                                    <h3>{vehicle.make} {vehicle.model}</h3>
                                    <div className="vehicle-details">
                                        <span>Vehicle ID: {vehicle.id}</span>
                                        <span className={vehicle.quantity > 0 ? "in-stock" : "out-of-stock"}>{vehicle.quantity > 0 ? `${vehicle.quantity} in stock` : "Out of stock"}</span>
                                    </div>
                                    <div className="card-footer">
                                        <strong>₹{Number(vehicle.price || 0).toLocaleString("en-IN")}</strong>
                                        <button onClick={() => purchaseVehicle(vehicle.id)} disabled={vehicle.quantity === 0}>Purchase</button>
                                    </div>
                                </div>
                            </article>
                        ))}
                    </div>
                )}
            </section>
        </main>
    );
}

export default Dashboard;
