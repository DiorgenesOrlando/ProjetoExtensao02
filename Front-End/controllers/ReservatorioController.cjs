//const Reservatorio = require('../models/Reservatorio')

//import axios from 'axios';
const axios = require('axios')

const axiosConfig = {
    headers: {
      Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODE1NDQyMjMsInVzZXJfbmFtZSI6InRoaWFnbyIsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiM2IwNjcxMWYtMTBjNS00ODBkLWFhODEtNDYzNDk2ODQ2NDc3IiwiY2xpZW50X2lkIjoid2ViIiwic2NvcGUiOlsid3JpdGUiLCJyZWFkIl19.VVi3jjUKEdno6-9DCeTOk8Ja9U0W9Nygny_hK9-4viU"
    }
  };

const { Op, json } = require('sequelize')


module.exports = class ReservatorioController {

   
    //metodo para listar reservatorios

    //metodo para chamar roda de listagem de reservatorio


    static async mostrarReservatorios(req, res) {
        const resp = await axios.get('http://localhost:8080/reservatorio/ativos', axiosConfig);

        let reservatorios = resp.data;
        //console.log(reservatorios)
        res.render('reservatorio/listar', { reservatorios: reservatorios });
    }

    //metodo para pegar dados do formulario e inserir reservatorio no banco

    static criarReservatorio(req, res) {
        res.render('reservatorio/criar')
    }


    static async criarReservatorioPost(req, res) {      
        const id = req.body.id;
       

        const nomeCombustivel = req.body.combustivel;
        console.log(nomeCombustivel)
        var combustivelId = 1
        if (nomeCombustivel == "GASOLINA") {
            combustivelId = 1

        } else if (nomeCombustivel == "DIESEL") {
            combustivelId = 2

        } else {
            combustivelId = 3
        }

        const combustivel = {
            id: combustivelId
        }
        const reservatorio = {
            id: req.body.id,
            nome: req.body.nome,
            porcentagem: req.body.porcentagem,
            combustivel: combustivel,
            volume : req.body.volume
        }
        console.log(reservatorio)

        const resp = await axios.post(`http://localhost:8080/reservatorio`, reservatorio);


        res.redirect('/reservatorio');
    }

    static async editarReservatorio(req, res) {
        const id = req.params.id;
        console.log('ID : ', id)
        const resp = await axios.get(`http://localhost:8080/reservatorio/${id}`);
        let reservatorio = resp.data;
        console.log(reservatorio)
        res.render('reservatorio/editar', {reservatorio})
    }

    static async editarReservatorioPost(req, res) {
        const id = req.body.id;
       

        const nomeCombustivel = req.body.combustivel;
        console.log(nomeCombustivel)
        var combustivelId = 1
        if (nomeCombustivel == "GASOLINA") {
            combustivelId = 1

        } else if (nomeCombustivel == "DIESEL") {
            combustivelId = 2

        } else {
            combustivelId = 3
        }

        const combustivel = {
            id: combustivelId
        }
        const reservatorio = {
            id: req.body.id,
            nome: req.body.nome,
            porcentagem: req.body.porcentagem,
            combustivel: combustivel,
            volume : req.body.volume
        }
        console.log(reservatorio)

        const resp = await axios.put(`http://localhost:8080/reservatorio/${id}`, reservatorio);


        res.redirect('/reservatorio');
    }


    //metodo para chamar rota ediçao reservatorio


    //metodo para editar reservatorio via psot


    //rota para excluir reservatorio
    static async removerReservatorio(req, res) {
        const id = req.body.id
        console.log(id)
        const resp = await axios.put(`http://localhost:8080/reservatorio/desativar/${id}`);

        if (resp.status == 200) {
            console.log(resp.data)
            res.redirect('/reservatorio');
        }
    }
}
