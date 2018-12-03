<template>
  <div class="product_name">
    {{ info.data.name }}
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'product_name',
    data () {
      return {
        info: null
      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/products/' +  this.$route.params.id )
        .then(response => (this.info = response));
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }
</style>
