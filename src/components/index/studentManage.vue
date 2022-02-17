<template>
  <div class="mainContainer" style="overflow: auto; flex-direction: column">
    <el-dialog
      :visible.sync="showConfirmDialog"
      title="警告"
      width="30%"
    >
      <h1 style="text-align: center">您确定要删除此学生吗？此操作不可撤销！</h1>
      <el-button style="margin-top: 20px;" type="danger" @click="doDeleteStudent">确定</el-button>
      <el-button style="margin-top: 20px;" type="default" @click="showConfirmDialog = false;deleteStudentID = 0">取消</el-button>
    </el-dialog>
    <el-dialog
      :visible.sync="showReportDialog"
      title="报道"
      width="50%"
    >
      <div class="inputGroup">
        <div class="inputItem">
          <span class="label">分配宿舍：</span>
          <el-input class="input" type="text" v-model="reportData.dormitoryID"></el-input>
        </div>
        <div class="inputItem">
          <span class="label">分配班级：</span>
          <el-input class="input" type="text" v-model="reportData.clazz"></el-input>
        </div>
      </div>
      <el-button type="primary" class="selected" style="margin-left: 20px" @click="report">确定</el-button>
    </el-dialog>
    <el-dialog
      :visible.sync="showReportSuccessDialog"
      width="50%"
      title="报到成功"
    >
      <div class="reportSuccessWrapper">
        <i class="el-icon-success"></i>
        <div style="margin-left: 20px" class="flexCenter">
          <span style="font-size: 20px">学生的临时用户名为{{temporaryPersonInfo.name}}，临时密码为{{temporaryPersonInfo.password}}</span>
        </div>
      </div>
      <el-button type="primary" style="margin-top: 20px" @click="showReportSuccessDialog = false">关闭</el-button>
    </el-dialog>
    <div class="card">
      <span class="cardTitle">学生列表</span>
      <span class="refreshIcon" @click="updateStudentList"></span>
      <el-table :data="studentList">
        <el-table-column prop="id" label="学号" width="50px" align="center"></el-table-column>
        <el-table-column prop="name" label="姓名" width="100px" align="left"></el-table-column>
        <el-table-column prop="age" label="年龄" width="50px" align="center"></el-table-column>
        <el-table-column prop="sex" label="性别" width="50px" align="center" :formatter="sexFormatter"></el-table-column>
        <el-table-column prop="clazz" label="班级" width="70px" align="center"></el-table-column>
        <el-table-column prop="address" label="住址" width="300px" align="left"></el-table-column>
        <el-table-column prop="telephone" label="电话" width="150px" align="left"></el-table-column>
        <el-table-column prop="dormitoryID" label="宿舍号码" width="50px" align="center"></el-table-column>
        <el-table-column prop="matriculateNum" label="录取通知书编号" width="100px" align="left"></el-table-column>
        <el-table-column prop="personID" label="用户ID" width="50px" align="center"></el-table-column>
        <el-table-column label="操作" width="100px">
          <template slot-scope="slot">
            <el-button type="danger" @click="deleteStudent(slot.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        :total="studentListTotalPage"
        :current-page.sync="studentListPage"
        background
        layout="prev, pager, next"
        @current-change="updateStudentList"
        class="pagination"
      ></el-pagination>
    </div>
    <div class="card">
      <span class="cardTitle">学生报道</span>
      <span class="refreshIcon" @click="updateNotReportedStudents"></span>
      <div class="searchMatriculateContainer">
        <span>录取通知书编号：</span>
        <el-input type="text" class="matriculateNum" v-model="matriculateNumToQuery"></el-input>
        <el-button type="primary" @click="queryStudentByMatriculateNum">搜索</el-button>
      </div>
      <el-table :data="notReportedStudents">
        <el-table-column prop="id" label="学号" width="50px" align="center"></el-table-column>
        <el-table-column prop="name" label="姓名" width="100px" align="left"></el-table-column>
        <el-table-column prop="age" label="年龄" width="50px" align="center"></el-table-column>
        <el-table-column prop="sex" label="性别" width="50px" align="center" :formatter="sexFormatter"></el-table-column>
        <el-table-column prop="address" label="住址" width="300px" align="left"></el-table-column>
        <el-table-column prop="telephone" label="电话" width="150px" align="left"></el-table-column>
        <el-table-column prop="matriculateNum" label="录取通知书编号" width="100px" align="left"></el-table-column>
        <el-table-column label="操作" width="100px">
          <template slot-scope="slot">
            <el-button type="primary" @click="reportStudentID = slot.row.id;showReportDialog = true">报到</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        :total="notReportedStudentPageTotal"
        :current-page.sync="notReportedStudentsPage"
        background
        layout="prev, pager, next"
        @current-change="updateStudentList"
        class="pagination"
      ></el-pagination>
    </div>
    <div class="card" style="width: 30%">
      <span class="cardTitle">学生录取</span>
      <div class="inputGroup">
        <div class="inputItem">
          <span class="label">学生姓名：</span>
          <el-input type="text" class="input" v-model="admitStudent.name"></el-input>
        </div>
        <div class="inputItem">
          <span class="label">年龄：</span>
          <el-input type="text" class="input" v-model="admitStudent.age"></el-input>
        </div>
        <div class="inputItem">
          <span class="label">性别：</span>
          <div class="radios">
            <el-radio :label="1" v-model="admitStudent.sex">男</el-radio>
            <el-radio :label="0" v-model="admitStudent.sex">女</el-radio>
          </div>
        </div>
        <div class="inputItem">
          <span class="label">电话号码：</span>
          <el-input type="text" class="input" v-model="admitStudent.telephone"></el-input>
        </div>
        <div class="inputItem">
          <span class="label">住址：</span>
          <el-input type="text" class="input" v-model="admitStudent.address"></el-input>
        </div>
        <div class="inputItem">
          <span class="label">录取通知书编号：</span>
          <el-input type="text" class="input" v-model="admitStudent.matriculateNum"></el-input>
        </div>
      </div>
      <el-button type="primary" class="submit" style="float: right" @click="admit">提交</el-button>
    </div>
  </div>
</template>

<script>
import student from '../../api/student'

export default {
  name: 'studentManage',
  data () {
    return {
      studentList: null,
      studentListPage: 1,
      studentListTotalPage: 0,
      showConfirmDialog: false,
      deleteStudentID: 0,
      notReportedStudents: null,
      notReportedStudentsPage: 1,
      notReportedStudentPageTotal: 0,
      admitStudent: {
        name: '',
        age: '',
        sex: 1,
        telephone: '',
        address: '',
        matriculateNum: 0
      },
      matriculateNumToQuery: 0,
      reportStudentID: 0,
      showReportDialog: false,
      reportData: {
        dormitoryID: 0,
        clazz: 0
      },
      showReportSuccessDialog: false,
      temporaryPersonInfo: {
        name: '',
        password: ''
      }
    }
  },
  methods: {
    updateStudentList: function () {
      student.allStudents(this.studentListPage).then(resp => {
        console.log(resp)
        this.studentList = resp.paged
        this.studentListTotalPage = resp.totalPages
      })
    },
    sexFormatter: function (row, column, cellValue, index) {
      if (cellValue === 1) {
        return '男'
      } else {
        return '女'
      }
    },
    deleteStudent: function (id) {
      this.deleteStudentID = id
      this.showConfirmDialog = true
    },
    doDeleteStudent: function () {
      this.showConfirmDialog = false
      if (this.deleteStudentID === 0) {
        return
      }
      student.deleteStudent(this.deleteStudentID).then(() => {
        this.deleteStudentID = 0
        this.$message.success('删除成功！')
      }).catch(reason => {
        console.error(reason)
        this.deleteStudentID = 0
        this.$message.error('删除失败!')
      })
    },
    updateNotReportedStudents: function () {
      student.queryNotReportedStudent(this.notReportedStudentsPage).then(resp => {
        this.notReportedStudents = resp.paged
        this.notReportedStudentPageTotal = resp.totalPages
      }).catch(reason => {
        console.error(reason)
        this.$message.error('获取未报道列表失败！')
      })
    },
    admit: function () {
      if (this.admitStudent.name === '' || this.admitStudent.age === 0 || this.admitStudent.address === '' || this.admitStudent.telephone === '' || this.admitStudent.matriculateNum === '') {
        this.admitStudent.name = ''
        this.admitStudent.age = 0
        this.admitStudent.address = ''
        this.admitStudent.telephone = ''
        this.admitStudent.matriculateNum = 0
        this.$message.error('请填写所有字段')
        return
      }
      student.admit(this.admitStudent).then(() => {
        this.admitStudent.name = ''
        this.admitStudent.age = 0
        this.admitStudent.address = ''
        this.admitStudent.telephone = ''
        this.admitStudent.matriculateNum = 0
        this.$message.success('录取成功')
      }).catch(reason => {
        this.admitStudent.name = ''
        this.admitStudent.age = 0
        this.admitStudent.address = ''
        this.admitStudent.telephone = ''
        this.admitStudent.matriculateNum = 0
        this.$message.error('录取失败')
      })
    },
    queryStudentByMatriculateNum: function () {
      if (this.matriculateNumToQuery === 0) {
        this.$message.error('请填写全部字段')
        return
      }
      student.queryStudentByMatriculateNum(this.matriculateNumToQuery).then(resp => {
        if (resp.personID !== -1) {
          this.$message.error('学生已报道')
          return
        }
        this.reportStudentID = resp.id
        this.showReportDialog = true
      }).catch(resp => {
        console.error(resp)
        this.$message.error('查询失败！')
      })
    },
    report: function () {
      this.showReportDialog = false
      if (this.reportStudentID === 0 || this.reportData.clazz === 0 || this.reportData.dormitoryID === 0) {
        this.reportStudentID = 0
        this.reportData.clazz = 0
        this.reportData.dormitoryID = 0
        this.$message.error('请填写所有字段')
        return false
      }
      student.report(this.reportStudentID, this.reportData.clazz, this.reportData.dormitoryID).then(resp => {
        this.reportStudentID = 0
        this.reportData.clazz = 0
        this.reportData.dormitoryID = 0
        this.temporaryPersonInfo.name = resp.temporaryUsername
        this.temporaryPersonInfo.password = resp.temporaryPassword
        this.showReportSuccessDialog = true
      }).catch(reason => {
        console.error(reason)
        this.reportStudentID = 0
        this.reportData.clazz = 0
        this.reportData.dormitoryID = 0
        this.$message.error('报道失败')
      })
    }
  },
  created () {
    this.updateStudentList()
    this.updateNotReportedStudents()
  }
}
</script>

<style src="../../style/studentManage.less" lang="less" scoped></style>
