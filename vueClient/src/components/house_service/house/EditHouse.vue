<template>
  <div class="edit_house">
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
      <form id="edit_house_form" @submit="submitForm">
        <div class="form-group">
          <div class="col-lg-6">
            <div v-if="errors.length">
              <b>Please, correct this this mistakes:</b>
              <ul>
                <li v-for="error in errors">{{ error }}</li>
              </ul>
            </div>
            <input v-model="name" placeholder="House name" class="form-control" >
            <br>
            <input v-model="address" placeholder="House address" class="form-control" >
            <br>
            <input type="number" v-model.number="longitude" placeholder="House longitude" class="form-control" min="-180" max="180" step=any>
            <br>
            <input type="number" v-model.number="latitude" placeholder="House latitude" class="form-control" min="-90" max="90" step=any>
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

  function checkForm (e) {
    if (e.name) {
      return true;
    }

    e.errors = [];

    if (!e.name) {
      e.errors.push('Please, puts name.');
    }

    return false;
  }

  function checkForm (e) {
    if (e.name && e.address &&
      e.longitude && e.latitude) {
      return true;
    }

    e.errors = [];

    if (!e.name) {
      e.errors.push('Please, write house name.');
    }
    if (!e.address) {
      e.errors.push('Please, write house address.');
    }
    if (!e.longitude) {
      e.errors.push('Please, write house longitude.');
    }
    if (!e.latitude) {
      e.errors.push('Please, write house latitude.');
    }
    return false;
  }

  export default {
    name: 'edit_house',
    data () {
      return {
        showDangerAlert: false,
        msg: 'Edit Coffee House',
        name: null,
        address: null,
        longitude: null,
        latitude: null,
        errors: []
      }
    }, methods: {
      submitForm: function (e) {

        if (checkForm(this)){
          axios
            .put('http://localhost:5055/houses/' +  this.$route.params.id, {
              name: this.name,
              address: this.address,
              longitude: this.longitude,
              latitude: this.latitude
            }, {
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*",
                "Authorization": `Bearer ${localStorage.getItem("auth")}`
              }})
            .then(function (response) {
              console.log(response);
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
        e.preventDefault();

      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/houses/' +  this.$route.params.id , {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            "Access-Control-Allow-Origin": "*",
            "crossDomain": true,
            "Authorization": `Bearer ${localStorage.getItem("auth")}`
          }})
        .then(response_f => {
          this.name = response_f.data.name;
          this.address = response_f.data.address;
          this.longitude = response_f.data.longitude;
          this.latitude = response_f.data.latitude;
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
