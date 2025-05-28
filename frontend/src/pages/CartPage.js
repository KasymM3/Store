// src/pages/CartPage.js
import React, { useEffect, useState } from 'react';
import { api } from '../api';
import { useTranslation } from 'react-i18next';

export default function CartPage() {
    const { t } = useTranslation();
    const [cart, setCart] = useState([]);

    useEffect(() => {
        api.get('/cart')
            .then(r => setCart(r.data))
            .catch(console.error);
    }, []);

    const orderItem = productId => {
        api.post(`/cart/order/${productId}`)
            .then(() => setCart(prev => prev.filter(p => p.productId !== productId)))
            .catch(console.error);
    };

    const checkoutAll = () => {
        api.post('/cart/checkout')
            .then(() => setCart([]))
            .catch(console.error);
    };

    return (
        <div className="container py-4">
            <h1 className="mb-4">{t('cartPage.title')}</h1>

            {cart.length === 0 ? (
                <div className="alert alert-info">{t('cartPage.empty')}</div>
            ) : (
                <>
                    <div className="row gy-4">
                        {cart.map(item => (
                            <div key={item.productId} className="col-12 col-sm-6 col-md-4 col-lg-3 mb-4">
                                <div className="card h-100">
                                    <div className="card-body d-flex flex-column">
                                        <h5 className="card-title">{item.name}</h5>
                                        <p className="card-text">{item.category}</p>
                                        <span className="fw-semibold mb-3">{item.price} $</span>
                                        <button
                                            className="btn btn-primary mt-auto"
                                            onClick={() => orderItem(item.productId)}
                                        >
                                            {t('cartPage.order')}
                                        </button>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                </>
            )}
        </div>
    );
}
