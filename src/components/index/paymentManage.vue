<template>
  <div class="mainContainer" style="overflow: auto; flex-direction: column">
    <el-dialog
      :visible.sync="showNotPaymentStudentsDialog"
      width="fit-content"
      title="未缴费学生列表"
      class="notPaymentStudentsDialog"
      custom-class="unsetMargin"
      top="unset"
    >
      <div class="notPaymentStudentsTableWrapper">
        <el-table :data="notPayedStudents">
          <el-table-column prop="id" label="学号" width="50px" align="center"></el-table-column>
          <el-table-column prop="name" label="姓名" width="100px" align="left"></el-table-column>
          <el-table-column prop="age" label="年龄" width="50px" align="center"></el-table-column>
          <el-table-column prop="sex" label="性别" width="50px" align="center" :formatter="sexFormatter"></el-table-column>
          <el-table-column prop="address" label="住址" width="300px" align="left"></el-table-column>
          <el-table-column prop="telephone" label="电话" width="150px" align="left"></el-table-column>
          <el-table-column prop="matriculateNum" label="录取通知书编号" width="100px" align="left"></el-table-column>
        </el-table>
        <div class="myPagination">
          <button class="paginationButton flexCenter"
                  :disabled="notPayedStudentsPage === 1"
                  @click="queryNotPayedStudent(--notPayedStudentsPage)"
          >
            <i class="el-icon-arrow-left"></i>
          </button>
          <div class="myPager">
            <div v-for="i in notPayedStudentsTotalPage"
                 v-bind:key="i"
                 class="paginationButton flexCenter"
                 :class="[{currentButton : i === notPayedStudentsPage}, {hoverBlue : i !== notPayedStudentsPage}]"
                 @click="queryNotPayedStudent(i); notPayedStudentsPage = i"
            >
              <span v-text="i"></span>
            </div>
          </div>
          <button class="paginationButton flexCenter"
                  :disabled="notPayedStudentsPage === notPayedStudentsTotalPage"
                  @click="queryNotPayedStudent(++notPayedStudentsPage)"
          >
            <i class="el-icon-arrow-right"></i>
          </button>
        </div>
      </div>
    </el-dialog>
    <el-dialog
      :visible.sync="showConfirmDialog"
      title="警告"
      width="30%"
    >
      <h1 style="text-align: center">您确定要删除此学生吗？此操作不可撤销！</h1>
      <el-button style="margin-top: 20px;" type="danger" @click="doDeletePayment">确定</el-button>
      <el-button style="margin-top: 20px;" type="default" @click="showConfirmDialog = false;deletePaymentID = 0">取消</el-button>
    </el-dialog>
    <el-card class="paymentTableCard" style="overflow: auto; flex-shrink: 0; margin: 20px;border-radius: 10px;padding-bottom: 0;">
      <span class="cardTitle">缴费信息</span>
      <el-table :data="payments" style="overflow: auto">
        <el-table-column prop="id" label="ID" width="50px" align="center"></el-table-column>
        <el-table-column prop="content" label="内容" width="300px" align="left"></el-table-column>
        <el-table-column prop="fee" label="金额" width="100px" align="center"></el-table-column>
        <el-table-column prop="publishDate" label="发布时间" width="200px" align="left" :formatter="dateFormatter"></el-table-column>
        <el-table-column prop="limitDate" label="缴费时限" width="200px" align="left" :formatter="dateFormatter"></el-table-column>
        <el-table-column prop="payedCount" label="已缴费人数" width="100px" align="center"></el-table-column>
        <el-table-column label="操作" align="left" width="300px">
          <template slot-scope="slot">
            <el-button type="primary" @click="notPayedStudentsPaymentID = slot.row.id;queryNotPayedStudent(1)">查询未缴费学生</el-button>
            <el-button type="danger" @click="deletePaymentID = slot.row.id; showConfirmDialog = true">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        :total="paymentsPageTotal"
        :current-page.sync="paymentsPage"
        background
        layout="prev, pager, next"
        @current-change="updatePayments"
        class="pagination"
      ></el-pagination>
    </el-card>
    <el-card>
      <span class="cardTitle">发布缴费信息</span>
      <div class="inputGroup">
        <div class="inputItem">
          <span class="label">缴费内容：</span>
          <el-input type="text" class="input" v-model="publishPayment.content"></el-input>
        </div>
        <div class="inputItem">
          <span class="label">缴费金额：</span>
          <el-input type="text" class="input" v-model="publishPayment.fee"></el-input>
        </div>
        <div class="inputItem">
          <span class="label">在这天前缴费：</span>
          <el-date-picker
            v-model="publishPayment.limitDate"
            type="datetime"
            class="input"
            value-format="yyyy MM dd HH mm ss"
            v-on:change="(date) => {publishPayment.limitDate = date}"
          ></el-date-picker>
        </div>
      </div>
      <el-button
        type="primary"
        class="submit"
        style="float: right"
        @click="doPublishPayment"
      >提交</el-button>
    </el-card>
  </div>
</template>

<script>
import payment from '../../api/payment'
import moment from 'moment'

export default {
  name: 'paymentManage',
  data () {
    return {
      payments: null,
      paymentsPage: 1,
      paymentsPageTotal: 0,
      publishPayment: {
        content: '',
        fee: 0,
        limitDate: ''
      },
      notPayedStudents: null,
      notPayedStudentsPage: 1,
      notPayedStudentsTotalPage: 0,
      notPayedStudentsPaymentID: 0,
      showNotPaymentStudentsDialog: false,
      showConfirmDialog: false,
      deletePaymentID: 0
    }
  },
  methods: {
    updatePayments: function () {
      payment.payments(this.paymentsPage).then(resp => {
        this.payments = resp.paged
        this.paymentsPageTotal = resp.totalPages
      }).catch(reason => {
        this.$message.error('获取缴费信息列表失败')
      })
    },
    doPublishPayment: function () {
      if (this.publishPayment.content === '' || this.publishPayment.fee === 0 || this.publishPayment.limitDate === '') {
        this.publishPayment.content = ''
        this.publishPayment.fee = 0
        this.publishPayment.limitDate = ''
        this.$message.error('请填写全部字段')
        return
      }
      if (!moment(this.publishPayment.limitDate, 'yyyy MM dd HH mm ss').isBefore(moment())) {
        this.publishPayment.content = ''
        this.publishPayment.fee = 0
        this.publishPayment.limitDate = ''
        this.$message.error('缴费时限应晚于现在！')
        return
      }
      payment.publishPayment(this.publishPayment.content, this.publishPayment.fee, this.publishPayment.limitDate)
        .then(() => {
          this.publishPayment.content = ''
          this.publishPayment.fee = 0
          this.publishPayment.limitDate = ''
          this.$message.success('发布成功')
        }).catch(reason => {
          this.publishPayment.content = ''
          this.publishPayment.fee = 0
          this.publishPayment.limitDate = ''
          this.$message.error('发布失败')
        })
    },
    queryNotPayedStudent: function (page) {
      if (this.notPayedStudentsPaymentID === 0) {
        console.error('notPayedStudentsPaymentID is zero!')
        return
      }
      payment.queryNotPayedStudent(this.notPayedStudentsPaymentID, page).then(resp => {
        this.notPayedStudents = resp.paged
        this.notPayedStudentsTotalPage = resp.totalPages
        console.log(this.notPayedStudentsTotalPage)
        this.showNotPaymentStudentsDialog = true
      }).catch(reason => {
        this.$message.error('查询失败！')
      })
    },
    doDeletePayment: function () {
      this.showConfirmDialog = false
      if (this.deletePaymentID === 0) {
        return
      }
      payment.deletePayment(this.deletePaymentID).then(() => {
        this.deletePaymentID = 0
        this.$message.success('删除成功')
      }).catch(reason => {
        this.$message.error('删除失败')
      })
    }
  },
  created () {
    this.updatePayments()
  },
  mounted () {
    this.$nextTick(() => {
      let paymentTableCardElement = this.$refs.paymentTableCard
      let publishPaymentCardElement = this.$refs.publishPaymentCard
      let paymentTableCardHeight = window.getComputedStyle(paymentTableCardElement).height
      let publishPaymentCardTop = (paymentTableCardHeight + 20) + 'px'
      publishPaymentCardElement.style.top = publishPaymentCardTop
    })
  }
}
</script>

<style src="../../style/paymentManage.less" lang="less" scoped></style>
