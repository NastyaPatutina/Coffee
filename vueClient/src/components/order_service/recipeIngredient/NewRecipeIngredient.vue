<template>
  <div class="new_recipe_ingredient">
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
      <form id="new_recipe_ingredient_form" @submit="submitForm">
        <div class="form-group">
          <div class="col-lg-6">
            <div v-if="errors.length">
              <b>Please, correct this this mistakes:</b>
              <ul>
                <li v-for="error in errors">{{ error }}</li>
              </ul>
            </div>
            <v-select id="recipeId" v-model="recipeId" v-if="recipes != null" :options="recipes" placeholder="Select recipe">
            </v-select>
            <br>
            <v-select id="productId" v-model="productId" v-if="products != null" :options="products" placeholder="Select product">
            </v-select>
            <br>
            <input type="number" v-model="count" placeholder="Product count" class="form-control" min="0">
            <br>
            <div class="container">
              <div class="row">
                <input class="btn btn-primary" type="submit" value="Create" >
                <router-link to="/recipe_ingredients" class="btn nav-link col-lg-2">Back</router-link>
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
    if (e.count && e.productId && e.recipeId) {
      return true;
    }

    e.errors = [];

    if (!e.count) {
      e.errors.push('Please, write ingredient count.');
    }
    if (!e.productId) {
      e.errors.push('Please, write ingredient product.');
    }
    if (!e.recipeId) {
      e.errors.push('Please, write ingredient recipe.');
    }
    return false;
  }

  function SelectIdAndValueForRecipe(recipes) {
    var res = [];
    recipes.forEach(function(recipe) {
      res.push({value : recipe.id, label: recipe.name });
    });
    return res;
  }
  function SelectIdAndValueForProduct(recipes) {
    var res = [];
    recipes.forEach(function(recipe) {
      res.push({value : recipe.id, label: recipe.name });
    });
    return res;
  }

  export default {
    name: 'new_recipe_ingredient',
    data () {
      return {
        msg: 'New Coffee Ingredient',
        count: null,
        products: null,
        recipes: null,
        productId: null,
        recipeId: null,
        showDangerAlert: false,
        errors: []
      }
    }, methods: {
      submitForm: function (e) {

        if (checkForm(this)){
          axios
            .post('http://localhost:5055/recipe_ingredients/', {
              count: this.count,
              productId: this.productId.value,
              recipeId: this.recipeId.value
            }, {
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*",
                "Authorization": `Bearer ${AUTH_TOKEN}`
              }})
            .then(function (response) {
              console.log(response);
              window.location = 'http://localhost:5000/recipe_ingredients/' + response.data.id;
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
        .get('http://localhost:5055/products' , {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            "Access-Control-Allow-Origin": "*",
            "crossDomain": true,
            "Authorization": `Bearer ${AUTH_TOKEN}`
          }})
        .then(response => (this.products = SelectIdAndValueForProduct(response.data)))
        .catch(error => {
          console.log(error);
          this.showDangerAlert = true;
        });
      axios
        .get('http://localhost:5055/recipes' , {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            "Access-Control-Allow-Origin": "*",
            "crossDomain": true,
            "Authorization": `Bearer ${AUTH_TOKEN}`
          }})
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
