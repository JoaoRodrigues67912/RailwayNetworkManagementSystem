Descrição

Este projeto consiste numa aplicação em linha de comandos que permite gerir uma rede ferroviária composta por:

-Linhas
-Estações
-Horários de comboios

O sistema suporta a criação, remoção e consulta de linhas e horários, bem como a pesquisa de percursos e comboios por estação.

A aplicação lê comandos da entrada padrão e escreve os resultados na saída padrão, seguindo um formato rigoroso definido no enunciado do trabalho.

Funcionalidades
O sistema implementa os seguintes comandos:
Comando	Descrição
IL	Inserção de linha
RL	Remoção de linha
CL	Consulta das estações de uma linha
CE	Consulta das linhas de uma estação
IH	Inserção de horário
RH	Remoção de horário
CH	Consulta dos horários de uma linha
LC	Listagem de comboios por estação
MH	Melhor horário entre duas estações
TA	Terminar aplicação

Persistência de dados
O estado do sistema é guardado automaticamente em disco quando a aplicação termina e carregado na execução seguinte, através de serialização em Java.
