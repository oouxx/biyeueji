const Tabbar = () => import('@/components/Tabbar/');

export default [
  {
    path: '/comment/:id',
    name: 'comment',
    meta: {
      login: true
    },
    components: { 
      default: () => import('@/views/comment/comment'), 
      tabbar: Tabbar 
    }
  }
];
