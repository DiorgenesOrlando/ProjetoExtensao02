const express = require("express");

const verificaSessao = require("../controllers/UsuarioController.cjs").validarToken;

const rota = express.Router();

const UsuarioController = require("../controllers/UsuarioController.cjs");
// importa a verificaSessao
//const verificaSessao = require("../models/sessao").verificaSessao;
rota.get("/", verificaSessao,UsuarioController.mostrarUsuarios);
//rota.get("/criar",verificaSessao, UsuarioController.criarUsuario);
rota.post("/criarPost", UsuarioController.criarUsuarioPost);
rota.get("/editar/:id", UsuarioController.editarUsuario);

//rota.post("/editarPost",verificaSessao, UsuarioController.editarUsuarioPost);

rota.post("/remover", UsuarioController.removerUsuario);

rota.get("/", UsuarioController.mostrarUsuarios);
rota.get("/login", UsuarioController.login);

rota.get("/criar", UsuarioController.criarUsuario);
rota.post("/criarPost", UsuarioController.criarUsuarioPost);
rota.get("/editar/:id", UsuarioController.editarUsuario);
rota.post("/editarPost", UsuarioController.editarUsuaroPost);
rota.post("/remover", UsuarioController.removerUsuario);
rota.post("/loginPost", UsuarioController.loginPost);



module.exports = rota;
