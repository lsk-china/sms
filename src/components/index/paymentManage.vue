<template>
  <div class="mainContainer">
    <div class="card">
      <span class="cardTitle">缴费信息</span>
      <el-table></el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: 'paymentManage',
  data () {
    return {
      payments: null,
      paymentsPage: 1,
      paymentsPageTotal: 0
    }
  },
  created () {
  }
}
</script>

<style src="../../style/paymentManage.less" lang="less" scoped></style>
