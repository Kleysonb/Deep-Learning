# Aula 02

Aula introdutória sobre Redes Neurais.

## Dicionário de Símbolos

### Sinais de entrada { X1, X2, …, Xn }: 

São os sinais externos normalmente normalizados
para incrementar a eficiência computacional dos algoritmos de aprendizagem. São os
dados que alimentam seu modelo preditivo.

### Pesos sinápticos { W1, W2, …, Wn }: 

São valores para ponderar os sinais de cada
entrada da rede. Esses valores são aprendidos durante o treinamento.

### Combinador linear { Σ }: 

Agregar todos sinais de entrada que foram ponderados pelos
respectivos pesos sinápticos a fim de produzir um potencial de ativação.

### Limiar de ativação { Θ }: 

Especifica qual será o patamar apropriado para que o resultado
produzido pelo combinador linear possa gerar um valor de disparo de ativação.

### Potencial de ativação { u }: 

É o resultado obtido pela diferença do valor
produzido entre o combinador linear e o limiar de ativação. Se o valor for
positivo, ou seja, se u ≥ 0 então o neurônio produz um potencial excitatório;
caso contrário, o potencial será inibitório.

### Função de ativação { g }: 

Seu objetivo é limitar a saída de um neurônio em
um intervalo valores.

### Sinal de saída { y }: 

É o valor final de saída podendo ser usado como
entrada de outros neurônios que estão sequencialmente interligados.


## Atividade: Implementação AND

Dado um conjunto de entradas NA MATRIZ abaixo, onde cada linha é um exemplo e cada
coluna uma característica

```
[0,0; 0,1;1,0;1,1]
```

Em que cada posicao do vetor a seguir representa a classe (rótulos) de cada exemplo

```
[0,0,0,1]
```

* Teste com os pesos w0=0, w1=0, w2=0.

    y(0): 0.0
    Acertou
    y(1): 0.0
    Acertou
    y(2): 0.0
    Acertou
    y(3): 0.0
    Errou

* Teste com os pesos w0=-1, w1=1, w2=0.5.

    y(0): 1.0
    Errou
    y(1): 1.0
    Errou
    y(2): 0.0
    Acertou
    y(3): 1.0
    Acertou

* Teste com os pesos w0=1, w1=-1, w2=0.5.

    y(0): 1.0
    Errou
    y(1): 0.0
    Acertou
    y(2): 1.0
    Errou
    y(3): 1.0
    Acertou

Para quais pesos as saídas reais batem com o rótulo? 

    Não foi possível encontrar os rótulos com os pesos informados.