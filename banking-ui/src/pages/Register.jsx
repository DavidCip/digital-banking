import {useState} from "react";
import {useNavigate, Link} from "react-router-dom";
import api from "../api/Api.js";

function Register(){

    const navigate = useNavigate();

    const [form,setForm]=useState({
        firstName:"",
        lastName:"",
        username:"",
        password:"",
        cnp:"",
        phone:""
    });

    const handleChange=(e)=>{
        setForm({
            ...form,
            [e.target.name]:e.target.value
        });
    };

    const handleSubmit=async(e)=>{
        e.preventDefault();

        try{

            await api.post("/auth/register",form);

            alert("Account created successfully");

            navigate("/");

        }catch(error){

            alert(
                error.response?.data?.message ||
                "Registration failed"
            );

        }
    };

    return(
        <div className="auth-page">

            <div className="auth-card">

                <h2>Create Account</h2>

                <p className="auth-subtitle">
                    Start your digital banking journey.
                </p>

                <form onSubmit={handleSubmit}>

                    <div className="form-row">

                        <input
                            name="firstName"
                            placeholder="First name"
                            onChange={handleChange}
                        />

                        <input
                            name="lastName"
                            placeholder="Last name"
                            onChange={handleChange}
                        />

                    </div>

                    <input
                        name="username"
                        placeholder="Username"
                        onChange={handleChange}
                    />

                    <input
                        type="password"
                        name="password"
                        placeholder="Password"
                        onChange={handleChange}
                    />

                    <input
                        name="cnp"
                        placeholder="CNP"
                        onChange={handleChange}
                    />

                    <input
                        name="phone"
                        placeholder="Phone number"
                        onChange={handleChange}
                    />

                    <button type="submit">
                        Create Account
                    </button>

                </form>

                <p className="auth-link">
                    Already have an account?
                    <Link to="/"> Sign in</Link>
                </p>

            </div>

        </div>
    )

}

export default Register;