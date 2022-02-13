import base from './base'

const auth = {
  alreadyLogin: function () {
    return new Promise((resolve, reject) => {
      base.get('/alreadyLogin', {}).then(res => {
        resolve(res.data)
      })
    })
  },
  login: function (username, password) {
    return new Promise((resolve, reject) => {
      base.post('/login', {
        username: username,
        password: password
      }).then(resp => {
        console.log(resp)
        resolve()
      }).catch(reason => {
        console.log(reason)
        reject(reason.message)
      })
    })
  },
  loginInfo: function () {
    return new Promise((resolve, reject) => {
      base.get('/loginInfo', {}).then(resp => {
        console.log(resp)
        resolve(resp)
      }).catch(reason => {
        reject(reason)
      })
    })
  },
  changeUsername: function (newUsername) {
    return new Promise((resolve, reject) => {
      base.get('/changeUsername', {
        newUsername: newUsername
      }).then(resp => {
        console.log(resp)
        resolve()
      }).catch(reason => {
        console.log(reason)
        reject(reason)
      })
    })
  },
  changePassword: function (newPassword) {
    return new Promise((resolve, reject) => {
      base.post('/changePassword', {
        newPassword: newPassword
      }).then(resp => {
        console.log(resp)
        resolve()
      }).catch(reason => {
        reject(reason)
      })
    })
  }
}

export default auth
