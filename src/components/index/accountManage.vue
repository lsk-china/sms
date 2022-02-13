<template>
  <div class="mainContainer">
    <el-dialog
      :visible.sync="showEnsurePasswordDialog"
      title="确认密码"
      width="50%"
    >
      <div>
        <el-input type="password" v-model="ensurePasswordInput"></el-input>
        <el-button type="primary" v-on:click="updatePassword" style="margin-top: 20px">确认</el-button>
      </div>
    </el-dialog>
    <div class="card" style="height: 150px; width: 400px;">
      <span class="cardTitle">修改用户名</span>
      <el-input type="text" class="input" v-model="newUsername"></el-input>
      <el-button type="primary" class="submit" @click="updateUsername">提交</el-button>
    </div>
    <div class="card" style="height: 150px; width: 400px;">
      <span class="cardTitle">修改密码</span>
      <el-input type="password" class="input" v-model="newPassword"></el-input>
      <el-button type="primary" class="submit" @click="showEnsurePasswordDialog = true">提交</el-button>
    </div>
  </div>
</template>

<script>
import auth from '../../api/auth'

export default {
  name: 'accountManage',
  data () {
    return {
      newUsername: '',
      newPassword: '',
      ensurePasswordInput: '',
      showEnsurePasswordDialog: false
    }
  },
  methods: {
    updateUsername: function () {
      this.newUsername = this.newUsername.trim()
      if (this.newUsername === '') {
        this.$message.error('请输入用户名!')
        return
      }
      auth.changeUsername(this.newUsername).then(() => {
        this.newUsername = ''
        this.$message.success('更新成功！请重新登陆！')
      }).catch(reason => {
        this.$message.error('更新失败')
      })
    },
    updatePassword: function () {
      this.showEnsurePasswordDialog = false
      this.newPassword = this.newPassword.trim()
      this.ensurePasswordInput = this.ensurePasswordInput.trim()
      if (this.newPassword !== this.ensurePasswordInput) {
        this.$message.error('密码不一致！')
        return
      }
      auth.changePassword(this.newPassword).then(() => {
        this.newPassword = ''
        this.ensurePasswordInput = ''
        this.$message.success('更新成功！请重新登陆！')
      }).catch(reason => {
        this.$message.error('更新失败！')
      })
    }
  }
}
</script>

<style src="../../style/accountManage.less" lang="less" scoped></style>
