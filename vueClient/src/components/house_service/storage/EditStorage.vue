<template>
  <div class="edit_storage">
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
      <form id="edit_storage_form" @submit="submitForm">
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
    name: 'edit_storage',
    data () {
      return {
        msg: 'Edit Coffee Storage',
        showDangerAlert: false,
        showWarningAlert: false,
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
            .put('http://localhost:5055/storage/' +  this.$route.params.id, {
              count: this.count,
              houseId: this.houseId.value,
              productId: this.productId.value
            }, {
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*",
                "Authorization": `Bearer ${localStorage.getItem("auth")}`
              }})
            .then(function (response) {
              console.log(response);
              window.location = 'http://localhost:5000/storage/';
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
        .get('http://localhost:5055/storage/' +  this.$route.params.id , {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            "Access-Control-Allow-Origin": "*",
            "crossDomain": true,
            "Authorization": `Bearer ${localStorage.getItem("auth")}`
          }})
        .then(response_f => {
          this.count = response_f.data.count;
          axios
            .get('http://localhost:5055/products/', {
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*",
                "crossDomain": true,
                "Authorization": `Bearer ${localStorage.getItem("auth")}`
              }})
            .then(response => {
              this.products = SelectIdAndValueForProducts(response.data);
              var res = {};
              this.products.forEach(function(product) {
                if (product.value == response_f.data.product.id)
                  res = product;
              });
              this.productId = res;
            })
            .catch(error => {
              console.log(error);
              if (error.response.status == 401 || error.response.status == 403) {
                this.showWarningAlert = true;
                return
              }
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
            .then(response => {
              this.houses = SelectIdAndValueForCoffeeHouse(response.data);
              var res = {};
              this.houses.forEach(function(house) {
                if (house.value == response_f.data.house.id)
                  res = house;
              });
              this.houseId = res;
            })
            .catch(error => {
              console.log(error);
              if (error.response.status == 401 || error.response.status == 403) {
                this.showWarningAlert = true;
                return
              }
              this.showDangerAlert = true;
            });
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
