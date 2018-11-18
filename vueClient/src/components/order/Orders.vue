<template>
  <div class="orders">
    <button type="button" class="btn btn-primary"><router-link :to="{name: 'NewOrder'}" class="nav-link">New Order</router-link></button>
    <h1>{{ msg }}</h1>
  </div>
</template>

<script>
  export default {
    name: 'orders',
    data () {
      return {
        msg: 'Coffee Orders'
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }
  ul {
    list-style-type: none;
    padding: 0;
  }
  li {
    display: inline-block;
    margin: 0 10px;
  }
  a {
    color: #42b983;
  }
  button {

  }
</style>
