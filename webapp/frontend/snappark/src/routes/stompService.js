// stompService.js

const stompService = {
    stompClient: null,
    messages: [],

    connect: () => {
        // Your Stomp connection logic here
        // Example: stompService.stompClient = Stomp.client('ws://your-socket-server');
    },

    disconnect: () => {
        // Your Stomp disconnection logic here
    },

    subscribe: (callback) => {
        // Subscribe to Stomp messages and update the messages array
        stompService.stompClient.subscribe('/alerts/1', (message) => {
            const newMessage = JSON.parse(message.body);
            stompService.messages = [...stompService.messages, newMessage];
            callback(stompService.messages);
        });
    },
};

export default stompService;