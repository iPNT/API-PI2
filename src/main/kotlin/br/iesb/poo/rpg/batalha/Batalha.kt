package br.iesb.poo.rpg.batalha

import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.Rpg
import br.iesb.poo.rpg.TipoPersonagem
import br.iesb.poo.rpg.personagem.PersonagemMonstro


fun batalha(jogador: PersonagemJogador,monstro: PersonagemMonstro, RPG: Rpg, opt: Int?): String {

    val racaMonstro = arrayOf("Orc", "Goblin", "Gnomio")
    var log =
        "--BATALHA DE NÚMERO ${jogador.batalhas}--\n" +
                "--LOG DA BATALHA ENTRE ${jogador.nome} e ${racaMonstro[monstro.raca]} ${monstro.nome} DE NÍVEL ${monstro.nivel}--\n\n"

    // INÍCIO CÁLCULO DOS ATRIBUTOS

    var danoJ: Int = 0
    var danoM: Int = 0


    var danoUpgradeJ: Int = 0
    var danoUpgradeM: Int = 0

    var opcaoM: Int = 0
    var elementoAtk: Int = 0

    // FIM CÁLCULOS DE ATRIBUTOS
    // INÍCIO COMBATE

    var turno = 1

    if (jogador.velocidade > monstro.velocidade) {
        log += "[ * ] JOGADOR INICIOU O COMBATE\n\n"

        //Para batalha, a gente vai fazer um switch case para as opções que o jogador pode fazer
        // Nesse caso, vai ter 4 opções: Atacar, Magia, Inventário, Fugir
        // invés de conferir os pontos de vida pelo while, podemos fazer um if que faz esse conferencia, e caso foi verdadeiro, a gente muda a váriavel do while para false, assim ele para a batalha
        // então, vai ser: while (batalhaRolando = true) ai no fim da batalha, batalhaRolando = false, saindo do while

        jogador.defesa = jogador.maxDefesa
        monstro.defesa = monstro.maxDefesa


        //TURNO JOGADOR
        when (opt) {

            1 -> danoJ = jogador.atacarPersonagem(jogador.maxAtaque, monstro.defesa) //Ataque Basico
            2 -> jogador.defesa = jogador.defender(jogador.defesa) //opcao pro personagem defender
            3 -> if (jogador.pontosMana <= 0) {
                log += "\n--MANA INSUFICIENTE--\n"
                jogador.pontosMana = 0
            } else {
                if (jogador.elemento == 6) {
                    jogador.calcularCura(jogador.nivel, jogador.maxMana)
                } else {
                    danoJ = jogador.calcularDano(
                        jogador.nivel,
                        jogador.maxMana,
                        monstro.maxDefesa,
                        jogador.elemento,
                        jogador.elemento,
                        monstro.elemento
                    )
                }
            }

            4 -> if ((1..10).random() > 8) {
                log += "\n--VOCE NAO VAI CONSEGUI FUGIR SE SUAS PERNAS ESTIVEREM TREMENDO--\n"

            } else if (jogador.velocidade > monstro.velocidade) {
                RPG.monstros.removeAt(0)
                val novomonstro: PersonagemMonstro =
                    RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_MONSTRO)
                novomonstro.definirMonstro(jogador.nivel)

                jogador.pontosVida = jogador.maxVida
                jogador.batalhas++

                log += "\n--FIM DO COMBATE JOGADOR METEU O PÉ--\n"
                return log
            }


        }

        if (opt != 4) {
            monstro.pontosVida = monstro.pontosVida - danoJ
            log += "TURNO ${turno}: JOGADOR ATACOU COM $danoJ MONSTRO FICOU COM ${monstro.pontosVida} DE VIDA\n"
        }

        if (monstro.pontosVida <= 0) {
            log += "\n[ = ] JOGADOR GANHOU\n"
            log += monstro.derrota(RPG)
            log += jogador.vitoria(monstro)
            log += "\n--FIM DO COMBATE--\n"
        } else {
            opcaoM = (1..3).random()
            elementoAtk = (1..5).random()

            when (opcaoM) {

                1 -> danoM = monstro.atacarPersonagem(monstro.maxAtaque, jogador.defesa) //Ataque Basico
                2 -> monstro.defesa = monstro.defender(monstro.defesa) //opcao pro personagem defender
                3 -> danoM = monstro.calcularDano(
                    monstro.nivel,
                    monstro.maxMana,
                    jogador.maxDefesa,
                    elementoAtk,
                    monstro.elemento,
                    jogador.elemento
                )
            }

            jogador.pontosVida = jogador.pontosVida - danoM


            log += "TURNO ${turno}: MONSTRO ATACOU COM $danoM JOGADOR FICOU COM ${jogador.pontosVida} DE VIDA\n"

            turno++

            if (jogador.pontosVida <= 0) {
                log += "\n[ = ] JOGADOR PERDEU\n"
                log += jogador.derrota(RPG)

                log += "\n--FIM DO COMBATE--\n"
            }

        }


    } else {
        log += "[ * ] EMBOSCADA! MONSTRO INICIOU O COMBATE\n\n"


        jogador.defesa = jogador.maxDefesa
        monstro.defesa = monstro.maxDefesa

        //TURNO MONSTRO

        opcaoM = (1..3).random()
        elementoAtk = (1..5).random()

        when (opcaoM) {

            1 -> danoM = monstro.atacarPersonagem(monstro.maxAtaque, jogador.defesa) //Ataque Basico
            2 -> monstro.defesa = monstro.defender(monstro.defesa) //opcao pro personagem defender
            3 -> danoM = monstro.calcularDano(
                monstro.nivel,
                monstro.maxMana,
                jogador.maxDefesa,
                elementoAtk,
                monstro.elemento,
                jogador.elemento
            )
        }

        jogador.pontosVida = jogador.pontosVida - danoM


        log += "TURNO ${turno}: MONSTRO ATACOU COM $danoM JOGADOR FICOU COM ${jogador.pontosVida} DE VIDA\n"

        if (jogador.pontosVida <= 0) {
            log += "\n[ = ] JOGADOR PERDEU\n"
            log += jogador.derrota(RPG)
            log += "\n--FIM DO COMBATE--\n"
        } else {

            //TURNO JOGADOR
            when (opt) {

                1 -> danoJ = jogador.atacarPersonagem(jogador.maxAtaque, monstro.defesa) //Ataque Basico
                2 -> jogador.defesa = jogador.defender(jogador.defesa) //opcao pro personagem defender
                3 -> if (jogador.pontosMana <= 0) {
                    log += "\n--MANA INSUFICIENTE--\n"
                    jogador.pontosMana = 0
                } else {
                    if (jogador.elemento == 6) {
                        jogador.calcularCura(jogador.nivel, jogador.maxMana)
                    } else {
                        danoJ = jogador.calcularDano(
                            jogador.nivel,
                            jogador.maxMana,
                            monstro.maxDefesa,
                            jogador.elemento,
                            jogador.elemento,
                            monstro.elemento
                        )
                    }
                }
                4 -> log += "\n--VOCE NAO VAI CONSEGUI FUGIR SE SUAS PERNAS ESTIVEREM TREMENDO--\n"

            }


            if (opt != 4) {
                monstro.pontosVida = monstro.pontosVida - danoJ
                log += "TURNO ${turno}: JOGADOR ATACOU COM $danoJ MONSTRO FICOU COM ${monstro.pontosVida} DE VIDA\n"
            }

            turno++

            if (monstro.pontosVida <= 0) {
                log += "\n[ = ] JOGADOR GANHOU\n"
                log += monstro.derrota(RPG)
                log += jogador.vitoria(monstro)

                log += "\n--FIM DO COMBATE--\n"
            }


        }


    }

    if (monstro.pontosVida <= 0 || jogador.pontosVida <= 0) {
        RPG.monstros.removeAt(0)
        val novomonstro: PersonagemMonstro = RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_MONSTRO)
        novomonstro.definirMonstro(jogador.nivel)

        jogador.pontosVida = jogador.maxVida
        jogador.batalhas++
    }
    return log
}
/*
fun batalhaChefe(jogador: PersonagemJogador, RPG: Rpg): String {

    val chefe: PersonagemMonstro =
        RPG.criarMonstro(tipoPersonagem = TipoPersonagem.PERSONAGEM_CHEFE)

    var batalhaRolando = true


    var log =
        "--BATALHA DE NÚMERO ${jogador.batalhas}--\n" +
        "--LOG DA BATALHA ENTRE ${jogador.nome} E CHEFE ${chefe.nome}--\n\n"

//    if (jogador.durabilidadeataque == 0) {
//        jogador.removerItem(jogador)
//    } else if (jogador.durabilidadeataque != 0) {
//        jogador.durabilidadeataque--
//    }
//
//    if (jogador.durabilidadedefesa == 0) {
//        jogador.removerItem(jogador)
//    } else if (jogador.durabilidadedefesa != 0) {
//        jogador.durabilidadedefesa--
//    }

    var danoJ: Int = 0
    var danoM: Int = 0
    var curaJ: Int = 0
    var curaM: Int = 0

    var danoUpgradeJ: Int = 0
    var danoUpgradeM: Int = 0
    var curaUpgradeJ: Int = 0
    var curaUpgradeM: Int = 0
    var reducaoMana: Int = 0

    var opcaoJ: Int = 0
    var opcaoM: Int = 0

    jogador.pontosVida = jogador.maxVida
    jogador.pontosMana = jogador.maxMana
    jogador.ataque = jogador.maxAtaque
    jogador.defesa = jogador.maxDefesa
    jogador.velocidade = jogador.maxVelocidade

    chefe.pontosVida = chefe.maxVida
    chefe.pontosMana = chefe.maxMana
    chefe.ataque = chefe.maxAtaque
    chefe.defesa = chefe.maxDefesa
    chefe.velocidade = chefe.maxVelocidade

*/
/*
    log += "[ f ] CHEFE FINAL - ATAQUE $ataqueM /// DEFESA ${defesaM}\n"
    log += "[ f ] JOGADOR FINAL - ATAQUE $ataqueJ /// DEFESA ${defesaJ}\n\n"*//*



    var turno = 1


    if (jogador.velocidade > chefe.velocidade) {

        log += "[ * ] JOGADOR INICIOU O COMBATE\n\n"

        while (batalhaRolando) {

            jogador.defesa = jogador.maxDefesa
            chefe.defesa = chefe.maxDefesa


            //TURNO JOGADOR
            when (opcaoJ) {

                1 -> danoJ = jogador.atacarPersonagem(jogador.maxAtaque, jogador.nivel, chefe.defesa) //Ataque Basico
                2 -> jogador.defesa = jogador.defender(jogador.defesa) //opcao pro personagem defender
                3 -> batalhaRolando = jogador.fugirPersonagem(jogador.velocidade, chefe.velocidade) //opcao pro jogador fugir da batalha
                4 -> danoJ = jogador.usarMagia(jogador.nivel, danoUpgradeJ, jogador.maxAtaque, chefe.maxDefesa, jogador.elemento, chefe.elemento)
                5 -> curaJ = jogador.calcularCura(jogador.nivel, 10 + curaUpgradeJ, jogador.maxMana, jogador.elemento)

            }

//            if(opcaoJ == 4){
//
//                reducaoMana = jogador.reduzMana(custoMagia)
//
//                jogador.pontosMana -= reducaoMana
//            }
//            else if (opcaoJ == 5){
//
//                reducaoMana = jogador.reduzMana(custoMagia)
//
//                jogador.pontosMana -= reducaoMana
//            }

            jogador.pontosVida += curaJ
            chefe.pontosVida -= danoJ

            log += "TURNO ${turno}: JOGADOR ATACOU COM $danoJ MONSTRO FICOU COM ${chefe.pontosVida} DE DEFESA\n"

            if (chefe.pontosVida <= 0) {
                log += "\n[ = ] JOGADOR GANHOU\n"
                log += chefe.derrota(RPG)
                log += jogador.vitoria(chefe)
                batalhaRolando = false
            }

            //TURNO MONSTRO

            opcaoM = (1..3).random()

            when(opcaoM) {

                1 -> danoM = chefe.atacarPersonagem(chefe.maxAtaque, chefe.nivel, jogador.defesa) //Ataque Basico
                2 -> chefe.defesa = chefe.defender(chefe.defesa) //opcao pro personagem defender
                3 -> danoM = chefe.usarMagia(chefe.nivel, danoUpgradeM, chefe.maxAtaque, jogador.maxDefesa, chefe.elemento, jogador.elemento)
                4 -> curaM = chefe.calcularCura(chefe.nivel, 10, chefe.maxMana, chefe.elemento)
            }

            chefe.pontosVida += curaM
            jogador.pontosVida -= danoM


            log += "TURNO ${turno}: MONSTRO ATACOU COM $danoM JOGADOR FICOU COM ${jogador.pontosVida} DE DEFESA\n"

            turno++

            if (jogador.pontosVida <= 0) {
                log += "\n[ = ] JOGADOR PERDEU\n"
                log += jogador.derrota(RPG)
                batalhaRolando = false
            }
        }
    } else {
        log += "[ * ] EMBOSCADA! MONSTRO INICIOU O COMBATE\n\n"

        while (batalhaRolando) {

            //TURNO MONSTRO

            opcaoM = (1..3).random()

            when(opcaoM) {

                1 -> danoM = chefe.atacarPersonagem(chefe.maxAtaque, chefe.nivel, jogador.defesa) //Ataque Basico
                2 -> chefe.defesa = chefe.defender(chefe.defesa) //opcao pro personagem defender
                3 -> danoM = chefe.usarMagia(chefe.nivel, danoUpgradeM, chefe.maxAtaque, jogador.maxDefesa, chefe.elemento, jogador.elemento)
                4 -> curaM = chefe.calcularCura(chefe.nivel, 10, chefe.maxMana, chefe.elemento)
            }

//            if(opcaoM == 3){
//
//                reducaoMana = monstro.reduzMana(custoMagia)
//
//                monstro.pontosMana -= reducaoMana
//            }
//            else if (opcaoM == 4){
//
//                reducaoMana = monstro.reduzMana(custoMagia)
//
//                monstro.pontosMana -= reducaoMana
//            }

            chefe.pontosVida += curaM
            jogador.pontosVida -= danoM


            log += "TURNO ${turno}: MONSTRO ATACOU COM $danoM JOGADOR FICOU COM ${jogador.pontosVida}\n"

            if (jogador.pontosVida <= 0) {
                log += "\n[ = ] JOGADOR PERDEU\n"
                log += jogador.derrotaChefe(RPG)
                break
            }

            //TURNO JOGADOR
            when (opcaoJ) {

                1 -> danoJ = jogador.atacarPersonagem(jogador.maxAtaque, jogador.nivel, chefe.defesa) //Ataque Basico
                2 -> jogador.defesa = jogador.defender(jogador.defesa) //opcao pro personagem defender
                3 -> batalhaRolando = jogador.fugirPersonagem(jogador.velocidade, chefe.velocidade) //opcao pro jogador fugir da batalha
                4 -> danoJ = jogador.usarMagia(jogador.nivel, danoUpgradeJ, jogador.maxAtaque, chefe.maxDefesa, jogador.elemento, chefe.elemento)
                5 -> curaJ = jogador.calcularCura(jogador.nivel, 10 + curaUpgradeJ, jogador.maxMana, jogador.elemento)

            }

            jogador.pontosVida += curaJ
            chefe.pontosVida -= danoJ

            
            log += "TURNO ${turno}: JOGADOR ATACOU COM $danoJ MONSTRO FICOU COM ${chefe.pontosVida}\n"

            turno++

            if (chefe.pontosVida <= 0) {
                log += "\n[ = ] JOGADOR GANHOU\n"
                log += chefe.derrota(RPG)
                log += jogador.vitoria(chefe)
                break
            }
        }

        log += "\n--FIM DO COMBATE--\n"
    }

    return log
}
*/



