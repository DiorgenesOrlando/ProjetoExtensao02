//const Consumo = require('../models/Consumo')

//import axios from 'axios';
const axios = require('axios')
const moment = require('moment')
const fs = require('fs')

// Importando a biblioteca Moment.js


const { Op, json } = require('sequelize')

module.exports = class ConsumoController {

    //metodo para listar consumos

    //metodo para chamar roda de listagem de consumo


    static async mostrarConsumos(req, res) {
        const resp = await axios.get('http://localhost:8080/saida-combustivel/listar');

        let consumos = resp.data;
        //console.log(consumos)
        res.render('consumo/listar', { consumos: consumos });
    }
    static async mostrarConsumoMaquinas(req, res) {
       // const resp = await axios.get('http://localhost:8080/saida-combustivel/listar');

       // let consumos = resp.data;
        //console.log(consumos)
        res.render('consumo/listarpormaquina');
    }
    
    static async criarConsumoMaquinasPost(req, res) {
        const dataInicio = req.body.dataInicio;
        const valorAgregado = req.body.checks;
        const dataFim = req.body.dataFim;
      
        let objetoRetorno;
      
        console.log(`valorAgregado :`,valorAgregado);
      
      if(valorAgregado == "true"){
          
          const resp = await axios.get(`http://localhost:8080/saida-combustivel/filtrar-consumo-maquina-agregado?dataInicio=${dataInicio}&dataFim=${dataFim}`);
          
          objetoRetorno = resp.data;
        }else{
            const resp = await axios.get(`http://localhost:8080/saida-combustivel/filtrar-consumo-maquina?dataInicio=${dataInicio}&dataFim=${dataFim}`);
          
            objetoRetorno = resp.data;
        }
        
       // console.log(objetoRetorno)
        // criar uma nova propriedade "dataHoraSaidaFormatada" com a data formatada em "objetoRetorno"
        objetoRetorno = objetoRetorno.map(item => {
          return {
            ...item,
            dataHoraSaidaFormatada: new Date(item.dataHoraSaida).toLocaleString('pt-BR')
          };
        });
      
        console.log({ objetoRetorno,valorAgregado, dataInicio, dataFim });
      
        res.render('consumo/listarpormaquina', { objetoRetorno,valorAgregado, dataInicio, dataFim });
      }
      

    //metodo para pegar dados do formulario e inserir consumo no banco

    static criarConsumo(req, res) {
        res.render('consumo/criar')
    }

    static async criarConsumoPost(req, res) {
        const dataInicio = req.body.dataInicio;
        const combustivel = req.body.valorCombustivel;
        const dataFim = req.body.dataFim;
      
        let objetoRetorno;
      
        console.log(combustivel);
      
        if (combustivel === 'GLP') {
          console.log('combustivel = GLP');
      
          const resp = await axios.get(`http://localhost:8080/saida-combustivel/filtrar-consumo-glp?dataInicio=${dataInicio}&dataFim=${dataFim}`);
          objetoRetorno = resp.data;
        } else {
          console.log('combustivel = DIESEL');
      
          const resp = await axios.get(`http://localhost:8080/saida-combustivel/filtrar-consumo-diesel?dataInicio=${dataInicio}&dataFim=${dataFim}`);
          objetoRetorno = resp.data;
        }
      
        // criar uma nova propriedade "dataHoraSaidaFormatada" com a data formatada em "objetoRetorno"
        objetoRetorno = objetoRetorno.map(item => {
          return {
            ...item,
            dataHoraSaidaFormatada: new Date(item.dataHoraSaida).toLocaleString('pt-BR')
          };
        });
      
        console.log({ objetoRetorno, combustivel, dataInicio, dataFim });
      
        res.render('consumo/listar', { objetoRetorno, combustivel, dataInicio, dataFim });
      }
      


    static async editarConsumo(req, res) {
        const id = req.params.id;
        console.log('ID : ', id)
        const resp = await axios.get(`http://localhost:8080/saida-combustivel/${id}`);
        let consumo = resp.data;
        console.log(consumo)
        res.render('consumo/editar', { consumo })
    }

    static async editarConsumoPost(req, res) {

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
        const origem = {
            id : req.body.id

        }
        const destino = {
            codigoSap : req.body.codigoSap

        }
        const abastecedor = {
            id : 1
        }
        let consumo = {
            id : req.body.consumoId,
            origem,
            destino,
           // nome: req.body.nome,
           abastecedor,
           quantidade: req.body.quantidade,
           combustivel: combustivel,
            dataHoraSaida: req.body.dataHoraSaida,
          
        }
        console.log(consumo)
       // const codigoSap = req.body.codigoSap

        let resp = await axios.put(`http://localhost:8080/saida-combustivel/editar`, consumo);
        if (resp.status == 200) {
            console.log(resp.data)
            
            const id = req.body.consumoId;
            console.log('ID : ', id)
             resp = await axios.get(`http://localhost:8080/saida-combustivel/${id}`);
             consumo = resp.data;
            console.log(consumo)
            res.render('consumo/editar', { consumo })
        }


    }

    //rota para excluir consumo
    static async removerConsumo(req, res) {
        const id = req.body.id
        const dataInicio = req.body.ini;
        console.log(req.body)
        const dataFim = req.body.fim;
        console.log(id)
        let resp = await axios.delete(`http://localhost:8080/saida-combustivel/${id}`);

        if (resp.status == 204) {
            console.log(resp.data)
            resp = await axios.get(`http://localhost:8080/saida-combustivel/filtrar-consumo-maquina?dataInicio=${dataInicio}&dataFim=${dataFim}`);
          
           let objetoRetorno = resp.data;

            res.render('consumo/listarpormaquina',{objetoRetorno,dataInicio, dataFim});
        }
    }
    static async consumoDownload(req, res) {
        try {
            //const id = req.params.id;
            //console.log('ID : ', id);
            const dataInicio = req.body.valorIni;
            console.log(dataInicio)
            const dataFim = req.body.valorFim;

            const filePath = `public/sap/consumoDiesel.txt`;
           
    
            const url = `http://localhost:8080/saida-combustivel/filtrar-consumo-diesel/download?dataInicio=${dataInicio}&dataFim=${dataFim}`;
            const response = await axios.get(url, { responseType: 'stream' });
    
            const file = fs.createWriteStream(filePath);
            file.on('finish', () => {
                console.log("txt criado")
                res.download(filePath);
            });
    
            response.data.pipe(file);
    
        } catch (error) {
            console.error(error);
            res.status(500).send('Erro ao processar solicitação');
        }
    }
    static async consumoAgregadoDownload(req, res) {
        try {
            //const id = req.params.id;
            //console.log('ID : ', id);
            const dataInicio = req.body.valorIni;
            console.log(dataInicio)
            const dataFim = req.body.valorFim;

            const filePath = `public/sap/consumo.xls`;
           
    
            const url = `http://localhost:8080/saida-combustivel/filtrar-consumo-agregado/download?dataInicio=${dataInicio}&dataFim=${dataFim}`;
            const response = await axios.get(url, { responseType: 'stream' });
    
            const file = fs.createWriteStream(filePath);
            file.on('finish', () => {
                console.log("txt criado")
                res.download(filePath);
            });
    
            response.data.pipe(file);
    
        } catch (error) {
            console.error(error);
            res.status(500).send('Erro ao processar solicitação');
        }
    }
    static async consumoMaquinaDownload(req, res) {
        try {
            //const id = req.params.id;
            //console.log('ID : ', id);
            const dataInicio = req.body.valorIni;
            console.log(dataInicio)
            const dataFim = req.body.valorFim;

            const filePath = `public/sap/consumo.xls`;
           
    
            const url = `http://localhost:8080/saida-combustivel/filtrar-consumo-maquina/download?dataInicio=${dataInicio}&dataFim=${dataFim}`;
            const response = await axios.get(url, { responseType: 'stream' });
    
            const file = fs.createWriteStream(filePath);
            file.on('finish', () => {
                console.log("txt criado")
                res.download(filePath);
            });
    
            response.data.pipe(file);
    
        } catch (error) {
            console.error(error);
            res.status(500).send('Erro ao processar solicitação');
        }
    }
}
