import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Transfers from "./pages/Transfers";
import Cards from "./pages/Cards";
import Payments from "./pages/Payments";
import Exchange from "./pages/Exchange";
import Support from "./pages/Support";
import MyAccount from "./pages/MyAccount";
import Transactions from "./pages/Transactions";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/dashboard" element={<Dashboard />} />
                <Route path="/transfers" element={<Transfers />} />
                <Route path="/cards" element={<Cards />} />
                <Route path="/payments" element={<Payments />} />
                <Route path="/exchange" element={<Exchange />} />
                <Route path="/support" element={<Support />} />
                <Route path="/account" element={<MyAccount />} />
                <Route path="/transactions" element={<Transactions />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;