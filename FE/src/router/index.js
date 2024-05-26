import { createRouter, createWebHistory } from 'vue-router';
import Login from '@/views/Signin/Login.vue';
import SignupUser from '@/views/Signup/SignupUser.vue';
import store from '@/store';
import Home from '@/views/Home/Home.vue';
import AddProduct from '@/views/AddProduct/AddProduct.vue';
import MoreProducts from '@/views/MoreProducts/MoreProducts.vue';
import ProductsCategory from '@/views/ProductsCategory/ProductsCategory.vue';
import DetailProduct from '@/views/DetailProduct/DetailProduct.vue';
import Cart from '@/views/Cart/Cart.vue';
import Order from '@/views/Order/Order.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: true,
      },
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
    },
    {
      path: '/register',
      name: 'signup',
      component: SignupUser,
    },
    {
      path: '/add-product',
      name: 'add-product',
      component: AddProduct,
    },
    {
      path: '/page-product',
      name: 'page-product',
      component: MoreProducts,
    },
    {
      path: '/page-category-product/:id',
      name: 'page-category-product',
      component: ProductsCategory,
    },
    {
      path: '/product/:id',
      name: 'product',
      component: DetailProduct,
    },
    {
      path: '/cart',
      name: 'cart',
      component: Cart,
    },
    {
      path: '/order',
      name: 'order',
      component: Order,
    },
  ],
});

router.beforeEach((to, from, next) => {
  const loggedIn = store.state.auth.status.loggedIn;
  const currentUser = store.state.auth.user;

  if (to.matched.some((record) => record.meta.requiresAuth) && !loggedIn) {
    next({
      name: 'login',
      query: { redirect: to.fullPath },
    });
  } else if (loggedIn) {
    switch (to.name) {
      case 'login' || 'register':
        next({
          path: '/',
        });
        break;
      default:
        next();
        break;
    }
  } else next();
});

export default router;
