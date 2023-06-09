const express = require("express");
const router = express.Router();
const ReservatorioController = require("../controllers/ReservatorioController.cjs");


//rotas para os metodos
router.get("/",  ReservatorioController.mostrarReservatorios);
router.get("/criar",  ReservatorioController.criarReservatorio);
router.post("/criarPost",  ReservatorioController.criarReservatorioPost);
router.get("/editar/:id", ReservatorioController.editarReservatorio);
router.post("/editarPost",  ReservatorioController.editarReservatorioPost);
router.post("/desativar",  ReservatorioController.removerReservatorio);


module.exports = router;
