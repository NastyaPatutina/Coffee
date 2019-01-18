<template>
  <div class="order">
    <h1 v-if="info != null">{{ msg }} ({{ info.data.recipe.name }}) for {{ info.data.customerId }}</h1>
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
          <strong>Recipe name: </strong>{{ info.data.recipe.name }}
        </p>
        <p>
          <strong>Recipe Cost: </strong>{{ info.data.recipe.cost }}
        </p>
        <p>
          <strong>Customer: </strong>
          <CustomerName v-bind:id="info.data.customerId"/>
        </p>
        <p>
          <strong>Coffee House: </strong>
          <HouseName v-bind:id="info.data.coffeeHouseId"/>
        </p>
        <div class="container">
          <div class="row">
            <router-link :to="{name: 'EditOrder', params: { id: info.data.id }}" class="btn btn-primary nav-link col-lg-2">Edit Order</router-link>
            <router-link to="/orders" class="btn nav-link col-lg-2">Back</router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import CustomerName from '../../customer/CustomerName.vue'
  import HouseName from '../../house_service/house/HouseName.vue'

  export default {
    name: 'order',
    components: {
      CustomerName,
      HouseName
    },
    data () {
      return {
        msg: 'Coffee Order',
        showDangerAlert: false,
        info: null
      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/orders/' +  this.$route.params.id , {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            "Access-Control-Allow-Origin": "*",
            "crossDomain": true,
            "Authorization": `Bearer ${localStorage.getItem("auth")}`
          }})
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
