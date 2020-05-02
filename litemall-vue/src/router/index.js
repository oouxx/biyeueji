import Vue from 'vue';
import Router from 'vue-router';
import { getLocalStorage } from '@/utils/local-storage';

import home from './home';
import items from './items';
import user from './user';
import order from './order';
import login from './login';
import comment from './comment';

Vue.use(Router);

const RouterModel = new Router({
  routes: [...home, ...items, ...user, ...order, ...login, ...comment]
});

RouterModel.beforeEach((to, from, next) => {
  const { Authorization } = getLocalStorage(
    'Authorization'
  );
  if (!Authorization) {
    if (to.meta.login) {
      next({ name: 'login', query: { redirect: to.name } });
      return;
    }
  }
  next();
});

export default RouterModel;
