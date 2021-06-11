package br.iesb.poo.rpg.personagem

import br.iesb.poo.rpg.Rpg

open class Personagem(nick: String, element: Int) {

    var id: Int = -1
    var nome: String = nick

    var sorte: Int = 0
    var nivel: Int = 1
    var dinheiro: Int = 10
    var magia: Int = 0

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

    var elemento: Int = element

    fun calcularDano(nivelAtacante: Int,
                     poderAtaque: Int,
                     statusAtaqueAtacante: Int,
                     statusDefesaVitima: Int,
                     elementoAtaque: Int,
                     elementoAtacante: Int,
                     elementoVitima: Int): Int{ //Função que calcula o dano de um ataque

        if(elementoAtaque != elementoAtacante) {

            stab = 1

        }

            //INICIO AGUA

        var dano: Int = 0

            if(elementoAtaque == 1 && elementoVitima == 1){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab

            } else if(elementoAtaque == 1 && elementoVitima == 2){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * bonusEfetivo


            } else if(elementoAtaque == 1 && elementoVitima == 3){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * 1/2


            } else if(elementoAtaque == 1 && elementoVitima == 4){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


            } else if(elementoAtaque == 1 && elementoVitima == 5){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab

            }

            //INICIO FOGO

            else if(elementoAtaque == 2 && elementoVitima == 1){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * 1/2

            } else if(elementoAtaque == 2 && elementoVitima == 2){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


            } else if(elementoAtaque == 2 && elementoVitima == 3){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * bonusEfetivo


            } else if(elementoAtaque == 2 && elementoVitima == 4){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


            } else if(elementoAtaque == 2 && elementoVitima == 5){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab

            }

            //INICIO NATUREZA

            else if(elementoAtaque == 3 && elementoVitima == 1){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * bonusEfetivo

            } else if(elementoAtaque == 3 && elementoVitima == 2){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * 1/2


            } else if(elementoAtaque == 3 && elementoVitima == 3){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


            } else if(elementoAtaque == 3 && elementoVitima == 4){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


            } else if(elementoAtaque == 3 && elementoVitima == 5){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab

            }

            //INICIO LUZ

            else if(elementoAtaque == 4 && elementoVitima == 1){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab

            } else if(elementoAtaque == 4 && elementoVitima == 2){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


            } else if(elementoAtaque == 4 && elementoVitima == 3){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


            } else if(elementoAtaque == 4 && elementoVitima == 4){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


            } else if(elementoAtaque == 4 && elementoVitima == 5){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * bonusEfetivo

            }

            //INICIO ESCURIDAO

            else if(elementoAtaque == 5 && elementoVitima == 1){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab

            } else if(elementoAtaque == 5 && elementoVitima == 2){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


            } else if(elementoAtaque == 5 && elementoVitima == 3){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab


            } else if(elementoAtaque == 5 && elementoVitima == 4){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab * bonusEfetivo


            } else if(elementoAtaque == 5 && elementoVitima == 5){

                dano = (((((2 * nivelAtacante) + 2) * poderAtaque * (statusAtaqueAtacante)/statusDefesaVitima) / 50) + 2) * stab

            }

        reduzMana(dano)

        return dano
    }

    fun atacarPersonagem(ataquePersonagem: Int, defesaInimigo: Int): Int{

        var dano: Int = 0

        dano = (ataquePersonagem - 2 .. ataquePersonagem + 2).random() - (defesaInimigo/2)

        return dano


    }

    fun defender(defesaPersonagem: Int): Int {

        var defesaP: Int = defesaPersonagem

        defesaP += defesaP/2

        return defesaP
    }


    fun calcularCura(nivelAtacante: Int, poderAtaque: Int, statusMaxManaAtacante: Int): Int{

        var cura: Int = 0
        var dano: Int = 0


        cura = ((nivelAtacante * poderAtaque) * statusMaxManaAtacante)
        this.pontosVida += cura

        if (this.pontosVida > this.maxVida){
            this.pontosVida = this.maxVida
        }


        return dano

    }

    fun usarMagia(nivelAtacante: Int,
                  upgradeMagia: Int,
                  statusAtaqueAtacante: Int,
                  statusDefesaVitima: Int,
                  elementoAtacante: Int,
                  elementoVitima: Int): Int{

        var dano: Int = 0

        when (magia){
            1-> dano = calcularDano(nivelAtacante, 10 + upgradeMagia, statusAtaqueAtacante, statusDefesaVitima, 1, elementoAtacante, elementoVitima)
            2-> dano = calcularDano(nivelAtacante, 10 + upgradeMagia, statusAtaqueAtacante, statusDefesaVitima, 2, elementoAtacante, elementoVitima)
            3-> dano = calcularDano(nivelAtacante, 10 + upgradeMagia, statusAtaqueAtacante, statusDefesaVitima, 3, elementoAtacante, elementoVitima)
            4-> dano = calcularDano(nivelAtacante, 10 + upgradeMagia, statusAtaqueAtacante, statusDefesaVitima, 4, elementoAtacante, elementoVitima)
            5-> dano = calcularDano(nivelAtacante, 10 + upgradeMagia, statusAtaqueAtacante, statusDefesaVitima, 5, elementoAtacante, elementoVitima)
            6-> dano = calcularDano(nivelAtacante, 10 + upgradeMagia, statusAtaqueAtacante, statusDefesaVitima, 6, elementoAtacante, elementoVitima)
        }

        return dano

    }


    fun reduzMana(dmg: Int){
        this.pontosMana = dmg - 10/100
    }

    //INTERFACES?
    protected open fun genId(rpgAtual: Rpg): Int {
        return 0
    }

    open fun derrota(rpg: Rpg): String {
        return ""
    }
}