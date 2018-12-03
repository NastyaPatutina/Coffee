<template>
  <div class="edit_recipe">
    <h1>{{ msg }}</h1>
    <br>
    <div class="container">
      <div class="col-lg-1"></div>
      <form id="edit_recipe_form" @submit="submitForm">
        <div class="form-group">
          <div class="col-lg-6">
            <p v-if="errors.length">
              <b>Пожалуйста исправьте указанные ошибки:</b>
            <ul>
              <li v-for="error in errors">{{ error }}</li>
            </ul>
            </p>
            <br>
            <!--TODO-->
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
    return true;
  }

  export default {
    name: 'new_recipe',
    data () {
      return {
        msg: 'Edit Coffee recipe',
        info: null,
        errors: []
      }
    }, methods: {
      submitForm: function (e) {

        if (checkForm(this)){
          axios
            .put('http://localhost:5055/recipes/' +  this.$route.params.id, {
            }, {
              headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                "Access-Control-Allow-Origin": "*"
              }})
            .then(function (response) {
              console.log(response);
              window.location = 'http://localhost:5000/recipes/' + response.data.id;
            });
        }
        e.preventDefault();

      }
    },
    mounted() {
      axios
        .get('http://localhost:5055/recipes/' +  this.$route.params.id )
        .then(response_f => {
          console.log(response_f);
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
