import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";

function AddVehicle() {
    const [form, setForm] = useState({ make: "", model: "", category: "", price: "", quantity: "" });
    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await api.post("/vehicles", form, {
                headers: { Authorization: `Bearer ${localStorage.getItem("token")}` }
            });
            // Redirect back to dashboard immediately
            //if user  then user dash board or admin dashboard
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
                setMessage("Unable to connect to server.");
            }
        }
    };

    return (
        <div>
            <h2>Add Vehicle</h2>
            {message && <p style={{ color: "red" }}>{message}</p>}
            <form onSubmit={handleSubmit}>
                <input type="text" name="make" placeholder="Make" value={form.make} onChange={handleChange} required /><br /><br />
                <input type="text" name="model" placeholder="Model" value={form.model} onChange={handleChange} required /><br /><br />
                <input type="text" name="category" placeholder="Category" value={form.category} onChange={handleChange} required /><br /><br />
                <input type="number" name="price" placeholder="Price" value={form.price} onChange={handleChange} required /><br /><br />
                <input type="number" name="quantity" placeholder="Quantity" value={form.quantity} onChange={handleChange} required /><br /><br />
                <button type="submit">Save Vehicle</button>
                <button type="button" onClick={() => navigate("/dashboard")} style={{ marginLeft: "10px" }}>Cancel</button>
            </form>
        </div>
    );
}

export default AddVehicle;