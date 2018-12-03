import Vue from 'vue'
import VueRouter from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Orders from '@/components/order/Orders'
import Order from '@/components/order/Order'
import NewOrder from '@/components/order/NewOrder'
import EditOrder from '@/components/order/EditOrder'
import Recipes from '@/components/recipe/Recipes'
import Recipe from '@/components/recipe/Recipe'
import NewRecipe from '@/components/recipe/NewRecipe'
import EditRecipe from '@/components/recipe/EditRecipe'

Vue.use(VueRouter)

export default new VueRouter({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/orders',
      name: 'Orders',
      component: { render: h => h('router-view') },
      children: [
        {
          path: '',
          component: Orders
        },
        {
          path: 'new',
          name: 'NewOrder',
          component: NewOrder
        },
        {
          path: ':id',
          name: 'Order',
          component: Order
        },
        {
          path: ':id/edit',
          name: 'EditOrder',
          component: EditOrder
        }
      ]
    },
    {
      path: '/recipes',
      name: 'Recipe',
      component: { render: h => h('router-view') },
      children: [
        {
          path: '',
          component: Recipes
        },
        {
          path: 'new',
          name: 'NewRecipe',
          component: NewRecipe
        },
        {
          path: ':id',
          name: 'Recipe',
          component: Recipe
        },
        {
          path: ':id/edit',
          name: 'EditRecipe',
          component: EditRecipe
        }
      ]
    }
  ]
})
