package br.iesb.poo.rpg

import br.iesb.poo.RPG
import br.iesb.poo.rpg.personagem.PersonagemJogador
import br.iesb.poo.rpg.personagem.PersonagemMonstro

enum class TipoPersonagem {
    PERSONAGEM_MONSTRO,
    PERSONAGEM_CHEFE
}

class Rpg {

    val jogadores = mutableListOf<PersonagemJogador>()
    val monstros = mutableListOf<PersonagemMonstro>()

    private val listaNomes = arrayOf(
        "Bei, ",
        "Thok, ",
        "Mashbu, ",
        "Lurtz, "
    )


    private val listaTitulos = arrayOf(
        "o Comilão",
        "o Banguela",
        "o Preguiçoso",
        "o Destraido"
    )

    private val listaChefe = arrayOf(
        "Blexoverreth, o Terrivel",
        "Nedraco, o não Bonito",
        "Hanthad, o Perverso",
        "Kenniston Funkeiro",
        "P3, a Prova"
    )

    fun criarMonstro(
        tipoPersonagem: TipoPersonagem
    ): PersonagemMonstro {

        val novoPersonagem = if (tipoPersonagem == TipoPersonagem.PERSONAGEM_MONSTRO){
            PersonagemMonstro(
                novaRaca = 1,
                (listaNomes).random() + (listaTitulos).random(),
                elementoMonstro = (1..4).random(),
                RPG
            )
        } else{
            PersonagemMonstro(
                novaRaca = 2,
                (listaChefe).random(),
                elementoMonstro = 1,
                RPG
            )
        }

        monstros.add(novoPersonagem)
        return novoPersonagem
    }

}
