import { NavLink, useNavigate } from "react-router-dom";

function Layout({ children }) {
    const navigate = useNavigate();
    const user = JSON.parse(localStorage.getItem("user"));

    const logout = () => {
        localStorage.clear();
        navigate("/");
    };

    return (
        <div className="app-layout">
            <aside className="sidebar">
                <h2>DigitalBank</h2>

                <nav>
                    <NavLink to="/dashboard" className={({ isActive }) => isActive ? "active-link" : ""}>
                        Dashboard
                    </NavLink>

                    <NavLink to="/transactions" className={({ isActive }) => isActive ? "active-link" : ""}>
                        Transactions
                    </NavLink>

                    <NavLink to="/cards" className={({ isActive }) => isActive ? "active-link" : ""}>
                        Cards
                    </NavLink>

                    <NavLink to="/transfers" className={({ isActive }) => isActive ? "active-link" : ""}>
                        Transfers
                    </NavLink>

                    <NavLink to="/payments" className={({ isActive }) => isActive ? "active-link" : ""}>
                        Payments
                    </NavLink>

                    <NavLink to="/exchange" className={({ isActive }) => isActive ? "active-link" : ""}>
                        Exchange
                    </NavLink>

                    <NavLink to="/support" className={({ isActive }) => isActive ? "active-link" : ""}>
                        Support
                    </NavLink>

                    <NavLink to="/account" className={({ isActive }) => isActive ? "active-link" : ""}>
                        My Account
                    </NavLink>
                </nav>

                <button onClick={logout} className="logout-btn">
                    Logout
                </button>
            </aside>

            <main className="main-content">
                <header className="topbar">
                    <div>
                        <p>Welcome back,</p>
                        <h3>{user?.username}</h3>
                    </div>
                </header>

                {children}
            </main>
        </div>
    );
}

export default Layout;