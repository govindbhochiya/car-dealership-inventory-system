import { useState } from "react";
import { Link } from "react-router-dom";
import api from "../api/api";

function Register() {

    const [user, setUser] = useState({
        fullName: "",
        email: "",
        password: "",
        role: "USER"
    });

    const [message, setMessage] = useState("");

    const handleChange = (e) => {
        setUser({
            ...user,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {

            const response = await api.post("/auth/register", user);

            setMessage("Registration Successful!");

            console.log(response.data);

            setUser({
                fullName: "",
                email: "",
                password: "",
                role: "USER"
            });

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

            <h1>User Registration</h1>

            <form onSubmit={handleSubmit}>

                <div>

                    <label>Full Name</label><br />

                    <input
                        type="text"
                        name="fullName"
                        value={user.fullName}
                        onChange={handleChange}
                    />

                </div>

                <br />

                <div>

                    <label>Email</label><br />

                    <input
                        type="email"
                        name="email"
                        value={user.email}
                        onChange={handleChange}
                    />

                </div>

                <br />

                <div>

                    <label>Password</label><br />

                    <input
                        type="password"
                        name="password"
                        value={user.password}
                        onChange={handleChange}
                    />

                </div>

                <br />

                <div>

                    <label>Role</label><br />

                    <select
                        name="role"
                        value={user.role}
                        onChange={handleChange}
                    >
                        <option value="USER">USER</option>
                        <option value="ADMIN">ADMIN</option>
                    </select>

                </div>

                <br />

                <button type="submit">
                    Register
                </button>

            </form>

            <br />

            {message && <p>{message}</p>}

            <p>
                Already have an account?
                <Link to="/login"> Login</Link>
            </p>

        </div>
    );
}

export default Register;