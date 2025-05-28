// src/api/auth.js
import axios from 'axios';

const API = axios.create({
    baseURL: '/api/auth',
});

export const signup = data => API.post('/signup', data);
export const login = data => API.post('/login', data);
export const updatePassword = data => API.post('/update-password', data);
