import _axios from 'axios'
import qs from 'qs'

const axios = _axios.create({
//  baseURL: 'http://124.222.28.103:9001/api',
  baseURL: 'http://localhost:9001/api',
  withCredentials: true
})

const base = {
  get: function (url, params) {
    return new Promise((resolve, reject) => {
      axios.get(url, {
        params: params
      }).then(resp => {
        let data = resp.data
        if (data.code === 200) {
          resolve(data.data)
        } else {
          reject(data)
        }
      }).catch(err => {
        console.log(err)
        reject(err)
      })
    })
  },
  post: function (url, params) {
    return new Promise((resolve, reject) => {
      axios.post(url, params, {
        transformRequest: function (params) {
          return qs.stringify(params)
        }
      }).then(resp => {
        let data = resp.data
        if (data.code === 200) {
          resolve(data.data)
        } else {
          reject(data)
        }
      }).catch(err => {
        console.log(err)
        reject(err)
      })
    })
  }
}

export default base
