<template>
  <div class="mainContainer">
    <div class="loginPanel">
      <h1>请登录</h1>
      <div class="inputs">
        <div>
          <span class="label">用户名:</span>
          <input class="input" type="text" v-model="username"/>
        </div>
        <div>
          <span class="label" style="">密码:</span>
          <input class="input" type="password" v-model="password"/>
        </div>
      </div>
      <button class="loginButton" ref="loginButton" @click="login">登录</button>
    </div>
  </div>
</template>

<script>
import auth from '../api/auth'
import sha256 from 'sha256'

export default {
  name: 'login',
  data () {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    login: function () {
      this.username = this.username.trim()
      this.password = this.password.trim()
      if (this.username === '' || this.password === '') {
        this.$message.error('请填写用户名和密码！')
        this.username = ''
        this.password = ''
        return
      }
      auth.login(this.username, sha256(this.password)).then(() => {
        this.$router.push('/index')
      }).catch(reason => {
        console.log(reason)
        this.$message.error('登陆失败!')
        this.username = ''
        this.password = ''
      })
    }
  }
}
</script>

<style src="../style/login.less" lang="less" scoped></style>
