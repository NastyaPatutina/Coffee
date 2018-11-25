import Vue from 'vue'
import VueRouter from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Orders from '@/components/order/Orders'
import Order from '@/components/order/Order'
import NewOrder from '@/components/order/NewOrder'
import EditOrder from '@/components/order/EditOrder'

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
          path: 'edit/:id',
          name: 'EditOrder',
          component: EditOrder
        },
        {
          path: ':id',
          name: 'Order',
          component: Order
        }
      ]
    }
  ]
})
