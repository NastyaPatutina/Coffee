<template>
  <div class="house">
    <h1 v-if="info != null">{{ msg }}</h1>
    <h1 v-else>{{ msg }}</h1>
    <div class="container">
      <div class="col-lg-1"></div>
      <div>
        <b-alert variant="info"
                 dismissible
                 :show="showInfoAlert"
                 @dismissed="showInfoAlert=false">
          Sorry... Provided information may not be complete...
        </b-alert>
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
      <div class="col-lg-11" v-if="info != null">
        <p v-if="info.data.name != null">
          <strong>House name: </strong>{{ info.data.name }}
        </p>
        <p v-if="info.data.address != null">
          <strong>House address: </strong>{{ info.data.address }}
        </p>
        <strong v-if="info.data.recipes != null" >Recipes:</strong>
        <div class="col-lg-1"></div>
        <div class="col-lg-11"  v-if="info.data.recipes != null">
          <div v-for="item of info.data.recipes">
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

  export default {
    name: 'house',
    components: {
      RecipeIngredientInfo
    },
    data () {
      return {
        msg: 'Coffee House',
        showDangerAlert: false,
        showWarningAlert: false,
        showInfoAlert: false,
        info: null
      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/houses/' +  this.$route.params.id + '/recipes', {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            "Access-Control-Allow-Origin": "*",
            "crossDomain": true,
            "Authorization": `Bearer ${localStorage.getItem("auth")}`
          }})
        .then(response => {
          if(response.status == 206){
            this.showInfoAlert = true;
          }
          this.info = response
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
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }
</style>
