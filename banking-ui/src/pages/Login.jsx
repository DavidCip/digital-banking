import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/Api.js";

function Login() {
    const navigate = useNavigate();

    const [form, setForm] = useState({
        username: "",
        password: ""
    });

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await api.post("/auth/login", form);

            localStorage.setItem("user", JSON.stringify(response.data));
            localStorage.setItem("userId", response.data.userId);

            navigate("/dashboard");
        } catch (error) {
            alert(error.response?.data?.message || "Login failed");
        }
    };

    return (
        <div className="login-page">
            <div className="login-left">
                <h1>Digital Banking</h1>
                <p>
                    Manage your money, savings,
                    transfers and cards in one place.
                </p>
            </div>

            <div className="login-card">
                <h2>Welcome Back</h2>

                <form onSubmit={handleSubmit}>
                    <input
                        name="username"
                        type="text"
                        placeholder="Username"
                        value={form.username}
                        onChange={handleChange}
                    />

                    <input
                        name="password"
                        type="password"
                        placeholder="Password"
                        value={form.password}
                        onChange={handleChange}
                    />

                    <button type="submit">
                        Sign In
                    </button>
                </form>

                <p>
                    Don't have an account?{" "}
                    <Link to="/register">
                        Register
                    </Link>
                </p>
            </div>
        </div>
    );
}

export default Login;