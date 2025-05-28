import axios from 'axios';

const API = axios.create({ baseURL: '/api/profile' });

export const getProfile = token => API.get('',  { headers: { Authorization: `Bearer ${token}` } });

export const updateProfile = (token, data) =>
    API.put('', data, { headers: { Authorization: `Bearer ${token}` } });

export const uploadAvatar = (token, file) => {
    const fd = new FormData();
    fd.append('file', file);
    return API.post('/avatar', fd, {
        headers: { Authorization: `Bearer ${token}` }
    });
};
