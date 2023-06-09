const express = require("express");
const router = express.Router();
const EquipamentoController = require("../controllers/EquipamentoController.cjs");


//rotas para os metodos
router.get("/",  EquipamentoController.mostrarEquipamentos);
router.get("/criar",  EquipamentoController.criarEquipamento);
router.post("/criarPost",  EquipamentoController.criarEquipamentoPost);
router.get("/codigo/:id",  EquipamentoController.codigoEquipamento);

router.get("/editar/:id", EquipamentoController.editarEquipamento);
router.post("/editarPost",  EquipamentoController.editarEquipamentoPost);
router.post("/desativar",  EquipamentoController.removerEquipamento);


module.exports = router;
