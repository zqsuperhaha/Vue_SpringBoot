import Vue from 'vue'
import Router from 'vue-router'
import Student from '@/components/page/studentPage/Student'
import Teacher from '@/components/page/teacherPage/Teacher'
import Admin from '@/components/page/adminPage/Admin'
import Login from '@/components/page/loginPage/Login'
import Person from '@/components/page/adminPage/Person'
import Main from '@/components/page/adminPage/Main'
import Worklist from '@/components/page/adminPage/right/worklist'
import Banjilist from '@/components/page/adminPage/right/banji/banjilist'
import Courselist from '@/components/page/adminPage/right/course/courselist'
import Tchlist from '@/components/page/adminPage/right/teacher/tchlist'
import Stulist from '@/components/page/adminPage/right/student/stulist'
import Adminlist from '@/components/page/adminPage/right/admin/adminlist'
import Webset from '@/components/page/adminPage/right/webset/webset'
import Scoreset from '@/components/page/adminPage/right/webset/scoreset'
import Tmain from '@/components/page/teacherPage/Tmain'
import Tperson from '@/components/page/teacherPage/Tperson'
import Unchecklist from '@/components/page/teacherPage/right/uncheck/unchecklist'
import Checkedlist from '@/components/page/teacherPage/right/checked/checkedlist'
import Tworklist from '@/components/page/teacherPage/right/tworklist/tworklist'
import Tworkadd from '@/components/page/teacherPage/right/tworkadd'
import Tranklist from '@/components/page/teacherPage/right/tranklist'
import Smain from '@/components/page/studentPage/Smain'
import Sperson from '@/components/page/studentPage/Sperson'
import Unfinish from '@/components/page/studentPage/right/unfinish/unfinish'
import Finish from '@/components/page/studentPage/right/finish/finish'
import Sranklist from '@/components/page/studentPage/right/sranklist'
import Suserinfo from '@/components/page/studentPage/personRight/userinfo'
import Supdatepwd from '@/components/page/studentPage/personRight/updatepwd'
import Tuserinfo from '@/components/page/teacherPage/personRight/userinfo'
import Tupdatepwd from '@/components/page/teacherPage/personRight/updatepwd'
import Userinfo from '@/components/page/adminPage/personRight/userinfo'
import Updatepwd from '@/components/page/adminPage/personRight/updatepwd'



Vue.use(Router)

export default new Router({
  mode:'history',
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/student',
      name: 'Student',
      component: Student,
      redirect:'/student/smain',  // 默认跳转为 首页
      children:[
        {
          path: '/student/smain',    // 首页
          name: 'Smain',
          component: Smain,
          redirect:'/student/smain/unfinish',  // 默认跳转为 未完成列表

          // 左部导航
          children: [
            {
              path: '/student/smain/unfinish',   // 未完成列表
              name: 'Unfinish',
              component: Unfinish,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/student/smain/finish',   // 已完成列表
              name: 'Finish',
              component: Finish,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/student/smain/sranklist',   // 学生排名
              name: 'Sranklist',
              component: Sranklist,
              meta: {
                requireAuth: true
              }
            },
          ]
        },
        {
          path: '/student/sperson',  // 个人主页
          name: 'Sperson',
          component: Sperson,
          redirect:'/student/sperson/userinfo',  // 默认跳转为 账号信息

          // 个人主页 左部导航
          children:[
            {
              path: '/student/sperson/userinfo',   // 账号信息
              name: 'Suserinfo',
              component: Suserinfo,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/student/sperson/updatepwd',   // 修改密码
              name: 'Supdatepwd',
              component: Supdatepwd,
              meta: {
                requireAuth: true
              }
            },
          ]

        }
      ],
      meta:{
        requireAuth:true  // 路由的 全局变量
      }
    },
    {
      path: '/teacher',
      name: 'Teacher',
      component: Teacher,
      redirect:'/teacher/tmain',  // 默认跳转 首页
      children:[
        {
          path: '/teacher/tmain',    // 首页
          name: 'Tmain',
          component: Tmain,
          redirect:'/teacher/tmain/unchecklist',  // 默认跳转为 未批改列表

          // 左部导航
          children: [
            {
              path: '/teacher/tmain/unchecklist',   // 未批改列表
              name: 'Unchecklist',
              component: Unchecklist,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/teacher/tmain/checkedlist',   // 已批改列表
              name: 'Checkedlist',
              component: Checkedlist,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/teacher/tmain/tworkadd',   // 布置作业
              name: 'Tworkadd',
              component: Tworkadd,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/teacher/tmain/tworklist',   // 作业列表
              name: 'Tworklist',
              component: Tworklist,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/teacher/tmain/tranklist',   // 学生排名
              name: 'Tranklist',
              component: Tranklist,
              meta: {
                requireAuth: true
              }
            },
          ]
        },
        {
          path: '/teacher/tperson',  // 个人主页
          name: 'Tperson',
          component: Tperson,
          redirect:'/teacher/tperson/userinfo',  // 默认跳转为 账号信息

          // 个人主页 左部导航
          children:[
            {
              path: '/teacher/tperson/userinfo',   // 账号信息
              name: 'Tuserinfo',
              component: Tuserinfo,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/teacher/tperson/updatepwd',   // 修改密码
              name: 'Tupdatepwd',
              component: Tupdatepwd,
              meta: {
                requireAuth: true
              }
            },
          ]
        }
      ],
      meta:{
        requireAuth:true  // 路由的 全局变量
      }
    },

    // 顶部导航
    {
      path: '/admin',
      name: 'Admin',
      component: Admin,
      redirect:'/admin/main',  // 默认跳转 首页
      children: [
        {
          path: '/admin/main',    // 首页
          name: 'Main',
          component: Main,
          redirect:'/admin/main/worklist',  // 默认跳转 作业列表

          // 左部导航
          children: [
            {
              path: '/admin/main/worklist',   // 作业列表
              name: 'Worklist',
              component: Worklist,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/admin/main/banjilist',   // 班级列表
              name: 'banjilist',
              component: Banjilist,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/admin/main/courselist',   // 课程列表
              name: 'courselist',
              component: Courselist,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/admin/main/tchlist',   // 教师列表
              name: 'tchlist',
              component: Tchlist,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/admin/main/stulist',   // 学生列表
              name: 'stulist',
              component: Stulist,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/admin/main/adminlist',   // 管理员列表
              name: 'adminlist',
              component: Adminlist,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/admin/main/webset',   // 文件设置
              name: 'webset',
              component: Webset,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/admin/main/scoreset',   // 积分设置
              name: 'scoreset',
              component: Scoreset,
              meta: {
                requireAuth: true
              }
            }
          ],
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/admin/person',  // 个人主页
          name: 'Person',
          component: Person,
          redirect:'/admin/person/userinfo',  // 默认跳转为 账号信息

          // 个人主页 左部导航
          children:[
            {
              path: '/admin/person/userinfo',   // 账号信息
              name: 'Userinfo',
              component: Userinfo,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/admin/person/updatepwd',   // 修改密码
              name: 'Updatepwd',
              component: Updatepwd,
              meta: {
                requireAuth: true
              }
            },
          ]
        },
      ],
      meta:{
        requireAuth:true  // 路由的 全局变量
      }
    }

  ]
})
