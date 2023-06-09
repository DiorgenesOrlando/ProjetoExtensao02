const express = require("express");
const router = express.Router();
const ConsumoController = require("../controllers/ConsumoController.cjs");


//rotas para os metodos
router.get("/",  ConsumoController.mostrarConsumos);
router.get("/criar",  ConsumoController.criarConsumo);
router.post("/criarPost",  ConsumoController.criarConsumoPost);
router.get("/editar/:id", ConsumoController.editarConsumo);
router.post("/editarPost",  ConsumoController.editarConsumoPost);
router.post("/excluir",  ConsumoController.removerConsumo);
router.post("/consumoDownload",  ConsumoController.consumoDownload);
router.get("/maquinas",  ConsumoController.mostrarConsumoMaquinas);
router.post("/maquinas/criarPost",  ConsumoController.criarConsumoMaquinasPost);
router.post("/consumoAgregadoDownload",  ConsumoController.consumoAgregadoDownload);
router.post("/consumoMaquinaDownload",  ConsumoController.consumoMaquinaDownload);






module.exports = router;
