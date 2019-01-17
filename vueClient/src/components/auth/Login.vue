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
    </div>
    <form class="form-signin" @submit="submitForm">
      <div class="col-lg-6">
        <h2 class="form-signin-heading">Please sign in</h2>
        <p>
          <input v-model="username" class="form-control" placeholder="Username" required autofocus>
        </p>
        <p>
          <input v-model="password" class="form-control" placeholder="Password" required>
        </p>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </div>
    </form>
  </div>
</template>

<script>
  import axios from 'axios'

  export var AUTH_TOKEN = "";

  function checkForm (e) {
    if (e.username && e.password) {
      return true;
    }

    e.errors = [];

    if (!e.first_name) {
      e.errors.push('Please, write username.');
    }
    if (!e.last_name) {
      e.errors.push('Please, write password.');
    }
    return false;
  }

  export default {
    name: 'Login',
    data () {
      return {
        showDangerAlert: false,
        username: null,
        password: null,
        errors: []
      }
    }, methods: {
      submitForm: function (e) {

        if (checkForm(this)){
          axios
            .post('http://localhost:5055/auth/', {
              username: this.username,
              password: this.password
            }, {
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*",
                "crossDomain": true
              }})
            .then(function (response) {
              AUTH_TOKEN = response.headers.authorization.split(" ")[1];
              console.log(AUTH_TOKEN);
              // window.location = 'http://localhost:5000';
            })
            .catch(error => {
              console.log(error);
              this.showDangerAlert = true;
            });
        }
        e.preventDefault();
      }
    }
  }
</script>

<style scoped>

</style>
