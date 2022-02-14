<template>
  <div class="mainContainer">
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
      showEnsureDeleteDialog: false
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
    }
  },
  created () {
    admin.personList().then(resp => {
      console.log(resp)
      this.persons = resp
    }).catch(reason => {
      this.$message.error('获取用户列表失败！')
    })
  }
}
</script>

<style src="../../style/accountManage.less" lang="less" scoped></style>
