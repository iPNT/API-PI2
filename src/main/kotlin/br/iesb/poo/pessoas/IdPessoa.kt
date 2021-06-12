package br.iesb.poo.pessoas

import br.iesb.poo.rpg.Rpg
import kotlin.collections.ArrayList

class IdPessoa (rpgAtual: Rpg){

    var id: Int = 0

    init {
        id = genId(rpgAtual)

    }

    fun genId(rpgAtual: Rpg): Int {

        var novaId = (0..10000).random()
        while (rpgAtual.ids.find { it.id == novaId } != null) { //TODO EVITAR LOOP INFINITO(CONTADOR?)
            novaId = (0..10000).random()
        }
        return novaId
    }

}