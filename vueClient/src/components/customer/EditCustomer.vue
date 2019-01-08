<template>
  <div class="edit_customer">
    <h1>{{ msg }}</h1>
    <br>
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
      <form id="edit_customer_form" @submit="submitForm">
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
            <p>
              <input class="btn btn-primary" type="submit" value="Edit" >
            </p>
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
    name: 'edit_customer',
    data () {
      return {
        msg: 'Edit Coffee Customer',
        showDangerAlert: false,
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
            .put('http://localhost:5055/customers/' +  this.$route.params.id, {
              first_name: this.first_name,
              last_name: this.last_name,
              gender: this.gender,
              email: this.email,
              phone: this.phone
            }, {
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*"
              }})
            .then(function (response) {
              console.log(response);
              window.location = 'http://localhost:5000/customers/' + response.data.id;
            })
            .catch(error => {
              console.log(error);
              this.showDangerAlert = true;
            });
        }
        e.preventDefault();

      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/customers/' +  this.$route.params.id )
        .then(response_f => {
          this.first_name = response_f.data.firstName;
          this.last_name = response_f.data.lastName;
          this.gender = response_f.data.gender;
          this.email = response_f.data.email;
          this.phone = response_f.data.phone;
        })
        .catch(error => {
          console.log(error);
          this.showDangerAlert = true;
        });
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }
</style>
