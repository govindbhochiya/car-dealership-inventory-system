import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import "./VehicleForm.css";

function AddVehicle() {
    const [form, setForm] = useState({ make: "", model: "", category: "", price: "", quantity: "" });
    const [message, setMessage] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);
    const navigate = useNavigate();

    const handleChange = (event) => {
        setForm({ ...form, [event.target.name]: event.target.value });
        setMessage("");
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        setIsSubmitting(true);
        try {
            await api.post("/vehicles", form, { headers: { Authorization: `Bearer ${localStorage.getItem("token")}` } });
            navigate("/admin/dashboard");
        } catch (error) {
            setMessage(error.response?.data?.message || "Unable to save vehicle.");
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <main className="vehicle-form-page">
            <section className="vehicle-form-card">
                <div className="vehicle-form-header">
                    <p className="page-label">Administration</p>
                    <h1>Add Vehicle</h1>
                    <p>Add a new vehicle to the inventory.</p>
                </div>
                <form onSubmit={handleSubmit} className="vehicle-form">
                    <div className="field-grid">
                        <div><label htmlFor="make">Make</label><input id="make" type="text" name="make" placeholder="e.g. Toyota" value={form.make} onChange={handleChange} required /></div>
                        <div><label htmlFor="model">Model</label><input id="model" type="text" name="model" placeholder="e.g. Camry" value={form.model} onChange={handleChange} required /></div>
                    </div>
                    <div><label htmlFor="category">Category</label><input id="category" type="text" name="category" placeholder="e.g. Sedan" value={form.category} onChange={handleChange} required /></div>
                    <div className="field-grid">
                        <div><label htmlFor="price">Price</label><input id="price" type="number" name="price" placeholder="Enter price" value={form.price} onChange={handleChange} min="0" required /></div>
                        <div><label htmlFor="quantity">Quantity</label><input id="quantity" type="number" name="quantity" placeholder="Enter quantity" value={form.quantity} onChange={handleChange} min="0" required /></div>
                    </div>
                    {message && <p className="vehicle-form-message">{message}</p>}
                    <div className="form-actions"><button className="secondary-button" type="button" onClick={() => navigate("/admin/dashboard")}>Cancel</button><button className="primary-button" type="submit" disabled={isSubmitting}>{isSubmitting ? "Saving..." : "Save Vehicle"}</button></div>
                </form>
            </section>
        </main>
    );
}

export default AddVehicle;
