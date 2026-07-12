import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/api";

function EditVehicle() {
    const { id } = useParams(); // Grabs the URL ID parameter
    const navigate = useNavigate();
    const [message, setMessage] = useState("");
    const [form, setForm] = useState({ make: "", model: "", category: "", price: "", quantity: "" });

    useEffect(() => {
        // Fetch specific vehicle data to prefill form fields
        const fetchVehicle = async () => {
            try {
                const response = await api.get(`/vehicles`, {
                    headers: { Authorization: `Bearer ${localStorage.getItem("token")}` }
                });
                // Find the matching vehicle record out of the array response
                const foundVehicle = response.data.find(v => v.id === parseInt(id, 10));
                if (foundVehicle) {
                    setForm({
                        make: foundVehicle.make,
                        model: foundVehicle.model,
                        category: foundVehicle.category,
                        price: foundVehicle.price,
                        quantity: foundVehicle.quantity
                    });
                }
            } catch (error) {
                setMessage("Error loading vehicle details.");
            }
        };
        fetchVehicle();
    }, [id]);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await api.put(`/vehicles/${id}`, form, {
                headers: { Authorization: `Bearer ${localStorage.getItem("token")}` }
            });
            // Redirect back to dashboard safely
            const userRole = localStorage.getItem("role");
            if (userRole === "ADMIN") {
                navigate("/admin/dashboard");
            } else {
                navigate("/dashboard");
            }
        } catch (error) {
            if (error.response) {
                setMessage(error.response.data.message);
            } else {
                setMessage("Unable to update vehicle.");
            }
        }
    };

    return (
        <div>
            <h2>Update Vehicle</h2>
            {message && <p style={{ color: "red" }}>{message}</p>}
            <form onSubmit={handleSubmit}>
                <input type="text" name="make" placeholder="Make" value={form.make} onChange={handleChange} required /><br /><br />
                <input type="text" name="model" placeholder="Model" value={form.model} onChange={handleChange} required /><br /><br />
                <input type="text" name="category" placeholder="Category" value={form.category} onChange={handleChange} required /><br /><br />
                <input type="number" name="price" placeholder="Price" value={form.price} onChange={handleChange} required /><br /><br />
                <input type="number" name="quantity" placeholder="Quantity" value={form.quantity} onChange={handleChange} required /><br /><br />
                <button type="submit">Update Vehicle</button>
                <button type="button" onClick={() => navigate("/dashboard")} style={{ marginLeft: "10px" }}>Cancel</button>
            </form>
        </div>
    );
}

export default EditVehicle;