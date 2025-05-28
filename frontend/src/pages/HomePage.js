// src/pages/HomePage.js
import React, { useContext } from 'react';
import { AuthContext } from '../contexts/AuthContext';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

export default function HomePage() {
    const { t } = useTranslation();
    const { logout, userRoles } = useContext(AuthContext);

    return (
        <div className="container py-4">
            <h1 className="mb-4">{t('homePage.welcome')}</h1>
            <div className="d-flex flex-column flex-md-row gap-2">
                <button className="btn btn-outline-primary" onClick={logout}>
                    {t('homePage.logout')}
                </button>
                {userRoles.includes('ROLE_ADMIN') && (
                    <Link to="/admin" className="btn btn-primary">
                        {t('homePage.adminPanel')}
                    </Link>
                )}
            </div>
        </div>
    );
}
