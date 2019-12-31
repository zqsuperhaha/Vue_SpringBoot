import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

var axios = require('axios')
axios.defaults.baseURL = 'http://localhost:8443/api'

Vue.prototype.$axios = axios
Vue.config.productionTip = false
Vue.use(ElementUI)

router.beforeEach((to,from,next) => {

  if(to.meta.requireAuth){
    // 如果访问 /admin ，就判断 store仓库 里面是否有 username
    if(store.state.user.username){

      var toPath = to.path.substring(0,6)

      if(store.state.user.who=='学生' && (toPath === '/admin' || toPath === '/teach')){
        next('student')
      }
      if(store.state.user.who=='教师' && (toPath === '/admin' || toPath === '/stude')){
        next('teacher')
      }
      if(store.state.user.who=='管理员' && (toPath === '/stude' || toPath === '/teach')){
        next('admin')
      }

      // 直接跳转 输入的地址
      next()

    }else{
      next({  // 没有 就将你请求的 localhost:8080 后面的地址“收入囊中”，放到 query 里面
        path:'login',
        query:{
          redirect:to.fullPath
        }
      })
    }
  }else{
    next()  // 和next(true)等同，如果不写next，就等同 next(false)，是不能进入路由的，进入了路由才知道要跳转的页面在哪里，然后就直接跳转到匹配上的！！！
  }
})
new Vue({
  el: '#app',
  render: h => h(App),
  router,
  store,
  components: { App },
  template: '<App/>'
})
