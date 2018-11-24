<template>
  <div class="order">
    <h1 v-if="info != null">{{ msg }} ({{ info.data.recipe.name }}) for {{ info.data.userId }}</h1>
    <h1 v-else>{{ msg }}</h1>
    <div class="container">
      <div class="col-lg-1"></div>
      <div class="col-lg-11"  v-if="info != null">
        <p>
          <strong>Recipe name: </strong>{{ info.data.recipe.name }}
        </p>
        <p>
          <strong>Recipe Cost: </strong>{{ info.data.recipe.cost }}
        </p>
        <p>
          <strong>User: </strong>{{ info.data.userId }}
        </p>
        <p>
          <strong>Coffee House: </strong>{{ info.data.coffeeHouseId }}
        </p>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'order',
    data () {
      return {
        msg: 'Coffee Order',
        info: null
      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/orders/' +  this.$route.params.id )
        .then(response => (this.info = response));
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
</style>
