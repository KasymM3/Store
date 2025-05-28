// src/pages/OrderFormPage.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

export default function OrderFormPage() {
    const { t } = useTranslation();
    const [type, setType]     = useState('physical');
    const [price, setPrice]   = useState('');
    const navigate            = useNavigate();

    const handleSubmit = async e => {
        e.preventDefault();
        await fetch('http://localhost:8080/api/orders', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ type, basePrice: parseFloat(price) })
        });
        navigate('/orders');
    };

    return (
        <div className="container py-4">
            <div className="card mx-auto w-100" style={{ maxWidth: 500 }}>
                <div className="card-header">
                    <h2 className="h5 mb-0">{t('orderFormPage.title')}</h2>
                </div>
                <div className="card-body">
                    <form onSubmit={handleSubmit}>
                        <div className="mb-3">
                            <label className="form-label">{t('orderFormPage.typeLabel')}</label>
                            <select
                                className="form-select"
                                value={type}
                                onChange={e => setType(e.target.value)}
                                required
                            >
                                <option value="physical">{t('orderFormPage.type.physical')}</option>
                                <option value="digital">{t('orderFormPage.type.digital')}</option>
                            </select>
                        </div>
                        <div className="mb-3">
                            <label className="form-label">{t('orderFormPage.priceLabel')}</label>
                            <input
                                type="number"
                                step="0.01"
                                className="form-control"
                                value={price}
                                onChange={e => setPrice(e.target.value)}
                                required
                            />
                        </div>
                        <div className="d-flex flex-column flex-md-row justify-content-between gap-2">
                            <button type="submit" className="btn btn-primary">
                                {t('orderFormPage.create')}
                            </button>
                            <button
                                type="button"
                                className="btn btn-secondary"
                                onClick={() => navigate('/orders')}
                            >
                                {t('orderFormPage.cancel')}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}
