import axios from "../custom-axios/axios";

const MemberService = {
    registration: (username,email,password,repeat,firstName,lastName) => {
        let params = "?username="+ username+
            "&email="+email+
            "&password="+password+
            "&repeat="+repeat+
            "&firstName="+firstName+
            "&lastName="+lastName;
        return axios.post("/api/members/registration"+ params);
    },
    login: (username, passwod) => {
        return axios.get("/api/members/login?username="+username + "&password="+passwod);
    },
    verify: (code, username) => {
        return axios.post("/api/members/verify?code="+code + "&username="+username);
    }

};

export default MemberService;