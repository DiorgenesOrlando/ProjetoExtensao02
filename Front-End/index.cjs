// Comandos para iniciar o projeto e instalar os pacotes
// npm init -y
// npm install express express-handlebars sequelize mysql2 bcryptjs connect-flash cookie-parser cookie-session express-flash express-session session-file-store nodemon

const express = require("express");
const expressHandlebars = require("express-handlebars");
const session = require("express-session");
const FileStore = require("session-file-store")(session);
const flash = require("express-flash");

//Instancia o express, handlebars e mid dos formulários
const aplicacao = express();


aplicacao.engine("handlebars", expressHandlebars.engine({
  defaultLayout: 'main',
  runtimeOptions: {
    allowProtoPropertiesByDefault: true,
    allowProtoMethodsByDefault: true,
  },
}));
aplicacao.set("view engine", "handlebars");

aplicacao.use(
  express.urlencoded({
    extended: true,
  })
  );
  
aplicacao.use(express.json());

aplicacao.use(express.static('public'))
//middleware do controle de sessão

// Importa os Models para a criação das tabelas
//const Usuario = require("./models/Usuario");
//const Motorista = require("./models/Motorista");
//const Pessoa = require("./models/Pessoa");
//const Viagem = require("./models/Viagem");
//const Veiculo = require("./models/Veiculo");
//const Viagem_Motoristas = require("./models/Viagem_Motoristas");
//const Orcamento = require("./models/Orcamento");
//const Cliente = require("./models/Cliente");

// importa a verificaSessao

//Rota inicial da aplicação



aplicacao.get('/', function (req, res) {
  res.render('home')
})


const equipamentoRotas = require("./routes/equipamentoRotas.cjs");
aplicacao.use("/equipamento", equipamentoRotas);

const abastecedorRotas = require("./routes/abastecedorRotas.cjs");
aplicacao.use("/abastecedor", abastecedorRotas);

const  consumoRotas = require("./routes/consumoRotas.cjs");
aplicacao.use("/consumo", consumoRotas);

const  reservatorioRotas = require("./routes/reservatorioRotas.cjs");
aplicacao.use("/reservatorio", reservatorioRotas);


aplicacao.listen(3000);
