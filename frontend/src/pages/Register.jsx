import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/api";

function Login() {

    const navigate = useNavigate();

    const [user, setUser] = useState({
        email: "",
        password: ""
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

            const response = await api.post("/auth/login", user);

            localStorage.setItem("token", response.data.token);

            setMessage("Login Successful!");

            console.log(response.data);

            setUser({
                email: "",
                password: ""
            });

            navigate("/");

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

            <h1>User Login</h1>

            <form onSubmit={handleSubmit}>

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

                <button type="submit">
                    Login
                </button>

            </form>

            <br />

            {message && <p>{message}</p>}

            <p>
                Don't have an account?
                <Link to="/register"> Register</Link>
            </p>

        </div>
    );
}

export default Login;