<template>
    <div class="main">
        <h3 @click="toMain()">学生信息管理系统</h3>
        <div class="buttons">
            <router-link class="a-button" to="/login" v-show="!alreadyLogin">登陆</router-link>
            <router-link class="a-button" to="/register" v-show="!alreadyLogin">注册</router-link>
        </div>
    </div>
</template>

<script>
import auth from '../api/auth'

export default {
  name: 'headerComponent',
  data () {
    return {
      alreadyLogin: false,
      avatar: ''
    }
  },
  methods: {
    toMain: function () {
      this.$router.push('/')
    },
    toUser: function () {
      this.$router.push('/user')
    }
  },
  created () {
    auth.alreadyLogin().then(res => {
      this.alreadyLogin = res
    })
  }
}
</script>

<style src="../style/header.less" lang="less"></style>
