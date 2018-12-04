import Vue from 'vue'
import VueRouter from 'vue-router'
import HelloWorld from '@/components/HelloWorld'

import Orders from '@/components/order_service/order/Orders'
import Order from '@/components/order_service/order/Order'
import NewOrder from '@/components/order_service/order/NewOrder'
import EditOrder from '@/components/order_service/order/EditOrder'
import Recipes from '@/components/order_service/recipe/Recipes'
import Recipe from '@/components/order_service/recipe/Recipe'
import NewRecipe from '@/components/order_service/recipe/NewRecipe'
import EditRecipe from '@/components/order_service/recipe/EditRecipe'
import RecipeIngredients from '@/components/order_service/recipeIngredient/RecipeIngredients'
import RecipeIngredient from '@/components/order_service/recipeIngredient/RecipeIngredient'
import NewRecipeIngredient from '@/components/order_service/recipeIngredient/NewRecipeIngredient'
import EditRecipeIngredient from '@/components/order_service/recipeIngredient/EditRecipeIngredient'

import Products from '@/components/house_service/product/Products'
import NewProduct from '@/components/house_service/product/NewProduct'
import EditProduct from '@/components/house_service/product/EditProduct'
import Houses from '@/components/house_service/house/Houses'
import NewHouse from '@/components/house_service/house/NewHouse'
import EditHouse from '@/components/house_service/house/EditHouse'
import Storage from '@/components/house_service/storage/Storage'
import NewStorage from '@/components/house_service/storage/NewStorage'
import EditStorage from '@/components/house_service/storage/EditStorage'

import Users from '@/components/user/Users'
import User from '@/components/user/User'
import NewUser from '@/components/user/NewUser'
import EditUser from '@/components/user/EditUser'

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
    },
    {
      path: '/recipe_ingredients',
      name: 'RecipeIngredients',
      component: { render: h => h('router-view') },
      children: [
        {
          path: '',
          component: RecipeIngredients
        },
        {
          path: 'new',
          name: 'NewRecipeIngredient',
          component: NewRecipeIngredient
        },
        {
          path: ':id',
          name: 'RecipeIngredient',
          component: RecipeIngredient
        },
        {
          path: ':id/edit',
          name: 'EditRecipeIngredient',
          component: EditRecipeIngredient
        }
      ]
    },
    {
      path: '/products',
      name: 'Products',
      component: { render: h => h('router-view') },
      children: [
        {
          path: '',
          component: Products
        },
        {
          path: 'new',
          name: 'NewProduct',
          component: NewProduct
        },
        {
          path: ':id/edit',
          name: 'EditProduct',
          component: EditProduct
        }
      ]
    },
    {
      path: '/houses',
      name: 'Houses',
      component: { render: h => h('router-view') },
      children: [
        {
          path: '',
          component: Houses
        },
        {
          path: 'new',
          name: 'NewHouse',
          component: NewHouse
        },
        {
          path: ':id/edit',
          name: 'EditHouse',
          component: EditHouse
        }
      ]
    },
    {
      path: '/storage',
      name: 'Storage',
      component: { render: h => h('router-view') },
      children: [
        {
          path: '',
          component: Storage
        },
        {
          path: 'new',
          name: 'NewStorage',
          component: NewStorage
        },
        {
          path: ':id/edit',
          name: 'EditStorage',
          component: EditStorage
        }
      ]
    },
    {
      path: '/users',
      name: 'User',
      component: { render: h => h('router-view') },
      children: [
        {
          path: '',
          component: Users
        },
        {
          path: 'new',
          name: 'NewUser',
          component: NewUser
        },
        {
          path: ':id',
          name: 'User',
          component: User
        },
        {
          path: ':id/edit',
          name: 'EditUser',
          component: EditUser
        }
      ]
    }

  ]
})
