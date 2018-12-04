<template>
  <div class="recipe_ingredients">
    <h1 v-if="info != null">{{ msg }}</h1>
    <h1 v-else>{{ msg }}</h1>
    <div class="container">
      <div class="col-lg-1"></div>
      <div class="col-lg-11"  v-if="info != null">
        <p>
          <strong>Recipe name: </strong>{{ info.data.recipe.name }}
        </p>
        <RecipeIngredientInfo v-bind:info="info.data"/>
        <div class="container">
          <div class="row">
            <router-link :to="{name: 'EditRecipeIngredients', params: { id: info.data.id }}" class="btn btn-primary nav-link col-lg-3">Edit Recipe Ingredient</router-link>
            <router-link to="/recipe_ingredients" class="btn nav-link col-lg-2">Back</router-link>
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
    name: 'recipe_ingredients',
    components: {
      RecipeIngredientInfo
    },
    data () {
      return {
        msg: 'Coffee Recipe Ingredient',
        info: null
      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/recipe_ingredients/' +  this.$route.params.id )
        .then(response => (this.info = response));
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }
</style>
