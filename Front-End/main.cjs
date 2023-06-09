import axios from 'axios'

class Api {
  static async getAddress(cep) {
    const response = 
    await axios.get(`http://localhost:8080/maquinas`)
   
    console.log(response.data)
  }
}

//Criação da Classe Address


Api.getAddress()
