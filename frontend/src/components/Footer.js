import { useEffect } from 'react';
import SockJS from 'sockjs-client';
import Stomp  from 'stompjs';

export default function Footer() {
    useEffect(() => {
        const socket = new SockJS('/ws');
        const client = Stomp.over(socket);
        client.connect({}, frame => {
            client.subscribe('/topic/orders', msg => {
                const data = JSON.parse(msg.body);
                alert(`🔔 Заказ #${data.id} → ${data.state}`);
            });
        }, console.error);
    }, []);

    return null; // данные скрипты нам не нужно рендерить как DOM-элемент
}
