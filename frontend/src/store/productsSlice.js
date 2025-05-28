import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import { api } from '../api'

// Загрузка списка продуктов с учётом фильтров q и sort
export const fetchProducts = createAsyncThunk(
    'products/fetchAll',
    async ({ q, sort }) => {
        const params = {}
        if (q)    params.q    = q
        if (sort) params.sort = sort
        const res = await api.get('/products', { params })
        return res.data
    }
)

const productsSlice = createSlice({
    name: 'products',
    initialState: {
        list: [],
        status: 'idle',    // idle | loading | succeeded | failed
        error: null,
    },
    reducers: {},
    extraReducers: builder => {
        builder
            .addCase(fetchProducts.pending, state => {
                state.status = 'loading'
                state.error  = null
            })
            .addCase(fetchProducts.fulfilled, (state, action) => {
                state.status = 'succeeded'
                state.list   = action.payload
            })
            .addCase(fetchProducts.rejected, (state, action) => {
                state.status = 'failed'
                state.error  = action.error.message
            })
    },
})

export default productsSlice.reducer
