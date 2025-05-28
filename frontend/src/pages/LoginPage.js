// src/pages/LoginPage.js
import React, { useState, useContext } from "react";
import { AuthContext } from "../contexts/AuthContext";
import { useNavigate, Link } from "react-router-dom";
import { useTranslation } from 'react-i18next';
import "../styles/FormStyles.css";

export default function LoginPage() {
    const { t } = useTranslation();
    const [email, setEmail]       = useState("");
    const [password, setPassword] = useState("");
    const [error, setError]       = useState("");
    const { login }               = useContext(AuthContext);
    const navigate                = useNavigate();

    const handleSubmit = async e => {
        e.preventDefault();
        try {
            await login(email, password);
            navigate("/");
        } catch (err) {
            setError(err.response?.data || err.message || t('loginPage.loginFailed'));
        }
    };

    return (
        <div className="form-container py-4">
            <div className="form-card">
                <h2 className="form-title">{t('loginPage.title')}</h2>
                <form onSubmit={handleSubmit}>
                    <input
                        className="form-input"
                        type="email"
                        placeholder={t('loginPage.emailPlaceholder')}
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                        required
                    />
                    <input
                        className="form-input"
                        type="password"
                        placeholder={t('loginPage.passwordPlaceholder')}
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                        required
                    />
                    <button type="submit" className="form-button">
                        {t('loginPage.login')}
                    </button>
                    {error && <p className="form-text text-danger">{error}</p>}
                    <div className="form-text">
                        {t('loginPage.noAccount')}{" "}
                        <Link to="/register" className="form-link">
                            {t('loginPage.register')}
                        </Link>
                    </div>
                    <div className="form-text">
                        <Link to="/reset-password" className="form-link">
                            {t('loginPage.forgotPassword')}
                        </Link>
                    </div>
                </form>
            </div>
        </div>
    );
}
