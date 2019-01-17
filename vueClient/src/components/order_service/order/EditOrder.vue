<template>
  <div class="edit_order">
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
      <form id="edit_order_form" @submit="submitForm">
        <div class="form-group">
          <div class="col-lg-6">
            <div v-if="errors.length">
              <b>Please, correct this this mistakes:</b>
              <ul>
                <li v-for="error in errors">{{ error }}</li>
              </ul>
            </div>
            <v-select id="customerId" v-model="customerId" v-if="customers != null" :options="customers" placeholder="Select customer">
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
  import { AUTH_TOKEN } from "@/components/auth/Login"

  function SelectIdAndValueForCustomer(customers) {
    var res = [];
    customers.forEach(function(customer) {
      res.push({value : customer.id, label: customer.firstName + " " + customer.lastName });
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
    if (e.customerId && e.recipeId && e.houseId) {
      return true;
    }

    e.errors = [];

    if (!e.customerId) {
      e.errors.push('Please, select customer.');
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
    name: 'edit_order',
    data () {
      return {
        msg: 'Edit Coffee Order',
        showDangerAlert: false,
        customerId: null,
        recipeId: null,
        houseId: null,
        customers: null,
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
              customerId: this.customerId.value,
              recipeId: this.recipeId.value,
              coffeeHouseId: this.houseId.value
            }, {
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*",
                "Authorization": `Bearer ${AUTH_TOKEN}`
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
        .get('http://localhost:5055/orders/' +  this.$route.params.id , {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            "Access-Control-Allow-Origin": "*",
            "crossDomain": true,
            "Authorization": `Bearer ${AUTH_TOKEN}`
          }})
        .then(response_f => {
          axios
            .get('http://localhost:5055/customers/', {
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*",
                "crossDomain": true,
                "Authorization": `Bearer ${AUTH_TOKEN}`
              }})
            .then(response => {
              this.customers = SelectIdAndValueForCustomer(response.data);
              var res = {};
              this.customers.forEach(function(customer) {
                if (customer.value == response_f.data.customerId)
                  res = customer;
              });
              this.customerId = res;
            })
            .catch(error => {
              console.log(error);
              this.showDangerAlert = true;
            });
          axios
            .get('http://localhost:5055/houses/', {
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*",
                "crossDomain": true,
                "Authorization": `Bearer ${AUTH_TOKEN}`
              }})
            .then(response => {
              this.houses = SelectIdAndValueForCoffeeHouse(response.data);
              var res = {};
              this.houses.forEach(function(house) {
                if (house.value == response_f.data.coffeeHouseId)
                  res = house;
              });
              this.houseId = res;
            })
            .catch(error => {
              console.log(error);
              this.showDangerAlert = true;
            });
          axios
            .get('http://localhost:5055/recipes/', {
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*",
                "crossDomain": true,
                "Authorization": `Bearer ${AUTH_TOKEN}`
              }})
            .then(response => {
              this.recipes = SelectIdAndValueForRecipe(response.data);
              var res = {};
              this.recipes.forEach(function(recipe) {
                if (recipe.value == response_f.data.recipe.id)
                  res = recipe;
              });
              this.recipeId = res;
            })
            .catch(error => {
              console.log(error);
              this.showDangerAlert = true;
            });
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
