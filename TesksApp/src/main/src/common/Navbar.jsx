import {Link, useNavigate } from "react-router-dom";
import ApiService from "../api/ApiService"

const Navbar = () => {

    const isAuthenticated = ApiService.isAuthenticated();
    const navigate = useNavigate()

    const handleLogout = () =>{
        const isLogout = window.confirm("Are you sure want to logout?")
        if(isLogout){
            ApiService.logout();
            navigate("/login")
        }
    }

    return (
        <nav className="navbar">
            <div className="logo">
            <Link to="/" className="logo-link">
                Task Manager
            </Link>
            </div>

            <div className="desktop-nav">
                {isAuthenticated ? (
                    <>
                    <Link to="/tasks" className="nav-link">My Task </Link>
                    <button onClick={handleLogout} className="nav-button">
                    Logout
                    </button>
                    </>
                ): (
                    <>
                     <Link to="/login" className="nav-link">Login</Link>
                      <Link to="/register" className="nav-link">Register</Link>
                    </>

                ) }
            </div>
        </nav>
    )
}

export default Navbar;