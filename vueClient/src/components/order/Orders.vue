<template>
  <div class="orders">
    <h1>{{msg}}</h1>
    <div class="container">
      <div class="col-lg-1"></div>
      <div class="col-lg-11"  v-if="info != null">
        <button type="button" class="btn btn-primary"><router-link :to="{name: 'NewOrder'}" class="nav-link">New Order</router-link></button>
        <table class="table">
          <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Recipe name</th>
            <th scope="col">User</th>
            <th scope="col">Coffee House</th>
            <th scope="col"></th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="item of info.data">
            <th>
              {{item.id}}
            </th>
            <td>
              {{item.recipe.name}}
            </td>
            <td>
              {{item.userId}}
            </td>
            <td>
              {{item.coffeeHouseId}}
            </td>
            <td>
              <button type="button" class="btn btn-primary"><router-link :to="{name: 'Order', params: { id: item.id }}" class="nav-link">Order</router-link></button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <h2 v-else>No orders yet...</h2>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'orders',
    data () {
      return {
        msg: 'Coffee Orders',
        info: null
      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/orders')
        .then(response => (this.info = response));
    }
  }
</script>

