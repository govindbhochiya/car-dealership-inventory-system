import { useState } from "react";
import { Link } from "react-router-dom";
import api from "../api/api";
import "./Register.css";

function Register() {
    const [user, setUser] = useState({
        fullName: "",
        email: "",
        password: "",
        role: "USER"
    });
    const [message, setMessage] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    const handleChange = (event) => {
        setUser({ ...user, [event.target.name]: event.target.value });
        setMessage("");
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        setIsSubmitting(true);

        try {
            await api.post("/auth/register", user);
            setMessage("Registration successful. You can now log in.");
            setUser({ fullName: "", email: "", password: "", role: "USER" });
        } catch (error) {
            setMessage(error.response?.data?.message || "Unable to connect to server.");
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <main className="register-page">
            <section className="register-card">
                <div className="form-header">
                    <h2>Create an account</h2>
                    <p>Enter your details to register for the system.</p>
                </div>

                <form onSubmit={handleSubmit} className="register-form">
                    <label htmlFor="fullName">Full Name</label>
                    <input id="fullName" type="text" name="fullName" placeholder="Enter your full name" value={user.fullName} onChange={handleChange} required />

                    <label htmlFor="email">Email Address</label>
                    <input id="email" type="email" name="email" placeholder="Enter your email" value={user.email} onChange={handleChange} required />

                    <label htmlFor="password">Password</label>
                    <input id="password" type="password" name="password" placeholder="Enter your password" value={user.password} onChange={handleChange} minLength="6" required />

                    <label htmlFor="role">Role</label>
                    <select id="role" name="role" value={user.role} onChange={handleChange}>
                        <option value="USER">User</option>
                        <option value="ADMIN">Admin</option>
                    </select>

                    {message && <p className="form-message">{message}</p>}

                    <button type="submit" disabled={isSubmitting}>
                        {isSubmitting ? "Registering..." : "Create Account"}
                    </button>
                </form>

                <p className="login-link">Already have an account? <Link to="/login">Login</Link></p>
            </section>
        </main>
    );
}

export default Register;
