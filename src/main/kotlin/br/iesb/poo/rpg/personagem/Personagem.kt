package br.iesb.poo.rpg.personagem

import br.iesb.poo.rpg.Rpg

open class Personagem(nick: String, element: Int) {

    var id: Int = -1
    var nome: String = nick

    var sorte: Int = 0
    var nivel: Int = 1
    var dinheiro: Int = 10

    //Vai ser o local aonde vai armazenar as mudanças definitivas nos status

    var maxVida: Int = 1
    var maxMana: Int = 1
    var maxAtaque: Int = 1
    var maxDefesa: Int = 1
    var maxVelocidade: Int = 1

    //Vai ser o local aonde vai armazenar as mudanças temporárias nos status, como em batalhas

    var pontosVida: Int = 1
    var pontosMana: Int = 1
    var ataque: Int = 1
    var defesa: Int = 1
    var velocidade: Int = 1

    //Nós vamos definir os statusBase de cada classe e mosntro para serem usados no levelUp (Ex: statusBase de um Pokémon no Lv 1)

    var statusBaseVida:Int = 1
    var statusBaseMana: Int = 1
    var statusBaseAtaque: Int = 1
    var statusBaseDefesa: Int = 1
    var statusBaseVelocidade: Int = 1

    private var stab: Int = 2
    var bonusEfetivo: Int = 2


    // Água - 1; Fogo - 2; Natureza - 3; Luz - 4; Escuridão - 5;
    // Água > Fogo > Natureza > Água || Luz > Escuridão > Luz
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

    fun calcularDano(nivelAtacante: Int,
                     poderAtaque: Int,
                     statusAtaqueAtacante: Int,
                     statusDefesaVitima: Int,
                     elementoAtaque: Int,
                     elementoAtacante: Int,
                     elementoVitima: Int): Int{ //Função que calcula o dano de um ataque

        if(elementoAtaque == elementoAtacante){

            if(elementoAtacante == 1 && elementoVitima == 2){

                return (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * bonusEfetivo

            }
            else if(elementoAtacante == 2 && elementoVitima == 3){

                return (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * bonusEfetivo

            }
            else if(elementoAtacante == 3 && elementoVitima == 1){

                return (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * bonusEfetivo

            }
            else if(elementoAtacante == 4 && elementoVitima == 5){

                return (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * bonusEfetivo

            }
            else if(elementoAtacante == 5 && elementoVitima == 4){

                return (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * bonusEfetivo

            }
            else{ //Vai ser só o STAB

                return (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab

            }
        }
        else {

            return ((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante) / statusDefesaVitima) / 50) + 2
        }

    }


    fun calcularCura(nivelAtacante: Int, poderAtaque: Int, statusMaxManaAtacante: Int, elementoAtacante: Int): Int{

        if(elementoAtacante == 4){

            return (((nivelAtacante * poderAtaque) * statusMaxManaAtacante) / 200) * stab

        }
        else{

            return ((nivelAtacante * poderAtaque) * statusMaxManaAtacante) / 200 //Valor divisor a ser ajustado com testes de balanceamento
        }

    }

    //INTERFACES?
    protected open fun genId(rpgAtual: Rpg): Int {
        return 0
    }

    open fun derrota(rpg: Rpg): String {
        return ""
    }
}