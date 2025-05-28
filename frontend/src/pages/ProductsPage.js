// src/pages/ProductsPage.js
import React, { useEffect, useContext } from 'react'
import { Link, useSearchParams, useNavigate } from 'react-router-dom'
import { useTranslation } from 'react-i18next'
import { AuthContext } from '../contexts/AuthContext'
import { useSelector, useDispatch } from 'react-redux'

import { fetchProducts } from '../store/productsSlice'
import { fetchLikes, toggleLike } from '../store/likesSlice'
import { fetchCart, addToCart } from '../store/cartSlice'

export default function ProductsPage() {
    const { t } = useTranslation()
    const { userRoles } = useContext(AuthContext)
    const navigate = useNavigate()
    const dispatch = useDispatch()

    const [sp, setSp] = useSearchParams()
    const q    = sp.get('q')    || ''
    const sort = sp.get('sort') || ''

    // Данные из Redux
    const { list: products, status: prodStatus, error: prodError } = useSelector(state => state.products)
    const likes   = useSelector(state => state.likes.entities)
    const { items: cart } = useSelector(state => state.cart)

    // Загрузка продуктов при изменении фильтров
    useEffect(() => {
        dispatch(fetchProducts({ q, sort }))
    }, [q, sort, dispatch])

    // Подгрузка лайков для каждого продукта
    useEffect(() => {
        products.forEach(p => dispatch(fetchLikes(p.id)))
    }, [products, dispatch])

    // Загрузка корзины один раз
    useEffect(() => {
        dispatch(fetchCart())
    }, [dispatch])

    // Фильтры
    const applyFilters = e => {
        e.preventDefault()
        const data = new FormData(e.target)
        const params = {}
        for (let [k, v] of data.entries()) if (v) params[k] = v
        setSp(params)
    }

    // Лайк
    const onToggleLike = id => {
        if (!userRoles?.length) {
            alert(t('productsPage.loginToLike'))
            return navigate('/login')
        }
        dispatch(toggleLike(id))
            .unwrap()
            .catch(err => {
                if (err.status === 401) {
                    alert(t('productsPage.loginToLike'))
                    navigate('/login')
                }
            })
    }

    // В корзину
    const onAddToCart = id => {
        dispatch(addToCart(id))
            .unwrap()
            .catch(() => alert(t('productsPage.addToCartError')))
    }

    // Отрисовка по статусам
    if (prodStatus === 'loading') return <div className="text-center py-5">{t('productsPage.loading')}</div>
    if (prodStatus === 'failed')  return <div className="alert alert-danger">{prodError}</div>

    return (
        <div className="container py-4">
            {/* — шапка и фильтры без изменений — */}
            <div className="page-header mb-4">
                <div className="row align-items-center gy-2">
                    <div className="col-md-4">
                        <h2 className="mb-0">{t('productsPage.title')}</h2>
                        <Link to="/products/new" className="btn btn-success mt-3">
                            {t('productsPage.addProduct')}
                        </Link>
                        {userRoles.includes('ROLE_ADMIN') && (
                            <Link to="/admin" className="btn btn-danger mt-3 ms-2">
                                {t('productsPage.adminPanel')}
                            </Link>
                        )}
                        <Link to="/profile" className="btn btn-primary mt-3 ms-2">
                            {t('productsPage.profile')}
                        </Link>
                    </div>
                    <div className="col-md-8">
                        <form onSubmit={applyFilters} className="search-group d-flex flex-column flex-md-row align-items-center gap-2">
                            <input
                                name="q"
                                defaultValue={q}
                                className="form-control"
                                type="search"
                                placeholder={t('productsPage.searchPlaceholder')}
                            />
                            <select name="sort" defaultValue={sort} className="form-select">
                                <option value="">{t('productsPage.sort.none')}</option>
                                <option value="priceAsc">{t('productsPage.sort.priceAsc')}</option>
                                <option value="priceDesc">{t('productsPage.sort.priceDesc')}</option>
                                <option value="nameAsc">{t('productsPage.sort.nameAsc')}</option>
                                <option value="nameDesc">{t('productsPage.sort.nameDesc')}</option>
                            </select>
                            <button className="btn btn-outline-primary" type="submit">
                                {t('productsPage.applyFilters')}
                            </button>
                        </form>
                    </div>
                </div>
            </div>

            {/* — список продуктов — */}
            <div className="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
                {products.map(p => (
                    <div key={p.id} className="col">
                        <div className="card product-card h-100">
                            <img
                                src={p.imageUrl || 'https://via.placeholder.com/160'}
                                alt={p.name}
                                className="product-img w-100"
                            />
                            <div className="card-body d-flex flex-column">
                                <h5 className="card-title">{p.name}</h5>
                                <p className="card-text text-truncate">{p.description}</p>
                                <div className="mt-auto">
                                    <div className="d-flex justify-content-between align-items-center mb-2">
                                        <span className="fw-semibold">{p.price} $</span>
                                        <div>
                                            <button
                                                className="btn btn-outline-danger btn-sm me-2"
                                                onClick={() => onToggleLike(p.id)}
                                            >
                                                {likes[p.id]?.liked ? '❤️' : '🤍'} {likes[p.id]?.count || 0}
                                            </button>
                                            <button
                                                className="btn btn-primary btn-sm"
                                                onClick={() => onAddToCart(p.id)}
                                            >
                                                {t('productsPage.toCart')}
                                            </button>
                                        </div>
                                    </div>
                                    <p className="text-sm text-muted">
                                        {t('productsPage.addedBy')}: {p.addedBy ? p.addedBy.name : t('productsPage.unknown')}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>

            {/* — корзина — */}
            <div className="mt-5">
                <h2>{t('productsPage.yourCart')}</h2>
                {cart.length === 0 ? (
                    <div>{t('productsPage.cartEmpty')}</div>
                ) : (
                    <ul className="list-group">
                        {cart.map((item, idx) => (
                            <li key={idx} className="list-group-item d-flex justify-content-between align-items-center">
                                {item.name}
                                <span className="badge bg-primary rounded-pill">{item.quantity}</span>
                            </li>
                        ))}
                    </ul>
                )}
            </div>
        </div>
    )
}
