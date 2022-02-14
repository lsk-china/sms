import base from './base'

const admin = {
  personList: function () {
    return new Promise((resolve, reject) => {
      base.get('/admin/personList', {}).then(resp => {
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
  }
}

export default admin
