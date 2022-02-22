import base from './base'

const student = {
  allStudents: function (page) {
    return new Promise((resolve, reject) => {
      base.get('/admin/allStudents', {page: page}).then(resp => {
        console.log(resp)
        resolve(resp)
      }).catch(reason => {
        reject(reason)
      })
    })
  },
  deleteStudent: function (id) {
    return new Promise((resolve, reject) => {
      base.get('/admin/deleteStudent', {id: id}).then(resp => {
        console.log(resp)
        resolve()
      }).catch(reason => {
        reject(reason)
      })
    })
  },
  queryNotReportedStudent: function (page) {
    return new Promise((resolve, reject) => {
      base.get('/admin/notReportedStudents', {page: page}).then(resp => {
        console.log(resp)
        resolve(resp)
      }).catch(reason => {
        reject(reason)
      })
    })
  },
  admit: function (student) {
    return new Promise((resolve, reject) => {
      base.post('/admin/admitStudent', {
        name: student.name,
        age: student.age,
        sex: student.sex,
        matriculateNum: student.matriculateNum,
        address: student.address,
        telephone: student.telephone
      }).then(resp => {
        console.log(resp)
        resolve()
      }).catch(reason => {
        reject(reason)
      })
    })
  },
  queryStudentByMatriculateNum: function (matriculateNum) {
    return new Promise((resolve, reject) => {
      base.get('/admin/queryStudentByMatriculate', {matriculate: matriculateNum}).then(resp => {
        console.log(resp)
        resolve(resp)
      }).catch(reason => {
        reject(reason)
      })
    })
  },
  report: function (id, clazz, dormitoryID) {
    return new Promise((resolve, reject) => {
      base.post('/admin/studentReport', {
        id: id,
        clazz: clazz,
        dormitoryID: dormitoryID
      }).then(resp => {
        console.log(resp)
        resolve(resp)
      }).catch(reason => {
        reject(reason)
      })
    })
  },
  studentInfo: function () {
    return new Promise((resolve, reject) => {
      base.get('/student/info', {}).then(resp => {
        console.log(resp)
        resolve(resp)
      }).catch(reason => {
        console.error(reason)
        reject(reason)
      })
    })
  }
}

export default student
