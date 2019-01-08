<template>
  <div class="new_product">
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
      <form id="new_product_form" @submit="submitForm">
        <div class="form-group">
          <div class="col-lg-6">
            <div v-if="errors.length">
              <b>Please, correct this this mistakes:</b>
              <ul>
                <li v-for="error in errors">{{ error }}</li>
              </ul>
            </div>
            <v-select id="productId" v-model="productId" v-if="products != null" :options="products" placeholder="Select product">
            </v-select>
            <br>
            <v-select id="houseId" v-model="houseId" v-if="houses != null" :options="houses" placeholder="Select house">
            </v-select>
            <br>
            <input type="number" v-model="count" placeholder="Product count" class="form-control" min="0">
            <br>
            <div class="container">
              <div class="row">
                <input class="btn btn-primary" type="submit" value="Create" >
                <router-link to="/products" class="btn nav-link col-lg-2">Back</router-link>
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

  function SelectIdAndValueForProducts(products) {
    var res = [];
    products.forEach(function(product) {
      res.push({value : product.id, label: product.name });
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
    if (e.houseId && e.count && e.productId) {
      return true;
    }

    e.errors = [];

    if (!e.name) {
      e.errors.push('Please, write product name.');
    }
    if (!e.count) {
      e.errors.push('Please, write product count.');
    }
    if (!e.houseId) {
      e.errors.push('Please, write house.');
    }

    return false;
  }

  export default {
    name: 'new_product',
    data () {
      return {
        msg: 'New Coffee Storage',
        showDangerAlert: false,
        count: null,
        productId: null,
        houseId: null,
        products: null,
        houses: null,
        errors: []
      }
    }, methods: {
      submitForm: function (e) {

        if (checkForm(this)){
          axios
            .post('http://localhost:5055/storage/', {
              count: this.count,
              houseId: this.houseId.value,
              productId: this.productId.value
            }, {
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*"
              }})
            .then(function (response) {
              console.log(response);
              window.location = 'http://localhost:5000/storage/';
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
        .get('http://localhost:5055/products/')
        .then(response => (this.products = SelectIdAndValueForProducts(response.data)))
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
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }
</style>
