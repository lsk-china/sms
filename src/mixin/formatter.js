import moment from 'moment'
import storage from 'good-storage'
import auth from '../api/auth'

const formatter = {
  methods: {
    dateFormatter: function (row, column, cellValue, index) {
      console.log(cellValue)
      return moment(cellValue, 'yyyy MM dd HH mm ss').format('yyyy年MM月DD日 HH:mm:ss')
    },
    sexFormatter: function (row, column, cellValue, index) {
      if (cellValue === 1) {
        return '男'
      } else {
        return '女'
      }
    },
    noticeFormatter: function (row, column, cellValue, index) {
      switch (row.type) {
        case 'PLAIN':
          return cellValue
        case 'ITEM':
          let json = JSON.parse(cellValue)
          let receiveTime = moment(json.time, 'yyyy MM dd HH mm ss').format('yyyy年MM月DD日 HH:mm:ss')
          return '请于' + receiveTime + '在' + json.address + '领取' + json.content
        default:
          return ''
      }
    },
    uid2name: function (id) {
      return new Promise(resolve => {
        if (storage.has('USERNAME-' + id)) {
          resolve(storage.get('USERNAME-' + id))
        } else {
          auth.username(id).then(resp => {
            storage.set('USERNAME-' + id, resp)
            resolve(resp)
          })
        }
      })
    },
    usernameFormatter: async function (row, column, cellValue, index) {
      let username = await this.uid2name(cellValue)
      return username
    }
  }
}

export default formatter
