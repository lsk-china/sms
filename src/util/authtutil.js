import storage from 'good-storage'

export default {
  currentRole: function () {
    if (!storage.has('auth')) {
      throw new Error('User not login')
    }
    let auth = storage.get('auth')
    return auth.authorities[0].role
  }
}
