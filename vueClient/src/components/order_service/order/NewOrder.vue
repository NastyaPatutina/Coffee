<template>
  <div class="new_order">
    <h1>{{ msg }}</h1>
    <br>
    <div class="container">
      <div class="col-lg-1"></div>
      <div>
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
      <form id="new_order_form" @submit="submitForm">
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
    name: 'new_order',
    data () {
      return {
        msg: 'New Coffee Order',
        showDangerAlert: false,
        showWarningAlert: false,
        customerId: null,
        recipeId: null,
        houseId: null,
        customers: null,
        recipes: null,
        houses: null,
        errors: []
      }
    }, methods: {
      submitForm: function (e) {

        if (checkForm(this)){
          axios
            .post('http://localhost:5055/orders/', {
              customerId: this.customerId.value,
              recipeId: this.recipeId.value,
              coffeeHouseId: this.houseId.value
            }, {
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*",
                "Authorization": `Bearer ${localStorage.getItem("auth")}`
              }})
            .then(function (response) {
              console.log(response);
              window.location = 'http://localhost:5000/orders/' + response.data.id;
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
        e.preventDefault();

      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/customers/', {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            "Access-Control-Allow-Origin": "*",
            "crossDomain": true,
            "Authorization": `Bearer ${localStorage.getItem("auth")}`
          }})
        .then(response => (this.customers = SelectIdAndValueForCustomer(response.data)))
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
            "Authorization": `Bearer ${localStorage.getItem("auth")}`
          }})
        .then(response => (this.houses = SelectIdAndValueForCoffeeHouse(response.data)))
        .catch(error => {
          console.log(error);
          if (error.response.status == 401 || error.response.status == 403) {
            this.showWarningAlert = true;
            return
          }
          this.showDangerAlert = true;
        });
      axios
        .get('http://localhost:5055/recipes/', {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            "Access-Control-Allow-Origin": "*",
            "crossDomain": true,
            "Authorization": `Bearer ${localStorage.getItem("auth")}`
          }})
        .then(response => (this.recipes = SelectIdAndValueForRecipe(response.data)))
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
