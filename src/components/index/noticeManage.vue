<template>
  <div class="mainContainer" style="display: flex; flex-direction: column;">
    <el-dialog
      :visible.sync="showDeleteNoticeDialog"
      title="确认"
      center
    >
      <div class="full flexCenter" style="flex-direction: column">
        <h1>你确认要删除这条公告吗？</h1>
        <h2 style="color: red">该操作不可撤销！</h2>
        <div style="margin-top: 10px">
          <el-button type="primary" @click="deleteNoticeID = 0; showDeleteNoticeDialog = false">取消</el-button>
          <el-button type="danger" @click="deleteNotice">删除</el-button>
        </div>
      </div>
    </el-dialog>
    <el-dialog
      :visible.sync="showNotReceivedStudentDialog"
      title="查询结果"
      center
      width="20%"
    >
      <div style="display: flex; flex-direction: column; align-items: center">
        <span v-for="(item, i) in notReceivedStudents" v-text="item" v-bind:key="i"></span>
        <el-button type="primary" @click="showNotReceivedStudentDialog = false">关闭</el-button>
      </div>
    </el-dialog>
    <div class="card" v-if="role !== 'ROLE_STUDENT'">
      <span class="cardTitle">公告列表</span>
      <el-table :data="noticeTable">
        <el-table-column prop="id" label="通知ID" align="center" width="50px"></el-table-column>
        <el-table-column prop="title" label="标题" align="left" width="150px"></el-table-column>
        <el-table-column prop="content" label="内容" align="left" width="300px" :formatter="noticeFormatter"></el-table-column>
        <el-table-column prop="publishDate" label="发布日期" align="left" width="200px" :formatter="dateFormatter"></el-table-column>
        <el-table-column prop="publisher" label="发布者" align="left" width="150px"></el-table-column>
        <el-table-column label="操作" align="left" width="170px">
          <template slot-scope="slot">
            <el-button v-if="role === 'ROLE_ADMIN'"
                       type="danger"
                       style="margin-right: 10px"
                       @click="showDeleteNoticeDialog = true; deleteNoticeID = slot.row.id"
            >删除</el-button>
            <el-button v-if="role === 'ROLE_FINANCE' && slot.row.type === 'ITEM'"
                       type="primary"
                       style="margin-right: 10px"
                       @click="notReceivedStudentsNoticeID = slot.row.id; updateNotReceivedStudents()"
            >查询未领取学生</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        :pageCount="noticeTableTotalPages"
        :current-page.sync="noticeTablePage"
        background
        layout="prev, pager, next"
        @current-change="updateNoticeTable"
        class="pagination"
      ></el-pagination>
    </div>
    <div class="card" v-if="this.role !== 'ROLE_STUDENT'" style="width: 30%; float: left;">
      <span class="cardTitle">物品领取公告</span>
      <div class="inputGroup">
        <div class="inputItem">
          <span class="label">标题</span>
          <el-input type="text" v-model="itemNotice.title"></el-input>
        </div>
        <div class="inputItem">
          <span class="label">要领取的物品</span>
          <el-input type="text" v-model="itemNotice.content"></el-input>
        </div>
        <div class="inputItem">
          <span class="label">领取地点</span>
          <el-input type="text" v-model="itemNotice.address"></el-input>
        </div>
        <div class="inputItem">
          <span class="label">领取时间</span>
          <el-date-picker type="datetime"
                          style="width: 100%"
                          v-model="itemNotice.receiveDate"
                          value-format="yyyy MM dd HH mm ss"
                          v-on:change="(date) => {itemNotice.receiveDate = date}"
          ></el-date-picker>
        </div>
      </div>
      <el-button type="primary" @click="createItemNotice" class="submit" style="float: right; margin-top: 20px">提交</el-button>
    </div>
    <div class="card" v-if="this.role !== 'ROLE_STUDENT'" style="width: 30%; margin-bottom: 20px; float: left">
      <span class="cardTitle">发布公告</span>
      <div clas="inputGroup">
        <div class="inputItem">
          <span class="label">标题：</span>
          <el-input type="text" v-model="publishNoticeTitle"></el-input>
        </div>
        <div class="inputItem">
          <span class="label">内容：</span>
          <el-input type="textarea" resize="none" rows="4" v-model="publishNoticeContent"></el-input>
        </div>
      </div>
      <el-button type="primary" style="float: right; margin-top: 20px;" @click="publishNotice">提交</el-button>
    </div>
    <div v-if="role === 'ROLE_STUDENT'"
         class="card"
         v-for="notice in noticeTable"
         v-bind:key="notice.id"
         style="align-self: center; width: 50%"
    >
      <div style="display: flex; align-items: baseline; width: 100%; justify-content: space-between">
        <span class="cardTitle" v-text="notice.title"></span>
        <div class="metaContainer">
          <i class="el-icon-user-solid icon"></i>
          <p class="metadata" v-text="notice.publisher"></p>
          <i class="el-icon-time"></i>
          <p class="metadata" v-text="formatTime(notice.publishDate)"></p>
        </div>
      </div>
      <div style="display: flex; flex-direction: row; justify-content: space-between; width: 100%; align-items: baseline">
        <p v-text="formatItemNotice(notice.type, notice.content)"></p>
        <el-button v-if="notice.type === 'ITEM'"
                   type="primary"
                   style="margin-right: 10px;"
                   :disabled="notice.hasReceived"
                   @click="receiveItem(notice.id)"
        >
          <span v-if="notice.hasReceived">已领取</span>
          <span v-else>确认</span>
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
import notice from '../../api/notice'
import moment from 'moment'

export default {
  name: 'noticeManage',
  data () {
    return {
      role: '',
      noticeTable: null,
      noticeTablePage: 1,
      noticeTableTotalPages: 0,
      publishNoticeTitle: '',
      publishNoticeContent: '',
      deleteNoticeID: 0,
      showDeleteNoticeDialog: false,
      itemNotice: {
        title: '',
        content: '',
        address: '',
        receiveDate: null
      },
      notReceivedStudents: null,
      notReceivedStudentsPage: 1,
      showNotReceivedStudentDialog: false
    }
  },
  methods: {
    updateNoticeTable: function () {
      notice.notices(this.noticeTablePage, this.role).then(resp => {
        this.noticeTable = resp.paged
        this.noticeTableTotalPages = resp.totalPages
      }).catch(reason => {
        this.$message.error('获取公告列表失败！')
      })
    },
    publishNotice: function () {
      if (this.publishNoticeContent === '' || this.publishNoticeTitle === '') {
        this.publishNoticeContent = ''
        this.publishNoticeTitle = ''
        this.$message.error('请填写全部字段！')
        return
      }
      // notice.publishNotice(this.publishNoticeTitle, this.publishNoticeContent).then(() => {
      //   this.publishNoticeContent = ''
      //   this.publishNoticeTitle = ''
      //   this.updateNoticeTable()
      //   this.$message.success('发布成功')
      // }).catch(reason => {
      //   this.publishNoticeContent = ''
      //   this.publishNoticeTitle = ''
      //   this.$message.error('发布失败！')
      // })
      notice.publishNotice2({
        title: this.publishNoticeTitle,
        content: this.publishNoticeContent
      }, 'PLAIN').then(() => {
        this.publishNoticeContent = ''
        this.publishNoticeTitle = ''
        this.updateNoticeTable()
        this.$message.success('发布成功')
      }).catch(reason => {
        console.error(reason)
        this.publishNoticeContent = ''
        this.publishNoticeTitle = ''
        this.$message.error('发布失败！')
      })
    },
    deleteNotice: function () {
      this.showDeleteNoticeDialog = false
      if (this.deleteNoticeID === 0) {
        return
      }
      notice.deleteNotice(this.deleteNoticeID).then(() => {
        this.deleteNoticeID = 0
        this.$message.success('删除成功！')
        this.updateNoticeTable()
      }).catch(reason => {
        this.deleteNoticeID = 0
        this.$message.error('删除失败！')
      })
    },
    createItemNotice: function () {
      if (this.itemNotice.title === '' || this.itemNotice.content === '' || this.itemNotice.address === '' || this.itemNotice.receiveDate === '') {
        this.itemNotice.title = ''
        this.itemNotice.content = ''
        this.itemNotice.address = ''
        this.itemNotice.receiveDate = null
        this.$message.error('请填写全部字段')
        return
      }
      notice.publishNotice2(this.itemNotice, 'ITEM').then(() => {
        this.itemNotice.title = ''
        this.itemNotice.content = ''
        this.itemNotice.address = ''
        this.itemNotice.receiveDate = null
        this.$message.success('发布成功')
      }).catch(reason => {
        console.error(reason)
        this.itemNotice.title = ''
        this.itemNotice.content = ''
        this.itemNotice.address = ''
        this.itemNotice.receiveDate = null
        this.$message.error('发布失败')
      })
    },
    formatItemNotice: function (type, content) {
      switch (type) {
        case 'PLAIN':
          return content
        case 'ITEM':
          let json = JSON.parse(content)
          let receiveTime = moment(json.time, 'yyyy MM dd HH mm ss').format('yyyy年MM月DD日 HH:mm:ss')
          return '请于' + receiveTime + '在' + json.address + '领取' + json.content
        default:
          return ''
      }
    },
    formatTime: function (time) {
      return moment(time, 'yyyy MM dd HH mm ss').format('yyyy年MM月DD日 HH:mm:ss')
    },
    receiveItem: function (id) {
      notice.receiveItem(id).then(() => {
        this.$message.success('领取成功')
        this.updateNoticeTable()
      }).catch(reason => {
        this.$message.error('领取失败')
      })
    },
    updateNotReceivedStudents: function () {
      console.log('debug')
      notice.notReceivedStudents(this.notReceivedStudentsNoticeID, this.notReceivedStudentsPage).then(resp => {
        this.notReceivedStudents = resp.paged
        this.showNotReceivedStudentDialog = true
      }).catch(reason => {
        this.$message.error('查询失败')
      })
    }
  },
  created () {
    this.role = this.$parent.loginInfo.authorities[0].role
    this.updateNoticeTable()
  }
}
</script>

<style src="../../style/noticeManage.less" lang="less" scoped></style>
