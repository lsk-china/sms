<template>
  <div class="mainContainer" style="position: relative">
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
    <div class="card"
         v-if="$parent.loginInfo.authorities[0].role === 'ROLE_STUDENT'"
         style="position: absolute; left: 0; top: 210px;"
    >
      <span class="cardTitle">我的信息</span>
      <div class="studentInfo">
        <div class="infoItem">
          <span class="key">学号：</span>
          <span class="value" v-text="studentInfo.id"></span>
        </div>
        <div class="infoItem">
          <span class="key">姓名：</span>
          <span class="value" v-text="studentInfo.name"></span>
        </div>
        <div class="infoItem">
          <span class="key">年龄：</span>
          <span class="value" v-text="studentInfo.age"></span>
        </div>
        <div class="infoItem">
          <span class="key">性别：</span>
          <span class="value" v-if="studentInfo.sex === 1">男</span>
          <span class="value" v-if="studentInfo.sex === 0">女</span>
        </div>
        <div class="infoItem">
          <span class="key">电话：</span>
          <span class="value" v-text="studentInfo.telephone"></span>
        </div>
        <div class="infoItem">
          <span class="key">住址：</span>
          <span class="value" v-text="studentInfo.address"></span>
        </div>
        <div class="infoItem">
          <span class="key">班级：</span>
          <span class="value" v-text="studentInfo.clazz"></span>
        </div>
        <div class="infoItem">
          <span class="key">宿舍编号：</span>
          <span class="value" v-text="studentInfo.dormitoryID"></span>
        </div>
        <div class="infoItem">
          <span class="key">录取通知书编号：</span>
          <span class="value" v-text="studentInfo.matriculateNum"></span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import auth from '../../api/auth'
import student from '../../api/student'

export default {
  name: 'accountManage',
  data () {
    return {
      newUsername: '',
      newPassword: '',
      ensurePasswordInput: '',
      showEnsurePasswordDialog: false,
      studentInfo: null
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
  },
  created () {
    if (this.$parent.loginInfo.authorities[0].role === 'ROLE_STUDENT') {
      student.studentInfo().then(resp => {
        this.studentInfo = resp
      }).catch(reason => {
        this.$message.error('获取学生信息失败')
      })
    }
  }
}
</script>

<style src="../../style/accountManage.less" lang="less" scoped></style>
