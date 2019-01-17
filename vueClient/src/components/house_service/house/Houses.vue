<template>
  <div class="houses">
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
        <router-link :to="{name: 'NewHouse'}" class="btn btn-primary nav-link col-lg-2">New House</router-link>
        <br>
        <table class="table">
          <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">House name</th>
            <th scope="col">Address</th>
            <th scope="col">Longitude/Latitude</th>
            <th scope="col"></th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="item of info.data">
            <th>
              {{item.id}}
            </th>
            <td>
              {{item.name}}
            </td>
            <td>
              {{item.address}}
            </td>
            <td>
              {{item.longitude}}/{{item.latitude}}
            </td>
            <td>
              <div class="container">
                <div class="row">
                  <div class="col-lg-4">
                    <router-link class="btn btn-primary col-lg-12" :to="{name: 'House', params: { id: item.id }}">
                      <font-awesome-icon icon="coffee" />
                    </router-link>
                  </div>
                  <div class="col-lg-4">
                    <router-link class="btn btn-primary col-lg-12" :to="{name: 'EditHouse', params: { id: item.id }}">
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
      <h2 v-else>No houses yet...</h2>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import { AUTH_TOKEN } from "@/components/auth/Login"

  export default {
    name: 'houses',
    data () {
      return {
        msg: 'Coffee Houses',
        showDangerAlert: false,
        info: null
      }
    },
    methods: {
      deleteEntity: function (id) {

        axios
          .delete('http://localhost:5055/houses/' + id, {
            headers: {
              'Content-Type': 'application/json;charset=UTF-8',
              "Access-Control-Allow-Origin": "*",
              "crossDomain": true,
              "Authorization": `Bearer ${AUTH_TOKEN}`
            }})
          .then(function (response) {
            console.log("Deleted!", response);
            window.location = 'http://localhost:5000/houses/';
          })
          .catch(error => {
            console.log(error);
            if (error.response.status == 401 || error.response.status == 403) {
              this.msg = "Access denied";
              return
            }
            this.showDangerAlert = true;
          });

      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/houses', {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            "Access-Control-Allow-Origin": "*",
            "crossDomain": true,
            "Authorization": `Bearer ${AUTH_TOKEN}`
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

