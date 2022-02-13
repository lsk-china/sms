<template>
    <div class="mainContainer">
      <div class="leftContainer">
        <h1>{{loginInfo.name}}<br/>你好</h1>
        <div class="buttonsContainer">
          <div v-if="loginInfo.authorities[0].role === 'ROLE_ADMIN'">
            <div class="divButton flexCenter" @click="changeComponent('accountManage')">
              <h3>账户管理</h3>
            </div>
            <div class="divButton flexCenter" @click="changeComponent('userManage')">
              <h3>用户管理</h3>
            </div>
            <div class="divButton flexCenter">
              <h3>学生管理</h3>
            </div>
          </div>
          <div v-if="loginInfo.authorities[0].role === 'ROLE_STUDENT'"></div>
          <div v-if="loginInfo.authorities[0].role === 'ROLE_FINANCE'"></div>
        </div>
      </div>
      <div class="rightContainer">
        <component :is="rightComponent"></component>
      </div>
    </div>
</template>

<script>
import auth from '../api/auth'
import accountManage from './index/accountManage'
import userManage from './index/userManage'

export default {
  name: 'index',
  data () {
    return {
      loginInfo: null,
      rightComponent: accountManage
    }
  },
  methods: {
    changeComponent: function (target) {
      switch (target) {
        case 'accountManage':
          this.rightComponent = accountManage
          break
        case 'userManage':
          this.rightComponent = userManage
          break
      }
    }
  },
  created () {
    auth.loginInfo().then(resp => {
      this.loginInfo = resp
    }).catch(reason => {
      if (reason.message === 'User not login') {
        this.$message.error('用户未登陆！3秒后跳转到登陆界面。')
        setTimeout(() => {
          this.$router.push('/')
        }, 3000)
      } else {
        this.$message.error('获取登陆信息失败！')
      }
    })
  }
}
</script>

<style src="../style/index.less" lang="less" scoped></style>
