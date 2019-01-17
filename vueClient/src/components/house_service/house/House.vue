<template>
  <div class="house">
    <h1 v-if="info != null">{{ msg }}</h1>
    <h1 v-else>{{ msg }}</h1>
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
      <div class="col-lg-11" v-if="info != null">
        <p>
          <strong>House name: </strong>{{ info.data.name }}
        </p>
        <p>
          <strong>House address: </strong>{{ info.data.address }}
        </p>
        <strong>Recipes:</strong>
        <div class="col-lg-1"></div>
        <div class="col-lg-11"  v-if="recipes != null">
          <div v-for="item of recipes.data">
          <hr>
          <hr>
          <p>
            <strong>{{ item.name }}( {{ item.cost }} )</strong>
          </p>
          <div class="col-lg-2"></div>
          <div class="col-lg-10">
            <strong>Ingredients: </strong>
            <div v-for="recipeIngredient of item.recipeIngredients">
              <hr>
              <p>
                <RecipeIngredientInfo v-bind:info="recipeIngredient"/>
              </p>
            </div>
          </div>
          <br>
          </div>
        </div>
        <div class="container">
          <div class="row">
            <router-link :to="{name: 'EditHouse', params: { id: info.data.id }}" class="btn btn-primary nav-link col-lg-2">Edit House</router-link>
            <router-link to="/houses" class="btn nav-link col-lg-2">Back</router-link>
          </div>
        </div>
      </div>
      <div class="col-lg-11"  v-else >
        <strong>No available recipes</strong>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import RecipeIngredientInfo from '../../order_service/recipeIngredient/RecipeIngredientInfo.vue'
  import { AUTH_TOKEN } from "@/components/auth/Login"

  export default {
    name: 'house',
    components: {
      RecipeIngredientInfo
    },
    data () {
      return {
        msg: 'Coffee House',
        recipes: null,
        showDangerAlert: false,
        info: null
      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/houses/' +  this.$route.params.id , {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            "Access-Control-Allow-Origin": "*",
            "crossDomain": true,
            "Authorization": `Bearer ${AUTH_TOKEN}`
          }})
        .then(response => (this.info = response))
        .catch(error => {
          console.log(error);
          this.showDangerAlert = true;
        });
      axios
        .get('http://localhost:5055/houses/' +  this.$route.params.id + '/recipes', {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            "Access-Control-Allow-Origin": "*",
            "crossDomain": true,
            "Authorization": `Bearer ${AUTH_TOKEN}`
          }})
        .then(response => (this.recipes = response))
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
