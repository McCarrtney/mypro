//跨域代理前缀
// const API_PROXY_PREFIX='/api'
// const BASE_URL = process.env.NODE_ENV === 'production' ? process.env.VUE_APP_API_BASE_URL : API_PROXY_PREFIX
// const BASE_URL = process.env.VUE_APP_API_BASE_URL
// const BASE_URL = "http://localhost:8080"
const BASE_URL = process.env.NODE_ENV === 'production' ? "http://localhost:8080" : process.env.VUE_APP_API_BASE_URL
module.exports = {
  LOGIN: process.env.NODE_ENV === 'production' ? `${BASE_URL}/user/login` : `${BASE_URL}/login`,
  REGISTER: `${BASE_URL}/user/register`,
  GETHEALTHINFO: `${BASE_URL}/user/getHealth`,
  ROUTES: `${BASE_URL}/routes`,
  GOODS: `${BASE_URL}/goods`,
  GOODS_COLUMNS: `${BASE_URL}/columns`,
}
