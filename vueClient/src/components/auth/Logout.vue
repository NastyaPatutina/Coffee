<template>
  <div class="container">
    <div class="col-lg-1"></div>
    <div>
      <b-alert variant="danger"
               dismissible
               :show="showDangerAlert"
               @dismissed="showDangerAlert=false">
        Something went wrong... Sorry, try later...
      </b-alert>
      <h1>{{msg}}</h1>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'Logout',
    data () {
      return {
        showDangerAlert: false,
        msg: "Hmmmm...  ",
        errors: []
      }
    },
    mounted() {
      axios
        .post('http://localhost:5055/logout', {
        headers: {
          'Content-Type': 'application/json;charset=UTF-8',
          "Access-Control-Allow-Origin": "*",
          "crossDomain": true
        },
            maxRedirects: 0
        })
        .then(response => {
          console.log(response);
          this.msg = "You have been signed out";
        })
        .catch(error => {
          console.log(error);
          this.showDangerAlert = true;
        });
    }
  }
</script>

<style scoped>

</style>
