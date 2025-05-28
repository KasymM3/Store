// src/pages/OrderDetailPage.js
import React, { useState, useEffect }    from 'react';
import { useParams, useNavigate }        from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { api } from '../api';

export default function OrderDetailPage() {
    const { t } = useTranslation();
    const { id } = useParams();
    const navigate = useNavigate();
    const [order, setOrder] = useState(null);

    useEffect(() => {
        api.get(`/orders/${id}`)
            .then(r => setOrder(r.data))
            .catch(console.error);
    }, [id]);

    if (!order) {
        return <p>{t('orderDetailPage.loading')}</p>;
    }

    const handleAction = async (action) => {
        const params = new URLSearchParams();
        params.append('action', action);
        try {
            const res = await fetch(`/api/orders/${id}/action`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: params.toString()
            });
            if (!res.ok) throw new Error(`Ошибка ${res.status}`);

            if (action === 'pay') {
                alert(t('orderDetailPage.alerts.paid'));
            } else if (action === 'cancel') {
                alert(t('orderDetailPage.alerts.cancelled'));
            } else if (action === 'refund') {
                alert(t('orderDetailPage.alerts.refunded'));
            }
            navigate('/orders');
        } catch (e) {
            console.error(e);
            alert(t('orderDetailPage.alerts.error', { message: e.message }));
        }
    };

    const raw      = order.state.toLowerCase();
    const isNew    = raw.startsWith('new');
    const isClosed = raw.startsWith('closed');

    return (
        <div className="container py-4">
            <div className="card mx-auto w-100" style={{ maxWidth: 600 }}>
                <div className="card-header">
                    <h2 className="h5 mb-0">
                        {t('orderDetailPage.orderNumber', { id: order.id })}
                    </h2>
                </div>
                <div className="card-body">
                    <ul className="list-group list-group-flush mb-3">
                        <li className="list-group-item d-flex justify-content-between">
                            <span>{t('orderDetailPage.type')}:</span> <strong>{order.type}</strong>
                        </li>
                        <li className="list-group-item d-flex justify-content-between">
                            <span>{t('orderDetailPage.state')}:</span> <strong>{order.state}</strong>
                        </li>
                        <li className="list-group-item d-flex justify-content-between">
                            <span>{t('orderDetailPage.basePrice')}:</span> <strong>{order.basePrice}</strong>
                        </li>
                        <li className="list-group-item d-flex justify-content-between">
                            <span>{t('orderDetailPage.deliveryCost')}:</span> <strong>{order.deliveryCost}</strong>
                        </li>
                        <li className="list-group-item d-flex justify-content-between">
                            <span>{t('orderDetailPage.total')}:</span> <strong>{order.basePrice + order.deliveryCost}</strong>
                        </li>
                        <li className="list-group-item d-flex justify-content-between">
                            <span>{t('orderDetailPage.estimatedDays')}:</span> <strong>{order.estimatedDays}</strong>
                        </li>
                    </ul>

                    <div className="d-flex flex-column flex-md-row gap-2">
                        {isNew && (
                            <>
                                <button
                                    onClick={() => handleAction('pay')}
                                    className="btn btn-success flex-fill"
                                >
                                    {t('orderDetailPage.actions.pay')}
                                </button>
                                <button
                                    onClick={() => handleAction('cancel')}
                                    className="btn btn-warning flex-fill"
                                >
                                    {t('orderDetailPage.actions.cancel')}
                                </button>
                            </>
                        )}
                        {isClosed && (
                            <button
                                onClick={() => handleAction('refund')}
                                className="btn btn-danger flex-fill"
                            >
                                {t('orderDetailPage.actions.refund')}
                            </button>
                        )}
                    </div>
                </div>
                <div className="card-footer text-end">
                    <button onClick={() => navigate('/orders')} className="btn btn-link">
                        {t('orderDetailPage.backToList')}
                    </button>
                </div>
            </div>
        </div>
    );
}
