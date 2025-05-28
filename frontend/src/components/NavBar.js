// src/components/NavBar.js
import React, { useState, useEffect } from 'react';
import { Link, NavLink } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

export default function NavBar() {
  const { t, i18n } = useTranslation();
  const [theme, setTheme] = useState(() =>
    localStorage.getItem('theme') === 'dark' ? 'dark' : 'light'
  );

  useEffect(() => {
    localStorage.setItem('theme', theme);
    document.body.classList.remove('light', 'dark');
    document.body.classList.add(theme);
  }, [theme]);

  const toggleTheme = () => setTheme(prev => (prev === 'light' ? 'dark' : 'light'));
  const toggleLanguage = () => i18n.changeLanguage(i18n.language === 'en' ? 'ru' : 'en');

  const navLinkClass =
    'nav-link d-flex align-items-center gap-1 px-2 py-1 fw-medium';
  const btnClass = 'btn btn-sm btn-outline-primary';

  return (
    <nav className="navbar navbar-expand-lg navbar-custom sticky-top">
      <div className="container">
        <Link className="navbar-brand fw-bold" to="/">
          MyShop
        </Link>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav ms-auto mb-2 mb-lg-0 align-items-lg-center">
            <li className="nav-item">
              <NavLink to="/products" className={navLinkClass}>
                {t('navbar.products')}
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/cart" className={navLinkClass}>
                {t('navbar.cart')}
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/orders" className={navLinkClass}>
                {t('navbar.orders')}
              </NavLink>
            </li>
            <li className="nav-item d-flex">
              <button className={btnClass} onClick={toggleTheme}>
                {theme === 'light' ? '🌙' : '☀️'}
              </button>
            </li>
            <li className="nav-item d-flex">
              <button className={btnClass + ' ms-2'} onClick={toggleLanguage}>
                {i18n.language === 'en' ? 'RU' : 'EN'}
              </button>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}