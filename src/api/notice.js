import base from './base'

const notice = {
  notices: function (page, role) {
    return new Promise((resolve, reject) => {
      let api = '/' + role.toLowerCase().replace('role_', '') + '/notice/list'
      console.debug(api)
      base.get(api, {page: page}).then(resp => {
        console.log(resp)
        resolve(resp)
      }).catch(reason => {
        console.error(reason)
        reject(reason)
      })
    })
  },
  publishNotice: function (title, content) {
    return new Promise((resolve, reject) => {
      base.post('/admin/notice/publish', {
        title: title,
        content: content
      }).then(resp => {
        console.log(resp)
        resolve()
      }).catch(reason => {
        console.error(reason)
        reject(reason)
      })
    })
  },
  deleteNotice: function (id) {
    return new Promise((resolve, reject) => {
      base.get('/admin/notice/delete', {noticeID: id}).then(resp => {
        console.log(resp)
        resolve()
      }).catch(reason => {
        console.error(reason)
        reject(reason)
      })
    })
  },
  publishItemNotice: function (data) {
    return new Promise((resolve, reject) => {
      base.post('/finance/notice/publish', data).then(resp => {
        console.log(resp)
        resolve()
      }).catch(reason => {
        console.error(reason)
        reject(reason)
      })
    })
  },
  receiveItem: function (id) {
    return new Promise((resolve, reject) => {
      base.get('/student/notice/receive', {noticeID: id}).then(resp => {
        console.log(resp)
        resolve()
      }).catch(reason => {
        console.error(reason)
        reject(reason)
      })
    })
  },
  hasReceived: function (id) {
    return new Promise((resolve, reject) => {
      base.get('/student/notice/')
    })
  },
  notReceivedStudents: function (id, page) {
    return new Promise((resolve, reject) => {
      base.get('/finance/notice/notReceivedStudents', {page: page, noticeID: id}).then(resp => {
        console.log(resp)
        resolve(resp)
      }).catch(reason => {
        console.error(reason)
        reject(reason)
      })
    })
  }
}

export default notice
