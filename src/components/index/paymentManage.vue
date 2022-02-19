<template>
  <div class="mainContainer" style="overflow: auto; flex-direction: column">
<!--    <div class="card" style="margin-right: 20px" ref="paymentTableCard">-->

<!--    </div>-->
<!--    <div class="card publishPaymentCard" style="width: 30%" ref="publishPaymentCard">-->
<!--    </div>-->
    <el-card class="paymentTableCard" style="overflow: auto; flex-shrink: 0; margin: 20px;border-radius: 10px;">
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
            <el-button type="primary" @click="">查询未缴费学生</el-button>
            <el-button type="danger">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
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
      }
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
    dateFormatter: function (row, column, cellValue, index) {
      console.log(cellValue)
      return moment(cellValue, 'yyyy MM dd HH mm ss').format('yyyy年MM月DD日 HH:mm:ss')
    },
    queryNotPayedStudent: function (targetID, page) {

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
