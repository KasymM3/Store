
import React, { useState, useEffect, useCallback } from 'react';
import { Link } from 'react-router-dom';
import { api } from '../api';
import { useTranslation } from 'react-i18next';

export default function AdminPage() {
    const { t } = useTranslation();
    const [products, setProducts] = useState([]);
    const [loading, setLoading]   = useState(true);
    const [error, setError]       = useState(null);

    const fetchProducts = useCallback(() => {
        setLoading(true);
        setError(null);
        api.get('/products')
            .then(res => setProducts(res.data))
            .catch(() => setError(t('adminPage.loadError')))
            .finally(() => setLoading(false));
    }, [t]);

    useEffect(() => {
        fetchProducts();
    }, [fetchProducts]);

    const handleDelete = id => {
        if (!window.confirm(t('adminPage.deleteConfirm'))) return;
        api.delete(`/products/${id}`)
            .then(() => {
                setProducts(prev => prev.filter(p => p.id !== id));
            })
            .catch(() => alert(t('adminPage.deleteError')));
    };

    return (
        <div className="container py-4">
            <h2 className="mb-4">{t('adminPage.title')}</h2>

            <div className="d-flex flex-column flex-md-row gap-2 mb-4">
                <Link to="/products/new" className="btn btn-success">
                    {t('adminPage.addNew')}
                </Link>
                <Link to="/products" className="btn btn-secondary">
                    {t('adminPage.backToCatalog')}
                </Link>
            </div>

            {loading && <p>{t('adminPage.loading')}</p>}
            {error   && <p className="text-danger">{error}</p>}

            {!loading && !error && (
                <div className="table-responsive">
                    <table className="table table-hover">
                        <thead>
                        <tr>
                            <th>{t('adminPage.header.id')}</th>
                            <th>{t('adminPage.header.name')}</th>
                            <th>{t('adminPage.header.price')}</th>
                            <th>{t('adminPage.header.category')}</th>
                            <th>{t('adminPage.header.actions')}</th>
                        </tr>
                        </thead>
                        <tbody>
                        {products.map(p => (
                            <tr key={p.id}>
                                <td>{p.id}</td>
                                <td>{p.name}</td>
                                <td>{p.price}</td>
                                <td>{p.category?.name || '—'}</td>
                                <td>
                                    <Link
                                        to={`/products/${p.id}/edit`}
                                        className="btn btn-sm btn-primary me-2"
                                    >
                                        {t('adminPage.edit')}
                                    </Link>
                                    <button
                                        className="btn btn-sm btn-danger"
                                        onClick={() => handleDelete(p.id)}
                                    >
                                        {t('adminPage.delete')}
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
}
