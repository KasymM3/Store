// src/contexts/AuthContext.js
import React, { createContext, useState, useEffect } from 'react';
import { login as apiLogin } from '../api/auth';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState(() => localStorage.getItem('jwt'));
    const [userRoles, setUserRoles] = useState(() => {
        const saved = localStorage.getItem('roles');
        return saved ? JSON.parse(saved) : [];
    });

    useEffect(() => {
        if (token) {
            localStorage.setItem('jwt', token);
            // из JWT можно декодировать роли, но для простоты передаём их из ответа
        } else {
            localStorage.removeItem('jwt');
            localStorage.removeItem('roles');
        }
    }, [token]);

    const login = async (email, password) => {
        const resp = await apiLogin({ email, password });
        const jwt = resp.data.token;
        // Предполагаем, что бэкенд возвращает роли в заголовке или теле — здесь для примера:
        const roles = resp.data.roles || [];
        setUserRoles(roles);
        localStorage.setItem('roles', JSON.stringify(roles));
        setToken(jwt);
    };

    const logout = () => {
        setToken(null);
        setUserRoles([]);
    };

    return (
        <AuthContext.Provider value={{ token, userRoles, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};
