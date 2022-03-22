<template>
  <div class="mainContainer" style="flex-direction: column; flex-shrink: 0; overflow-y: auto">
    <el-dialog
      :visible.sync="showGrantPersonDialog"
      title="选择权限"
      width="30%"
    >
      <el-select v-model="grantPersonTargetRole">
        <el-option value="ADMIN" label="管理员"></el-option>
        <el-option value="STUDENT" label="学生"></el-option>
        <el-option value="FINANCE" label="财务"></el-option>
      </el-select>
      <el-button type="primary" class="submit" style="margin-top: 0" v-on:click="doGrantPerson">提交</el-button>
    </el-dialog>
    <el-dialog
      :visible.sync="showEnsureDeleteDialog"
      title="警告"
      width="30%"
    >
      <h1 style="text-align: center">您确定要删除此用户吗？此操作不可撤销！</h1>
      <el-button style="margin-top: 20px;" type="danger" @click="doDeletePerson">确定</el-button>
      <el-button style="margin-top: 20px;" type="default" @click="showEnsureDeleteDialog = false;deletePersonID = 0">取消</el-button>
    </el-dialog>
    <div class="card">
      <span class="cardTitle">用户列表</span>
      <span class="refreshIcon" @click="refreshTable"></span>
      <keep-alive>
        <el-table :data="persons" stripe border width="100%" style="margin-top: 20px;">
          <el-table-column prop="id" label="用户ID" width="100px" align="center"></el-table-column>
          <el-table-column prop="name" label="用户名" width="300px" align="left"></el-table-column>
          <el-table-column prop="role" label="角色" width="100px" align="center"></el-table-column>
          <el-table-column label="操作" width="200px">
            <template slot-scope="slot">
              <el-button type="primary" @click="grantPerson(slot.row.id)">授权</el-button>
              <el-button type="danger" @click="deletePerson(slot.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </keep-alive>
      <el-pagination
        background
        layout="prev, pager, next"
        :total="userListTotalPages"
        :current-page.sync="userListPage"
        @current-change="refreshTable"
      >
      </el-pagination>
    </div>
    <div class="card">
      <span class="cardTitle" style="float: left">新建用户</span>
      <div class="inputGroup">
        <div class="groupItem">
          <div class="labelWrap"><span class="label">用户名:</span></div>
          <el-input type="text" class="input" v-model="createPerson.name"></el-input>
        </div>
        <div class="groupItem">
          <div class="labelWrap"><span class="label">密码:</span></div>
          <el-input type="password" class="input" v-model="createPerson.password"></el-input>
        </div>
        <div class="groupItem">
          <div class="labelWrap" style="margin-right: 20px;"><span class="label">角色:</span></div>
          <el-select v-model="createPerson.role">
            <el-option value="ADMIN" label="管理员"></el-option>
            <el-option value="FINANCE" label="财务"></el-option>
          </el-select>
        </div>
        <el-button type="primary" class="submit createUserSubmit" @click="doCreatePerson">提交</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import admin from '../../api/admin'

export default {
  name: 'userManage',
  data () {
    return {
      persons: null,
      grantPersonID: 0,
      grantPersonTargetRole: '',
      deletePersonID: 0,
      showGrantPersonDialog: false,
      showEnsureDeleteDialog: false,
      createPerson: {
        name: '',
        password: '',
        role: ''
      },
      userListPage: 1,
      userListTotalPages: 0
    }
  },
  methods: {
    grantPerson: function (id) {
      this.grantPersonID = id
      this.showGrantPersonDialog = true
    },
    deletePerson: function (id) {
      this.deletePersonID = id
      this.showEnsureDeleteDialog = true
    },
    doGrantPerson: function () {
      this.showGrantPersonDialog = false
      if (this.grantPersonTargetRole === '' || this.grantPersonID === 0) {
        this.$message.error('请选择！')
        return
      }
      admin.grantPerson(this.grantPersonID, this.grantPersonTargetRole).then(() => {
        this.grantPersonTargetRole = ''
        this.grantPersonID = 0
        this.$message.success('修改成功！')
      }).catch(reason => {
        this.$message.error('修改失败！')
      })
    },
    doDeletePerson: function () {
      this.showEnsureDeleteDialog = false
      if (this.deletePersonID === 0) {
        return
      }
      admin.deletePerson(this.deletePersonID).then(() => {
        this.deletePersonID = 0
        this.$message.success('删除成功!')
      }).catch(reason => {
        this.$message.error('删除失败!')
      })
    },
    doCreatePerson: function () {
      this.createPerson.name = this.createPerson.name.trim()
      this.createPerson.name = this.createPerson.name.trim()
      if (this.createPerson.name === '' || this.createPerson.password === '' || this.createPerson.role === '') {
        this.$message.error('请填写参数！')
        return
      }
      admin.createPerson(this.createPerson.name, this.createPerson.password, this.createPerson.role).then(() => {
        this.createPerson.name = ''
        this.createPerson.password = ''
        this.createPerson.role = ''
        this.$message.success('创建成功！')
      }).catch(reason => {
        this.createPerson.name = ''
        this.createPerson.password = ''
        this.createPerson.role = ''
        this.$message.error('创建失败！')
      })
    },
    refreshTable: function () {
      admin.personList(this.userListPage).then(resp => {
        console.log(resp)
        this.persons = resp.paged
        this.userListTotalPages = resp.totalPages * 10
      }).catch(reason => {
        this.$message.error('获取用户列表失败！')
      })
    }
  },
  created () {
    this.refreshTable()
  }
}
</script>

<style src="../../style/userManage.less" lang="less" scoped></style>
