import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import { api } from '../api'

// Получить лайки по продукту
export const fetchLikes = createAsyncThunk(
    'likes/fetchByProduct',
    async (productId) => {
        const res = await api.get(`/products/${productId}/likes`)
        return { productId, ...res.data }
    }
)

// Переключить лайк
export const toggleLike = createAsyncThunk(
    'likes/toggle',
    async (productId) => {
        const res = await api.post(`/products/${productId}/like`)
        return { productId, ...res.data }
    }
)

const likesSlice = createSlice({
    name: 'likes',
    initialState: { entities: {}, error: null },
    reducers: {},
    extraReducers: builder => {
        builder
            .addCase(fetchLikes.fulfilled, (state, action) => {
                state.entities[action.payload.productId] = {
                    liked: action.payload.liked,
                    count: action.payload.count,
                }
            })
            .addCase(toggleLike.fulfilled, (state, action) => {
                state.entities[action.payload.productId] = {
                    liked: action.payload.liked,
                    count: action.payload.count,
                }
            })
            .addMatcher(
                action => action.type.startsWith('likes/') && action.error,
                (state, action) => { state.error = action.error.message }
            )
    },
})

export default likesSlice.reducer
