// src/App.js
import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { PrivateRoute } from './components/PrivateRoute';

import { AuthProvider }       from './contexts/AuthContext';
import ProtectedRoute         from './components/ProtectedRoute';

import NavBar                 from './components/NavBar';
import Footer                 from './components/Footer';

import LoginPage              from './pages/LoginPage';

import ProductsPage           from './pages/ProductsPage';
import ProductFormPage        from './pages/ProductFormPage';
import CartPage               from './pages/CartPage';
import OrdersPage             from './pages/OrdersPage';
import OrderFormPage          from './pages/OrderFormPage';
import OrderDetailPage        from './pages/OrderDetailPage';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';


import './App.css';
import AdminPage from "./pages/AdminPage";
import Register from "./pages/Register";
import ResetPassword from "./pages/ResetPassword";
import ProfilePage from './pages/ProfilePage.jsx';
import EditProductPage from "./pages/EditProductPage";
import './index.css';
function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <NavBar />

                <Routes>
                    {/* public */}
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/register" element={<Register/>} />
                    <Route path="/reset-password" element={<ResetPassword />} />

                    <Route element={<PrivateRoute />}>

                    <Route path="/profile" element={<ProtectedRoute><ProfilePage /> </ProtectedRoute>}/>
                    {/* redirect root */}
                    <Route path="/" element={<Navigate to="/products" replace />} />

                    {/* protected */}
                    <Route
                        path="/products"
                        element={
                            <ProtectedRoute>
                                <ProductsPage />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/products/new"
                        element={
                            <ProtectedRoute>
                                <ProductFormPage />
                            </ProtectedRoute>
                        }
                    />
                        <Route path="/admin" element={<AdminPage />} />
                        <Route path="/products/:id/edit" element={<EditProductPage />} />
                    <Route
                        path="/cart"
                        element={
                            <ProtectedRoute>
                                <CartPage />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/orders"
                        element={
                            <ProtectedRoute>
                                <OrdersPage />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/orders/new"
                        element={
                            <ProtectedRoute>
                                <OrderFormPage />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/orders/:id"
                        element={
                            <ProtectedRoute>
                                <OrderDetailPage />
                            </ProtectedRoute>
                        }
                    />

                    </Route>
                </Routes>

                <Footer />
            </BrowserRouter>
        </AuthProvider>
    );
}

export default App;
