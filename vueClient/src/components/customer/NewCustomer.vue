<template>
  <div class="new_customer">
    <h1>{{ msg }}</h1>
    <br>
    <div class="container">
      <div class="col-lg-1"></div>
      <div>
        <b-alert variant="warning"
                 dismissible
                 :show="showWarningAlert"
                 @dismissed="showWarningAlert=false">
          Sorry ... Access denied...
        </b-alert>
        <b-alert variant="danger"
                 dismissible
                 :show="showDangerAlert"
                 @dismissed="showDangerAlert=false">
          Something went wrong... Sorry, try later...
        </b-alert>
      </div>
      <form id="new_customer_form" @submit="submitForm">
        <div class="form-group">
          <div class="col-lg-6">
            <div v-if="errors.length">
              <b>Please, correct this this mistakes:</b>
              <ul>
                <li v-for="error in errors">{{ error }}</li>
              </ul>
            </div>
            <input v-model="first_name" placeholder="First name" class="form-control" >
            <br>
            <input v-model="last_name" placeholder="Last name" class="form-control" >
            <br>
            <v-select v-model="gender" :options="['male','female']" placeholder="Select gender"></v-select>
            <br>
            <input v-model="email" placeholder="E-mail" class="form-control" >
            <br>
            <input v-model="phone" placeholder="phone" class="form-control" >
            <br>
            <div class="container">
              <div class="row">
                <input class="btn btn-primary" type="submit" value="Create" >
                <router-link to="/customers" class="btn nav-link col-lg-2">Back</router-link>
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'

  function checkForm (e) {
    if (e.first_name && e.last_name && e.gender &&
      e.email && e.phone) {
      return true;
    }

    e.errors = [];

    if (!e.first_name) {
      e.errors.push('Please, write customer first name.');
    }
    if (!e.last_name) {
      e.errors.push('Please, write customer last name.');
    }
    if (!e.gender) {
      e.errors.push('Please, write customer gender.');
    }
    if (!e.email) {
      e.errors.push('Please, write customer e-mail.');
    }
    if (!e.phone) {
      e.errors.push('Please, write customer phone.');
    }
    return false;
  }

  export default {
    name: 'new_customer',
    data () {
      return {
        msg: 'New Coffee Customer',
        showDangerAlert: false,
        showWarningAlert: false,
        first_name: null,
        last_name: null,
        gender: null,
        email: null,
        phone: null,
        errors: []
      }
    }, methods: {
      submitForm: function (e) {

        if (checkForm(this)){
          axios
            .post('http://localhost:5055/customers/', {
              first_name: this.first_name,
              last_name: this.last_name,
              gender: this.gender,
              email: this.email,
              phone: this.phone
            }, {
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*",
                "Authorization": `Bearer ${localStorage.getItem("auth")}`
              }})
            .then(function (response) {
              console.log(response);
              window.location = 'http://localhost:5000/customers/' + response.data.id;
            })
            .catch(error => {
              console.log(error);
              if (error.response.status == 401 || error.response.status == 403) {
                this.showWarningAlert = true;
                return
              }
              this.showDangerAlert = true;
            });
        }
        e.preventDefault();
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
