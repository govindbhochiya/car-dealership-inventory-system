import { Link } from "react-router-dom";

function Register() {

    return (
        <div>

            <h1>User Registration</h1>

            <form>

                <div>
                    <label>Full Name</label><br />
                    <input
                        type="text"
                        placeholder="Enter Full Name"
                    />
                </div>

                <br />

                <div>
                    <label>Email</label><br />
                    <input
                        type="email"
                        placeholder="Enter Email"
                    />
                </div>

                <br />

                <div>
                    <label>Password</label><br />
                    <input
                        type="password"
                        placeholder="Enter Password"
                    />
                </div>

                <br />

                <div>
                    <label>Role</label><br />
                    <select>
                        <option value="">Select Role</option>
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

            <p>
                Already have an account?
                <Link to="/login"> Login</Link>
            </p>

        </div>
    );
}

export default Register;