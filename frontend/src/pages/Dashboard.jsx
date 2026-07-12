import { useEffect, useState } from "react";
import api from "../api/api";

function Dashboard() {

    const [vehicles, setVehicles] = useState([]);
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

    return (

        <div>

            <h1>Dashboard</h1>

            {message && <p>{message}</p>}

            <table border="1" cellPadding="8">

                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Make</th>
                        <th>Model</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Quantity</th>
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
                        </tr>
                    ))}

                </tbody>

            </table>

        </div>

    );
}

export default Dashboard;