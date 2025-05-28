// src/pages/ProfilePage.jsx
import React, { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../contexts/AuthContext';
import { getProfile, updateProfile, uploadAvatar } from '../api/profile';
import { useTranslation } from 'react-i18next';

export default function ProfilePage() {
    const { t } = useTranslation();
    const { token, logout } = useContext(AuthContext);
    const navigate = useNavigate();
    const [form, setForm] = useState({ name: '', email: '' });
    const [avatarPreview, setAvatarPreview] = useState(null);
    const [file, setFile] = useState(null);

    useEffect(() => {
        if (!token) navigate('/login', { replace: true });
    }, [token, navigate]);

    useEffect(() => {
        if (!token) return;
        getProfile(token)
            .then(res => {
                setForm({ name: res.data.name, email: res.data.email });
                if (res.data.avatar) {
                    setAvatarPreview(
                        `data:${res.data.avatarContentType};base64,${res.data.avatar}`
                    );
                }
            })
            .catch(() => {
                alert(t('profilePage.loadError'));
                logout();
                navigate('/login', { replace: true });
            });
    }, [token, logout, navigate, t]);

    const handleSubmit = e => {
        e.preventDefault();
        updateProfile(token, form)
            .then(() => alert(t('profilePage.saveSuccess')))
            .catch(() => alert(t('profilePage.saveError')));
    };

    const handleAvatarUpload = () => {
        if (!file) {
            alert(t('profilePage.selectFile'));
            return;
        }
        uploadAvatar(token, file)
            .then(() => getProfile(token))
            .then(res => {
                if (res.data.avatar) {
                    setAvatarPreview(
                        `data:${res.data.avatarContentType};base64,${res.data.avatar}`
                    );
                    alert(t('profilePage.avatarUploaded'));
                }
            })
            .catch(() => alert(t('profilePage.avatarError')));
    };

    const handleLogout = () => {
        logout();
        navigate('/login', { replace: true });
    };

    return (
        <div className="container py-4">
            <div className="card mx-auto w-100" style={{ maxWidth: 500 }}>
                <div className="card-body">
                    <h2 className="card-title text-center mb-4">{t('profilePage.title')}</h2>

                    <form onSubmit={handleSubmit}>
                        <div className="text-center mb-4">
                            <img
                                src={avatarPreview || '/default-avatar.png'}
                                alt={t('profilePage.avatarAlt')}
                                className="rounded-circle"
                                style={{ width: 120, height: 120, objectFit: 'cover' }}
                            />
                        </div>

                        <div className="mb-3">
                            <label className="form-label">{t('profilePage.nameLabel')}</label>
                            <input
                                type="text"
                                className="form-control"
                                value={form.name}
                                onChange={e => setForm({ ...form, name: e.target.value })}
                                required
                            />
                        </div>

                        <div className="mb-3">
                            <label className="form-label">{t('profilePage.emailLabel')}</label>
                            <input
                                type="email"
                                className="form-control"
                                value={form.email}
                                readOnly
                            />
                        </div>

                        <div className="mb-3">
                            <label className="form-label">{t('profilePage.avatarLabel')}</label>
                            <input
                                type="file"
                                className="form-control"
                                accept="image/*"
                                onChange={e => setFile(e.target.files[0])}
                            />
                        </div>

                        <div className="d-flex flex-column flex-md-row justify-content-between gap-2">
                            <button type="submit" className="btn btn-primary">
                                {t('profilePage.save')}
                            </button>
                            <button
                                type="button"
                                className="btn btn-outline-secondary"
                                onClick={handleAvatarUpload}
                            >
                                {t('profilePage.uploadAvatar')}
                            </button>
                        </div>
                    </form>

                    <hr className="my-4" />

                    <div className="d-flex justify-content-center">
                        <button className="btn btn-secondary" onClick={handleLogout}>
                            {t('profilePage.logout')}
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}
