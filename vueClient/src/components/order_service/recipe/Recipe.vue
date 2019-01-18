<template>
  <div class="recipe">
    <h1 v-if="info != null">{{ msg }} ({{ info.data.name }})</h1>
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
      <div class="col-lg-11"  v-if="info != null">
        <p>
          <strong>Recipe name: </strong>{{ info.data.name }}
        </p>
        <p>
          <strong>Recipe Cost: </strong>{{ info.data.cost }}
        </p>
        <div class="col-lg-2"></div>
        <div class="col-lg-10">
          <strong>Recipe Ingredients: </strong>
          <div v-for="recipeIngredient of info.data.recipeIngredients">
            <hr>
            <p>
              <RecipeIngredientInfo v-bind:info="recipeIngredient"/>
            </p>
          </div>
        </div>
        <div class="container">
          <div class="row">
            <router-link :to="{name: 'EditRecipe', params: { id: info.data.id }}" class="btn btn-primary nav-link col-lg-2">Edit Recipe</router-link>
            <router-link to="/recipes" class="btn nav-link col-lg-2">Back</router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import RecipeIngredientInfo from '../recipeIngredient/RecipeIngredientInfo.vue'

  export default {
    name: 'recipe',
    components: {
      RecipeIngredientInfo
    },
    data () {
      return {
        msg: 'Coffee Recipe',
        showDangerAlert: false,
        info: null
      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/recipes/' +  this.$route.params.id, {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            "Access-Control-Allow-Origin": "*",
            "crossDomain": true,
            "Authorization": `Bearer ${localStorage.getItem("auth")}`
          }} )
        .then(response => (this.info = response))
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
