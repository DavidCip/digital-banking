import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Layout from "../components/Layout";
import api from "../api/Api.js";

function MyAccount() {
    const userId = localStorage.getItem("userId");
    const navigate = useNavigate();

    const [message, setMessage] = useState("");
    const [messageType, setMessageType] = useState("success");

    const [user, setUser] = useState(null);
    const [phone, setPhone] = useState("");

    const [passwordForm, setPasswordForm] = useState({
        oldPassword: "",
        newPassword: ""
    });

    useEffect(() => {
        loadUser();
    }, []);

    const showMessage = (text, type = "success") => {
        setMessage(text);
        setMessageType(type);

        setTimeout(() => {
            setMessage("");
        }, 3000);
    };

    const loadUser = async () => {
        try {
            const response = await api.get(`/users/${userId}`);
            setUser(response.data);
            setPhone(response.data.phone);
        } catch (error) {
            showMessage("Could not load account data", "error");
        }
    };

    const updatePhone = async (e) => {
        e.preventDefault();

        try {
            const response = await api.put(`/users/${userId}/phone`, {
                phone
            });

            setUser(response.data);
            showMessage("Phone updated successfully");
        } catch (error) {
            showMessage(error.response?.data?.message || "Could not update phone", "error");
        }
    };

    const updatePassword = async (e) => {
        e.preventDefault();

        try {
            await api.put(`/users/${userId}/password`, passwordForm);

            showMessage("Password updated successfully");

            setPasswordForm({
                oldPassword: "",
                newPassword: ""
            });
        } catch (error) {
            showMessage(error.response?.data?.message || "Could not update password", "error");
        }
    };

    const deleteAccount = async () => {
        const confirmDelete = window.confirm(
            "Are you sure you want to deactivate your account?"
        );

        if (!confirmDelete) return;

        try {
            await api.delete(`/users/${userId}`);

            localStorage.clear();
            navigate("/");
        } catch (error) {
            showMessage(error.response?.data?.message || "Could not deactivate account", "error");
        }
    };

    return (
        <Layout>
            {message && (
                <div className={messageType === "success" ? "success-message" : "error-message"}>
                    {message}
                </div>
            )}

            <section className="dashboard-header">
                <div>
                    <h1>My Account</h1>
                    <p>View and manage your personal banking profile.</p>
                </div>
            </section>

            <div className="forms-grid">
                <div className="panel">
                    <h2>Personal Information</h2>

                    {!user ? (
                        <p className="muted">Loading...</p>
                    ) : (
                        <div className="profile-info">
                            <div>
                                <span>Full name</span>
                                <strong>{user.firstName} {user.lastName}</strong>
                            </div>

                            <div>
                                <span>Username</span>
                                <strong>{user.username}</strong>
                            </div>

                            <div>
                                <span>CNP</span>
                                <strong>{user.cnp}</strong>
                            </div>

                            <div>
                                <span>Phone</span>
                                <strong>{user.phone}</strong>
                            </div>

                            <div>
                                <span>Status</span>
                                <strong>{user.active ? "ACTIVE" : "INACTIVE"}</strong>
                            </div>

                            <div>
                                <span>Created at</span>
                                <strong>{user.createdAt?.replace("T", " ").slice(0, 16)}</strong>
                            </div>
                        </div>
                    )}

                    <button className="danger-btn full-width account-delete-btn" onClick={deleteAccount}>
                        Deactivate Account
                    </button>
                </div>

                <div className="panel">
                    <h2>Update Phone</h2>

                    <form className="bank-form" onSubmit={updatePhone}>
                        <label>Phone number</label>

                        <input
                            value={phone}
                            onChange={(e) => setPhone(e.target.value)}
                            placeholder="0712345678"
                        />

                        <button type="submit">Update Phone</button>
                    </form>

                    <div className="section-divider"></div>

                    <h2>Update Password</h2>

                    <form className="bank-form" onSubmit={updatePassword}>
                        <label>Old password</label>

                        <input
                            type="password"
                            value={passwordForm.oldPassword}
                            onChange={(e) =>
                                setPasswordForm({
                                    ...passwordForm,
                                    oldPassword: e.target.value
                                })
                            }
                            placeholder="Old password"
                        />

                        <label>New password</label>

                        <input
                            type="password"
                            value={passwordForm.newPassword}
                            onChange={(e) =>
                                setPasswordForm({
                                    ...passwordForm,
                                    newPassword: e.target.value
                                })
                            }
                            placeholder="New password"
                        />

                        <button type="submit">Update Password</button>
                    </form>
                </div>
            </div>
        </Layout>
    );
}

export default MyAccount;