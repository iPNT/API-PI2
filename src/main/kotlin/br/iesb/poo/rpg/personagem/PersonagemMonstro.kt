package br.iesb.poo.rpg.personagem

import br.iesb.poo.rpg.Rpg

class PersonagemMonstro(
        novaRaca: Int,
        nomeMonstro: String,
        elementoMonstro: Int,
        rpgAtual: Rpg
) : Personagem(nomeMonstro, elementoMonstro) {


    var raca: Int = 0
    var derrotado = false

    init {
        id = genId(rpgAtual)
        raca = novaRaca


    }


    fun definirMonstro(nivelMasmorra: Int){ //Inicialização dos statusBase

        this.maxAtaque = 1
        this.statusBaseAtaque = 6

        if (raca == 1) {

           // nivelMasmorra += (-2..2).random()

            this.maxVida = 5
            this.maxMana = 5
            this.maxDefesa = 5
            this.maxVelocidade = 5

            this.maxAtaque = 5
            this.statusBaseAtaque = 5

            this.statusBaseVida = 5
            this.statusBaseMana = 5
            this.statusBaseDefesa = 5
            this.statusBaseVelocidade = 5


        } else{

            this.maxVida = 10
            this.maxMana = 10
            this.maxDefesa = 10
            this.maxVelocidade = 0

            this.statusBaseVida = 10
            this.statusBaseMana = 10
            this.statusBaseDefesa = 10
            this.statusBaseVelocidade = 10

        }

        this.maxVida = ((2 * statusBaseVida) * nivelMasmorra)/2
        this.maxMana = ((2 * statusBaseMana) * nivelMasmorra)/2 + nivelMasmorra
        this.maxAtaque = ((2 * statusBaseAtaque) * nivelMasmorra)/2
        this.maxDefesa = ((2 * statusBaseDefesa) * nivelMasmorra)/2
        this.maxVelocidade = ((2 * statusBaseVelocidade) * nivelMasmorra)/4

        this.pontosVida = this.maxVida
        this.pontosMana = this.maxMana
        this.ataque = this.maxAtaque
        this.defesa = this.maxDefesa
        this.velocidade = this.maxVelocidade
    }

    override fun genId(rpgAtual: Rpg): Int {
        var novaId = (0..10000).random()
        while (rpgAtual.monstros.find{it.id == novaId} != null){ //TODO EVITAR LOOP INFINITO(CONTADOR?)
            novaId = (0..10000).random()
        }
        return novaId
    }

    override fun derrota(rpg: Rpg): String {
        this.derrotado = true
        return "[ c: ] ${this.nome} FOI DERROTADO\n"
    }
}