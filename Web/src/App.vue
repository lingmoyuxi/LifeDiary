<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<style lang="scss">

html, body, #app {
  margin: 0;
  padding: 0;
  //height: 100%;
}

#app {
  font-family: cursive, "Source Sans Pro", Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  //text-align: center;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  //width: 100%;
  //height: 100%;
}
</style>
