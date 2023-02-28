<template>
    <div class="home">
        <el-input placeholder="请输入内容" v-model="input" clearable></el-input>
    </div>
</template>

<script>
// @ is an alias to /src
export default {
    name: "TestView",
    data() {
        return {
            input: ""
        }
    },
    components: {
    },
    created() {
        // this.$axios.get("/page").then(function (response) {
        //     console.log(response);
        // })
    }
}
</script>
