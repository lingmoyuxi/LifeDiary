import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "@/views/HomeView.vue";
import TestView from "@/views/TestView.vue";
import LoginView from "@/views/LoginView.vue";
import LayoutView from "@/views/LayoutVIew.vue";
import EditorView from "@/views/EditorView.vue";
import TimeAxisView from '@/views/TimeAxisView.vue'
import UserSettingView from '@/views/UserSettingView.vue'

Vue.use(VueRouter);

const routes = [
    {
        path: "/",
        name: "Layout",
        component: LayoutView,
        itemView:true,
        redirect:"/home",
        meta: {
            requireAuth: true
        },
        children: [
            {
                path: "home",
                name: "HomeView",
                component: HomeView,
            },
            {
                path: "editor",
                name: "EditorView",
                component: EditorView,
            }, {
                path: "time",
                name: "TimeAxisView",
                component: TimeAxisView,
            },
            {
                path: "userSetting",
                name: "UserSettingView",
                component: UserSettingView,
            }

        ]
    },
    {
        path: "/test",
        name: "TestView",
        component: TestView,
    },
    {
        path: "/login",
        name: "Login",
        component: LoginView,
    },
];

const router = new VueRouter({
    mode: "history",
    routes: routes,
});

export default router;
