
import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

export default function Register() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const [form, setForm] = useState({
        name: '',
        email: '',
        password: '',
        role: 'user'
    });
    const [error, setError] = useState('');

    const handleChange = e =>
        setForm(prev => ({ ...prev, [e.target.name]: e.target.value }));

    const handleSubmit = async e => {
        e.preventDefault();
        try {
            const res = await fetch('/api/auth/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(form)
            });
            if (res.ok) {
                navigate('/login');
            } else {
                const data = await res.json();
                setError(data.message || t('registerPage.fail'));
            }
        } catch {
            setError(t('registerPage.networkError'));
        }
    };

    return (
        <div className="container py-4">
            <div className="card mx-auto w-100" style={{ maxWidth: 400 }}>
                <div className="card-body">
                    <h2 className="card-title text-center mb-4">{t('registerPage.title')}</h2>
                    <form onSubmit={handleSubmit}>
                        <input
                            className="form-control mb-3"
                            name="name"
                            type="text"
                            placeholder={t('registerPage.namePlaceholder')}
                            value={form.name}
                            onChange={handleChange}
                            required
                        />
                        <input
                            className="form-control mb-3"
                            name="email"
                            type="email"
                            placeholder={t('registerPage.emailPlaceholder')}
                            value={form.email}
                            onChange={handleChange}
                            required
                        />
                        <input
                            className="form-control mb-3"
                            name="password"
                            type="password"
                            placeholder={t('registerPage.passwordPlaceholder')}
                            value={form.password}
                            onChange={handleChange}
                            required
                        />
                        <select
                            className="form-select mb-3"
                            name="role"
                            value={form.role}
                            onChange={handleChange}
                        >
                            <option value="user">{t('registerPage.roleUser')}</option>
                            <option value="admin">{t('registerPage.roleAdmin')}</option>
                        </select>

                        <div className="d-grid gap-2">
                            <button type="submit" className="btn btn-primary">
                                {t('registerPage.submit')}
                            </button>
                        </div>

                        {error && <p className="text-danger mt-3 text-center">{error}</p>}

                        <p className="mt-4 text-center">
                            {t('registerPage.haveAccount')}{' '}
                            <Link to="/login">{t('registerPage.login')}</Link>
                        </p>
                    </form>
                </div>
            </div>
        </div>
    );
}
