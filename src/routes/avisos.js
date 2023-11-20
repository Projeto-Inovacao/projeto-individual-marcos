var express = require("express");
var router = express.Router();

var avisoController = require("../controllers/avisoController");

router.get("/listar", function (req, res) {
    avisoController.listar(req, res);
});

router.get("/listar/:fkcolaborador", function (req, res) {
    avisoController.listarPorUsuario(req, res);
});

router.get("/pesquisar/:descricao", function (req, res) {
    avisoController.pesquisarDescricao(req, res);
});

router.post("/publicar/:id_colaborador", function (req, res) {
    avisoController.publicar(req, res);
});

router.put("/editar/:id_chat", function (req, res) {
    avisoController.editar(req, res);
});

router.delete("/deletar/:id_chat", function (req, res) {
    avisoController.deletar(req, res);
});

module.exports = router;