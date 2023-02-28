/**
 * 请求登录验证，如果没有登录，不能访问页面，返回到登录页面
 */
import router from "./router";
import Element from "element-ui";

//路由判断登录，根据路由配置文件的参数
router.beforeEach((to, from, next) => {
    //判断该路由是否需要登录权限
    //record.meta.requireAuth是获取到该请求中携带的该参数
    if (to.matched.some(record => record.meta.requireAuth)) {
        //获取到本地的token
        const token = localStorage.getItem("token")
        //判断当前的token是否存在，也就是登录时的token
        if (token) {
            //如果指向的是登录页面，不做任何操作
            //如果不是登录页面，且token存在，就放行
            if (to.path !== "/login") {
                next()
            }
        } else {
            //    如果token不存在
            //    前往登录
            Element.Message.error("请先登录！");
            next({path: '/login'})
        }

    } else {
        //如果不需要登录认证，就直接访问
        next()
    }
})