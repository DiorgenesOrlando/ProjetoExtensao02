//const Abastecedor = require('../models/Abastecedor')

//import axios from 'axios';
const axios = require('axios')

const { Op, json } = require('sequelize')

module.exports = class AbastecedorController {

    //metodo para listar abastecedors

    //metodo para chamar roda de listagem de abastecedor


    static async mostrarAbastecedores(req, res) {
        const resp = await axios.get('http://localhost:8080/abastecedores/ativos');

        let abastecedores = resp.data;
        //console.log(abastecedors)
        res.render('abastecedor/listar', { abastecedores: abastecedores });
    }

    //metodo para pegar dados do formulario e inserir abastecedor no banco

    static criarAbastecedor(req, res) {
        res.render('abastecedor/criar')
    }


    static async criarAbastecedorPost(req, res) {      
        const abastecedor = {
            nome: req.body.nome,
            matricula: req.body.matricula
        }
        console.log(abastecedor)

        const resp = await axios.post(`http://localhost:8080/abastecedores`, abastecedor);


        res.redirect('/abastecedor');

    }


    static async editarAbastecedor(req, res) {
        const id = req.params.id;
        console.log('ID : ', id)
        const resp = await axios.get(`http://localhost:8080/abastecedores/${id}`);
        let abastecedor = resp.data;
        console.log(abastecedor)
        res.render('abastecedor/editar', {abastecedor})
    }

    static async editarAbastecedorPost(req, res) {
        const id = req.body.id;
        const abastecedor = {
            id: id,
            nome: req.body.nome,
            matricula: req.body.matricula
        }
        console.log(abastecedor)

        const resp = await axios.put(`http://localhost:8080/abastecedores/${id}`, abastecedor);


        res.redirect('/abastecedor');
    }


    //metodo para chamar rota ediçao abastecedor


    //metodo para editar abastecedor via psot


    //rota para excluir abastecedor
    static async removerAbastecedor(req, res) {
        const id = req.body.id
        console.log(id)
        const resp = await axios.put(`http://localhost:8080/abastecedores/desativar/${id}`);

        if (resp.status == 200) {
            console.log(resp.data)
            res.redirect('/abastecedor');
        }
    }
}
