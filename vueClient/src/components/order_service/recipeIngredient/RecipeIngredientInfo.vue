<template>
  <div class="recipe_ingredients" v-if="info != null">
    <p>
      <strong>Product name: </strong><ProductName v-bind:id="info.productId"/>
    </p>
    <p>
      <strong>Product count: </strong>{{ info.count }}
    </p>
  </div>
</template>

<script>
  import axios from 'axios'
  import ProductName from '../../house_service/product/ProductName.vue'

  export default {
    name: 'recipe_ingredients',
    props: {
      info: {
        type: Object,
        required: true
      }
    },
    components: {
      ProductName
    },
    data () {
      return {
        msg: 'Coffee Recipe Ingredient'
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }
</style>
