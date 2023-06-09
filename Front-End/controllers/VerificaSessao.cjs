const axios = require('axios');

module.exports = class ReservatorioController {

 static async validarToken(req, res) {
    //var token = req.data.token
    var token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODE1MzM0MjgsInVzZXJfbmFtZSI6InRoaWFnbyIsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiYjBhZDU0OWUtYjRlNi00M2YwLTlkNjgtYzM2Y2U4YWJiNDRiIiwiY2xpZW50X2lkIjoid2ViIiwic2NvcGUiOlsid3JpdGUiLCJyZWFkIl19.mKLs4tvKN59tQjdzgjjWaiQEPPg-YRsweWK6CJNGpdw";

    axios.post("http://localhost:8081/oauth/check_token", {token})
    .then(res =>{
        var token = res.data.token
        //localStorage.setItem("token", token);
        console.log(res.data)

        console.log("LOGADO")
    }).catch(err=>{
        console.log("Falhou !")
    })
   
}
}