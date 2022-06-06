import axios from "../custom-axios/axios";

const OrderService = {
    allOrders: (username) => {
        if(username != undefined) {
            return axios.get("/api/orders?username=" + username);
        }
        return axios.get("/api/orders");
    },
    add: (order) => {
        return axios.post("/api/orders/save", order);
    },
    addTracking:(id, number) => {
        return axios.post("/api/orders/edit?number="+ number+"&id=" + id);
    }
}

export default OrderService;