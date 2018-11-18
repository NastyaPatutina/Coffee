import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Orders from '@/components/order/Orders'
import Order from '@/components/order/Order'
import NewOrder from '@/components/order/NewOrder'
import EditOrder from '@/components/order/EditOrder'

Vue.use(Router)

export default new Router({
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
      component: Orders,
      children: [
        {
          path: ':id',
          name: 'Order',
          component: Order
        },
        // {
        //   path: 'new',
        //   name: 'NewOrder',
        //   component: NewOrder
        // },
        {
          path: 'edit',
          name: 'EditOrder',
          component: EditOrder
        },
        {
          path: '',
          component: Orders
        }
      ]
    },
    {
      path: '/orders/new',
      name: 'NewOrder',
      component: NewOrder
    }
  ]
})
