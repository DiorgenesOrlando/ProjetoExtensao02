//const Equipamento = require('../models/Equipamento')

//import axios from 'axios';
const axios = require('axios');
const { response } = require('express');
const fs = require('fs')
const { Op, json } = require('sequelize');
const { Stream } = require('stream');

module.exports = class EquipamentoController {

    //metodo para listar equipamentos

    //metodo para chamar roda de listagem de equipamento


    static async mostrarEquipamentos(req, res) {
        const token = req.body.token;
        console.log(token)
        //console.log(`token : ${token}`)
        const config = {
          headers: {
            Authorization: `Bearer ${token}`
          }
        };
      //  console.log(config)
      
        try {
          const resp = await axios.get('http://localhost:8080/maquinas/ativos', config);
          const equipamentos = resp.data;
        //  console.log(equipamentos)
          res.render('equipamento/listar', { equipamentos });
        } catch (error) {
          console.error(error);
          res.status(500).send('Ocorreu um erro ao buscar os equipamentos');
        }
      }
      
    //metodo para pegar dados do formulario e inserir equipamento no banco

    static criarEquipamento(req, res) {
        res.render('equipamento/criar')
    }


    static async criarEquipamentoPost(req, res) {
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

        const equipamento = {
            codigoSap: req.body.codigoSap,
            ordemInterna: req.body.ordemInterna,
            centroCusto : req.body.centroCusto,
            nomeEquipamento: req.body.nomeEquipamento,
            combustivel: combustivel,
            volumeTanque: req.body.volumeTanque,
            capacidade: req.body.capacidade
        }
        console.log(equipamento)

        const resp = await axios.post(`http://localhost:8080/maquinas`, equipamento);


        res.redirect('/equipamento');

    }


    static async editarEquipamento(req, res) {
        const id = req.params.id;
        console.log('ID : ', id)
        const resp = await axios.get(`http://localhost:8080/maquinas/${id}`);
        let equipamento = resp.data;
        console.log(equipamento)
        res.render('equipamento/editar', {equipamento})
    }
     
    static async codigoEquipamento(req, res) {
        try {
            const id = req.params.id;
            console.log('ID : ', id);
    
            const filePath = `public/imagens/qr/${id}.png`;
            if (fs.existsSync(filePath)) {
                console.log("imagem encontrada")
                return res.download(filePath);
            }
    
            const url = `http://localhost:8080/maquinas/codigo/${id}`;
            const response = await axios.get(url, { responseType: 'stream' });
    
            const file = fs.createWriteStream(filePath);
            file.on('finish', () => {
                console.log("imagem criada")
                res.download(filePath);
            });
    
            response.data.pipe(file);
    
        } catch (error) {
            console.error(error);
            res.status(500).send('Erro ao processar solicitação');
        }
    }
      
    

    static async editarEquipamentoPost(req, res) {

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
        const id1 = combustivelId;
        console.log(combustivelId)
        const combustivel = {
            id: combustivelId
        }
        const equipamento = {
            codigoSap: req.body.codigoSap,
            ordemInterna: req.body.ordemInterna,
            nomeEquipamento: req.body.nomeEquipamento,
            combustivel: combustivel,
            volumeTanque: req.body.capacidade,
            centroCusto : req.body.centroCusto,
            capacidade : req.body.capacidade,
            volumeTanque : req.body.volumeTanque
        }
        console.log(equipamento)
        const codigoSap = req.body.codigoSap

        const resp = await axios.put(`http://localhost:8080/maquinas/${codigoSap}`, equipamento);
        if (resp.status == 200) {
            console.log(resp.data)
            res.redirect('/equipamento');
        }




        res.render(`equipamento/editar`, { equipamento })
    }


    //metodo para chamar rota ediçao equipamento


    //metodo para editar equipamento via psot


    //rota para excluir equipamento
    static async removerEquipamento(req, res) {
        const codigoSap = req.body.id
        console.log(codigoSap)
        const resp = await axios.put(`http://localhost:8080/maquinas/desativar/${codigoSap}`);

        if (resp.status == 200) {
            console.log(resp.data)
            res.redirect('/equipamento');
        }
    }
}
