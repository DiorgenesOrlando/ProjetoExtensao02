const express = require("express");
const router = express.Router();
const AbastecedorController = require("../controllers/AbastecedorController.cjs");


//rotas para os metodos
router.get("/",  AbastecedorController.mostrarAbastecedores);
router.get("/criar",  AbastecedorController.criarAbastecedor);
router.post("/criarPost",  AbastecedorController.criarAbastecedorPost);
router.get("/editar/:id", AbastecedorController.editarAbastecedor);
router.post("/editarPost",  AbastecedorController.editarAbastecedorPost);
router.post("/desativar",  AbastecedorController.removerAbastecedor);


module.exports = router;
