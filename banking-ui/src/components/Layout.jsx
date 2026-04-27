import { Link, useNavigate } from "react-router-dom";

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
                    <Link to="/dashboard">Dashboard</Link>
                    <Link to="/cards">Cards</Link>
                    <Link to="/transactions">Transactions</Link>
                    <Link to="/transfers">Transfers</Link>
                    <Link to="/payments">Payments</Link>
                    <Link to="/exchange">Exchange</Link>
                    <Link to="/support">Support</Link>
                    <Link to="/account">My Account</Link>
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