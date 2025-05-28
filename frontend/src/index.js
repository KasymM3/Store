import React from 'react';

import './index.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import './styles/theme.css';
import ReactDOM from 'react-dom/client';
import './App.css';
import App from './App';
import './i18n';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import { store } from './store'
import {Provider} from "react-redux";




const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
      <Provider store={store}>
              <App />
            </Provider>,
  </React.StrictMode>
);