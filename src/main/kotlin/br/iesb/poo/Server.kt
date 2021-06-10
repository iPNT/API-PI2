package br.iesb.poo

import br.iesb.poo.rpg.Rpg
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
import br.iesb.poo.rpg.batalha.batalhaChefe
import br.iesb.poo.rpg.loja.Itens
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

            get("/") {
                call.respondText(
                        "ROTAS\n\n" +
                                "GET Listar Jogadores: /jogadores\n" +
                                "GET Listar Monstros: /monstros\n" +
                                "POST Criar um novo Jogador: /jogadores/criarjogador\n" +
                                "POST Travar uma Batalha: /batalha/(JogadorID)/(AjudanteID)\n" +
                                "POST Comprar Itens: /loja/(JogadorID)/(ItemID)\n" +
                                "POST Visualizar seu Inventario: /inventario/(JogadorID)\n" +
                                "POST Contratar um Mercenário: /taverna/(JogadorID)\n" +
                                "PUT Enviar uma Menssagem: /taverna/chat/(JogadorID)/(Menssagem)\n" +
                                "GET Visualizar o Chat de Menssagens: /taverna/chat",
                        status = HttpStatusCode.OK
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
                val novojogador = PersonagemJogador(
                        atributos.classe,
                        atributos.nome,
                        atributos.elemento,
                        RPG
                )
                novojogador.definirStatusBase() //Conferir com a Isa dps pra ver se faz sentido

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

            get("/batalha/{idURL}") {

                val idJogador = call.parameters["idURL"]?.toInt()
                val jogador = RPG.jogadores.find { it.id == idJogador }

                if (jogador != null) {
                    if (jogador.batalhas % 10 == 0 && (1..10).random() > 5) {
                        val log: String = batalhaChefe(jogador, RPG)
                        call.respondText(log)
                    } else {
                        val log: String = batalha(jogador, RPG)
                        call.respondText(log)
                    }
                    jogador.batalhas++
                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            }

            post("/loja/{idURL}/{opcao}") {

                val idJogador = call.parameters["idURL"]?.toInt()
                val opcao = call.parameters["opcao"].toString()
                val jogador = RPG.jogadores.find { it.id == idJogador }

                if (jogador != null) {

                    val itens = Itens(opcao, "", "", "", -1, jogador)
                    val retorno = itens.buscar(opcao)

                    if (!retorno.isNullOrEmpty()) {

                        if (jogador.dinheiro >= retorno[4].toInt()) {
                            jogador.dinheiro = jogador.dinheiro - retorno[4].toInt()

                            itens.efeito(jogador, opcao)

                            if (retorno[1] != "poção") {
                                var eff = itens.buscar(opcao)[3].split(".") as ArrayList<String>
                                if (eff[0] == "atk") {
                                    var arr = arrayListOf<String>(retorno[1], "O equipamento é ${retorno[2]}", "${eff[1]} de ataque")
                                    jogador.inventario.add(arr)

                                }else{
                                    var arr = arrayListOf<String>(retorno[1], "O equipamento é ${retorno[2]}", "${eff[1]} de defesa")
                                    jogador.inventario.add(arr)
                                }

                            }

                            call.respondText(
                                    "Você comprou ${retorno[2]} pelo valor de ${retorno[4]} moedas de ouro!\n" +
                                            "Muito Obrigada! Volte sempre",
                                    status = (HttpStatusCode.OK)
                            )
                        } else {
                            call.respondText(
                                    "Você não tem moedas de ouro suficientes para comprar ${retorno[2]} e não vendemos fiado",
                                    status = (HttpStatusCode.Forbidden)
                            )

                        }

                    } else {
                        call.respondText(
                                "Infelizmente estamos com falta de estoque!\n" +
                                        "Muito Obrigada! Agradeçemos a compreensão",
                                status = (HttpStatusCode.NoContent)
                        )

                    }
                } else {
                    call.respondText(
                            "Verifique o ID, jogador não existe!!",
                            status = (HttpStatusCode.NoContent)
                    )

                }
            }

            post("/inventario/{idURL}") {

                val idJogador = call.parameters["idURL"]?.toInt()
                val jogador = RPG.jogadores.find { it.id == idJogador }


                if (jogador != null) {

                    if (jogador.inventario.isNotEmpty()) {

                        call.respond(jogador.inventario)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            }

            /*put("/taverna/chat/{idURL}/{texto}") {
                var msg = call.parameters["texto"].toString()
                val idJogador = call.parameters["idURL"]?.toInt()
                val jogador = RPG.jogadores.find { it.id == idJogador }

                //Formatação da Data
                val data = LocalDateTime.now()
                val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")
                val formatado = data.format(formato)

                if (jogador != null) {
                    msg = "<${formatado}> ${jogador.nome} diz: " + msg + "\n";
                    File("iesbRPG/src/main/kotlin/br/iesb/poo/rpg/taverna/chat.txt").appendText(msg)
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            }*/

        }
    }.start(wait = true)
}