<template>
  <div id="app">
    <ul class="nav">
      <li class="nav-item">
        <a class="nav-link active" href="/">Home</a>
      </li>
      <li class="nav-item">
        <router-link to="/orders"  class="nav-link">Orders</router-link>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Link</a>
      </li>
      <li class="nav-item">
        <a class="nav-link disabled" href="#">Disabled</a>
      </li>
    </ul>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  margin-top: 60px;
}

h1 {
    text-align: center;

}
  button {
    margin-bottom: 15px;
  }
  button a {
    color: white;
  }
</style>
