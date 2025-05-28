// src/api.js
import axios from 'axios';

/**
 * Единая точка входа для всех HTTP-запросов фронта.
 * Важно:  ✓ baseURL = '/api'
 *         ✓ withCredentials = true       (если используете Cookie)
 */
export const api = axios.create({
    baseURL: '/api',
    withCredentials: true,
});

/**
 * Интерсептор ставим именно на instance **api**, а не на глобальный axios.
 * Перед каждым запросом автоматически подставляем Bearer-токен,
 * который мы сохранили в localStorage под ключом 'jwt'.
 */
api.interceptors.request.use(config => {
    const token = localStorage.getItem('jwt');      // <- ключ совпадает c AuthContext
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});
