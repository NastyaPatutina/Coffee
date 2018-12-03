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
    }

  ]
})
