import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import { api } from '../api'

// Загрузка корзины
export const fetchCart = createAsyncThunk(
    'cart/fetchAll',
    async () => {
        const res = await api.get('/cart')
        return res.data
    }
)

// Добавить в корзину
export const addToCart = createAsyncThunk(
    'cart/addItem',
    async (productId) => {
        const res = await api.post('/cart', { productId })
        return res.data
    }
)

const cartSlice = createSlice({
    name: 'cart',
    initialState: { items: [], status: 'idle', error: null },
    reducers: {},
    extraReducers: builder => {
        builder
            .addCase(fetchCart.pending,    state => { state.status = 'loading'; state.error = null })
            .addCase(fetchCart.fulfilled,  (state, action) => { state.status = 'succeeded'; state.items = action.payload })
            .addCase(fetchCart.rejected,   (state, action) => { state.status = 'failed';    state.error = action.error.message })
            .addCase(addToCart.pending,    state => { state.status = 'loading' })
            .addCase(addToCart.fulfilled,  (state, action) => { state.status = 'succeeded'; state.items = action.payload })
            .addCase(addToCart.rejected,   (state, action) => { state.status = 'failed';    state.error = action.error.message })
    },
})

export default cartSlice.reducer
