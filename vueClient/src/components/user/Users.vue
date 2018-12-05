<template>
  <div class="users">
    <h1>{{msg}}</h1>
    <div class="container">
      <div class="col-lg-1"></div>
      <div class="col-lg-11"  v-if="info != null">
        <router-link :to="{name: 'NewUser'}" class="btn btn-primary nav-link col-lg-2">New User</router-link>
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
                    <router-link class="btn btn-primary col-lg-12" :to="{name: 'User', params: { id: item.id }}">
                      <font-awesome-icon icon="coffee" />
                    </router-link>
                  </div>
                  <div class="col-lg-4">
                    <router-link class="btn btn-primary col-lg-12" :to="{name: 'EditUser', params: { id: item.id }}">
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
    name: 'users',
    data () {
      return {
        msg: 'Coffee Users',
        info: null
      }
    },
    methods: {
      deleteEntity: function (id) {

        axios
          .delete('http://localhost:5055/users/' + id)
          .then(function (response) {
            console.log("Deleted!", response);
            window.location = 'http://localhost:5000/users/';
          });

      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/users')
        .then(response => (this.info = response));
    }
  }
</script>

