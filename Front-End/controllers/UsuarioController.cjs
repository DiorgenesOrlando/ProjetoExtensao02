const bcrypt = require('bcryptjs')
const axios = require('axios')


const { Op } = require('sequelize')

module.exports = class UsuarioController {

    // --------------------------------------------------------------------------
    // FUNÇÃO PARA MOSTRAR Usuario
    // --------------------------------------------------------------------------
    static mostrarUsuarios(req, res) {

    }

    // funcao que renderiza tela de login

    static login(req, res){
        res.render('/usuario/login')

    }
    static async validarToken(req, res, next) {
        const token = req.body.token;
        console.log('Usuario controller token : ',token)
      
        try {
          const response = await axios.post(
            `http://localhost:8081/oauth/check_token?token=${token}`
          );
          console.log(response.data);
          console.log("LOGADO");
          //next();
        } catch (error) {
          console.log("Falhou !");
          res.render("./usuario/login"); // redirecionar para a página de login se a validação falhar
        }
      }
      
      
      
      

    // --------------------------------------------------------------------------
    // FUNÇÃO PARA REDIRECIONAR PARA A PÁGINA DE CRIAR Usuario
    // --------------------------------------------------------------------------
    static criarUsuario(req, res) {
        res.render('usuario/criar')
    }


    // --------------------------------------------------------------------------
    // FUNÇÃO PARA CRIAR Usuario
    // --------------------------------------------------------------------------
    static criarUsuarioPost(req, res) {
        const salt = bcrypt.genSaltSync(10)
        const hashSenha = bcrypt.hashSync(req.body.senha, salt)

        const usuario = {
            //Cria o hash
            username: req.body.username,
            senha: hashSenha, //Usa o hash para cadastrar no bd            email: req.body.email,
            nome: req.body.nome,
            sobrenome: req.body.sobrenome,
            email : req.body.email
        }

        console.log(usuario)
        Usuario.create(usuario)
            .then(() => {
                res.redirect('/usuario/')
            })
            .catch((err) => console.log(err))
    }


    // --------------------------------------------------------------------------
    // FUNÇÃO PARA REDIRECIONAR PARA A PÁGINA DE EDITAR Usuario
    // --------------------------------------------------------------------------
    static editarUsuario(req, res) {

        const id = req.params.id

        Usuario.findOne({ where: { id: id }, raw: true })
            .then((usuario) => {
                res.render('usuario/editar', { usuario })
                console.log(usuario)

            })
            .catch((err) => console.log(err))
    }


    // --------------------------------------------------------------------------
    // FUNÇÃO PARA EDITAR Usuario
    // --------------------------------------------------------------------------
    static editarUsuaroPost(req, res) {
        const id = req.body.id
        const salt = bcrypt.genSaltSync(10)
        const hashSenha = bcrypt.hashSync(req.body.senha, salt) //cria o hash

        const usuario = {
            username: req.body.username,
            senha: hashSenha,
            email: req.body.email,
            nome: req.body.nome,
            sobrenome: req.body.sobrenome

        }

        Usuario.update(usuario, { where: { id: id } })
            .then(() => {
                res.redirect('/usuario')
            })
            .catch((err) => console.log(err))
    }


    // --------------------------------------------------------------------------
    // FUNÇÃO PARA REMOVER Usuario
    // --------------------------------------------------------------------------
    static removerUsuario(req, res) {

        const id = req.body.id

        Usuario.destroy({ where: { id: id } })
            .then(() => {
                res.redirect('/usuario')
            })
            .catch((err) => console.log(err))
    }

    static async loginPost(req, res) {
        var usernameField = req.body.username;
        var passwordField = req.body.password;
      
        var username = usernameField;
        console.log(username);
      
        var password = passwordField;
        console.log(password);
      
        var grant_type = "password";
        var client_id = "web";
        var client_secret = "Ab@stec!ment0";
      
        axios.post(`http://localhost:8081/oauth/token?username=${username}&password=${password}&grant_type=${grant_type}&client_id=${client_id}&client_secret=${client_secret}`)
          .then(response => {
            var token = response.data.access_token;
            //localStorage.setItem("token", token);
          //  console.log(`Token : ${token}`)
      
            console.log("LOGADO");
            //console.log(token);
            res.render("home", {token} );
          })
          .catch(error => {
            console.log("Falhou !");
            //eq.flash('error', 'Usuário ou senha inválidos.');
            res.redirect('/login');
          });
      }
      

}
