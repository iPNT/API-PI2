package br.iesb.poo.rpg.loja

import br.iesb.poo.rpg.personagem.PersonagemJogador
import java.io.File
import java.nio.file.Paths


open class Itens(
    identrada: String?,
    tipoentrada: String,
    nomeentrada: String,
    efeitoentrada: String,
    precoentrada: Int,
    jogador: PersonagemJogador
) {

    var preco: Int = -1

    var id: String? = ""

    var nome: String = ""

    var efeito: String = ""

    var tipo: String = ""

    var qtd: Int = 0


    init {


        id = identrada

        nome = nomeentrada

        efeito = efeitoentrada


        //1-pocao 2-arma 3-armadura
        tipo = tipoentrada

        preco = precoentrada


    }

    open fun buscar(id: String?): ArrayList<String> {
        var arrAux: ArrayList<String>
        var retorno = arrayListOf<String>()

        File("iesbRPG/src/main/kotlin/br/iesb/poo/rpg/loja/item.txt").forEachLine {//[0] - id | [1] - tipo | [2] -nome | [3] -efeito | [4] - preco
            arrAux = it.split("|") as ArrayList<String>
            if (arrAux[0] == id) {
                retorno = arrAux
            }
        }
        return retorno
    }


    open fun efeito(jogador: PersonagemJogador, id: String?) {
        var eff = buscar(id)[3].split(".") as ArrayList<String>
        if (eff[0] == "hp") { //jogador.eff[0]
            jogador.vida = jogador.vida + eff[1].toInt()

        } else if (eff[0] == "atk") {
            jogador.ataqueitem = jogador.ataqueitem + eff[1].toInt()
            jogador.durabilidadeataque = jogador.durabilidadeataque + 3

        } else if (eff[0] == "def") {
            jogador.defesaitem = jogador.defesaitem + eff[1].toInt()
            jogador.durabilidadedefesa = jogador.durabilidadedefesa + 3
        }
    }


}



