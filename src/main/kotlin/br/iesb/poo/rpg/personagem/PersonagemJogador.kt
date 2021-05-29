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

    private fun definirStatusBase(){ //Inicialização dos statusBase

        if (classe == 1) {

            this.maxVida = 5
            this.maxMana = 5
            this.maxAtaque = 6
            this.maxDefesa = 5
            this.maxVelocidade = 7

        } else {

            this.maxVida = 7
            this.maxMana = 5
            this.maxAtaque = 6
            this.maxDefesa = 5
            this.maxVelocidade = 5

        }
    }

    private fun morrerJogador(rpg: Rpg): String {

        rpg.jogadores.remove(rpg.jogadores.find { it.id == this.id })
        return "\n[ ✝ ] VOCÊ MORREU, SEU PERSONAGEM FOI DELETADO\n"
    }

    override fun derrota(rpg: Rpg): String { //Sugestão: retirar o negócio da redução de vida, manter a consequêcia do dinhiero

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
            xpProxNv += i * 100 //Sugestão: Mudar o numero multiplicado por 500, para não facilitar o aumento de nível, mas tbm não deixar muito dificil
                                //Colocar o While pra pegar o i até chegar no nível seguinte do nível atual, ou seja, fazer this.nivel + 1
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

            this.maxVida += ((2 * statusBaseVida) * nivel)/100 + nivel + 8
            this.maxMana += ((2 * statusBaseMana) * nivel)/100 + nivel + 10
            this.maxAtaque += ((2 * statusBaseAtaque) * nivel)/100 + nivel + 10
            this.maxDefesa += ((2 * statusBaseDefesa) * nivel)/100 + nivel + 10
            this.maxVelocidade += ((2 * statusBaseVelocidade) * nivel)/100 + nivel + 12

        } else {

            this.maxVida += ((2 * statusBaseVida) * nivel)/100 + nivel + 10
            this.maxMana += ((2 * statusBaseMana) * nivel)/100 + nivel + 8
            this.maxAtaque += ((2 * statusBaseAtaque) * nivel)/100 + nivel + 12
            this.maxDefesa += ((2 * statusBaseDefesa) * nivel)/100 + nivel + 10
            this.maxVelocidade += ((2 * statusBaseVelocidade) * nivel)/100 + nivel + 10

        }

        var log = "\n[ ↑ ] VOCÊ UPOU E AGORA ESTÁ NO NÍVEL ${this.nivel}\n"

//        if (vida < 5) {
//            this.vida++
//
//            log += "\n[ ♥ ] JUNTO COM A EXPERIÊNCIA ADQUIRIDA VOCÊ SE SENTE REVIGORADO, SUA VIDA AGORA É ${this.vida}\n"
//
//        }
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