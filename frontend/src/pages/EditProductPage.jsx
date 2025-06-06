
import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { api } from '../api';
import { useTranslation } from 'react-i18next';

export default function EditProductPage() {
    const { t } = useTranslation();
    const { id } = useParams();
    const navigate = useNavigate();

    const [product, setProduct] = useState({
        name:        '',
        description: '',
        price:       0,
        imageUrl:    '',
        category:    ''
    });
    const [categories, setCategories] = useState([]);
    const [loading, setLoading]     = useState(true);
    const [error, setError]         = useState(null);

    useEffect(() => {
        setCategories(['Digital', 'Physical']);
        api.get(`/products/${id}`)
            .then(res => {
                setProduct(res.data);
                setLoading(false);
            })
            .catch(err => {
                console.error('Ошибка загрузки товара:', err);
                setError(t('editProductPage.loadError'));
                setLoading(false);
            });
    }, [id, t]);

    const handleChange = e => {
        const { name, value } = e.target;
        setProduct(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async e => {
        e.preventDefault();
        try {
            await api.put(`/products/${id}`, product);
            navigate('/admin', { replace: true });
        } catch (err) {
            console.error('Ошибка при сохранении:', err);
            alert(t('editProductPage.saveError', { message: err.response?.data || err.message }));
        }
    };

    return (
        <div className="container py-4">
            <div className="card mx-auto w-100" style={{ maxWidth: '600px' }}>
                <div className="card-header">
                    <h2 className="h5 mb-0">
                        {t('editProductPage.title', { id })}
                    </h2>
                </div>
                <div className="card-body">
                    {loading && <p>{t('editProductPage.loading')}</p>}
                    {error   && <p className="text-danger">{error}</p>}

                    {!loading && !error && (
                        <form onSubmit={handleSubmit}>
                            <div className="mb-3">
                                <label className="form-label">{t('editProductPage.nameLabel')}</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    name="name"
                                    value={product.name}
                                    onChange={handleChange}
                                    required
                                />
                            </div>

                            <div className="mb-3">
                                <label className="form-label">{t('editProductPage.descriptionLabel')}</label>
                                <textarea
                                    className="form-control"
                                    rows="3"
                                    name="description"
                                    value={product.description}
                                    onChange={handleChange}
                                />
                            </div>

                            <div className="mb-3">
                                <label className="form-label">{t('editProductPage.priceLabel')}</label>
                                <input
                                    type="number"
                                    step="0.01"
                                    className="form-control"
                                    name="price"
                                    value={product.price}
                                    onChange={handleChange}
                                    required
                                />
                            </div>

                            <div className="mb-3">
                                <label className="form-label">{t('editProductPage.imageUrlLabel')}</label>
                                <input
                                    type="url"
                                    className="form-control"
                                    name="imageUrl"
                                    value={product.imageUrl}
                                    onChange={handleChange}
                                />
                            </div>

                            <div className="mb-3">
                                <label className="form-label">{t('editProductPage.categoryLabel')}</label>
                                <select
                                    className="form-select"
                                    name="category"
                                    value={product.category}
                                    onChange={handleChange}
                                    required
                                >
                                    <option value="" disabled>
                                        {t('editProductPage.categoryPlaceholder')}
                                    </option>
                                    {categories.map(c => (
                                        <option key={c} value={c}>{c}</option>
                                    ))}
                                </select>
                            </div>

                            <div className="d-flex flex-column flex-md-row justify-content-between gap-2">
                                <button type="submit" className="btn btn-success">
                                    {t('editProductPage.save')}
                                </button>
                                <button
                                    type="button"
                                    className="btn btn-secondary"
                                    onClick={() => navigate('/admin', { replace: true })}
                                >
                                    {t('editProductPage.cancel')}
                                </button>
                            </div>
                        </form>
                    )}
                </div>
            </div>
        </div>
    );
}
