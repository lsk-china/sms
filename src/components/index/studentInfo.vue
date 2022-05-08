<template>
  <div class="mainContainer">
    <div class="card"
         v-if="$parent.loginInfo.authorities[0].role === 'ROLE_STUDENT'"
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
import student from '../../api/student'

export default {
  name: 'studentInfo',
  data () {
    return {
      studentInfo: null
    }
  },
  created () {
    student.studentInfo().then(resp => {
      this.studentInfo = resp
    })
  }
}
</script>

<style src="../../style/studentInfo.less" lang="less" scoped></style>
