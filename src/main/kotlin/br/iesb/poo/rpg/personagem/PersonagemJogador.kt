package br.iesb.poo.rpg.personagem

import br.iesb.poo.rpg.Rpg
import kotlin.collections.ArrayList

class PersonagemJogador(
    classeJogador: Int,
    nomeJogador: String,
    elementoJogador: Int,
    rpgAtual: Rpg
) : Personagem(nomeJogador, elementoJogador) {

    //Arqueiro = 1; Cavaleiro = 2; Mago = 3(Não foi implementado ainda)
    //Arqueiro + Velocidade - maxVida; Cavaleiro + Ataque - maxMana; Mago + maxMana - Defesa

    var classe: Int = classeJogador
    var vida: Int = 5
    private var xp: Int = 0
    var inventario = mutableListOf<ArrayList<String>>()
    var batalhas : Int = 1
    var durabilidadeataque : Int = 0
    var durabilidadedefesa : Int = 0
    var ataqueitem: Int = 0
    var defesaitem: Int = 0

//    val inventario = arrayOf<Array<String>>() //[Item,Quantidade]

//    val inventario = mutableListOf<String>()


    //var inventario = [[PotP, X], [PotM, y]...]
    //equipados = [arma,armadura]

    init {
        id = genId(rpgAtual)

    }

    override fun genId(rpgAtual: Rpg): Int {

        var novaId = (0..10000).random()
        while (rpgAtual.jogadores.find { it.id == novaId } != null) { //TODO EVITAR LOOP INFINITO(CONTADOR?)
            novaId = (0..10000).random()
        }
        return novaId
    }

    private fun morrerJogador(rpg: Rpg): String {

        rpg.jogadores.remove(rpg.jogadores.find { it.id == this.id })
        return "\n[ ✝ ] VOCÊ MORREU, SEU PERSONAGEM FOI DELETADO\n"
    }

    override fun derrota(rpg: Rpg): String {

        this.vida--
        this.dinheiro = this.dinheiro / 2

        var log = "\n[ :c ] VOCE TEM ${this.vida} VIDAS RESTANTES\n"

        log += if (this.vida <= 0) {

            this.morrerJogador(rpg)

        } else {

            "[ :0 ] VOCÊ FOI OBLITERADO E RESTARAM ${this.dinheiro} MOEDAS DE OURO\n"

        }
        return log
    }

    fun derrotaChefe(rpg: Rpg): String {

        this.vida = this.vida - 2
        this.dinheiro = this.dinheiro / 10

        var log = "\n[ :c ] VOCE TEM ${this.vida} VIDAS RESTANTES\n"

        log += if (this.vida <= 0) {

            this.morrerJogador(rpg)

        } else {

            "[ :0 ] VOCÊ FOI OBLITERADO E RESTARAM ${this.dinheiro} MOEDAS DE OURO\n"

        }
        return log
    }

    fun vitoria(monstro: PersonagemMonstro): String { //CALCULAR RECOMPENSAS DE VITÓRIA NO COMBATE E ATUALIZAR NÍVEL

        this.dinheiro += monstro.dinheiro
        val xpganho = monstro.nivel * 100
        this.xp += xpganho

        var log =
            "\n[ $ ] AGORA VOCÊ ESTÁ COM ${this.dinheiro} MOEDAS DE OURO E GANHOU $xpganho XP NO NÍVEL ${this.nivel}\n"

        var xpProxNv = 0
        var i = 0

        // LOOP PARA CALCULAR XP NECESSÁRIO PARA O PRÓXIMO NÍVEL
        do {
            i++
            xpProxNv += i * 100
        } while (i in 1 until this.nivel)

        // LOOP PARA ATUALIZAR NÍVEL DO PERSONAGEM CASO GANHE XP SUFICIENTE PARA MAIS DE UMA EVOLUÇÃO
        while (this.xp >= xpProxNv) {
            this.nivel++
            log += this.nivelUp()
            xpProxNv += this.nivel * 100
        }

        log += "\n[ ➽ ] XP ATUAL: ${this.xp}\n"
        log += "[ ➽ ] XP NECESSÁRIO PARA O PRÓXIMO NÍVEL: ${xpProxNv}\n"

        if ((1..10).random() + this.sorte >= 9) {
                this.vida++
                log += "\n[ ♥ ] VOCÊ ENCONTROU UMA POÇÃO DE VIDA NOS ESPÓLIOS, AGORA SUA VIDA É ${this.vida}\n"
        }

        return log
    }

    private fun nivelUp(): String { //Será utilizada a mesma fórmula usada para calcular os status dos monstros: ((2 * statusBase) * nivel)/100 + nivel + 10
                                    //obs: caso o personagem tenha alguma vantagem com um atributo, adicionar + 2, caso tenha desvantagem com um atributo, diminuir - 2

        if (classe == 1) {

            this.maxVida += (1..3).random()
            this.maxMana += (1..4).random()
            this.maxAtaque += (1..4).random()
            this.maxDefesa += (1..4).random()
            this.maxVelocidade += (2..5).random()

        } else {

            this.maxVida += (1..4).random()
            this.maxMana += (1..3).random()
            this.maxAtaque += (2..5).random()
            this.maxDefesa += (1..4).random()
            this.maxVelocidade += (1..4).random()

        }

        var log = "\n[ ↑ ] VOCÊ UPOU E AGORA ESTÁ NO NÍVEL ${this.nivel}\n"

        if (vida < 5) {
            this.vida++

            log += "\n[ ♥ ] JUNTO COM A EXPERIÊNCIA ADQUIRIDA VOCÊ SE SENTE REVIGORADO, SUA VIDA AGORA É ${this.vida}\n"

        }
        return log
    }

    open fun removerItem (jogador: PersonagemJogador){
        if (jogador.durabilidadeataque == 0) {
            jogador.inventario.remove(jogador.inventario.find { it.get(0) == "arma" })
            ataqueitem = 0
        } else{
            jogador.inventario.remove(jogador.inventario.find { it.get(0) == "armadura" })
            defesaitem = 0
        }
    }

}