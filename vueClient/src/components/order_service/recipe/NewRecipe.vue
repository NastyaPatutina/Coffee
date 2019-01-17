<template>
  <div class="new_recipe">
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
      <form id="new_recipe_form" @submit="submitForm">
        <div class="form-group">
          <div class="col-lg-6">
            <div v-if="errors.length">
              <b>Please, correct this this mistakes:</b>
              <ul>
                <li v-for="error in errors">{{ error }}</li>
              </ul>
            </div>
            <input v-model="name" placeholder="Recipe name" class="form-control" >
            <br>
            <input type="number" v-model="cost" placeholder="Recipe cost" class="form-control" min="0">
            <br>
            <div class="container">
              <div class="row">
                <input class="btn btn-primary" type="submit" value="Create" >
                <router-link to="/recipes" class="btn nav-link col-lg-2">Back</router-link>
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
  import { AUTH_TOKEN } from "@/components/auth/Login"

  function checkForm (e) {
    return true;
  }

  export default {
    name: 'new_recipe',
    data () {
      return {
        msg: 'New Coffee Recipe',
        showDangerAlert: false,
        cost: null,
        name: null,
        errors: []
      }
    }, methods: {
      submitForm: function (e) {

        if (checkForm(this)){
          axios
            .post('http://localhost:5055/recipes/', {
              cost: this.cost,
              name: this.name
            }, {
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*",
                "Authorization": `Bearer ${AUTH_TOKEN}`
              }})
            .then(function (response) {
              console.log(response);
              window.location = 'http://localhost:5000/recipes/' + response.data.id;
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

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }
</style>
