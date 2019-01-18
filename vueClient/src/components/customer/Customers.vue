<template>
  <div class="customers">
    <h1>{{msg}}</h1>
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
        <router-link :to="{name: 'NewCustomer'}" class="btn btn-primary nav-link col-lg-2">New Customer</router-link>
        <br>
        <table class="table">
          <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">First name</th>
            <th scope="col">Last name</th>
            <th scope="col">Gender</th>
            <th scope="col"></th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="item of info.data">
            <th>
              {{item.id}}
            </th>
            <td>
              {{item.firstName}}
            </td>
            <td>
              {{item.lastName}}
            </td>
            <td>
              <font-awesome-icon icon="male" size="2x" v-if="item.gender === 'male'" :style="{ color: 'lightblue' }" />
              <font-awesome-icon icon="female" size="2x" v-else :style="{ color: 'pink' }"/>
            </td>
            <td>
              <div class="container">
                <div class="row">
                  <div class="col-lg-4">
                    <router-link class="btn btn-primary col-lg-12" :to="{name: 'Customer', params: { id: item.id }}">
                      <font-awesome-icon icon="coffee" />
                    </router-link>
                  </div>
                  <div class="col-lg-4">
                    <router-link class="btn btn-primary col-lg-12" :to="{name: 'EditCustomer', params: { id: item.id }}">
                      <font-awesome-icon icon="edit" />
                    </router-link>
                  </div>
                  <div class="col-lg-4">
                    <button class="btn btn-danger col-lg-12 delete-btn" v-on:click="deleteEntity(item.id)">
                      <font-awesome-icon icon="trash" />
                    </button>
                  </div>
                </div>
              </div>
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
    name: 'customers',
    data () {
      return {
        msg: 'Coffee Customers',
        showDangerAlert: false,
        info: null
      }
    },
    methods: {
      deleteEntity: function (id) {

        axios
          .delete('http://localhost:5055/customers/' + id, {
            headers: {
              'Content-Type': 'application/json;charset=UTF-8',
              "Access-Control-Allow-Origin": "*",
              "crossDomain": true,
              "Authorization": `Bearer ${localStorage.getItem("auth")}`
            }})
          .then(function (response) {
            console.log("Deleted!", response);
            window.location = 'http://localhost:5000/customers/';
          })
          .catch(error => {
            console.log(error);
            this.showDangerAlert = true;
          });

      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/customers', {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            "Access-Control-Allow-Origin": "*",
            "crossDomain": true,
            "Authorization": `Bearer ${localStorage.getItem("auth")}`
          }})
        .then(response => (this.info = response))
        .catch(error => {
          console.log(error);
          if (error.response.status == 401 || error.response.status == 403) {
            this.msg = "Access denied";
            return
          }
          this.showDangerAlert = true;
        });
    }
  }
</script>

