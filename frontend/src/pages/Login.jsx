import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/api";
import "./Login.css";

function Login() {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({ email: "", password: "" });
    const [message, setMessage] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    const handleChange = (event) => {
        setFormData({ ...formData, [event.target.name]: event.target.value });
        setMessage("");
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        setIsSubmitting(true);

        try {
            const response = await api.post("/auth/login", formData);
            const data = response.data;

            localStorage.setItem("token", data.token);
            localStorage.setItem("role", data.role);
            navigate(data.role === "ADMIN" ? "/admin/dashboard" : "/dashboard");
        } catch (error) {
            setMessage(error.response?.data?.message || "Invalid email or password.");
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <main className="login-page">
            <section className="login-card">
                <div className="form-header">
                    <h1>Welcome back</h1>
                    <p>Log in to access the vehicle inventory system.</p>
                </div>

                <form onSubmit={handleSubmit} className="login-form">
                    <label htmlFor="email">Email Address</label>
                    <input id="email" type="email" name="email" placeholder="Enter your email" value={formData.email} onChange={handleChange} required />

                    <label htmlFor="password">Password</label>
                    <input id="password" type="password" name="password" placeholder="Enter your password" value={formData.password} onChange={handleChange} required />

                    {message && <p className="form-message">{message}</p>}

                    <button type="submit" disabled={isSubmitting}>
                        {isSubmitting ? "Logging in..." : "Login"}
                    </button>
                </form>

                <p className="register-link">Don't have an account? <Link to="/register">Register</Link></p>
            </section>
        </main>
    );
}

export default Login;
