<template>
  <div class="order">
    <h1 v-if="info != null">{{ msg }}</h1>
    <h1 v-else>{{ msg }}</h1>
    <div class="container">
      <div class="col-lg-1"></div>
      <div class="col-lg-11"  v-if="info != null">
        <p>
          <strong>First name: </strong>{{ info.data.firstName }}
        </p>
        <p>
          <strong>Last name: </strong>{{ info.data.lastName }}
        </p>
        <p>
          <strong>Gender: </strong>
          <font-awesome-icon icon="male" size="2x" v-if="info.data.gender === 'male'" />
          <font-awesome-icon icon="female" size="2x" v-else />
        </p>
        <p>
          <strong>E-mail: </strong>{{ info.data.email }}
        </p>
        <p>
          <strong>Phone: </strong>{{ info.data.phone }}
        </p>
        <div class="container">
          <div class="row">
            <router-link :to="{name: 'EditUser', params: { id: info.data.id }}" class="btn btn-primary nav-link col-lg-2">Edit User</router-link>
            <router-link to="/orders" class="btn nav-link col-lg-2">Back</router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'order',
    data () {
      return {
        msg: 'Coffee User',
        info: null
      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/users/' +  this.$route.params.id )
        .then(response => (this.info = response));
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }
</style>
