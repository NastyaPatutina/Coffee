<template>
  <span v-if="info != null" class="user_name">
    {{ info.data.firstName }} {{ info.data.lastName }}
  </span>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'user_name',
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
          .get('http://localhost:5055/users/' + this.id)
          .then(response => (this.info = response));
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
