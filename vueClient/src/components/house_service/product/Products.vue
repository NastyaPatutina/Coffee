<template>
  <div class="products">
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
        <router-link :to="{name: 'NewProduct'}" class="btn btn-primary nav-link col-lg-2">New Product</router-link>
        <br>
        <table class="table">
          <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Product name</th>
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
              <div class="container">
                <div class="row">
                  <div class="col-lg-6">
                    <router-link class="btn btn-primary col-lg-12" :to="{name: 'EditProduct', params: { id: item.id }}">
                      <font-awesome-icon icon="edit" />
                    </router-link>
                  </div>
                  <div class="col-lg-6">
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
      <h2 v-else>No products yet...</h2>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'products',
    data () {
      return {
        msg: 'Coffee Products',
        showDangerAlert: false,
        info: null
      }
    },
    methods: {
      deleteEntity: function (id) {

        axios
          .delete('http://localhost:5055/products/' + id)
          .then(function (response) {
            console.log("Deleted!", response);
            window.location = 'http://localhost:5000/products/';
          })
          .catch(error => {
            console.log(error);
            this.showDangerAlert = true;
          });

      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/products')
        .then(response => (this.info = response))
        .catch(error => {
          console.log(error);
          this.showDangerAlert = true;
        });
    }
  }
</script>

