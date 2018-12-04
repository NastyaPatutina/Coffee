// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import BootstrapVue from 'bootstrap-vue'
import vSelect from 'vue-select'
import { library } from '@fortawesome/fontawesome-svg-core'
import { faCoffee, faEdit, faTrash, faMale, faFemale } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

library.add(faCoffee, faEdit, faTrash, faMale, faFemale);
Vue.config.productionTip = false;

Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('v-select', vSelect);
Vue.use(BootstrapVue);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
