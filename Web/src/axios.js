import axios from "axios";
import router from "./router";
import Element from "element-ui";

// axios.defaults.baseURL = 'http://loaclhost:8080'
axios.defaults.baseURL = process.env.VUE_APP_SERVER;

const request = axios.create({
	timeout: 5000,
	headers: {
		"Content-Type": "application/json; charset=utf-8",
	},
});

request.interceptors.request.use((config) => {
	if (!config.url.match(/https?:\/\//g)) {
		config.headers["token"] = localStorage.getItem("token");
	}
	return config;
});

request.interceptors.response.use(
	(response) => {
		if (response.config.url.indexOf(process.env.VUE_APP_SERVER) !== -1) {
			let res = response.data;
			if (res.code === 200) return response.data;
			else {
				if (res.code === 301 || res.code === 401) {
					Element.Message.error(res.msg);
					router.push({path: "/login"});
					return Promise.reject(res.msg);
				}
				Element.Message.error(
					!res.msg ? "系统异常，请联系管理员!!" : res.msg
				);
				return Promise.reject(res.msg);
			}
		} else {
			return response.data
		}
	},
	(error) => {
		if (error.response) {
			if (error.response.data)
				error.message = error.response.data.message;
			if (error.response.status === 401) router.push("/login");
		}
		Element.Message.error("请求出错:" + error.message, { duration: 3000 });
		console.log(error)
		return Promise.reject("请求出错:" + error.message);
	}
);

export default request;
