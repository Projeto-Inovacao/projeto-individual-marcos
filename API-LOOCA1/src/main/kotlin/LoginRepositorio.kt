import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate

@Suppress("UNREACHABLE_CODE")
class LoginRepositorio {

    // objeto JdbcTemplate usado para interagir com o banco de dados
//    lateinit var jdbcTemplate: JdbcTemplate
    var jdbcTemplate_server = Conexao.jdbcTemplate_server!!

    // método para iniciar o repositório, geralmente chamado no início para configurar a conexão com o banco de dados
//    fun iniciar() {
//        jdbcTemplate = Conexao.jdbcTemplate!!
//    }

    fun iniciar_server() {
        jdbcTemplate_server = Conexao.jdbcTemplate_server!!
    }

    fun validarLogin(login: Usuario): Boolean {
        try {
            val usuario = jdbcTemplate_server.queryForObject(
                """
            select nome, email, senha, fk_empresa from colaborador where (email = '${login.email}' and senha = '${login.senha}')
            """, BeanPropertyRowMapper(Usuario::class.java)
            )
            return true
        } catch (e: EmptyResultDataAccessException) {
            println("Seu login não está no banco...")
            return false // Ou outro valor padrão que faça sentido no seu contexto
        }
    }

    fun comprimentar(login: Usuario): String {
        val usuario = jdbcTemplate_server.queryForObject(
            """
            select nome from colaborador where (email = '${login.email}' and senha = '${login.senha}')
            """, BeanPropertyRowMapper(Usuario::class.java)
        )
        val mensagem = """
            Boas-vindas, ${usuario.nome}, seu login foi validado com sucesso!
            Agora você irá prosseguir para etapa de monitoramento. 
        """.trimIndent()

        return mensagem
    }

    fun verificarEmpresa(login: Usuario): Int {
        val usuario = jdbcTemplate_server.queryForObject(
            """
            select fk_empresa from colaborador where (email = '${login.email}' and senha = '${login.senha}')  
            """, BeanPropertyRowMapper(Usuario::class.java)
        )
        val fk_empresa = usuario.fk_empresa

        return fk_empresa
    }

    fun mostrarMaquina(fk_empresa: Int): String {

        val listaMaquinas: List<Maquina> = jdbcTemplate_server.query(
            "select * from maquina where fk_empresaM = $fk_empresa",
            BeanPropertyRowMapper(Maquina::class.java)
        )

        val maquinas = listaMaquinas.map {
            "Maquina ${it.id_maquina} - ${it.hostname}"
        }.joinToString("\n")
        return maquinas
    }

}