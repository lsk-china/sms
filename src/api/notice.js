import base from './base'
import authtutil from '../util/authtutil'

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
  },
  publishNotice2: function (data, type) {
    return new Promise((resolve, reject) => {
      let role = authtutil.currentRole()
      data['type'] = type
      console.log(role)
      if (role === 'ROLE_ADMIN') {
        base.post('/admin/notice/publish', data).then(resp => {
          console.log(resp)
          resolve()
        }).catch(reason => {
          console.error(reason)
          reject(reason)
        })
      } else if (role === 'ROLE_FINANCE') {
        base.post('/finance/notice/publish', data).then(resp => {
          console.log(resp)
          resolve()
        }).catch(reason => {
          console.error(reason)
          reject(reason)
        })
      } else {
        reject(new Error('Permission dined'))
      }
    })
  }
}

export default notice
