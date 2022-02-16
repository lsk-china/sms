import base from './base'

const admin = {
  personList: function (page) {
    return new Promise((resolve, reject) => {
      base.get('/admin/personList', {
        page: page
      }).then(resp => {
        console.log(resp)
        resolve(resp)
      }).catch(reason => {
        reject(reason)
      })
    })
  },
  grantPerson: function (id, role) {
    return new Promise((resolve, reject) => {
      base.get('/admin/grant', {
        targetID: id,
        role: role
      }).then(resp => {
        console.log(resp)
        resolve()
      }).catch(reason => {
        reject(reason)
      })
    })
  },
  deletePerson: function (id) {
    return new Promise((resolve, reject) => {
      base.get('/admin/deletePerson', {
        id: id
      }).then(resp => {
        resolve()
      }).catch(reason => {
        reject(reason)
      })
    })
  },
  createPerson: function (name, password, role) {
    return new Promise((resolve, reject) => {
      base.post('/admin/createPerson', {
        username: name,
        password: password,
        role: role
      }).then(resp => {
        console.log(resp)
        resolve()
      }).catch(reason => {
        reject(reason)
      })
    })
  }
}

export default admin
