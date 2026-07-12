import { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import "./AdminDashboard.css";

function AdminDashboard() {
    const [vehicles, setVehicles] = useState([]);
    const [search, setSearch] = useState({ make: "", model: "", category: "", minPrice: "", maxPrice: "" });
    const [message, setMessage] = useState("");
    const [restockQuantities, setRestockQuantities] = useState({});
    const navigate = useNavigate();

    const authConfig = { headers: { Authorization: `Bearer ${localStorage.getItem("token")}` } };

    const loadVehicles = useCallback(async () => {
        try {
            const response = await api.get("/vehicles", { headers: { Authorization: `Bearer ${localStorage.getItem("token")}` } });
            setVehicles(response.data);
        } catch (error) {
            setMessage(error.response?.data?.message || "Unable to connect to server.");
        }
    }, []);

    useEffect(() => { loadVehicles(); }, [loadVehicles]);

    const handleSearchChange = (event) => setSearch({ ...search, [event.target.name]: event.target.value });

    const handleSearch = async (event) => {
        event.preventDefault();
        try {
            const params = {};
            Object.entries(search).forEach(([key, value]) => {
                if (String(value).trim()) params[key] = String(value).trim();
            });
            const response = await api.get("/vehicles/search", { params, ...authConfig });
            setVehicles(response.data);
            setMessage("");
        } catch (error) {
            setMessage(error.response?.data?.message || "Unable to connect to server.");
        }
    };

    const handleDeleteVehicle = async (id) => {
        if (!window.confirm("Are you sure you want to delete this vehicle?")) return;
        try {
            await api.delete(`/vehicles/${id}`, authConfig);
            setMessage("Vehicle deleted successfully.");
            loadVehicles();
        } catch (error) {
            setMessage(error.response?.data?.message || "Unable to connect to server.");
        }
    };

    const handleRestock = async (id) => {
        const quantity = restockQuantities[id];
        if (!quantity || Number(quantity) <= 0) {
            setMessage("Enter a valid restock quantity.");
            return;
        }
        try {
            await api.post(`/vehicles/${id}/restock?quantity=${quantity}`, {}, authConfig);
            setRestockQuantities({ ...restockQuantities, [id]: "" });
            setMessage("Vehicle restocked successfully.");
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
        <main className="admin-page">
            <header className="admin-header">
                <div>
                    <p className="page-label">Administration</p>
                    <h1>Inventory Management</h1>
                    <p className="header-description">Manage vehicle availability and inventory details.</p>
                </div>
                <div className="header-actions">
                    <button className="add-button" onClick={() => navigate("/vehicles/add")}>+ Add Vehicle</button>
                    <button className="logout-button" onClick={handleLogout}>Logout</button>
                </div>
            </header>

            <section className="admin-search-section">
                <div className="section-title">
                    <h2>Search Vehicles</h2>
                    <button className="clear-button" type="button" onClick={() => { setSearch({ make: "", model: "", category: "", minPrice: "", maxPrice: "" }); loadVehicles(); }}>Clear filters</button>
                </div>
                <form className="admin-search-form" onSubmit={handleSearch}>
                    <input type="text" name="make" placeholder="Make" value={search.make} onChange={handleSearchChange} />
                    <input type="text" name="model" placeholder="Model" value={search.model} onChange={handleSearchChange} />
                    <input type="text" name="category" placeholder="Category" value={search.category} onChange={handleSearchChange} />
                    <input type="number" name="minPrice" placeholder="Min price" value={search.minPrice} onChange={handleSearchChange} min="0" />
                    <input type="number" name="maxPrice" placeholder="Max price" value={search.maxPrice} onChange={handleSearchChange} min="0" />
                    <button type="submit">Search</button>
                </form>
            </section>

            {message && <p className="admin-message">{message}</p>}

            <section className="admin-inventory-section">
                <div className="inventory-heading"><h2>All Vehicles</h2><span>{vehicles.length} vehicle{vehicles.length !== 1 ? "s" : ""} found</span></div>
                {vehicles.length === 0 ? (
                    <div className="empty-state"><h3>No vehicles found</h3><p>Add a vehicle or change the search filters.</p></div>
                ) : (
                    <div className="admin-vehicle-grid">
                        {vehicles.map((vehicle) => (
                            <article className="admin-vehicle-card" key={vehicle.id}>
                                <div className="admin-card-top"><span className="vehicle-type">{vehicle.category || "Vehicle"}</span><span className={vehicle.quantity > 0 ? "stock-badge available" : "stock-badge unavailable"}>{vehicle.quantity > 0 ? `${vehicle.quantity} in stock` : "Out of stock"}</span></div>
                                <h3>{vehicle.make} {vehicle.model}</h3>
                                <p className="vehicle-id">Vehicle ID: {vehicle.id}</p>
                                <p className="vehicle-price">₹{Number(vehicle.price || 0).toLocaleString("en-IN")}</p>
                                <div className="admin-card-actions">
                                    <button className="edit-button" onClick={() => navigate(`/vehicles/edit/${vehicle.id}`)}>Edit</button>
                                    <button className="delete-button" onClick={() => handleDeleteVehicle(vehicle.id)}>Delete</button>
                                </div>
                                <div className="restock-box">
                                    <label htmlFor={`restock-${vehicle.id}`}>Restock quantity</label>
                                    <div><input id={`restock-${vehicle.id}`} type="number" min="1" placeholder="Qty" value={restockQuantities[vehicle.id] || ""} onChange={(event) => setRestockQuantities({ ...restockQuantities, [vehicle.id]: event.target.value })} /><button onClick={() => handleRestock(vehicle.id)}>Restock</button></div>
                                </div>
                            </article>
                        ))}
                    </div>
                )}
            </section>
        </main>
    );
}

export default AdminDashboard;
