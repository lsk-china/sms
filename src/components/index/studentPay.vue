<template>
  <div class="mainContainer" style="flex-direction: column;">
    <el-dialog
      :visible.sync="showPayDialog"
      title="缴费"
      width="40%"
      center
    >
      <h3>请在银行软件缴费，然后在下面填写交易的流水号</h3>
      <el-input type="text" style="margin-top: 20px" v-model="paySerialNumber"></el-input>
      <div style="margin-top: 20px;width: 100%;display: flex;align-items: flex-end">
        <el-button type="primary" @click="pay">提交</el-button>
      </div>
    </el-dialog>
    <div class="card">
      <span class="cardTitle">我的缴费记录</span>
      <el-table :data="myPayRecord">
        <el-table-column prop="id" label="ID" width="50px" align="center"></el-table-column>
        <el-table-column prop="tarPaymentID" label="缴费号" width="75px" align="center"></el-table-column>
        <el-table-column prop="serialNumber" label="流水号" width="100px" align="center"></el-table-column>
        <el-table-column prop="operateDate" label="缴费日期" width="300px" align="left" :formatter="dateFormatter"></el-table-column>
      </el-table>
    </div>
    <div class="card">
      <span class="cardTitle">缴费</span>
      <el-table :data="payments" style="overflow: auto">
        <el-table-column prop="id" label="ID" width="50px" align="center"></el-table-column>
        <el-table-column prop="content" label="内容" width="300px" align="left"></el-table-column>
        <el-table-column prop="fee" label="金额" width="100px" align="center"></el-table-column>
        <el-table-column prop="publishDate" label="发布时间" width="200px" align="left" :formatter="dateFormatter"></el-table-column>
        <el-table-column prop="limitDate" label="缴费时限" width="200px" align="left" :formatter="dateFormatter"></el-table-column>
      </el-table>
      <div style="margin-top: 20px;">
          <span class="label">选择目标缴费通知：</span>
          <el-select v-model="payTargetPaymentID">
            <el-option v-for="payment in payments"
                       v-bind:key="payment.id"
                       :label="payment.content"
                       :value="payment.id"
            ></el-option>
          </el-select>
        <el-button class="submit"
                   type="primary"
                   style="float: right"
                   :disabled="payTargetPaymentID === 0"
                   @click="showPayDialog = true"
        >提交</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import payment from '../../api/payment'
import moment from 'moment'

export default {
  name: 'studentPay',
  data () {
    return {
      myPayRecord: null,
      myPayRecordPage: 1,
      myPayRecordTotalPage: 0,
      payments: null,
      paymentsPage: 1,
      paymentsPageTotal: 0,
      payTargetPaymentID: 0,
      showPayDialog: false,
      paySerialNumber: 0
    }
  },
  methods: {
    updateMyPayRecords: function () {
      payment.myPayRecords(this.myPayRecordPage).then(resp => {
        this.myPayRecord = resp.paged
        this.myPayRecordTotalPage = resp.totalPages
      }).catch(reason => {
        this.$message.error('获取我的缴费记录失败')
      })
    },
    updatePayments: function () {
      payment.payments(this.paymentsPage).then(resp => {
        this.payments = resp.paged
        this.paymentsPageTotal = resp.totalPages
      }).catch(reason => {
        this.$message.error('获取缴费信息列表失败')
      })
    },
    dateFormatter: function (row, column, cellValue, index) {
      console.log(cellValue)
      return moment(cellValue, 'yyyy MM dd HH mm ss').format('yyyy年MM月DD日 HH:mm:ss')
    },
    pay: function () {
      this.showPayDialog = false
      if (this.paySerialNumber === 0) {
        this.$message.error('请填写流水号')
        return
      }
      payment.pay(this.payTargetPaymentID, this.paySerialNumber).then(resp => {
        this.payTargetPaymentID = 0
        this.paySerialNumber = 0
        this.$message.success('缴费成功')
      }).catch(reason => {
        this.payTargetPaymentID = 0
        this.paySerialNumber = 0
        this.$message.error('缴费失败')
      })
    }
  },
  created () {
    this.updateMyPayRecords()
    this.updatePayments()
  }
}
</script>

<style src="../../style/studentPay.less" lang="less" scoped></style>
