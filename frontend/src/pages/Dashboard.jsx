import { useEffect, useState } from "react";
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

    useEffect(() => {
        loadVehicles();
    }, []);

    const loadVehicles = async () => {

        try {

            const response = await api.get("/vehicles", {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
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
        setSearch({
            ...search,
            [e.target.name]: e.target.value
        });
    };

    const handleSearch = async () => {

        try {

            const params = {};

            if (search.make.trim()) {
                params.make = search.make.trim();
            }

            if (search.model.trim()) {
                params.model = search.model.trim();
            }

            if (search.category.trim()) {
                params.category = search.category.trim();
            }

            if (search.minPrice) {
                params.minPrice = search.minPrice;
            }

            if (search.maxPrice) {
                params.maxPrice = search.maxPrice;
            }

            const response = await api.get("/vehicles/search", {
                params,
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
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

            await api.post(
                `/vehicles/${id}/purchase`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem("token")}`
                    }
                }
            );

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
    return (

        <div>

            <h1>Vehicle Dashboard</h1>

            <h3>Search Vehicle</h3>

            <div>

                <input
                    type="text"
                    name="make"
                    placeholder="Make"
                    value={search.make}
                    onChange={handleSearchChange}
                />

                <br /><br />

                <input
                    type="text"
                    name="model"
                    placeholder="Model"
                    value={search.model}
                    onChange={handleSearchChange}
                />

                <br /><br />

                <input
                    type="text"
                    name="category"
                    placeholder="Category"
                    value={search.category}
                    onChange={handleSearchChange}
                />

                <br /><br />

                <input
                    type="number"
                    name="minPrice"
                    placeholder="Minimum Price"
                    value={search.minPrice}
                    onChange={handleSearchChange}
                />

                <br /><br />

                <input
                    type="number"
                    name="maxPrice"
                    placeholder="Maximum Price"
                    value={search.maxPrice}
                    onChange={handleSearchChange}
                />

                <br /><br />

                <button onClick={handleSearch}>
                    Search
                </button>

                <button
                    onClick={loadVehicles}
                    style={{ marginLeft: "10px" }}
                >
                    Show All
                </button>

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
                                <button
                                    onClick={() => purchaseVehicle(vehicle.id)}
                                    disabled={vehicle.quantity === 0}
                                >
                                    Purchase
                                </button>
                            </td>
                        </tr>

                    ))}

                </tbody>

            </table>

        </div>

    );
}

export default Dashboard;