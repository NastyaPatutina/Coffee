<template>
  <div class="new_order">
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
      <form id="new_order_form" @submit="submitForm">
        <div class="form-group">
          <div class="col-lg-6">
            <div v-if="errors.length">
              <b>Please, correct this this mistakes:</b>
              <ul>
                <li v-for="error in errors">{{ error }}</li>
              </ul>
            </div>
            <v-select id="userId" v-model="userId" v-if="users != null" :options="users" placeholder="Select user">
            </v-select>
            <br>
            <v-select id="recipeId" v-model="recipeId" v-if="recipes != null" :options="recipes" placeholder="Select recipe">
            </v-select>
            <br>
            <v-select id="houseId" v-model="houseId" v-if="houses != null" :options="houses" placeholder="Select coffee house">
            </v-select>
            <br>
            <div class="container">
              <div class="row">
                <input class="btn btn-primary" type="submit" value="Create" >
                <router-link to="/orders" class="btn nav-link col-lg-2">Back</router-link>
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

  function SelectIdAndValueForUser(users) {
    var res = [];
    users.forEach(function(user) {
      res.push({value : user.id, label: user.firstName + " " + user.lastName });
    });
    return res;
  }

  function SelectIdAndValueForRecipe(recipes) {
    var res = [];
    recipes.forEach(function(recipe) {
      res.push({value : recipe.id, label: recipe.name });
    });
    return res;
  }

  function SelectIdAndValueForCoffeeHouse(houses) {
    var res = [];
    houses.forEach(function(house) {
      res.push({value : house.id, label: house.name + "(" + house.address + ")" });
    });
    return res;
  }

  function checkForm (e) {
    if (e.userId && e.recipeId && e.houseId) {
      return true;
    }

    e.errors = [];

    if (!e.userId) {
      e.errors.push('Please, select user.');
    }
    if (!e.recipeId) {
      e.errors.push('Please, select recipe.');
    }
    if (!e.houseId) {
      e.errors.push('Please, select house.');
    }

    return false;
  }

  export default {
    name: 'new_order',
    data () {
      return {
        msg: 'New Coffee Order',
        showDangerAlert: false,
        userId: null,
        recipeId: null,
        houseId: null,
        users: null,
        recipes: null,
        houses: null,
        errors: []
      }
    }, methods: {
      submitForm: function (e) {

        if (checkForm(this)){
          axios
            .post('http://localhost:5055/orders/', {
              userId: this.userId.value,
              recipeId: this.recipeId.value,
              coffeeHouseId: this.houseId.value
            }, {
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*"
              }})
            .then(function (response) {
              console.log(response);
              window.location = 'http://localhost:5000/orders/' + response.data.id;
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
        .get('http://localhost:5055/users/')
        .then(response => (this.users = SelectIdAndValueForUser(response.data)))
        .catch(error => {
          console.log(error);
          this.showDangerAlert = true;
        });
      axios
        .get('http://localhost:5055/houses/')
        .then(response => (this.houses = SelectIdAndValueForCoffeeHouse(response.data)))
        .catch(error => {
          console.log(error);
          this.showDangerAlert = true;
        });
      axios
        .get('http://localhost:5055/recipes/')
        .then(response => (this.recipes = SelectIdAndValueForRecipe(response.data)))
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
