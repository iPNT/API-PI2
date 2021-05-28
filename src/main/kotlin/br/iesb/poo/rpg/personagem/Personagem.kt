package br.iesb.poo.rpg.personagem

import br.iesb.poo.rpg.Rpg

open class Personagem(nick: String, element: Int) {

    var id: Int = -1
    var nome: String = nick

    var sorte: Int = 0
    var nivel: Int = 1
    var dinheiro: Int = 10

    var maxVida: Int = 1
    var maxMana: Int = 1
    var maxAtaque: Int = 1
    var maxDefesa: Int = 1
    var maxVelocidade: Int = 1

    var pontosVida: Int = 1
    var pontosMana: Int = 1
    var ataque: Int = 1
    var defesa: Int = 1
    var velocidade: Int = 1

    // Água - 1; Fogo - 2; Ar - 3; Terra - 4;
    // Água > Fogo > Ar > Terra > Água [...]

    var elemento: Int = element

    fun getMaxVida(jogador: Personagem): Int{

        return jogador.maxVida
    }

    fun getMaxMana(jogador: Personagem): Int{

        return jogador.maxMana
    }

    fun getMaxAtaque(jogador: Personagem): Int{

        return jogador.maxAtaque
    }

    fun getMaxDefesa(jogador: Personagem): Int{

        return jogador.maxDefesa
    }

    fun getMaxVelocidade(jogador: Personagem): Int{

        return jogador.maxVelocidade
    }

    //INTERFACES?
    protected open fun genId(rpgAtual: Rpg): Int {
        return 0
    }

    open fun derrota(rpg: Rpg): String {
        return ""
    }
}