package br.iesb.poo

import br.iesb.poo.pessoas.IdPessoa
import br.iesb.poo.rpg.Rpg
import br.iesb.poo.rpg.TipoPersonagem
import br.iesb.poo.rpg.personagem.PersonagemJogador
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import br.iesb.poo.rpg.batalha.batalha
import br.iesb.poo.rpg.loja.Itens
import br.iesb.poo.rpg.personagem.PersonagemMonstro
import java.io.File

val RPG: Rpg = Rpg()

fun main() {
    embeddedServer(Netty, System.getenv("PORT")?.toInt() ?: 8080) {
        routing {
            install(ContentNegotiation) {
                gson {
                    setPrettyPrinting()
                }
            }

            get("/pessoa"){
                //call.receive<IdPessoa>()
                val novaPessoa = IdPessoa(RPG)
                RPG.ids.add(novaPessoa)
                call.respondText(
                    "Criado com sucesso de ID: ${novaPessoa.id}",
                    status = HttpStatusCode.Created
                )
            }

            get("/jogadores") {
                if (RPG.jogadores.isNotEmpty()) {
                    call.respond(RPG.jogadores)
                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            }

            get("/monstros") {
                if (RPG.monstros.isNotEmpty()) {
                    call.respond(RPG.monstros)
                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            }

            get("/taverna/chat") {

                call.respondText(File("iesbRPG/src/main/kotlin/br/iesb/poo/rpg/taverna/chat.txt").readText())
            }

            post("/jogadores/criarjogador") {
                val atributos = call.receive<PersonagemJogador>()
                val novomonstro : PersonagemMonstro = RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_MONSTRO)
                val novojogador = PersonagemJogador(
                        atributos.classe,
                        atributos.nome,
                        atributos.elemento,
                        RPG
                )
                novojogador.definirStatusBase() //Conferir com a Isa dps pra ver se faz sentido
                novomonstro.definirMonstro(nivelMasmorra = novojogador.nivel)

                RPG.jogadores.add(novojogador)
                call.respondText(
                        "Criado com sucesso ${if (novojogador.classe == 1){
                            "Arqueiro"
                        } else if (novojogador.classe == 2){
                            "Cavaleiro"
                        } else{
                            "Mago"
                        }
                        } ${novojogador.nome} de ID: ${novojogador.id}",
                        status = HttpStatusCode.Created
                )
            }

            get("/batalha/{n}/{idURL}/{op}") {
                val bt = call.parameters["n"]?.toInt()
                val idJogador = call.parameters["idURL"]?.toInt()
                val jogador = RPG.jogadores.find { it.id == idJogador }
                var option = call.parameters["op"]?.toInt()


                if (jogador != null) {
                    if (bt==0){
                        val log: String = batalha(jogador, RPG.monstros[0], RPG, option, 0)
                        call.respondText(log)
                    }else {
                        val log: String = batalha(jogador, RPG.monstros[0], RPG, option, bt)
                        call.respondText(log)
                    }

                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            }

            get("/elemento/{idURL}/{elemento}"){
                val idJogador = call.parameters["idURL"]?.toInt()
                val jogador = RPG.jogadores.find { it.id == idJogador }
                val element = call.parameters["elemento"]?.toInt()

                if (element != null) {
                    if (jogador != null) {
                        jogador.elemento = element
                    }
                }
            }


            get("/loja/{idURL}/{opcao}") {

                val idJogador = call.parameters["idURL"]?.toInt()
                val jogador = RPG.jogadores.find { it.id == idJogador }
                val opcao = call.parameters["opcao"].toString()
                var log: String = ""
                if (jogador != null) {
                    val itens = Itens()
                    log = itens.efeito(jogador, opcao)
                 }
                call.respondText(log)
            }

        }
    }.start(wait = true)
}