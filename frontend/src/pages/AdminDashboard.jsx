import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom"; // Import useNavigate
import api from "../api/api";

function Dashboard() {
    const [vehicles, setVehicles] = useState([]);
    const [search, setSearch] = useState({
        make: "",
        model: "",
        category: "",
        minPrice: "",
        maxPrice: ""
    });
    const [message, setMessage] = useState("");
    const [restockQuantities, setRestockQuantities] = useState({});

    const navigate = useNavigate(); // Initialize navigation
    const role = localStorage.getItem("role");
    const isAdmin = role === "ADMIN";

    useEffect(() => {
        loadVehicles();
    }, []);

    const loadVehicles = async () => {
        try {
            const response = await api.get("/vehicles", {
                headers: { Authorization: `Bearer ${localStorage.getItem("token")}` }
            });
            setVehicles(response.data);
        } catch (error) {
            if (error.response) {
                setMessage(error.response.data.message);
            } else {
                setMessage("Unable to connect to server.");
            }
        }
    };

    const handleSearchChange = (e) => {
        setSearch({ ...search, [e.target.name]: e.target.value });
    };

    const handleSearch = async () => {
        try {
            const params = {};
            if (search.make.trim()) params.make = search.make.trim();
            if (search.model.trim()) params.model = search.model.trim();
            if (search.category.trim()) params.category = search.category.trim();
            if (search.minPrice) params.minPrice = search.minPrice;
            if (search.maxPrice) params.maxPrice = search.maxPrice;

            const response = await api.get("/vehicles/search", {
                params,
                headers: { Authorization: `Bearer ${localStorage.getItem("token")}` }
            });
            setVehicles(response.data);
        } catch (error) {
            if (error.response) {
                setMessage(error.response.data.message);
            } else {
                setMessage("Unable to connect to server.");
            }
        }
    };

    const purchaseVehicle = async (id) => {
        try {
            await api.post(`/vehicles/${id}/purchase`, {}, {
                headers: { Authorization: `Bearer ${localStorage.getItem("token")}` }
            });
            setMessage("Vehicle purchased successfully.");
            loadVehicles();
        } catch (error) {
            if (error.response) {
                setMessage(error.response.data.message);
            } else {
                setMessage("Unable to connect to server.");
            }
        }
    };

    const handleDeleteVehicle = async (id) => {
        try {
            await api.delete(`/vehicles/${id}`, {
                headers: { Authorization: `Bearer ${localStorage.getItem("token")}` }
            });
            setMessage("Vehicle deleted successfully.");
            loadVehicles();
        } catch (error) {
            if (error.response) {
                setMessage(error.response.data.message);
            } else {
                setMessage("Unable to connect to server.");
            }
        }
    };

    const handleRestockQuantityChange = (id, value) => {
        setRestockQuantities({ ...restockQuantities, [id]: value });
    };

    const handleRestock = async (id) => {

        const qty = restockQuantities[id];

        if (!qty || parseInt(qty, 10) <= 0) {
            setMessage("Please enter a valid restock quantity.");
            return;
        }

        try {

            await api.post(
                `/vehicles/${id}/restock?quantity=${qty}`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem("token")}`
                    }
                }
            );

            setMessage("Vehicle restocked successfully.");

            setRestockQuantities({
                ...restockQuantities,
                [id]: ""
            });

            loadVehicles();

        } catch (error) {

            if (error.response) {
                setMessage(error.response.data.message);
            } else {
                setMessage("Unable to connect to server.");
            }

        }

    };
    const handleLogout = () => {
        // 1. Remove the authentication items
        localStorage.removeItem("token");
        localStorage.removeItem("role");

        // 2. Send the user back to the login screen
        navigate("/login");
    };
    return (
        <div>
            <h1>Vehicle Dashboard</h1>
            <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>

                <button
                    onClick={handleLogout}
                    style={{
                        padding: "8px 15px",
                        backgroundColor: "#dc3545",
                        color: "white",
                        border: "none",
                        cursor: "pointer",
                        borderRadius: "4px"
                    }}
                >
                    Logout
                </button>
            </div>
            {/* Admin Panel containing ONLY the navigation button now */}
            {isAdmin && (
                <div style={{ padding: "10px 0", marginBottom: "20px" }}>
                    <button
                        onClick={() => navigate("/vehicles/add")}
                        style={{ padding: "10px 15px", backgroundColor: "green", color: "white", cursor: "pointer" }}
                    >
                        + Add New Vehicle
                    </button>
                </div>
            )}

            <h3>Search Vehicle</h3>
            <div>
                <input type="text" name="make" placeholder="Make" value={search.make} onChange={handleSearchChange} />
                <br /><br />
                <input type="text" name="model" placeholder="Model" value={search.model} onChange={handleSearchChange} />
                <br /><br />
                <input type="text" name="category" placeholder="Category" value={search.category} onChange={handleSearchChange} />
                <br /><br />
                <input type="number" name="minPrice" placeholder="Minimum Price" value={search.minPrice} onChange={handleSearchChange} />
                <br /><br />
                <input type="number" name="maxPrice" placeholder="Maximum Price" value={search.maxPrice} onChange={handleSearchChange} />
                <br /><br />
                <button onClick={handleSearch}>Search</button>
                <button onClick={loadVehicles} style={{ marginLeft: "10px" }}>Show All</button>
            </div>

            <br />
            {message && <p>{message}</p>}
            <p>Total Vehicles: {vehicles.length}</p>

            <table border="1" cellPadding="10">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Make</th>
                        <th>Model</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Action</th>
                        {isAdmin && (
                            <>
                                <th>Edit</th>
                                <th>Delete</th>
                                <th>Restock</th>
                            </>
                        )}
                    </tr>
                </thead>
                <tbody>
                    {vehicles.map((vehicle) => (
                        <tr key={vehicle.id}>
                            <td>{vehicle.id}</td>
                            <td>{vehicle.make}</td>
                            <td>{vehicle.model}</td>
                            <td>{vehicle.category}</td>
                            <td>{vehicle.price}</td>
                            <td>{vehicle.quantity}</td>
                            <td>
                                <button onClick={() => purchaseVehicle(vehicle.id)} disabled={vehicle.quantity === 0}>
                                    Purchase
                                </button>
                            </td>
                            {isAdmin && (
                                <>
                                    <td>
                                        {/* Dynamic route path matching your routing system */}
                                        <button onClick={() => navigate(`/vehicles/edit/${vehicle.id}`)}>
                                            Edit
                                        </button>
                                    </td>
                                    <td>
                                        <button onClick={() => handleDeleteVehicle(vehicle.id)}>Delete</button>
                                    </td>
                                    <td>
                                        <input
                                            type="number"
                                            placeholder="Qty"
                                            style={{ width: "60px", marginRight: "5px" }}
                                            value={restockQuantities[vehicle.id] || ""}
                                            onChange={(e) => handleRestockQuantityChange(vehicle.id, e.target.value)}
                                        />
                                        <button onClick={() => handleRestock(vehicle.id)}>Restock</button>
                                    </td>
                                </>
                            )}
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default Dashboard;