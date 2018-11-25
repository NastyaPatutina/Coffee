<template>
  <div class="edit_order">
    <h1>{{ msg }}</h1>
    <br>
    <div class="container">
      <div class="col-lg-1"></div>
      <form id="edit_order_form" @submit="submitForm">
        <div class="form-group">
          <div class="col-lg-6">
            <p v-if="errors.length">
            <b>Пожалуйста исправьте указанные ошибки:</b>
            <ul>
            <li v-for="error in errors">{{ error }}</li>
            </ul>
            </p>
            <v-select id="userId" v-model="userId" v-if="users != null" :options="users" placeholder="Select user">
            </v-select>
            <br>
            <v-select id="recipeId" v-model="recipeId" v-if="recipes != null" :options="recipes" placeholder="Select recipe">
            </v-select>
            <br>
            <v-select id="houseId" v-model="houseId" v-if="houses != null" :options="houses" placeholder="Select coffee house">
            </v-select>
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
        msg: 'Edit Coffee Order',
        userId: null,
        recipeId: null,
        houseId: null,
        users: null,
        recipes: null,
        houses: null,
        info: null,
        errors: []
      }
    }, methods: {
      submitForm: function (e) {

        if (checkForm(this)){
          axios
            .put('http://localhost:5055/orders/' +  this.$route.params.id, {
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
            });
        }
        e.preventDefault();

      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/orders/' +  this.$route.params.id )
        .then(response_f => {
          axios
            .get('http://localhost:5055/users/')
            .then(response => {
              this.users = SelectIdAndValueForUser(response.data);
              var res = {};
              this.users.forEach(function(user) {
                  if (user.value == response_f.data.userId)
                    res = user;
              });
              this.userId = res;
            });
          axios
            .get('http://localhost:5055/houses/')
            .then(response => {
              this.houses = SelectIdAndValueForCoffeeHouse(response.data);
              var res = {};
              this.houses.forEach(function(house) {
                if (house.value == response_f.data.coffeeHouseId)
                  res = house;
              });
              this.houseId = res;
            });
          axios
            .get('http://localhost:5055/recipes/')
            .then(response => {
              this.recipes = SelectIdAndValueForRecipe(response.data);
              var res = {};
              this.recipes.forEach(function(recipe) {
                if (recipe.value == response_f.data.recipe.id)
                  res = recipe;
              });
              this.recipeId = res;
            });
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
