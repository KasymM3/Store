// src/pages/OrdersPage.js
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { api } from '../api';

export default function OrdersPage() {
    const { t } = useTranslation();
    const [orders, setOrders]   = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        api.get('/orders')
            .then(r => setOrders(r.data))
            .catch(console.error)
            .finally(() => setLoading(false));
    }, []);

    if (loading) {
        return <p className="container py-4">{t('ordersPage.loading')}</p>;
    }

    return (
        <div className="container py-4">
            <div className="d-flex justify-content-between align-items-center mb-3">
                <h1 className="h3">{t('ordersPage.title')}</h1>
            </div>
            <div className="table-responsive">
                <table className="table table-striped table-hover align-middle">
                    <thead className="table-dark">
                    <tr>
                        <th>{t('ordersPage.header.id')}</th>
                        <th>{t('ordersPage.header.type')}</th>
                        <th>{t('ordersPage.header.state')}</th>
                        <th>{t('ordersPage.header.deliveryCost')}</th>
                        <th>{t('ordersPage.header.action')}</th>
                    </tr>
                    </thead>
                    <tbody>
                    {orders.map(o => (
                        <tr key={o.id}>
                            <td>{o.id}</td>
                            <td>{o.type}</td>
                            <td>{o.state}</td>
                            <td>{o.deliveryCost}</td>
                            <td>
                                <Link
                                    className="btn btn-outline-primary btn-sm"
                                    to={`/orders/${o.id}`}
                                >
                                    {t('ordersPage.view')}
                                </Link>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}
