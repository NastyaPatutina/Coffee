<template>
  <div class="recipe">
    <h1 v-if="info != null">{{ msg }} ({{ info.data.name }})</h1>
    <h1 v-else>{{ msg }}</h1>
    <div class="container">
      <div class="col-lg-1"></div>
      <div class="col-lg-11"  v-if="info != null">
        <p>
          <strong>Recipe name: </strong>{{ info.data.name }}
        </p>
        <p>
          <strong>Recipe Cost: </strong>{{ info.data.cost }}
        </p>
        <p>
          <!--TODO-->
          <strong>Recipe Ingredients: </strong>{{ info.data.recipeIngredients }}
        </p>
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

  export default {
    name: 'recipe',
    data () {
      return {
        msg: 'Coffee Recipe',
        info: null
      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/recipes/' +  this.$route.params.id )
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
