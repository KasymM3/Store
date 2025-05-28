// src/pages/ResetPassword.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

export default function ResetPassword() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');

    const handleSubmit = async e => {
        e.preventDefault();
        try {
            const res = await fetch('/api/auth/reset-password', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password })
            });
            const data = await res.json();
            if (res.ok) {
                alert(t('resetPage.success'));
                navigate('/login');
            } else {
                setMessage(data.message || t('resetPage.fail'));
            }
        } catch {
            setMessage(t('resetPage.networkError'));
        }
    };

    return (
        <div className="container py-4">
            <div className="card mx-auto w-100" style={{ maxWidth: 400 }}>
                <div className="card-body">
                    <h2 className="card-title text-center mb-4">{t('resetPage.title')}</h2>
                    <form onSubmit={handleSubmit}>
                        <input
                            className="form-control mb-3"
                            type="email"
                            placeholder={t('resetPage.emailPlaceholder')}
                            value={email}
                            onChange={e => setEmail(e.target.value)}
                            required
                        />
                        <input
                            className="form-control mb-3"
                            type="password"
                            placeholder={t('resetPage.passwordPlaceholder')}
                            value={password}
                            onChange={e => setPassword(e.target.value)}
                            required
                        />

                        <div className="d-grid gap-2">
                            <button type="submit" className="btn btn-primary">
                                {t('resetPage.submit')}
                            </button>
                        </div>

                        {message && (
                            <p className="text-danger mt-3 text-center">{message}</p>
                        )}
                    </form>
                </div>
            </div>
        </div>
    );
}
