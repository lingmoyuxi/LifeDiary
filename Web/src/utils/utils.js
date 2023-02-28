
import router from "@/router";
const tools = {
    getToken() {
        return localStorage.getItem('token')
    },
    getUserInfo() {
        let userinfoJson = localStorage.getItem('userInfo')
        if (userinfoJson) return JSON.parse(userinfoJson);
        else return null;
    },
    login(userInfo){
        localStorage.setItem("token", userInfo.token)
        localStorage.setItem("userInfo", JSON.stringify(userInfo))
    },
    loginOut(){
        localStorage.removeItem("userInfo")
        localStorage.removeItem("token")
        router.push('/login')
    }
};
export default tools;