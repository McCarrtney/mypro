import TabsView from '@/layouts/tabs/TabsView'
import BlankView from '@/layouts/BlankView'
// import PageView from '@/layouts/PageView'

// 路由配置
const options = {
  routes: [
    {
      path: '/login',
      name: '登录页',
      component: () => import('@/pages/login')
    },
    {
      path: '*',
      name: '404',
      component: () => import('@/pages/exception/404'),
    },
    {
      path: '/403',
      name: '403',
      component: () => import('@/pages/exception/403'),
    },
    {
      path: '/',
      name: '首页',
      component: TabsView,
      redirect: '/login',
      children: [
        {
          name: 'test',
          path: 'test',
          meta: {
            icon: 'project',
            query: {
              name: '菜单默认参数'
            }
          },
          component: () => import('@/pages/test')
        },
        {
          path: 'info',
          name: '个人中心',
          meta: {
            icon: 'dashboard'
          },
          component: BlankView,
          children: [
            {
              path: 'healthinfo',
              name: '健康数据',
              meta: {
                page: {
                  closable: false
                }
              },
              component: () => import('@/pages/user/info/healthInfo'),
            },
            // {
            //   path: 'analysis',
            //   name: '分析页',
            //   component: () => import('@/pages/dashboard/analysis'),
            // }
          ]
        },
        {
          path: 'dashboard',
          name: 'Dashboard',
          meta: {
            icon: 'dashboard'
          },
          component: BlankView,
          children: [
            {
              path: 'workplace',
              name: '工作台',
              meta: {
                page: {
                  closable: false
                }
              },
              component: () => import('@/pages/dashboard/workplace'),
            },
            // {
            //   path: 'analysis',
            //   name: '分析页',
            //   component: () => import('@/pages/dashboard/analysis'),
            // }
          ]
        },

      ]
    },
  ]
}

export default options
