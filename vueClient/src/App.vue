<template>
  <div id="app">
    <ul class="nav">
      <li class="nav-item">
        <a class="nav-link active" href="/">Home</a>
      </li>
      <li class="nav-item">
        <router-link to="/orders" class="nav-link">Orders</router-link>
      </li>
      <li class="nav-item">
        <router-link to="/recipes" class="nav-link">Recipes</router-link>
      </li>
      <li class="nav-item">
        <router-link to="/recipe_ingredients" class="nav-link">Recipe Ingredients</router-link>
      </li>
      <li class="nav-item">
        <router-link to="/products" class="nav-link">Products</router-link>
      </li>
      <li class="nav-item">
        <router-link to="/houses" class="nav-link">Houses</router-link>
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
