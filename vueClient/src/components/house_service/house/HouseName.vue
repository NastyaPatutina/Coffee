<template>
  <span v-if="info != null" class="house_name">
    {{ info.data.name }}
  </span>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'house_name',
    props: {
      id: {
        type: Number,
        required: true
      }
    },
    data () {
      return {
        info: null
      }
    },
    mounted() {
      if (this.id) {
        axios
          .get('http://localhost:5055/houses/' + this.id, {
            headers: {
              'Content-Type': 'application/json;charset=UTF-8',
              "Access-Control-Allow-Origin": "*",
              "crossDomain": true,
              "Authorization": `Bearer ${localStorage.getItem("auth")}`
            }})
          .then(response => (this.info = response))
          .catch(error => {
            console.log(error);
          });
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
