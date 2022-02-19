import base from './base'

const payment = {
  payments: function (page) {
    return new Promise((resolve, reject) =>  {
      base.get('/payments', {page: page}).then(resp => {
        console.log(resp)
        resolve(resp)
      }).catch(reason => {
        console.error(reason)
        reject(reason)
      })
    })
  },
  publishPayment: function (content, fee, limitDate) {
    return new Promise((resolve, reject) => {
      base.post('/finance/publishPayment', {
        content: content,
        fee: fee,
        limitDate: limitDate
      }).then(resp => {
        console.log(resp)
        resolve()
      }).catch(reason => {
        console.error(reason)
        reject(reason)
      })
    })
  },
  queryNotPayedStudent: function (targetID, page) {
    return new Promise((resolve, reject) => {
      base.get('/finance/notPayedStudents', )
    })
  }
}

export default payment
