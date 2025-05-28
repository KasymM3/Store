// src/components/AdminRoute.js
import React, { useContext } from 'react';
import { Navigate } from 'react-router-dom';
import { AuthContext } from '../contexts/AuthContext';

export default function AdminRoute({ children }) {
    const { token, userRoles } = useContext(AuthContext);
    return token && userRoles.includes('ROLE_ADMIN')
        ? children
        : <Navigate to="/" replace />;
}
