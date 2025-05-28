import { configureStore } from '@reduxjs/toolkit'
import productsReducer from './productsSlice'
import likesReducer    from './likesSlice'
import cartReducer     from './cartSlice'

export const store = configureStore({
    reducer: {
        products: productsReducer,
        likes:    likesReducer,
        cart:     cartReducer,
    },
})
