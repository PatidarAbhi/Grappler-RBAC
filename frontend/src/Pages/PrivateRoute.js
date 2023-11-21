import React from 'react'
import { Route, Navigate, Outlet } from 'react-router-dom'
import { DoLogout, IsLoggedIn } from '../Authentication';
import NavBar from '../Components/NavBar';
import { jwtDecode } from 'jwt-decode';
import UserNavbar from '../Components/UserNavbar';
import { toast } from 'react-toastify';

const PrivateRoute = ({ allowedRoles }) => {

    const notify = () => toast.error("You are not authorized");
    const data = localStorage.getItem('data');
    const parsedData = data ? JSON.parse(data) : null;
    let userRole = '';

    if (parsedData && parsedData.jwtToken) {
        const decodedToken = jwtDecode(parsedData.jwtToken);
        userRole = decodedToken.role;
    }

    if (IsLoggedIn() && userRole == allowedRoles) {
        return (
            <>
                {userRole == "ROLE_ADMIN" ? <NavBar /> : <UserNavbar />}
                <Outlet />
            </>
        )
    }
    else {
        return (
            <>
                {userRole == "ROLE_ADMIN" ? <NavBar /> : <UserNavbar />}
                <div style={{ textAlign: 'center', marginTop: '50px' }}>
                    <h2>Oops! Access Denied</h2>
                    <p>Sorry, you don't have permission to access this page.</p>
                </div>
            </>
        )
    }
}
export default PrivateRoute