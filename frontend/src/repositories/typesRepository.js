import axios from "../custom-axios/axios";

const TypeService = {
    allTypes: () => {
        return axios.get("/api/types");
    },
    productsByType: (type) => {
        return axios.get("/api/types/by-type", type);
    },
    add:(type) => {
        return axios.post("/api/types/add", type);
    }
}

export default TypeService;